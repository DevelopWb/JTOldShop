package com.juntai.shop.mall.ui.act;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.juntai.mall.base.base.BaseActivity;
import com.juntai.mall.base.utils.LogUtil;
import com.juntai.mall.base.utils.SPTools;
import com.juntai.mall.bdmap.utils.BaiDuLocationOneUtils;
import com.juntai.shop.mall.App;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.ui.goods.ShopActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

/**
 * Created by Ma
 * on 2019/8/23
 */
public class StartActivity extends AppCompatActivity {
    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        new RxPermissions(this)
                .request(permissions)
                .delay(1, TimeUnit.SECONDS)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        //所有权限通过
                    } else {
                        //有一个权限没通过
                    }

                    if (SPTools.getBoolean(StartActivity.this,"first_start",true)){
                        startActivity(new Intent(StartActivity.this, GuideActivity.class));
                    }else {
                        startActivity(new Intent(StartActivity.this, MainActivity.class));
                    }
                    finish();
                }, throwable -> {
                });
    }
}
