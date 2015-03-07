package com.zeroone_creative.basicapplication.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zeroone_creative.basicapplication.R;
import com.zeroone_creative.basicapplication.model.pojo.Book;

import java.util.ArrayList;
import java.util.List;


/**
 * 本用のRecyclerViewAdapter
 * Created by shunhosaka on 2015/02/27.
 */
public class BookAdapter extends BaseRecyclerAdapter {

    private LayoutInflater mInflator;
    private Context mContext;

    /**
     * コンストラクタ
     * @param context
     */
    public BookAdapter(Context context) {
        mContext = context;
        mInflator = LayoutInflater.from(context);

    }

    // Viewを生成
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = mInflator.inflate(R.layout.item_book_adapter, viewGroup, false);
        setItemClick(itemView);
        return new ViewHolder(itemView);
    }

    // Viewにデータを設定する
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Book book = (Book) super.getItem(position);
        Picasso.with(mContext).load(book.imageUrl).into(((ViewHolder) viewHolder).imageView);
        ((ViewHolder) viewHolder).nameTextView.setText(book.name);
    }

    // ViewHolder内部でIDと関連づけ
    private static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView nameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_book_imageview);
            nameTextView = (TextView) itemView.findViewById(R.id.item_book_textview_name);
        }
    }
}
