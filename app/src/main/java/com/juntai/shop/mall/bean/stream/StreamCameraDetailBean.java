package com.juntai.shop.mall.bean.stream;


import com.juntai.mall.base.base.BaseResult;
import com.juntai.shop.mall.bean.CameraDetailsBean;

/**
 * @Author: tobato
 * @Description: 作用描述   摄像头详情
 * @CreateDate: 2020/6/3 8:34
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/6/3 8:34
 */
public class StreamCameraDetailBean extends BaseResult {
    /**
     * error : null
     * returnValue : {"id":65,"number":"638187229","remark":"沂河","place":"山东省临沂市沂水县滨河西路","imgUrl":"0","maccountId":6,"account":"13656419301","appKey":"f1ddf5933add421db2f09e1f4bb1dc9d","accessToken":"at.7abv2z2s3vq420x25om626s266kgtysu-6pliyd6ivf-0hcmfdv-i0iglprdd","hlshd":"http://hls01open.ys7.com/openlive/9243b5aba21c455c86c40407b510b9b0.hd.m3u8","rtmphd":"rtmp://rtmp01open.ys7.com/openlive/9243b5aba21c455c86c40407b510b9b0.hd"}
     * msg : null
     * code : null
     * type : null
     */

    private StreamCameraDetailBean.ReturnValueBean returnValue;

    public StreamCameraDetailBean.ReturnValueBean getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(StreamCameraDetailBean.ReturnValueBean returnValue) {
        this.returnValue = returnValue;
    }

    public static class ReturnValueBean {
        private Integer id;//监控id

        private String number;//监控编号

        private String name;//监控名称

        private String ezOpen;//监控封面图片

        private String address;//监控地址

        private Integer viewNumber;//访问量

        private Integer isOnline;//是否在线（0离线；1在线

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getNumber() {
            return number == null ? "" : number;
        }

        public void setNumber(String number) {
            this.number = number == null ? "" : number;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name == null ? "" : name;
        }

        public String getEzOpen() {
            return ezOpen == null ? "" : ezOpen;
        }

        public void setEzOpen(String ezOpen) {
            this.ezOpen = ezOpen == null ? "" : ezOpen;
        }

        public String getAddress() {
            return address == null ? "" : address;
        }

        public void setAddress(String address) {
            this.address = address == null ? "" : address;
        }

        public Integer getViewNumber() {
            return viewNumber;
        }

        public void setViewNumber(Integer viewNumber) {
            this.viewNumber = viewNumber;
        }

        public Integer getIsOnline() {
            return isOnline;
        }

        public void setIsOnline(Integer isOnline) {
            this.isOnline = isOnline;
        }
    }
}
