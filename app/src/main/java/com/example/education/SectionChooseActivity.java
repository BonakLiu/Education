package com.example.education;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.education.objects.Course;
import com.example.education.objects.Section;
import com.example.education.objects.User;
import com.example.education.tools.HttpsConnect;
import com.example.education.tools.HttpsListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SectionChooseActivity extends AppCompatActivity {
    private Button tijiao;
    private TextView tishi;
    //发送
    private String sectionid = "weixuanze";
    private final String getSectionListUrl = "http://129.211.12.161:8080/Login/selectServlet";
    private final String commitCourseUrl = "http://129.211.12.161:8080/Login/chooseServlet";
    private List<Section> sectionData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_choose);
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
        tishi = (TextView) findViewById(R.id.section_choose_tishi);
        final SectionAdapter adapter = new SectionAdapter(SectionChooseActivity.this, R.layout.section_item, sectionData);
        final ListView sectionList = (ListView) findViewById(R.id.section_choose_section);
        //权宜之计
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
                Toast.makeText(SectionChooseActivity.this, "已选择" + section.getCoursename(), Toast.LENGTH_LONG).show();
            }
        });
        tijiao = (Button) findViewById(R.id.section_choose_tijiao);
        tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sectionid == "weixuanze") {
                    Toast.makeText(SectionChooseActivity.this, "请选择课程", Toast.LENGTH_LONG).show();
                } else {
                    HttpsConnect.sendRequest(commitCourseUrl, "POST", getJsonDataTakes(), new HttpsListener() {
                        @Override
                        public void success(String response) {
                            catchResponseTakes(response);
                        }

                        @Override
                        public void failed(Exception exception) {
                            exception.printStackTrace();
                        }
                    });
                }
            }
        });
    }

    //获取
    private JSONObject getJsonDataSection() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", User.getUserType());
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

    //学生选课
    private JSONObject getJsonDataTakes() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", User.getUserType());
            jsonObject.put("sid", User.getId());
            jsonObject.put("sectionid", sectionid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    //标准
    private void catchResponseTakes(final String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            boolean result = jsonObject.getBoolean("result");
            String reason = jsonObject.getString("reason");
            if (result) {
                tishi.setText("选课成功！");
            } else {
                tishi.setText(reason);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
