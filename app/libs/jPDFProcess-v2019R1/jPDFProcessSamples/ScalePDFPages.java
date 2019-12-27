package jPDFProcessSamples;

import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.pdfProcess.PDFPage;

public class ScalePDFPages
{
	private static final int PDF_TOTAL_WIDTH = 612; // 8.5 * 72
    private static final int PDF_TOTAL_HEIGHT = 792; // 11 * 72

    
    /* This sample code will open a PDF and copy the pages to a new PDF with rescaled pages of 8.5 x 11.*/
    /* Note that since v2017R1, there is a new method in the API: PDFPage.resizePage to resize pages in a PDF */
    /* See the ResizePDFPages sample */
    
    public static void main (String [] args)
    {
        try
        {
        	// load original PDF
            PDFDocument originalDoc = new PDFDocument ("input.pdf", null);
            
            // create a new PDF Document 
            PDFDocument scaledDoc = new PDFDocument();
            
            // Loop through all pages
            for (int i = 0; i < originalDoc.getPageCount(); i++) 
            {
            	// create a new page of the expected size in the new PDF 
                PDFPage newPage = scaledDoc.appendNewPage(PDF_TOTAL_WIDTH, PDF_TOTAL_HEIGHT);
                // get original page in the original PDF 
                PDFPage originalPage = originalDoc.getPage(i);
                // compute the new scale
                double scale = calculatePDFPageScale(originalPage);
                // append the content of the original page to the new PDF Page
                // using the computed scale so it is resized as expected
                newPage.appendPageContent(originalPage, 0, 0, scale, scale, null);
            }
            
            // save the scaled document
            scaledDoc.saveDocument ("output.pdf");
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }
    

    private static double calculatePDFPageScale(PDFPage page) 
    {
        double originalWidth = page.getDisplayWidth();
        double originalHeight = page.getDisplayHeight();
        double targetWidth = PDF_TOTAL_WIDTH;
        double targetHeight = PDF_TOTAL_HEIGHT;

        // No need to scale if original is smaller than target
        if (targetWidth - originalWidth >= 0 && targetHeight - originalHeight >= 0) {
            return 1;
        }

        // Skip scaling and just crop if either dimension is 0.
        if (originalWidth == 0 || originalHeight == 0) {
            return 1;
        }

        // Only scale as much as necessary. Maintain aspect ratio.
        double widthScale = targetWidth / originalWidth;
        double heightScale = targetHeight / originalHeight;
        return (widthScale <= heightScale) ? widthScale : heightScale;
    }

}
