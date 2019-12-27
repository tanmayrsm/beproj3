/*
 * This sample shows how to search a text label in a PDF
 * and then remove text following that label.
 * This is done by adding a redaction annotation and burning it
 * which remove any content below the redaction annotation.
 *
 */
package jPDFProcessSamples;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import com.qoppa.pdf.TextPosition;
import com.qoppa.pdf.annotations.Redaction;
import com.qoppa.pdfProcess.PDFDocument;

public class SearchAndRedact
{
    public static void main (String [] args)
    {
        try
        {
            // Load the document
            PDFDocument pdfDoc = new PDFDocument ("C:\\myfolder\\input.pdf", null);
            
            // this is my search label that comes before the text to be redacted
            String searchLabel = "Phone Number";
            
            
           // Loop through all pages in the document
    		boolean foundLabel = false;
    		for (int pageix = 0; pageix < pdfDoc.getPageCount(); ++pageix)
    		{
    			// Search for the label text
    			Vector<TextPosition> labelInstances = pdfDoc.getPage(pageix).findText(searchLabel, false, false);
    		
    			// Add annotations after the instances of the label
    			if (labelInstances != null && labelInstances.size() > 0)
    			{
    				foundLabel = true;
    				for (TextPosition tp : labelInstances)
    				{
    					Rectangle2D labelBounds = tp.getPDFSelectionShape().getBounds2D();
    					Rectangle2D.Double eraseBounds = new Rectangle2D.Double(labelBounds.getX() + labelBounds.getWidth() + 1, 
    																			labelBounds.getY() - 2, 2 * 72, labelBounds.getHeight() + 4);
    		
    					Redaction redact = pdfDoc.getAnnotationFactory().createRedaction("Redaction");
    					redact.setRectangle(eraseBounds);
    					redact.setInternalColor(Color.black);
    					pdfDoc.getPage(pageix).addAnnotation(redact);
    				}
    				
    				// Apply redactions
    				pdfDoc.getPage(pageix).applyRedactionAnnotations();
    			}
    		}

    		// output whether the search label was found or not
            System.out.println("Label Found" + foundLabel);
            
            // save doc with redaction
            pdfDoc.saveDocument ("C:\\myfolder\\output_redact.pdf");
           
        }
		catch (Throwable t)
		{
		    t.printStackTrace();
		}
   }

}

