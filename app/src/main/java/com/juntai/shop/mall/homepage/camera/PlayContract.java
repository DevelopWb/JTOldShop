package com.juntai.shop.mall.homepage.camera;


import com.juntai.mall.base.mvp.IView;

import okhttp3.RequestBody;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/5/30 9:50
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/30 9:50
 */
public interface PlayContract{


    String GET_URL_PATH = "get_url_path";//获取流地址
    String GET_STREAM_CAMERA_DETAIL = "get_url_path_detail";//获取摄像头详情
    String GET_STREAM_CAMERA_THUMBNAIL = "get_url_path_capture";//缩略图
    String GET_STREAM_CAMERA_CAPTURE = "get_url_path_capture_save";//截图  保存本地
    String UPLOAD_CAMERA_CAPTURE = "upload_path_capture";//上传截图
    interface IPlayView extends IView {

    }

    interface IPlayPresent{
        /**
         * 打开视频流
         * @param channelid
         * @param type
         * @param videourltype
         * @param tag
         */
        void openStream(String channelid, String type, String videourltype, String tag);

        /**
         * 截图
         * @param channelid
         * @param type
         * @param tag
         */
        void  capturePic(String channelid, String type, String tag);

        /**
         * 摄像头详情
         * @param tag
         */
        void  getStreamCameraDetail(RequestBody requestBody, String tag);
        /**
         * 上传封面图
         * @param tag
         */
        void  uploadStreamCameraThumbPic(RequestBody requestBody, String tag);
    }
}
