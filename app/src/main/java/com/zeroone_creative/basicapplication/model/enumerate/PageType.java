package com.zeroone_creative.basicapplication.model.enumerate;

import com.zeroone_creative.basicapplication.R;

/**
 * Created by shunhosaka on 2015/03/07.
 */
public enum PageType {
    New(1, R.string.tab_new, R.color.tab_new, R.drawable.img_main_header_new, R.drawable.img_tab_new),
    Topic(2, R.string.tab_topic, R.color.tab_topic, R.drawable.img_main_header_topics, R.drawable.img_tab_topic),
    Category(3, R.string.tab_category, R.color.tab_category, R.drawable.img_main_header_category, R.drawable.img_tab_category),
    Search(4, R.string.tab_search, R.color.tab_search, R.drawable.img_main_header_search, R.drawable.img_tab_search),;

    public int id;
    public int name;
    public int color;
    public int image;
    public int tab;

    PageType(int id, int name, int color, int image, int tab) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.image = image;
        this.tab = tab;
    }

    public static PageType getTypeById(int id) {
        id = Math.max(0, id);
        id = Math.min(id, PageType.values().length-1);
        return PageType.values()[id];
    }
}