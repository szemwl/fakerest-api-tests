package ui.steps;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import ui.model.Product;
import ui.page.CheckoutPage;

import java.util.List;

public class CheckoutSteps {
    private final CheckoutPage checkoutPage;

    public CheckoutSteps(WebDriver driver) {
        this.checkoutPage = new CheckoutPage(driver);
    }

    @Step("")
    public CheckoutSteps fillCustomerInfo(String name, String surname, String zip) {
        checkoutPage.fillCustomerInfo(name, surname, zip);
        return this;
    }

    @Step("")
    public CheckoutSteps continueBtn() {
        checkoutPage.continueBtn();
        return this;
    }

    @Step("")
    public CheckoutSteps finishBtn() {
        checkoutPage.finishBtn();
        return this;
    }

    @Step("")
    public CheckoutSteps backToProductsBtn() {
        checkoutPage.backToProductsBtn();
        return this;
    }

    @Step("")
    public boolean isErrorContainerDisplayed() {
        return checkoutPage.isErrorContainerDisplayed();
    }

    @Step("")
    public String getErrorContainerText() {
        return checkoutPage.getErrorContainerText();
    }

    @Step("")
    public boolean isCheckoutOverviewPageOpened() {
        return checkoutPage.isCheckoutOverviewPageOpened();
    }

    @Step("")
    public boolean isCheckoutCompletePageOpened() {
        return checkoutPage.isCheckoutCompletePageOpened();
    }

    @Step("")
    public String getCompleteMessageText() {
        return checkoutPage.getCompleteMessageText();
    }

    @Step("")
    public List<Product> getOverviewProducts() {
        return checkoutPage.getOverviewProducts();
    }
}
