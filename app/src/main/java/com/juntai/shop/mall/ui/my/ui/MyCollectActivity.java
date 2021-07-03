package com.juntai.shop.mall.ui.my.ui;

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
 * 我的收藏
 * Created by Ma
 * on 2019/11/26
 */
public class MyCollectActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    MainAdapter adapter;
    CustomViewPager mainViewpager;
    TabLayout mainTablayout;
    private String[] title;
    List<Fragment> mFragments = new ArrayList<>();
    int nowFragment;
    int function = 1;//1=我的收藏  2=我的分享
    @Override
    public int getLayoutView() {
        return R.layout.activity_mycollect;
    }

    @Override
    public void initView() {
        function = getIntent().getIntExtra("function",0);
        if (function == 0){
            finish();
        }
        if (function == 1){
            setTitleName("我的收藏");
            title = new String[]{"店铺收藏","商品收藏"};
        }else {
            setTitleName("我的分享");
            title = new String[]{"店铺分享","商品分享"};
        }
        getTitleRightTv().setTextColor(getResources().getColor(R.color.black));
        getTitleRightTv().setText("编辑");
        getTitleRightTv().setOnClickListener(v -> {
            if (getTitleRightTv().getText().equals("编辑")){
                getTitleRightTv().setText("完成");
                ((MyCollectFragment)mFragments.get(nowFragment)).setEdit(true);
            }else {
                getTitleRightTv().setText("编辑");
                ((MyCollectFragment)mFragments.get(nowFragment)).setEdit(false);
            }
        });

        mainViewpager = findViewById(R.id.collect_viewpager);
        mainTablayout = findViewById(R.id.collect_tablayout);
        mFragments.add(MyCollectFragment.newInstance(0,function));
        mFragments.add(MyCollectFragment.newInstance(1,function));

        adapter = new MainAdapter(getSupportFragmentManager(), this, title, mFragments);
        mainViewpager.setAdapter(adapter);
        mainViewpager.setOffscreenPageLimit(title.length);
        /*viewpager切换监听，包含滑动点击两种*/
        mainViewpager.addOnPageChangeListener(this);
        //TabLayout
//        tabLayout.addTab(tabLayout.newTab().setText("index").setIcon(R.mipmap.point_focus));
        mainTablayout.setupWithViewPager(mainViewpager);
//        tabLayout.setOnTabSelectedListener();
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
        nowFragment = 0;
    }

    @Override
    public void initData() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        nowFragment = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
