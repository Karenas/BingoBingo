package com.gmself.stidio.gm.superflyerpage.entity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.gmself.stidio.gm.superflyerpage.ui.customView.FreeItemView;
import com.gmself.stidio.gm.superflyerpage.ui.listener.FlyerViewOnClickListener;
import com.gmself.studio.mg.basemodule.animation.animator_roaming.AnimatorCarrier;

/**
 *
 * Created by guomeng on 3/7.
 *
 * 双链表结构
 *
 */

public class Flyer {

    private int id;

    private FreeItemView view;

    private double money;
    private String info;
    private int imageResID;

    private float x = 0; //当前x坐标（相对于viewgroup）
    private float y = 0; //当前y坐标（相对于viewgroup）

    private int width = 100;
    private int height = 100;

    private AnimatorCarrier CircleMovement;
    private AnimatorCarrier ProminentMovement;

    private boolean isFirst = false;
    private boolean isLast = false;

    private Flyer previousFlyer; //上一个
    private Flyer nextFlyer; //下一个


    public void addToGroup(ViewGroup viewGroup){
//        view.measure(width, height);
        viewGroup.addView(view);
    }

    public void initView(Context mContext) {
        view = new FreeItemView(mContext, width, height);
        view.loadImageResID(imageResID);
    }

    public FreeItemView getView() {
        return view;
    }

    public void moveTo(float x, float y){
        this.x = x;
        this.y = y;
        view.setTranslationX(x);
        view.setTranslationY(y);
    }

    public void reSize(){
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
    }


    public void setVisibility(int visibility){
        view.setVisibility(visibility);
    }

    public void setOnClickListener(final FlyerViewOnClickListener listener){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(Flyer.this, v);
            }
        });
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getImageResID() {
        return imageResID;
    }

    public void setImageResID(int imageResID) {
        this.imageResID = imageResID;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Flyer getPreviousFlyer() {
        return previousFlyer;
    }

    public void setPreviousFlyer(Flyer previousFlyer) {
        this.previousFlyer = previousFlyer;
    }

    public Flyer getNextFlyer() {
        return nextFlyer;
    }

    public void setNextFlyer(Flyer nextFlyer) {
        this.nextFlyer = nextFlyer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AnimatorCarrier getCircleMovement() {
        return CircleMovement;
    }

    public void setCircleMovement(AnimatorCarrier circleMovement) {
        CircleMovement = circleMovement;
    }

    public AnimatorCarrier getProminentMovement() {
        return ProminentMovement;
    }

    public void setProminentMovement(AnimatorCarrier prominentMovement) {
        ProminentMovement = prominentMovement;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }
}
