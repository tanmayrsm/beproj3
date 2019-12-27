package jPDFProcessSamples;

import java.awt.Color;
import java.awt.Font;
import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.pdfProcess.PDFGraphics;
import com.qoppa.pdfProcess.PDFPage;

public class AddText
{
    public static void main (String [] args)
    {
        try
        {
            // Load document
            PDFDocument pdfDoc = new PDFDocument ("C:\\myfolder\\input.pdf", null);
            
            // draw text content into a PDF
            PDFPage page = pdfDoc.getPage(0);
            
            // Helvetica 12 font
            Font helveticaFont = PDFGraphics.HELVETICA.deriveFont(12f);            

            // draw text on the page 
            page.drawText("NEW TEXT", helveticaFont, Color.BLUE, 494, 14, null);

            // Save the document
            pdfDoc.saveDocument ("C:\\myfolder\\output.pdf");
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }
}
