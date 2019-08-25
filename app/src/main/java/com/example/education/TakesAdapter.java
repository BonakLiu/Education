package com.example.education;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.education.objects.Course;
import com.example.education.objects.Takes;

import java.util.List;

public class TakesAdapter extends ArrayAdapter<Takes> {
    private int resourseId;

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    private int selectedPosition = -1;

    public TakesAdapter(Context context, int textViewResourceId, List<Takes> objects) {
        super(context, textViewResourceId, objects);
        resourseId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Takes takes = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourseId, parent, false);
        TextView sname = (TextView) view.findViewById(R.id.takes_item_sname);
        TextView tname = (TextView) view.findViewById(R.id.takes_item_tname);
        sname.setText(takes.getSname());
        tname.setText(takes.getTname());
        if (selectedPosition == position) {
            view.setBackgroundColor(Color.parseColor("#F4FA58"));
        } else {
            view.setBackgroundColor(Color.TRANSPARENT);
        }
        return view;
    }
}
