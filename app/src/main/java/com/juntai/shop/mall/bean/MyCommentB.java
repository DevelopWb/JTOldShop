package com.juntai.shop.mall.bean;

import com.juntai.mall.base.base.BaseResult;

import java.util.List;

/**
 * Created by Ma
 * on 2019/11/29
 */
public class MyCommentB extends BaseResult {

    /**
     * error : null
     * returnValue : {"datas":[{"purchaserId":6,"nickName":"c2NfNTVtbg==","headUrl":"/userHead/b3181e89bd6947d8839d1faeae3fef57.jpeg","shopId":1,"shopName":"掉渣饼","logoId":2,"evaluateId":15,"degreeOfSatisfaction":4,"perCaPita":15.2,"gmtCreate":"2019-11-28 15:26:14","evaluate":"大事发生","videoUrl":"/commodityEvaluate/03e88d3c358e4052bf8a2a1455b8c1b5.mp4","replyEvaluate":null,"replyDate":null,"browseNum":null,"evaluateImg":[{"imgId":17,"imgUrl":"/commodityEvaluate/1b75a6c1e3d44b16bffd16a2b90ed28e.jpeg"},{"imgId":18,"imgUrl":"/commodityEvaluate/c18b5f4c276f4b12bb0754f6196cee2d.jpeg"}]},{"purchaserId":6,"nickName":"c2NfNTVtbg==","headUrl":"/userHead/b3181e89bd6947d8839d1faeae3fef57.jpeg","shopId":5,"shopName":"小林水产","logoId":107,"evaluateId":16,"degreeOfSatisfaction":5,"perCaPita":2,"gmtCreate":"2019-12-28 15:07:00","evaluate":"哈哈","videoUrl":"/commodityEvaluate/50d0f770f43742ae965a20f58e4c56ef.mp4","replyEvaluate":null,"replyDate":null,"browseNum":null,"evaluateImg":[{"imgId":19,"imgUrl":"/commodityEvaluate/37669b5b693e420fbb6986af2c5845fb.jpeg"}]}],"total":2,"listSize":2,"pageCount":1}
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
         * datas : [{"purchaserId":6,"nickName":"c2NfNTVtbg==","headUrl":"/userHead/b3181e89bd6947d8839d1faeae3fef57.jpeg","shopId":1,"shopName":"掉渣饼","logoId":2,"evaluateId":15,"degreeOfSatisfaction":4,"perCaPita":15.2,"gmtCreate":"2019-11-28 15:26:14","evaluate":"大事发生","videoUrl":"/commodityEvaluate/03e88d3c358e4052bf8a2a1455b8c1b5.mp4","replyEvaluate":null,"replyDate":null,"browseNum":null,"evaluateImg":[{"imgId":17,"imgUrl":"/commodityEvaluate/1b75a6c1e3d44b16bffd16a2b90ed28e.jpeg"},{"imgId":18,"imgUrl":"/commodityEvaluate/c18b5f4c276f4b12bb0754f6196cee2d.jpeg"}]},{"purchaserId":6,"nickName":"c2NfNTVtbg==","headUrl":"/userHead/b3181e89bd6947d8839d1faeae3fef57.jpeg","shopId":5,"shopName":"小林水产","logoId":107,"evaluateId":16,"degreeOfSatisfaction":5,"perCaPita":2,"gmtCreate":"2019-12-28 15:07:00","evaluate":"哈哈","videoUrl":"/commodityEvaluate/50d0f770f43742ae965a20f58e4c56ef.mp4","replyEvaluate":null,"replyDate":null,"browseNum":null,"evaluateImg":[{"imgId":19,"imgUrl":"/commodityEvaluate/37669b5b693e420fbb6986af2c5845fb.jpeg"}]}]
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
             * purchaserId : 6
             * nickName : c2NfNTVtbg==
             * headUrl : /userHead/b3181e89bd6947d8839d1faeae3fef57.jpeg
             * shopId : 1
             * shopName : 掉渣饼
             * logoId : 2
             * evaluateId : 15
             * degreeOfSatisfaction : 4
             * perCaPita : 15.2
             * gmtCreate : 2019-11-28 15:26:14
             * evaluate : 大事发生
             * videoUrl : /commodityEvaluate/03e88d3c358e4052bf8a2a1455b8c1b5.mp4
             * replyEvaluate : null
             * replyDate : null
             * browseNum : null
             * evaluateImg : [{"imgId":17,"imgUrl":"/commodityEvaluate/1b75a6c1e3d44b16bffd16a2b90ed28e.jpeg"},{"imgId":18,"imgUrl":"/commodityEvaluate/c18b5f4c276f4b12bb0754f6196cee2d.jpeg"}]
             */

            private int purchaserId;
            private String nickName;
            private String headUrl;
            private int shopId;
            private String shopName;
            private int logoId;
            private int evaluateId;
            private int degreeOfSatisfaction;
            private double perCaPita;
            private String gmtCreate;
            private String evaluate;
            private String videoUrl;
            private String replyEvaluate;
            private String replyDate;
            private int browseNum;
            private List<EvaluateImgBean> evaluateImg;

            public int getPurchaserId() {
                return purchaserId;
            }

            public void setPurchaserId(int purchaserId) {
                this.purchaserId = purchaserId;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getHeadUrl() {
                return headUrl;
            }

            public void setHeadUrl(String headUrl) {
                this.headUrl = headUrl;
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

            public int getEvaluateId() {
                return evaluateId;
            }

            public void setEvaluateId(int evaluateId) {
                this.evaluateId = evaluateId;
            }

            public int getDegreeOfSatisfaction() {
                return degreeOfSatisfaction;
            }

            public void setDegreeOfSatisfaction(int degreeOfSatisfaction) {
                this.degreeOfSatisfaction = degreeOfSatisfaction;
            }

            public double getPerCaPita() {
                return perCaPita;
            }

            public void setPerCaPita(double perCaPita) {
                this.perCaPita = perCaPita;
            }

            public String getGmtCreate() {
                return gmtCreate;
            }

            public void setGmtCreate(String gmtCreate) {
                this.gmtCreate = gmtCreate;
            }

            public String getEvaluate() {
                return evaluate;
            }

            public void setEvaluate(String evaluate) {
                this.evaluate = evaluate;
            }

            public String getVideoUrl() {
                return videoUrl;
            }

            public void setVideoUrl(String videoUrl) {
                this.videoUrl = videoUrl;
            }

            public String getReplyEvaluate() {
                return replyEvaluate;
            }

            public void setReplyEvaluate(String replyEvaluate) {
                this.replyEvaluate = replyEvaluate;
            }

            public String getReplyDate() {
                return replyDate;
            }

            public void setReplyDate(String replyDate) {
                this.replyDate = replyDate;
            }

            public int getBrowseNum() {
                return browseNum;
            }

            public void setBrowseNum(int browseNum) {
                this.browseNum = browseNum;
            }

            public List<EvaluateImgBean> getEvaluateImg() {
                return evaluateImg;
            }

            public void setEvaluateImg(List<EvaluateImgBean> evaluateImg) {
                this.evaluateImg = evaluateImg;
            }

            public static class EvaluateImgBean {
                /**
                 * imgId : 17
                 * imgUrl : /commodityEvaluate/1b75a6c1e3d44b16bffd16a2b90ed28e.jpeg
                 */

                private int imgId;
                private String imgUrl;

                public int getImgId() {
                    return imgId;
                }

                public void setImgId(int imgId) {
                    this.imgId = imgId;
                }

                public String getImgUrl() {
                    return imgUrl;
                }

                public void setImgUrl(String imgUrl) {
                    this.imgUrl = imgUrl;
                }
            }
        }
    }
}
