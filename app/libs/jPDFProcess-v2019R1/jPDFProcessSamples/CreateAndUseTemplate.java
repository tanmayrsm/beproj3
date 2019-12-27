package jPDFProcessSamples;

import java.io.IOException;

import com.qoppa.pdf.PDFException;
import com.qoppa.pdfProcess.PDFDocument;

public class CreateAndUseTemplate
{
	 /* This sample code will open a PDF and create a template from the first page. */
	 /* It will then instantiate the template as every 3rd page of the document (if it has more than 3 pages) */
	
	public static void main(String[] args)
	{
		try
		{
			// Load document
	    	PDFDocument pdfDoc = new PDFDocument ("C:\\myfolder\\input.pdf", null);

	    	// Create a unique template name
	    	String templateName = "sectionHeader";
	    	int index = 0;

	    	while (pdfDoc.getTemplateNames().contains(templateName))
	    	{
	    		templateName = "sectionHeader" + index;
	    		index++;
	    	}

	    	// Create the template based on the first page, leaving that first page in tact
	    	pdfDoc.createTemplateFromPage(0, templateName, true);

	    	// Perhaps this template is needed to be inserted as every third page in the document
	    	for (int i = 3; i < pdfDoc.getPageCount(); i += 3)
	    	{
	    		pdfDoc.addPageFromTemplate(templateName, i);
	    	}

			// Save the output document
			pdfDoc.saveDocument("C:\\myfolder\\output.pdf");
		}
		catch (PDFException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
