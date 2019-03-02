package com.gmself.studio.mg.basemodule.base.ui.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    private AdapterView.OnItemClickListener mListener;
    private GestureDetector mGestureDetector;
    private View childView;
    private RecyclerView touchView;

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        touchView = rv;
        childView = rv.findChildViewUnder(e.getX(), e.getY());
        mGestureDetector.onTouchEvent(e);
        return false;
    }
 
    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
 
    }
 
    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
 
    }
}