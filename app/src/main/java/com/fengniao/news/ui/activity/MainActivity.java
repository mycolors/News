package com.fengniao.news.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.fengniao.news.R;
import com.fengniao.news.ui.base.BaseActivity;
import com.fengniao.news.ui.fragment.TouTiaoNewsFragment;
import com.fengniao.news.ui.fragment.ZhiHuArticleFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private String[] titles = {"知乎日报", "今日头条"};
    private List<Fragment> mList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView() {
        mList = new ArrayList<>();
        mList.add(new ZhiHuArticleFragment());
        mList.add(new TouTiaoNewsFragment());
        MyViewPagerAdapter mAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(mList.size());
        tabLayout.setupWithViewPager(viewPager);
    }


    private class MyViewPagerAdapter extends FragmentStatePagerAdapter {
        MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mList.get(position);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
