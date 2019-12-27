package jPDFAssembleSamples;

import com.qoppa.pdfAssemble.PDFAssemble;

public class SimpleMerge
{
    public static void main (String [] args)
    {
        try
        {
            // Load the two documents
            PDFAssemble pdfDoc1 = new PDFAssemble ("doc1.pdf", null);
            PDFAssemble pdfDoc2 = new PDFAssemble ("doc2.pdf", null);
            
            // Append the second document to the first one
            pdfDoc1.appendDocument(pdfDoc2);
            
            // Save the merged document
            pdfDoc1.saveDocument ("merged.pdf");
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }



}
