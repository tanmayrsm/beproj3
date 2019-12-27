package jPDFTextSamples;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.qoppa.pdf.FontSettings;
import com.qoppa.pdfText.PDFText;

/**
 * @author Gerald Holmann
 *
 */
public class PDFTextFrame extends JFrame
{
	/**
	 * This method initializes 
	 * 
	 */
	public PDFTextFrame()
	{
		super();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() 
	{
        this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        PDFTextPanel pdfPanel = new PDFTextPanel();
        this.setContentPane(pdfPanel);
        this.setSize((int)(750 * SampleUtil.getDPIScalingMultiplier()),(int)(750 * SampleUtil.getDPIScalingMultiplier()));
        setLocationRelativeTo(null);
        this.setTitle("jPDFText Sample - " + PDFText.getVersion());

        try
		{
    		// Try to set multiple images, java 1.6 only
			Method setIconImages = this.getClass().getMethod("setIconImages", new Class[] {List.class});
			Vector imageList = new Vector();
			imageList.add(ImageIO.read(getClass().getResourceAsStream("jPDFText16.png")));
			imageList.add(ImageIO.read(getClass().getResourceAsStream("jPDFText32.png")));
			setIconImages.invoke(this, new Object[] {imageList});
		}
		catch (Throwable t1)
		{
			try
	        {
	            this.setIconImage(ImageIO.read(getClass().getResourceAsStream("jPDFText16.png")));
	        }
	        catch(Throwable t2)
	        {
	            // ignore
	        }
		}

        // load sample pdf file
        pdfPanel.openPDFResource(PDFTextPanel.WELCOME_PDF);
        pdfPanel.extractText();
	}
	
	
	public static void  main (String [] args)
	{
		try
		{
			// Create a log file in the user's home directory
			File outFile = new File(System.getProperty("user.home") + File.separator + "jpdftext.log");
			PrintStream out = new PrintStream(new FileOutputStream(outFile));
			System.setOut(out);
			System.setErr(out);
			
			// Initial log information
			SampleUtil.libraryLogStart(PDFText.class);			
		}
		catch (Throwable t)
		{
			// Do nothing, continue without log file
		}
		
	    // Set the look and feel
	    setLookAndFeel();
	    
		// When rendering, use a substitute font when the specified font cannot
		// be found, or the is an error with the embedded font, otherwise the
		// document will not be rendered and an exception will be thrown.
		FontSettings.setUseSubstituteFont(true);

	    SwingUtilities.invokeLater(new Runnable() {
	    	@Override
	    	public void run()
	    	{
	    		// Create and show the frame
	    	    PDFTextFrame frame = new PDFTextFrame();
	    	    frame.setVisible(true);
	    	}
	    });
	}
		
    /**
     * Set the look and feel.
     */
    private static void setLookAndFeel()
    {
        // Set the look and feel
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Throwable t)
        {
            try
            {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            catch (Throwable tt)
            {
                // ignore
            }
        }
    }
}