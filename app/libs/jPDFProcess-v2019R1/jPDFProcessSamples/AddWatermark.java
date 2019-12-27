/*
 * Created on Sep 24, 2008
 *
 */
package jPDFProcessSamples;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.pdfProcess.PDFGraphics;
import com.qoppa.pdfProcess.PDFPage;

public class AddWatermark
{
    public static void main (String [] args)
    {
        try
        {
            // Load document
            PDFDocument pdfDoc = new PDFDocument ("input.pdf", null);
            
            // Loop through all pages
            Font wmFont = PDFGraphics.HELVETICA.deriveFont(64f);
            for (int pageIx = 0; pageIx < pdfDoc.getPageCount(); ++pageIx)
            {
                // Get the page
                PDFPage page = pdfDoc.getPage(pageIx);
                
                // Get a graphics object to draw onto the page
                Graphics2D g2d = page.createGraphics();
                
                // Draw watermark
                g2d.setColor (new Color (160, 160, 160, 160));
                g2d.setFont(wmFont);
                g2d.translate(72, 72 + g2d.getFontMetrics().getAscent());
                g2d.rotate(Math.toRadians(45));
                g2d.drawString ("Confidential", 0, 0);
            }
            
            // Save the document
            pdfDoc.saveDocument ("output.pdf");
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }
}
