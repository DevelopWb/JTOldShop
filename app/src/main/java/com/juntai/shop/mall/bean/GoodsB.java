package com.juntai.shop.mall.bean;

import com.juntai.mall.base.base.BaseResult;

import java.util.List;

/**
 * @aouther Ma
 * @date 2019/3/6
 */
public class GoodsB extends BaseResult {

    /**
     * error : null
     * returnValue : {"id":4,"shopId":5,"shopName":"小林水产","commodityName":"女装牛仔裤","commoditySynopsis":"蔡依林明星同款高腰隐形口袋紧身九分牛仔裤","monthlySales":0,"isCollect":0,"commodityPicture":[{"id":38,"imgUrl":"/commodityImg/955ca6e63e4b001171bc891369b002a.jpg"},{"id":39,"imgUrl":"/commodityImg/ca59b7a673bc6b770fa081aa3d5ddae.jpg"},{"id":40,"imgUrl":"/commodityImg/fee42b9e83be07e2a2ddc690e88414d.jpg"},{"id":41,"imgUrl":"/commodityImg/1fc4825043a12614c882e19547d84e0.jpg"}],"commoditySpecification":[{"specId":5,"specificationAttribute":"货号","specificationValue":"0635147"},{"specId":6,"specificationAttribute":"面料","specificationValue":"主材质：纯棉。衬里：聚酯纤维"},{"specId":7,"specificationAttribute":"洗涤建议","specificationValue":"40℃机洗"}],"commodityParameterType":[{"id":3,"parameterTypeName":"尺码","parameterList":[{"parameterId":11,"parameteName":"均码（S-L码。90-130）"},{"parameterId":12,"parameteName":"大码"}]},{"id":4,"parameterTypeName":"颜色","parameterList":[{"parameterId":13,"parameteName":"天蓝色"},{"parameterId":14,"parameteName":"深蓝色"},{"parameterId":15,"parameteName":"黑色"}]},{"id":5,"parameterTypeName":"图案","parameterList":[{"parameterId":28,"parameteName":"米奇"},{"parameterId":29,"parameteName":"米妮"},{"parameterId":30,"parameteName":"唐老鸭"}]}],"sku":[{"attributeId":20,"price":99.1,"packingCharges":0,"inventoryNum":2000,"parameter":[{"parameterId":11,"parameterName":"均码（S-L码。90-130）"},{"parameterId":13,"parameterName":"天蓝色"},{"parameterId":28,"parameterName":"米奇"}]},{"attributeId":21,"price":99.2,"packingCharges":0,"inventoryNum":2000,"parameter":[{"parameterId":11,"parameterName":"均码（S-L码。90-130）"},{"parameterId":14,"parameterName":"深蓝色"},{"parameterId":28,"parameterName":"米奇"}]},{"attributeId":22,"price":99.3,"packingCharges":0,"inventoryNum":2000,"parameter":[{"parameterId":11,"parameterName":"均码（S-L码。90-130）"},{"parameterId":15,"parameterName":"黑色"},{"parameterId":28,"parameterName":"米奇"}]},{"attributeId":23,"price":99.4,"packingCharges":0,"inventoryNum":3000,"parameter":[{"parameterId":11,"parameterName":"均码（S-L码。90-130）"},{"parameterId":14,"parameterName":"深蓝色"},{"parameterId":30,"parameterName":"唐老鸭"}]},{"attributeId":24,"price":99.5,"packingCharges":0,"inventoryNum":3000,"parameter":[]},{"attributeId":25,"price":99.6,"packingCharges":0,"inventoryNum":3000,"parameter":[]},{"attributeId":26,"price":109.7,"packingCharges":0,"inventoryNum":1000,"parameter":[]},{"attributeId":27,"price":109.8,"packingCharges":0,"inventoryNum":900,"parameter":[]},{"attributeId":28,"price":109.9,"packingCharges":0,"inventoryNum":800,"parameter":[]}]}
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
         * id : 4
         * shopId : 5
         * shopName : 小林水产
         * commodityName : 女装牛仔裤
         * commoditySynopsis : 蔡依林明星同款高腰隐形口袋紧身九分牛仔裤
         * monthlySales : 0
         * isCollect : 0
         * commodityPicture : [{"id":38,"imgUrl":"/commodityImg/955ca6e63e4b001171bc891369b002a.jpg"},{"id":39,"imgUrl":"/commodityImg/ca59b7a673bc6b770fa081aa3d5ddae.jpg"},{"id":40,"imgUrl":"/commodityImg/fee42b9e83be07e2a2ddc690e88414d.jpg"},{"id":41,"imgUrl":"/commodityImg/1fc4825043a12614c882e19547d84e0.jpg"}]
         * commoditySpecification : [{"specId":5,"specificationAttribute":"货号","specificationValue":"0635147"},{"specId":6,"specificationAttribute":"面料","specificationValue":"主材质：纯棉。衬里：聚酯纤维"},{"specId":7,"specificationAttribute":"洗涤建议","specificationValue":"40℃机洗"}]
         * commodityParameterType : [{"id":3,"parameterTypeName":"尺码","parameterList":[{"parameterId":11,"parameteName":"均码（S-L码。90-130）"},{"parameterId":12,"parameteName":"大码"}]},{"id":4,"parameterTypeName":"颜色","parameterList":[{"parameterId":13,"parameteName":"天蓝色"},{"parameterId":14,"parameteName":"深蓝色"},{"parameterId":15,"parameteName":"黑色"}]},{"id":5,"parameterTypeName":"图案","parameterList":[{"parameterId":28,"parameteName":"米奇"},{"parameterId":29,"parameteName":"米妮"},{"parameterId":30,"parameteName":"唐老鸭"}]}]
         * sku : [{"attributeId":20,"price":99.1,"packingCharges":0,"inventoryNum":2000,"parameter":[{"parameterId":11,"parameterName":"均码（S-L码。90-130）"},{"parameterId":13,"parameterName":"天蓝色"},{"parameterId":28,"parameterName":"米奇"}]},{"attributeId":21,"price":99.2,"packingCharges":0,"inventoryNum":2000,"parameter":[{"parameterId":11,"parameterName":"均码（S-L码。90-130）"},{"parameterId":14,"parameterName":"深蓝色"},{"parameterId":28,"parameterName":"米奇"}]},{"attributeId":22,"price":99.3,"packingCharges":0,"inventoryNum":2000,"parameter":[{"parameterId":11,"parameterName":"均码（S-L码。90-130）"},{"parameterId":15,"parameterName":"黑色"},{"parameterId":28,"parameterName":"米奇"}]},{"attributeId":23,"price":99.4,"packingCharges":0,"inventoryNum":3000,"parameter":[{"parameterId":11,"parameterName":"均码（S-L码。90-130）"},{"parameterId":14,"parameterName":"深蓝色"},{"parameterId":30,"parameterName":"唐老鸭"}]},{"attributeId":24,"price":99.5,"packingCharges":0,"inventoryNum":3000,"parameter":[]},{"attributeId":25,"price":99.6,"packingCharges":0,"inventoryNum":3000,"parameter":[]},{"attributeId":26,"price":109.7,"packingCharges":0,"inventoryNum":1000,"parameter":[]},{"attributeId":27,"price":109.8,"packingCharges":0,"inventoryNum":900,"parameter":[]},{"attributeId":28,"price":109.9,"packingCharges":0,"inventoryNum":800,"parameter":[]}]
         */

        private int id;
        private int shopId;
        private String shopName;
        private String commodityName;
        private String commoditySynopsis;
        private String webUrl;
        private int monthlySales;
        private int isCollect;
        private int isType;
        private List<CommodityPictureBean> commodityPicture;
        private List<CommoditySpecificationBean> commoditySpecification;
        private List<CommodityParameterTypeBean> commodityParameterType;
        private List<SkuBean> sku;

        public int getIsType() {
            return isType;
        }

        public void setIsType(int isType) {
            this.isType = isType;
        }

        public String getWebUrl() {
            return webUrl;
        }

        public void setWebUrl(String webUrl) {
            this.webUrl = webUrl;
        }

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

        public String getCommodityName() {
            return commodityName;
        }

        public void setCommodityName(String commodityName) {
            this.commodityName = commodityName;
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

        public int getIsCollect() {
            return isCollect;
        }

        public void setIsCollect(int isCollect) {
            this.isCollect = isCollect;
        }

        public List<CommodityPictureBean> getCommodityPicture() {
            return commodityPicture;
        }

        public void setCommodityPicture(List<CommodityPictureBean> commodityPicture) {
            this.commodityPicture = commodityPicture;
        }

        public List<CommoditySpecificationBean> getCommoditySpecification() {
            return commoditySpecification;
        }

        public void setCommoditySpecification(List<CommoditySpecificationBean> commoditySpecification) {
            this.commoditySpecification = commoditySpecification;
        }

        public List<CommodityParameterTypeBean> getCommodityParameterType() {
            return commodityParameterType;
        }

        public void setCommodityParameterType(List<CommodityParameterTypeBean> commodityParameterType) {
            this.commodityParameterType = commodityParameterType;
        }

        public List<SkuBean> getSku() {
            return sku;
        }

        public void setSku(List<SkuBean> sku) {
            this.sku = sku;
        }

        public static class CommodityPictureBean {
            /**
             * id : 38
             * imgUrl : /commodityImg/955ca6e63e4b001171bc891369b002a.jpg
             */

            private int id;
            private String imgUrl;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }
        }

        public static class CommoditySpecificationBean {
            /**
             * specId : 5
             * specificationAttribute : 货号
             * specificationValue : 0635147
             */

            private int specId;
            private String specificationAttribute;
            private String specificationValue;

            public int getSpecId() {
                return specId;
            }

            public void setSpecId(int specId) {
                this.specId = specId;
            }

            public String getSpecificationAttribute() {
                return specificationAttribute;
            }

            public void setSpecificationAttribute(String specificationAttribute) {
                this.specificationAttribute = specificationAttribute;
            }

            public String getSpecificationValue() {
                return specificationValue;
            }

            public void setSpecificationValue(String specificationValue) {
                this.specificationValue = specificationValue;
            }
        }

        public static class CommodityParameterTypeBean {
            /**
             * id : 3
             * parameterTypeName : 尺码
             * parameterList : [{"parameterId":11,"parameteName":"均码（S-L码。90-130）"},{"parameterId":12,"parameteName":"大码"}]
             */

            private int id;
            private String parameterTypeName;
            private List<ParameterListBean> parameterList;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getParameterTypeName() {
                return parameterTypeName;
            }

            public void setParameterTypeName(String parameterTypeName) {
                this.parameterTypeName = parameterTypeName;
            }

            public List<ParameterListBean> getParameterList() {
                return parameterList;
            }

            public void setParameterList(List<ParameterListBean> parameterList) {
                this.parameterList = parameterList;
            }

            public static class ParameterListBean {
                /**
                 * parameterId : 11
                 * parameteName : 均码（S-L码。90-130）
                 */

                private int parameterId;
                private String parameteName;

                public int getParameterId() {
                    return parameterId;
                }

                public void setParameterId(int parameterId) {
                    this.parameterId = parameterId;
                }

                public String getParameteName() {
                    return parameteName;
                }

                public void setParameteName(String parameteName) {
                    this.parameteName = parameteName;
                }
            }
        }

        public static class SkuBean {
            /**
             * attributeId : 20
             * price : 99.1
             * packingCharges : 0.0
             * inventoryNum : 2000
             * parameter : [{"parameterId":11,"parameterName":"均码（S-L码。90-130）"},{"parameterId":13,"parameterName":"天蓝色"},{"parameterId":28,"parameterName":"米奇"}]
             */

            private int attributeId;
            private double price;
            private double packingCharges;
            private int inventoryNum;
            private List<ParameterBean> parameter;

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

            public List<ParameterBean> getParameter() {
                return parameter;
            }

            public void setParameter(List<ParameterBean> parameter) {
                this.parameter = parameter;
            }

            public static class ParameterBean {
                /**
                 * parameterId : 11
                 * parameterName : 均码（S-L码。90-130）
                 */

                private int parameterId;
                private String parameterName;

                public int getParameterId() {
                    return parameterId;
                }

                public void setParameterId(int parameterId) {
                    this.parameterId = parameterId;
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
}
