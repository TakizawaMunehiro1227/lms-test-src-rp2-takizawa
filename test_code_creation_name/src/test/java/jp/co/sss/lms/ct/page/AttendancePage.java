package jp.co.sss.lms.ct.page;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AttendancePage {

	private WebDriver webDriver;
	private WebDriverWait wait;

	private static final String NORMAL_START_HOUR = "09";
	private static final String NORMAL_START_MINUTE = "00";
	private static final String NORMAL_END_HOUR = "18";
	private static final String NORMAL_END_MINUTE = "00";

	// 上部メニュー「勤怠」
	@FindBy(linkText = "勤怠")
	private WebElement attendance;

	// 出勤ボタン
	@FindBy(name = "punchIn")
	private WebElement clockIn;

	// 退勤ボタン
	@FindBy(name = "punchOut")
	private WebElement clockOut;

	// 登録完了メッセージ
	@FindBy(css = "div.alert.alert-info > span")
	private WebElement message;

	// 「勤怠情報を直接編集する」
	@FindBy(linkText = "勤怠情報を直接編集する")
	private WebElement attendanceChange;

	// 出勤時
	@FindBy(css = "select[name$='.trainingStartTimeHour']")
	private List<WebElement> startHours;

	// 出勤分
	@FindBy(css = "select[name$='.trainingStartTimeMinute']")
	private List<WebElement> startMinutes;

	// 退勤時
	@FindBy(css = "select[name$='.trainingEndTimeHour']")
	private List<WebElement> endHours;

	// 退勤分
	@FindBy(css = "select[name$='.trainingEndTimeMinute']")
	private List<WebElement> endMinutes;

	// 中抜け時間
	@FindBy(css = "select[name$='.blankTime']")
	private List<WebElement> blankTimes;

	// 備考
	@FindBy(css = "input[name$='.note']")
	private List<WebElement> notes;

	// 更新ボタン
	@FindBy(name = "complete")
	private WebElement updateButton;

	// エラーメッセージ
	@FindBy(css = "span.help-inline.error")
	private List<WebElement> errorMessages;

	/**
	 * コンストラクタ
	 */
	public AttendancePage(WebDriver webDriver) {

		this.webDriver = webDriver;
		this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

		PageFactory.initElements(webDriver, this);
	}

	/**
	 * 上部メニュー「勤怠」をクリック
	 */
	public void clickAttendance() {

		wait.until(ExpectedConditions.elementToBeClickable(attendance));
		attendance.click();
	}

	/**
	 * 「出勤」をクリック
	 */
	public void clickClockIn() {

		wait.until(ExpectedConditions.elementToBeClickable(clockIn));
		clockIn.click();
	}

	/**
	 * 「退勤」をクリック
	 */
	public void clickClockOut() {

		wait.until(ExpectedConditions.elementToBeClickable(clockOut));
		clockOut.click();
	}

	/**
	 * 「勤怠情報を直接編集する」をクリック
	 */
	public void clickAttendanceChange() {

		wait.until(ExpectedConditions.elementToBeClickable(attendanceChange));
		attendanceChange.click();
	}

	/**
	 * 登録完了メッセージを取得
	 */
	public String getMessage() {

		wait.until(ExpectedConditions.visibilityOf(message));

		return message.getText();
	}

	/**
	 * エラーメッセージを取得
	 */
	public String getErrorMessage() {

		wait.until(
				ExpectedConditions.visibilityOfAllElements(errorMessages));

		return errorMessages.get(0).getText();
	}

	/**
	 * AlertのOKをクリック
	 */
	public void acceptAlert() {

		Alert alert = wait.until(
				ExpectedConditions.alertIsPresent());

		alert.accept();
	}

	/**
	 * Alertが表示されている場合のみOKをクリック
	 */
	public void acceptAlertIfPresent() {

		try {

			WebDriverWait alertWait =
					new WebDriverWait(webDriver, Duration.ofSeconds(2));

			Alert alert = alertWait.until(
					ExpectedConditions.alertIsPresent());

			alert.accept();

		} catch (TimeoutException e) {

			// Alertが表示されない場合は何もしない
		}
	}

	/**
	 * 更新ボタンをクリックし確認ダイアログのOKをクリック
	 */
	public void clickUpdate() {

		JavascriptExecutor js =
				(JavascriptExecutor) webDriver;

		// 更新ボタンまでスクロール
		js.executeScript(
				"arguments[0].scrollIntoView({block:'center'});",
				updateButton);

		// 更新ボタンをクリック
		js.executeScript(
				"arguments[0].click();",
				updateButton);

		// 確認ダイアログのOKをクリック
		acceptAlert();
	}

	/**
	 * 全研修日程の勤怠情報を09:00～18:00に設定
	 */
	public void updateAllAttendance() {

		for (int i = 0; i < startHours.size(); i++) {

			setValidAttendance(i);
		}
	}

	/**
	 * 全研修日程が09:00～18:00に設定されているか確認
	 */
	public boolean isAllAttendanceUpdated() {

		for (int i = 0; i < startHours.size(); i++) {

			if (!isSelected(startHours.get(i), NORMAL_START_HOUR)) {
				return false;
			}

			if (!isSelected(startMinutes.get(i), NORMAL_START_MINUTE)) {
				return false;
			}

			if (!isSelected(endHours.get(i), NORMAL_END_HOUR)) {
				return false;
			}

			if (!isSelected(endMinutes.get(i), NORMAL_END_MINUTE)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 出勤「分」のみ空白にする
	 */
	public void inputMissingMinute(int index) {

		setValidAttendance(index);

		selectBlank(startMinutes.get(index));
	}

	/**
	 * 出勤を空白、退勤のみ入力する
	 */
	public void inputOnlyEndTime(int index) {

		setValidAttendance(index);

		selectBlank(startHours.get(index));
		selectBlank(startMinutes.get(index));
	}

	/**
	 * 出勤を退勤より遅くする
	 */
	public void inputStartAfterEnd(int index) {

		setValidAttendance(index);

		setAttendance(
				index,
				"18",
				"00",
				"09",
				"00");
	}

	/**
	 * 勤務時間を超える中抜け時間を設定
	 */
	public void inputInvalidBlankTime(int index) {

		setValidAttendance(index);

		// 勤務時間を09:00～10:00にする
		setAttendance(
				index,
				"09",
				"00",
				"10",
				"00");

		Select blankTime =
				new Select(blankTimes.get(index));

		List<WebElement> options =
				blankTime.getOptions();

		// 最大の中抜け時間を設定
		blankTime.selectByIndex(options.size() - 1);
	}

	/**
	 * 備考を101文字にする
	 */
	public void inputNoteOver100(int index) {

		setValidAttendance(index);

		notes.get(index).sendKeys("あ".repeat(101));
	}

	/**
	 * 指定行を正常な勤怠情報に戻す
	 */
	private void setValidAttendance(int index) {

		setAttendance(
				index,
				NORMAL_START_HOUR,
				NORMAL_START_MINUTE,
				NORMAL_END_HOUR,
				NORMAL_END_MINUTE);

		selectBlank(blankTimes.get(index));

		notes.get(index).clear();
	}

	/**
	 * 指定行の出退勤時間を設定
	 */
	private void setAttendance(
			int index,
			String startHour,
			String startMinute,
			String endHour,
			String endMinute) {

		selectByVisibleText(
				startHours.get(index),
				startHour);

		selectByVisibleText(
				startMinutes.get(index),
				startMinute);

		selectByVisibleText(
				endHours.get(index),
				endHour);

		selectByVisibleText(
				endMinutes.get(index),
				endMinute);
	}

	/**
	 * プルダウンを表示文字列で選択
	 */
	private void selectByVisibleText(
			WebElement element,
			String value) {

		new Select(element)
				.selectByVisibleText(value);
	}

	/**
	 * プルダウンを空白にする
	 */
	private void selectBlank(WebElement element) {

		new Select(element)
				.selectByIndex(0);
	}

	/**
	 * 指定値が選択されているか確認
	 */
	private boolean isSelected(
			WebElement element,
			String expectedValue) {

		String actualValue =
				new Select(element)
						.getFirstSelectedOption()
						.getText();

		return expectedValue.equals(actualValue);
	}
}
