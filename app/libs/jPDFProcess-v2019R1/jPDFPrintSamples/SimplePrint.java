/*
 * Created on Sep 23, 2008
 *
 */
package jPDFPrintSamples;

import com.qoppa.pdfPrint.PDFPrint;

public class SimplePrint
{
    public static void main (String [] args)
    {
        try
        {
            PDFPrint pdfPrint = new PDFPrint("input.pdf", null);
            pdfPrint.print (null);
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }

}
