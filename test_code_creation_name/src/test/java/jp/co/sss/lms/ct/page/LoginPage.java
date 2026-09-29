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

/**
 * ログイン画面、利用規約画面、パスワード変更画面の操作を行うPage Objectクラス
 */
public class LoginPage {

	/** WebDriver */
	private WebDriver webDriver;

	/** 明示的待機 */
	private WebDriverWait wait;

	/** ログインID入力欄 */
	@FindBy(id = "loginId")
	private WebElement loginId;

	/** ログイン画面のパスワード入力欄 */
	@FindBy(id = "password")
	private WebElement password;

	/** ログインボタン */
	@FindBy(css = "input[value='ログイン']")
	private WebElement loginButton;

	/** ログイン認証失敗時のエラーメッセージ */
	@FindBy(css = "form > span.help-inline.error")
	private WebElement errorMessage;

	/** 利用規約画面のエラーメッセージ */
	@FindBy(css = "div.error")
	private WebElement securityErrorMessage;

	/** 利用規約画面の「次へ」ボタン */
	@FindBy(css = "button[type='submit'].btn.btn-primary")
	private WebElement nextButton;

	/** 利用規約同意チェックボックス */
	@FindBy(css = "input[type='checkbox'][name='securityFlg']")
	private WebElement securityCheck;

	/** パスワード変更画面の「変更」ボタン */
	@FindBy(id = "upd-btn")
	private WebElement updateButton;

	/** 現在のパスワード入力欄 */
	@FindBy(id = "currentPassword")
	private WebElement currentPassword;

	/** 新しいパスワード入力欄 */
	@FindBy(id = "password")
	private WebElement newPassword;

	/** 確認用パスワード入力欄 */
	@FindBy(id = "passwordConfirm")
	private WebElement passwordConfirm;

	/** 現在のパスワード入力欄に表示されるエラー */
	private final By currentPasswordError =
			By.xpath("//input[@id='currentPassword']/following-sibling::ul[1]//span[contains(@class,'error')]");

	/** 新しいパスワード入力欄に表示されるエラー */
	private final By passwordErrors =
			By.xpath("//input[@id='password']/following-sibling::ul[1]//span[contains(@class,'error')]");

	/** 確認用パスワード入力欄に表示されるエラー */
	private final By passwordConfirmError =
			By.xpath("//input[@id='passwordConfirm']/following-sibling::ul[1]//span[contains(@class,'error')]");

	/**
	 * LoginPageを初期化する。
	 *
	 * @param webDriver WebDriver
	 */
	public LoginPage(WebDriver webDriver) {
		this.webDriver = webDriver;
		this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

		PageFactory.initElements(webDriver, this);
	}

	/**
	 * ログインIDを入力する。
	 *
	 * @param id ログインID
	 */
	public void inputLoginId(String id) {
		wait.until(ExpectedConditions.visibilityOf(loginId));
		loginId.clear();
		loginId.sendKeys(id);
	}

	/**
	 * ログイン画面のパスワードを入力する。
	 *
	 * @param pass パスワード
	 */
	public void inputPassword(String pass) {
		password.clear();
		password.sendKeys(pass);
	}

	/**
	 * ログインボタンを押下する。
	 */
	public void clickLogin() {
		loginButton.click();
	}

	/**
	 * ログインIDとパスワードを入力し、ログインする。
	 *
	 * @param id   ログインID
	 * @param pass パスワード
	 */
	public void login(String id, String pass) {
		inputLoginId(id);
		inputPassword(pass);
		clickLogin();
	}

	/**
	 * ログイン認証失敗時のエラーメッセージが表示されているか確認する。
	 *
	 * @return 表示されている場合true
	 */
	public boolean isErrorMessageDisplayed() {
		wait.until(ExpectedConditions.visibilityOf(errorMessage));
		return errorMessage.isDisplayed();
	}

	/**
	 * ログイン認証失敗時のエラーメッセージを取得する。
	 *
	 * @return エラーメッセージ
	 */
	public String getErrorMessage() {
		wait.until(ExpectedConditions.visibilityOf(errorMessage));
		return errorMessage.getText();
	}

	/**
	 * 利用規約画面の「次へ」ボタンを押下する。
	 */
	public void clickNext() {
		nextButton.click();
	}

	/**
	 * 利用規約画面のエラーメッセージが表示されているか確認する。
	 *
	 * @return 表示されている場合true
	 */
	public boolean isSecurityErrorMessageDisplayed() {
		wait.until(ExpectedConditions.visibilityOf(securityErrorMessage));
		return securityErrorMessage.isDisplayed();
	}

	/**
	 * 利用規約画面のエラーメッセージを取得する。
	 *
	 * @return エラーメッセージ
	 */
	public String getSecurityErrorMessage() {
		wait.until(ExpectedConditions.visibilityOf(securityErrorMessage));
		return securityErrorMessage.getText();
	}

	/**
	 * 利用規約の同意チェックボックスをクリックする。
	 */
	public void clickSecurityCheck() {
		wait.until(ExpectedConditions.elementToBeClickable(securityCheck));
		securityCheck.click();
	}

	/**
	 * パスワード変更画面の「変更」ボタンを押下する。
	 *
	 * 既にエラーメッセージが表示されている場合は、
	 * 画面更新によって古い要素が破棄されるまで待機する。
	 */
	public void clickUpdateButton() {

		// 変更前に表示されているエラー要素を取得
		List<WebElement> oldErrors = webDriver.findElements(passwordErrors);

		// 「変更」ボタンが押下可能になるまで待機
		wait.until(ExpectedConditions.elementToBeClickable(updateButton));
		updateButton.click();

		// エラーが表示されていた場合は、
		// 画面更新によって古いエラー要素が無効になるまで待機
		if (!oldErrors.isEmpty()) {
			wait.until(ExpectedConditions.stalenessOf(oldErrors.get(0)));
		}
	}

	/**
	 * Chromeのパスワード警告をEnterキーで閉じる。
	 *
	 * Seleniumでは操作できないブラウザ側の警告に対して
	 * Robotを使用してキー操作を行う。
	 *
	 * @throws Exception Robot生成時などに例外が発生した場合
	 */
	public void closeChromePasswordWarning() throws Exception {

		// Chromeの警告が表示されるまで待機
		Thread.sleep(2000);

		// Enterキーを押下して警告を閉じる
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	/**
	 * パスワード変更画面の各入力欄に値を入力する。
	 *
	 * @param current 現在のパスワード
	 * @param newPass 新しいパスワード
	 * @param confirm 確認用パスワード
	 */
	public void inputChangePassword(
			String current,
			String newPass,
			String confirm) {

		// 現在のパスワードを入力
		currentPassword.clear();
		currentPassword.sendKeys(current);

		// 新しいパスワードを入力
		newPassword.clear();
		newPassword.sendKeys(newPass);

		// 確認用パスワードを入力
		passwordConfirm.clear();
		passwordConfirm.sendKeys(confirm);
	}

	/**
	 * 現在のパスワード入力欄のエラーメッセージを取得する。
	 *
	 * @return エラーメッセージ
	 */
	public String getCurrentPasswordError() {

		return wait.until(driver -> {
			try {
				WebElement error = driver.findElement(currentPasswordError);
				String text = error.getText().trim();

				return text.isEmpty() ? null : text;

			} catch (org.openqa.selenium.StaleElementReferenceException e) {

				// 画面更新によって要素が古くなった場合は再取得させる
				return null;
			}
		});
	}

	/**
	 * 新しいパスワード入力欄のエラーメッセージを取得する。
	 *
	 * 複数の入力チェックエラーが表示される可能性があるため、
	 * indexで取得するメッセージを指定する。
	 *
	 * @param index 取得するエラーメッセージの位置
	 * @return エラーメッセージ
	 */
	public String getPasswordError(int index) {

		return wait.until(driver -> {
			try {
				List<WebElement> errors = driver.findElements(passwordErrors);

				// 指定された位置のエラーが存在しない場合
				if (errors.size() <= index) {
					return null;
				}

				String text = errors.get(index).getText().trim();

				return text.isEmpty() ? null : text;

			} catch (org.openqa.selenium.StaleElementReferenceException e) {

				// 画面更新によって要素が古くなった場合は再取得させる
				return null;
			}
		});
	}

	/**
	 * 確認用パスワード入力欄のエラーメッセージを取得する。
	 *
	 * @return エラーメッセージ
	 */
	public String getPasswordConfirmError() {

		return wait.until(driver -> {
			try {
				WebElement error = driver.findElement(passwordConfirmError);
				String text = error.getText().trim();

				return text.isEmpty() ? null : text;

			} catch (org.openqa.selenium.StaleElementReferenceException e) {

				// 画面更新によって要素が古くなった場合は再取得させる
				return null;
			}
		});
	}
}