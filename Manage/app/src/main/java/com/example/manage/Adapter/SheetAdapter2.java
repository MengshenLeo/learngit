package com.example.manage.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.manage.R;
import com.example.manage.pojo.AllRecord;

import java.util.List;

public class SheetAdapter2 extends RecyclerView.Adapter {
    private List<AllRecord> list;

    public SheetAdapter2(List<AllRecord> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sheet_item_layout_2, parent, false);
        return new SheetAdapter2.sheetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SheetAdapter2.sheetViewHolder vh = (SheetAdapter2.sheetViewHolder) holder;

        vh.getTv_name().setText(list.get(position).getName());
        vh.getTv_phone().setText(list.get(position).getPhone());
        vh.getTv_start_status().setText(list.get(position).getStartstatus());
        vh.getTv_end_status().setText(list.get(position).getEndstatus());
        vh.getTv_start_time().setText(list.get(position).getStarttime());
        vh.getTv_start_local().setText(list.get(position).getStartlocal());
        vh.getTv_end_time().setText(list.get(position).getEndtime());
        vh.getTv_end_local().setText(list.get(position).getEndlocal());
        vh.getTv_apartment().setText(list.get(position).getApartment());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class sheetViewHolder extends RecyclerView.ViewHolder{
        public final View mView;
        public final TextView tv_name;
        public final TextView tv_phone;
        public final TextView tv_start_status;
        public final TextView tv_end_status;
        public final TextView tv_start_time;
        public final TextView tv_start_local;
        public final TextView tv_end_time;
        public final TextView tv_end_local;
        public final TextView tv_apartment;

        public sheetViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_phone = (TextView) itemView.findViewById(R.id.tv_phone);
            tv_start_status = (TextView) itemView.findViewById(R.id.tv_start_status);
            tv_end_status = (TextView) itemView.findViewById(R.id.tv_end_status);
            tv_start_time = (TextView) itemView.findViewById(R.id.tv_start_time);
            tv_start_local = (TextView) itemView.findViewById(R.id.tv_start_local);
            tv_end_time = (TextView) itemView.findViewById(R.id.tv_end_time);
            tv_end_local = (TextView) itemView.findViewById(R.id.tv_end_local);
            tv_apartment = (TextView) itemView.findViewById(R.id.tv_apartment);
        }

        public TextView getTv_name() {
            return tv_name;
        }

        public TextView getTv_phone() {
            return tv_phone;
        }

        public TextView getTv_start_status() {
            return tv_start_status;
        }

        public TextView getTv_end_status() {
            return tv_end_status;
        }

        public TextView getTv_start_time() {
            return tv_start_time;
        }

        public TextView getTv_start_local() {
            return tv_start_local;
        }

        public TextView getTv_end_time() {
            return tv_end_time;
        }

        public TextView getTv_end_local() {
            return tv_end_local;
        }

        public TextView getTv_apartment() {
            return tv_apartment;
        }

    }
}
