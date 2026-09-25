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
 * ケース16
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース16 受講生 初回ログイン 変更パスワード未入力")
public class Case16 {

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
	@DisplayName("テスト04 パスワードを未入力で「変更」ボタン押下")
	void test04() {
		
		LoginPage loginPage = new LoginPage(webDriver);
		
		loginPage.clickNext(); // パスワード変更画面の「変更」
		
		loginPage.clickUpdateButton(); // 確認モーダルの「変更」
		
		assertEquals(
			    "現在のパスワードは必須です。",
			    loginPage.getCurrentPasswordError()
			);

			assertEquals(
			    "確認パスワードは必須です。",
			    loginPage.getPasswordConfirmError()
			);
			
			getEvidence(new Object() {});
	}
	@Test
	@Order(5)
	@DisplayName("テスト05 20文字を超える変更パスワードを入力し「変更」ボタン押下")
	void test05() {

	    LoginPage loginPage = new LoginPage(webDriver);

	    // 21文字
	    String newPassword = "Abcdefghij1234567890X";

	    loginPage.inputChangePassword(
	            "StudentAA01",
	            newPassword,
	            newPassword);

	    // 「変更」ボタン押下
	    loginPage.clickNext();

	    // 確認モーダルの「変更」
	    loginPage.clickUpdateButton();

	    // エラー確認
	    assertEquals(
	        "パスワードの長さが最大値(20)を超えています。",
	        loginPage.getPasswordError(0)
	    );

	    getEvidence(new Object() {});
	}
	
	@Test
	@Order(6)
	@DisplayName("テスト06 ポリシーに合わない変更パスワードを入力し「変更」ボタン押下")
	void test06() {

	    LoginPage loginPage = new LoginPage(webDriver);

	    // 8文字だが、大文字・数字なし
	    String newPassword = "abcdefgh";

	    loginPage.inputChangePassword(
	            "StudentAA01",
	            newPassword,
	            newPassword);

	    loginPage.clickNext();
	    loginPage.clickUpdateButton();

	    assertEquals(
	    	    "「パスワード」には半角英数字のみ使用可能です。また、半角英大文字、半角英小文字、数字を含めた8～20文字を入力してください。",
	    	    loginPage.getPasswordError(0)
	    	);

	    getEvidence(new Object() {});
	}
	
	@Test
	@Order(7)
	@DisplayName("テスト07 一致しない確認パスワードを入力し「変更」ボタン押下")
	void test07() {

	    LoginPage loginPage = new LoginPage(webDriver);

	    loginPage.inputChangePassword(
	            "StudentAA01",
	            "Abcd1234",
	            "Abcd1235");

	    loginPage.clickNext();
	    loginPage.clickUpdateButton();

	    // エラーメッセージ確認
	    assertEquals(
	            "パスワードと確認パスワードが一致しません。",
	            loginPage.getPasswordError(0));

	    getEvidence(new Object() {});
	}


}
