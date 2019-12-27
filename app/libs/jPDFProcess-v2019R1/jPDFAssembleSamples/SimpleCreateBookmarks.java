package jPDFAssembleSamples;

import com.qoppa.pdf.Bookmark;
import com.qoppa.pdfAssemble.PDFAssemble;

public class SimpleCreateBookmarks
{
    public static void main (String [] args)
    {
    	try
        {
    		// Load the document
            PDFAssemble pdfDoc = new PDFAssemble ("doc1.pdf", null);
            
            if(pdfDoc.getPageCount() > 0)
			{
	    		// get current root bookmark tree
	            Bookmark rootBK = pdfDoc.getRootBookmark();
	    		
	    		// create a root bookmark tree if it's null
	    		if(rootBK == null)
				{
					rootBK = pdfDoc.createRootBookmark();
				}
	    		
	    		// add a bookmark for each page
	    		for (int i = 1; i <= pdfDoc.getPageCount(); i++)
				{
	    			// add a bookmark named "Page i" in the bookmark tree
					Bookmark bk = rootBK.addChildBookmark("Page" + " " + i);
					// add a go to page i action to the bookmark
					pdfDoc.addGoToPage(bk, i);
				}
				
	    		// save the document
				pdfDoc.saveDocument("doc1_bookmarks.pdf");
		    }
        }
	    catch(Throwable t)
	    {
	    	t.printStackTrace();
	    }
    }

}
