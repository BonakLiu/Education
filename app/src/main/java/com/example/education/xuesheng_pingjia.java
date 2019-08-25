package com.example.education;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.education.objects.Course;
import com.example.education.objects.Takes;
import com.example.education.objects.User;
import com.example.education.tools.HttpsConnect;
import com.example.education.tools.HttpsListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Xuesheng_pingjia extends AppCompatActivity {
    private Spinner pingfen;
    private EditText pingjia;
    private Button tijiao;
    //接收
    private List<Takes> takesData = new ArrayList<>();
    //发送
    private String sectionid;
    private String tid;
    private String fen;
    private final String getTakesListUrl = "http://129.211.12.161:8080/Login/sidServlet";
    private final String commitpingjiaUrl = "http://129.211.12.161:8080/Login/Commend";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuesheng_pingjia);
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
        pingfen = (Spinner) findViewById(R.id.xuesheng_pingjia_pingfen);
        pingjia = (EditText) findViewById(R.id.xuesheng_pingjia_pingjia);
        tijiao = (Button) findViewById(R.id.xuesheng_pingjia_tijiao);
        pingfen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] temp = getResources().getStringArray(R.array.pingfen);
                fen = temp[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpsConnect.sendRequest(commitpingjiaUrl, "POST", getJsonDataPing(), new HttpsListener() {
                    @Override
                    public void success(String response) {
                        catchResponsePing(response);
                    }

                    @Override
                    public void failed(Exception exception) {
                        exception.printStackTrace();
                    }
                });
            }
        });
        final TakesAdapter adapter = new TakesAdapter(Xuesheng_pingjia.this, R.layout.takes_item, takesData);
        final ListView courseList = (ListView) findViewById(R.id.xuesheng_pingjia_view);
        courseList.setAdapter(adapter);
        courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Takes takes = takesData.get(i);
                tid = takes.getTid();
                sectionid = takes.getSectionid();
                adapter.setSelectedPosition(i);
                adapter.notifyDataSetInvalidated();
                Toast.makeText(Xuesheng_pingjia.this, "已选择" + takes.getSname(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private JSONObject getJsonDataTakes() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", User.getUserType());
            jsonObject.put("sid", User.getId());
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
                String coursename = jsonObject.getString("coursename");
                String name = jsonObject.getString("name");
                String sectionid = jsonObject.getString("sectionid");
                String tid = jsonObject.getString("tid");
                Takes takes = new Takes(tid, sectionid, name,coursename);
                takesData.add(takes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JSONObject getJsonDataPing() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", User.getUserType());
            jsonObject.put("sid", User.getId());
            jsonObject.put("tid", tid);
            jsonObject.put("sectionid", sectionid);
            jsonObject.put("mark", fen);
            jsonObject.put("commend", pingjia.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void catchResponsePing(final String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            boolean result = jsonObject.getBoolean("result");
            String reason = jsonObject.getString("reason");
            if (result) {
                Toast.makeText(this, "成功", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, reason, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
