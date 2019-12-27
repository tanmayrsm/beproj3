package jPDFProcessSamples.cli;

import com.qoppa.pdf.PDFException;
import com.qoppa.pdfProcess.PDFDocument;

public class PDFFunction_RemovePage implements PDFFunction
{
	private int mPageIndex;

	public PDFFunction_RemovePage(int pageIndex)
	{
		mPageIndex = pageIndex;
	}
	
	public void perform(PDFDocument doc) throws PDFException 
	{
		doc.deletePage(mPageIndex);
	}
}
