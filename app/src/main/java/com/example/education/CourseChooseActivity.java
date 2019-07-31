package com.example.education;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.education.objects.Course;
import com.example.education.objects.User;
import com.example.education.tools.HttpsConnect;
import com.example.education.tools.HttpsListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CourseChooseActivity extends AppCompatActivity {
    private Spinner kaishizhou;
    private Spinner jieshuzhou;
    private Spinner xingqi;
    private Spinner jiaci;
    private EditText jiaoshi;
    private EditText jiaoshiid;
    private Button commit;
    //接收
    private List<Course> courseData = new ArrayList<>();
    //发送
    private String sectionid;
    private String courseid;
    private String coursename;
    private String startdate;
    private String enddate;
    private String day;
    private String timeslot;
    private String classroom;
    private String tid;

    private final String getCourseListUrl = "http://129.211.12.161:8080/Login/Course";
    private final String commitCourseUrl = "http://129.211.12.161:8080/Login/Section";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HttpsConnect.sendRequest(getCourseListUrl, "POST", getJsonDataCourse(), new HttpsListener() {
            @Override
            public void success(String response) {
                catchResponseCourse(response);
            }

            @Override
            public void failed(Exception exception) {
                exception.printStackTrace();
            }
        });
        setContentView(R.layout.activity_course_choose);
        kaishizhou = (Spinner) findViewById(R.id.course_choose_kaishizhou);
        jieshuzhou = (Spinner) findViewById(R.id.course_choose_jieshuzhou);
        xingqi = (Spinner) findViewById(R.id.course_choose_xingqi);
        jiaci = (Spinner) findViewById(R.id.course_choose_jieci);
        jiaoshi = (EditText) findViewById(R.id.course_choose_jiaoshi);
        jiaoshiid = (EditText) findViewById(R.id.course_choose_jiaoshiid);
        commit = (Button) findViewById(R.id.course_choose_commit);

        kaishizhou.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] temp = getResources().getStringArray(R.array.week);
                startdate = temp[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        jieshuzhou.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] temp = getResources().getStringArray(R.array.week);
                enddate = temp[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        xingqi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] temp = getResources().getStringArray(R.array.xingqi);
                day = temp[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        jiaci.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] temp = getResources().getStringArray(R.array.jieci);
                timeslot = temp[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpsConnect.sendRequest(commitCourseUrl, "POST", getJsonDataCommit(), new HttpsListener() {
                    @Override
                    public void success(String response) {
                        catchResponseCommit(response);
                    }

                    @Override
                    public void failed(Exception exception) {
                        exception.printStackTrace();
                    }
                });
            }
        });
        final CourseAdapter adapter = new CourseAdapter(CourseChooseActivity.this, R.layout.course_item, courseData);
        final ListView courseList = (ListView) findViewById(R.id.course_choose_course);
        courseList.setAdapter(adapter);
        courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Course course = courseData.get(i);
                courseid = course.getId();
                coursename = course.getName();
                adapter.setSelectedPosition(i);
                adapter.notifyDataSetInvalidated();
                Toast.makeText(CourseChooseActivity.this, "已选择" + course.getName(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private JSONObject getJsonDataCourse() {
        JSONObject jsonObject = new JSONObject();
        return jsonObject;
    }

    private void catchResponseCourse(final String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String credits = jsonObject.getString("credits");
                Course course = new Course(id, name, credits);
                courseData.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JSONObject getJsonDataCommit() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sectionid", Long.toString(System.currentTimeMillis()));
            jsonObject.put("courseid", courseid);
            jsonObject.put("coursename", coursename);
            jsonObject.put("margin", "80");
            jsonObject.put("startdate", startdate);
            jsonObject.put("enddate", enddate);
            jsonObject.put("day", day);
            jsonObject.put("timeslot", timeslot);
            jsonObject.put("classroom", jiaoshi.getText());
            jsonObject.put("tid", jiaoshiid.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    private void catchResponseCommit(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("LOGIN_TAG", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean result = jsonObject.getBoolean("result");
                    String reason = jsonObject.getString("reason");
                    Toast.makeText(CourseChooseActivity.this,reason,Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
