package com.juntai.shop.mall.ui.adapter;

import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.mall.base.utils.ImageLoadUtil;
import com.juntai.shop.mall.AppHttpPath;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.CartItemLocB;
import com.juntai.shop.mall.bean.OrderCommodityListBean;

import java.util.List;

/**
 * 确认订单-商品
 * @aouther Ma
 * @date 2019/3/5
 */
public class OrderConfirmGoodsAdapter extends BaseQuickAdapter<OrderCommodityListBean, BaseViewHolder> {
    public OrderConfirmGoodsAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderCommodityListBean item) {
        ImageLoadUtil.loadImageNoCrash(mContext, AppHttpPath.IMAGE + item.getCommodityImg(),R.mipmap.ic_launcher,helper.getView(R.id.item_order_goodsimage));
        helper.setText(R.id.item_order_goodsname,item.getCommodityName());
        helper.setText(R.id.item_order_specification,item.getParameterName());
        helper.setText(R.id.item_order_count,"x"+item.getCommodityNumber());
        helper.setText(R.id.item_order_goods_price,String.format("￥%s",item.getPrice()));
        helper.setText(R.id.item_order_pack_price,String.format("￥%s",item.getPackingCharges()));
    }
}