package jPDFProcessSamples;

import com.qoppa.pdfProcess.PDFDocument;

public class SimpleSplit extends SimplePrint
{
    public static void main (String [] args)
    {
        try
        {
            // Load the two documents
            PDFDocument pdfDoc = new PDFDocument ("input.pdf", null);
            
            // Append the second document to the first one
            for (int count = 0; count < pdfDoc.getPageCount(); ++count)
            {
                PDFDocument split = new PDFDocument();
                split.appendPage(pdfDoc.getPage(count));
                split.saveDocument("output_page_" + (count+1) + ".pdf");
            }
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }



}
