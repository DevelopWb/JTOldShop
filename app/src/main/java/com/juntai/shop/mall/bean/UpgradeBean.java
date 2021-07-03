package com.juntai.shop.mall.bean;

import com.juntai.mall.base.base.BaseResult;

/**
 * Created by Ma
 * on 2019/12/24
 */
public class UpgradeBean extends BaseResult {
    /**
     * error : null
     * returnValue : {"fileName":"客户管理.apk","versionsCode":2,"versionsName":"v1.1","downloadLink":"http://192.168.1.110:20080/downloadLocal/客户管理.apk","constraintUpdate":false,"gmtCreate":"2019-12-23 15:42:41"}
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
         * fileName : 客户管理.apk
         * versionsCode : 2
         * versionsName : v1.1
         * downloadLink : http://192.168.1.110:20080/downloadLocal/客户管理.apk
         * constraintUpdate : false
         * gmtCreate : 2019-12-23 15:42:41
         */

        private String fileName;
        private int versionsCode;
        private String versionsName;
        private String updateContent;
        private String downloadLink;
        private boolean constraintUpdate;
        private String gmtCreate;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getUpdateContent() {
            return updateContent;
        }

        public void setUpdateContent(String updateContent) {
            this.updateContent = updateContent;
        }

        public int getVersionsCode() {
            return versionsCode;
        }

        public void setVersionsCode(int versionsCode) {
            this.versionsCode = versionsCode;
        }

        public String getVersionsName() {
            return versionsName;
        }

        public void setVersionsName(String versionsName) {
            this.versionsName = versionsName;
        }

        public String getDownloadLink() {
            return downloadLink;
        }

        public void setDownloadLink(String downloadLink) {
            this.downloadLink = downloadLink;
        }

        public boolean isConstraintUpdate() {
            return constraintUpdate;
        }

        public void setConstraintUpdate(boolean constraintUpdate) {
            this.constraintUpdate = constraintUpdate;
        }

        public String getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(String gmtCreate) {
            this.gmtCreate = gmtCreate;
        }
    }
}
