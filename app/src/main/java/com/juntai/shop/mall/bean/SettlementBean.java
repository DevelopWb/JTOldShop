package com.juntai.shop.mall.bean;

import com.juntai.mall.base.base.BaseResult;

import java.util.List;

/**
 * Created by Ma
 * on 2019/12/16
 */
public class SettlementBean extends BaseResult {

    /**
     * error : null
     * returnValue : {"transportCharges":2,"footing":22,"sumPackingCharges":5,"commodity":[{"commodityId":50,"price":15,"commodityNum":1,"packingCharges":5,"transportCharges":2}]}
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
         * transportCharges : 2.0
         * footing : 22.0
         * sumPackingCharges : 5.0
         * commodity : [{"commodityId":50,"price":15,"commodityNum":1,"packingCharges":5,"transportCharges":2}]
         */

        private double transportCharges;
        private double footing;
        private double sumPackingCharges;
        private List<OrderCommodityListBean> commodity;

        public double getTransportCharges() {
            return transportCharges;
        }

        public void setTransportCharges(double transportCharges) {
            this.transportCharges = transportCharges;
        }

        public double getFooting() {
            return footing;
        }

        public void setFooting(double footing) {
            this.footing = footing;
        }

        public double getSumPackingCharges() {
            return sumPackingCharges;
        }

        public void setSumPackingCharges(double sumPackingCharges) {
            this.sumPackingCharges = sumPackingCharges;
        }

        public List<OrderCommodityListBean> getCommodity() {
            return commodity;
        }

        public void setCommodity(List<OrderCommodityListBean> commodity) {
            this.commodity = commodity;
        }

    }
}
