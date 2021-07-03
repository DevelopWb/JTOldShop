package com.juntai.shop.mall.bean;

import com.juntai.mall.base.base.BaseResult;

/**
 * 摄像头详情
 * Created by Ma
 * on 2019/12/23
 */
public class CameraDetailsBean extends BaseResult {

    /**
     * error : null
     * returnValue : {"id":65,"number":"638187229","remark":"沂河","place":"山东省临沂市沂水县滨河西路","imgUrl":"0","maccountId":6,"account":"13656419301","appKey":"f1ddf5933add421db2f09e1f4bb1dc9d","accessToken":"at.7abv2z2s3vq420x25om626s266kgtysu-6pliyd6ivf-0hcmfdv-i0iglprdd","hlshd":"http://hls01open.ys7.com/openlive/9243b5aba21c455c86c40407b510b9b0.hd.m3u8","rtmphd":"rtmp://rtmp01open.ys7.com/openlive/9243b5aba21c455c86c40407b510b9b0.hd"}
     * msg : null
     * code : null
     * type : null
     */

    private ReturnValueBean returnValue;

    public ReturnValueBean getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(ReturnValueBean returnValue) {
        this.returnValue = returnValue;
    }

    public static class ReturnValueBean {
        /**
         * id : 65
         * number : 638187229
         * remark : 沂河
         * place : 山东省临沂市沂水县滨河西路
         * imgUrl : 0
         * maccountId : 6
         * account : 13656419301
         * appKey : f1ddf5933add421db2f09e1f4bb1dc9d
         * accessToken : at.7abv2z2s3vq420x25om626s266kgtysu-6pliyd6ivf-0hcmfdv-i0iglprdd
         * hlshd : http://hls01open.ys7.com/openlive/9243b5aba21c455c86c40407b510b9b0.hd.m3u8
         * rtmphd : rtmp://rtmp01open.ys7.com/openlive/9243b5aba21c455c86c40407b510b9b0.hd
         */

        private int id;
        private String number;
        private String remark;
        private String place;
        private String imgUrl;
        private int maccountId;
        private String account;
        private String appKey;
        private String accessToken;
        private String hlshd;
        private String rtmphd;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public int getMaccountId() {
            return maccountId;
        }

        public void setMaccountId(int maccountId) {
            this.maccountId = maccountId;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getAppKey() {
            return appKey;
        }

        public void setAppKey(String appKey) {
            this.appKey = appKey;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getHlshd() {
            return hlshd;
        }

        public void setHlshd(String hlshd) {
            this.hlshd = hlshd;
        }

        public String getRtmphd() {
            return rtmphd;
        }

        public void setRtmphd(String rtmphd) {
            this.rtmphd = rtmphd;
        }
    }
}
