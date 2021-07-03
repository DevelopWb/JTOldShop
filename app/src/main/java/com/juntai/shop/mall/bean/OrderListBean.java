package com.juntai.shop.mall.bean;

import com.juntai.mall.base.base.BaseResult;

import java.util.List;

/**
 * Created by Ma
 * on 2019/12/5
 * 替换OrderCommodityListBean
 */
public class OrderListBean extends BaseResult {

    /**
     * error : null
     * returnValue : {"datas":[{"orderId":null,"footing":92,"establishTime":1577678430,"status":0,"shopId":5,"shopAccount":"j822mh","logoId":107,"shopName":"小林水产","commodityList":[{"id":177,"commodityId":50,"commodityName":"背心","commodityImg":"/commodityImg/5ea55858e8d94988bd940d58ee1ad6fe.jpeg","commodityNumber":1,"price":15,"parameterName":"L;绿色"},{"id":178,"commodityId":50,"commodityName":"背心","commodityImg":"/commodityImg/5ea55858e8d94988bd940d58ee1ad6fe.jpeg","commodityNumber":5,"price":12,"parameterName":"M;蓝色"}]}],"total":1,"listSize":1,"pageCount":1}
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
         * datas : [{"orderId":null,"footing":92,"establishTime":1577678430,"status":0,"shopId":5,"shopAccount":"j822mh","logoId":107,"shopName":"小林水产","commodityList":[{"id":177,"commodityId":50,"commodityName":"背心","commodityImg":"/commodityImg/5ea55858e8d94988bd940d58ee1ad6fe.jpeg","commodityNumber":1,"price":15,"parameterName":"L;绿色"},{"id":178,"commodityId":50,"commodityName":"背心","commodityImg":"/commodityImg/5ea55858e8d94988bd940d58ee1ad6fe.jpeg","commodityNumber":5,"price":12,"parameterName":"M;蓝色"}]}]
         * total : 1
         * listSize : 1
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
             * orderId : null
             * footing : 92.0
             * establishTime : 1577678430
             * status : 0
             * shopId : 5
             * shopAccount : j822mh
             * logoId : 107
             * shopName : 小林水产
             * commodityList : [{"id":177,"commodityId":50,"commodityName":"背心","commodityImg":"/commodityImg/5ea55858e8d94988bd940d58ee1ad6fe.jpeg","commodityNumber":1,"price":15,"parameterName":"L;绿色"},{"id":178,"commodityId":50,"commodityName":"背心","commodityImg":"/commodityImg/5ea55858e8d94988bd940d58ee1ad6fe.jpeg","commodityNumber":5,"price":12,"parameterName":"M;蓝色"}]
             */

            private int orderId;
            private double footing;
            private int establishTime;
            private int status;
            private int shopId;
            private String shopAccount;
            private int logoId;
            private String shopName;
            private List<OrderCommodityListBean> commodityList;

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            public double getFooting() {
                return footing;
            }

            public void setFooting(double footing) {
                this.footing = footing;
            }

            public int getEstablishTime() {
                return establishTime;
            }

            public void setEstablishTime(int establishTime) {
                this.establishTime = establishTime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
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

            public int getLogoId() {
                return logoId;
            }

            public void setLogoId(int logoId) {
                this.logoId = logoId;
            }

            public String getShopName() {
                return shopName;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }

            public List<OrderCommodityListBean> getCommodityList() {
                return commodityList;
            }

            public void setCommodityList(List<OrderCommodityListBean> commodityList) {
                this.commodityList = commodityList;
            }
        }
    }
}
