/*
 * This sample java program uses jPDFProcess
 * to create a new PDF file, add a page to it
 * and draw an image and text on the page.
 * 
 */
package jPDFProcessSamples;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.pdfProcess.PDFGraphics;
import com.qoppa.pdfProcess.PDFPage;

public class CreatePDFWithTextAndImage
{
    public static void main (String [] args)
    {
    	try
    	{
    		// create document
    		PDFDocument pdfDoc = new PDFDocument ();
    	 
    		// create and add a page
    		PDFPage page = pdfDoc.appendNewPage(8.5 * 72, 11 * 72);
    	
    		// get graphics from the page
    		// this object is a Graphics2D Object and you can draw anything 
    		// you would draw on a Graphics2D
    		PDFGraphics g2d = (PDFGraphics) page.createGraphics();
    	
    		// read an image from png,. jpeg, etc... 
    		BufferedImage image = ImageIO.read(new File("C:\\myimage.png"));
    		
    		// draw the image on the page
    		g2d.drawImage(image, 0, 0, null);
        
    		// set the font and color
    		g2d.setFont (PDFGraphics.HELVETICA.deriveFont(24f));
    		g2d.setColor(Color.BLUE);
        
    		// draw text on the graphics object of the page
    		g2d.drawString("NEW TEXT", 200, 100);
    	 
    		// Save the document
    		pdfDoc.saveDocument ("C:\\test.pdf");
    	}
    	catch(Throwable t)
    	{
    		t.printStackTrace();
    	}
    }
}
