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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import jp.co.sss.lms.ct.page.FaqPage;
import jp.co.sss.lms.ct.page.LoginPage;

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

		getEvidence(new Object() {});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// TODO ここに追加

		LoginPage loginPage = new LoginPage(webDriver);

		//IDを入力（初回ログイン済み）
		//PassWord(初回ログイン済み）を入力
		//ログインボタンをクリック
		loginPage.login("StudentAA01", "StudentAA02");

		//画面遷移にいくまで５秒待機
		WebDriverWait wait =
				new WebDriverWait(webDriver, Duration.ofSeconds(5));

		wait.until(
				ExpectedConditions.urlToBe(
						"http://localhost:8080/lms/course/detail"
				)
		);

		//URLをチェック
		String currentUrl = webDriver.getCurrentUrl();

		assertEquals(
				"http://localhost:8080/lms/course/detail",
				currentUrl
		);

		getEvidence(new Object() {});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// TODO ここに追加

		FaqPage faqPage = new FaqPage(webDriver);

		//「機能」をクリック
		//「ヘルプ」をクリック
		faqPage.clickHelp();

		//画面遷移にいくまで５秒待機
		WebDriverWait wait =
				new WebDriverWait(webDriver, Duration.ofSeconds(5));

		wait.until(
				ExpectedConditions.urlToBe(
						"http://localhost:8080/lms/help"
				)
		);

		//URLチェック
		String currentUrl = webDriver.getCurrentUrl();

		assertEquals(
				"http://localhost:8080/lms/help",
				currentUrl
		);

		getEvidence(new Object() {});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {

		FaqPage faqPage = new FaqPage(webDriver);

		// 現在のタブを保存
		//「よくある質問」をクリック
		// 別タブが開くまで待つ
		// 新しく開いたタブへ切り替える
		faqPage.openFaq();

		//画面遷移にいくまで５秒待機
		WebDriverWait wait =
				new WebDriverWait(webDriver, Duration.ofSeconds(5));

		// FAQ画面に遷移するまで待機
		wait.until(
				ExpectedConditions.urlToBe(
						"http://localhost:8080/lms/faq"
				)
		);

		//URLチェック
		String currentUrl = webDriver.getCurrentUrl();

		assertEquals(
				"http://localhost:8080/lms/faq",
				currentUrl
		);

		getEvidence(new Object() {});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {

		FaqPage faqPage = new FaqPage(webDriver);

		//研修関係のリンクをクリックする。
		faqPage.clickTrainingCategory();

		// 検索結果が表示されるまで待つ

		// 検索結果確認①
		assertTrue(faqPage.isResult1Displayed());

		// 検索結果確認②
		assertTrue(faqPage.isResult2Displayed());

		getEvidence(new Object() {});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {

		FaqPage faqPage = new FaqPage(webDriver);

		// 検索結果が2件表示されるまで待つ

		// 1件目
		assertTrue(faqPage.openQuestion(0));

		// 2件目
		assertTrue(faqPage.openQuestion(1));

		// 2つ目の回答までスクロール
		faqPage.scrollToAnswer(1);

		getEvidence(new Object() {});
	}
}