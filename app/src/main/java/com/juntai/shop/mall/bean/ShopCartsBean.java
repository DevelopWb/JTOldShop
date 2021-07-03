package com.juntai.shop.mall.bean;

import com.juntai.mall.base.base.BaseResult;

import java.util.List;

/**
 * Created by Ma
 * on 2019/12/14
 */
public class ShopCartsBean extends BaseResult {

    /**
     * error : null
     * returnValue : [{"trolleyId":4,"purchaserId":6,"merchantId":5,"commodityId":4,"commodityName":"女装牛仔裤","commodityImg":"/commodityImg/c9a3ea735886a27d98a8b3547353120.jpg","commodityNum":1,"attributeId":21,"price":99.2,"packingCharges":0,"inventoryNum":2000,"specification":[{"parameterTypeName":"尺码","parameterName":"长度14cm，宽度8cm,推荐年龄8-10个月"},{"parameterTypeName":"颜色","parameterName":"蓝色"}]}]
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
         * trolleyId : 4
         * purchaserId : 6
         * merchantId : 5
         * commodityId : 4
         * commodityName : 女装牛仔裤
         * commodityImg : /commodityImg/c9a3ea735886a27d98a8b3547353120.jpg
         * commodityNum : 1
         * attributeId : 21
         * price : 99.2
         * packingCharges : 0.0
         * inventoryNum : 2000
         * specification : [{"parameterTypeName":"尺码","parameterName":"长度14cm，宽度8cm,推荐年龄8-10个月"},{"parameterTypeName":"颜色","parameterName":"蓝色"}]
         */

        private int trolleyId;
        private int purchaserId;
        private int merchantId;
        private int commodityId;
        private String commodityName;
        private String commodityImg;
        private int commodityNum;
        private int attributeId;
        private double price;
        private double packingCharges;
        private int inventoryNum;
        private List<SpecificationBean> specification;

        public int getTrolleyId() {
            return trolleyId;
        }

        public void setTrolleyId(int trolleyId) {
            this.trolleyId = trolleyId;
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

        public int getCommodityNum() {
            return commodityNum;
        }

        public void setCommodityNum(int commodityNum) {
            this.commodityNum = commodityNum;
        }

        public int getAttributeId() {
            return attributeId;
        }

        public void setAttributeId(int attributeId) {
            this.attributeId = attributeId;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getPackingCharges() {
            return packingCharges;
        }

        public void setPackingCharges(double packingCharges) {
            this.packingCharges = packingCharges;
        }

        public int getInventoryNum() {
            return inventoryNum;
        }

        public void setInventoryNum(int inventoryNum) {
            this.inventoryNum = inventoryNum;
        }

        public List<SpecificationBean> getSpecification() {
            return specification;
        }

        public void setSpecification(List<SpecificationBean> specification) {
            this.specification = specification;
        }

        public static class SpecificationBean {
            /**
             * parameterTypeName : 尺码
             * parameterName : 长度14cm，宽度8cm,推荐年龄8-10个月
             */

            private String parameterTypeName;
            private String parameterName;

            public String getParameterTypeName() {
                return parameterTypeName;
            }

            public void setParameterTypeName(String parameterTypeName) {
                this.parameterTypeName = parameterTypeName;
            }

            public String getParameterName() {
                return parameterName;
            }

            public void setParameterName(String parameterName) {
                this.parameterName = parameterName;
            }
        }
    }
}
