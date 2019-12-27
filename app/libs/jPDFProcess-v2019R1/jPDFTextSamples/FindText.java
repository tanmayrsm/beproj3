/*
 * Created on Jan 20, 2009
 *
 */
package jPDFTextSamples;

import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.Vector;

import com.qoppa.pdf.PDFException;
import com.qoppa.pdf.TextPosition;
import com.qoppa.pdfText.PDFText;

public class FindText
{
    public static void main (String [] args)
    {
        try
        {
            // Load the document
            PDFText pdfText = new PDFText ("input.pdf", null);

            // Loop through the pages in the document
            for (int pageIx = 0; pageIx < pdfText.getPageCount(); ++pageIx)
            {
                // Get the text for the document
                Vector<TextPosition> textList = pdfText.findText(pageIx, "search", false, false);
                
                // Echo results
                System.out.println ("Page " + (pageIx + 1) + " - " + textList.size() + " occurrences.");
                
                if (textList.isEmpty())
                {
                	System.out.println ("\tNo Occurrences");
                }
                else
                {
                	// Echo the position information
                    for (int count = 0; count < textList.size(); ++count)
                    {
                        TextPosition tp = textList.get(count);
                        System.out.println ("\tInstance " + (count+1) + " - " + echoQuad (tp.getViewQuadrilateral()));
                    }
                }
            }            
        }
        catch (PDFException t)
        {
            t.printStackTrace();
        }
    }
    
    
    private static String echoQuad (Point2D [] quadPoints)
    {
        DecimalFormat decFormat = new DecimalFormat ("0.00");
        
        StringBuffer quadString = new StringBuffer();
        quadString.append (decFormat.format(quadPoints [0].getX()) + "," + decFormat.format(quadPoints [0].getY()));
        quadString.append (" to ");
        quadString.append (decFormat.format(quadPoints [1].getX()) + "," + decFormat.format(quadPoints [1].getY()));
        quadString.append (" to ");
        quadString.append (decFormat.format(quadPoints [2].getX()) + "," + decFormat.format(quadPoints [2].getY()));
        quadString.append (" to ");
        quadString.append (decFormat.format(quadPoints [3].getX()) + "," + decFormat.format(quadPoints [3].getY()));
        
        return quadString.toString();
    }

}
