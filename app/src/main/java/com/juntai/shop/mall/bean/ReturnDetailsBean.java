package com.juntai.shop.mall.bean;

import com.juntai.mall.base.base.BaseResult;

import java.util.List;

/**
 * 退款退货详情
 * Created by Ma
 * on 2019/12/24
 */
public class ReturnDetailsBean extends BaseResult {

    /**
     * error : null
     * returnValue : {"returnId":8,"shopName":"小林水产","orderId":175,"orderFormNumber":"20191225113516465","causeName":"其他","returnPrice":596.1,"returnCommodityNum":null,"shopAccount":"j822mh","phone":"15615691823","merchantIsAgreeStatus":null,"refundTime":"2019-12-25 12:00:00","merchantConfirmTime":null,"succeedTime":null,"returnCommodity":[{"commodityId":4,"commodityName":"女装牛仔裤","commodityImg":"/commodityImg/c9a3ea735886a27d98a8b3547353120.jpg","price":99.1,"commodityNumber":1},{"commodityId":4,"commodityName":"女装牛仔裤","commodityImg":"/commodityImg/c9a3ea735886a27d98a8b3547353120.jpg","price":99.3,"commodityNumber":1}]}
     * msg : null
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
         * returnId : 8
         * shopName : 小林水产
         * orderId : 175
         * orderFormNumber : 20191225113516465
         * causeName : 其他
         * returnPrice : 596.1
         * returnCommodityNum : null
         * shopAccount : j822mh
         * phone : 15615691823
         * merchantIsAgreeStatus : null
         * refundTime : 2019-12-25 12:00:00
         * merchantConfirmTime : null
         * succeedTime : null
         * returnCommodity : [{"commodityId":4,"commodityName":"女装牛仔裤","commodityImg":"/commodityImg/c9a3ea735886a27d98a8b3547353120.jpg","price":99.1,"commodityNumber":1},{"commodityId":4,"commodityName":"女装牛仔裤","commodityImg":"/commodityImg/c9a3ea735886a27d98a8b3547353120.jpg","price":99.3,"commodityNumber":1}]
         */

        private int returnId;
        private String shopName;
        private int shopUserId;
        private int orderId;
        private String orderFormNumber;
        private String causeName;
        private double returnPrice;
        private String shopAccount;
        private String phone;
        private int merchantIsAgreeStatus;//商家是否同意退款状态（0同意；1拒绝）
        private int ultimatelyStatus;//退款状态（0成功；1失败）
        private String refundTime;
        private String merchantConfirmTime;
        private String ultimatelyTime;
        private List<OrderCommodityListBean> returnCommodity;

        public int getShopUserId() {
            return shopUserId;
        }

        public void setShopUserId(int shopUserId) {
            this.shopUserId = shopUserId;
        }

        public int getReturnId() {
            return returnId;
        }

        public void setReturnId(int returnId) {
            this.returnId = returnId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getOrderFormNumber() {
            return orderFormNumber;
        }

        public void setOrderFormNumber(String orderFormNumber) {
            this.orderFormNumber = orderFormNumber;
        }

        public String getCauseName() {
            return causeName;
        }

        public void setCauseName(String causeName) {
            this.causeName = causeName;
        }

        public double getReturnPrice() {
            return returnPrice;
        }

        public void setReturnPrice(double returnPrice) {
            this.returnPrice = returnPrice;
        }

        public String getShopAccount() {
            return shopAccount;
        }

        public void setShopAccount(String shopAccount) {
            this.shopAccount = shopAccount;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getMerchantIsAgreeStatus() {
            return merchantIsAgreeStatus;
        }

        public void setMerchantIsAgreeStatus(int merchantIsAgreeStatus) {
            this.merchantIsAgreeStatus = merchantIsAgreeStatus;
        }

        public String getRefundTime() {
            return refundTime;
        }

        public void setRefundTime(String refundTime) {
            this.refundTime = refundTime;
        }

        public String getMerchantConfirmTime() {
            return merchantConfirmTime;
        }

        public void setMerchantConfirmTime(String merchantConfirmTime) {
            this.merchantConfirmTime = merchantConfirmTime;
        }

        public int getUltimatelyStatus() {
            return ultimatelyStatus;
        }

        public void setUltimatelyStatus(int ultimatelyStatus) {
            this.ultimatelyStatus = ultimatelyStatus;
        }

        public String getUltimatelyTime() {
            return ultimatelyTime;
        }

        public void setUltimatelyTime(String ultimatelyTime) {
            this.ultimatelyTime = ultimatelyTime;
        }

        public List<OrderCommodityListBean> getReturnCommodity() {
            return returnCommodity;
        }

        public void setReturnCommodity(List<OrderCommodityListBean> returnCommodity) {
            this.returnCommodity = returnCommodity;
        }
    }
}
