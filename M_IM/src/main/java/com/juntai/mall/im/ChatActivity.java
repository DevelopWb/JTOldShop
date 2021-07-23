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
 *
 * @aouther Ma
 * @date 2019/4/5
 */
public class ChatActivity extends BaseActivity {


    @Override
    public int getLayoutView() {
        return R.layout.activity_chat;
    }

    public void initView() {
        //竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        String title = getIntent().getData().getQueryParameter("title");
        setTitleName(title);
    }

    @Override
    public void initData() {

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
