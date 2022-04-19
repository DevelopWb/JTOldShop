package com.juntai.shop.mall;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.baidu.location.BDLocation;
import com.google.android.material.tabs.TabLayout;
import com.juntai.mall.base.base.IMLoginOutEvent;
import com.juntai.mall.base.mvp.BasePresenter;
import com.juntai.mall.base.utils.NotificationTool;
import com.juntai.mall.base.utils.SPTools;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.mall.im.CustomMessage;
import com.juntai.shop.mall.baseinfo.BaseAppActivity;
import com.juntai.shop.mall.ui.act.LoginActivity;
import com.juntai.shop.mall.ui.adapter.MainAdapter;
import com.juntai.shop.mall.homepage.HomepageFragment;
import com.juntai.shop.mall.ui.fmt.PublishFragment;
import com.juntai.shop.mall.mine.MineFragment;
import com.juntai.shop.mall.utils.AppUtils;
import com.juntai.shop.mall.view.CustomViewPager;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends BaseAppActivity implements ViewPager.OnPageChangeListener {
    MainAdapter adapter;
    CustomViewPager mainViewpager;
    TabLayout mainTablayout;
    private String[] title = new String[]{"首页", "附近", "我的"};
    private int[] tabDrawables = new int[]{R.drawable.pre_main_home, R.drawable.pre_main_post, R.drawable.pre_main_my};
    List<Fragment> mFragments = new ArrayList<>();
    private HomepageFragment homepageFragment;
    private long mExitTime = 0;//声明一个long类型变量：用于存放上一点击“返回键”的时刻
    //[]{new HomeFragment(), nearServiceFragment,new GoodsFragment(), new ShopCartFragment(), new MyFragment()};
    @Override
    public int getLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initView() {
        mImmersionBar.reset().transparentStatusBar().statusBarDarkFont(true).init();
        getToolbar().setVisibility(View.GONE);
        mBaseRootCol.setFitsSystemWindows(false);
        mainViewpager = findViewById(R.id.main_viewpager);
        mainTablayout = findViewById(R.id.main_tablayout);
        homepageFragment = new HomepageFragment();
        mFragments.add(homepageFragment);
        mFragments.add(new PublishFragment());
        mFragments.add(new MineFragment());
        //
        initTab();
    }


    @Override
    public void initData() {
        if (MyApp.app.getUser() != null) {
//            ModuleIm_Init.connectIM(MyApp.app.getUser().getReturnValue().getrOngYunToken());
        }

        update( false);
    }

    public void initTab() {
        adapter = new MainAdapter(getSupportFragmentManager(), this, title, tabDrawables, mFragments);
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
//                tab.setIcon(tabDrawables[i]);
//                tab.setText(title[i]);
                tab.setCustomView(adapter.getTabView(i));
            }
        }
        /*viewpager切换默认第一个*/
        mainViewpager.setCurrentItem(0);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        switch (i) {
            case 2:
                mImmersionBar.reset().transparentStatusBar().statusBarDarkFont(false).init();
                break;
            default:
                mImmersionBar.reset().transparentStatusBar().statusBarDarkFont(true).init();
                break;
        }
    }

    /**
     * 登录被顶-或需要重新登录
     *
     * @param login
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginOutEvent(IMLoginOutEvent login) {
        ToastUtils.toast(mContext, login.toast);
        //重新登录
//        ModuleIm_Init.logout();
        //登录信息设置为空
        SPTools.saveString(MyApp.app, AppUtils.SP_KEY_LOGIN, "");
        MyApp.app.setUserBean(null);
        MyApp.app.getNowActivity().finish();
        startActivity(new Intent(mContext, LoginActivity.class));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void imCustom(CustomMessage customMessage) {
        //getChannel  1发货推送；2退款推送；3拒绝退款推送
        if (customMessage.getChannel() == 1) {
            NotificationTool.sendNotifMessage(
                    mContext,
                    1,
                    "商品已发货",
                    customMessage.getContent(),
                    R.drawable.ic_noti_logo,
                    MyApp.app.activityTool.getOrderDtailsIntent(mContext, customMessage.getId()));
        } else if (customMessage.getChannel() == 2 || customMessage.getChannel() == 3) {
            NotificationTool.sendNotifMessage(
                    mContext,
                    2,
                    customMessage.getChannel() == 2 ? "商家同意退款" : "商家拒绝退款",
                    customMessage.getContent(),
                    R.drawable.ic_noti_logo,
                    MyApp.app.activityTool.getReturnDetailsIntent(mContext, customMessage.getId()));
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onLocationReceived(BDLocation bdLocation) {
        if (homepageFragment != null) {
            homepageFragment.onLocationReceived(bdLocation);
        }

    }

    @Override
    public boolean requestLocation() {
        return true;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFragments.get(2).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(String tag, Object o) {

    }

    @Override
    public void onError(String tag, Object o) {

    }


    @Override
    public void onBackPressed() {

        //与上次点击返回键时刻作差
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            //大于2000ms则认为是误操作，使用Toast进行提示
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            //并记录下本次点击“返回键”的时刻，以便下次进行判断
            mExitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }
}
