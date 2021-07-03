package com.juntai.mall.im;

import android.os.Parcel;
import android.util.Log;

import io.rong.common.ParcelUtils;
import io.rong.common.RLog;
import io.rong.imlib.DestructionTag;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MentionedInfo;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 案件/任务  推送消息
 */
@DestructionTag
@MessageTag(value = "app:noteComment", flag = MessageTag.NONE)
public class CustomMessage extends MessageContent {
    private static final String TAG = "CustomMessage";
    private String content;//推送内容
    private String extra;//附加内容
    private Integer channel;//1发货推送；2退款推送；3拒绝退款推送
    private Integer id;//相关id
    public static final Creator<CustomMessage> CREATOR = new Creator<CustomMessage>() {
        public CustomMessage createFromParcel(Parcel source) {
            return new CustomMessage(source);
        }

        public CustomMessage[] newArray(int size) {
            return new CustomMessage[size];
        }
    };

    public String getContent() {
        return content;
    }

    public String getExtra() {
        return extra;
    }

    public Integer getChannel() {
        return channel;
    }

    public Integer getId() {
        return id;
    }

    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("content", content);
            jsonObj.put("extra", extra);
            jsonObj.put("channel", channel);
            jsonObj.put("id", id);
        } catch (JSONException var4) {
            RLog.e("CustomMessage", "JSONException " + var4.getMessage());
        }

        try {
            return jsonObj.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException var3) {
            RLog.e("CustomMessage", "UnsupportedEncodingException ", var3);
            return null;
        }
    }

    public CustomMessage(byte[] data) {
        String jsonStr = null;

        try {
            if (data != null && data.length >= 40960) {
                RLog.e("CustomMessage", "TextMessage length is larger than 40KB, length :" + data.length);
            }
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException var5) {
            RLog.e("CustomMessage", "UnsupportedEncodingException ", var5);
        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            if (jsonObj.has("content")) {
                content = jsonObj.optString("content");
            }

            if (jsonObj.has("extra")) {
                extra = jsonObj.optString("extra");
            }

            if (jsonObj.has("channel")) {
                channel = jsonObj.optInt("channel");
            }

            if (jsonObj.has("id")) {
                id = jsonObj.optInt("id");
            }
        } catch (JSONException var4) {
            RLog.e("TextMessage", "JSONException " + var4.getMessage());
        }

    }

    public int describeContents() {
        return 0;
    }

    /**
     * 顺序和下边方法要对应
     * @param dest
     * @param flags
     */
    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, channel);
        ParcelUtils.writeToParcel(dest, id);
        ParcelUtils.writeToParcel(dest, extra);
        ParcelUtils.writeToParcel(dest, content);
    }

    public CustomMessage(Parcel in) {
        channel = ParcelUtils.readIntFromParcel(in);
        id = ParcelUtils.readIntFromParcel(in);
        extra = ParcelUtils.readFromParcel(in);
        content = ParcelUtils.readFromParcel(in);
    }

    @Override
    public String toString() {
        return "CustomMessage{" +
                "content='" + content + '\'' +
                ", extra='" + extra + '\'' +
                ", channel=" + channel +
                ", id=" + id +
                '}';
    }

}