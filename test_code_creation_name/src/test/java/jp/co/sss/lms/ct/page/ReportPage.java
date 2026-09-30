package jp.co.sss.lms.ct.page;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class ReportPage {
	
	private WebDriver webDriver;
	
	// 「未提出」の行にある最初の詳細ボタン
	@FindBy(xpath = "(//tr[.//*[normalize-space(.)='未提出']]"
	        + "//input[@type='submit' and @value='詳細'])[1]")
	private WebElement detailLink;
	

	//日報【デモ】を提出するボタン
    @FindBy(css = "input[value='日報【デモ】を提出する']")
    private WebElement reportButton;
	

  // 提出するボタン
    @FindBy(xpath = "//button[@type='submit' and normalize-space()='提出する']")
    private WebElement reportRegistButton;
	
    //日報入力欄
    @FindBy(tagName = "textarea")
	private WebElement reportText;
    
    //ようこそリンク
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
    
 // 提出済み日報【デモ】を確認するボタン
    @FindBy(css = "input[value='提出済み日報【デモ】を確認する']")
    private WebElement submittedDailyReportButton;
    
 // 提出済み週報【デモ】を確認するボタン
    @FindBy(css = "input[value='提出済み週報【デモ】を確認する']")
    private WebElement submittedWeeklyReportButton;
    
 // 週報提出済みの研修日の詳細ボタン
    @FindBy(xpath = "(//tr[.//*[normalize-space(.)='提出済み']]"
            + "//input[@type='submit' and @value='詳細'])[2]")
    private WebElement submittedDetailLink;
    
 // 週報【デモ】の詳細ボタン
    @FindBy(xpath = "//tr[td[normalize-space()='週報【デモ】']]"
            + "//input[@type='submit' and @value='詳細']")
    private WebElement weeklyReportDetailButton;
    
  

    // コンストラクタ
    public ReportPage(WebDriver webDriver) {
        this.webDriver = webDriver;

        PageFactory.initElements(webDriver, this);
    }

    // 詳細をクリック
    public void clickDetail() {

    	visibilityTimeout(
    			By.xpath(
    					"(//tr[.//*[normalize-space(.)='未提出']]"
    					+ "//input[@type='submit' and @value='詳細'])[1]"
    			),
    			5
    	);

    	detailLink.click();
    }
    
 // 日報【デモ】の「提出する」ボタンをクリック
    public void clickReport() {

    	visibilityTimeout(
    			By.cssSelector("input[value='日報【デモ】を提出する']"),
    			5
    	);

    	reportButton.click();
    }
    
    //レポート提出するボタンをクリック
    public void clickReportRegist() {

    	visibilityTimeout(
    			By.xpath(
    					"//button[@type='submit' and normalize-space()='提出する']"
    			),
    			5
    	);

    	((JavascriptExecutor) webDriver).executeScript(
    			"arguments[0].scrollIntoView({block:'center'});",
    			reportRegistButton
    	);

    	reportRegistButton.click();
    }
    // 日報内容を入力
    public void inputReport(String text) {
        reportText.clear();
        reportText.sendKeys(text);
    }
    
 // 提出・更新ボタンの表示名を取得
    public String getReportRegistButtonValue() {
     
        return reportRegistButton.getAttribute("value");
    }
    
    
 // ようこそ●●さんリンクをクリック
    public void clickWelcome() {

    	visibilityTimeout(
    			By.partialLinkText("ようこそ"),
    			5
    	);

    	welcomeLink.click();
    }
	

 // 提出済みレポートの詳細をクリック
    public void clickReportDetail() {

    	visibilityTimeout(
    			By.xpath(
    					"(//form[contains(@action,'/report/detail')]"
    					+ "//input[@type='submit' and @value='詳細'])[1]"
    			),
    			5
    	);

    	((JavascriptExecutor) webDriver).executeScript(
    			"arguments[0].click();",
    			reportDetailButton
    	);
    }
	
	//目標の達成度を登録
	public void inputAchievement(String text) {
	    achievement.clear();
	    achievement.sendKeys(text);
	}

	//所感を登録
	public void inputImpression(String text) {
	    impression.clear();
	    impression.sendKeys(text);
	}

	//週報を登録
	public void inputWeeklyReview(String text) {
	    weeklyReview.clear();
	    weeklyReview.sendKeys(text);
	}
	
	// 週報【デモ】の「修正する」ボタンをクリック
	public void clickReportUpdate() {

		visibilityTimeout(
				By.xpath(
						"(//tr[contains(.,'週報【デモ】')]"
						+ "//input[@type='submit' and @value='修正する'])[1]"
				),
				5
		);

		// ボタンの位置までスクロール
		((JavascriptExecutor) webDriver).executeScript(
				"arguments[0].scrollIntoView({block:'center'});",
				reportUpdateButton
		);

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
	
	/**
	 * 日報の「提出する」ボタンが表示されているか確認
	 */
	public boolean isReportButtonDisplayed() {

		visibilityTimeout(
				By.cssSelector("input[value='日報【デモ】を提出する']"),
				5
		);

		return reportButton.isDisplayed();
	}

	
	/**
	 * 未提出の研修日の詳細ボタンが表示されているか確認
	 */
	public boolean isDetailDisplayed() {

		visibilityTimeout(
				By.xpath(
						"(//tr[.//*[normalize-space(.)='未提出']]"
						+ "//input[@type='submit' and @value='詳細'])[1]"
				),
				5
		);

		return detailLink.isDisplayed();
	}
	
	/**
	 * 日報が提出済みになっているか確認
	 */
	public boolean isSubmittedDailyReportDisplayed() {

		visibilityTimeout(
				By.cssSelector(
						"input[value='提出済み日報【デモ】を確認する']"
				),
				5
		);

		return submittedDailyReportButton.isDisplayed();
	}
	
	/**
	 * 提出済みレポートの詳細ボタンが表示されているか確認
	 */
	public boolean isReportDetailButtonDisplayed() {

		visibilityTimeout(
				By.xpath(
						"(//form[contains(@action,'/report/detail')]"
						+ "//input[@type='submit' and @value='詳細'])[1]"
				),
				5
		);

		return reportDetailButton.isDisplayed();
	}

	/**
	 * 週報【デモ】の「修正する」ボタンが表示されているか確認
	 */
	public boolean isReportUpdateButtonDisplayed() {

		visibilityTimeout(
				By.xpath(
						"(//tr[contains(.,'週報【デモ】')]"
						+ "//input[@type='submit' and @value='修正する'])[1]"
				),
				5
		);

		return reportUpdateButton.isDisplayed();
	}
	
	/**
	 * 提出済み週報が表示されているか確認
	 */
	public boolean isSubmittedWeeklyReportDisplayed() {

		visibilityTimeout(
				By.cssSelector(
						"input[value='提出済み週報【デモ】を確認する']"
				),
				5
		);

		return submittedWeeklyReportButton.isDisplayed();
	}
	
	/**
	 * 提出済み週報【デモ】を確認するボタンをクリック
	 */
	public void clickSubmittedWeeklyReport() {

		visibilityTimeout(
				By.cssSelector(
						"input[value='提出済み週報【デモ】を確認する']"
				),
				5
		);

		submittedWeeklyReportButton.click();
	}
	


	
	/**
	 * 週報編集画面が表示されているか確認
	 */
	public boolean isWeeklyReportEditDisplayed() {

		visibilityTimeout(
				By.id("content_1"),
				5
		);

		return impression.isDisplayed();
	}
	

	
	/**
	 * 週報提出済みの研修日の詳細ボタンが表示されているか確認
	 */
	public boolean isSubmittedDetailDisplayed() {

		visibilityTimeout(
				By.xpath(
						"(//tr[.//*[normalize-space(.)='提出済み']]"
						+ "//input[@type='submit' and @value='詳細'])[2]"
				),
				5
		);

		return submittedDetailLink.isDisplayed();
	}

	/**
	 * 週報提出済みの研修日の詳細ボタンをクリック
	 */
	public void clickSubmittedDetail() {

		visibilityTimeout(
				By.xpath(
						"(//tr[.//*[normalize-space(.)='提出済み']]"
						+ "//input[@type='submit' and @value='詳細'])[2]"
				),
				5
		);

		submittedDetailLink.click();
	}
	
	/**
	 * レポート詳細画面の所感を取得
	 */
	public String getReportDetailImpression() {

		By locator = By.xpath(
				"//tr[*[1][normalize-space()='所感']]/*[2]"
		);

		visibilityTimeout(locator, 5);

		return webDriver.findElement(locator).getText();
	}

	/**
	 * レポート詳細画面の一週間の振り返りを取得
	 */
	public String getReportDetailWeeklyReview() {

		By locator = By.xpath(
				"//tr[*[1][normalize-space()='一週間の振り返り']]/*[2]"
		);

		visibilityTimeout(locator, 5);

		return webDriver.findElement(locator).getText();
	}
	
	/**
	 * 週報【デモ】の詳細ボタンをクリック
	 */
	public void clickWeeklyReportDetail() {

		visibilityTimeout(
				By.xpath(
						"//tr[td[normalize-space()='週報【デモ】']]"
						+ "//input[@type='submit' and @value='詳細']"
				),
				5
		);

		((JavascriptExecutor) webDriver).executeScript(
				"arguments[0].click();",
				weeklyReportDetailButton
		);
	}
	
	/**
	 * 入力チェックエラーが表示されているか確認
	 */
	public boolean isErrorInputDisplayed() {

		visibilityTimeout(
				By.className("errorInput"),
				5
		);

		return true;
	}
}
