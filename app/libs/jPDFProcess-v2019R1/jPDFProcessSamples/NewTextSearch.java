package jPDFProcessSamples;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.Vector;

import com.qoppa.pdf.TextPosition;
import com.qoppa.pdf.annotations.TextMarkup;
import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.pdfProcess.PDFPage;

public class NewTextSearch
{
 public static void main(String[] args)
 {
 try
 {
 // Open the document
 PDFDocument inDoc = new PDFDocument("test09.pdf", null);

// Loop through the pages, searching for text
 for (int pageIx = 0; pageIx < inDoc.getPageCount(); ++pageIx)
 {
 // Search for the text in a page
 PDFPage page = inDoc.getPage(pageIx);

Vector<TextPosition> searchResults = page.findText("the", false, false);

if (searchResults.size() > 0)
 {
 for (int count = 0; count < searchResults.size(); ++count)
 {
 // Get the position of the text
 TextPosition textPos = searchResults.get(count);

Vector<Point2D[]> quadList = textPos.getPDFQuadrilaterals();

// Create markup annotation and add it to the page
 TextMarkup markup = inDoc.getAnnotationFactory().createTextMarkup("Test Markup", quadList, "Highlight");
 markup.setColor(Color.yellow);
 page.addAnnotation(markup);
 }
 }
 }

inDoc.saveDocument("test_markup_output.pdf");
 }
 catch (Throwable t)
 {
 t.printStackTrace();
 }
 }
}