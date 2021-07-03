package com.juntai.shop.mall.bean;

import com.juntai.mall.base.base.BaseResult;

import java.util.List;

public class MallHomeB extends BaseResult {

    /**
     * data : {"banner":[{"bigPicture":"https://gd2.alicdn.com/imgextra/i1/2566175783/O1CN01NN4odV1saewSUh1Q6_!!2566175783.jpg","id":1},{"bigPicture":"https://gd1.alicdn.com/imgextra/i4/2929335943/O1CN015YMLlc1tlwIcxBUai_!!2929335943.jpg","id":2},{"bigPicture":"https://gd3.alicdn.com/imgextra/i4/0/O1CN01T7SOEg1u3nlIfoDOf_!!0-item_pic.jpg","id":3}],"menu":[{"id":1,"littlePicture":"","name":"天猫"},{"id":2,"littlePicture":"","name":"聚划算"},{"id":3,"littlePicture":"","name":"天猫国际"},{"id":4,"littlePicture":"","name":"饿了么"},{"id":5,"littlePicture":"","name":"天猫超市"},{"id":6,"littlePicture":"","name":"充值中心"},{"id":7,"littlePicture":"","name":"飞猪旅行"},{"id":8,"littlePicture":"","name":"淘金币"},{"id":9,"littlePicture":"","name":"拍卖"},{"id":10,"littlePicture":"","name":"分类"}],"goodslist":[{"picture":"https://gd2.alicdn.com/imgextra/i2/387784786/O1CN011lE21xGZ9gUYp1D_!!387784786.jpg","id":3,"name":"iPhone XS Max","money":"23"},{"picture":"https://gd4.alicdn.com/imgextra/i4/1665069890/O1CN012MvfYOUCebeWXev_!!1665069890.jpg","id":4,"name":"iPhone XS","money":"1"},{"picture":"https://gd2.alicdn.com/imgextra/i2/2534651412/O1CN011MIjiHmrSDKGLko_!!2534651412.jpg","id":5,"name":"iPhone XR","money":"313"},{"picture":"https://gd4.alicdn.com/imgextra/i4/315980614/O1CN01ZXlDge1GPFfLrOXlC_!!315980614.jpg","id":6,"name":"iPhone X","money":"351"},{"picture":"https://gd2.alicdn.com/imgextra/i2/739025418/O1CN01pIhtlL1ptUMWDCNQV_!!739025418.jpg","id":7,"name":"iPhone 8 Plus","money":"135"},{"picture":"https://gd3.alicdn.com/imgextra/i3/2600543804/O1CN01gPRRYS1dyH78NrKmw_!!2600543804.jpg","id":8,"name":"iPhone 8","money":"135"},{"picture":"https://gd4.alicdn.com/imgextra/i4/1680409736/O1CN01OIg8yB2Ln8alQeUoG_!!1680409736.jpg","id":9,"name":"1561·1·51·","money":"31"},{"picture":"https://gd3.alicdn.com/imgextra/i1/2178247665/O1CN01u9gakp26UcLBNgFN7_!!2178247665.jpg","id":15,"name":"顾启杭","money":"131"},{"picture":"https://gd2.alicdn.com/imgextra/i3/45379896/O1CN01OG3X5I2MyPxVuFWu9_!!45379896.jpg","id":17,"name":"131","money":"31"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<BannerBean> banner;
        private List<MenuBean> menu;
        private List<GoodslistBean> goodslist;

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<MenuBean> getMenu() {
            return menu;
        }

        public void setMenu(List<MenuBean> menu) {
            this.menu = menu;
        }

        public List<GoodslistBean> getGoodslist() {
            return goodslist;
        }

        public void setGoodslist(List<GoodslistBean> goodslist) {
            this.goodslist = goodslist;
        }

        public static class BannerBean {
            /**
             * bigPicture : https://gd2.alicdn.com/imgextra/i1/2566175783/O1CN01NN4odV1saewSUh1Q6_!!2566175783.jpg
             * id : 1
             */

            private String bigPicture;
            private int id;

            public String getBigPicture() {
                return bigPicture;
            }

            public void setBigPicture(String bigPicture) {
                this.bigPicture = bigPicture;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }

        public static class MenuBean {
            /**
             * id : 1
             * littlePicture :
             * name : 天猫
             */

            private int id;
            private String littlePicture;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLittlePicture() {
                return littlePicture;
            }

            public void setLittlePicture(String littlePicture) {
                this.littlePicture = littlePicture;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class GoodslistBean {
            /**
             * picture : https://gd2.alicdn.com/imgextra/i2/387784786/O1CN011lE21xGZ9gUYp1D_!!387784786.jpg
             * id : 3
             * name : iPhone XS Max
             * money : 23
             */

            private String picture;
            private int id;
            private String name;
            private String money;

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
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

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }
        }
    }
}
