package com.example.manage.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.manage.R;
import com.example.manage.pojo.User;
import com.example.manage.utils.Utils;
import com.mob.MobSDK;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences sp;
    private EditText et_code;
    private Button bt_login;
    private Button bt_register;
    private Button bt_code;
    private EditText et_phone;

    private Connection conn = null;
    private PreparedStatement st = null;
    private ResultSet rs = null;

    private EventHandler eh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //获取首选项
        sp=getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        initView();
        String myPhone = sp.getString("phone","");
        et_phone.setText(myPhone);
        //点击输入框时提示文本消失
        et_phone.setOnFocusChangeListener(onFocusAutoClearHintListener);
        et_code.setOnFocusChangeListener(onFocusAutoClearHintListener);

        MobSDK.submitPolicyGrantResult(true, null);

        eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (event == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "语音验证发送", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        Log.i("test", "test");
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                    Throwable throwable = (Throwable) data;
                    throwable.printStackTrace();
                    Log.i("1234", throwable.toString());
                    try {
                        JSONObject obj = new JSONObject(throwable.getMessage());
                        final String des = obj.optString("detail");
                        if (!TextUtils.isEmpty(des)) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, des, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };


    }

    private void initView() {
        //找到控件
        et_code = findViewById(R.id.et_code);
        et_phone = findViewById(R.id.et_phone);
        bt_login = findViewById(R.id.bt_login);
        bt_register = findViewById(R.id.bt_register);
        bt_code = findViewById(R.id.bt_code);
        //设置监听
        MyOnClickListener l = new MyOnClickListener();
        bt_login.setOnClickListener(l);
        bt_register.setOnClickListener(l);
        bt_code.setOnClickListener(l);

    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String phone = et_phone.getText().toString().trim();
            String code = et_code.getText().toString().trim();

            switch (v.getId()) {
                //注册按钮
                case R.id.bt_register:
                    //注册操作，跳转到注册界面
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                    break;
                //登录按钮
                case R.id.bt_login:
                    //登录操作
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                //1.加载驱动
                                Class.forName("com.mysql.jdbc.Driver"); //固定写法，加载驱动

                                //2.用户信息和url
                                String url = "jdbc:mysql://rm-bp1v8goj767jxm00wdo.mysql.rds.aliyuncs.com/myuser?useUnicode=true&characterEncoding=utf8&useSSL=false";
                                String username = "root";
                                String password = "asd123456ROOT";
                                //3.连接成功，数据库对象
                                conn = DriverManager.getConnection(url, username, password);

                                String sql = "SELECT * FROM people where phone=?";

                                //跟statement的区别
                                st = conn.prepareStatement(sql); //预编译SQL，先写sql，然后不执行

                                //手动给参数赋值
                                st.setString(1, phone);
                                rs = st.executeQuery();
                                if (rs.next()) {
                                    String part = rs.getString("part");
                                    String shuxing = rs.getString("shuxing");
                                    String sex = rs.getString("sex");
                                    String pos = rs.getString("position");
                                    String name = rs.getString("name");
                                    String pwd = rs.getString("password");
                                    String phone = rs.getString("phone");

                                    //记住手机号
                                    SharedPreferences.Editor editor=sp.edit();
                                    editor.putString("phone",phone);
                                    editor.commit();

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
                                        }
                                    });
                                    if (!code.isEmpty()) {
                                        //提交验证码
                                        SMSSDK.submitVerificationCode("86", phone, code);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "请输入验证码", Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(LoginActivity.this, "手机号不存在或未注册", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                                //在Ui线程中运行Toast，否则会报错
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_LONG).show();
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
                    break;
                case R.id.bt_code:
                    //注册一个事件回调监听，用于处理SMSSDK接口请求的结果
                    SMSSDK.registerEventHandler(eh);
                        if (!phone.isEmpty()) {
                            if (Utils.checkTel(phone)) { //利用正则表达式获取检验手机号
                                // 获取验证码
                                SMSSDK.getVerificationCode("86", phone);
                            } else {
                                Toast.makeText(getApplicationContext(), "请输入有效的手机号", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "请输入手机号", Toast.LENGTH_LONG).show();
                            return;
                        }
                    break;
            }
//            // 使用完EventHandler需注销，否则可能出现内存泄漏
//            @Override
//            protected void onDestroy() {
//                super.onDestroy();
//                SMSSDK.unregisterEventHandler(eh);
//            }
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