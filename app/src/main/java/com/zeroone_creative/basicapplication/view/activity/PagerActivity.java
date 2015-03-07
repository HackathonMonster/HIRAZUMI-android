package com.zeroone_creative.basicapplication.view.activity;

import android.app.Activity;
import android.support.v4.view.ViewPager;

import com.zeroone_creative.basicapplication.R;
import com.zeroone_creative.basicapplication.view.adapter.BasicPagerAdapter;
import com.zeroone_creative.basicapplication.view.fragment.PageFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_pager)
public class PagerActivity extends Activity {

    BasicPagerAdapter mBasicPagerAdapter;

    @ViewById(R.id.pager_viewpager)
    ViewPager mViewPager;

    @AfterViews
    void onAfterViews() {
        setPager();
    }

    void setPager() {
        mBasicPagerAdapter = new BasicPagerAdapter(getFragmentManager());
        mBasicPagerAdapter.addPages(PageFragment_.builder().pageId(0).build());
        mBasicPagerAdapter.addPages(PageFragment_.builder().pageId(1).build());
        mBasicPagerAdapter.addPages(PageFragment_.builder().pageId(2).build());
        mBasicPagerAdapter.addPages(PageFragment_.builder().pageId(3).build());
        mViewPager.setAdapter(mBasicPagerAdapter);
    }
}
