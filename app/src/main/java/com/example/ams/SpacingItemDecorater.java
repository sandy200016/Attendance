package com.example.ams;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpacingItemDecorater extends RecyclerView.ItemDecoration {
    private final int horizontalSpacingHeight;

    public SpacingItemDecorater(int horizontalSpacingHeight1) {
        this.horizontalSpacingHeight = horizontalSpacingHeight1;

    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.right=horizontalSpacingHeight;
        outRect.left=horizontalSpacingHeight;

    }
}
