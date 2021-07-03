package com.juntai.mall.im.ry.listener;

import android.content.Intent;

import com.juntai.mall.base.app.BaseApplication;
import com.juntai.mall.base.utils.ActionConfig;
import com.juntai.mall.base.utils.LogUtil;
import com.juntai.mall.im.CustomMessage;

import org.greenrobot.eventbus.EventBus;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

/**
 * 消息监听
 * @aouther Ma
 * @date 2019/4/6
 */
public class MyOnReceiveMessageListener implements RongIMClient.OnReceiveMessageListener {
    @Override
    public boolean onReceived(Message message, int i) {
        LogUtil.d("RongYun-消息监听---i ==" + i + "    " + message.toString());
        //推送消息类型（1发货推送；2退款推送；3拒绝退款推送）
        if(message.getConversationType() == Conversation.ConversationType.SYSTEM){
            if(message.getObjectName().equals("app:noteComment")){
//                LogUtil.d("RongYun-消息监听---i =游记评论="+ ((CustomMessage) message.getContent()).toString());
                CustomMessage caseMessage = (CustomMessage) message.getContent();
                EventBus.getDefault().post(caseMessage);
            }
        }
        return false;
    }
}
