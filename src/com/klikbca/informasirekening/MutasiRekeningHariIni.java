package com.klikbca.informasirekening;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.klikbca.screenshotname.ScreenshotName;

import com.klikbca.utils.PrintPDF;
import com.klikbca.utils.Singleton;
@Listeners(PrintPDF.class)
public class MutasiRekeningHariIni {
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
		String path = (prop.getProperty("InformasiRekeningMutasiHarIni"));	// conver webdriver object to TakeScreenshot
		 
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
		st.outdir = prop.getProperty("PDFInformasiRekeningMutasiHarIni");
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

	@Test (priority = 0)
	public void inputusername() throws Exception {
		Properties prop = new Properties();
		// prop.load(new FileInputStream("kbi.properties"));
		InputStream stream = null;
		stream = new FileInputStream("kbi.properties");
		prop.load(stream);
		driver.findElement(By.id("user_id")).sendKeys(prop.getProperty("username"));
		takeSnapShot(driver);
	}
	@Test (priority = 1)
	public void inputpassword()throws Exception {
		Properties prop = new Properties();
		// prop.load(new FileInputStream("kbi.properties"));
		InputStream stream = null;
		stream = new FileInputStream("kbi.properties");
		prop.load(stream);
		driver.findElement(By.id("pswd")).sendKeys(prop.getProperty("password"));
		takeSnapShot(driver);
	
	}
	@Test (priority = 2)
	public void buttonlogin() throws Exception {
	//	driver.findElement(By.linkText("LOGIN")).click();
		driver.findElement(By.name("value(Submit)")).click();
		takeSnapShot(driver);
	
	}
	@Test (priority = 3)
	public void KlikInformasiRekening() throws Exception {
	driver.switchTo().frame(driver.findElement(By.name("menu")));
	driver.findElement(By.xpath("/html/body/table/tbody/tr/td[2]/table/tbody/tr[13]/td/a/font/b")).click();
		takeSnapShot(driver);
	
	}
	@Test (priority = 4)
	public void KlikMutasiRekening() throws Exception {
		driver.findElement(By.xpath("/html/body/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr[2]/td[2]/font/a")).click();
		takeSnapShot(driver);
	
	}
	@Test (priority = 5)
	public void LihatMutasiRekening() throws Exception {
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.name("atm")));
	driver.findElement(By.name("value(submit1)")).click();
		takeSnapShot(driver);
	
	}
	@Test(priority = 8)
	public void logout() throws Exception {
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.name("header")));
		driver.findElement(By.id("gotohome")).click();
		takeSnapShot(driver);
	}

	@Test(priority = 9)
	public void Close() throws Exception {
		
		driver.close();

	}
}
