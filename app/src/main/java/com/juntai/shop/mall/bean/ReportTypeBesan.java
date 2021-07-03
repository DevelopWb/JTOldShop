package com.juntai.shop.mall.bean;

import com.juntai.mall.base.base.BaseResult;

import java.util.List;

/**
 * Created by Ma
 * on 2019/12/18
 */
public class ReportTypeBesan extends BaseResult {

    /**
     * error : null
     * returnValue : [{"id":1,"name":"商家刷单"},{"id":2,"name":"价格高于店内价格"},{"id":3,"name":"商家资质问题"},{"id":4,"name":"商品问题"}]
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
         * id : 1
         * name : 商家刷单
         */

        private int id;
        private String name;

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
    }
}
