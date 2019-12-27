package jPDFFieldsSamples;

import javax.swing.JApplet;
import javax.swing.UIManager;

public class PDFFieldsApplet extends JApplet
{
	/**
	 * This method initializes 
	 * 
	 */
	public PDFFieldsApplet() 
	{
		super();
	}
		
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void init() 
	{
		this.setLookAndFeel();
        this.setContentPane(new PDFFieldsSample());
        this.setSize((int)(600 * SampleUtil.getDPIScalingMultiplier()), (int)(620 * SampleUtil.getDPIScalingMultiplier()));
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
}
