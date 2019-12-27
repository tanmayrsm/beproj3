/**
 * Qoppa Software - Sample Source Code
 * This sample program opens an existing PDF document
 * and echoes the words and position information
 * for the text contained in the document. 
 * Words separators can be customized.
 * This program is too extract actual words in 
 * a book like or article like document
 * If you are trying to extract data from 
 * structured reports, look at GetLinesAndPositions 
 * 
 */
package jPDFTextSamples;

import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.Vector;

import com.qoppa.pdf.TextPosition;
import com.qoppa.pdfText.PDFText;

public class GetWordsAndPositions extends ExtractTextByPage
{
    public static void main (String [] args)
    {
        try
        {
            // Load the document
            PDFText pdfText = new PDFText ("C:\\test\\test.pdf", null);
            
            // Loop through the pages
            for (int pageIx = 0; pageIx < pdfText.getPageCount(); ++pageIx)
            {
                // Echo page number
                System.out.println ("\n***** Page " + pageIx + " *****\n");

                // Get the words in the page and their position
                // We are using here the default separators  ,/;\n><():?&.@*\t
                // but they can be customized depending on the data your are trying to extract
                Vector<TextPosition> wordList = pdfText.getWordsWithPositions(pageIx," ,/;\n><():?&.@*\t");
                
                // Echo each of the words in the document
                for (int wordIx = 0; wordIx < wordList.size(); ++wordIx)
                {
                    // Echo the word information
                    TextPosition tp = (TextPosition)wordList.get(wordIx);
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
        
        StringBuffer quadString = new StringBuffer("Word Rectangle Coordinates: ");
        quadString.append ("(" + decFormat.format(quadPoints [0].getX()) + "," + decFormat.format(quadPoints [0].getY()) + ") ");
        quadString.append ("(" +decFormat.format(quadPoints [1].getX()) + "," + decFormat.format(quadPoints [1].getY()) + "), ");
        quadString.append ("(" +decFormat.format(quadPoints [2].getX()) + "," + decFormat.format(quadPoints [2].getY())+ ") ");
        quadString.append ("(" +decFormat.format(quadPoints [3].getX()) + "," + decFormat.format(quadPoints [3].getY())+ ")");
        
        return quadString.toString();
    }
}
