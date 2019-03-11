package com.oracle.auto.testapp.tests.web.backup.poc.steps;

import org.jbehave.core.annotations.*;

/**
 * Created by grcao on 9/27/2017.
 */
public class Test {
    @Then("test para: $para")
    public void test1(String para){
        int i=1;

        System.out.println("test1: " + i + para);
        }
}
