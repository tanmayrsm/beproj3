/**
 * This java program uses Qoppa's jPDFImages 
 * to convert a PDF to JPEG images.
 * A JPEG image file is created for each page
 * contained in the PDF document. 
 *
 */
package jPDFImagesSamples;

import com.qoppa.pdfImages.PDFImages;

public class PDFToJPEGs 
{
    public static void main (String [] args)
    {
        try
        {
        	// open the PDF file
            PDFImages pdfDoc = new PDFImages ("input.pdf", null);
            // loop through pages
            for (int count = 0; count < pdfDoc.getPageCount(); ++count)
            {
            	// save the current page as a JPEG image 
        		// at a resolution of 150 dpi with a jpeg quality of 0.8
                pdfDoc.savePageAsJPEG(count, "output_" + count + ".jpg", 150, 0.8f);
            }
        }
        catch (Throwable t)
        {
        	t.printStackTrace();
        }
    }
}
