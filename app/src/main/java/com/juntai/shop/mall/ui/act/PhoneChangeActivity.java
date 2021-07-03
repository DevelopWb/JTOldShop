package com.juntai.shop.mall.ui.act;

import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageView;

import com.juntai.mall.base.base.BaseActivity;
import com.juntai.mall.base.base.BaseObserver;
import com.juntai.mall.base.base.BaseResult;
import com.juntai.mall.base.utils.LogUtil;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.shop.mall.App;
import com.juntai.shop.mall.AppHttpPath;
import com.juntai.shop.mall.AppNetModule;
import com.juntai.shop.mall.R;

import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 修改手机号
 * Created by Ma
 * on 2019/12/20
 */
public class PhoneChangeActivity extends BaseActivity {
    EditText editTextPhone,editTextCode;
    ImageView imageView;
    int time = 0;
    @Override
    public int getLayoutView() {
        return R.layout.activity_phone;
    }

    @Override
    public void initView() {
        setTitleName("修改手机号");

        imageView = findViewById(R.id.phone_change_getcode);
        editTextPhone = findViewById(R.id.phone_change_phone);
        editTextCode = findViewById(R.id.phone_change_code);

        imageView.setOnClickListener(v -> {
            if (time == 0){
                imageView.setImageResource(R.mipmap.ic_phone_change_code_hui);
                getCode();
            }else {
                ToastUtils.toast(mContext,String.format("请%s秒后再试",60 - time));
            }
        });
        findViewById(R.id.phone_change_btn).setOnClickListener(v -> {
            SMSSDK.submitVerificationCode("+86", editTextPhone.getText().toString(), editTextCode.getText().toString());
        });

        SMSSDK.registerEventHandler(eventHandler);// 注册一个事件回调，用于处理SMSSDK接口请求的结果
    }

    private void getCode() {
        new Thread(() -> {
            for (time = 1; time < 60 ; time++) {
                if (time == 0)return;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            time = 0;
            runOnUiThread(() -> imageView.setImageResource(R.mipmap.ic_phone_change_code));
        }).start();
        // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
        SMSSDK.getVerificationCode("+86", editTextPhone.getText().toString(), (s, s1) -> false);
    }

    @Override
    public void initData() {

    }
    private void phoneChange(){
        AppNetModule.createrRetrofit()
                .editPhone(App.app.getAccount(),App.app.getUserToken(),editTextPhone.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResult>(null) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        ToastUtils.success(mContext,String.format("%s,请重新登录",result.msg));
                        startActivity(new Intent(mContext,LoginActivity.class));
                        finish();
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtils.toast(mContext,msg);
                    }
                });
    }
    String TAG = "验证码";
    /**
     * 短信验证码回调
     */
    EventHandler eventHandler = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            super.afterEvent(event, result, data);
            LogUtil.d(TAG+ " event=" + event + " result=" +result + " o="+data.toString());
            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //验证码验证成功
                    LogUtil.d(TAG+ "提交验证码正确");
                    //submitRegister();
                    phoneChange();
                    //提交验证码成功
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    runOnUiThread(() -> {
                        ToastUtils.toast(mContext,"验证码已发送");
                    });
                    LogUtil.d(TAG+ "获取验证码成功");
                    //获取验证码成功
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //返回支持发送验证码的国家列表
                }
            }else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE && result ==0){
                runOnUiThread(() -> {
                    ToastUtils.toast(mContext,"验证码错误");
                });
                LogUtil.d(TAG+ "验证码错误");
            }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE && result ==0){
                runOnUiThread(() -> {
                    imageView.setImageResource(R.mipmap.ic_phone_change_code);
                    time = -1;
                    try {
                        ToastUtils.toast(mContext,new JSONObject(data.toString().substring(data.toString().indexOf("{"))).getString("detail"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        ToastUtils.toast(mContext,"获取验证码失败");
                    }
                });
                LogUtil.d(TAG+ "获取验证码失败");
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
