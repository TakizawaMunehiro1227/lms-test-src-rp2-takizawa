package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.Assert.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト ログイン機能①
 * ケース02
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース02 受講生 ログイン 認証失敗")
public class Case02 {
	
	

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
		// TODO ここに追加
		goTo("http://localhost:8080/lms");
		
		getEvidence(new Object() {});
		
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test02() {
		// TODO ここに追加
		
		//ID:1を入力（DB未登録ユーザー）
		WebElement LoginId = webDriver.findElement(By.id("loginId"));
		LoginId.clear();
		LoginId.sendKeys("1");
		
		//PassWord:StudentAA01を入力
		WebElement Loginpass = webDriver.findElement(By.id("password"));
		Loginpass.clear();
		Loginpass.sendKeys("StudentAA01");
		
		//ログインボタンをクリック
		WebElement loginButton = webDriver.findElement(By.cssSelector("input[value='ログイン']"));
		
		loginButton.click();
		
		// エラーメッセージが表示されるまで最大5秒待機
		visibilityTimeout(By.cssSelector("form > span.help-inline.error"),5);
		
		//エラーメッセージを取得して比較
		WebElement errorMessage = webDriver.findElement(By.cssSelector("span.help-inline.error"));
		
		assertTrue(errorMessage.isDisplayed());
		
		assertEquals("* ログインに失敗しました。",errorMessage.getText());
		
		//URLチェック
				String currentUrl=webDriver.getCurrentUrl();
				assertTrue(currentUrl.startsWith("http://localhost:8080/lms/login"));
		
		//エビデンスを取得
		getEvidence(new Object() {});
		
	}

}
