package com.juntai.shop.mall.ui.goods.adt;


import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.mall.base.utils.ImageLoadUtil;
import com.juntai.shop.mall.App;
import com.juntai.shop.mall.R;

import java.util.List;

/**
 * Created by Ma
 * on 2019/7/20
 */

public class CommentImagesAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public CommentImagesAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView imageView = helper.getView(R.id.item_comment_images);
        imageView.getLayoutParams().width = App.W / 4;
        imageView.getLayoutParams().height = App.W / 4;
        ImageLoadUtil.loadImage(mContext,item,imageView);
    }
}
