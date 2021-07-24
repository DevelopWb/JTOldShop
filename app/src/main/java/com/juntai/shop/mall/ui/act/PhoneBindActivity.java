package com.juntai.shop.mall.ui.act;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.juntai.mall.base.base.BaseActivity;
import com.juntai.mall.base.base.BaseObserver;
import com.juntai.mall.base.base.BaseResult;
import com.juntai.mall.base.utils.GsonTools;
import com.juntai.mall.base.utils.LogUtil;
import com.juntai.mall.base.utils.SPTools;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.mall.im.ModuleIm_Init;
import com.juntai.shop.mall.MyApp;
import com.juntai.shop.mall.AppNetModule;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.baseinfo.sendcode.SmsCheckCodeActivity;
import com.juntai.shop.mall.bean.LoginBean;
import com.juntai.shop.mall.utils.AppCode;
import com.juntai.shop.mall.utils.AppUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ma
 * on 2019/12/27
 */

// TODO: 2021/7/24 这个界面和逻辑待优化
public class PhoneBindActivity extends SmsCheckCodeActivity {
    TextInputEditText editPhone;
    boolean isSuccess = false;
    TextView tvSubmit;
    String phone,weChatId,weChatName,qqId,qqName;
    @Override
    public int getLayoutView() {
        return R.layout.activity_phone_bind;
    }

    @Override
    public void initView() {
        setTitleName("绑定手机号");
        initToolbarAndStatusBar(false);
        qqId = getIntent().getStringExtra("qqId");
        qqName = getIntent().getStringExtra("qqName");
        weChatId = getIntent().getStringExtra("weChatId");
        weChatName = getIntent().getStringExtra("weChatName");
        editPhone = findViewById(R.id.bind_phone);
        findViewById(R.id.bind_close).setOnClickListener(v -> finish());
        findViewById(R.id.bind_getcode).setOnClickListener(v -> {
            //验证码
            phone = editPhone.getText().toString();
            if (!phone.isEmpty() && phone.length() == 11){
                startActivityForResult(new Intent(mContext, CodeActivity.class)
                        .putExtra("phone",phone), AppCode.SSM_CODE);
            }else {
                ToastUtils.toast(mContext,"请输入正确手机号");
            }
        });
        tvSubmit = findViewById(R.id.bind_submit);
        tvSubmit.setOnClickListener(v -> {
            if (isSuccess){
                login();
            }
        });

    }

    @Override
    public void initData() {

    }

    /**
     * 登陆
     */
    public void login(){
        AppNetModule.createrRetrofit()
                .login(phone,null,null,null,null,null,null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {
                    LoginBean userBean;
                    String loginStr = responseBody.string();
                    userBean = GsonTools.changeGsonToBean(loginStr, LoginBean.class);
                    if (userBean.success) {
                        ToastUtils.success(mContext, "登录成功");
                        SPTools.saveString(MyApp.app, AppUtils.SP_KEY_LOGIN, loginStr);
                        MyApp.app.setUserBean(null);
//                        ModuleIm_Init.connectIM(MyApp.app.getUser().getReturnValue().getrOngYunToken());
                        bind();
                        LogUtil.d("token=" + MyApp.app.getUserToken());
                        LogUtil.d("token=" + userBean.getReturnValue().getAccount());
                    } else {
                        ToastUtils.toast(mContext, userBean.error);
                    }
                }, throwable -> {
                    LogUtil.d(throwable.toString());
                    LogUtil.d("token=" + throwable.toString());
                    ToastUtils.error(mContext, "登录失败");
                });
    }

    /**
     * 绑定
     */
    public void bind(){
        AppNetModule.createrRetrofit()
                // TODO: 2021/7/24 这个地方的验证码暂时穿null
                .bind(MyApp.app.getAccount(), MyApp.app.getUserToken(),weChatId,weChatName,qqId,qqName,null)
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
                        ToastUtils.toast(mContext,msg);
                    }
                });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppCode.SSM_CODE && resultCode == RESULT_OK) {
            isSuccess = true;
            tvSubmit.setBackgroundResource(R.drawable.bg_btn_theme);
        }else {
            tvSubmit.setBackgroundResource(R.drawable.bg_r_gray);
        }
    }

    @Override
    protected TextView getSendCodeTv() {
        return null;
    }
}
