package com.juntai.shop.mall.ui.act;

import android.content.Intent;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.juntai.mall.base.utils.GsonTools;
import com.juntai.mall.base.utils.LogUtil;
import com.juntai.mall.base.utils.SPTools;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.mall.base.widght.ProgressDialog;
import com.juntai.mall.im.ModuleIm_Init;
import com.juntai.shop.mall.AppNetModule;
import com.juntai.shop.mall.MyApp;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.baseinfo.sendcode.SmsCheckCodeActivity;
import com.juntai.shop.mall.bean.LoginBean;
import com.juntai.shop.mall.utils.AppCode;
import com.juntai.shop.mall.utils.AppUtils;
import com.juntai.shop.mall.utils.HawkProperty;
import com.juntai.shop.mall.utils.RSAUtils;
import com.orhanobut.hawk.Hawk;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 登录
 *
 * @aouther Ma
 * @date 2019/3/4
 */
public class LoginActivity extends SmsCheckCodeActivity implements View.OnClickListener {
    TextInputEditText mPhoneEt, editPass;
    boolean logining = false;
    ProgressDialog progressDialog;
    CheckBox mLoginStyleCb, passV;//选中状态-密码登录
    LinearLayout mPwdLoginLl;
    /**
     * 输入验证码
     */
    private EditText mSmsCodeEt;
    /**
     * 获取验证码
     */
    private TextView mSendCheckCodeTv;
    private LinearLayout mSmsCodeLl;
    private TextView loginTv;

    @Override
    public int getLayoutView() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        initToolbarAndStatusBar(false);
        mPhoneEt = findViewById(R.id.login_phone);
        editPass = findViewById(R.id.login_pass);
        passV = findViewById(R.id.login_pass_v);
        loginTv = findViewById(R.id.login_tv);
        loginTv.setOnClickListener(this);
        mPwdLoginLl = findViewById(R.id.pwd_login_ll);
        progressDialog = new ProgressDialog(mContext);
        mLoginStyleCb = findViewById(R.id.login_style_cb);

        findViewById(R.id.login_forget).setOnClickListener(this);
        findViewById(R.id.login_qq).setOnClickListener(this);
        findViewById(R.id.login_wx).setOnClickListener(this);
        findViewById(R.id.login_close).setOnClickListener(this);
        findViewById(R.id.login_phone_clear).setOnClickListener(this);
        mSmsCodeEt = (EditText) findViewById(R.id.sms_code_et);
        mSendCheckCodeTv = (TextView) findViewById(R.id.send_check_code_tv);
        mSmsCodeLl = (LinearLayout) findViewById(R.id.sms_code_ll);
        mSendCheckCodeTv.setOnClickListener(this);
        mLoginStyleCb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mLoginStyleCb.setText("密码登录");
                mPwdLoginLl.setVisibility(View.GONE);
                mSmsCodeLl.setVisibility(View.VISIBLE);
            } else {
                editPass.setText("");
                pass = null;
                mLoginStyleCb.setText("验证码登录");
                mPwdLoginLl.setVisibility(View.VISIBLE);
                mSmsCodeLl.setVisibility(View.GONE);
                passV.setChecked(false);
            }
        });
        passV.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                editPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                editPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });
    }


    @Override
    public void initData() {

    }

    String phone, pass, weChatId, weChatName, qqId, qqName;

    /**
     * 登录
     */
    public void login() {
        logining = true;
        progressDialog.show();
        AppNetModule.createrRetrofit()
                .login(phone, pass, weChatId, weChatName, qqId, qqName,getTextViewValue(mSmsCodeEt))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {
                    LoginBean userBean;
                    String loginStr = responseBody.string();
                    userBean = GsonTools.changeGsonToBean(loginStr, LoginBean.class);
                    if (userBean.success) {
                        switch (userBean.code) {
                            case 200:
                                ToastUtils.success(mContext, "登录成功");
                                Hawk.put(HawkProperty.USER_INFO, userBean.getReturnValue());
                                SPTools.saveString(MyApp.app, AppUtils.SP_KEY_LOGIN, loginStr);
                                MyApp.app.setUserBean(null);
                                ModuleIm_Init.connectIM(MyApp.app.getUser().getReturnValue().getrOngYunToken());
                                setResult(RESULT_OK);
                                finish();
                                break;
                            case 302:
                                startActivityForResult(new Intent(mContext, PhoneBindActivity.class)
                                                .putExtra("qqId", qqId)
                                                .putExtra("qqName", qqName),
                                        AppCode.BIND_PHONE);
                                break;
                            case 301:
                                startActivityForResult(new Intent(mContext, PhoneBindActivity.class)
                                                .putExtra("weChatId", weChatId)
                                                .putExtra("weChatName", weChatName),
                                        AppCode.BIND_PHONE);
                                break;
                            default:
                                ToastUtils.error(mContext, userBean.msg);
                                break;
                        }
                        logining = false;
                        progressDialog.dismiss();
                        LogUtil.d("token=" + MyApp.app.getUserToken());
                        LogUtil.d("token=" + userBean.getReturnValue().getAccount());
                    }

                }, throwable -> {
                    LogUtil.d(throwable.toString());
                    LogUtil.d("token=" + throwable.toString());
                    logining = false;
                    progressDialog.dismiss();
                });
    }

    PlatformDb platDB;

    /**
     * 第三方数据
     *
     * @param name
     */
    public void loginForQQWeChat(String name) {
        phone = null;
        pass = null;
        qqId = null;
        qqName = null;
        weChatId = null;
        weChatName = null;
        Platform plat = ShareSDK.getPlatform(name);
        plat.removeAccount(true); //移除授权状态和本地缓存，下次授权会重新授权
        plat.SSOSetting(false); //SSO授权，传false默认是客户端授权，没有客户端授权或者不支持客户端授权会跳web授权
        plat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                //数据
                LogUtil.d("userinfo=" + platform.getDb().exportData());
                LogUtil.d("userinfo=" + platform.getName());
                LogUtil.d("userinfo=" + hashMap.toString());
                //用户资源都保存到res
                //通过打印res数据看看有哪些数据是你想要的
                if (i == Platform.ACTION_USER_INFOR) {
                    platDB = platform.getDb();//获取数平台数据DB
                    //通过DB获取各种数据
                    platDB.getToken();
                    platDB.getUserGender();
                    platDB.getUserIcon();
                    platDB.getUserId();
                    platDB.getUserName();

                    if (platform.getName().equals(QQ.NAME)) {
                        qqId = platDB.getUserId();
                        qqName = platDB.getUserName();
                    } else {
                        weChatId = platDB.getUserId();
                        weChatName = platDB.getUserName();
                    }
                    runOnUiThread(() -> login());
                }
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                LogUtil.e(throwable.toString());
            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });//授权回调监听，监听oncomplete，onerror，oncancel三种状态
        if (plat.isClientValid()) {
            //判断是否存在授权凭条的客户端，true是有客户端，false是无
        }
        if (plat.isAuthValid()) {
            //判断是否已经存在授权状态，可以根据自己的登录逻辑设置
            Toast.makeText(this, "已经授权过了", Toast.LENGTH_SHORT).show();
            return;
        }
        ShareSDK.setActivity(this);//抖音登录适配安卓9.0
        plat.showUser(null);    //要数据不要功能，主要体现在不会重复出现授权界面
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         if (requestCode == AppCode.BIND_PHONE && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
           login();
        }
    }


    @Override
    protected TextView getSendCodeTv() {
        return mSendCheckCodeTv;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.login_close:
               finish();
                break;
            case R.id.login_forget:
                phone = mPhoneEt.getText().toString();
                if (!phone.isEmpty() && phone.length() == 11) {
                    MyApp.app.activityTool.toPassChange(mContext, phone, 1);
                } else {
                    ToastUtils.toast(mContext, "请输入正确手机号");
                }

                break;
            case R.id.login_phone_clear:
                mPhoneEt.setText("");
                break;
            case R.id.login_wx:
                loginForQQWeChat(Wechat.NAME);
                break;
            case R.id.login_qq:
                loginForQQWeChat(QQ.NAME);
                break;
            case R.id.send_check_code_tv:
                sendCheckCode(getTextViewValue(mPhoneEt));
                break;
            case R.id.login_tv:
                qqId = null;
                qqName = null;
                weChatId = null;
                weChatName = null;
                phone = mPhoneEt.getText().toString();
                if (!phone.isEmpty() && phone.length() == 11) {
                    if (!mLoginStyleCb.isChecked()) {
                        //密码登录
                        pass = editPass.getText().toString();
                        if (!pass.isEmpty()) {
                            try {
                                //用公钥加密
                                byte[] encrypt = RSAUtils.encryptByPublicKey(pass.getBytes());
                                pass = Base64.encodeToString(encrypt, Base64.NO_WRAP);
                                login();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            ToastUtils.toast(mContext, "密码不能为空");
                        }
                    } else {
                        pass = null;
                        //验证码登录
                        if (TextUtils.isEmpty(getTextViewValue(mSmsCodeEt))) {
                            ToastUtils.toast(mContext, "请输入验证码");
                            return;
                        }
                        login();
                    }
                } else {
                    ToastUtils.toast(mContext, "请输入正确手机号");
                }
                break;
        }
    }
}
