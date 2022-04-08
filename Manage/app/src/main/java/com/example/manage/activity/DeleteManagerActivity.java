package com.example.manage.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manage.Adapter.DeleteAdapter;
import com.example.manage.R;
import com.example.manage.pojo.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeleteManagerActivity extends AppCompatActivity {
    private Spinner spinner;
    private Connection conn = null;
    private ResultSet rs=null;
    private PreparedStatement st = null;
    private DeleteAdapter addAdapter;
    ArrayAdapter<CharSequence> arrayAdapter=null;
    private ListView listView=null;
    private TextView textView=null;
    List<User> users;
    List<User>selected;
    String Position;
    private RecyclerView rv_deletemangage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.delete_manager);
        init();
        InitSpinner();
        InitListView();

    }

    public void init()
    {
        spinner=findViewById(R.id.spinner_part);
        rv_deletemangage=findViewById(R.id.rv_deletemangage);
        //设置线性布局 Creates a vertical LinearLayoutManager
        rv_deletemangage.setLayoutManager(new LinearLayoutManager(this));
        //设置recyclerView每个item间的分割线
        rv_deletemangage.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        users=new ArrayList<>();
        selected=new ArrayList<>();
    }
    public void InitSpinner(){
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

                    String sql = "select DISTINCT part FROM people";

                    //跟statement的区别
                    st = conn.prepareStatement(sql); //预编译SQL，先写sql，然后不执行

                    //手动给参数赋值

                    st= conn.prepareStatement(sql); //预编译SQL，先写sql，然后不执行
                    rs = st.executeQuery();
                    List<String> parts=new ArrayList<>();
                    while(rs.next())//保存所有的部门名给spinner
                    {
                        parts.add(rs.getString("part"));
                    }
                    String []str=new String[parts.size()];
                    for(int i=0;i<parts.size();i++){
                        str[i]=parts.get(i);
                        //System.out.println(parts.get(i));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            arrayAdapter=new ArrayAdapter<CharSequence>(DeleteManagerActivity.this, R.layout.support_simple_spinner_dropdown_item,str);
                            spinner.setAdapter(arrayAdapter);
                            spinner.setOnItemSelectedListener(new OnItemSelectedListenerImpl());
                        }
                    });

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



    private  class OnItemSelectedListenerImpl implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Position=parent.getItemAtPosition(position).toString();


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

    }
    public void InitListView(){
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

                    String sql = "select name,phone FROM manager";

                    //跟statement的区别
                    st = conn.prepareStatement(sql); //预编译SQL，先写sql，然后不执行

                    //手动给参数赋值

                    st= conn.prepareStatement(sql); //预编译SQL，先写sql，然后不执行
                    rs = st.executeQuery();
                    users.clear();
                    while(rs.next())//保存所有的部门名给spinner
                    {
                        User u=new User();
                        u.setUsername(rs.getString("name"));
                        u.setPhone(rs.getString("phone"));
                        users.add(u);

//                        System.out.println(rs.getString("name")+" "+rs.getString("phone"));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addAdapter = new DeleteAdapter(DeleteManagerActivity.this,users);
                            rv_deletemangage.setLayoutManager(new LinearLayoutManager(DeleteManagerActivity.this, LinearLayoutManager.VERTICAL, false));

                            rv_deletemangage.setAdapter(addAdapter);
                            addAdapter.setOnItemClickListener(new DeleteAdapter.onItemClickListener() {
                                @Override
                                public void onClick(View view, int position) {

                                    if(selected.contains(users.get(position))) {
                                        selected.remove(users.get(position));

                                        System.out.println("delete"+users.get(position).getUsername());

                                    } else{
                                        selected.add(users.get(position));
                                        selected.add(users.get(position));
                                        System.out.println("add"+users.get(position).getUsername());
                                    }
                                }

                            });
                        }
                    });

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void setManage(View view) {
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
                    for(User u:selected){
                        String sql = "DELETE FROM manager where phone='"+u.getPhone()+"'";
                        //跟statement的区别
                        st = conn.prepareStatement(sql); //预编译SQL，先写sql，然后不执行

                        //手动给参数赋值

                        st= conn.prepareStatement(sql); //预编译SQL，先写sql，然后不执行
                        int result= st.executeUpdate();

                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(DeleteManagerActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
