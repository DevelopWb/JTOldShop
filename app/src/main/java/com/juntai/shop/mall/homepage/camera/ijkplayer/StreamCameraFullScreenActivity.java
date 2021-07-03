package com.juntai.shop.mall.homepage.camera.ijkplayer;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.dou361.ijkplayer.listener.OnShowThumbnailListener;
import com.dou361.ijkplayer.utils.MediaUtils;
import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;
import com.juntai.mall.base.base.BaseMvpActivity;
import com.juntai.mall.base.utils.ImageLoadUtil;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.homepage.camera.PlayContract;
import com.juntai.shop.mall.homepage.camera.PlayPresent;


/**
 * @aouther tobato  流摄像头 全屏播放
 * @description 描述
 * @date 2020/6/1 16:49
 */

public class StreamCameraFullScreenActivity extends BaseMvpActivity<PlayPresent> implements PlayContract.IPlayView {
    private PlayerView player;
    //    private String url = "rtmp://juntaikeji.net:1935/video/37130201561327011011";
    private PowerManager.WakeLock wakeLock;
    public static String STREAM_CAMERA_URL = "stream_num_rul";
    public static String STREAM_CAMERA_TITLE = "stream_num_rul_title";
    private String url,mCameraNum;
    private String mThumIv;

    @Override
    protected PlayPresent createPresenter() {
        return null;
    }

    @Override
    public int getLayoutView() {
        return R.layout.simple_player_view_player_full;
    }

    @Override
    public void initView() {
        url = getIntent().getStringExtra(STREAM_CAMERA_URL);
        mThumIv = getIntent().getStringExtra(PlayerLiveActivity.STREAM_CAMERA_THUM_URL);
        mCameraNum = getIntent().getStringExtra(PlayerLiveActivity.STREAM_CAMERA_NUM);
        //        setTitleName(getIntent().getStringExtra(STREAM_CAMERA_TITLE));
        getToolbar().setVisibility(View.GONE);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE); //去除这个Activity的标题栏
        super.onCreate(savedInstanceState);
        /**常亮*/
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "liveTAG");
        wakeLock.acquire();

        player = new PlayerView(this, mBaseRootCol)
                .setTitle("")
                //分辨率
                .hideSteam(true)
                //旋转
                .hideRotation(true)
                //底部功能键
                .hideControlPanl(false)
                .setOnlyFullScreen(true)
                .setScaleType(PlayStateParams.wrapcontent).showThumbnail(new OnShowThumbnailListener() {
                    @Override
                    public void onShowThumbnail(ImageView ivThumbnail) {
                        ImageLoadUtil.loadImageNoCrash(mContext.getApplicationContext(),
                                mThumIv,ivThumbnail);
                    }
                });
        if (!TextUtils.isEmpty(url)) {
            player.setPlaySource(url).startPlay();
        }
        player.hideHideTopBar(false)
                .forbidTouch(false)
                .setForbidHideControlPanl(false)
                .hideMenu(true)
        ;
    }

    @Override
    public void onSuccess(String tag, Object o) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
        MediaUtils.muteAudioFocus(mContext, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
        MediaUtils.muteAudioFocus(mContext, false);
        if (wakeLock != null) {
            wakeLock.acquire();
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onBackPressed() {
        if (wakeLock != null) {
            wakeLock.release();
        }
        if (player != null && player.onBackPressed()) {
            return;
        }
        super.onBackPressed();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }

    }

}
