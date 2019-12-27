/*
 * This Java sample uses jPDFProcess to clear and delete 
 * any signature field and associated widget in a PDF document
 *
 */
package jPDFProcessSamples;

import java.util.Vector;

import com.qoppa.pdf.annotations.Annotation;
import com.qoppa.pdf.annotations.WidgetSignature;
import com.qoppa.pdf.form.SignatureField;
import com.qoppa.pdfProcess.PDFDocument;

public class ClearDeleteSignatures
{
    public static void main (String [] args)
    {
        try
        {
            // Load the document
            PDFDocument pdfDoc = new PDFDocument ("C:\\myfolder\\signedpdf.pdf", null);
            
            /** first clear all the signature fields **/
            // get signature fields
            Vector<SignatureField> sigFields = pdfDoc.getAcroForm().getSignatureFields();
            
            // loop through all the signature fields
            for(int count = 0; count < sigFields.size(); count++)
            {
            	System.out.println("Clearing signature field " + sigFields.get(count).getFieldName());    			
            	sigFields.get(count).clearSignature();
            }
            
            /** now delete all the signature widgets **/         
            // loop through all pages in the PDF
            for(int page = 0; page <pdfDoc.getPageCount(); page++ )
            {
            	// get annotations - which include form field widgets - on the current page 
            	Vector<Annotation> annots = pdfDoc.getPage(0).getAnnotations();
            	// loop through annotations
            	for(int count = 0; count < annots.size(); count++)
            	{
            		// if this annotation is a signature widget
            		if(annots.get(count) instanceof WidgetSignature)
            		{
            			// remove the signature widget which will also
                		// remove any associated signature field
                		System.out.println("Signature widget found and removed on page " + (page+1));
            			pdfDoc.getPage(page).removeAnnotation(annots.get(count));
            		}
            	}
            }
            
            // save the PDF (unsigned)
            pdfDoc.saveDocument("C:\\myfolder\\unsignedpdf.pdf");
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }
}
