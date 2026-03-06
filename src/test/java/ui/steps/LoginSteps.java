package ui.steps;

import org.openqa.selenium.WebDriver;
import ui.page.LoginPage;

public class LoginSteps {

    private final LoginPage loginPage;

    public LoginSteps(WebDriver driver) {
        this.loginPage = new LoginPage(driver);
    }

    public LoginSteps openLoginPage() {
        loginPage.open();
        return this;
    }

    public LoginSteps login(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
        return this;
    }
}
