package jPDFTextSamples;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.qoppa.pdf.IPassword;
import com.qoppa.pdf.PDFException;
import com.qoppa.pdfText.PDFText;
import com.qoppa.pdfViewer.PDFViewerBean;

/**
 * @author Gerald Holmann
 *
 */
public class PDFTextPanel extends JPanel implements IPassword
{
	private File m_LastFile = null;
	private URL m_CurrentURL;
	
	private PDFViewerBean PDFViewer = null;
	private JPanel jpAPI = null;
	private JLabel jlJARLocation = null;
	private JButton jbViewAPI = null;

	private final static String OS_WINDOWS_START = "windows";
	private final static String OS_MAC = "mac";
	private final static String JAR_FILE_NAME = "jPDFText.jar";  //  @jve:decl-index=0:
	private final static String SAMPLES_DIR_NAME = "jPDFTextSamples";
	private final static String API_INDEX_FILENAME = "javadoc/index.html";
    public final static String WELCOME_PDF = "/jpdftext.pdf";
	
	private JLabel jlSampleLocation = null;
	private JButton jbExtractText = null;
	private JButton jbOpen = null;
	private JScrollPane jScrollPane = null;
	private JTextArea jTextArea = null;
	private JLabel jlFileName = null;

	private JPanel jpButtons = null;

	private JPanel jpTitle = null;  //  @jve:decl-index=0:visual-constraint="632,192"
	
	/**
	 * This method initializes 
	 * 
	 */
	public PDFTextPanel() 
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
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setSize(new java.awt.Dimension((int)(585 * SampleUtil.getDPIScalingMultiplier()),(int)(795 * SampleUtil.getDPIScalingMultiplier())));
		this.setPreferredSize(new java.awt.Dimension((int)(585 * SampleUtil.getDPIScalingMultiplier()),(int)(780 * SampleUtil.getDPIScalingMultiplier())));
		this.add(getJpTitle(), null);
		this.add(getPDFViewer(), null);
		this.add(getJpButtons(), null);
		this.add(getJScrollPane(), null);
		this.add(getJpAPI(), null);
        this.setBorder(new EmptyBorder(0,10,0,10));
		
		File jarFile = new File ("lib/" + JAR_FILE_NAME);
		getjlJARLocation().setText (JAR_FILE_NAME + " is located at " + jarFile.getAbsolutePath () + ".");
		getjlJARLocation().setToolTipText(jarFile.getAbsolutePath ());
		File samplesDir = new File (SAMPLES_DIR_NAME);
		getjlSampleLocation().setText ("You can find sample code at " + samplesDir.getAbsolutePath());
		getjlSampleLocation().setToolTipText(samplesDir.getAbsolutePath());
	}
	
	/**
	 * This method initializes jlFileName	
	 * 	
	 * @return javax.swing.JLabel
	 */    
	protected JLabel getjlFileName() 
	{
		if (jlFileName == null) {
			jlFileName = new JLabel("No File Selected");
			jlFileName.setBounds(new java.awt.Rectangle((int)(44 * SampleUtil.getDPIScalingMultiplier()),(int)(6 * SampleUtil.getDPIScalingMultiplier()),(int)(321 * SampleUtil.getDPIScalingMultiplier()),(int)(26 * SampleUtil.getDPIScalingMultiplier())));
			jlFileName.setPreferredSize(new java.awt.Dimension((int)(200 * SampleUtil.getDPIScalingMultiplier()),(int)(16 * SampleUtil.getDPIScalingMultiplier())));
		}
		return jlFileName;
	}
	
	/**
	 * This method initializes PDFViewer	
	 * 	
	 * @return com.qoppa.pdfViewer.PDFViewer	
	 */    
	protected PDFViewerBean getPDFViewer() {
		if (PDFViewer == null) {
			PDFViewer = new PDFViewerBean();
			PDFViewer.getToolbar().getjbOpen().removeActionListener(PDFViewer);
            PDFViewer.getToolbar().getjbOpen().addActionListener(new ActionListener () {
               public void actionPerformed(ActionEvent e)
                {
                   cmdOpenFile();
                } 
            });
            PDFViewer.getToolbar().getjbPrint().setVisible(false);
			PDFViewer.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
			PDFViewer.setSplitOpen(true);
			PDFViewer.setPreferredSize(new Dimension((int)(585 * SampleUtil.getDPIScalingMultiplier()),(int)(500 * SampleUtil.getDPIScalingMultiplier())));
		}
		return PDFViewer;
	}
	
	/**
	 * This method initializes jpAPI	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	public JPanel getJpAPI() {
		if (jpAPI == null) {
			jpAPI = new JPanel();
			JLabel jLabel = new JLabel("to view the API.");
			jpAPI.setLayout(null);
			jpAPI.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jpAPI.setPreferredSize(new java.awt.Dimension((int)(585 * SampleUtil.getDPIScalingMultiplier()),(int)(70 * SampleUtil.getDPIScalingMultiplier())));
			jpAPI.setMinimumSize(new java.awt.Dimension(0,(int)(70 * SampleUtil.getDPIScalingMultiplier())));
			jLabel.setBounds(new java.awt.Rectangle((int)(99 * SampleUtil.getDPIScalingMultiplier()),(int)(46 * SampleUtil.getDPIScalingMultiplier()),(int)(456 * SampleUtil.getDPIScalingMultiplier()),(int)(16 * SampleUtil.getDPIScalingMultiplier())));
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jLabel.getFont().getSize(), 12)));
			jpAPI.add(getjlJARLocation(), null);
			jpAPI.add(getJbViewAPI(), null);
			jpAPI.add(jLabel, null);
			jpAPI.add(getjlSampleLocation(), null);
		}
		return jpAPI;
	}
	
	private JLabel getjlSampleLocation()
	{
	    if (jlSampleLocation == null)
	    {
			jlSampleLocation = new JLabel("JLabel");
			jlSampleLocation.setBounds(new java.awt.Rectangle((int)(9 * SampleUtil.getDPIScalingMultiplier()),(int)(24 * SampleUtil.getDPIScalingMultiplier()),(int)(548 * SampleUtil.getDPIScalingMultiplier()),(int)(16 * SampleUtil.getDPIScalingMultiplier())));
			jlSampleLocation.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jlSampleLocation.getFont().getSize(), 12)));
	    }
	    return jlSampleLocation;
	}
	private JLabel getjlJARLocation ()
	{
	    if (jlJARLocation == null)
	    {
			jlJARLocation = new JLabel("JLabel");
			jlJARLocation.setBounds(new java.awt.Rectangle((int)(9 * SampleUtil.getDPIScalingMultiplier()),(int)(4 * SampleUtil.getDPIScalingMultiplier()),(int)(548 * SampleUtil.getDPIScalingMultiplier()),(int)(16 * SampleUtil.getDPIScalingMultiplier())));
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
			jbViewAPI.setBounds(new java.awt.Rectangle((int)(9 * SampleUtil.getDPIScalingMultiplier()),(int)(44 * SampleUtil.getDPIScalingMultiplier()),(int)(85 * SampleUtil.getDPIScalingMultiplier()),(int)(20 * SampleUtil.getDPIScalingMultiplier())));
			jbViewAPI.setMargin(new java.awt.Insets(2,2,2,2));
			jbViewAPI.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jbViewAPI.getFont().getSize(), 12)));
			jbViewAPI.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) 
				{    
				    viewAPI();
				}
			});
		}
		return jbViewAPI;
	}
	/**
	 * This method initializes jbExtractText	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbExtractWords() {
		if (jbExtractText == null) {
			jbExtractText = new JButton("Extract Text");
			jbExtractText.setMargin(new Insets (2,2,2,2));
			jbExtractText.setBounds(new java.awt.Rectangle((int)(464 * SampleUtil.getDPIScalingMultiplier()),(int)(6 * SampleUtil.getDPIScalingMultiplier()),(int)(98 * SampleUtil.getDPIScalingMultiplier()),(int)(26 * SampleUtil.getDPIScalingMultiplier())));
			jbExtractText.addActionListener(new java.awt.event.ActionListener() 
			{ 
				public void actionPerformed(java.awt.event.ActionEvent e) 
				{    
				    extractText();
				}
			});
		}
		return jbExtractText;
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setPreferredSize(new Dimension((int)(585 * SampleUtil.getDPIScalingMultiplier()),(int)(350 * SampleUtil.getDPIScalingMultiplier())));
			jScrollPane.setViewportView(getJTextPane());
		}
		return jScrollPane;
	}
	/**
	 * This method initializes jTextPane	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextArea getJTextPane()
    {
		if (jTextArea == null) 
        {
            jTextArea = new JTextArea();
            jTextArea.setFont(new Font("Dialog", Font.PLAIN, (int)SampleUtil.getScaledFont(jTextArea.getFont().getSize(), 12)));
            jTextArea.setLineWrap(true);
            jTextArea.setWrapStyleWord(true);
            jTextArea.setMargin (new Insets (10, 10, 10, 10));
            JScrollPane areaScrollPane = new JScrollPane(jTextArea);
            areaScrollPane.setVerticalScrollBarPolicy(
                            ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            areaScrollPane.setPreferredSize(new java.awt.Dimension((int)(585 * SampleUtil.getDPIScalingMultiplier()),(int)(550 * SampleUtil.getDPIScalingMultiplier())));
        }
		return jTextArea;
	}
    
    public String [] getPasswords()
    {
        // Create password dialog
        Frame parentFrame = (Frame)SwingUtilities.windowForComponent(this);
        PwdDialog dialog = new PwdDialog(parentFrame);
        SampleUtil.centerDialog(parentFrame, dialog);

        // Show the dialog
        dialog.setVisible(true);
        
        return new String [] {dialog.getPassword()};
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
   	
   	protected void viewAPI ()
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
   	
   	public void extractText()
   	{
   		try
   		{
   			// create a new PDFText object - this opens the pdf document
	   		PDFText pdfText = new PDFText(m_CurrentURL, this);
	   		
	   		// create a new string buffer
	   		StringBuffer strBuffer = new StringBuffer();
	   		
	   		// loop through all the pages of the pdf documents
	   		for(int pageIx = 0; pageIx < pdfText.getPageCount(); pageIx++)
	   		{
	   			if(pageIx == 0)
	   			{
	   				strBuffer.append("****  PAGE " + (pageIx + 1) + " ****** \n\n");
	   			}
	   			else
	   			{
	   				strBuffer.append("\n\n****  PAGE " + (pageIx + 1) + " ****** \n\n");
	   			}
	   			
	   			// get text for this page
	   			strBuffer.append(pdfText.getText(pageIx));
	   			
	   		}
	   		
	   		// set text in the text pane
   			getJTextPane().setText(strBuffer.toString());
   			
   			// scroll to the top of the text pane
   			getJTextPane().setCaretPosition(0);
   		}
   		catch(Throwable t)
   		{
   			// log and display throwable
            t.printStackTrace();
   			JOptionPane.showMessageDialog (this, t.getMessage ());
   		}
   		
   	}

   	/**
	 * This method shows a JFileChooser with a PDF filter
	 * and returns the chosen file.  If the user hits cancel,
	 * the method returns null.
	 * 	
	 * @param	currentFile	The file chooser will select this file
	 * 			when it comes up.
	 * 
	 * @return	Chosen file.	
	 */    
	protected File getFile()
	{
	    // Create file chooser
		JFileChooser fileChooser = new JFileChooser ();
		
		// Initialize directory
	    fileChooser.setCurrentDirectory(m_LastFile);
	    
		// PDF File filter
		PDFFileFilter pdfFilter = new PDFFileFilter();

		// Set the filter
		fileChooser.setFileFilter(pdfFilter);

		// Show the JFileChooser
		int rc = fileChooser.showOpenDialog(this);
		if (rc == JFileChooser.APPROVE_OPTION)
		{
			File file = fileChooser.getSelectedFile();
			m_LastFile = file;
		    return file;
		}
		else
		{
			return null;
		}
	}
	/**
	 * This method initializes jbOpen	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	public void openPDF(URL url) 
	{
		try
		{
			m_CurrentURL = url;
			getPDFViewer().loadPDF(url);
			getjlFileName().setText(url.toString());
		}
		catch (PDFException pdfE)
		{
			JOptionPane.showMessageDialog(null, pdfE.getMessage());
		}
	}
	
	public void openPDFResource (String resourceName)
	{
        try
        {
    	    m_CurrentURL = getClass().getResource(resourceName);
    	    getPDFViewer().loadPDF (m_CurrentURL);
    	    getjlFileName().setText (resourceName);
        }
        catch (PDFException pdfE)
        {
            JOptionPane.showMessageDialog(null, pdfE.getMessage());
        }
	}
    
	
    public void openPDF (String fileName)
    {
        try
        {
            File pdfFile = new File (fileName);
            m_CurrentURL = pdfFile.toURL();
            getPDFViewer().loadPDF (fileName);
            getjlFileName().setText (fileName);
        }
        catch (MalformedURLException mURLE)
        {
            JOptionPane.showMessageDialog (null, "Error loading sample PDF file.");
        }
        catch (PDFException pdfE)
        {
            JOptionPane.showMessageDialog(null, pdfE.getMessage());
        }
    }
     
	/**
	 * This method initializes jbOpen	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJbOpen() {
		if (jbOpen == null) {
			jbOpen = new JButton("Open PDF");
			jbOpen.setMargin(new Insets (2,2,2,2));
			jbOpen.setBounds(new java.awt.Rectangle((int)(373 * SampleUtil.getDPIScalingMultiplier()),(int)(6 * SampleUtil.getDPIScalingMultiplier()),(int)(89 * SampleUtil.getDPIScalingMultiplier()),(int)(26 * SampleUtil.getDPIScalingMultiplier())));
			jbOpen.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, (int)SampleUtil.getScaledFont(jbOpen.getFont().getSize(), 12)));
			jbOpen.addActionListener(new java.awt.event.ActionListener() 
			     { 
				public void actionPerformed(java.awt.event.ActionEvent e)
				{    
                    cmdOpenFile ();
				}
			});
		}
		return jbOpen;
	}
        
    protected void cmdOpenFile()
    {
        //  let user select a pdf file
        File file = getFile();
        if (file == null)
        {
            return;
        }
        
        // open pdf file
        try
        {
            openPDF(file.getAbsolutePath());
        }
        catch(Throwable t)
        {
            JOptionPane.showMessageDialog(null, t.getMessage());
        }
       
    }


	/**
	 * This method initializes jpButtons	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJpButtons() {
		if (jpButtons == null) {
			JLabel jlFile = new JLabel("File:");
			jlFile.setBounds(new java.awt.Rectangle((int)(6 * SampleUtil.getDPIScalingMultiplier()),(int)(6 * SampleUtil.getDPIScalingMultiplier()),(int)(29 * SampleUtil.getDPIScalingMultiplier()),(int)(26 * SampleUtil.getDPIScalingMultiplier())));
			jpButtons = new JPanel();
			jpButtons.setLayout(null);
			jpButtons.setPreferredSize(new java.awt.Dimension((int)(585 * SampleUtil.getDPIScalingMultiplier()),(int)(40 * SampleUtil.getDPIScalingMultiplier())));
			jpButtons.setMinimumSize(new java.awt.Dimension(0,(int)(40 * SampleUtil.getDPIScalingMultiplier())));
			jpButtons.add(jlFile, null);
			jpButtons.add(getjlFileName(), null);
			jpButtons.add(getJbOpen(), null);
			jpButtons.add(getJbExtractWords(), null);
		}
		return jpButtons;
	}


	/**
	 * This method initializes jpTitle	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJpTitle() {
		if (jpTitle == null) {
			jpTitle = new JPanel();
			jpTitle.setLayout(new BorderLayout());
			jpTitle.setPreferredSize(new java.awt.Dimension((int)(585 * SampleUtil.getDPIScalingMultiplier()),(int)(30 * SampleUtil.getDPIScalingMultiplier())));
			jpTitle.setMinimumSize(new java.awt.Dimension((int)(585 * SampleUtil.getDPIScalingMultiplier()),(int)(30 * SampleUtil.getDPIScalingMultiplier())));
            jpTitle.setMaximumSize(new java.awt.Dimension((int)(585 * SampleUtil.getDPIScalingMultiplier()),(int)(30 * SampleUtil.getDPIScalingMultiplier())));
            JLabel jlTitle = new JLabel("jPDFText Sample - " + PDFText.getVersion());
			jlTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			jlTitle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            jlTitle.setFont(new Font("dialog", Font.BOLD, (int)SampleUtil.getScaledFont(jlTitle.getFont().getSize(), 14)));
			jpTitle.add(jlTitle, java.awt.BorderLayout.CENTER);
		}
		return jpTitle;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"