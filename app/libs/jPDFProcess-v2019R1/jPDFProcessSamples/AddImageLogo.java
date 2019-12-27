/*
 * Sample java program using jPDFProcess to open a PDF, 
 * add a company logo image at the top of each page, 
 * then save the document.
 */

package jPDFProcessSamples;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.qoppa.pdf.settings.ImageCompression;
import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.pdfProcess.PDFPage;

public class AddImageLogo
{
    public static void main (String [] args)
    {
        try
        {
            // Load document
            PDFDocument pdfDoc = new PDFDocument ("C:\\myfolder\\input.pdf", null);
            
            // Load foreground image
            BufferedImage logoImage = ImageIO.read(new File("C:\\myfolder\\logo.jpg"));
            
            for (int i=0; i< pdfDoc.getPageCount(); i++)
            {
        	  // get current page
        	  PDFPage currentPage = pdfDoc.getPage(i);
        	 
        	  // add image logo on top
        	  currentPage.drawImage(logoImage, 0, 0, null, null, new ImageCompression(ImageCompression.COMPRESSION_DEFLATE, 1.0f));
            }

            // Save the PDF
            pdfDoc.saveDocument ("C:\\myfolder\\output.pdf");
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }
}
