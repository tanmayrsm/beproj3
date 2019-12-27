package jPDFProcessSamples.cli;

import com.qoppa.pdf.PDFException;
import com.qoppa.pdfProcess.PDFDocument;

public class PDFFunction_ExtractPage implements PDFFunction
{
	private int mPageIndex;

	public PDFFunction_ExtractPage(int pageIndex)
	{
		mPageIndex = pageIndex;
	}

	public void perform(PDFDocument doc) throws PDFException
	{
		// Extract page is the same as deleting all other pages
		while(doc.getPageCount() > mPageIndex+1)
		{
			doc.deletePage(mPageIndex+1);
		}
		
		while (doc.getPageCount() > 1)
		{
			doc.deletePage(0);
		}
	}
}
