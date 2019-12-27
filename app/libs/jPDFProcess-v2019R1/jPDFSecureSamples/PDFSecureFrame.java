package jPDFSecureSamples;

import java.awt.HeadlessException;
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

import com.qoppa.pdfSecure.PDFSecure;

public class PDFSecureFrame extends JFrame
{
    private static boolean m_ShowAPI = true;
    
    public PDFSecureFrame() throws HeadlessException
    {
        super();
        initialize();
    }
    
    public static void  main (String [] args)
    {
		try
		{
			// Create a log file in the user's home directory
			File outFile = new File(System.getProperty("user.home") + File.separator + "jpdfsecure.log");
			PrintStream out = new PrintStream(new FileOutputStream(outFile));
			System.setOut(out);
			System.setErr(out);
			
			// Initial log information
			SampleUtil.libraryLogStart(PDFSecure.class);			
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
		        PDFSecureFrame frame = new PDFSecureFrame();
		        frame.setVisible(true);
			}
		});
    }
        
    public void initialize()
    {
        this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        PDFSecurePanel pdfSecurePanel = new PDFSecurePanel();
        pdfSecurePanel.getjpAPI().setVisible(m_ShowAPI);
        this.setContentPane(pdfSecurePanel);
        this.setSize((int)(730 * SampleUtil.getDPIScalingMultiplier()), (int)(830 * SampleUtil.getDPIScalingMultiplier()));
        this.setLocationRelativeTo(null);
        this.setTitle("jPDFSecure Sample - " + PDFSecure.getVersion ());

        try
		{
    		// Try to set multiple images, java 1.6 only
			Method setIconImages = this.getClass().getMethod("setIconImages", new Class[] {List.class});
			Vector imageList = new Vector();
			imageList.add(ImageIO.read(getClass().getResourceAsStream("jPDFSecure16.png")));
			imageList.add(ImageIO.read(getClass().getResourceAsStream("jPDFSecure32.png")));
			setIconImages.invoke(this, new Object[] {imageList});
		}
		catch (Throwable t1)
		{
			try
	        {
	            this.setIconImage(ImageIO.read(getClass().getResourceAsStream("jPDFSecure16.png")));
	        }
	        catch(Throwable t2)
	        {
	            // ignore
	        }
		}
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
