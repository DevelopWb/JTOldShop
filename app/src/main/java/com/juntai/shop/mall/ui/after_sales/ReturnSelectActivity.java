package com.juntai.shop.mall.ui.after_sales;

import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.juntai.mall.base.base.BaseActivity;
import com.juntai.shop.mall.MyApp;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.OrderCommodityListBean;
import com.juntai.shop.mall.ui.after_sales.adt.ReturnGoodsAdapter;

/**
 * 选择退货还是退款
 * Created by Ma
 * on 2019/12/20
 */
public class ReturnSelectActivity extends BaseActivity {
    RecyclerView recyclerView;
    ReturnGoodsAdapter goodsAdapter;
    TextView textView;
    private TextView mTotalPriceTv;
    double price;

    @Override
    public int getLayoutView() {
        return R.layout.activity_return_select;
    }

    @Override
    public void initView() {
        setTitleName("退款退货");
        textView = findViewById(R.id.goods_return_shopname);
        mTotalPriceTv = findViewById(R.id.total_price_tv);
        textView.setText(getIntent().getStringExtra("name"));
        recyclerView = findViewById(R.id.goods_return_list);
        goodsAdapter = new ReturnGoodsAdapter(R.layout.item_return_goods, MyApp.app.goodsReturnBeans);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(goodsAdapter);
        //换货-1，退货-2，退款-3
        findViewById(R.id.return_select_tk_layout).setOnClickListener(v -> {
            MyApp.app.activityTool.toReturnGoodsActivity(mContext,3,getIntent());
        });
        findViewById(R.id.return_select_th_layout).setOnClickListener(v -> {
            MyApp.app.activityTool.toReturnGoodsActivity(mContext,2,getIntent());
        });
        for (OrderCommodityListBean s : MyApp.app.goodsReturnBeans) {
            price += s.getPrice() * s.getCommodityNumber();
        }
        mTotalPriceTv.setText(String.format("￥%s元", price));
    }

    @Override
    public void initData() {

    }
}
