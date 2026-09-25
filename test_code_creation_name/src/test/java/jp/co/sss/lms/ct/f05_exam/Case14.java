package jp.co.sss.lms.ct.f05_exam;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.Assert.*;

import java.time.Duration;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import jp.co.sss.lms.ct.page.ExamPage;
import jp.co.sss.lms.ct.page.LoginPage;

/**
 * 結合テスト 試験実施機能
 * ケース14
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース13 受講生 試験の実施 結果50点")
public class Case14 {

	/** テスト07およびテスト08 試験実施日時 */
	static Date date;

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
	@DisplayName("テスト03 「試験有」の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {

	    ExamPage examPage =
	            new ExamPage(webDriver);

	    // 「試験有」の研修日の「詳細」をクリック
	    examPage.clickExamTrainingDetail();

	    // URLチェック
	    String currentUrl = webDriver.getCurrentUrl();

	    assertTrue(
	        currentUrl.contains("/lms/section/detail")
	    );

	    // エビデンス取得
	    getEvidence(new Object() {});
	}

	
	@Test
	@Order(4)
	@DisplayName("テスト04 「本日の試験」エリアの「詳細」ボタンを押下し試験開始画面に遷移")
	void test04() {

	    ExamPage examPage = new ExamPage(webDriver);

	    // 「本日の試験」の「詳細」をクリック
	    examPage.clickExamDetail();

	 // 試験開始画面への遷移を待機
	    WebDriverWait wait =
	            new WebDriverWait(webDriver, Duration.ofSeconds(5));

	    wait.until(ExpectedConditions.urlToBe(
	            "http://localhost:8080/lms/exam/start"
	    ));

	    // URLチェック
	    String currentUrl = webDriver.getCurrentUrl();

	    assertEquals(
	            "http://localhost:8080/lms/exam/start",
	            currentUrl
	    );

	    // エビデンス取得
	    getEvidence(new Object() {});
	}
	
	@Test
	@Order(5)
	@DisplayName("テスト05 「試験を開始する」ボタンを押下し試験問題画面に遷移")
	void test05() {

	    ExamPage examPage =
	            new ExamPage(webDriver);

	    // 「試験を開始する」をクリック
	    examPage.clickExamStart();

	    // URLチェック
	    String currentUrl =
	            webDriver.getCurrentUrl();

	    assertTrue(
	    	    currentUrl.contains("/lms/exam/question")
	    	);

	    // エビデンス取得
	    getEvidence(new Object() {});
	}


	@Test
	@Order(6)
	@DisplayName("テスト06 正答と誤答が半々で「確認画面へ進む」ボタンを押下し試験回答確認画面に遷移")
	void test06() {

	    ExamPage examPage = new ExamPage(webDriver);

	    // 第1問～第6問：正答
	    examPage.selectAnswer(0, 2);
	    examPage.selectAnswer(1, 2);
	    examPage.selectAnswer(2, 0);
	    examPage.selectAnswer(3, 0);
	    examPage.selectAnswer(4, 1);
	    examPage.selectAnswer(5, 1);

	    // 第7問～第12問：誤答
	    examPage.selectAnswer(6, 0);
	    examPage.selectAnswer(7, 0);
	    examPage.selectAnswer(8, 0);
	    examPage.selectAnswer(9, 0);
	    examPage.selectAnswer(10, 0);
	    examPage.selectAnswer(11, 0);

	    // 「確認画面へ進む」を押下
	    examPage.clickConfirm();

	    // 試験回答確認画面への遷移を待つ
	    new WebDriverWait(webDriver, Duration.ofSeconds(5))
	    .until(ExpectedConditions.urlContains("/exam/answerCheck"));

	    // URLチェック
	    String currentUrl = webDriver.getCurrentUrl();

	    assertEquals(
	        "http://localhost:8080/lms/exam/answerCheck",
	        currentUrl
	    );

	    // エビデンス取得
	    getEvidence(new Object() {});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 「回答を送信する」ボタンを押下し試験結果画面に遷移")
	void test07() throws InterruptedException {

	    ExamPage examPage =
	            new ExamPage(webDriver);

	    // 「回答を送信する」をクリックし確認ダイアログでOK
	    examPage.clickSubmit();

	    // 試験結果画面への遷移を待つ
	    new WebDriverWait(webDriver, Duration.ofSeconds(5))
	            .until(ExpectedConditions.urlToBe(
	                    "http://localhost:8080/lms/exam/result"));

	    // URLチェック
	    String currentUrl = webDriver.getCurrentUrl();

	    assertEquals(
	            "http://localhost:8080/lms/exam/result",
	            currentUrl);

	    // エビデンス取得
	    getEvidence(new Object() {});
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 「戻る」ボタンを押下し試験開始画面に遷移後当該試験の結果が反映される")
	void test08() throws InterruptedException {

	    ExamPage examPage =
	            new ExamPage(webDriver);

	    // 「戻る」をクリック
	    examPage.clickBack();

	    // 試験開始画面への遷移を待つ
	    new WebDriverWait(webDriver, Duration.ofSeconds(5))
	            .until(ExpectedConditions.urlToBe(
	                    "http://localhost:8080/lms/exam/start"));

	    // URLチェック
	    String currentUrl = webDriver.getCurrentUrl();

	    assertEquals(
	            "http://localhost:8080/lms/exam/start",
	            currentUrl);

	    // 過去の試験結果に今回の結果が表示されていることを確認
	    assertTrue(examPage.isExamResultDisplayed());

	    // エビデンス取得
	    getEvidence(new Object() {});
	}

}
