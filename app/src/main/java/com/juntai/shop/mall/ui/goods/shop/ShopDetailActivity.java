package com.juntai.shop.mall.ui.goods.shop;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.juntai.mall.im.ModuleIm_Init;
import com.juntai.mall.im.UserIM;
import com.juntai.shop.mall.AppHttpPath;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.baseinfo.BaseAppActivity;
import com.juntai.shop.mall.bean.ShopBean;
import com.juntai.shop.mall.ui.adapter.TopTabAdapter;
import com.juntai.shop.mall.ui.goods.fmt.CommentsFragment;
import com.juntai.shop.mall.ui.goods.fmt.GoodsFragment;
import com.juntai.shop.mall.ui.goods.fmt.ShopInfoFragment;
import com.juntai.shop.mall.utils.GlideImageLoader;
import com.juntai.shop.mall.utils.StringTools;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @aouther tobato
 * @description 描述  店铺详情
 * @date 2021/7/17 10:03
 */
public class ShopDetailActivity extends BaseAppActivity<ShopPresent> implements ShopContract.IShopContractView,ViewPager.OnPageChangeListener, View.OnClickListener {

    private Banner banner;
    GlideImageLoader glideImageLoader = new GlideImageLoader();
    private int shopId;
    private ShopBean.ReturnValueBean shopInfoBean;
    private String[] title = new String[]{"商品","评价", "商家"};
    List<Fragment> mFragments = new ArrayList<>();
    GoodsFragment goodsFragment = new GoodsFragment();
    TopTabAdapter adapter;
    ViewPager viewpager;
    TabLayout tablayout;

    @Override
    protected ShopPresent createPresenter() {
        return new ShopPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_shop_detail;
    }

    @Override
    public void initView() {
        mImmersionBar.reset().transparentStatusBar().statusBarDarkFont(true).init();
        mBaseRootCol.setFitsSystemWindows(false);
        getToolbar().setVisibility(View.GONE);
        initBannerView();
        initTab();
    }
    public void initTab() {
        //
        viewpager = findViewById(R.id.shop_viewpager);
        tablayout = findViewById(R.id.shop_tablayout);
        mFragments.add(goodsFragment);
        mFragments.add(new CommentsFragment());
        mFragments.add(new ShopInfoFragment());
        adapter = new TopTabAdapter(getSupportFragmentManager(), this, title, mFragments);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(title.length);
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
    }
    /**
     * banner
     */
    private void initBannerView() {
        banner = findViewById(R.id.banner);
        banner.isAutoPlay(false);
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                glideImageLoader.pause();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //banner.setImages(Arrays.asList(images)).setImageLoader(new GlideImageLoader()).start();
        banner.setOnBannerListener(position -> {
//            if (imagesAdapter.getData().get(position).indexOf(".mp4") != -1){
//                mContext.startActivity(new Intent(mContext, VideoNetPlayerActivity.class)
//                        .putExtra("path",imagesAdapter.getData().get(position)));
//            }else {
//                imagepos = position;
//                if (imagesAdapter.getData().get(0).indexOf(".mp4") != -1) {
//                    imagepos = position - 1;
//                }
//                mContext.startActivity(new Intent(mContext, ImageZoomActivity.class)
//                        .putStringArrayListExtra("paths", (ArrayList<String>) imagesAdapter.getData())
//                        .putExtra("item",imagepos));
//            }
        });
    }

    @Override
    public void initData() {
        shopId = getIntent().getIntExtra("shopId", -1);
        if (shopId == -1) {
            finish();
        }
        mPresenter.getShopDetailInfo(shopId, AppHttpPath.SHOP_INFO);


    }


    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            case AppHttpPath.SHOP_INFO:
                ShopBean shopBean = (ShopBean) o;
                if (shopBean != null) {
                    shopInfoBean = shopBean.getReturnValue();
                 String   shopRyid = shopInfoBean.getShopAccount();
                    String shopName = shopInfoBean.getShopName();
//                    //0：未收藏 1：已收藏
//                    ivCollect.setImageResource(shopInfoBean.getIsCollect() == 0?R.mipmap.ic_collect:R.mipmap.ic_collect_check);
//                    tvTItle.setText(result.getReturnValue().getShopName());
                    List<String> sssss = new ArrayList<>();
                    if (shopInfoBean.getVideoUrl() != null && !shopInfoBean.getVideoUrl().isEmpty()){
                        sssss.add(AppHttpPath.VIDEO_FOR_CRM + shopInfoBean.getShopId());
                    }
                    sssss.addAll(StringTools.getImagesForCrmBig(shopInfoBean.getImgId()));
                    banner.setImages(sssss).setImageLoader(glideImageLoader).start();
//                    //
//                    goodsFragment.setDateList(shopInfoBean.getShopClassify());
//                    if (gid != -1){
//                        coordinatorLayout.setVisibility(View.GONE);
//                    }
//                    //
//                    ModuleIm_Init.setUser(new UserIM(shopInfoBean.getShopAccount(),shopName,StringTools.getImageForCrmInt(shopInfoBean.getShopUserId())));
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        glideImageLoader.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        glideImageLoader.release();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
