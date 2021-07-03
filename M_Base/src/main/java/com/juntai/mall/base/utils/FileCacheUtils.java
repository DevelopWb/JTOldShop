package com.juntai.mall.base.utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;

import com.juntai.mall.base.app.BaseApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 * 文件
 * @aouther Ma
 * @date 2019/3/21
 */
public class FileCacheUtils {
    public static  String  STREAM_THUMBNAIL = "streamThumbnail";//流媒体缩略图目录
    public static  String  STREAM_CAPTURE = "摄像头截图/";//流媒体截图目录
    /**
     * 获取app文件地址
     * @return
     */
    public static String getAppPath(){
        File destDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                File.separator + BaseAppUtils.getAppName());
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        return destDir.getAbsolutePath() +"/";
    }
    /**
     * 压缩图片存放目录
     * @return
     */
    public static String getAppImagePath(String dirName){
        File destDir = new File(getAppPath() + "image/"+ dirName+File.separator);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        return destDir.getAbsolutePath() + File.separator;
    }
    /**
     * 下载图片的地址
     * @return
     */
    public static String getImageSavePath(){
        File destDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                File.separator + "/zhly0");
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        return destDir.getAbsolutePath() +"/";
    }

    /**
     * 压缩图片存放目录
     * @return
     */
    public static String getAppImagePath(){
        File destDir = new File(getAppPath() + "image/");
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        return destDir.getAbsolutePath() + "/";
    }

    /**
     * 获取video缓存目录
     * @return
     */
    public static String getAppVideoPath(){
        File destDir = new File(getAppPath() + "video/");
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        return destDir.getAbsolutePath() + "/";
    }
    public static String getAppRecordVideoPath(){
        File destDir = new File(getAppPath() + "recordVideo/");
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        return destDir.getAbsolutePath() + "/";
    }

    public static String getAppVoicePath(){
        File destDir = new File(getAppPath() + "scenicVoice/");
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        return destDir.getAbsolutePath() + "/";
    }


    public static String saveBitmap(Bitmap bmp) {
        FileOutputStream out;
        Calendar calendar = Calendar.getInstance();
        String bitmapName = String.valueOf(calendar.get(Calendar.YEAR)) + String.valueOf(calendar.get(Calendar.MONTH)) + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + 1)
                + String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)) + String.valueOf(calendar.get(Calendar.MINUTE)) + String.valueOf(calendar.get(Calendar.SECOND)) + ".jpg";
        File file;
        String path = null;
        try {
            // 获取SDCard指定目录下
            String sdCardDir = getAppImagePath();
            File dirFile = new File(sdCardDir);  //目录转化成文件夹
            if (!dirFile.exists()) {              //如果不存在，那就建立这个文件夹
                dirFile.mkdirs();
            }
            file = new File(sdCardDir, bitmapName);
            out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            path = sdCardDir + bitmapName;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }


    /**
     * 创建文件夹
     * @param fileName
     */
    public static void creatFile(String fileName){

        File file = new File(fileName);

        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 清除图片缓存
     */
    public static void clearImages(List<String> lists){
        try {
            //判断是否为目录
            for (String path:lists) {
                new File(path).delete();
                BaseApplication.app.getContentResolver().delete(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.Media.DATA + "=?", new String[]{path});//删除系统缩略图
            }
        }catch (Exception e){
            LogUtil.e("image-删除缓存文件失败="+e.toString());
        }
    }
    /**
     * 清除图片缓存-固定文件夹内
     */
    public static void clearImage(){
        try {
            deleteFile(new File(getAppImagePath()));
        }catch (Exception e){
            LogUtil.e("image-删除缓存文件失败="+e.toString());
        }
    }

    /**
     * 清除视频缓存
     */
    public static void clearVideo(){
        try {
            deleteFile(new File(getAppVideoPath()));
        }catch (Exception e){
            LogUtil.e("video-删除缓存文件失败="+e.toString());
        }
    }

    /**
     * 清除所有缓存
     */
    public static void clearAll(){
        try {
            deleteFile(new File(getAppPath()));
//            deleteFile(new File(getAppImagePath()));
//            deleteFile(new File(getAppVideoPath()));
        }catch (Exception e){
            LogUtil.e("all-删除缓存文件失败="+e.toString());
        }
    }

    /**
     * 删除文件
     * @param file
     */
    private static void deleteFile(File file){
        //判断是否为目录
        for (File ff:file.listFiles()) {
            if (ff.isDirectory()){
                deleteFile(ff);
            }
            ff.delete();
            BaseApplication.app.getContentResolver().delete(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.Media.DATA + "=?", new String[]{ff.getPath()});//删除系统缩略图
        }
    }


    /**文件大小*/
    private static float cacheSize = 0.00f;
    /**
     * 获取文件缓存大小
     * @return
     */
    public static String getCacheSize(){
        File file = new File(getAppPath());
        cacheSize = 0.00f;
        getCacheSize(file);
        //LogUtil.e("fff文件缓存大小 = "+ String.format("%.2f",(cacheSize / 1024)) + " k");
        return String.format("%.2f",(cacheSize / 1024 / 1024)) + "m";
    }

    /**
     * 递归查看文件大小
     * @param file
     */
    private static void getCacheSize(File file){
        //判断是否为目录
        for (File ff:file.listFiles()) {
            cacheSize += ff.length();
            if (ff.isDirectory()){
                getCacheSize(ff);
            }
        }
    }


}
