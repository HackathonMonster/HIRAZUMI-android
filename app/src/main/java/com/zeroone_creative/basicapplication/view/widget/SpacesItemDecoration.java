package com.zeroone_creative.basicapplication.view.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by shunhosaka on 2015/03/07.
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int[] mSpaces = new int[4];

    public SpacesItemDecoration(int space) {
        this(space, space, space, space);
    }

    public SpacesItemDecoration(int topSpace, int leftSpace, int bottomSpace, int rightSpace) {
        this.mSpaces[0] = topSpace;
        this.mSpaces[1] = leftSpace;
        this.mSpaces[2] = bottomSpace;
        this.mSpaces[3] = rightSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = mSpaces[0];
        outRect.right = mSpaces[1];
        outRect.bottom = mSpaces[2];
        outRect.top = mSpaces[3];
    }
}
