package com.juntai.shop.mall.bean;

import com.juntai.mall.base.base.BaseResult;

/**
 * Created by Ma
 * on 2019/12/17
 */
public class LogisticsBean extends BaseResult {

    /**
     * error : null
     * returnValue : {"id":1,"expressAge":"顺丰快递","expressAgeNumber":"123523633266562263266","expressAgeLink":"www.baidu.com","gmtCreate":"2019-11-27 08:46:59"}
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
         * id : 1
         * expressAge : 顺丰快递
         * expressAgeNumber : 123523633266562263266
         * expressAgeLink : www.baidu.com
         * gmtCreate : 2019-11-27 08:46:59
         */

        private int id;
        private String expressAge;
        private String expressAgeNumber;
        private String expressAgeLink;
        private String gmtCreate;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getExpressAge() {
            return expressAge;
        }

        public void setExpressAge(String expressAge) {
            this.expressAge = expressAge;
        }

        public String getExpressAgeNumber() {
            return expressAgeNumber;
        }

        public void setExpressAgeNumber(String expressAgeNumber) {
            this.expressAgeNumber = expressAgeNumber;
        }

        public String getExpressAgeLink() {
            return expressAgeLink;
        }

        public void setExpressAgeLink(String expressAgeLink) {
            this.expressAgeLink = expressAgeLink;
        }

        public String getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(String gmtCreate) {
            this.gmtCreate = gmtCreate;
        }
    }
}
