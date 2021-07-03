package com.juntai.shop.mall.bean;

import com.juntai.mall.base.base.BaseResult;

import java.util.List;

/**
 * 退货退款原因
 * Created by Ma
 * on 2019/12/23
 */
public class ReturnReasonBean extends BaseResult {

    /**
     * error : null
     * returnValue : [{"id":1,"causeName":"快递一直未到"},{"id":2,"causeName":"未按照约定时间发货"},{"id":3,"causeName":"快递无跟踪记录"},{"id":4,"causeName":"其他"}]
     * msg : null
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
         * causeName : 快递一直未到
         */

        private int id;
        private String causeName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCauseName() {
            return causeName;
        }

        public void setCauseName(String causeName) {
            this.causeName = causeName;
        }
    }
}
