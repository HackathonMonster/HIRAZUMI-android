package com.zeroone_creative.basicapplication.model.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shunhosaka on 2015/03/07.
 */
public class Book {
    //List
    public String id;
    public String name;
    public String imageUrl;
    public String release;
    public String description;
    //Detail
    public String size;
    public int yen;
    public int page;
    public String launguage;
    public int level;
    public String isbN10;
    public String isbN13;
    public List<Article> articles = new ArrayList<>();
    public List<Author> authors = new ArrayList<>();
    public List<Category>  categories = new ArrayList<>();
    public Publisher publisher;
    public List<ShopUrl> shopUrls = new ArrayList<>();

    public Book(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }
}
