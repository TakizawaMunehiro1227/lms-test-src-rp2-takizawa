package jp.co.sss.lms.ct.page;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

	private WebDriver webDriver;
	private WebDriverWait wait;

	@FindBy(id = "loginId")
	private WebElement loginId;

	@FindBy(id = "password")
	private WebElement password;

	@FindBy(css = "input[value='ログイン']")
	private WebElement loginButton;

	@FindBy(css = "form > span.help-inline.error")
	private WebElement errorMessage;

	public LoginPage(WebDriver webDriver) {
		this.webDriver = webDriver;
		this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

		PageFactory.initElements(webDriver, this);
	}

	public void inputLoginId(String id) {
		wait.until(ExpectedConditions.visibilityOf(loginId));
		loginId.clear();
		loginId.sendKeys(id);
	}

	public void inputPassword(String pass) {
		password.clear();
		password.sendKeys(pass);
	}

	public void clickLogin() {
		loginButton.click();
	}

	public void login(String id, String pass) {
		inputLoginId(id);
		inputPassword(pass);
		clickLogin();
	}

	public boolean isErrorMessageDisplayed() {
		wait.until(ExpectedConditions.visibilityOf(errorMessage));
		return errorMessage.isDisplayed();
	}

	public String getErrorMessage() {
		wait.until(ExpectedConditions.visibilityOf(errorMessage));
		return errorMessage.getText();
	}
}