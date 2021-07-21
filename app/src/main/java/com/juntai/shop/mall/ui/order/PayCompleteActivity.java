package com.juntai.shop.mall.ui.order;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.juntai.mall.base.base.BaseActivity;
import com.juntai.shop.mall.MyApp;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.MainActivity;

/**
 * 订单支付后的界面--成功-失败
 * Created by Ma
 * on 2019/9/8
 */
public class PayCompleteActivity extends BaseActivity implements View.OnClickListener {
    LinearLayout layoutSuc,layoutFia;
    @Override
    public int getLayoutView() {
        return R.layout.activity_order_pay_complete;
    }

    @Override
    public void initView() {
        mImmersionBar.reset().transparentStatusBar().statusBarDarkFont(false).init();
        mBaseRootCol.setFitsSystemWindows(false);
        setTitleName("");
        setToolbarBg(R.mipmap.my_center_red_bg);
        layoutSuc = findViewById(R.id.pay_end_success_layout);
        layoutFia = findViewById(R.id.pay_end_failure_layout);
        findViewById(R.id.pay_end_home1).setOnClickListener(this);
        findViewById(R.id.pay_end_home2).setOnClickListener(this);
        findViewById(R.id.pay_end_order).setOnClickListener(this);
        findViewById(R.id.pay_end_retry).setOnClickListener(this);
    }

    @Override
    public void initData() {
        layoutSuc.setVisibility(View.VISIBLE);
        layoutFia.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pay_end_home1:
                startActivity(new Intent(mContext, MainActivity.class));
                break;
            case R.id.pay_end_home2:
                startActivity(new Intent(mContext, MainActivity.class));
                break;
            case R.id.pay_end_order:
                MyApp.app.activityTool.toOrderDtailsActivity(mContext,getIntent().getIntExtra("id",0));
                break;
            case R.id.pay_end_retry:
                break;
        }
    }
}
