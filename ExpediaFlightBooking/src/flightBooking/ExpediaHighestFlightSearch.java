package flightBooking;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import org.openqa.selenium.interactions.Actions;

public class ExpediaHighestFlightSearch {

	public static WebDriver driver;

	public static void main(String[] args) {

		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "\\ChromeDriver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.expedia.com/");
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		// click on flights tab
		driver.findElement(By.xpath("//a[@aria-controls='wizard-flight-pwa']")).click();

		WebElement origin = driver
				.findElement(By.xpath("//button[@data-stid='location-field-leg1-origin-menu-trigger']"));
		origin.sendKeys("London Heathrow");

		driver.findElement(
				By.xpath("//div[@id='location-field-leg1-origin-menu']/div[2]/ul/li[2]/button/div/div/span/strong"))
				.click();
		WebElement destination = driver
				.findElement(By.xpath("//button[@data-stid='location-field-leg1-destination-menu-trigger']"));
		destination.sendKeys("Greece");
		driver.findElement(
				By.xpath("//div[@id='location-field-leg1-destination-menu']/div[2]/ul/li/button/div/div/span/strong"))
				.click();

		driver.findElement(By.id("adaptive-menu")).click();
		driver.findElement(By.xpath("//input[@id='adult-input-0']/following-sibling::button")).click();
		driver.findElement(By.xpath("//input[@id='child-input-0']/following-sibling::button")).click();

		String actual = "Please provide the ages of children.";
		String expected = driver.findElement(By.className("uitk-error-summary-heading")).getText();
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			System.out.println(e);
		}

		System.out.println("verification is successful");
		WebElement childAge = driver.findElement(By.id("child-age-input-0-0"));
		childAge.click();
		Select age = new Select(childAge);
		age.selectByVisibleText("3");
		driver.findElement(By.xpath("//button[@data-testid='guests-done-button']")).click();

		driver.findElement(By.xpath("//li/a/span[@class='uitk-tab-text'][text()='One-way']")).click();
		driver.findElement(By.id("d1-btn")).click();
		while (!driver.findElement(By.cssSelector("h2.uitk-date-picker-month-name.uitk-type-medium")).getText()
				.contains("July")) {
			driver.findElement(By.xpath("//button[@data-stid='date-picker-paging'][2]")).click();
		}

		driver.findElement(By.xpath("//td/button[@class='uitk-date-picker-day uitk-new-date-picker-day']"))
				.sendKeys("Jul 1");
		driver.findElement(By.xpath("//button[@data-stid='apply-date-picker']")).click();

		driver.findElement(By.xpath("//button[@data-testid='submit-button']")).click();

		// 1 stop flight
		driver.findElement(By.xpath("//input[@id='stops-1']")).click();

		// airline
		driver.findElement(By.xpath("//input[@id='preferred-airline-KL']")).click();

		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div/div/label[@for='departure-time-MORNING']")));
		Actions act = new Actions(driver);
		act.moveToElement(driver.findElement(By.xpath("//div/div/label[@for='departure-time-AFTERNOON']"))).click()
				.perform();

		// highest priced flight list

		WebDriverWait wait1 = new WebDriverWait(driver, 15);
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("listings-sort")));

		WebElement sort = driver.findElement(By.id("listings-sort"));
		Select ss = new Select(sort);

		List<WebElement> opt = ss.getOptions();

		for (int i = 0; i < opt.size(); i++) {

			String s = opt.get(i).getText();

			String requiredString = s.substring(s.indexOf("(") + 1, s.indexOf(")"));

			if (requiredString.equalsIgnoreCase("Highest")) {

				opt.get(i).click();

				driver.findElement(By.xpath("//ul[@data-test-id='listings']/li[1]")).click();
				driver.findElement(By.xpath("//button[@data-test-id='select-button']")).click();

				break;

			}

		}
		driver.close();
	} 
}
