package jPDFSecureSamples;

import com.qoppa.pdf.permissions.AllPDFPermissions;
import com.qoppa.pdf.permissions.Restrictions;
import com.qoppa.pdfSecure.PDFSecure;

public class ListAllPermissions
{
    public static void main (String [] args)
    {
        try
        {
            // Load the document
            PDFSecure pdfSecure = new PDFSecure ("input.pdf", null);
            
            // Get the current permissions
            AllPDFPermissions perms = pdfSecure.getPDFPermissions();
            boolean checkOwnerPassword = true;
            
            // List permissions
            System.out.println ("Assemble Document - " + getYesNo(perms.isAssembleDocumentAllowed(checkOwnerPassword)));
            System.out.println ("Change Document - " + getYesNo(perms.isChangeDocumentAllowed(checkOwnerPassword)));
            System.out.println ("Extract Content - " + getYesNo(perms.isExtractTextGraphicsAllowed(checkOwnerPassword)));
            System.out.println ("Extract Content for Accessibility - " + getYesNo(perms.isExtractTextGraphicsForAccessibilityAllowed(checkOwnerPassword)));
            System.out.println ("Fill Form Fields - " + getYesNo(perms.isFillFormFieldsAllowed(checkOwnerPassword)));
            System.out.println ("Modify Annotations - " + getYesNo(perms.isModifyAnnotsAllowed(checkOwnerPassword)));
            System.out.println ("Print - " + getYesNo(perms.isPrintAllowed(checkOwnerPassword)));
            System.out.println ("Print High Resolution - " + getYesNo(perms.isPrintHighResAllowed(checkOwnerPassword)));
            System.out.println();
            
            // List the restrictions
            System.out.println ("Assemble Document - " + getRestrictions(perms.getAssembleDocumentRestrictions(checkOwnerPassword)));
            System.out.println ("Change Document - " + getRestrictions(perms.getChangeDocumentRestrictions(checkOwnerPassword)));
            System.out.println ("Extract Content - " + getRestrictions(perms.getExtractTextGraphicsRestrictions(checkOwnerPassword)));
            System.out.println ("Extract Content for Accessibility - " + getRestrictions(perms.getExtractTextGraphicsForAccessibilityRestrictions(checkOwnerPassword)));
            System.out.println ("Fill Form Fields - " + getRestrictions(perms.getFillFormFieldsRestrictions(checkOwnerPassword)));
            System.out.println ("Modify Annotations - " + getRestrictions(perms.getModifyAnnotsRestrictions(checkOwnerPassword)));
            System.out.println ("Print - " + getRestrictions(perms.getPrintRestrictions(checkOwnerPassword)));
            System.out.println ("Print High Resolution - " + getRestrictions(perms.getPrintHighResRestrictions(checkOwnerPassword)));
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
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
    
    private static String getRestrictions(Restrictions restrictions)
    {
    	if (!restrictions.isRestricted())
    	{
    		return "None";
    	}
    	else
    	{
    		String result = "";
    		if (restrictions.isRestrictedByPasswordPermissions())
    		{
    			result += "Password ";
    		}
    		if (restrictions.isRestrictedByDocMDPPermissions())
    		{
    			result += "DocMDP ";
    		}
    		if (restrictions.isRestrictedByUsageRightsPermissions())
    		{
    			result += "UsageRights";
    		}
    		return result;
    	}
    }
}
