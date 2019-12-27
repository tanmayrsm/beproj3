/*
 * Created on Sep 24, 2008
 *
 */
package jPDFProcessSamples;

import java.io.FileOutputStream;

import com.qoppa.pdf.TIFFOptions;
import com.qoppa.pdfProcess.PDFDocument;

public class PDFToTIFF
{
    public static void main (String [] args)
    {
        try
        {
            PDFDocument pdfDoc = new PDFDocument ("input.pdf", null);
            TIFFOptions options = new TIFFOptions (150, TIFFOptions.TIFF_PACKBITS);
            
            FileOutputStream outStream = new FileOutputStream ("output.tif");
            pdfDoc.saveDocumentAsTIFF(outStream, options);
            outStream.close();
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }
}
