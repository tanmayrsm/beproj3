package jPDFImagesSamples;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.qoppa.pdfImages.PDFImages;

public class PDFToBufferedImages
{
	public static void main(String[] args)
	{
		try
		{
			// Load the document
			PDFImages pdfDoc = new PDFImages ("input.pdf", null);
       
        	// Loop through pages
			for (int count = 0; count < pdfDoc.getPageCount(); ++count)
	        {
	            // Get an image of the page
	            BufferedImage pageImage = pdfDoc.getPageImage(count, 150);
	
	            Graphics2D g2d = pageImage.createGraphics();
	
	            // BufferedImage can be modified using all drawing functions available in Graphics2D
	            // here is an example on how to add a watermark
	            g2d.setFont (new Font ("sansserif", Font.PLAIN, 200));
	            g2d.rotate(Math.toRadians(45));
	            g2d.setColor (new Color (128, 128, 128, 128));
	            g2d.drawString ("Watermark", 300, g2d.getFontMetrics().getMaxDescent());
	           
	            // Save the image as a JPEG
	            File outputFile = new File ("output_" + count + ".jpg");
	            ImageIO.write(pageImage, "JPEG", outputFile);
	        }
	    }
	    catch (Throwable t)
	    {
	        t.printStackTrace();
	    }
	}
}

