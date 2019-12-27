/*
 * This java program uses jPDFProcess 
 * and will make a copy of a PDF (preserving all vector content) 
 * and add a background and foreground image.
 */
package jPDFProcessSamples;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.qoppa.pdf.settings.ImageCompression;
import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.pdfProcess.PDFPage;

public class AddBackgroundImage
{
    public static void main (String [] args)
    {
        try
        {
            // Load document
            PDFDocument pdfDoc = new PDFDocument ("C:\\myfolder\\input.pdf", null);
            
            // Load background image
            BufferedImage bgImage = ImageIO.read(new File("C:\\myfolder\\background.jpg"));
            
            // Load foreground image
            BufferedImage fgImage = ImageIO.read(new File("C:\\myfolder\\foreground.jpg"));
            
            // Create new PDF for output
            PDFDocument outputPDF = new PDFDocument();
                        
            
           for (int i=0; i< pdfDoc.getPageCount(); i++)
           {
        	   
        	  // get source PDF
        	  PDFPage currentPage = pdfDoc.getPage(i);
        	   
              // Create new page of the same size as source page
              PDFPage newPage = outputPDF.appendNewPage(currentPage.getPaperWidth(), currentPage.getPaperHeight());
              
              // 1- draw background image onto page
              newPage.drawImage(bgImage, 0, 0, 
                      null, null, 
                      new ImageCompression(ImageCompression.COMPRESSION_DEFLATE, 1.0f));
              
              // 2. Overlay PDF content for current source page.
              //    Parameters can be chanegd to pass on 
              //    an (x,y) offset to shift content
              //    a (scalex, scaley) to resize content
              newPage.appendPageContent(currentPage, 0, 0, 1, 1, null);
                                    
              // 3. Draw foreground image
             newPage.drawImage(fgImage, 0, 0, 
            		  null, null, 
            		  new ImageCompression(ImageCompression.COMPRESSION_DEFLATE, 1.0f));

              // append the new page to the new PDF
              outputPDF.appendPage(newPage);
           }

            // Save the output document
           outputPDF.saveDocument ("C:\\myfolder\\output.pdf");
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }
}
