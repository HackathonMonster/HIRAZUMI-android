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
public class BookAdapter extends RecyclerView.Adapter {

    private LayoutInflater mInflator;
    private Context mContext;
    private List<Book> mContents = new ArrayList<>();

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
        return new ViewHolder(mInflator.inflate(R.layout.item_book_adapter, viewGroup, false));
    }

    // Viewにデータを設定する
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Book book = getItem(position);
        Picasso.with(mContext).load(book.imageUrl).into(((ViewHolder) viewHolder).imageView);
        ((ViewHolder) viewHolder).nameTextView.setText(book.name);
    }

    @Override
    public int getItemCount() {
        //return mContents.size();
        return 10;
    }

    /**
     * Get Object Item
     * @param position
     */
    public Book getItem(int position) {
        //return mContents.get(position);
        //TODO 後で正式なデータに変更する
        return new Book("Rubyのしくみ -Ruby Under a Microscope-", "https://dl.dropboxusercontent.com/u/31455721/hirazumi/img_book_ruby.jpeg");
    }

    /**
     * データのセット
     * @param contents
     */
    public void setItems(List<Book> contents) {
        this.mContents = contents;
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
