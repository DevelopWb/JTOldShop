package com.juntai.shop.mall.ui.after_sales;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.juntai.mall.base.base.BaseActivity;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.shop.mall.MyApp;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.OrderCommodityListBean;
import com.juntai.shop.mall.ui.after_sales.adt.GoodsSelectAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 申请售后商品选择
 * Created by Ma
 * on 2019/12/21
 */
public class GoodsSelectActivity extends BaseActivity {
    SmartRefreshLayout smartRefreshLayout;
    RecyclerView recyclerView;
    GoodsSelectAdapter selectAdapter;
    @Override
    public int getLayoutView() {
        return R.layout.activity_goods_select;
    }

    @Override
    public void initView() {
        setTitleName("商品选择");
        for (OrderCommodityListBean bean: MyApp.app.goodsReturnBeans) {
            bean.setChecked(false);
        }
        smartRefreshLayout = findViewById(R.id.smartRefreshLayout);
        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setEnableLoadMore(false);
        recyclerView = findViewById(R.id.recyclerView);
        selectAdapter = new GoodsSelectAdapter(R.layout.item_goods_select, MyApp.app.goodsReturnBeans);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(selectAdapter);
        findViewById(R.id.goods_select_submit).setOnClickListener(v -> {
            int size = MyApp.app.goodsReturnBeans.size();
            List<OrderCommodityListBean> listBeans = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                if (!MyApp.app.goodsReturnBeans.get(i).isChecked()){
                    listBeans.add(MyApp.app.goodsReturnBeans.get(i));
                }
            }
            MyApp.app.goodsReturnBeans.removeAll(listBeans);
            if (MyApp.app.goodsReturnBeans.size() == 0){
                ToastUtils.toast(mContext,"请选择商品");
            }else {
                //有选中
                MyApp.app.activityTool.toAfterSalesActivity(mContext,getIntent());
                finish();
            }
        });

    }

    @Override
    public void initData() {

    }
}
