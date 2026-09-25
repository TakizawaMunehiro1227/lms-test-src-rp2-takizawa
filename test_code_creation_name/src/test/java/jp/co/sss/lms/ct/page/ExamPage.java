package jp.co.sss.lms.ct.page;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ExamPage {

    private WebDriver webDriver;
    private WebDriverWait wait;

    // 「試験有」の行の詳細ボタン
    @FindBy(xpath = "//tr[.//*[contains(normalize-space(.),'試験有')]]//input[@value='詳細']")
    private WebElement examTrainingDetail;
    
 // 「本日の試験」の詳細ボタン
    @FindBy(css = "form[action='/lms/exam/start'] input[value='詳細']")
    private WebElement examDetail;

    // 「試験を開始する」ボタン
    @FindBy(css = "form[action='/lms/exam/question'] input[value='試験を開始する']")
    private WebElement examStartButton;

    // 「確認画面へ進む」ボタン
    @FindBy(css = "input[value='確認画面へ進む']")
    private WebElement confirmButton;

    // 「回答を送信する」ボタン
    @FindBy(id = "sendButton")
    private WebElement submitButton;

    // 試験結果画面の「戻る」ボタン
    @FindBy(css = "form[action='/lms/exam/start'] input[value='戻る']")
    private WebElement backButton;

 // 過去の試験結果
    @FindBy(xpath = "//h3[contains(normalize-space(.),'過去の試験結果')]/following-sibling::table[1]//tbody/tr")
    private List<WebElement> examResults;

    
    //コンストラクタ
    public ExamPage(WebDriver webDriver) {

        this.webDriver = webDriver;

        this.wait =
                new WebDriverWait(webDriver, Duration.ofSeconds(5));

        PageFactory.initElements(webDriver, this);
    }
    

    // 「試験有」の研修日の詳細をクリック
    public void clickExamTrainingDetail() {

        wait.until(ExpectedConditions
                .elementToBeClickable(examTrainingDetail));

        examTrainingDetail.click();
    }
    
    public void clickExamDetail() {
        wait.until(ExpectedConditions.elementToBeClickable(examDetail));
        examDetail.click();
    }

    public void clickExamStart() {
        wait.until(ExpectedConditions.elementToBeClickable(examStartButton));
        examStartButton.click();
    }
    
    public void selectAnswer(int questionIndex, int answerIndex) {

        WebElement answer = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.id("answer-" + questionIndex + "-" + answerIndex)
            )
        );

        JavascriptExecutor js = (JavascriptExecutor) webDriver;

        js.executeScript(
            "arguments[0].scrollIntoView({block:'center'});",
            answer
        );

        answer.click();
    }

    public void clickConfirm() {

        JavascriptExecutor js = (JavascriptExecutor) webDriver;

        js.executeScript(
            "arguments[0].scrollIntoView({block:'center'});",
            confirmButton
        );

        wait.until(ExpectedConditions.elementToBeClickable(confirmButton));
        confirmButton.click();
    }

    public void clickSubmit() {

        JavascriptExecutor js = (JavascriptExecutor) webDriver;

        js.executeScript(
            "arguments[0].scrollIntoView({block:'center'});",
            submitButton
        );

        wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        submitButton.click();

        // 「回答を送信します。よろしいですか？」→ OK
        Alert alert = wait.until(
            ExpectedConditions.alertIsPresent()
        );

        alert.accept();
    }
    
    public boolean isExamResultDisplayed() {

        List<WebElement> results = webDriver.findElements(
            By.xpath("//h3[normalize-space()='過去の試験結果']/following-sibling::table[1]//tbody/tr")
        );


        return results.size() > 0;
    }

    public void clickBack() {

        JavascriptExecutor js = (JavascriptExecutor) webDriver;

        js.executeScript(
            "arguments[0].scrollIntoView({block:'center'});",
            backButton
        );

        wait.until(ExpectedConditions.elementToBeClickable(backButton));
        backButton.click();
    }
    
}
    
 