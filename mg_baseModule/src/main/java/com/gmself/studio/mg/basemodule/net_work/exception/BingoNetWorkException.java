package com.gmself.studio.mg.basemodule.net_work.exception;

/**
 * Created by guomeng on 3/24.
 */

public class BingoNetWorkException extends Exception {
    private BingoNetWorkExceptionType type;

    public BingoNetWorkException(BingoNetWorkExceptionType exceptionType) {
        super();

        this.type = exceptionType;
    }

    public BingoNetWorkException(BingoNetWorkExceptionType exceptionType, String msg) {
        super(msg);

        this.type = exceptionType;
    }

    public BingoNetWorkExceptionType getType() {
        return type;
    }
}
