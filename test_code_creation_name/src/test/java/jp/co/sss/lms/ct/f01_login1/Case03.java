package jp.co.sss.lms.ct.f01_login1;

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
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト ログイン機能①
 * ケース03
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース03 受講生 ログイン 正常系")
public class Case03 {

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
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		
		//IDを入力（初回ログイン済み）
		WebElement LoginId = webDriver.findElement(By.id("loginId"));
		LoginId.clear();
		LoginId.sendKeys("StudentAA01");
		
		//PassWord(初回ログイン済み）を入力
		WebElement Loginpass = webDriver.findElement(By.id("password"));
		Loginpass.clear();
		Loginpass.sendKeys("StudentAA02");
		
		//ログインボタンをクリック
		WebElement loginButton = webDriver.findElement(By.cssSelector("input[value='ログイン']"));
		
		loginButton.click();
		
		//画面遷移にいくまで５秒待機
		WebDriverWait wait =
		        new WebDriverWait(webDriver, Duration.ofSeconds(5));

		wait.until(
		        ExpectedConditions.urlToBe(
		                "http://localhost:8080/lms/course/detail"
		        )
		);
		
		//URLをチェック
		String currentUrl=webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/course/detail",currentUrl);
		
		//エビデンスを取得
				getEvidence(new Object() {});
	}
	

}
