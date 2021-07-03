package com.juntai.shop.mall.bean;

/**
 * 所有规格集合
 * Created by Ma
 * on 2019/11/30
 */
public class SpecificationsBean {
    int id;
    double packingCharges;//包装费
    int inventoryNum;//库存
    double price;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPackingCharges() {
        return packingCharges;
    }

    public void setPackingCharges(double packingCharges) {
        this.packingCharges = packingCharges;
    }

    public int getInventoryNum() {
        return inventoryNum;
    }

    public void setInventoryNum(int inventoryNum) {
        this.inventoryNum = inventoryNum;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
}
