package com.juntai.shop.mall.homepage.camera.ijkplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.Nullable;

import com.dou361.ijkplayer.listener.OnShowThumbnailListener;
import com.dou361.ijkplayer.utils.MediaUtils;
import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;
import com.juntai.mall.base.base.BaseDownLoadActivity;
import com.juntai.mall.base.bean.OpenLiveBean;
import com.juntai.mall.base.utils.DisplayUtil;
import com.juntai.mall.base.utils.FileCacheUtils;
import com.juntai.mall.base.utils.ImageLoadUtil;
import com.juntai.mall.base.utils.ScreenUtils;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.baseinfo.BaseAppActivity;
import com.juntai.shop.mall.bean.stream.StreamCameraDetailBean;
import com.juntai.shop.mall.homepage.camera.PlayContract;
import com.juntai.shop.mall.homepage.camera.PlayPresent;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @aouther tobato
 * @description 描述   播放视频流
 * @date 2020/7/25 16:44
 */
public class PlayerLiveActivity extends BaseAppActivity<PlayPresent> implements PlayContract.IPlayView,
        View.OnClickListener, BaseDownLoadActivity.OnFileDownloaded {

    private PlayerView player;
    //    private String url = "rtmp://juntaikeji.net:1935/video/37130201561327011011";
    private PowerManager.WakeLock wakeLock;
    private Intent intent;
    public static String STREAM_CAMERA_ID = "stream";
    public static String STREAM_CAMERA_NUM = "stream_num";
    public static String STREAM_CAMERA_URL = "stream_url";
    public static String STREAM_CAMERA_THUM_URL = "stream_thum_url";//缩略图路径
    public static String STREAM_CAMERA_SESSION_ID = "sessionid";
    private int mCameraId;//
    private String mCameraNum;//
    private TextView tvstate;
    private StreamCameraDetailBean.ReturnValueBean mStreamCameraBean;//详情
    private TextView tvplace;
    private String playUrl;
    private String mThumUrl;


    @Override
    protected PlayPresent createPresenter() {
        return new PlayPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.simple_player_view_player;
    }

    @Override
    public void initView() {
        setFileDownLoadCallBack(this);
        getToolbar().setVisibility(View.GONE);
        mBaseRootCol.setFitsSystemWindows(false);
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = (ScreenUtils.getInstance(getApplicationContext()).getScreenWidth() / 10) * 9;
        wlp.height = DisplayUtil.dp2px(mContext,278);
        window.setAttributes(wlp);
        mCameraId = getIntent().getIntExtra(STREAM_CAMERA_ID, 0);
        mCameraNum = getIntent().getStringExtra(STREAM_CAMERA_NUM);
        mThumUrl = getIntent().getStringExtra(STREAM_CAMERA_THUM_URL);
        tvplace = findViewById(R.id.deviceplace);
        tvstate = findViewById(R.id.devicestate);
    }


    @Override
    public void initData() {

        //获取摄像头信息
        mPresenter.getStreamCameraDetail(mPresenter.getMultipartBody()
                        .addFormDataPart("cameraId", String.valueOf(mCameraId)).build(),
                PlayContract.GET_STREAM_CAMERA_DETAIL);
    }

    @SuppressLint("InvalidWakeLockTag")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE); //去除这个Activity的标题栏
        super.onCreate(savedInstanceState);

        /**常亮*/
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "liveTAG");
        wakeLock.acquire();

        player = new PlayerView(this, mBaseRootCol)
                .setTitle("")
                //隐藏顶部布局
                .hideHideTopBar(true)
                //分辨率
                .hideSteam(true)
                //旋转
                .hideRotation(true)
                //隐藏全屏按钮，true隐藏，false为显示
                .hideFullscreen(false)
                .hideCenterPlayer(false)
                .forbidTouch(false)
                .hideBottonBar(false)
                .setForbidDoulbeUp(true)
                .setScaleType(PlayStateParams.fitxy).showThumbnail(new OnShowThumbnailListener() {
                    @Override
                    public void onShowThumbnail(ImageView ivThumbnail) {
                        ImageLoadUtil.loadImageNoCrash(mContext.getApplicationContext(),
                                mThumUrl,ivThumbnail);
                    }
                });
        player.getBottonBarView().setVisibility(View.VISIBLE);
        player.getFullScreenView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(playUrl)) {
                    startActivity(new Intent(mContext, StreamCameraFullScreenActivity.class)
                            .putExtra(StreamCameraFullScreenActivity.STREAM_CAMERA_TITLE,
                                    mStreamCameraBean.getAddress() )
                            .putExtra(StreamCameraFullScreenActivity.STREAM_CAMERA_URL, playUrl)
                            .putExtra(PlayerLiveActivity.STREAM_CAMERA_THUM_URL, mThumUrl)
                    .putExtra(STREAM_CAMERA_NUM,mCameraNum));
                } else {
                    ToastUtils.toast(mContext, "无法获取流地址");
                }
            }
        });

        player.getCapturePicView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //截屏
                mPresenter.capturePic(mCameraNum, "1", PlayContract.GET_STREAM_CAMERA_CAPTURE);
            }
        });
        playUrl = getIntent().getStringExtra(STREAM_CAMERA_URL);
       String strsessionid = getIntent().getStringExtra(STREAM_CAMERA_SESSION_ID);
        if (playUrl !=null&&!TextUtils.isEmpty(playUrl)) {
            player.setPlaySource(playUrl).startPlay();
            tvstate.append("在线");
            //调用截图的接口  然后 上传封面图
//            mPresenter.capturePic(mCameraNum, "1", PlayContract.GET_STREAM_CAMERA_THUMBNAIL);
            intent = new Intent(this, KeepAliveService.class).putExtra("sessionId", strsessionid);
            startService(intent);
        }else{
            //打开流数据
            mPresenter.openStream(mCameraNum, "1", "rtmp", PlayContract.GET_URL_PATH);
        }

    }

    @Override
    protected String getTitleRightName() {
        return null;
    }

    @Override
    protected String getDownLoadPath() {
        return null;
    }

    @Override
    public void onError(String tag, Object o) {
        super.onError(tag, o);
    }


    @Override
    public void onClick(View v) {
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
    public void onSuccess(String tag, Object o) {


        switch (tag) {
            case PlayContract.GET_URL_PATH:
                OpenLiveBean openLiveBean = (OpenLiveBean) o;
                int errorCode = openLiveBean.getErrcode();
                if (errorCode < 0) {
                    tvstate.append("离线");
                    ToastUtils.toast(mContext, "设备离线，无数据");
                    return;
                } else {
                    tvstate.append("在线");
                    //调用截图的接口  然后 上传封面图
//                    mPresenter.capturePic(mCameraNum, "1", PlayContract.GET_STREAM_CAMERA_THUMBNAIL);
                }
                playUrl = openLiveBean.getVideourl();
                player.setPlaySource(playUrl).startPlay();
                intent = new Intent(this, KeepAliveService.class).putExtra("sessionId", openLiveBean.getStrsessionid());
                startService(intent);
                break;
            case PlayContract.GET_STREAM_CAMERA_THUMBNAIL:
                //截图  每次打开 更新缩略图
                OpenLiveBean captureBean = (OpenLiveBean) o;
                int errCode = captureBean.getErrcode();
                if (errCode < 0) {
                    //截图失败
                } else {
                    //截图成功 上传封面图（将图片路径上传后台）  http://60.213.43.241:8080/image/37131201561327001001.jpg
                    String imagePath = captureBean.getImageurl();
                    if (!TextUtils.isEmpty(imagePath)) {
                        if (imagePath.contains("image")) {
                            //截图成功 保存本地
                            downloadFileContentUnique(FileCacheUtils.STREAM_THUMBNAIL, imagePath);
                        }
                    }
                }
                break;
            case PlayContract.GET_STREAM_CAMERA_CAPTURE:
                //截图并保存本地
                OpenLiveBean captureBeanToLocal = (OpenLiveBean) o;
                int errMsg = captureBeanToLocal.getErrcode();
                if (errMsg < 0) {
                    //截图失败
                    ToastUtils.toast(mContext, "截图失败");
                } else {
                    //截图成功 保存本地
                    String imagePath = captureBeanToLocal.getImageurl();
                    if (!TextUtils.isEmpty(imagePath)) {
                      downloadFileContent(FileCacheUtils.STREAM_CAPTURE+mStreamCameraBean.getAddress(), imagePath);
                    }
                }
                break;
            case PlayContract.UPLOAD_CAMERA_CAPTURE:
                //上传封面图
                break;
            case PlayContract.GET_STREAM_CAMERA_DETAIL:
                StreamCameraDetailBean detailBean = (StreamCameraDetailBean) o;
                if (detailBean != null) {
                    mStreamCameraBean = detailBean.getReturnValue();
                    tvplace.append(mStreamCameraBean.getAddress() );
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
        if (intent != null) {
            stopService(intent);
        }
        setFileDownLoadCallBack(null);
    }

    @Override
    public void onFileDownloaded(String filePath) {
        if (!filePath.contains(FileCacheUtils.STREAM_CAPTURE)) {
            String fileName = filePath.substring(filePath.lastIndexOf(
                    "/") + 1, filePath.lastIndexOf("."));
            //压缩图片作为缩略图
//            compressImage(filePath, FileCacheUtils.STREAM_THUMBNAIL,fileName, new OnImageCompressedPath() {
//                @Override
//                public void compressedImagePath(File file) {
//                    if (mPresenter == null) {
//                        return;
//                    }
//                    MultipartBody.Builder builder =   mPresenter.getPublishMultipartBody()
//                            .addFormDataPart("id",String.valueOf(mCameraId))
//                            .addFormDataPart("number",String.valueOf(mCameraNum))
//                            .addFormDataPart("file",fileName, RequestBody.create(MediaType.parse("file"),file));
//                    mPresenter.uploadStreamCameraThumbPic(builder.build(),PlayContract.UPLOAD_CAMERA_CAPTURE);
//                }
//            });
        }

    }
}
