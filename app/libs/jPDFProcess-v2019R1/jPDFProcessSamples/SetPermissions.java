/*
 * Created on Sep 24, 2008
 *
 */
package jPDFProcessSamples;

import com.qoppa.pdf.permissions.PasswordPermissions;
import com.qoppa.pdfProcess.PDFDocument;

public class SetPermissions
{
    public static void main (String [] args)
    {
        try
        {
            // Load the document
            PDFDocument pdfDoc = new PDFDocument ("input.pdf", null);
            
            // Create permissions object to allow printing only
            PasswordPermissions perms = new PasswordPermissions (false);
            perms.setPrintAllowed(true);

            // Set document security
            pdfDoc.setPasswordPermissions("owner", "user", perms, null, PasswordPermissions.ENCRYPTION_AES_128);
            
            // Save the document
            pdfDoc.saveDocument ("output.pdf");
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }

}
