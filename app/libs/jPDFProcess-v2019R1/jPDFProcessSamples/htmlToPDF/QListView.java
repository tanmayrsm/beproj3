package jPDFProcessSamples.htmlToPDF;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.text.Element;
import javax.swing.text.html.ListView;
import javax.swing.text.html.StyleSheet;

public class QListView extends ListView
{
	private StyleSheet.ListPainter m_LP;
	
	public QListView (Element elem)
	{
		super (elem);
	}
	
	public void paintBullet (Graphics g, Rectangle alloc, int childIndex)
	{
		m_LP.paint(g, alloc.x, alloc.y, alloc.width, alloc.height, this, childIndex);		
	}	
	
    protected void setPropertiesFromAttributes() 
    {
    	super.setPropertiesFromAttributes();
    	m_LP = getStyleSheet().getListPainter(getAttributes());
    }
}
