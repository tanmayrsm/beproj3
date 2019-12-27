package jPDFAssembleSamples;

import com.qoppa.pdfAssemble.PDFAssemble;

public class SimpleSplit
{
    public static void main (String [] args)
    {
        try
        {
            // Load the original PDF document
        	PDFAssemble pdfDoc1 = new PDFAssemble ("doc1.pdf", null);
		   
        	// create 2 new PDF documents
		    PDFAssemble split1 = new PDFAssemble();
 			PDFAssemble split2 = new PDFAssemble();
            
			// Add pages 1 and 2 to split1 
            split1.appendPage (pdfDoc1, 0);
			split1.appendPage (pdfDoc1, 1);
			
			// Add pages 2 and 3 to split2
			split2.appendPage (pdfDoc1, 2);
			split2.appendPage (pdfDoc1, 3);
			
			// save two new PDF documents
			split1.saveDocument ("split1.pdf");
			split2.saveDocument ("split2.pdf"); 
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }
}