package com.juntai.mall.base.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.juntai.mall.base.R;


/**
 * 图片加载工具
 *
 * @aouther Ma
 * @date 2019/3/5
 */
public class ImageLoadUtil {

    /**
     * 加载本地图片
     *
     * @param context
     * @param recouse
     * @param view
     */
    public static void loadImage(Context context, int recouse, ImageView view) {
        Glide.with(context)
                .load(recouse)
                .into(view);
    }

    /**
     * 加载图片
     */
    public static void loadImage(Context context, Bitmap bitmap, ImageView view) {
        Glide.with(context)
                .load(bitmap)
                .into(view);
    }

    /**
     * @param context
     * @param url
     * @param view
     */
    public static void loadImage(Context context, String url, ImageView view) {
        Glide.with(context)
                .load(url)
                .error(R.drawable.nopicture)
                .into(view);
    }

    /**
     * 地图部分用加载图片
     *
     * @param context
     * @param url
     * @param view
     */
    public static void loadMapImg(Context context, String url, ImageView view) {
        Glide.with(context)
                .load(url)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.drawable.nopicture)
                .into(view);
    }




    /**
     * @param context
     * @param url
     * @param view
     */
    public static void loadImageNoCrash(Context context, String url, ImageView view) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true))
                .into(view);
    }

    /**
     * 加载圆角
     * @param context
     * @param url
     * @param placeholder
     * @param view
     */
    public static void loadImageNoCrashRound(Context context,int radius,String url,  int placeholder,ImageView view) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions()
                        .transform(new CenterCrop(),new RoundedCorners(radius))
                        .placeholder(placeholder))
                .into(view);
    }

    /**
     * @param context
     * @param url
     * @param view
     */
    public static void loadImageNoCrash(Context context, String url,  int placeholder,ImageView view) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().placeholder(placeholder).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true))
                .into(view);
    }

    /**
     * @param context
     * @param url
     * @param placeholder
     * @param view
     */
    public static void loadImage(Context context, String url, int placeholder, ImageView view) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().placeholder(placeholder))
                .into(view);
    }

    /**
     * @param context
     * @param url
     * @param error
     * @param placeholder
     * @param view
     */
    public static void loadImage(Context context, String url, int error, int placeholder, ImageView view) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().error(error).placeholder(placeholder))
                .into(view);
    }

    /**
     * 加载圆形图片
     *
     * @param context
     * @param url
     * @param error
     * @param placeholder
     * @param view
     */
    public static void loadCircularImage(Context context, String url, int error, int placeholder, ImageView view) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().error(error).placeholder(placeholder).circleCrop())
                .into(view);
    }

    /**
     * 无缓存
     * @param context
     * @param url
     * @param error
     * @param placeholder
     * @param view
     */
    public static void loadCircularImageNoCache(Context context, String url, int error, int placeholder, ImageView view) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().error(error).placeholder(placeholder).circleCrop().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true))
                .into(view);
    }

    public static void loadCircularImage(Context context, int url, int error, int placeholder, ImageView view) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().error(error).placeholder(placeholder).circleCrop().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true))
                .into(view);
    }
}
