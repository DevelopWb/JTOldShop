package com.juntai.mall.im;

import android.content.Context;
import android.net.Uri;

import com.juntai.mall.base.utils.LogUtil;
import com.juntai.mall.im.ry.ContactNotificationMessageProvider;
import com.juntai.mall.im.ry.MyExtensionModule;
import com.juntai.mall.im.ry.listener.MyConnectionStatusListener;
import com.juntai.mall.im.ry.listener.MyConversationClickListener;
import com.juntai.mall.im.ry.listener.MyConversationListBehaviorListener;
import com.juntai.mall.im.ry.listener.MyOnReceiveMessageListener;
import com.juntai.mall.im.ry.listener.MySendMessageListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imkit.widget.provider.RealTimeLocationMessageProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.ipc.RongExceptionHandler;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import io.rong.push.RongPushClient;
import io.rong.push.pushconfig.PushConfig;

/**
 * @aouther Ma
 * @date 2019/4/2
 */
public class ModuleIm_Init {
    private static Context mContext;
    public static void init(Context app){
        mContext = app;

//        Stetho.initializeWithDefaults(mContext);
        //融云推送设置
        PushConfig config = new PushConfig
                .Builder()
//                .enableHWPush(true)
//                .enableMiPush("2882303761517473625", "5451747338625")
//                .enableMeiZuPush("112988", "2fa951a802ac4bd5843d694517307896")
//                .enableVivoPush(true)
//                .enableFCM(true)
                .build();
        RongPushClient.setPushConfig(config);


        Thread.setDefaultUncaughtExceptionHandler(new RongExceptionHandler(mContext));

        RongIM.setServerInfo("nav.cn.ronghub.com", "up.qbox.me");

        RongIM.init(mContext);
        //自定义消息
        RongIM.registerMessageType(CustomMessage.class);
//        RongIM.registerMessageType(PushMessage.class);
        try {
            RongIM.registerMessageTemplate(new ContactNotificationMessageProvider());
            RongIM.registerMessageTemplate(new RealTimeLocationMessageProvider());
//            RongIM.registerMessageType(TestMessage.class);
//            RongIM.registerMessageTemplate(new TestMessageProvider());

        } catch (Exception e) {
            e.printStackTrace();
        }

        /**设置地理位置提供者,不用位置可以注掉此行代码*/
        RongIM.setLocationProvider(new RongIM.LocationProvider() {
            @Override
            public void onStartLocation(Context context, LocationCallback locationCallback) {
                LogUtil.d("RongYun融云位置 onStartLocation = " + locationCallback.toString());
                /**
                 * demo 代码  开发者需替换成自己的代码。
                 */
            }
        });


        /**融云发送消息监听*/
        RongIM.getInstance().setSendMessageListener(new MySendMessageListener());
        /**融云接收消息监听*/
        RongIM.setOnReceiveMessageListener(new MyOnReceiveMessageListener());
        /**连接状态监听*/
        RongIM.setConnectionStatusListener(new MyConnectionStatusListener());
        /**设置会话界面操作的监听*/
        RongIM.setConversationClickListener(new MyConversationClickListener());
        /**会话列表操作监听*/
        RongIM.setConversationListBehaviorListener(new MyConversationListBehaviorListener());

        setInputProvider();

        //{"code":200,"userId":"111","token":"GSulmzhDDKIMRBSEqDEhezg+7g/oEuu3wtBbT3ulANlLwepHVAhdaUSOVxHbzFKxsDTLgXYTYDk6TZGoQDWEiA=="}
        //{"code":200,"userId":"222","token":"xkxsKFyUvvfReTaOMyEtkkF0L2Exc/8fAyQA+8FNrGY2GePk3qb50FpgsGgjKLDZUvIiPG4wxCJap9A5MpcY3w=="}
        //{"code":200,"userId":"333","token":"7Idg87l3DRI9Bov8lspyDkF0L2Exc/8fAyQA+8FNrGY2GePk3qb50F3fhWoB1HQC5MNZdu5bxUsnfEwShcEVlA=="}
        //http://pic15.nipic.com/20110628/1369025_192645024000_2.jpg
        //http://img1.ph.126.net/SSvFbcJzwGBlqy4xon6FjA==/6608832342050415367.jpg
        //http://pic32.nipic.com/20130823/13339320_183302468194_2.jpg
//        connect("b7GeqowyiokTwFXvUVIgq0F0L2Exc/8fAyQA+8FNrGY2GePk3qb50MTsVI3EQ5YeMNiHLFQzdyg9EBVCmj3ccg==");


//        connectIM("GSulmzhDDKIMRBSEqDEhezg+7g/oEuu3wtBbT3ulANlLwepHVAhdaUSOVxHbzFKxsDTLgXYTYDk6TZGoQDWEiA==");
//        connectIM("xkxsKFyUvvfReTaOMyEtkkF0L2Exc/8fAyQA+8FNrGY2GePk3qb50FpgsGgjKLDZUvIiPG4wxCJap9A5MpcY3w==");
//        connectIM("7Idg87l3DRI9Bov8lspyDkF0L2Exc/8fAyQA+8FNrGY2GePk3qb50F3fhWoB1HQC5MNZdu5bxUsnfEwShcEVlA==");
    }
    private static void setInputProvider() {

        List<IExtensionModule> moduleList = RongExtensionManager.getInstance().getExtensionModules();
        IExtensionModule defaultModule = null;
        if (moduleList != null) {
            for (IExtensionModule module : moduleList) {
                if (module instanceof DefaultExtensionModule) {
                    defaultModule = module;
                    break;
                }
            }
            if (defaultModule != null) {
                RongExtensionManager.getInstance().unregisterExtensionModule(defaultModule);
                RongExtensionManager.getInstance().registerExtensionModule(new MyExtensionModule());
            }
        }

        /**添加小视频*/
        //RongExtensionManager.getInstance().registerExtensionModule(new SightExtensionModule());
    }
    private static HashMap<String,UserInfo> infoHashMap = new HashMap<>();

    /**
     * 设置用户信息
     * @param arrayList
     */
    public static void setUsers(ArrayList<UserIM> arrayList){
        infoHashMap.clear();
        for (UserIM user:arrayList) {
            UserInfo userInfo = new UserInfo(user.id,user.name, Uri.parse(user.image));
//            infoHashMap.put(user.id,userInfo);
            RongIM.getInstance().refreshUserInfoCache(userInfo);//刷新用户信息(本地缓存)
        }
        arrayList.clear();
//        RongIM.UserInfoProvider userInfoProvider = new RongIM.UserInfoProvider() {
//            @Override
//            public UserInfo getUserInfo(String s) {
//                UserInfo userInfo = infoHashMap.get(s);
//                if (userInfo != null) {
//                    return userInfo;
//                }
//                return null;
//            }
//        };
//        RongIM.setUserInfoProvider(userInfoProvider,true);
    }
    public static void setUser(UserIM userIM){
        if (userIM.id == null || userIM.name == null || userIM.image == null)return;
        UserInfo userInfo = new UserInfo(userIM.id,userIM.name, Uri.parse(userIM.image));
//            infoHashMap.put(user.id,userInfo);
        RongIM.getInstance().refreshUserInfoCache(userInfo);//刷新用户信息(本地缓存)

    }
    private static IUnReadMessageLinstener unReadMessageLinstener;
    public static void setUnReadMessageListener(IUnReadMessageLinstener listener){
        unReadMessageLinstener = listener;
        /**
         * 设置未读消息数变化监听器。必须在 init 之后即可调用。
         * 注意:如果是在 activity 中设置,那么要在 activity 销毁时,调用 {@link #removeUnReadMessageCountChangedObserver(IUnReadMessageObserver)}
         * 否则会造成内存泄漏。
         *
         * @param observer          接收未读消息消息的监听器。
         * @param conversationTypes 接收未读消息的会话类型。
         */
        final Conversation.ConversationType[] conversationTypes = {
                Conversation.ConversationType.PRIVATE,
                Conversation.ConversationType.GROUP, Conversation.ConversationType.SYSTEM,
                Conversation.ConversationType.PUBLIC_SERVICE, Conversation.ConversationType.APP_PUBLIC_SERVICE
        };
        RongIM.getInstance().addUnReadMessageCountChangedObserver(new IUnReadMessageObserver() {
            @Override
            public void onCountChanged(int i) {
                if (unReadMessageLinstener != null){
                    unReadMessageLinstener.onCountChanged(i);
                }
                LogUtil.d("RongYun：未读消息数量 = " + i);
            }
        },conversationTypes);
    }
    /**
     * <p>连接服务器，在整个应用程序全局，只需要调用一次，需在 {@link #init(Context)} 之后调用。</p>
     * <p>如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
     * 在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。</p>
     *
     * //@param token    从服务端获取的用户身份令牌（Token）。
     * //@param callback 连接回调。
     * //@return RongIM  客户端核心类的实例。
     */
    public static void connectIM(String token) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            /**
             * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
             *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
             */
            @Override
            public void onTokenIncorrect() {
                LogUtil.d("RongYun：连接  onTokenIncorrect ");
            }

            /**
             * 连接融云成功
             * @param userid 当前 token 对应的用户 id
             */
            @Override
            public void onSuccess(String userid) {
                LogUtil.d("RongYun：连接成功  userid = " + userid);
            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                LogUtil.e("RongYun：errorCode = " + errorCode);
            }
        });
    }

    /**
     * 退出登录且不再接受新消息通知
     */
    public static void logout(){
        RongIM.getInstance().logout();
    }
    public static void removeAll_Clear(){
//        RongIM.removeUnReadMessageCountChangedObserver();
    }

    /**
     *
     * @param context
     * @param targetId:要与之聊天的用户 Id-account
     * @param title:聊天的标题
     */
    public static void chat(Context context, String targetId,String title){
        RongIM.getInstance().startPrivateChat(context, targetId, title);
    }
}
