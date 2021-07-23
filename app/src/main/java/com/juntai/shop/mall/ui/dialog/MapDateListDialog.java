package com.juntai.shop.mall.ui.dialog;

import android.view.View;
import android.view.WindowManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.juntai.mall.base.widght.BottomDialogFragment;
import com.juntai.shop.mall.MyApp;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.ShopLocationB;
import com.juntai.shop.mall.ui.adapter.MapDateAdapter;

import java.util.ArrayList;

/**
 * Created by Ma
 * on 2019/11/16
 */
public class MapDateListDialog extends BottomDialogFragment {
    RecyclerView recyclerView;
    MapDateAdapter adapter;
    ArrayList<ShopLocationB.ReturnValueBean> arrayList = new ArrayList<>();
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
            MyApp.app.activityTool.toShopActivity(getActivity(),adapter.getData().get(position).getId());
        });
        adapter.setOnItemChildClickListener((adapter1, view1, position) -> {
            if (view1.getId() == R.id.item_dialog_map_navigation_tv){
//                navigationDialog.setData(adapter.getData().get(position).getLatitude(),
//                        adapter.getData().get(position).getLongitude(),
//                        adapter.getData().get(position).getAddress(),
//                        MyApp.app.getBdLocation());
//                navigationDialog.show(getFragmentManager(),"nav");
                // : 2021/7/4   导航的逻辑 
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
