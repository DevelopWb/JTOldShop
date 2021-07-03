package com.juntai.shop.mall.ui.order;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alipay.sdk.app.PayTask;
import com.juntai.mall.base.base.BaseActivity;
import com.juntai.mall.base.base.BaseObserver;
import com.juntai.mall.base.utils.LogUtil;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.shop.mall.App;
import com.juntai.shop.mall.AppNetModule;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.OrderCreateBean;
import com.juntai.shop.mall.bean.OrderPayWxBean;
import com.juntai.shop.mall.bean.StringBean;
import com.juntai.shop.mall.bean.event.EventWx;
import com.juntai.shop.mall.utils.AppUtils;
import com.juntai.shop.mall.utils.StringTools;
import com.juntai.shop.mall.wxapi.WXPayEntryActivity;
import com.mabeijianxi.smallvideorecord2.StringUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.mm.opensdk.utils.ILog;
import com.videogo.constant.Constant;
import com.videogo.util.MD5Util;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 订单支付页--选择支付方式
 * Created by Ma
 * on 2019/9/8
 */
public class OrderPayActivity extends BaseActivity implements View.OnClickListener {
    private int SDK_PAY_FLAG = 1;
    private int UPDATE_TIME = 2;
    private int id;
    private double a;
    private String name;
    private TextView tvTime,tvName,tvPrice;
    RadioButton radioButtonWx,radioButtonZfb;
    IWXAPI msgApi;
    long timeShow;
    long time;
    @SuppressLint("HandlerLeak")
    public Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == SDK_PAY_FLAG){
                LogUtil.d(String.valueOf(msg.obj));
                Map<String,String> map = (Map<String, String>) msg.obj;
                try {
                    String status = map.get("resultStatus");
                    switch (status){
                        case "9000"://支付成功
                            toActivity();
                            return;
                        case "8000"://正在处理中，支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
                            break;
                        case "4000"://订单支付失败
                            startActivity(getIntent().setClass(mContext,PayCompleteActivity.class).putExtra("isSuccess",false));
                            break;
                        case "5000"://重复请求
                            break;
                        case "6001"://支付取消-不需要操作
                            break;
                        case "6002"://网络连接出错
                            break;
                        case "6004"://支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
                            break;
                    }
                    //ToastUtils.toast(mContext,memo);
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.e("支付失败:"+e.toString());
                }
                //{resultStatus=9000, result={"alipay_trade_app_pay_response":{"code":"10000","msg":"Success","app_id":"2019122060094093","auth_app_id":"2019122060094093","charset":"utf-8","timestamp":"2019-12-26 09:44:04","out_trade_no":"20191226094205269","total_amount":"0.01","trade_no":"2019122622001457171403700650","seller_id":"2088731141099773"},"sign":"jjIcQhZof7N0IREIHWOcXwHFjzzvEunZUxYNtSJRiyNoiq5KOD335xcCWmM5rakZpK1f0vUu9aEbm8wGPOlBvTfuzwWuNJvP8te0W+vEoORKCIjARFc2STprzSlgUfh88WEx0Ft6+LJ4Mr/FsdsRQ8t0Znpyck0g+dxocnnSxMheCCczGyfZvrnbHRo/R3BV4JMtEuhpTkQq+RFongIDyE6/T+0IriLGiaUE8MoLd5xnh7dIZJJx3bav6NvBJAZjkmGf5ca8WSJJT3SPsV178Gv6J7fxAkmDYWNrx8SOLY0vvZvFYG+Bc9Mom/HR/BXWck+3UWe5BYV2+C1a6Lxaug==","sign_type":"RSA2"}, memo=, extendInfo={"doNotExit":true,"isDisplayResult":true}}
                //{resultStatus=6001, result=, memo=操作已经取消。}
            }else if (msg.what == UPDATE_TIME){
                tvTime.setText(String.format("支付剩余时间 %s",StringTools.getStringData("mm:ss",timeShow * 1000)));
                if (timeShow == 0){
                    setResult(RESULT_OK);
                    finish();
                }
            }
        }
    };
    public void toActivity(){
        startActivity(getIntent().setClass(mContext, PayCompleteActivity.class));
        finish();
    }
    @Override
    public int getLayoutView() {
        return R.layout.activity_orderpay;
    }

    @Override
    public void initView() {
        setTitleName("订单支付");
        id = getIntent().getIntExtra("id",-1);
        a = getIntent().getDoubleExtra("a",-1);
        name = getIntent().getStringExtra("name");
        time = getIntent().getLongExtra("time",0);
        time = 900 - (System.currentTimeMillis() - time * 1000) / 1000;
        tvTime = findViewById(R.id.pay_time);
        tvName = findViewById(R.id.pay_name);
        tvPrice = findViewById(R.id.pay_price);
        radioButtonWx = findViewById(R.id.pay_radio_wx);
        radioButtonZfb = findViewById(R.id.pay_radio_zfb);
        findViewById(R.id.pay_btn).setOnClickListener(this);
        findViewById(R.id.pay_type_wx).setOnClickListener(this);
        findViewById(R.id.pay_type_zfb).setOnClickListener(this);

        tvName.setText(name);
//        tvTime.setText(time);
        tvPrice.setText(String.format("￥%s",a));
    }

    @Override
    public void initData() {
        msgApi = WXAPIFactory.createWXAPI(mContext,"wx55706643d79cd22a");
        // 将该app注册到微信
         //msgApi.registerApp("wx55706643d79cd22a");
        new Thread(() -> {
            try {
                if (time <= 0)return;
                for (int i = 0; i <= time; i++) {
                    timeShow = time - i;
                    Message msg = new Message();
                    msg.what = UPDATE_TIME;
                    mHandler.sendMessage(msg);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pay_type_wx:
                radioButtonWx.setChecked(true);
                radioButtonZfb.setChecked(false);
                break;
            case R.id.pay_type_zfb:
                radioButtonWx.setChecked(false);
                radioButtonZfb.setChecked(true);
                break;
            case R.id.pay_btn:
                if (radioButtonZfb.isChecked()){
                    getOrderInfo();
                }else {
                    getOrderInfoWx();
                }
                //
                //finish();
                break;
        }
    }
    String orderInfo_ZFB;
    private void getOrderInfo(){
        AppNetModule.createrRetrofit()
                .orderInfoZfb(App.app.getAccount(),
                        App.app.getUserToken(),
                        id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<StringBean>() {
                    @Override
                    public void onSuccess(StringBean o) {
                        orderInfo_ZFB = o.getReturnValue();
                        zhifubao();
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtils.toast(mContext,msg);
                    }
                });
    }

    /**
     * 微信
     */
    private void getOrderInfoWx(){
        AppNetModule.createrRetrofit()
                .orderInfoWx(App.app.getAccount(),
                        App.app.getUserToken(),
                        id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<OrderPayWxBean>() {
                    @Override
                    public void onSuccess(OrderPayWxBean o) {
                        if (o.code == 200){
                            if(!msgApi.isWXAppInstalled()){
                                //未安装的处理
                                ToastUtils.toast(mContext,"未安装微信");
                            }else {
                                PayReq request = new PayReq();
                                request.appId = o.getReturnValue().getAppid();//appid
                                request.partnerId = o.getReturnValue().getMch_id();//商户号
                                request.prepayId= o.getReturnValue().getPrepayId();//微信返回的支付交易会话ID
                                request.packageValue = o.getReturnValue().getPackageVal();//暂填写固定值Sign=WXPay
                                request.nonceStr= o.getReturnValue().getNonce_str();//随机字符串，不长于32位。推荐随机数生成算法
                                request.timeStamp= o.getReturnValue().getTimestamp();//时间戳，请见接口规则-参数规定
                                request.sign= o.getReturnValue().getSign();//签名，详见签名生成算法注意：签名方式一定要与统一下单接口使用的一致
                                msgApi.sendReq(request);
                            }
                        }

                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtils.toast(mContext,msg);
                    }
                });
    }
    public void zhifubao(){
        //final String orderInfo = "";   // 订单信息
        Runnable payRunnable = () -> {
            PayTask alipay = new PayTask(OrderPayActivity.this);
            Map<String,String> result = alipay.payV2(orderInfo_ZFB,true);
            Message msg = new Message();
            msg.what = SDK_PAY_FLAG;
            msg.obj = result;
            mHandler.sendMessage(msg);
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventWx eventWx){
        toActivity();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
