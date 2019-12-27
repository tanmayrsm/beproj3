/**
 * Qoppa Software - Sample Source Code
 * This sample program opens an existing PDF document
 * and echoes the lines and position information
 * for the text contained in the document
 */
package jPDFTextSamples;

import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.Vector;

import com.qoppa.pdf.TextPosition;
import com.qoppa.pdfText.PDFText;

public class GetLinesAndPositions extends ExtractTextByPage
{
    public static void main (String [] args)
    {
        try
        {
            // Load the document
            PDFText pdfText = new PDFText ("C:\\test\\sample_invoice.pdf", null);
            
            // Loop through the pages
            for (int pageIx = 0; pageIx < pdfText.getPageCount(); ++pageIx)
            {
                // Echo page number
                System.out.println ("\n***** Page " + pageIx + " *****\n");

                // Get the lines in the page and their position
                Vector<TextPosition> lineList = pdfText.getLinesWithPositions(pageIx);
                
                // Echo each of the lines in the document
                for (int i = 0; i < lineList.size(); ++i)
                {
                    // Echo the line information
                    TextPosition tp = (TextPosition)lineList.get(i);
                    System.out.println (tp.getText() + " - " + echoQuad (tp.getViewQuadrilateral()));
                }
            }
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }
    
    private static String echoQuad (Point2D [] quadPoints)
    {
        DecimalFormat decFormat = new DecimalFormat ("0.00");
        
        StringBuffer quadString = new StringBuffer("Line Rectangle Coordinates: ");
        quadString.append ("(" + decFormat.format(quadPoints [0].getX()) + "," + decFormat.format(quadPoints [0].getY()) + ") ");
        quadString.append ("(" +decFormat.format(quadPoints [1].getX()) + "," + decFormat.format(quadPoints [1].getY()) + ") ");
        quadString.append ("(" +decFormat.format(quadPoints [2].getX()) + "," + decFormat.format(quadPoints [2].getY())+ ") ");
        quadString.append ("(" +decFormat.format(quadPoints [3].getX()) + "," + decFormat.format(quadPoints [3].getY())+ ")");
               
        return quadString.toString();
    }
}
