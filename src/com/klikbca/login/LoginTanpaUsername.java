package com.klikbca.login;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.klikbca.screenshotname.ScreenshotName;
import com.klikbca.utils.PrintPDF;
import com.klikbca.utils.Singleton;
@Listeners(PrintPDF.class)
public class LoginTanpaUsername {
	static ScreenshotName src = new ScreenshotName();
	WebDriver driver;
	

		
	public static void takeSnapShot(WebDriver webdriver) throws Exception {
		Properties prop = new Properties();
		// prop.load(new FileInputStream("kbi.properties"));
		InputStream stream = null;
		stream = new FileInputStream("kbi.properties");
		prop.load(stream);
		// conver webdriver object to TakeScreenshot
		
		// conver webdriver object to TakeScreenshot
		String path =(prop.getProperty("LoginTanpaUsername"));	// conver webdriver object to TakeScreenshot
		 
		TakesScreenshot scrShot = ((TakesScreenshot) webdriver);

		// call getScreemshotAs method to create image file
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

		// move image file to new destination
		String imgdest = path + src.getN() + ".jpg";
		File Destfile = new File(imgdest);

		// copy file at destionation
		FileUtils.copyFile(SrcFile,Destfile);
		String img = "<img src=\"" + imgdest + "\" alt=\"\"/>";
		Reporter.log(img);
		Singleton st = Singleton.Getinstance();
		st.path = path;
		st.outdir = prop.getProperty("PDFLoginTanpaUsername");

	}

	@BeforeSuite
	public void beforeSuite() throws Exception  {
		Properties prop = new Properties();
		// prop.load(new FileInputStream("kbi.properties"));
		InputStream stream = null;
		stream = new FileInputStream("kbi.properties");
		prop.load(stream);
		String urlProp = prop.getProperty("url");
		URL url = new URL(urlProp);String pathdriver = prop.getProperty("chromedriver");
		System.setProperty("webdriver.chrome.driver", pathdriver);
		driver = new ChromeDriver();
		driver.navigate().to(url);
		driver.manage().window().maximize();
		takeSnapShot(driver);
	}
	@Test(priority = 1)
	public void inputform() throws Exception {
	
		driver.findElement(By.id("pswd")).sendKeys("aaaaaa");
		takeSnapShot(driver);
		driver.findElement(By.name("value(Submit)")).click();
	}
	@Test(priority = 1)
	public void alerthandling() throws Exception {
		Alert alrt = driver.switchTo().alert();
		Reporter.log(alrt.getText());
		alrt.accept();
	
		// driver.close();
	}
	@Test(priority = 8)
	public void close()  {
		
		driver.close();
	}
	
}
