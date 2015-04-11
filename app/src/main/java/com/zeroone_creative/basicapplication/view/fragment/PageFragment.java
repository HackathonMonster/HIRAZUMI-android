package com.zeroone_creative.basicapplication.view.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.zeroone_creative.basicapplication.R;
import com.zeroone_creative.basicapplication.controller.provider.NetworkTaskCallback;
import com.zeroone_creative.basicapplication.controller.provider.VolleyHelper;
import com.zeroone_creative.basicapplication.controller.util.JSONArrayRequestUtil;
import com.zeroone_creative.basicapplication.controller.util.UriUtil;
import com.zeroone_creative.basicapplication.model.enumerate.NetworkTasks;
import com.zeroone_creative.basicapplication.model.enumerate.PageType;
import com.zeroone_creative.basicapplication.model.pojo.Article;
import com.zeroone_creative.basicapplication.model.pojo.Book;
import com.zeroone_creative.basicapplication.model.pojo.Category;
import com.zeroone_creative.basicapplication.view.activity.ArticleDetailActivity_;
import com.zeroone_creative.basicapplication.view.activity.BookDetailsActivity_;
import com.zeroone_creative.basicapplication.view.activity.CategoryResultActivity_;
import com.zeroone_creative.basicapplication.view.adapter.ArticleAdapter;
import com.zeroone_creative.basicapplication.view.adapter.BaseRecyclerAdapter;
import com.zeroone_creative.basicapplication.view.adapter.BookAdapter;
import com.zeroone_creative.basicapplication.view.adapter.CategoryAdapter;
import com.zeroone_creative.basicapplication.view.adapter.RecyclerOnItemClickListener;
import com.zeroone_creative.basicapplication.view.widget.SpacesItemDecoration;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.FlipInBottomXAnimator;

@EFragment(R.layout.fragment_page)
public class PageFragment extends Fragment implements RecyclerOnItemClickListener {

    @ViewById(R.id.page_recyclerview)
    RecyclerView mRecyclerView;
    @FragmentArg("page_id")
    int pageId;
    private PageType mPageType;

    private BaseRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private final static int REQUEST_LIMIT = 10;
    private boolean mIsLoading = false;
    private int mStart = 0;

    private PageScrollListener mScrollListener;
    //検索用のあたい
    private String mSearchParam;

    private int mTotalScrollY = 0;

    @AfterInject
    void onAfterInject() {
        mPageType = PageType.getTypeById(pageId);
    }

    @AfterViews
    void onAfterViews() {
        switch (mPageType) {
            case New:
                mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                //mLayoutManager = new GridLayoutManager(getActivity(), 2);
                mAdapter = new BookAdapter(getActivity());
                getBooks();
                break;
            case Search:
                mLayoutManager = new GridLayoutManager(getActivity(), 2);
                mAdapter = new BookAdapter(getActivity());
                break;
            case Topic:
                mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);
                mAdapter = new ArticleAdapter(getActivity());
                break;
            case Category:
                mLayoutManager = new GridLayoutManager(getActivity(), 2);
                mAdapter = new CategoryAdapter(getActivity());
                getCategories();
                break;
            default:
                return;
        }
        if (mLayoutManager != null && mAdapter != null) {
            mAdapter.setItemClickListener(this);
            mRecyclerView.setItemAnimator(new FlipInBottomXAnimator());
            mRecyclerView.getItemAnimator().setChangeDuration(2000);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.addItemDecoration(new SpacesItemDecoration(getResources().getDimensionPixelSize(R.dimen.element_spacing_small)));
            mRecyclerView.setAdapter(mAdapter);
            //mRecyclerView.setItemAnimator(new FadeInDownAnimator());

            mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    //Calc scroll Y position
                    mTotalScrollY += dy;

                    int visibleItemCount = recyclerView.getChildCount();
                    int totalItemCount = mLayoutManager.getItemCount();
                    int firstVisibleItem = 0;
                    if (mLayoutManager instanceof StaggeredGridLayoutManager) {
                        int[] tmp = new int[2];
                        int[] firstVisibleItems = ((StaggeredGridLayoutManager) mLayoutManager).findFirstVisibleItemPositions(tmp);
                        firstVisibleItem = firstVisibleItems[0];
                    } else if (mLayoutManager instanceof LinearLayoutManager) {
                        firstVisibleItem = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
                    } else if (mLayoutManager instanceof GridLayoutManager) {
                        firstVisibleItem = ((GridLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
                    }
                    if (!mIsLoading) {
                        if ((visibleItemCount + firstVisibleItem) >= totalItemCount) {
                            mIsLoading = true;
                            if (mPageType.equals(PageType.New) || (mPageType.equals(PageType.Search) && mSearchParam != null)) {
                                getBooks();
                            }
                            Log.v("...", "Last Item Wow !");
                        }
                    }
                    if (mScrollListener != null) {
                        mScrollListener.onScrollChanged(mPageType, mTotalScrollY);
                    }

                }
            });
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof PageScrollListener) {
            this.mScrollListener = (PageScrollListener) activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mScrollListener = null;
    }

    @Override
    public void onItemClick(RecyclerView.Adapter adapter, int position, Object object) {
        if (object == null) return;
        if (object instanceof Book) {
            BookDetailsActivity_.intent(getActivity()).mBookJson(new Gson().toJson((Book) object)).start();
        } else if (object instanceof Category) {
            CategoryResultActivity_.intent(getActivity()).mCategoryJson(new Gson().toJson((Category) object)).start();
        } else if (object instanceof Article) {
            ArticleDetailActivity_.intent(getActivity()).mArticleJson(new Gson().toJson((Article) object)).start();
        }
    }

    private void getCategories() {
        if (!mPageType.equals(PageType.Category)) return;
        JSONArrayRequestUtil categoryRequestUtil = new JSONArrayRequestUtil(new NetworkTaskCallback() {
            @Override
            public void onSuccessNetworkTask(int taskId, Object object) {
                List<Object> content = new ArrayList<>();
                if (object instanceof JSONArray) {
                    JSONArray data = (JSONArray) object;
                    for (int i = 0; i < data.length(); i++) {
                        try {
                            content.add(new Gson().fromJson(data.getJSONObject(i).toString(), Category.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                mAdapter.setItems(content);
            }

            @Override
            public void onFailedNetworkTask(int taskId, Object object) {

            }
        },
                this.getClass().getSimpleName(),
                null);
        categoryRequestUtil.onRequest(VolleyHelper.getRequestQueue(getActivity()), Request.Priority.HIGH, UriUtil.getCategoryUri(new ArrayList<NameValuePair>()), NetworkTasks.GetCategory);
    }

    private void getBooks() {
        if (!mPageType.equals(PageType.New) && !(mPageType.equals(PageType.Search))) return;
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
        if (mPageType.equals(PageType.New)) {
            bookRequestUtil.onRequest(VolleyHelper.getRequestQueue(getActivity()), Request.Priority.HIGH, UriUtil.getBookUri(REQUEST_LIMIT, mStart), NetworkTasks.GetBooks);
        } else if (mPageType.equals(PageType.Search) && mSearchParam != null) {
            bookRequestUtil.onRequest(VolleyHelper.getRequestQueue(getActivity()), Request.Priority.HIGH, UriUtil.getBookUriSearch(REQUEST_LIMIT, mStart, mSearchParam), NetworkTasks.GetBooks);
        }
        mStart += REQUEST_LIMIT;
    }

    /**
     * 検索のパラメーター設定とリクエスト
     *
     * @param searchParam
     */
    public void setSearchParam(String searchParam) {
        mSearchParam = searchParam;
        mAdapter.setItems(new ArrayList<Object>());
        getBooks();
    }

    public interface PageScrollListener {
        public void onScrollChanged(PageType pageType, int scrollY);
    }



}