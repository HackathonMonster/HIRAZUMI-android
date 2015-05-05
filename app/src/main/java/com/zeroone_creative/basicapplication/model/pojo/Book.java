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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((isbN10 == null) ? 0 : isbN10.hashCode());
        //result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (object instanceof Book) {
            if(name.equals(((Book) object).name)) {
                return true;
            }
        }
        return false;
    }

}
