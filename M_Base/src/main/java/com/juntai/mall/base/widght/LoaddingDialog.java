package com.juntai.mall.base.widght;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.juntai.mall.base.R;
import com.juntai.mall.base.app.BaseApplication;

/**
 * 盖住整个界面的
 * @aouther Ma
 * @date 2019/3/7
 */
public class LoaddingDialog extends Dialog {
    public LoaddingDialog(@NonNull Context context) {
        super(context);
    }

    public LoaddingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public LoaddingDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.getDecorView().setBackgroundColor(Color.WHITE);
        window.setDimAmount(0f);
        WindowManager.LayoutParams lp = window.getAttributes();

        lp.width = BaseApplication.W;
        lp.height = BaseApplication.H - BaseApplication.statusBarH;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);
        window.setContentView(R.layout.loaddingdialog);
    }
}
