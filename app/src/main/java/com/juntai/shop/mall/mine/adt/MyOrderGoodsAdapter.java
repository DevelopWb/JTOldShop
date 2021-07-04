package com.juntai.shop.mall.mine.adt;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.mall.base.utils.ImageLoadUtil;
import com.juntai.shop.mall.AppHttpPath;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.OrderCommodityListBean;

import java.util.List;

/**
 * 我的订单-商品
 *
 */
public class MyOrderGoodsAdapter extends BaseQuickAdapter<OrderCommodityListBean, BaseViewHolder> {
    public MyOrderGoodsAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderCommodityListBean item) {
        helper.setText(R.id.item_myorder_goods_name,item.getCommodityName());
        helper.setText(R.id.item_myorder_goods_price,"￥" + item.getPrice());
        helper.setText(R.id.item_myorder_goods_num,"x" + item.getCommodityNumber());
        helper.setText(R.id.item_myorder_goods_spec,"规格：" + item.getParameterName());
        if (item.getParameterName() == null){
            helper.setText(R.id.item_myorder_goods_spec,"");
        }
        ImageLoadUtil.loadImageNoCrash(mContext, AppHttpPath.IMAGE + item.getCommodityImg(),R.mipmap.ic_launcher,helper.getView(R.id.item_myorder_goods_image));
    }
}