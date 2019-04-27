package com.gmself.mg.a7zip;

/**
 * Created by guomeng on 4/27.
 */

public enum Zip7Suffix {
    PNG(".png"),


    ;
    String value;

    Zip7Suffix(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
