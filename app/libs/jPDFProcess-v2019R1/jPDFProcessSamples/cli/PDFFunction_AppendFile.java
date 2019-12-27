package jPDFProcessSamples.cli;

import com.qoppa.pdf.PDFException;
import com.qoppa.pdfProcess.PDFDocument;

public class PDFFunction_AppendFile implements PDFFunction
{
	private String mFileName;

	public PDFFunction_AppendFile(String fileName)
	{
		mFileName = fileName;
	}

	public void perform(PDFDocument doc) throws PDFException 
	{
		PDFDocument appendDoc = new PDFDocument(mFileName, null);
		doc.appendDocument(appendDoc);
	}
}
