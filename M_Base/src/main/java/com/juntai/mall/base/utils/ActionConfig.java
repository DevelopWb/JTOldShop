package com.juntai.mall.base.utils;

import com.juntai.mall.base.app.BaseApplication;

/**
 * activity - action
 */
public class ActionConfig {
    /**登录*/
    //public static String ACTION_LOGIN = BaseApplication.app.getPackageName() + ".login";
    /**地图查看*/
    public final static String ACTION_LOCATION_LOOK = BaseApplication.app.getPackageName() + ".im.location.look";
    /**地图选择*/
    public final static String ACTION_LOCATION_SELTION = BaseApplication.app.getPackageName() + ".im.location.seltion";

    /*=====================================广播==================================*/
    /**需要重新登录*/
    public static final String BROAD_LOGIN = BaseApplication.app.getPackageName() + ".login_error";
    /**需要重新登录*/
    public static final String BROAD_VIDEO = BaseApplication.app.getPackageName() + ".VideoBroadcastReceiver";
    /**案件详情*/
    public static final String BROAD_CASE_DETAILS = BaseApplication.app.getPackageName() + ".CaseDetails";
    /**评论/点赞通知*/
    public static final String BROAD_COMMENT = BaseApplication.app.getPackageName() + ".ExistComment";

    /*====================================服务====================================*/
    /**地图选择*/
    public final static String ACTION_SERVICE_LOCATION = BaseApplication.app.getPackageName() + ".service.location";
}
