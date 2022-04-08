package com.example.manage.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.manage.Adapter.LinearAdapter;
import com.example.manage.R;
import com.example.manage.pojo.User;

public class UserFragment extends Fragment {
    private RecyclerView rv_user;

    private TextView tv_user_name2;
    private TextView tv_user_phone2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rv_user=(RecyclerView) getActivity().findViewById(R.id.rv_user);
        rv_user.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        rv_user.setAdapter(new LinearAdapter(getActivity().getApplicationContext()));
        //添加Android自带的分割线
        rv_user.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        tv_user_name2 = getActivity().findViewById(R.id.tv_user_name2);
        tv_user_phone2 = getActivity().findViewById(R.id.tv_user_phone2);

        User user=(User)getActivity().getApplication();
        String name = user.getUsername();
        String phone = user.getPhone();
        tv_user_name2.setText(name);
        tv_user_phone2.setText(phone);
    }

}
