package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.Assert.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;

import jp.co.sss.lms.ct.page.LoginPage;

/**
 * 結合テスト ログイン機能①
 * ケース02
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース02 受講生 ログイン 認証失敗")
public class Case02 {

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
		
		// ログイン画面が表示されていることを確認
		assertTrue(
				webDriver.findElement(By.id("loginId")).isDisplayed()
		);

		// エビデンスを取得
		getEvidence(new Object() {});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test02() {

		// LoginPageを生成
		LoginPage loginPage = new LoginPage(webDriver);

		// DB未登録ユーザーでログイン
		loginPage.login("1", "StudentAA01");

		// エラーメッセージが表示されていることを確認
		assertTrue(loginPage.isErrorMessageDisplayed());

		// エラーメッセージを取得して比較
		assertEquals(
				"* ログインに失敗しました。",
				loginPage.getErrorMessage()
		);

		// URLチェック
		String currentUrl = webDriver.getCurrentUrl();

		assertTrue(
				currentUrl.startsWith("http://localhost:8080/lms/login")
		);

		// エビデンスを取得
		getEvidence(new Object() {});
	}
	
	/*
	 * 【同値分割・境界値分析の確認】
	 * ・DBに登録されていないユーザーIDを入力した場合について、
	 *   ログインに失敗することを確認している。
	 * ・未登録ユーザーIDは、ログインできない入力値の同値クラスとして確認済み。
	 * ・登録済みユーザーID＋誤ったパスワード、ID未入力、パスワード未入力、
	 *   両方未入力について、他のテストケースで確認されていない場合は
	 *   異常系の追加検討が必要。
	 * ・ID、パスワードに文字数制限が仕様として定義されている場合は、
	 *   最小文字数、最小文字数-1、最大文字数、最大文字数+1について
	 *   境界値として確認する必要がある。
	 *
	 * 【自己添削・リファクタリング案】
	 * ・URL、ログインID、パスワード、エラーメッセージを直接記述しているため、
	 *   定数化することで変更時の修正箇所を減らすことができる。
	 * ・ログイン操作をLoginPageにまとめているため画面操作は分離できているが、
	 *   URL確認など複数ケースで使用する処理についても共通化する余地がある。
	 * ・エラーメッセージの表示確認と内容確認を行っているため、
	 *   認証失敗時の結果は明確に検証できている。
	 */
}
