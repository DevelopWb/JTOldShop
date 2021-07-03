package com.juntai.shop.mall.ui.goods;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.mall.base.base.BaseObserver;
import com.juntai.mall.base.utils.ImageLoadUtil;
import com.juntai.mall.base.utils.ToastUtils;
import com.juntai.mall.base.widght.BottomDialogFragment;
import com.juntai.shop.mall.App;
import com.juntai.shop.mall.AppNetModule;
import com.juntai.shop.mall.R;
import com.juntai.shop.mall.bean.ReportTypeBesan;
import com.juntai.shop.mall.bean.ShopBean;
import com.juntai.shop.mall.ui.goods.adt.ReportAdapter;
import com.juntai.shop.mall.utils.GlideImageLoader;
import com.juntai.shop.mall.utils.StringTools;

import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 选择投诉理由
 * Created by Ma
 * on 2019/9/7
 */
public class ReportDialog extends BottomDialogFragment {
    RecyclerView recyclerView;
    ReportAdapter listAdapter = new ReportAdapter(R.layout.item_report_text, new ArrayList());
    LinearLayout linearLayout;
    LinearLayoutManager layoutManager;
    int nowPosition;
    TextView nowTextView;
    OnSelectListener onSelectListener;
    @Override
    public int setView() {
        return R.layout.dialog_report_select;
    }

    @Override
    public void initView(View view) {

        recyclerView = view.findViewById(R.id.report_selectlist);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(listAdapter);
        //
        layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                nowPosition = (layoutManager.findFirstVisibleItemPosition() + layoutManager.findLastVisibleItemPosition()) / 2;
                setTextView();
            }
        });
        listAdapter.setOnItemClickListener((adapter, view1, position) -> {
            nowPosition = position;
            setTextView();
        });
        if (listAdapter.getData().size() == 0){
            getData();
        }else {
            setTextView();
        }
        view.findViewById(R.id.dialog_report_qx).setOnClickListener(v -> dismiss());
        view.findViewById(R.id.dialog_report_qd).setOnClickListener(v -> {
            if (onSelectListener != null){
                onSelectListener.select(listAdapter.getData().get(nowPosition));
                dismiss();
            }
        });
    }

    @Override
    public int dialogHeight() {
        return App.H / 3;
    }

    /**
     * 动态设置text
     */
    public void setTextView(){
        if (nowTextView != null){
            nowTextView.setTextSize(16);
            nowTextView.setTextColor(getResources().getColor(R.color.gray));
        }
        nowTextView = listAdapter.getTextViewList().get(nowPosition);
        nowTextView.setTextSize(18);
        nowTextView.setTextColor(getResources().getColor(R.color.black));
    }
    private void getData(){
        //selectReportMerchantType
        AppNetModule.createrRetrofit()
                .reportType(App.app.getAccount(),App.app.getUserToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ReportTypeBesan>(null) {
                    @Override
                    public void onSuccess(ReportTypeBesan result) {
                        listAdapter.getData().clear();
                        listAdapter.addData(result.getReturnValue());
                    }
                    @Override
                    public void onError(String msg) {
                        ToastUtils.toast(getActivity(),msg);
                    }
                });
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    public interface OnSelectListener{
        void select(ReportTypeBesan.ReturnValueBean bean);
    }
}
