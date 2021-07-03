package com.juntai.shop.mall.bean;

import com.juntai.mall.base.base.BaseResult;

import java.io.Serializable;

/**
 * @aouther Ma
 * @date 2019/3/4
 */
public class UserB extends BaseResult implements Serializable{

    /**
     * error : null
     * returnValue : {"id":6,"account":"15854968931","phone":"15854968931","birthday":null,"nickName":"sc_552bpsdorhm4mn","headUrl":"/userHead/default.jpg","weChatName":null,"qqName":null,"orderFormNum":1,"commodityEvaluateNum":0,"myCollectNum":2,"myShareNum":0}
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

    public static class ReturnValueBean implements Serializable {
        /**
         * id : 6
         * account : 15854968931
         * phone : 15854968931
         * birthday : null
         * nickName : sc_552bpsdorhm4mn
         * headUrl : /userHead/default.jpg
         * weChatName : null
         * qqName : null
         * orderFormNum : 1
         * commodityEvaluateNum : 0
         * myCollectNum : 2
         * myShareNum : 0
         */

        private int id;
        private String account;
        private String phone;
        private String birthday;
        private String nickName;
        private String headUrl;
        private String weChatName;
        private String qqName;
        private String weChatId;
        private String qqId;
        private String customerServicePhone;
        private int orderFormNum;
        private int commodityEvaluateNum;
        private int myCollectNum;
        private int myShareNum;

        public String getCustomerServicePhone() {
            return customerServicePhone;
        }

        public void setCustomerServicePhone(String customerServicePhone) {
            this.customerServicePhone = customerServicePhone;
        }

        public String getWeChatId() {
            return weChatId;
        }

        public void setWeChatId(String weChatId) {
            this.weChatId = weChatId;
        }

        public String getQqId() {
            return qqId;
        }

        public void setQqId(String qqId) {
            this.qqId = qqId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
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

        public String getWeChatName() {
            return weChatName;
        }

        public void setWeChatName(String weChatName) {
            this.weChatName = weChatName;
        }

        public String getQqName() {
            return qqName;
        }

        public void setQqName(String qqName) {
            this.qqName = qqName;
        }

        public int getOrderFormNum() {
            return orderFormNum;
        }

        public void setOrderFormNum(int orderFormNum) {
            this.orderFormNum = orderFormNum;
        }

        public int getCommodityEvaluateNum() {
            return commodityEvaluateNum;
        }

        public void setCommodityEvaluateNum(int commodityEvaluateNum) {
            this.commodityEvaluateNum = commodityEvaluateNum;
        }

        public int getMyCollectNum() {
            return myCollectNum;
        }

        public void setMyCollectNum(int myCollectNum) {
            this.myCollectNum = myCollectNum;
        }

        public int getMyShareNum() {
            return myShareNum;
        }

        public void setMyShareNum(int myShareNum) {
            this.myShareNum = myShareNum;
        }
    }
}
