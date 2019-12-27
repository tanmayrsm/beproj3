package jPDFProcessSamples.htmlToPDF;

import java.net.URL;

import javax.swing.event.DocumentEvent;
import javax.swing.event.UndoableEditEvent;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.ElementIterator;
import javax.swing.text.GapContent;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import javax.swing.undo.UndoableEdit;


public class QHTMLDocument extends HTMLDocument 
{
	public static String DEFAULT_STYLE_SHEET_NAME = "style.css";
	  
	public QHTMLDocument() 
	{
		this(new GapContent(BUFFER_SIZE_DEFAULT), new StyleSheet());
	}
	public QHTMLDocument(StyleSheet styles) 
	{
		this(new GapContent(BUFFER_SIZE_DEFAULT), styles);
	}
	
	public QHTMLDocument(Content c, StyleSheet styles) 
	{
	    super(c, styles);
	}
	
	public void addAttributes(Element e, AttributeSet a) 
	{
		if ((e != null) && (a != null)) 
		{
			try 
			{
				writeLock();
		        //System.out.println("SHTMLDocument addAttributes e=" + e);
		        //System.out.println("SHTMLDocument addAttributes a=" + a);
		        int start = e.getStartOffset();
		        DefaultDocumentEvent changes = new DefaultDocumentEvent(start,
		            e.getEndOffset() - start, DocumentEvent.EventType.CHANGE);
		        AttributeSet sCopy = a.copyAttributes();
		        MutableAttributeSet attr = (MutableAttributeSet) e.getAttributes();
		        changes.addEdit(new AttributeUndoableEdit(e, sCopy, false));
		        attr.addAttributes(a);
		        changes.end();
		        fireChangedUpdate(changes);
		        fireUndoableEditUpdate(new UndoableEditEvent(this, changes));
			}
			finally 
			{
				writeUnlock();
			}
		}
	}
	public void removeElements(Element e, int index, int count)throws BadLocationException 
	{
		writeLock();
		int start = e.getElement(index).getStartOffset();
		int end = e.getElement(index + count - 1).getEndOffset();
		try {
			Element[] removed = new Element[count];
			Element[] added = new Element[0];
			for (int counter = 0; counter < count; counter++) {
				removed[counter] = e.getElement(counter + index);
			}
			DefaultDocumentEvent dde = new DefaultDocumentEvent(start, end
					- start, DocumentEvent.EventType.REMOVE);
			((AbstractDocument.BranchElement) e).replace(index, removed.length,
					added);
			dde.addEdit(new ElementEdit(e, index, removed, added));
			UndoableEdit u = getContent().remove(start, end - start);
			if (u != null) {
				dde.addEdit(u);
			}
			postRemoveUpdate(dde);
			dde.end();
			fireRemoveUpdate(dde);
			if (u != null) {
				fireUndoableEditUpdate(new UndoableEditEvent(this, dde));
			}
		} finally {
			writeUnlock();
		}
	}
	  public void insertStyleRef() throws Exception
	  {
		//try
		{
			String styleRef = "  <link rel=stylesheet type=\"text/css\" href=\"" + DEFAULT_STYLE_SHEET_NAME + "\">";
			Element defaultRoot = getDefaultRootElement();
			Element head = findElementDown(HTML.Tag.HEAD.toString(),
					defaultRoot);
			if (head != null) {
				Element pImpl = findElementDown(HTML.Tag.IMPLIED
						.toString(), head);
				if (pImpl != null) {
					Element link = findElementDown(HTML.Tag.LINK
							.toString(), pImpl);
					if (link != null) {
						setOuterHTML(link, styleRef);
					} else {
						insertBeforeEnd(pImpl, styleRef);
					}
				}
			} else {
				Element body = findElementDown(HTML.Tag.BODY.toString(),
						defaultRoot);
				insertBeforeStart(body, "<head>" + styleRef + "</head>");
			}
		}
	}

	/**
	 * check whether or not this SHTMLDocument has an explicit style sheet reference
	 *
	 * @return true, if a style sheet reference was found, false if not
	 */
	public boolean hasStyleRef() {
		return (getStyleRef() != null);
	}

	/**
	 * get the style sheet reference of the document in this
	 * <code>DocumentPane</code>.
	 *
	 * @return the reference to this document's style sheet or
	 *    null if none is found
	 */
	public String getStyleRef() {
		String linkName = null;
		Element link = findElementDown(HTML.Tag.LINK.toString(),
				getDefaultRootElement());
		if (link != null) {
			Object href = link.getAttributes()
					.getAttribute(HTML.Attribute.HREF);
			if (href != null) {
				linkName = href.toString();
			}
		}
		return linkName;
	}

	  /**
	   * Fetches the reader for the parser to use to load the document
	   * with HTML.  This is implemented to return an instance of
	   * SHTMLDocument.SHTMLReader.
	   */
	  public HTMLEditorKit.ParserCallback getReader(int pos)
	  {
	    Object desc = getProperty(Document.StreamDescriptionProperty);
	    if (desc instanceof URL) {
	        setBase((URL)desc);
	    }
	    SHTMLReader reader = new SHTMLReader(pos);
	    return reader;
	  }

	  /**
	   * This reader extends HTMLDocument.HTMLReader by the capability
	   * to handle SPAN tags
	   */
	  public class SHTMLReader extends HTMLDocument.HTMLReader {

	    /** action needed to handle SPAN tags */
	    SHTMLCharacterAction ca = new SHTMLCharacterAction();

	    /** the attributes found in a STYLE attribute */
	    AttributeSet styleAttributes;

	    /** indicates whether we're inside a SPAN tag */
	    boolean inSpan = false;

	    /**
	     * Constructor
	     */
	    public SHTMLReader(int offset) {
	      super(offset, 0, 0, null);
	    }

	    /**
	     * handle a start tag received by the parser
	     *
	     * if it is a SPAN tag, convert the contents of the STYLE
	     * attribute to an AttributeSet and add it to the contents
	     * of this tag.
	     *
	     * Otherwise let HTMLDocument.HTMLReader do the work.
	     */
	    public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
	      if(t == HTML.Tag.SPAN) {
	        if(a.isDefined(HTML.Attribute.STYLE)) {
	          String decl = (String)a.getAttribute(HTML.Attribute.STYLE);
	          a.removeAttribute(HTML.Attribute.STYLE);
	          styleAttributes = getStyleSheet().getDeclaration(decl);
	          a.addAttributes(styleAttributes);
	        }
	        else {
	          styleAttributes = null;
	        }
	        TagAction action = ca;
	        if (action != null) 
            {
	          /**
	           * remember which part we're in for handleSimpleTag
	           */
	          inSpan = true;

	          action.start(t, a);
	        }
	      }
	      else {
	        super.handleStartTag(t, a, pos);
	      }
	    }

	    /**
	     * SPAN tags are directed to handleSimpleTag by the parser.
	     * If a SPAN tag is detected in this method, it gets redirected
	     * to handleStartTag and handleEndTag respectively.
	     */
	    public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, int pos) {
	      if(t == HTML.Tag.SPAN) {
	        if(inSpan) {
	          handleEndTag(t, pos);
	        }
	        else {
	          handleStartTag(t, a, pos);
	        }
	      }
	      else {
	        super.handleSimpleTag(t, a, pos);
	      }
	    }

	    /**
	     * If a SPAN tag is directed to this method, end its action,
	     * otherwise, let HTMLDocument.HTMLReader do the work
	     */
	    public void handleEndTag(HTML.Tag t, int pos) 
        {
	      if(t == HTML.Tag.SPAN) {
	        TagAction action = ca;
	        if (action != null) {
	          /**
	           * remember which part we're in for handleSimpleTag
	           */
	          inSpan = false;

	          action.end(t);
	        }
	      }
	      else {
	        super.handleEndTag(t, pos);
	      }
	    }
	    /**
	     * this action is used to read the style attribute from
	     * a SPAN tag and to map from HTML to Java attributes.
	     */
	    class SHTMLCharacterAction extends HTMLDocument.HTMLReader.CharacterAction {
	      public void start(HTML.Tag t, MutableAttributeSet attr) {
	        pushCharacterStyle();
	        if (attr.isDefined(IMPLIED)) {
	          attr.removeAttribute(IMPLIED);
	        }
	        charAttr.addAttribute(t, attr.copyAttributes());
	        if (styleAttributes != null) {
	          charAttr.addAttributes(styleAttributes);
	        }
	        if(charAttr.isDefined(HTML.Tag.SPAN)) {
	          charAttr.removeAttribute(HTML.Tag.SPAN);
	        }
	        //System.out.println("mapping attributes");
	        charAttr = (MutableAttributeSet) new AttributeMapper(charAttr).
	                   getMappedAttributes(AttributeMapper.toJava);
	      }

	      public void end(HTML.Tag t) {
	        popCharacterStyle();
	      }
	    }
	  }
	  public static Element findElementDown(String name, Element parent) 
	  {
	    Element foundElement = null;
	    ElementIterator eli = new ElementIterator(parent);
	    Element thisElement = eli.first();
	    while(thisElement != null && foundElement == null) 
	    {
	      if(thisElement.getName().equalsIgnoreCase(name)) 
	      {
	        foundElement = thisElement;
	      }
	      thisElement = eli.next();
	    }
	    return foundElement;
	  }
}
