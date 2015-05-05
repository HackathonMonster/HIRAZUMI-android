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

/**
 * 本用のRecyclerViewAdapter
 * Created by shunhosaka on 2015/02/27.
 */
public class CategoryAdapter extends BaseRecyclerAdapter {

    private LayoutInflater mInflator;
    private Context mContext;

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
        View itemView = mInflator.inflate(R.layout.item_category_adapter, viewGroup, false);
        setItemClick(itemView);
        return new ViewHolder(itemView);
    }

    // Viewにデータを設定する
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Category category = (Category) getItem(position);
        ((ViewHolder) viewHolder).backImageView.setColorFilter(Color.parseColor(category.color), PorterDuff.Mode.SRC_IN);
        Picasso.with(mContext).load(category.imageUrl).into(((ViewHolder) viewHolder).iconImageView);
        ((ViewHolder) viewHolder).nameTextView.setText(category.name);
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
