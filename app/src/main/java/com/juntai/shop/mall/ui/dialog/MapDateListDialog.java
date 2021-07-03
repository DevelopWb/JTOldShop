package com.juntai.shop.mall.ui.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.juntai.mall.base.widght.BottomDialogFragment;
import com.juntai.mall.bdmap.act.NavigationDialog;
import com.juntai.shop.mall.App;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.ShopLocationB;
import com.juntai.shop.mall.ui.adapter.MapDateAdapter;
import com.juntai.shop.mall.ui.goods.ShopActivity;

import java.util.ArrayList;

/**
 * Created by Ma
 * on 2019/11/16
 */
public class MapDateListDialog extends BottomDialogFragment {
    RecyclerView recyclerView;
    MapDateAdapter adapter;
    ArrayList<ShopLocationB.ReturnValueBean> arrayList = new ArrayList<>();
    NavigationDialog navigationDialog = new NavigationDialog();
    @Override
    public int setView() {
        return R.layout.dialog_map_date;
    }

    @Override
    public void initView(View view) {
        recyclerView = view.findViewById(R.id.diglog_mapdate_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MapDateAdapter(R.layout.item_map_date,arrayList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter1, view1, position) -> {
            App.app.activityTool.toShopActivity(getActivity(),adapter.getData().get(position).getId());
        });
        adapter.setOnItemChildClickListener((adapter1, view1, position) -> {
            if (view1.getId() == R.id.item_dialog_map_dh){
                navigationDialog.setData(adapter.getData().get(position).getLatitude(),
                        adapter.getData().get(position).getLongitude(),
                        adapter.getData().get(position).getAddress(),
                        App.app.getBdLocation());
                navigationDialog.show(getFragmentManager(),"nav");
            }
        });
    }

    public int dialogHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }
    public void setDate(ArrayList<ShopLocationB.ReturnValueBean> list){
        arrayList.clear();
        arrayList.addAll(list);
        if (adapter != null){
            adapter.notifyDataSetChanged();
        }
    }
}
