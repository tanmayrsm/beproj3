package jPDFProcessSamples;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import com.qoppa.pdf.annotations.Redaction;
import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.pdfProcess.PDFPage;

public class RedactionApplyBurn
{
    public static void main (String [] args)
    {
        try
        {
            // Load the two documents
            PDFDocument pdfDoc = new PDFDocument ("C:\\myfolder\\input.pdf", null);
            
            if(pdfDoc.getPageCount() > 0)
            {
            
            	// create a redaction annotation
            	Redaction annotation = pdfDoc.getAnnotationFactory().createRedaction("");
            	// set annotation bounds
            	annotation.setRectangle(new Rectangle2D.Double(100, 100, 100, 100));	
            	// annotation border color (Red)
            	annotation.setColor(Color.RED);
            	// annotation opacity
            	annotation.setOpacity(50);            	
            	// annotation internal color (Gray)
            	annotation.setInternalColor(Color.GRAY);
            	// set overlay text color (Blue)
            	annotation.setOverlayTextColor(Color.BLUE);
            	// set overlay font police and size
            	annotation.setOverlayFont("Times-Roman", 8);
            	// set overlay text
            	annotation.setOverlayText("[NEW TERM]");
            	// set overlay repeat
            	annotation.setOverlayTextRepeats(true);
            	
            	// add the annotation to the page
            	PDFPage page = pdfDoc.getPage(0);
            	page.addAnnotation(annotation);
            	
            	// save doc with redaction
            	pdfDoc.saveDocument ("C:\\myfolder\\input_redact_annot.pdf");
            	
            	// apply / burn redaction annotations
            	page.applyRedactionAnnotations();
            	
            	// save doc with redaction
            	pdfDoc.saveDocument ("C:\\myfolder\\input_redact_burn.pdf");
            
            }
        }
		catch (Throwable t)
		{
		    t.printStackTrace();
		}
   }

}

