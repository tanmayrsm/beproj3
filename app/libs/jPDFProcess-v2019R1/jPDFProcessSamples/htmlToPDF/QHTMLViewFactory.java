package jPDFProcessSamples.htmlToPDF;

import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.View;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit.HTMLFactory;
import javax.swing.text.html.ImageView;

public class QHTMLViewFactory extends HTMLFactory
{
	public View create(Element elem) 
	{
	    Object o = elem.getAttributes().getAttribute(StyleConstants.NameAttribute);
	    if (o instanceof HTML.Tag) 
	    {
	    	HTML.Tag kind = (HTML.Tag) o;
	    	if ((kind == HTML.Tag.UL) || (kind == HTML.Tag.OL)) 
	    	{
	 		    return new QListView(elem);
	    	}
	    }
    	View v = super.create(elem);
        if (v instanceof ImageView)
        {
            ((ImageView)v).setLoadsSynchronously(true);
        }
        return v;
	}
}
