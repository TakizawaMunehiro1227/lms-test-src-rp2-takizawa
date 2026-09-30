package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.Assert.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import jp.co.sss.lms.ct.page.LoginPage;
import jp.co.sss.lms.ct.page.ReportPage;

/**
 * 結合テスト レポート機能
 * ケース07
 *
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

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

		getEvidence(new Object() {});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {

		LoginPage loginPage = new LoginPage(webDriver);
		ReportPage reportPage = new ReportPage(webDriver);

		// 初回ログイン済みの受講生ユーザーでログイン
		loginPage.login("StudentAA01", "StudentAA02");

		// コース詳細画面が表示されるまで待機
		assertTrue(reportPage.isDetailDisplayed());

		// URLチェック
		String currentUrl = webDriver.getCurrentUrl();

		assertEquals(
				"http://localhost:8080/lms/course/detail",
				currentUrl
		);

		getEvidence(new Object() {});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {

		ReportPage reportPage = new ReportPage(webDriver);

		// 未提出の研修日の詳細ボタンをクリック
		reportPage.clickDetail();

		// URLチェック
		String currentUrl = webDriver.getCurrentUrl();

		assertEquals(
				"http://localhost:8080/lms/section/detail",
				currentUrl
		);

		// 未提出の日に遷移できていることを確認
		assertTrue(reportPage.isReportButtonDisplayed());

		getEvidence(new Object() {});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {

		ReportPage reportPage = new ReportPage(webDriver);

		// 日報【デモ】を提出するボタンをクリック
		reportPage.clickReport();

		// URLチェック
		String currentUrl = webDriver.getCurrentUrl();

		assertEquals(
				"http://localhost:8080/lms/report/regist",
				currentUrl
		);

		getEvidence(new Object() {});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下しレポートが登録されることを確認")
	void test05() {

		ReportPage reportPage = new ReportPage(webDriver);

		// 日報内容を入力
		reportPage.inputReport("今日は３問演習課題を解きました。");

		// 提出するボタンをクリック
		reportPage.clickReportRegist();

		// 「提出済み日報【デモ】を確認する」が表示され、
		// 日報が提出済みになったことを確認
		assertTrue(reportPage.isSubmittedDailyReportDisplayed());

		// URLチェック
		String currentUrl = webDriver.getCurrentUrl();

		assertTrue(
				currentUrl.startsWith(
						"http://localhost:8080/lms/section/detail?sectionId="
				)
		);

		getEvidence(new Object() {});
	}
}
