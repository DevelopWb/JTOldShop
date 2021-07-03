package com.juntai.mall.base.widght;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.juntai.mall.base.R;
import com.juntai.mall.base.app.BaseApplication;

/**
 * 商品规格选择
 */
public abstract class BottomDialogFragment extends DialogFragment {
    int height = BaseApplication.H / 4 * 3;
    View view;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(setView(), null);
        Dialog dialog = new Dialog(getActivity(), R.style.CusDialog);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.setTitle("标题");
        dialog.setCanceledOnTouchOutside(true);
        //Do something
        // 设置宽度为屏宽、位置靠近屏幕底部
        Window window = dialog.getWindow();
        //window.setWindowAnimations(R.style.dialogWindowAnim);
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        height = dialogHeight() == 0 ? height : dialogHeight();
//        if (wlp.height > height){
        if (true){
            wlp.height = height;
        }
        window.setAttributes(wlp);
        initView(view);
        return dialog;
    }
    public abstract int setView();
    public abstract void initView(View view);
    public abstract int dialogHeight();
}
