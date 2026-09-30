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

		goTo("http://localhost:8080/lms");

		getEvidence(new Object() {});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {

		LoginPage loginPage = new LoginPage(webDriver);
		ReportPage reportPage = new ReportPage(webDriver);

		// Case07と同じ受講生ユーザーでログイン
		loginPage.login("StudentAA01", "StudentAA02");

		// 提出済みの研修日が表示されるまで待機
		assertTrue(reportPage.isSubmittedDetailDisplayed());

		// URLチェック
		assertEquals(
				"http://localhost:8080/lms/course/detail",
				webDriver.getCurrentUrl()
		);

		getEvidence(new Object() {});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 提出済みの研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {

		ReportPage reportPage = new ReportPage(webDriver);

		// 提出済みの研修日の「詳細」ボタンをクリック
		reportPage.clickSubmittedDetail();

		// 「提出済み週報【デモ】を確認する」が表示されることを確認
		assertTrue(reportPage.isSubmittedWeeklyReportDisplayed());

		// URLチェック
		assertEquals(
				"http://localhost:8080/lms/section/detail",
				webDriver.getCurrentUrl()
		);

		getEvidence(new Object() {});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出済み週報【デモ】を確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {

		ReportPage reportPage = new ReportPage(webDriver);

		// 提出済み週報を確認
		reportPage.clickSubmittedWeeklyReport();

		// 週報編集画面が表示されるまで待機
		assertTrue(reportPage.isWeeklyReportEditDisplayed());

		// URLチェック
		assertEquals(
				"http://localhost:8080/lms/report/regist",
				webDriver.getCurrentUrl()
		);

		getEvidence(new Object() {});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 週報内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {

		ReportPage reportPage = new ReportPage(webDriver);

		// 修正前のエビデンス
		getEvidence(new Object() {}, "01");

		// 所感を修正
		reportPage.inputImpression(
				"今週の学習内容を振り返り、理解を深めることができました。"
		);

		// 一週間の振り返りを修正
		reportPage.inputWeeklyReview(
				"一週間を通して計画的に学習を進めることができました。"
		);
		// 修正後のエビデンス
		getEvidence(new Object() {}, "02");

		// 「提出する」ボタンをクリック
		reportPage.clickReportRegist();

		// 提出済み週報が表示されるまで待機
		assertTrue(reportPage.isSubmittedWeeklyReportDisplayed());

		// セクション詳細画面に遷移したことを確認
		assertEquals(
				"セクション詳細 | LMS",
				webDriver.getTitle()
		);

		getEvidence(new Object() {}, "03");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {

		ReportPage reportPage = new ReportPage(webDriver);

		// 「ようこそ○○さん」をクリック
		reportPage.clickWelcome();

		// 提出済みレポートの「詳細」が表示されるまで待機
		assertTrue(reportPage.isReportDetailButtonDisplayed());

		// ユーザー詳細画面であることを確認
		assertEquals(
				"ユーザー詳細",
				webDriver.getTitle()
		);

		getEvidence(new Object() {});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映されることを確認")
	void test07() {

		ReportPage reportPage = new ReportPage(webDriver);

		// 週報の「詳細」をクリック
		reportPage.clickWeeklyReportDetail();

		// レポート詳細画面であることを確認
		assertEquals(
				"レポート詳細 | LMS",
				webDriver.getTitle()
		);

		// 修正した内容を取得
		String impression =
				reportPage.getReportDetailImpression();

		String weeklyReview =
				reportPage.getReportDetailWeeklyReview();

		// 修正した所感が反映されていることを確認
		assertEquals(
				"今週の学習内容を振り返り、理解を深めることができました。",
				impression
		);

		// 修正した一週間の振り返りが反映されていることを確認
		assertEquals(
				"一週間を通して計画的に学習を進めることができました。",
				weeklyReview
		);

		getEvidence(new Object() {});
	}
}
