package com.juntai.shop.mall.ui.my.adt;

import android.content.Intent;
import android.widget.RatingBar;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.mall.base.utils.ImageLoadUtil;
import com.juntai.mall.video.img.ImageZoomActivity;
import com.juntai.mall.video.player.VideoNetPlayerActivity;
import com.juntai.shop.mall.AppHttpPath;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.MyCommentB;
import com.juntai.shop.mall.utils.StringTools;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的评价
 */
public class MyAssessAdapter extends BaseQuickAdapter<MyCommentB.ReturnValueBean.DatasBean, BaseViewHolder> {
    int imagepos;
    public MyAssessAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyCommentB.ReturnValueBean.DatasBean item) {
        helper.addOnClickListener(R.id.item_myassess_more);
        helper.addOnClickListener(R.id.item_myassess_shop_layout);

        ImageLoadUtil.loadCircularImage(mContext, AppHttpPath.IMAGE + item.getHeadUrl(),R.mipmap.ic_launcher,R.mipmap.ic_launcher,helper.getView(R.id.item_myassess_head));
        ImageLoadUtil.loadImageNoCrash(mContext, StringTools.getImageForCrmInt(item.getLogoId()),R.mipmap.ic_launcher,helper.getView(R.id.item_myassess_shop_logo));
        helper.setText(R.id.item_myassess_name,item.getNickName());
        helper.setText(R.id.item_myassess_time,item.getGmtCreate().substring(0,9));
        helper.setText(R.id.item_myassess_rj,item.getPerCaPita()+"/人");
        helper.setText(R.id.item_myassess_content,item.getEvaluate());
        helper.setText(R.id.item_myassess_shop_name,item.getShopName());
        helper.setText(R.id.item_myassess_scannum,"浏览" + item.getBrowseNum());
        helper.setText(R.id.item_myassess_sjhf_content,item.getReplyEvaluate());

        RatingBar ratingBar = helper.getView(R.id.item_myassess_ratingBar);
        ratingBar.setRating(item.getDegreeOfSatisfaction());

        RecyclerView recyclerView = helper.getView(R.id.item_myassess_images);
        ItemImagesAdapter imagesAdapter = new ItemImagesAdapter(R.layout.item_comments_images,new ArrayList());
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,3));
        recyclerView.setAdapter(imagesAdapter);
        if (item.getVideoUrl() != null && !item.getVideoUrl().isEmpty()){
            imagesAdapter.addData(AppHttpPath.IMAGE + item.getVideoUrl());
        }
        for (MyCommentB.ReturnValueBean.DatasBean.EvaluateImgBean b:item.getEvaluateImg()) {
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