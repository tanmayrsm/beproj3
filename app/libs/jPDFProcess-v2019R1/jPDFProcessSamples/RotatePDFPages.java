package jPDFProcessSamples;

import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.pdfProcess.PDFPage;

public class RotatePDFPages
{
	/* This sample code will open a PDF and rotate each page of the document by a 180 degrees */
	/* It then saves the rotated document */
    
    public static void main (String [] args)
    {
        try
        {
        	// load original PDF
            PDFDocument pdfDoc = new PDFDocument ("C:\\myfolder\\input.pdf", null);
            
             // Loop through all pages
            for (int i = 0; i < pdfDoc.getPageCount(); i++) 
            {
            	// get page in the original PDF 
                PDFPage page = pdfDoc.getPage(i);
                
                // change the page rotation to flip it by 180 degrees
                page.setPageRotation(180);
            }
            
            // save the document
            pdfDoc.saveDocument ("C:\\myfolder\\output.pdf");
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }

}
