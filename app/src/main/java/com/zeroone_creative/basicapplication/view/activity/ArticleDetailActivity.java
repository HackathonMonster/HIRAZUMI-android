package com.zeroone_creative.basicapplication.view.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.zeroone_creative.basicapplication.R;
import com.zeroone_creative.basicapplication.model.pojo.Article;
import com.zeroone_creative.basicapplication.view.widget.ObservableScrollView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_article_detail)
public class ArticleDetailActivity extends ActionBarActivity implements ObservableScrollView.Callbacks {

    @Extra("article_json")
    String mArticleJson;
    Article mArticle;

    //private static final float PHOTO_ASPECT_RATIO = 1.7777777f;
    private static final float PHOTO_ASPECT_RATIO = 1.2f;

    @ViewById(R.id.scroll_view)
    ObservableScrollView mScrollView;
    @ViewById(R.id.scroll_view_child)
    View mScrollViewChild;
    @ViewById(R.id.icon_photo_container)
    View mPhotoViewContainer;
    @ViewById(R.id.icon_photo)
    ImageView mPhotoView;
    @ViewById(R.id.details_toolbar_actionbar)
    Toolbar mToolbar;
    @ViewById(R.id.detail_textview_title)
    TextView mTitleTextView;
    @ViewById(R.id.detail_textview_author)
    TextView mAuthorTextView;
    @ViewById(R.id.detail_textview_detail)
    TextView mDetailTextView;
    @ViewById(R.id.books_container)
    LinearLayout mBooksLayout;
    @ViewById(R.id.header_session)
    View mHeaderBox;
    @ViewById(R.id.details_container)
    View mDetailsContainer;

    private boolean mSessionCursor = false;
    private boolean mSpeakersCursor = false;
    private boolean mHasSummaryContent = false;

    private int mPhotoHeightPixels;
    private int mHeaderHeightPixels;

    private boolean mHasPhoto = false;

    private Target mGetPhotoTarget = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            mPhotoView.setImageBitmap(bitmap);
            mHasPhoto = true;
            //画像の高さを入力しての再計算
            recomputePhotoAndScrollingMetrics();
        }
        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            mHasPhoto = false;
            //画像の高さを入力しての再計算
            recomputePhotoAndScrollingMetrics();
        }
        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    };

    @AfterInject
    void onAfterInject() {
        mArticle = new Gson().fromJson(mArticleJson, Article.class);
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

        mScrollView.addCallbacks(this);
        ViewTreeObserver vto = mScrollView.getViewTreeObserver();
        if (vto.isAlive()) {
            vto.addOnGlobalLayoutListener(mGlobalLayoutListener);
        }
        setBookUi(mArticle);



    }

    private void setBookUi(Article article) {
        if (article == null) return;
        mTitleTextView.setText(article.title);
        mAuthorTextView.setText("Pat Shaughnessy");
        Picasso.with(this).load(article.author.imageUrl).into(mGetPhotoTarget);
        mDetailTextView.setText(article.description);
    }

    private void recomputePhotoAndScrollingMetrics() {
        mHeaderHeightPixels = mHeaderBox.getHeight();

        mPhotoHeightPixels = 0;
        if (mHasPhoto) {
            mPhotoHeightPixels = (int) (mPhotoView.getWidth() / PHOTO_ASPECT_RATIO);
            mPhotoHeightPixels = Math.min(mPhotoHeightPixels, mScrollView.getHeight() * 2 / 3);
        }

        ViewGroup.LayoutParams lp;
        lp = mPhotoViewContainer.getLayoutParams();
        if (lp.height != mPhotoHeightPixels) {
            lp.height = mPhotoHeightPixels;
            mPhotoViewContainer.setLayoutParams(lp);
        }

        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams)
                mDetailsContainer.getLayoutParams();
        if (mlp.topMargin != mHeaderHeightPixels + mPhotoHeightPixels) {
            mlp.topMargin = mHeaderHeightPixels + mPhotoHeightPixels;
            mDetailsContainer.setLayoutParams(mlp);
        }
        onScrollChanged(0, 0); // trigger scroll handling
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mScrollView == null) {
            return;
        }

        ViewTreeObserver vto = mScrollView.getViewTreeObserver();
        if (vto.isAlive()) {
            vto.removeGlobalOnLayoutListener(mGlobalLayoutListener);
        }
    }

    private ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener
            = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            recomputePhotoAndScrollingMetrics();
        }
    };

    @Override
    public void onScrollChanged(int deltaX, int deltaY) {
        // Reposition the header bar -- it's normally anchored to the top of the content,
        // but locks to the top of the screen on scroll
        int scrollY = mScrollView.getScrollY();
        float newTop = Math.max(mPhotoHeightPixels, scrollY);
        mHeaderBox.setTranslationY(newTop);

        float gapFillProgress = 1;
        if (mPhotoHeightPixels != 0) {
            gapFillProgress = Math.min(Math.max(getProgress(scrollY,
                    0,
                    mPhotoHeightPixels), 0), 1);
        }
        // Move background photo (parallax effect)
        mPhotoViewContainer.setTranslationY(scrollY * 0.5f);
    }

    private float getProgress(int value, int min, int max) {
        if (min == max) {
            throw new IllegalArgumentException("Max (" + max + ") cannot equal min (" + min + ")");
        }

        return (value - min) / (float) (max - min);
    }

    private void updateEmptyView() {
        findViewById(android.R.id.empty).setVisibility(
                (mSpeakersCursor && mSessionCursor && !mHasSummaryContent)
                        ? View.VISIBLE
                        : View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_book_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:

                return true;
        }
        return false;
    }

}
