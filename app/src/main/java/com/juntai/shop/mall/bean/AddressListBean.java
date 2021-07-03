package com.juntai.shop.mall.bean;

import com.juntai.mall.base.base.BaseResult;

import java.util.List;

/**
 * @aouther Ma
 * @date 2019/3/9
 */
public class AddressListBean extends BaseResult {

    /**
     * error : null
     * returnValue : [{"id":5,"name":"测试地址","phone":"10086","provinceName":"福建省","cityName":"南平市","areaName":"建阳市","streetName":"将口镇","detailedAddress":"测试的","defaultAddress":0},{"id":3,"name":"张三","phone":"223322","provinceName":"山东省","cityName":"临沂市","areaName":"河东区","streetName":"九曲街道","detailedAddress":"大的刚发的给他还是图","defaultAddress":0},{"id":6,"name":"测试2","phone":"10086","provinceName":"贵州省","cityName":"黔南布依族苗族自治州","areaName":"惠水县","streetName":"斗底乡","detailedAddress":"测试","defaultAddress":1}]
     * msg : null
     * code : null
     * type : null
     */

    private List<ReturnValueBean> returnValue;

    public List<ReturnValueBean> getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(List<ReturnValueBean> returnValue) {
        this.returnValue = returnValue;
    }

    public static class ReturnValueBean {
        /**
         * id : 5
         * name : 测试地址
         * phone : 10086
         * provinceName : 福建省
         * cityName : 南平市
         * areaName : 建阳市
         * streetName : 将口镇
         * detailedAddress : 测试的
         * defaultAddress : 0
         */

        private int id;
        private String name;
        private String phone;
        private String provinceName;
        private String cityName;
        private String areaName;
        private String label;
        private String streetName;
        private String detailedAddress;
        private int defaultAddress;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public int getDefaultAddress() {
            return defaultAddress;
        }

        public void setDefaultAddress(int defaultAddress) {
            this.defaultAddress = defaultAddress;
        }
    }
}
