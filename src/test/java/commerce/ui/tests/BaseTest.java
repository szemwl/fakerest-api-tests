package commerce.ui.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.WebDriver;
import commerce.ui.driver.Browser;
import commerce.ui.driver.DriverFactory;
import commerce.ui.steps.CheckoutSteps;
import commerce.ui.steps.LoginSteps;
import commerce.ui.steps.ProductsSteps;
import commerce.ui.utils.AllureScreenshotExtension;

public class BaseTest {

    protected WebDriver driver;

    @RegisterExtension
    protected AllureScreenshotExtension screenshotExtension =
            new AllureScreenshotExtension();

    protected LoginSteps loginSteps;
    protected ProductsSteps productsSteps;
    protected CheckoutSteps checkoutSteps;

    protected void initDriver() {
        initDriver(Browser.CHROME);
    }

    protected void initDriver(Browser browser) {
        driver = DriverFactory.createDriver(browser);
        screenshotExtension.setDriver(driver);

        loginSteps = new LoginSteps(driver);
        productsSteps = new ProductsSteps(driver);
        checkoutSteps = new CheckoutSteps(driver);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
