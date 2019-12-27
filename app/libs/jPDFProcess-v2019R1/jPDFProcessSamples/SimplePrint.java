/*
 * Created on Sep 24, 2008
 *
 */
package jPDFProcessSamples;

import com.qoppa.pdfProcess.PDFDocument;

public class SimplePrint
{
    public static void main (String [] args)
    {
        try
        {
            // Load document
            PDFDocument pdfDoc = new PDFDocument ("input.pdf", null);
            
            // Print the document
            pdfDoc.print("my printer", null);
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }




}
