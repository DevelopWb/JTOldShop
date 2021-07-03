package com.juntai.shop.mall.bean;

/**
 * 购物车商品
 * @aouther Ma
 * @date 2019/3/5
 */
public class ItemShopCartGoods {
    public String title;
    public String subTitle;
    public double price;
    public int number = 1;

    public ItemShopCartGoods(String title, String subTitle, double price) {
        this.title = title;
        this.subTitle = subTitle;
        this.price = price;
    }
}