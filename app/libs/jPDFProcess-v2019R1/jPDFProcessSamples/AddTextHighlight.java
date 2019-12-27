package jPDFProcessSamples;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.Vector;

import com.qoppa.pdf.PDFException;
import com.qoppa.pdf.TextPosition;
import com.qoppa.pdf.annotations.Square;
import com.qoppa.pdf.annotations.TextMarkup;
import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.pdfProcess.PDFPage;

public class AddTextHighlight
{
	public static void main(String[] args)
	{
		try
		{
			// Open the document
			PDFDocument pdfDoc = new PDFDocument("c:\\qoppa\\pdfSamples\\test41.pdf", null);

			// Loop through the pages
			PDFPage pdfPage = pdfDoc.getPage(0);
			
			Graphics2D g2d = pdfPage.createGraphics();
			g2d.setColor(Color.green);
			g2d.drawLine(100, 100, 500, 100);
			
			Square testSquare = pdfDoc.getAnnotationFactory().createSquare(null);
			testSquare.setColor(Color.blue);
			testSquare.setInternalColor(null);
			testSquare.setBorderWidth(1.0);
			testSquare.setRectangle(new Rectangle(100, 105, 500, 5));
			pdfPage.addAnnotation(testSquare);


			// Search for the text
			Vector<TextPosition> searchResults = pdfPage.findText("Good Karma", false, false);

			if (searchResults.size() > 0)
			{
				for (int i = 0; i < searchResults.size(); i++)
				{
					// Get the position of the text
					TextPosition textPos = searchResults.get(i);

					// Create a quad list
					Vector<Point2D[]> quadList = textPos.getPDFQuadrilaterals();
					
					TextMarkup textHighlight = pdfDoc.getAnnotationFactory().createTextMarkup("Test Markup", quadList, "Highlight");
					
					pdfPage.addAnnotation(textHighlight);
				}
			}

			pdfDoc.saveDocument("c:\\qoppa\\pdfSamples\\test41output.pdf");

		}
		catch (PDFException ex)
		{

		}
		catch (IOException ex)
		{

		}
	}
}
