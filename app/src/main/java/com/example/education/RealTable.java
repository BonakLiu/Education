package com.example.education;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.education.objects.Section;
import com.example.education.objects.Subject;
import com.example.education.objects.User;
import com.example.education.tools.HttpsConnect;
import com.example.education.tools.HttpsListener;
import com.zhuangfei.timetable.TimetableView;
import com.zhuangfei.timetable.listener.ISchedule;
import com.zhuangfei.timetable.listener.IWeekView;
import com.zhuangfei.timetable.listener.OnItemBuildAdapter;
import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.view.WeekView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.education.CourseTable.getWeekList;

public class RealTable extends AppCompatActivity implements View.OnClickListener {
    TimetableView mTimetableView;
    WeekView mWeekView;
    LinearLayout layout;
    TextView titleTextView;
    List<Subject> mySubjects;

    //记录切换的周次，不一定是当前周
    int target = -1;
    AlertDialog alertDialog;

    private final String getTakesListUrl = "http://129.211.12.161:8080/Login/studentclassmap";
    public static String classString = "[";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_table);
        //隐藏标题栏
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        titleTextView = findViewById(R.id.id_title);
        layout = findViewById(R.id.id_layout);
        layout.setOnClickListener(this);
        initTimetableView();
        HttpsConnect.sendRequest(getTakesListUrl, "POST", getJsonDataTakes(), new HttpsListener() {
            @Override
            public void success(String response) {
                catchResponseTakes(response);
            }

            @Override
            public void failed(Exception exception) {
                exception.printStackTrace();
            }
        });
        requestData();
    }

    //请求数据
    private void requestData() {
        alertDialog = new AlertDialog.Builder(this)
                .setMessage("请求网络..")
                .setTitle("Tips").create();
        alertDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0x123);
            }
        }).start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (alertDialog != null) alertDialog.hide();
            mySubjects = loadDefaultSubjects();
            mWeekView.source(mySubjects).showView();
            mTimetableView.source(mySubjects).showView();
        }
    };

    //加载课程string
    public static List<Subject> loadDefaultSubjects() {
        //例子
        String json =
                "[[\"2017-2018学年秋\", \"\", \"\", \"计算机组成原理\", \"\", \"\", \"\", \"\", \"刘静\", \"\", \"\", \"1\", 1, 1, 2, \"\", \"计算机综合楼106\", \"\"]," +
                        "[\"2017-2018学年秋\", \"\", \"\", \"算法分析与设计\", \"\", \"\", \"\", \"\", \"王静\", \"\", \"\", \"1\", 1, 3, 2, \"\", \"计算机综合楼205\", \"\"], " +
                        "[\"2017-2018学年秋\", \"\", \"\", \"毛概\", \"\", \"\", \"\", \"\", \"杨晓军\", \"\", \"\", \"week6-week12,14-17\", 1, 5, 2, \"\", \"3号教学楼3208\", \"\"]," +
                        "[\"2017-2018学年秋\", \"\", \"\", \"高等数学提高\", \"\", \"\", \"\", \"\", \"彭波\", \"\", \"\", \"3-12\", 1, 9, 2, \"\", \"3号教学楼3101\", \"\"], " +
                        "[\"2017-2018学年秋\", \"\", \"\", \"面向对象分析与设计\", \"\", \"\", \"\", \"\", \"马永强\", \"\", \"\", \"1-8\", 2, 1, 2, \"\", \"计算机综合楼106\", \"\"], " +
                        "[\"2017-2018学年秋\", \"\", \"\", \"软件工程\", \"\", \"\", \"\", \"\", \"马永强\", \"\", \"\", \"6-12,14-18\", 2, 3, 2, \"\", \"计算机综合楼106\", \"\"], " +
                        "[\"2017-2018学年秋\", \"\", \"\", \"Linux原理与应用\", \"\", \"\", \"\", \"\", \"刘永利\", \"\", \"\", \"9-12,14-15\", 2, 9, 2, \"\", \"计算机综合楼205\", \"\"], " +
                        "[\"2017-2018学年秋\", \"\", \"\", \"计算机组成原理\", \"\", \"\", \"\", \"\", \"刘静\", \"\", \"\", \"8-12,14-17\", 3, 1, 2, \"\", \"计算机综合楼106\", \"\"], " +
                        "[\"2017-2018学年秋\", \"\", \"\", \"算法分析与设计\", \"\", \"\", \"\", \"\", \"王静\", \"\", \"\", \"1-12\", 3, 3, 2, \"\", \"计算机综合楼205\", \"\"], " +
                        "[\"2017-2018学年秋\", \"\", \"\", \"毛概\", \"\", \"\", \"\", \"\", \"杨晓军\", \"\", \"\", \"6-12,14-17\", 3, 5, 2, \"\", \"3号教学楼3208\", \"\"], " +
                        "[\"2017-2018学年秋\", \"\", \"\", \"高等数学提高\", \"\", \"\", \"\", \"\", \"彭波\", \"\", \"\", \"3-4\", 3, 7, 2, \"\", \"3号教学楼3101\", \"\"], " +
                        "[\"2017-2018学年秋\", \"\", \"\", \"数据库高级应用\", \"\", \"\", \"\", \"\", \"李国斌\", \"\", \"\", \"9-12,14-18\", 3, 9, 2, \"\", \"计算机综合楼202\", \"\"], " +
                        "[\"2017-2018学年秋\", \"\", \"\", \"面向对象分析与设计\", \"\", \"\", \"\", \"\", \"马永强\", \"\", \"\", \"1-8\", 4, 1, 2, \"\", \"计算机综合楼106\", \"\"], " +
                        "[\"2017-2018学年秋\", \"\", \"\", \"数字图像处理\", \"\", \"\", \"\", \"\", \"王静\", \"\", \"\", \"1-10\", 4, 3, 2, \"\", \"计算机综合楼102\", \"\"], " +
                        "[\"2017-2018学年秋\", \"\", \"\", \"数据库高级应用\", \"\", \"\", \"\", \"\", \"李国斌\", \"\", \"\", \"9-12,14-18\", 4, 5, 2, \"\", \"计算机综合楼202\", \"\"],]";
        return parse(classString);
    }

    //对json字符串进行解析
    public static List<Subject> parse(String parseString) {
        List<Subject> courses = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(parseString);
            for (int i = 0; i < array.length(); i++) {
                JSONArray array2 = array.getJSONArray(i);
                String term = array2.getString(0);
                String name = array2.getString(3);
                String teacher = array2.getString(8);
                if (array2.length() <= 10) {
                    courses.add(new Subject(term, name, null, teacher, null, -1, -1, -1, -1, null));
                    continue;
                }
                String string = array2.getString(17);
                if (string != null) {
                    string = string.replaceAll("\\(.*?\\)", "");
                }
                String room = array2.getString(16) + string;
                String weeks = array2.getString(11);
                int day, start, step;
                try {
                    day = Integer.parseInt(array2.getString(12));
                    start = Integer.parseInt(array2.getString(13));
                    step = Integer.parseInt(array2.getString(14));
                } catch (Exception e) {
                    day = -1;
                    start = -1;
                    step = -1;
                }
                courses.add(new Subject(term, name, room, teacher, getWeekList(weeks), start, step, day, -1, null));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return courses;
    }

    //初始化课程控件
    private void initTimetableView() {
        //获取控件
        mWeekView = findViewById(R.id.id_weekview);
        mTimetableView = findViewById(R.id.id_timetableView);

        //设置周次选择属性
        mWeekView.curWeek(1)
                .callback(new IWeekView.OnWeekItemClickedListener() {
                    @Override
                    public void onWeekClicked(int week) {
                        int cur = mTimetableView.curWeek();
                        //更新切换后的日期，从当前周cur->切换的周week
                        mTimetableView.onDateBuildListener()
                                .onUpdateDate(cur, week);
                        mTimetableView.changeWeekOnly(week);
                    }
                })
                .callback(new IWeekView.OnWeekLeftClickedListener() {
                    @Override
                    public void onWeekLeftClicked() {
                        onWeekLeftLayoutClicked();
                    }
                })
                .isShow(false)//设置隐藏，默认显示
                .showView();

        mTimetableView.curWeek(1)
                .curTerm("大三下学期")
                .callback(new ISchedule.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, List<Schedule> scheduleList) {
                        display(scheduleList);
                    }
                })
                .callback(new ISchedule.OnItemLongClickListener() {
                    @Override
                    public void onLongClick(View v, int day, int start) {
                        Toast.makeText(RealTable.this,
                                "长按:周" + day + ",第" + start + "节",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .callback(new ISchedule.OnWeekChangedListener() {
                    @Override
                    public void onWeekChanged(int curWeek) {
                        titleTextView.setText("第" + curWeek + "周");
                    }
                })
                .callback(new OnItemBuildAdapter() {
                    @Override
                    public void onItemUpdate(FrameLayout layout, TextView textView, TextView countTextView, Schedule schedule, GradientDrawable gd) {
                        super.onItemUpdate(layout, textView, countTextView, schedule, gd);
                    }
                })
                .showView();
    }

    //更新一下，防止因程序在后台时间过长（超过一天）而导致的日期或高亮不准确问题。
    @Override
    protected void onStart() {
        super.onStart();
        mTimetableView.onDateBuildListener()
                .onHighLight();
    }

    //周次选择布局的左侧被点击时回调对话框修改当前周次
    protected void onWeekLeftLayoutClicked() {
        final String items[] = new String[20];
        int itemCount = mWeekView.itemCount();
        for (int i = 0; i < itemCount; i++) {
            items[i] = "第" + (i + 1) + "周";
        }
        target = -1;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置当前周");
        builder.setSingleChoiceItems(items, mTimetableView.curWeek() - 1,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        target = i;
                    }
                });
        builder.setPositiveButton("设置为当前周", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (target != -1) {
                    mWeekView.curWeek(target + 1).updateView();
                    mTimetableView.changeWeekForce(target + 1);
                }
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }

    //显示内容
    protected void display(List<Schedule> beans) {
        String str = "";
        for (Schedule bean : beans) {
            str += bean.getName() + "," + bean.getWeekList().toString() + "," + bean.getStart() + "," + bean.getStep() + "\n";
        }
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_layout:
                //如果周次选择已经显示了，那么将它隐藏，更新课程、日期
                //否则，显示
                if (mWeekView.isShowing()) hideWeekView();
                else showWeekView();
                break;
        }
    }

    //隐藏周次选择，此时需要将课表的日期恢复到本周并将课表切换到当前周
    public void hideWeekView() {
        mWeekView.isShow(false);
        titleTextView.setTextColor(getResources().getColor(R.color.app_course_textcolor_blue));
        int cur = mTimetableView.curWeek();
        mTimetableView.onDateBuildListener()
                .onUpdateDate(cur, cur);
        mTimetableView.changeWeekOnly(cur);
    }

    public void showWeekView() {
        mWeekView.isShow(true);
        titleTextView.setTextColor(getResources().getColor(R.color.app_red));
    }


    //获取
    private JSONObject getJsonDataTakes() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", 0);
            jsonObject.put("sid", "201730682010");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void catchResponseTakes(final String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                classString += "[\"2017-2018学年秋\", \"\", \"\", \"";
                classString += jsonObject.getString("coursename");
                classString += "\", \"\", \"\", \"\", \"\", \"老hi\", \"\", \"\", \"";
                classString += jsonObject.getString("startdate");
                classString += "-";
                classString += jsonObject.getString("enddate");
                classString += "\", ";
                String[] temp = jsonObject.getString("timeslot").split("day");
                classString += change(temp[0]);
                classString += ", ";
                classString += temp[1].substring(0, 1);
                classString += ", 2, \"\", \"";
                classString += jsonObject.getString("classroom");
                classString += "\", \"\"],";
            }
            classString += "]";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //星期转换
    private int change(String string) {
        switch (string) {
            case "Mon":
                return 1;
            case "Tues":
                return 2;
            case "Wednes":
                return 3;
            case "Thurs":
                return 4;
            case "Fri":
                return 5;
            case "Satur":
                return 6;
            case "Sun":
                return 7;
        }
        return 1;
    }
}
