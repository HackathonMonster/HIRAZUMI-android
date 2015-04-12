package com.zeroone_creative.basicapplication.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zeroone_creative.basicapplication.R;
import com.zeroone_creative.basicapplication.controller.util.OvalTransformation;
import com.zeroone_creative.basicapplication.model.pojo.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * 本用のRecyclerViewAdapter
 * Created by shunhosaka on 2015/02/27.
 */
public class ArticleAdapter extends BaseRecyclerAdapter {

    private LayoutInflater mInflator;
    private Context mContext;

    /**
     * コンストラクタ
     * @param context
     */
    public ArticleAdapter(Context context) {
        mContext = context;
        mInflator = LayoutInflater.from(context);
    }

    // Viewを生成
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = mInflator.inflate(R.layout.item_article_adapter, viewGroup, false);
        setItemClick(itemView);
        return new ViewHolder(itemView);
    }

    // Viewにデータを設定する
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Article article = (Article) getItem(position);
        Picasso.with(mContext).load(article.author.imageUrl).transform(new OvalTransformation()).into(((ViewHolder) viewHolder).iconImageView);
        ((ViewHolder) viewHolder).userNameTextView.setText(article.author.name);
        //TODO
        ((ViewHolder) viewHolder).dateTextView.setText(article.datePublished.date);
        ((ViewHolder) viewHolder).titleTextView.setText(article.title);
        ((ViewHolder) viewHolder).descriptionTextView.setText(article.description.replaceAll("\\n", ""));
    }

    // ViewHolder内部でIDと関連づけ
    private static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iconImageView;
        public TextView userNameTextView;
        public TextView dateTextView;
        public TextView titleTextView;
        public TextView descriptionTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            iconImageView = (ImageView) itemView.findViewById(R.id.item_article_imageview_icon);
            userNameTextView = (TextView) itemView.findViewById(R.id.item_article_textview_user);
            dateTextView = (TextView) itemView.findViewById(R.id.item_article_textview_date);
            titleTextView = (TextView) itemView.findViewById(R.id.item_article_textview_title);
            descriptionTextView = (TextView) itemView.findViewById(R.id.item_article_textview_description);
        }
    }

}
