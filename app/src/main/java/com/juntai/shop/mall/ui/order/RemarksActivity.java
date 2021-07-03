package com.juntai.shop.mall.ui.order;

import android.content.Intent;
import android.widget.EditText;

import com.juntai.mall.base.base.BaseActivity;
import com.juntai.shop.mall.R;

/**
 * 添加备注
 * Created by Ma
 * on 2019/9/8
 */
public class RemarksActivity extends BaseActivity {
    EditText editText;
    @Override
    public int getLayoutView() {
        return R.layout.activity_remarks;
    }

    @Override
    public void initView() {
        setTitleName("添加备注");
        editText = findViewById(R.id.remark_edit);
        editText.setText(getIntent().getStringExtra("remark"));
        findViewById(R.id.remark_btn).setOnClickListener(v -> {
            setResult(RESULT_OK,new Intent().putExtra("remark",editText.getText().toString()));
            finish();
        });
    }

    @Override
    public void initData() {

    }
}
