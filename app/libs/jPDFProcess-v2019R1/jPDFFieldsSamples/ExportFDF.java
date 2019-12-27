/**
 * Qoppa Software - Sample Source Code
 */
package jPDFFieldsSamples;

import com.qoppa.pdfFields.PDFFields;

public class ExportFDF
{
    public static void main (String [] args)
    {
        try
        {
            // Load the document
            PDFFields pdfDoc = new PDFFields ("input.pdf", null);
            
            // Export field data in FDF format
            pdfDoc.exportAsFDF("output.fdf", true);
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }


}
