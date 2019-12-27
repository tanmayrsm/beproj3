/**
 * Qoppa Software - Sample Source Code
 */
package jPDFSecureSamples;

import java.awt.geom.Rectangle2D;
import java.io.FileInputStream;
import java.security.KeyStore;

import javax.swing.SwingConstants;

import com.qoppa.pdf.SignatureAppearance;
import com.qoppa.pdf.SigningInformation;
import com.qoppa.pdf.form.SignatureField;
import com.qoppa.pdfSecure.PDFSecure;

public class CustomSignature
{

    public static void main (String [] args)
    {
        try
        {
            // Load the document
            PDFSecure pdfDoc = new PDFSecure ("input.pdf", null);
            
            // Load the keystore that contains the digital id to use in signing
            FileInputStream pkcs12Stream = new FileInputStream ("keystore.pfx");
            KeyStore store = KeyStore.getInstance("PKCS12");
            store.load(pkcs12Stream, "store_pwd".toCharArray());
            pkcs12Stream.close();
            
            // Create signing information
            SigningInformation signInfo = new SigningInformation (store, "key_alias", "key_pwd");
            
            // Customize the signature appearance
            SignatureAppearance signAppear = signInfo.getSignatureAppearance();
            
            // Show an image instead of the signer's name on the left side of the signature field
            signAppear.setVisibleName(false);
            signAppear.setImagePosition(SwingConstants.LEFT);
            signAppear.setImageFile("input.gif");
            
            // Only show the signer's name and date on the right side of the signature field
            signAppear.setVisibleCommonName(false);
            signAppear.setVisibleOrgUnit(false);
            signAppear.setVisibleOrgName(false);
            signAppear.setVisibleLocal(false);
            signAppear.setVisibleState(false);
            signAppear.setVisibleCountry(false);
            signAppear.setVisibleEmail(false);

            // Create signature field on the first page
            Rectangle2D signBounds = new Rectangle2D.Double (36, 36, 144, 48);
            SignatureField signField = pdfDoc.addSignatureField(0, "signature", signBounds);
            
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
