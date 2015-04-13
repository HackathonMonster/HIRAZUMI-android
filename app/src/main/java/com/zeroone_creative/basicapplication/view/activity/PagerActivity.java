package com.zeroone_creative.basicapplication.view.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.zeroone_creative.basicapplication.R;
import com.zeroone_creative.basicapplication.controller.util.AssetUtil;
import com.zeroone_creative.basicapplication.model.enumerate.PageType;
import com.zeroone_creative.basicapplication.model.pojo.PageHeader;
import com.zeroone_creative.basicapplication.view.adapter.BasicPagerAdapter;
import com.zeroone_creative.basicapplication.view.fragment.PageFragment;
import com.zeroone_creative.basicapplication.view.fragment.PageFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_pager)
public class PagerActivity extends Activity implements PageFragment.PageScrollListener {

    //private static final float PHOTO_ASPECT_RATIO = 1.7777777f;
    private static final float PHOTO_ASPECT_RATIO = 2.4f;

    @ViewById(R.id.pager_layout_header_photo_container)
    FrameLayout mHeaderPhotoContainerLayout;
    @ViewById(R.id.pager_imageview_header_photo)
    ImageView mHeaderPhotoImageView;
    @ViewById(R.id.pager_layout_content)
    LinearLayout mContentLayout;
    @ViewById(R.id.pager_viewpager)
    ViewPager mViewPager;
    @ViewById(R.id.pager_radiogroup)
    RadioGroup mTabRadioGroup;
    @ViewById(R.id.pager_layout_search)
    LinearLayout mSearchLayout;
    @ViewById(R.id.pager_edittext_search)
    EditText mSearchEditText;

    private int mMaxPhotoHeightPixels;
    private int mPhotoHeightPixels;
    private List<PageHeader> mPageHeaders = new ArrayList<>();
    BasicPagerAdapter mBasicPagerAdapter;

    @AfterViews
    void onAfterViews() {
        RadioButton[] radioButtons = new RadioButton[4];
        radioButtons[0] = (RadioButton) findViewById(R.id.pager_radiobutton0);
        radioButtons[1] = (RadioButton) findViewById(R.id.pager_radiobutton1);
        radioButtons[2] = (RadioButton) findViewById(R.id.pager_radiobutton2);
        radioButtons[3] = (RadioButton) findViewById(R.id.pager_radiobutton3);
        for (int i = 0; i < Math.min(radioButtons.length, PageType.values().length); i++) {
            radioButtons[i].setText(PageType.values()[i].name);
            radioButtons[i].setBackgroundResource(PageType.values()[i].tab);
            radioButtons[i].setTag(new Integer(i));
            radioButtons[i].setOnClickListener(mTabClicked);
        }
        loadHeader();
        setPager();

        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        // ディスプレイのインスタンス生成
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mMaxPhotoHeightPixels = (int) (size.x / PHOTO_ASPECT_RATIO);
        changeTabState(0, true);
    }

    private View.OnClickListener mTabClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getTag() instanceof Integer) {
                Integer position = (Integer) v.getTag();
                mViewPager.setCurrentItem(position, true);
            }
        }
    };

    private void loadHeader() {
        //HeaderのJSON読み込
        String headerJson = AssetUtil.jsonAssetReader("jsons/header.json", getApplicationContext());
        try {
            if (headerJson != null) {
                JSONArray headerArray = new JSONArray(headerJson);
                Gson gson = new Gson();
                for (int i = 0; i < headerArray.length(); i++) {
                    mPageHeaders.add(gson.fromJson(headerArray.getJSONObject(i).toString(), PageHeader.class));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                ((PageFragment_) mBasicPagerAdapter.getItem(mViewPager.getCurrentItem())).setScrollReset();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void changeTabState(int position, boolean state) {
        position = Math.min(position, mTabRadioGroup.getChildCount());
        position = Math.max(position, 0);
        RadioButton radioButton = (RadioButton) mTabRadioGroup.getChildAt(position);
        radioButton.setChecked(state);

        mSearchLayout.setVisibility((position == PageType.values().length - 1) ? View.VISIBLE : View.GONE);
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            if (position == PageType.values().length - 1) {
                // ソフトキーボードを表示する
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.SHOW_IMPLICIT);
            } else {
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        mHeaderPhotoImageView.setImageResource(PageType.values()[position].image);
        mHeaderPhotoContainerLayout.setBackgroundResource(PageType.values()[position].color);
        recomputePhotoAndScrollingMetrics();
    }

    @Click(R.id.pager_imagebutton_search)
    void bookSearch() {
        if (mViewPager.getCurrentItem() == PageType.values().length - 1) {
            ((PageFragment_) mBasicPagerAdapter.getItem(PageType.values().length - 1)).setSearchParam(mSearchEditText.getText().toString());
        }
        // ソフトキーボードを非表示にする
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void recomputePhotoAndScrollingMetrics() {
        mPhotoHeightPixels = 0;
        if (true) {
            mPhotoHeightPixels = mMaxPhotoHeightPixels;
        }
        ViewGroup.LayoutParams layoutParams = mHeaderPhotoContainerLayout.getLayoutParams();
        if (layoutParams.height != mPhotoHeightPixels) {
            layoutParams.height = mPhotoHeightPixels;
            mHeaderPhotoContainerLayout.setLayoutParams(layoutParams);
        }
        //TODO 現在の場所のフラグメントを取得しonScrollChangedに渡す
        onScrollChanged(null, 0); // trigger scroll handling
    }

    @Override
    public void onScrollChanged(PageType pageType, int scrollY) {
        float newTop = Math.max(mPhotoHeightPixels - scrollY, 0);
        newTop = Math.min(newTop, mMaxPhotoHeightPixels);
        mContentLayout.setTranslationY(newTop);
        // Move background photo (parallax effect)
        scrollY = Math.max(scrollY, 0);
        mHeaderPhotoContainerLayout.setTranslationY(scrollY * 0.5f * -1);
        Log.d(getClass().getSimpleName(), "ScrollY: " + String.valueOf(scrollY));
    }
}
