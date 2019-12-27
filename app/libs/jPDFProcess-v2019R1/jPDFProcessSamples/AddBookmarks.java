/*
 * Created on Sep 24, 2008
 *
 */
package jPDFProcessSamples;

import com.qoppa.pdf.Bookmark;
import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.pdf.actions.GotoPageAction;

public class AddBookmarks
{
    public static void main (String [] args)
    {
        try
        {
            // Load the document
            PDFDocument pdfDoc = new PDFDocument ("input.pdf", null);

            // Get the root bookmark, create one if necessary
            Bookmark rootBK = pdfDoc.getRootBookmark();
            if (rootBK == null)
            {
                rootBK = pdfDoc.createRootBookmark ();
            }

            // Add a bookmark for every page
            for (int page = 0; page < pdfDoc.getPageCount(); ++page)
            {
                Bookmark bk = rootBK.addChildBookmark("Page " + (page + 1));
                bk.addAction(new GotoPageAction (pdfDoc.getIPage(page)));
            }
            
            // Save the document
            pdfDoc.saveDocument ("output.pdf");
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }
}
