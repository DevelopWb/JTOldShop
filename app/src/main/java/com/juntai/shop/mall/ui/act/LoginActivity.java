package com.juntai.shop.mall.ui.act;

import android.content.Intent;
import android.text.InputType;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.juntai.mall.base.base.BaseActivity;
import com.juntai.mall.base.utils.GsonTools;
import com.juntai.mall.base.utils.LogUtil;
import com.juntai.mall.base.utils.SPTools;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.mall.base.widght.ProgressDialog;
import com.juntai.mall.im.ModuleIm_Init;
import com.juntai.shop.mall.MyApp;
import com.juntai.shop.mall.AppNetModule;
import com.juntai.shop.mall.R;
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
 * @aouther Ma
 * @date 2019/3/4
 */
public class LoginActivity extends BaseActivity {
    TextInputEditText editPhone,editPass;
    boolean logining = false;
    Button loginBtn;
    ProgressDialog progressDialog;
    CheckBox checkBox,passV;//选中状态-密码登录
    LinearLayout linearLayout;
    TextView tvHint;
    @Override
    public int getLayoutView() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        mBaseRootCol.setBackgroundResource(R.color.content_layout);
        getToolbar().setVisibility(View.GONE);
        findViewById(R.id.login_code).setOnClickListener(v -> {
//            sendCode(mContext);
        });
        editPhone = findViewById(R.id.login_phone);
        editPass = findViewById(R.id.login_pass);
        passV = findViewById(R.id.login_pass_v);
        loginBtn = findViewById(R.id.login_btn);
        linearLayout = findViewById(R.id.pass_layout);
        tvHint = findViewById(R.id.login_hint);
        progressDialog = new ProgressDialog(mContext);
        loginBtn.setOnClickListener(v -> {
            qqId = null;
            qqName = null;
            weChatId = null;
            weChatName = null;
            phone = editPhone.getText().toString();
            if (!phone.isEmpty() && phone.length() == 11){
                if (checkBox.isChecked()){
                    //密码登录
                    pass = editPass.getText().toString();
                    if (!pass.isEmpty()){
                        try {
                            //用公钥加密
                            byte[] encrypt = RSAUtils.encryptByPublicKey(pass.getBytes());
                            pass = Base64.encodeToString(encrypt,Base64.NO_WRAP);
                            login();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }else {
                        ToastUtils.toast(mContext,"密码不能为空");
                    }
                }else {
                    pass = null;
                    getCode(AppCode.SSM_CODE_LOGIN);
                }
            }else {
                ToastUtils.toast(mContext,"请输入正确手机号");
            }
        });
        checkBox = findViewById(R.id.login_code);
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                loginBtn.setText("登陆");
                checkBox.setText("短信验证码登录");
                linearLayout.setVisibility(View.VISIBLE);
                tvHint.setVisibility(View.GONE);
            }else {
                editPass.setText("");
                pass = null;
                loginBtn.setText("获取短信验证码");
                checkBox.setText("密码登录");
                linearLayout.setVisibility(View.GONE);
                tvHint.setVisibility(View.VISIBLE);
                passV.setChecked(false);
            }
        });
        passV.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                editPass.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }else {
                editPass.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });
        findViewById(R.id.login_forget).setOnClickListener(v -> {
            phone = editPhone.getText().toString();
            if (!phone.isEmpty() && phone.length() == 11){
                getCode(AppCode.SSM_CODE_FORGET);
            }else {
                ToastUtils.toast(mContext,"请输入正确手机号");
            }
        });
        findViewById(R.id.login_qq).setOnClickListener(v -> {
            loginForQQWeChat(QQ.NAME);
        });
        findViewById(R.id.login_wx).setOnClickListener(v -> {
            loginForQQWeChat(Wechat.NAME);
        });
        findViewById(R.id.login_close).setOnClickListener(v -> finish());
        findViewById(R.id.login_phone_clear).setOnClickListener(v -> {
            editPhone.setText("");
        });
    }

    public void getCode(int code){
        //验证码登陆
        int time = (int) ((System.currentTimeMillis() - MyApp.app.codeOverTime) / 1000);
        if (60 - time > 0){
            //验证码不可获取直接返回
            ToastUtils.toast(mContext,String.format("请在%s秒后重新获取",(60 - time)));
            return;
        }
        startActivityForResult(new Intent(mContext, CodeActivity.class)
                .putExtra("phone",editPhone.getText().toString()), code);
    }
    @Override
    public void initData() {

    }

    String phone,pass,weChatId,weChatName,qqId,qqName;

    /**
     * 登陆
     */
    public void login(){
        logining = true;
        progressDialog.show();
        AppNetModule.createrRetrofit()
                .login(phone,pass,weChatId,weChatName,qqId,qqName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {
                    LoginBean userBean;
                    String loginStr = responseBody.string();
                    userBean = GsonTools.changeGsonToBean(loginStr, LoginBean.class);
                    if (userBean.success) {
                        ToastUtils.success(mContext, "登录成功");
                        Hawk.put(HawkProperty.USER_INFO,userBean.getReturnValue());
                        SPTools.saveString(MyApp.app, AppUtils.SP_KEY_LOGIN, loginStr);
                        MyApp.app.setUserBean(null);
                        ModuleIm_Init.connectIM(MyApp.app.getUser().getReturnValue().getrOngYunToken());
                        setResult(RESULT_OK);
                        finish();
                        LogUtil.d("token=" + MyApp.app.getUserToken());
                        LogUtil.d("token=" + userBean.getReturnValue().getAccount());
                    } else {
                        if (userBean.code == 302){
                            startActivityForResult(new Intent(mContext,PhoneBindActivity.class)
                                    .putExtra("qqId",qqId)
                                    .putExtra("qqName",qqName),
                                    AppCode.BIND_PHONE);
                        }else if (userBean.code == 301){
                            startActivityForResult(new Intent(mContext,PhoneBindActivity.class)
                                            .putExtra("weChatId",weChatId)
                                            .putExtra("weChatName",weChatName),
                                    AppCode.BIND_PHONE);
                        }
                        ToastUtils.toast(mContext, userBean.error);
                    }
                    logining = false;
                    progressDialog.dismiss();
                }, throwable -> {
                    LogUtil.d(throwable.toString());
                    LogUtil.d("token=" + throwable.toString());
                    ToastUtils.error(mContext, "登录失败");
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
//                    Log.e("userinfo", "token=" + platDB.getToken());
//                    Log.e("userinfo", "性别=" + platDB.getUserGender());
//                    Log.e("userinfo", "image=" + platDB.getUserIcon());
//                    Log.e("userinfo", "id=" + platDB.getUserId());
//                    Log.e("userinfo", "name=" + platDB.getUserName());

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppCode.SSM_CODE_LOGIN && resultCode == RESULT_OK) {
            login();
        }else if (requestCode == AppCode.SSM_CODE_FORGET && resultCode == RESULT_OK) {
            MyApp.app.activityTool.toPassChange(mContext,phone,1);
        }else if (requestCode == AppCode.BIND_PHONE && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }
}
