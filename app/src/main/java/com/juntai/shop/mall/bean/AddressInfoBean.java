package com.juntai.shop.mall.bean;

import com.juntai.mall.base.base.BaseResult;

/**
 * Created by Ma
 * on 2019/12/17
 */
public class AddressInfoBean extends BaseResult {

    /**
     * error : null
     * returnValue : {"id":6,"purchaserId":6,"name":"测试2","sex":0,"phone":"10086","provinceName":"贵州省","cityName":"黔南布依族苗族自治州","areaName":"惠水县","streetName":"斗底乡","detailedAddress":"测试","label":"家","defaultAddress":1}
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
         * id : 6
         * purchaserId : 6
         * name : 测试2
         * sex : 0
         * phone : 10086
         * provinceName : 贵州省
         * cityName : 黔南布依族苗族自治州
         * areaName : 惠水县
         * streetName : 斗底乡
         * detailedAddress : 测试
         * label : 家
         * defaultAddress : 1
         */

        private int id;
        private int purchaserId;
        private String name;
        private int sex;
        private String phone;
        private String provinceName;
        private String cityName;
        private String areaName;
        private String streetName;
        private String detailedAddress;
        private String label;
        private int defaultAddress;

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

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getStreetName() {
            return streetName;
        }

        public void setStreetName(String streetName) {
            this.streetName = streetName;
        }

        public String getDetailedAddress() {
            return detailedAddress;
        }

        public void setDetailedAddress(String detailedAddress) {
            this.detailedAddress = detailedAddress;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public int getDefaultAddress() {
            return defaultAddress;
        }

        public void setDefaultAddress(int defaultAddress) {
            this.defaultAddress = defaultAddress;
        }
    }
}
