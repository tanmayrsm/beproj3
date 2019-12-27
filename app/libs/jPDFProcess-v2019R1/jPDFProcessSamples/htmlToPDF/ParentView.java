/*
 * Created on Nov 22, 2004
 *
 */
package jPDFProcessSamples.htmlToPDF;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Shape;

import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.Position;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

/**
 * @author Gerald Holmann
 *
 */
public class ParentView extends View
{
    ViewFactory viewFactory; // The ViewFactory for the hierarchy of views

    public ParentView(Element rootElement, ViewFactory viewFactory)
    {
        super(rootElement);
        this.viewFactory = viewFactory;
    }

//     These methods return key pieces of information required by
//     the View hierarchy.
    public ViewFactory getViewFactory()
    {
        return viewFactory;
    }
    
    public Container getContainer() 
    {
        return null; 
    }

//     These methods are abstract in View, so we've got to provide
//     dummy implementations of them here, even though they're never used.
    public void paint(Graphics g, Shape allocation) {}
    public float getPreferredSpan(int axis)
    {
        return 0.0f;
    }
    public int viewToModel(float x, float y, Shape a, Position.Bias[] bias)
    {
        return 0;
    }

    public Shape modelToView(int pos, Shape a, Position.Bias b)
            throws BadLocationException
    {
        return a;
    }
}
