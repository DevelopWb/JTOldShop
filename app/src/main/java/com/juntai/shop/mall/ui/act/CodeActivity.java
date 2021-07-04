package com.juntai.shop.mall.ui.act;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.juntai.mall.base.base.BaseActivity;
import com.juntai.mall.base.utils.LogUtil;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.shop.mall.MyApp;
import com.juntai.shop.mall.R;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * 获取验证码
 * Created by Ma
 * on 2019/11/12
 */
public class CodeActivity extends BaseActivity {
    EditText editText1,editText2,editText3,editText4,nowEdittext;
    String phone;
    TextView tvHint;
    public int codeOverTime = 60;//验证码获取剩余时间
    @Override
    public int getLayoutView() {
        return R.layout.activity_code;
    }
    @Override
    public void initView() {
        mBaseRootCol.setBackgroundResource(R.color.content_layout);
        getToolbar().setVisibility(View.GONE);
        editText1 = findViewById(R.id.code_edit_1);
        editText2 = findViewById(R.id.code_edit_2);
        editText3 = findViewById(R.id.code_edit_3);
        editText4 = findViewById(R.id.code_edit_4);
        tvHint = findViewById(R.id.code_time);
        findViewById(R.id.code_close).setOnClickListener(v -> finish());
        editText1.setOnTouchListener(onTouchListener);
        editText2.setOnTouchListener(onTouchListener);
        editText3.setOnTouchListener(onTouchListener);
        editText4.setOnTouchListener(onTouchListener);
        nowEdittext = editText1;
        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1){
                    viewSet(editText2);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1){
                    viewSet(editText3);
                }else if (count == 0){
                    viewSet(editText1);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        editText3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1){
                    viewSet(editText4);
                }else if (count == 0){
                    viewSet(editText2);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        editText4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1){
                    //验证
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(editText1.getText().toString())
                            .append(editText2.getText().toString())
                            .append(editText3.getText().toString())
                            .append(editText4.getText().toString());
                    SMSSDK.submitVerificationCode("+86", phone, stringBuffer.toString());
                }else if (count == 0){
                    viewSet(editText3);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        InputMethodManager inputManager =
                (InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText1, 0);
    }
    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (v == nowEdittext) return false;
            else return true;
        }
    };
    public void viewSet(EditText open){
        nowEdittext = open;
        open.setFocusable(true);
        open.setFocusableInTouchMode(true);
        open.requestFocus();
    }
    @Override
    public void initData() {
        phone = getIntent().getStringExtra("phone");
        getCode();
    }

    public void getCode(){
        SMSSDK.registerEventHandler(eventHandler);// 注册一个事件回调，用于处理SMSSDK接口请求的结果
        // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
        SMSSDK.getVerificationCode("+86", phone, (s, s1) -> false);
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
                    setResult(RESULT_OK);
                    finish();
                    //提交验证码成功
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    runOnUiThread(() -> {
                        ToastUtils.toast(CodeActivity.this,"验证码已发送");
                    });
                    MyApp.app.codeOverTime = System.currentTimeMillis();
                    new Thread(() -> {
                        for (int i = 0; i < 59; i++) {
                            try {
                                Thread.sleep(1000);
                                codeOverTime = 59 - i;
                                if (codeOverTime == 0){
                                    tvHint.setText("");
                                    return;
                                }else {
                                    runOnUiThread(() -> {
                                        tvHint.setText(String.format("%s秒后重新获取验证码",codeOverTime));
                                    });
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    LogUtil.d(TAG+ "获取验证码成功");
                    //获取验证码成功
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //返回支持发送验证码的国家列表
                }
            }else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE && result ==0){
                runOnUiThread(() -> {
                    ToastUtils.toast(CodeActivity.this,"验证码错误");
                });
                LogUtil.d(TAG+ "验证码错误");
            }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE && result ==0){
                runOnUiThread(() -> {
                    ToastUtils.toast(CodeActivity.this
                            ,"获取验证码失败");
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
