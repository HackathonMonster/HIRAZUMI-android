package com.zeroone_creative.basicapplication.model.enumerate;

import com.android.volley.Request;

public enum NetworkTasks {
    GetCategory(1, Request.Method.GET),
    GetBooks(2, Request.Method.GET)
    ;
    public int id;
    //Request
    public int method;

    private NetworkTasks(int id, int method) {
        this.id = id;
        this.method = method;
    }
}
