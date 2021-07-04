package com.juntai.shop.mall.mine.adt;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.mall.base.utils.ImageLoadUtil;
import com.juntai.shop.mall.MyApp;
import com.juntai.shop.mall.R;
import com.shuyu.gsyvideoplayer.player.PlayerFactory;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.List;

import tv.danmaku.ijk.media.exo2.Exo2PlayerManager;

/**
 * Created by Ma
 * on 2019/11/29
 */
public class ItemImagesAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    StandardGSYVideoPlayer videoPlayer;
    public ItemImagesAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.addOnClickListener(R.id.item_client_images);
        ImageView imageView = helper.getView(R.id.item_client_images);
        imageView.getLayoutParams().width = MyApp.W / 4;
        imageView.getLayoutParams().height = MyApp.W / 4;
//        Glide.with(mContext).load(item).into(imageView);
        if (item.contains(".mp4")){
            videoPlayer = helper.getView(R.id.item_client_video);
            helper.getView(R.id.item_client_video).setVisibility(View.VISIBLE);
            startVideo(item);
            //加载视频第一帧
//            ImageLoadUtil.loadImage(mContext,getNetVideoBitmap(item),imageView);
//            imageView.setImageBitmap(getNetVideoBitmap("https://www.w3school.com.cn/example/html5/mov_bbb.mp4"));
//            imageView.setImageBitmap(getNetVideoBitmap(item));
        }else {
            ImageLoadUtil.loadImageNoCrash(mContext,item,imageView);
            helper.getView(R.id.item_client_video).setVisibility(View.GONE);
        }
    }
    private ImageView startVideo(String path) {
        //RTMP播放需切换至exo播放
        PlayerFactory.setPlayManager(Exo2PlayerManager.class);
        videoPlayer.setUp(path, false, "");
        videoPlayer.getLayoutParams().width = MyApp.W / 4;
        videoPlayer.getLayoutParams().height = MyApp.W / 4;
        //增加封面
        ImageView imageView = new ImageView(mContext);
        Glide.with(mContext).asBitmap().load(path).into(imageView);
        videoPlayer.setThumbImageView(imageView);
        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.GONE);
        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.GONE);
        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(false);
        //设置返回按键功能
        videoPlayer.getBackButton().setVisibility(View.GONE);
        return imageView;
    }
}
