package com.zeroone_creative.basicapplication.model.enumerate;

import com.zeroone_creative.basicapplication.R;

/**
 * Created by shunhosaka on 2015/03/07.
 */
public enum PageType {
    New(1, R.string.tab_new, R.color.tab_new, R.drawable.ic_launcher),
    Topic(2, R.string.tab_topic, R.color.tab_topic, R.drawable.ic_launcher),
    Category(3, R.string.tab_category, R.color.tab_category, R.drawable.ic_launcher),
    Search(4, R.string.tab_search, R.color.tab_search, R.drawable.ic_launcher),;

    public int id;
    public int name;
    public int color;
    public int image;

    PageType(int id, int name, int color, int image) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.image = image;
    }

    public static PageType getTypeById(int id) {
        id = Math.max(0, id);
        id = Math.min(id, PageType.values().length-1);
        return PageType.values()[id];
    }
}