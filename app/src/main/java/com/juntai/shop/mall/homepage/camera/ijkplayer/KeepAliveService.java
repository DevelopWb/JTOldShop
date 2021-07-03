package com.juntai.shop.mall.homepage.camera.ijkplayer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.juntai.mall.base.base.BaseObserver;
import com.juntai.mall.base.bean.OpenLiveBean;
import com.juntai.shop.mall.AppNetModule;


public class KeepAliveService extends Service {

    private String sessionId;
    private Thread thread;
    private boolean isDeatrory = false;

    public KeepAliveService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sessionId = intent.getStringExtra("sessionId");
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(50000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                AppNetModule.createrRetrofit()
                        .keepAlive(sessionId)
//                        .compose(RxScheduler.ObsIoMain(null))
                        .subscribe(new BaseObserver<OpenLiveBean>(null) {
                            @Override
                            public void onSuccess(OpenLiveBean o) {
                                Log.d("444444", o.getErrcode() + "");
                                if (!isDeatrory) {
                                    run();
                                }

                            }

                            @Override
                            public void onError(String msg) {

                            }
                        });
            }

        });
        thread.start();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("444444", "服务杀死");
        isDeatrory = true;
        thread.interrupt();
    }
}
