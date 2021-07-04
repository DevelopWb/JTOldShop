package com.juntai.shop.mall.ui.act;

import android.text.InputType;
import android.util.Base64;
import android.widget.CheckBox;
import android.widget.EditText;

import com.juntai.mall.base.base.BaseActivity;
import com.juntai.mall.base.base.BaseObserver;
import com.juntai.mall.base.base.BaseResult;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.shop.mall.MyApp;
import com.juntai.shop.mall.AppNetModule;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.utils.RSAUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 修改密码
 * Created by Ma
 * on 2019/8/24
 */
public class PassChangeActivity extends BaseActivity {
    EditText editText1,editText2;
    CheckBox checkBox1,checkBox2;
    int type = 0;
    String phone;
    @Override
    public int getLayoutView() {
        return R.layout.activity_passchange;
    }

    @Override
    public void initView() {
        type = getIntent().getIntExtra("type",0);
        switch (type){
            case 0:
                finish();
                break;
            case 1:
                setTitleName("重置密码");
                phone = getIntent().getStringExtra("phone");
                break;
            case 2:
                setTitleName("修改密码");
                break;
        }


        editText1 = findViewById(R.id.pass_change_pass1);
        editText2 = findViewById(R.id.pass_change_pass2);
        checkBox1 = findViewById(R.id.pass_change_check1);
        checkBox2 = findViewById(R.id.pass_change_check2);

        findViewById(R.id.pass_change_submit).setOnClickListener(v -> {
            if (editText1.getText().toString().isEmpty() && editText2.getText().toString().isEmpty()){
                ToastUtils.toast(mContext,"密码不能为空");
            }else if (editText1.getText().toString().equals(editText2.getText().toString())){
                try {
                    //用公钥加密
                    byte[] encrypt = RSAUtils.encryptByPublicKey(editText2.getText().toString().getBytes());
                    String pass = Base64.encodeToString(encrypt,Base64.NO_WRAP);
                    edit(pass);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                ToastUtils.toast(mContext,"密码不一致");
            }
        });

        checkBox1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                editText1.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }else {
                editText1.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });
        checkBox2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                editText2.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }else {
                editText2.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });
    }

    @Override
    public void initData() {

    }
    public void edit(String pass){
        Observable<BaseResult> observable;
        if (type == 1){
            observable = AppNetModule.createrRetrofit().findPass(phone,pass);
        }else {
            observable = AppNetModule.createrRetrofit().editPass(MyApp.app.getAccount(), MyApp.app.getUserToken(),pass);
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResult>(null) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        ToastUtils.success(mContext, result.msg);
                        finish();
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtils.toast(mContext,msg);
                    }
                });
    }
}
