package jPDFProcessSamples;

import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.io.File;
import java.text.DateFormat;
import java.util.Date;

import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.pdfProcess.PDFGraphics;
import com.qoppa.pdfProcess.PDFPage;

public class FileAttachments {

	public static void main (String [] args)
	{
	        try
	        {
	            // Load PDF document
	            PDFDocument pdfDoc = new PDFDocument ();
	            
	            PDFPage newPage = pdfDoc.appendNewPage(new PageFormat().getWidth(), new PageFormat().getHeight());
	            
	            // Draw to the page
	            Graphics2D g2d = newPage.createGraphics();
	            g2d.setFont (PDFGraphics.HELVETICA.deriveFont(24f));
	            g2d.drawString("Database Change Request from User", 100, 100);
	            g2d.drawString("Date " + DateFormat.getInstance().format(new Date()), 100, 300);
	            g2d.drawString("3 files attached ", 100, 400);
	            
	            // embed a Word File
	            pdfDoc.addEmbeddedFile(new File("C:\\test\\sampleWord.doc"));
	            
	            // embed an Excel File
	            pdfDoc.addEmbeddedFile(new File("C:\\test\\sampleExcel.xls"));
	            
	            // embed an Image File
	            pdfDoc.addEmbeddedFile(new File("C:\\test\\SampleImage.jpg"));
	            
	            
	            // Add the page to the document and save it
	            pdfDoc.saveDocument("C:\\test\\output.pdf");

	        }
	        catch (Throwable t)
	        {
	            t.printStackTrace();
	        }
	    }
	}
