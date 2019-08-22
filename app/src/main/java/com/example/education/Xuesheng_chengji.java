package com.example.education;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.education.objects.Section;
import com.example.education.objects.User;

import org.json.JSONArray;
import org.json.JSONObject;

public class Xuesheng_chengji extends AppCompatActivity {
    private TextView xuesheng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuesheng_chengji);
        xuesheng = (TextView) findViewById(R.id.chengji_xuesheng);

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
//在这里写
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
