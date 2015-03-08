package com.zeroone_creative.basicapplication.model.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shunhosaka on 2015/03/08.
 */
public class Author {
    public List<Book> books = new ArrayList<>();
    public String id;
    public String name;
    public String description;
    public String url;
}
