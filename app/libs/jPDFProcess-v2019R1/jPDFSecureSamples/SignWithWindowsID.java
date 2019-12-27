package jPDFSecureSamples;

import java.awt.geom.Rectangle2D;
import java.security.KeyStore;
import java.util.Enumeration;
import com.qoppa.pdf.SigningInformation;
import com.qoppa.pdf.form.SignatureField;
import com.qoppa.pdfSecure.PDFSecure;

/**
 * 
 * Demonstrates how to load and sign with the Windows keystore for personal certificates.
 *
 */
public class SignWithWindowsID
{
	public static void main (String [] args)
	{
		try 
		{
			// Create a keystore for Windows personal certificates
			KeyStore ks = KeyStore.getInstance("Windows-MY");
			ks.load(null, null);
			
			// Get the alias of the first entry in the keystore
			Enumeration<String> aliases = ks.aliases();
			if (aliases.hasMoreElements() == false)
			{
				System.out.println ("No digital IDs found in token.");
				System.exit(-1);
			}
			String idAlias = (String)aliases.nextElement();

			// Load PDF document with jPDFSecure
			PDFSecure pdf = new PDFSecure ("input.pdf", null);
			
			// Add a signature field to the document
			SignatureField signField = pdf.addSignatureField(0, "SignHere", new Rectangle2D.Double(180, 72, 200, 60));
			
			// Create signature information from the keystore and personal Windows certificate
			SigningInformation signingInfo = new SigningInformation(ks, idAlias, "");
			
			// Sign and save the document
			pdf.signDocument(signField, signingInfo);
			pdf.saveDocument("signed.pdf");
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
	}
}
