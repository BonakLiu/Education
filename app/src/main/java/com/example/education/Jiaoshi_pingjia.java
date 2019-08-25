package com.example.education;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.education.objects.Commend;
import com.example.education.objects.Course;
import com.example.education.objects.User;
import com.example.education.tools.HttpsConnect;
import com.example.education.tools.HttpsListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Jiaoshi_pingjia extends AppCompatActivity {
    private TextView coursename;
    private TextView mark;
    private TextView commend;
    private Button shang;
    private Button xia;

    private int index=0;
    //接收
    private List<Commend> commendData = new ArrayList<>();
    private final String checkpingjiaUrl = "http://129.211.12.161:8080/Login/CheckCommend";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiaoshi_pingjia);
        HttpsConnect.sendRequest(checkpingjiaUrl, "POST", getJsonDatap(), new HttpsListener() {
            @Override
            public void success(String response) {
                catchResponsep(response);
            }

            @Override
            public void failed(Exception exception) {
                exception.printStackTrace();
            }
        });
        coursename = (TextView) findViewById(R.id.jiaoshi_pingjia_coursename);
        mark = (TextView) findViewById(R.id.jiaoshi_pingjia_pingfen);
        commend = (TextView) findViewById(R.id.jiaoshi_pingjia_neirong);
        shang = (Button) findViewById(R.id.jiaoshi_pingjia_shang);
        xia = (Button) findViewById(R.id.jiaoshi_pingjia_xia);
        shang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index != 0) {
                    index--;
                    coursename.setText(commendData.get(index).getCoursename());
                    mark.setText(commendData.get(index).getMark());
                    commend.setText(commendData.get(index).getCommend());
                }
            }
        });
        xia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index != commendData.size()) {
                    index++;
                    coursename.setText(commendData.get(index).getCoursename());
                    mark.setText(commendData.get(index).getMark());
                    commend.setText(commendData.get(index).getCommend());
                }
            }
        });
    }

    private JSONObject getJsonDatap() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", User.getUserType());
            jsonObject.put("tid", User.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void catchResponsep(final String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String mark = jsonObject.getString("mark");
                String coursename = jsonObject.getString("coursename");
                String commend = jsonObject.getString("commend");
                Commend commend1 = new Commend(coursename, mark, commend);
                commendData.add(commend1);
            }
            coursename.setText(commendData.get(0).getCoursename());
            mark.setText(commendData.get(0).getMark());
            commend.setText(commendData.get(0).getCommend());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
