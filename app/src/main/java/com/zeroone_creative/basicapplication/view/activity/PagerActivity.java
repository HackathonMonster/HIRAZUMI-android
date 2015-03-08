package com.zeroone_creative.basicapplication.view.activity;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zeroone_creative.basicapplication.R;
import com.zeroone_creative.basicapplication.model.enumerate.PageType;
import com.zeroone_creative.basicapplication.view.adapter.BasicPagerAdapter;
import com.zeroone_creative.basicapplication.view.fragment.PageFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_pager)
public class PagerActivity extends Activity {

    BasicPagerAdapter mBasicPagerAdapter;

    @ViewById(R.id.pager_viewpager)
    ViewPager mViewPager;
    @ViewById(R.id.pager_radiogroup)
    RadioGroup mTabRadioGroup;
    RadioButton[] mRadioButtons;
    @ViewById(R.id.pager_layout_search)
    LinearLayout mSearchLayout;
    @ViewById(R.id.pager_edittext_search)
    EditText mSearchEditText;

    @AfterViews
    void onAfterViews() {
        mRadioButtons = new RadioButton[4];
        mRadioButtons[0] = (RadioButton) findViewById(R.id.pager_radiobutton0);
        mRadioButtons[1] = (RadioButton) findViewById(R.id.pager_radiobutton1);
        mRadioButtons[2] = (RadioButton) findViewById(R.id.pager_radiobutton2);
        mRadioButtons[3] = (RadioButton) findViewById(R.id.pager_radiobutton3);
        for (int i = 0; i < Math.min(mRadioButtons.length, PageType.values().length); i++) {
            mRadioButtons[i].setText(PageType.values()[i].name);
            mRadioButtons[i].setBackgroundResource(PageType.values()[i].tab);
        }
        changeTabState(0, true);
        setPager();
    }

    private void setPager() {
        mBasicPagerAdapter = new BasicPagerAdapter(getFragmentManager());
        mBasicPagerAdapter.addPages(PageFragment_.builder().pageId(0).build());
        mBasicPagerAdapter.addPages(PageFragment_.builder().pageId(1).build());
        mBasicPagerAdapter.addPages(PageFragment_.builder().pageId(2).build());
        mBasicPagerAdapter.addPages(PageFragment_.builder().pageId(3).build());
        mViewPager.setAdapter(mBasicPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                changeTabState(position, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void changeTabState(int position, boolean state) {
        if (position >= 0 && position < mRadioButtons.length) {
            mRadioButtons[position].setChecked(state);
        }
        mSearchLayout.setVisibility((position == PageType.values().length - 1) ? View.VISIBLE : View.GONE);
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (position == PageType.values().length - 1) {
            // ソフトキーボードを表示する
            inputMethodManager.hideSoftInputFromWindow(mSearchEditText.getWindowToken(), InputMethodManager.SHOW_IMPLICIT);
        } else {
            inputMethodManager.hideSoftInputFromWindow(mSearchEditText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Click(R.id.pager_imagebutton_search)
    void bookSearch() {
        if (mViewPager.getCurrentItem() == PageType.values().length - 1) {
            ((PageFragment_) mBasicPagerAdapter.getItem(PageType.values().length - 1)).setSearchParam(mSearchEditText.getText().toString());
        }
        // ソフトキーボードを非表示にする
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mSearchEditText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
