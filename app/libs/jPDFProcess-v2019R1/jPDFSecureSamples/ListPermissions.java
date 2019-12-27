/**
 * Qoppa Software - Source Code Sample
 */
package jPDFSecureSamples;

import com.qoppa.pdf.permissions.AllPDFPermissions;
import com.qoppa.pdf.permissions.DocMDPPermissions;
import com.qoppa.pdf.permissions.IPDFPermissions;
import com.qoppa.pdf.permissions.PasswordPermissions;
import com.qoppa.pdf.permissions.UsageRightsPermissions;
import com.qoppa.pdfSecure.PDFSecure;

public class ListPermissions
{

    public static void main (String [] args)
    {
        try
        {
            // Load the document
            PDFSecure pdfSecure = new PDFSecure ("input.pdf", null);
            
            // Check if there are open and permissions passwords
            System.out.println ("Open Password - " + getYesNo (pdfSecure.hasOpenPassword()));
            System.out.println ("Permission Password - " + getYesNo(pdfSecure.hasPermissionsPassword()));
            System.out.println ();
            
            AllPDFPermissions allPDFPerms = pdfSecure.getPDFPermissions();
            
            // List the Password permissions
            PasswordPermissions passwordPerms = allPDFPerms.getPasswordPermissions();
            listPermissions(passwordPerms, "Password permissions");
            
            // List the DocMDP/Certifying Signature permissions
            DocMDPPermissions docMDPPerms = allPDFPerms.getDocMDPPermissions();
            if (docMDPPerms != null)
            {
            	listPermissions(docMDPPerms, "DocMDP permissions");
            }
            
            // List the Usage Rights permissions
            UsageRightsPermissions urPerms = allPDFPerms.getUsageRightsPermissions();
            if (urPerms != null)
            {
            	listPermissions(urPerms, "Usage Rights permissions");
            }
            
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }
    
    private static void listPermissions(IPDFPermissions perms, String label)
    {
    	System.out.println("\n" + label + ":\n");
        System.out.println ("Assemble Document - " + getYesNo(perms.isAssembleDocumentAllowed()));
        System.out.println ("Change Document - " + getYesNo(perms.isChangeDocumentAllowed()));
        System.out.println ("Extract Content - " + getYesNo(perms.isExtractTextGraphicsAllowed()));
        System.out.println ("Extract Content for Accessibility - " + getYesNo(perms.isExtractTextGraphicsForAccessibilityAllowed()));
        System.out.println ("Fill Form Fields - " + getYesNo(perms.isFillFormFieldsAllowed()));
        System.out.println ("Modify Annotations - " + getYesNo(perms.isModifyAnnotsAllowed()));
        System.out.println ("Print - " + getYesNo(perms.isPrintAllowed()));
        System.out.println ("Print High Resolution - " + getYesNo(perms.isPrintHighResAllowed()));
    }
    
    private static String getYesNo (boolean yes)
    {
        if (yes)
        {
            return "Yes";
        }
        else
        {
            return "No";
        }
    }
}
