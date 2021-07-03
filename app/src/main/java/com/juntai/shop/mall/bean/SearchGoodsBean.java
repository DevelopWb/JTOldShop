package com.juntai.shop.mall.bean;

import com.juntai.mall.base.base.BaseResult;

import java.util.List;

/**
 * 搜索商品列表
 * Created by Ma
 * on 2019/9/30
 */
public class SearchGoodsBean extends BaseResult {

    /**
     * error : null
     * returnValue : {"datas":[{"commodityId":4,"commodityName":"女装牛仔裤","commodityImg":"/commodityImg/c9a3ea735886a27d98a8b3547353120.jpg","monthlySales":0,"price":99.3},{"commodityId":4,"commodityName":"女装牛仔裤","commodityImg":"/commodityImg/c9a3ea735886a27d98a8b3547353120.jpg","monthlySales":0,"price":99.4},{"commodityId":4,"commodityName":"女装牛仔裤","commodityImg":"/commodityImg/c9a3ea735886a27d98a8b3547353120.jpg","monthlySales":0,"price":99.5},{"commodityId":4,"commodityName":"女装牛仔裤","commodityImg":"/commodityImg/c9a3ea735886a27d98a8b3547353120.jpg","monthlySales":0,"price":99.6},{"commodityId":4,"commodityName":"女装牛仔裤","commodityImg":"/commodityImg/c9a3ea735886a27d98a8b3547353120.jpg","monthlySales":0,"price":109.7},{"commodityId":4,"commodityName":"女装牛仔裤","commodityImg":"/commodityImg/c9a3ea735886a27d98a8b3547353120.jpg","monthlySales":0,"price":109.8},{"commodityId":4,"commodityName":"女装牛仔裤","commodityImg":"/commodityImg/c9a3ea735886a27d98a8b3547353120.jpg","monthlySales":0,"price":99.1},{"commodityId":4,"commodityName":"女装牛仔裤","commodityImg":"/commodityImg/c9a3ea735886a27d98a8b3547353120.jpg","monthlySales":0,"price":109.9},{"commodityId":4,"commodityName":"女装牛仔裤","commodityImg":"/commodityImg/c9a3ea735886a27d98a8b3547353120.jpg","monthlySales":0,"price":99.2}],"total":9,"listSize":9,"pageCount":1}
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
         * datas : [{"commodityId":4,"commodityName":"女装牛仔裤","commodityImg":"/commodityImg/c9a3ea735886a27d98a8b3547353120.jpg","monthlySales":0,"price":99.3},{"commodityId":4,"commodityName":"女装牛仔裤","commodityImg":"/commodityImg/c9a3ea735886a27d98a8b3547353120.jpg","monthlySales":0,"price":99.4},{"commodityId":4,"commodityName":"女装牛仔裤","commodityImg":"/commodityImg/c9a3ea735886a27d98a8b3547353120.jpg","monthlySales":0,"price":99.5},{"commodityId":4,"commodityName":"女装牛仔裤","commodityImg":"/commodityImg/c9a3ea735886a27d98a8b3547353120.jpg","monthlySales":0,"price":99.6},{"commodityId":4,"commodityName":"女装牛仔裤","commodityImg":"/commodityImg/c9a3ea735886a27d98a8b3547353120.jpg","monthlySales":0,"price":109.7},{"commodityId":4,"commodityName":"女装牛仔裤","commodityImg":"/commodityImg/c9a3ea735886a27d98a8b3547353120.jpg","monthlySales":0,"price":109.8},{"commodityId":4,"commodityName":"女装牛仔裤","commodityImg":"/commodityImg/c9a3ea735886a27d98a8b3547353120.jpg","monthlySales":0,"price":99.1},{"commodityId":4,"commodityName":"女装牛仔裤","commodityImg":"/commodityImg/c9a3ea735886a27d98a8b3547353120.jpg","monthlySales":0,"price":109.9},{"commodityId":4,"commodityName":"女装牛仔裤","commodityImg":"/commodityImg/c9a3ea735886a27d98a8b3547353120.jpg","monthlySales":0,"price":99.2}]
         * total : 9
         * listSize : 9
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
             * commodityId : 4
             * commodityName : 女装牛仔裤
             * commodityImg : /commodityImg/c9a3ea735886a27d98a8b3547353120.jpg
             * monthlySales : 0
             * price : 99.3
             */

            private int commodityId;
            private int shopId;
            private String commodityName;
            private String commodityImg;
            private int monthlySales;
            private double price;

            public int getCommodityId() {
                return commodityId;
            }

            public int getShopId() {
                return shopId;
            }

            public void setShopId(int shopId) {
                this.shopId = shopId;
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
        }
    }
}
