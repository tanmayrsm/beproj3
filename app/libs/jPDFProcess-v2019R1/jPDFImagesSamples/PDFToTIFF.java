/**
 * This Java program uses Qoppa's jPDFImages to
 * convert a PDF to a multi-page TIFF image file
 * using packbits compression. 
 *
 */
package jPDFImagesSamples;

import com.qoppa.pdf.TIFFOptions;
import com.qoppa.pdfImages.PDFImages;

public class PDFToTIFF 
{
    public static void main (String [] args)
    {
        try
        {
        	// open PDF document
            PDFImages pdfDoc = new PDFImages ("input.pdf", null);
            // define TIFF options such as compression
            TIFFOptions options = new TIFFOptions (150, TIFFOptions.TIFF_PACKBITS);
            // convert PDF to a single TIFF file
            pdfDoc.saveDocumentAsTIFF("output.tif", options);
        }
        catch (Throwable t)
        {
        	t.printStackTrace();
        }
    }
}
