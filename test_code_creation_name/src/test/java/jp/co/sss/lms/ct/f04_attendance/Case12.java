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
 * ケース12
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース12 受講生 勤怠直接編集 入力チェック")
public class Case12 {

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
		getEvidence(new Object() {
		});
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

	    // 警告ダイアログが表示された場合はOKをクリック
	    attendancePage.acceptAlertIfPresent();

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
	@DisplayName("テスト04 「勤怠情報を直接編集する」リンクから勤怠情報直接変更画面に遷移")
	void test04() {
		// TODO ここに追加
		AttendancePage attendancePage = new AttendancePage(webDriver);

		// 「勤怠」をクリック
		attendancePage.clickAttendanceChange();

		// URLチェック
		String currentUrl = webDriver.getCurrentUrl();

		assertEquals(
				"http://localhost:8080/lms/attendance/update",
				currentUrl);

		getEvidence(new Object() {
		});
	}
	
	@Test
	@Order(5)
	@DisplayName("テスト05 不適切な内容で修正してエラー表示：出退勤の（時）と（分）のいずれかが空白")
	void test05() {

	    AttendancePage attendancePage =
	            new AttendancePage(webDriver);

	    // 1行目だけ変更
	    // 出勤 09:空白、退勤 18:00
	    attendancePage.inputMissingMinute(0);

	    // 更新
	    attendancePage.clickUpdate();

	    // エラーメッセージ確認
	    assertEquals(
	    	    "* 出勤時間が正しく入力されていません。",
	    	    attendancePage.getErrorMessage()
	    	);

	    // エビデンス取得
	    getEvidence(new Object() {});
	}


	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正してエラー表示：出勤が空白で退勤に入力あり")
	void test06() {

	    AttendancePage attendancePage =
	            new AttendancePage(webDriver);

	    // 1行目だけ変更
	    // 出勤：空白、退勤：18:00
	    attendancePage.inputOnlyEndTime(0);

	    // 更新
	    attendancePage.clickUpdate();

	    // エラーメッセージ確認
	    assertEquals(
	    	    "* 出勤情報がないため退勤情報を入力出来ません。",
	    	    attendancePage.getErrorMessage()
	    	);

	    // エビデンス取得
	    getEvidence(new Object() {});
	}


	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正してエラー表示：出勤が退勤よりも遅い時間")
	void test07() {

	    AttendancePage attendancePage =
	            new AttendancePage(webDriver);

	    // 1行目だけ変更
	    // 出勤18:00、退勤09:00
	    attendancePage.inputStartAfterEnd(0);

	    // 更新
	    attendancePage.clickUpdate();

	    // エラーメッセージ確認
	    assertEquals(
	    	    "* 退勤時刻[0]は出勤時刻[0]より後でなければいけません。",
	    	    attendancePage.getErrorMessage()
	    	);

	    // エビデンス取得
	    getEvidence(new Object() {});
	}


	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正してエラー表示：出退勤時間を超える中抜け時間")
	void test08() {

	    AttendancePage attendancePage =
	            new AttendancePage(webDriver);

	    // 1行目だけ変更
	    // 09:00～10:00に対して長すぎる中抜け時間
	    attendancePage.inputInvalidBlankTime(0);

	    // 更新
	    attendancePage.clickUpdate();

	    // エラーメッセージ確認
	    assertEquals(
	    	    "* 中抜け時間が勤務時間を超えています。",
	    	    attendancePage.getErrorMessage()
	    	);

	    // エビデンス取得
	    getEvidence(new Object() {});
	}


	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正してエラー表示：備考が100文字超")
	void test09() {

	    AttendancePage attendancePage =
	            new AttendancePage(webDriver);

	    // 1行目の備考だけ101文字にする
	    attendancePage.inputNoteOver100(0);

	    // 更新
	    attendancePage.clickUpdate();

	    // エラーメッセージ確認
	    assertEquals(
	    	    "* 備考の長さが最大値(100)を超えています。",
	    	    attendancePage.getErrorMessage()
	    	);

	    // エビデンス取得
	    getEvidence(new Object() {});
	}

}
