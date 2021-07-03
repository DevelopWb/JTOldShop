package com.juntai.shop.mall.bean;

import com.google.gson.annotations.SerializedName;
import com.juntai.mall.base.base.BaseResult;

/**
 * Created by Ma
 * on 2020/1/7
 */
public class OrderPayWxBean extends BaseResult {

    /**
     * error : null
     * returnValue : {"nonce_str":"21c3134ee5edcb618c4f9aae358d73a7","code":200,"appid":"wx55706643d79cd22a","sign":"912C7CC0921FF01B666E6BB18F0A3049","prepayId":"wx07103627477921ae4d90a8cc1941846900","mch_id":"1573354791","info":"success","timestamp":"1578364482"}
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
         * nonce_str : 21c3134ee5edcb618c4f9aae358d73a7
         * code : 200
         * appid : wx55706643d79cd22a
         * sign : 912C7CC0921FF01B666E6BB18F0A3049
         * prepayId : wx07103627477921ae4d90a8cc1941846900
         * mch_id : 1573354791
         * info : success
         * timestamp : 1578364482
         */

        private String nonce_str;
        private String appid;
        private String sign;
        private String prepayId;
        private String packageVal;
        private String mch_id;
        private String info;
        private String timestamp;

        public String getNonce_str() {
            return nonce_str;
        }

        public String getPackageVal() {
            return packageVal;
        }

        public void setPackageVal(String packageVal) {
            this.packageVal = packageVal;
        }

        public void setNonce_str(String nonce_str) {
            this.nonce_str = nonce_str;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getPrepayId() {
            return prepayId;
        }

        public void setPrepayId(String prepayId) {
            this.prepayId = prepayId;
        }

        public String getMch_id() {
            return mch_id;
        }

        public void setMch_id(String mch_id) {
            this.mch_id = mch_id;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }
}
