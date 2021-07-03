package com.juntai.shop.mall.bean;

import java.io.Serializable;

/**
 * 订单内-商品列表
 * Created by Ma
 * on 2019/12/9
 * private boolean checked;
 */
public class OrderCommodityListBean implements Serializable {

    /**
     * commodityId : 50
     * commodityName : 背心
     * commodityImg : /commodityImg/5ea55858e8d94988bd940d58ee1ad6fe.jpeg
     * price : 15.0
     * commodityNumber : 1
     * packingCharges : 5.0
     * transportCharges : 2.0
     * parameterName : 绿色;L
     */

    private int id;
    private int commodityId;
    private String commodityName;
    private String commodityImg;
    private double price;
    private int commodityNumber;
    private double packingCharges;
    private double transportCharges;
    private String parameterName;
    private boolean checked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCommodityImg() {
        return commodityImg;
    }

    public void setCommodityImg(String commodityImg) {
        this.commodityImg = commodityImg;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCommodityNumber() {
        return commodityNumber;
    }

    public void setCommodityNumber(int commodityNumber) {
        this.commodityNumber = commodityNumber;
    }

    public double getPackingCharges() {
        return packingCharges;
    }

    public void setPackingCharges(double packingCharges) {
        this.packingCharges = packingCharges;
    }

    public double getTransportCharges() {
        return transportCharges;
    }

    public void setTransportCharges(double transportCharges) {
        this.transportCharges = transportCharges;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }
}
