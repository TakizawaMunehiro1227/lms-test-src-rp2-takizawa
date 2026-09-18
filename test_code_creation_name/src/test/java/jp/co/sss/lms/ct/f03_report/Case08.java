package jp.co.sss.lms.ct.f03_report;

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
import jp.co.sss.lms.ct.page.ReportPage;

/**
 * 結合テスト レポート機能
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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

		goTo("http://localhost:8080/lms");

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {

		LoginPage loginPage = new LoginPage(webDriver);

		// 初回ログイン済みの受講生ユーザーでログイン
		loginPage.login("StudentAA01", "StudentAA02");

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

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {

		ReportPage reportPage = new ReportPage(webDriver);

		// 未提出の研修日の詳細ボタンをクリック
		reportPage.clickDetail();

		// セクション詳細画面に遷移するまで待機
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

		wait.until(
				ExpectedConditions.urlToBe(
						"http://localhost:8080/lms/section/detail"));

		// URLチェック
		String currentUrl = webDriver.getCurrentUrl();

		assertEquals(
				"http://localhost:8080/lms/section/detail",
				currentUrl);

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {

		ReportPage reportPage = new ReportPage(webDriver);

		// 日報【デモ】を提出するボタンをクリック
		reportPage.clickReport();

		// レポート登録画面に遷移するまで待機
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

		wait.until(
				ExpectedConditions.urlToBe(
						"http://localhost:8080/lms/report/regist"));

		// URLチェック
		String currentUrl = webDriver.getCurrentUrl();

		assertEquals(
				"http://localhost:8080/lms/report/regist",
				currentUrl);

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {

		ReportPage reportPage = new ReportPage(webDriver);

		// 日報内容を入力
		reportPage.inputReport("今日は３問演習課題を解きました。");

		// 提出するボタンをクリック
		reportPage.clickReportRegist();

		// セクション詳細画面に遷移するまで待機
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

		wait.until(
				ExpectedConditions.urlContains(
						"http://localhost:8080/lms/section/detail?sectionId="));

		// URLチェック
		String currentUrl = webDriver.getCurrentUrl();

		assertTrue(
				currentUrl.startsWith(
						"http://localhost:8080/lms/section/detail?sectionId="));

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
		// TODO ここに追加
		ReportPage reportPage = new ReportPage(webDriver);
		
		reportPage.clickWelcome();
		
		// ユーザー詳細画面に遷移するまで待機
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

		wait.until(
				ExpectedConditions.urlContains(
						"http://localhost:8080/lms/user/detail"));

		// URLチェック
		String currentUrl = webDriver.getCurrentUrl();

		assertTrue(
				currentUrl.startsWith(
						"http://localhost:8080/lms/user/detail"));

		getEvidence(new Object() {
		});

	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {

	    ReportPage reportPage = new ReportPage(webDriver);

	    // 該当レポートの詳細ボタンをクリック
	    reportPage.clickReportDetail();

	    // レポート詳細画面に遷移するまで待機
	    WebDriverWait wait =
	            new WebDriverWait(webDriver, Duration.ofSeconds(5));

	    wait.until(
	            ExpectedConditions.urlContains(
	                    "http://localhost:8080/lms/report/detail"));

	    // URLチェック
	    String currentUrl = webDriver.getCurrentUrl();

	    assertTrue(
	            currentUrl.startsWith(
	                    "http://localhost:8080/lms/report/detail"));

	    getEvidence(new Object() {
	    });
	}

}
