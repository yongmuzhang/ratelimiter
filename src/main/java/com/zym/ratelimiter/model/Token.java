package com.zym.ratelimiter.model;

/**
 * Created by zym on 17/1/24.
 */
public class Token {
    private String name;

    public Token(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
