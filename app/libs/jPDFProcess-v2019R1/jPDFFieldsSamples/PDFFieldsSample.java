/*
 * Created on May 31, 2005
 *
 */
package jPDFFieldsSamples;


import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTMLDocument;

import com.qoppa.pdf.IPassword;
import com.qoppa.pdf.JavaScriptEnabler;
import com.qoppa.pdf.JavaScriptSettings;
import com.qoppa.pdf.PDFException;
import com.qoppa.pdf.PasswordDialog;
import com.qoppa.pdf.form.CheckBoxField;
import com.qoppa.pdf.form.ComboField;
import com.qoppa.pdf.form.FormField;
import com.qoppa.pdf.form.ListField;
import com.qoppa.pdf.form.RadioButtonGroupField;
import com.qoppa.pdf.form.SignatureField;
import com.qoppa.pdf.form.TextField;
import com.qoppa.pdfFields.PDFFields;

/**
 * @author Gerald Holmann
 *
 */
public class PDFFieldsSample extends JPanel implements KeyListener, IPassword, ComponentListener
{
	// all visual components
	private JScrollPane jspRTFDocument = null;
	private JEditorPane jepRTFPane = null;
    private JPanel jpAPI = null;
    private JLabel jlJARLocation = null;
	private JPanel jpSampleProcessing = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private JButton jbOpen = null;
    private JButton jbExportFDF = null;
    private JButton jbImportFDF = null;
    private JButton jbExportXDP = null;
    private JButton jbImportXDP = null;
    private JButton jbExportXFDF = null;
    private JButton jbImportXFDF = null;
	private JLabel jlNumFields = null;
	private JTextField jtfFileName = null;
	private JCheckBox jcbJavascript = null;
	private JTable jtAcroFields = null;
	private JScrollPane jspFieldTable = null;
    private JButton jbSavePDF = null;
    private JLabel jlSampleLocation = null;
    private JLabel jlDemoLimit = new JLabel();
    

    private JButton jbViewAPI = null;
    private JPanel jpTitle = null;
    private JLabel jlTitle = null;
    
    // current open PDF file
    protected File m_File = null;

	// PDFFields object
    private File m_LastFileDir = null;
	private PDFFields m_PDFFields;
    private JButton jbFlattenFields = null;
    private JButton jbResetFields = null;
    
    private final static String JAR_FILE_NAME = "jPDFFields.jar";
    private final static String SAMPLE_DIR_NAME = "jPDFFieldsSamples";
    private final static String API_INDEX_FILENAME = "javadoc/index.html";
    private final static String OS_WINDOWS_START = "windows";
    private final static String OS_MAC = "mac";

	/**
	 * This method initializes 
	 * 
	 */
	public PDFFieldsSample() 
	{
		super();
		initialize();
		m_PDFFields = null;
		init();
	}
	/**
	 * This method initializes this
	 * 
	 */
	private void initialize()
    {
        // Layout
        setLayout(new BoxLayout (this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.addComponentListener(this);

        this.setSize(new java.awt.Dimension((int)(660 * SampleUtil.getDPIScalingMultiplier()),(int)(575 * SampleUtil.getDPIScalingMultiplier())));
        this.add(getjpTitle(), null);
        this.add(Box.createRigidArea(new Dimension(5,5)));
        add(getjspRTFDocument(), null);
        this.add(Box.createRigidArea(new Dimension(5,5)));
        add(getjpAPI(), null);
        this.add(Box.createRigidArea(new Dimension(5,5)));
        add(getjpSampleProcessing(), null);
        
        // Initialize help message
        File jarFile = new File (JAR_FILE_NAME);
        if (jarFile.exists())
        {
            getjlJARLocation().setText (JAR_FILE_NAME + " is located at " + jarFile.getAbsolutePath () + ".");
            getjlJARLocation().setToolTipText(jarFile.getAbsolutePath ());
        }
        else
        {
            getjlJARLocation().setText("");
        }
            
        File sampleDir = new File (SAMPLE_DIR_NAME);
        if (sampleDir.exists())
        {
            getjlSampleLocation().setText("Sample code is located at " + sampleDir.getAbsolutePath() + ".");
            getjlSampleLocation().setToolTipText(sampleDir.getAbsolutePath());
        }
        else
        {
            getjlSampleLocation().setText("Sample code will be included when you download the demo");
        }
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void init() 
	{
        this.getjlTitle().setText("jPDFFields Sample - " + PDFFields.getVersion ());
		
        
        // add listeners
        getJtfFileName().addKeyListener(this);
        
        InputStream stream = getClass().getResourceAsStream("/jPDFFields.html");
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
    	
    	// fill the acroform field table
    	fillFieldTable();
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
            jspRTFDocument.setPreferredSize(new Dimension((int)(630 * SampleUtil.getDPIScalingMultiplier()),(int)(200 * SampleUtil.getDPIScalingMultiplier())));
            jspRTFDocument.setViewportView(getJepRTFPane());
		}
		return jspRTFDocument;
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
            jepRTFPane.setPreferredSize(new Dimension((int)(630 * SampleUtil.getDPIScalingMultiplier()),(int)(400 * SampleUtil.getDPIScalingMultiplier())));
            jepRTFPane.setMinimumSize(new Dimension((int)(630 * SampleUtil.getDPIScalingMultiplier()),(int)(400 * SampleUtil.getDPIScalingMultiplier())));
		}
		return jepRTFPane;
	}
	
    public JPanel getjpTitle()
    {
        if (jpTitle == null)
        {
            jpTitle = new JPanel();
            jpTitle.setLayout(new CardLayout());
            jpTitle.setPreferredSize(new Dimension((int)(630 * SampleUtil.getDPIScalingMultiplier()),(int)(25 * SampleUtil.getDPIScalingMultiplier())));
            jpTitle.setBorder(new EtchedBorder());
            jpTitle.add(getjlTitle(), getjlTitle().getName());
        }
        return jpTitle;
    }
    
    public JLabel getjlTitle()
    {
        if (jlTitle == null)
        {
            jlTitle = new JLabel("jlTitle");
            jlTitle.setText(PDFFields.getVersion());
            jlTitle.setHorizontalAlignment(SwingConstants.CENTER);
            jlTitle.setFont(new Font("dialog", Font.BOLD, (int)SampleUtil.getScaledFont(jlTitle.getFont().getSize(), 14)));
        }
        return jlTitle;
    }
    
    /*
     * Return the file name of the current PDF document without the extension
     */
    public String getFileName()
    {
        if(m_File != null)
        {
            String name = m_File.getName();
            if (name != null && name.toLowerCase().endsWith(".pdf"))
            {
            	return name.substring(0, name.length()-4);
            }
        }
        return "out";
    }

	
	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getjpSampleProcessing() 
    {
		if (jpSampleProcessing == null) 
        {
			JLabel jLabel9 = new JLabel("Number of AcroForm Fields:");
			jLabel9.setBounds(new Rectangle((int)(20 * SampleUtil.getDPIScalingMultiplier()), (int)(55 * SampleUtil.getDPIScalingMultiplier()), (int)(174 * SampleUtil.getDPIScalingMultiplier()), (int)(18 * SampleUtil.getDPIScalingMultiplier())));
			jLabel9.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jLabel9.getFont().getSize(), 12)));
			JLabel jLabel = new JLabel("File Name:");
			jLabel.setBounds(new Rectangle((int)(20 * SampleUtil.getDPIScalingMultiplier()), (int)(25 * SampleUtil.getDPIScalingMultiplier()), (int)(80 * SampleUtil.getDPIScalingMultiplier()), (int)(18 * SampleUtil.getDPIScalingMultiplier())));
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jLabel.getFont().getSize(), 12)));

			jlDemoLimit.setBounds(new Rectangle((int)(20 * SampleUtil.getDPIScalingMultiplier()), (int)(293 * SampleUtil.getDPIScalingMultiplier()), (int)(480 * SampleUtil.getDPIScalingMultiplier()), (int)(18 * SampleUtil.getDPIScalingMultiplier())));
			jlDemoLimit.setText("Demo version limits: watermark + some field values changed to Demo Version");
            jlDemoLimit.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jlDemoLimit.getFont().getSize(), 12)));
            jlDemoLimit.setForeground(new Color(200,0,0));
			
            jpSampleProcessing = new JPanel();
            jpSampleProcessing.setLayout(null);
            jpSampleProcessing.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED), "Sample Processing", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(new JLabel().getFont().getSize(), 12)), java.awt.Color.black));
            jpSampleProcessing.setPreferredSize(new Dimension((int)(630 * SampleUtil.getDPIScalingMultiplier()), (int)(400 * SampleUtil.getDPIScalingMultiplier())));
            
            jpSampleProcessing.add(getjbOpen());
            jpSampleProcessing.add(jlDemoLimit);
            jpSampleProcessing.add(getjbExportFDF());
            jpSampleProcessing.add(getJtfFileName());
            jpSampleProcessing.add(jLabel);
            jpSampleProcessing.add(getjbImportFDF());
            jpSampleProcessing.add(jLabel9);
            jpSampleProcessing.add(getJcbJavaScript());
            jpSampleProcessing.add(getjlNumFields());
            jpSampleProcessing.add(getJspFieldTable());
            jpSampleProcessing.add(getjbSavePDF());
            jpSampleProcessing.add(getjbExportXFDF());
            jpSampleProcessing.add(getjbImportXFDF());
            jpSampleProcessing.add(getjbFlattenFields());
            jpSampleProcessing.add(getjbResetFields());
            jpSampleProcessing.add(getjbExportXDP());
            jpSampleProcessing.add(getjbImportXDP());
		}
		return jpSampleProcessing;
	}
	
	/**
	 * This method displays a file chooser dialog and gets a file name.	
	 * 	
	 * @return java.io.File	
	 */ 
	public File getFile (boolean open, String fileFilter, String defaultFileName)
	{
	    // Get first file name
	    JFileChooser fileChooser = new JFileChooser ();

	    // Initialize directory
	    if (m_LastFileDir != null)
	    {
	        fileChooser.setCurrentDirectory(m_LastFileDir);
	    }
	    
        // set default file name
        if(defaultFileName != null)
        {
            fileChooser.setSelectedFile(new File(defaultFileName));
        }
        
	    // Look for PDF files
	    fileChooser.setFileFilter(new CustomFileFilter(fileFilter));
	    
        // Show the open dialog
		if(open)
		{
		    if (fileChooser.showOpenDialog(this) == JFileChooser.CANCEL_OPTION)
		    {
		        return null;
		    }
		}
		// Show save dialog
		else
		{
			if (fileChooser.showSaveDialog(this) == JFileChooser.CANCEL_OPTION)
		    {
		        return null;
		    }
		}
		
		// Save last directory location
		File chosenFile = fileChooser.getSelectedFile();
		return chosenFile;
	}
	
	/**
	 * This method returns the applet's first parent frame and positions
	 * it at the same location as the applet. 
	 * 	
	 * @return java.io.File	
	 */ 
	public Frame getParentFrame()
	{
		Object parent = this.getParent();
		while(!(parent instanceof Frame)) parent=((Component)parent).getParent();
		Frame frame = (Frame) parent;
 
		Point p = this.getLocationOnScreen();
		frame.setLocation(p.x, p.y);
 
		return frame;
	}

	
	/**
	 * This method displays a dialog to type in a password and returns the password.
	 * 	
	 * @return byte[]	
	 */ 
	public String [] getPasswords()
    {
        // Create and show the password dialog
        Frame parentFrame = (Frame)SwingUtilities.windowForComponent(this);
        return PasswordDialog.showAndGetPassword(null, parentFrame);
    }
    
	/**
	 * This method initializes jbOpen	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getjbOpen() {
		if (jbOpen == null) {
			jbOpen = new JButton("Open PDF");
			jbOpen.setBounds(new Rectangle((int)(470 * SampleUtil.getDPIScalingMultiplier()), (int)(15 * SampleUtil.getDPIScalingMultiplier()), (int)(132 * SampleUtil.getDPIScalingMultiplier()), (int)(25 * SampleUtil.getDPIScalingMultiplier())));
			jbOpen.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jbOpen.getFont().getSize(), 12)));
			jbOpen.addActionListener(new java.awt.event.ActionListener() 
			     { 
				public void actionPerformed(java.awt.event.ActionEvent e)
				{    
				    // Open pdf file
				    File file = getFile (true, "PDF", null);
				    if (file == null)
				    {
				        return;
				    }				    
				    openPDF(file);
                    m_File = file;
				}
			});
		}
		return jbOpen;
	}
	/**
	 * This method initializes jbExportFDF	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getjbExportFDF() {
		if (jbExportFDF == null) {
			jbExportFDF = new JButton("Export As FDF");
			jbExportFDF.setBounds(new Rectangle((int)(470 * SampleUtil.getDPIScalingMultiplier()), (int)(45 * SampleUtil.getDPIScalingMultiplier()), (int)(132 * SampleUtil.getDPIScalingMultiplier()), (int)(25 * SampleUtil.getDPIScalingMultiplier())));
			jbExportFDF.setFont(new Font("Dialog", Font.PLAIN, (int)SampleUtil.getScaledFont(jbExportFDF.getFont().getSize(), 12)));
			jbExportFDF.addActionListener(new java.awt.event.ActionListener() 
				     { 
					public void actionPerformed(java.awt.event.ActionEvent e)
					{    
					    exportFDF();
					}
				});
		}
		return jbExportFDF;
	}
	
	
	/**
	 * Exports the fields of the loaded pdf document in FDF format
	 * 		
	 */
	public void exportFDF() 
	{
		if(m_PDFFields != null)
        {
			File file = getFile(false, "FDF", getFileName() + ".fdf");
			if(file != null)
			{
				try
				{
					m_PDFFields.exportAsFDF(file.getAbsolutePath(), true);
				}
				catch(PDFException pdfException)
			    {
			       JOptionPane.showMessageDialog(this, "Error exporting as fdf " + pdfException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			    }
				catch (IOException ioE)
				{
				    JOptionPane.showMessageDialog(this, "Error exporting as fdf " + ioE.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
        }
        
	}
    
    /**
     * Exports the fields of the loaded pdf document in XDP format
     *      
     */
    public void exportXDP() 
    {
        if(m_PDFFields != null)
        {
            File file = getFile(false, "XDP", getFileName() + ".xdp");
            if(file != null)
            {
                try
                {
                    m_PDFFields.exportAsXDP(file.getAbsolutePath(), true);
                }
                catch(PDFException pdfException)
                {
                   JOptionPane.showMessageDialog(this, "Error exporting as xdp " + pdfException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                catch(IOException ioException)
                {
                   JOptionPane.showMessageDialog(this, "Error exporting as xdp " + ioException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
    }
    
    /**
     * Exports the fields of the loaded pdf document in XFDF format
     *      
     */
    public void exportXFDF() 
    {
        if(m_PDFFields != null)
        {
            File file = getFile(false, "XFDF", getFileName() + ".xfdf");
            if(file != null)
            {
                try
                {
                    m_PDFFields.exportAsXFDF(file.getAbsolutePath(), true);
                }
                catch(PDFException pdfException)
                {
                   JOptionPane.showMessageDialog(this, "Error exporting as xfdf " + pdfException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
    }
    
    /**
     * Imports data from XFDF into the fields of the loaded pdf document.
     *      
     */
    public void importXFDF() 
    {
        if(m_PDFFields != null)
        {
            File file = getFile(false, "XFDF", getFileName() + ".xfdf");
            if(file != null)
            {
                try
                {
                    m_PDFFields.importXFDF(file.getAbsolutePath());
					// fill the field table with new data
					fillFieldTable();
                    // message
                    JOptionPane.showMessageDialog(this, "XFDF file imported, please make sure to save PDF document", "Message", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(PDFException pdfException)
                {
                   JOptionPane.showMessageDialog(this, "Error importing as XFDF " + pdfException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
    }
	
	/**
	 * This method opens a pdf document and fill the fields table with all the fields information.
	 * 	
	 */
	public void openPDF(File file)
	{
		try
	    {
			// keep file path for next time
			if (file != null)
			{
			    m_LastFileDir = file.getParentFile();
			}
			
	    	// set file name in text field
	    	getJtfFileName().setText(file.getAbsolutePath());
	    	
		    // Open and decrypt the document
	    	m_PDFFields = new PDFFields(file.getAbsolutePath(), this);
	    	
	    	// fill the acroform field table
	    	fillFieldTable();
	    }
	    catch (PDFException pdfE)
	    {
	        JOptionPane.showMessageDialog (this, pdfE.getMessage());
	    }
	}
	
    /**
     * Imports data from FDF into the fields of the loaded pdf document.
     *      
     */
	public void importFDF()
	{
		if(m_PDFFields != null)
        {
			File file = getFile(true, "FDF", getFileName() + ".fdf");
			if(file != null)
			{
				try
				{
					m_PDFFields.importFDF(file.getAbsolutePath());
					// fill the field table with new data
					fillFieldTable();
					// message
                    JOptionPane.showMessageDialog(this, "FDF file imported, please make sure to save PDF document", "Message", JOptionPane.INFORMATION_MESSAGE);
                }
				catch(PDFException pdfException)
			    {
			       JOptionPane.showMessageDialog(this, "Error importing from FDF " + pdfException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			    }
			}
        }
	}
    
    /**
     * Imports data from XDP into the fields of the loaded pdf document.
     *      
     */
    public void importXDP()
    {
        if(m_PDFFields != null)
        {
            File file = getFile(true, "XDP", getFileName() + ".xdp");
            if(file != null)
            {
                try
                {
                    m_PDFFields.importXDP(file.getAbsolutePath());
                    // fill the field table with new data
                    fillFieldTable();
                    // message
                    JOptionPane.showMessageDialog(this, "XDP file imported, please make sure to save PDF document", "Message", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(PDFException pdfException)
                {
                   JOptionPane.showMessageDialog(this, "Error importing from XDP " + pdfException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                catch(IOException ioException)
                {
                   JOptionPane.showMessageDialog(this, "Error importing from XDP " + ioException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
     /**
     * Flatten fields in the current pdf document. Fields will be painted directly into the content of the page
     * and will be removed as fields from the document.      
     */
    public void flattenFields()
    {
        if(m_PDFFields != null)
        {
            try
            {
                // don't paint the push buttons
                m_PDFFields.flattenFields(false, false);
                
                // refill the fields table to empty it
                fillFieldTable(false);
                
                // message
                JOptionPane.showMessageDialog(this, "Fields flattened, please make sure to save PDF document", "Message", JOptionPane.INFORMATION_MESSAGE);
            }
            catch(PDFException pdfException)
            {
               JOptionPane.showMessageDialog(this, "Error flattening pdf " + pdfException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
	
	/**
	 * This method initializes jlEncrypted	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	private JLabel getjlNumFields() {
		if (jlNumFields == null) {
			jlNumFields = new JLabel("N/A");
			jlNumFields.setBounds(new Rectangle((int)(194 * SampleUtil.getDPIScalingMultiplier()), (int)(55 * SampleUtil.getDPIScalingMultiplier()), (int)(110 * SampleUtil.getDPIScalingMultiplier()), (int)(18 * SampleUtil.getDPIScalingMultiplier())));
			jlNumFields.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jlNumFields.getFont().getSize(), 12)));
		}
		return jlNumFields;
	}
	/**
	 * This method initializes jtfFileName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtfFileName() {
		if (jtfFileName == null) {
			jtfFileName = new JTextField();
			jtfFileName.setBounds(new Rectangle((int)(100 * SampleUtil.getDPIScalingMultiplier()), (int)(24 * SampleUtil.getDPIScalingMultiplier()), (int)(335 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier())));
		}
		return jtfFileName;
	}
	/**
	 * This method initializes jbImportFDF	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getjbImportFDF() {
		if (jbImportFDF == null) {
			jbImportFDF = new JButton("Import FDF");
			jbImportFDF.setBounds(new Rectangle((int)(470 * SampleUtil.getDPIScalingMultiplier()), (int)(75 * SampleUtil.getDPIScalingMultiplier()), (int)(132 * SampleUtil.getDPIScalingMultiplier()), (int)(25 * SampleUtil.getDPIScalingMultiplier())));
			jbImportFDF.setFont(new Font("Dialog", Font.PLAIN, (int)SampleUtil.getScaledFont(jbImportFDF.getFont().getSize(), 12)));
			jbImportFDF.addActionListener(new java.awt.event.ActionListener() 
				     {   
					public void actionPerformed(java.awt.event.ActionEvent e)
					{    						
					    importFDF();
					}
			   }
		);
		}
		return jbImportFDF;
	}
	public void keyPressed(KeyEvent keyEvent) {
		// nothing to do
		
	}
	public void keyReleased(KeyEvent keyEvent) 
	{
		if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER)
		{
			openPDF(new File(getJtfFileName().getText()));
		}
	}
	public void keyTyped(KeyEvent keyEvent) {
		// nothing to do
		
	}
	/**
	 * Return the ScrollPaneTable property value.
	 * @return javax.swing.JTable
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JTable getjtAcroFields() {
		if (jtAcroFields == null) {
			try {
                jtAcroFields = new javax.swing.JTable();
                jtAcroFields.setName("ScrollPaneTable");
                jtAcroFields.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
                jtAcroFields.setBounds(0, 0, (int)(200 * SampleUtil.getDPIScalingMultiplier()), (int)(200 * SampleUtil.getDPIScalingMultiplier()));
                jtAcroFields.setRowSelectionAllowed(true);
			} catch (java.lang.Throwable ivjExc) {
			}
		}
		return jtAcroFields;
	}
	/**
	 * Return the printTable property value.
	 * @return javax.swing.JScrollPane
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private JScrollPane getJspFieldTable() {
		if (jspFieldTable == null) {
			try {
				jspFieldTable = new javax.swing.JScrollPane();
				jspFieldTable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
				jspFieldTable.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				jspFieldTable.setBounds(new Rectangle((int)(20 * SampleUtil.getDPIScalingMultiplier()), (int)(80 * SampleUtil.getDPIScalingMultiplier()), (int)(440 * SampleUtil.getDPIScalingMultiplier()), (int)(210 * SampleUtil.getDPIScalingMultiplier())));
				jspFieldTable.setMinimumSize(new Dimension((int)(440 * SampleUtil.getDPIScalingMultiplier()), (int)(210 * SampleUtil.getDPIScalingMultiplier())));
				jspFieldTable.setPreferredSize(new Dimension((int)(440 * SampleUtil.getDPIScalingMultiplier()), (int)(210 * SampleUtil.getDPIScalingMultiplier())));
				jspFieldTable.setMaximumSize(new Dimension((int)(440 * SampleUtil.getDPIScalingMultiplier()), (int)(210 * SampleUtil.getDPIScalingMultiplier())));
				jspFieldTable.setViewportView(getjtAcroFields());
			} catch (java.lang.Throwable ivjExc) {
			}
		}
		return jspFieldTable;
	}
	public void fillFieldTable()
	{
		fillFieldTable(true);
	}
	/**
	 * Comment
	 */
	public void fillFieldTable(boolean displayNoFieldMsg) 
	{
		//
		// Create non-editable table model
		//
		DefaultTableModel tableModel = new DefaultTableModel ()
		{
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};

		//
		// Table columns
		//
		tableModel.addColumn ("Field Type");
		tableModel.addColumn ("Name");
		tableModel.addColumn ("Description");
		tableModel.addColumn ("Value");
		tableModel.addColumn ("Options");
		tableModel.addColumn ("Default");
		
		// initialize num fields label
        getjlNumFields().setText("0");
        
		if(m_PDFFields != null)
		{
			Vector fields = m_PDFFields.getFieldList();	
			if(fields != null)
			{
			    // set the label for number of fields
				getjlNumFields().setText(Integer.toString(fields.size()));
				
				// fill the table
				Vector newRow;
				for (int count = 0; count < fields.size(); ++count)
				{
					newRow = new Vector ();
					FormField field = (FormField) fields.get(count);
					
					if(field != null)
					{
						// field type description
						newRow.addElement (field.getFieldTypeDesc());

						//	field name
						newRow.addElement (field.getFullFieldName());
						
						//	field description (alternate name)
						newRow.addElement (field.getAltFieldName());
						
						// field value & options
						if(field instanceof TextField)
						{
							newRow.addElement (((TextField) field).getValue());
							newRow.addElement (null);
							newRow.addElement (((TextField) field).getDefaultValue());
						}
						else if(field instanceof RadioButtonGroupField)
						{
							newRow.addElement (((RadioButtonGroupField) field).getValue());
							newRow.addElement (null);
							newRow.addElement (((RadioButtonGroupField) field).getDefaultValue());
							
						}
						else if(field instanceof CheckBoxField)
						{
							newRow.addElement (((CheckBoxField) field).getValue());
							newRow.addElement (null);
							newRow.addElement (((CheckBoxField) field).getDefaultValue());
						}
						else if(field instanceof ComboField)
						{
							newRow.addElement (((ComboField) field).getValue());
							newRow.addElement (((ComboField) field).getDisplayOptions());
							newRow.addElement (((ComboField) field).getDefaultValue());
						}
						else if(field instanceof ListField)
						{
							newRow.addElement (((ListField) field).getValues());
							newRow.addElement (((ListField) field).getDisplayOptions());
							newRow.addElement (((ListField) field).getDefaultValue());
						}
						else if(field instanceof SignatureField)
                        {
                            newRow.addElement (((SignatureField) field).getSignName() + "-" +((SignatureField) field).getSignDateTime());
                         }
					}
					tableModel.addRow (newRow);
				}
			}
			else if (displayNoFieldMsg)
			{
	            JOptionPane.showMessageDialog (this, "The PDF document does not have any form fields.", "No Form Fields", JOptionPane.INFORMATION_MESSAGE);
			}
		}
        getjtAcroFields().setModel (tableModel);
		
		// columns width
        getjtAcroFields().getColumnModel().getColumn(0).setPreferredWidth(getJspFieldTable().getWidth()/6);
        getjtAcroFields().getColumnModel().getColumn(1).setPreferredWidth(getJspFieldTable().getWidth()/6);
        getjtAcroFields().getColumnModel().getColumn(2).setPreferredWidth(getJspFieldTable().getWidth()/6);
        getjtAcroFields().getColumnModel().getColumn(3).setPreferredWidth(getJspFieldTable().getWidth()/6);
        getjtAcroFields().getColumnModel().getColumn(4).setPreferredWidth(getJspFieldTable().getWidth()/6);
        getjtAcroFields().getColumnModel().getColumn(5).setPreferredWidth(getJspFieldTable().getWidth()/6);
	}

	private JButton getjbSavePDF()
	{
		if (jbSavePDF == null) {
			jbSavePDF = new JButton("Save As PDF");
			jbSavePDF.setBounds(new Rectangle((int)(470 * SampleUtil.getDPIScalingMultiplier()), (int)(289 * SampleUtil.getDPIScalingMultiplier()), (int)(132 * SampleUtil.getDPIScalingMultiplier()), (int)(25 * SampleUtil.getDPIScalingMultiplier())));
			jbSavePDF.setFont(new Font("Dialog", Font.PLAIN, (int)SampleUtil.getScaledFont(jbSavePDF.getFont().getSize(), 12)));
			jbSavePDF.addActionListener(new java.awt.event.ActionListener() 
			{   
				public void actionPerformed(java.awt.event.ActionEvent e)
				{    						
					   savePDF();
				}
			});
		}
		return jbSavePDF;
	}
    
    /**
     * Reset the fields to their default value. If there is no default value then the value is set to null;    
     */
    public void resetFields()
    {
        if(m_PDFFields != null)
        {
            try
            {
                m_PDFFields.resetFields();

                // fill the field table with new data
                fillFieldTable();
                
                // message
                JOptionPane.showMessageDialog(this, "Fields reset, please make sure to save PDF document", "Message", JOptionPane.INFORMATION_MESSAGE);
            }
            catch(PDFException pdfException)
            {
               JOptionPane.showMessageDialog(this, "Error resetting fields " + pdfException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
	

    /**
     * Prompts for a pdf file name and saves the updated form in this pdf file.
     *  
     */
	public void savePDF() 
	{
		try
        {
        	// get a file name
        	File file = getFile(false, "PDF", getFileName() + "_2.pdf");
        	 if (file != null)
		    {
		    	if( file.exists())
		    	{ int rc = JOptionPane.showConfirmDialog(this, "Overwrite " + file.getName() + "?", "Overwrite", JOptionPane.YES_NO_OPTION);
			        if (rc != JOptionPane.YES_OPTION)
			        {
			            return;
			        }
		    	}
		    	
		    	// save the document
	        	m_PDFFields.saveDocument(file.getAbsolutePath());
		    }
        }
        catch(PDFException pdfException)
        {
        	JOptionPane.showMessageDialog(this, "Error saving file " + pdfException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        catch(IOException ioException)
        {
        	JOptionPane.showMessageDialog(this, "Error saving file " + ioException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
		
	}
    /**
     * This method initializes jButton	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getjbExportXFDF()
    {
        if (jbExportXFDF == null)
        {
            jbExportXFDF = new JButton("Export As XFDF");
            jbExportXFDF.setBounds(new Rectangle((int)(470 * SampleUtil.getDPIScalingMultiplier()), (int)(105 * SampleUtil.getDPIScalingMultiplier()), (int)(132 * SampleUtil.getDPIScalingMultiplier()), (int)(26 * SampleUtil.getDPIScalingMultiplier())));
            jbExportXFDF.setFont(new Font("Dialog", Font.PLAIN, (int)SampleUtil.getScaledFont(jbExportXFDF.getFont().getSize(), 12)));
            
            jbExportXFDF.addActionListener(new java.awt.event.ActionListener() 
                    { 
                   public void actionPerformed(java.awt.event.ActionEvent e)
                   {    
                          exportXFDF();
                   }
               });
        }
        return jbExportXFDF;
    }
    /**
     * This method initializes jButton1	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getjbImportXFDF()
    {
        if (jbImportXFDF == null)
        {
            jbImportXFDF = new JButton("Import XFDF");
            jbImportXFDF.setBounds(new Rectangle((int)(470 * SampleUtil.getDPIScalingMultiplier()), (int)(135 * SampleUtil.getDPIScalingMultiplier()), (int)(132 * SampleUtil.getDPIScalingMultiplier()), (int)(26 * SampleUtil.getDPIScalingMultiplier())));
            jbImportXFDF.setFont(new Font("Dialog", Font.PLAIN, (int)SampleUtil.getScaledFont(jbImportXFDF.getFont().getSize(), 12)));
            
            jbImportXFDF.addActionListener(new java.awt.event.ActionListener() 
                 { 
                public void actionPerformed(java.awt.event.ActionEvent e)
                {    
                       importXFDF();
                }
            });
        }
        return jbImportXFDF;
    }
    /**
     * This method initializes jButton	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getjbFlattenFields()
    {
        if (jbFlattenFields == null)
        {
            jbFlattenFields = new JButton("Flatten Fields");
            jbFlattenFields.setBounds(new Rectangle((int)(470 * SampleUtil.getDPIScalingMultiplier()), (int)(229 * SampleUtil.getDPIScalingMultiplier()), (int)(132 * SampleUtil.getDPIScalingMultiplier()), (int)(26 * SampleUtil.getDPIScalingMultiplier())));
            jbFlattenFields.setFont(new Font("Dialog", Font.PLAIN, (int)SampleUtil.getScaledFont(jbFlattenFields.getFont().getSize(), 12)));
            
            jbFlattenFields.addActionListener(new java.awt.event.ActionListener() 
                    { 
                   public void actionPerformed(java.awt.event.ActionEvent e)
                   {    
                          flattenFields();
                   }
               });
        }
        return jbFlattenFields;
    }
    /**
     * This method initializes jButton	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getjbResetFields()
    {
        if (jbResetFields == null)
        {
            jbResetFields = new JButton("Reset Fields");
            jbResetFields.setBounds(new Rectangle((int)(470 * SampleUtil.getDPIScalingMultiplier()), (int)(259 * SampleUtil.getDPIScalingMultiplier()), (int)(132 * SampleUtil.getDPIScalingMultiplier()), (int)(26 * SampleUtil.getDPIScalingMultiplier())));
            jbResetFields.setFont(new Font("Dialog", Font.PLAIN, (int)SampleUtil.getScaledFont(jbResetFields.getFont().getSize(), 12)));
            jbResetFields.addActionListener(new java.awt.event.ActionListener() 
            {   
                public void actionPerformed(java.awt.event.ActionEvent e)
                {                           
                       resetFields();
                }

            });
        }
        return jbResetFields;
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
            jpAPI.setPreferredSize(new Dimension ((int)(630 * SampleUtil.getDPIScalingMultiplier()), (int)(70 * SampleUtil.getDPIScalingMultiplier())));
            jpAPI.setMinimumSize(new Dimension((int)(630 * SampleUtil.getDPIScalingMultiplier()), (int)(70 * SampleUtil.getDPIScalingMultiplier())));
            jpAPI.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
            jLabel1.setBounds(new Rectangle((int)(100 * SampleUtil.getDPIScalingMultiplier()), (int)(40 * SampleUtil.getDPIScalingMultiplier()), (int)(126 * SampleUtil.getDPIScalingMultiplier()), (int)(16 * SampleUtil.getDPIScalingMultiplier())));
            jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jLabel1.getFont().getSize(), 12)));
            jpAPI.add(getjlJARLocation(), null);
            jpAPI.add(jLabel1, null);
            jpAPI.add(getJbViewAPI(), null);
            jpAPI.add(getjlSampleLocation(), null);
        }
        return jpAPI;
    }
    
    private JLabel getjlJARLocation ()
    {
        if (jlJARLocation == null)
        {
            jlJARLocation = new JLabel("JLabel");
            jlJARLocation.setBounds(new Rectangle((int)(15 * SampleUtil.getDPIScalingMultiplier()), (int)(3 * SampleUtil.getDPIScalingMultiplier()), (int)(630 * SampleUtil.getDPIScalingMultiplier()), (int)(16 * SampleUtil.getDPIScalingMultiplier())));
            jlJARLocation.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jlJARLocation.getFont().getSize(), 12)));
        }
        return jlJARLocation;       
    }
    
    private JLabel getjlSampleLocation()
    {
        if (jlSampleLocation == null)
        {
            jlSampleLocation = new JLabel("JLabel");
            jlSampleLocation.setBounds(new Rectangle((int)(14 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier()), (int)(630 * SampleUtil.getDPIScalingMultiplier()), (int)(16 * SampleUtil.getDPIScalingMultiplier())));
            jlSampleLocation.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jlSampleLocation.getFont().getSize(), 12)));
        }
        return jlSampleLocation;
    }
    
    /**
     * This method initializes jbViewAPI    
     *  
     * @return javax.swing.JButton  
     */    
    private JButton getJbViewAPI() {
        if (jbViewAPI == null)
        {
            jbViewAPI = new JButton("Click Here");
            jbViewAPI.setBounds(new Rectangle((int)(10 * SampleUtil.getDPIScalingMultiplier()), (int)(38 * SampleUtil.getDPIScalingMultiplier()), (int)(85 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier())));
            jbViewAPI.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jbViewAPI.getFont().getSize(), 12)));
            jbViewAPI.setMargin(new java.awt.Insets(2,2,2,2));
            jbViewAPI.addActionListener(new java.awt.event.ActionListener()
            { 
                public void actionPerformed(java.awt.event.ActionEvent e) 
                {
                    viewAPI();
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
            try
            {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            catch (Throwable tt)
            {
                // ignore
            }
        }
    }
    public void componentResized(ComponentEvent arg0)
    {
        // resize the fields table
        int newTableWidth = getjpSampleProcessing().getWidth() - getJspFieldTable().getX() - (int)(150 * SampleUtil.getDPIScalingMultiplier());
        int newTableHeight = getjpSampleProcessing().getHeight() - getJspFieldTable().getY() - (int)(20 * SampleUtil.getDPIScalingMultiplier());
        getJspFieldTable().setSize(newTableWidth, newTableHeight);
        getJspFieldTable().doLayout();
        getJspFieldTable().repaint();
        
        // move demo label
        jlDemoLimit.setLocation(jlDemoLimit.getLocation().x, getjpSampleProcessing().getHeight() - (int)(20 * SampleUtil.getDPIScalingMultiplier()));
        
        // move x for buttons
        int newButtonX= getjpSampleProcessing().getWidth() - (int)(140 * SampleUtil.getDPIScalingMultiplier());
        getjbFlattenFields().setLocation(newButtonX, getjbFlattenFields().getLocation().y);
        getjbImportFDF().setLocation(newButtonX, getjbImportFDF().getLocation().y);
        getjbExportFDF().setLocation(newButtonX, getjbExportFDF().getLocation().y);
        getjbExportXFDF().setLocation(newButtonX, getjbExportXFDF().getLocation().y);
        getjbImportXFDF().setLocation(newButtonX, getjbImportXFDF().getLocation().y);
        getjbExportXDP().setLocation(newButtonX, getjbExportXDP().getLocation().y);
        getjbImportXDP().setLocation(newButtonX, getjbImportXDP().getLocation().y);
        getjbOpen().setLocation(newButtonX, getjbOpen().getLocation().y);
        getjbResetFields().setLocation(newButtonX, getjbResetFields().getLocation().y);
        getjbSavePDF().setLocation(newButtonX, getjbSavePDF().getLocation().y);
        
        // columns width
        if(getjtAcroFields().getColumnModel().getColumnCount() >= 6)
        {
            getjtAcroFields().getColumnModel().getColumn(0).setPreferredWidth(getJspFieldTable().getWidth()/6);
            getjtAcroFields().getColumnModel().getColumn(1).setPreferredWidth(getJspFieldTable().getWidth()/6);
            getjtAcroFields().getColumnModel().getColumn(2).setPreferredWidth(getJspFieldTable().getWidth()/6);
            getjtAcroFields().getColumnModel().getColumn(3).setPreferredWidth(getJspFieldTable().getWidth()/6);
            getjtAcroFields().getColumnModel().getColumn(4).setPreferredWidth(getJspFieldTable().getWidth()/6);
            getjtAcroFields().getColumnModel().getColumn(5).setPreferredWidth(getJspFieldTable().getWidth()/6);
        }
        
    }
    public void componentMoved(ComponentEvent arg0)
    {
        // nothing to do
        
    }
    public void componentShown(ComponentEvent arg0)
    {
        // nothing to do
    }
    public void componentHidden(ComponentEvent arg0)
    {
        // nothing to do
    }
    /**
     * This method initializes jButton	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getjbExportXDP()
    {
        if (jbExportXDP == null)
        {
            jbExportXDP = new JButton("Export As XDP");
            jbExportXDP.setBounds(new Rectangle((int)(470 * SampleUtil.getDPIScalingMultiplier()), (int)(166 * SampleUtil.getDPIScalingMultiplier()), (int)(132 * SampleUtil.getDPIScalingMultiplier()), (int)(26 * SampleUtil.getDPIScalingMultiplier())));
            jbExportXDP.setFont(new Font("Dialog", Font.PLAIN, (int)SampleUtil.getScaledFont(jbExportXDP.getFont().getSize(), 12)));
            jbExportXDP.addActionListener(new java.awt.event.ActionListener() 
                    {   
                public void actionPerformed(java.awt.event.ActionEvent e)
                {                           
                    exportXDP();
                }
           });
        }
        return jbExportXDP;
    }
    /**
     * This method initializes jButton1	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getjbImportXDP()
    {
        if (jbImportXDP == null)
        {
            jbImportXDP = new JButton("Import XDP");
            jbImportXDP.setBounds(new Rectangle((int)(470 * SampleUtil.getDPIScalingMultiplier()), (int)(198 * SampleUtil.getDPIScalingMultiplier()), (int)(132 * SampleUtil.getDPIScalingMultiplier()), (int)(26 * SampleUtil.getDPIScalingMultiplier())));
            jbImportXDP.setFont(new Font("Dialog", Font.PLAIN, (int)SampleUtil.getScaledFont(jbImportXDP.getFont().getSize(), 12)));
            jbImportXDP.addActionListener(new java.awt.event.ActionListener() 
                     {   
                    public void actionPerformed(java.awt.event.ActionEvent e)
                    {                           
                        importXDP();
                    }
               });
        }
        return jbImportXDP;
    }
    
    /**
     * This method initializes jcbJavaScript
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getJcbJavaScript()
    {
    	if (jcbJavascript == null)
    	{
    		jcbJavascript = new JCheckBox("Enable JavaScript");
    		jcbJavascript.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jcbJavascript.getFont().getSize(), 12)));
    		jcbJavascript.setBounds(new Rectangle((int)(250 * SampleUtil.getDPIScalingMultiplier()), (int)(55 * SampleUtil.getDPIScalingMultiplier()), (int)(150 * SampleUtil.getDPIScalingMultiplier()), (int)(18 * SampleUtil.getDPIScalingMultiplier())));
            jcbJavascript.addItemListener(new ItemListener() 
    		{
				public void itemStateChanged(ItemEvent e)
				{
					if (jcbJavascript.isSelected())
					{
						JavaScriptSettings.setJSEnabler(new JavaScriptEnabler(true));
						JavaScriptSettings.setAllowPopups(true);
					}
					else
					{
						JavaScriptSettings.setJSEnabler(new JavaScriptEnabler(false));
					}

					// handle case that document is already open
					if (m_PDFFields != null)
					{
						SwingUtilities.invokeLater(new Runnable(){
							public void run()
							{
								JOptionPane.showMessageDialog(null, "This setting will apply to newly opened documents.", "JavaScript", JOptionPane.WARNING_MESSAGE);
							}
						});
					}
				}
			});
    	}
    	return jcbJavascript;
    }

}  //  @jve:decl-index=0:visual-constraint="37,1"
