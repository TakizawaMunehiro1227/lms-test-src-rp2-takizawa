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
 *
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
		WebDriverWait wait =
				new WebDriverWait(webDriver, Duration.ofSeconds(5));

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

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {

		ReportPage reportPage = new ReportPage(webDriver);

		// 「ようこそ○○さん」をクリック
		reportPage.clickWelcome();

		// ユーザー詳細画面に遷移するまで待機
		WebDriverWait wait =
				new WebDriverWait(webDriver, Duration.ofSeconds(5));

		wait.until(
				ExpectedConditions.urlContains(
						"/lms/user/detail"));

		// URLチェック
		String currentUrl = webDriver.getCurrentUrl();

		assertTrue(currentUrl.contains("/lms/user/detail"));

		// エビデンス取得
		getEvidence(new Object() {});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当週報の「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {

		ReportPage reportPage = new ReportPage(webDriver);

		// 週報【デモ】の「修正する」ボタンをクリック
		reportPage.clickReportUpdate();

		// レポート登録画面に遷移するまで待機
		WebDriverWait wait =
				new WebDriverWait(webDriver, Duration.ofSeconds(5));

		wait.until(
				ExpectedConditions.urlContains(
						"/lms/report/regist"));

		// URLチェック
		String currentUrl = webDriver.getCurrentUrl();

		assertTrue(currentUrl.contains("/lms/report/regist"));

		// エビデンス取得
		getEvidence(new Object() {});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 週報内容を修正して「提出する」ボタンを押下")
	void test05() {

		ReportPage reportPage = new ReportPage(webDriver);

		// 目標の達成度を修正
		reportPage.inputAchievement("5");

		// 所感を修正
		reportPage.inputImpression("週報の内容を修正しました。");

		// 一週間の振り返りを修正
		reportPage.inputWeeklyReview("一週間の振り返りを修正しました。");

		// 「提出する」ボタンをクリック
		reportPage.clickReportRegist();

		// 画面遷移を待機
		WebDriverWait wait =
				new WebDriverWait(webDriver, Duration.ofSeconds(5));

		wait.until(
				ExpectedConditions.not(
						ExpectedConditions.urlContains(
								"/lms/report/regist")));

		// エビデンス取得
		getEvidence(new Object() {});
	}
}
