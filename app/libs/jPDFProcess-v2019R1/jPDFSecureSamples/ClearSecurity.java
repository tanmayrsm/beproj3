/**
 * Qoppa Software - Source Code Sample
 */
package jPDFSecureSamples;

import com.qoppa.pdfSecure.PDFSecure;

public class ClearSecurity
{
    public static void main (String [] args)
    {
        try
        {
            // Load the document
            PDFSecure pdfSecure = new PDFSecure ("input.pdf", null);
            
            // Clear security
            pdfSecure.clearPasswordPermissions("owner_password");

            // Save the document
            pdfSecure.saveDocument ("output.pdf");
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }
}