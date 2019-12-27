/*
 * After assembling a PDF document, it is sometimes necessary to edit the meta data. 
 * jPDFAssemble allows to set and edit document properties
 * such as title, author, subject and keywords.
 */
package jPDFAssembleSamples;

import com.qoppa.pdfAssemble.PDFAssemble;

public class SetDocumentProperties
{
    public static void main (String [] args)
    {
        try
        {
        	// load a PDF
        	PDFAssemble pdfDoc = new PDFAssemble("C:\\support\\accenture\\Sample.pdf", null);
        	
        	// setting document properties
        	// document title
        	pdfDoc.getDocumentInfo().setTitle("My Document Title");
        	// document author
        	pdfDoc.getDocumentInfo().setAuthor("My Author");
        	// document subject
        	pdfDoc.getDocumentInfo().setSubject("My Subject");
        	// document keywords (must be separated by commas or semicolons)
        	pdfDoc.getDocumentInfo().setKeywords("keyword1, keyword2, keyword3, keyword4");
        	 
        	// save to out.pdf
        	pdfDoc.saveDocument("C:\\support\\accenture\\out.pdf"); 

        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }

}
