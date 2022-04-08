package com.example.manage.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.manage.R;
import com.example.manage.pojo.Entity;

import java.util.List;


public class SheetAdapter extends RecyclerView.Adapter {
    private List<Entity> list;

    public SheetAdapter(List<Entity> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sheet_item_layout, parent, false);
        return new sheetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        sheetViewHolder vh = (sheetViewHolder) holder;

        vh.getTv_sheetRow1().setText(list.get(position).getName());
        vh.getTv_sheetRow2().setText(list.get(position).getPhone());
        vh.getTv_sheetRow3().setText(list.get(position).getStatus());
        vh.getTv_sheetRow4().setText(list.get(position).getTime());
        vh.getTv_sheetRow5().setText(list.get(position).getLocal());
        vh.getTv_sheetRow6().setText(list.get(position).getApartment());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class sheetViewHolder extends RecyclerView.ViewHolder{
        public final View mView;
        public final TextView tv_sheetRow1;
        public final TextView tv_sheetRow2;
        public final TextView tv_sheetRow3;
        public final TextView tv_sheetRow4;
        public final TextView tv_sheetRow5;
        public final TextView tv_sheetRow6;

        public sheetViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            tv_sheetRow1 = (TextView) itemView.findViewById(R.id.tv_sheetRow1);
            tv_sheetRow2 = (TextView) itemView.findViewById(R.id.tv_sheetRow2);
            tv_sheetRow3 = (TextView) itemView.findViewById(R.id.tv_sheetRow3);
            tv_sheetRow4 = (TextView) itemView.findViewById(R.id.tv_sheetRow4);
            tv_sheetRow5 = (TextView) itemView.findViewById(R.id.tv_sheetRow5);
            tv_sheetRow6 = (TextView) itemView.findViewById(R.id.tv_sheetRow6);
        }

        public TextView getTv_sheetRow1() {
            return tv_sheetRow1;
        }

        public TextView getTv_sheetRow2() {
            return tv_sheetRow2;
        }

        public TextView getTv_sheetRow3() {
            return tv_sheetRow3;
        }

        public TextView getTv_sheetRow4() {
            return tv_sheetRow4;
        }

        public TextView getTv_sheetRow5() {
            return tv_sheetRow5;
        }

        public TextView getTv_sheetRow6() {
            return tv_sheetRow6;
        }
    }
}

