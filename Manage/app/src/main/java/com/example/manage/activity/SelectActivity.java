package com.example.manage.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.manage.R;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SelectActivity extends AppCompatActivity {

    private ImageButton bt_return;
    private ImageButton bt_start_sign_in_menu;
    private ImageButton bt_end_sign_in_menu;
    private Button bt_select_submit;
    private Spinner spinner_year;
    private Spinner spinner_month;
    private Spinner spinner_day;
    private TextView tv_sign_in;
    private TextView tv_un_sign_in;
    private TextView tv_sign_in_2;
    private TextView tv_un_sign_in_2;

    private Connection conn = null;
    private ResultSet rs=null;
    private PreparedStatement st = null;


//    //接受子线程的数据，并传输到sheetAdapter
//    private Handler mHandler = new Handler(Looper.myLooper()) {
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//            if (msg.what == 1) {
//                int count = (int) msg.obj;
//                String strCount = count+"";
//                tv_sign_in.setText(strCount+"人");
//            }
//
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_main);

        initView();

        myOnclickListener();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //连接数据库
                    Class.forName("com.mysql.jdbc.Driver");
                    String url="jdbc:mysql://rm-bp1v8goj767jxm00wdo.mysql.rds.aliyuncs.com/record?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
                    String username = "root";
                    String password = "asd123456ROOT";
                    conn = DriverManager.getConnection(url, username, password);
                    //表名
                    SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMdd");
                    String date = sdf.format(new Date());
                    String tableName = "record_"+date;

                    String sql = "SELECT COUNT(*) FROM "+tableName+" where startstatus NOT IN ('未打卡')";
                    st = conn.prepareStatement(sql);
                    rs = st.executeQuery();
                    while (rs.next()) {

                        int count = rs.getInt("COUNT(*)");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_sign_in.setText(count+"人");
                            }
                        });

                    }

                    sql = "SELECT COUNT(*) FROM "+tableName+" where startstatus IN ('未打卡')";
                    st = conn.prepareStatement(sql);
                    rs = st.executeQuery();
                    while (rs.next()) {

                        int count = rs.getInt("COUNT(*)");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_un_sign_in.setText(count+"人");
                            }
                        });

                    }
                    sql = "SELECT COUNT(*) FROM "+tableName+" where endstatus NOT IN ('未打卡')";
                    st = conn.prepareStatement(sql);
                    rs = st.executeQuery();
                    while (rs.next()) {

                        int count = rs.getInt("COUNT(*)");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_sign_in_2.setText(count+"人");
                            }
                        });

                    }
                    sql = "SELECT COUNT(*) FROM "+tableName+" where endstatus IN ('未打卡')";
                    st = conn.prepareStatement(sql);
                    rs = st.executeQuery();
                    while (rs.next()) {

                        int count = rs.getInt("COUNT(*)");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_un_sign_in_2.setText(count+"人");
                            }
                        });

                    }

                }catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void initView() {
        bt_return = findViewById(R.id.bt_return_select);
        bt_start_sign_in_menu = findViewById(R.id.bt_start_sign_in_menu);
        bt_end_sign_in_menu = findViewById(R.id.bt_end_sign_in_menu);
        bt_select_submit = findViewById(R.id.bt_select_submit);
        spinner_year = findViewById(R.id.spinner_year);
        spinner_month = findViewById(R.id.spinner_month);
        spinner_day = findViewById(R.id.spinner_day);
        tv_sign_in = findViewById(R.id.tv_sign_in);
        tv_un_sign_in = findViewById(R.id.tv_un_sign_in);
        tv_sign_in_2 = findViewById(R.id.tv_sign_in_2);
        tv_un_sign_in_2 = findViewById(R.id.tv_un_sign_in_2);

    }

    private void myOnclickListener() {
        //返回按钮的监听事件
        bt_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectActivity.this, MainActivity.class));
                Log.d("onClick: return","yes");
            }
        });

        bt_start_sign_in_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectActivity.this, StartSignInMenuActivity.class));
            }
        });

        bt_end_sign_in_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectActivity.this, EndSignInMenuActivity.class));
            }
        });

        //提交查询按钮，跳转到指定日期的考勤记录界面
        bt_select_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //获得被选中的选项的文本，Index, ID
                String value_year =  spinner_year.getSelectedItem().toString();
                String value_month =  spinner_month.getSelectedItem().toString();
                String value_day =  spinner_day.getSelectedItem().toString();


                //通过bundle传送给查询往日考勤记录的activity
                Intent intentSimple = new Intent();
                intentSimple.setClass(SelectActivity.this, SignInRecordActivity.class);

                Bundle bundle=new Bundle();
                bundle.putString("value_year",value_year);
                bundle.putString("value_month",value_month);
                bundle.putString("value_day",value_day);
                intentSimple.putExtras(bundle);
                startActivity(intentSimple);
            }
        });
    }

}
