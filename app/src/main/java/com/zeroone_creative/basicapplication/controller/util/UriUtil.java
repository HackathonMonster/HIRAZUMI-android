package com.zeroone_creative.basicapplication.controller.util;

import android.net.Uri;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 * Created by shunhosaka on 2014/12/06.
 */
public class UriUtil {

    static private String baseUrl = "hirazumi.azurewebsites.net";

    private static  Uri.Builder getBaseUri() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http");
        builder.encodedAuthority(baseUrl);
        return builder;
    }

    public static String getCategoryUri(List<NameValuePair> params) {
        Uri.Builder builder = getBaseUri();
        builder.path("/api/categories");
        for (NameValuePair param : params) {
            builder.appendQueryParameter(param.getName(), param.getValue());
        }
        return builder.build().toString();
    }

    public static String getBookUri(int top, int skip) {
        Uri.Builder builder = getBaseUri();
        builder.path("/api/books");
        builder.appendQueryParameter("$top", String.valueOf(top));
        builder.appendQueryParameter("$skip", String.valueOf(skip));
        return builder.build().toString();
    }

    public static String getBookUriAddCategoryFilter(int top, int skip, String name) {
        Uri.Builder builder = getBaseUri();
        builder.path("/api/books");
        builder.appendQueryParameter("$top", String.valueOf(top));
        builder.appendQueryParameter("$skip", String.valueOf(skip));
        builder.appendQueryParameter("$filter", "substringof('" + name + "', Name) eq true");
        return builder.build().toString();
    }

    public static String getBookUriSearch(int top, int skip, String name) {
        Uri.Builder builder = getBaseUri();
        builder.path("/api/books");
        builder.appendQueryParameter("$top", String.valueOf(top));
        builder.appendQueryParameter("$skip", String.valueOf(skip));
        builder.appendQueryParameter("$filter", "substringof('" + name + "', Name) eq true");
        return builder.build().toString();
    }

    public static String getBookByISBN(int top, int skip, List<String> isbns) {
        Uri.Builder builder = getBaseUri();
        builder.path("/api/books");
        builder.appendQueryParameter("$top", String.valueOf(top));
        builder.appendQueryParameter("$skip", String.valueOf(skip));
        StringBuilder filterBuilder = new StringBuilder();
        if (isbns.size() > 0) {
            filterBuilder.append("IsbN10 eq ");
            filterBuilder.append(isbns.get(0));
            isbns.remove(0);
            for (String isbn : isbns) {
                filterBuilder.append(" or ");
                filterBuilder.append("IsbN10 eq ");
                filterBuilder.append(isbn);
            }
        }
        builder.appendQueryParameter("$filter", filterBuilder.toString());
        return builder.build().toString();
    }
}
