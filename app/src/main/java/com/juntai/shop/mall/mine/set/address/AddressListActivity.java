package com.juntai.shop.mall.mine.set.address;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.juntai.mall.base.base.BaseActivity;
import com.juntai.mall.base.base.BaseObserver;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.shop.mall.AppNetModule;
import com.juntai.shop.mall.MyApp;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.AddressListBean;
import com.juntai.shop.mall.utils.AppCode;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 收货地址列表
 *
 * @aouther Ma
 * @date 2019/3/9
 */
public class AddressListActivity extends BaseActivity implements View.OnClickListener {
    SmartRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    AddressListAdapter listAdapter;
    /**
     * 新增收货地址
     */
    private TextView mAddReceiveAddrTv;

    @Override
    public int getLayoutView() {
        return R.layout.activity_address_list;
    }

    @Override
    public void initView() {
        setTitleName("地址管理");
        refreshLayout = findViewById(R.id.smartRefreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadMore(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        listAdapter = new AddressListAdapter(R.layout.item_address, new ArrayList());
        recyclerView.setAdapter(listAdapter);
        listAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            MyApp.app.activityTool.toAddAddress(AddressListActivity.this, listAdapter.getData().get(position).getId());
        });
        mAddReceiveAddrTv = (TextView) findViewById(R.id.add_receive_addr_tv);
        mAddReceiveAddrTv.setOnClickListener(this);
    }

    @Override
    public void initData() {
        getAddress(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddress(false);
    }

    /**
     * 获取所有收货地址
     */
    public void getAddress(boolean isR) {
        AppNetModule.createrRetrofit()
                .addressList(MyApp.app.getAccount(), MyApp.app.getUserToken(), MyApp.app.getUid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<AddressListBean>(null) {
                    @Override
                    public void onSuccess(AddressListBean o) {
                        listAdapter.getData().clear();
                        listAdapter.addData(o.getReturnValue());
                    }

                    @Override
                    public void onError(String msg) {
                        if (!isR) {
                            ToastUtils.toast(mContext, msg);
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppCode.ADDRESS && resultCode == RESULT_OK) {
            getAddress(false);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.add_receive_addr_tv:
                MyApp.app.activityTool.toAddAddress(AddressListActivity.this, -1);
                break;
        }
    }
}
