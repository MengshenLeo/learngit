package com.example.manage.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.manage.Adapter.Deletepeople;
import com.example.manage.R;
import com.example.manage.activity.AddManagerActivity;
import com.example.manage.activity.DeleteManagerActivity;
import com.example.manage.activity.DeletePeopleActivity;
import com.example.manage.activity.LoginActivity;
import com.example.manage.activity.RegisterActivity;
import com.example.manage.activity.SelectActivity;
import com.example.manage.pojo.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HomeFragment extends Fragment {

    private ImageButton bt_3_1;
    private TextView tv_user_name;
    private TextView tv_user_phone;
    private ImageButton add_manager;
    private ImageButton delete_manager;
    private ImageButton manage_people;
    private Connection conn = null;
    private PreparedStatement st = null;
    private ResultSet rs = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bt_3_1 = getActivity().findViewById(R.id.bt_3_1);
        tv_user_name = getActivity().findViewById(R.id.tv_user_name);
        tv_user_phone = getActivity().findViewById(R.id.tv_user_phone);
        manage_people=getActivity().findViewById(R.id.manage_people);
        bt_3_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),SelectActivity.class));
            }
        });
        User user=(User)getActivity().getApplication();
        String name = user.getUsername();
        String phone = user.getPhone();
        tv_user_name.setText(name);
        tv_user_phone.setText(phone);
        add_manager=getActivity().findViewById(R.id.add_manage);
        delete_manager=getActivity().findViewById(R.id.deletemanager);
        delete_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //1.????????????
                            Class.forName("com.mysql.jdbc.Driver"); //???????????????????????????

                            //2.???????????????url
                            String url="jdbc:mysql://rm-bp1v8goj767jxm00wdo.mysql.rds.aliyuncs.com/myuser?useUnicode=true&characterEncoding=utf-8&useSSL=false";
                            String username = "root";
                            String password = "asd123456ROOT";
                            //3.??????????????????????????????
                            conn = DriverManager.getConnection(url, username, password);

                            String sql = "SELECT * FROM admin where phone=?";

                            //???statement?????????
                            st = conn.prepareStatement(sql); //?????????SQL?????????sql??????????????????

                            //?????????????????????
                            st.setString(1, user.getPhone());

                            rs = st.executeQuery();
                            if (rs.next()) {
                                startActivity(new Intent(getActivity(), DeleteManagerActivity.class));
                            } else {

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.d("isequal","yes");
                                        Toast.makeText(getActivity(), "????????????ROOT?????????????????????????????????", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }

                        } catch (SQLException | ClassNotFoundException throwables) {
                            throwables.printStackTrace();

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
        });
        add_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //1.????????????
                            Class.forName("com.mysql.jdbc.Driver"); //???????????????????????????

                            //2.???????????????url
                            String url="jdbc:mysql://rm-bp1v8goj767jxm00wdo.mysql.rds.aliyuncs.com/myuser?useUnicode=true&characterEncoding=utf-8&useSSL=false";
                            String username = "root";
                            String password = "asd123456ROOT";
                            //3.??????????????????????????????
                            conn = DriverManager.getConnection(url, username, password);

                            String sql = "SELECT * FROM admin where phone=?";

                            //???statement?????????
                            st = conn.prepareStatement(sql); //?????????SQL?????????sql??????????????????

                            //?????????????????????
                            st.setString(1, user.getPhone());

                            rs = st.executeQuery();
                            if (rs.next()) {
                                startActivity(new Intent(getActivity(), AddManagerActivity.class));
                            } else {

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.d("isequal","yes");
                                        Toast.makeText(getActivity(), "????????????ROOT?????????????????????????????????", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }

                        } catch (SQLException | ClassNotFoundException throwables) {
                            throwables.printStackTrace();

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
        });
        manage_people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //1.????????????
                            Class.forName("com.mysql.jdbc.Driver"); //???????????????????????????

                            //2.???????????????url
                            String url="jdbc:mysql://rm-bp1v8goj767jxm00wdo.mysql.rds.aliyuncs.com/myuser?useUnicode=true&characterEncoding=utf-8&useSSL=false";
                            String username = "root";
                            String password = "asd123456ROOT";
                            //3.??????????????????????????????
                            conn = DriverManager.getConnection(url, username, password);

                            String sql = "SELECT * FROM manager where phone=?";

                            //???statement?????????
                            st = conn.prepareStatement(sql); //?????????SQL?????????sql??????????????????

                            //?????????????????????
                            st.setString(1, user.getPhone());

                            rs = st.executeQuery();
                            if (rs.next()) {
                                startActivity(new Intent(getActivity(), DeletePeopleActivity.class));
                            } else {

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.d("isequal","yes");
                                        Toast.makeText(getActivity(), "??????????????????????????????????????????", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }

                        } catch (SQLException | ClassNotFoundException throwables) {
                            throwables.printStackTrace();

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
        });
    }
}
