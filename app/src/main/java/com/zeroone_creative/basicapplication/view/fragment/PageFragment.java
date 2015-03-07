package com.zeroone_creative.basicapplication.view.fragment;

import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.zeroone_creative.basicapplication.R;
import com.zeroone_creative.basicapplication.model.enumerate.PageType;
import com.zeroone_creative.basicapplication.view.adapter.ArticleAdapter;
import com.zeroone_creative.basicapplication.view.adapter.BookAdapter;
import com.zeroone_creative.basicapplication.view.adapter.CategoryAdapter;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_page)
public class PageFragment extends Fragment {

    @ViewById(R.id.page_recyclerview)
    RecyclerView mRecyclerView;

    @FragmentArg("page_id")
    int pageId;
    private PageType mPageType;

    private RecyclerView.Adapter mAdapter;

    @AfterInject
    void onAfterInject() {
        mPageType = PageType.getTypeById(pageId);
    }

    @AfterViews
    void onAfterViews() {
        RecyclerView.LayoutManager layoutManager = null;
        switch (mPageType) {
            case New:
            case Search:
                layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                mAdapter = new BookAdapter(getActivity());
                break;
            case Topic:
                layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);
                mAdapter = new ArticleAdapter(getActivity());
                break;
            case Category:
                layoutManager = new GridLayoutManager(getActivity(), 2);
                mAdapter = new CategoryAdapter(getActivity());
                break;
            default:
                return;
        }
        if (layoutManager != null && mAdapter != null) {
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

}