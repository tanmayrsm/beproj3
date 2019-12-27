package jPDFProcessSamples.ant;

import java.awt.geom.Rectangle2D;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.qoppa.pdf.PDFException;
import com.qoppa.pdf.SigningInformation;
import com.qoppa.pdf.form.SignatureField;
import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.pdfProcess.PDFPage;

/**
 * Sample implementation of an ANT taks that uses jPDFProcess to digitally sign a PDF document.
 */
public class SignPDF extends Task
{
	private String mInputFile;
	private String mOutputFile;
	
	private String mKeystoreFile;
	private String mKeystorePassword;
	private String mKeyAlias;
	private String mKeyPassword;
	
	private String mSignFieldName = "signature";

	public void execute() throws BuildException 
	{
        try
        {
            // Load the document
            PDFDocument pdfDoc = new PDFDocument (mInputFile, null);
            
            // Load the keystore that contains the digital id to use in signing
            FileInputStream pkcs12Stream = new FileInputStream (mKeystoreFile);
            KeyStore store = KeyStore.getInstance("PKCS12");
            store.load(pkcs12Stream, mKeystorePassword.toCharArray());
            pkcs12Stream.close();
            
            // Create signing information
            SigningInformation signInfo = new SigningInformation (store, mKeyAlias, mKeyPassword);

            // Create signature field on the first page
            PDFPage firstPage = pdfDoc.getPage (0);
            Rectangle2D signBounds = new Rectangle2D.Double (36, 36, 144, 48);
            SignatureField signField = firstPage.addSignatureField(mSignFieldName, signBounds);
            
            // Apply digital signature
            pdfDoc.signDocument(signField, signInfo);
            
            // Save the document
            pdfDoc.saveDocument (mOutputFile);
        }
        catch (PDFException pdfe)
        {
        	throw new BuildException(pdfe);
        }
        catch(IOException ioe)
        {
        	throw new BuildException(ioe);
        }
        catch(GeneralSecurityException gse)
        {
        	throw new BuildException(gse);
        }
	}
	
	public void setInputFile(String filename)
	{
		mInputFile = filename;
	}
	
	public void setKeystoreFile(String filename)
	{
		mKeystoreFile = filename;
	}
	
	public void setKeystorePassword(String password)
	{
		mKeystorePassword = password;
	}
	
	public void setKeyAlias(String alias)
	{
		mKeyAlias = alias;
	}

	public void setKeyPassword(String password)
	{
		mKeyPassword = password;
	}

	public void setOutputFile(String filename)
	{
		mOutputFile = filename;
	}
}
