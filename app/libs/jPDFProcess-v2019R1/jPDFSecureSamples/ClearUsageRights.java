package jPDFSecureSamples;

import com.qoppa.pdfSecure.PDFSecure;

public class ClearUsageRights
{
    public static void main (String [] args)
    {
        try
        {
            // Load the document
            PDFSecure pdfSecure = new PDFSecure ("input.pdf", null);
            
            // Clear usage rights
            pdfSecure.clearUsageRights();

            // Save the document
            pdfSecure.saveDocument ("output.pdf");
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }
}
