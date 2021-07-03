package com.juntai.mall.bdmap.act;

import android.view.View;

import com.baidu.lbsapi.BMapManager;
import com.baidu.lbsapi.MKGeneralListener;
import com.baidu.lbsapi.panoramaview.PanoramaView;
import com.baidu.lbsapi.panoramaview.PanoramaViewListener;
import com.juntai.mall.base.base.BaseActivity;
import com.juntai.mall.bdmap.R;

/**
 * 全景图
 * Created by Ma
 * on 2019/11/16
 */
public class PanoramaActivity extends BaseActivity {
    PanoramaView mPanoView;
    BMapManager mBMapManager;
    double lat = 0.0,lon = 0.0;
    @Override
    public int getLayoutView() {
        if (mBMapManager == null) {
            mBMapManager = new BMapManager(getApplication());

            mBMapManager.init(new MKGeneralListener() {
                @Override
                public void onGetPermissionState(int i) {

                }
            });
        }
        return R.layout.activity_panorama;
    }

    @Override
    public void initView() {
        getToolbar().setVisibility(View.GONE);
        mPanoView = findViewById(R.id.panorama);

        //设置全景图片的显示级别
        //根据枚举类ImageDefinition来设置清晰级别
        //较低清晰度 ImageDefinationLow
        //中等清晰度 ImageDefinationMiddle
        //较高清晰度 ImageDefinationHigh
        mPanoView.setPanoramaImageLevel(PanoramaView.ImageDefinition.ImageDefinitionLow);
        findViewById(R.id.panorama_high).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPanoView.setPanoramaImageLevel(PanoramaView.ImageDefinition.ImageDefinitionHigh);
            }
        });
        findViewById(R.id.panorama_middle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPanoView.setPanoramaImageLevel(PanoramaView.ImageDefinition.ImageDefinitionMiddle);
            }
        });
        findViewById(R.id.panorama_low).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPanoView.setPanoramaImageLevel(PanoramaView.ImageDefinition.ImageDefinitionLow);
            }
        });

        lat = getIntent().getDoubleExtra("lat",0.0);
        lon = getIntent().getDoubleExtra("lon",0.0);
        mPanoView.setPanorama(lon,lat);
        mPanoView.setPanoramaViewListener(new PanoramaViewListener() {
            @Override
            public void onDescriptionLoadEnd(String s) {

            }

            @Override
            public void onLoadPanoramaBegin() {

            }

            @Override
            public void onLoadPanoramaEnd(String s) {

            }

            @Override
            public void onLoadPanoramaError(String s) {

            }

            @Override
            public void onMessage(String s, int i) {

            }

            @Override
            public void onCustomMarkerClick(String s) {

            }

            @Override
            public void onMoveStart() {

            }

            @Override
            public void onMoveEnd() {

            }
        });
    }

    @Override
    public void initData() {

    }
    @Override
    protected void onPause() {
        super.onPause();
        mPanoView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPanoView.onResume();
    }

    @Override
    protected void onDestroy() {
        mPanoView.destroy();
        super.onDestroy();
    }
}
