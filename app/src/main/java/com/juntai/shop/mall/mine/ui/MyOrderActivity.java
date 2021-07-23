package com.juntai.shop.mall.mine.ui;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.juntai.mall.base.base.BaseActivity;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.ui.adapter.MainAdapter;
import com.juntai.shop.mall.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的订单
 * Created by Ma
 * on 2019/12/5
 */
public class MyOrderActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    MainAdapter adapter;
    CustomViewPager mainViewpager;
    TabLayout mainTablayout;
    private String[] title = new String[]{"全部","待付款","待发货","待收货","待评价","退款/售后"};
//    private String[] title = new String[]{"全部","待付款","待发货","待收货"};
    //全部，待付款，待发货，待收货，待评价，退款/售后
    //(0：待付款）（1：待发货）（2：待收货）（3：待评价）（4：退款中）（5：完成）（6:订单取消）（7：退款完成）（8：全部订单）
    private int[] status = new int[]{8,0,1,2,3,4};
//    private int[] status = new int[]{8,0,1,2};
    List<Fragment> mFragments = new ArrayList<>();
    @Override
    public int getLayoutView() {
        return R.layout.activity_myorder;
    }

    @Override
    public void initView() {
        setTitleName("我的订单");
        mainViewpager = findViewById(R.id.order_viewpager);
        mainTablayout = findViewById(R.id.order_tablayout);
        mainViewpager.setScanScroll(true);
        for (int i = 0; i < status.length; i++) {
            mFragments.add(MyOrderFragment.newInstance(status[i]));
        }

        adapter = new MainAdapter(getSupportFragmentManager(), this, title, mFragments);
        mainViewpager.setAdapter(adapter);
        mainViewpager.setOffscreenPageLimit(title.length);
        /*viewpager切换监听，包含滑动点击两种*/
        mainViewpager.addOnPageChangeListener(this);
        mainTablayout.setupWithViewPager(mainViewpager);
        //设置tab文本的没有选中（第一个参数）和选中（第二个参数）的颜色
        mainTablayout.setTabTextColors(Color.BLACK, Color.RED);
        /**
         * 添加自定义tab布局
         * */
        for (int i = 0; i < mainTablayout.getTabCount(); i++) {
            TabLayout.Tab tab = mainTablayout.getTabAt(i);
            if (tab != null) {
                tab.setText(title[i]);
            }
        }
        /*viewpager切换默认第一个*/
        mainViewpager.setCurrentItem(0);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        needRefresh();
    }

    public void needRefresh(){
        for (Fragment f:mFragments) {
            ((MyOrderFragment)f).setRefresh(true);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        TabLayout.Tab tab = mainTablayout.getTabAt(position);
        View view = tab.getCustomView();
        if (null != view && view instanceof TextView) {
            // 改变 tab 未选择状态下的字体大小
//            ((TextView) view).setTextSize(18);
            // 改变 tab 未选择状态下的字体颜色
            ((TextView) view).setTextColor(ContextCompat.getColor(mContext, R.color.red));
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
