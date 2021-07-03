package com.juntai.shop.mall.bean;

import com.juntai.mall.base.base.BaseResult;

/**
 * Created by Ma
 * on 2019/11/13
 */
public class LoginBean extends BaseResult {

    /**
     * error : null
     * returnValue : {"id":5,"account":"123456","password":null,"phone":"123456","nickName":"sc_o0213d3r0vppm6","headUrl":"default.jpeg","birthday":null,"rOngYunToken":"Aw1Uo4/KiSKuqKBmeVSqs5fmVSrUsVMXgwWrrlIS+CZ5Yt4RacyCuPNBSjObvZaG07KJ2sp2jpg5ewxMlcWqdg==","sex":0,"age":0,"weChatId":null,"weChatName":null,"qqId":null,"qqName":null,"status":0,"frozenStatus":0,"gmtCreate":"2019-11-13 08:49:39","lastLoginTime":null,"token":"B2AG55O992-99NASXKJWB85906HLYOX2-0O2IKW2K-0"}
     * list : null
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
         * id : 5
         * account : 123456
         * password : null
         * phone : 123456
         * nickName : sc_o0213d3r0vppm6
         * headUrl : default.jpeg
         * birthday : null
         * rOngYunToken : Aw1Uo4/KiSKuqKBmeVSqs5fmVSrUsVMXgwWrrlIS+CZ5Yt4RacyCuPNBSjObvZaG07KJ2sp2jpg5ewxMlcWqdg==
         * sex : 0
         * age : 0
         * weChatId : null
         * weChatName : null
         * qqId : null
         * qqName : null
         * status : 0
         * frozenStatus : 0
         * gmtCreate : 2019-11-13 08:49:39
         * lastLoginTime : null
         * token : B2AG55O992-99NASXKJWB85906HLYOX2-0O2IKW2K-0
         */

        private int id;
        private String account;
        private String password;
        private String phone;
        private String nickName;
        private String headUrl;
        private String birthday;
        private String rOngYunToken;
        private int sex;
        private int age;
        private String weChatId;
        private String weChatName;
        private String qqId;
        private String qqName;
        private int status;
        private int frozenStatus;
        private String gmtCreate;
        private String lastLoginTime;
        private String token;

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

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
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

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getrOngYunToken() {
            return rOngYunToken;
        }

        public void setrOngYunToken(String rOngYunToken) {
            this.rOngYunToken = rOngYunToken;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getWeChatId() {
            return weChatId;
        }

        public void setWeChatId(String weChatId) {
            this.weChatId = weChatId;
        }

        public String getWeChatName() {
            return weChatName;
        }

        public void setWeChatName(String weChatName) {
            this.weChatName = weChatName;
        }

        public String getQqId() {
            return qqId;
        }

        public void setQqId(String qqId) {
            this.qqId = qqId;
        }

        public String getQqName() {
            return qqName;
        }

        public void setQqName(String qqName) {
            this.qqName = qqName;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getFrozenStatus() {
            return frozenStatus;
        }

        public void setFrozenStatus(int frozenStatus) {
            this.frozenStatus = frozenStatus;
        }

        public String getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(String gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public String getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(String lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
