/*
 * Created on Dec 24, 2010
 *
 */
package jPDFProcessSamples;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qoppa.pdf.PDFException;
import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.pdfProcess.PDFGraphics;
import com.qoppa.pdfProcess.PDFPage;

public class ServletSample extends HttpServlet
{
    /**
    * @see javax.servlet.http.HttpServlet#void (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
    */
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        servePDF(req, resp);
    }

    /**
    * @see javax.servlet.http.HttpServlet#void (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
    */
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        servePDF(req, resp);
    }
    
    private void servePDF (HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException
    {
        try
        {
            // Get servlet output stream
            ServletOutputStream sOut = res.getOutputStream();
            res.setContentType( "application/pdf" );  
            res.setHeader("Content-disposition", "attachment; filename=" + "report.pdf" );
                              
            // Load document
            PDFDocument pdfDoc = new PDFDocument ("input.pdf", null);
            
            // Loop through all pages
            Font wmFont = PDFGraphics.HELVETICA.deriveFont(64f);
            for (int pageIx = 0; pageIx < pdfDoc.getPageCount(); ++pageIx)
            {
                // Get the page
                PDFPage page = pdfDoc.getPage(pageIx);
                
                // Get a graphics object to draw onto the page
                Graphics2D g2d = page.createGraphics();
                
                // Draw watermark
                g2d.setColor (new Color (160, 160, 160, 160));
                g2d.setFont(wmFont);
                g2d.translate(72, 72 + g2d.getFontMetrics().getAscent());
                g2d.rotate(Math.toRadians(45));
                g2d.drawString ("Confidential", 0, 0);
            }
            
            // Save the document
            pdfDoc.saveDocument (sOut);
    
            // Close the server output stream
            sOut.close();
        }
        catch (PDFException pdfE)
        {
            throw new ServletException(pdfE);
        }
    }
}
