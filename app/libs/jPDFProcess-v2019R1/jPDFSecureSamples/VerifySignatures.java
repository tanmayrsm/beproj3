package jPDFSecureSamples;

import java.util.Vector;

import com.qoppa.pdf.SignatureValidity;
import com.qoppa.pdf.form.SignatureField;
import com.qoppa.pdfSecure.PDFSecure;

/**
 * Demonstrates how to validate signatures in a document.
 * 
 * @author Qoppa Software
 *
 */
public class VerifySignatures
{
    public static void main (String [] args)
    {
        try
        {
            // Load the document
            PDFSecure pdfDoc = new PDFSecure ("c:/qoppa/auto/test01s.pdf", null);
            
            // Get a list of signature fields
            Vector<?> signFields = pdfDoc.getSignatureFields();
            if (signFields != null && signFields.size() > 0)
            {
            	for (int count = 0; count < signFields.size(); ++count)
            	{
            		// Get the next signature field
            		SignatureField field = (SignatureField)signFields.get(count);
            		
            		if (field.hasBeenSigned())
            		{
            			System.out.print ("Signature Field: " + field.getFieldName());
            			SignatureValidity valid = field.getSignatureValidity();
            			if (valid.isAllValid())
            			{
            				System.out.println (" - ALL VALID");
            			}
            			else
            			{
            				System.out.println (" - NOT ALL VALID");
            				if (valid.isValidSignatureHash() == false)
            				{
            					System.out.println ("\t- Document has been changed, invalid signature.");
            				}
            				if (valid.isValidCertificateChain() == false)
            				{
            					System.out.println ("\t- Invalid certificate chain.");
            				}
            				if (valid.isValidExpiration() == false)
            				{
            					System.out.println ("\t- Invalid expiration date.");
            				}
            				if (valid.isTrustedChain() == false)
            				{
            					System.out.println ("\t- Certificate chain is not trusted.");
            				}
            				// ...
            				// Check for other conditions using the isValid...() methods
            			}
            		}
            		else
            		{
            			System.out.println ("Unsigned field: " + field.getFieldName());
            		}
            	}
            }
            else
            {
            	System.out.println ("No Signatures Found.");
            }
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }

}
