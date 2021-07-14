package com.juntai.shop.mall.mine.set;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.juntai.mall.base.base.BaseObserver;
import com.juntai.mall.base.base.BaseResult;
import com.juntai.mall.base.utils.ActivityManagerTool;
import com.juntai.mall.base.utils.SPTools;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.mall.im.ModuleIm_Init;
import com.juntai.shop.mall.AppNetModule;
import com.juntai.shop.mall.MyApp;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.baseinfo.sendcode.SmsCheckCodeActivity;
import com.juntai.shop.mall.ui.act.LoginActivity;
import com.juntai.shop.mall.utils.AppUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 修改手机号
 * Created by Ma
 * on 2019/12/20
 */
public class PhoneChangeActivity extends SmsCheckCodeActivity implements View.OnClickListener {

    /**
     * 输入手机号
     */
    private EditText mPhoneChangePhoneEt;
    /**
     * 获取验证码
     */
    private TextView mRegistSendCheckCodeTv;
    /**
     * 输入验证码
     */
    private EditText mPhoneChangeCodeEt;
    /**
     * 完成
     */
    private TextView mPhoneChangeBtn;

    @Override
    public int getLayoutView() {
        return R.layout.activity_phone;
    }

    @Override
    public void initView() {
        setTitleName("修改手机号");

        mPhoneChangePhoneEt = (EditText) findViewById(R.id.phone_change_phone);
        mRegistSendCheckCodeTv = (TextView) findViewById(R.id.regist_send_check_code_tv);
        mRegistSendCheckCodeTv.setOnClickListener(this);
        mPhoneChangeCodeEt = (EditText) findViewById(R.id.phone_change_code_et);
        mPhoneChangeBtn = (TextView) findViewById(R.id.phone_change_btn);
        mPhoneChangeBtn.setOnClickListener(this);
    }


    @Override
    public void initData() {

    }

    private void phoneChange() {
        AppNetModule.createrRetrofit()
                .editPhone(MyApp.app.getAccount(), MyApp.app.getUserToken(),getTextViewValue(mPhoneChangePhoneEt),getTextViewValue(mPhoneChangeCodeEt))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResult>(null) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        ToastUtils.success(mContext, String.format("%s,请重新登录", result.msg));
                        ModuleIm_Init.logout();
                        SPTools.saveString(MyApp.app, AppUtils.SP_KEY_LOGIN, "");
                        MyApp.app.setUserBean(null);
                        ActivityManagerTool.getInstance().finishApp();
                        startActivity(new Intent(mContext, LoginActivity.class));
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtils.error(mContext, msg);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected TextView getSendCodeTv() {
        return mRegistSendCheckCodeTv;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.regist_send_check_code_tv:
                sendCheckCode(getTextViewValue(mPhoneChangePhoneEt));
                break;
            case R.id.phone_change_btn:
                if (!mPresenter.mobileFormatIsOk(getTextViewValue(mPhoneChangePhoneEt))) {
                    return;
                }
                if (TextUtils.isEmpty(getTextViewValue(mPhoneChangeCodeEt))) {
                    ToastUtils.toast(mContext,"验证码不能为空");
                    return;
                }
                phoneChange();
                break;
        }
    }
}
