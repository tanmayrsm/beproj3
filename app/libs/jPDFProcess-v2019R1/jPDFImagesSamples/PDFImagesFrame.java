package jPDFImagesSamples;

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

import com.qoppa.pdf.FontSettings;
import com.qoppa.pdfImages.PDFImages;

/**
 * @author Qoppa Software
 *
 */
public class PDFImagesFrame extends JFrame
{
    private static boolean m_ShowAPI = true;

    public static void  main (String [] args)
    {
		try
		{
			// Create a log file in the user's home directory
			File outFile = new File(System.getProperty("user.home") + File.separator + "jpdfimages.log");
			PrintStream out = new PrintStream(new FileOutputStream(outFile));
			System.setOut(out);
			System.setErr(out);
			
			// Initial log information
			SampleUtil.libraryLogStart(PDFImages.class);			
		}
		catch (Throwable t)
		{
			// Do nothing, continue without log file
		}
		
        // look and feel
        PDFImagesSample.setLookAndFeel();
        
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
		        // Create frame and show it
		        PDFImagesFrame frame = new PDFImagesFrame();
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

    public PDFImagesFrame() 
    {
        // Set title and size
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        setTitle("jPDFImages Sample - " + PDFImages.getVersion ());
        
        setSize((int)(800 * SampleUtil.getDPIScalingMultiplier()), (int)(620 * SampleUtil.getDPIScalingMultiplier()));
        setLocationRelativeTo(null);

        // Set the content pane
        PDFImagesSample samplePanel = new PDFImagesSample();
        samplePanel.getJlSampleCode().setVisible(false);
        samplePanel.getJPAPI().setVisible (m_ShowAPI);
        setContentPane(samplePanel);
        
        try
		{
    		// Try to set multiple images, java 1.6 only
			Method setIconImages = this.getClass().getMethod("setIconImages", new Class[] {List.class});
			Vector imageList = new Vector();
			imageList.add(ImageIO.read(getClass().getResourceAsStream("jPDFImages16.gif")));
			imageList.add(ImageIO.read(getClass().getResourceAsStream("jPDFImages32.png")));
			setIconImages.invoke(this, new Object[] {imageList});
		}
		catch (Throwable t1)
		{
			try
	        {
	            this.setIconImage(ImageIO.read(getClass().getResourceAsStream("jPDFImages16.gif")));
	        }
	        catch(Throwable t2)
	        {
	            // ignore
	        }
		}
    }
}