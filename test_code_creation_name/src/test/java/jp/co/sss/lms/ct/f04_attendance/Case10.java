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

		AttendancePage attendancePage =
				new AttendancePage(webDriver);

		// 「勤怠」をクリック
		attendancePage.clickAttendance();

		// 警告ダイアログのOKをクリック
		attendancePage.acceptAlert();

		// 勤怠管理画面に遷移するまで待機
		WebDriverWait wait =
				new WebDriverWait(webDriver, Duration.ofSeconds(5));

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

		AttendancePage attendancePage =
				new AttendancePage(webDriver);

		// 「出勤」をクリック
		attendancePage.clickClockIn();

		// 警告ダイアログのOKをクリック
		attendancePage.acceptAlert();

		// 登録完了メッセージ確認
		assertEquals(
				"勤怠情報の登録が完了しました。",
				attendancePage.getMessage());

		// エビデンス取得
		getEvidence(new Object() {});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「退勤」ボタンを押下し退勤時間を登録")
	void test05() {

		AttendancePage attendancePage =
				new AttendancePage(webDriver);

		// 「退勤」をクリック
		attendancePage.clickClockOut();

		// 警告ダイアログのOKをクリック
		attendancePage.acceptAlert();

		// 登録完了メッセージ確認
		assertEquals(
				"勤怠情報の登録が完了しました。",
				attendancePage.getMessage());

		// エビデンス取得
		getEvidence(new Object() {});
	}
}