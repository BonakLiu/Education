package com.example.education;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.education.objects.User;
import com.example.education.tools.*;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText idText;
    private EditText pwdText;
    private RadioGroup typeSelect;
    private RadioButton typeStudent;
    private RadioButton typeTeacher;
    private RadioButton typeMaster;
    private Button loginButton;

    private int type = 3;
    private final String address = "http://129.211.12.161:8080/Login/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        idText = (EditText) findViewById(R.id.idText);
//        idText.setText("201730682010");
        pwdText = (EditText) findViewById(R.id.pwdText);
//        pwdText.setText("aaaaaa");
        loginButton = (Button) findViewById(R.id.bt_login);
        loginButton.setOnClickListener(this);
        typeSelect = (RadioGroup) findViewById(R.id.type_select);
        typeStudent = (RadioButton) findViewById(R.id.type_student);
        typeTeacher = (RadioButton) findViewById(R.id.type_teacher);
        typeMaster = (RadioButton) findViewById(R.id.type_master);
        typeStudent.setOnClickListener(this);
        typeTeacher.setOnClickListener(this);
        typeMaster.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.type_student:
                type = 0;
                break;
            case R.id.type_teacher:
                type = 1;
                break;
            case R.id.type_master:
                type = 2;
                break;
            case R.id.bt_login:
                if (type == 3) {
                    Toast.makeText(this, "选择身份", Toast.LENGTH_SHORT);
                } else {
                    HttpsConnect.sendRequest(address, "POST", getJsonData(), new HttpsListener() {
                        @Override
                        public void success(String response) {
                            catchResponse(response);
                        }

                        @Override
                        public void failed(Exception exception) {
                            exception.printStackTrace();
                        }
                    });
                }
                break;
        }
    }

    //封装JSON
    private JSONObject getJsonData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", idText.getText());
            jsonObject.put("password", pwdText.getText());
            jsonObject.put("type", type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    //接受JSON
    private void catchResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("LOGIN_TAG", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean result = jsonObject.getBoolean("result");
                    String reason = jsonObject.getString("reason");
                    if (result) {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        User.setUserType(type);
                        User.setId(jsonObject.getString("id"));
                        User.setName(jsonObject.getString("name"));
                        User.setCollege(jsonObject.getString("college"));
                        User.setMajor(jsonObject.getString("major"));
                        User.setGrade(jsonObject.getString("grade"));
                        User.setBanji(jsonObject.getString("banji"));
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, reason, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
