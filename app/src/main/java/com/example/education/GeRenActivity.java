package com.example.education;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.education.objects.User;

public class GeRenActivity extends AppCompatActivity {

    private TextView id;
    private TextView college;
    private TextView major;
    private TextView name;
    private TextView grade;
    private TextView banji;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ge_ren);
        id = (TextView) findViewById(R.id.geren_id);
        college = (TextView) findViewById(R.id.geren_college);
        major = (TextView) findViewById(R.id.geren_major);
        name = (TextView) findViewById(R.id.geren_name);
        grade = (TextView) findViewById(R.id.geren_grade);
        banji = (TextView) findViewById(R.id.geren_banji);
        id.setText(User.getId());
        college.setText(User.getCollege());
        major.setText(User.getMajor());
        name.setText(User.getName());
        grade.setText(User.getGrade());
        banji.setText(User.getBanji());
    }
}
