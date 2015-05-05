package com.zeroone_creative.basicapplication.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shunhosaka on 2015/03/07.
 */
public class BaseRecyclerAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private List<Object> mContents = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerOnItemClickListener mClickListener;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mRecyclerView = null;
    }

    // Viewを生成
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return null;
    }

    // Viewにデータを設定する
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
    }

    @Override
    public int getItemCount() {
        return mContents.size();
    }

    /**
     * Get Object Item
     * @param position
     */
    public Object getItem(int position) {
        return mContents.get(position);
    }

    /**
     * データのセット
     * @param contents
     */
    public void setItems(List<Object> contents) {
        this.mContents = contents;
        notifyDataSetChanged();
    }

    /**
     * データの追加
     * @param contents
     */
    public void addItems(List<Object> contents) {
        this.mContents.addAll(contents);
        notifyDataSetChanged();
    }

    void setItemClick(View view) {
        view.setOnClickListener(this);
    }

    public void setItemClickListener(RecyclerOnItemClickListener listener) {
        this.mClickListener = listener;
    }

    @Override
    public void onClick(View view) {
        if (mRecyclerView == null) return;
        if (mClickListener != null) {
            int position = mRecyclerView.getChildPosition(view);
            Object object = getItem(position);
            mClickListener.onItemClick(this, position, object);
        }

    }


}
