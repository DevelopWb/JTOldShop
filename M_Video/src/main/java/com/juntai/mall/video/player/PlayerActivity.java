package com.juntai.mall.video.player;

import android.net.Uri;
import android.os.Handler;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.juntai.mall.base.base.BaseActivity;
import com.juntai.mall.base.net.FileRetrofit;
import com.juntai.mall.base.utils.BaseAppUtils;
import com.juntai.mall.base.utils.LogUtil;
import com.juntai.mall.video.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * @aouther Ma
 * @date 2019/4/1
 *
 * 视频播放
 *         Intent intent = new Intent(mContext, PlayerActivity.class);
 *         String ss = "http://kb167.cn:43212/zhcg/u/appInferFace/appDownloadVideo.shtml?token="
 *                 +App.getUserToken()+"&account="
 *                 +App.getAccount()+"&id=1628";
 *         intent.putExtra("playPath",ss);
 *         intent.putExtra("savePath", FileCacheUtils.getAppVideoPath()+1628+".mp4");
 *         startActivity(intent);
 */

public class PlayerActivity extends BaseActivity {
    private String playPath,savePath,locPath;
    private PlayerView playerview;
    private File file;
    SimpleExoPlayer player;
    @Override
    public int getLayoutView() {
        return R.layout.activity_player;
    }

    @Override
    public void initView() {
        setTitleName("视频");
        //下载视频到本地播放
        playPath = getIntent().getStringExtra("playPath");
        savePath = getIntent().getStringExtra("savePath");
        //不需要请求网络视频流
        locPath = getIntent().getStringExtra("locPath");
        playerview = findViewById(R.id.playerview);
        //LogUtil.d("案件视频播放地址 =  " + playPath);
        //LogUtil.d("案件视频保存地址 =  " + savePath);
    }

    @Override
    public void initData() {
        if (locPath == null || locPath.equals("")){
            file = new File(savePath);
            getVideo();
        }else {
            file = new File(locPath);
            playVideo();
        }
    }
    public void getVideo(){
        FileRetrofit.getInstance().getFileService()
                .getFile_POST(playPath)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        try {
                            //responseBody里的数据只可以读取一次
                            //Log.e("ffffffff", "" + responseBody.bytes().length);
                            if (!file.exists()){
                                saveVideo(responseBody.byteStream());
                            }
                            playVideo();
//                            startActivity(new Intent(mContext, VideoPlayerActivity.class).putExtra("path",savePath));
                        } catch (Exception e) {
                            e.printStackTrace();
                            LogUtil.e( e.toString());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtil.e(throwable.toString());
                    }
                });
    }
    /**
     * 播放视频
     */
    public void playVideo(){
        //1.创建默认的 TrackSelector
        Handler mainHandler = new Handler();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);
        //2.创建播放器
        player =
                ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);

        // 将播放器附加到view
        playerview.setPlayer(player);

        /**准备播放*/
        // 在播放期间测量带宽。 如果不需要，可以为null
        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
        // 生成用于加载媒体数据的 DataSource 实例
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(mContext,
                Util.getUserAgent(mContext, BaseAppUtils.getAppName()), defaultBandwidthMeter);
        // 这是要播放媒体的MediaSource
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.fromFile(file));
        // 准备播放器的资源
        player.prepare(videoSource);
        player.setPlayWhenReady(true);
    }

    /**
     * 缓存视频到本地
     * @param ins
     */
    public void saveVideo(InputStream ins) {
        try {
            LogUtil.d("----->in");
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            LogUtil.d("----->ok");
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("----->error-"+e.toString());
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (player != null){
            player.stop();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (player != null) {
            player.release();
        }
    }
}
