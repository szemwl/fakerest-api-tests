package tests.ui;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import ui.driver.Browser;
import ui.driver.DriverFactory;
import ui.step.LoginSteps;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest {

    @ParameterizedTest
    @EnumSource(Browser.class)
    public void loginTest(Browser browser) {

        WebDriver driver = DriverFactory.createDriver(browser);

        LoginSteps loginSteps = new LoginSteps(driver);

        loginSteps
                .openLoginPage()
                .login("standard_user", "secret_sauce");

        assertTrue(driver.getCurrentUrl().endsWith("inventory.html"));
        driver.quit();
    }
}
