package jPDFSecureSamples;

import java.security.KeyStore;
import com.qoppa.pdf.SignatureSettings;

/**
 * 
 * Demonstrates how to create a keystore for Windows trusted root CAs and add them to the trusted certificate list.
 *
 */
public class TrustWindowsRootCAs
{
	public static void main(String [] args)
	{
		try
		{
			// Create keystore for Windows trusted root certification authorities
			KeyStore keystore = KeyStore.getInstance("Windows-ROOT");
			keystore.load(null, null);
			
			// Add keystore to trusted certificate list
			SignatureSettings.addKeyStore(keystore);
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
	}
}
