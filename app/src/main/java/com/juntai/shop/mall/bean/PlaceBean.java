package com.juntai.shop.mall.bean;

import com.juntai.mall.base.base.BaseResult;

import java.util.List;

/**
 * 省市县街道地址
 * Created by Ma
 * on 2019/9/25
 */
public class PlaceBean extends BaseResult {
    /**
     * error : null
     * returnValue : [{"id":1,"name":"北京","latitude":39.904987,"longitude":116.405289,"cityNum":"110000"},{"id":2,"name":"天津","latitude":39.125595,"longitude":117.190186,"cityNum":"120000"},{"id":3,"name":"河北省","latitude":38.045475,"longitude":114.502464,"cityNum":"130000"},{"id":4,"name":"山西省","latitude":37.857014,"longitude":112.549248,"cityNum":"140000"},{"id":5,"name":"内蒙古自治区","latitude":40.81831,"longitude":111.670799,"cityNum":"150000"},{"id":6,"name":"辽宁省","latitude":41.796768,"longitude":123.429092,"cityNum":"210000"},{"id":7,"name":"吉林省","latitude":43.886841,"longitude":125.324501,"cityNum":"220000"},{"id":8,"name":"黑龙江省","latitude":45.756966,"longitude":126.642464,"cityNum":"230000"}]
     * msg : null
     * list : null
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
         * id : 1
         * name : 北京
         * latitude : 39.904987
         * longitude : 116.405289
         * cityNum : 110000
         */
        private String pinYin;
        private int id;
        private String name = "";
        private double latitude;
        private double longitude;
        private long cityNum = 0;

        public String getPinYin() {
            return pinYin;
        }

        public void setPinYin(String pinYin) {
            this.pinYin = pinYin;
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

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public long getCityNum() {
            return cityNum;
        }

        public void setCityNum(long cityNum) {
            this.cityNum = cityNum;
        }

        @Override
        public String toString() {
            return "ReturnValueBean{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", latitude=" + latitude +
                    ", longitude=" + longitude +
                    ", cityNum='" + cityNum + '\'' +
                    '}';
        }
    }
}
