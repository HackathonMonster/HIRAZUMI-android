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
    private List<Article> mContents = new ArrayList<>();

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
        ((ViewHolder) viewHolder).leftLayout.setBackgroundColor(Color.parseColor(article.color));
        Picasso.with(mContext).load(article.icon).transform(new OvalTransformation()).into(((ViewHolder) viewHolder).iconImageView);
        ((ViewHolder) viewHolder).dateTextView.setText(article.date);
    }

    // ViewHolder内部でIDと関連づけ
    private static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout leftLayout;
        public ImageView iconImageView;
        public TextView dateTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            leftLayout = (LinearLayout) itemView.findViewById(R.id.item_article_layout_left);
            iconImageView = (ImageView) itemView.findViewById(R.id.item_article_imageview_icon);
            dateTextView = (TextView) itemView.findViewById(R.id.item_article_textview_date);
        }
    }

}
