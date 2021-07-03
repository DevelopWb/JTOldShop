package com.juntai.shop.mall.bean;

import com.juntai.mall.base.base.BaseResult;

import java.util.List;

/**
 * Created by Ma
 * on 2019/12/10
 */
public class ShopCommentsBean extends BaseResult {


    /**
     * error : null
     * returnValue : {"datas":[{"userId":null,"nickName":"匿名用户","headUrl":"/shopHead/head_default.jpg","id":8,"degreeOfSatisfaction":5,"evaluate":"测试","gmtCreate":"2019-11-17 08:59:06","videoUrl":"/commodityEvaluate/24f98007871d471f8db4eb26c4eedf90.mp4","evaluateImgList":[{"id":12,"imgUrl":"/commodityEvaluate/5f73aefa63fa4b4288dc798fb6c53260.jpeg"},{"id":13,"imgUrl":"/commodityEvaluate/9ddb4269e84b4e4983cf21caa4295358.jpeg"},{"id":14,"imgUrl":"/commodityEvaluate/e4dcd6bdb65d4858941dd67d1cebe140.jpeg"}]},{"userId":1,"nickName":"铁人王进喜","headUrl":"/userHead/029de9783ccc4d2c99d1945fe93b3971.jpeg","id":9,"degreeOfSatisfaction":4,"evaluate":"测试","gmtCreate":"2019-11-17 10:45:46","videoUrl":"/commodityEvaluate/24f98007871d471f8db4eb26c4eedf90.mp4","evaluateImgList":[]}],"total":2,"listSize":2,"pageCount":1}
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
         * datas : [{"userId":null,"nickName":"匿名用户","headUrl":"/shopHead/head_default.jpg","id":8,"degreeOfSatisfaction":5,"evaluate":"测试","gmtCreate":"2019-11-17 08:59:06","videoUrl":"/commodityEvaluate/24f98007871d471f8db4eb26c4eedf90.mp4","evaluateImgList":[{"id":12,"imgUrl":"/commodityEvaluate/5f73aefa63fa4b4288dc798fb6c53260.jpeg"},{"id":13,"imgUrl":"/commodityEvaluate/9ddb4269e84b4e4983cf21caa4295358.jpeg"},{"id":14,"imgUrl":"/commodityEvaluate/e4dcd6bdb65d4858941dd67d1cebe140.jpeg"}]},{"userId":1,"nickName":"铁人王进喜","headUrl":"/userHead/029de9783ccc4d2c99d1945fe93b3971.jpeg","id":9,"degreeOfSatisfaction":4,"evaluate":"测试","gmtCreate":"2019-11-17 10:45:46","videoUrl":"/commodityEvaluate/24f98007871d471f8db4eb26c4eedf90.mp4","evaluateImgList":[]}]
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
             * userId : null
             * nickName : 匿名用户
             * headUrl : /shopHead/head_default.jpg
             * id : 8
             * degreeOfSatisfaction : 5
             * evaluate : 测试
             * gmtCreate : 2019-11-17 08:59:06
             * videoUrl : /commodityEvaluate/24f98007871d471f8db4eb26c4eedf90.mp4
             * evaluateImgList : [{"id":12,"imgUrl":"/commodityEvaluate/5f73aefa63fa4b4288dc798fb6c53260.jpeg"},{"id":13,"imgUrl":"/commodityEvaluate/9ddb4269e84b4e4983cf21caa4295358.jpeg"},{"id":14,"imgUrl":"/commodityEvaluate/e4dcd6bdb65d4858941dd67d1cebe140.jpeg"}]
             */

            private int userId;
            private String nickName;
            private String headUrl;
            private int id;
            private int degreeOfSatisfaction;
            private String evaluate;
            private String gmtCreate;
            private String videoUrl;
            private List<EvaluateImgListBean> evaluateImgList;

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
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

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getDegreeOfSatisfaction() {
                return degreeOfSatisfaction;
            }

            public void setDegreeOfSatisfaction(int degreeOfSatisfaction) {
                this.degreeOfSatisfaction = degreeOfSatisfaction;
            }

            public String getEvaluate() {
                return evaluate;
            }

            public void setEvaluate(String evaluate) {
                this.evaluate = evaluate;
            }

            public String getGmtCreate() {
                return gmtCreate;
            }

            public void setGmtCreate(String gmtCreate) {
                this.gmtCreate = gmtCreate;
            }

            public String getVideoUrl() {
                return videoUrl;
            }

            public void setVideoUrl(String videoUrl) {
                this.videoUrl = videoUrl;
            }

            public List<EvaluateImgListBean> getEvaluateImgList() {
                return evaluateImgList;
            }

            public void setEvaluateImgList(List<EvaluateImgListBean> evaluateImgList) {
                this.evaluateImgList = evaluateImgList;
            }

            public static class EvaluateImgListBean {
                /**
                 * id : 12
                 * imgUrl : /commodityEvaluate/5f73aefa63fa4b4288dc798fb6c53260.jpeg
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
        }
    }
}
