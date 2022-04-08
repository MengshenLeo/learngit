package com.example.manage.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.manage.R;

import com.example.manage.pojo.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class RegisterActivity extends AppCompatActivity {


    private EditText et_name;
    private EditText et_pwd;
    private EditText et_pwd2;
    private EditText et_sex;
    private EditText et_part;
    private EditText et_phone;
    private EditText et_pos;
    private EditText et_shuxing;
    private Button bt_submit;
    private ImageButton bt_return_register;

    private Connection conn = null;
    private PreparedStatement st = null;
    private ResultSet rs = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();

        bt_return_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
    }

    private void initView() {
        //找到控件
        et_name=findViewById(R.id.et_name);
        et_pwd=findViewById(R.id.et_pwd);
        et_pwd2=findViewById(R.id.et_pwd2);
        bt_submit=findViewById(R.id.bt_submit);
        et_part=findViewById(R.id.et_part);
        et_sex=findViewById(R.id.et_sex);
        et_phone=findViewById(R.id.et_phone);
        et_pos=findViewById(R.id.et_pos);
        et_shuxing=findViewById(R.id.et_shuxing);
        bt_return_register=findViewById(R.id.bt_return_register);
        //设置监听
        MyMyOnClickListener l = new MyMyOnClickListener();
        bt_submit.setOnClickListener(l);
    }

    public class MyMyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String part=et_part.getText().toString().trim();
            String name=et_name.getText().toString().trim();
            String sex=et_sex.getText().toString().trim();
            String shuxing=et_shuxing.getText().toString().trim();
            String pos=et_pos.getText().toString().trim();
            String phone=et_phone.getText().toString().trim();
            String pwd=et_pwd.getText().toString().trim();
            String pwd2=et_pwd2.getText().toString().trim();
            if(TextUtils.isEmpty(phone)||TextUtils.isEmpty(pwd)){
                Toast.makeText(RegisterActivity.this,"用户名或者密码为空",Toast.LENGTH_SHORT).show();
            } else {
                //判断用户名是否存在
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //1.加载驱动
                                Class.forName("com.mysql.jdbc.Driver"); //固定写法，加载驱动

                                //2.用户信息和url
                                String url="jdbc:mysql://rm-bp1v8goj767jxm00wdo.mysql.rds.aliyuncs.com/myuser?useUnicode=true&characterEncoding=utf-8&useSSL=false";
                                String username = "root";
                                String password = "asd123456ROOT";
                                //3.连接成功，数据库对象
                                conn = DriverManager.getConnection(url, username, password);

                                String sql = "SELECT name FROM people where phone=?";

                                //跟statement的区别
                                st = conn.prepareStatement(sql); //预编译SQL，先写sql，然后不执行

                                //手动给参数赋值
                                st.setString(1, phone);

                                rs = st.executeQuery();
                                if (rs.next()) {
                                    System.out.println("用户名已存在");
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.d("isequal","yes");
                                            Toast.makeText(RegisterActivity.this, "用户名已存在", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                } else {
                                    sql = "INSERT INTO people (part,name,sex,shuxing,position,phone,password) " +
                                            "VALUES (?,?,?,?,?,?,?)";
                                    st = conn.prepareStatement(sql);
                                    st.setString(1,part);
                                    st.setString(2,name);
                                    st.setString(3,sex);
                                    st.setString(4,shuxing);
                                    st.setString(5,pos);
                                    st.setString(6,phone);
                                    st.setString(7,pwd);

                                    int i = st.executeUpdate();
                                    if (i > 0) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.d("register","success");
                                                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                                Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                }

                            } catch (SQLException | ClassNotFoundException throwables) {
                                throwables.printStackTrace();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(RegisterActivity.this,"注册失败", Toast.LENGTH_LONG).show();
                                    }
                                });
                            } finally {

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

    }
}
