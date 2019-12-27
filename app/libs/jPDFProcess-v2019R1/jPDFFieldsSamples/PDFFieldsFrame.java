package jPDFFieldsSamples;

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
import com.qoppa.pdfFields.PDFFields;

public class PDFFieldsFrame extends JFrame 
{
    private static boolean m_ShowAPI = true;

    /**
     * Main entry method.
     * 
     * @param args
     */
	public static void main(java.lang.String[] args) 
	{
		try
		{
			// Create a log file in the user's home directory
			File outFile = new File(System.getProperty("user.home") + File.separator + "jpdffields.log");
			PrintStream out = new PrintStream(new FileOutputStream(outFile));
			System.setOut(out);
			System.setErr(out);
			
			// Initial log information
			SampleUtil.libraryLogStart(PDFFields.class);			
		}
		catch (Throwable t)
		{
			// Do nothing, continue without log file
		}
		
        // look and feel
        PDFFieldsSample.setLookAndFeel();
        
		// Use a substitute font when the specified font cannot be found, or
		// there is an error with the embedded font, otherwise an exception will
		// be thrown when the document is parsed.
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
		        PDFFieldsFrame aFrame = new PDFFieldsFrame();
		        aFrame.setVisible(true);
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
     * PDFFieldsFrame constructor.
     *
     */
    public PDFFieldsFrame() 
	{
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        PDFFieldsSample samplePane = new PDFFieldsSample();
        samplePane.getjpAPI().setVisible(m_ShowAPI);
        setContentPane(samplePane);
        setSize((int)(650 * SampleUtil.getDPIScalingMultiplier()), (int)(650 * SampleUtil.getDPIScalingMultiplier()));
        setLocationRelativeTo(null);
        setTitle("jPDFFields Sample - " + PDFFields.getVersion ());
        
        try
		{
    		// Try to set multiple images, java 1.6 only
			Method setIconImages = this.getClass().getMethod("setIconImages", new Class[] {List.class});
			Vector imageList = new Vector();
			imageList.add(ImageIO.read(getClass().getResourceAsStream("jPDFFields16.png")));
			imageList.add(ImageIO.read(getClass().getResourceAsStream("jPDFFields32.png")));
			setIconImages.invoke(this, new Object[] {imageList});
		}
		catch (Throwable t1)
		{
			try
	        {
	            this.setIconImage(ImageIO.read(getClass().getResourceAsStream("jPDFFields16.png")));
	        }
	        catch(Throwable t2)
	        {
	            // ignore
	        }
		}
   }
}  
