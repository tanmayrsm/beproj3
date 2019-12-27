package jPDFProcessSamples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.qoppa.pdf.PDFException;
import com.qoppa.pdf.TextPosition;
import com.qoppa.pdf.TextPositionWithContext;
import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.pdfProcess.PDFPage;

public class TextRedactionWithRegEx
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
				
				//overly simplistic email matching expresssion I copied off the web
				Vector<TextPositionWithContext> emails_maybe = pdfPage.findTextWithContextUsingRegEx("([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})");
				
				//overly simplistic phone # regex I just came up with without being too careful 
				// should match 7 digit phone numbers with optional 3 digit area code
				Vector<TextPositionWithContext> usPhoneNums = pdfPage.findTextWithContextUsingRegEx("(([(][0-9]{3}[)])|[0-9]{3})(\\w|-)[0-9]{3}(\\w|-)[0-9]{4}");
				
				//SSNs ###-##-####
				Vector<TextPositionWithContext> ssns = pdfPage.findTextWithContextUsingRegEx("[0-9]{3}-[0-9]{2}-[0-9]{4}");
				
				//very simple dates in YYYY-MM-DD format
				Vector<TextPositionWithContext> yearFirstDates = pdfPage.findTextWithContextUsingRegEx("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}");
				
				List<TextPositionWithContext> allResults = new ArrayList<TextPositionWithContext>();
				allResults.addAll(emails_maybe);
				allResults.addAll(usPhoneNums);
				allResults.addAll(ssns);
				allResults.addAll(yearFirstDates);
				
				//create redaction annotations
				for (TextPosition textPos : allResults)
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