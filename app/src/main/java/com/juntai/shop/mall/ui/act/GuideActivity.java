package com.juntai.shop.mall.ui.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.gyf.barlibrary.ImmersionBar;
import com.juntai.mall.base.utils.SPTools;
import com.juntai.shop.mall.MainActivity;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.ui.adapter.GuideAdapter;

import java.util.ArrayList;

/**
 * 引导页
 * Created by Ma
 * on 2019/7/10
 */
public class GuideActivity extends AppCompatActivity {
    private ViewPager vp;
    private LinearLayout llPoints;
    private Button btnExperienceNow;
    //
    private int lastPointIndex = 0,pointViewSize = 30;
    //
    int[] imgs = new int[]{R.mipmap.bg_guide1, R.mipmap.bg_guide2,R.mipmap.bg_guide3};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImmersionBar.with(this).init();
        setContentView(R.layout.activity_guide);
        //
        vp = findViewById(R.id.guideActivity_vp);
        llPoints = findViewById(R.id.guideActivity_ll_points);
        btnExperienceNow = findViewById(R.id.guideActivity_bt_experience_now);
        //
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                if (i == (imgs.length -1)){
                    btnExperienceNow.setVisibility(View.VISIBLE);
                }else {
                    btnExperienceNow.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

        btnExperienceNow.setOnClickListener(v -> {
            SPTools.saveBoolean(GuideActivity.this,"first_start",false);
            startActivity(new Intent(GuideActivity.this, MainActivity.class));
            finish();
        });
        initData();

    }
    private void initData() {

        ArrayList<ImageView> imageViews = new ArrayList<>();
        ImageView imageView;
        for (int i = 0; i < imgs.length; i++) {

            //添加图片
            imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(imgs[i]);

            imageViews.add(imageView);

        }
        GuideAdapter guideAdapter = new GuideAdapter(imageViews);
        vp.setAdapter(guideAdapter);
    }
}
