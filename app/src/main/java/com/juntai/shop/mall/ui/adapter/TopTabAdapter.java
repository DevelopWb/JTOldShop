package com.juntai.shop.mall.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.juntai.shop.mall.R;

import java.util.List;

/**
 * Created by Ma
 * on 2019/8/29
 */
public class TopTabAdapter extends FragmentPagerAdapter {
    private Context mContext;
    List<Fragment> mFragments;
    private String[] titles;

    public TopTabAdapter(FragmentManager fm, Context contexts, String[] title, List<Fragment> fragments) {
        super(fm);
        mContext = contexts;
        mFragments = fragments;
        this.titles = title;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    /**
     * 自定义底部消息tab
     *
     * @param position
     * @return
     */
    public View getTabView(int position) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.custom_top_tabitem, null);
        TextView title = v.findViewById(R.id.tabitem_text);
        title.setText(titles[position]);
        return v;
    }
}