package dbmi;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.Dimension;

import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.Keys;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.IOException;

/**
 *@author: Yifan Ning, Eric Chou
 *@date: Dec 13, 2016 5:01:32 PM
 *
 */
public class Login extends TestBase{

    @Test
    public void test_user_login() throws InterruptedException { 
        try {  
            PrintWriter writer = new PrintWriter("test-output.txt", "UTF-8");     
            driver.get(appUrl);
            driver.manage().window().maximize();
            Thread.sleep(800);
            driver.findElement(By.linkText("Login")).click(); // click login 
            Thread.sleep(500);
            driver.findElement(By.name("email")).sendKeys("test@gmail.com"); // fill username
            driver.findElement(By.name("password")).sendKeys("testtest"); // fill password
            Thread.sleep(500);
            driver.findElement(By.xpath("//button[contains(.,'Login')]")).click(); // submit 
            Thread.sleep(1000);
            // driver.findElement(By.linkText("Logout")).click(); // logout

            // load test PMC article
            // driver.findElement(By.name("sourceURL")).sendKeys("http://localhost/PMC/PMC2686069.html");
            driver.findElement(By.name("sourceURL")).sendKeys("http://localhost/PMC/PMC3922121.html");
            // driver.findElement(By.name("sourceURL")).sendKeys("http://localhost/test/test.html");
            driver.findElement(By.xpath("//button[contains(.,'load')]")).click();
            Thread.sleep(1500);

            // import existing annotations
            driver.findElement(By.xpath("//button[contains(.,'Import')]")).click(); // import
            Thread.sleep(3000);

            /*
            ANNOTATE ARTICLE
            */
            WebDriverWait wait = new WebDriverWait(driver, 4000);
            wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("P16"))));
            // WebElement firstP = driver.findElement(By.id("drug1")); // for test file
            // WebElement firstP = driver.findElement(By.id("__p14"));
            WebElement firstP = driver.findElement(By.id("P16"));
            // create drug mention 1
            createDrug(firstP,0,0);

            //move cursor to a new word, create drug mention 2
            createDrug(firstP,50,50);

            // highlight passage until beginning of following paragraph
            // WebElement next = driver.findElement(By.id("__p15"));
            WebElement next = driver.findElement(By.id("P17"));
            highlightText(firstP, next);

            // create claim with the highlighted passage
            WebElement mp = driver.findElement(By.className("mp-menu-btn"));
            Actions claim = new Actions(driver);
            claim.moveToElement(mp).build().perform();
            driver.findElement(By.className("mp-main-menu-1")).click();
            Thread.sleep(2000);

            WebElement d1 =  driver.findElement(By.id("Drug1"));
            Select dropdown1 = new Select(d1);
            // dropdown1.selectByVisibleText("warfarin");
            dropdown1.selectByIndex(0);
            Thread.sleep(1000);

            WebElement d2 = driver.findElement(By.id("Drug2"));
            Select dropdown2 = new Select(d2);
            // dropdown2.selectByVisibleText("ambrisentan");
            dropdown2.selectByIndex(2);
            Thread.sleep(1000);

            WebElement precipitant = driver.findElement(By.id("drug1precipitant"));
            precipitant.click();
            Thread.sleep(2000);

            driver.findElement(By.xpath("//button[contains(.,'Save and Close')]")).click();
            Thread.sleep(2000);

            driver.findElement(By.id("finish-same-span-btn")).click();
            Thread.sleep(2000);

            // add data and material
            driver.findElement(By.xpath("//td[contains(@onclick, 'addDataCellByEditor(\"evRelationship\",0);')]")).click();
            Thread.sleep(500);
            driver.findElement(By.xpath("//input[contains(@value, 'refutes')]")).click();
            Thread.sleep(500);
            driver.findElement(By.xpath("//input[contains(@value, 'supports')]")).click();
            Thread.sleep(500);
            driver.findElement(By.xpath("//button[contains(.,'Save and Close')]")).click();
            Thread.sleep(2000);

            // Select data cells that require highlighted text to make sure they aren't able to be entered. 
            // addDataText("//td[contains(@onclick, 'addDataCellByEditor(\"participants\",\"0\");')]");
            // addDataText("//td[contains(@onclick, 'addDataCellByEditor(\"dose1\",\"0\");')]");
            // addDataText("//td[contains(@onclick, 'addDataCellByEditor(\"dose2\",\"0\");')]");
            // addDataText("//td[contains(@onclick, 'addDataCellByEditor(\"auc\",\"0\");')]");
            // addDataText("//td[contains(@onclick, 'addDataCellByEditor(\"cmax\",\"0\");')]");
            // addDataText("//td[contains(@onclick, 'addDataCellByEditor(\"clearance\",\"0\");')]");
            // addDataText("//td[contains(@onclick, 'addDataCellByEditor(\"halflife\",\"0\");')]");

            driver.findElement(By.xpath("//td[contains(@onclick, 'addDataCellByEditor(\"studytype\",\"0\");')]")).click();
            Thread.sleep(500);
            driver.findElement(By.xpath("//input[(@id='grouprandom' and @value='yes')]")).click();
            Thread.sleep(500);
            driver.findElement(By.xpath("//input[(@id='parallelgroup' and @value='yes')]")).click();
            Thread.sleep(500);
            driver.findElement(By.id("study-type-qs-clear")).click();
            Thread.sleep(500);
            driver.findElement(By.xpath("//input[(@id='grouprandom' and @value='no')]")).click();
            Thread.sleep(500);
            driver.findElement(By.xpath("//input[(@id='parallelgroup' and @value='no')]")).click();
            driver.findElement(By.xpath("//button[contains(.,'Save and Close')]")).click();
            Thread.sleep(2000);

            // TODO highlight some other passage (P17?) and use this to add data and material
            // WebElement e2 = driver.findElement(By.id("P18"));
            // highlightText(next, e2);

            // // add data and material
            // addDataMaterialByText();

            // // go through data and material that require text. Delete and re-highlight text to re-test next data and material box.
            // addDataText("//td[contains(@onclick, 'addDataCellByEditor(\"dose1\",\"0\");')]");
            // Thread.sleep(1000);
            // // TODO go up more parents until found
            // WebElement topElement = driver.findElement(By.id("tabs-1"));
            // WebElement parentElement = topElement.findElement(By.id("mp-data-form-dose1"));
            // writer.println("PARENT ELEMENT" + parentElement);
            // parentElement.findElement(By.xpath("//input[(@id='drug1Dose')]")).sendKeys("10");
            // Thread.sleep(1000);
            // save();
            // Thread.sleep(500);
            // Select formulation = new Select(parentElement.findElement(By.id("drug1Formulation")));
            // // dropdown2.selectByVisibleText("ambrisentan");
            // formulation.selectByIndex(1);
            // Thread.sleep(500);
            // parentElement.findElement(By.xpath("//input[(@id='drug1Duration')]")).sendKeys("20");
            // Thread.sleep(500);
            // Select regimen = new Select(parentElement.findElement(By.id("drug1Regimens")));
            // // dropdown2.selectByVisibleText("ambrisentan");
            // regimen.selectByIndex(1);
            // Thread.sleep(500);
            // delete();

            // edit drug claim - check dropdown menus for "Relationships" and "Methods"
            // driver.findElement(By.className("btn btn-success")).click();
            driver.findElement(By.xpath("//button[contains(.,'Edit Claim')]")).click();
            Thread.sleep(1500);

            WebElement methods = driver.findElement(By.id("method"));
            Select dropdownM = new Select(methods);
            List<WebElement> dd = dropdownM.getOptions();

            // Get length
            // writer.println(dd.size() + " methods options");

            // Loop to print one by one
            for (int i = 0; i < dd.size(); i++) {
                writer.println("Method index " + i + ": " + dd.get(i).getText());
                String method = dd.get(i).getText();
                dropdownM.selectByIndex(i);
                Thread.sleep(2000);

                // inner loop for the new sets of relationships that come up for each method.
                WebElement relationships = driver.findElement(By.id("relationship"));
                Select dropdownR = new Select(relationships);
                List<WebElement> de = dropdownR.getOptions();
                List<String> relationshipList = new ArrayList<String>();

                for(int j = 0; j < de.size(); j++) {
                    // writer.println(de.get(j).getAttribute("disabled"));
                    if (de.get(j).getAttribute("disabled") == null) {
                        // writer.println("RELATIONSHIP INDEX " + j + ": " + de.get(j).getText());
                        String r = de.get(j).getText();
                        relationshipList.add(r);
                    }
                }
                writer.println("Relationships:\n" + relationshipList);

                // make sure relationship lists are correct for each method
                if (method.equals("DDI clinical trial")) {
                    List<String> correctR = new ArrayList<>(Arrays.asList("interact with", "inhibits", "substrate of"));
                    if (relationshipList.equals(correctR)) {
                        writer.println("CORRECT set of relationships\n");
                    }
                    else {
                        writer.println("ALERT: this set of relationships is incorrect for " + method + "\n");
                    }
                }
                else if (method.equals("Phenotype clinical study")) {
                    List<String> correctR = new ArrayList<>(Arrays.asList("inhibits", "substrate of"));
                    if (relationshipList.equals(correctR)) {
                        writer.println("CORRECT set of relationships\n");
                    }
                    else {
                        writer.println("ALERT: this set of relationships is incorrect for " + method + "\n");
                    }
                }
                else if (method.equals("Case Report")) {
                    List<String> correctR = new ArrayList<>(Arrays.asList("interact with"));
                    if (relationshipList.equals(correctR)) {
                        writer.println("CORRECT set of relationships\n");
                    }
                    else {
                        writer.println("ALERT: this set of relationships is incorrect for " + method + "\n");
                    }
                }
                else if (method.equals("Statement")) {
                    List<String> correctR = new ArrayList<>(Arrays.asList("interact with", "inhibits", "substrate of"));
                    if (relationshipList.equals(correctR)) {
                        writer.println("CORRECT set of relationships\n");
                    }
                    else {
                        writer.println("ALERT: this set of relationships is incorrect for " + method + "\n");
                    }
                }
                else if (method.equals("Experiment")) {
                    List<String> correctR = new ArrayList<>(Arrays.asList("inhibits", "substrate of", "has metabolite", "controls formation of", "inhibition constant"));
                    if (relationshipList.equals(correctR)) {
                        writer.println("CORRECT set of relationships\n");
                    }
                    else {
                        writer.println("ALERT: this set of relationships is incorrect for " + method + "\n");
                    }
                }
            }

            // delete claim, close annotator
            // need to go to edit menu
            driver.findElement(By.id("annotator-delete")).click();
            Thread.sleep(1500);
            driver.findElement(By.id("claim-delete-confirm-btn")).click();
            Thread.sleep(2000);

            // delete drug annotations
            deleteDrug(firstP,0,0);

            deleteDrug(firstP,5,50);
            Thread.sleep(4000);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createDrug(WebElement element, int x, int y) throws InterruptedException
    {
        Actions action = new Actions(driver);
        action.click();
        Thread.sleep(2000);
        action.moveToElement(element,x,y).doubleClick().build().perform();
        Thread.sleep(2000);
        driver.findElement(By.className("hl-adder-btn")).click();
        Thread.sleep(1000);
    }

    public void deleteDrug(WebElement element, int x, int y) throws InterruptedException
    {
        Actions action = new Actions(driver);
        // action.click();
        Thread.sleep(2000);
        // action.moveToElement(element,x,y).click();
        action.moveToElement(element,x,y).doubleClick().build().perform();
        Thread.sleep(2000);
        // driver.findElement(By.className("annotator-delete")).click();
        Thread.sleep(1000);
    }

    public void highlightText(WebElement e1, WebElement e2) throws InterruptedException
    {
        Actions highlight = new Actions(driver);
        highlight.moveToElement(e1,0,0).click();
        highlight.keyDown(Keys.SHIFT);
        highlight.moveToElement(e2,0,0).click();
        highlight.keyUp(Keys.SHIFT);
        highlight.build().perform();
        Thread.sleep(2000);
    }

    public void addDataText(String s) throws InterruptedException
    {   
        driver.findElement(By.xpath(s)).click();
        Thread.sleep(1000);
        driver.findElement(By.id("select-text-dialog-close")).click();
        Thread.sleep(2000);
    }

    public void addDataMaterialByText() throws InterruptedException
    {
        Actions add = new Actions(driver);
        add.moveToElement(driver.findElement(By.className("mp-menu-btn"))).build().perform();
        add.moveToElement(driver.findElement(By.className("mp-main-menu-2"))).build().perform();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[contains(.,'interact with')]")).click();
        Thread.sleep(2000);
    }

    public void save() throws InterruptedException
    {
        driver.findElement(By.xpath("//button[contains(.,'Save')]")).click();
    }

    public void delete() throws InterruptedException
    {
        driver.findElement(By.className("annotator-delete")).click();
        driver.findElement(By.id("data-delete-confirm-btn")).click();
    }
}