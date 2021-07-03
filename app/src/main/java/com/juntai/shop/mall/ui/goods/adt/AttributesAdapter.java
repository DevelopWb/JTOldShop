package com.juntai.shop.mall.ui.goods.adt;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.GoodsB;

import java.util.List;

/**
 * 商品属性列表
 * @aouther Ma
 * @date 2019/3/5
 */
public class AttributesAdapter extends BaseQuickAdapter<GoodsB.ReturnValueBean.CommoditySpecificationBean, BaseViewHolder> {
    public AttributesAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsB.ReturnValueBean.CommoditySpecificationBean item) {
        helper.setText(R.id.item_goods_attributes_left,item.getSpecificationAttribute());
        helper.setText(R.id.item_goods_attributes_right,item.getSpecificationValue());
    }
}