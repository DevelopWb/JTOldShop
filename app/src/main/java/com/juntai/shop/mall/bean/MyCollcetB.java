package com.juntai.shop.mall.bean;

import com.juntai.mall.base.base.BaseResult;

import java.util.List;

/**
 * Created by Ma
 * on 2019/11/26
 * private boolean checked;  是否选中
 *
 */
public class MyCollcetB extends BaseResult {

    /**
     * error : null
     * returnValue : {"datas":[{"id":15,"shopId":3,"shopName":"临沂智云网络科技有限公司","logoId":87,"address":"山东省临沂市河东区人民大街","degreeOfSatisfaction":0,"distance":0.3878017563776475,"commodityId":null,"commodityName":null,"commodityImg":null,"monthlySales":null,"price":null},{"id":8,"shopId":1,"shopName":"掉渣饼","logoId":2,"address":"兰山区东夷大街","degreeOfSatisfaction":4,"distance":0.2748421966126476,"commodityId":null,"commodityName":null,"commodityImg":null,"monthlySales":null,"price":null}],"total":2,"listSize":2,"pageCount":1}
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
         * datas : [{"id":15,"shopId":3,"shopName":"临沂智云网络科技有限公司","logoId":87,"address":"山东省临沂市河东区人民大街","degreeOfSatisfaction":0,"distance":0.3878017563776475,"commodityId":null,"commodityName":null,"commodityImg":null,"monthlySales":null,"price":null},{"id":8,"shopId":1,"shopName":"掉渣饼","logoId":2,"address":"兰山区东夷大街","degreeOfSatisfaction":4,"distance":0.2748421966126476,"commodityId":null,"commodityName":null,"commodityImg":null,"monthlySales":null,"price":null}]
         * total : 2
         * listSize : 2
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
             * id : 15
             * shopId : 3
             * shopName : 临沂智云网络科技有限公司
             * logoId : 87
             * address : 山东省临沂市河东区人民大街
             * degreeOfSatisfaction : 0
             * distance : 0.3878017563776475
             * commodityId : null
             * commodityName : null
             * commodityImg : null
             * monthlySales : null
             * price : null
             */

            private int id;
            private int shopId;
            private String shopName;
            private int logoId;
            private String address;
            private int degreeOfSatisfaction;
            private double distance;
            private int commodityId;
            private String commodityName;
            private String commodityImg;
            private int monthlySales;
            private double price;
            private boolean checked;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

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

            public int getCommodityId() {
                return commodityId;
            }

            public void setCommodityId(int commodityId) {
                this.commodityId = commodityId;
            }

            public String getCommodityName() {
                return commodityName;
            }

            public void setCommodityName(String commodityName) {
                this.commodityName = commodityName;
            }

            public String getCommodityImg() {
                return commodityImg;
            }

            public void setCommodityImg(String commodityImg) {
                this.commodityImg = commodityImg;
            }

            public int getMonthlySales() {
                return monthlySales;
            }

            public void setMonthlySales(int monthlySales) {
                this.monthlySales = monthlySales;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public boolean isChecked() {
                return checked;
            }

            public void setChecked(boolean checked) {
                this.checked = checked;
            }
        }
    }
}
