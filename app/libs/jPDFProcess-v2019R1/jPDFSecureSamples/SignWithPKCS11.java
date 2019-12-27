package jPDFSecureSamples;

import java.awt.geom.Rectangle2D;
import java.lang.reflect.Constructor;
import java.security.KeyStore;
import java.security.Provider;
import java.security.Security;
import java.util.Enumeration;

import com.qoppa.pdf.SigningInformation;
import com.qoppa.pdf.form.SignatureField;
import com.qoppa.pdfSecure.PDFSecure;

/**
 * This samples demonstrates using a hardware token to apply a digital signature to a document, using PKCS#11.
 * Java has some support for PKCS#11 through its SunPKCS11 class, in JDK 1.6.0.  The class takes a configuration
 * file that points to the native library that works with the hardware token.
 * 
 * @author Qoppa Software
 *
 */
public class SignWithPKCS11 
{
	public static void main (String [] args)
	{
		try 
		{
			// Get constructor to the Sun PKCS11 provider
			Class<?> pkcs11Class = Class.forName("sun.security.pkcs11.SunPKCS11");
			Constructor<?> construct = pkcs11Class.getConstructor(new Class[] {String.class});

			// Construct the provider
			String configName = "pkcs11.cfg";
			Provider p = (Provider)construct.newInstance(new Object[] {configName});
			Security.addProvider(p);

			// Create key store
			KeyStore ks = KeyStore.getInstance("PKCS11");
			ks.load(null, "tokenpwd".toCharArray());

			// Get the alias of the first entry in the keystore
			Enumeration<?> aliases = ks.aliases();
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
			
			// Create signature information from the keystore
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
