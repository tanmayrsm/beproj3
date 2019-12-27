package jPDFProcessSamples;

import javax.swing.JApplet;
import javax.swing.UIManager;

/**
 * @author Gerald Holmann
 *
 */
public class PDFProcessApplet extends JApplet
{
	/**
	 * This method initializes 
	 * 
	 */
	public PDFProcessApplet() 
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
        PDFProcessPanel processPanel = new PDFProcessPanel();
        // hide the API Panel with path to local jar file and api files (only used in the frame)
        processPanel.getJPAPI().setVisible(false);
        this.setContentPane(processPanel);
        this.setSize((int)(800 * SampleUtil.getDPIScalingMultiplier()),(int)(600 * SampleUtil.getDPIScalingMultiplier()));
	}
	
	/**
	 * Set the look and feel.
	 */
	private void setLookAndFeel()
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
	    PDFProcessApplet applet = new PDFProcessApplet ();
	    applet.setVisible(true);
	}
}