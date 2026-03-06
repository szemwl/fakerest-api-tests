package ui.tests;

import org.junit.jupiter.api.Test;
import ui.driver.Browser;
import ui.driver.DriverFactory;
import ui.steps.LoginSteps;
import ui.steps.ProductSteps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductsTest extends BaseTest {

    @Test
    public void shouldAddAndRemoveProductFromCart() {
        driver = DriverFactory.createDriver(Browser.CHROME);

        LoginSteps loginSteps = new LoginSteps(driver);
        ProductSteps productSteps = new ProductSteps(driver);

        String productId = "sauce-labs-backpack";

        loginSteps
                .openLoginPage()
                .login("standard_user", "secret_sauce");

        productSteps
                .addProductToCart(productId);

        assertTrue(productSteps.isRemoveButtonVisible(productId));
        assertEquals(1, productSteps.getProductsPageCartCounter());

        productSteps
                .openCart()
                .removeProductFromCart(productId);

        assertEquals(0, productSteps.getCartPageCartCounter());
        assertEquals(0, productSteps.getCartItemsCount());
    }
}
