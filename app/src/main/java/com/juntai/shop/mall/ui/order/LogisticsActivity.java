package com.juntai.shop.mall.ui.order;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.widget.TextView;

import com.juntai.mall.base.base.BaseActivity;
import com.juntai.mall.base.base.BaseObserver;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.shop.mall.App;
import com.juntai.shop.mall.AppNetModule;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.LogisticsBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 物流
 * Created by Ma
 * on 2019/12/9
 */
public class LogisticsActivity extends BaseActivity {
    TextView tvName,tvNumber,tvLink;
    int id;
    private ClipboardManager cm;
    private ClipData mClipData;
    @Override
    public int getLayoutView() {
        return R.layout.activity_logistics;
    }

    @Override
    public void initView() {
        setTitleName("物流");
        id = getIntent().getIntExtra("id",-1);
        if (id == -1) finish();
        tvName = findViewById(R.id.logistics_name);
        tvNumber = findViewById(R.id.logistics_number);
        tvLink = findViewById(R.id.logistics_link);
        findViewById(R.id.logistics_copy).setOnClickListener(v -> {
            cm = (ClipboardManager) getSystemService(mContext.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            mClipData = ClipData.newPlainText("Label", tvNumber.getText().toString());
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            ToastUtils.toast(mContext,"已复制");
        });
        tvLink.setOnClickListener(v -> {
            Uri uri = Uri.parse(tvLink.getText().toString());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
    }

    @Override
    public void initData() {
        AppNetModule.createrRetrofit()
                .logistics(App.app.getAccount(), App.app.getUserToken(),id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<LogisticsBean>() {
                    @Override
                    public void onSuccess(LogisticsBean result) {
                        tvName.setText(result.getReturnValue().getExpressAge());
                        tvNumber.setText(result.getReturnValue().getExpressAgeNumber());
                        tvLink.setText(result.getReturnValue().getExpressAgeLink());
                        tvLink.setText("https://www.baidu.com");
                    }
                    @Override
                    public void onError(String msg) {
                        ToastUtils.toast(mContext,msg);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }
                });
    }
}
