package jPDFProcessSamples.cli;

import java.io.IOException;

import com.qoppa.pdf.PDFException;
import com.qoppa.pdfProcess.PDFDocument;

public interface PDFFunction 
{
	public void perform(PDFDocument doc) throws PDFException, IOException;
}
