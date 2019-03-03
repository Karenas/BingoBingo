package com.gmself.studio.mg.bingobingo.overall.ui.customView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.gmself.studio.mg.basemodule.log_tool.Logger;
import com.gmself.studio.mg.bingobingo.overall.R;

/**
 * Created by guomeng on 3/3.
 */

public class RecyclerView_home extends RecyclerView{

    public RecyclerView_home(Context context) {
        super(context);
    }

    public RecyclerView_home(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerView_home(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                Logger.log(Logger.Type.DEBUG, " recyclerView   ACTION_DOWN");
//                onMove(e.getX(), e.getY());
                break;
            case MotionEvent.ACTION_UP:
                Logger.log(Logger.Type.DEBUG, " recyclerView   ACTION_UP");
                resetChildTurnTo();
                break;
            case MotionEvent.ACTION_MOVE:
                Logger.log(Logger.Type.DEBUG, " recyclerView   ACTION_MOVE",
                "x = "+e.getX()+"   y = "+ e.getY()
                );
                onMove(e.getX(), e.getY());
                break;
            case MotionEvent.ACTION_CANCEL:
                Logger.log(Logger.Type.DEBUG, " recyclerView   ACTION_CANCEL");
                break;
        }

        return super.onTouchEvent(e);
    }

    private void onMove(float x, float y){
        LinearLayout_homeRv rvLL = null;
        for (int i = 0; i < getChildCount(); i++){
            View view = getChildAt(i);
            if (null != view){
                rvLL = view.findViewById(R.id.overall_home_rv_item_ll);
                if (null != rvLL){
                    rvLL.turnToTouch(x, y, getRight(), getBottom());
                }
            }
        }
    }

    private void resetChildTurnTo(){
        LinearLayout_homeRv rvLL = null;
        for (int i = 0; i < getChildCount(); i++){
            View view = getChildAt(i);
            if (null != view){
                rvLL = view.findViewById(R.id.overall_home_rv_item_ll);
                if (null != rvLL){
                    rvLL.resetTurnTo();
                }
            }
        }
    }

}
