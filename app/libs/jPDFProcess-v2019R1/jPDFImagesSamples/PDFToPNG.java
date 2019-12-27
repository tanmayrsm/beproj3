/**
 * This java program uses Qoppa's jPDFImages 
 * to convert a PDF to PNG images.
 * A PNG image file is created for each page
 * contained in the PDF document. 
 *
 */
package jPDFImagesSamples;

import com.qoppa.pdfImages.PDFImages;

public class PDFToPNG 
{
    public static void main (String [] args)
    {
        try
        {
        	// open PDF document
            PDFImages pdfDoc = new PDFImages ("C:\\myfolder\\input.pdf", null);
            // loop through pages 
        	for (int count = 0; count < pdfDoc.getPageCount(); ++count)
        	{
        		// convert current page to PNG at a resolution of 150 dpi
        		pdfDoc.savePageAsPNG(count, "C:\\myfolder\\output_" + count + ".png", 150);
        	}
        }
        catch (Throwable t)
        {
        	t.printStackTrace();
        }
    }
}
