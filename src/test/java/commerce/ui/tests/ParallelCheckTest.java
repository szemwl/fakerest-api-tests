package commerce.ui.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static commerce.ui.driver.Browser.CHROME;
import static commerce.ui.driver.Browser.FIREFOX;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Feature("ParallelCheck")
@Tag("ui")
@DisplayName("Тесты запуска в параллельном режиме")
public class ParallelCheckTest extends BaseTest {

    @Test
    @Story("Login")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Успешная авторизация пользователя через Chrome браузер")
    @Description("Проверка логина с валидными данными через Chrome браузер")
    void loginParallelChromeTest() {
        initDriver(CHROME);

        loginSteps
                .openLoginPage()
                .login("standard_user", "secret_sauce");

        assertTrue(driver.getCurrentUrl().contains("inventory"));
    }

    @Test
    @Story("Login")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Успешная авторизация пользователя через Firefox браузер")
    @Description("Проверка логина с валидными данными через Firefox браузер")
    void loginParallelFirefoxTest() {
        initDriver(FIREFOX);

        loginSteps
                .openLoginPage()
                .login("standard_user", "secret_sauce");

        assertTrue(driver.getCurrentUrl().contains("inventory"));
    }
}
