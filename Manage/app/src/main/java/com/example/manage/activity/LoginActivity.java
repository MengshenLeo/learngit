package com.example.manage.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.manage.R;
import com.example.manage.pojo.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {


    SharedPreferences sp;
    private EditText et_name;
    private EditText et_pwd;
    private CheckBox cb_remeberpwd;
    private CheckBox cb_autologin;
    private Button bt_login;
    private Button bt_register;
    private  EditText et_phone;

    private Connection conn = null;
    private PreparedStatement st = null;
    private ResultSet rs = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //获取首选项
        sp=getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
//        sp2=getSharedPreferences("AutoLogin", Context.MODE_PRIVATE);
        initView();
        //第二次打开，从SP获取数据进行这个画面的同步,回显数据
        boolean  RememberPwd=sp.getBoolean("RememberPwd",false);
        boolean  AutoLogin=sp.getBoolean("AutoLogin",false);
        if(RememberPwd){
            //获取sp里面的name和pwd,并保存到id.text;
            String phone=sp.getString("phone","");
            String pwd=sp.getString("password","");
            et_name.setText(phone);
            et_pwd.setText(pwd);
            cb_remeberpwd.setChecked(true);
            if(AutoLogin){
                cb_autologin.setChecked(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            //1.加载驱动
                            Class.forName("com.mysql.jdbc.Driver"); //固定写法，加载驱动

                            //2.用户信息和url
                            String url="jdbc:mysql://rm-bp1v8goj767jxm00wdo.mysql.rds.aliyuncs.com/myuser?useUnicode=true&characterEncoding=utf8&useSSL=false";
                            String username = "root";
                            String password = "asd123456ROOT";
                            //3.连接成功，数据库对象
                            conn = DriverManager.getConnection(url, username, password);

                            String sql = "SELECT * FROM people where phone=? and password = ?";

                            //跟statement的区别
                            st = conn.prepareStatement(sql); //预编译SQL，先写sql，然后不执行

                            //手动给参数赋值
                            st.setString(1, phone);
                            st.setString(2, pwd);

                            rs = st.executeQuery();

                            if (rs.next()) {
                                String part=rs.getString("part");
                                String shuxing=rs.getString("shuxing");
                                String sex=rs.getString("sex");
                                String pos=rs.getString("position");
                                String name=rs.getString("name");
                                String pwd=rs.getString("password");
                                String phone=rs.getString("phone");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        User user=(User)getApplication();
                                        user.setPart(part);
                                        user.setShuxing(shuxing);
                                        user.setSex(sex);
                                        user.setPosition(pos);
                                        user.setPhone(phone);
                                        user.setUsername(name);
                                        user.setPassword(pwd);
                                        Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                                    }
                                });

                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }finally {
                            try {
                                conn.close();
                                st.close();
                                rs.close();
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        }

        //点击输入框时提示文本消失
        et_name.setOnFocusChangeListener(onFocusAutoClearHintListener);
        et_pwd.setOnFocusChangeListener(onFocusAutoClearHintListener);


    }

    private void initView() {
        //找到控件
        et_name=findViewById(R.id.et_name);
        et_pwd=findViewById(R.id.et_pwd);
        et_phone=findViewById(R.id.et_phone);
        cb_remeberpwd=findViewById(R.id.cb_remeberpwd);
        cb_autologin=findViewById(R.id.cb_autologin);
        bt_login=findViewById(R.id.bt_login);
        bt_register=findViewById(R.id.bt_register);
        //设置监听
        MyOnClickListener l=new MyOnClickListener();
        bt_login.setOnClickListener(l);
        bt_register.setOnClickListener(l);

    }
    private  class MyOnClickListener implements View.OnClickListener{
        @Override
        public  void onClick(View v){
            String phone=et_name.getText().toString().trim();
            String pwd=et_pwd.getText().toString().trim();

            switch (v.getId()){
                //注册按钮
                case R.id.bt_register:
                    //注册操作，跳转到注册界面
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                    break;
                //登录按钮
                case R.id.bt_login:
                    //登录操作

                    if(TextUtils.isEmpty(phone)||TextUtils.isEmpty(pwd)){
                        Toast.makeText(LoginActivity.this,"用户名或者密码为空",Toast.LENGTH_SHORT).show();
                    } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                //1.加载驱动
                                Class.forName("com.mysql.jdbc.Driver"); //固定写法，加载驱动

                                //2.用户信息和url
                                String url="jdbc:mysql://rm-bp1v8goj767jxm00wdo.mysql.rds.aliyuncs.com/myuser?useUnicode=true&characterEncoding=utf8&useSSL=false";
                                String username = "root";
                                String password = "asd123456ROOT";
                                //3.连接成功，数据库对象
                                conn = DriverManager.getConnection(url, username, password);

                                String sql = "SELECT * FROM people where phone=? and password = ?";

                                //跟statement的区别
                                st = conn.prepareStatement(sql); //预编译SQL，先写sql，然后不执行

                                //手动给参数赋值
                                st.setString(1, phone);
                                st.setString(2, pwd);

                                rs = st.executeQuery();

                                if (rs.next()) {
                                    String part=rs.getString("part");
                                    String shuxing=rs.getString("shuxing");
                                    String sex=rs.getString("sex");
                                    String pos=rs.getString("position");
                                    String name=rs.getString("name");
                                    String pwd=rs.getString("password");
                                    String phone=rs.getString("phone");
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            SharedPreferences.Editor editor=sp.edit();
                                            //判断记住密码和自动登录有没有打勾
                                            if(cb_remeberpwd.isChecked()){
                                                //用户名和密保存码都需要保存到sp，记住密码的状态也要
                                                editor.putString("phone",phone);
                                                editor.putString("password",pwd);
                                                editor.putBoolean("RememberPwd",true);
                                                editor.commit();
                                            }
                                            if (cb_autologin.isChecked()){
                                                editor.putBoolean("AutoLogin",true);
                                                editor.commit();
                                            }
                                            User user=(User)getApplication();
                                            user.setPart(part);
                                            user.setShuxing(shuxing);
                                            user.setSex(sex);
                                            user.setPosition(pos);
                                            user.setPhone(phone);
                                            user.setUsername(name);
                                            user.setPassword(pwd);
                                            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                                        }
                                    });

                                } else{
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }


                            }catch (Exception e){
                                e.printStackTrace();
                                //在Ui线程中运行Toast，否则会报错
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_LONG).show();
                                    }
                                });

                            }finally {
                                try {
                                    conn.close();
                                    st.close();
                                    rs.close();
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }
                            }
                        }
                    }).start();

            }
                    break;

            }
        }


    }

    public static View.OnFocusChangeListener onFocusAutoClearHintListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            EditText textView = (EditText) v;
            String hint;
            if (hasFocus) {
                hint = textView.getHint().toString();
                textView.setTag(hint);
                textView.setHint("");
            } else {
                hint = textView.getTag().toString();
                textView.setHint(hint);
            }
        }
    };





}