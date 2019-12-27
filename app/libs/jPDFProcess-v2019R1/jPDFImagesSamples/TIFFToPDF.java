package jPDFImagesSamples;

import com.qoppa.pdfImages.PDFImages;

public class TIFFToPDF 
{
    public static void main (String [] args)
    {
        try
        {
            PDFImages pdfDoc = new PDFImages();
            pdfDoc.appendTIFFAsPages("input.tif");
            pdfDoc.saveDocument("output.pdf");
        }
        catch (Throwable t)
        {
        	t.printStackTrace();
        }
    }
}
