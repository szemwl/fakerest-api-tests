package ui.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ui.driver.Browser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@Feature("Login")
public class LoginTest extends BaseTest {

    @ParameterizedTest
    @EnumSource(Browser.class)
    @Story("Login")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Успешная авторизация пользователя")
    @Description("Проверка логина с валидными данными")
    void loginTest(Browser browser) {
        initDriver(browser);

        loginSteps
                .openLoginPage()
                .login("standard_user", "secret_sauce");

        assertTrue(driver.getCurrentUrl().contains("inventory"));
    }

    @ParameterizedTest
    @EnumSource(value = Browser.class, names = {"CHROME_HEADLESS"})
    void shouldFailHeadlessTest(Browser browser) {
        initDriver();

        loginSteps
                .openLoginPage()
                .login("standard_user", "secret_sauce");

        fail();
    }
}
