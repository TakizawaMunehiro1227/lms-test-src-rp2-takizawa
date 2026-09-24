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
 * ケース11
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース11 受講生 勤怠直接編集 正常系")
public class Case11 {

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

		AttendancePage attendancePage = new AttendancePage(webDriver);

		// 「勤怠」をクリック
		attendancePage.clickAttendance();


		// URLチェック
		String currentUrl = webDriver.getCurrentUrl();

		assertEquals(
				"http://localhost:8080/lms/attendance/detail",
				currentUrl);

		// エビデンス取得
		getEvidence(new Object() {
		});
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
	@DisplayName("テスト05 すべての研修日程の勤怠情報を正しく更新し勤怠管理画面に遷移")
	void test05() {

	    AttendancePage attendancePage = new AttendancePage(webDriver);

	 // 全研修日程の勤怠情報を変更
	    attendancePage.updateAllAttendance();

	    // 入力確認
	    assertTrue(attendancePage.isAllAttendanceUpdated());

	    // 画面描画待ち
	    try {
	        Thread.sleep(1000);
	    } catch (InterruptedException e) {
	        Thread.currentThread().interrupt();
	    }

	    // 上部
	    scrollTo("0");

	    try {
	        Thread.sleep(500);
	    } catch (InterruptedException e) {
	        Thread.currentThread().interrupt();
	    }

	    getEvidence(new Object() {}, "top");

	    // 下部
	    scrollTo("10000");

	    try {
	        Thread.sleep(500);
	    } catch (InterruptedException e) {
	        Thread.currentThread().interrupt();
	    }

	    getEvidence(new Object() {}, "bottom");
	    // 更新
	    attendancePage.clickUpdate();

	    // 登録完了メッセージ確認
	    assertEquals(
	        "勤怠情報の登録が完了しました。",
	        attendancePage.getMessage()
	    );

	    // 更新後エビデンス
	    getEvidence(new Object() {});
	}
}
