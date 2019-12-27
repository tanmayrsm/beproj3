package jPDFImagesSamples;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qoppa.pdf.PDFException;
import com.qoppa.pdfImages.PDFImages;

public class PDFImagesServlet extends HttpServlet
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
            res.setContentType( "image/jpeg" );  
            res.setHeader("Content-disposition", "attachment; filename=" + "page0.jpg");
                              
            // Load document
            PDFImages pdf = new PDFImages ("input.pdf", null);
            
            //
            // This sample serves the first page of the PDF document as a JPEG file.  The sample can be modified to take
            // the page number desired inteh HTTP request and send that back instead.
            //
            pdf.savePageAsJPEG(0, sOut, 100, 0.8f);
    
            // Close the server output stream
            sOut.close();
        }
        catch (PDFException pdfE)
        {
            throw new ServletException(pdfE);
        }
    }
}
