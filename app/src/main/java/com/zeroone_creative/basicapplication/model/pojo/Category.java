package com.zeroone_creative.basicapplication.model.pojo;

/**
 * Book　Categoryオブジェクト
 * Created by shunhosaka on 2015/03/07.
 */
public class Category {
    public String id;
    public String name;
    public String color;
    public String image_url;
    public String latest_version;

    public Category(String id, String name, String color, String image_url, String latest_version) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.image_url = image_url;
        this.latest_version = latest_version;
    }
}
