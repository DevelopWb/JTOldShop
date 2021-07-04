package com.juntai.shop.mall.ui.goods.fmt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.juntai.mall.base.base.BaseFragment;
import com.juntai.mall.base.base.BaseObserver;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.shop.mall.MyApp;
import com.juntai.shop.mall.AppHttpPath;
import com.juntai.shop.mall.AppNetModule;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.CartItemLocB;
import com.juntai.shop.mall.bean.GoodsB;
import com.juntai.shop.mall.bean.IntBean;
import com.juntai.shop.mall.ui.goods.SpecificationsDialog;
import com.juntai.shop.mall.ui.goods.adt.AttributesAdapter;
import com.juntai.shop.mall.utils.AppUtils;
import com.juntai.shop.mall.utils.DpTools;
import com.juntai.shop.mall.utils.GlideImageLoader;
import com.juntai.shop.mall.utils.ShareUtils;
import com.juntai.shop.mall.view.CountView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 商品详情页
 */
public class GoodsDetailsFragment extends BaseFragment implements View.OnClickListener {
    private Banner banner;
    RecyclerView recyclerView;
    AttributesAdapter attributesAdapter;
    //title,返回的点击事件在商铺详情页
    ImageView ivCollect,ivMore,ivSpc;
    TextView tvTitle;

    CountView countview;
    TextView tvPrice,tvName,tvYue,tvContent,tvSpecfication;
    int goodsId = -1;
    GoodsB.ReturnValueBean goodsB;
    SpecificationsDialog specificationsDialog = new SpecificationsDialog();
    boolean isCollect = false;
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_goodsdetail;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
        getData();
    }

    @Override
    public void initView() {
        tvTitle = getView(R.id.title_goods_title);
        ivCollect = getView(R.id.title_goods_check);
        ivMore = getView(R.id.title_goods_more);
        ivCollect.setOnClickListener(this);
        ivMore.setOnClickListener(this);
        getView(R.id.goods_deatils_share).setOnClickListener(v -> share());
        //
        recyclerView = getView(R.id.goods_deatils_attributes);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //添加默认分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
        attributesAdapter = new AttributesAdapter(R.layout.item_attributes,new ArrayList());
        recyclerView.setAdapter(attributesAdapter);
        //
        tvPrice = getView(R.id.goods_deatils_price);
        tvName = getView(R.id.goods_deatils_name);
        tvYue = getView(R.id.goods_deatils_yue);
        tvContent = getView(R.id.goods_deatils_content);
        tvSpecfication = getView(R.id.specfication_text);
        countview = getView(R.id.details_countview);
        ivSpc = getView(R.id.goods_deatils_specification);
        ivSpc.setOnClickListener(this);

        /*anner*/
        banner = getView(R.id.banner);
        banner.isAutoPlay(false);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setImageLoader(new GlideImageLoader());

        countview.setNumber(0);
        //countview.setNumEdit(false);
        countview.setOnCountChangeListener((count, type) -> {
            if (count > goodsB.getSku().get(0).getInventoryNum()){
                ToastUtils.toast(mContext,"当前库存不足");
            }else {
                CartItemLocB cartItemLocB = new CartItemLocB(
                        0,
                        goodsB.getId(),
                        goodsB.getCommodityName(),
                        goodsB.getCommodityPicture().get(0).getImgUrl(),
                        count,
                        goodsB.getSku().get(0).getInventoryNum(),
                        goodsB.getSku().get(0).getPackingCharges(),
                        goodsB.getSku().get(0).getPrice(),
                        goodsB.getSku().get(0).getAttributeId(),"");
                EventBus.getDefault().post(cartItemLocB);
            }
        });

        specificationsDialog.setGoodsId(goodsId);
        specificationsDialog.setOnXXListener(new SpecificationsDialog.OnXXListener() {
            @Override
            public void selectedComplete(int spcid) {
                for (GoodsB.ReturnValueBean.SkuBean bean:goodsB.getSku()) {
                    if (bean.getAttributeId() == spcid){
                        tvPrice.setText("￥"+bean.getPrice());
                    }
                }
            }

            @Override
            public void onDismiss() {
                btnStatus();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (banner != null){
            banner.releaseBanner();
            banner.removeAllViews();
        }
        banner = null;
    }

    @Override
    public void initData() {

    }

    /**
     *
     * @param id
     * @param num
     */
    public void refreshNum(int id,int num){
        if (goodsId == id){
            countview.setNumber(num);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_goods_check:
                collect();
                break;
            case R.id.title_goods_more:
                showPopMore();
                break;
            case R.id.goods_deatils_specification:
                specificationsDialog.show(getChildFragmentManager(),"spec");
                break;
        }
    }
    public void getData(){
        AppNetModule.createrRetrofit()
                .GoodsDetalis(goodsId, MyApp.app.getUid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<GoodsB>() {
                    @Override
                    public void onSuccess(GoodsB result) {
                        goodsB = result.getReturnValue();
                        specificationsDialog.setGoodsId(goodsB.getId());
                        specificationsDialog.setGoodsB(goodsB);
                        attributesAdapter.getData().clear();
                        attributesAdapter.addData(goodsB.getCommoditySpecification());
                        attributesAdapter.notifyDataSetChanged();
                        tvTitle.setText(goodsB.getCommodityName());
                        tvName.setText(goodsB.getCommodityName());
                        tvContent.setText(goodsB.getCommoditySynopsis());
                        tvYue.setText("月销 "+goodsB.getMonthlySales());
                        tvContent.setText(goodsB.getCommoditySynopsis());
                        ivCollect.setImageResource(goodsB.getIsCollect() == 0?R.mipmap.ic_collect:R.mipmap.ic_collect_check);
                        if (goodsB.getSku() != null && goodsB.getSku().size() > 0){
                            tvPrice.setText("￥" + goodsB.getSku().get(0).getPrice());
                        }
                        if (goodsB.getIsType() == 0){
                            //默认单规格
                            countview.setVisibility(View.VISIBLE);
                            countview.setMaxNumber(goodsB.getSku().get(0).getInventoryNum());
                        }else {
                            ivSpc.setVisibility(View.VISIBLE);
                        }
                        btnStatus();
                        List<String> images = new ArrayList<>();
                        for (GoodsB.ReturnValueBean.CommodityPictureBean bean:result.getReturnValue().getCommodityPicture()) {
                            images.add(AppHttpPath.IMAGE + bean.getImgUrl());
                        }
                        banner.update(images);
                    }
                    @Override
                    public void onError(String msg) {
                        ToastUtils.toast(mContext,msg);
                    }
                });
    }

    /**
     * 收藏
     */
    public void collect(){
        if (goodsB == null || isCollect) return;
        isCollect = true;
        AppNetModule.createrRetrofit()
                .collectOperateOne(MyApp.app.getAccount(), MyApp.app.getUserToken(), MyApp.app.getUid(),
                        1,goodsB.getShopId(),goodsB.getId(),goodsB.getIsCollect(),goodsB.getIsCollect())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<IntBean>() {
                    @Override
                    public void onSuccess(IntBean result) {
                        //0：未收藏 1：已收藏
                        goodsB.setIsCollect(result.getReturnValue());
                        ivCollect.setImageResource(goodsB.getIsCollect() == 0?R.mipmap.ic_collect:R.mipmap.ic_collect_check);
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
            view.findViewById(R.id.report_shop).setVisibility(View.GONE);
            TextView textView = view.findViewById(R.id.share_shop);
            textView.setText("分享商品");
            textView.setOnClickListener(v -> {
                share();
            });
            //显示（自定义位置）
        }
        popupWindow.showAsDropDown(ivMore, -DpTools.px2dip(mContext,40),0);//显示在控件下面
    }
    public void share(){
        if (goodsB.getWebUrl() != null && !goodsB.getWebUrl().isEmpty()){
            ShareUtils.shareForMob(mContext,
                    AppUtils.getAppName(),
                    goodsB.getWebUrl(),
                    goodsB.getCommodityName(),
                    AppHttpPath.IMAGE + goodsB.getCommodityPicture().get(0).getImgUrl());
        }
    }

    //是否只有默认规格
    int cartCount;
    public void btnStatus(){
        //
        cartCount = 0;
        for (CartItemLocB b: MyApp.app.getCartBeansForShop(goodsB.getShopId())) {
            if (b.getGoodsId() == goodsId){
                cartCount += b.getNum();
            }
        }
        countview.setNumber(cartCount);
    }
}
