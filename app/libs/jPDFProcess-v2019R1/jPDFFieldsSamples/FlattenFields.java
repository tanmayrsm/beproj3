/**
 * Qoppa Software - Sample Source Code
 */
package jPDFFieldsSamples;

import com.qoppa.pdfFields.PDFFields;
import com.qoppa.pdf.IPassword;

public class FlattenFields
{
	static class MyIPassword implements IPassword
	{
		public String [] getPasswords()
        {
        	// This is a simple way to return a static list of possible passwords
			// It is preferable to enter permission passwords rather than open passwords
            // as all permissions will then be granted.
			// This method could also be edited to retrieve the password for each PDF
            // for instance from the database.
         	return new String[]{"password1", "password2"};
        }
	}
	
	public static void main (String [] args)
    {
        try
        {
            // Load the document
            PDFFields pdfDoc = new PDFFields ("input.pdf", null);
            
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
