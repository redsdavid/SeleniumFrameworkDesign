package SelfStudyAcademy.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import SelfStudyAcademy.SeleniumFrameworkDesign.pageobjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	
	public WebDriver driver;
	public LandingPage landingPage;
	
	public WebDriver initializeDriver() throws IOException {
		
		//Create global properties: 
		
		Properties prop= new Properties();
		//FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "src\\main\\java\\SelfStudyAcademy\\SeleniumFrameworkDesign\\resources\\GlobalData.properties");
		FileInputStream fis = new FileInputStream("C:\\Users\\david.velezs\\eclipse-workspace\\SeleniumFrameworkDesign\\src\\main\\java\\SelfStudyAcademy\\SeleniumFrameworkDesign\\resources\\GlobalData.properties");
		prop.load(fis);
		String browserName = System.getProperty("browser")!=null ? System.getProperty("browser") :prop.getProperty("browser");
		
		if (browserName.contains("chrome")) {
			ChromeOptions options = new ChromeOptions();
			WebDriverManager.chromedriver().setup();
			if(browserName.contains("headless")) {
				options.addArguments("--headless");		
				driver = new ChromeDriver(options);
			}else {
				driver = new ChromeDriver();
			}
			driver.manage().window().setSize(new Dimension(1440, 900)); // Full Screen
			
		}else if(browserName.equalsIgnoreCase("firefox")) {
			//Firefox
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
		else if (browserName.equalsIgnoreCase("edge")) {
			// Edge
			EdgeOptions options = new EdgeOptions();			
			options.addArguments("--start-maximized");
//			options.setCapability("ms:edgeOptions", "{ \"excludeSwitches\": [\"enable-automation\"] }");
		    //options.setExperimentalOption("excludeSwitches", List.of("disable-popup-blocking"));
			Map<String, Object> prefs = new LinkedHashMap<>();
			prefs.put("user_experience_metrics.personalization_data_consent_enabled", Boolean.valueOf(true));
			options.setExperimentalOption("prefs", prefs);
			driver = new EdgeDriver(options);
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	}
	
	@BeforeMethod(alwaysRun = true)
	public LandingPage launchApplication() throws IOException {
		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goTo();
		return landingPage;
	}
	
	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}
	
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
		
		//read JSON to String
		String jsonContent = FileUtils.readFileToString(new File(filePath),
				StandardCharsets.UTF_8);
		
		// String to HashMap, (With Jackson Databind, get from mvn dependencies)
		ObjectMapper mapper = new ObjectMapper();
		List <HashMap<String,String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>(){
		});
		return data;
	}
	
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
		String userDir = System.getProperty("user.dir");
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(userDir + "//reports//" + testCaseName +".png");
		FileUtils.copyFile(source, file);
		return userDir + "//reports//" + testCaseName +".png";
	}

}
