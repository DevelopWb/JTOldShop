package com.juntai.mall.im;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gyf.barlibrary.ImmersionBar;
import com.juntai.mall.base.app.BaseApplication;
import com.juntai.mall.base.base.BaseActivity;

import org.w3c.dom.Text;

import java.util.Locale;

import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.model.Conversation;

/**
 * 会话activity
 * @aouther Ma
 * @date 2019/4/5
 */
public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_chat);
//        ImmersionBar.with(this).statusBarDarkFont(true).init();
        initView();
    }

    public void initView() {
        String mTargetId = getIntent().getData().getQueryParameter("targetId");
        String mTargetIds = getIntent().getData().getQueryParameter("targetIds");
        String title  = getIntent().getData().getQueryParameter("title");

        findViewById(R.id.chat_title_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView textView = findViewById(R.id.chat_title_name);
        textView.setText(title);
//        Conversation.ConversationType conversationType = Conversation.ConversationType.valueOf(getIntent().getData()
//                .getLastPathSegment().toUpperCase(Locale.US));
//
//
//        FragmentManager fragmentManage = getSupportFragmentManager();
//        ConversationFragment fragement = (ConversationFragment) fragmentManage.findFragmentById(R.id.conversation);
//        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
//                .appendPath("conversation").appendPath(conversationType.getName().toLowerCase())
//                .appendQueryParameter("targetId", mTargetId).build();
//
//        fragement.setUri(uri);
    }

    public void setStatusListener(){
//        RongIMClient.setTypingStatusListener(new RongIMClient.TypingStatusListener() {
//            @Override
//            public void onTypingStatusChanged(Conversation.ConversationType type, String targetId, Collection<TypingStatus> typingStatusSet) {
//                //当输入状态的会话类型和targetID与当前会话一致时，才需要显示
//                if (type.equals(mConversationType) && targetId.equals(mTargetId)) {
//                    //count表示当前会话中正在输入的用户数量，目前只支持单聊，所以判断大于0就可以给予显示了
//                    int count = typingStatusSet.size();
//                    if (count > 0) {
//                        Iterator iterator = typingStatusSet.iterator();
//                        TypingStatus status = (TypingStatus) iterator.next();
//                        String objectName = status.getTypingContentType();
//
//                        MessageTag textTag = TextMessage.class.getAnnotation(MessageTag.class);
//                        MessageTag voiceTag = VoiceMessage.class.getAnnotation(MessageTag.class);
//                        //匹配对方正在输入的是文本消息还是语音消息
//                        if (objectName.equals(textTag.value())) {
//                            //显示“对方正在输入”
//                            mHandler.sendEmptyMessage(SET_TEXT_TYPING_TITLE);
//                        } else if (objectName.equals(voiceTag.value())) {
//                            //显示"对方正在讲话"
//                            mHandler.sendEmptyMessage(SET_VOICE_TYPING_TITLE);
//                        }
//                    } else {
//                        //当前会话没有用户正在输入，标题栏仍显示原来标题
//                        mHandler.sendEmptyMessage(SET_TARGETID_TITLE);
//                    }
//                }
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
