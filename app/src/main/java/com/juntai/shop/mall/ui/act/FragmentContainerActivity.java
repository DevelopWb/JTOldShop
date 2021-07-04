package com.juntai.shop.mall.ui.act;


import android.view.View;

import com.juntai.mall.base.base.BaseActivity;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.mine.MineFragment;

/**
 * 加载fragment
 * Created by Ma
 * on 2019/4/15
 */
public class FragmentContainerActivity extends BaseActivity {
    //我的
    public static final int MY = 0x1;
    private String title = "";
    private int type;
    @Override
    public int getLayoutView() {
        return R.layout.activity_fragment;
    }

    @Override
    public void initView() {
        title = getIntent().getStringExtra("title");
        type = getIntent().getIntExtra("type",0);
        setTitleName(title);
        switch (type){
            case 0:
                finish();
                break;
            case MY://我的
                title("个人中心");
                getToolbar().setVisibility(View.GONE);
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_content,new MineFragment())
                        .commit();
                break;
        }
    }

    /**
     * 默认title
     * @param tit
     */
    public void title(String tit){
        if (title == null || title.equals("")){
            setTitleName(tit);
        }
    }
    @Override
    public void initData() {

    }
}
