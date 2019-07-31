package com.example.education;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.education.objects.Course;
import com.example.education.objects.Section;

import java.util.ArrayList;
import java.util.List;

public class SectionAdapter extends ArrayAdapter<Section> {
    private int resourseId;
    private ArrayList<Integer> selectedPosition = new ArrayList<>();

    public void add(int a) {
        Integer temp = new Integer(a);
        selectedPosition.add(temp);
    }
    public void remove(int a) {
        Integer temp = new Integer(a);
        selectedPosition.remove(temp);
    }

    public SectionAdapter(Context context, int textViewResourceId, List<Section> objects) {
        super(context, textViewResourceId, objects);
        resourseId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Section section = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourseId, parent, false);
        TextView kechengming = (TextView) view.findViewById(R.id.section_item_kechengming);
        TextView kaishizhou = (TextView) view.findViewById(R.id.section_item_kaishizhou);
        TextView jieshuzhou = (TextView) view.findViewById(R.id.section_item_jieshuzhou);
        TextView shangkeri = (TextView) view.findViewById(R.id.section_item_shangkeri);
        TextView jeici = (TextView) view.findViewById(R.id.section_item_jieci);
        TextView keshi = (TextView) view.findViewById(R.id.section_item_keshi);
        TextView yuliang = (TextView) view.findViewById(R.id.section_item_yuliang);
        kechengming.setText(section.getCoursename());
        kaishizhou.setText(section.getStartdate());
        jieshuzhou.setText(section.getEnddate());
        shangkeri.setText(section.getDay());
        jeici.setText(section.getTimeslot());
        keshi.setText(section.getClassroom());
        yuliang.setText(section.getYuliang());
        if (contain(selectedPosition,position)) {
            view.setBackgroundColor(Color.parseColor("#F4FA58"));
        } else {
            view.setBackgroundColor(Color.TRANSPARENT);
        }
        return view;
    }

    public boolean contain(ArrayList<Integer> array, int a) {
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i) == a) {
                return true;
            }
        }
        return false;
    }
}

