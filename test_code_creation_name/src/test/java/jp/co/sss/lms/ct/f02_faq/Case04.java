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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import jp.co.sss.lms.ct.page.FaqPage;
import jp.co.sss.lms.ct.page.LoginPage;

/**
 * 結合テスト よくある質問機能
 * ケース04
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース04 よくある質問画面への遷移")
public class Case04 {

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
		
		// ログイン画面が表示されていることを確認
		assertTrue(
				webDriver.findElement(By.id("loginId")).isDisplayed()
		);

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
	
	/*
	 * 【同値分割・境界値分析の確認】
	 * 画面遷移を確認するテストのため、数値や文字数などの
	 * 境界値を持つ入力項目はなく、境界値分析の対象外となる。
	 *
	 * ヘルプリンク、よくある質問リンクを正常に操作できる場合を
	 * 正常系の同値クラスとして確認している。
	 */

	/*
	 * 【自己添削・リファクタリング案】
	 * ・URLやログインID、パスワードを定数化する。
	 * ・WebDriverWaitや画面遷移確認などの共通処理をメソッド化する。
	 * ・LoginPageなどのPage Objectを作成し、画面操作の責務を分離する。
	 * ・FAQ画面を別タブで開く処理について、
	 *   windowHandleの取得・切り替え処理を共通化する。
	 * ・URLだけでなく、遷移先画面固有の要素もassertすることで、
	 *   正しく画面が表示されたことをより明確に確認する。
	 */
}
