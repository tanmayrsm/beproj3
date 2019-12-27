/*
 * Created on Dec 8, 2004
 *
 */
package jPDFImagesSamples;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTMLDocument;

import com.qoppa.pdf.IPassword;
import com.qoppa.pdf.PDFException;
import com.qoppa.pdf.PasswordDialog;
import com.qoppa.pdf.TIFFOptions;
import com.qoppa.pdfImages.PDFImages;

/**
 * @author Qoppa Software
 * 
 */
public class PDFImagesSample extends JPanel implements IPassword
{
	private JScrollPane jspRTFDocument = null;
	private JEditorPane jepRTFPane = null;
	private JPanel jpAPI = null;
	private JLabel jlJARLocation = null;
	private JButton jbViewAPI = null;

	private JPanel jpTitle = null;
	private JLabel jlTitle = null;

	private final static String OS_WINDOWS_START = "windows";
	private final static String OS_MAC = "mac";
	private final static String JAR_FILE_NAME = "jPDFImages.jar";
	private final static String SAMPLES_DIR_NAME = "jPDFImagesSamples";
	private final static String API_INDEX_FILENAME = "javadoc/index.html";

	private final static String JPG_EXT = ".jpg";
	private final static String PNG_EXT = ".png";
	private final static String TIF_EXT = ".tif";

	private JPanel jpSample = null;
	private JButton jbExportJPEGs = null;
	private JLabel jlSampleCode = null;
	private JLabel jlSampleLocation = null;
	private JButton jbExportTIFF = null;
	private JButton jbExportTIFFBW = null;
	private JButton jbExportPNGs = null;
	private JButton jbPDFFromImage = null;

	private File m_LastFileDir = null;
	private JButton jbOpen = null;
	private JTextField jtfFileName = null;
	private JRadioButton jrbSingleFile = null;
	private JRadioButton jrbDirectory = null;

	// Sample DPI exports at 144 pixels per inch (double native PDF resolution)
	private final static int EXPORT_DPI = 144;

	/**
	 * This method initializes
	 * 
	 */
	public PDFImagesSample()
	{
		super();
		initialize();
	}

	private void initialize()
	{
		// Layout
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		this.add(getjpTitle(), null);
		this.add(Box.createRigidArea(new Dimension(5, 5)));
		this.add(getjspRTFDocument(), null);
		this.add(Box.createRigidArea(new Dimension(5, 5)));
		this.add(getJPAPI(), null);
		this.add(Box.createRigidArea(new Dimension(5, 5)));
		this.add(getJlSampleCode(), null);
		this.add(Box.createRigidArea(new Dimension(5, 5)));
		this.add(getJPSample(), null);
		InputStream stream = getClass().getResourceAsStream("/jPDFImages.html");
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
		File jarFile = new File("lib/" + JAR_FILE_NAME);
		getjlJARLocation().setText(JAR_FILE_NAME + " is located at " + jarFile.getAbsolutePath() + ".");
		getjlJARLocation().setToolTipText(jarFile.getAbsolutePath());

		File samplesDir = new File(SAMPLES_DIR_NAME);
		getjlSampleLocation().setText("You can find sample code at " + samplesDir.getAbsolutePath());
		getjlSampleLocation().setToolTipText(samplesDir.getAbsolutePath());
	}

	public JPanel getjpTitle()
	{
		if (jpTitle == null)
		{
			jpTitle = new JPanel();
			jpTitle.add(getjlTitle());
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
			jlTitle.setText(PDFImages.getVersion());
			jlTitle.setFont(new Font("dialog", Font.BOLD, (int)SampleUtil.getScaledFont(jlTitle.getFont().getSize(), 14)));
		}
		return jlTitle;
	}

	private JScrollPane getjspRTFDocument()
	{
		if (jspRTFDocument == null)
		{
			jspRTFDocument = new JScrollPane();
			jspRTFDocument.setLocation(20, 20);
			jspRTFDocument.setPreferredSize(new java.awt.Dimension((int)(700 * SampleUtil.getDPIScalingMultiplier()), (int)(210 * SampleUtil.getDPIScalingMultiplier())));
			jspRTFDocument.setViewportView(getJepRTFPane());
		}
		return jspRTFDocument;
	}

	private JEditorPane getJepRTFPane()
	{
		if (jepRTFPane == null)
		{
			jepRTFPane = new JEditorPane();
			jepRTFPane.setMargin(new java.awt.Insets(10, 10, 10, 10));
			jepRTFPane.setEditable(false);
		}
		return jepRTFPane;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	public JPanel getJPAPI()
	{
		if (jpAPI == null)
		{
			jpAPI = new JPanel();
			JLabel jLabel1 = new JLabel("to view the API.");
			jpAPI.setLayout(null);
			jpAPI.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jpAPI.setPreferredSize(new java.awt.Dimension((int)(700 * SampleUtil.getDPIScalingMultiplier()), (int)(100 * SampleUtil.getDPIScalingMultiplier())));
			jLabel1.setBounds((int)(109 * SampleUtil.getDPIScalingMultiplier()), (int)(50 * SampleUtil.getDPIScalingMultiplier()), (int)(133 * SampleUtil.getDPIScalingMultiplier()), (int)(22 * SampleUtil.getDPIScalingMultiplier()));
			jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jLabel1.getFont().getSize(), 12)));
			jpAPI.add(getjlJARLocation(), null);
			jpAPI.add(getjlSampleLocation(), null);
			jpAPI.add(jLabel1, null);
			jpAPI.add(getJbViewAPI(), null);
		}
		return jpAPI;
	}

	private JLabel getjlJARLocation()
	{
		if (jlJARLocation == null)
		{
			jlJARLocation = new JLabel("JLabel");
			jlJARLocation.setBounds((int)(15 * SampleUtil.getDPIScalingMultiplier()), (int)(5 * SampleUtil.getDPIScalingMultiplier()), (int)(650 * SampleUtil.getDPIScalingMultiplier()), (int)(19 * SampleUtil.getDPIScalingMultiplier()));
			jlJARLocation.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jlJARLocation.getFont().getSize(), 12)));
		}
		return jlJARLocation;
	}

	private JButton getJbViewAPI()
	{
		if (jbViewAPI == null)
		{
			jbViewAPI = new JButton("Click Here");
			jbViewAPI.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jbViewAPI.getFont().getSize(), 12)));
			jbViewAPI.setBounds((int)(14 * SampleUtil.getDPIScalingMultiplier()), (int)(49 * SampleUtil.getDPIScalingMultiplier()), (int)(85 * SampleUtil.getDPIScalingMultiplier()), (int)(25 * SampleUtil.getDPIScalingMultiplier()));
			jbViewAPI.setMargin(new java.awt.Insets(2, 2, 2, 2));
			jbViewAPI.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					viewAPI();
				}
			});
		}
		return jbViewAPI;
	}

	protected void viewAPI()
	{
		try
		{
			File apiIndex = new File(API_INDEX_FILENAME);
			if (isSystemWindows())
			{
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + apiIndex.getAbsolutePath());
			}
			else if (isSystemMac())
			{
				String[] cmdArray = new String[2];
				cmdArray[0] = "open";
				cmdArray[1] = apiIndex.getAbsolutePath();
				Runtime.getRuntime().exec(cmdArray);
			}
			// assume Unix or Linux
			else
			{
				String[] browsers = { "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape", "google-chrome" };
				String browser = null;
				for (int count = 0; count < browsers.length && browser == null; count++)
				{
					if (Runtime.getRuntime().exec(new String[] { "which", browsers[count] }).waitFor() == 0)
					{
						browser = browsers[count];
						break;
					}
				}
				if (browser == null)
					throw new Exception("Could not find web browser");
				else
					Runtime.getRuntime().exec(new String[] { browser, apiIndex.getAbsolutePath() });
			}
		}
		catch (Throwable t)
		{
			javax.swing.JOptionPane.showMessageDialog(this, t.getMessage());
		}
	}

	private boolean isSystemMac()
	{
		// Check the OS
		String osName = System.getProperty("os.name");
		osName = osName.toLowerCase();
		int firstIndexOfMac = osName.indexOf(OS_MAC);
		if (firstIndexOfMac == -1)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	private boolean isSystemWindows()
	{
		// Check the OS
		String osName = System.getProperty("os.name");
		osName = osName.toLowerCase();
		return osName.startsWith(OS_WINDOWS_START);
	}

	public JLabel getJlSampleCode()
	{
		if (jlSampleCode == null)
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
			jlSampleLocation.setBounds((int)(15 * SampleUtil.getDPIScalingMultiplier()), (int)(25 * SampleUtil.getDPIScalingMultiplier()), (int)(645 * SampleUtil.getDPIScalingMultiplier()), (int)(19 * SampleUtil.getDPIScalingMultiplier()));
			jlSampleLocation.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jlSampleLocation.getFont().getSize(), 12)));
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
		if (jpSample == null)
		{
			JLabel jLabel3 = new JLabel("Creates a PDF document from an image file.  Source Code: PDFImagesSample.createFromImage().");
			jLabel3.setBounds((int)(120 * SampleUtil.getDPIScalingMultiplier()), (int)(229 * SampleUtil.getDPIScalingMultiplier()), (int)(749 * SampleUtil.getDPIScalingMultiplier()), (int)(16 * SampleUtil.getDPIScalingMultiplier()));
			jLabel3.setFont(new Font("Dialog", Font.PLAIN, (int)SampleUtil.getScaledFont(jLabel3.getFont().getSize(), 12)));
			JLabel jLabel2 = new JLabel("Exports a PDF document as a set of PNG files.  Source Code: PDFImagesSample.exportPNGs().");
			jLabel2.setBounds((int)(120 * SampleUtil.getDPIScalingMultiplier()), (int)(194 * SampleUtil.getDPIScalingMultiplier()), (int)(712 * SampleUtil.getDPIScalingMultiplier()), (int)(16 * SampleUtil.getDPIScalingMultiplier()));
			jLabel2.setFont(new Font("Dialog", Font.PLAIN, (int)SampleUtil.getScaledFont(jLabel2.getFont().getSize(), 12)));
			JLabel jLabel = new JLabel("Exports a PDF document as a multi-page TIFF file.  Source Code: PDFImagesSample.exportTIFF().");
			jLabel.setBounds((int)(120 * SampleUtil.getDPIScalingMultiplier()), (int)(124 * SampleUtil.getDPIScalingMultiplier()), (int)(712 * SampleUtil.getDPIScalingMultiplier()), (int)(16 * SampleUtil.getDPIScalingMultiplier()));
			jLabel.setFont(new Font("Dialog", Font.PLAIN, (int)SampleUtil.getScaledFont(jLabel.getFont().getSize(), 12)));
			JLabel jLabel4 = new JLabel("Exports a PDF document as a multi-page black And white TIFF file.  Source Code: PDFImagesSample.exportTIFF().");
			jLabel4.setBounds((int)(120 * SampleUtil.getDPIScalingMultiplier()), (int)(159 * SampleUtil.getDPIScalingMultiplier()), (int)(712 * SampleUtil.getDPIScalingMultiplier()), (int)(16 * SampleUtil.getDPIScalingMultiplier()));
			jLabel4.setFont(new Font("Dialog", Font.PLAIN, (int)SampleUtil.getScaledFont(jLabel4.getFont().getSize(), 12)));
			JLabel jLabel5 = new JLabel("Exports a PDF document as a set of JPEG files. Source Code: PDFImagesSample.exportJPGs().");
			jLabel5.setBounds((int)(120 * SampleUtil.getDPIScalingMultiplier()), (int)(89 * SampleUtil.getDPIScalingMultiplier()), (int)(712 * SampleUtil.getDPIScalingMultiplier()), (int)(16 * SampleUtil.getDPIScalingMultiplier()));
			jLabel5.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jLabel5.getFont().getSize(), 12)));
			jpSample = new JPanel();
			jpSample.setLayout(null);
			jpSample.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED), "Sample Processing",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(new JLabel().getFont().getSize(), 12)), java.awt.Color.black));
			jpSample.setPreferredSize(new java.awt.Dimension((int)(700 * SampleUtil.getDPIScalingMultiplier()), (int)(340 * SampleUtil.getDPIScalingMultiplier())));

			jpSample.add(getJrbSingleFile(), null);
			jpSample.add(getJrbDirectory(), null);
			jpSample.add(getJbOpen(), null);
			jpSample.add(getJtfFileName(), null);
			jpSample.add(getJbExportJPEG(), null);
			jpSample.add(jLabel5, null);
			jpSample.add(getjbExportTIFF(), null);
			jpSample.add(jLabel, null);
			jpSample.add(getjbExportTIFFBW(), null);
			jpSample.add(jLabel4, null);
			jpSample.add(getjbExportPNGs(), null);
			jpSample.add(jLabel2, null);
			jpSample.add(getJbPDFFromImage(), null);
			jpSample.add(jLabel3, null);

			ButtonGroup radioGroup = new ButtonGroup();
			radioGroup.add(getJrbSingleFile());
			radioGroup.add(getJrbDirectory());
			getJrbSingleFile().setSelected(true);
		}
		return jpSample;
	}

	public String[] getPasswords()
	{
		// Create and show the password dialog
		Frame parentFrame = (Frame)SwingUtilities.windowForComponent(this);
		return PasswordDialog.showAndGetPassword(null, parentFrame);
	}

	private JRadioButton getJrbSingleFile()
	{
		if (jrbSingleFile == null)
		{
			jrbSingleFile = new JRadioButton("Single File");
			jrbSingleFile.setBounds((int)(120 * SampleUtil.getDPIScalingMultiplier()), (int)(22 * SampleUtil.getDPIScalingMultiplier()), (int)(100 * SampleUtil.getDPIScalingMultiplier()), (int)(26 * SampleUtil.getDPIScalingMultiplier()));
			jrbSingleFile.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jrbSingleFile.getFont().getSize(), 11)));
			jrbSingleFile.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					clearFields();
				}
			});
		}
		return jrbSingleFile;
	}

	private JRadioButton getJrbDirectory()
	{
		if (jrbDirectory == null)
		{
			jrbDirectory = new JRadioButton("Directory");
			jrbDirectory.setBounds((int)(220 * SampleUtil.getDPIScalingMultiplier()), (int)(22 * SampleUtil.getDPIScalingMultiplier()), (int)(100 * SampleUtil.getDPIScalingMultiplier()), (int)(26 * SampleUtil.getDPIScalingMultiplier()));
			jrbDirectory.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jrbDirectory.getFont().getSize(), 11)));
			jrbDirectory.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					clearFields();
				}
			});
		}
		return jrbDirectory;
	}

	private void clearFields()
	{
		getJtfFileName().setText("");
	}

	private JTextField getJtfFileName()
	{
		if (jtfFileName == null)
		{
			jtfFileName = new JTextField();
			jtfFileName.setBounds((int)(120 * SampleUtil.getDPIScalingMultiplier()), (int)(52 * SampleUtil.getDPIScalingMultiplier()), (int)(401 * SampleUtil.getDPIScalingMultiplier()), (int)(22 * SampleUtil.getDPIScalingMultiplier()));
		}
		return jtfFileName;
	}

	private JButton getJbOpen()
	{
		if (jbOpen == null)
		{
			jbOpen = new JButton("Open File");
			jbOpen.setBounds((int)(12 * SampleUtil.getDPIScalingMultiplier()), (int)(50 * SampleUtil.getDPIScalingMultiplier()), (int)(100 * SampleUtil.getDPIScalingMultiplier()), (int)(25 * SampleUtil.getDPIScalingMultiplier()));
			jbOpen.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jbOpen.getFont().getSize(), 11)));
			jbOpen.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					// Open pdf file
					File file = getFile();
					if (file == null)
					{
						return;
					}
					open(file);
				}
			});
		}
		return jbOpen;
	}

	protected File getFile()
	{
		// Get first file name
		JFileChooser fileChooser = new JFileChooser();

		if (getJrbDirectory().isSelected())
		{
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		}

		// Initialize directory
		if (m_LastFileDir != null)
		{
			fileChooser.setCurrentDirectory(m_LastFileDir);
		}

		// Look for PDF files
		fileChooser.setFileFilter(new ExtFileFilter(".pdf"));
		fileChooser.addChoosableFileFilter(new ExtFileFilter(new String[] { ".tif", ".jpg", ".png" }));

		// Show the open dialog
		if (fileChooser.showOpenDialog(this) == JFileChooser.CANCEL_OPTION)
		{
			return null;
		}

		// Save last directory location
		File chosenFile = fileChooser.getSelectedFile();
		return chosenFile;
	}

	public void open(File file)
	{
		// keep file path for next time
		if (file != null)
		{
			m_LastFileDir = file.getParentFile();
		}

		// set file name in text field
		getJtfFileName().setText(file.getAbsolutePath());
	}

	private JButton getJbExportJPEG()
	{
		if (jbExportJPEGs == null)
		{
			jbExportJPEGs = new JButton("Export JPGs");
			jbExportJPEGs.setBounds((int)(12 * SampleUtil.getDPIScalingMultiplier()), (int)(85 * SampleUtil.getDPIScalingMultiplier()), (int)(100 * SampleUtil.getDPIScalingMultiplier()), (int)(25 * SampleUtil.getDPIScalingMultiplier()));
			jbExportJPEGs.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jbExportJPEGs.getFont().getSize(), 11)));
			jbExportJPEGs.setMargin(new java.awt.Insets(0, 0, 0, 0));
			jbExportJPEGs.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					exportImages(JPG_EXT, null);
				}
			});
		}
		return jbExportJPEGs;
	}

	private boolean isAcceptedImageFile(String fileName)
	{
		return fileName.toLowerCase().endsWith(".tif") || fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith("png");
	}

	/**
	 * Creates a PDF file from an image file. The method allows the user to choose JPEG, TIFF or PNG image files. If the file chosen is a TIFF file
	 * and it has multiple images, jPDFImages will create a new page for each image.
	 * 
	 */
	private void createPDFFromImage()
	{
		File selectedFile = new File(getJtfFileName().getText());

		if (getJtfFileName().getText().isEmpty() || (getJrbSingleFile().isSelected() && !isAcceptedImageFile(getJtfFileName().getText())) || !selectedFile.exists()
				|| (getJrbDirectory().isSelected() && !selectedFile.isDirectory()))
		{
			JOptionPane.showMessageDialog(null, "No valid document or directory open");
			return;
		}

		final File[] fileList = getJrbDirectory().isSelected() ? selectedFile.listFiles() : new File[] { selectedFile };

		final File exportsDir = SampleUtil.chooseDir(fileList[0].getParent(), PDFImagesSample.this, true);
		if (exportsDir == null)
		{
			return;
		}

		Thread processThread = new Thread() {
			public void run()
			{
				final ProgressDialog progressDialog = ProgressDialog.getInstance((Frame)SwingUtilities.windowForComponent(PDFImagesSample.this), "Extract Pages As Images");
				progressDialog.setVisible(true);

				int succeeded = 0;
				int failed = 0;
				Vector errors = new Vector();

				for (int i = 0; i < fileList.length && progressDialog.shouldContinue(); i++)
				{
					String name = fileList[i].getAbsolutePath();
					if (name.toLowerCase().endsWith("tif") || name.toLowerCase().endsWith("jpg") || name.toLowerCase().endsWith("png"))
					{
						try
						{
							PDFImages pdfDoc = new PDFImages();
		
							progressDialog.setJlFunctionName("Process Image: " + fileList[i].getName());
	
							// Decide how to add the image based on the extension
							if (name.toLowerCase().endsWith("tif"))
							{
								pdfDoc.appendTIFFAsPages(name);
							}
							else if (name.toLowerCase().endsWith("jpg"))
							{
								pdfDoc.appendJPEGAsPage(name);
							}
							else if (name.toLowerCase().endsWith("png"))
							{
								pdfDoc.appendPNGAsPage(name);
							}
							else
							{
								throw new PDFException("Unrecognized extension: " + name);
							}
	
							succeeded++;

							File outFile = new File(exportsDir, fileList[i].getName().substring(0, fileList[i].getName().lastIndexOf(".")) + "_" + ".pdf");
							int prevOverwrite = 1;
							while (outFile.exists())
							{
								outFile = new File(exportsDir, fileList[i].getName().substring(0, fileList[i].getName().lastIndexOf(".")) + "_" + prevOverwrite + ".pdf");
								prevOverwrite++;
							}

							pdfDoc.saveDocument(new FileOutputStream(outFile));
						}
						catch (Throwable e)
						{
							errors.add(name);
							errors.add(e.getMessage());
							failed++;
						}
					}
				}

				progressDialog.dispose();
				
				errors.add(new Integer(succeeded));

				if (succeeded + failed > 0)
				{
					ErrorDialogController errorController = new ErrorDialogController();
					errorController.showDialog(errors, exportsDir.getAbsolutePath());
				}
			}
		};
		processThread.start();
	}

	private static DecimalFormat getZeroPaddingFormat(int pageCount)
	{
		int padding = Integer.toString(pageCount).length();
		char[] zeros = new char[padding];
		for (int count = 0; count < padding; ++count)
		{
			zeros[count] = '0';
		}
		return new DecimalFormat(new String(zeros));
	}

	private void exportImages(final String ext, final String tiffCompression)
	{
		File selectedFile = new File(getJtfFileName().getText());

		if (getJtfFileName().getText().isEmpty() || (getJrbSingleFile().isSelected() && !getJtfFileName().getText().toLowerCase().endsWith(".pdf")) || !selectedFile.exists()
				|| (getJrbDirectory().isSelected() && !selectedFile.isDirectory()))
		{
			JOptionPane.showMessageDialog(null, "No valid document or directory open");
			return;
		}

		final File[] fileList = getJrbDirectory().isSelected() ? selectedFile.listFiles() : new File[] { selectedFile };

		// Make sure there is an exports folder
		final File exportsDir = SampleUtil.chooseDir(fileList[0].getParent(), PDFImagesSample.this, true);
		if (exportsDir == null)
		{
			return;
		}

		Thread processThread = new Thread() {
			public void run()
			{
				final ProgressDialog progressDialog = ProgressDialog.getInstance((Frame)SwingUtilities.windowForComponent(PDFImagesSample.this), "Extract Pages As Images");
				progressDialog.setVisible(true);

				int succeeded = 0;
				int failed = 0;
				Vector errors = new Vector();

				for (int i = 0; i < fileList.length && progressDialog.shouldContinue(); i++)
				{
					String name = fileList[i].getAbsolutePath();
					if (name.toLowerCase().endsWith(".pdf"))
					{
						try
						{
							progressDialog.setJlFunctionName("Export: " + fileList[i].getName());

							// Load the document
							PDFImages images = new PDFImages(name, PDFImagesSample.this);

							if (ext == TIF_EXT)
							{
								File outFile = new File(exportsDir, fileList[i].getName().substring(0, fileList[i].getName().lastIndexOf(".")) + ext);
								int prevOverwrite = 1;
								while (outFile.exists())
								{
									outFile = new File(exportsDir, fileList[i].getName().substring(0, fileList[i].getName().lastIndexOf(".")) + "_" + prevOverwrite + ext);
									i++;
								}
								images.saveDocumentAsTIFF(outFile.getAbsolutePath(), EXPORT_DPI, tiffCompression);
							}
							else
							{
								DecimalFormat df = getZeroPaddingFormat(images.getPageCount());

								// get document pages
								for (int count = 0; count < images.getPageCount() && progressDialog.shouldContinue(); ++count)
								{
									progressDialog.updateProgress("Page " + (count + 1) + " of " + images.getPageCount());

									File outFile = new File(exportsDir, fileList[i].getName().substring(0, fileList[i].getName().lastIndexOf(".")) + "_" + df.format(count + 1) + ext);
									int prevOverwrite = 1;
									while (outFile.exists())
									{
										outFile = new File(exportsDir, fileList[i].getName().substring(0, fileList[i].getName().lastIndexOf(".")) + "_" + df.format(count + 1) + "_" + prevOverwrite + ext);
										prevOverwrite++;
									}

									FileOutputStream outStream = new FileOutputStream(outFile);

									if (ext == JPG_EXT)
									{
										images.savePageAsJPEG(count, outStream, EXPORT_DPI, 0.80f);
									}
									else if (ext == PNG_EXT)
									{
										images.savePageAsPNG(count, outFile.getAbsolutePath(), EXPORT_DPI);
									}
									outStream.close();
								}

								succeeded++;
							}
						}
						catch (Throwable e)
						{
							errors.add(name);
							errors.add(e.getMessage());
							failed++;
						}
					}
				}

				progressDialog.dispose();

				errors.add(new Integer(succeeded));

				if (succeeded + failed > 0)
				{
					ErrorDialogController errorController = new ErrorDialogController();
					errorController.showDialog(errors, exportsDir.getAbsolutePath());
				}
			}
		};
		processThread.start();
	}

	/**
	 * Set the look and feel.
	 */
	public static void setLookAndFeel()
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

	private JButton getjbExportTIFF()
	{
		if (jbExportTIFF == null)
		{
			jbExportTIFF = new JButton("Export TIFF");
			jbExportTIFF.setBounds((int)(12 * SampleUtil.getDPIScalingMultiplier()), (int)(120 * SampleUtil.getDPIScalingMultiplier()), (int)(100 * SampleUtil.getDPIScalingMultiplier()), (int)(25 * SampleUtil.getDPIScalingMultiplier()));
			jbExportTIFF.setMargin(new Insets(0, 0, 0, 0));
			jbExportTIFF.setFont(new Font("Dialog", Font.PLAIN, (int)SampleUtil.getScaledFont(jbExportTIFF.getFont().getSize(), 11)));
			jbExportTIFF.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					exportImages(TIF_EXT, TIFFOptions.TIFF_PACKBITS);
				}
			});
		}
		return jbExportTIFF;
	}

	private JButton getjbExportTIFFBW()
	{
		if (jbExportTIFFBW == null)
		{
			jbExportTIFFBW = new JButton("Export B/W TIFF");
			jbExportTIFFBW.setBounds((int)(12 * SampleUtil.getDPIScalingMultiplier()), (int)(155 * SampleUtil.getDPIScalingMultiplier()), (int)(100 * SampleUtil.getDPIScalingMultiplier()), (int)(25 * SampleUtil.getDPIScalingMultiplier()));
			jbExportTIFFBW.setMargin(new Insets(0, 0, 0, 0));
			jbExportTIFFBW.setFont(new Font("Dialog", Font.PLAIN, (int)SampleUtil.getScaledFont(jbExportTIFFBW.getFont().getSize(), 11)));
			jbExportTIFFBW.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					exportImages(TIF_EXT, TIFFOptions.TIFF_FAX_GROUP4);
				}
			});
		}
		return jbExportTIFFBW;
	}

	private JButton getjbExportPNGs()
	{
		if (jbExportPNGs == null)
		{
			jbExportPNGs = new JButton("Export PNGs");
			jbExportPNGs.setBounds((int)(12 * SampleUtil.getDPIScalingMultiplier()), (int)(190 * SampleUtil.getDPIScalingMultiplier()), (int)(100 * SampleUtil.getDPIScalingMultiplier()), (int)(25 * SampleUtil.getDPIScalingMultiplier()));
			jbExportPNGs.setMargin(new Insets(0, 0, 0, 0));
			jbExportPNGs.setFont(new Font("Dialog", Font.PLAIN, (int)SampleUtil.getScaledFont(jbExportPNGs.getFont().getSize(), 11)));
			jbExportPNGs.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					exportImages(PNG_EXT, null);
				}
			});
		}
		return jbExportPNGs;
	}

	private JButton getJbPDFFromImage()
	{
		if (jbPDFFromImage == null)
		{
			jbPDFFromImage = new JButton("PDF From Image");
			jbPDFFromImage.setBounds((int)(12 * SampleUtil.getDPIScalingMultiplier()), (int)(225 * SampleUtil.getDPIScalingMultiplier()), (int)(100 * SampleUtil.getDPIScalingMultiplier()), (int)(25 * SampleUtil.getDPIScalingMultiplier()));
			jbPDFFromImage.setMargin(new Insets(0, 0, 0, 0));
			jbPDFFromImage.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jbPDFFromImage.getFont().getSize(), 11)));
			jbPDFFromImage.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					createPDFFromImage();
				}
			});
		}
		return jbPDFFromImage;
	}

	public class ErrorsDialog extends JDialog
	{
		private JPanel jpErrors = null;
		private JScrollPane jspErrors = null;
		private JTextArea jtaErrors = null;
		private JButton jbOK = null;

		public ErrorsDialog(JFrame parent)
		{
			super(parent);
			initialize();
		}

		public JButton getJbOk()
		{
			if (jbOK == null)
			{
				jbOK = new JButton("Ok");
				jbOK.setName("ButtonOK");
			}
			return jbOK;
		}

		public JTextArea getJtaErrors()
		{
			if (jtaErrors == null)
			{
				jtaErrors = new JTextArea();
				jtaErrors.setEditable(false);
			}
			return jtaErrors;
		}

		private JPanel getJpErrors()
		{
			if (jpErrors == null)
			{
				jpErrors = new JPanel();
				jpErrors.add(getJspErrors());
				jpErrors.add(getJbOk());
			}
			return jpErrors;
		}

		private JScrollPane getJspErrors()
		{
			if (jspErrors == null)
			{
				jspErrors = new JScrollPane();
				jspErrors.setViewportView(getJtaErrors());
			}
			return jspErrors;
		}

		private void initialize()
		{
			this.setModal(true);
			this.getRootPane().setDefaultButton(getJbOk());
			setContentPane(getJpErrors());
			this.pack();
		}
	}

	public class ErrorDialogController implements ActionListener
	{
		private String CMD_OK = "OK";

		private ErrorsDialog m_Dialog;

		public void actionPerformed(ActionEvent e)
		{
			if (e.getActionCommand() == CMD_OK)
			{
				m_Dialog.dispose();
			}
		}

		protected void showDialog(Vector info, String dir)
		{
			m_Dialog = new ErrorsDialog(null);

			// action commands
			m_Dialog.getJbOk().addActionListener(this);
			m_Dialog.getJbOk().setActionCommand(CMD_OK);

			// Prep the info for the dialog
			//
			int succeeded = toInteger(info.remove(info.size() - 1));
			String message = "";
			if (succeeded > 0)
			{
				message += "There were " + succeeded + " file(s) converted successfully and saved in " + dir + ".  ";
			}
			if (info.size() > 0)
			{
				message += info.size() / 2 + " file(s) failed to convert";
				message += "\nFailed Documents:";
			}

			for (int i = 0; i < info.size(); i++)
			{
				String name = info.get(i).toString();
				i++;
				String error = info.get(i).toString();
				message += "\n" + name + "- " + error;
			}
			m_Dialog.getJtaErrors().setText(message);

			m_Dialog.pack();

			m_Dialog.setLocationRelativeTo(null);

			// Show the dialog
			m_Dialog.setVisible(true);
		}

		private int toInteger(Object obj)
		{
			if (obj != null)
			{
				if (obj instanceof Number)
				{
					return ((Number)obj).intValue();
				}
				try
				{
					return Double.valueOf(obj.toString()).intValue();
				}
				catch (Exception e)
				{
					return 0;
				}
			}

			return 0;
		}
	}
} // @jve:decl-index=0:visual-constraint="10,10"
