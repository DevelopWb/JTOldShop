package com.juntai.shop.mall.ui.goods.fmt;

import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.juntai.mall.base.base.BaseMvpFragment;
import com.juntai.mall.base.mvp.IPresenter;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.ShopBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @aouther tobato
 * @description 描述 店铺商品  分类
 * @date 2021/7/18 14:37
 */

// TODO: 2021/7/19  这个暂时留着 可以去掉 
public class GoodsFragment2 extends BaseMvpFragment implements ViewPager.OnPageChangeListener {
    ViewPager viewpager;
    private TabLayout tablayout;
    List<Fragment> mFragments = new ArrayList<>();

    private ShopGoodsTabAdapter adapter;

    @Override
    protected IPresenter createPresenter() {
        return null;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.goods_list_fg;
    }

    @Override
    protected void initView() {

    }

    public void initTab(String[] title) {
        //
        viewpager = (ViewPager) getView(R.id.goods_viewpager);
        tablayout = (TabLayout) getView(R.id.goods_tab);
        for (String s : title) {
            mFragments.add(GoodsListFragment.getInstance(s));
        }
        adapter = new ShopGoodsTabAdapter(getChildFragmentManager(), mContext, title, mFragments);
        viewpager.setAdapter(adapter);
//        viewpager.setOffscreenPageLimit(title.length);
        /*viewpager切换监听，包含滑动点击两种*/
        viewpager.addOnPageChangeListener(this);
        //TabLayout
        tablayout.setupWithViewPager(viewpager);
//        tabLayout.setOnTabSelectedListener();
        /**
         * 添加自定义tab布局
         * */
        for (int i = 0; i < title.length; i++) {
            TabLayout.Tab tab = tablayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(adapter.getTabView(i));
            }
        }
//        tablayout.newTab();
        /*viewpager切换默认第一个*/
        viewpager.setCurrentItem(0);
        onPageSelected(0);
    }

    /**
     * 设置数据
     *
     * @param list
     */
    public void setDateList(List<ShopBean.ReturnValueBean.ShopClassifyBean> list) {

        if (list != null && list.size() > 0) {
            String[] title = new String[list.size() + 10];
            for (int i = 0; i < list.size(); i++) {
                ShopBean.ReturnValueBean.ShopClassifyBean shopClassifyBean = list.get(i);
                String shopClassifyName = shopClassifyBean.getShopClassifyName();
                title[i] = shopClassifyName;
            }
            title[1] = "测试";
            title[2] = "测试";
            title[3] = "测试";
            title[4] = "测试";
            title[5] = "测试";
            title[6] = "测试";
            title[7] = "测试";
            title[8] = "测试";
            title[9] = "测试";
            title[10] = "测试";
            initTab(title);
        }


    }

    @Override
    protected void initData() {

    }

    @Override
    public void onSuccess(String tag, Object o) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < tablayout.getTabCount(); i++) {

            if (position == i) {
                TextView textView = tablayout.getTabAt(i).getCustomView().findViewById(R.id.tab_title_tv);
                textView.setBackgroundResource(R.drawable.bg_red_border);
                textView.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            } else {
                TextView textView = tablayout.getTabAt(i).getCustomView().findViewById(R.id.tab_title_tv);
                textView.setBackgroundResource(R.drawable.bg_circle_white);
                textView.setTextColor(ContextCompat.getColor(mContext, R.color.black));
            }
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
