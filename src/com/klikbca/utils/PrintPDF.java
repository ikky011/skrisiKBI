package com.klikbca.utils;

import java.awt.Color;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.io.File;
import java.io.FileOutputStream;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class PrintPDF implements ITestListener {
	Singleton st = Singleton.Getinstance(); 
	
/*
	private String inputDir;
	private String outputDir;
	
	public JyperionListener (String inputDir, String outputDir){
		this.inputDir = inputDir;
		this.outputDir = outputDir;
	}
*/	
	
	/**
	 * Document
	 */
	private Document document = null;

	/**
	 * PdfPTables
	 */
	PdfPTable successTable = null, failTable = null;

	/**
	 * throwableMap
	 */
	private HashMap<Integer, Throwable> throwableMap = null;

	/**
	 * nbExceptions
	 */
	private int nbExceptions = 0;

	/**
	 * JyperionListener
	 */
	public PrintPDF() {
		log("JyperionListener()");

		this.document = new Document();
		this.throwableMap = new HashMap<Integer, Throwable>();
	}

	/**
	 * @see com.beust.testng.ITestListener#onTestSuccess(com.beust.testng.ITestResult)
	 */
	public void onTestSuccess(ITestResult result) {
		log("onTestSuccess(" + result + ")");
//		String file = System.getProperty("user.dir")+"\\"+"screenshot"+(new Random().nextInt())+".png";
		if (successTable == null) {
			this.successTable = new PdfPTable(new float[] { .3f, .3f, .1f });
			Paragraph p = new Paragraph("PASSED TESTS", new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.BOLD));
			p.setAlignment(Element.ALIGN_CENTER);
			PdfPCell cell = new PdfPCell(p);
			cell.setColspan(3);
			cell.setBackgroundColor(Color.GREEN);
			this.successTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Class"));
			cell.setBackgroundColor(Color.LIGHT_GRAY);
			this.successTable.addCell(cell);
			cell = new PdfPCell(new Paragraph("Method"));
			cell.setBackgroundColor(Color.LIGHT_GRAY);
			this.successTable.addCell(cell);
			cell = new PdfPCell(new Paragraph("Time (ms)"));
			cell.setBackgroundColor(Color.LIGHT_GRAY);
			this.successTable.addCell(cell);

			
			
		}

		PdfPCell cell = new PdfPCell(new Paragraph(result.getTestClass().toString()));
		this.successTable.addCell(cell);
		cell = new PdfPCell(new Paragraph(result.getName().toString()));
		this.successTable.addCell(cell);
		cell = new PdfPCell(new Paragraph("" + (result.getEndMillis() - result.getStartMillis())));
		this.successTable.addCell(cell);
		
		Throwable throwable = result.getThrowable();
		
//		if (throwable != null) {
//			this.throwableMap.put(new Integer(throwable.hashCode()), throwable);
//			this.nbExceptions++;
//			Paragraph excep = new Paragraph(
//					new Chunk(throwable.toString(), new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.UNDERLINE))
//							.setLocalGoto("" + throwable.hashCode()));
//			cell = new PdfPCell(excep);
//			this.successTable.addCell(cell);
//		} else {
//			this.successTable.addCell(new PdfPCell(new Paragraph("")));
//		}

		
	}
	// cell = new PdfPCell(new PdfAction(b.gambare()));
	/*
	 * File outputFile = new File(b.gambare().toString()); Image img =
	 * Image.getInstance(b.gambare());
	 */
	/*
	 * try { Image img1 = Image.getInstance(b.gambare()); cell = new
	 * PdfPCell(Image.getInstance(img)); this.successTable.addCell(cell); }
	 * catch (Exception e) { e.getMessage(); }
	 */

	/**
	 * @see com.beust.testng.ITestListener#onTestFailure(com.beust.testng.ITestResult)
	 */
	

	public void onTestFailure(ITestResult result) {
		log("onTestFailure(" + result + ")");

		if (this.failTable == null) {
			this.failTable = new PdfPTable(new float[] { .3f, .3f, .1f, .3f });
			this.failTable.setTotalWidth(20f);
			Paragraph p = new Paragraph("FAILED TESTS", new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.BOLD));
			p.setAlignment(Element.ALIGN_CENTER);
			PdfPCell cell = new PdfPCell(p);
			cell.setColspan(4);
			cell.setBackgroundColor(Color.RED);
			this.failTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Class"));
			cell.setBackgroundColor(Color.LIGHT_GRAY);
			this.failTable.addCell(cell);
			cell = new PdfPCell(new Paragraph("Method"));
			cell.setBackgroundColor(Color.LIGHT_GRAY);
			this.failTable.addCell(cell);
			cell = new PdfPCell(new Paragraph("Time (ms)"));
			cell.setBackgroundColor(Color.LIGHT_GRAY);
			this.failTable.addCell(cell);
			cell = new PdfPCell(new Paragraph("Exception"));
			cell.setBackgroundColor(Color.LIGHT_GRAY);
			this.failTable.addCell(cell);
		}

		PdfPCell cell = new PdfPCell(new Paragraph(result.getClass().toString()));
		this.failTable.addCell(cell);
		cell = new PdfPCell(new Paragraph(result.getName().toString()));
		this.failTable.addCell(cell);
		cell = new PdfPCell(new Paragraph("" + (result.getEndMillis() - result.getStartMillis())));
		this.failTable.addCell(cell);
		// String exception = result.getThrowable() == null ? "" :
		// result.getThrowable().toString();
		// cell = new PdfPCell(new Paragraph(exception));
		// this.failTable.addCell(cell);

		Throwable throwable = result.getThrowable();
		if (throwable != null) {
			this.throwableMap.put(new Integer(throwable.hashCode()), throwable);
			this.nbExceptions++;
			Paragraph excep = new Paragraph(
					new Chunk(throwable.toString(), new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.UNDERLINE))
							.setLocalGoto("" + throwable.hashCode()));
			cell = new PdfPCell(excep);
			this.failTable.addCell(cell);
		} else {
			this.failTable.addCell(new PdfPCell(new Paragraph("")));
		}
		
	}

	/**
	 * @see com.beust.testng.ITestListener#onTestSkipped(com.beust.testng.ITestResult)
	 */
	public void onTestSkipped(ITestResult result) {
		log("onTestSkipped(" + result + ")");
	}

	/**
	 * @see com.beust.testng.ITestListener#onStart(com.beust.testng.ITestContext)
	 */

	public void onStart(ITestContext context) {
		log("onStart(" + context + ")");
//		InformasiRekening a = new InformasiRekening();
		//ITestResult result = null;
//		String outdir = sd.outdir;
//		String path = "D:/workspace/TestingIBANK/pdf/" + outdir;
//		try {
//			PdfWriter.getInstance(this.document, new FileOutputStream(path + ".pdf"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		this.document.open();
//
//		Paragraph p = new Paragraph("Report Testing ibank.klikbca.com",
//				FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD, new Color(0, 0, 255)));
//
//		try {
//			this.document.add(p);
//			this.document.add(new Paragraph(new Date().toString()));
//		} catch (DocumentException e1) {
//			e1.printStackTrace();
//		}
	}

	/**
	 * @see com.beust.testng.ITestListener#onFinish(com.beust.testng.ITestContext)
	 */

	public void onFinish(ITestContext context) {

		
		
		log("onFinish(" + context + ")");
		String outdir = st.outdir;
		File dir = new File("D:/workspace/SkripsiTestingKBI/outputPDF/");
		String path = "D:/workspace/SkripsiTestingKBI/outputPDF/" + outdir;
		if(!dir.exists())
			dir.mkdir();
		
		try {
			PdfWriter.getInstance(this.document, new FileOutputStream(path + ".pdf"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.document.open();

		Paragraph pr = new Paragraph("Report Testing ibank.klikbca.com",
				FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD, new Color(0, 0, 255)));

		try {
			this.document.add(pr);
			this.document.add(new Paragraph(new Date().toString()));
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		
		try {
			if (this.failTable != null) {
				log("Added fail table");
				this.failTable.setSpacingBefore(15f);
				this.document.add(this.failTable);
				this.failTable.setSpacingAfter(15f);
			}

			if (this.successTable != null) {
				log("Added success table");
				this.successTable.setSpacingBefore(15f);
				this.document.add(this.successTable);
				this.successTable.setSpacingBefore(15f);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}

				
		
		 File directory = new File(st.path);
		 String folder = st.path;
		 int fileCount=directory.list().length;
		 Image image = null;
		 try{
			 for(int i = 1 ; i <= fileCount ; i++){
				 image = Image.getInstance(folder+i+".jpg");
				 image.scaleAbsolute(700f, 600f);
				 this.document.add(image);
			 }
			 
//				this.successTable.addCell(cell);
		 } catch (Exception e){
			 e.printStackTrace();
		 }
		this.document.close();
	}

	/**
	 * log
	 * 
	 * @param o
	 */
	public static void log(Object o) {
		// System.out.println("[JyperionListener] " + o);
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	public void onTestStart(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	
}
