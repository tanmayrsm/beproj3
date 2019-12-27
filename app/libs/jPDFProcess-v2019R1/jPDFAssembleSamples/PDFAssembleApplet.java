package jPDFAssembleSamples;

import javax.swing.JApplet;
import javax.swing.UIManager;

/**
 * @author Qoppa Software
 *
 */
public class PDFAssembleApplet extends JApplet
{
	/**
	 * This method initializes 
	 * 
	 */
	public PDFAssembleApplet() 
	{
		super();
		init();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void init() 
	{
        setLookAndFeel();
        PDFAssemblePanel processPanel = new PDFAssemblePanel();
        // hide the API Panel with path to local jar file and api files (only used in the frame)
        processPanel.getJPAPI().setVisible(false);
        this.setContentPane(processPanel);
        this.setSize((int)(800 * SampleUtil.getDPIScalingMultiplier()),(int)(600 * SampleUtil.getDPIScalingMultiplier()));
	}
	
    /**
     * Set the look and feel.
     */
    private void setLookAndFeel ()
    {
        // Set the look and feel
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Throwable t)
        {
        	// ignore
        }
    }

    public static void  main (String [] args)
	{
	    PDFAssembleApplet applet = new PDFAssembleApplet ();
	    applet.setVisible(true);
	}
}