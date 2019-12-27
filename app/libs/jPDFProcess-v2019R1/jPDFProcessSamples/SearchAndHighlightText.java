/*
 * This java sample shows how to search text in a PDF
 * and add text highlights (or other text markups)
 * on top of the text found then save the PDF
 * 
 */
package jPDFProcessSamples;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.Vector;

import com.qoppa.pdf.TextPosition;
import com.qoppa.pdf.annotations.TextMarkup;
import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.pdfProcess.PDFPage;

public class SearchAndHighlightText
{
    public static void main (String [] args)
    {
        try
        {
            // Open the document
            PDFDocument inDoc = new PDFDocument ("c:/input.pdf", null);

            // Loop through the pages, searching for text
            for (int pageIx = 0; pageIx < inDoc.getPageCount(); ++pageIx)
            {
                // Search for the text in a page
                PDFPage page = inDoc.getPage (pageIx);
                Vector<TextPosition> searchResults = page.findText("the", false, false);
                System.out.println ("Page " + pageIx + " - Found " + searchResults.size() + " instances");
                if (searchResults.size () > 0)
                {
                    for (int count = 0; count < searchResults.size(); ++count)
                    {
                        // Get the position of the text
                        TextPosition textPos = (TextPosition)searchResults.get (count);

                        // Vector for annotation quadrilateral corners
                        Vector<Point2D[]> quadList = new Vector<Point2D[]>();
                        quadList.add(textPos.getPDFQuadrilateral());

                        // Create markup annotation and add it to the page
                        // The subtype for text markup annot can be Highlight, Underline, StrikeOut, Squiggly
						TextMarkup markup = inDoc.getAnnotationFactory().createTextMarkup("Test Markup", quadList, "Highlight");
                        markup.setColor(Color.yellow);
                        page.addAnnotation(markup);
                    }
                }
            }
            
            inDoc.saveDocument("C:/output.pdf");
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }   
    }
}
