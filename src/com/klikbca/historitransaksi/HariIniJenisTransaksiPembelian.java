package com.klikbca.historitransaksi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
public class HariIniJenisTransaksiPembelian {
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
		String path = (prop.getProperty("HistoriHariIniJenisTransaksiPembelian"));
		
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
		st.outdir = prop.getProperty("PDFHistoriHariIniJenisTransaksiPembelian"); 
			
	}

	@BeforeSuite
	public void beforeSuite() throws Exception {
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

	@Test(priority = 0)
	public void inputusername() throws Exception {
		Properties prop = new Properties();
		// prop.load(new FileInputStream("kbi.properties"));
		InputStream stream = null;
		stream = new FileInputStream("kbi.properties");
		prop.load(stream);
		driver.findElement(By.id("user_id")).sendKeys(prop.getProperty("username"));
		takeSnapShot(driver);
	}

	@Test(priority = 1)
	public void inputpassword() throws Exception {
		Properties prop = new Properties();
		// prop.load(new FileInputStream("kbi.properties"));
		InputStream stream = null;
		stream = new FileInputStream("kbi.properties");
		prop.load(stream);
		driver.findElement(By.id("pswd")).sendKeys(prop.getProperty("password"));
		takeSnapShot(driver);

	}

	@Test(priority = 2)
	public void buttonlogin() throws Exception {
		// driver.findElement(By.linkText("LOGIN")).click();
		driver.findElement(By.name("value(Submit)")).click();
		takeSnapShot(driver);

	}

	@Test(priority = 3)
	public void KlikHistoriTransaksi() throws Exception {
		driver.switchTo().frame(driver.findElement(By.name("menu")));
		driver.findElement(By.xpath("/html/body/table/tbody/tr/td[2]/table/tbody/tr[23]/td/a/font/b")).click();
		takeSnapShot(driver);

	}
	
	@Test(priority = 4)
	public void PilihJenisTransaksi() throws Exception {
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.name("atm")));
		boolean bvalue = false;
		List<WebElement> rdBtn =  driver.findElements(By.name("value(txntype)"));
		bvalue =  rdBtn.get(0).isSelected();
		if (bvalue == true) {
			rdBtn.get(1).click();
		} else {
		rdBtn.get(0).click();
		}
		takeSnapShot(driver);

	}
	@Test(priority = 5)
	public void PilihPembelian() throws Exception {
		Select jenisSelect = new Select(driver.findElement(By.id("jnstxn")));
		jenisSelect.selectByValue("Pembelian");
		takeSnapShot(driver);
	}
	@Test(priority = 6)
	public void ButtonOK() throws Exception {
	
		driver.findElement(By.name("value(submit)")).click();
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
