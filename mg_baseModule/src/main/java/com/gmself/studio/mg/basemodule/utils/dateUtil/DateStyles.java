package com.gmself.studio.mg.basemodule.utils.dateUtil;

public enum DateStyles {
	/**
	 * yyyy-MM
	 */
	YYYY_MM("yyyy-MM"),

    YYYY_MM_DD("yyyy-MM-dd"),

    YYYY_MM_DD_CN("yyyy年MM月dd日"),

    YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),
	;

    private String value;

    DateStyles(String value) {
        this.value = value;
    }  
      
    public String getValue() {
        return value;  
    }  

}
