package com.zeroone_creative.basicapplication.view.activity;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.zeroone_creative.basicapplication.R;
import com.zeroone_creative.basicapplication.controller.provider.NetworkTaskCallback;
import com.zeroone_creative.basicapplication.controller.provider.VolleyHelper;
import com.zeroone_creative.basicapplication.controller.util.JSONArrayRequestUtil;
import com.zeroone_creative.basicapplication.controller.util.UriUtil;
import com.zeroone_creative.basicapplication.model.enumerate.NetworkTasks;
import com.zeroone_creative.basicapplication.model.pojo.Book;
import com.zeroone_creative.basicapplication.model.pojo.Category;
import com.zeroone_creative.basicapplication.view.adapter.BaseRecyclerAdapter;
import com.zeroone_creative.basicapplication.view.adapter.BookAdapter;
import com.zeroone_creative.basicapplication.view.adapter.RecyclerOnItemClickListener;
import com.zeroone_creative.basicapplication.view.widget.SpacesItemDecoration;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.FlipInBottomXAnimator;


@EActivity(R.layout.activity_category_result)
public class CategoryResultActivity extends ActionBarActivity implements RecyclerOnItemClickListener {

    private final static int REQUEST_LIMIT = 10;
    private boolean mIsLoading = false;
    private int mStart = 0;

    @Extra("category_json")
    String mCategoryJson;
    Category mCategory;

    @ViewById(R.id.category_layout_header)
    FrameLayout mHeaderContainerLayout;
    @ViewById(R.id.category_toolbar_actionbar)
    Toolbar mToolbar;
    @ViewById(R.id.category_textview_name)
    TextView mNameTextView;
    @ViewById(R.id.category_imageview_icon)
    ImageView mIconImageView;
    @ViewById(R.id.category_recyclerview)
    RecyclerView mRecyclerView;

    private BaseRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @AfterInject
    void onAffterInject() {
        mCategory = new Gson().fromJson(mCategoryJson, Category.class);
    }

    @AfterViews
    void onAfterViews() {
        setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("");
        mToolbar.setNavigationIcon(R.drawable.ic_up);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mHeaderContainerLayout.setBackgroundColor(Color.parseColor(mCategory.color));
        mNameTextView.setText(mCategory.name);
        Picasso.with(this).load(mCategory.imageUrl).into(mIconImageView);
        setRecyclerView();
    }

    private void setRecyclerView() {
        //mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager = new GridLayoutManager(this, 2);
        mAdapter = new BookAdapter(this);

        if (mLayoutManager != null && mAdapter != null) {
            mAdapter.setItemClickListener(this);
            mRecyclerView.setItemAnimator(new FlipInBottomXAnimator());
            mRecyclerView.getItemAnimator().setChangeDuration(2000);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.addItemDecoration(new SpacesItemDecoration(getResources().getDimensionPixelSize(R.dimen.element_spacing_small)));
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int visibleItemCount = recyclerView.getChildCount();
                    int totalItemCount = mLayoutManager.getItemCount();
                    int firstVisibleItem = 0;
                    if (mLayoutManager instanceof GridLayoutManager) {
                        firstVisibleItem = ((GridLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
                    } else if (mLayoutManager instanceof StaggeredGridLayoutManager) {
                        //firstVisibleItem = ((StaggeredGridLayoutManager) mLayoutManager).findFirstVisibleItemPositions():
                    }
                    if (!mIsLoading) {
                        if ((visibleItemCount + firstVisibleItem) >= totalItemCount) {
                            mIsLoading = true;
                            getBooks();
                        }
                    }
                }
            });
            getBooks();
        }
    }

    @Override
    public void onItemClick(RecyclerView.Adapter adapter, int position, Object object) {
        if (object == null) return;
        if (object instanceof Book) {
            BookDetailsActivity_.intent(this).mBookJson(new Gson().toJson((Book) object)).start();
        }
    }

    private void getBooks() {
        JSONArrayRequestUtil bookRequestUtil = new JSONArrayRequestUtil(new NetworkTaskCallback() {
            @Override
            public void onSuccessNetworkTask(int taskId, Object object) {
                List<Object> content = new ArrayList<>();
                if (object instanceof JSONArray) {
                    JSONArray data = (JSONArray) object;
                    for (int i = 0; i < data.length(); i++) {
                        try {
                            content.add(new Gson().fromJson(data.getJSONObject(i).toString(), Book.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                mIsLoading = false;
                mAdapter.addItems(content);
            }

            @Override
            public void onFailedNetworkTask(int taskId, Object object) {

            }
        },
                this.getClass().getSimpleName(),
                null);
        bookRequestUtil.onRequest(VolleyHelper.getRequestQueue(this), Request.Priority.HIGH, UriUtil.getBookUriAddCategoryFilter(REQUEST_LIMIT, mStart, mCategory.name), NetworkTasks.GetBooks);
        mStart += REQUEST_LIMIT;
    }
}