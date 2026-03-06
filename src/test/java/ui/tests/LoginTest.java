package ui.tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ui.driver.Browser;
import ui.driver.DriverFactory;
import ui.steps.LoginSteps;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest extends BaseTest {

    @ParameterizedTest
    @EnumSource(Browser.class)
    public void loginTest(Browser browser) {

        driver = DriverFactory.createDriver(browser);

        LoginSteps loginSteps = new LoginSteps(driver);

        loginSteps
                .openLoginPage()
                .login("standard_user", "secret_sauce");

        assertTrue(driver.getCurrentUrl().contains("inventory"));
    }
}
