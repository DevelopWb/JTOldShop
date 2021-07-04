package com.juntai.shop.mall.ui.act;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.barlibrary.ImmersionBar;
import com.juntai.mall.base.base.BaseObserver;
import com.juntai.mall.base.utils.DialogUtil;
import com.juntai.mall.base.utils.SPTools;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.shop.mall.MyApp;
import com.juntai.shop.mall.AppNetModule;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.SearchGoodsBean;
import com.juntai.shop.mall.bean.SearchShopBean;
import com.juntai.shop.mall.ui.adapter.SearchGoodsAdapter;
import com.juntai.shop.mall.ui.adapter.SearchShopsAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 搜索
 * Created by Ma
 * on 2019/11/23
 */
public class SearchActivity extends AppCompatActivity implements View.OnClickListener{
    TagFlowLayout flowLayout;
    RelativeLayout relativeLayout;
    LinearLayout linearLayout;
    EditText editSearch;
    private SmartRefreshLayout smartRefreshLayout ;
    private RecyclerView recyclerView;
    SearchShopsAdapter shopsAdapter;
    SearchGoodsAdapter goodsAdapter;
    int page = 1,pagesize = 10;
    JSONArray jsonArray = new JSONArray();
    JSONObject jsonObject = new JSONObject();
    List<String> history = new ArrayList<>();
    String strSearch = "";
    CheckBox checkBox;
    TagAdapter tagAdapter = new TagAdapter<String>(history) {
        @Override
        public View getView(FlowLayout parent, int position, String s) {
            TextView tv = new TextView(SearchActivity.this);
            tv.setBackgroundResource(R.drawable.bg_tag);
            tv.setTextColor(Color.BLACK);
            tv.setPadding(20,10,20,10);
            tv.setText(s);
            return tv;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).statusBarDarkFont(true).init();
        setContentView(R.layout.activity_search);
        findViewById(R.id.search_back).setOnClickListener(v -> finish());
        findViewById(R.id.search_top_null_view).getLayoutParams().height = MyApp.statusBarH;
        initView();
        initData();
    }

    public void initView() {
        //
        linearLayout = findViewById(R.id.search_parent_layout);
        relativeLayout = findViewById(R.id.search_historry_layout);
        checkBox = findViewById(R.id.search_check);
        editSearch = findViewById(R.id.search_edit);
        findViewById(R.id.history_delete).setOnClickListener(this);
        flowLayout = findViewById(R.id.search_flowlayout);
        //
        smartRefreshLayout = findViewById(R.id.smartRefreshLayout);
        //刷新
        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            if (goodsAdapter.getData().size() != 0 || shopsAdapter.getData().size() != 0){
                page ++;
                getData();
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        shopsAdapter = new SearchShopsAdapter(R.layout.item_mycollect,new ArrayList());
        goodsAdapter = new SearchGoodsAdapter(R.layout.item_mycollect,new ArrayList());
        recyclerView.setAdapter(goodsAdapter);
        shopsAdapter.setOnItemClickListener((adapter, view, position) -> {
            MyApp.app.activityTool.toShopActivity(SearchActivity.this,shopsAdapter.getData().get(position).getShopId());
        });
        goodsAdapter.setOnItemClickListener((adapter, view, position) -> {
            MyApp.app.activityTool.toGoodsActivity(SearchActivity.this,
                    goodsAdapter.getData().get(position).getShopId(),
                    goodsAdapter.getData().get(position).getCommodityId());
        });
        //
        flowLayout.setTag("history");
        flowLayout.setAdapter(tagAdapter);
        flowLayout.setOnTagClickListener((view, position, parent) -> {
            editSearch.setText(history.get(position));
            search(history.get(position));
            return false;
        });
        editSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                search(editSearch.getText().toString());
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(SearchActivity.this
                        .getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
            return false;
        });
        editSearch.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus){
                relativeLayout.setVisibility(View.VISIBLE);
            }else {
                relativeLayout.setVisibility(View.GONE);
            }
        });
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                checkBox.setText("商品");
                recyclerView.setAdapter(shopsAdapter);
                if (shopsAdapter.getData().size() == 0 && !editSearch.getText().toString().isEmpty()){
                    getData();
                }
            }else {
                checkBox.setText("店铺");
                recyclerView.setAdapter(goodsAdapter);
                if (goodsAdapter.getData().size() == 0 && !editSearch.getText().toString().isEmpty()){
                    getData();
                }
            }
        });
        //
        getHistory();
    }


    public void initData() {

    }
    public void search(String his){
        if (his.isEmpty()){
            ToastUtils.toast(SearchActivity.this,"请输入搜索关键字");
            return;
        }
        linearLayout.requestFocus();
        updateHistory(his);
        relativeLayout.setVisibility(View.GONE);
        strSearch = his;
        page = 1;
        getData();
    }

    /**
     * 获取
     */
    public void getData(){
        if (checkBox.isChecked()){//店铺
            AppNetModule.createrRetrofit()
                    .searchShop(strSearch,String.valueOf(MyApp.app.getBdLocation().getLatitude()),String.valueOf(MyApp.app.getBdLocation().getLongitude()),0,page,pagesize)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<SearchShopBean>(null) {
                        @Override
                        public void onSuccess(SearchShopBean result) {
                            if (page == 1){
                                shopsAdapter.getData().clear();
                            }
                            shopsAdapter.addData(result.getReturnValue().getDatas());
                            if (result.getReturnValue().getDatas().size() < pagesize){
                                smartRefreshLayout.setNoMoreData(true);
                            }
                            pagesize ++;
                        }
                        @Override
                        public void onError(String msg) {
                            ToastUtils.toast(SearchActivity.this,msg);
                        }
                    });
        }else {//商品
            AppNetModule.createrRetrofit()
                    .searchGoods(strSearch,String.valueOf(MyApp.app.getBdLocation().getLatitude()),String.valueOf(MyApp.app.getBdLocation().getLongitude()),1,page,pagesize)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<SearchGoodsBean>(null) {
                        @Override
                        public void onSuccess(SearchGoodsBean result) {
                            if (page == 1){
                                goodsAdapter.getData().clear();
                            }
                            recyclerView.setAdapter(goodsAdapter);
                            goodsAdapter.addData(result.getReturnValue().getDatas());
                            if (result.getReturnValue().getDatas().size() < pagesize){
                                smartRefreshLayout.setNoMoreData(true);
                            }
                            pagesize ++;
                        }
                        @Override
                        public void onError(String msg) {
                            ToastUtils.toast(SearchActivity.this,msg);
                        }
                    });
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.history_delete:
                DialogUtil.getConfirmDialog(SearchActivity.this, "是否清除所有搜索记录", (dialog, which) -> {
                    try {
                        history.clear();
                        jsonArray = new JSONArray();
                        jsonObject = new JSONObject();
                        jsonObject.put("list",jsonArray);
                        SPTools.saveString(SearchActivity.this,"history",jsonObject.toString());
                        tagAdapter.notifyDataChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }).show();
                break;
        }
    }

    /**
     * 获取本地历史记录
     */
    public void getHistory(){
        try {
            history.clear();
            jsonObject = new JSONObject(SPTools.getString(SearchActivity.this,"history",""));
            jsonArray = jsonObject.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                history.add(jsonArray.getString(i));
            }
            tagAdapter.notifyDataChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新历史记录
     * @param his
     */
    public void updateHistory(String his){
        if (his.isEmpty())
            return;
        try {
            history.remove(his);
            history.add(0,his);
            jsonArray = new JSONArray(history);
            jsonObject.put("list",jsonArray);
            SPTools.saveString(SearchActivity.this,"history",jsonObject.toString());
            tagAdapter.notifyDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    /**
     * 点击空白隐藏键盘
     * @param event
     * @param view
     * @param activity
     */
    public void hideKeyboard(MotionEvent event, View view,
                                    Activity activity) {
        try {
            if (view != null && view instanceof EditText) {
                int[] location = {0, 0};
                view.getLocationInWindow(location);
                int left = location[0], top = location[1], right = left
                        + view.getWidth(), bootom = top + view.getHeight();
                // 判断焦点位置坐标是否在空间内，如果位置在控件外，则隐藏键盘
                if (event.getRawX() < left || event.getRawX() > right
                        || event.getY() < top || event.getRawY() > bootom) {

                    // 隐藏键盘
                    IBinder token = view.getWindowToken();
                    InputMethodManager inputMethodManager = (InputMethodManager) activity
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(token,
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                View view = getCurrentFocus();
                hideKeyboard(ev, view, SearchActivity.this);//调用方法判断是否需要隐藏键盘
                break;

            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
