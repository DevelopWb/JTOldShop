package com.juntai.shop.mall.bean;

import com.juntai.mall.base.base.BaseResult;

/**
 * 订单创建
 * Created by Ma
 * on 2019/12/16
 */
public class OrderCreateBean extends BaseResult {

    /**
     * error : null
     * returnValue : {"id":122,"purchaserId":6,"merchantId":5,"addressId":6,"name":"测试2","sex":0,"phone":"10086","address":"贵州省黔南布依族苗族自治州惠水县斗底乡测试","totalPrices":null,"subtotal":null,"footing":220.3,"transportCharges":2,"orderFormNumber":"20191216153342906","establishTime":"2019-12-16 15:33:42","remark":"测试备注"}
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
         * id : 122
         * purchaserId : 6
         * merchantId : 5
         * addressId : 6
         * name : 测试2
         * sex : 0
         * phone : 10086
         * address : 贵州省黔南布依族苗族自治州惠水县斗底乡测试
         * totalPrices : null
         * subtotal : null
         * footing : 220.3
         * transportCharges : 2.0
         * orderFormNumber : 20191216153342906
         * establishTime : 2019-12-16 15:33:42
         * remark : 测试备注
         */

        private int id;
        private int purchaserId;
        private int merchantId;
        private int addressId;
        private String name;
        private String shopName;
        private int sex;
        private String phone;
        private String address;
        private double totalPrices;
        private double subtotal;
        private double footing;
        private double transportCharges;
        private String orderFormNumber;
        private String establishTime;
        private String remark;

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPurchaserId() {
            return purchaserId;
        }

        public void setPurchaserId(int purchaserId) {
            this.purchaserId = purchaserId;
        }

        public int getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(int merchantId) {
            this.merchantId = merchantId;
        }

        public int getAddressId() {
            return addressId;
        }

        public void setAddressId(int addressId) {
            this.addressId = addressId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public double getTotalPrices() {
            return totalPrices;
        }

        public void setTotalPrices(double totalPrices) {
            this.totalPrices = totalPrices;
        }

        public double getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(double subtotal) {
            this.subtotal = subtotal;
        }

        public double getFooting() {
            return footing;
        }

        public void setFooting(double footing) {
            this.footing = footing;
        }

        public double getTransportCharges() {
            return transportCharges;
        }

        public void setTransportCharges(double transportCharges) {
            this.transportCharges = transportCharges;
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
