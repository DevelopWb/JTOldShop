package com.juntai.mall.im.ry;

import android.content.Context;

import com.juntai.mall.base.utils.LogUtil;

import io.rong.push.PushType;
import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

/**
 * Created by Ma
 * on 2019/4/13
 */
public class MyNotificationReceiver extends PushMessageReceiver {
    @Override
    public boolean onNotificationMessageArrived(Context context, PushType pushType, PushNotificationMessage pushNotificationMessage) {
        LogUtil.d("RongYun: 通知栏消息: "  + pushNotificationMessage.getObjectName() + "  content: " +pushNotificationMessage.getPushContent());
        //用来接收服务器发来的通知栏消息
        return false;
    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushType pushType, PushNotificationMessage pushNotificationMessage) {
        LogUtil.d("RongYun: 点击通知栏消息: "  + pushNotificationMessage.getObjectName() + "  content: " +pushNotificationMessage.getPushContent());
        //用户点击通知栏消息时触发
        return false;
    }
}
