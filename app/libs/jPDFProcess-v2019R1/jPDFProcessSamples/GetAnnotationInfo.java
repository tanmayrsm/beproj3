package jPDFProcessSamples;

import java.util.Vector;

import com.qoppa.pdf.PDFException;
import com.qoppa.pdf.annotations.Annotation;
import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.pdfProcess.PDFPage;

public class GetAnnotationInfo 
{

	public static void main(String[] args) 
	{
		 // Load document
        try
        {
        	
        	PDFDocument pdfDoc = new PDFDocument ("C:\\test\\solicitor.pdf", null);
            
	        for (int i = 0; i < pdfDoc.getPageCount(); i++) 
			{
				PDFPage p = pdfDoc.getPage(i);
				Vector<Annotation> annots = p.getAnnotations();
				if(annots != null)
				{
					System.out.println("*** Page " + (i+1) +" contains " + annots.size() + " annotations ***");
					for(int count = 0; count < annots.size(); count++)
					{
						Annotation annot = annots.get(count);
						System.out.println("Annot Number " + (count + 1));
						
						// annotation type
						System.out.println("Type: " + annot.getSubtype());
						
						// annotation color (or fill color)
						System.out.println("Color: " + annot.getColor());
						
						// annotation subject
						System.out.println("Subject: " + annot.getSubject());
						
						// annotation creator
						System.out.println("Creator: " + annot.getCreator());
						
						// annotation modified date
						System.out.println("Modified Date: " + annot.getModifiedDate());
						
						// annotation location on the page 
						System.out.println("Location: " + annot.getRectangle());
						
						// annotation contents (this is the note attached to the annotation)
						System.out.println("Content: " + annot.getContents());
						
						// Does this annotation have an IRT parent annotation? 
						// Have an IRT parent annotations means that:
						// a) this is a reply to annotation to the parent annotation 
						// or 
						// b) that this annotation is grouped with the parent annotation
						if(annot.getIRTAnnotation() != null)
						{
							System.out.println("This annotation has an IRT parent annotation");
							
							// output the parent IRT annotation contents
							System.out.println("Parent Annotation Contents " + annot.getIRTAnnotation().getContents());
							
							// Is this a grouped annotation?
							System.out.println("This is a Grouped Annotation " + annot.isIRTGroup());

							// Is this a reply annotation?
							System.out.println("This is a Reply Annotation " + !annot.isIRTGroup());							
						}
					}
				}
			}
        }
        catch(PDFException pdfe)
        {
        	pdfe.printStackTrace();
        }
	}
}
