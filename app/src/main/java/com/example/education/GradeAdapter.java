package com.example.education;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.education.objects.Course;
import com.example.education.objects.Grade;

import java.util.List;

public class GradeAdapter extends ArrayAdapter<Grade> {
    private int resourseId;

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    private int selectedPosition = -1;

    public GradeAdapter(Context context, int textViewResourceId, List<Grade> objects) {
        super(context,textViewResourceId,objects);
        resourseId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Grade grade = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourseId, parent, false);
        TextView coursename = (TextView) view.findViewById(R.id.grade_item_coursename);
        TextView grape = (TextView) view.findViewById(R.id.grade_item_grade);
        TextView gpa = (TextView) view.findViewById(R.id.grade_item_gpa);
        coursename.setText(grade.getCourseName());
        grape.setText(grade.getGrade());
        gpa.setText(grade.getGpa());
        if (selectedPosition == position) {
            view.setBackgroundColor(Color.parseColor("#F4FA58"));
        } else {
            view.setBackgroundColor(Color.TRANSPARENT);
        }
        return view;
    }
}
