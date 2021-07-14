package com.juntai.shop.mall.mine.set.address.selector;

import android.graphics.Color;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.PlaceBean;

import java.util.List;

/**
 * @aouther Ma
 * @date 2019/3/10
 */
public class AddressSelectorAdapter extends BaseQuickAdapter<PlaceBean.ReturnValueBean, BaseViewHolder> {
    int selector = 0;
    String pinyin = "0";
    int color = -1;
    //MallHomeB
    public AddressSelectorAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PlaceBean.ReturnValueBean item) {
        if (color == -1) {
            color = mContext.getResources().getColor(R.color.colorTheme);
        }
        if (selector == helper.getAdapterPosition()){
            helper.setBackgroundRes(R.id.item_address_layout, R.color.white);
            helper.setTextColor(R.id.item_address_selector, color);
            helper.setTextColor(R.id.item_address_pinyin, color);
            helper.getView(R.id.item_address_image).setVisibility(View.VISIBLE);
        }else {
            helper.setBackgroundRes(R.id.item_address_layout, R.color.background);
            helper.setTextColor(R.id.item_address_selector, Color.BLACK);
            helper.setTextColor(R.id.item_address_pinyin, Color.BLACK);
            helper.getView(R.id.item_address_image).setVisibility(View.GONE);
        }

        helper.setText(R.id.item_address_pinyin, item.getPinYin());
        if (pinyin.equals(item.getPinYin())){
            helper.setText(R.id.item_address_pinyin,"");
        }
        helper.setText(R.id.item_address_selector, item.getName());
        pinyin = item.getPinYin();
    }

    /**
     *
     * @param position
     */
    public void selectorPosition(int position){
        selector = position;
    }
}