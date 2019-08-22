package com.example.education;

import android.content.Intent;
import android.os.Bundle;

import com.example.education.objects.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private ImageView touxiang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "开发中……", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        //侧边栏头部
//        View drawview = navigationView.inflateHeaderView(R.layout.nav_header_main);
//        touxiang = (ImageView) findViewById(R.id.slide_head_touxiang);
//        touxiang.setOnClickListener(this);
        //User user = User.getInstance();
    }

    //返回键缩回侧边栏
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //右上角三点
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //右上角选项选择
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //侧边栏选项选择
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_kebiao) {
            //跳转到课表页面
            Intent intent = new Intent(this, RealTable.class);
            startActivity(intent);
        } else if (id == R.id.nav_geren) {
            if (User.getUserType() == 3) {
                //跳转到登录页面
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            } else {
                //跳转到个人信息
                Intent intent = new Intent(this, GeRenActivity.class);
                startActivity(intent);
            }
        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(this, SectionChooseActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_fabu) {
            Intent intent = new Intent(this, CourseChooseActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_chengji) {
            if (User.getUserType() == 1) {
                Intent intent = new Intent(this, Jiaowuyuan_chengji.class);
                startActivity(intent);
            } else if (User.getUserType() == 0) {
                Intent intent = new Intent(this, Xuesheng_chengji.class);
                startActivity(intent);
            }

        } else if (id == R.id.nav_pingjia) {
            if (User.getUserType() == 1) {
                Intent intent = new Intent(this, Jiaoshi_pingjia.class);
                startActivity(intent);
            } else if (User.getUserType() == 0) {
                Intent intent = new Intent(this, xuesheng_pingjia.class);
                startActivity(intent);
            }
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
