package com.example.education;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.education.objects.Course;
import com.example.education.objects.Grade;
import com.example.education.objects.Section;
import com.example.education.objects.User;
import com.example.education.tools.HttpsConnect;
import com.example.education.tools.HttpsListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Xuesheng_chengji extends AppCompatActivity {
    //接收
    private List<Grade> gradesData = new ArrayList<>();
    private final String checkgradeUrl = "http://129.211.12.161:8080/Login/CheckGrade";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuesheng_chengji);

        HttpsConnect.sendRequest(checkgradeUrl, "POST", getJsonDataChengji(), new HttpsListener() {
            @Override
            public void success(String response) {
                catchResponseChengji(response);
            }

            @Override
            public void failed(Exception exception) {
                exception.printStackTrace();
            }
        });
        //权宜之计
        try {
            Thread.sleep(300);
        } catch (Exception e) {
            e.printStackTrace();
        }
        final GradeAdapter adapter = new GradeAdapter(Xuesheng_chengji.this, R.layout.grade_item, gradesData);
        final ListView courseList = (ListView) findViewById(R.id.grade_view);
        courseList.setAdapter(adapter);
    }

    //获取
    private JSONObject getJsonDataChengji() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", User.getUserType());
            jsonObject.put("sid", User.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void catchResponseChengji(final String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            String temp = "";
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String coursename = jsonObject.getString("coursename");
                String grade = jsonObject.getString("grade");
                String gpa = jsonObject.getString("gpa");
                Grade grade1 = new Grade(coursename, grade, gpa);
                gradesData.add(grade1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
