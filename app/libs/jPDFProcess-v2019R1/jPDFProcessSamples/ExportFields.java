/*
 * Created on Sep 24, 2008
 *
 */
package jPDFProcessSamples;

import com.qoppa.pdfProcess.PDFDocument;

public class ExportFields
{

    public static void main (String [] args)
    {
        try
        {
            // Load the document
            PDFDocument pdfDoc = new PDFDocument ("input.pdf", null);

            // Export field values
            pdfDoc.getAcroForm().exportAsFDF("output.fdf", true);
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }


}
