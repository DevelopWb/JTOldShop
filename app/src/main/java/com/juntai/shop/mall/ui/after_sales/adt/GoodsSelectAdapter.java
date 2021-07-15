package com.juntai.shop.mall.ui.after_sales.adt;

import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.mall.base.utils.ImageLoadUtil;
import com.juntai.shop.mall.AppHttpPath;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.OrderCommodityListBean;

import java.util.List;

/**
 * 售后商品选择
 *
 */
public class GoodsSelectAdapter extends BaseQuickAdapter<OrderCommodityListBean, BaseViewHolder> {
    public GoodsSelectAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderCommodityListBean item) {
        Log.e("ffffffff",item.isChecked() + "");
        helper.setChecked(R.id.item_goods_select_check,item.isChecked());
        helper.setText(R.id.item_goods_select_name,item.getCommodityName());
        helper.setText(R.id.item_goods_select_price_tag_tv,"￥" + item.getPrice());
        helper.setText(R.id.item_goods_select_num,"X" + item.getCommodityNumber());
        ImageLoadUtil.loadImageNoCrash(mContext, AppHttpPath.IMAGE + item.getCommodityImg(),R.mipmap.ic_launcher,helper.getView(R.id.item_goods_select_image));
        helper.setOnCheckedChangeListener(R.id.item_goods_select_check,(buttonView, isChecked) -> {
            item.setChecked(isChecked);
        });
    }
}