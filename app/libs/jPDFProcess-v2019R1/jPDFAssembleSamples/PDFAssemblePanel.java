/*
 * Created on Aug 26, 2009
 *
 */
package jPDFAssembleSamples;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTMLDocument;

import com.qoppa.pdf.Bookmark;
import com.qoppa.pdf.IPassword;
import com.qoppa.pdf.PDFException;
import com.qoppa.pdf.PasswordDialog;
import com.qoppa.pdfAssemble.PDFAssemble;
/**
 * @author Qoppa Software
 *
 */
public class PDFAssemblePanel extends JPanel implements IPassword
{
	private JScrollPane jScrollPane = null;
	private JEditorPane jepRTFPane = null;
	private JPanel jpAPI = null;
	private JLabel jlJARLocation = null;
	private JLabel jLabel1 = null;
	private JButton jbViewAPI = null;

	private final static String OS_WINDOWS_START = "windows";
	private final static String OS_MAC = "mac";
	private final static String JAR_FILE_NAME = "jPDFAssemble.jar";
    private final static String SAMPLES_DIR_NAME = "jPDFAssembleSamples";
	private final static String API_INDEX_FILENAME = "javadoc/index.html";
    
	private JButton jbMerge = null;
	private JButton jbInterleave = null;
	private JButton jbCreateBookmarks = null;
    private JButton jbSplit = null;
	
	private JPanel jpSample = null;
	
    private JLabel jlSampleCode = null;
    private JLabel jlSampleLocation = null;
    
    private JPanel jpTitle = null;
    private JLabel jlTitle = null;

    /**
	 * This method initializes 
	 * 
	 */
	public PDFAssemblePanel() {
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
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setSize((int)(900 * SampleUtil.getDPIScalingMultiplier()),(int)(592 * SampleUtil.getDPIScalingMultiplier()));
        
        // Title panel
        this.add(getjpTitle(), null);
        this.add(Box.createRigidArea(new Dimension(5,5)));

        this.add(getJScrollPane(), null);
        this.add(Box.createRigidArea(new Dimension(5,5)));
        this.add(getJPAPI(), null);
        this.add(Box.createRigidArea(new Dimension(5,5)));
        this.add(getJlSampleCode(), null);
        this.add(Box.createRigidArea(new Dimension(5,5)));
        this.add(getJPSample(), null);
        InputStream stream = getClass().getResourceAsStream("/jPDFAssemble.html");
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

        // Initialize help messages
        File jarFile = new File ("lib/" + JAR_FILE_NAME);
        getjlJARLocation().setText (JAR_FILE_NAME + " is located at " + jarFile.getAbsolutePath () + ".");
        getjlJARLocation().setToolTipText(jarFile.getAbsolutePath ());

        File samplesDir = new File (SAMPLES_DIR_NAME);
        getjlSampleLocation().setText ("You can find sample code at " + samplesDir.getAbsolutePath());
        getjlSampleLocation().setToolTipText(samplesDir.getAbsolutePath());
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane() 
    {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new java.awt.Rectangle((int)(9 * SampleUtil.getDPIScalingMultiplier()),(int)(11 * SampleUtil.getDPIScalingMultiplier()),(int)(700 * SampleUtil.getDPIScalingMultiplier()),(int)(230 * SampleUtil.getDPIScalingMultiplier())));
            jScrollPane.setPreferredSize(new java.awt.Dimension((int)(700 * SampleUtil.getDPIScalingMultiplier()),(int)(225 * SampleUtil.getDPIScalingMultiplier())));
			jScrollPane.setViewportView(getJepRTFPane());
		}
		return jScrollPane;
	}
	/**
	 * This method initializes jepRTFPane	
	 * 	
	 * @return javax.swing.JEditorPane	
	 */    
	private JEditorPane getJepRTFPane() {
		if (jepRTFPane == null) 
		{
			jepRTFPane = new JEditorPane();
			jepRTFPane.setMargin(new java.awt.Insets(10,10,10,10));
			jepRTFPane.setEditable(false);
		}
		return jepRTFPane;
	}
/*	public static void main(java.lang.String[] args) 
	{
		PDFProcessPanel aSample = new PDFProcessPanel();
		aSample.show();
	}*/
	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	public JPanel getJPAPI() {
		if (jpAPI == null) {
			jpAPI = new JPanel();
			jLabel1 = new JLabel("to view the API.");
			jpAPI.setLayout(null);
			jpAPI.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jpAPI.setBounds(new java.awt.Rectangle((int)(12 * SampleUtil.getDPIScalingMultiplier()),(int)(416 * SampleUtil.getDPIScalingMultiplier()),(int)(700 * SampleUtil.getDPIScalingMultiplier()),(int)(64 * SampleUtil.getDPIScalingMultiplier())));
            jpAPI.setPreferredSize(new java.awt.Dimension((int)(700 * SampleUtil.getDPIScalingMultiplier()),(int)(80 * SampleUtil.getDPIScalingMultiplier())));
			jLabel1.setBounds(new java.awt.Rectangle((int)(109 * SampleUtil.getDPIScalingMultiplier()),(int)(54 * SampleUtil.getDPIScalingMultiplier()),(int)(133 * SampleUtil.getDPIScalingMultiplier()),(int)(22 * SampleUtil.getDPIScalingMultiplier())));
            jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jLabel1.getFont().getSize(), 14)));
			jpAPI.add(getjlJARLocation(), null);
            jpAPI.add(getjlSampleLocation(), null);
			jpAPI.add(jLabel1, null);
			jpAPI.add(getJbViewAPI(), null);
		}
		return jpAPI;
	}
	
	private JLabel getjlJARLocation ()
	{
	    if (jlJARLocation == null)
	    {
	        jlJARLocation = new JLabel("JLabel");
			jlJARLocation.setBounds(new java.awt.Rectangle((int)(15 * SampleUtil.getDPIScalingMultiplier()),(int)(7 * SampleUtil.getDPIScalingMultiplier()),(int)(650 * SampleUtil.getDPIScalingMultiplier()),(int)(21 * SampleUtil.getDPIScalingMultiplier())));
            jlJARLocation.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jlJARLocation.getFont().getSize(), 14)));
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
			jbViewAPI.setBounds(new java.awt.Rectangle((int)(14 * SampleUtil.getDPIScalingMultiplier()),(int)(53 * SampleUtil.getDPIScalingMultiplier()),(int)(85 * SampleUtil.getDPIScalingMultiplier()),(int)(25 * SampleUtil.getDPIScalingMultiplier())));
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
	/**
	 * This method initializes jbMerge	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJbMerge() {
		if (jbMerge == null) {
			jbMerge = new JButton("Merge");
			jbMerge.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jbMerge.getFont().getSize(), 12)));
			jbMerge.setBounds(new java.awt.Rectangle((int)(17 * SampleUtil.getDPIScalingMultiplier()),(int)(25 * SampleUtil.getDPIScalingMultiplier()),(int)(85 * SampleUtil.getDPIScalingMultiplier()),(int)(25 * SampleUtil.getDPIScalingMultiplier())));
            jbMerge.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) 
				{
				    merge ();
				}
			});
		}
		return jbMerge;
	}
	/**
	 * This method initializes jbInterleave	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJbInterleave() {
		if (jbInterleave == null) {
			jbInterleave = new JButton("Interleave");
			jbInterleave.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jbInterleave.getFont().getSize(), 12)));
			jbInterleave.setMargin(new java.awt.Insets(2,2,2,2));
			jbInterleave.setBounds(new java.awt.Rectangle((int)(17 * SampleUtil.getDPIScalingMultiplier()),(int)(55 * SampleUtil.getDPIScalingMultiplier()),(int)(85 * SampleUtil.getDPIScalingMultiplier()),(int)(25 * SampleUtil.getDPIScalingMultiplier())));
            jbInterleave.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) 
				{
				    interleave ();
				}
			});
		}
		return jbInterleave;
	}
    
    public JLabel getJlSampleCode()
    {
        if(jlSampleCode == null)
        {
            jlSampleCode = new JLabel("Sample code for all the fuctions below is included in our Demo Version available for download.");
            jlSampleCode.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, (int)SampleUtil.getScaledFont(jlSampleCode.getFont().getSize(), 12)));
        }
        return jlSampleCode;
    }
    
    private JLabel getjlSampleLocation()
    {
        if (jlSampleLocation == null)
        {
            jlSampleLocation = new JLabel("JLabel");
            jlSampleLocation.setBounds(new java.awt.Rectangle((int)(15 * SampleUtil.getDPIScalingMultiplier()),(int)(30 * SampleUtil.getDPIScalingMultiplier()),(int)(645 * SampleUtil.getDPIScalingMultiplier()),(int)(21 * SampleUtil.getDPIScalingMultiplier())));
            jlSampleLocation.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jlSampleLocation.getFont().getSize(), 14)));
        }
        return jlSampleLocation;
    }
    
	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPSample() {
		if (jpSample == null) {
			jpSample = new JPanel();
			jpSample.setLayout(null);
			jpSample.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED), "Sample Processing", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jLabel1.getFont().getSize(), 12)), java.awt.Color.black));
			jpSample.setBounds(new java.awt.Rectangle(9,173,700,215));
			jpSample.setBounds(new java.awt.Rectangle((int)(9 * SampleUtil.getDPIScalingMultiplier()),(int)(173 * SampleUtil.getDPIScalingMultiplier()),(int)(700 * SampleUtil.getDPIScalingMultiplier()),(int)(215 * SampleUtil.getDPIScalingMultiplier())));
            jpSample.setPreferredSize(new java.awt.Dimension((int)(700 * SampleUtil.getDPIScalingMultiplier()),(int)(230 * SampleUtil.getDPIScalingMultiplier())));
            
            JLabel jLabel = new JLabel("Merge two PDF files into a single file.  See code in PDFAssemblePanel.merge.");
			jLabel.setBounds(new java.awt.Rectangle((int)(115 * SampleUtil.getDPIScalingMultiplier()),(int)(29 * SampleUtil.getDPIScalingMultiplier()),(int)(592 * SampleUtil.getDPIScalingMultiplier()),(int)(16 * SampleUtil.getDPIScalingMultiplier())));
            jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jLabel.getFont().getSize(), 12)));
			
			JLabel jLabel2 = new JLabel("Interleave the pages in two PDF files. See code in PDFAssemblePanel.interleave.");
			jLabel2.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jLabel2.getFont().getSize(), 12)));
			jLabel2.setBounds(new java.awt.Rectangle((int)(115 * SampleUtil.getDPIScalingMultiplier()),(int)(59 * SampleUtil.getDPIScalingMultiplier()),(int)(592 * SampleUtil.getDPIScalingMultiplier()),(int)(16 * SampleUtil.getDPIScalingMultiplier())));
			
			JLabel jLabel3 = new JLabel("Create Bookmarks in a PDF document.  See code PDFAssemblePanel.createBookmarks.");
			jLabel3.setBounds(new java.awt.Rectangle((int)(115 * SampleUtil.getDPIScalingMultiplier()),(int)(89 * SampleUtil.getDPIScalingMultiplier()),(int)(592 * SampleUtil.getDPIScalingMultiplier()),(int)(16 * SampleUtil.getDPIScalingMultiplier())));
			jLabel3.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jLabel3.getFont().getSize(), 12)));
            
			JLabel jLabel4 = new JLabel("Split a PDF in 2.  See code PDFAssemblePanel.splitPDF.");
            jLabel4.setBounds(new java.awt.Rectangle((int)(115 * SampleUtil.getDPIScalingMultiplier()),(int)(119 * SampleUtil.getDPIScalingMultiplier()),(int)(592 * SampleUtil.getDPIScalingMultiplier()),(int)(16 * SampleUtil.getDPIScalingMultiplier())));
            jLabel4.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jLabel4.getFont().getSize(), 12)));
            

			jpSample.add(jLabel, null);
			jpSample.add(getJbMerge(), null);
			jpSample.add(jLabel2, null);
			jpSample.add(getJbInterleave(), null);
			jpSample.add(jLabel3, null);
			jpSample.add(getJbCreateBookmarks(), null);
            jpSample.add(jLabel4, null);
            jpSample.add(getJbSplitPDF(), null);
		}
		return jpSample;
	}
	
    protected  void interleave ()
	{
        // Get the file names
        MergeDialogController mdc = new MergeDialogController();
        int rc = mdc.showDialog ((Frame)SwingUtilities.windowForComponent(this), "Interleave Documents");
        if (rc == MergeDialogController.RC_OK)
        {
            // Interleave the two files
            try
            {
                // Load the two documents
                PDFAssemble doc1 = new PDFAssemble(mdc.m_Document1.getAbsolutePath(), this);
                PDFAssemble doc2 = new PDFAssemble(mdc.m_Document2.getAbsolutePath(), this);
                
                // Create blank output document
                PDFAssemble outDocument = new PDFAssemble();
                
                // Interleave pages from document 1 and 2 into the output document
                int maxPages = Math.max (doc1.getPageCount(), doc2.getPageCount());
                for (int count = 0; count < maxPages; ++count)
                {
                    if (count < doc1.getPageCount())
                    {
                        outDocument.appendPage(doc1, count);
                    }
                    
                    if (count < doc2.getPageCount())
                    {
                        outDocument.appendPage (doc2, count);
                    }
                }
                
                // Save the output document
                outDocument.saveDocument(mdc.m_OutputDocument.getAbsolutePath());
                
                // show user message
                JOptionPane.showMessageDialog(this, "Done!");
            }
            catch (PDFException pdfE)
            {
                JOptionPane.showMessageDialog (this, "Error " + pdfE.getMessage());
            }
            catch (IOException ioE)
            {
                JOptionPane.showMessageDialog (this, "Error " + ioE.getMessage());
            }
            catch (Throwable t)
            {
                JOptionPane.showMessageDialog (this, "Error " + t.getMessage());
            }
        }
	}
    
    protected void createBookmarks()
	{
    	InputDialogController idc = new InputDialogController();
        int rc = idc.showDialog((Frame)SwingUtilities.windowForComponent(this), "Choose a PDF document");
        if (rc == InputDialogController.RC_OK)
        {
            try
            {
                // Load the document
                PDFAssemble pdfDoc = new PDFAssemble (idc.m_InputDocument.getAbsolutePath(), this);
		            
		                
		    	if(pdfDoc.getPageCount() > 0)
				{
		    		Bookmark rootBK = pdfDoc.getRootBookmark();
		    		
		    		// create a root bookmark if it's null
		    		if(rootBK == null)
					{
						rootBK = pdfDoc.createRootBookmark();
					}
		    		
		    		// add a bookmark for each page
		    		for (int i = 1; i <= pdfDoc.getPageCount(); i++)
					{ 
                        Bookmark bk = rootBK.insertChildBookmark("Page" + " " + i, i - 1);
						pdfDoc.addGoToPage(bk, i);
					}
                    
                    // save document
                    pdfDoc.saveDocument(idc.m_OutputDocument.getAbsolutePath());
                    
                    // show user message
                    JOptionPane.showMessageDialog(this, "Done!");
				}
                else
                {
                    // show user message
                    JOptionPane.showMessageDialog(this, "Empty document!");
                }
            }
            catch(PDFException pe)
            {
            	JOptionPane.showMessageDialog(this, "Error " + pe.getMessage());
            }
            catch(IOException ioe)
            {
                JOptionPane.showMessageDialog(this, "Error " + ioe.getMessage());
            }
            catch (Throwable t)
            {
                JOptionPane.showMessageDialog (this, "Error " + t.getMessage());
            }
        }
	}

    protected void merge()
	{
        // Get the file names
        MergeDialogController mdc = new MergeDialogController();
        int rc = mdc.showDialog ((Frame)SwingUtilities.windowForComponent(this), "Merge Documents");
        if (rc == MergeDialogController.RC_OK)
        {
            // Merge the two files
            try
            {
                // Load the two documents
                PDFAssemble doc1 = new PDFAssemble(mdc.m_Document1.getAbsolutePath(), this);
                PDFAssemble doc2 = new PDFAssemble(mdc.m_Document2.getAbsolutePath(), this);
                
                // Append the second document to the first document
                doc1.appendDocument(doc2);
                
                // Save the merged document
                doc1.saveDocument(mdc.m_OutputDocument.getAbsolutePath());
                
                // show user message
                JOptionPane.showMessageDialog(this, "Done!");
            }
            catch (PDFException pdfE)
            {
                JOptionPane.showMessageDialog (this, "Error " + pdfE.getMessage());
            }
            catch (IOException ioE)
            {
                JOptionPane.showMessageDialog (this, "Error " + ioE.getMessage());
            }
            catch (Throwable t)
            {
                JOptionPane.showMessageDialog (this, "Error " + t.getMessage());
            }
        }
	}
	
	public String [] getPasswords()
    {
        // Create and show the password dialog
        Frame parentFrame = (Frame)SwingUtilities.windowForComponent(this);
        return PasswordDialog.showAndGetPassword(null, parentFrame);
    }
	/**
	 * This method initializes jbPrint	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJbCreateBookmarks() {
		if (jbCreateBookmarks == null) {
			jbCreateBookmarks = new JButton("Bookmarks");
			jbCreateBookmarks.setMargin(new Insets(0,0,0,0));
			jbCreateBookmarks.setBounds(17, 85, 85, 25);
			jbCreateBookmarks.setBounds(new java.awt.Rectangle((int)(17 * SampleUtil.getDPIScalingMultiplier()),(int)(85 * SampleUtil.getDPIScalingMultiplier()),(int)(85 * SampleUtil.getDPIScalingMultiplier()),(int)(25 * SampleUtil.getDPIScalingMultiplier())));
			jbCreateBookmarks.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jbCreateBookmarks.getFont().getSize(), 12)));
			jbCreateBookmarks.addActionListener(new java.awt.event.ActionListener() 
			        { 
				public void actionPerformed(java.awt.event.ActionEvent e)
				{    
				    createBookmarks ();
				}
			});
		}
		return jbCreateBookmarks;
	}
    
    /**
     * This method initializes jbPrint  
     *  
     * @return javax.swing.JButton  
     */    
    private JButton getJbSplitPDF() {
        if (jbSplit == null) {
            jbSplit = new JButton("Split");
            jbSplit.setMargin(new Insets(0,0,0,0));
            jbSplit.setBounds(17, 115, 85, 25);
            jbSplit.setBounds(new java.awt.Rectangle((int)(17 * SampleUtil.getDPIScalingMultiplier()),(int)(115 * SampleUtil.getDPIScalingMultiplier()),(int)(85 * SampleUtil.getDPIScalingMultiplier()),(int)(25 * SampleUtil.getDPIScalingMultiplier())));
			jbSplit.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jbSplit.getFont().getSize(), 12)));
            jbSplit.addActionListener(new java.awt.event.ActionListener() 
                    { 
                public void actionPerformed(java.awt.event.ActionEvent e)
                {    
                    splitPDF();
                }
            });
        }
        return jbSplit;
    }
    
    protected void splitPDF ()
    {
        File inputFile = SampleUtil.getFile(this, SampleUtil.OPEN_FILE, new PDFFileFilter());
        if (inputFile != null)
        {
            
            try
            {
                // Load the document
                PDFAssemble pdfDoc = new PDFAssemble (inputFile.getAbsolutePath(), this);
                    
                        
                if(pdfDoc.getPageCount() >= 2)
                {
                    
                    // create 2 new PDF documents
                    PDFAssemble split1 = new PDFAssemble();
                    PDFAssemble split2 = new PDFAssemble();
                    
                    int halfIndex = (pdfDoc.getPageCount() + 1)/2;
                    
                    // add first pages to split1
                    for (int i = 0; i < halfIndex; i++)
                    { 
                        split1.appendPage(pdfDoc, i);
                    }
                    
                    // add last pages to split2
                    for (int i = halfIndex; i < pdfDoc.getPageCount(); i++)
                    { 
                        split2.appendPage(pdfDoc, i);
                    }
                    
                    // save document2
                    split1.saveDocument (inputFile.getParent() + File.separator + "split1.pdf");
                    split2.saveDocument (inputFile.getParent() + File.separator + "split2.pdf"); 
                    
                    // show user message
                    JOptionPane.showMessageDialog(this, "Saved split files to " + inputFile.getParent() + "\n as split1.pdf, split2.pdf");
                }
                else
                {
                    // show user message
                    JOptionPane.showMessageDialog(this, "Document has less than 2 pages. Can't split.");
                }
            }
            catch(PDFException pe)
            {
                JOptionPane.showMessageDialog(this, "Error " + pe.getMessage());
            }
            catch(IOException ioe)
            {
                JOptionPane.showMessageDialog(this, "Error " + ioe.getMessage());
            }
            catch (Throwable t)
            {
                JOptionPane.showMessageDialog (this, "Error " + t.getMessage());
            }
        }
    }
    public JPanel getjpTitle()
    {
        if (jpTitle == null)
        {
            jpTitle = new JPanel();
            jpTitle.add (getjlTitle());//, BorderLayout.CENTER);
            jpTitle.setBorder(new EtchedBorder());
        }
        return jpTitle;
    }
    
    public JLabel getjlTitle()
    {
        if (jlTitle == null)
        {
            jlTitle = new JLabel(PDFAssemble.getVersion());
            jlTitle.setFont(new Font ("dialog", Font.BOLD, (int)SampleUtil.getScaledFont(jlTitle.getFont().getSize(), 14)));
        }
        return jlTitle;
    }
}
