package com.zeroone_creative.basicapplication.view.adapter;

import android.support.v7.widget.RecyclerView;

/**
 * RecyclerViewからのイベントをを返すリスナ−
 * Created by shunhosaka on 2015/03/07.
 */
public interface RecyclerOnItemClickListener {
    public void onItemClick(RecyclerView.Adapter adapter, int position, Object object);
}
