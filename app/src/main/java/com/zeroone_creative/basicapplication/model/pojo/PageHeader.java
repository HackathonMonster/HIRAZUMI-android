package com.zeroone_creative.basicapplication.model.pojo;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by shunhosaka on 2015/04/10.
 */
public class PageHeader {
    public String name;
    public String icon;
    public String background;
    public String image;


    public boolean isPhotoGet = false;
    public Bitmap mBitmap;
    public Target mGetPhotoTarget = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            mBitmap = bitmap;
            isPhotoGet = true;
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            isPhotoGet = false;
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };
}
