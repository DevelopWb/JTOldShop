package com.juntai.shop.mall.ui.act;

import android.view.View;

import com.juntai.shop.mall.R;
import com.juntai.mall.base.base.BaseActivity;

/**
 * 图片查看
 */
public class ImageToViewActivity extends BaseActivity {
    @Override
    public int getLayoutView() {
        return R.layout.activity_imagetoview;
    }

    @Override
    public void initView() {
        getToolbar().setVisibility(View.GONE);
        findViewById(R.id.imagetoview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void initData() {

    }
}
