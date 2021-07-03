package com.juntai.shop.mall.ui.goods.fmt;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.juntai.mall.base.base.BaseLazyFragment;
import com.juntai.mall.base.base.BaseObserver;
import com.juntai.mall.base.utils.ImageLoadUtil;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.mall.video.img.ImageZoomActivity;
import com.juntai.shop.mall.App;
import com.juntai.shop.mall.AppNetModule;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.ShopBean;
import com.juntai.shop.mall.bean.ShopInfoBean;
import com.juntai.shop.mall.ui.goods.ShopActivity;
import com.juntai.shop.mall.ui.goods.adt.CommentsAdapter;
import com.juntai.shop.mall.utils.AppUtils;
import com.juntai.shop.mall.utils.StringTools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 店铺评论
 * Created by Ma
 * on 2019/9/2
 */
public class ShopInfoFragment extends BaseLazyFragment implements View.OnClickListener {
    TextView tvPlace,tvJj;
    ImageView ivCall,iv1,iv2;
    String phone,image1,image2;


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_shopinfo;
    }

    @Override
    protected void initView() {
        tvPlace = getView(R.id.shop_info_palce);
        tvJj = getView(R.id.shop_info_jj);
        ivCall = getView(R.id.shop_info_call);
        iv1 = getView(R.id.shop_info_image1);
        iv2 = getView(R.id.shop_info_image2);
        ivCall.setOnClickListener(this);
        iv1.setOnClickListener(this);
        iv2.setOnClickListener(this);
        //
    }

    @Override
    protected void initData() {

    }
    @Override
    protected void lazyLoad() {
        AppNetModule.createrRetrofit()
                .shopInfo(App.app.getAccount(), App.app.getUserToken(), ShopActivity.shopId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ShopInfoBean>(null) {
                    @Override
                    public void onSuccess(ShopInfoBean result) {
                        tvPlace.setText(result.getReturnValue().getRegion() + "\n" + result.getReturnValue().getAddress());
                        tvJj.setText(result.getReturnValue().getSynopsis());
                        phone = result.getReturnValue().getContactsPhone();
                        image1 = StringTools.getImageForCrmInt(result.getReturnValue().getBusinessImg());
                        image2 = StringTools.getImageForCrmInt(result.getReturnValue().getSanitationImg());
                        ImageLoadUtil.loadImage(mContext, image1,R.mipmap.ic_launcher,iv1);
                        ImageLoadUtil.loadImage(mContext, image2,R.mipmap.ic_launcher,iv2);
                    }
                    @Override
                    public void onError(String msg) {
                        ToastUtils.toast(mContext,msg);
                    }
                });
    }
    ArrayList<String> strings = new ArrayList<>();
    @Override
    public void onClick(View v) {
        strings.clear();
        strings.addAll(Arrays.asList(new String[]{image1,image2}));
        switch (v.getId()){
            case R.id.shop_info_call:
                if (phone != null)
                    AppUtils.callPhone(mContext,phone);
                break;
            case R.id.shop_info_image1:
                startActivity(new Intent(mContext, ImageZoomActivity.class)
                        .putStringArrayListExtra("paths", strings)
                        .putExtra("item",0));
                break;
            case R.id.shop_info_image2:
                startActivity(new Intent(mContext, ImageZoomActivity.class)
                        .putStringArrayListExtra("paths",strings)
                        .putExtra("item",1));
                break;
        }
    }
}
