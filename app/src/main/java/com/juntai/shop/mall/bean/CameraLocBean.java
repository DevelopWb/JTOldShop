package com.juntai.shop.mall.bean;

import com.juntai.mall.base.base.BaseResult;

import java.util.List;

/**
 * 摄像头位置
 * Created by Ma
 * on 2019/12/23
 */
public class CameraLocBean extends BaseResult {

    /**
     * error : null
     * returnValue : [{"id":65,"latitude":35.800151,"longitude":118.620137,"imgUrl":"0"},{"id":66,"latitude":35.797516,"longitude":118.626677,"imgUrl":"0"},{"id":71,"latitude":35.776143,"longitude":118.63839,"imgUrl":"6778"}]
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
         * id : 65
         * latitude : 35.800151
         * longitude : 118.620137
         * imgUrl : 0
         */

        private Integer id;//监控id

        private String number;//监控编号

        private String address;//地址

        private String latitude;//维度

        private String longitude;//经度

        private String name;//监控名称

        private String ezOpen;//监控封面图片

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getNumber() {
            return number == null ? "" : number;
        }

        public void setNumber(String number) {
            this.number = number == null ? "" : number;
        }

        public String getAddress() {
            return address == null ? "" : address;
        }

        public void setAddress(String address) {
            this.address = address == null ? "" : address;
        }

        public String getLatitude() {
            return latitude == null ? "" : latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude == null ? "" : latitude;
        }

        public String getLongitude() {
            return longitude == null ? "" : longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude == null ? "" : longitude;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name == null ? "" : name;
        }

        public String getEzOpen() {
            return ezOpen == null ? "" : ezOpen;
        }

        public void setEzOpen(String ezOpen) {
            this.ezOpen = ezOpen == null ? "" : ezOpen;
        }
    }
}
