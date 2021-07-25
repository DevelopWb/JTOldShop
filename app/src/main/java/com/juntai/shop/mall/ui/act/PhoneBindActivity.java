package com.juntai.shop.mall.ui.act;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.juntai.mall.base.base.BaseObserver;
import com.juntai.mall.base.base.BaseResult;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.shop.mall.AppNetModule;
import com.juntai.shop.mall.MyApp;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.baseinfo.sendcode.SmsCheckCodeActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ma
 * on 2019/12/27
 */

public class PhoneBindActivity extends SmsCheckCodeActivity implements View.OnClickListener {
    TextInputEditText editPhone;
    String  weChatId, weChatName, qqId, qqName;
    private EditText mSmsCodeEt;
    /**
     * 获取验证码
     */
    private TextView mBindGetcode;
    /**
     * 绑定
     */
    private TextView mBindSubmit;

    @Override
    public int getLayoutView() {
        return R.layout.activity_phone_bind;
    }

    @Override
    public void initView() {
        initToolbarAndStatusBar(false);
        qqId = getIntent().getStringExtra("qqId");
        qqName = getIntent().getStringExtra("qqName");
        weChatId = getIntent().getStringExtra("weChatId");
        weChatName = getIntent().getStringExtra("weChatName");
        editPhone = findViewById(R.id.bind_phone);
        findViewById(R.id.bind_close).setOnClickListener(v -> finish());

        mBindGetcode = (TextView) findViewById(R.id.bind_getcode);
        mSmsCodeEt = (EditText) findViewById(R.id.sms_code_et);
        mBindGetcode.setOnClickListener(this);
        mBindSubmit = (TextView) findViewById(R.id.bind_submit);
        mBindSubmit.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }


    /**
     * 绑定
     */
    public void bind() {
        if (TextUtils.isEmpty(getTextViewValue(mSmsCodeEt))) {
            ToastUtils.toast(mContext,"请输入验证码");
            return;
        }
        AppNetModule.createrRetrofit()
                .bind(getTextViewValue(editPhone), MyApp.app.getUserToken(), weChatId, weChatName, qqId, qqName, getTextViewValue(mSmsCodeEt))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResult>(null) {
                    @Override
                    public void onSuccess(BaseResult o) {
                        setResult(RESULT_OK);
                        finish();
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtils.toast(mContext, msg);
                    }
                });
    }


    @Override
    protected TextView getSendCodeTv() {
        return mBindGetcode;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bind_getcode:
                sendCheckCode(getTextViewValue(editPhone));
                break;
            case R.id.bind_submit:
                bind();
                break;
        }
    }
}
