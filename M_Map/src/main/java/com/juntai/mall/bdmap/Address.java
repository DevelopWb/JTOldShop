package com.juntai.mall.bdmap;

/**
 * Created by Ma
 * on 2019/7/25
 */
public class Address {
    String address;
    public boolean ischecked;

    public Address(String address, boolean ischecked) {
        this.address = address;
        this.ischecked = ischecked;
    }

    public void setIschecked(boolean ischecked) {
        this.ischecked = ischecked;
    }


    public String getAddress() {
        return address;
    }

    public boolean isIschecked() {
        return ischecked;
    }
}
