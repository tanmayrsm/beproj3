package jPDFAssembleSamples;

import java.awt.image.BufferedImage;
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

import com.qoppa.pdfAssemble.PDFAssemble;

/**
 * @author Qoppa Software
 *
 */
public class PDFAssembleFrame extends JFrame
{
    private static boolean m_ShowAPI = true;

	/**
	 * This method initializes 
	 * 
	 */
	public PDFAssembleFrame() 
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
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        setTitle("jPDFAssemble Sample - " + PDFAssemble.getVersion());
        
        PDFAssemblePanel pdfProcessPanel = new PDFAssemblePanel();
        // hide label about sample code (only used in the applet)
        pdfProcessPanel.getJlSampleCode().setVisible(false);
        pdfProcessPanel.getJPAPI().setVisible (m_ShowAPI);

        this.setContentPane(pdfProcessPanel);
        this.setSize((int)(800 * SampleUtil.getDPIScalingMultiplier()), (int)(650 * SampleUtil.getDPIScalingMultiplier()));
        this.setLocationRelativeTo(null);

        try
		{
    		// Try to set multiple images, java 1.6 only
			Method setIconImages = this.getClass().getMethod("setIconImages", new Class[] {List.class});
			Vector<BufferedImage> imageList = new Vector<BufferedImage>();
			imageList.add(ImageIO.read(getClass().getResourceAsStream("jPDFAssemble16.gif")));
			imageList.add(ImageIO.read(getClass().getResourceAsStream("jPDFAssemble32.png")));
			setIconImages.invoke(this, new Object[] {imageList});
		}
		catch (Throwable t1)
		{
			try
	        {
	            this.setIconImage(ImageIO.read(getClass().getResourceAsStream("jPDFAssemble16.gif")));
	        }
	        catch(Throwable t2)
	        {
	            // ignore
	        }
		}
	}
	
	
	public static void  main (String [] args)
	{
		try
		{
			// Create a log file in the user's home directory
			File outFile = new File(System.getProperty("user.home") + File.separator + "jpdfassemble.log");
			PrintStream out = new PrintStream(new FileOutputStream(outFile));
			System.setOut(out);
			System.setErr(out);
			
			// Initial log information
			SampleUtil.libraryLogStart(PDFAssemble.class);			
		}
		catch (Throwable t)
		{
			// Do nothing, continue without log file
		}
		
        // Set the look and feel
        setLookAndFeel();
        
        // Check flag to show api
        if (args != null && args.length > 0 && "-hideapi".equalsIgnoreCase(args [0]))
        {
            m_ShowAPI = false;
        }

        SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
		        // Create and show the frame
		        PDFAssembleFrame frame = new PDFAssembleFrame ();
			    frame.setVisible(true);
			}
        });
	}
		
    /**
     * Set the look and feel.
     */
    private static void setLookAndFeel ()
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