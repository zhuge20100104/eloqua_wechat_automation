package com.oracle.auto.testapp.tests.web.backup.poc.steps;

import com.oracle.auto.testapp.model.UserData;
import com.oracle.auto.web.jbehave_ext.test.Trader;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

/**
 * Created by zhous5 on 2017/6/14.
 */
public class ParametersSteps {
    @Given("welcome $name")
    public void welcome(String name) {
        System.out.println("Welcome: " + name);
    }

    @Given("welcome1 $name")
    public void welcome(Trader trader) {
        System.out.println(trader.getAge());
    }

    @When("its description is $description")
    public void getDesc(String description) {
        System.out.println("its description is: " + description);
    }

    @Then("thanks everyone")
    public void end() {
        System.out.println("thanks everyone");
    }

    @Then("their boss is $userData")
    public void verifyBoss(UserData userData) {
        System.out.println(userData.firstName);
    }
}
