package com.oracle.auto.web.jbehave_ext.test;

/**
 * Created by zhous5 on 2017/6/15.
 */
public class Trader {
    private String name;
    private int age;

    Trader(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
