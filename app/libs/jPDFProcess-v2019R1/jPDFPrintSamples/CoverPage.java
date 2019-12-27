/*
 * Created on May 22, 2008
 *
 */
package jPDFPrintSamples;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import com.qoppa.pdfPrint.PDFPrint;

public class CoverPage implements Printable
{
    private PDFPrint m_PDFPrint;
    
    public static void main (String [] args)
    {
        try
        {
            // Load PDF document
            PDFPrint pdfPrint = new PDFPrint ("input.pdf", null);
            
            // Create printer job
            PrinterJob pJob = PrinterJob.getPrinterJob();
            if (pJob.printDialog())
            {
                // Set the printable to our custom printable
                pJob.setPrintable (new CoverPage (pdfPrint));
                
                // Print
                pJob.print();
            }
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }

    public CoverPage (PDFPrint pdfPrint)
    {
        m_PDFPrint = pdfPrint;
    }

    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException
    {
        // Print the cover page here
        if (pageIndex == 0)
        {
            graphics.setFont(new Font ("sansserif", Font.BOLD, 36));
            graphics.drawString("Cover Page", 144, 144);
            return PAGE_EXISTS;
        }
        else
        {
            // Print the PDF page
            return m_PDFPrint.print(graphics, pageFormat, pageIndex-1);
        }
    }
    
    public static void paintWatermark (Graphics2D g)
    {
        // Watermark color (this can have transparency, but Java then makes the spool file very large)
        g.setColor(new Color (160, 160, 160));

        // Set the font
        g.setFont (new Font ("sansserif", Font.BOLD, 96));
        
        // Draw the watermark string, rotated 45 degrees
        g.rotate (Math.toRadians(45));
        g.drawString ("Watermark", 100, g.getFontMetrics().getMaxDescent());
    }
}
