package jPDFProcessSamples;

import com.qoppa.pdf.PrintListener;
import com.qoppa.pdf.dom.IPDFDocument;
import com.qoppa.pdfProcess.PDFDocument;

public class SimplePrintWithPrintListener
{
    public static void main (String [] args)
    {
        try
        {
            // Load document
            PDFDocument pdfDoc = new PDFDocument ("input.pdf", null);
            
            // Create the print listener
            PrintListener listener = new PrintListener() {
				public void pagePrinting(IPDFDocument document, int pageIndex)
				{
					System.out.println("Printing page " + (pageIndex + 1) + " of " + document.getPageCount());
				}
            };
            
            // Add the print listener
            pdfDoc.addPrintListener(listener);
            
            // Print the document
            pdfDoc.print(null);
            
            // Remove the print listener
            pdfDoc.removePrintListener(listener);
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }

}
