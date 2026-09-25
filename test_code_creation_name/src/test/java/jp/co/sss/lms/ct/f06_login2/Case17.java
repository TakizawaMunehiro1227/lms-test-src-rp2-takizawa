package jp.co.sss.lms.ct.f06_login2;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.Assert.*;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import jp.co.sss.lms.ct.page.LoginPage;

/**
 * 結合テスト ログイン機能②
 * ケース17
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース17 受講生 初回ログイン 正常系")
public class Case17 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {

		// トップページURLでアクセス
		goTo("http://localhost:8080/lms");

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに初期登録された未ログインの受講生ユーザーでログイン")
	void test02() throws Exception {

		LoginPage loginPage = new LoginPage(webDriver);

		// 未ログインの受講生ユーザーでログイン
		loginPage.login("StudentAA01", "StudentAA01");
		
		// Chromeのパスワード警告を閉じる
		loginPage.closeChromePasswordWarning();
		

		// 遷移するまで待機
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

		wait.until(
				ExpectedConditions.urlToBe(
						"http://localhost:8080/lms/user/agreeSecurity"));

		// URLチェック
		String currentUrl = webDriver.getCurrentUrl();

		assertEquals(
				"http://localhost:8080/lms/user/agreeSecurity",
				currentUrl);

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 「同意します」チェックボックスにチェックをせず「次へ」ボタンを押下")
	void test03() {

	    LoginPage loginPage = new LoginPage(webDriver);
	    
	    loginPage.clickSecurityCheck();

	    loginPage.clickNext();
	    
	    WebDriverWait wait =
	            new WebDriverWait(webDriver, Duration.ofSeconds(5));

	    wait.until(
	            ExpectedConditions.urlToBe(
	                    "http://localhost:8080/lms/password/changePassword"
	            )
	    );

	    String currentUrl = webDriver.getCurrentUrl();

	    assertEquals(
	            "http://localhost:8080/lms/password/changePassword",
	            currentUrl
	    );


	    getEvidence(new Object() {});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 変更パスワードを入力し「変更」ボタン押下")
	void test04() {
		
		LoginPage loginPage = new LoginPage(webDriver);
		
	    String newPassword = "StudentAA02";

	    loginPage.inputChangePassword(
	            "StudentAA01",
	            newPassword,
	            newPassword);

	    loginPage.clickNext();
	    loginPage.clickUpdateButton();
	    
		// コース詳細画面に遷移するまで待機
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

		wait.until(
				ExpectedConditions.urlToBe(
						"http://localhost:8080/lms/course/detail"));

		// URLチェック
		String currentUrl = webDriver.getCurrentUrl();

		assertEquals(
				"http://localhost:8080/lms/course/detail",
				currentUrl);
	    
	    // エビデンス取得
	    getEvidence(new Object() {});

	}

}
