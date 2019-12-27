/*
 * Created on Sep 24, 2008
 *
 */
package jPDFProcessSamples;

import com.qoppa.pdfProcess.PDFDocument;

public class SimpleMerge
{
    public static void main (String [] args)
    {
        try
        {
            // Load the two documents
            PDFDocument pdfDoc1 = new PDFDocument ("input1.pdf", null);
            PDFDocument pdfDoc2 = new PDFDocument ("input2.pdf", null);
            
            // Append the second document to the first one
            pdfDoc1.appendDocument(pdfDoc2);
            
            // Save the merged document
            pdfDoc1.saveDocument ("output.pdf");
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }



}
