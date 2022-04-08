package com.example.manage.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;


import com.amap.api.location.AMapLocationClient;
import com.example.manage.R;
import com.example.manage.activity.RuleActivity;
import com.example.manage.activity.SelectActivity;

public class MapFragment extends Fragment {

   private String location="1";
   private AMapLocationClient ServiceSettings;

   private ImageButton bt_kaoqin;
   private ImageButton bt_rule;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bt_kaoqin = getActivity().findViewById(R.id.bt_kaoqin);
        bt_rule = getActivity().findViewById(R.id.bt_rule);

        bt_kaoqin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SelectActivity.class));
            }
        });
        bt_rule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), RuleActivity.class));
            }
        });

    }
}
