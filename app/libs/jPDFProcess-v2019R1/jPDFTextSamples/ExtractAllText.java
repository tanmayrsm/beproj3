/**
 * Qoppa Software - Sample Source Code
 */
package jPDFTextSamples;

import java.io.FileWriter;

import com.qoppa.pdfText.PDFText;

public class ExtractAllText
{
    public static void main (String [] args)
    {
        try
        {
            // Load the document
            PDFText pdfText = new PDFText ("input.pdf", null);

            // Get the text for the document
            String docText = pdfText.getText();
            
            // Save the text in a file
            FileWriter output = new FileWriter ("output.txt");
            output.write(docText);
            output.close();
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }
}
