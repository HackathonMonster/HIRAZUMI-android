package com.zeroone_creative.basicapplication.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zeroone_creative.basicapplication.R;
import com.zeroone_creative.basicapplication.model.pojo.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * 本用のRecyclerViewAdapter
 * Created by shunhosaka on 2015/02/27.
 */
public class CategoryAdapter extends RecyclerView.Adapter {

    private LayoutInflater mInflator;
    private Context mContext;
    private List<Category> mContents = new ArrayList<>();

    /**
     * コンストラクタ
     * @param context
     */
    public CategoryAdapter(Context context) {
        mContext = context;
        mInflator = LayoutInflater.from(context);
    }

    // Viewを生成
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(mInflator.inflate(R.layout.item_category_adapter, viewGroup, false));
    }

    // Viewにデータを設定する
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Category category = getItem(position);
        ((ViewHolder) viewHolder).backImageView.setColorFilter(Color.parseColor(category.color), PorterDuff.Mode.SRC_IN);
        Picasso.with(mContext).load(category.image_url).into(((ViewHolder) viewHolder).iconImageView);
        ((ViewHolder) viewHolder).nameTextView.setText(category.name);
    }

    @Override
    public int getItemCount() {
        // return mContents.size();
        return 10;
    }

    /**
     * Get Object Item
     * @param position
     */
    public Category getItem(int position) {
        //return mContents.get(position);
        //TODO
        return new Category("1", "Dribble", "#DB5E74", "https://dl.dropboxusercontent.com/u/31455721/hirazumi/img_category_dribble.png", "1.0.0");
    }

    /**
     * データのセット
     * @param contents
     */
    public void setItems(List<Category> contents) {
        this.mContents = contents;
    }

    // ViewHolder内部でIDと関連づけ
    private static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView backImageView;
        public ImageView iconImageView;
        public TextView nameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            backImageView = (ImageView) itemView.findViewById(R.id.item_category_imageview_back);
            iconImageView = (ImageView) itemView.findViewById(R.id.item_category_imageview_icon);
            nameTextView = (TextView) itemView.findViewById(R.id.item_category_textview_name);
        }
    }

}
