package com.juntai.shop.mall.ui.adapter;


import android.widget.RatingBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.mall.base.utils.ImageLoadUtil;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.ShopLocationB;
import com.juntai.shop.mall.utils.StringTools;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 客户分布
 */
public class MapDateAdapter extends BaseQuickAdapter<ShopLocationB.ReturnValueBean, BaseViewHolder> {
    public MapDateAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopLocationB.ReturnValueBean item) {
        helper.addOnClickListener(R.id.item_dialog_map_navigation_tv);
        helper.setText(R.id.item_dialog_map_name,item.getShopName());
        DecimalFormat format = new DecimalFormat("##0.0");
        helper.setText(R.id.item_dialog_map_distance,format.format(item.getDistance()) + "km");
        helper.setText(R.id.item_dialog_map_address,"地址：" + item.getAddress());
        RatingBar ratingBar = helper.getView(R.id.item_dialog_map_ratingBar);
        ratingBar.setRating(item.getDegreeOfSatisfaction());
        ImageLoadUtil.loadImageNoCrashRound(mContext,20,
                StringTools.getImageForCrmInt(item.getLogoId()),
                R.mipmap.ic_launcher,
                helper.getView(R.id.item_dialog_map_image));
    }
}