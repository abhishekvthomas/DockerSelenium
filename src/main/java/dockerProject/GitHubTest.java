package dockerProject;

import static org.testng.Assert.assertTrue;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class GitHubTest{

	RemoteWebDriver driver;
//	WebDriver driver;

	@Parameters("browser")
	@BeforeTest
	public void beforeMethod(String browser) {

		if (browser.equals("chrome")) {

			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();

			ChromeOptions opt = new ChromeOptions();
			opt.addArguments("disable-infobars");
			opt.addArguments("--start-maximized");
			opt.addArguments("--disable-extensions");
			
			try {
				// add the url where docker is present
				URL url = new URL("http://localhost:4444/wd/hub");
				driver = new RemoteWebDriver(url, opt);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		else if (browser.equals("firefox")) {

			WebDriverManager.firefoxdriver().setup();
//			driver = new FirefoxDriver();
			
			FirefoxOptions opt = new FirefoxOptions();
			opt.addArguments("disable-infobars");
			opt.addArguments("--start-maximized");
			opt.addArguments("--disable-extensions");

			try {
				// add the url where docker is present
				URL url = new URL("http://localhost:4444/wd/hub");
				driver = new RemoteWebDriver(url, opt);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		driver.get("https://github.com/abhishekvthomas");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@Test
	public void getTitle() {
		assertTrue(driver.getTitle().contains("abhishekvthomas"));
	}

	@Test
	public void verifyDockerRepoExists() {
		List<WebElement> repoList = driver.findElements(By.cssSelector(".js-pinned-items-reorder-container a"));
		List<String> list = new ArrayList<String>();
		
		for(WebElement element: repoList) {
			list.add(element.getText());
		}
		
		assertTrue(list.contains("DockerSelenium"));
	}

	@AfterTest
	public void afterMethod() {
		driver.quit();
	}
}
