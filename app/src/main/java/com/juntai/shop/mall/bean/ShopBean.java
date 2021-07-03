package com.juntai.shop.mall.bean;

import com.juntai.mall.base.base.BaseResult;

import java.util.List;

/**
 * Created by Ma
 * on 2019/11/18
 */
public class ShopBean extends BaseResult {

    /**
     * error : null
     * returnValue : {"id":1,"shopName":"掉渣饼","transportCharges":5,"logoId":2,"imgId":"3","isCollect":1,"videoUrl":null,"webUrl":"www.baidu.com?id=1","shopClassify":[{"classifyId":1,"shopClassifyName":"热销","commodity":[{"commodityId":1,"commodityName":"15元D套餐","commodityImg":"/commodityImg/63d9246d483ead5ba4c1787a22eba69.jpg","commoditySynopsis":"碍事法师大纲是","monthlySales":0,"attrId":103,"price":10,"inventoryNum":550,"packingCharges":0,"commodityNum":0,"isType":1},{"commodityId":2,"commodityName":"套餐","commodityImg":"/commodityImg/64a71ce18d01f956731a7ce8e3877b6.jpg","commoditySynopsis":"阿三国杀","monthlySales":0,"attrId":120,"price":10,"inventoryNum":1,"packingCharges":0,"commodityNum":0,"isType":0}]},{"classifyId":2,"shopClassifyName":"15元套餐","commodity":[]}]}
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
         * id : 1
         * shopName : 掉渣饼
         * transportCharges : 5.0
         * logoId : 2
         * imgId : 3
         * isCollect : 1
         * videoUrl : null
         * webUrl : www.baidu.com?id=1
         * shopClassify : [{"classifyId":1,"shopClassifyName":"热销","commodity":[{"commodityId":1,"commodityName":"15元D套餐","commodityImg":"/commodityImg/63d9246d483ead5ba4c1787a22eba69.jpg","commoditySynopsis":"碍事法师大纲是","monthlySales":0,"attrId":103,"price":10,"inventoryNum":550,"packingCharges":0,"commodityNum":0,"isType":1},{"commodityId":2,"commodityName":"套餐","commodityImg":"/commodityImg/64a71ce18d01f956731a7ce8e3877b6.jpg","commoditySynopsis":"阿三国杀","monthlySales":0,"attrId":120,"price":10,"inventoryNum":1,"packingCharges":0,"commodityNum":0,"isType":0}]},{"classifyId":2,"shopClassifyName":"15元套餐","commodity":[]}]
         */

        private int shopId;
        private String shopName;
        private double transportCharges;
        private int logoId;
        private String imgId;
        private int shopUserId;
        private int isCollect;
        private String videoUrl;
        private String webUrl;
        private String shopAccount;
        private List<ShopClassifyBean> shopClassify;

        public int getShopUserId() {
            return shopUserId;
        }

        public void setShopUserId(int shopUserId) {
            this.shopUserId = shopUserId;
        }

        public String getShopAccount() {
            return shopAccount;
        }

        public void setShopAccount(String shopAccount) {
            this.shopAccount = shopAccount;
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

        public double getTransportCharges() {
            return transportCharges;
        }

        public void setTransportCharges(double transportCharges) {
            this.transportCharges = transportCharges;
        }

        public int getLogoId() {
            return logoId;
        }

        public void setLogoId(int logoId) {
            this.logoId = logoId;
        }

        public String getImgId() {
            return imgId;
        }

        public void setImgId(String imgId) {
            this.imgId = imgId;
        }

        public int getIsCollect() {
            return isCollect;
        }

        public void setIsCollect(int isCollect) {
            this.isCollect = isCollect;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public String getWebUrl() {
            return webUrl;
        }

        public void setWebUrl(String webUrl) {
            this.webUrl = webUrl;
        }

        public List<ShopClassifyBean> getShopClassify() {
            return shopClassify;
        }

        public void setShopClassify(List<ShopClassifyBean> shopClassify) {
            this.shopClassify = shopClassify;
        }

        public static class ShopClassifyBean {
            /**
             * classifyId : 1
             * shopClassifyName : 热销
             * commodity : [{"commodityId":1,"commodityName":"15元D套餐","commodityImg":"/commodityImg/63d9246d483ead5ba4c1787a22eba69.jpg","commoditySynopsis":"碍事法师大纲是","monthlySales":0,"attrId":103,"price":10,"inventoryNum":550,"packingCharges":0,"commodityNum":0,"isType":1},{"commodityId":2,"commodityName":"套餐","commodityImg":"/commodityImg/64a71ce18d01f956731a7ce8e3877b6.jpg","commoditySynopsis":"阿三国杀","monthlySales":0,"attrId":120,"price":10,"inventoryNum":1,"packingCharges":0,"commodityNum":0,"isType":0}]
             */

            private int classifyId;
            private String shopClassifyName;
            private List<CommodityBean> commodity;

            public int getClassifyId() {
                return classifyId;
            }

            public void setClassifyId(int classifyId) {
                this.classifyId = classifyId;
            }

            public String getShopClassifyName() {
                return shopClassifyName;
            }

            public void setShopClassifyName(String shopClassifyName) {
                this.shopClassifyName = shopClassifyName;
            }

            public List<CommodityBean> getCommodity() {
                return commodity;
            }

            public void setCommodity(List<CommodityBean> commodity) {
                this.commodity = commodity;
            }

            public static class CommodityBean {
                /**
                 * commodityId : 1
                 * commodityName : 15元D套餐
                 * commodityImg : /commodityImg/63d9246d483ead5ba4c1787a22eba69.jpg
                 * commoditySynopsis : 碍事法师大纲是
                 * monthlySales : 0
                 * attrId : 103
                 * price : 10.0
                 * inventoryNum : 550
                 * packingCharges : 0.0
                 * commodityNum : 0
                 * isType : 1
                 */

                private int commodityId;
                private String commodityName;
                private String commodityImg;
                private String commoditySynopsis;
                private int monthlySales;
                private int attrId;
                private double price;
                private int inventoryNum;
                private double packingCharges;
                private int commodityNum;
                private int isType;

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

                public String getCommoditySynopsis() {
                    return commoditySynopsis;
                }

                public void setCommoditySynopsis(String commoditySynopsis) {
                    this.commoditySynopsis = commoditySynopsis;
                }

                public int getMonthlySales() {
                    return monthlySales;
                }

                public void setMonthlySales(int monthlySales) {
                    this.monthlySales = monthlySales;
                }

                public int getAttrId() {
                    return attrId;
                }

                public void setAttrId(int attrId) {
                    this.attrId = attrId;
                }

                public double getPrice() {
                    return price;
                }

                public void setPrice(double price) {
                    this.price = price;
                }

                public int getInventoryNum() {
                    return inventoryNum;
                }

                public void setInventoryNum(int inventoryNum) {
                    this.inventoryNum = inventoryNum;
                }

                public double getPackingCharges() {
                    return packingCharges;
                }

                public void setPackingCharges(double packingCharges) {
                    this.packingCharges = packingCharges;
                }

                public int getCommodityNum() {
                    return commodityNum;
                }

                public void setCommodityNum(int commodityNum) {
                    this.commodityNum = commodityNum;
                }

                public int getIsType() {
                    return isType;
                }

                public void setIsType(int isType) {
                    this.isType = isType;
                }
            }
        }
    }
}
