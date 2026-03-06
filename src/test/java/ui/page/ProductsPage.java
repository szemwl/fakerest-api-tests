package ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductsPage extends BasePage {

    private final By cartBadge = By.className("shopping_cart_badge");
    private final By cartLink = By.className("shopping_cart_link");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public void addProductToCart(String productId) {
        click(addToCartButton(productId));
    }

    public void removeProduct(String productId) {
        click(removeButton(productId));
    }

    private By addToCartButton(String productId) {
        return By.id("add-to-cart-" + productId);
    }

    private By removeButton(String productId) {
        return By.id("remove-" + productId);
    }

    public boolean isRemoveButtonVisible(String productId) {
        return !findAll(removeButton(productId)).isEmpty();
    }

    public boolean isAddToCartButtonVisible(String productId) {
        return !findAll(addToCartButton(productId)).isEmpty();
    }

    public int getCartCounter() {
        if (findAll(cartBadge).isEmpty()) {
            return 0;
        }
        return Integer.parseInt(find(cartBadge).getText());
    }

    public void openCart() {
        click(cartLink);
    }
}
