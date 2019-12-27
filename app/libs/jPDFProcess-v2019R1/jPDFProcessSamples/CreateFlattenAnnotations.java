package jPDFProcessSamples;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import com.qoppa.pdf.PDFException;
import com.qoppa.pdf.annotations.Callout;
import com.qoppa.pdf.annotations.FreeText;
import com.qoppa.pdf.annotations.IAnnotationFactory;
import com.qoppa.pdf.annotations.RubberStamp;
import com.qoppa.pdf.annotations.Square;
import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.pdfProcess.PDFPage;

public class CreateFlattenAnnotations
{
	public static void main(String[] args)
	{
		try
		{
			PDFDocument pdfDoc = new PDFDocument("C:/myfolder/test.pdf", null);
			
			
			// get the annotations factory which allows to create new annotations
			IAnnotationFactory annotFactory = pdfDoc.getAnnotationFactory();
			
			// create a standard rubber stamp annotation
			RubberStamp rubberstamp = annotFactory.createRubberStamp("Confidential", Color.BLUE);
			rubberstamp.setRectangle(new Rectangle2D.Double(50, 50, 100, 25));
	        rubberstamp.setIconName("SBConfidential"); 
			rubberstamp.setRotation(45);

			// create a square annotation
			Square square = annotFactory.createSquare("This is my square annotation content");
			square.setRectangle(new Rectangle2D.Double(20, 20, 40, 40));
			square.setColor(Color.GREEN);
			// set the stroke with dashed border style
			square.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[] {3, 3}, 0));
			
			// Create a free text
			FreeText freeText = annotFactory.createFreeText("free text");
			freeText.setRectangle(new Rectangle2D.Double(100, 100, 100, 50));
			freeText.setTextColor(Color.RED);
			freeText.setColor(Color.LIGHT_GRAY);
			freeText.setPDFFont("Helvetica-BoldOblique", 14f);
			
			// Create a typewriter
			FreeText typeWriter = annotFactory.createTypeWriter("type writer");
			typeWriter.setRectangle(new Rectangle2D.Double(200, 200, 100, 25));
			typeWriter.setTextColor(Color.blue);
			typeWriter.setPDFFont("Courier-BoldOblique", 14f);

			// Create a callout
			Callout callout = annotFactory.createCallout("callout");
			callout.setRectangle(new Rectangle2D.Double(300, 300, 100, 100));
			callout.setInnerRect(new Rectangle2D.Double(0, 0, 50, 50));
			callout.setArrow(new double[] {100, 100, 75, 25, 50, 25});
			callout.setColor(Color.GREEN);
			callout.setTextColor(Color.GRAY);
			callout.setPDFFont("Times-BoldItalic", 14f);

			// get first page in PDF
			PDFPage pdfPage = pdfDoc.getPage(0);

			// Add the annotations to the page
			pdfPage.addAnnotation(rubberstamp);
			pdfPage.addAnnotation(square);
			pdfPage.addAnnotation(freeText);
			pdfPage.addAnnotation(typeWriter);
			pdfPage.addAnnotation(callout);
			
			// save document with annotations
			pdfDoc.saveDocument("C:/myfolder/annotations.pdf");
			
			// Flatten the annotations
			pdfPage.flattenAnnotations(false);
			
			// save flattened document
			pdfDoc.saveDocument("C:/myfolder/annotations_flatten.pdf");

		}
		catch (PDFException ex)
		{
			ex.printStackTrace();
		}
		catch (IOException ex)
		{ 
			ex.printStackTrace();
		}
	}

}
