package com.juntai.shop.mall.ui.act;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.juntai.mall.base.base.BaseObserver;
import com.juntai.mall.base.utils.FileCacheUtils;
import com.juntai.mall.base.utils.ImageLoadUtil;
import com.juntai.mall.base.utils.LogUtil;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.shop.mall.App;
import com.juntai.shop.mall.AppHttpPath;
import com.juntai.shop.mall.AppNetModule;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.CameraDetailsBean;
import com.juntai.shop.mall.utils.StringTools;
import com.videogo.errorlayer.ErrorInfo;
import com.videogo.openapi.EZConstants;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.EZPlayer;

import java.util.Calendar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 监控
 */
public class CameraActivity extends AppCompatActivity implements SurfaceHolder.Callback, Handler.Callback {

    Handler handler, handler2;
    EZPlayer player;
    SurfaceHolder mSVH;
    SurfaceView mSurfaceView;
    WindowManager.LayoutParams originalLP;
    Boolean isRecording = false;
    Boolean isSavingTheStream = true;
    RelativeLayout mSVControllor, mSurfaceViewRl;
    RelativeLayout.LayoutParams originScreenLp;
    Button setAsCover;
    Bitmap newCover;
    String newCoverPath;
    saveTheDataThread saveTheDataThread;
    Bitmap bm2, capturePicture;
    CameraDetailsBean bean;
    boolean isFirst = true;
    private int cameraId;
    private String cameraNumber;
    boolean isDialog = false;
    LinearLayout linearLayout;
    ImageView backImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
    }

    public void initView() {
        Intent mintent = getIntent();
        isDialog = mintent.getBooleanExtra("dialog",false);
        linearLayout = findViewById(R.id.camera_layout);
        backImage = findViewById(R.id.camera_back);
        backImage.setOnClickListener(v -> finish());
        mSurfaceView = findViewById(R.id.realplay_sv);
        mSurfaceViewRl = findViewById(R.id.mSurfaceview_Rl);
        cameraId = mintent.getIntExtra("id", 0);
        AppNetModule.createrRetrofit()
                .cameraInfo(cameraId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<CameraDetailsBean>(null) {
                    @Override
                    public void onSuccess(CameraDetailsBean o) {
                        bean = o;
                        App.app.setYsAccount(o.getReturnValue().getAppKey(), o.getReturnValue().getAccessToken());
                        TextView tvplace = findViewById(R.id.deviceplace);
//                        TextView tvstate = findViewById(R.id.devicestate);
                        mSVControllor = findViewById(R.id.mSurfaceview_controllor);
                        tvplace.append(o.getReturnValue().getPlace() + "(" + o.getReturnValue().getRemark() + ")");
                        cameraNumber = o.getReturnValue().getNumber();
                        ImageLoadUtil.loadMapImg(CameraActivity.this,
                                AppHttpPath.IMAGE + o.getReturnValue().getImgUrl(),
                                findViewById(R.id.player_cover));
                        next();
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtils.toast(CameraActivity.this, msg);
                    }
                });

        Window window = getWindow();
        if (isDialog){
//        window.setWindowAnimations(R.style.dialogWindowAnim);
//            window.setBackgroundDrawableResource(R.color.transparent);
            WindowManager.LayoutParams wlp = window.getAttributes();
            //wlp.gravity = Gravity.BOTTOM;
            wlp.width = (App.W / 10) * 9;
            wlp.height = (App.H / 5) * 4;
            window.setAttributes(wlp);
            //
            linearLayout.setBackgroundResource(R.drawable.videoplayer_btn_bg);
        }else {
            WindowManager.LayoutParams wlp = window.getAttributes();
            //wlp.gravity = Gravity.BOTTOM;
            wlp.width = App.W;
            wlp.height = App.H - App.statusBarH;
            window.setAttributes(wlp);
            //
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mSurfaceViewRl.getLayoutParams();
            layoutParams.setMargins(0,0,0,0);
            backImage.setVisibility(View.VISIBLE);
            linearLayout.setBackgroundResource(R.color.white);
        }
    }
    public void next() {
        setAsCover = findViewById(R.id.setAsCover);
        handler = new Handler(this);
        handler2 = new Handler();
        originalLP = getWindow().getAttributes();
        findViewById(R.id.player_cover).setOnClickListener(v -> {
//            if (cameraStatus.equals("1")) {
//                findViewById(R.id.play_button).setVisibility(View.GONE);
//                v.setVisibility(View.GONE);
            startPlay();
//            } else {
//                ToastUtils.toast(mContext, "该摄像机正在维护中");
//            }
        });
        final RelativeLayout.LayoutParams fullScreenLp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        fullScreenLp.leftMargin = 0;
        fullScreenLp.rightMargin = 0;
        originScreenLp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        originScreenLp.leftMargin = dip2px(getApplicationContext(), 10);
        originScreenLp.rightMargin = dip2px(getApplicationContext(), 10);
        originScreenLp.topMargin = dip2px(getApplicationContext(), 10);
        findViewById(R.id.fullscreen).setOnClickListener(v ->
                CameraFullScreenActivity.startCameraFullScreen(CameraActivity.this, cameraNumber));
        findViewById(R.id.mSurfaceview_capture).setOnClickListener(v -> {
            Bitmap capturePicture;
            try {
                capturePicture = player.capturePicture();
                String path = FileCacheUtils.saveBitmap(capturePicture);
                newCoverPath = path;
                Toast.makeText(CameraActivity.this, "截图已保存至:" + path, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(CameraActivity.this, "摄像头未播放", Toast.LENGTH_SHORT).show();
                return;
            }

        });
        findViewById(R.id.play_button).setOnClickListener(v -> {
            startPlay();
//            if (cameraStatus.equals("1")) {
//                findViewById(R.id.player_cover).setVisibility(View.GONE);
//                v.setVisibility(View.GONE);
//            } else {
//                ToastUtils.toast(mContext, "该摄像机正在维护中");
//            }
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
                    Toast.makeText(CameraActivity.this, "开始录制", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CameraActivity.this, "录制失败", Toast.LENGTH_SHORT).show();
                }
            } else {
                boolean stopRecording = player.stopLocalRecord();
                if (stopRecording) {
                    ((ImageButton) findViewById(R.id.sv_record)).setImageResource(R.drawable.record);
                    Toast.makeText(CameraActivity.this, "停止录制", Toast.LENGTH_SHORT).show();
                    isRecording = false;
                } else
                    Toast.makeText(CameraActivity.this, "停止录制失败", Toast.LENGTH_SHORT).show();
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
            startPlay();
            saveTheData();
        });
        findViewById(R.id.mSurfaceView_pause).setOnClickListener(v -> handler.post(stopPlay));

        //获取EZUIPlayer实例
//        LogUtil.d("caNum" + cameraNum);
        player = EZOpenSDK.getInstance().createPlayer(String.valueOf(cameraNumber), 1);
        //设置Handler, 该handler将被用于从播放器向handler传递消息
        player.setHandler(handler);
        //设置播放器的显示Surface
        mSVH = mSurfaceView.getHolder();
        mSVH.addCallback(this);
        player.setSurfaceHold(mSVH);
        saveTheData();
//        if (cameraStatus.equals("1"))
        startPlay();
//        else
//            ToastUtils.toast(mContext, "该摄像机正在维护中");
    }

    @Override
    protected void onPause() {
        if (player != null){
            player.stopRealPlay();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (player != null)
            startPlay();
        super.onResume();
    }

    public void startPlay() {
        if (!isFirst) {
            findViewById(R.id.cameraPlayProgressbar).setVisibility(View.VISIBLE);
        }
        isFirst = false;
        if(player != null){
            player.startRealPlay();
        }
    }

    public void saveTheData() {
        if (saveTheDataThread != null && saveTheDataThread.isAlive()) {
            saveTheDataThread.interrupt();
        }
        saveTheDataThread = null;
        saveTheDataThread = new saveTheDataThread();
        try {
            saveTheDataThread.start();
        } catch (IllegalThreadStateException e) {
            LogUtil.e(e.toString());
        }
    }

    boolean isbackPlay = false;

    //back缩回屏幕
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isbackPlay) {
            mSurfaceViewRl.setLayoutParams(originScreenLp);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//横屏
            getWindow().setAttributes(originalLP);
            getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            if (keyCode == KeyEvent.KEYCODE_BACK
                    && event.getRepeatCount() == 0) {
                ViewGroup.LayoutParams lp = mSurfaceView.getLayoutParams();
                lp.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                lp.height = 600;
                mSurfaceView.setLayoutParams(lp);
                findViewById(R.id.cameraInfoRl).setVisibility(View.VISIBLE);
                isbackPlay = false;
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean handleMessage(Message msg) {
        findViewById(R.id.cameraPlayProgressbar).setVisibility(View.GONE);
        switch (msg.what) {
            //播放成功
            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_PLAY_SUCCESS:
                findViewById(R.id.play_button).setVisibility(View.GONE);
                findViewById(R.id.player_cover).setVisibility(View.GONE);
//                uploadCover(new File(FileCacheUtils.saveBitmap(player.capturePicture())));
                break;
            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_PLAY_FAIL:
                //播放失败,得到失败信息
                ErrorInfo errorinfo = (ErrorInfo) msg.obj;
                LogUtil.e(msg.obj.toString());
//                cameraStatus = "2";
                ((TextView) findViewById(R.id.devicestate)).setText("设备状态:设备状态发生错误");
                Toast.makeText(this, errorinfo.description, Toast.LENGTH_SHORT).show();
                break;
            case EZConstants.MSG_VIDEO_SIZE_CHANGED:
                //解析出视频画面分辨率回调
                break;
            default:
                LogUtil.d("errorcode:" + String.valueOf(msg.what));
                break;
        }
        return false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (player != null){
            player.stopRealPlay();
        }

    }

    Runnable hideSetAsCoverbtn = new Runnable() {
        @Override
        public void run() {
            LogUtil.d("startToGone");
            setAsCover.setVisibility(View.INVISIBLE);
            LogUtil.d("setGone");
        }
    };


    Runnable stopPlay = new Runnable() {
        @Override
        public void run() {
            if (isSavingTheStream) {
                if (player != null)
                    player.stopRealPlay();
                findViewById(R.id.player_cover).setVisibility(View.VISIBLE);
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (newCover != null)
            newCover.recycle();
        if (bm2 != null)
            bm2.recycle();
        if (player != null){
            player.stopRealPlay();
            player.release();
            player = null;
        }
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

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
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
