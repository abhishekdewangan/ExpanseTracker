package com.money.expencetracker.ui.behaviour;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.money.expencetracker.R;

/**
 * Created by abhishek on 13/01/18.
 */

public class CommonItemDecorator extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = view.getResources().getDimensionPixelSize(R.dimen.margin8dp);
        }
        outRect.bottom = view.getResources().getDimensionPixelSize(R.dimen.margin8dp);
        outRect.left = view.getResources().getDimensionPixelSize(R.dimen.margin8dp);
        outRect.right = view.getResources().getDimensionPixelSize(R.dimen.margin8dp);
    }
}
