package jPDFSecureSamples;

import java.awt.geom.Rectangle2D;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

import com.qoppa.pdf.PDFException;
import com.qoppa.pdf.SigningInformation;
import com.qoppa.pdf.form.SignatureField;
import com.qoppa.pdfSecure.PDFSecure;

public class PDFSecureCLI 
{
	public static void main(String[] args)
	{
		// Parse the arguments
		JobInfo jobInfo = parseArgs(args);
		
		// Check for the key
		if (jobInfo.m_LicenseKey != null)
		{
			PDFSecure.setKey(jobInfo.m_LicenseKey);
		}
		
		try
		{
			// load the document
			PDFSecure pdfDoc = new PDFSecure(jobInfo.m_InputPDFFile, null);
			
            // Load the keystore that contains the digital id to use in signing
            FileInputStream pkcs12Stream = new FileInputStream (jobInfo.m_PKCS12File);
            KeyStore store = KeyStore.getInstance("PKCS12");
            store.load(pkcs12Stream, jobInfo.m_StorePWD.toCharArray());
            pkcs12Stream.close();

            // Create signing information
            SigningInformation signInfo = new SigningInformation (store, jobInfo.m_KeyAlias, jobInfo.m_KeyPWD);

            // Create signature field on the first page
            Rectangle2D signBounds = new Rectangle2D.Double (36, 36, 144, 48);
            SignatureField signField = pdfDoc.addSignatureField(0, "signature", signBounds);
            
            // Apply digital signature
            pdfDoc.signDocument(signField, signInfo);
 
			// Save to output, save to the same file if there is no output file
			if (jobInfo.m_OutputPDFFile != null && jobInfo.m_OutputPDFFile.length() > 0)
			{
				pdfDoc.saveDocument(jobInfo.m_OutputPDFFile);
			}
			else
			{
				pdfDoc.saveDocument(jobInfo.m_InputPDFFile);
			}
		}
		catch (PDFException e)
		{
			e.printStackTrace();
		}
		catch (GeneralSecurityException gse)
		{
			gse.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private static class JobInfo
	{
		private String m_LicenseKey;

		private String m_InputPDFFile;
		private String m_OutputPDFFile;

		private String m_PKCS12File;
		private String m_StorePWD;
		private String m_KeyAlias;
		private String m_KeyPWD;
	}

	private static JobInfo parseArgs(String[] args)
	{
		if (args.length < 4)
		{
			printUsage();
			System.exit(0);
		}

		// Create new job info
		JobInfo jobInfo = new JobInfo();

		// Loop through the arguments
		int ix = 0;
		while (ix < args.length)
		{
			if ("-inputpdf".equalsIgnoreCase(args[ix]))
			{
				jobInfo.m_InputPDFFile = args[ix + 1];
				ix += 2;
			}
			else if ("-outputpdf".equalsIgnoreCase(args[ix]))
			{
				jobInfo.m_OutputPDFFile = args[ix + 1];
				ix += 2;
			}
			else if ("-pkcs12".equalsIgnoreCase(args[ix]))
			{
				jobInfo.m_PKCS12File = args[ix+1];
				ix += 2;
			}
			else if ("-storepwd".equalsIgnoreCase(args[ix]))
			{
				jobInfo.m_StorePWD = args[ix+1];
				ix += 2;
			}
			else if ("-keyalias".equalsIgnoreCase(args[ix]))
			{
				jobInfo.m_KeyAlias = args[ix+1];
				ix += 2;
			}
			else if ("-keypwd".equalsIgnoreCase(args[ix]))
			{
				jobInfo.m_KeyPWD = args[ix+1];
				ix += 2;
			}
			else if ("-lickey".equalsIgnoreCase(args[ix]))
			{
				jobInfo.m_LicenseKey = args[ix + 1];
				ix += 2;
			}
			else
			{
				throw new RuntimeException("Unrecognized command line option: " + args[ix]);
			}
		}

		// Default key password to store password
		if (jobInfo.m_KeyPWD == null)
		{
			jobInfo.m_KeyPWD = jobInfo.m_StorePWD;
		}

		return jobInfo;
	}

	private static void printUsage()
	{
		System.out.println("Usage: -inputpdf <filename> -outputpdf <filename> -pkcs12 <filename> -storepwd <pwd> -keyalias <alias>");
		System.out.println();
		System.out.println("Optional flags:");
		System.out.println("-keypwd <keypwd>           Optional pwd for the private key, will use the store pwd if missing.");
		System.out.println("-lickey <key>              License key to run in production mode");
	}
}
