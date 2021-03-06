package com.klikbca.statustransaksi;

import java.io.File;
import java.io.FileInputStream;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.klikbca.screenshotname.ScreenshotName;
import com.klikbca.utils.PrintPDF;
import com.klikbca.utils.Singleton;
@Listeners(PrintPDF.class)
public class SemuaPertransaksiDibuatPertanggal31FebruariSampaiSekarang {
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
		String path = (prop.getProperty("StatusSemuaPerTransaksiDibuatPerTanggal31Februari"));
		
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
		st.outdir = prop.getProperty("PDFStatusSemuaPerTransaksiDibuatPerTanggal31Februari");
	}

	@BeforeSuite
	public void beforeSuite() throws Exception  {
		Properties prop = new Properties();
		// prop.load(new FileInputStream("kbi.properties"));
		InputStream stream = null;
		stream = new FileInputStream("kbi.properties");
		prop.load(stream);
		String urlProp = prop.getProperty("url");
		URL url = new URL(urlProp);
		String pathdriver = prop.getProperty("chromedriver");
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
	public void KlikStatusTransaksi() throws Exception {
	driver.switchTo().frame(driver.findElement(By.name("menu")));
	try {
		driver.findElement(By.linkText("Status Transaksi")).click();
		takeSnapShot(driver);
	} catch (Exception e) {
		driver.findElement(By.linkText("Transaction Status")).click();
		takeSnapShot(driver);
	}

	}
	@Test (priority = 5)
	public void PilihMonthdate() throws Exception {
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.name("atm")));
		Select jenisSelect = new Select(driver.findElement(By.id("startMt")));
		jenisSelect.selectByIndex(1);
		takeSnapShot(driver);
	
	}
	@Test (priority = 4)
	public void PilihStartdate() throws Exception {
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.name("atm")));
		Select jenisSelect = new Select(driver.findElement(By.id("startDt")));
		jenisSelect.selectByIndex(30);
		takeSnapShot(driver);
	
	}
	
	@Test (priority = 6)
	public void ButtonOK() throws Exception {
	driver.findElement(By.name("value(submit)")).click();
		takeSnapShot(driver);
	
	}
	@Test(priority = 7)
	public void alertHandling() {

		Alert alert = driver.switchTo().alert();

		String a = alert.getText();
		Reporter.log(a);
		alert.accept();
	}
	@Test(priority = 8)
	public void logout() throws Exception {
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.name("header")));
		driver.findElement(By.id("gotohome")).click();
		takeSnapShot(driver);
	}

	@Test(priority = 9)
	public void close() {

		driver.close();
	}
}
