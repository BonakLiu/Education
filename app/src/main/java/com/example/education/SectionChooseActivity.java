package com.example.education;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.education.objects.Course;
import com.example.education.objects.Section;
import com.example.education.tools.HttpsConnect;
import com.example.education.tools.HttpsListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SectionChooseActivity extends AppCompatActivity {
    private final String getSectionListUrl = "http://129.211.12.161:8080/Login/selectServlet";
    private final String commitCourseUrl = "http://129.211.12.161:8080/Login/Section";
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
        final ListView sectionList = (ListView) findViewById(R.id.section_choose_section);
        final SectionAdapter adapter = new SectionAdapter(SectionChooseActivity.this, R.layout.course_item, sectionData);
        sectionList.setAdapter(adapter);
        sectionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Section section = sectionData.get(i);
//                courseid = course.getId();
//                coursename = course.getName();
                adapter.add(i);
                adapter.notifyDataSetInvalidated();
                Toast.makeText(SectionChooseActivity.this, "已选择" + section.getCoursename(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private JSONObject getJsonDataSection() {
        JSONObject jsonObject = new JSONObject();
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
}
