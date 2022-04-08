package com.example.manage.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.manage.R;
import com.example.manage.activity.LoginActivity;
import com.example.manage.activity.MainActivity;
import com.example.manage.activity.NewPasswordActivity;
import com.example.manage.activity.SettingActivity;
import com.example.manage.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

public class LinearAdapter extends RecyclerView.Adapter <LinearAdapter.LinearViewHolder>{

    //context
    private Context mContext;
    //展示的数据
    private List<String> list=new ArrayList<>();
    public LinearAdapter(Context context){
        this.mContext=context;
        list.add("修改密码");
        list.add("退出登录");
        list.add("设置");
    }

    //onCreateViewHolder方法创建一个viewHolder，viewholder可以理解为一条数据的展示布局，这里我们自定义类LinearViewHolder创建一个只有TextView的item
    //这里我们需要创建每条布局使用的layout：layout_linear_item
    @Override
    public LinearAdapter.LinearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_linear_item,parent,false));
    }

    //onBindViewHolder方法为item的UI绑定展示数据，
    @Override
    public void onBindViewHolder(LinearAdapter.LinearViewHolder holder, int position) {
        holder.textView.setText(String.format(list.get(position)));
    }

    //item的总长度
    @Override
    public int getItemCount() {
        return list.size();
    }

    //LinearViewHolder可以看作一个item的展示和内部逻辑处理，故而如果需要绑定事件，获取控件的时候要在itemView中获取
    //itemView由onCreateViewHolder方法LayoutInflater.inflatec创建
    class LinearViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        public LinearViewHolder(View itemView){
            super(itemView);
            textView=(TextView) itemView.findViewById(R.id.tv_user);

            //简单事件处理
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    switch (textView.getText().toString().trim()){
                        case "修改密码":
                            intent = new Intent(mContext, NewPasswordActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent);
                            break;
                        case "退出登录":
                            SharedPreferences sp= mContext.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=sp.edit();
                            editor.putBoolean("AutoLogin",false);
                            editor.commit();
                            intent = new Intent(mContext, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent);
                            break;
                        case "设置":
                            intent = new Intent(mContext, SettingActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent);
                            break;
                    }

                }
            });
        }
    }

}
