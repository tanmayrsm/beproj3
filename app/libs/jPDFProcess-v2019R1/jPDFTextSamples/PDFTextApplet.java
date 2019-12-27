package jPDFTextSamples;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JApplet;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 * @author Gerald Holmann
 *
 */
public class PDFTextApplet extends JApplet
{
	/**
	 * This method initializes 
	 * 
	 */
	public PDFTextApplet() 
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
		
		PDFTextPanel pdfPanel = new PDFTextPanel();
        
        pdfPanel.getJpAPI().setVisible(false);
        this.setContentPane(pdfPanel);
        this.setSize((int)(700 * SampleUtil.getDPIScalingMultiplier()),(int)(795 * SampleUtil.getDPIScalingMultiplier()));
	}
	
	public void start()
	{
        // load sample pdf file
        try
        {
            // Load a PDF document from a URL
            URL pdfURL = createURL(getParameter("OpenURL"));
            if (pdfURL != null)
            {
                PDFTextPanel pdfPanel = (PDFTextPanel)getContentPane();
                pdfPanel.openPDF(pdfURL);
                pdfPanel.extractText();
            }
        }
        catch (Throwable t)
        {
            t.printStackTrace();
            JOptionPane.showMessageDialog(this, t.getMessage());
        }
	}
	
    private URL createURL (String urlString) throws MalformedURLException
    {
        if (urlString == null || urlString.trim().length() == 0)
        {
            return null;
        }

        if (urlString.startsWith("http://") || urlString.startsWith ("https://"))
        {
            return new URL (urlString);
        }
        else
        {
            return new URL (getDocumentBase(), urlString);
        }
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
	    PDFTextApplet applet = new PDFTextApplet ();
        applet.setVisible(true);
	}
}