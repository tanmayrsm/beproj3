/*
 * Created on Sep 24, 2008
 *
 */
package jPDFProcessSamples;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Vector;

import com.qoppa.pdf.annotations.Link;
import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.pdfProcess.PDFGraphics;
import com.qoppa.pdfProcess.PDFPage;
import com.qoppa.pdf.actions.GotoPageAction;

public class AddTOC
{
    public static void main (String [] args)
    {
        try
        {
            // Load the document
            PDFDocument pdfDoc = new PDFDocument ("input.pdf", null);
            
            // If document has 10 pages, remove 1 - We do this because the
            // demo version of PDFdocument only allows 10 pages, so the TOC pages
            // would not be added!!!
            while (pdfDoc.getPageCount() >= 10)
            {
                pdfDoc.deletePage(pdfDoc.getPageCount() - 1);
            }
            
            // Add a page at the beginning of the document and create a graphics object for it
            PDFPage tocPage = pdfDoc.insertNewPage(8.5 * 72, 11 * 72, 0);
            Graphics2D g2d = tocPage.createGraphics();
            
            // start entries at 2 inches from top and left
            int locX = 144;
            int locY = 144;
            
            // Set font and get font metrics
            g2d.setFont(PDFGraphics.HELVETICA.deriveFont(12f));
            FontMetrics fm = g2d.getFontMetrics();
            int lineSpacing = fm.getHeight() + (fm.getHeight() / 2);
            
            // Loop through pages in the document
            for (int pageIx = 1; pageIx < pdfDoc.getPageCount(); ++pageIx)
            {
                // Draw the TOC entry string
                String entryString = "TOC Entry " + pageIx + "................................. Page " + pageIx;
                g2d.drawString (entryString, locX, locY);
                
                // goto page action
                GotoPageAction gotoAction = new GotoPageAction (pdfDoc.getIPage(pageIx));
                Vector<GotoPageAction> actions = new Vector<GotoPageAction> ();
                actions.add(gotoAction);

                // Create link annotation
                Link l = tocPage.getDocument().getAnnotationFactory().createLink();
                l.setRectangle(new Rectangle (locX, locY - fm.getAscent() - 2, fm.stringWidth(entryString), fm.getHeight() + 4));
                l.setActions(actions);
                l.setBorderWidth(0);
                tocPage.addAnnotation(l);
                
                // Advance to the next line
                locY += lineSpacing;
            }
            
            // Save the document
            pdfDoc.saveDocument("output.pdf");
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }

}
