package com.juntai.shop.mall;

import com.juntai.mall.base.base.BaseResult;
import com.juntai.mall.base.bean.OpenLiveBean;
import com.juntai.shop.mall.bean.AddressInfoBean;
import com.juntai.shop.mall.bean.AddressListBean;
import com.juntai.shop.mall.bean.CameraDetailsBean;
import com.juntai.shop.mall.bean.CameraLocBean;
import com.juntai.shop.mall.bean.CartB;
import com.juntai.shop.mall.bean.GoodsB;
import com.juntai.shop.mall.bean.IntBean;
import com.juntai.shop.mall.bean.LogisticsBean;
import com.juntai.shop.mall.bean.MallHomeB;
import com.juntai.shop.mall.bean.MyCollcetB;
import com.juntai.shop.mall.bean.MyCommentB;
import com.juntai.shop.mall.bean.NearB;
import com.juntai.shop.mall.bean.OrderConfirmB;
import com.juntai.shop.mall.bean.OrderCreateBean;
import com.juntai.shop.mall.bean.OrderInfoBean;
import com.juntai.shop.mall.bean.OrderListBean;
import com.juntai.shop.mall.bean.OrderPayWxBean;
import com.juntai.shop.mall.bean.PlaceBean;
import com.juntai.shop.mall.bean.ReportTypeBesan;
import com.juntai.shop.mall.bean.ReturnDetailsBean;
import com.juntai.shop.mall.bean.ReturnReasonBean;
import com.juntai.shop.mall.bean.SearchGoodsBean;
import com.juntai.shop.mall.bean.SearchShopBean;
import com.juntai.shop.mall.bean.SettlementBean;
import com.juntai.shop.mall.bean.ShopCartsBean;
import com.juntai.shop.mall.bean.ShopCommentsBean;
import com.juntai.shop.mall.bean.ShopBean;
import com.juntai.shop.mall.bean.ShopInfoBean;
import com.juntai.shop.mall.bean.ShopLocationB;
import com.juntai.shop.mall.bean.StringBean;
import com.juntai.shop.mall.bean.UserB;
import com.juntai.shop.mall.bean.WeatherBean;
import com.juntai.shop.mall.bean.stream.StreamCameraBean;
import com.juntai.shop.mall.bean.stream.StreamCameraDetailBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface AppServer {
    /**
     * 登录
     * @param account：手机号
     * @param password：密码
     * @param weChatId：微信id
     * @param weChatName：微信昵称
     * @param qqId：qqid
     * @param qqName:qq昵称
     * @return
     */
    @POST(AppHttpPath.LOGIN)
    Observable<ResponseBody> login(@Query("account")String account,
                             @Query("password")String password,
                             @Query("weChatId")String weChatId,
                             @Query("weChatName")String weChatName,
                             @Query("qqId")String qqId,
                             @Query("qqName")String qqName);

    @POST(AppHttpPath.BIND)
    Observable<BaseResult> bind(@Query("account")String account,
                                 @Query("token")String token,
                                 @Query("weChatId")String weChatId,
                                 @Query("weChatName")String weChatName,
                                 @Query("qqId")String qqId,
                                 @Query("qqName")String qqName);

    /**
     * 退出登录
     * @param account
     * @param token
     * @return
     */
    @POST(AppHttpPath.LOGIN_OUT)
    Observable<BaseResult> loginOut(@Query("account")String account,
                                    @Query("token")String token);

    /**
     * 用户信息
     * @param account
     * @param token
     * @param purchaserId
     * @return
     */
    @GET(AppHttpPath.USERINFO)
    Observable<UserB> userInfo(@Query("account")String account,
                               @Query("token")String token,
                               @Query("purchaserId")int purchaserId);

    /**
     * 修改密码
     * @param account
     * @param token
     * @param password:rsa后的
     * @return
     */
    @POST(AppHttpPath.EDIT_PASS)
    Observable<BaseResult> editPass(@Query("account")String account,
                               @Query("token")String token,
                               @Query("password")String password);

    /**
     * 忘记密码
     * @param account
     * @param NewPassWord
     * @return
     */
    @POST(AppHttpPath.FIND_PASS)
    Observable<BaseResult> findPass(@Query("account")String account,
                                    @Query("NewPassWord")String NewPassWord);

    /**
     * 修改用户信息
     * @param account
     * @param token
     * @param id
     * @param nickName
     * @param birthday
     * @return
     */
    @POST(AppHttpPath.USER_EDIT)
    Observable<UserB> userInfoEdit(@Query("account")String account,
                                   @Query("token")String token,
                                   @Query("id")int id,
                                   @Query("nickName")String nickName,
                                   @Query("birthday")String birthday);

    /**
     * 修改手机号
     * @param account
     * @param token
     * @param phone
     * @return
     */
    @POST(AppHttpPath.USER_PHONE)
    Observable<BaseResult> editPhone(@Query("account")String account,
                                     @Query("token")String token,
                                     @Query("phone")String phone);

    /**
     * 实时天气
     * @param account
     * @param token
     * @param latitude
     * @param longitude
     * @return
     */
    @GET(AppHttpPath.WEATHER)
    Observable<WeatherBean> weather(@Query("account")String account,
                                    @Query("token")String token,
                                    @Query("latitude")String latitude,
                                    @Query("longitude")String longitude);
    /**
     * 根据经纬度查询附近商家（5KM内）
     * @param latitude
     * @param longitude
     * @return
     */
    @GET(AppHttpPath.SHOP_LOCATION)
    Observable<ShopLocationB> shopsLocation(@Query("latitude")String latitude,
                                            @Query("longitude")String longitude);

    /**
     * 摄像头位置
     * @return
     */
    @POST(AppHttpPath.CAMERA_LOCATION)
    Observable<CameraLocBean> cameraLocation();

    /**
     * 摄像头详情
     * @return
     */
    @POST(AppHttpPath.CAMERA_INFO)
    Observable<CameraDetailsBean> cameraInfo(@Query("cameraId")int cameraId);
    /**
     * 店铺详情
     * @param account
     * @param token
     * @param shopId：店铺id
     * @param purchaserId：用户id
     * @return
     */
    @GET(AppHttpPath.SHOP_INFO1)
    Observable<ShopBean> shop(@Query("account")String account,
                              @Query("token")String token,
                              @Query("shopId")int shopId,
                              @Query("purchaserId")int purchaserId);
    /**
     * 商铺评论
     * @param account
     * @param token
     * @param merchantId：	商家id
     * @param typeId：查询类型区分（1:全部）（2:最新）（3:好评）（4:差评）（5:有图）
     * @param currentPage：开始页数（从1开始）。默认为1
     * @param pageSize：每页显示条数。默认为10
     * @return
     */
    @GET(AppHttpPath.SHOP_COMMENTS)
    Observable<ShopCommentsBean> shopComments(@Query("account")String account,
                                              @Query("token")String token,
                                              @Query("merchantId")int merchantId,
                                              @Query("typeId")int typeId,
                                              @Query("currentPage")int currentPage,
                                              @Query("pageSize")int pageSize);


    /**
     * 商家简介及营业执照
     * @param account
     * @param token
     * @param shopId
     * @return
     */
    @GET(AppHttpPath.SHOP_INFO2)
    Observable<ShopInfoBean> shopInfo(@Query("account")String account,
                                      @Query("token")String token,
                                      @Query("shopId")int shopId);

    /**
     * 举报商家类型
     * @param account
     * @param token
     * @return
     */
    @POST(AppHttpPath.REPORT_TYPE)
    Observable<ReportTypeBesan> reportType(@Query("account")String account,
                                           @Query("token")String token);

    /**
     * 商家购物车
     * @param account
     * @param token
     * @param purchaserId
     * @param merchantId
     * @return
     */
    @GET(AppHttpPath.SHOP_CART)
    Observable<ShopCartsBean> shopCarts(@Query("account")String account,
                                        @Query("token")String token,
                                        @Query("purchaserId")int purchaserId,
                                        @Query("merchantId")int merchantId);

    /**
     * 同步购物车
     * @param account
     * @param token
     * @param purchaserId
     * @param shopId
     * @param commodityJson
     * @return
     */
    @POST(AppHttpPath.CART_SYS)
    Observable<BaseResult> cartSys(@Query("account")String account,
                                   @Query("token")String token,
                                   @Query("purchaserId")int purchaserId,
                                   @Query("merchantId")int shopId,
                                   @Query("commodityJson") String commodityJson);

    /**
     * 结算购物车
     * @param account：
     * @param token：
     * @param purchaserId：
     * @param merchantId：
     */
    @POST(AppHttpPath.SETTLEMENT)
    Observable<SettlementBean> settlement(@Query("account")String account,
                                          @Query("token")String token,
                                          @Query("purchaserId")int purchaserId,
                                          @Query("merchantId")int merchantId);

    /**
     * 创建订单
     * @param account:
     * @param token:
     * @param purchaserId:地址id
     * @param merchantId:商家id
     * @param addressId:地址id
     * @param sumPackingCharges:总包装费
     * @param footing:合计价格
     * @param remark：备注
     * @return
     */
    @POST(AppHttpPath.ORDER_CREATE)
    Observable<OrderCreateBean> orderCreate(@Query("account")String account,
                                            @Query("token")String token,
                                            @Query("purchaserId")int purchaserId,
                                            @Query("merchantId")int merchantId,
                                            @Query("addressId")int addressId,
                                            @Query("sumPackingCharges")double sumPackingCharges,
                                            @Query("footing")double footing,
                                            @Query("remark")String remark);

    /**
     * 收货地址列表
     * @param account
     * @param token
     * @param purchaserId
     * @return
     */
    @GET(AppHttpPath.ADDRESS_LIST)
    Observable<AddressListBean> addressList(@Query("account")String account,
                                            @Query("token")String token,
                                            @Query("purchaserId")int purchaserId);

    /**
     * 收货地址详情
     * @param account
     * @param token
     * @param addressId
     * @return
     */
    @GET(AppHttpPath.ADDRESS_INFO)
    Observable<AddressInfoBean> addressInfo(@Query("account")String account,
                                            @Query("token")String token,
                                            @Query("addressId")int addressId);

    /**
     * 删除地址
     * @param account
     * @param token
     * @param addressId
     * @return
     */
    @GET(AppHttpPath.ADDRESS_DEL)
    Observable<BaseResult> addressDel(@Query("account")String account,
                                      @Query("token")String token,
                                      @Query("addressId")int addressId);

    /**
     * 商品详情
     * @param commodityId:商品id
     * @param purchaserId:用户id
     * @return
     */
    @POST(AppHttpPath.GOODS_INFO)
    Observable<GoodsB> GoodsDetalis(@Query("commodityId") int commodityId,@Query("purchaserId") int purchaserId);

    /**
     * 我的收藏
     * @param account
     * @param token
     * @param purchaserId：用户id
     * @param typeId:（0：查询商家）（1：查询商品）
     * @param latitude
     * @param longitude
     * @return
     */
    @GET(AppHttpPath.COLLECT_MY)
    Observable<MyCollcetB> collectsMy(@Query("account")String account,
                                      @Query("token")String token,
                                      @Query("purchaserId")int purchaserId,
                                      @Query("typeId")int typeId,
                                      @Query("latitude")String latitude,
                                      @Query("longitude")String longitude);

    /**
     * 我的分享
     * @param account
     * @param token
     * @param purchaserId
     * @param typeId
     * @param latitude
     * @param longitude
     * @return
     */
    @GET(AppHttpPath.SHARE_MY)
    Observable<MyCollcetB> shareMy(@Query("account")String account,
                                      @Query("token")String token,
                                      @Query("purchaserId")int purchaserId,
                                      @Query("typeId")int typeId,
                                      @Query("latitude")String latitude,
                                      @Query("longitude")String longitude);
    /**
     * 我的评论
     * @param account
     * @param token
     * @param purchaserId
     * @return
     */
    @GET(AppHttpPath.COMMENT_MY)
    Observable<MyCommentB> commentMy(@Query("account")String account,
                                     @Query("token")String token,
                                     @Query("purchaserId")int purchaserId);
    /**
     * 我的订单
     * @param account
     * @param token
     * @param purchaserId
     * @param status：(0：待付款）（1：待发货）（2：待收货）（3：待评价）（4：退款中）（5：完成）（6:订单取消）（7：退款完成）（8：全部订单）
     * @return
     */
    @POST(AppHttpPath.ORDER_MY)
    Observable<OrderListBean> orderMy(@Query("account")String account,
                                      @Query("token")String token,
                                      @Query("purchaserId")int purchaserId,
                                      @Query("status")int status);

    @POST(AppHttpPath.ORDER_DETAILS)
    Observable<OrderInfoBean> orderInfo(@Query("account")String account,
                                        @Query("token")String token,
                                        @Query("orderId")int orderId);
    /**
     * 修改订单状态
     * @param path
     * @param account
     * @param token
     * @param purchaserId
     * @param orderId：订单id
     * @param time:付款时间 - 修改订单状态（待发货)传--
     * @return
     */
    @POST()
    Observable<BaseResult> orderEdit(@Url String path,
                                     @Query("account")String account,
                                     @Query("token")String token,
                                     @Query("purchaserId")int purchaserId,
                                     @Query("orderId")int orderId,
                                     @Query("time")String time);
    @GET(AppHttpPath.LOGISTICS)
    Observable<LogisticsBean> logistics(@Query("account")String account,
                                        @Query("token")String token,
                                        @Query("orderId")int orderId);

    /**
     * 收藏和取消收藏(店铺和商品)
     * @param account
     * @param token
     * @param purchaserId
     * @param typeId：（0：商家）（1：商品）
     * @param merchantId:商家id
     * @param commodityId:商品id
     * @param isCollect：是否为已收藏-（当前的收藏状态）
     * @return
     */
    @POST(AppHttpPath.COLLECTS_OPERATE)
    Observable<IntBean> collectOperateOne(@Query("account")String account,
                                          @Query("token")String token,
                                          @Query("purchaserId")int purchaserId,
                                          @Query("typeId")int typeId,
                                          @Query("merchantId")int merchantId,
                                          @Query("commodityId")int commodityId,
                                          @Query("isCollect")int isCollect,
                                          @Query("ids")int ids);

    /**
     * 批量删除收藏
     * @param account
     * @param token
     * @param typeId：（0：商家）（1：商品）
     * @param isCollect：是否为已收藏-固定1
     * @param ids
     * @return
     */
    @POST(AppHttpPath.COLLECTS_OPERATE)
    Observable<BaseResult> collectsOperate(@Query("account")String account,
                                           @Query("token")String token,
                                           @Query("typeId")int typeId,
                                           @Query("isCollect")int isCollect,
                                           @Query("ids")List<Integer> ids);

    /**
     * 批量删除分享
     * @param account
     * @param token
     * @param ids
     * @return
     */
    @POST(AppHttpPath.DELETE_SHARE)
    Observable<BaseResult> deleteShare(@Query("account")String account,
                                       @Query("token")String token,
                                       @Query("ids")List<Integer> ids);

    /**
     * 搜索-商家
     * @param keyWord
     * @param latitude
     * @param longitude
     * @param typeId
     * @param currentPage
     * @param pageSize
     * @return
     */
    @POST(AppHttpPath.SEARCH)
    Observable<SearchShopBean> searchShop(@Query("keyWord")String keyWord,
                                          @Query("latitude")String latitude,
                                          @Query("longitude")String longitude,
                                          @Query("typeId")int typeId,
                                          @Query("currentPage")int currentPage,
                                          @Query("pageSize")int pageSize);
    /**
     * 搜索商品
     * */
    @POST(AppHttpPath.SEARCH)
    Observable<SearchGoodsBean> searchGoods(@Query("keyWord")String keyWord,
                                            @Query("latitude")String latitude,
                                            @Query("longitude")String longitude,
                                            @Query("typeId")int typeId,
                                            @Query("currentPage")int currentPage,
                                            @Query("pageSize")int pageSize);

    /**
     * 查询退货退款原因
     * @param typeId:(1:退款)(2:退货换货)
     * @return
     */
    @POST(AppHttpPath.RETURNCAUSE)
    Observable<ReturnReasonBean> returnCause(@Query("typeId")int typeId);

    /**
     * 获取省市县街道
     * @return
     */
    @GET(AppHttpPath.GET_PROVINCE)
    Observable<PlaceBean> getProvince();
    /**
     * @param cityName:省代码
     * @return
     */
    @GET(AppHttpPath.GET_CITY)
    Observable<PlaceBean> getCity(@Query("cityName")long cityName);
    @GET(AppHttpPath.GET_AREA)
    Observable<PlaceBean> getArea(@Query("cityName")long cityName);
    @GET(AppHttpPath.GET_STREET)
    Observable<PlaceBean> getStreet(@Query("cityName")long cityName);

    /**
     * RequestBody参数上传
     * @param jsonBody
     * @return
     */
    @POST()
    Observable<BaseResult> submitBody(@Url String path,@Body RequestBody jsonBody);

    /**
     * 申请售后-提交
     * @param path
     * @param commodityId
     * @param jsonBody
     * @return
     */
    @POST()
    Observable<BaseResult> returnGoods(@Url String path,@Query("commodityId")List<Integer> commodityId,@Body RequestBody jsonBody);

    /**
     * 退款退货详情
     * @param account
     * @param token
     * @param orderId
     * @return
     */
    @POST(AppHttpPath.RETURN_INFO)
    Observable<ReturnDetailsBean> returnInfo(@Query("account")String account,
                                             @Query("token")String token,
                                             @Query("orderId")int orderId);

    /**
     * 支付宝订单
     * @param account
     * @param token
     * @param orderId
     * @return
     */
    @POST(AppHttpPath.ORDERINFO_ZFB)
    Observable<StringBean> orderInfoZfb(@Query("account")String account,
                                        @Query("token")String token,
                                        @Query("orderId")int orderId);

    /**
     * 微信订单
     * @param account
     * @param token
     * @param orderId
     * @return
     */
    @POST(AppHttpPath.ORDERINFO_WX)
    Observable<OrderPayWxBean> orderInfoWx(@Query("account")String account,
                                           @Query("token")String token,
                                           @Query("orderId")int orderId);



    /*====================================================    流媒体   ==============================================================*/




    /**
     * 获取所有的流摄像头
     *
     * @return
     */
    @POST(AppHttpPath.STREAM_CAMERAS)
    Observable<StreamCameraBean> getAllStreamCameras(@Body RequestBody requestBody);


    /**
     * 获取硬盘录像机下的所有的流摄像头
     *
     * @return
     */
    @POST(AppHttpPath.STREAM_CAMERAS_FROM_VCR)
    Observable<StreamCameraBean> getAllStreamCamerasFromVCR(@Body RequestBody requestBody);

    /**
     * 摄像头详情
     *
     * @return
     */
    @POST(AppHttpPath.STREAM_CAMERAS_DETAIL)
    Observable<StreamCameraDetailBean> getStreamCameraDetail(@Body RequestBody requestBody);

    /**
     * 上传封面图
     *
     * @return
     */
    @POST(AppHttpPath.UPLOAD_STREAM_CAMERAS_THUMB)
    Observable<BaseResult> uploadStreamCameraThumbPic(@Body RequestBody requestBody);

    /**
     * 打开视频流
     *
     * 字段说明：
     * 	"channelid":  (字符串)   通道20位编号
     * 	"type":       (数字)   	 国标请求视频类型：1：udp 2：tcp主动 3：tcp被动
     * 	"videourltype":  (字符串)   视频类型：rtsp：返回rtsp地址  rtmp：返回rtmp地址 hls：返回hls地址
     * @param channelid
     * @param type
     * @param videourltype
     * @return
     */
    @GET(AppHttpPath.BASE_CAMERA_URL+"/vss/open_stream/{channelid}/{type}/{videourltype}")
    Observable<OpenLiveBean> openStream(@Path("channelid") String channelid, @Path("type")String type,
                                        @Path("videourltype")String videourltype);

    /**
     * 会话id   保活的接口
     * @param sessionid
     * @return
     */
    @GET(AppHttpPath.BASE_CAMERA_URL+"/vss/video_keepalive/{sessionid}")
    Observable<OpenLiveBean> keepAlive(@Path("sessionid") String sessionid);
    /**
     * 截图
     * @param channelid
     * @return
     */
    @GET(AppHttpPath.BASE_CAMERA_URL+"/vss/get_image/{channelid}/{type}")
    Observable<OpenLiveBean> capturePic(@Path("channelid") String channelid,@Path("type") String type);



}
