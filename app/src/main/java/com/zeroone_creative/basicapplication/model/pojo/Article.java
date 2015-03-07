package com.zeroone_creative.basicapplication.model.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shunhosaka on 2015/03/07.
 */
public class Article {
    //List
    public String id;
    public String date;
    public String title;
    public String icon;
    public String color;
    public String source;
    //Detail
    public String source_url;
    public String bodyText;
    public List<Book> books = new ArrayList<>();
    public List<Category> categories = new ArrayList<>();

    public Article(String id, String date, String title, String icon, String color, String source) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.icon = icon;
        this.color = color;
        this.source = source;
    }

    public Article(String id, String date, String title, String icon, String color, String source, String source_url, String bodyText, List<Book> books, List<Category> categories) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.icon = icon;
        this.color = color;
        this.source = source;
        this.source_url = source_url;
        this.bodyText = bodyText;
        this.books = books;
        this.categories = categories;
    }
}
