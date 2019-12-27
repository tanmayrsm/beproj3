package jPDFProcessSamples;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.Vector;
import java.util.regex.Pattern;

import com.qoppa.pdf.TextPosition;
import com.qoppa.pdf.annotations.Redaction;
import com.qoppa.pdfProcess.PDFDocument;

public class SearchPatternAndRedact
{

	public static void main(String[] args)
	{
		try
        {
            // Load the document
            PDFDocument pdfDoc = new PDFDocument ("C:\\qoppa\\pdfSamples\\test28.pdf", null);
            
            //Regular Expression for matching some common phone number formats (national US primarily). This is not complete. I don't even promise it's entirely correct. It's surely sub-optimal.
            String searchRegexString = "(\\b|\\B|^)((\\d{3})|(\\(\\d{3}\\)))?(\\s*-)?\\s*\\d{3}(\\s*-)?\\s*\\d{4}(\\b|\\B|$)";
            Pattern searchRegex = Pattern.compile(searchRegexString);
                        
            // Loop through all pages in the document
    		for (int pageix = 0; pageix < pdfDoc.getPageCount(); ++pageix)
    		{
    			// Search for the label text
    			Vector<TextPosition> phoneNumberInstances = pdfDoc.getPage(pageix).findLinesMatchingRegex(searchRegex);
    		
    			for (TextPosition tp : phoneNumberInstances)
				{
    				//note that for non-orthogonal text, you should use the quads array 
					Rectangle2D textBounds = tp.getPDFSelectionShape().getBounds2D();
							
					Redaction redact = pdfDoc.getAnnotationFactory().createRedaction("Redaction");
					redact.setRectangle(textBounds);
					redact.setInternalColor(Color.black);
					pdfDoc.getPage(pageix).addAnnotation(redact);
					
					// output matched text
					System.out.println("Phone Number Found: " + tp.getText());
				}
				
				
    		}

    		// Apply redactions if desired
			// pdfDoc.getPage(pageix).applyRedactionAnnotations();
            
            // save doc with redaction
            pdfDoc.saveDocument ("C:\\qoppa\\out\\test28_redact.pdf");
           
        }
		catch (Throwable t)
		{
		    t.printStackTrace();
		}

	}

}
