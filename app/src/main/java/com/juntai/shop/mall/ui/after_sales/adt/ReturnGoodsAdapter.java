package com.juntai.shop.mall.ui.after_sales.adt;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.mall.base.utils.ImageLoadUtil;
import com.juntai.shop.mall.AppHttpPath;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.OrderCommodityListBean;
import com.juntai.shop.mall.utils.Arith;

import java.util.List;

/**
 * 售后商品-展示
 *
 */
public class ReturnGoodsAdapter extends BaseQuickAdapter<OrderCommodityListBean, BaseViewHolder> {
    public ReturnGoodsAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderCommodityListBean item) {
        helper.setText(R.id.item_return_goods_name,item.getCommodityName());
        helper.setText(R.id.item_return_goods_price,"￥" + item.getPrice());
        helper.setText(R.id.item_return_goods_num,"x" + item.getCommodityNumber());
        helper.setText(R.id.item_return_goods_spec,item.getParameterName());
        if (item.getParameterName() == null){
            helper.setText(R.id.item_return_goods_spec,"");
        }
        helper.setText(R.id.item_return_goods_pack,String.format("包装费￥%s元",item.getPrice() * item.getPackingCharges()));
        helper.setText(R.id.item_return_goods_price_all,String.format("总金额￥%s元", Arith.mul(item.getPrice() , item.getCommodityNumber())));
        ImageLoadUtil.loadImageNoCrash(mContext, AppHttpPath.IMAGE + item.getCommodityImg(),R.mipmap.ic_launcher,helper.getView(R.id.item_return_goods_image));
    }
}