package jp.co.sss.lms.ct.page;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FaqPage {

	private WebDriver webDriver;
	private WebDriverWait wait;

	// --- Case04 よくある質問画面への遷移 ---

	// 上部メニュー「機能」
	@FindBy(linkText = "機能")
	private WebElement category;

	// 「ヘルプ」リンク
	@FindBy(linkText = "ヘルプ")
	private WebElement helpLink;

	// 「よくある質問」リンク
	@FindBy(linkText = "よくある質問")
	private WebElement faqLink;


	// --- Case05 キーワード検索 ---

	// キーワード入力欄
	@FindBy(id = "form")
	private WebElement keyword;

	// 検索ボタン
	@FindBy(css = "input[value='検索']")
	private WebElement searchButton;

	// クリアボタン
	@FindBy(css = "input[value='クリア']")
	private WebElement clearButton;


	// --- Case06 カテゴリ検索 ---

	// 研修関係カテゴリ
	@FindBy(linkText = "【研修関係】")
	private WebElement trainingCategory;

	// 検索結果①
	@FindBy(xpath = "//*[contains(text(),'キャンセル料・途中退校について')]")
	private WebElement result1;

	// 検索結果②
	@FindBy(xpath = "//*[contains(text(),'研修の申し込みはどのようにすれば良いですか？')]")
	private WebElement result2;

	// 質問一覧
	@FindBy(css = "dl[id^='question-h']")
	private List<WebElement> questions;


	// --- コンストラクタ ---

	public FaqPage(WebDriver webDriver) {

		this.webDriver = webDriver;

		this.wait =
				new WebDriverWait(webDriver, Duration.ofSeconds(5));

		PageFactory.initElements(webDriver, this);
	}


	// --- Case04 ---

	// 上部メニューの「機能」から「ヘルプ」をクリック
	public void clickHelp() {

		wait.until(ExpectedConditions.elementToBeClickable(category));
		category.click();

		wait.until(ExpectedConditions.elementToBeClickable(helpLink));
		helpLink.click();
	}


	// 「よくある質問」を別タブで開く
	public void openFaq() {

		// 現在のタブを保存
		String originalWindow = webDriver.getWindowHandle();

		// 「よくある質問」をクリック
		wait.until(ExpectedConditions.elementToBeClickable(faqLink));
		faqLink.click();

		// 別タブが開くまで待つ
		wait.until(ExpectedConditions.numberOfWindowsToBe(2));

		// 新しく開いたタブへ切り替える
		for (String windowHandle : webDriver.getWindowHandles()) {

			if (!windowHandle.equals(originalWindow)) {

				webDriver.switchTo().window(windowHandle);
				break;
			}
		}
	}


	// --- Case05 ---

	// キーワードを入力
	public void inputKeyword(String text) {

		wait.until(ExpectedConditions.visibilityOf(keyword));

		keyword.clear();
		keyword.sendKeys(text);
	}


	// 検索ボタンをクリック
	public void clickSearch() {

		wait.until(ExpectedConditions.elementToBeClickable(searchButton));
		searchButton.click();
	}


	// 検索結果確認
	public boolean isCancelResultDisplayed() {

		wait.until(ExpectedConditions.visibilityOf(result1));

		return result1.isDisplayed();
	}


	// クリアボタンをクリック
	public void clickClear() {

		wait.until(ExpectedConditions.elementToBeClickable(clearButton));
		clearButton.click();
	}


	// キーワード入力欄の値を取得
	public String getKeywordValue() {

		return keyword.getAttribute("value");
	}


	// --- Case06 ---

	// 研修関係カテゴリをクリック
	public void clickTrainingCategory() {

		wait.until(
				ExpectedConditions.elementToBeClickable(trainingCategory));

		trainingCategory.click();
	}


	// 検索結果①の表示確認
	public boolean isResult1Displayed() {

		wait.until(ExpectedConditions.visibilityOf(result1));

		return result1.isDisplayed();
	}


	// 検索結果②の表示確認
	public boolean isResult2Displayed() {

		wait.until(ExpectedConditions.visibilityOf(result2));

		return result2.isDisplayed();
	}


	// 指定した質問をクリックして回答表示確認
	public boolean openQuestion(int index) {

		// 検索結果が指定件数表示されるまで待つ
		wait.until(
				ExpectedConditions.numberOfElementsToBeMoreThan(
						By.cssSelector("dl[id^='question-h']"),
						index
				)
		);

		// 質問を取得
		WebElement question = questions.get(index);

		// 回答を取得
		WebElement answer =
				question.findElement(
						By.cssSelector("dd[id^='answer-h']")
				);

		// 質問をクリック
		question.click();

		// 回答が表示されるまで待つ
		wait.until(
				ExpectedConditions.visibilityOf(answer)
		);

		return answer.isDisplayed();
	}


	// 指定した回答までスクロール
	public void scrollToAnswer(int index) {

		WebElement question = questions.get(index);

		WebElement answer =
				question.findElement(
						By.cssSelector("dd[id^='answer-h']")
				);

		((JavascriptExecutor) webDriver)
				.executeScript(
						"arguments[0].scrollIntoView({block:'center'});",
						answer
				);
	}
}