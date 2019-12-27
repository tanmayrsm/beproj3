/*
 * Created on May 31, 2005
 *
 */
package jPDFSecureSamples;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTMLDocument;

import com.qoppa.pdf.IPassword;
import com.qoppa.pdf.PDFException;
import com.qoppa.pdf.PasswordDialog;
import com.qoppa.pdf.SigningInformation;
import com.qoppa.pdf.form.SignatureField;
import com.qoppa.pdf.permissions.PasswordPermissions;
import com.qoppa.pdfSecure.PDFSecure;

/**
 * @author Qoppa Software
 *
 */
public class PDFSecurePanel extends JPanel implements KeyListener, IPassword
{
	// all visual components
	private JScrollPane jspRTFDoc = null;
	private JEditorPane jepRTFPane = null;
	private JPanel jpSamplePane = null;
	private File m_LastFileDir;
	private JButton jbOpen = null;
	private JPanel jpPasswords = null;
	private JLabel jlEncrypted = null;
	private JLabel jlHasOpenPassword = null;
	private JLabel jlHasPermissionsPassword = null;
	private JTextField jtfFileName = null;
	private JButton jbClear = null;
	private JButton jbChangeSecurity = null;
	private JButton jbSign = null;
	private JLabel jlJARLocation = null; // @jve:decl-index=0:visual-constraint="765,235"
	private JLabel jlSampleLocation = null; // @jve:decl-index=0:visual-constraint="791,262"
	private JButton jbViewAPI = null;
	private JPanel jpAPI = null;
	private JPanel jpSecurity = null;
	private JPanel jpSign = null; // @jve:decl-index=0:
	private JPanel jpTitle = null;
	private JLabel jlTitle = null;
	private JButton jbPermissions = null;
	private JRadioButton jrbSampleSign = null;
	private JRadioButton jrbCustomSign = null;

	// PDFSecure object
	private PDFSecure m_PDFSecure;

	private final static String OS_WINDOWS_START = "windows";
	private final static String OS_MAC = "mac";
	private final static String JAR_FILE_NAME = "jPDFSecure.jar";
	private final static String API_INDEX_FILENAME = "javadoc/index.html";
	private final static String SAMPLE_DIR_NAME = "jPDFSecureSamples";

	/**
	 * This method initializes
	 * 
	 */
	public PDFSecurePanel()
	{
		super();
		initialize();
		m_PDFSecure = null;
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize()
	{
		// Layout
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//		this.setSize(new Dimension(703, 960));

		// Title panel
		this.add(getjpTitle());
		this.add(getSpacer());

		// add rtf document scroll pane
		this.add(getjspRTFDoc());
		this.add(getSpacer());

		// add api panel
		this.add(getjpAPI());
		this.add(getSpacer());

		// add sample panel
		this.add(getjpSamplePane());

		// add listeners
		getJtfFileName().addKeyListener(this);

		InputStream stream = getClass().getResourceAsStream("/jPDFSecure.html");
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

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getjspRTFDoc()
	{
		if (jspRTFDoc == null)
		{
			jspRTFDoc = new JScrollPane();
			jspRTFDoc.setViewportView(getJepRTFPane());
			jspRTFDoc.setAlignmentX(LEFT_ALIGNMENT);
		}
		return jspRTFDoc;
	}

	/**
	 * This method initializes jepRTFPane
	 * 
	 * @return javax.swing.JEditorPane
	 */
	private JEditorPane getJepRTFPane()
	{
		if (jepRTFPane == null)
		{
			jepRTFPane = new JEditorPane() {
				public void paint(Graphics g)
				{
					Graphics2D g2d = (Graphics2D) g;
					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

					super.paint(g);
				}
			};
			jepRTFPane.setEditable(false);
		}
		return jepRTFPane;
	}

	/**
	 * This method initializes jpSamplePane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getjpSamplePane()
	{
		if (jpSamplePane == null)
		{
			jpSamplePane = getJPanel(BoxLayout.Y_AXIS, BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Sample Processing",
					TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN, (int)SampleUtil.getScaledFont(new JLabel().getFont().getSize(), 12)),
					Color.black));

			JPanel jpFile = getJPanel(BoxLayout.X_AXIS, null);
			JLabel jlFileName = getJLabel("File Name:");
			jpFile.add(jlFileName);
			jpFile.add(getStrut());
			jpFile.add(getJtfFileName());
			jpFile.add(Box.createHorizontalGlue());
			jpFile.add(getStrut());
			jpFile.add(getJbOpen());
			jpSamplePane.add(jpFile);
			jpSamplePane.add(getSpacer());
			
			JPanel jpAuthor = getJPanel(BoxLayout.X_AXIS, null);
			jpAuthor.add(Box.createHorizontalGlue());
			JLabel jlWatermark = getJLabel("Watermark added in Demo Mode");
			jlWatermark.setForeground(new Color(200, 0, 0));
			jlWatermark.setHorizontalAlignment(SwingConstants.RIGHT);
			jpAuthor.add(jlWatermark);
			jpSamplePane.add(jpAuthor);
			
			jpSamplePane.add(getjpSecurity());
			jpSamplePane.add(getjpSign());
			
		}
		return jpSamplePane;
	}

	/**
	 * This method initializes jpSecurity
	 * 
	 * @return javax.swing.JPanel
	 */
	public JPanel getjpSecurity()
	{
		if (jpSecurity == null)
		{
			jpSecurity = getJPanel(BoxLayout.X_AXIS, BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Security",
					TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN, (int)SampleUtil.getScaledFont(new JLabel().getFont().getSize(), 12)), Color.black));

			JPanel jpLeft = getJPanel(BoxLayout.Y_AXIS, null);
			
			JPanel jpEncrypted = getJPanel(BoxLayout.X_AXIS, null);
			jpEncrypted.add(getJLabel("Document is Encrypted:"));
			jpEncrypted.add(getStrut());
			jpEncrypted.add(getjlEncrypted());
			
			jpLeft.add(jpEncrypted);
			jpLeft.add(getSpacer());
			jpLeft.add(getjpPasswords());
			jpSecurity.add(jpLeft);
			jpSecurity.add(Box.createHorizontalGlue());
			
			JPanel jpButtons = getJPanel(BoxLayout.Y_AXIS, null);
			jpButtons.add(getJbPermissions());
			jpButtons.add(getSpacer());
			jpButtons.add(getjbClear());
			jpButtons.add(getSpacer());
			jpButtons.add(getjbChangeSecurity());
			jpSecurity.add(jpButtons);
		}
		return jpSecurity;
	}

	/**
	 * This method initializes jpSecurity
	 * 
	 * @return javax.swing.JPanel
	 */
	public JPanel getjpSign()
	{
		if (jpSign == null)
		{
			jpSign = getJPanel(BoxLayout.Y_AXIS, BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Sign",
					TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN, (int)SampleUtil.getScaledFont(new JLabel().getFont().getSize(), 12)),
					Color.black));

			JPanel jpSignPDF = getJPanel(BoxLayout.X_AXIS, null);

			jpSignPDF.add(getJrbSampleSign());
			jpSignPDF.add(getJrbCustomSign());
			jpSignPDF.add(Box.createHorizontalGlue());
			jpSignPDF.add(getjbSign());
			jpSign.add(jpSignPDF);
			jpSign.add(getSpacer());

			ButtonGroup bg = new ButtonGroup();
			bg.add(getJrbSampleSign());
			bg.add(getJrbCustomSign());
			
			JLabel jlwarning = new JLabel("<html> New signatures will show as unknown because sample certificate is not trusted. "
					+ "This is resolved when using a trusted certificate. <br>Previous signatures will show that subsquent changes"
					+ " were made to the document due to the demo version watermark.</html>");
			jlwarning.setFont(new Font("Dialog", Font.ITALIC, (int)SampleUtil.getScaledFont(jlwarning.getFont().getSize(), 10)));
			jpSign.add(jlwarning);
			jpSign.setAlignmentX(LEFT_ALIGNMENT);
		}
		return jpSign;
	}

	/**
	 * This method displays a file chooser dialog and gets a file name.
	 * 
	 * @return java.io.File
	 */
	protected File getFile(boolean open)
	{
		// Get first file name
		JFileChooser fileChooser = new JFileChooser();

		// Initialize directory
		if (m_LastFileDir != null)
		{
			fileChooser.setCurrentDirectory(m_LastFileDir);
		}

		// Look for PDF files
		fileChooser.setFileFilter(new PDFFileFilter());

		// Show the open dialog
		if (open)
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
	 * This method displays a dialog to type in a password and returns the
	 * password.
	 * 
	 * @return byte[]
	 */
	public String[] getPasswords()
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
	private JButton getJbOpen()
	{
		if (jbOpen == null)
		{
			jbOpen = getJButton("Open");
			jbOpen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					// Open pdf file
					File file = getFile(true);
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

	/**
	 * This method saves the pdf document to the file
	 * 
	 */
	public void save()
	{
		try
		{
			if (m_PDFSecure == null)
			{
				JOptionPane.showMessageDialog(null, "No document open");
				return;
			}

			// get a file name
			File file = getFile(false);
			if (file != null)
			{
				if (file.exists())
				{
					int rc = JOptionPane.showConfirmDialog(this, "Overwrite " + file.getName() + "?", "Overwrite", JOptionPane.YES_NO_OPTION);
					if (rc != JOptionPane.YES_OPTION)
					{
						return;
					}
				}

				// save the document
				m_PDFSecure.saveDocument(file.getAbsolutePath());
				getJtfFileName().setText(file.getAbsolutePath());
			}

		}
		catch (PDFException pdfException)
		{
			JOptionPane.showMessageDialog(null, "Error saving file " + pdfException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch (IOException ioException)
		{
			JOptionPane.showMessageDialog(null, "Error saving file " + ioException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void signPDF()
	{
		try
		{
			if (m_PDFSecure == null)
			{
				JOptionPane.showMessageDialog(null, "Please open a document first.");
				return;
			}

			if (getJrbSampleSign().isSelected())
			{
				// Load the keystore that contains the digital id to use in signing
				InputStream pkcs12Stream = this.getClass().getResourceAsStream("/keystore.pfx");
				KeyStore store = KeyStore.getInstance("PKCS12");
				store.load(pkcs12Stream, "store_pwd".toCharArray());
				pkcs12Stream.close();
	
				// Create signing information
				SigningInformation signInfo = new SigningInformation(store, "key_alias", "store_pwd");
				signInfo.setReason("Approve");
				signInfo.setLocation("Atlanta, GA 30307");
	
				// Create signature field on the first page
				Rectangle2D signBounds = new Rectangle2D.Double(36, 36, 144, 48);
				SignatureField signField = m_PDFSecure.addSignatureField(0, "signature", signBounds);
	
				// Apply digital signature
				m_PDFSecure.signDocument(signField, signInfo);

				save();
			}
			else
			{
				ChooseSignatureController csc = new ChooseSignatureController(this, m_PDFSecure);
				csc.showDialog();
			}
		}
		catch (Throwable t)
		{
			JOptionPane.showMessageDialog(this, "Error signing document " + t.getMessage());
		}
	}

	/**
	 * This method opens a pdf document and displays security information on the
	 * applet's components.
	 * 
	 */
	public void open(File file)
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
			m_PDFSecure = new PDFSecure(file.getAbsolutePath(), this);

			// display security settings for this pdf
			displaySecurityInfo();
		}
		catch (PDFException pdfE)
		{
			JOptionPane.showMessageDialog(this, pdfE.getMessage());
		}
	}

	/**
	 * This method clears all security info and remove any encryption on the pdf
	 * file.
	 * 
	 */
	public void clearSecurity()
	{
		if (m_PDFSecure == null)
		{
			JOptionPane.showMessageDialog(null, "Please open a document first");
			return;
		}

		// get current permissions password if any
		String currentPermPwd = null;
		if (m_PDFSecure.hasPermissionsPassword())
		{
			String[] passwords = getPasswords();
			if (passwords == null)
			{
				JOptionPane.showMessageDialog(this, "Document is encrypted and password is required");
				return;
			}

			currentPermPwd = passwords[0];
		}

		try
		{
			m_PDFSecure.clearPasswordPermissions(currentPermPwd);
			displaySecurityInfo();
			save();
		}
		catch (PDFException pdfE)
		{
			JOptionPane.showMessageDialog(this, pdfE.getMessage());
		}
	}

	/**
	 * This method allows to change security settings on the pdf document. It
	 * will open the encryption dialog where user can enter passwords and set
	 * permissions for the file. It will then save the settings to the PDFSecure
	 * object.
	 * 
	 */
	public void changeSecurity()
	{
		if (m_PDFSecure == null)
		{
			JOptionPane.showMessageDialog(null, "Please open a document first");
			return;
		}

		// get current permissions password if any
		String currentPermPwd = null;
		if (m_PDFSecure.hasPermissionsPassword())
		{
			String[] passwords = getPasswords();
			if (passwords == null)
			{
				JOptionPane.showMessageDialog(this, "Document is encrypted and password is required");
				return;
			}

			currentPermPwd = passwords[0];
		}

		// open the encryption dialog
		// that will return a SecurityInfo object that contains all security
		// settings
		EncryptionDialogController encryptionDiagController = new EncryptionDialogController();
		Frame parentFrame = (Frame) SwingUtilities.windowForComponent(this);
		SecurityInfo securityInfo = encryptionDiagController.showDialog(parentFrame, m_PDFSecure.getPDFPermissions().getPasswordPermissions(), getEncryptionString(m_PDFSecure.getEncryptionType()));

		if (securityInfo != null)
		{
			// save the security settings in the PDFSecure object
			try
			{
				m_PDFSecure.setPasswordPermissions(securityInfo.getPermissionsPassword(), securityInfo.getOpenPassword(), securityInfo.getPermissions(), currentPermPwd, securityInfo.getEncryption());
				displaySecurityInfo();
				save();
			}
			catch (PDFException pdfExc)
			{
				JOptionPane.showMessageDialog(null, "Error changing security " + pdfExc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private String getEncryptionString(int encryptType)
	{
	    if (encryptType == PasswordPermissions.ENCRYPTION_RC4_128)
	    {
			return EncryptionDialog.STR_RC4;
	    }
	    else if (encryptType == PasswordPermissions.ENCRYPTION_AES_128)
	    {
	    	return EncryptionDialog.STR_128AES;
	    }
	    else if (encryptType == PasswordPermissions.ENCRYPTION_AES_256)
	    {
	    	return EncryptionDialog.STR_256AES;
	    }
	    return null;
	}

	/**
	 * This method initializes jpPasswords
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getjpPasswords()
	{
		if (jpPasswords == null)
		{
			jpPasswords = getJPanel(BoxLayout.Y_AXIS, BorderFactory.createTitledBorder(null, "Passwords", TitledBorder.DEFAULT_JUSTIFICATION, 
					TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN, (int)SampleUtil.getScaledFont(new JLabel().getFont().getSize(), 11)), Color.black));
			
			JPanel jpOpen = getJPanel(BoxLayout.X_AXIS, null);
			jpOpen.add(getJLabel("Has Password to Open Document:"));
			jpOpen.add(getStrut());
			jpOpen.add(getjlHasOpenPassword());
			jpOpen.add(Box.createHorizontalGlue());
			jpPasswords.add(jpOpen);
			jpPasswords.add(getSpacer());
			
			JPanel jpChange = getJPanel(BoxLayout.X_AXIS, null);
			jpChange.add(getJLabel("Has Password to Change Security:"));
			jpChange.add(getStrut());
			jpChange.add(getjlHasPermissionsPassword());
			jpChange.add(Box.createHorizontalGlue());
			jpPasswords.add(jpChange);
		}
		return jpPasswords;
	}

	/**
	 * This method initializes jlHasOpenPassword
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getjlHasOpenPassword()
	{
		if (jlHasOpenPassword == null)
		{
			jlHasOpenPassword = getJLabel("N/A");
			jlHasOpenPassword.setEnabled(true);
		}
		return jlHasOpenPassword;
	}

	/**
	 * This method initializes jlHasPermissionsPassword
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getjlHasPermissionsPassword()
	{
		if (jlHasPermissionsPassword == null)
		{
			jlHasPermissionsPassword = getJLabel("N/A");
		}
		return jlHasPermissionsPassword;
	}

	/**
	 * This method initializes jlEncrypted
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getjlEncrypted()
	{
		if (jlEncrypted == null)
		{
			jlEncrypted = getJLabel("N/A");
		}
		return jlEncrypted;
	}

	/**
	 * This method initializes jtfFileName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJtfFileName()
	{
		if (jtfFileName == null)
		{
			jtfFileName = new JTextField();
			jtfFileName.setMinimumSize(new Dimension((int)(360 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier())));
			jtfFileName.setPreferredSize(new Dimension((int)(360 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier())));
			jtfFileName.setMaximumSize(new Dimension((int)(360 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier())));
		}
		return jtfFileName;
	}

	/**
	 * This method initializes jbClear
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getjbClear()
	{
		if (jbClear == null)
		{
			jbClear = getJButton("Clear Security");
			jbClear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					clearSecurity();
				}
			});
		}
		return jbClear;
	}

	/**
	 * This method initializes jbChangeSecurity
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getjbChangeSecurity()
	{
		if (jbChangeSecurity == null)
		{
			jbChangeSecurity = getJButton("Change Security");
			jbChangeSecurity.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					changeSecurity();
				}
			});
		}
		return jbChangeSecurity;
	}

	/**
	 * This method initializes jbSign
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getjbSign()
	{
		if (jbSign == null)
		{
			jbSign = getJButton("Sign PDF");
			jbSign.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					signPDF();
				}
			});
		}
		return jbSign;
	}

	public void keyReleased(KeyEvent keyEvent)
	{
		// nothing to do
	}

	public void keyPressed(KeyEvent keyEvent)
	{
		// nothing to do
	}

	public void keyTyped(KeyEvent keyEvent)
	{
		if (keyEvent.getKeyChar() == KeyEvent.VK_ENTER)
		{
			open(new File(getJtfFileName().getText()));
		}
	}

	/**
	 * This method displays security settings for the PDFSecure object on the
	 * applet's components.
	 * 
	 */
	public void displaySecurityInfo()
	{
		if (m_PDFSecure != null)
		{
			// Encryption
			getjlEncrypted().setText("No");
			if (m_PDFSecure.isEncrypted())
			{
				String encryptType = getEncryptionString(m_PDFSecure.getEncryptionType());
				if (encryptType != null)
				{
					getjlEncrypted().setText(encryptType);
				}
				else
				{
					getjlEncrypted().setText("Unknown");
				}
			}

			// Open Document password
			getjlHasOpenPassword().setText("No");
			if (m_PDFSecure.hasOpenPassword())
			{
				getjlHasOpenPassword().setText("Yes");
			}

			// Permissions password
			getjlHasPermissionsPassword().setText("No");
			if (m_PDFSecure.hasPermissionsPassword())
			{
				getjlHasPermissionsPassword().setText("Yes");
			}
		}
	}

	/**
	 * This method initializes jbViewAPI
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJbViewAPI()
	{
		if (jbViewAPI == null)
		{
			jbViewAPI = getJButton("View API");
			jbViewAPI.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					viewAPI();
				}
			});
		}
		return jbViewAPI;
	}

	private JLabel getjlJARLocation()
	{
		if (jlJARLocation == null)
		{
			// Initialize help message
			File jarFile = new File("lib/" + JAR_FILE_NAME);
			jlJARLocation = getJLabel(JAR_FILE_NAME + " is located at " + jarFile.getAbsolutePath() + ".");
			jlJARLocation.setToolTipText(jarFile.getAbsolutePath());
		}
		return jlJARLocation;
	}

	private JLabel getjlSampleLocation()
	{
		if (jlSampleLocation == null)
		{
			File sampleDir = new File(SAMPLE_DIR_NAME);
			jlSampleLocation = getJLabel("Sample code is located at " + sampleDir.getAbsolutePath() + ".");
			jlSampleLocation.setToolTipText(sampleDir.getAbsolutePath());
		}
		return jlSampleLocation;
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

	/**
	 * Insert the method's description here. Creation date: (11/10/2003 9:16:17
	 * PM)
	 * 
	 * @return boolean
	 */
	private boolean isSystemWindows()
	{
		// Check the OS
		String osName = System.getProperty("os.name");
		osName = osName.toLowerCase();
		return osName.startsWith(OS_WINDOWS_START);
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

	/**
	 * This method initializes jpAPI
	 * 
	 * @return javax.swing.JPanel
	 */
	public JPanel getjpAPI()
	{
		if (jpAPI == null)
		{
			jpAPI = getJPanel(BoxLayout.Y_AXIS, BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			
			jpAPI.add(getjlJARLocation());
			jpAPI.add(getSpacer());
			jpAPI.add(getjlSampleLocation());
			jpAPI.add(getSpacer());
			
			JPanel jpAPIButton = getJPanel(BoxLayout.X_AXIS, null);
			jpAPIButton.add(getJbViewAPI());
			jpAPIButton.add(Box.createHorizontalGlue());
			jpAPI.add(jpAPIButton);
		}
		return jpAPI;
	}

	public JPanel getjpTitle()
	{
		if (jpTitle == null)
		{
			jpTitle = getJPanel(BoxLayout.X_AXIS, BorderFactory.createEtchedBorder());
			jpTitle.add(Box.createHorizontalGlue());
			jpTitle.add(getjlTitle());
			jpTitle.add(Box.createHorizontalGlue());
		}
		return jpTitle;
	}

	public JLabel getjlTitle()
	{
		if (jlTitle == null)
		{
			jlTitle = new JLabel();
			jlTitle.setText(PDFSecure.getVersion());
			jlTitle.setFont(new Font("dialog", Font.BOLD, (int)SampleUtil.getScaledFont(jlTitle.getFont().getSize(), 14)));
		}
		return jlTitle;
	}

	public JButton getJbPermissions()
	{
		if (jbPermissions == null)
		{
			jbPermissions = getJButton("View Permissions");
			jbPermissions.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					viewPermissions();
				}

			});
		}
		return jbPermissions;
	}

	private void viewPermissions()
	{
		if (m_PDFSecure == null)
		{
			JOptionPane.showMessageDialog(null, "Please open a document first");
			return;
		}
		Frame parentFrame = (Frame) SwingUtilities.windowForComponent(this);

		PermissionsDialog pd = new PermissionsDialog(parentFrame, m_PDFSecure.getPDFPermissions());
		pd.setVisible(true);
	}

	public JRadioButton getJrbSampleSign()
	{
		if (jrbSampleSign == null)
		{
			jrbSampleSign = new JRadioButton("Sample Signature");
			jrbSampleSign.setFont(new Font("Dialog", Font.PLAIN, (int)SampleUtil.getScaledFont(jrbSampleSign.getFont().getSize(), 12)));
			jrbSampleSign.setSelected(true);
		}
		return jrbSampleSign;
	}

	public JRadioButton getJrbCustomSign()
	{
		if (jrbCustomSign == null)
		{
			jrbCustomSign = new JRadioButton("Custom Signature");
			jrbCustomSign.setFont(new Font("Dialog", Font.PLAIN, (int)SampleUtil.getScaledFont(jrbCustomSign.getFont().getSize(), 12)));
		}
		return jrbCustomSign;
	}

	public JLabel getJLabel(String text)
	{
		JLabel label = new JLabel(text);
		label.setFont(new Font("Dialog", Font.PLAIN, (int)SampleUtil.getScaledFont(label.getFont().getSize(), 12)));
		return label;
	}
	
	public JButton getJButton(String text)
	{
		JButton button = new JButton(text);
		button.setFont(new Font("Dialog", Font.PLAIN, (int)SampleUtil.getScaledFont(button.getFont().getSize(), 12)));
		// all 3 of these are needed to make the button widths match when layed out vertically
		button.setPreferredSize(new Dimension((int)(150 * SampleUtil.getDPIScalingMultiplier()), button.getPreferredSize().height));
		button.setMinimumSize(new Dimension((int)(150 * SampleUtil.getDPIScalingMultiplier()), button.getPreferredSize().height));
		button.setMaximumSize(new Dimension((int)(150 * SampleUtil.getDPIScalingMultiplier()), button.getPreferredSize().height));
		return button;
	}
	
	public JPanel getJPanel(int axis, Border outer)
	{
		JPanel panel = new JPanel();
		panel.setAlignmentX(LEFT_ALIGNMENT);
		panel.setLayout(new BoxLayout(panel, axis));
		Border empty = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		if (outer != null)
		{
			panel.setBorder(BorderFactory.createCompoundBorder(outer, empty));
		}
		return panel;
	}
	
	public Component getSpacer()
	{
		return Box.createRigidArea(new Dimension(5, 5));
	}
	
	public Component getStrut()
	{
		return Box.createHorizontalStrut(20);
	}

} // @jve:decl-index=0:visual-constraint="20,40"
