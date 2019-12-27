/*
 * Created on May 31, 2005
 *
 */
package jPDFPrintSamples;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.qoppa.pdf.FontSettings;
import com.qoppa.pdfPrint.PDFPrint;

/**
 * @author Gerald Holmann
 *
 */
public class PDFPrintFrame extends JFrame
{
    private static boolean m_ShowAPI = true;
    
    public static void main (String [] args)
    {
		try
		{
			// Create a log file in the user's home directory
			File outFile = new File(System.getProperty("user.home") + File.separator + "jpdfprint.log");
			PrintStream out = new PrintStream(new FileOutputStream(outFile));
			System.setOut(out);
			System.setErr(out);
			
			// Initial log information
			SampleUtil.libraryLogStart(PDFPrint.class);			
		}
		catch (Throwable t)
		{
			// Do nothing, continue without log file
		}
		
        // look and feel
        PDFPrintSample.setLookAndFeel();
        
		// When rendering, use a substitute font when the specified font cannot
		// be found, or the is an error with the embedded font, otherwise the
		// document will not be rendered and an exception will be thrown.
		FontSettings.setUseSubstituteFont(true);
        
        // Check flag to show api
        if (args != null && args.length > 0 && "-hideapi".equalsIgnoreCase(args [0]))
        {
            m_ShowAPI = false;
        }

        SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				// Create frame and show it
		        PDFPrintFrame sample = new PDFPrintFrame();
		        sample.setVisible(true);
			}
		});
    }
    
	/**
	 * This method initializes 
	 * 
	 */
	public PDFPrintFrame() 
    {
        // Set title and size
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        setTitle("jPDFPrint Sample - " + PDFPrint.getVersion ());
        setSize((int)(650 * SampleUtil.getDPIScalingMultiplier()), (int)(600 * SampleUtil.getDPIScalingMultiplier()));
        setLocationRelativeTo(null);

        // Set the content pane
        PDFPrintSample contentPane = new PDFPrintSample();
        contentPane.getjpAPI().setVisible (m_ShowAPI);
        setContentPane(contentPane);
        
        try
		{
    		// Try to set multiple images, java 1.6 only
			Method setIconImages = this.getClass().getMethod("setIconImages", new Class[] {List.class});
			Vector imageList = new Vector();
			imageList.add(ImageIO.read(getClass().getResourceAsStream("jPDFPrint16.png")));
			imageList.add(ImageIO.read(getClass().getResourceAsStream("jPDFPrint32.png")));
			setIconImages.invoke(this, new Object[] {imageList});
		}
		catch (Throwable t1)
		{
			try
	        {
	            this.setIconImage(ImageIO.read(getClass().getResourceAsStream("jPDFPrint16.png")));
	        }
	        catch(Throwable t2)
	        {
	            // ignore
	        }
		}
    }
}
