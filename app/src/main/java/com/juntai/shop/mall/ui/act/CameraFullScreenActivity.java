package com.juntai.shop.mall.ui.act;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.juntai.mall.base.base.BaseActivity;
import com.juntai.mall.base.utils.FileCacheUtils;
import com.juntai.mall.base.utils.LogUtil;
import com.juntai.shop.mall.R;
import com.videogo.errorlayer.ErrorInfo;
import com.videogo.openapi.EZConstants;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.EZPlayer;

import java.util.Calendar;

public class CameraFullScreenActivity extends BaseActivity implements SurfaceHolder.Callback, Handler.Callback {
    SurfaceView mSurfaceView;
    EZPlayer player;
    RelativeLayout mSVControllor;
    LinearLayout cameraController;
    String newCoverPath;
    Boolean isRecording = false;
    Boolean isSavingTheStream = true;
    saveTheDataThread saveTheDataThread;
    Handler handler;
    String cameraNum;

    @Override
    public int getLayoutView() {
        return R.layout.activity_camera_full_screen;
    }

    public static void startCameraFullScreen(Context mContext, String cameraNum) {
        mContext.startActivity(new Intent(mContext, CameraFullScreenActivity.class)
                .putExtra("cameraNum", cameraNum));
    }

    @Override
    protected void onDestroy() {
        try{
            player.stopRealPlay();
            player.release();
        }catch (Exception e){
            e.printStackTrace();
        }catch (Error e){
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        player.startRealPlay();
        super.onResume();
    }

    @Override
    protected void onPause() {
        try{
            player.stopRealPlay();
        }catch (Exception e){
            e.printStackTrace();
        }
        super.onPause();
    }

    @Override
    public void initView() {
        ImmersionBar.with(this).init();
        handler = new Handler(this);
        Intent mintent = getIntent();
        cameraNum = mintent.getStringExtra("cameraNum");
//        String cameraAccount = o.getData().getCameraMaccount();
        mSurfaceView = findViewById(R.id.realplay_sv);
        cameraController = findViewById(R.id.camera_controller);

        mSurfaceView.setOnClickListener(v -> {
            if (cameraController.getVisibility() == View.VISIBLE) {
                cameraController.setVisibility(View.GONE);
                getToolbar().setVisibility(View.GONE);
            } else {
                cameraController.setVisibility(View.VISIBLE);
                getToolbar().setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.mSurfaceview_capture).setOnClickListener(v -> {
            Bitmap capturePicture = null;
            try {
                capturePicture = player.capturePicture();
                String path = FileCacheUtils.saveBitmap(capturePicture);
                newCoverPath = path;
                Toast.makeText(mContext, "截图已保存至:" + path, Toast.LENGTH_SHORT).show();
                capturePicture.recycle();
            } catch (NullPointerException e) {
                Toast.makeText(mContext, "摄像头未播放", Toast.LENGTH_SHORT).show();
                capturePicture.recycle();
                return;
            }

        });
        (findViewById(R.id.sv_record)).setOnClickListener(v -> {
            if (!isRecording) {
                Calendar calendar = Calendar.getInstance();
                String sdCardDir = FileCacheUtils.getAppRecordVideoPath();
                String videoFile = String.valueOf(calendar.get(Calendar.YEAR)) + String.valueOf(calendar.get(Calendar.MONTH)) + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + 1)
                        + String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)) + String.valueOf(calendar.get(Calendar.MINUTE)) + String.valueOf(calendar.get(Calendar.SECOND)) + ".mp4";
                isRecording = player.startLocalRecordWithFile(sdCardDir + videoFile);
                if (isRecording) {
                    ((ImageButton) findViewById(R.id.sv_record)).setImageResource(R.drawable.camera_pre);
                    Toast.makeText(mContext, "Recording", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Recording Failed", Toast.LENGTH_SHORT).show();
                }
            } else {
                boolean stopRecording = player.stopLocalRecord();
                if (stopRecording) {
                    ((ImageButton) findViewById(R.id.sv_record)).setImageResource(R.drawable.record);
                    Toast.makeText(mContext, "Stop Recording", Toast.LENGTH_SHORT).show();
                    isRecording = false;
                } else
                    Toast.makeText(mContext, "Stop Recording Failed", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.saveTheNetStream).setOnClickListener(v -> {
            if (isSavingTheStream) {
                ((ImageButton) findViewById(R.id.saveTheNetStream)).setImageResource(R.drawable.flow);
                saveTheDataThread.interrupt();
                isSavingTheStream = false;
            } else {
                saveTheData();
                ((ImageButton) findViewById(R.id.saveTheNetStream)).setImageResource(R.drawable.flow_pre);
                isSavingTheStream = true;
            }
        });

        findViewById(R.id.mSurfaceview_play).setOnClickListener(v -> {
            player.startRealPlay();
            saveTheData();
        });
        findViewById(R.id.mSurfaceView_pause).setOnClickListener(v -> player.stopRealPlay());

        player = EZOpenSDK.getInstance().createPlayer(cameraNum, 1);
        //设置Handler, 该handler将被用于从播放器向handler传递消息
        player.setHandler(handler);
        //设置播放器的显示Surface
        mSVH = mSurfaceView.getHolder();
        mSVH.addCallback(this);
        player.setSurfaceHold(mSVH);
        saveTheData();
        player.startRealPlay();
    }

    SurfaceHolder mSVH;

    public void saveTheData() {
        saveTheDataThread = null;
        saveTheDataThread = new saveTheDataThread();
        try {
            saveTheDataThread.start();
        } catch (IllegalThreadStateException e) {
            LogUtil.e(e.toString());
        }
    }

    @Override
    public void initData() {

    }

    Runnable stopPlay = () -> {
        player.stopRealPlay();
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏

//        setContentView(R.layout.activity_camera_full_screen);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_PLAY_SUCCESS:
                //播放成功
                LogUtil.d("播放中...");
                break;
            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_PLAY_FAIL:
                //播放失败,得到失败信息

                try {
                    ErrorInfo errorinfo = (ErrorInfo) msg.obj;
                    LogUtil.e("播放失败 -> ",errorinfo.errorCode);
                    Toast.makeText(this, errorinfo.description, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                } catch (Error e){
                    e.printStackTrace();
                }
                break;
            case EZConstants.MSG_VIDEO_SIZE_CHANGED:
                //解析出视频画面分辨率回调
                try {
                    String temp = (String) msg.obj;
                    String[] strings = temp.split(":");
                    int mVideoWidth = Integer.parseInt(strings[0]);
                    int mVideoHeight = Integer.parseInt(strings[1]);
                    //解析出视频分辨率
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                LogUtil.e(String.valueOf(msg.what));
                player.startRealPlay();
                break;
        }
        return false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (player != null) {
            player.setSurfaceHold(holder);
        }
        mSVH = holder;
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public class saveTheDataThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                Thread.sleep(30000);
                handler.post(stopPlay);
            } catch (InterruptedException e) {
                LogUtil.e(e.toString());
            }
        }
    }

}
