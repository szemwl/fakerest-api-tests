package ui.steps;

import org.openqa.selenium.WebDriver;
import ui.page.CartPage;
import ui.page.ProductsPage;

public class ProductSteps {

    private final ProductsPage productsPage;
    private final CartPage cartPage;

    public ProductSteps(WebDriver driver) {
        this.productsPage = new ProductsPage(driver);
        this.cartPage = new CartPage(driver);
    }

    public ProductSteps addProductToCart(String productId) {
        productsPage.addProductToCart(productId);
        return this;
    }

    public ProductSteps openCart() {
        productsPage.openCart();
        return this;
    }

    public ProductSteps removeProductFromCart(String productId) {
        cartPage.removeProduct(productId);
        return this;
    }

    public boolean isRemoveButtonVisible(String productId) {
        return productsPage.isRemoveButtonVisible(productId);
    }

    public boolean isAddToCartButtonVisible(String productId) {
        return productsPage.isAddToCartButtonVisible(productId);
    }

    public int getProductsPageCartCounter() {
        return productsPage.getCartCounter();
    }

    public int getCartPageCartCounter() {
        return cartPage.getCartCounter();
    }

    public int getCartItemsCount() {
        return cartPage.getCartItemsCount();
    }
}
