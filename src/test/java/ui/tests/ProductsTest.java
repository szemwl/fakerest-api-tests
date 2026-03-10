package ui.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ui.driver.Browser;
import ui.driver.DriverFactory;
import ui.model.SortType;
import ui.steps.CheckoutSteps;
import ui.steps.LoginSteps;
import ui.steps.ProductsSteps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductsTest extends BaseTest {

    private static final int EMPTY_CART = 0;
    private static final int ONE_PRODUCT = 1;
    private static final String USERNAME = "standard_user";
    private static final String PASSWORD = "secret_sauce";
    private static final Random RANDOM = new Random();

    @Test
    public void shouldAddAndRemoveProductFromCart() {
        driver = DriverFactory.createDriver(Browser.CHROME);

        LoginSteps loginSteps = new LoginSteps(driver);
        ProductsSteps productsSteps = new ProductsSteps(driver);

        String productId = "sauce-labs-backpack";

        loginSteps
                .openLoginPage()
                .login(USERNAME, PASSWORD);

        productsSteps
                .addProductToCart(productId);

        assertFalse(productsSteps.isAddToCartButtonVisible(productId));
        assertTrue(productsSteps.isRemoveButtonVisible(productId));
        assertEquals(ONE_PRODUCT, productsSteps.getProductsPageCartCounter());

        productsSteps
                .openCart()
                .removeProductFromCart(productId);

        assertEquals(EMPTY_CART, productsSteps.getCartBadgeCount());
        assertEquals(EMPTY_CART, productsSteps.getCartItemsCount());
    }

    @Test
    public void shouldAddThreeRandomProductsAndRemoveOneFromCart() {
        driver = DriverFactory.createDriver(Browser.CHROME);

        LoginSteps loginSteps = new LoginSteps(driver);
        ProductsSteps productsSteps = new ProductsSteps(driver);
        int expectedCartItems = 0;

        loginSteps
                .openLoginPage()
                .login(USERNAME, PASSWORD);

        List<String> productIdList = productsSteps.getAllProductIds();

        Collections.shuffle(productIdList, RANDOM);

        List<String> randomProducts = productIdList.subList(0, 3);

        for (String randomProduct : randomProducts) {
            productsSteps.addProductToCart(randomProduct);
            assertEquals(++expectedCartItems, productsSteps.getProductsPageCartCounter());
        }

        productsSteps.openCart();

        assertEquals(expectedCartItems, productsSteps.getCartItemsCount());

        for (String productId : randomProducts) {
            assertTrue(productsSteps.isProductNameVisibleInCart(productId));
            assertTrue(productsSteps.isProductPriceVisibleInCart(productId));
            assertTrue(productsSteps.isRemoveButtonVisibleInCart(productId));
        }

        String removedProduct = randomProducts.remove(
                RANDOM.nextInt(randomProducts.size())
        );

        productsSteps.removeProductFromCart(removedProduct);

        assertEquals(expectedCartItems - 1, productsSteps.getCartItemsCount());

        productsSteps.continueShopping();

        assertTrue(productsSteps.isAddToCartButtonVisible(removedProduct));
    }

    @Test
    public void shouldCompleteCheckoutWithCorrectProducts() {
        driver = DriverFactory.createDriver(Browser.CHROME);

        LoginSteps loginSteps = new LoginSteps(driver);
        ProductsSteps productsSteps = new ProductsSteps(driver);
        CheckoutSteps checkoutSteps = new CheckoutSteps(driver);

        loginSteps
                .openLoginPage()
                .login(USERNAME, PASSWORD);

        List<String> productIdList = productsSteps.getAllProductIds();

        Collections.shuffle(productIdList, RANDOM);

        List<String> randomProducts = productIdList.subList(0, 2);

        for (String randomProduct : randomProducts) {
            productsSteps.addProductToCart(randomProduct);
        }

        productsSteps.openCart();

        for (String randomProduct : randomProducts) {
            assertTrue(productsSteps.isProductNameVisibleInCart(randomProduct));
        }

        productsSteps.checkout();

        checkoutSteps
                .fillCustomerInfo("Joe", "Doe", "123")
                .continueBtn()
                .finishBtn()
                .backToProductsBtn();

        assertEquals(EMPTY_CART, productsSteps.getCartItemsCount());
    }

    @ParameterizedTest
    @EnumSource(SortType.class)
    public void shouldSortProducts(SortType sortType) {
        driver = DriverFactory.createDriver(Browser.CHROME);

        LoginSteps loginSteps = new LoginSteps(driver);
        ProductsSteps productsSteps = new ProductsSteps(driver);

        loginSteps
                .openLoginPage()
                .login(USERNAME, PASSWORD);

        productsSteps.selectSort(sortType.getValue());

        if (sortType.getValue().equals("az")) {
            List<String> actual = productsSteps.getProductNames();
            List<String> expected = new ArrayList<>(actual);
            Collections.sort(expected);

            assertEquals(expected, actual);
        }

        if (sortType.getValue().equals("za")) {
            List<String> actual = productsSteps.getProductNames();
            List<String> expected = new ArrayList<>(actual);
            expected.sort(Collections.reverseOrder());

            assertEquals(expected, actual);
        }

        if (sortType.getValue().equals("lohi")) {
            List<Double> actual = productsSteps.getProductPrices();
            List<Double> expected = new ArrayList<>(actual);
            Collections.sort(expected);

            assertEquals(expected, actual);
        }

        if (sortType.getValue().equals("hilo")) {
            List<Double> actual = productsSteps.getProductPrices();
            List<Double> expected = new ArrayList<>(actual);
            expected.sort(Collections.reverseOrder());

            assertEquals(expected, actual);
        }
    }
}
