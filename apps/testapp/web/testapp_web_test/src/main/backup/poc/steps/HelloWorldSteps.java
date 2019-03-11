package com.oracle.auto.testapp.tests.web.backup.poc.steps;

import com.oracle.auto.testapp.web.pages.GoogleHomePage;
import com.oracle.auto.testapp.web.pages.TestAppPageFactory;
import com.oracle.auto.web.utility.WebAssert;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

/**
 * Created by zhous5 on 2017/6/13.
 */
public class HelloWorldSteps {
    private TestAppPageFactory factory;

    public HelloWorldSteps() {
        factory = TestAppPageFactory.getInstance();
    }

    @Given("poc given step")
    public void f() {
        System.out.println("f()");
        GoogleHomePage googleHomePage = factory.resetBrowserAsLoginPage("googlechrome", GoogleHomePage.class, true);
        googleHomePage.getInputField().setValue("HelloWorld");
    }

    @When("poc when step")
    public void f1() {
        System.out.println("f1()");
        WebAssert.assertTrue("Exception", false);
    }

    @Then("poc then step")
    public void f2() {
        System.out.println("f2()");
//        GoogleHomePage googleHomePage = factory.pageAs(GoogleHomePage.class);
//        googleHomePage.getInputField().setValue("Hello World!");
    }
}
