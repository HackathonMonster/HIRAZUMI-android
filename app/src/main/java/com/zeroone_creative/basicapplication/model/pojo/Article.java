package com.zeroone_creative.basicapplication.model.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shunhosaka on 2015/03/07.
 */
public class Article {

    public Oid id;
    public String title;
    public String description;
    public String url;
    public Author author;
    public Date datePublished;
    public Date dateRegister;
    public List<String> isbn = new ArrayList<>();
    public int version;


    public class Date {
        public String date;
    }

    public class Oid {
        public String oid;
    }


}
