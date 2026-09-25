package jp.co.sss.lms.ct.page;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
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
	
	// 利用規約画面のエラーメッセージ
	@FindBy(css = "div.error")
	private WebElement securityErrorMessage;
	
	@FindBy(css = "button[type='submit'].btn.btn-primary")
	private WebElement nextButton;
	
	@FindBy(css = "input[type='checkbox'][name='securityFlg']")
	private WebElement securityCheck;
	
	@FindBy(id = "upd-btn")
	private WebElement updateButton;
	
	// 現在のパスワード
	@FindBy(id = "currentPassword")
	private WebElement currentPassword;

	// 新しいパスワード
	@FindBy(id = "password")
	private WebElement newPassword;

	// 確認パスワード
	@FindBy(id = "passwordConfirm")
	private WebElement passwordConfirm;
	
	// 現在のパスワードエラー
	private final By currentPasswordError =
	        By.xpath("//input[@id='currentPassword']/following-sibling::ul[1]//span[contains(@class,'error')]");

	// 新しいパスワードエラー
	private final By passwordErrors =
	        By.xpath("//input[@id='password']/following-sibling::ul[1]//span[contains(@class,'error')]");

	// 確認パスワードエラー
	private final By passwordConfirmError =
	        By.xpath("//input[@id='passwordConfirm']/following-sibling::ul[1]//span[contains(@class,'error')]");
	
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
	
	public void clickNext() {
		nextButton.click();
	}
	
	public boolean isSecurityErrorMessageDisplayed() {
	    wait.until(ExpectedConditions.visibilityOf(securityErrorMessage));
	    return securityErrorMessage.isDisplayed();
	}

	public String getSecurityErrorMessage() {
	    wait.until(ExpectedConditions.visibilityOf(securityErrorMessage));
	    return securityErrorMessage.getText();
	}
	
	public void clickSecurityCheck() {
	    wait.until(ExpectedConditions.elementToBeClickable(securityCheck));
	    securityCheck.click();
	}
	
	public void clickUpdateButton() {

	    // 変更前に表示されているエラーを取得
	    List<WebElement> oldErrors = webDriver.findElements(passwordErrors);

	    wait.until(ExpectedConditions.elementToBeClickable(updateButton));
	    updateButton.click();

	    // 前のエラーが存在していた場合、
	    // その要素が古くなる（画面が更新される）まで待機
	    if (!oldErrors.isEmpty()) {
	        wait.until(ExpectedConditions.stalenessOf(oldErrors.get(0)));
	    }
	}
	
	public void closeChromePasswordWarning() throws Exception {

	    Thread.sleep(2000);

	    Robot robot = new Robot();
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	}
	
	public void inputChangePassword(
	        String current,
	        String newPass,
	        String confirm) {

	    currentPassword.clear();
	    currentPassword.sendKeys(current);

	    newPassword.clear();
	    newPassword.sendKeys(newPass);

	    passwordConfirm.clear();
	    passwordConfirm.sendKeys(confirm);
	}
	
	public String getCurrentPasswordError() {

	    return wait.until(driver -> {
	        try {
	            WebElement error = driver.findElement(currentPasswordError);
	            String text = error.getText().trim();

	            return text.isEmpty() ? null : text;

	        } catch (org.openqa.selenium.StaleElementReferenceException e) {
	            return null;
	        }
	    });
	}

	public String getPasswordError(int index) {

	    return wait.until(driver -> {
	        try {
	            List<WebElement> errors = driver.findElements(passwordErrors);

	            if (errors.size() <= index) {
	                return null;
	            }

	            String text = errors.get(index).getText().trim();

	            return text.isEmpty() ? null : text;

	        } catch (org.openqa.selenium.StaleElementReferenceException e) {
	            return null;
	        }
	    });
	}

	public String getPasswordConfirmError() {

	    return wait.until(driver -> {
	        try {
	            WebElement error = driver.findElement(passwordConfirmError);
	            String text = error.getText().trim();

	            return text.isEmpty() ? null : text;

	        } catch (org.openqa.selenium.StaleElementReferenceException e) {
	            return null;
	        }
	    });
	}
}