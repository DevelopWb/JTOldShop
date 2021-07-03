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
public class ClassftLeftAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    ArrayList<TextView> textViews = new ArrayList<>();
    public ClassftLeftAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.item_classft_left, item);
        textViews.add(helper.getView(R.id.item_classft_left));
//        helper.setImageResource(R.id.icon, item.getImageResource());
//        // 加载网络图片
//        Glide.with(mContext).load(item.getUserAvatar()).crossFade().into((ImageView) helper.getView(R.id.iv));
    }

    public ArrayList<TextView> getTextViews() {
        return textViews;
    }
}