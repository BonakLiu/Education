package com.example.education;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.education.objects.Course;

import java.util.List;

public class CourseAdapter extends ArrayAdapter<Course> {
    private int resourseId;

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    private int selectedPosition = -1;

    public CourseAdapter(Context context, int textViewResourceId, List<Course> objects) {
        super(context, textViewResourceId, objects);
        resourseId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Course course = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourseId, parent, false);
        TextView id = (TextView) view.findViewById(R.id.course_item_id);
        TextView name = (TextView) view.findViewById(R.id.course_item_name);
        TextView credits = (TextView) view.findViewById(R.id.course_item_credits);
        id.setText(course.getId());
        name.setText(course.getName());
        credits.setText(course.getCredits());
        if (selectedPosition == position) {
            view.setBackgroundColor(Color.parseColor("#F4FA58"));
        } else {
            view.setBackgroundColor(Color.TRANSPARENT);
        }
        return view;
    }
}
