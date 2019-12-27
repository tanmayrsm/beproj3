package jPDFImagesSamples;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.qoppa.pdf.IWatermark;
import com.qoppa.pdfImages.PDFImages;

public class Watermark
{
	public static void main(String[] args)
	{
		try
		{
			// Load the document
			PDFImages pdfDoc = new PDFImages("input.pdf", null);

			// Create the watermark
			IWatermark watermark = new IWatermark() {
				public void drawWatermark(Graphics2D g, int type, int pageIndex, int pageWidth, int pageHeight)
				{
					// Watermark color
					g.setColor(new Color(128, 128, 128, 128));
					
					// Set the font
					g.setFont(new Font("sansserif", Font.PLAIN, 100));
					
					// Draw the watermark string, rotated 45 degrees
					g.rotate(Math.toRadians(45));
					g.drawString("Watermark", 150, g.getFontMetrics().getMaxDescent());
				}
			};

			// Set the watermark on the document
			pdfDoc.setWatermark(watermark);

			// Loop through pages
			for (int count = 0; count < pdfDoc.getPageCount(); ++count)
			{
				// Get an image of the page
				BufferedImage pageImage = pdfDoc.getPageImage(count, 150);

				// Save the image as a JPEG
				File outputFile = new File("output_" + count + ".jpg");
				ImageIO.write(pageImage, "JPEG", outputFile);
			}
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
	}
}
