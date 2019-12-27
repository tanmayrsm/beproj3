/*
 * This sample java program uses jPDFWriter 
 * to open a text file and create a PDF file 
 * from the text contained in the text file.
 * It takes care of pagination.
 *
 */
package jPDFProcessSamples;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.AttributedString;

import com.qoppa.pdf.PDFException;
import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.pdfProcess.PDFGraphics;
import com.qoppa.pdfProcess.PDFPage;

public class TextToPDF
{
    public static void main (String [] args)
    {
        // Page dimensions and margins, in inches
        float pageWidth = 8.5f;
        float pageHeight = 11f;
        
        float marginLeft = 1;
        float marginTop = 1;
        float marginBottom = 1;
        float marginRight = 1;

        try 
        {
            // Create the PDF document
            PDFDocument pdfDoc = new PDFDocument();
            
            // Create font
            Font font = PDFGraphics.HELVETICA.deriveFont(Font.PLAIN, 11f);

            // Init page information
            PDFPage newPage = null;
            Graphics2D g2 = null;
            FontMetrics fm = null;
            float currentY = marginTop * 72;
            float wrapWidth = (pageWidth - (marginLeft + marginRight)) * 72f;
            
            // Create a reader to read the file using the default OS encoding
            // !! This assumes that the text file is using the default OS encoding 
            // otherwise you will need to specify the encoding
            BufferedReader reader = new BufferedReader (new FileReader("C:\\myfolder\\input.txt"));
            String line = reader.readLine();
            while (line != null)
            {
                // Create new page when needed
                if (newPage == null)
                {
                    newPage = pdfDoc.appendNewPage(pageWidth * 72, pageHeight * 72);
                    g2 = newPage.createGraphics();
                    g2.setFont(font);
                    fm = g2.getFontMetrics();
                    currentY = marginTop * 72;
                }
                
                if (line.length() <= 0)
            	{
            		// Advance to next line
                    currentY += fm.getHeight();
                    line = reader.readLine();
                	continue;
            	}
                
                AttributedString attrString = new AttributedString(line);
                attrString.addAttribute(TextAttribute.FONT, font, 0, line.length());
                LineBreakMeasurer lbm = new LineBreakMeasurer(attrString.getIterator(), g2.getFontRenderContext());
                
                int offset = 0;
                
                while (offset < line.length())
                {
                	offset = lbm.nextOffset(wrapWidth);
                	
                	// Draw the line
                    g2.drawString(line.substring(lbm.getPosition(), offset), marginLeft * 72, currentY);
                    
                    // update the line LineBreakMeasurer position
                    lbm.setPosition(offset);
                    
                    // Advance to next line
                    currentY += fm.getHeight();
                    if (currentY >= ((pageHeight - marginBottom) * 72))
                    {
                    	newPage = pdfDoc.appendNewPage(pageWidth * 72, pageHeight * 72);
                        g2 = newPage.createGraphics();
                        g2.setFont(font);
                        fm = g2.getFontMetrics();
                        currentY = marginTop * 72;
                    }
                }

                // Read the next line
                line = reader.readLine();
            }
         
            // Close the text file
            reader.close();
            
            // Save the document
            pdfDoc.saveDocument("C:\\myfolder\\output.pdf");
        }
        catch (PDFException pdfE)
        {
            pdfE.printStackTrace();
        }
        catch (IOException ioE)
        {
            ioE.printStackTrace();
        }
    }
}
