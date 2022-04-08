package com.example.manage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.manage.R;
import com.example.manage.pojo.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NewPasswordActivity extends AppCompatActivity {


    private ImageButton bt_return_new_password;
    private Button bt_new_password_submit;

    private EditText et_old_pwd;
    private EditText et_new_pwd1;
    private EditText et_new_pwd2;

    private Connection conn = null;
    private ResultSet rs=null;
    private PreparedStatement st = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_new_password);

        initView();
        MyOnClickListener clickListener = new MyOnClickListener();
        bt_return_new_password.setOnClickListener(clickListener);
        bt_new_password_submit.setOnClickListener(clickListener);

        et_old_pwd.setOnFocusChangeListener(onFocusAutoClearHintListener);
        et_new_pwd1.setOnFocusChangeListener(onFocusAutoClearHintListener);
        et_new_pwd2.setOnFocusChangeListener(onFocusAutoClearHintListener);
    }

    public void initView() {

        bt_return_new_password = findViewById(R.id.bt_return_new_password);
        bt_new_password_submit = findViewById(R.id.bt_new_password_submit);
        et_old_pwd = findViewById(R.id.et_old_pwd);
        et_new_pwd1 = findViewById(R.id.et_new_pwd1);
        et_new_pwd2 = findViewById(R.id.et_new_pwd2);
    }

    public class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String oldPwd = et_old_pwd.getText().toString().trim();
            String newPwd1 = et_new_pwd1.getText().toString().trim();
            String newPwd2 = et_new_pwd2.getText().toString().trim();

            switch (view.getId()) {
                case R.id.bt_return_new_password:
                    Intent intent = new Intent(NewPasswordActivity.this, MainActivity.class);
                    intent.putExtra("id",1);
                    startActivity(intent);
                    break;
                case R.id.bt_new_password_submit:

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Class.forName("com.mysql.jdbc.Driver");
                                String url="jdbc:mysql://rm-bp1v8goj767jxm00wdo.mysql.rds.aliyuncs.com/myuser?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
                                String username = "root";
                                String password = "asd123456ROOT";
                                conn = DriverManager.getConnection(url, username, password);
                                User user=(User)getApplication();
                                String phone = user.getPhone();
                                String oldPassword = user.getPassword();

                                if (oldPwd.equals(oldPassword)){
                                    if (newPwd1.equals(newPwd2)) {
                                        String sql = "update people\n" +
                                                "set password = ?\n" +
                                                "where phone = ? and password = ?";
                                        st = conn.prepareStatement(sql);
                                        st.setString(1,newPwd1);
                                        st.setString(2,phone);
                                        st.setString(3,oldPassword);
                                        int i = st.executeUpdate();
                                        if (i > 0) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Log.d("isequal","yes");
                                                    Toast.makeText(NewPasswordActivity.this, "修改密码成功！", Toast.LENGTH_LONG).show();
                                                    startActivity(new Intent(NewPasswordActivity.this,LoginActivity.class));

                                                }
                                            });
                                        } else {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(NewPasswordActivity.this, "修改密码失败！", Toast.LENGTH_LONG).show();

                                                }
                                            });
                                        }
                                        //Toast.makeText(NewPasswordActivity.this,"修改密码成功",Toast.LENGTH_LONG).show();
                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(NewPasswordActivity.this,"两次输入密码不同",Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }

                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(NewPasswordActivity.this,"旧密码错误！",Toast.LENGTH_LONG).show();
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
