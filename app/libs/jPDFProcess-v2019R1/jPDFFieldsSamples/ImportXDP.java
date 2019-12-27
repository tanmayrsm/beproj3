/**
 * This sample Java program shows 
 * how to import data in xdp / xml format
 * into an XFA dynamic form.
 */
package jPDFFieldsSamples;

import com.qoppa.pdfFields.PDFFields;

public class ImportXDP
{
    public static void main (String [] args)
    {
        try
        {
            // Load the xfa dynamic form
            PDFFields pdfDoc = new PDFFields ("C:/test/xfa_form.pdf", null);
            
            // Import the fields data in xdp format
            pdfDoc.importXDP("C:/test/xfa_form_data.xdp");
            
            // Save the filled form 
            pdfDoc.saveDocument("C:/test/xfa_form_filled.pdf");
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }
}
