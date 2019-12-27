package jPDFProcessSamples;

import java.awt.geom.Rectangle2D;
import java.io.IOException;

import com.qoppa.pdf.PDFException;
import com.qoppa.pdf.ResizePageOptions;
import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.pdfProcess.PDFPage;

public class ResizePDFPages
{
	 /* This sample code will open a PDF and resize its pages to A3 format */
	 /* Media Box and Crop Box will be resized to A3 */
	 /*  The page content is rescaled and centered to fit the new format */
	
	public static void main(String[] args)
	{
		try
		{
			// Set up the page resize options
			ResizePageOptions options = new ResizePageOptions();

			// Get the page size A3 (297mm x 420mm) in points
			double width = 297 / 25.4 * 72.0;
			double height = 420 / 25.4 * 72.0;

			// Set the new Media Box
			options.setMediaBox(new Rectangle2D.Double(0, 0, width, height));

			// Scale the page contents to fit the new page size
			options.setAutoScale(true);

			// Center the page contents on the page
			options.setCenter(true);

			// Set the crop box to the same size as the media box
			options.setFitToMedia(true);
			
			// Load document
			PDFDocument pdfDoc = new PDFDocument("C:/qoppa/test/in.pdf", null);
			
			// Resize all pages
			for (int index = 0; index < pdfDoc.getPageCount(); ++index)
			{
				// Get the page
				PDFPage page = pdfDoc.getPage(index);

				// Resize the page
				page.resizePage(options);
			}

			// Save the output document
			pdfDoc.saveDocument("C:/qoppa/test/out.pdf");
		}
		catch (PDFException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
