package jp.co.sss.lms.ct.f02_faq;

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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト よくある質問機能
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

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
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// TODO ここに追加
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
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

		wait.until(
				ExpectedConditions.urlToBe(
						"http://localhost:8080/lms/course/detail"));

		//URLをチェック
		String currentUrl = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/course/detail", currentUrl);
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// TODO ここに追加
		WebElement category = webDriver.findElement(By.linkText("機能"));
		category.click();

		webDriver.findElement(By.linkText("ヘルプ")).click();

		//画面遷移にいくまで５秒待機
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		wait.until(
				ExpectedConditions.urlToBe(
						"http://localhost:8080/lms/help"));

		//URLチェック
		String currentUrl = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/help", currentUrl);

		getEvidence(new Object() {
		});

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		// 現在のタブを保存
		String originalWindow = webDriver.getWindowHandle();

		webDriver.findElement(By.linkText("よくある質問")).click();

		//画面遷移にいくまで５秒待機
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		// 別タブが開くまで待つ
		wait.until(ExpectedConditions.numberOfWindowsToBe(2));

		// 新しく開いたタブへ切り替える
		for (String windowHandle : webDriver.getWindowHandles()) {

			if (!windowHandle.equals(originalWindow)) {
				webDriver.switchTo().window(windowHandle);
				break;
			}
		}

		// FAQ画面に遷移するまで待機
		wait.until(ExpectedConditions.urlToBe("http://localhost:8080/lms/faq"));

		//URLチェック
		String currentUrl = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/faq", currentUrl);

		getEvidence(new Object() {});
	}
	
	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {
		
		//研修関係のリンクをクリックする。
		WebElement category =webDriver.findElement(By.linkText("【研修関係】"));

		category.click();
		
		 // 検索結果が表示されるまで待つ
	    WebDriverWait wait =new WebDriverWait(webDriver, Duration.ofSeconds(5));
		
		// 検索結果確認①
	    WebElement result1 = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath("//*[contains(text(),'キャンセル料・途中退校について')]")
	            ));
	    
	 // 検索結果確認②
	    WebElement result2 = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath("//*[contains(text(),'研修の申し込みはどのようにすれば良いですか？')]")
	            ));

		assertTrue(result1.isDisplayed());
		assertTrue(result2.isDisplayed());
		getEvidence(new Object() {});
	}
	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {

	    WebDriverWait wait =
	            new WebDriverWait(webDriver, Duration.ofSeconds(5));

	    // 検索結果が2件表示されるまで待つ
	    wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
	            By.cssSelector("dl[id^='question-h']"), 1));

	    // 1件目
	    WebElement question1 = webDriver
	            .findElements(By.cssSelector("dl[id^='question-h']"))
	            .get(0);

	    WebElement answer1 =
	            question1.findElement(By.cssSelector("dd[id^='answer-h']"));

	    question1.click();

	    wait.until(ExpectedConditions.visibilityOf(answer1));

	    assertTrue(answer1.isDisplayed());


	    // 2件目
	    WebElement question2 = webDriver
	            .findElements(By.cssSelector("dl[id^='question-h']"))
	            .get(1);

	    WebElement answer2 =
	            question2.findElement(By.cssSelector("dd[id^='answer-h']"));

	    question2.click();

	    wait.until(ExpectedConditions.visibilityOf(answer2));

	    assertTrue(answer2.isDisplayed());

	 // 2つ目の回答までスクロール
	    JavascriptExecutor js = (JavascriptExecutor) webDriver;
	    js.executeScript("arguments[0].scrollIntoView({block:'center'});", answer2);

	    getEvidence(new Object() {});
	}
	

}
