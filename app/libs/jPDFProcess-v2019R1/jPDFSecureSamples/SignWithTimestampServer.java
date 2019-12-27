package jPDFSecureSamples;

import java.awt.geom.Rectangle2D;

import com.qoppa.pdf.SigningInformation;
import com.qoppa.pdf.TimestampServer;
import com.qoppa.pdf.form.SignatureField;
import com.qoppa.pdfSecure.PDFSecure;

public class SignWithTimestampServer
{
	public static void main (String [] args)
	{
		try
		{
			PDFSecure pdf = new PDFSecure ("MyDocument.pdf", null);
			SignatureField sf = pdf.addSignatureField(0, "Signature1", new Rectangle2D.Float (72, 72, 216, 54));

			SigningInformation si = new SigningInformation("MyId.pfx", "storepwd", "key", "storepwd");
			
			// Add a TimestampServer to the SigningInformation
			TimestampServer timestampServer = new TimestampServer("http://tsatest1.digistamp.com/tsa", "user","password");
			si.setTimestampServer(timestampServer);

			// Sign the document
			pdf.signDocument(sf, si);
			
			// Save the signed document
			pdf.saveDocument ("MyDocument-Signed.pdf");
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
	}
}
