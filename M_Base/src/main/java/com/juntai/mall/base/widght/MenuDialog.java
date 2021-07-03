package com.juntai.mall.base.widght;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.mall.base.R;
import com.juntai.mall.base.app.BaseApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * 底部菜单弹窗
 *
 * Created by Ma
 * on 2019/4/17
 *
 * 使用
 * MenuDialog menuDialog = new MenuDialog();
 * 传值
 * menuDialog.showMenu(getSupportFragmentManager(),Arrays.asList(new String[]{"哈哈，你确定吗","确定","取消"}));
 * 监听
 * menuDialog.setOnItemClickListener(new MenuDialog.OnItemClickListener() {
 *             @Override
 *             public void onItemClick(View view, int position) {
 *                 ToastUtils.toast(mContext,""+position);
 *             }
 *         });
 */
public class MenuDialog extends DialogFragment {
    //
    private List<String> arrayList = new ArrayList();
    RecyclerView recyclerView;
    TextCenterAdapter centerAdapter;
    private OnItemClickListener itemClickListener;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_menu,container,false);
        //
        centerAdapter = new TextCenterAdapter(R.layout.dialog_text_list_center,arrayList);
        recyclerView = view.findViewById(R.id.dialog_menu_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(centerAdapter);
        centerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (itemClickListener != null){
                    itemClickListener.onItemClick(view,position);
                }
                if (getDialog().isShowing())
                    dismiss();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().setCanceledOnTouchOutside(true);
        //Do something
        // 设置宽度为屏宽、位置靠近屏幕底部
        Window window = getDialog().getWindow();
//        window.setWindowAnimations(R.style.dialogWindowAnim);
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = (BaseApplication.W / 10) * 9;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);
    }

    /**
     * 监听事件
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }

    /**
     * 显示dialog - menu
     * @param fragmentManager
     */
    public void showMenu(FragmentManager fragmentManager, List<String> list){
        arrayList.clear();
        arrayList.addAll(list);
        show(fragmentManager,"menu");
    }

    /**
     * 显示dialog - menu
     * @param fragmentTransaction
     * @param list
     */
    public void showMenu(FragmentTransaction fragmentTransaction,  List<String> list){
        arrayList.clear();
        arrayList.addAll(list);
        show(fragmentTransaction,"menu");
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
}

