package jPDFPrintSamples;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import com.qoppa.pdfPrint.PDFPrint;

/**
 * This sample implements the Printable interface 
 * which can be useful when you need a finer control over the printing of PDF pages
 * In this sample, we're changing the rotation of the page printout by 180 degrees 
 * 
 * @author Qoppa Software
 * 
 */
public class MyPrintable implements Printable
{

	PDFPrint m_PDFPrint;
	
	public MyPrintable(PDFPrint pdfPrint)
	{
		m_PDFPrint = pdfPrint;
	}
	public static void main(String[] args)
	{
		try
		{
			PDFPrint pdfPrint = new PDFPrint("C:/myfolder/data1.pdf", null);
			PrinterJob printerJob = PrinterJob.getPrinterJob();
			if (printerJob.printDialog())
			{
				printerJob.setPrintable(new MyPrintable(pdfPrint));
				printerJob.print();
			}
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
	}

	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException
	{
		// apply a rotation of -180 degrees to the graphics and translate accordingly
		Graphics2D g2d= ((Graphics2D) graphics);
		g2d.rotate(-Math.toRadians(180));
		g2d.translate (-pageFormat.getWidth(), 0);
        g2d.translate (0, -pageFormat.getHeight());
        // call PDFprint to print the page with the transformed graphics object
		return m_PDFPrint.print(graphics, pageFormat, pageIndex);
	}

}
