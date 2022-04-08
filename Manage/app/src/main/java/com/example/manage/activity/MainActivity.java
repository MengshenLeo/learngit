package com.example.manage.activity;


import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.manage.Adapter.LinearAdapter;
import com.example.manage.R;
import com.example.manage.fragment.HomeFragment;
import com.example.manage.fragment.MapFragment;
import com.example.manage.fragment.UserFragment;
import com.example.manage.pojo.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tabMap;
    private TextView tabHome;
    private TextView tabUser;
    private AMapLocationClient ServiceSettings;
    private FrameLayout ly_content;
    private String location;
    private MapFragment f1;
    private HomeFragment f2;
    private UserFragment f3;
    private Connection conn = null;
    private ResultSet rs=null;
    private PreparedStatement st = null;

    private RecyclerView rv_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        bindView();



    }
    //UI组件初始化与事件绑定
    private void bindView() {
        tabMap = (TextView)this.findViewById(R.id.txt_map);
        tabHome = (TextView)this.findViewById(R.id.txt_home);
        tabUser = (TextView)this.findViewById(R.id.txt_user);
        ly_content = (FrameLayout) findViewById(R.id.fragment_container);

        tabMap.setOnClickListener(this);
        tabHome.setOnClickListener(this);
        tabUser.setOnClickListener(this);

        tabHome.setSelected(true);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        f2 = new HomeFragment();
        transaction.add(R.id.fragment_container,f2);
        transaction.commit();
//        tabHome.setSelected(true);

//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.fragment_container,new Fragment(R.layout.home_fragment))
//                .addToBackStack(null)
//                .commit();

    }

    //重置所有文本的选中状态
    public void selected(){
        tabMap.setSelected(false);
        tabHome.setSelected(false);
        tabUser.setSelected(false);
    }

    //隐藏所有Fragment
    public void hideAllFragment(FragmentTransaction transaction){
        if(f1!=null){
            transaction.hide((f1));
        }
        if(f2!=null){
            transaction.hide(f2);
        }
        if(f3!=null){
            transaction.hide(f3);
        }
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        switch(v.getId()){
            case R.id.txt_map:
                selected();
                tabMap.setSelected(true);
                if(f1==null){
                    f1 = new MapFragment();
                    transaction.add(R.id.fragment_container,f1);
                }else{
                    transaction.show(f1);
                }
                break;

            case R.id.txt_home:
                selected();
                tabHome.setSelected(true);
                if(f2==null){
                    f2 = new HomeFragment();
                    transaction.add(R.id.fragment_container,f2);
                }else{
                    transaction.show(f2);
                }
                break;

            case R.id.txt_user:
                selected();
                tabUser.setSelected(true);
                if(f3==null){
                    f3 = new UserFragment();
                    transaction.add(R.id.fragment_container,f3);
                }else{
                    transaction.show(f3);
                }
                break;

        }
        transaction.commit();
    }

    public void getstartlocation(View view) {
        requestPermissions(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        },1);
        if(isLocationProviderEnabled()==false) {
            openLocationServer();
        }
        StartLocation();
        TextView start_time=findViewById(R.id.start_time);
        TextView start_location=findViewById(R.id.start_location);
        start_location.setText(location);
        String format=  "yyyy-MM-dd HH:mm:ss";
        Date  date=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        start_time.setText(formatter.format(date));
    }
    public boolean isLocationProviderEnabled() {
        boolean result = false;
        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        if (locationManager == null) {
            return false;
        }
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            result = true;
        }
        return result;
    }
    public void getendlocation(View view) {
        requestPermissions(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        },1);
        if(isLocationProviderEnabled()==false) {
            openLocationServer();
        }
        StartLocation();
        TextView end_time=findViewById(R.id.end_time);
        TextView end_location=findViewById(R.id.end_location);
        end_location.setText(location);
        String format=  "yyyy-MM-dd HH:mm:ss";
        Date  date=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        end_time.setText(formatter.format(date));

    }
    private void openLocationServer() {
        Intent i = new Intent();
        i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(i, 1);
    }
    //这里完成定位相关的程序
    //高德定位客户端对象
    private AMapLocationClient mAMapClient;
    //高德定位监听器
    private AMapLocationListener mAMapListener=new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            //aMapLocation参数包含最新的位置信息：经纬度、街道、区、城市等
            location = aMapLocation.getCity() + aMapLocation.getDistrict() + aMapLocation.getStreet();
            //TODO:将定位存储到数据库
            StopLocation();
        }
    };
    public void StartLocation() {
//创建客户端实例
        try {
            ServiceSettings.updatePrivacyShow(this, true, true);
            ServiceSettings.updatePrivacyAgree(this,true);
            mAMapClient =new AMapLocationClient(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //绑定监听器
        mAMapClient.setLocationListener(mAMapListener);
        //设定高德定位客户端的选项
        AMapLocationClientOption option=new AMapLocationClientOption();
        //设定定位精度（高精度米级,卫星定位）
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设定为需要获取地址信息
        option.setNeedAddress(true);
        //设定刷新周期,毫秒为单位
        option.setInterval(10*1000);
        //设定允许使用模拟定位
        option.setMockEnable(true);
        //把option对象给客户端
        mAMapClient.setLocationOption(option);
        //启动定位,防止已经在定位，先停止一下
        mAMapClient.stopLocation();
        mAMapClient.startLocation();
        Log.d("dingwei","AMap location start");

    }
    public void StopLocation() {
        mAMapClient.stopLocation();
        mAMapClient.onDestroy();
        Log.d("dingwei","AMap location stop");
    }


    public void submitstart(View view) {
        TextView start_location=findViewById(R.id.start_location);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //1.加载驱动
                    Class.forName("com.mysql.jdbc.Driver"); //固定写法，加载驱动

                    //2.用户信息和url
                    String url="jdbc:mysql://rm-bp1v8goj767jxm00wdo.mysql.rds.aliyuncs.com/record?useUnicode=true&characterEncoding=utf-8&useSSL=false";
                    String username = "root";
                    String password = "asd123456ROOT";
                    //3.连接成功，数据库对象
                    conn = DriverManager.getConnection(url, username, password);
                    Date d = new Date();
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");
                    String dateNowStr = sdf.format(d);
                    java.text.SimpleDateFormat sd = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                    String dateNowStr1 = sd.format(d);
                    Date date=new Date();
                    int hours = date.getHours();
                    int minute=date.getMinutes();
                    int second=date.getSeconds();
                    String startstatus=hours*3600+minute*60*second>3600*8+30*60?"迟到":"正常";
                    User user=(User)getApplication();
                    System.out.println(location);
                    String sql = "UPDATE record_"+dateNowStr+" SET starttime= '"+dateNowStr1+"',startlocal='"+start_location.getText()+"',startstatus='"+startstatus+"' WHERE phone = '"+user.getPhone()+"'";


                    //跟statement的区别
                    st = conn.prepareStatement(sql); //预编译SQL，先写sql，然后不执行

                    //手动给参数赋值

                    int i = st.executeUpdate();
                    if (i > 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("isequal","yes");
                                Toast.makeText(MainActivity.this, "打卡成功", Toast.LENGTH_LONG).show();

                            }
                        });
//
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void submitend(View view) {

        TextView end_location=findViewById(R.id.end_location);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //1.加载驱动
                    Class.forName("com.mysql.jdbc.Driver"); //固定写法，加载驱动
                    //2.用户信息和url
                    String url="jdbc:mysql://rm-bp1v8goj767jxm00wdo.mysql.rds.aliyuncs.com/record?useUnicode=true&characterEncoding=utf-8&useSSL=false";
                    String username = "root";
                    String password = "asd123456ROOT";
                    //3.连接成功，数据库对象
                    conn = DriverManager.getConnection(url, username, password);
                    Date d = new Date();
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");
                    String dateNowStr = sdf.format(d);
                    java.text.SimpleDateFormat sd = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                    String dateNowStr1 = sd.format(d);
                    User user=(User)getApplication();
                    System.out.println(dateNowStr);
                    String find_phone="SELECT *FROM record_"+dateNowStr+" WHERE phone="+"'"+user.getPhone()+"'";
                    //跟statement的区别
                    st= conn.prepareStatement(find_phone); //预编译SQL，先写sql，然后不执行
                    rs = st.executeQuery();
                    if(rs.next())//确保有了签到
                    {
                        System.out.println(location);
                        Date date=new Date();
                        int hours = date.getHours();
                        int minute=date.getMinutes();
                        int second=date.getSeconds();
                        String endstatus=hours*3600+minute*60*second<3600*7?"早退":"正常";
                        String sql = "UPDATE record_"+dateNowStr+" SET endtime= '"+dateNowStr1+"',endlocal='"+end_location.getText()+"',endstatus='"+endstatus+"' WHERE phone = '"+user.getPhone()+"'";
                        st = conn.prepareStatement(sql); //预编译SQL，先写sql，然后不执行
                        int i = st.executeUpdate();
                        if (i > 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d("isequal","yes");
                                    Toast.makeText(MainActivity.this, "打卡成功", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                    else
                    {   runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("isequal","yes");
                            Toast.makeText(MainActivity.this, "请先签到", Toast.LENGTH_LONG).show();
                        }
                    });

                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

