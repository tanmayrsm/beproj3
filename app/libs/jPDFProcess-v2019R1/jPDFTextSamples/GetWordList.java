/*
 * Created on Sep 24, 2008
 *
 */
package jPDFTextSamples;

import java.util.Vector;

import com.qoppa.pdfText.PDFText;

public class GetWordList
{
    public static void main (String [] args)
    {
        try
        {
            // Load the document
            PDFText pdfText = new PDFText ("input.pdf", null);

            // Get the words in the document
            Vector<?> wordList = pdfText.getWords();
            
            // Echo the words
            for (int wordIx = 0; wordIx < wordList.size(); ++wordIx)
            {
                System.out.println (wordList.get(wordIx));
            }
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }
}
