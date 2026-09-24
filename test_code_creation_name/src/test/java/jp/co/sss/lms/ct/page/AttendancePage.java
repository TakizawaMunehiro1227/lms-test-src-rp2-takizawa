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
	
	

}
