package jPDFProcessSamples;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.qoppa.pdf.Layer;
import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.pdfProcess.PDFGraphics;
import com.qoppa.pdfProcess.PDFPage;

/**
* This sample Java program shows how to add new layers to a PDF document 
* and add content on to the new layers using jPDFProcess. 
* jPDFProcess can draw onto new or existing layers, on new or existing PDF documents / pages.
*/
public class CreateLayers 
{
	public static void main(String [] args)
	{
		try
		{
			// Create a new document
			PDFDocument pdf = new PDFDocument();
			
			// Add a blank page to the document
			PDFPage page = pdf.appendNewPage(8.5 * 72, 11 * 72);
			
			// Add new layers
			Layer layer1 = pdf.addLayer("Layer 1", Layer.STATE_ON, true);
			Layer layer2 = pdf.addLayer("Layer 2", Layer.STATE_ON, true);
					
			// Add content to the back layer
            Graphics2D g2d = page.createGraphics(layer1);
            g2d.setFont (PDFGraphics.HELVETICA.deriveFont(Font.BOLD, 24.0f));
            g2d.drawString ("This is a string in layer 1.", 72, 72);
            g2d.setColor(Color.red);
            g2d.fillOval(216, 72, 10, 20);
            g2d.fillOval(246, 72, 10, 20);
            g2d.fillOval(276, 72, 10, 20);
            // g2d.drawImage(myImage, 200, 200, null);
            
            // Add content to the front layer
            g2d = page.createGraphics(layer2);
            g2d.setFont (PDFGraphics.HELVETICA.deriveFont(Font.BOLD, 24.0f));
            g2d.drawString ("This is a string in layer 2.", 72, 144);
            g2d.setColor(Color.blue);
            g2d.fillOval(216, 144, 10, 20);
            g2d.fillOval(246, 144, 10, 20);
            g2d.fillOval(276, 144, 10, 20);
            // g2d.drawImage(myImage, 200, 300, null);
            
            pdf.saveDocument("output.pdf");
		}
		catch(Throwable t)
		{
			t.printStackTrace();
		}
	}
}
