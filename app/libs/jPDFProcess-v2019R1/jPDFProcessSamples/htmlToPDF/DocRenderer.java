package jPDFProcessSamples.htmlToPDF;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.print.PageFormat;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Element;
import javax.swing.text.View;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.rtf.RTFEditorKit;

import com.qoppa.pdf.PDFException;
import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.pdfProcess.PDFPage;

public class DocRenderer
{
    private PageFormat m_PF;
    private PDFDocument m_PDFDoc;

    private int m_PageStartY;
    private Graphics2D m_Graphics;
    double m_Scale;
    
    public void renderHTML (PDFDocument pdfDoc, InputStream htmlStream, URL baseURL, PageFormat pf, boolean fitToPage) throws PDFException, IOException, BadLocationException
    {
        // Save globals
        m_PDFDoc = pdfDoc;
        m_PF = pf;

        // Read the document
        QHTMLEditorKit htmlKit = new QHTMLEditorKit();
        HTMLDocument htmlDocument = (HTMLDocument)htmlKit.createDefaultDocument();
        htmlDocument.putProperty("IgnoreCharsetDirective", new Boolean(true));
        htmlDocument.setBase(baseURL);
        htmlKit.read (htmlStream, htmlDocument, 0);

        // Create root view
        Element rootElement = htmlDocument.getDefaultRootElement();
        ParentView fakeView = new ParentView (rootElement, htmlKit.getViewFactory());
        View rootView = htmlKit.getViewFactory().create (rootElement);
        rootView.setParent(fakeView);

        // Calculate scale
        m_Scale = 1;
        if (fitToPage)
        {
            if (rootView.getPreferredSpan(View.X_AXIS) > pf.getImageableWidth())
            {
                m_Scale = pf.getImageableWidth() / rootView.getPreferredSpan(View.X_AXIS);
            }
            rootView.setSize (rootView.getPreferredSpan(View.X_AXIS), (float)pf.getImageableHeight());
        }
        else
        {
            rootView.setSize((float)pf.getImageableWidth(), (float)pf.getImageableHeight());
        }

        // Create the first page
        PDFPage page = m_PDFDoc.appendNewPage(m_PF.getWidth(), m_PF.getHeight());
        m_Graphics = page.createGraphics();
        m_Graphics.setClip((int)m_PF.getImageableX(), (int)m_PF.getImageableY(), 
                        (int)m_PF.getImageableWidth(), (int)m_PF.getImageableHeight());
        m_Graphics.translate(m_PF.getImageableX(), m_PF.getImageableY());
        m_Graphics.scale (m_Scale, m_Scale);

        // Get started on the painting
        m_PageStartY = 0;
        Rectangle allocation = new Rectangle (0, 0,
                                                (int)(rootView.getPreferredSpan(View.X_AXIS)),
                                                (int)(rootView.getPreferredSpan(View.Y_AXIS)));
        paintView(rootView, allocation);
    }

    public void renderRTF (PDFDocument pdfDoc, InputStream rtfStream, PageFormat pf) throws PDFException, IOException, BadLocationException
    {
        // Save globals
        m_PDFDoc = pdfDoc;
        m_PF = pf;
        m_Scale = 1.0;

        // Read the document
        RTFEditorKit rtfKit = new RTFEditorKit();
        DefaultStyledDocument rtfDocument = new DefaultStyledDocument();
        rtfKit.read (rtfStream, rtfDocument, 0);

        // Create RTF view
        Element rootElement = rtfDocument.getDefaultRootElement();
        ParentView fakeView = new ParentView (rootElement, rtfKit.getViewFactory());
        View rootView = rtfKit.getViewFactory().create (rootElement);
        rootView.setParent(fakeView);
        rootView.setSize((float)m_PF.getImageableWidth(), (float)m_PF.getImageableHeight());

        // Create the first page
        PDFPage page = m_PDFDoc.appendNewPage(m_PF.getWidth(), m_PF.getHeight());
        m_Graphics = page.createGraphics();
        m_Graphics.setClip((int)m_PF.getImageableX(), (int)m_PF.getImageableY(), 
                        (int)m_PF.getImageableWidth(), (int)m_PF.getImageableHeight());
        m_Graphics.translate(m_PF.getImageableX(), m_PF.getImageableY());
        m_Graphics.scale (m_Scale, m_Scale);

        // Get started on the painting
        m_PageStartY = 0;
        Rectangle allocation = new Rectangle (0, 0, (int)m_PF.getImageableWidth(), (int)m_PF.getImageableHeight());
        paintView (rootView, allocation);
    }
    
    private void paintView(View view, Shape allocation) throws PDFException
    {
    	int pageMinY = m_PageStartY;
    	int pageMaxY = (int)Math.ceil(m_PageStartY + (m_PF.getImageableHeight() / m_Scale));
    	int viewMinY = allocation.getBounds().y;
    	int viewMaxY = viewMinY + allocation.getBounds().height;

    	// No need to paint
    	if (viewMaxY < pageMinY)
    	{
    		return;
    	}

    	// if the view starts after the end of this page, create new pages
    	while(viewMinY > pageMaxY)
    	{
    		// Create new page
    		pageMinY += (int)Math.ceil(pageMinY + (m_PF.getImageableHeight() / m_Scale));
            createNewPage (pageMinY);
            pageMaxY = (int)Math.ceil(m_PageStartY + (m_PF.getImageableHeight() / m_Scale));
    	}
    	// Check if the view will fit entirely in the page
    	if (viewMaxY <= pageMaxY)
    	{
    		// Paint the element
    		view.paint(m_Graphics, allocation);
    	}
    	else
    	{
    		// This view has no children
    		if (view.getViewCount() == 0)
    		{
    			// If this is true, there have been other elements painted on this page,
    			// so we create a new page and paint this view on the next page
    			if (viewMinY > pageMinY)
    			{
                    // Create new page
                    createNewPage (viewMinY);
                    
                    // Paint the view.  We make a recursive call so that we don't have to rewrite the
                    // logic to paint the full view if it fits, or a partial view if it does not.
                    paintView(view, allocation);
    			}
    			// the else condition means that this is the first element on this page, which means that this
    			// element is too big to fit in a single page.  We paint partial element and go to the next page
    			else
    			{
            		// Paint the partial view
            		view.paint(m_Graphics, allocation);
            		
            		// Create new page starting at the end of this page
            		createNewPage(pageMaxY+1);
            		
            		// Paint the rest of the view.  We make a recursive call so that we don't have to rewrite
                    // the logic to paint the full view if it fits, or a partial view if it does not.
            		paintView(view, allocation);
    			}
    		}
    		// this view has children
    		else
    		{
    			// Get the amount of this view that we can paint on this page
    			int childMaxY = getMaxYForPage(view, allocation);
    			if(childMaxY > m_PageStartY)
    			{
    				// Clip rect
    				Rectangle clipRect = new Rectangle(0, m_PageStartY, (int)((m_PF.getImageableWidth()+4) / m_Scale), childMaxY - m_PageStartY);
    			
	    			// Paint this much
	    			m_Graphics.clip(clipRect);
	    			view.paint(m_Graphics, allocation);
    			
	    			// Create new page
	    			createNewPage(pageMaxY+1);
    			
    				// Call recursively to paint the rest of this view
	    			paintView(view, allocation);
    			}
    		}
    	}
    }
    
    private int getMaxYForPage(View view, Shape viewAllocation)
    {
    	int maxY = m_PageStartY;
    	int pageMaxY = (int)Math.ceil(m_PageStartY + (m_PF.getImageableHeight() / m_Scale));

        // Look at children allocations
        for(int i = 0; i<view.getViewCount();i++)
        {
            Shape childAllocation = view.getChildAllocation(i, viewAllocation);
            if (childAllocation != null)
            {
            	Rectangle childBounds = childAllocation.getBounds();
            	if (childBounds.getMaxY() < pageMaxY)
            	{
            		maxY = (int)Math.ceil(Math.max(childBounds.getMaxY(), maxY));
            	}
            	else
            	{
            		View childView = view.getView(i);
            		if (childView.getViewCount() > 0)
            		{
            			maxY = (int)Math.ceil(Math.max(getMaxYForPage(childView, childAllocation), maxY));
            		}
            		else
            		{
            			// We're done
            			return maxY;
            		}
            	}
            }
        }
        
        return maxY;
    }

    private PDFPage createNewPage (int newY) throws PDFException
    {
    	PDFPage page = m_PDFDoc.appendNewPage (m_PF.getWidth(), m_PF.getHeight());
        m_Graphics = page.createGraphics();
        m_Graphics.setClip((int)m_PF.getImageableX()-2, (int)m_PF.getImageableY()-2, 
                        (int)m_PF.getImageableWidth()+4, (int)m_PF.getImageableHeight()+4);
        m_Graphics.translate(m_PF.getImageableX(), m_PF.getImageableY());

        // New starting point for the page
        m_PageStartY = newY;
        
        // Scale and translate
        m_Graphics.scale (m_Scale, m_Scale);
        m_Graphics.translate(0, -m_PageStartY);
        
        return page;        
    }
}
