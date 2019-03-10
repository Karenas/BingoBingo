package com.gmself.stidio.gm.superflyerpage.entity;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;

import com.gmself.stidio.gm.superflyerpage.ui.customView.FreeItemView;

/**
 *
 * Created by guomeng on 3/7.
 *
 * 双链表结构
 *
 */

public class Flyer {

    private FreeItemView view;

    private double money;
    private String info;
    private int imageResID;

    private float x = 0; //当前x坐标（相对于viewgroup）
    private float y = 0; //当前y坐标（相对于viewgroup）

    private int width = 60;
    private int height = 60;

    private Flyer previousFlyer; //上一个
    private Flyer nextFlyer; //下一个


    public void addToGroup(ViewGroup viewGroup){
//        view.measure(width, height);
        viewGroup.addView(view);
    }

    public void initView(Context mContext) {
        view = new FreeItemView(mContext, width, height);
//        view.setXY(x, y);
        view.setBackgroundColor(Color.BLACK);
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

}
