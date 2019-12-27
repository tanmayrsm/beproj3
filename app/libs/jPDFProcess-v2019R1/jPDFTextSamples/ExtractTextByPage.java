/**
 * Qoppa Software - Sample Source Code
 */
package jPDFTextSamples;

import java.io.FileWriter;

import com.qoppa.pdfText.PDFText;

public class ExtractTextByPage
{
    public static void main (String [] args)
    {
        try
        {
            // Load the document
            PDFText pdfText = new PDFText ("input.pdf", null);

            // Loop through the pages
            for (int pageIx = 0; pageIx < pdfText.getPageCount(); ++pageIx)
            {
                // Get the text for the page
                String pageText = pdfText.getText(pageIx);
                
                // Save the text
                // Save the text in a file
                FileWriter output = new FileWriter ("output_" + pageIx + ".txt");
                output.write(pageText);
                output.close();
            }
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }

}
