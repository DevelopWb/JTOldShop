package com.juntai.shop.mall.utils;


import com.juntai.shop.mall.bean.LoginBean;
import com.orhanobut.hawk.Hawk;

/**
 * @Author: tobato
 * @Description: 作用描述   用户信息管理类
 * @CreateDate: 2020/7/9 10:05
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/9 10:05
 */
public class UserInfoManager {


    /**
     * 获取用户信息
     * @return
     */
    public static LoginBean.ReturnValueBean  getUserBean(){
        return Hawk.get(HawkProperty.USER_INFO);
    }
    /**
     * 获取用户信息
     * @return
     */
    public static int  getUserId(){
        return getUserBean().getId();
    }

    /**
     * 获取用户信息 token
     * @return
     */
    public static String  getUserToken(){
        return getUserBean().getToken();
    }
    /**
     * 获取用户信息 pwd
     * @return
     */
    public static String  getUserPwd(){
        return getUserBean().getPassword();
    }
    /**
     * 获取用户信息 Account
     * @return
     */
    public static String  getUserAccount(){
        return getUserBean().getAccount();
    }


}
