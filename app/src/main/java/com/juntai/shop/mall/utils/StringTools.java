package com.juntai.shop.mall.utils;

import com.juntai.mall.bdmap.utils.MD5;
import com.juntai.shop.mall.AppHttpPath;
import com.mob.tools.utils.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Ma
 * on 2019/4/16
 */
public class StringTools {


    public static String getStringPrice(double data) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(data);
    }
    /**
     * 时间戳转 yyyy-MM-dd HH:mm:ss
     *
     * @param data
     * @return
     */
    public static String getStringData(long data) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(data);
    }

    public static Date getDataForString(String data){
        try {
            Date parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data);
            return parse;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * 时间戳转自定义
     *
     * @param pattern
     * @param data
     * @return
     */
    public static String getStringData(String pattern, long data) {
        return new SimpleDateFormat(pattern).format(data);
    }

    /**
     * 原图
     * @param id
     * @return
     */
    public static String getImageUrlForInt(int id) {
        return AppHttpPath.IMAGE + "?id=" + id;
    }

    public static String getImageForCrmBig(String id) {
        return AppHttpPath.IMAGE_FOR_CRM_BIG + id;
    }
    public static String getImageForCrmSmall(String id) {
        return AppHttpPath.IMAGE_FOR_CRM_SMALL + id;
    }
    public static String getImageForCrmInt(int id) {
        return AppHttpPath.IMAGE_FOR_CRM_BIG + id;
    }

    /**
     * 大图
     * @param str
     * @return
     */
    public static ArrayList<String> getImagesForCrmBig(String str) {
        String[] ss = getStrings(str);
        for (int i = 0; i < ss.length; i++) {
            ss[i] = getImageForCrmBig(ss[i]);
        }
        return new ArrayList<>(Arrays.asList(ss));
    }
    /**
     * 缩略图
     * @param str
     * @return
     */
    public static ArrayList<String> getImagesForCrmSmall(String str) {
        String[] ss = getStrings(str);
        for (int i = 0; i < ss.length; i++) {
            ss[i] = getImageForCrmSmall(ss[i]);
        }
        return new ArrayList<>(Arrays.asList(ss));
    }

    /**
     * 分割字符串--空的话默认0
     *
     * @param str
     * @return
     */
    public static String[] getStringsDefault0(String str) {
        if (str != null && !"".equals(str))
            if (str.contains(","))
                return str.split(",");
            else
                return new String[]{str};
        else
            return new String[]{"0"};
    }

    /**
     * 分割字符串
     * @param str
     * @return
     */
    public static String[] getStrings(String str) {
        if (str != null && !"".equals(str))
            if (str.contains(","))
                return str.split(",");
            else
                return new String[]{str};
        else
            return new String[]{};
    }

    /**
     * 标签需要
     * @param str
     * @return
     */
    public static List<String> getStringsArray(String str) {
        try {
            if (str != null && !"".equals(str))
                if (str.contains(","))
                    return Arrays.asList(str.split(","));
                else
                    return new ArrayList<>(Arrays.asList(str));
            else
                return new ArrayList<>();
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    public static String passMD(String a, String b) {
        return MD5.md5(String.format("%s#%s", a, b));
    }
}
