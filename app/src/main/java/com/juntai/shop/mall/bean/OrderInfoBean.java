package com.juntai.shop.mall.bean;

import com.juntai.mall.base.base.BaseResult;

import java.util.List;

/**
 * Created by Ma
 * on 2019/12/9
 */
public class OrderInfoBean extends BaseResult {

    /**
     * error : null
     * returnValue : {"id":40,"shopId":5,"shopAccount":"j822mh","name":"小林水产","footing":498.4,"orderFormNumber":"20191202165552205","establishTime":"2019-12-02 16:55:52","paymentTime":null,"shipmentsTime":null,"status":6,"commodityList":[{"id":58,"commodityId":4,"commodityName":"女装牛仔裤","commodityImg":"/commodityImg/c9a3ea735886a27d98a8b3547353120.jpg","commodityNumber":3,"price":99.2},{"id":59,"commodityId":4,"commodityName":"女装牛仔裤","commodityImg":"/commodityImg/c9a3ea735886a27d98a8b3547353120.jpg","commodityNumber":2,"price":99.4}]}
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
         * id : 40
         * shopId : 5
         * shopAccount : j822mh
         * name : 小林水产
         * footing : 498.4
         * orderFormNumber : 20191202165552205
         * establishTime : 2019-12-02 16:55:52
         * paymentTime : null
         * shipmentsTime : null
         * status : 6
         * commodityList : [{"id":58,"commodityId":4,"commodityName":"女装牛仔裤","commodityImg":"/commodityImg/c9a3ea735886a27d98a8b3547353120.jpg","commodityNumber":3,"price":99.2},{"id":59,"commodityId":4,"commodityName":"女装牛仔裤","commodityImg":"/commodityImg/c9a3ea735886a27d98a8b3547353120.jpg","commodityNumber":2,"price":99.4}]
         */

        private int orderId;
        private int shopId;
        private String shopAccount;
        private String shopName;
        private int shopUserId;
        private double sumPackingCharges;
        private double transportCharges;
        private double footing;
        private String orderFormNumber;
        private String establishTime;
        private String paymentTime;
        private String shipmentsTime;
        private String contactsPhone;
        private int status;
        private List<OrderCommodityListBean> commodityList;

        public int getShopUserId() {
            return shopUserId;
        }

        public void setShopUserId(int shopUserId) {
            this.shopUserId = shopUserId;
        }

        public double getSumPackingCharges() {
            return sumPackingCharges;
        }

        public double getTransportCharges() {
            return transportCharges;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getContactsPhone() {
            return contactsPhone;
        }

        public void setContactsPhone(String contactsPhone) {
            this.contactsPhone = contactsPhone;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public String getShopAccount() {
            return shopAccount;
        }

        public void setShopAccount(String shopAccount) {
            this.shopAccount = shopAccount;
        }

        public double getFooting() {
            return footing;
        }

        public void setFooting(double footing) {
            this.footing = footing;
        }

        public String getOrderFormNumber() {
            return orderFormNumber;
        }

        public void setOrderFormNumber(String orderFormNumber) {
            this.orderFormNumber = orderFormNumber;
        }

        public String getEstablishTime() {
            return establishTime;
        }

        public void setEstablishTime(String establishTime) {
            this.establishTime = establishTime;
        }

        public String getPaymentTime() {
            return paymentTime;
        }

        public void setPaymentTime(String paymentTime) {
            this.paymentTime = paymentTime;
        }

        public String getShipmentsTime() {
            return shipmentsTime;
        }

        public void setShipmentsTime(String shipmentsTime) {
            this.shipmentsTime = shipmentsTime;
        }

        public List<OrderCommodityListBean> getCommodityList() {
            return commodityList;
        }

        public void setCommodityList(List<OrderCommodityListBean> commodityList) {
            this.commodityList = commodityList;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

    }
}
