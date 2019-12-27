package jPDFProcessSamples;

import java.io.IOException;

import com.qoppa.pdf.PDFException;
import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.pdfProcess.PDFPage;

public class Imposition
{
	/* 
	 * This sample will open an existing PDF,
	 * reorganize pages by putting 4 pages of any size 
	 * onto one single page in letter format (8.5 x 11)
	 * and save them in a new PDF
	 * 
	 * The pages are organized sequentially 
	 * in 2 columns and 2 rows,	  
	 * called a sequential imposition layout
	 */
	public static void main(String[] args)
	{
		try
		{
			// Load document
			PDFDocument srcDoc = new PDFDocument("C:/temp/manchester.pdf", null);

			// Create new PDF for output
			PDFDocument targetDoc = new PDFDocument();

			PDFPage targetPage = null;

			int rows = 2;
			int cols = 2;

			for (int i = 0; i < srcDoc.getPageCount(); i++)
			{
				// Get source PDF page
				PDFPage srcPage = srcDoc.getPage(i);

				int index = i % (rows * cols);

				if (index == 0)
				{
					// Create a new target page
					targetPage = targetDoc.appendNewPage(8.5 * 72, 11 * 72);
				}

				// Calculate the transform to position the page
				double w = srcPage.getDisplayWidth() * cols;
				double h = srcPage.getDisplayHeight() * rows;

				double scale = Math.min(targetPage.getDisplayWidth() / w, targetPage.getDisplayHeight() / h);

				double tx = srcPage.getDisplayWidth() * scale * (index % cols);
				double ty = srcPage.getDisplayHeight() * scale * (index / cols);

				// Overlay PDF content for current source page
				targetPage.appendPageContent(srcPage, tx, ty, scale, scale, null);
			}

			// Save the output document
			targetDoc.saveDocument("C:/temp/out.pdf");
			System.out.println("Done!");
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
