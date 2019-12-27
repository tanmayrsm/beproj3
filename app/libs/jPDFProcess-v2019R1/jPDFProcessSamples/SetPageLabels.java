package jPDFProcessSamples;

import com.qoppa.pdfProcess.PDFDocument;

/*
 * This is a sample program that opens an existing PDF 
 * and sets page labels of pages 1 to 3 
 * to use Roman numerals with a prefix.
 */

public class SetPageLabels
{

	public static void main(String[] args)
	{
		try
		{
			// Load document
			PDFDocument pdfDoc = new PDFDocument("C:/test/myfile.pdf", null);
			
			int[] indices = { 0, 1, 2};
			String prefix = "page ";
			// R style is for upper case Roman numerals
			String style = "R";
			int offset = 1;
			
			// Set the page labels
			pdfDoc.setPageLabels(indices, prefix, style, offset);
	
			// Save the output document
			pdfDoc.saveDocument("C:/test/out.pdf");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

}