package com.juntai.mall.bdmap.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.juntai.mall.base.utils.BaseAppUtils;
import com.juntai.mall.base.utils.LogUtil;
import com.juntai.mall.base.utils.NotificationTool;
import com.juntai.mall.bdmap.utils.BaiDuLocationUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LocateAndUpload extends Service {
    private BaiDuLocationUtils baiDuLocationUtils = null;
    public static Double lat = 0.0;
    public static Double lng = 0.0;
    public static BDLocation bdLocation = null;
    static Callback callback;
    private int id;
    private String token,account,historyApiUrl;
    private OkHttpClient okHttpClient;
    private Request request;
    private Map<String,String> params;
    private RequestBody body;
    private SimpleDateFormat simpleDateFormat;
    private Boolean isFirstWarning = true;
    private BDAbstractLocationListener bdAbstractLocationListener;

    public class Binder extends android.os.Binder {
        public LocateAndUpload getService() {
            return LocateAndUpload.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // : Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        historyApiUrl = intent.getStringExtra("historyApiUrl");
        params = (HashMap)intent.getSerializableExtra("params");
        okHttpClient = new OkHttpClient();

//        return super.onStartCommand(intent, flags, startId);
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        bdAbstractLocationListener = new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                lat = bdLocation.getLatitude();
                lng = bdLocation.getLongitude();
                LocateAndUpload.bdLocation = bdLocation;
//                LogUtil.d("------------------->"+lat);
                if (callback != null) {
                    callback.onLocationChange(bdLocation);
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), "");
                            String url = historyApiUrl
                                    +"?Lat="+lat
                                    +"&Lng="+lng;
                            for (HashMap.Entry<String, String> entry : params.entrySet()) {
                                url += "&" + entry.getKey() + "=" + entry.getValue();
                            }
                            request = new Request.Builder()
                                    .post(body)
                                    .url(url)
                                    .build();
                            Response rs = okHttpClient.newCall(request).execute();
                            JSONObject jsonObject = new JSONObject(rs.body().string());
//                            LogUtil.d("update Location ---> "+lat+","+lng);
                            if(!"200".equals(jsonObject.optString("status"))&&isFirstWarning){
//                                ToastUtils.toast(getApplicationContext(),"位置服务更新失败");
                                LogUtil.d("安保更新位置失败->"+jsonObject.toString());
                                isFirstWarning = false;
                            }else if("200".equals(jsonObject.optString("status"))){
//                                ToastUtils.toast(getApplicationContext(),"已重新连接位置更新");
                                isFirstWarning = true;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        };

        baiDuLocationUtils = new BaiDuLocationUtils(this);
        baiDuLocationUtils.start(bdAbstractLocationListener);
        Notification notification = NotificationTool.sendNotifLocService(getApplicationContext(), BaseAppUtils.getAppName(),"正在运行",BaseAppUtils.getAppLogo(),true);
        startForeground(111,notification);
    }

    @Override
    public void onDestroy() {
        baiDuLocationUtils.stop(bdAbstractLocationListener);
        super.onDestroy();
    }


    public static void setCallback(Callback callback) {
        LocateAndUpload.callback = callback;
    }


    public interface Callback {
        void onLocationChange(BDLocation bdLocation);
    }
}
