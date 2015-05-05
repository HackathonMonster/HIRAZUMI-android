package com.zeroone_creative.basicapplication.model.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Book　Categoryオブジェクト
 * Created by shunhosaka on 2015/03/07.
 */
public class Category {

    public String id;
    public String name;
    public String color;
    public String imageUrl;
    public String latest_version;
    public List<Book> books = new ArrayList<>();

    public Category() {
    }

    public Category(String id, String name, String color, String imageUrl, String latest_version) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.imageUrl = imageUrl;
        this.latest_version = latest_version;
    }
}
