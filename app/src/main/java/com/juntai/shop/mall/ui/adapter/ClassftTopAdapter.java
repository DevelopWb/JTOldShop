package com.juntai.shop.mall.ui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.shop.mall.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 商城-分类左侧菜单适配器
 */
public class ClassftTopAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    ArrayList<TextView> textViews = new ArrayList<>();
    public ClassftTopAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.item_classft_left, item);
        textViews.add(helper.getView(R.id.item_classft_left));
    }

    public ArrayList<TextView> getTextViews() {
        return textViews;
    }
}