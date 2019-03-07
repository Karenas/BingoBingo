package com.gmself.stidio.gm.superflyerpage.ui.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by guomeng on 3/7.
 */

public class FreeItemView extends View {

    public FreeItemView(Context context) {
        super(context);
    }

    public FreeItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FreeItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color. BLACK);
    }
}
