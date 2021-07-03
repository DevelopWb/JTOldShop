package com.juntai.shop.mall.bean;

import com.juntai.mall.base.base.BaseResult;

import java.util.List;

/**
 * Created by Ma
 * on 2019/11/14
 */
public class ShopLocationB extends BaseResult {
    /**
     * error : null
     * returnValue : [{"id":4,"shopName":"便利店","address":"山东省临沂市河东区人民大街中段","latitude":35.09066,"longitude":118.402291,"logoId":14,"distance":6.438825178517565E-4,"degreeOfSatisfaction":0},{"id":2,"shopName":"测试店铺","address":"山东省临沂市河东区聚贤居(临沂饭店河东NO.1北)","latitude":35.090669,"longitude":118.402269,"logoId":113,"distance":0.0016333286248543478,"degreeOfSatisfaction":0},{"id":1,"shopName":"掉渣饼","address":"兰山区东夷大街","latitude":35.0930899,"longitude":118.4017237,"logoId":2,"distance":0.27485029619413137,"degreeOfSatisfaction":3},{"id":3,"shopName":"临沂智云网络科技有限公司","address":"山东省临沂市河东区人民大街","latitude":35.08959,"longitude":118.3982203,"logoId":87,"distance":0.3884420616064747,"degreeOfSatisfaction":0},{"id":8,"shopName":"杰伦形象店","address":"山东省临沂市河东区滨河东路","latitude":35.1003698,"longitude":118.3926777,"logoId":129,"distance":1.3889916801974502,"degreeOfSatisfaction":0},{"id":10,"shopName":"服装02-自己修改商铺","address":"山东省临沂市兰山区柳青河西路","latitude":35.0911849,"longitude":118.3690972,"logoId":180,"distance":3.020037204658052,"degreeOfSatisfaction":0},{"id":6,"shopName":"马队长牛肉拉面","address":"山东省临沂市兰山区酒厂前街26","latitude":35.0733732,"longitude":118.3642823,"logoId":32,"distance":3.956336230389455,"degreeOfSatisfaction":0},{"id":9,"shopName":"咖啡小店001","address":"山东省临沂市兰山区南坊街道天津路与沭河路交汇处","latitude":35.1148539,"longitude":118.3692769,"logoId":147,"distance":4.031492467362268,"degreeOfSatisfaction":0},{"id":12,"shopName":"超市便利店888","address":"临沂市兰山区南坊新区市政府办公大楼天元商务大厦522房间","latitude":35.1091699,"longitude":118.3626025,"logoId":191,"distance":4.155454603641399,"degreeOfSatisfaction":0}]
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
         * id : 4
         * shopName : 便利店
         * address : 山东省临沂市河东区人民大街中段
         * latitude : 35.09066
         * longitude : 118.402291
         * logoId : 14
         * distance : 6.438825178517565E-4
         * degreeOfSatisfaction : 0
         */

        private int id;
        private String shopName;
        private String address;
        private double latitude;
        private double longitude;
        private int logoId;
        private double distance;
        private int degreeOfSatisfaction;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public int getLogoId() {
            return logoId;
        }

        public void setLogoId(int logoId) {
            this.logoId = logoId;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public int getDegreeOfSatisfaction() {
            return degreeOfSatisfaction;
        }

        public void setDegreeOfSatisfaction(int degreeOfSatisfaction) {
            this.degreeOfSatisfaction = degreeOfSatisfaction;
        }
    }
}
