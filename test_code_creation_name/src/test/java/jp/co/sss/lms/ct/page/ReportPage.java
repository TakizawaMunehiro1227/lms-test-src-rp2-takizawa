package jp.co.sss.lms.ct.page;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReportPage {
	
	private WebDriver webDriver;
	private WebDriverWait wait;
	
	// 「未提出」の行にある最初の詳細ボタン
	@FindBy(xpath = "(//tr[.//*[normalize-space(.)='未提出']]"
	        + "//input[@type='submit' and @value='詳細'])[1]")
	private WebElement detailLink;
	

	
    @FindBy(css = "input[value='日報【デモ】を提出する']")
    private WebElement reportButton;
	

  // 提出するボタン
    @FindBy(xpath = "//button[@type='submit' and normalize-space()='提出する']")
    private WebElement reportRegistButton;
	
    //日報入力欄
    @FindBy(tagName = "textarea")
	private WebElement reportText;
    
    @FindBy(partialLinkText = "ようこそ")
    private WebElement welcomeLink;
    
 // 提出済みレポートの詳細ボタン
    @FindBy(xpath = "(//form[contains(@action,'/report/detail')]"
            + "//input[@type='submit' and @value='詳細'])[1]")
    private WebElement reportDetailButton;
    
 // 週報【デモ】の「修正する」ボタン
    @FindBy(xpath = "(//tr[contains(.,'週報【デモ】')]"
            + "//input[@type='submit' and @value='修正する'])[1]")
    private WebElement reportUpdateButton;
    
 // 学習項目
    @FindBy(id = "intFieldName_0")
    private WebElement learningItem;

    // 理解度
    @FindBy(id = "intFieldValue_0")
    private WebElement intelligibility;
 
 // 目標の達成度
    @FindBy(id = "content_0")
    private WebElement achievement;

    // 所感
    @FindBy(id = "content_1")
    private WebElement impression;

    // 一週間の振り返り
    @FindBy(id = "content_2")
    private WebElement weeklyReview;
    
    
  

    // コンストラクタ
    public ReportPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

        PageFactory.initElements(webDriver, this);
    }

    // 詳細をクリック
    public void clickDetail() {
        wait.until(ExpectedConditions.elementToBeClickable(detailLink));
        detailLink.click();
    }
    
    // 提出済み日報をクリック
    public void clickReport() {
        wait.until(ExpectedConditions.elementToBeClickable(reportButton));
        reportButton.click();
    }
    
    public void clickReportRegist() {

        // 提出するボタンまでスクロール
        ((JavascriptExecutor) webDriver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                reportRegistButton
        );

        // クリック可能になるまで待機
        WebDriverWait wait =
                new WebDriverWait(webDriver, Duration.ofSeconds(5));

        wait.until(ExpectedConditions.elementToBeClickable(reportRegistButton));

        // 提出するボタンをクリック
        reportRegistButton.click();
    }
    // 日報内容を入力
    public void inputReport(String text) {
        reportText.clear();
        reportText.sendKeys(text);
    }
    
 // 提出・更新ボタンの表示名を取得
    public String getReportRegistButtonValue() {
        wait.until(ExpectedConditions.visibilityOf(reportRegistButton));
        return reportRegistButton.getAttribute("value");
    }
    
    
    //ようこそ●●さんリンクをクリック
	public void clickWelcome() {

		wait.until(ExpectedConditions.elementToBeClickable(welcomeLink));
		welcomeLink.click();
	}
	
	// 提出済みレポートの詳細をクリック
	// 提出済みレポートの詳細をクリック
	public void clickReportDetail() {

	    wait.until(ExpectedConditions.visibilityOf(reportDetailButton));

	    ((JavascriptExecutor) webDriver).executeScript(
	            "arguments[0].click();",
	            reportDetailButton);
	}
	
	public void inputAchievement(String text) {
	    achievement.clear();
	    achievement.sendKeys(text);
	}

	public void inputImpression(String text) {
	    impression.clear();
	    impression.sendKeys(text);
	}

	public void inputWeeklyReview(String text) {
	    weeklyReview.clear();
	    weeklyReview.sendKeys(text);
	}
	
	// 「修正する」ボタンをクリック
	public void clickReportUpdate() {

	    wait.until(ExpectedConditions.visibilityOf(reportUpdateButton));

	    // ボタンの位置までスクロール
	    ((JavascriptExecutor) webDriver).executeScript(
	            "arguments[0].scrollIntoView({block:'center'});",
	            reportUpdateButton);

	    // クリック可能になるまで待機
	    wait.until(ExpectedConditions.elementToBeClickable(reportUpdateButton));

	    // クリック
	    reportUpdateButton.click();
	}
	
	// 学習項目を入力
	public void inputLearningItem(String text) {
	    learningItem.clear();
	    learningItem.sendKeys(text);
	}

	// 学習項目を空にする
	public void clearLearningItem() {
	    learningItem.clear();
	}

	// 理解度を選択
	public void selectIntelligibility(String value) {
	    Select select = new Select(intelligibility);
	    select.selectByValue(value);
	}

	// 理解度を未選択にする
	public void clearIntelligibility() {
	    Select select = new Select(intelligibility);
	    select.selectByValue("");
	}
	



}
