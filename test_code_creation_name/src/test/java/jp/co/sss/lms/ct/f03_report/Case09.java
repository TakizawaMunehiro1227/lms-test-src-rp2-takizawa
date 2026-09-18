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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import jp.co.sss.lms.ct.page.LoginPage;
import jp.co.sss.lms.ct.page.ReportPage;

/**
 * 結合テスト レポート機能
 * ケース09
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

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
	    getEvidence(new Object() {});
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
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {

	    ReportPage reportPage = new ReportPage(webDriver);

	    // 「ようこそ○○さん」をクリック
	    reportPage.clickWelcome();

	    WebDriverWait wait =
	            new WebDriverWait(webDriver, Duration.ofSeconds(5));

	    wait.until(
	            ExpectedConditions.urlContains("/lms/user/detail"));

	    String currentUrl = webDriver.getCurrentUrl();

	    assertTrue(currentUrl.contains("/lms/user/detail"));

	    getEvidence(new Object() {});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {

	    ReportPage reportPage = new ReportPage(webDriver);

	    // 修正するボタンをクリック
	    reportPage.clickReportUpdate();

	    WebDriverWait wait =
	            new WebDriverWait(webDriver, Duration.ofSeconds(5));

	    wait.until(
	            ExpectedConditions.urlContains("/lms/report/regist"));

	    String currentUrl = webDriver.getCurrentUrl();

	    assertTrue(currentUrl.contains("/lms/report/regist"));

	    getEvidence(new Object() {});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() {

	    ReportPage reportPage = new ReportPage(webDriver);

	    // 学習項目を未入力にする
	    reportPage.clearLearningItem();

	    // 提出する
	    reportPage.clickReportRegist();

	    // エビデンス取得
	    getEvidence(new Object() {});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {

	    ReportPage reportPage = new ReportPage(webDriver);

	    // 学習項目は正常値に戻す
	    reportPage.inputLearningItem("Java");

	    // 理解度を未選択
	    reportPage.clearIntelligibility();

	    // 提出する
	    reportPage.clickReportRegist();

	    getEvidence(new Object() {});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {

	    ReportPage reportPage = new ReportPage(webDriver);

	    // 入力処理
	    // ～省略～

	    // 提出する
	    reportPage.clickReportRegist();

	    // 画面の読み込み完了を待つ
	    WebDriverWait wait =
	            new WebDriverWait(webDriver, Duration.ofSeconds(5));

	    wait.until(driver ->
	        ((JavascriptExecutor) driver)
	            .executeScript("return document.readyState")
	            .equals("complete")
	    );

	    // エビデンス取得
	    getEvidence(new Object() {});
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {

	    ReportPage reportPage = new ReportPage(webDriver);

	    // 目標の達成度に範囲外の値を入力（1～10のため11）
	    reportPage.inputAchievement("11");

	    // 他の項目は正常値を入力
	    reportPage.inputImpression("週報のサンプルです。");
	    reportPage.inputWeeklyReview("一週間の振り返りです。");

	    // 提出するボタンをクリック
	    reportPage.clickReportRegist();

	    // エビデンス取得
	    getEvidence(new Object() {});
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() {

	    ReportPage reportPage = new ReportPage(webDriver);

	    // 目標の達成度を未入力
	    reportPage.inputAchievement("");

	    // 所感を未入力
	    reportPage.inputImpression("");

	    // 一週間の振り返りは正常値
	    reportPage.inputWeeklyReview("一週間の振り返りです。");

	    // 提出するボタンをクリック
	    reportPage.clickReportRegist();

	    // エビデンス取得
	    getEvidence(new Object() {});
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() {

	    ReportPage reportPage = new ReportPage(webDriver);

	    // 目標の達成度は正常値
	    reportPage.inputAchievement("5");

	    // 2001文字の文字列を作成
	    String overText = "あ".repeat(2001);

	    // 所感を2000文字超にする
	    reportPage.inputImpression(overText);

	    // 一週間の振り返りを2000文字超にする
	    reportPage.inputWeeklyReview(overText);

	    // 提出するボタンをクリック
	    reportPage.clickReportRegist();

	    // エビデンス取得
	    getEvidence(new Object() {});
	}

}
