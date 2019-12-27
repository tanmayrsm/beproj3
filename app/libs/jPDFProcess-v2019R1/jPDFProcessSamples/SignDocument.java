/**
 * Qoppa Software - Sample Source Code
 */
package jPDFProcessSamples;

import java.awt.geom.Rectangle2D;
import java.io.FileInputStream;
import java.security.KeyStore;

import com.qoppa.pdf.SigningInformation;
import com.qoppa.pdf.form.SignatureField;
import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.pdfProcess.PDFPage;

public class SignDocument extends ExportFields
{
    public static void main (String [] args)
    {
        try
        {
            // Load the document
            PDFDocument pdfDoc = new PDFDocument ("input.pdf", null);
            
            // Load the keystore that contains the digital id to use in signing
            FileInputStream pkcs12Stream = new FileInputStream ("keystore.pfx");
            KeyStore store = KeyStore.getInstance("PKCS12");
            store.load(pkcs12Stream, "store_pwd".toCharArray());
            pkcs12Stream.close();
            
            // Create signing information
            SigningInformation signInfo = new SigningInformation (store, "key_alias", "key_pwd");

            // Create signature field on the first page
            PDFPage firstPage = pdfDoc.getPage (0);
            Rectangle2D signBounds = new Rectangle2D.Double (36, 36, 144, 48);
            SignatureField signField = firstPage.addSignatureField("signature", signBounds);
            
            // Apply digital signature
            pdfDoc.signDocument(signField, signInfo);
            
            // Save the document
            pdfDoc.saveDocument ("output.pdf");
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }
}
