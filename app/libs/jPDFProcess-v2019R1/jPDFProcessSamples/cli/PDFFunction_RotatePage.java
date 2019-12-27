package jPDFProcessSamples.cli;

import com.qoppa.pdf.PDFException;
import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.pdfProcess.PDFPage;

public class PDFFunction_RotatePage implements PDFFunction
{
	private int mPageIndex;
	private int mRotateDegrees;
	
	public PDFFunction_RotatePage(int pageIndex, int rotDegrees)
	{
		mPageIndex = pageIndex;
		mRotateDegrees = rotDegrees;
	}
	
	public void perform(PDFDocument doc) throws PDFException 
	{
		PDFPage page = doc.getPage(mPageIndex);
		page.setPageRotation(page.getPageRotation() + mRotateDegrees);
	}
}
