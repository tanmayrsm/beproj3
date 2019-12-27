/**
 * Qoppa Software - Sample Source Code
 */
package jPDFFieldsSamples;

import com.qoppa.pdfFields.PDFFields;

public class ImportFDF
{
    public static void main (String [] args)
    {
        try
        {
            // Load the document
            PDFFields pdfDoc = new PDFFields ("input.pdf", null);
            
            // Import the data
            pdfDoc.importFDF("input.fdf");
            
            // Save the document
            pdfDoc.saveDocument("output.pdf");
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }
}
