package jPDFProcessSamples.htmlToPDF;

import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.html.CSS;
import javax.swing.text.html.HTML;
import javax.swing.text.html.StyleSheet;

public class AttributeMapper extends SimpleAttributeSet {

	  public static final int toHTML = 1;
	  public static final int toJava = 2;
	  private static StyleSheet STYLE_SHEET = new StyleSheet();

	  public AttributeMapper() {
	    super();
	  }

	  public AttributeMapper(AttributeSet a) {
	    super(a);
	  }

	  public AttributeSet getMappedAttributes(int direction) {
	    switch(direction) {
	      case toHTML:
	        mapToHTMLAttributes();
	        break;
	      case toJava:
	        mapToJavaAttributes();
	        break;
	    }
	    //System.out.println("AttributeMapper transformed attributes=");
	    //de.calcom.cclib.html.HTMLDiag hd = new de.calcom.cclib.html.HTMLDiag();
	    //hd.listAttributes(this, 2);
	    return this;
	  }

	  private void mapToHTMLAttributes() {
	    Object cssFontFamily = getAttribute(CSS.Attribute.FONT_FAMILY);
	    if(cssFontFamily != null) {
	      if(cssFontFamily.toString().equalsIgnoreCase("SansSerif")) {
	        addAttribute(CSS.Attribute.FONT_FAMILY, "SansSerif, Sans-Serif");
	        //System.out.println("mapToHTMLAttributes SansSerif, Sans-Serif");
	      }
	      else if(cssFontFamily.toString().indexOf("Monospaced") > -1) {
	        addAttribute(CSS.Attribute.FONT_FAMILY, "Monospace, Monospaced");
	      }
	    }
	    /*
	    Object cssFontSize = getAttribute(CSS.Attribute.FONT_SIZE);
	    if(cssFontSize != null) {
	      int size = new Float(new LengthValue(cssFontSize).getValue() / 1.3).intValue();
	      addAttribute(CSS.Attribute.FONT_SIZE, Integer.toString(size) + "pt");
	    }
	    */
	  }

	  private void mapToJavaAttributes() {
	    Object htmlFontFace = getAttribute(HTML.Attribute.FACE);
	    Object cssFontFamily = getAttribute(CSS.Attribute.FONT_FAMILY);
	    if(htmlFontFace != null) {
	      if(cssFontFamily != null) {
	        removeAttribute(HTML.Attribute.FACE);
	        if(cssFontFamily.toString().indexOf("Sans-Serif") > -1) {
	          STYLE_SHEET.addCSSAttribute(this, CSS.Attribute.FONT_FAMILY, "SansSerif");
	        }
	        else if(cssFontFamily.toString().indexOf("Monospace") > -1) {
	        	STYLE_SHEET.addCSSAttribute(this, CSS.Attribute.FONT_FAMILY, "Monospaced");
	        }
	      }
	      else {
	        removeAttribute(HTML.Attribute.FACE);
	        if(htmlFontFace.toString().indexOf("Sans-Serif") > -1) {
	        	STYLE_SHEET.addCSSAttribute(this, CSS.Attribute.FONT_FAMILY, "SansSerif");
	        }
	        else if(htmlFontFace.toString().indexOf("Monospace") > -1) {
	        	STYLE_SHEET.addCSSAttribute(this, CSS.Attribute.FONT_FAMILY, "Monospaced");
	        }
	        else {
	        	STYLE_SHEET.addCSSAttribute(this, CSS.Attribute.FONT_FAMILY, htmlFontFace.toString());
	        }
	      }
	    }
	    else {
	      if(cssFontFamily != null) {
	        if(cssFontFamily.toString().indexOf("Sans-Serif") > -1) {
	        	STYLE_SHEET.addCSSAttribute(this, CSS.Attribute.FONT_FAMILY, "SansSerif");
	        }
	        else if(cssFontFamily.toString().indexOf("Monospace") > -1) {
	        	STYLE_SHEET.addCSSAttribute(this, CSS.Attribute.FONT_FAMILY, "Monospaced");
	        }
	      }
	    }
	  }
}
