package jp.co.sss.lms.ct.page;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
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

	// 上部メニュー「勤怠」
	@FindBy(linkText = "勤怠")
	private WebElement attendance;

	// 出勤ボタン
	@FindBy(name = "punchIn")
	private WebElement clockIn;

	// 退勤ボタン
	@FindBy(name = "punchOut")
	private WebElement clockOut;
		
	@FindBy(css = "div.alert.alert-info > span")
	private WebElement message;
	
	// 上部メニュー「勤怠」
	@FindBy(linkText = "勤怠情報を直接編集する")
	private WebElement attendanceChange;
	
	@FindBy(css = "select[name$='.trainingStartTimeHour']")
	private List<WebElement> startHours;

	@FindBy(css = "select[name$='.trainingStartTimeMinute']")
	private List<WebElement> startMinutes;

	@FindBy(css = "select[name$='.trainingEndTimeHour']")
	private List<WebElement> endHours;

	@FindBy(css = "select[name$='.trainingEndTimeMinute']")
	private List<WebElement> endMinutes;

	@FindBy(css = "select[name$='.blankTime']")
	private List<WebElement> blankTimes;

	@FindBy(css = "input[name$='.note']")
	private List<WebElement> notes;
	
	// 更新ボタン
	@FindBy(name = "complete")
	private WebElement updateButton;
	
	//エラー取得
	@FindBy(css = "span.help-inline.error")
	private List<WebElement> errorMessages;

	public String getErrorMessage() {

	    wait.until(ExpectedConditions
	            .visibilityOfAllElements(errorMessages));

	    return errorMessages.get(0).getText();
	}


	// --- コンストラクタ ---

	public AttendancePage(WebDriver webDriver) {

		this.webDriver = webDriver;

		this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

		PageFactory.initElements(webDriver, this);
	}

	// 上部メニュー「勤怠」をクリック
	public void clickAttendance() {

		wait.until(ExpectedConditions.elementToBeClickable(attendance));
		attendance.click();

	}

	// 「出勤」をクリック
	public void clickClockIn() {

		wait.until(ExpectedConditions.elementToBeClickable(clockIn));
		clockIn.click();

	}
	
	// 「出勤」をクリック
	public void clickClockOut() {

		wait.until(ExpectedConditions.elementToBeClickable(clockOut));
		clockOut.click();

	}
	
	public String getMessage() {
	    return message.getText();
	}
	
	// 上部メニュー「勤怠」をクリック
	public void clickAttendanceChange() {

		wait.until(ExpectedConditions.elementToBeClickable(attendanceChange));
		attendanceChange.click();

	}
	
	//更新ボタンクリック
	public void clickUpdate() {

	    JavascriptExecutor js = (JavascriptExecutor) webDriver;

	    // 更新ボタンまでスクロール
	    js.executeScript(
	        "arguments[0].scrollIntoView({block:'center'});",
	        updateButton
	    );

	    // 更新ボタンをクリック
	    js.executeScript(
	        "arguments[0].click();",
	        updateButton
	    );

	    // 確認ダイアログが表示されるまで待機
	    WebDriverWait wait =
	        new WebDriverWait(webDriver, Duration.ofSeconds(5));

	    Alert alert = wait.until(
	        ExpectedConditions.alertIsPresent()
	    );

	    // OKをクリック
	    alert.accept();
	}

	public void updateAllAttendance() {

	    for (int i = 0; i < startHours.size(); i++) {

	        // 出勤 09:00
	        new Select(startHours.get(i))
	            .selectByVisibleText("09");

	        new Select(startMinutes.get(i))
	            .selectByVisibleText("00");

	        // 退勤 18:00
	        new Select(endHours.get(i))
	            .selectByVisibleText("18");

	        new Select(endMinutes.get(i))
	            .selectByVisibleText("00");

	        // 中抜けなし
	        new Select(blankTimes.get(i))
	            .selectByIndex(0);
	    }
	}
	

	
	public boolean isAllAttendanceUpdated() {

	    for (int i = 0; i < startHours.size(); i++) {

	        if (!new Select(startHours.get(i))
	                .getFirstSelectedOption().getText().equals("09")) {
	            return false;
	        }

	        if (!new Select(startMinutes.get(i))
	                .getFirstSelectedOption().getText().equals("00")) {
	            return false;
	        }

	        if (!new Select(endHours.get(i))
	                .getFirstSelectedOption().getText().equals("18")) {
	            return false;
	        }

	        if (!new Select(endMinutes.get(i))
	                .getFirstSelectedOption().getText().equals("00")) {
	            return false;
	        }
	    }

	    return true;
	}
	
	// 出退勤の「時」「分」のいずれかを空白にする
	public void inputInvalidMissingTime() {

	    // 出勤 時：09
	    new Select(startHours.get(0))
	        .selectByVisibleText("09");

	    // 出勤 分：空白
	    new Select(startMinutes.get(0))
	        .selectByIndex(0);

	    // 退勤：18:00
	    new Select(endHours.get(0))
	        .selectByVisibleText("18");

	    new Select(endMinutes.get(0))
	        .selectByVisibleText("00");
	}


	// 出勤を空白、退勤だけ入力
	public void inputOnlyEndTime() {

	    new Select(startHours.get(0))
	        .selectByIndex(0);

	    new Select(startMinutes.get(0))
	        .selectByIndex(0);

	    new Select(endHours.get(0))
	        .selectByVisibleText("18");

	    new Select(endMinutes.get(0))
	        .selectByVisibleText("00");
	}


	// 出勤を退勤より遅くする
	public void inputStartTimeAfterEndTime() {

	    // 出勤 18:00
	    new Select(startHours.get(0))
	        .selectByVisibleText("18");

	    new Select(startMinutes.get(0))
	        .selectByVisibleText("00");

	    // 退勤 09:00
	    new Select(endHours.get(0))
	        .selectByVisibleText("09");

	    new Select(endMinutes.get(0))
	        .selectByVisibleText("00");
	}


	// 出退勤時間を超える中抜け時間
	public void inputInvalidBlankTime() {

	    // 出勤 09:00
	    new Select(startHours.get(0))
	        .selectByVisibleText("09");

	    new Select(startMinutes.get(0))
	        .selectByVisibleText("00");

	    // 退勤 10:00
	    new Select(endHours.get(0))
	        .selectByVisibleText("10");

	    new Select(endMinutes.get(0))
	        .selectByVisibleText("00");

	    // 1時間を超える中抜け時間を選択
	    new Select(blankTimes.get(0))
	        .selectByIndex(blankTimes.get(0)
	            .findElements(org.openqa.selenium.By.tagName("option")).size() - 1);
	}


	// 備考を101文字にする
	public void inputNoteOver100Characters() {

	    notes.get(0).clear();
	    notes.get(0).sendKeys("あ".repeat(101));
	}
	
	public void setAttendance(int index,
	        String startHour,
	        String startMinute,
	        String endHour,
	        String endMinute) {

	    new Select(startHours.get(index))
	        .selectByVisibleText(startHour);

	    new Select(startMinutes.get(index))
	        .selectByVisibleText(startMinute);

	    new Select(endHours.get(index))
	        .selectByVisibleText(endHour);

	    new Select(endMinutes.get(index))
	        .selectByVisibleText(endMinute);
	}
	
	// 指定した行の出退勤時間を設定


	// 指定した行のプルダウンを空白にする
	public void selectBlank(WebElement element) {
	    new Select(element).selectByIndex(0);
	}


	// テスト05
	public void inputMissingMinute(int index) {

	    new Select(startHours.get(index))
	        .selectByVisibleText("09");

	    // 出勤「分」だけ空白
	    new Select(startMinutes.get(index))
	        .selectByIndex(0);

	    new Select(endHours.get(index))
	        .selectByVisibleText("18");

	    new Select(endMinutes.get(index))
	        .selectByVisibleText("00");
	}


	// テスト06
	public void inputOnlyEndTime(int index) {

	    // 出勤を空白
	    new Select(startHours.get(index))
	        .selectByIndex(0);

	    new Select(startMinutes.get(index))
	        .selectByIndex(0);

	    // 退勤だけ入力
	    new Select(endHours.get(index))
	        .selectByVisibleText("18");

	    new Select(endMinutes.get(index))
	        .selectByVisibleText("00");
	}


	// テスト07
	public void inputStartAfterEnd(int index) {

	    // 出勤 18:00
	    new Select(startHours.get(index))
	        .selectByVisibleText("18");

	    new Select(startMinutes.get(index))
	        .selectByVisibleText("00");

	    // 退勤 09:00
	    new Select(endHours.get(index))
	        .selectByVisibleText("09");

	    new Select(endMinutes.get(index))
	        .selectByVisibleText("00");
	}


	// テスト08
	public void inputInvalidBlankTime(int index) {

	    // 出勤 09:00
	    new Select(startHours.get(index))
	        .selectByVisibleText("09");

	    new Select(startMinutes.get(index))
	        .selectByVisibleText("00");

	    // 退勤 10:00
	    new Select(endHours.get(index))
	        .selectByVisibleText("10");

	    new Select(endMinutes.get(index))
	        .selectByVisibleText("00");

	    // 中抜け時間を最大値にする
	    Select blankTime = new Select(blankTimes.get(index));

	    List<WebElement> options = blankTime.getOptions();

	    blankTime.selectByIndex(options.size() - 1);
	}


	// テスト09
	public void inputNoteOver100(int index) {

	    notes.get(index).clear();

	    notes.get(index).sendKeys("あ".repeat(101));
	}
	
	public void acceptAlertIfPresent() {

	    try {

	        WebDriverWait alertWait =
	                new WebDriverWait(webDriver, Duration.ofSeconds(2));

	        Alert alert = alertWait.until(
	                ExpectedConditions.alertIsPresent());

	        alert.accept();

	    } catch (org.openqa.selenium.TimeoutException e) {

	        // Alertが表示されなければ何もしない
	    }
	}
	
	

}
