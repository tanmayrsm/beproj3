package jPDFImagesSamples;

import java.awt.RenderingHints;
import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.awt.color.ICC_Profile;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;

import javax.imageio.ImageIO;

import com.qoppa.pdf.PDFRenderHints;
import com.qoppa.pdfImages.PDFImages;

public class PDFToOverPrintImages
{
	public static void main(String[] args)
	{
	    	 try
	    	 {
	           // Load the document
	           PDFImages pdfDoc = new PDFImages ("C:\\test\\myDoc.pdf", null);
	                                                
	                                                
	           // Turn off Java anti-aliasing needed when doing overprint simulation
	           PDFRenderHints.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
	                                                
	           // Loop through pages
	           for (int count = 0; count < pdfDoc.getPageCount(); ++count)
	           {
	               // Get an image of the page in the expected resolution in the CMYK color space with overprint simulation
	               ICC_Profile cmykprofile = ICC_Profile.getInstance("./CMYK Profiles/USWebCoatedSWOP.icc");
	               ICC_ColorSpace cmykColorSpace =  new ICC_ColorSpace(cmykprofile);
	               BufferedImage pageImage = pdfDoc.getPageImageCS(count, 200, new ICC_ColorSpace(cmykprofile), true);
	                
	                // Convert image from CMYK to RGB
	                BufferedImage bi = new BufferedImage (pageImage.getWidth(), pageImage.getHeight(), BufferedImage.TYPE_INT_RGB);
	                ColorConvertOp colorOp = new ColorConvertOp (cmykColorSpace, ColorSpace.getInstance(ColorSpace.CS_sRGB), null);
	                colorOp.filter(pageImage, bi);
	                
	                // Save the image as a PNG
	                File outputFile = new File ("C:/test/output_" + count + ".png");
	                ImageIO.write(bi, "PNG", outputFile);
	                System.out.println("Outptut File "+ count + " " + outputFile.getAbsolutePath());
	          }
	                                                
	                // Turn Java anti-aliasing back on
	                PDFRenderHints.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	                                                
	        }
	        catch (Throwable t)
	        {
	           t.printStackTrace();
	        }
	     }
	}
