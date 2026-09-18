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
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

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
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {

		FaqPage faqPage = new FaqPage(webDriver);

		// キーワードを入力
		faqPage.inputKeyword("キャンセル料");

		// 検索ボタンをクリック
		faqPage.clickSearch();

		// 検索結果が表示されるまで待つ

		// 検索結果確認
		assertTrue(faqPage.isCancelResultDisplayed());

		getEvidence(new Object() {});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		// TODO ここに追加

		FaqPage faqPage = new FaqPage(webDriver);

		// クリア
		faqPage.clickClear();

		// 入力欄が空になったことを確認
		assertEquals("", faqPage.getKeywordValue());

		getEvidence(new Object() {});
	}
	
	/*
	 * 【同値分割・境界値分析の確認】
	 *
	 * キーワード検索には、検索キーワードの入力項目がある。
	 *
	 * 本テストでは、「キャンセル料」という検索対象として有効なキーワードを入力し、
	 * 該当する検索結果のみが正常に表示される場合を正常系の同値クラスとして確認している。
	 *
	 * 検索結果が存在しないキーワードや未入力の場合などは、
	 * 別の同値クラスとして考えられるが、本ケースでは正常系の確認を目的としているため対象外とする。
	 *
	 * また、キーワードの文字数について上限・下限などの仕様が定められている場合は
	 * 境界値分析の対象となるが、本ケースではキーワード検索の正常動作確認を主目的としているため、
	 * 境界値の確認は実施していない。
	 *
	 * 「クリア」ボタンについては、入力したキーワードが消去され、
	 * 入力欄が空になることを確認している。
	 */
}
