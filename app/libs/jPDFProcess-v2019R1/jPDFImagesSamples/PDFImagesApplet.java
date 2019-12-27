/*
 * Created on May 31, 2005
 *
 */
package jPDFImagesSamples;


import javax.swing.JApplet;

/**
 * @author Gerald Holmann
 *
 */
public class PDFImagesApplet extends JApplet 
{
    /**
     * This method initializes this
     * 
     * @return void
     */
    public void init() 
    {
        // look and feel
        PDFImagesSample.setLookAndFeel();

        // Set the size
        setSize((int)(750 * SampleUtil.getDPIScalingMultiplier()), (int)(520 * SampleUtil.getDPIScalingMultiplier()));

        // Set the content pane
        PDFImagesSample contentPanel = new PDFImagesSample();
        contentPanel.getJPAPI().setVisible(false);
        setContentPane (contentPanel);
    }
}
