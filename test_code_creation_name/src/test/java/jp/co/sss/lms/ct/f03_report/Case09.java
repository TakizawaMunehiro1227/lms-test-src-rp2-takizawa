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
 * ケース09
 *
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
		assertEquals(
				"http://localhost:8080/lms/course/detail",
				webDriver.getCurrentUrl()
		);

		getEvidence(new Object() {});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {

		ReportPage reportPage = new ReportPage(webDriver);

		// 「ようこそ○○さん」をクリック
		reportPage.clickWelcome();

		// 提出済みレポートの詳細ボタンが表示されるまで待機
		assertTrue(reportPage.isReportDetailButtonDisplayed());

		// URLチェック
		assertTrue(
				webDriver.getCurrentUrl().contains(
						"/lms/user/detail"
				)
		);

		getEvidence(new Object() {});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {

		ReportPage reportPage = new ReportPage(webDriver);

		// 週報【デモ】の「修正する」ボタンをクリック
		reportPage.clickReportUpdate();

		// レポート登録画面が表示されるまで待機
		assertTrue(reportPage.isWeeklyReportEditDisplayed());

		// URLチェック
		assertTrue(
				webDriver.getCurrentUrl().contains(
						"/lms/report/regist"
				)
		);

		getEvidence(new Object() {});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 学習項目が未入力の場合にエラーが表示される")
	void test05() {

		ReportPage reportPage = new ReportPage(webDriver);

		// 学習項目：未入力
		reportPage.clearLearningItem();

		// 理解度：正常値
		reportPage.selectIntelligibility("3");

		// 目標の達成度：正常値
		reportPage.inputAchievement("3");

		// 所感：正常値
		reportPage.inputImpression(
				"今週の学習内容を振り返りました。"
		);

		// 一週間の振り返り：正常値
		reportPage.inputWeeklyReview(
				"一週間を通して計画的に学習しました。"
		);

		getEvidence(new Object() {}, "01");

		// 提出するボタンをクリック
		reportPage.clickReportRegist();

		// 入力チェックエラーが表示されることを確認
		assertTrue(reportPage.isErrorInputDisplayed());

		// URLチェック
		assertEquals(
				"http://localhost:8080/lms/report/complete",
				webDriver.getCurrentUrl()
		);

		getEvidence(new Object() {}, "02");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 理解度が未選択の場合にエラーが表示される")
	void test06() {

		ReportPage reportPage = new ReportPage(webDriver);

		// 学習項目：正常値
		reportPage.inputLearningItem("Java");

		// 理解度：未選択
		reportPage.clearIntelligibility();

		// 目標の達成度：正常値
		reportPage.inputAchievement("3");

		// 所感：正常値
		reportPage.inputImpression(
				"今週の学習内容を振り返りました。"
		);

		// 一週間の振り返り：正常値
		reportPage.inputWeeklyReview(
				"一週間を通して計画的に学習しました。"
		);

		reportPage.clickReportRegist();
		
		// 入力チェックエラーが表示されることを確認
		assertTrue(reportPage.isErrorInputDisplayed());

		// URLチェック
		assertEquals(
				"http://localhost:8080/lms/report/complete",
				webDriver.getCurrentUrl()
		);

		getEvidence(new Object() {});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 目標の達成度が数値以外の場合にエラーが表示される")
	void test07() {

		ReportPage reportPage = new ReportPage(webDriver);

		// 学習項目：正常値
		reportPage.inputLearningItem("Java");

		// 理解度：正常値
		reportPage.selectIntelligibility("3");

		// 目標の達成度：数値以外
		reportPage.inputAchievement("abc");

		// 所感：正常値
		reportPage.inputImpression(
				"今週の学習内容を振り返りました。"
		);

		// 一週間の振り返り：正常値
		reportPage.inputWeeklyReview(
				"一週間を通して計画的に学習しました。"
		);

		reportPage.clickReportRegist();
		
		// 入力チェックエラーが表示されることを確認
		assertTrue(reportPage.isErrorInputDisplayed());

		// URLチェック
		assertEquals(
				"http://localhost:8080/lms/report/complete",
				webDriver.getCurrentUrl()
		);

		getEvidence(new Object() {});
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 目標の達成度が範囲外の場合にエラーが表示される")
	void test08() {

		ReportPage reportPage = new ReportPage(webDriver);

		// 学習項目：正常値
		reportPage.inputLearningItem("Java");

		// 理解度：正常値
		reportPage.selectIntelligibility("3");

		// 目標の達成度：範囲外
		reportPage.inputAchievement("11");

		// 所感：正常値
		reportPage.inputImpression(
				"今週の学習内容を振り返りました。"
		);

		// 一週間の振り返り：正常値
		reportPage.inputWeeklyReview(
				"一週間を通して計画的に学習しました。"
		);

		reportPage.clickReportRegist();
		
		// 入力チェックエラーが表示されることを確認
		assertTrue(reportPage.isErrorInputDisplayed());

		// URLチェック
		assertEquals(
				"http://localhost:8080/lms/report/complete",
				webDriver.getCurrentUrl()
		);

		getEvidence(new Object() {});
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 目標の達成度・所感が未入力の場合にエラーが表示される")
	void test09() {

		ReportPage reportPage = new ReportPage(webDriver);

		// 学習項目：正常値
		reportPage.inputLearningItem("Java");

		// 理解度：正常値
		reportPage.selectIntelligibility("3");

		// 目標の達成度：未入力
		reportPage.inputAchievement("");

		// 所感：未入力
		reportPage.inputImpression("");

		// 一週間の振り返り：正常値
		reportPage.inputWeeklyReview(
				"一週間を通して計画的に学習しました。"
		);

		// 提出するボタンをクリック
		reportPage.clickReportRegist();
		
		// 入力チェックエラーを確認
		assertTrue(reportPage.isErrorInputDisplayed());

		assertEquals(
				"http://localhost:8080/lms/report/complete",
				webDriver.getCurrentUrl()
		);


		getEvidence(new Object() {});
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 所感・一週間の振り返りが2000文字超の場合にエラーが表示される")
	void test10() {

		ReportPage reportPage = new ReportPage(webDriver);

		// 2001文字の文字列
		String overText = "あ".repeat(2001);

		// 学習項目：正常値
		reportPage.inputLearningItem("Java");

		// 理解度：正常値
		reportPage.selectIntelligibility("3");

		// 目標の達成度：正常値
		reportPage.inputAchievement("3");

		// 所感：2001文字
		reportPage.inputImpression(overText);

		// 一週間の振り返り：2001文字
		reportPage.inputWeeklyReview(overText);

		// 提出するボタンをクリック
		reportPage.clickReportRegist();
		
		// 入力チェックエラーを確認
		assertTrue(reportPage.isErrorInputDisplayed());

		assertEquals(
				"http://localhost:8080/lms/report/complete",
				webDriver.getCurrentUrl()
		);



		getEvidence(new Object() {});
	}
}