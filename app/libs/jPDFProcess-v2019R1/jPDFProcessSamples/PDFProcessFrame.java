package jPDFProcessSamples;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.qoppa.pdf.FontSettings;
import com.qoppa.pdfProcess.PDFDocument;

/**
 * @author Qoppa Software
 *
 */
public class PDFProcessFrame extends JFrame
{
    private static boolean m_ShowAPI = true;

	/**
	 * This method initializes 
	 * 
	 */
	public PDFProcessFrame() 
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
        setTitle("jPDFProcess Sample - " + PDFDocument.getVersion());
        
        PDFProcessPanel pdfProcessPanel = new PDFProcessPanel();
        // hide label about sample code (only used in the applet)
        pdfProcessPanel.getJlSampleCode().setVisible(false);
        pdfProcessPanel.getJPAPI().setVisible (m_ShowAPI);

        this.setContentPane(pdfProcessPanel);
        this.setSize((int)(800 * SampleUtil.getDPIScalingMultiplier()),(int)(700 * SampleUtil.getDPIScalingMultiplier()));
        this.setLocationRelativeTo(null);

        try
		{
    		// Try to set multiple images, java 1.6 only
			Method setIconImages = this.getClass().getMethod("setIconImages", new Class[] {List.class});
			Vector imageList = new Vector();
			imageList.add(ImageIO.read(getClass().getResourceAsStream("jPDFProcess16.png")));
			imageList.add(ImageIO.read(getClass().getResourceAsStream("jPDFProcess32.png")));
			setIconImages.invoke(this, new Object[] {imageList});
		}
		catch (Throwable t1)
		{
			try
	        {
	            this.setIconImage(ImageIO.read(getClass().getResourceAsStream("jPDFProcess16.png")));
	        }
	        catch(Throwable t2)
	        {
	            // ignore
	        }
		}
	}
	
	
	public static void main(String [] args)
	{
		try
		{
			// Create a log file in the user's home directory
			File outFile = new File(System.getProperty("user.home") + File.separator + "jpdfprocess.log");
			PrintStream out = new PrintStream(new FileOutputStream(outFile));
			System.setOut(out);
			System.setErr(out);
			
			// Initial log information
			SampleUtil.libraryLogStart(PDFDocument.class);
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
		
		// Initialize the user font directory for font embedding
		initUserFontDirectories();
        
        // Check flag to show api
        if (args != null && args.length > 0 && "-hideapi".equalsIgnoreCase(args [0]))
        {
            m_ShowAPI = false;
        }

        SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
		        // Create and show the frame
		        PDFProcessFrame frame = new PDFProcessFrame ();
			    frame.setVisible(true);
			}
        });
	}
	
	private static void initUserFontDirectories()
	{
		// Set the user font directory for embedding
		File fontDir = new File("fonts");
		if (fontDir.exists())
		{
			List<String> userFontDirs = new ArrayList<String>();
			userFontDirs.add(fontDir.getAbsolutePath());
			FontSettings.setUserFontDirectories(userFontDirs);
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