package com.example.manage.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manage.R;
import com.example.manage.pojo.User;

import java.util.ArrayList;
import java.util.List;


public class AddAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<User> list;
    private onItemClickListener onItemClickListener;
    List<User>seleced =new ArrayList<>();
    public AddAdapter(Context context,List<User> list) {
        this.mContext=context;this.list = list;
    }


    //    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.add_item, parent, false);
//        return new addViewHolder(view);
//    }
    public interface onItemClickListener {
        void onClick(View view, int position);
    }

    public void setOnItemClickListener(AddAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public AddAdapter.addViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        addViewHolder holder = new addViewHolder(LayoutInflater.from(mContext).inflate(R.layout.add_item, viewGroup, false));
        return holder;


    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        addViewHolder vh = (addViewHolder) holder;

        vh.getTv_addRow1().setText(list.get(position).getUsername());
        vh.getTv_addRow2().setText(list.get(position).getPhone());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClick(v,position);
                if(vh.getCb_add().isChecked()){
                    vh.getCb_add().setChecked(false);}
                else{
                    vh.getCb_add().setChecked(true);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class addViewHolder extends RecyclerView.ViewHolder{
        public final View mView;
        public final TextView tv_addRow1;
        public final TextView tv_addRow2;
        public final CheckBox cb_add;


        public addViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            tv_addRow1 = (TextView) itemView.findViewById(R.id.tv_addRow1);
            tv_addRow2 = (TextView) itemView.findViewById(R.id.tv_addRow2);
            cb_add=(CheckBox) itemView.findViewById(R.id.cb_add);

        }
        public List<User> getSelected(){return seleced;}
        public TextView getTv_addRow1() {
            return tv_addRow1;
        }

        public TextView getTv_addRow2() {
            return tv_addRow2;
        }

        public CheckBox getCb_add(){return cb_add;}

    }


}

