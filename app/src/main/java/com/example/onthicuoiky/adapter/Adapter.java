package com.example.onthicuoiky.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.onthicuoiky.ListMonHoc;
import com.example.onthicuoiky.R;
import com.example.onthicuoiky.model.KhoaHoc;

import java.util.List;

public class Adapter extends BaseAdapter {
    private Context context;
    private int idLayout;
    private List<KhoaHoc> list;
    public Adapter(Context context, int idLayout, List<KhoaHoc> list) {
        this.context = context;
        this.idLayout = idLayout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(idLayout, viewGroup, false);
            TextView txtTenKhoaHoc = view.findViewById(R.id.txtTenItem);
            TextView txtSoTien = view.findViewById(R.id.txtGiaTien);
            ImageButton imgDelete = view.findViewById(R.id.imgRm);
            KhoaHoc khoaHoc = list.get(position);
            txtTenKhoaHoc.setText(khoaHoc.getTenHoaHoc());
            txtSoTien.setText(khoaHoc.getSoTien());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ListMonHoc.class);
                    Bundle bundle = new Bundle();

                    bundle.putString("id", khoaHoc.getId() + "");
                    bundle.putString("tenKhoaHoc", khoaHoc.getTenHoaHoc());
                    bundle.putString("giaTien", khoaHoc.getSoTien());

                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ListMonHoc.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id", khoaHoc.getId() + "");
                    bundle.putString("del", "del");

                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }

        return view;
    }
}
