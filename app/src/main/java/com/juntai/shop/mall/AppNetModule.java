package com.juntai.shop.mall;

import com.juntai.mall.base.net.ApiRetrofit;
import com.juntai.mall.base.net.ApiRetrofit;

/**
 * 网络请求
 */
public class AppNetModule {
    static AppServer appServer ;
    public static AppServer createrRetrofit() {
        if (appServer == null){
            appServer = ApiRetrofit.getInstance().getApiService(AppServer.class);
        }
        return appServer;
    }
}
