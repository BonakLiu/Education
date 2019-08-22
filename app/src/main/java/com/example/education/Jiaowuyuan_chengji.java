package com.example.education;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.education.objects.Section;
import com.example.education.objects.User;
import com.example.education.tools.HttpsConnect;
import com.example.education.tools.HttpsListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Jiaowuyuan_chengji extends AppCompatActivity {
    private Button check;
    private TextView view;
    private EditText id;
    private EditText grade;
    private Button tijiao;

    private String sectionid = "weixuanze";
    private List<Section> sectionData = new ArrayList<>();
    private final String getSectionListUrl = "http://129.211.12.161:8080/Login/selectServlet";
    private final String getStudentsListUrl = "http://129.211.12.161:8080/Login/GetRoll";
    private final String setGradetUrl = "http://129.211.12.161:8080/Login/setGrade";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiaowuyuan_chengji);
        HttpsConnect.sendRequest(getSectionListUrl, "POST", getJsonDataSection(), new HttpsListener() {
            @Override
            public void success(String response) {
                catchResponseSection(response);
            }

            @Override
            public void failed(Exception exception) {
                exception.printStackTrace();
            }
        });
        final SectionAdapter adapter = new SectionAdapter(Jiaowuyuan_chengji.this, R.layout.section_item, sectionData);
        final ListView sectionList = (ListView) findViewById(R.id.chengji_section);

        check = (Button) findViewById(R.id.chengji_check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpsConnect.sendRequest(getStudentsListUrl, "POST", getJsonDataStu(), new HttpsListener() {
                    @Override
                    public void success(String response) {
                        catchResponseStu(response);
                    }

                    @Override
                    public void failed(Exception exception) {
                        exception.printStackTrace();
                    }
                });
            }
        });
        view = (TextView) findViewById(R.id.chengji_view);
        id = (EditText) findViewById(R.id.chengji_id);
        grade = (EditText) findViewById(R.id.chengji_grade);
        tijiao = (Button) findViewById(R.id.chengji_tijiao);
        tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpsConnect.sendRequest(setGradetUrl, "POST", getJsonDataGrade(), new HttpsListener() {
                    @Override
                    public void success(String response) {
                        catchResponseGrade(response);
                    }

                    @Override
                    public void failed(Exception exception) {
                        exception.printStackTrace();
                    }
                });
            }
        });
        try {
            Thread.sleep(300);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sectionList.setAdapter(adapter);
        sectionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Section section = sectionData.get(i);
                sectionid = section.getSectionid();
                adapter.setSelectedPosition(i);
                adapter.notifyDataSetInvalidated();
                Toast.makeText(Jiaowuyuan_chengji.this, "已选择" + section.getCoursename(), Toast.LENGTH_LONG).show();
            }
        });
    }

    //获取
    private JSONObject getJsonDataSection() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", 0);//等后端改
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void catchResponseSection(final String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String classroom = jsonObject.getString("classroom");
                String courseid = jsonObject.getString("courseid");
                String coursename = jsonObject.getString("coursename");
                String day = jsonObject.getString("day");
                String enddate = jsonObject.getString("enddate");
                String sectionid = jsonObject.getString("sectionid");
                String startdate = jsonObject.getString("startdate");
                String yuliang = jsonObject.getString("margin");
                String tid = jsonObject.getString("tid");
                String timeslot = jsonObject.getString("timeslot");
                Section section = new Section(classroom, courseid, coursename, day, enddate, sectionid, startdate, tid, timeslot, yuliang);
                sectionData.add(section);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取学生列表
    private JSONObject getJsonDataStu() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", User.getUserType());
            jsonObject.put("sectionid", sectionid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void catchResponseStu(final String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            String temp = "";
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                temp += jsonObject.getString("sid");
                temp += "\n";
            }
            view.setText(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //设置成绩

    private JSONObject getJsonDataGrade() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", User.getUserType());
            jsonObject.put("sectionid", sectionid);
            jsonObject.put("grade", grade.getText());
            jsonObject.put("sid", id.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void catchResponseGrade(final String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            boolean result = jsonObject.getBoolean("result");
            String reason = jsonObject.getString("reason");
            if (result) {
                Toast.makeText(this, "成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, reason, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
