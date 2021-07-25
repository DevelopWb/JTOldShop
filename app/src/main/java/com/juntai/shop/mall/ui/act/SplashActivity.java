package com.juntai.shop.mall.ui.act;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.juntai.mall.base.utils.SPTools;
import com.juntai.shop.mall.MainActivity;
import com.juntai.shop.mall.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.concurrent.TimeUnit;

/**
 * Created by Ma
 * on 2019/8/23
 */
public class SplashActivity extends AppCompatActivity {
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

                    if (SPTools.getBoolean(SplashActivity.this,"first_start",true)){
                        startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    }else {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    }
                    finish();
                }, throwable -> {
                });
    }
}
