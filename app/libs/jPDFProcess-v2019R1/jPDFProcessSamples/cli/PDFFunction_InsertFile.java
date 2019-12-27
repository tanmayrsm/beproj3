package jPDFProcessSamples.cli;

import com.qoppa.pdf.PDFException;
import com.qoppa.pdfProcess.PDFDocument;

public class PDFFunction_InsertFile implements PDFFunction
{
	private String mFileName;
	private int mPageIndex;

	public PDFFunction_InsertFile(String fileName, int pageIndex)
	{
		mFileName = fileName;
		mPageIndex = pageIndex;
	}

	public void perform(PDFDocument doc) throws PDFException 
	{
		PDFDocument insertDoc = new PDFDocument(mFileName, null);
		for (int pageIx = insertDoc.getPageCount()-1; pageIx >= 0; --pageIx)
		{
			doc.insertPage(insertDoc.getPage(pageIx), mPageIndex+1);
		}
	}
}
