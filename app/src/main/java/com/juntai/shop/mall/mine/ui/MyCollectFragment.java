package com.juntai.shop.mall.mine.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.juntai.mall.base.base.BaseLazyFragment;
import com.juntai.mall.base.base.BaseObserver;
import com.juntai.mall.base.base.BaseResult;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.shop.mall.MyApp;
import com.juntai.shop.mall.AppNetModule;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.MyCollcetB;
import com.juntai.shop.mall.mine.adt.MyCollectAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ma
 * on 2019/11/26
 */
public class MyCollectFragment extends BaseLazyFragment implements View.OnClickListener {
    private SmartRefreshLayout smartRefreshLayout ;
    private RecyclerView recyclerView;
    MyCollectAdapter adapter;
    LinearLayout linearLayout;
    ImageView btnAll,btnDel;
    int type;//（0：查询商家）（1：查询商品）
    List<Integer> ids = new ArrayList<>();
    int function = 1;//功能，1=我的收藏  2=我的分享
    int page = 1, pagesize = 10;
    public static MyCollectFragment newInstance(int type,int f) {
        Bundle args = new Bundle();
        args.putInt("type",type);
        args.putInt("function",f);
        MyCollectFragment fragment = new MyCollectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt("type");
        function = getArguments().getInt("function");
    }

    @Override
    protected void lazyLoad() {
        getData();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_collect;
    }

    @Override
    protected void initView() {
        smartRefreshLayout = getView(R.id.smartRefreshLayout);

        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            getData();
        });
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            getData();
        });
        recyclerView = getView(R.id.recyclerView);
        linearLayout = getView(R.id.mycollect_bottom_layout);
        btnAll = getView(R.id.mycollect_bottom_all);
        btnDel = getView(R.id.mycollect_bottom_delete);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new MyCollectAdapter(R.layout.item_mycollect,new ArrayList(),type);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            //（0：查询商家）（1：查询商品）
            if (type == 0){
                MyApp.app.activityTool.toShopActivity(mContext,adapter.getData().get(position).getShopId());
            }else {
                MyApp.app.activityTool.toGoodsActivity(mContext,adapter.getData().get(position).getShopId(),adapter.getData().get(position).getCommodityId());
            }
        });
        //
        btnAll.setOnClickListener(this);
        btnDel.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    public void setEdit(boolean isEdit){
        adapter.setEdit(isEdit);
        linearLayout.setVisibility(isEdit?View.VISIBLE:View.GONE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mycollect_bottom_all:
                adapter.setAll();
                break;
            case R.id.mycollect_bottom_delete:
                ids.clear();
                for (MyCollcetB.ReturnValueBean.DatasBean bean: adapter.getData()) {
                    if (bean.isChecked()){
                        ids.add(bean.getId());
                    }
                }
                if (ids.size() != 0){
                    del();
                }
                break;
        }
    }
    Observable<MyCollcetB> observable;
    Observable<BaseResult> observableDel;
    /**
     * 获取数据
     */
    public void getData(){
        if (function == 1){
            //收藏、
            observable = AppNetModule.createrRetrofit()
                    .collectsMy(MyApp.app.getAccount(),
                            MyApp.app.getUserToken(),
                            MyApp.app.getUid(),type,
                            String.valueOf(MyApp.app.getBdLocation().getLatitude()),
                            String.valueOf(MyApp.app.getBdLocation().getLongitude()));
        }else{
            //我的分享
            observable = AppNetModule.createrRetrofit()
                    .shareMy(MyApp.app.getAccount(),
                            MyApp.app.getUserToken(),
                            MyApp.app.getUid(),type,
                            String.valueOf(MyApp.app.getBdLocation().getLatitude()),
                            String.valueOf(MyApp.app.getBdLocation().getLongitude()));
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<MyCollcetB>() {
                    @Override
                    public void onSuccess(MyCollcetB result) {
                        if (page == 1){
                            adapter.getData().clear();
                        }
                        adapter.addData(result.getReturnValue().getDatas());
                        if (result.getReturnValue().getDatas().size() < pagesize){
                            smartRefreshLayout.setNoMoreData(true);
                        }
                        smartRefreshLayout.finishRefresh();
                    }
                    @Override
                    public void onError(String msg) {
                        ToastUtils.toast(mContext,msg);
                        smartRefreshLayout.finishRefresh();
                    }
                });
    }

    /**
     * 批量删除
     */
    public void del(){
        if (function == 1){
            //收藏
            observableDel = AppNetModule.createrRetrofit()
                    .collectsOperate(MyApp.app.getAccount(), MyApp.app.getUserToken(),type,1,ids);
        }else{
            //我的分享
            observableDel = AppNetModule.createrRetrofit()
                    .deleteShare(MyApp.app.getAccount(), MyApp.app.getUserToken(),ids);
        }
        observableDel.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResult>() {
                    @Override
                    public void onSuccess(BaseResult result) {
                        adapter.setEdit(false);
                        getData();
                    }
                    @Override
                    public void onError(String msg) {
                        ToastUtils.toast(mContext,msg);
                    }
                });
    }
}
