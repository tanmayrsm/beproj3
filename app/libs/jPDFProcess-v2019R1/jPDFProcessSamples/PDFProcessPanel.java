/*
 * Created on Dec 8, 2004
 *
 */
package jPDFProcessSamples;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.geom.Rectangle2D;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;

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

import com.qoppa.pdf.IPassword;
import com.qoppa.pdf.PDFException;
import com.qoppa.pdf.PasswordDialog;
import com.qoppa.pdf.PrintSettings;
import com.qoppa.pdf.SigningInformation;
import com.qoppa.pdf.form.SignatureField;
import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.pdfProcess.PDFGraphics;
import com.qoppa.pdfProcess.PDFPage;
/**
 * @author Gerald Holmann
 *
 */
public class PDFProcessPanel extends JPanel implements IPassword
{
	private JScrollPane jScrollPane = null;
	private JEditorPane jepRTFPane = null;
	private JPanel jpAPI = null;
	private JLabel jlJARLocation = null;
	private JLabel jLabel1 = null;
	private JButton jbViewAPI = null;

	private final static String OS_WINDOWS_START = "windows";
	private final static String OS_MAC = "mac";
	private final static String JAR_FILE_NAME = "jPDFProcess.jar";
    private final static String SAMPLES_DIR_NAME = "jPDFProcessSamples";
	private final static String API_INDEX_FILENAME = "javadoc/index.html";
    
	private JButton jbMerge = null;
	private JButton jbInterleave = null;
	private JPanel jpSample = null;
	
	private JButton jbPrint = null;
	private JButton jbStamp = null;
    private JButton jbExportJPEG = null;
    private JButton jbExportFDF = null;
    private JLabel jlSampleCode = null;
    private JLabel jlSampleLocation = null;
    private JButton jbFlattenFields = null;
    
    private JPanel jpTitle = null;
    private JLabel jlTitle = null;

	private JButton jbSign = null;
    /**
	 * This method initializes 
	 * 
	 */
	public PDFProcessPanel() {
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
        this.setSize(new java.awt.Dimension((int)(900 * SampleUtil.getDPIScalingMultiplier()),(int)(600 * SampleUtil.getDPIScalingMultiplier())));
        this.add(getjpTitle(), null);
        this.add(Box.createRigidArea(new Dimension(5,5)));
        this.add(getJScrollPane(), null);
        this.add(Box.createRigidArea(new Dimension(5,5)));
        this.add(getJPAPI(), null);
        this.add(Box.createRigidArea(new Dimension(5,5)));
        this.add(getJlSampleCode(), null);
        this.add(Box.createRigidArea(new Dimension(5,5)));
        this.add(getJPSample(), null);
        
        InputStream stream = getClass().getResourceAsStream("/jPDFProcess.html");
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
		if (jepRTFPane == null) {
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
			jpAPI.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
            
			jpAPI.setLayout(new GridBagLayout());			
			GridBagConstraints c = new GridBagConstraints();
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.weightx = 1;
			c.insets = new Insets(0, 10, 5, 0);
			
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 2;
			jpAPI.add(getjlJARLocation(), c);
			
			c.gridx = 0;
			c.gridy = 1;
			c.gridwidth = 2;
            jpAPI.add(getjlSampleLocation(), c);
            
			c.weightx = 0;
			c.gridx = 0;
			c.gridy = 2;
			c.gridwidth = 1;
			jpAPI.add(getJbViewAPI(), c);

			c.weightx = 1;
            c.gridx = 1;
            c.gridy = 2;
            c.gridwidth = 1;
			jLabel1 = new JLabel("to view the API.");
			jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jLabel1.getFont().getSize(), 14)));
			jpAPI.add(jLabel1, c);
		}
		return jpAPI;
	}
	
	private JLabel getjlJARLocation ()
	{
	    if (jlJARLocation == null)
	    {
	        jlJARLocation = new JLabel("JLabel");
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
			jbViewAPI.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jbViewAPI.getFont().getSize(), 12)));
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
            jlSampleCode = new JLabel("Sample code for all the functions below is included in our Demo Version available for download.");
            jlSampleCode.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, (int)SampleUtil.getScaledFont(jlSampleCode.getFont().getSize(), 12)));
        }
        return jlSampleCode;
    }
    
    private JLabel getjlSampleLocation()
    {
        if (jlSampleLocation == null)
        {
            jlSampleLocation = new JLabel("JLabel");
            jlSampleLocation.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jlSampleLocation.getFont().getSize(), 14)));
        }
        return jlSampleLocation;
    }
    
	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPSample()
	{
		if (jpSample == null) {
			jpSample = new JPanel();
			jpSample.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.insets = new Insets(0, 10, 10, 0);

			int fontSize = (int)SampleUtil.getScaledFont(new JLabel().getFont().getSize(), 12);
			jpSample.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED), "Sample Processing", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.PLAIN, fontSize), java.awt.Color.black));

			JLabel jLabel = new JLabel("Merge two PDF files into a single file.  See PDFProcessPanel.merge method for the code.");
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, fontSize));

			JLabel jLabel2 = new JLabel("Interleave the pages in two PDF files. See PDFProcessPanel.interleave method for the code.");
			jLabel2.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, fontSize));

			JLabel jLabel3 = new JLabel("Prints a PDF document.  See PDFProcessPanel.print method for the code.");
			jLabel3.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, fontSize));

			JLabel jLabel4 = new JLabel("Stamps a PDF document with headers, footers, etc.  See PDFProcessPanel.stamp method for the code.");
			jLabel4.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, fontSize));

			JLabel jLabel5 = new JLabel("Exports a PDF document as a set of JPEG files. See PDFProcessPanel.exportJPGs method for the code.");
			jLabel5.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, fontSize));
			
			JLabel jLabel6 = new JLabel("Export Data from PDF Interactive Form to FDF. See PDFProcessPanel.exportFDF method for the code.");
			jLabel6.setFont(new Font("Dialog", Font.PLAIN, fontSize));
			
            JLabel jLabel7 = new JLabel("Flatten Form Fields. See PDFProcessPanel.flattenFields method for the code.");
            jLabel7.setFont(new Font("Dialog", Font.PLAIN, fontSize));
            
            JLabel jLabel8 = new JLabel("Sign a PDF document. See PDFProcessPanel.sign method for the code.");
            jLabel8.setFont(new Font("Dialog", Font.PLAIN, fontSize));
            
            c.fill = GridBagConstraints.BOTH;
            c.weightx = 0;
			c.gridx = 0;
			c.gridy = 0;
			jpSample.add(getJbMerge(), c);
			
			c.weightx = 1;
			c.gridx = 1;
			c.gridy = 0;
			jpSample.add(jLabel, c);
			
			c.weightx = 0;
			c.gridx = 0;
			c.gridy = 1;
			jpSample.add(getJbInterleave(), c);

			c.weightx = 1;
			c.gridx = 1;
			c.gridy = 1;
			jpSample.add(jLabel2, c);
			
			c.weightx = 0;
			c.gridx = 0;
			c.gridy = 2;
			jpSample.add(getJbPrint(), c);
			
			c.weightx = 1;
			c.gridx = 1;
			c.gridy = 2;
			jpSample.add(jLabel3, c);
			
			c.weightx = 0;
			c.gridx = 0;
			c.gridy = 3;
			jpSample.add(getJbStamp(), c);
			
			c.weightx = 1;
			c.gridx = 1;
			c.gridy = 3;
			jpSample.add(jLabel4, c);
			
			c.weightx = 0;
			c.gridx = 0;
			c.gridy = 4;
			jpSample.add(getJbExportJPEG(), c);
			
			c.weightx = 1;
			c.gridx = 1;
			c.gridy = 4;
			jpSample.add(jLabel5, c);
			
			c.weightx = 0;
			c.gridx = 0;
			c.gridy = 5;
			jpSample.add(getJbExportFDF(), c);
			
			c.weightx = 1;
			c.gridx = 1;
			c.gridy = 5;
			jpSample.add(jLabel6, c);
			
			c.weightx = 0;
			c.gridx = 0;
			c.gridy = 6;
            jpSample.add(getJbFlattenFields(), c);
            
            c.weightx = 1;
			c.gridx = 1;
			c.gridy = 6;
            jpSample.add(jLabel7, c);
            
            c.weightx = 0;
			c.gridx = 0;
			c.gridy = 7;
			jpSample.add(getJbSign(), c);
			
			c.weightx = 1;
			c.gridx = 1;
			c.gridy = 7;
			jpSample.add(jLabel8, c);
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
                PDFDocument doc1 = new PDFDocument(mdc.m_Document1.getAbsolutePath(), this);
                PDFDocument doc2 = new PDFDocument(mdc.m_Document2.getAbsolutePath(), this);
                
                // Create blank output document
                PDFDocument outDocument = new PDFDocument();
                
                // Interleave pages from document 1 and 2 into the output document
                int maxPages = Math.max (doc1.getPageCount(), doc2.getPageCount());
                for (int count = 0; count < maxPages; ++count)
                {
                    if (count < doc1.getPageCount())
                    {
                        outDocument.appendPage(doc1.getPage(count));
                    }
                    
                    if (count < doc2.getPageCount())
                    {
                        outDocument.appendPage (doc2.getPage (count));
                    }
                }
                
                // Save the output document
                outDocument.saveDocument(mdc.m_OutputDocument.getAbsolutePath());
            }
            catch (PDFException pdfE)
            {
                JOptionPane.showMessageDialog (this, pdfE.getMessage());
            }
            catch (IOException ioE)
            {
                JOptionPane.showMessageDialog (this, ioE.getMessage());
            }
        }
	}
	
	
    protected void merge ()
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
                PDFDocument doc1 = new PDFDocument(mdc.m_Document1.getAbsolutePath(), this);
                PDFDocument doc2 = new PDFDocument(mdc.m_Document2.getAbsolutePath(), this);
                
                // Append the second document to the first document
                doc1.appendDocument(doc2);
                
                // Save the merged document
                doc1.saveDocument(mdc.m_OutputDocument.getAbsolutePath());
            }
            catch (PDFException pdfE)
            {
                JOptionPane.showMessageDialog (this, pdfE.getMessage());
            }
            catch (IOException ioE)
            {
                JOptionPane.showMessageDialog (this, ioE.getMessage());
            }
            catch (Throwable t)
            {
                JOptionPane.showMessageDialog (this, "Error " + t.getMessage());
            }
        }
	}
	
	protected void stamp ()
	{
        InputDialogController idc = new InputDialogController ();
        int rc = idc.showDialog((Frame)SwingUtilities.windowForComponent(this), "Stamp A Document");
        if (rc == InputDialogController.RC_OK)
        {
            try
            {
                // Derive fonts from standard PDF fonts
                Font header = PDFGraphics.HELVETICA.deriveFont (Font.BOLD, 14);
                Font confFont = PDFGraphics.HELVETICA.deriveFont (Font.BOLD, 30);
                
                // Load the document, stamp it and save it
                PDFDocument doc1 = new PDFDocument(idc.m_InputDocument.getAbsolutePath(), this);
                for (int count = 0; count < doc1.getPageCount(); ++count)
                {
                    PDFPage page = doc1.getPage(count);
                    Graphics2D g2d = page.createGraphics();
                    g2d.setFont (header);
                    g2d.setColor (Color.blue);
                    g2d.drawString ("You can stamp documents with a header.", 72, 72);
                    g2d.drawString ("You can stamp documents with a footer - Page " + (count+1), 72, (float)page.getDisplayHeight() - 72);
                    
                    g2d.setFont (confFont);
                    g2d.setColor (Color.gray);
                    for (int posY = 144; posY < page.getDisplayHeight() - 72; posY += 72)
                    {
                        g2d.drawString ("CONFIDENTIAL", 72, posY);
                    }
                }
                doc1.saveDocument(idc.m_OutputDocument.getAbsolutePath());
            }
            catch (PDFException pdfE)
            {
                JOptionPane.showMessageDialog (this, pdfE.getMessage());
            }
            catch (IOException ioE)
            {
                JOptionPane.showMessageDialog (this, ioE.getMessage());
            }
        }
	}
	
	 protected  void sign ()
		{
		    // Get the file to sign
		    File file = SampleUtil.getFile (this, SampleUtil.OPEN_FILE, new PDFFileFilter());
		    if (file == null)
		    {
		        return;
		    }
		    
		    try
		    {   // Load the document
			    PDFDocument doc = new PDFDocument (file.getAbsolutePath(), this);
			    
			    // Load the keystore that contains the digital id to use in signing
                InputStream pkcs12Stream = this.getClass().getResourceAsStream ("/keystore.pfx");
	            KeyStore store = KeyStore.getInstance("PKCS12");
	            store.load(pkcs12Stream, "store_pwd".toCharArray());
	            pkcs12Stream.close();
	            
	            // Create signing information
	            SigningInformation signInfo = new SigningInformation (store, "key_alias", "store_pwd");
	            signInfo.setReason("Approve");
	            signInfo.setLocation("Atlanta, GA 30307");
	
	            // Create signature field on the first page
	            Rectangle2D signBounds = new Rectangle2D.Double (36, 36, 144, 48);
	            SignatureField signField = doc.getPage(0).addSignatureField("signature", signBounds);
	            
	            // Apply digital signature
	            doc.signDocument(signField, signInfo);
	            
	            // Save the document
	            file = SampleUtil.getFile (this, SampleUtil.SAVE_FILE, new PDFFileFilter());
	            if (file != null)
			    {
	            	doc.saveDocument(file.getAbsolutePath());
			    }
			    
		    }
		    catch (Throwable t)
		    {
		    	t.printStackTrace();
		        JOptionPane.showMessageDialog (this, t.getMessage());
		    }
		}
    
    protected  void print ()
	{
	    // Get the file to print
	    File file1 = SampleUtil.getFile (this, SampleUtil.OPEN_FILE, new PDFFileFilter());
	    if (file1 == null)
	    {
	        return;
	    }
	    
	    try
	    {
		    // Load the document and print it
		    PDFDocument doc1 = new PDFDocument (file1.getAbsolutePath(), this);
		    doc1.print(new PrintSettings(true, true, false, true));
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
	private JButton getJbPrint() {
		if (jbPrint == null) {
			jbPrint = new JButton("Print");
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
	 * This method initializes jbStamp	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJbStamp() {
		if (jbStamp == null) {
			jbStamp = new JButton("Stamp");
			jbStamp.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jbStamp.getFont().getSize(), 12)));
			jbStamp.addActionListener(new java.awt.event.ActionListener() 
			        { 
			    public void actionPerformed(java.awt.event.ActionEvent e) 
			    {    
			        stamp();
				}
			});
		}
		return jbStamp;
	}
	
	/**
	 * This method initializes jbStamp	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJbSign() {
		if (jbSign == null) {
			jbSign = new JButton("Sign");
			jbSign.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jbSign.getFont().getSize(), 12)));
			jbSign.addActionListener(new java.awt.event.ActionListener() 
			        { 
			    public void actionPerformed(java.awt.event.ActionEvent e) 
			    {    
			        sign();
				}
			});
		}
		return jbSign;
	}
    /**
     * This method initializes jbExport	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getJbExportJPEG()
    {
        if (jbExportJPEG == null)
        {
            jbExportJPEG = new JButton("Export JPGs");
            jbExportJPEG.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jbExportJPEG.getFont().getSize(), 12)));
            jbExportJPEG.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    exportJPGs ();
                }
            });
        }
        return jbExportJPEG;
    }
    
    protected void exportFDF()
    {
        InputDialogController sdc = new InputDialogController ();
        sdc.m_OutputFileFilter = new FDFFileFilter();
        int rc = sdc.showDialog((Frame)SwingUtilities.windowForComponent(this), "Export AcroForm Fields");
        if (rc == InputDialogController.RC_OK)
        {
            try
            {
                // Load the document
                PDFDocument doc1 = new PDFDocument (sdc.m_InputDocument.getAbsolutePath(), this);
                
                // export document to fdf
                if(doc1.getAcroForm() != null)
                {
                    // Export the FDF data 
                    doc1.getAcroForm().exportAsFDF(sdc.m_OutputDocument.getAbsolutePath(), true);
                    JOptionPane.showMessageDialog(this, "AcroForm fields were exported to: " + sdc.m_OutputDocument.getName());
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "No AcroForm data to export for this document");
                }
            }
            catch (PDFException pdfE)
            {
                JOptionPane.showMessageDialog (this, pdfE.getMessage());
            }
            catch (IOException ioE)
            {
                JOptionPane.showMessageDialog (this, ioE.getMessage());
            }
        }
    }
    
    protected void exportJPGs ()
    {
        // Get the file to export 
        File file1 = SampleUtil.getFile (this, SampleUtil.OPEN_FILE, new PDFFileFilter());
        if (file1 == null)
        {
            return;
        }
        
        try
        {
            // Make sure there is an exports folder
            File exportsDir = SampleUtil.chooseDir(file1.getParent(), this, true);
            if (exportsDir == null)
            {
                return;
            }

            // Load the document
            PDFDocument doc1 = new PDFDocument (file1.getAbsolutePath(), this);
            
            // get document pages
            for (int count = 0; count < doc1.getPageCount(); ++count)
            {
                // Get current page
                PDFPage page = doc1.getPage(count);
                
                // Save the buffered image as a JPEG
                File outFile = new File (exportsDir, "page" + count + ".jpg");
                FileOutputStream outStream = new FileOutputStream (outFile);
                page.savePageAsJPEG(outStream, 72, 0.80f);
                outStream.close();
            }
            
            // Show message
            JOptionPane.showMessageDialog(this, "Files were exported to:\n" + exportsDir.getAbsolutePath());
        }
        catch (PDFException pdfE)
        {
            JOptionPane.showMessageDialog (this, pdfE.getMessage());
        }
        catch (IOException ioE)
        {
            JOptionPane.showMessageDialog (this, ioE.getMessage());
        }
    }
    
    
    public void flattenFields()
    {
        // get input and output file names
        InputDialogController idc = new InputDialogController ();
        int rc = idc.showDialog((Frame)SwingUtilities.windowForComponent(this), "Stamp A Document");
        if (rc == InputDialogController.RC_OK)
        {
            try
            {
                // Load the document
                PDFDocument doc1 = new PDFDocument (idc.m_InputDocument.getAbsolutePath(), this);
                
                // flatten all fields, don't paint push buttons, don't paint non printable fields
                doc1.flattenFields(false, false);
                doc1.saveDocument(idc.m_OutputDocument.getAbsolutePath());
                
                // Show message
                JOptionPane.showMessageDialog(this, "Done");
            }
            catch (PDFException pdfE)
            {
                JOptionPane.showMessageDialog (this, pdfE.getMessage());
            } 
            catch (IOException ioE)
            {
                JOptionPane.showMessageDialog (this, ioE.getMessage());
            } 
        }
    }
    /**
     * This method initializes jButton	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getJbExportFDF()
    {
        if (jbExportFDF == null)
        {
            jbExportFDF = new JButton("Export FDF");
            jbExportFDF.setFont(new Font("Dialog", Font.PLAIN, (int)SampleUtil.getScaledFont(jbExportFDF.getFont().getSize(), 12)));
            jbExportFDF.addActionListener(new java.awt.event.ActionListener() 
                    { 
                public void actionPerformed(java.awt.event.ActionEvent e)
                {    
                    exportFDF ();
                }
            });
        }
        return jbExportFDF;
    }
    /**
     * This method initializes jbFlattenFields 
     *  
     * @return javax.swing.JButton  
     */
    private JButton getJbFlattenFields()
    {
        if (jbFlattenFields == null)
        {
            jbFlattenFields = new JButton("Flatten Fields");
            jbFlattenFields.setFont(new Font("Dialog", Font.PLAIN, (int)SampleUtil.getScaledFont(jbFlattenFields.getFont().getSize(), 12)));
            jbFlattenFields.addActionListener(new java.awt.event.ActionListener() 
                    { 
                public void actionPerformed(java.awt.event.ActionEvent e)
                {    
                    flattenFields ();
                }
            });
        }
        return jbFlattenFields;
    }
    
    public JPanel getjpTitle()
    {
        if (jpTitle == null)
        {
            jpTitle = new JPanel();
            jpTitle.add(getjlTitle());
            jpTitle.setPreferredSize(new Dimension((int)(700 * SampleUtil.getDPIScalingMultiplier()),(int)(30 * SampleUtil.getDPIScalingMultiplier())));
            jpTitle.setBorder(new EtchedBorder());
        }
        return jpTitle;
    }
    
    public JLabel getjlTitle()
    {
        if (jlTitle == null)
        {
            jlTitle = new JLabel(PDFDocument.getVersion());
            jlTitle.setFont(new Font("dialog", Font.BOLD, (int)SampleUtil.getScaledFont(jlTitle.getFont().getSize(), 14)));
        }
        return jlTitle;
    }
  }  //  @jve:decl-index=0:visual-constraint="10,10"
