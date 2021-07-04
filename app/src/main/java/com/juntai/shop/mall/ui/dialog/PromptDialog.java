package com.juntai.shop.mall.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.juntai.shop.mall.MyApp;
import com.juntai.shop.mall.R;

/**
 * 提示
 * Created by Ma
 * on 2019/10/11
 */
public class PromptDialog extends DialogFragment implements View.OnClickListener {
    TextView tvMsg,tvNo,tvOk;
    String msg = "",no = "",ok = "";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_prompt,container,false);
        tvMsg = view.findViewById(R.id.prompt_dialog_message);
        tvNo = view.findViewById(R.id.prompt_dialog_no);
        tvOk = view.findViewById(R.id.prompt_dialog_ok);
        tvNo.setOnClickListener(this);
        tvOk.setOnClickListener(this);
        tvMsg.setText(msg);
        tvNo.setText(no);
        tvOk.setText(ok);
        return view;
    }

    public void setMessage(String message, String n, String o){
        msg = message;
        no = n;
        ok = o;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 设置宽度为屏宽、位置靠近屏幕底部
        Window window = getDialog().getWindow();
        //window.setWindowAnimations(R.style.dialogWindowAnim);
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = MyApp.W / 3 * 2;
        window.setAttributes(wlp);
//        getDialog().setCanceledOnTouchOutside(false);
//        getDialog().setCancelable(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.prompt_dialog_no:
                isCancle = true;
                break;
            case R.id.prompt_dialog_ok :
                if (okClickListener != null){
                    okClickListener.onClick();
                }
                break;
        }
        dismiss();
    }
    boolean isCancle = false;
    @Override
    public void dismiss() {
        super.dismiss();
        if (okClickListener != null && isCancle){
            okClickListener.cancle();
        }
        isCancle = false;
    }

    OnOkClickListener okClickListener;

    public void setOkClickListener(OnOkClickListener okClickListener) {
        this.okClickListener = okClickListener;
    }

    public interface OnOkClickListener{
        void onClick();
        void cancle();
    }
}
