package com.juntai.shop.mall.bean;

import com.juntai.mall.base.base.BaseResult;

/**
 * Created by Ma
 * on 2019/12/10
 */
public class ShopInfoBean extends BaseResult {

    /**
     * error : null
     * returnValue : {"id":1,"contactsPhone":"15012345678","region":"山东省临沂市河东区九曲街道","address":"东夷大街","synopsis":"小吃实惠\n","businessImg":121,"sanitationImg":122}
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
         * id : 1
         * contactsPhone : 15012345678
         * region : 山东省临沂市河东区九曲街道
         * address : 东夷大街
         * synopsis : 小吃实惠
         * businessImg : 121
         * sanitationImg : 122
         */

        private int id;
        private String contactsPhone;
        private String region;
        private String address;
        private String synopsis;
        private int businessImg;
        private int sanitationImg;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContactsPhone() {
            return contactsPhone;
        }

        public void setContactsPhone(String contactsPhone) {
            this.contactsPhone = contactsPhone;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getSynopsis() {
            return synopsis;
        }

        public void setSynopsis(String synopsis) {
            this.synopsis = synopsis;
        }

        public int getBusinessImg() {
            return businessImg;
        }

        public void setBusinessImg(int businessImg) {
            this.businessImg = businessImg;
        }

        public int getSanitationImg() {
            return sanitationImg;
        }

        public void setSanitationImg(int sanitationImg) {
            this.sanitationImg = sanitationImg;
        }
    }
}
