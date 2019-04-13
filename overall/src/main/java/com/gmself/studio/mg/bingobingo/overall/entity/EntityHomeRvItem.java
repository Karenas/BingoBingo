package com.gmself.studio.mg.bingobingo.overall.entity;

/**
 * Created by guomeng on 3/2.
 */

public class EntityHomeRvItem {

    public enum TYPE{
        WEATHER,
        VIDEO,
        NORMAL
        ;
    }

    private String name;
    private int backGroundColor;
    private int height;
    private TYPE holderType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBackGroundColor() {
        return backGroundColor;
    }

    public void setBackGroundColor(int backGroundColor) {
        this.backGroundColor = backGroundColor;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public TYPE getHolderType() {
        return holderType;
    }

    public void setHolderType(TYPE holderType) {
        this.holderType = holderType;
    }
}
