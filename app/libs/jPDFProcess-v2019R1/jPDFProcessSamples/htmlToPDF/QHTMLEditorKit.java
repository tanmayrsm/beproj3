package jPDFProcessSamples.htmlToPDF;

import javax.swing.text.Document;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

public class QHTMLEditorKit extends HTMLEditorKit 
{
	private final static QHTMLViewFactory m_ViewFactory = new QHTMLViewFactory ();
	
	public Document createDefaultDocument() 
	{
	    StyleSheet ss = new StyleSheet();
	    try 
	    {
	      ss.importStyleSheet(Class.forName("javax.swing.text.html.HTMLEditorKit").getResource(DEFAULT_CSS));
	    }
	    catch(Exception e) {}
	    
	    QHTMLDocument doc = new QHTMLDocument(ss);
	    doc.setParser(getParser());
	    doc.setAsynchronousLoadPriority(4);
	    doc.setTokenThreshold(100);
	    return doc;
	}
	
	public ViewFactory getViewFactory()
	{
		return m_ViewFactory;
	}
}

