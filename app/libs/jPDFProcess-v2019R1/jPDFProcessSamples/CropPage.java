package jPDFProcessSamples;

/*
 * This sample shows how to open a PDF document
 * set a new crop box for the first page
 * and then save the PDF.
 */

import java.awt.geom.Rectangle2D;

import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.pdfProcess.PDFPage;

public class CropPage
{
    public static void main (String [] args)
    {
        try
        {
        	// Open PDF file
            PDFDocument pdfDoc = new PDFDocument("C:\\test\\mydocument.pdf", null);
                   
            // Get first page 
            PDFPage page = pdfDoc.getPage(0);
            
            // Define the new crop box, which is a rectangle that is used to crop content 
            // before displaying or printing the page. This rectangle is in PDF native 
            // coordinates starting at the bottom left and increasing up to the right.
            // The dimensions are given in PostScript points. 
            // 1 inch = 72 points, 1cm = 28.3465 points, 1mm = 2.8346 points
            // width in points     
            double crop_width = 200;
            // height in points  
            double crop_height = 300;
            // bottom left corner coordinates in points 
            double x_1 = 20;
            double y_1 = 20;      
            Rectangle2D.Double area = new Rectangle2D.Double(x_1, y_1, crop_width, crop_height);
            
            // set the new crop box
            page.setCropBox(area);
            
            // save the cropped document
            pdfDoc.saveDocument("C:\\test\\mydocument_cropped.pdf");
            
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }
}
