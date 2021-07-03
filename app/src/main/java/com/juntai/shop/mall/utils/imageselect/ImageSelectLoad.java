package com.juntai.shop.mall.utils.imageselect;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dxngxhl.imageselection.ImageLoader;

public class ImageSelectLoad extends ImageLoader {
    @Override
    public void imageLoad(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }
}
