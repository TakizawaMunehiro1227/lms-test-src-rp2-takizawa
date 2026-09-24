package jp.co.sss.lms.ct.f04_attendance;

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
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import jp.co.sss.lms.ct.page.AttendancePage;
import jp.co.sss.lms.ct.page.LoginPage;

/**
 * 結合テスト 勤怠管理機能
 * ケース10
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース10 受講生 勤怠登録 正常系")
public class Case10 {

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
	@DisplayName("テスト03 上部メニューの「勤怠」リンクから勤怠管理画面に遷移")
	void test03() {

	    AttendancePage attendancePage = new AttendancePage(webDriver);

	    // 「勤怠」をクリック
	    attendancePage.clickAttendance();

	    // 最大5秒待機
	    WebDriverWait wait =
	            new WebDriverWait(webDriver, Duration.ofSeconds(5));

	    // アラートが表示されるまで待機
	    Alert alert = wait.until(ExpectedConditions.alertIsPresent());

	    // アラートのOKをクリック
	    alert.accept();

	    // 勤怠管理画面への遷移を待機
	    wait.until(
	            ExpectedConditions.urlToBe(
	                    "http://localhost:8080/lms/attendance/detail"));

	    // URLチェック
	    String currentUrl = webDriver.getCurrentUrl();

	    assertEquals(
	            "http://localhost:8080/lms/attendance/detail",
	            currentUrl);

	    // エビデンス取得
	    getEvidence(new Object() {});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「出勤」ボタンを押下し出勤時間を登録")
	void test04() {
		// TODO ここに追加
		
	    AttendancePage attendancePage = new AttendancePage(webDriver);

	    // 「出勤」をクリック
	    attendancePage.clickClockIn();

	    // 最大5秒待機
	    WebDriverWait wait =
	            new WebDriverWait(webDriver, Duration.ofSeconds(5));

	    // アラートが表示されるまで待機
	    Alert alert = wait.until(ExpectedConditions.alertIsPresent());

	    // アラートのOKをクリック
	    alert.accept();

	 // 登録完了メッセージが表示されるまで待機
	    WebElement message = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                    By.cssSelector("div.alert.alert-info > span")));

	    // 登録完了メッセージを確認
	    assertEquals(
	            "勤怠情報の登録が完了しました。",
	            message.getText());


	    // エビデンス取得
	    getEvidence(new Object() {});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「退勤」ボタンを押下し退勤時間を登録")
	void test05() {

	    AttendancePage attendancePage = new AttendancePage(webDriver);

	    // 「退勤」をクリック
	    attendancePage.clickClockOut();

	    // 最大5秒待機
	    WebDriverWait wait =
	        new WebDriverWait(webDriver, Duration.ofSeconds(5));

	    // アラートが表示されるまで待機
	    Alert alert = wait.until(
	        ExpectedConditions.alertIsPresent()
	    );

	    // アラートのOKをクリック
	    alert.accept();

	    // 登録完了メッセージを確認
	    assertEquals(
	        "退勤情報の登録が完了しました。",
	        attendancePage.getMessage()
	    );

	    // エビデンス取得
	    getEvidence(new Object() {});
	}

}
