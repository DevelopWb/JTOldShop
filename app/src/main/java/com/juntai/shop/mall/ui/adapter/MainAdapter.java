package com.juntai.shop.mall.ui.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.juntai.shop.mall.R;

import java.util.List;

/**
 * 首页tablayout适配器
 */
public class MainAdapter extends FragmentPagerAdapter {
    private Context mContext;
    List<Fragment> mFragments;
    private int[] images;
    private String[] titles;
    public MainAdapter(FragmentManager fm, Context contexts, String[] title, int[] img, List<Fragment> fragments) {
        super(fm);
        mContext = contexts;
        images = img;
        mFragments = fragments;
        this.titles = title;
    }

    public MainAdapter(FragmentManager fm, Context contexts, String[] title, List<Fragment> fragments) {
        super(fm);
        mContext = contexts;
        mFragments = fragments;
        this.titles = title;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    /**
     * 自定义底部tab
     * */
    public View getTabView(int position) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.custom_tabitem, null);
        ImageView img = v.findViewById(R.id.tabitem_image);
        img.setImageResource(images[position]);
        TextView title = v.findViewById(R.id.tabitem_text);
        title.setText(titles[position]);
        return v;
    }
}
