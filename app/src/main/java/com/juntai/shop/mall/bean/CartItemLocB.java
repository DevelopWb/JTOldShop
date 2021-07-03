package com.juntai.shop.mall.bean;

import java.util.List;

/**
 * 购物车本地
 * Created by Ma
 * on 2019/11/25
 */
public class CartItemLocB {
    private int cartId;
    private int goodsId;//商品id
    private String goodsName;//商品id
    private String goodImage;//商品id
    private int num;//当前数量
    private int stock;//库存
    private double packingCharges;//包装费
    private double price;//价格
    private int spcId;//规格id
    private String spcName;//规格名称拼接

    public CartItemLocB() {
    }

    public CartItemLocB(int cartId, int goodsId, String goodsName, String goodImage, int num, int stock, double packingCharges, double price, int spcId, String spcName) {
        this.cartId = cartId;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodImage = goodImage;
        this.num = num;
        this.stock = stock;
        this.packingCharges = packingCharges;
        this.price = price;
        this.spcId = spcId;
        this.spcName = spcName;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodImage() {
        return goodImage;
    }

    public void setGoodImage(String goodImage) {
        this.goodImage = goodImage;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPackingCharges() {
        return packingCharges;
    }

    public void setPackingCharges(double packingCharges) {
        this.packingCharges = packingCharges;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSpcId() {
        return spcId;
    }

    public void setSpcId(int spcId) {
        this.spcId = spcId;
    }

    public String getSpcName() {
        return spcName;
    }

    public void setSpcName(String spcName) {
        this.spcName = spcName;
    }

    @Override
    public String toString() {
        return "CartItemLocB{" +
                "cartId=" + cartId +
                ", goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", goodImage='" + goodImage + '\'' +
                ", num=" + num +
                ", stock=" + stock +
                ", packingCharges=" + packingCharges +
                ", price=" + price +
                ", spcId=" + spcId +
                ", spcName='" + spcName + '\'' +
                '}';
    }
}
