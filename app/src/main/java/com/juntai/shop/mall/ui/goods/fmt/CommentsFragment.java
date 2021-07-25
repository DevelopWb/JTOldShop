package com.juntai.shop.mall.ui.goods.fmt;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.juntai.mall.base.base.BaseLazyFragment;
import com.juntai.mall.base.base.BaseObserver;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.shop.mall.MyApp;
import com.juntai.shop.mall.AppNetModule;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.ShopCommentsBean;
import com.juntai.shop.mall.ui.goods.shop.ShopActivity;
import com.juntai.shop.mall.ui.goods.adt.CommentsAdapter;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 店铺评论
 * Created by Ma
 * on 2019/9/2
 */
public class CommentsFragment extends BaseLazyFragment {
    TagFlowLayout flowLayout;
    RecyclerView recyclerView;
    ImageView imageView0 = null;
    CommentsAdapter commentsAdapter;
    private Integer[] flowRes = new Integer[]{
            R.drawable.check_tag_comment1,
            R.drawable.check_tag_comment2,
            R.drawable.check_tag_comment3,
            R.drawable.check_tag_comment4,
            R.drawable.check_tag_comment5};
    //查询类型区分（1:全部）（2:最新）（3:好评）（4:差评）（5:有图）
    int typeId = 1, page = 1, pagesize = 10;
    TextView textView;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_comments;
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void initView() {
        recyclerView = getView(R.id.shop_comments_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        commentsAdapter = new CommentsAdapter(R.layout.item_shop_comments, new ArrayList());
        recyclerView.setAdapter(commentsAdapter);
        textView = new TextView(mContext);
        textView.setPadding(10, 10, 10, 10);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(R.color.colorTheme));
        textView.setText("查看更多");
        commentsAdapter.setFooterView(textView);
        //
        flowLayout = getView(R.id.shop_flowlayout);
        flowLayout.setAdapter(new TagAdapter<Integer>(Arrays.asList(flowRes)) {
            @Override
            public View getView(FlowLayout parent, int position, Integer o) {
                ImageView tv = new ImageView(mContext);
                tv.setImageResource(o);
                if (position == 0) {
                    imageView0 = tv;
                    parent.setSelected(true);
                    imageView0.setImageResource(R.mipmap.ic_comment_tag_1);
                }
                return tv;
            }
        });
        flowLayout.setOnTagClickListener((view, position, parent) -> {
            imageView0.setImageResource(R.drawable.check_tag_comment1);
            typeId = position + 1;
            page = 1;
            getData();
            return false;
        });
        textView.setOnClickListener(v -> getData());
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void lazyLoad() {
        getData();
    }

    public void getData() {
        AppNetModule.createrRetrofit()
                .shopComments(MyApp.app.getAccount(), MyApp.app.getUserToken(), ShopActivity.shopId, typeId, page, pagesize).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ShopCommentsBean>() {
                    @Override
                    public void onSuccess(ShopCommentsBean result) {
                        if (page == 1) {
                            commentsAdapter.getData().clear();
                        }
                        commentsAdapter.addData(result.getReturnValue().getDatas());
                        if (result.getReturnValue().getDatas().size() < pagesize) {
                            textView.setVisibility(View.GONE);
                        } else {
                            textView.setVisibility(View.VISIBLE);
                        }
                        page++;
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtils.toast(mContext, msg);
                    }
                });
    }
}
