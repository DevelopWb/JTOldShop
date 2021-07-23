package com.juntai.shop.mall.ui.goods.shop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.juntai.mall.base.base.BaseObserver;
import com.juntai.mall.base.utils.LogUtil;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.mall.im.ModuleIm_Init;
import com.juntai.mall.im.UserIM;
import com.juntai.shop.mall.MyApp;
import com.juntai.shop.mall.AppHttpPath;
import com.juntai.shop.mall.AppNetModule;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.baseinfo.BaseAppActivity;
import com.juntai.shop.mall.bean.CartItemLocB;
import com.juntai.shop.mall.bean.IntBean;
import com.juntai.shop.mall.bean.ShopBean;
import com.juntai.shop.mall.bean.ShopCartsBean;
import com.juntai.shop.mall.bean.event.EventDetailsMessage;
import com.juntai.shop.mall.bean.event.EventToSubmit;
import com.juntai.shop.mall.ui.act.ReportActivity;
import com.juntai.shop.mall.ui.adapter.TopTabAdapter;
import com.juntai.shop.mall.ui.goods.fmt.CommentsFragment;
import com.juntai.shop.mall.ui.goods.fmt.GoodsDetailsFragment;
import com.juntai.shop.mall.ui.goods.fmt.GoodsFragment;
import com.juntai.shop.mall.ui.goods.fmt.GoodsFragment2;
import com.juntai.shop.mall.ui.goods.fmt.ShopCartFragment;
import com.juntai.shop.mall.ui.goods.fmt.ShopDesFragment;
import com.juntai.shop.mall.utils.AppUtils;
import com.juntai.shop.mall.utils.DpTools;
import com.juntai.shop.mall.utils.GlideImageLoader;
import com.juntai.shop.mall.utils.ShareUtils;
import com.juntai.shop.mall.utils.StringTools;
import com.juntai.shop.mall.utils.listener.GoodsStatusChangeListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 店铺详情
 * Created by Ma
 * on 2019/8/28
 * update  2021-7-16 tobato
 */

public class ShopActivity extends BaseAppActivity<ShopPresent> implements ViewPager.OnPageChangeListener, View.OnClickListener,ShopContract.IShopContractView {
    public static int shopId;
    public static String shopRyid,shopName;
    private Banner banner;
    GlideImageLoader glideImageLoader = new GlideImageLoader();
    TopTabAdapter adapter;
    ViewPager viewpager;
    TabLayout tablayout;
    ShopBean.ReturnValueBean shopInfoBean;
    private String[] title = new String[]{"商品","评价", "商家"};
    List<Fragment> mFragments = new ArrayList<>();
    GoodsFragment goodsFragment = new GoodsFragment();
    //title
    ImageView ivBack,ivCollect,ivMore;
    TextView tvTItle;
//    LinearLayout titlelayout;
    boolean isToSubmit = false,isCollect = false;
    //cart
    GoodsDetailsFragment detailsFragment = new GoodsDetailsFragment();
    ShopCartFragment shopCartFragment = new ShopCartFragment();
    int gid;
    private AppBarLayout mAppBarLayout;

    @Override
    public int getLayoutView() {
        return R.layout.activity_shop;
    }

    @Override
    public void initView() {
        shopId = getIntent().getIntExtra("shopId",-1);
        if (shopId == -1){
            finish();
        }
        getToolbar().setVisibility(View.GONE);
        mImmersionBar.reset().transparentStatusBar().statusBarDarkFont(false).init();
        mBaseRootCol.setFitsSystemWindows(false);
//        titlelayout = findViewById(R.id.shop_title_layout);
        ivBack = findViewById(R.id.shop_details_back);
        mAppBarLayout = findViewById(R.id.visit_analyze_appbarLayout);
        tvTItle = findViewById(R.id.shop_name_tv);
        ivCollect = findViewById(R.id.title_shop_check);
        ivMore = findViewById(R.id.shop_details_more_iv);
        ivBack.setOnClickListener(this);
        ivCollect.setOnClickListener(this);
        ivMore.setOnClickListener(this);
        initBannerView();

        //
        initTab();
        //
        showTitleRes(R.id.title_collect,R.id.title_more);
//        goodsFragment.setGoodsStatusChangeListener(new GoodsStatusChangeListener(){
//
//        });

        detailsFragment = (GoodsDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.shop_fragment_goodsdetails);
        shopCartFragment = (ShopCartFragment) getSupportFragmentManager().findFragmentById(R.id.shop_fragment_shopcart);
        //是否直接展示商品
        gid = getIntent().getIntExtra("goodsId",-1);
        if (gid != -1){
//            titlelayout.setVisibility(View.GONE);
            detailsFragment.setGoodsId(gid);
        }else {
            getSupportFragmentManager().beginTransaction().hide(detailsFragment).commit();
        }
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
        getData();
    }


    @Override
    protected void onResume() {
        super.onResume();
        isToSubmit = false;
        getCarts();
    }

    @Override
    protected void onPause() {
        glideImageLoader.pause();
        super.onPause();
    }

    public void getData(){
        AppNetModule.createrRetrofit()
                .shop(MyApp.app.getAccount(), MyApp.app.getUserToken(),shopId, MyApp.app.getUid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ShopBean>() {
                    @Override
                    public void onSuccess(ShopBean result) {
                        shopInfoBean = result.getReturnValue();
                        shopRyid = shopInfoBean.getShopAccount();
                        shopName = shopInfoBean.getShopName();
                        //0：未收藏 1：已收藏
                        ivCollect.setImageResource(shopInfoBean.getIsCollect() == 0?R.mipmap.ic_collect:R.mipmap.ic_collect_check);
                        tvTItle.setText(result.getReturnValue().getShopName());
                        List<String> sssss = new ArrayList<>();
                        if (shopInfoBean.getVideoUrl() != null && !shopInfoBean.getVideoUrl().isEmpty()){
                            sssss.add(AppHttpPath.VIDEO_FOR_CRM + shopInfoBean.getShopId());
                        }
                        sssss.addAll(StringTools.getImagesForCrmBig(result.getReturnValue().getImgId()));
                        banner.setImages(sssss).setImageLoader(glideImageLoader).start();
                        //
                        goodsFragment.setDateList(result.getReturnValue().getShopClassify());
                        if (gid != -1){
                            mAppBarLayout.setVisibility(View.GONE);
                        }
                        //
//                        ModuleIm_Init.setUser(new UserIM(shopInfoBean.getShopAccount(),shopName,StringTools.getImageForCrmInt(shopInfoBean.getShopUserId())));
                    }
                    @Override
                    public void onError(String msg) {
                        ToastUtils.toast(mContext,msg);
                    }
                });
    }

    /**
     * 获取购物车
     */
    public void getCarts(){
        AppNetModule.createrRetrofit()
                .shopCarts(MyApp.app.getAccount(), MyApp.app.getUserToken(), MyApp.app.getUid(), shopId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ShopCartsBean>() {
                    @Override
                    public void accept(ShopCartsBean shopCartsBean) throws Exception {
                        StringBuffer stringBuffer;
                        if (shopCartsBean.getReturnValue() != null && shopCartsBean.getReturnValue().size() > 0) {
                            MyApp.app.getCartBeansForShop(shopId).clear();
                            for (ShopCartsBean.ReturnValueBean bean : shopCartsBean.getReturnValue()) {
                                stringBuffer = new StringBuffer();
                                for (ShopCartsBean.ReturnValueBean.SpecificationBean bean1 : bean.getSpecification()) {
                                    stringBuffer.append(bean1.getParameterName());
                                }
                                CartItemLocB cartItemLocB = new CartItemLocB(
                                        bean.getTrolleyId(),
                                        bean.getCommodityId(),
                                        bean.getCommodityName(),
                                        bean.getCommodityImg(),
                                        bean.getCommodityNum(),
                                        bean.getInventoryNum(),
                                        bean.getPackingCharges(),
                                        bean.getPrice(),
                                        bean.getAttributeId(),
                                        stringBuffer.toString());
                                MyApp.app.setCartBean(shopId, cartItemLocB);
                            }
                        }
                        if (shopCartFragment != null) {
                            shopCartFragment.update();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (shopCartFragment != null){
                            shopCartFragment.update();
                        }
                        LogUtil.e("同步购物车失败");
                    }
                });
    }
    /**
     * 收藏
     */
    public void collect(){
        if (shopInfoBean == null || isCollect) return;
        isCollect = true;
        AppNetModule.createrRetrofit()
                .collectOperateOne(MyApp.app.getAccount(), MyApp.app.getUserToken(), MyApp.app.getUid(),
                        0,shopId,0, shopInfoBean.getIsCollect(), shopInfoBean.getIsCollect())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<IntBean>() {
                    @Override
                    public void onSuccess(IntBean result) {
                        //0：未收藏 1：已收藏
                        shopInfoBean.setIsCollect(result.getReturnValue());
                        ivCollect.setImageResource(shopInfoBean.getIsCollect() == 0?R.mipmap.ic_collect:R.mipmap.ic_collect_check);
                    }
                    @Override
                    public void onError(String msg) {
                        ToastUtils.toast(mContext,msg);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        isCollect = false;
                    }
                });
    }
    PopupWindow popupWindow;
    public void showPopMore(){
        if (popupWindow == null){
            View view= LayoutInflater.from(mContext).inflate(R.layout.pop_shop_more, null);
            //背景颜色
            popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            popupWindow.setTouchable(true);
            view.findViewById(R.id.report_shop).setOnClickListener(this);
            view.findViewById(R.id.share_shop).setOnClickListener(this);
            //显示（自定义位置）
        }
        popupWindow.showAsDropDown(ivMore, -DpTools.px2dip(mContext,40),0);//显示在控件下面
    }

    public void initTab() {
        //
        viewpager = findViewById(R.id.shop_viewpager);
        tablayout = findViewById(R.id.shop_tablayout);
        mFragments.add(goodsFragment);
        mFragments.add(new CommentsFragment());
        mFragments.add(new ShopDesFragment());
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.report_shop://举报商家
                startActivity(new Intent(mContext, ReportActivity.class));
                break;
            case R.id.share_shop://分享
                if (shopInfoBean.getWebUrl() != null && !shopInfoBean.getWebUrl().isEmpty()){
                    ShareUtils.shareForMob(mContext,
                            AppUtils.getAppName(),
                            shopInfoBean.getWebUrl(),
                            shopInfoBean.getShopName(),
                            StringTools.getImageForCrmInt(shopInfoBean.getLogoId()), new PlatformActionListener() {
                                @Override
                                public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

                                }

                                @Override
                                public void onError(Platform platform, int i, Throwable throwable) {

                                }

                                @Override
                                public void onCancel(Platform platform, int i) {

                                }
                            });
                }
                break;
            case R.id.title_shop_back:
                finish();
                break;
            case R.id.shop_details_back:
                finish();
                break;
            case R.id.title_shop_check:
                collect();
                break;
            case R.id.shop_details_more_iv:
                showPopMore();
                break;
        }
    }

    /**
     * 同步购物车
     */
    public void cartSys(){
        if (MyApp.app.getCartBeansForShop(shopId).size() == 0){
            return;
        }

        JSONArray jsonArray = new JSONArray();
        for (CartItemLocB b: MyApp.app.getCartBeansForShop(shopId)) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id",b.getCartId());//购物车id,没有为0
                jsonObject.put("commodityId",b.getGoodsId());
                jsonObject.put("commodityNum",b.getNum());
                jsonObject.put("attributeId",b.getSpcId());
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("商城",e.toString());
            }
        }
        AppNetModule.createrRetrofit()
                .cartSys(MyApp.app.getAccount(), MyApp.app.getUserToken(), MyApp.app.getUid(),shopId,jsonArray.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (isToSubmit){
                        MyApp.app.activityTool.toOrderConfirmActivity(mContext,shopId, shopInfoBean.getLogoId(), shopInfoBean.getShopName());
                    }
                    LogUtil.d("购物车同步success-："+ result.success);
                }, throwable -> {
                    LogUtil.d("购物车同步error-："+ throwable.toString());
                });
    }
    /**
     *  结算
     * @param eventToSubmit:是否是结算
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toSubmit(EventToSubmit eventToSubmit){
        isToSubmit = true;
        cartSys();
    }

    /**
     * 刷新购物车
     * @param itemLocB
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCartEvent(CartItemLocB itemLocB){
        //
        if (itemLocB.getGoodsId() != 0){
            MyApp.app.setCartBean(shopId,itemLocB);
        }
        shopCartFragment.update();
    }

    /**
     * @param locB
     */
    public void refrehsGoods(CartItemLocB locB){
//        goodsFragment.refreshList(locB.getGoodsId(),locB.getNum());
//        if (detailsFragment != null){
//            detailsFragment.refreshNum(locB.getGoodsId(),locB.getNum());
//        }
    }

    /**
     * 展示商品详情
     * @param detailsMessage
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventDetailsMessage detailsMessage){
        if (detailsMessage.goodsId == -1){
            //隐藏fragment
//            titlelayout.setVisibility(View.VISIBLE);
            mAppBarLayout.setVisibility(View.VISIBLE);
            //状态栏字体颜色变成白色
            mImmersionBar.statusBarDarkFont(false).init();
            getSupportFragmentManager().beginTransaction().hide(detailsFragment).commit();
        }else {
//            titlelayout.setVisibility(View.GONE);
            mAppBarLayout.setVisibility(View.GONE);
            detailsFragment.setGoodsId(detailsMessage.goodsId);
            //状态栏字体颜色变成黑色
            mImmersionBar.statusBarDarkFont(true).init();
            getSupportFragmentManager().beginTransaction().show(detailsFragment).commit();
        }
        glideImageLoader.pause();
    }

    @Override
    protected ShopPresent createPresenter() {
        return new ShopPresent();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        if (detailsFragment.isVisible()){
            getSupportFragmentManager().beginTransaction().hide(detailsFragment).commit();
        }else {
            //上传购物车
            cartSys();
//            goodsFragment.setGoodsStatusChangeListener(null);
            goodsFragment.setDateList(null);
            glideImageLoader.release();
            super.onDestroy();
        }
    }

    @Override
    public void onBackPressed() {
        if (detailsFragment.isVisible()){
            getSupportFragmentManager().beginTransaction().hide(detailsFragment).commit();
//            titlelayout.setVisibility(View.VISIBLE);
            mImmersionBar.statusBarDarkFont(false).init();
            mAppBarLayout.setVisibility(View.VISIBLE);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public void onSuccess(String tag, Object o) {

    }
}
