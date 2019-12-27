/**
 * Qoppa Software - Source Code Sample
 */
package jPDFSecureSamples;

import com.qoppa.pdf.permissions.PasswordPermissions;
import com.qoppa.pdfSecure.PDFSecure;

public class SetPermissions
{
    public static void main (String [] args)
    {
        try
        {
            // Load the document
            PDFSecure pdfSecure = new PDFSecure ("input.pdf", null);
            
            // Create permissions object to allow printing only
            PasswordPermissions perms = new PasswordPermissions (false);
            perms.setPrintAllowed(true);

            // Set document security
            pdfSecure.setPasswordPermissions("owner", "user", perms, null, PasswordPermissions.ENCRYPTION_AES_128);
            
            // Save the document
            pdfSecure.saveDocument ("output.pdf");
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }

}
