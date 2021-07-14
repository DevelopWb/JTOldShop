package com.juntai.shop.mall.mine.set.address;

import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.mall.base.widght.BottomDialogFragment;
import com.juntai.shop.mall.R;

/**
 * 地址--标签选择
 * Created by Ma
 * on 2019/9/7
 */
public class AddressTagDialog extends BottomDialogFragment implements View.OnClickListener {
    String strTag;
    ImageView imageView1,imageView2,imageView3,imageView4;
    EditText editText;
    OnSelectListener onSelectListener;
    boolean isSelected = false,isEdit = false;
    @Override
    public int setView() {
        return R.layout.dialog_address_tag;
    }

    @Override
    public void initView(View view) {
        editText = view.findViewById(R.id.dialog_address_tag_edit);
        imageView1 = view.findViewById(R.id.item_address_add_tag_image1);
        imageView2 = view.findViewById(R.id.item_address_add_tag_image2);
        imageView3 = view.findViewById(R.id.item_address_add_tag_image3);
        imageView4 = view.findViewById(R.id.item_address_add_tag_image4);


        view.findViewById(R.id.item_address_add_tag_layout1).setOnClickListener(this);
        view.findViewById(R.id.item_address_add_tag_layout2).setOnClickListener(this);
        view.findViewById(R.id.item_address_add_tag_layout3).setOnClickListener(this);
        view.findViewById(R.id.item_address_add_tag_submit).setOnClickListener(this);
        view.findViewById(R.id.dialog_address_tag_close).setOnClickListener(this);
        editText.setOnClickListener(this);
        imageView1.setVisibility(View.VISIBLE);
        strTag = "家";
    }

    @Override
    public int dialogHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }
    //家，公司，学校
    @Override
    public void onClick(View v) {
        imageView1.setVisibility(View.GONE);
        imageView2.setVisibility(View.GONE);
        imageView3.setVisibility(View.GONE);
        imageView4.setVisibility(View.GONE);
        switch (v.getId()){
            case R.id.item_address_add_tag_layout1:
                strTag = "家";
                imageView1.setVisibility(View.VISIBLE);
                break;
            case R.id.item_address_add_tag_layout2:
                strTag = "公司";
                imageView2.setVisibility(View.VISIBLE);
                break;
            case R.id.item_address_add_tag_layout3:
                strTag = "学校";
                imageView3.setVisibility(View.VISIBLE);
                break;
            case R.id.dialog_address_tag_edit:
                strTag = "自定义";
                imageView4.setVisibility(View.VISIBLE);
                break;
            case R.id.item_address_add_tag_submit:
                if ("自定义".equals(strTag)){//自定义
                    strTag = editText.getText().toString();
                    if (strTag.isEmpty()){
                        ToastUtils.toast(getActivity(),"自定义标签不可为空");
                        return;
                    }
                }
                if (onSelectListener != null){
                    onSelectListener.select(strTag);
                    dismiss();
                }
                break;
            case R.id.dialog_address_tag_close:
                dismiss();
                break;
        }
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    public interface OnSelectListener{
        void select(String tag);
    }
}
