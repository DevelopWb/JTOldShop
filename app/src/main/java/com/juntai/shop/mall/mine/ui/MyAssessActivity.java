package com.juntai.shop.mall.mine.ui;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.juntai.mall.base.base.BaseActivity;
import com.juntai.mall.base.base.BaseObserver;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.shop.mall.MyApp;
import com.juntai.shop.mall.AppNetModule;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.MyCommentB;
import com.juntai.shop.mall.mine.adt.MyAssessAdapter;
import com.juntai.shop.mall.utils.DpTools;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 我的评价
 * Created by Ma
 * on 2019/11/27
 */
public class MyAssessActivity extends BaseActivity {
    private SmartRefreshLayout smartRefreshLayout ;
    private RecyclerView recyclerView;
    MyAssessAdapter adapter;
    int page = 1, pagesize = 10;
    @Override
    public int getLayoutView() {
        return R.layout.recycleview_layout;
    }

    @Override
    public void initView() {
        setTitleName("我的评价");
        smartRefreshLayout = findViewById(R.id.smartRefreshLayout);
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
           page = 1;
           getData();
        });
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            getData();
        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new MyAssessAdapter(R.layout.item_myassess,new ArrayList());
        //添加默认分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        //刷新
        smartRefreshLayout.setEnableRefresh(false);
        //加载更多
        smartRefreshLayout.setEnableLoadMore(false);
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            if (view.getId() == R.id.item_myassess_more){
                initPopMore(view);
            }else if(view.getId() == R.id.item_myassess_shop_layout){
                //商家
                MyApp.app.activityTool.toShopActivity(mContext,adapter.getData().get(position).getShopId());
            }
        });
    }

    @Override
    public void initData() {
        getData();
    }
    private void getData(){
        AppNetModule.createrRetrofit()
                .commentMy(MyApp.app.getAccount(), MyApp.app.getUserToken(), MyApp.app.getUid()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<MyCommentB>() {
                    @Override
                    public void onSuccess(MyCommentB result) {
                        if (page == 1){
                            adapter.getData().clear();
                        }
                        adapter.addData(result.getReturnValue().getDatas());
                        if (result.getReturnValue().getDatas().size() < pagesize){
                            smartRefreshLayout.setNoMoreData(true);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        ToastUtils.toast(mContext,msg);
                    }
                });
    }

    PopupWindow popupWindow;
    int[] loca;
    public void initPopMore(View viewP){
        View view= LayoutInflater.from(mContext).inflate(R.layout.pop_assess_more, null);
        //背景颜色
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        view.findViewById(R.id.pop_assess_more_delte).setOnClickListener(v -> {
            ToastUtils.toast(mContext,"删除");
        });
        view.findViewById(R.id.pop_assess_more_cancle).setOnClickListener(v -> {
            popupWindow.dismiss();
        });
        loca = new int[2];
        viewP.getLocationOnScreen(loca);
        //显示（自定义位置）
        popupWindow.showAtLocation(viewP, Gravity.NO_GRAVITY,loca[0] - DpTools.dip2px(mContext,90),loca[1] - DpTools.dip2px(mContext,50));
    }
}
