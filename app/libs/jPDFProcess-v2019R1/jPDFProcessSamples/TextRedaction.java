package jPDFProcessSamples;

import java.io.IOException;
import java.util.List;

import com.qoppa.pdf.PDFException;
import com.qoppa.pdf.TextPosition;
import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.pdfProcess.PDFPage;

public class TextRedaction
{
	public static void main(String[] args) 
	{
		try 
		{
			// Open the document
			PDFDocument pdfDoc = new PDFDocument("input.pdf", null);
			
			// per page: search text, create redaction annotations, then apply
			for (int i = 0; i < pdfDoc.getPageCount(); i++) 
			{
				PDFPage pdfPage = pdfDoc.getPage(i);
				
				// Search for the text
				List<TextPosition> searchResults = pdfPage.findText("abcd", false, false);
				
				//create redaction annotations
				for (TextPosition textPos : searchResults)
				{
					pdfPage.addAnnotation(pdfDoc.getAnnotationFactory().createRedaction("Redaction sample", textPos.getPDFQuadrilaterals()));
				}
				
				//apply ("burn-in") all redaction annotations on the page
				pdfPage.applyRedactionAnnotations();
			}
			
			pdfDoc.saveDocument("output.pdf");
		
		} catch (PDFException ex) {
			
		}
		catch (IOException ex) {
			
		}
	}
}