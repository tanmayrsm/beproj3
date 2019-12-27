/**
 * This java sample shows how to search text in a PDF
 * and add link annotations that go to a specific URL 
 * on top of the text occurrences found 
 *
 */
package jPDFProcessSamples;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import com.qoppa.pdf.TextPosition;
import com.qoppa.pdf.annotations.Link;
import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.pdf.actions.URLAction;

public class SearchAndAddLinks
{
    public static void main (String [] args)
    {
        try
        {
            // Load the document
            PDFDocument pdfDoc = new PDFDocument ("C:\\myfolder\\input.pdf", null);
            
            // this is my search text
            String searchLabel = "www.qoppa.com";
            
           // Loop through all pages in the document
    		for (int pageix = 0; pageix < pdfDoc.getPageCount(); ++pageix)
    		{
    			// Search for the label text
    			Vector<TextPosition> labelInstances = pdfDoc.getPage(pageix).findText(searchLabel, false, false);
    		
    			// Add links on top of the text instances found
    			if (labelInstances != null && labelInstances.size() > 0)
    			{
    				for (TextPosition tp : labelInstances)
    				{
    					// Create the Link annotation
    					Rectangle2D linkBounds = tp.getPDFSelectionShape().getBounds2D();
    					
    					Link link = pdfDoc.getAnnotationFactory().createLink();
    					link.setRectangle(linkBounds);
    					link.setBorderWidth(1f);
    					link.setColor(Color.blue);
    					
    					// Add URL actions to the link
    					URLAction action = new URLAction("https://www.qoppa.com");
    					Vector<URLAction> actionList = new Vector<URLAction>();
    					actionList.add(action);
    					link.setActions(actionList);
    		
    					// Add the link to the page
    					pdfDoc.getPage(pageix).addAnnotation(link);
    				}
    			}
    		}
            // save doc with links
            pdfDoc.saveDocument ("C:\\myfolder\\output_links.pdf");
        }
		catch (Throwable t)
		{
		    t.printStackTrace();
		}
    }
}
