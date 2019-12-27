/*
 * Created on Sep 24, 2008
 *
 */
package jPDFProcessSamples;

import com.qoppa.pdfProcess.PDFDocument;

public class FlattenFields
{
    public static void main (String [] args)
    {
        try
        {
            // Load the document
            PDFDocument pdfDoc = new PDFDocument ("input.pdf", null);
            
            // Flatten fields
            pdfDoc.flattenFields(false, false);
            
            // Save the document
            pdfDoc.saveDocument ("output.pdf");
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }
}
