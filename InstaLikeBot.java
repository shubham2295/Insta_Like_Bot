import java.util.Scanner;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * @author Shubham Patel This script likes all the posts on Instagram for the
 *         account that user wants
 */
public class InstaLikeBot {

	public static void main(String[] args) throws InterruptedException {

		// Taking required data from user		
		Scanner input = new Scanner(System.in);
		System.out.println("Enter your username:");
		String userName = input.nextLine();
		System.out.println("Enter your password:");
		String password = input.nextLine();
		System.out.println("Enter a username for which you want to like all the posts:");
		String usertoLike = input.nextLine();
		input.close();

		// Initial setup
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.instagram.com/");

		// Login page locators
		By username = By.name("username");
		By pwd = By.name("password");
		By loginButton = By.xpath("//div[text()='Log In']");

		// Login process
		dowaitforElement(driver, username);
		driver.findElement(username).sendKeys(userName);
		driver.findElement(pwd).sendKeys(password);
		driver.findElement(loginButton).click();

		// Navigating to home page of the account
		By homepageButton = By.xpath("//a/*[local-name()='svg' and @aria-label='Home']");
		dowaitforElement(driver, homepageButton);
		driver.findElement(homepageButton).click();

		// Dismissing the notification pop-up
		By notnowNotification = By.xpath("//button[text()='Not Now']");
		dowaitforElement(driver, notnowNotification);
		driver.findElement(By.xpath("//button[text()='Not Now']")).click();

		// Searching and Navigating to the desired account
		By searchBox = By.xpath("//input[@type='text']");
		driver.findElement(searchBox).sendKeys(usertoLike);
		By searchResult = By.xpath("//div[@class='fuqBx ']/div/a");
		dowaitforElement(driver, searchResult);
		driver.findElement(searchResult).click();

		// Interacting with first post
		By firstpostLocator = By.xpath("(//div[@class='Nnq7C weEfm']/div/a)[1]");
		dowaitforElement(driver, firstpostLocator);
		WebElement firstpost = driver.findElement(firstpostLocator);
		firstpost.click();

		By likeButton = By.xpath("//span/*[local-name()='svg' and @aria-label='Like' and @height='24']");
		By unlikeButtonLocator = By.xpath("//span/*[local-name()='svg' and @aria-label='Unlike' and @height='24']");
		By nextCourser = By.xpath("//span/*[local-name()='svg' and @aria-label='Next']");

		WebElement nextButton = driver.findElement(nextCourser);

		// Liking all posts and skipping the posts which are already liked by user
		// before
		while (elementIsDisplayed(driver, nextCourser)) {
			if (elementIsDisplayed(driver, unlikeButtonLocator)) {
				nextButton.click();
			} else {
				dowaitforElement(driver, likeButton);
				WebElement likePost = driver.findElement(likeButton);
				likePost.click();
				nextButton.click();
			}
		}

		// Liking the last post
		dowaitforElement(driver, likeButton);
		WebElement likelastPost = driver.findElement(likeButton);
		likelastPost.click();
		driver.quit();

	}
	/*
	 * This method checks if the element is displayed or not and returns true or
	 * false accordingly
	 */

	public static boolean elementIsDisplayed(WebDriver driver, By locator) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 1);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			WebElement unlikeButton = driver.findElement(locator);
			return unlikeButton.isDisplayed();
		}

		catch (NoSuchElementException e) {
			return false;
		}

		catch (TimeoutException e) {
			return false;
		}
	}

	/*
	 * This methods takes the locator and waits explicitly until the element is
	 * visible and present on the DOM
	 */
	public static void dowaitforElement(WebDriver driver, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

}
