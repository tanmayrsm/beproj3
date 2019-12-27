/*
 * Created on Sep 24, 2008
 *
 */
package jPDFProcessSamples;

import java.io.FileWriter;

import com.qoppa.pdfProcess.PDFDocument;

public class ExtractText extends SignDocument
{
    public static void main (String [] args)
    {
        try
        {
            // Load the document
            PDFDocument pdfDoc = new PDFDocument ("input.pdf", null);

            // Get the text for the document
            String docText = pdfDoc.getText();
            
            // Save the text in a file
            FileWriter output = new FileWriter ("output.txt");
            output.write(docText);
            output.close();
            
            //
            // Text can also be retrieved for each page using
            //    pdfDoc.getText (pageIx)
            //
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }


}
