package com.juntai.shop.mall.ui.after_sales;

import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.mall.base.widght.BottomDialogFragment;
import com.juntai.shop.mall.R;

/**
 * 货物状态
 * Created by Ma
 * on 2019/9/7
 */
public class GoodsStatusDialog extends BottomDialogFragment implements View.OnClickListener {
    int status = 1;
    String[] str = new String[]{"","未收到货","已收到货"};
    OnSelectListener onSelectListener;
    RadioButton radioButton1,radioButton2;
    @Override
    public int setView() {
        return R.layout.dialog_goods_status;
    }

    @Override
    public void initView(View view) {
        radioButton1 = view.findViewById(R.id.dialog_goods_status_radio1);
        radioButton2 = view.findViewById(R.id.dialog_goods_status_radio2);
        radioButton1.setChecked(status == 1);
        radioButton2.setChecked(status == 2);

        view.findViewById(R.id.dialog_goods_status_layout1).setOnClickListener(this);
        view.findViewById(R.id.dialog_goods_status_layout2).setOnClickListener(this);
        view.findViewById(R.id.dialog_goods_status_qd).setOnClickListener(this);
        view.findViewById(R.id.dialog_goods_status_close).setOnClickListener(this);
    }

    @Override
    public int dialogHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }
    //家，公司，学校
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_goods_status_layout1:
                status = 1;
                radioButton1.setChecked(true);
                radioButton2.setChecked(false);
                break;
            case R.id.dialog_goods_status_layout2:
                status = 2;
                radioButton1.setChecked(false);
                radioButton2.setChecked(true);
                break;
            case R.id.dialog_goods_status_qd:
                if (onSelectListener != null){
                    onSelectListener.select(status,str[status]);
                    dismiss();
                }
                break;
            case R.id.dialog_goods_status_close:
                dismiss();
                break;
        }
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    public interface OnSelectListener{
        /**
         * 货物状态（1：未收到货）（2：已收到货）
         * @param status
         */
        void select(int status,String str);
    }
}
