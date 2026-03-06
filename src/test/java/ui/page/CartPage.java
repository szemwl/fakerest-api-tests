package ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    private final By cartItems = By.className("cart_item");
    private final By cartBadge = By.className("shopping_cart_badge");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    private By removeButton(String productId) {
        return By.id("remove-" + productId);
    }

    public void removeProduct(String productId) {
        click(removeButton(productId));
    }

    public int getCartItemsCount() {
        return findAll(cartItems).size();
    }

    public int getCartCounter() {
        if (findAll(cartBadge).isEmpty()) {
            return 0;
        }
        return Integer.parseInt(find(cartBadge).getText());
    }
}
