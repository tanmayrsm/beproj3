package jPDFImagesSamples;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.qoppa.pdfImages.PDFImages;

public class PDFToColorSpaceImages
{
	public static void main(String[] args)
	{
		try
		{
			// Load the document
			PDFImages pdfDoc = new PDFImages ("C:\\input.pdf", null);
			// Loop through pages
			for (int count = 0; count < pdfDoc.getPageCount(); ++count)
	        {
	            // Get an image of the page in the expected resolution and color space
	            BufferedImage pageImage = pdfDoc.getPageImageCS(count, 150, ColorSpace.getInstance(ColorSpace.CS_GRAY));
	
	            // Save the image as a PNG
	            File outputFile = new File ("output_" + count + ".png");
	            ImageIO.write(pageImage, "PNG", outputFile);
	            System.out.println("Outptut File "+ count + " " + outputFile.getAbsolutePath());
	        }
			
	    }
	    catch (Throwable t)
	    {
	        t.printStackTrace();
	    }
	}
}

