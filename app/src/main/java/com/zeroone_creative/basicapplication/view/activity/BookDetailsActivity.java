package com.zeroone_creative.basicapplication.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.view.ViewCompat;
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
import com.zeroone_creative.basicapplication.model.pojo.Author;
import com.zeroone_creative.basicapplication.model.pojo.Book;
import com.zeroone_creative.basicapplication.view.fragment.BookImageFragment;
import com.zeroone_creative.basicapplication.view.widget.CheckableFrameLayout;
import com.zeroone_creative.basicapplication.view.widget.ObservableScrollView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_book_details)
public class BookDetailsActivity extends ActionBarActivity implements ObservableScrollView.Callbacks {

    @Extra("book_json")
    String mBookJson;
    Book mBook;

    //private static final float PHOTO_ASPECT_RATIO = 1.7777777f;
    private static final float PHOTO_ASPECT_RATIO = 1.2f;

    @ViewById(R.id.scroll_view)
    ObservableScrollView mScrollView;
    @ViewById(R.id.scroll_view_child)
    View mScrollViewChild;
    @ViewById(R.id.session_photo_container)
    View mPhotoViewContainer;
    @ViewById(R.id.session_photo)
    ImageView mPhotoView;
    @ViewById(R.id.details_toolbar_actionbar)
    Toolbar mToolbar;
    @ViewById(R.id.detail_textview_title)
    TextView mTitleTextView;
    @ViewById(R.id.detail_textview_author)
    TextView mAuthorTextView;
    @ViewById(R.id.detail_textview_detail)
    TextView mDetailTextView;
    @ViewById(R.id.add_schedule_button)
    CheckableFrameLayout mAddScheduleButton;
    @ViewById(R.id.session_tags)
    LinearLayout mTagsLayout;
    @ViewById(R.id.header_session)
    View mHeaderBox;
    @ViewById(R.id.details_container)
    View mDetailsContainer;
    @ViewById(R.id.details_textview_price)
    TextView mPriceTextView;
    @ViewById(R.id.details_textview_page)
    TextView mPageTextView;

    private boolean mSessionCursor = false;
    private boolean mSpeakersCursor = false;
    private boolean mHasSummaryContent = false;

    private int mPhotoHeightPixels;
    private int mHeaderHeightPixels;
    private int mAddScheduleButtonHeightPixels;

    private float mMaxHeaderElevation;
    private float mFABElevation;

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
        mBook = new Gson().fromJson(mBookJson, Book.class);
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

        mFABElevation = getResources().getDimensionPixelSize(R.dimen.element_spacing_small);
        mMaxHeaderElevation = getResources().getDimensionPixelSize(R.dimen.element_spacing_small);
        mScrollView.addCallbacks(this);
        ViewTreeObserver vto = mScrollView.getViewTreeObserver();
        if (vto.isAlive()) {
            vto.addOnGlobalLayoutListener(mGlobalLayoutListener);
        }
        setBookUi(mBook);
    }

    private void setBookUi(Book book) {
        if (book == null) return;
        Picasso.with(this).load(book.imageUrl).into(mGetPhotoTarget);
        mTitleTextView.setText(book.name);
        StringBuilder authorBuilder = new StringBuilder();
        for (Author author : book.authors) {
            authorBuilder.append(author.name);
        }
        mPriceTextView.setText(getString(R.string.book_details_price, book.yen));
        mPageTextView.setText(getString(R.string.book_details_page, book.page));
        mAuthorTextView.setText(getString(R.string.book_details_author, authorBuilder.toString()));
        mDetailTextView.setText(book.description);
        if (book.description.equals("")) {
            mDetailTextView.setText("現在説明がありません。");
        }
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
            mAddScheduleButtonHeightPixels = mAddScheduleButton.getHeight();
            recomputePhotoAndScrollingMetrics();
        }
    };

    @Override
    public void onScrollChanged(int deltaX, int deltaY) {
        int scrollY = mScrollView.getScrollY();
        float newTop = Math.max(mPhotoHeightPixels, scrollY);
        mHeaderBox.setTranslationY(newTop);
        mAddScheduleButton.setTranslationY(newTop + mHeaderHeightPixels - mAddScheduleButtonHeightPixels / 2);
        float gapFillProgress = 1;
        if (mPhotoHeightPixels != 0) {
            gapFillProgress = Math.min(Math.max(getProgress(scrollY,
                    0,
                    mPhotoHeightPixels), 0), 1);
        }
        ViewCompat.setElevation(mHeaderBox, gapFillProgress * mMaxHeaderElevation);
        ViewCompat.setElevation(mAddScheduleButton, gapFillProgress * mMaxHeaderElevation + mFABElevation);
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
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_share:
                if (mBook != null) {
                    StringBuilder shareText = new StringBuilder();
                    shareText.append(mBook.name);
                    if (mBook.authors.size() > 0) {
                        shareText.append("(");
                        shareText.append(mBook.authors.get(0).name);
                        shareText.append(")");
                    }
                    if (mBook.shopUrls.size() > 0) {
                        shareText.append("\n");
                        shareText.append(mBook.shopUrls.get(0));
                    }
                    String url = "http://twitter.com/share?text=" + shareText.toString();
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
                return true;
            case R.id.menu_buy:
                if (mBook.shopUrls.size() > 0) {
                    Uri uri = Uri.parse(mBook.shopUrls.get(0).url);
                    intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
                return true;
        }
        return false;
    }

    @Click(R.id.session_photo)
    void scaleUpImage() {
        if (mBook != null && getSupportFragmentManager().findFragmentByTag("ScaleUpImageFragment") == null) {
            BookImageFragment.newInstance(mBook.imageUrl).show(getSupportFragmentManager(), "ScaleUpImageFragment");
        }
    }

}
