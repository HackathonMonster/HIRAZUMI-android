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
public class ArticleAdapter extends RecyclerView.Adapter {

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
        return new ViewHolder(mInflator.inflate(R.layout.item_article_adapter, viewGroup, false));
    }

    // Viewにデータを設定する
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Article article = getItem(position);
        ((ViewHolder) viewHolder).leftLayout.setBackgroundColor(Color.parseColor(article.color));
        Picasso.with(mContext).load(article.icon).transform(new OvalTransformation()).into(((ViewHolder) viewHolder).iconImageView);
        ((ViewHolder) viewHolder).dateTextView.setText(article.date);
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
    public Article getItem(int position) {
        //return mContents.get(position);
        //TODO 後で正式なデータに変更する
        return new Article("1", "2105-03-08", " iOSアプリのプロビジョニング周りを図にしてみる", "https://avatars.githubusercontent.com/u/5677723?", "#58BB09", "Qiita");
    }

    /**
     * データのセット
     * @param contents
     */
    public void setItems(List<Article> contents) {
        this.mContents = contents;
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
