package com.juntai.mall.base.widght;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.mall.base.R;

import java.util.List;

/**
 * 文字列表 （左右）
 * @aouther Ma
 * @date 2019/3/17
 */
public class TextCenterAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public TextCenterAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.item_text_list_center,item);
        //默认白色无圆角
        helper.getView(R.id.item_text_list_top).setVisibility(View.GONE);

        helper.getView(R.id.item_text_list_bottom).setVisibility(View.GONE);
        helper.getView(R.id.item_text_list_line1).setVisibility(View.VISIBLE);
        helper.getView(R.id.item_text_list_line2).setVisibility(View.VISIBLE);

        if(helper.getAdapterPosition() == 0 && (helper.getAdapterPosition() == (getData().size() - 2))){
            //只有2个的时候第一个
            helper.getView(R.id.item_text_list_line1).setVisibility(View.GONE);
            helper.getView(R.id.item_text_list_line2).setVisibility(View.GONE);
            helper.getView(R.id.item_text_list_top).setVisibility(View.VISIBLE);
            helper.getView(R.id.item_text_list_bottom).setVisibility(View.VISIBLE);
            helper.getView(R.id.item_text_list_center).setBackgroundResource(R.drawable.bg_radius_pre_white);
        }else if(helper.getAdapterPosition() == 0){
            //第一个顶部圆角
            helper.getView(R.id.item_text_list_line1).setVisibility(View.GONE);
            helper.getView(R.id.item_text_list_line2).setVisibility(View.GONE);
            helper.getView(R.id.item_text_list_center).setBackgroundResource(R.drawable.bg_radius_pre_white_top);
        }else if (helper.getAdapterPosition() == (getData().size() - 2)){
            //倒数第二个，底部圆角
            helper.getView(R.id.item_text_list_center).setBackgroundResource(R.drawable.bg_radius_pre_white_bottom);
        }else if (helper.getAdapterPosition() == (getData().size() - 1)){
            //最后一个(类似"取消"),四个圆角
            helper.getView(R.id.item_text_list_line1).setVisibility(View.GONE);
            helper.getView(R.id.item_text_list_line2).setVisibility(View.GONE);
            helper.getView(R.id.item_text_list_top).setVisibility(View.VISIBLE);
            helper.getView(R.id.item_text_list_bottom).setVisibility(View.VISIBLE);
            helper.getView(R.id.item_text_list_center).setBackgroundResource(R.drawable.bg_radius_pre_white);
        }else {
            //其他白色无圆角
            helper.getView(R.id.item_text_list_center).setBackgroundResource(R.color.white);
        }
    }
}