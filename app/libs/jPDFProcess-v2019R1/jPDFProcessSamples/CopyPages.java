package jPDFProcessSamples;

import com.qoppa.pdfProcess.PDFDocument;

/*
* This samples shows how to copy pages from one PDF to another new PDF
*/
public class CopyPages{
                
    public static void main(String[] args) 
    {
                try
                {
                        PDFDocument inputPDF = new PDFDocument("C:/test/input.pdf", null);
                        PDFDocument ouputPDF = new PDFDocument();
                        for (int i = 0; i < inputPDF.getPageCount(); i++) 
                        {
                                ouputPDF.appendPage(inputPDF.getPage(i));
                        }
                
                        ouputPDF.saveDocument("C:/test/output.pdf");
        } 
        catch (Throwable t) 
        {
            t.printStackTrace();
        }
    }
}
