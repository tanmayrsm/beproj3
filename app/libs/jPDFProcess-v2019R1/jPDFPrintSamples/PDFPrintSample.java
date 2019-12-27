/*
 * Created on Apr 26, 2007
 *
 */
package jPDFPrintSamples;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTMLDocument;

import com.qoppa.pdf.IPassword;
import com.qoppa.pdf.PDFException;
import com.qoppa.pdf.PasswordDialog;
import com.qoppa.pdf.PrintSettings;
import com.qoppa.pdfPrint.PDFPrint;

public class PDFPrintSample extends JPanel implements IPassword
{
    private JScrollPane jspRTFDocument = null;
    private JEditorPane jepRTFPane = null;
    private JPanel jpAPI = null;
    private JPanel jpButtons = null;
    private JLabel jlJARLocation = null;
    private JButton jbViewAPI = null;
    private JLabel jlSampleLocation = null;
    private JButton jbPrint = null;
    private JButton jbPrintSilent = null;
    private JButton jbPrintBatch = null;
    private JButton jbPrintURL = null;
    
    private final static String OS_WINDOWS_START = "windows";
    private final static String OS_MAC = "mac";
    private final static String JAR_FILE_PATH = "lib/jPDFPrint.jar";
    private final static String SAMPLE_DIR_NAME = "jPDFPrintSamples";
    private final static String API_INDEX_FILENAME = "javadoc/index.html";

    private File m_LastFileDir;
    private JPanel jpTitle = null;
    private JLabel jlTitle = null;

    public PDFPrintSample()
    {
        initialize();
    }
    
    private void initialize()
    {
        // Layout
        setLayout(new BoxLayout (this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.add(getjpTitle(), null);
        this.add(Box.createRigidArea(new Dimension(5,5)));
        add(getjspRTFDocument(), null);
        this.add(Box.createRigidArea(new Dimension(5,5)));
        add(getjpAPI(), null);
        this.add(Box.createRigidArea(new Dimension(5,5)));
        add(getjpButtons(), null);
        
        // Initialize help message
        File jarFile = new File (JAR_FILE_PATH);
        if (jarFile.exists())
        {
            getjlJARLocation().setText (JAR_FILE_PATH + " is located at " + jarFile.getAbsolutePath () + ".");
            getjlJARLocation().setToolTipText(jarFile.getAbsolutePath ());
        }
            
        File sampleDir = new File (SAMPLE_DIR_NAME);
        if (sampleDir.exists())
        {
            getjlSampleLocation().setText("Sample code is located at " + sampleDir.getAbsolutePath() + ".");
            getjlSampleLocation().setToolTipText(sampleDir.getAbsolutePath());
        }

        InputStream stream = getClass().getResourceAsStream("/jPDFPrint.html");
		if (stream != null)
		{
			try
			{
				getJepRTFPane().setContentType("text/html");

				EditorKit kit = getJepRTFPane().getEditorKit();
				Document doc = kit.createDefaultDocument();
				kit.read(stream, doc, 0);
				stream.close();
				getJepRTFPane().setDocument(doc);
				
				// Standard formatting is horrible, so use some stylesheet properties and system font
				Font font = new java.awt.Font("Dialog", java.awt.Font.PLAIN, new JLabel().getFont().getSize());
		        String rule = "body { font-family: " + font.getFamily() + "; " + "font-size: " + font.getSize() + "pt;}";
		        ((HTMLDocument)getJepRTFPane().getDocument()).getStyleSheet().addRule(rule);
		        rule = "h3 { font-family: " + font.getFamily() + "; " + "font-size: " + (int)(SampleUtil.getScaledFont(font.getSize(), 20)) + "pt;}";
				((HTMLDocument)getJepRTFPane().getDocument()).getStyleSheet().addRule(rule);
		        rule = "p { margin:0; padding:0;";
		        ((HTMLDocument)getJepRTFPane().getDocument()).getStyleSheet().addRule(rule);
			}
	        catch (Throwable t)
	        {
	            t.printStackTrace();
	        }
		}
    }

    public JPanel getjpTitle()
    {
        if (jpTitle == null)
        {
            jpTitle = new JPanel();
            //jpTitle.setLayout (new BorderLayout (5, 0));
            jpTitle.add(getjlTitle());//, BorderLayout.CENTER);
            jpTitle.setPreferredSize(new Dimension((int)(700 * SampleUtil.getDPIScalingMultiplier()), (int)(30 * SampleUtil.getDPIScalingMultiplier())));
            jpTitle.setBorder(new EtchedBorder());
        }
        return jpTitle;
    }
    
    public JLabel getjlTitle()
    {
        if (jlTitle == null)
        {
            jlTitle = new JLabel();
            jlTitle.setText(PDFPrint.getVersion ());
            jlTitle.setFont(new Font("dialog", Font.BOLD, (int)SampleUtil.getScaledFont(jlTitle.getFont().getSize(), 14)));
        }
        return jlTitle;
    }

    /**
     * This method initializes jScrollPane  
     *  
     * @return javax.swing.JScrollPane  
     */    
    private JScrollPane getjspRTFDocument()
    {
        if (jspRTFDocument == null) {
            jspRTFDocument = new JScrollPane();
            jspRTFDocument.setViewportView(getJepRTFPane());
            jspRTFDocument.setLocation(20, 20);
            jspRTFDocument.setPreferredSize(new Dimension((int)(700 * SampleUtil.getDPIScalingMultiplier()), (int)(220 * SampleUtil.getDPIScalingMultiplier())));
        }
        return jspRTFDocument;
    }
    
    /**
     * This method initializes jepRTFPane   
     *  
     * @return javax.swing.JEditorPane  
     */    
    private JEditorPane getJepRTFPane() {
        if (jepRTFPane == null) {
            jepRTFPane = new JEditorPane();
            jepRTFPane.setEditable(false);
        }
        return jepRTFPane;
    }
    
    /**
     * This method initializes jPanel1  
     *  
     * @return javax.swing.JPanel   
     */    
    public JPanel getjpAPI() {
        if (jpAPI == null) {
            jpAPI = new JPanel();
            JLabel jLabel1 = new JLabel("to view the API.");
            jpAPI.setLayout(null);
            jpAPI.setPreferredSize(new Dimension ((int)(700 * SampleUtil.getDPIScalingMultiplier()), (int)(90 * SampleUtil.getDPIScalingMultiplier())));
            jpAPI.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
            jLabel1.setBounds((int)(100 * SampleUtil.getDPIScalingMultiplier()), (int)(57 * SampleUtil.getDPIScalingMultiplier()), (int)(126 * SampleUtil.getDPIScalingMultiplier()), (int)(16 * SampleUtil.getDPIScalingMultiplier()));
            jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jLabel1.getFont().getSize(), 12)));
            jpAPI.add(getjlJARLocation(), null);
            jpAPI.add(jLabel1, null);
            jpAPI.add(getJbViewAPI(), null);
            jpAPI.add(getjlSampleLocation(), null);
        }
        return jpAPI;
    }
    
    /**
     * This method initializes jPanel2  
     *  
     * @return javax.swing.JPanel   
     */    
    private JPanel getjpButtons() {
        if (jpButtons == null) {
            jpButtons = new JPanel();
            jpButtons.setLayout(null);
            int fontSize = (int)SampleUtil.getScaledFont(new JLabel().getFont().getSize(), 12);
            jpButtons.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED), "Sample Processing", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.PLAIN, fontSize), java.awt.Color.black));
            jpButtons.setPreferredSize(new Dimension((int)(700 * SampleUtil.getDPIScalingMultiplier()), (int)(200 * SampleUtil.getDPIScalingMultiplier())));
            
            // print button + label
            JLabel jLabel3 = new JLabel("Prints a PDF document.");
            jLabel3.setBounds((int)(185 * SampleUtil.getDPIScalingMultiplier()), (int)(30 * SampleUtil.getDPIScalingMultiplier()), (int)(570 * SampleUtil.getDPIScalingMultiplier()), (int)(16 * SampleUtil.getDPIScalingMultiplier()));
            jLabel3.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, fontSize));
            JLabel jLabel31 = new JLabel("Look at PDFPrintSample.print() method for the code.");
            jLabel31.setBounds((int)(185 * SampleUtil.getDPIScalingMultiplier()), (int)(45 * SampleUtil.getDPIScalingMultiplier()), (int)(570 * SampleUtil.getDPIScalingMultiplier()), (int)(16 * SampleUtil.getDPIScalingMultiplier()));
            jLabel31.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, fontSize));
            jpButtons.add(getJbPrint(), null);
            jpButtons.add (jLabel3, null);
            jpButtons.add (jLabel31, null);
            
            // print silent  button + label
            jpButtons.add(getJbPrintSilent(), null);
            JLabel jLabel4 = new JLabel("Prints a PDF document silently to the default printer.");
            jLabel4.setBounds((int)(185 * SampleUtil.getDPIScalingMultiplier()), (int)(70 * SampleUtil.getDPIScalingMultiplier()), (int)(564 * SampleUtil.getDPIScalingMultiplier()), (int)(16 * SampleUtil.getDPIScalingMultiplier()));
            jLabel4.setFont(new Font("Dialog", Font.PLAIN, (int)SampleUtil.getScaledFont(jLabel4.getFont().getSize(), 13)));
            JLabel jLabel41 = new JLabel("Look at PDFPrintSample.printSilent() method for the code.");
            jLabel41.setBounds((int)(185 * SampleUtil.getDPIScalingMultiplier()), (int)(85 * SampleUtil.getDPIScalingMultiplier()), (int)(564 * SampleUtil.getDPIScalingMultiplier()), (int)(16 * SampleUtil.getDPIScalingMultiplier()));
            jLabel41.setFont(new Font("Dialog", Font.PLAIN, (int)SampleUtil.getScaledFont(jLabel41.getFont().getSize(), 13)));
            jpButtons.add (jLabel4, null);
            jpButtons.add (jLabel41, null);
            
            // batch print button + label
            jpButtons.add(getjbPrintBatch(), null);
            JLabel jLabel5 = new JLabel("Prints a batch of PDF documents.");
            jLabel5.setBounds((int)(185 * SampleUtil.getDPIScalingMultiplier()), (int)(110 * SampleUtil.getDPIScalingMultiplier()), (int)(564 * SampleUtil.getDPIScalingMultiplier()), (int)(16 * SampleUtil.getDPIScalingMultiplier()));
            jLabel5.setFont(new Font("Dialog", Font.PLAIN, fontSize));
            JLabel jLabel51 = new JLabel("Look at PDFPrintSample.printBatch() method for the code.");
            jLabel51.setBounds((int)(185 * SampleUtil.getDPIScalingMultiplier()), (int)(125 * SampleUtil.getDPIScalingMultiplier()), (int)(564 * SampleUtil.getDPIScalingMultiplier()), (int)(16 * SampleUtil.getDPIScalingMultiplier()));
            jLabel51.setFont(new Font("Dialog", Font.PLAIN, fontSize));
            jpButtons.add (jLabel5, null);
            jpButtons.add (jLabel51, null);
            
            // url print button + label
            jpButtons.add (getjbPrintURL(), null);
            JLabel jLabel6 = new JLabel("Prints a PDF document from a URL.");
            jLabel6.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, fontSize));
            jLabel6.setBounds((int)(185 * SampleUtil.getDPIScalingMultiplier()), (int)(150 * SampleUtil.getDPIScalingMultiplier()), (int)(570 * SampleUtil.getDPIScalingMultiplier()), (int)(16 * SampleUtil.getDPIScalingMultiplier()));
            JLabel jLabel61 = new JLabel("Look at PDFPrintSample.printURL() method for the code.");
            jLabel61.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, fontSize));
            jLabel61.setBounds((int)(185 * SampleUtil.getDPIScalingMultiplier()), (int)(165 * SampleUtil.getDPIScalingMultiplier()), (int)(570 * SampleUtil.getDPIScalingMultiplier()), (int)(16 * SampleUtil.getDPIScalingMultiplier()));
            jpButtons.add (jLabel6);
            jpButtons.add (jLabel61);
        }
        return jpButtons;
    }
    
    private JLabel getjlJARLocation ()
    {
        if (jlJARLocation == null)
        {
            jlJARLocation = new JLabel("JLabel");
            jlJARLocation.setBounds((int)(15 * SampleUtil.getDPIScalingMultiplier()), (int)(9 * SampleUtil.getDPIScalingMultiplier()), (int)(650 * SampleUtil.getDPIScalingMultiplier()), (int)(16 * SampleUtil.getDPIScalingMultiplier()));
            jlJARLocation.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jlJARLocation.getFont().getSize(), 12)));
        }
        return jlJARLocation;       
    }

    
    /**
     * This method initializes jbViewAPI    
     *  
     * @return javax.swing.JButton  
     */    
    private JButton getJbViewAPI() {
        if (jbViewAPI == null) {
            jbViewAPI = new JButton("Click Here");
            jbViewAPI.setBounds((int)(10 * SampleUtil.getDPIScalingMultiplier()), (int)(55 * SampleUtil.getDPIScalingMultiplier()), (int)(85 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier()));
            jbViewAPI.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jbViewAPI.getFont().getSize(), 12)));
            jbViewAPI.setMargin(new java.awt.Insets(2,2,2,2));
            jbViewAPI.addActionListener(new java.awt.event.ActionListener()
                    { 
                public void actionPerformed(java.awt.event.ActionEvent e) 
                {
                    viewAPI ();
                }
            });
        }
        return jbViewAPI;
    }

    private JLabel getjlSampleLocation()
    {
        if (jlSampleLocation == null)
        {
            jlSampleLocation = new JLabel("JLabel");
            jlSampleLocation.setBounds((int)(15 * SampleUtil.getDPIScalingMultiplier()), (int)(30 * SampleUtil.getDPIScalingMultiplier()), (int)(650 * SampleUtil.getDPIScalingMultiplier()), (int)(16 * SampleUtil.getDPIScalingMultiplier()));
            jlSampleLocation.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jlSampleLocation.getFont().getSize(), 12)));
        }
        return jlSampleLocation;
    }

    /**
     * This method initializes jbPrint  
     *  
     * @return javax.swing.JButton  
     */    
    private JButton getJbPrint() {
        if (jbPrint == null) {
            jbPrint = new JButton("Print");
            jbPrint.setBounds((int)(22 * SampleUtil.getDPIScalingMultiplier()), (int)(35 * SampleUtil.getDPIScalingMultiplier()), (int)(145 * SampleUtil.getDPIScalingMultiplier()), (int)(25 * SampleUtil.getDPIScalingMultiplier()));
            jbPrint.setMargin(new java.awt.Insets(0,0,0,0));
            jbPrint.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jbPrint.getFont().getSize(), 12)));
            jbPrint.addActionListener(new java.awt.event.ActionListener() 
                    { 
                public void actionPerformed(java.awt.event.ActionEvent e)
                {    
                    print ();
                }
            });
        }
        return jbPrint;
    }
    
    /**
     * This method initializes jbPrintPrintDefault
     *  
     * @return javax.swing.JButton  
     */    
    private JButton getJbPrintSilent() {
        if (jbPrintSilent == null) {
            jbPrintSilent = new JButton("Silent Print");
            jbPrintSilent.setBounds((int)(22 * SampleUtil.getDPIScalingMultiplier()), (int)(75 * SampleUtil.getDPIScalingMultiplier()), (int)(145 * SampleUtil.getDPIScalingMultiplier()), (int)(25 * SampleUtil.getDPIScalingMultiplier()));
            jbPrintSilent.setMargin(new java.awt.Insets(0,0,0,0));
            jbPrintSilent.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jbPrintSilent.getFont().getSize(), 12)));
            jbPrintSilent.addActionListener(new java.awt.event.ActionListener() 
                    { 
                public void actionPerformed(java.awt.event.ActionEvent e)
                {    
                    printSilent ();
                }
            });
        }
        return jbPrintSilent;
    }

    /**
     * This method initializes jButton  
     *  
     * @return javax.swing.JButton  
     */
    private JButton getjbPrintBatch()
    {
        if (jbPrintBatch == null)
        {
            jbPrintBatch = new JButton("Print Batch");
            jbPrintBatch.setBounds((int)(22 * SampleUtil.getDPIScalingMultiplier()), (int)(115 * SampleUtil.getDPIScalingMultiplier()), (int)(145 * SampleUtil.getDPIScalingMultiplier()), (int)(25 * SampleUtil.getDPIScalingMultiplier()));
            jbPrintBatch.setMargin(new java.awt.Insets(0,0,0,0));
            jbPrintBatch.setFont(new Font("Dialog", Font.PLAIN, (int)SampleUtil.getScaledFont(jbPrintBatch.getFont().getSize(), 12)));
            jbPrintBatch.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    printBatch();
                }
            });
        }
        return jbPrintBatch;
    }
    
    /**
     * This method initializes jbPrintURL  
     *  
     * @return javax.swing.JButton  
     */
    private JButton getjbPrintURL()
    {
        if (jbPrintURL == null)
        {
            jbPrintURL = new JButton("Print From URL");
            jbPrintURL.setBounds((int)(22 * SampleUtil.getDPIScalingMultiplier()), (int)(155 * SampleUtil.getDPIScalingMultiplier()), (int)(145 * SampleUtil.getDPIScalingMultiplier()), (int)(25 * SampleUtil.getDPIScalingMultiplier()));
            jbPrintURL.setMargin(new java.awt.Insets(0,0,0,0));
            jbPrintURL.setFont(new Font("Dialog", Font.PLAIN, (int)SampleUtil.getScaledFont(jbPrintURL.getFont().getSize(), 12)));
            jbPrintURL.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    printURL();
                }
            });
        }
        return jbPrintURL;
    }
   

    private void printURL ()
    {
        // get a URL from the user
        String pdfURL = JOptionPane.showInputDialog(this, "Enter URL for PDF", "http://");
        if (pdfURL == null || pdfURL.trim().length() == 0)
        {
            return;
        }
        
        // Create URL object
        try
        {
            URL url = new URL (pdfURL.trim());
            
            PDFPrint pdfPrint = new PDFPrint (url, this);
            pdfPrint.print (new PrintSettings());
        }
        catch (PDFException pdfE)
        {
            JOptionPane.showMessageDialog(this, pdfE.getMessage());
        }
        catch (PrinterException pe)
        {
            JOptionPane.showMessageDialog(this, pe);
        }
        catch (MalformedURLException mue)
        {
            JOptionPane.showMessageDialog(this, "Invalid URL: " + pdfURL.trim());
        }
    }

    /**
     * Prompts the user for a file to print, shows the print dialog to select the printer
     * and then prints the chosen file to the selected printer.  
     **/
    private void print ()
    {
        // Get the file to print
        File [] file1 = getFileList (false);
        if (file1 == null || file1.length == 0)
        {
            return;
        }
        
        try
        {
            // Print document
            PDFPrint.print (file1[0].getAbsolutePath(), new PrintSettings(true, true, false, true), this);
        }
        catch (PDFException pdfE)
        {
            JOptionPane.showMessageDialog (this, pdfE.getMessage());
        }
        catch (PrinterException pe)
        {
            JOptionPane.showMessageDialog (this, pe.getMessage());
        }
    }

    /**
     * Prompts the user for a file to print and then prints the chosen file silently to the default printer.  
     **/
    private void printSilent()
    {
        // Get the file to print
        File [] file1 = getFileList (false);
        if (file1 == null || file1.length == 0)
        {
            return;
        }
        
        try
        {
            // Print document to the default printer by passing null as printer name
            PDFPrint.print (file1[0].getAbsolutePath(), null, new PrintSettings(true, true, false, true), this);
        }
        catch (PDFException pdfE)
        {
            JOptionPane.showMessageDialog (this, pdfE.getMessage());
        }
        catch (PrinterException pe)
        {
            JOptionPane.showMessageDialog (this, pe.getMessage());
        }
    }
    
    private void printBatch()
    {
        // Get the file list
        File [] fileList = getFileList(true);
        if (fileList == null || fileList.length == 0)
        {
            return;
        }
        
        try
        {
            PrinterJob pJob = PrinterJob.getPrinterJob();
            if (pJob.printDialog())
            {
                for (int count = 0; count < fileList.length; ++count)
                {
                    PDFPrint pdfPrint = new PDFPrint (fileList[count].getAbsolutePath(), this);
                    pdfPrint.setPrintSettings(new PrintSettings(true, true, false, true));
                    pJob.setPrintable (pdfPrint);
                    pJob.print();
                }
            }
        }
        catch (PDFException pdfE)
        {
            JOptionPane.showMessageDialog (this, pdfE.getMessage());
        }
        catch (PrinterException pe)
        {
            JOptionPane.showMessageDialog (this, pe.getMessage());
        }
    }
    
    private File [] getFileList (boolean multiSelection)
    {
        // Get first file name
        JFileChooser fileChooser = new JFileChooser ();

        // Initialize directory
        if (m_LastFileDir != null)
        {
            fileChooser.setCurrentDirectory(m_LastFileDir);
        }
        
        // Look for PDF files
        fileChooser.setFileFilter(new PDFFileFilter());
        fileChooser.setMultiSelectionEnabled(multiSelection);

        // Show the dialog
        // Show the open dialog
        if (fileChooser.showOpenDialog(this) == JFileChooser.CANCEL_OPTION)
        {
            return null;
        }
        
        // Save last directory location
        File [] chosenFiles = null;
        if (multiSelection)
        {
            chosenFiles = fileChooser.getSelectedFiles();
        }
        else
        {
            File oneFile = fileChooser.getSelectedFile();
            if (oneFile != null)
            {
                chosenFiles = new File [1];
                chosenFiles [0] = oneFile;
            }
        }

        // Remember the last dir
        if (chosenFiles != null && chosenFiles.length > 0)
        {
            m_LastFileDir = chosenFiles [0].getParentFile();
        }
        return chosenFiles;
    }

    /**
     * Set the look and feel.
     */
    public static void setLookAndFeel ()
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
    private void viewAPI ()
    {
        try
        {
            File apiIndex = new File (API_INDEX_FILENAME);
            if (isSystemWindows())
            {
                Runtime.getRuntime().exec ("rundll32 url.dll,FileProtocolHandler " + apiIndex.getAbsolutePath ());
            }
            else if (isSystemMac())
            {
                String [] cmdArray = new String [2];
                cmdArray [0] = "open";
                cmdArray [1] = apiIndex.getAbsolutePath ();
                Runtime.getRuntime ().exec (cmdArray);
            }
            // assume Unix or Linux
            else 
            { 
                String[] browsers = {"firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape", "google-chrome" };
                String browser = null;
                for (int count = 0; count < browsers.length && browser == null; count++)
                {
                   if (Runtime.getRuntime().exec(new String[] {"which", browsers[count]}).waitFor() == 0)
                   {
                      browser = browsers[count];
                      break;
                   }
                }
                if (browser == null)
                   throw new Exception("Could not find web browser");
                else
                   Runtime.getRuntime().exec(new String[] {browser, apiIndex.getAbsolutePath ()});
            }
        }
        catch (Throwable t)
        {
            javax.swing.JOptionPane.showMessageDialog (this, t.getMessage ());
        }
    }
    private boolean isSystemMac() 
    {
        // Check the OS
        String osName = System.getProperty ("os.name");
        osName = osName.toLowerCase();
        int firstIndexOfMac = osName.indexOf (OS_MAC);
        if (firstIndexOfMac == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    /**
     * Insert the method's description here.
     * Creation date: (11/10/2003 9:16:17 PM)
     * @return boolean
     */
    private boolean isSystemWindows()
    {
        // Check the OS
        String osName = System.getProperty ("os.name");
        osName = osName.toLowerCase();
        return osName.startsWith(OS_WINDOWS_START);
    }
    
    public String [] getPasswords()
    {
        // Create and show the password dialog
        Frame parentFrame = (Frame)SwingUtilities.windowForComponent(this);
        return PasswordDialog.showAndGetPassword(null, parentFrame);
    }
}
