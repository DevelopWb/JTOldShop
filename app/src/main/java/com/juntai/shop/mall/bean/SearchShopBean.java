package com.juntai.shop.mall.bean;

import com.juntai.mall.base.base.BaseResult;

import java.util.List;

/**
 * 搜索店铺列表
 * Created by Ma
 * on 2019/9/30
 */
public class SearchShopBean extends BaseResult {

    /**
     * error : null
     * returnValue : {"datas":[{"shopId":4,"shopName":"便利店","logoId":14,"address":"山东省临沂市河东区人民大街中段","degreeOfSatisfaction":0,"distance":3.5521535890008183E-4},{"shopId":2,"shopName":"测试店铺","logoId":113,"address":"山东省临沂市河东区聚贤居(临沂饭店河东NO.1北)","degreeOfSatisfaction":0,"distance":0.002584263667415041},{"shopId":8,"shopName":"杰伦形象店","logoId":129,"address":"山东省临沂市河东区滨河东路","degreeOfSatisfaction":0,"distance":1.3898235059225712},{"shopId":9,"shopName":"咖啡小店001","logoId":147,"address":"山东省临沂市兰山区南坊街道天津路与沭河路交汇处","degreeOfSatisfaction":0,"distance":4.032392678382146},{"shopId":12,"shopName":"超市便利店888","logoId":191,"address":"临沂市兰山区南坊新区市政府办公大楼天元商务大厦522房间","degreeOfSatisfaction":0,"distance":4.156410205142662}],"total":5,"listSize":5,"pageCount":1}
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
         * datas : [{"shopId":4,"shopName":"便利店","logoId":14,"address":"山东省临沂市河东区人民大街中段","degreeOfSatisfaction":0,"distance":3.5521535890008183E-4},{"shopId":2,"shopName":"测试店铺","logoId":113,"address":"山东省临沂市河东区聚贤居(临沂饭店河东NO.1北)","degreeOfSatisfaction":0,"distance":0.002584263667415041},{"shopId":8,"shopName":"杰伦形象店","logoId":129,"address":"山东省临沂市河东区滨河东路","degreeOfSatisfaction":0,"distance":1.3898235059225712},{"shopId":9,"shopName":"咖啡小店001","logoId":147,"address":"山东省临沂市兰山区南坊街道天津路与沭河路交汇处","degreeOfSatisfaction":0,"distance":4.032392678382146},{"shopId":12,"shopName":"超市便利店888","logoId":191,"address":"临沂市兰山区南坊新区市政府办公大楼天元商务大厦522房间","degreeOfSatisfaction":0,"distance":4.156410205142662}]
         * total : 5
         * listSize : 5
         * pageCount : 1
         */

        private int total;
        private int listSize;
        private int pageCount;
        private List<DatasBean> datas;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getListSize() {
            return listSize;
        }

        public void setListSize(int listSize) {
            this.listSize = listSize;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public List<DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public static class DatasBean {
            /**
             * shopId : 4
             * shopName : 便利店
             * logoId : 14
             * address : 山东省临沂市河东区人民大街中段
             * degreeOfSatisfaction : 0
             * distance : 3.5521535890008183E-4
             */

            private int shopId;
            private String shopName;
            private int logoId;
            private String address;
            private int degreeOfSatisfaction;
            private double distance;

            public int getShopId() {
                return shopId;
            }

            public void setShopId(int shopId) {
                this.shopId = shopId;
            }

            public String getShopName() {
                return shopName;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }

            public int getLogoId() {
                return logoId;
            }

            public void setLogoId(int logoId) {
                this.logoId = logoId;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getDegreeOfSatisfaction() {
                return degreeOfSatisfaction;
            }

            public void setDegreeOfSatisfaction(int degreeOfSatisfaction) {
                this.degreeOfSatisfaction = degreeOfSatisfaction;
            }

            public double getDistance() {
                return distance;
            }

            public void setDistance(double distance) {
                this.distance = distance;
            }
        }
    }
}
