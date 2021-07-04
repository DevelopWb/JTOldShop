package com.juntai.shop.mall;

public class AppHttpPath {

    /**
     * 检查更新
     */
    public static final String APP_UPDATE = "";



    public static final String BASE = "http://61.156.157.132:31080/shoppingMall/u/appConnector/";
//
    public static final String B = "http://61.156.157.132:31080/shoppingMall/u/";
//    public static final String B = "http://192.168.124.118:8080/shoppingMall/u/";
//    public static final String BASE = B + "appConnector/";

    public static final String IMAGE = "http://61.156.157.132:32080";
//    public static final String IMAGE = "http://192.168.1.110:20080";
    /**
     * base
     */
    //    public static final String BASE_IMAGE = "http://image.kb167.cn/";
    public static final String BASE_IMAGE = "http://61.156.157.132:8092";
    /**
     * 流媒体缩略图地址
     */
    public static final String THUMB_ADDR = "http://61.156.157.132:8092/thumbnail/";
    /*CRM*/
    public static final String IMAGE_FOR_CRM_BIG = "http://61.156.157.132:30080/crm/u/appConnector/getImg.shtml?id=";
    public static final String IMAGE_FOR_CRM_SMALL = "http://61.156.157.132:30080/crm/u/appConnector/getThumbnailImg.shtml?id=";
    public static final String VIDEO_FOR_CRM = "http://61.156.157.132:30080/crm/u/appConnector/getVideo.shtml?id=";
    /**
     * 天气
     */
    public static final String WEATHER = "http://61.156.157.132:30080/crm/u/appConnector/getRealTimeWeather.shtml";

    /**
     * 登录
     */
    public static final String LOGIN = BASE + "appLogin.shtml";
    /**
     * 绑定第三方
     */
    public static final String BIND = BASE + "bindingQQAndWeChat.shtml";
    /**
     * 退出登录
     */
    public static final String LOGIN_OUT = BASE + "logout.shtml";
    /**
     * 用户信息
     */
    public static final String USERINFO = BASE + "selectUserInfo.shtml";
    /**
     * 修改个人信息
     */
    public static final String USER_EDIT = BASE + "updateUserInformation.shtml";
    /**
     * 修改手机号
     */
    public static final String USER_PHONE = BASE + "updatePhoneNumber.shtml";
    /**
     * 修改密码
     */
    public static final String EDIT_PASS = BASE + "updateUserPassWord.shtml";
    /**
     * 忘记密码
     */
    public static final String FIND_PASS = BASE + "updateForgetUserPassWord.shtml";
    /**
     * 修改头像
     */
    public static final String USER_HEAD_EDIT = BASE + "updateAppHeadImg.shtml";
    /**
     * 店铺位置
     */
    public static final String SHOP_LOCATION = BASE + "selectMerchantShopByLocation.shtml";
    /**
     * 摄像头位置
     */
    public static final String CAMERA_LOCATION = BASE + "getCameraLocation.shtml";
    /**
     * 摄像头详情
     */
    public static final String CAMERA_INFO = BASE + "getCameraInfo.shtml";
    /**
     * 查询商家店铺详情
     */
    public static final String SHOP_INFO1 = BASE + "selectMerchantShopInfo.shtml";
    /**
     * 店铺评论列表
     */
    public static final String SHOP_COMMENTS = BASE + "selectShopEvaluateList.shtml";
    /**
     * 商家简介及营业执照
     */
    public static final String SHOP_INFO2 = BASE + "selectShopBusinessLicenseInfo.shtml";
    /**
     * 举报商家类型
     */
    public static final String REPORT_TYPE = BASE + "selectReportMerchantType.shtml";
    /**
     * 举报商家
     */
    public static final String REPORT_SHOP = BASE + "insertReportMerchant.shtml";
    /**
     * 评论
     */
    public static final String COMMENT = BASE + "insertCommodityEvaluate.shtml";
    /**
     * 购物车
     */
    public static final String SHOP_CART = BASE + "selectShoppingTrolley.shtml";

    /**
     *  同步购物车
     */
    public static final String CART_SYS = BASE + "insertShoppingTrolley.shtml";
    /**
     * 结算购物车
     */
    public static final String SETTLEMENT = BASE + "selectTrolleyCloseAnAccount.shtml";
    /**
     * 创建订单
     */
    public static final String ORDER_CREATE = BASE + "insertOrderForm.shtml";
    /**
     * 收货地址列表
     */
    public static final String ADDRESS_LIST = BASE + "selectShippingAddressList.shtml";
    /**
     * 地址详情
     */
    public static final String ADDRESS_INFO = BASE + "selectShippingAddressInfo.shtml";
    /**
     * 添加收货地址
     */
    public static final String ADDRESS_ADD = BASE + "insertShippingAddress.shtml";
    /**
     * 删除地址
     */
    public static final String ADDRESS_DEL = BASE + "deleteShoppingAddress.shtml";
    /**
     * 修改地址
     */
    public static final String ADDRESS_UPDATE = BASE + "updateShippingAddress.shtml";
    /**
     * 查询商品详情
     */
    public static final String GOODS_INFO = BASE + "selectCommodityInfo.shtml";
    /**
     * 我的收藏
     */
    public static final String COLLECT_MY = BASE + "selectUserCollect.shtml";

    /**
     * 我的分享
     */
    public static final String SHARE_MY = BASE + "selectUserShare.shtml";
    /**
     *  我的评论
     */
    public static final String COMMENT_MY = BASE + "selectMyEvaluate.shtml";
    /**
     * 我的订单
     */
    public static final String ORDER_MY = BASE + "selectOrderFormList.shtml";
    /**
     * 订单详情
     */
    public static final String ORDER_DETAILS = BASE + "selectOrderFormInfo.shtml";
    /**
     * 获取支付宝orderinfo
     */
    public static final String ORDERINFO_ZFB = B + "AliPay/aliPayTradeAppPayRequest.shtml";
    /**
     * 微信
     */
    public static final String ORDERINFO_WX = B + "WeChatPay/weChatPayTradeAppPayRequest.shtml";
    /**
     * 确认收货
     */
    public static final String ORDER_MY_SH = BASE + "updateOrderFormConfirmStatus.shtml";
    /**
     * 取消订单
     */
    public static final String ORDER_MY_QX = BASE + "updateOrderFormCancelStatus.shtml";
    /**
     * 删除订单
     */
    public static final String ORDER_MY_DEL = BASE + "deleteMyOrderForm.shtml";
    /**
     * 查询物流
     */
    public static final String LOGISTICS = BASE + "selectPhysicalDistribution.shtml";

    /**
     * 收藏操作
     */
    public static final String COLLECTS_OPERATE = BASE + "insertCollectShopCommodity.shtml";
    /**
     * 批量删除分享
     */
    public static final String DELETE_SHARE = BASE + "deleteShareShopCommodity.shtml";
    /**
     * 搜索
     */
    public static final String SEARCH = BASE + "selectShopCommoditySearch.shtml";
    /**
     * 查询退货退款原因
     */
    public static final String RETURNCAUSE = BASE + "selectSalesReturnCause.shtml";
    /**
     * 申请提交退款/退货/换货
     */
    public static final String RETURNSTATUS = BASE + "insertSalesReturnStatus.shtml";
    /**
     * 退款退货详情
     */
    public static final String RETURN_INFO = BASE + "selectSalesReturnInfo.shtml";
    /**
     * 版本更新
     */
    public static final String UPDATE_VERSION = BASE + "detectionAppVersions.shtml";
    /**
     * 获取省(直辖市),市,县,街道
     */
    public static final String GET_PROVINCE = BASE + "getProvince.shtml";
    public static final String GET_CITY = BASE + "getCity.shtml";
    public static final String GET_AREA = BASE + "getArea.shtml";
    public static final String GET_STREET = BASE + "getStreet.shtml";



    /*====================================================    流媒体   ==============================================================*/

    //摄像头拉流地址
    public static final String BASE_CAMERA_URL = "http://www.juntaikeji.net:8060";
    //摄像头拉流地址
    public static final String BASE_CAMERA_DNS = "rtmp://www.juntaikeji.net:1935";



    //摄像头拉流地址
    public static final String BASE_CAMERA_CAPTURE_URL = "http://juntaikeji.net:8080/";


    /**BaseSearchFragment
     * 硬盘录像机和OpenLiveBean独立摄像头列表
     */
    public static final String STREAM_CAMERAS = BASE + "/u/camera/selectDvrAndCameraListAPP.shtml";
    /**
     * 硬盘录像机下面的摄像头列表
     */
    public static final String STREAM_CAMERAS_FROM_VCR = BASE + "/u/camera/selectCameraByDvrIdAPP.shtml?";
    /**
     * 摄像头详情
     */
    public static final String STREAM_CAMERAS_DETAIL = BASE + "getCameraInfo.shtml";

    /**
     * 上传封面图
     */
    public static final String UPLOAD_STREAM_CAMERAS_THUMB = BASE + "/u/camera/uploadCameraImgAPP.shtml";



}
