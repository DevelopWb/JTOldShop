package com.juntai.shop.mall.ui.goods.adt;

import android.content.Intent;
import android.view.View;
import android.widget.RatingBar;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.mall.base.utils.ImageLoadUtil;
import com.juntai.mall.video.img.ImageZoomActivity;
import com.juntai.mall.video.player.VideoNetPlayerActivity;
import com.juntai.shop.mall.App;
import com.juntai.shop.mall.AppHttpPath;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.MyCommentB;
import com.juntai.shop.mall.bean.ShopCommentsBean;
import com.juntai.shop.mall.ui.my.adt.ItemImagesAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 评论适配器
 */
public class CommentsAdapter extends BaseQuickAdapter<ShopCommentsBean.ReturnValueBean.DatasBean, BaseViewHolder> {
    int imagepos;
    public CommentsAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopCommentsBean.ReturnValueBean.DatasBean item) {
        helper.setText(R.id.item_shop_comment_name,item.getNickName());
        helper.setText(R.id.item_shop_comment_content,item.getEvaluate());
        helper.setText(R.id.item_shop_comment_time,item.getGmtCreate());
        RatingBar ratingBar = helper.getView(R.id.item_shop_comment_score);
        ratingBar.setRating(item.getDegreeOfSatisfaction());
        //
        ImageLoadUtil.loadImage(mContext,AppHttpPath.IMAGE + item.getHeadUrl(),R.mipmap.ic_launcher,R.mipmap.ic_launcher,helper.getView(R.id.item_shop_comment_image));

        RecyclerView recyclerView = helper.getView(R.id.item_comment_gridview);
        ItemImagesAdapter imagesAdapter = new ItemImagesAdapter(R.layout.item_comments_images,new ArrayList());
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,3));
        recyclerView.setAdapter(imagesAdapter);
        if (item.getVideoUrl() != null && !item.getVideoUrl().isEmpty()){
            imagesAdapter.addData(AppHttpPath.IMAGE + item.getVideoUrl());
        }
        for (ShopCommentsBean.ReturnValueBean.DatasBean.EvaluateImgListBean b:item.getEvaluateImgList()) {
            imagesAdapter.addData(AppHttpPath.IMAGE + b.getImgUrl());
        }
        imagesAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (imagesAdapter.getData().get(position).indexOf(".mp4") != -1){
                mContext.startActivity(new Intent(mContext, VideoNetPlayerActivity.class)
                        .putExtra("path",imagesAdapter.getData().get(position)));
            }else {
                imagepos = position;
                if (imagesAdapter.getData().get(0).indexOf(".mp4") != -1) {
                    imagepos = position - 1;
                }
                mContext.startActivity(new Intent(mContext, ImageZoomActivity.class)
                        .putStringArrayListExtra("paths", (ArrayList<String>) imagesAdapter.getData())
                        .putExtra("item",imagepos));
            }
        });
    }
}