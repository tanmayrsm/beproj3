package jPDFSecureSamples;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.qoppa.pdf.PDFException;
import com.qoppa.pdf.SignatureAppearance;
import com.qoppa.pdf.SigningInformation;
import com.qoppa.pdf.TimestampServer;
import com.qoppa.pdf.form.SignatureField;
import com.qoppa.pdf.util.GenUtil;
import com.qoppa.pdfSecure.PDFSecure;

public class ChooseSignatureController implements ActionListener, ItemListener
{
	private Frame m_Parent;
	private ChooseSignatureDialog m_Dialog;
	private static final String CMD_SIGN = "cmdSign";
	private static final String CMD_BROWSE = "cmdBrowse";
	private static final String CMD_SIGNATURE_TYPE = "cmdSignatureType";
	private static final String CMD_ALIAS = "cmdAlias";
	private PDFSecure m_PDFSecure;
	private PDFSecurePanel m_pdfSecurePanel;
	
	public ChooseSignatureController(PDFSecurePanel parent, PDFSecure pdfSecure)
	{
		m_Parent = (Frame) SwingUtilities.windowForComponent(parent);
		m_pdfSecurePanel = parent;
		m_PDFSecure = pdfSecure;
	}
	
	public void showDialog()
	{
		m_Dialog = new ChooseSignatureDialog(m_Parent);
		m_Dialog.setTitle("Choose Signature");
		m_Dialog.setSize((int)(500 * SampleUtil.getDPIScalingMultiplier()), (int)(620 * SampleUtil.getDPIScalingMultiplier()));
		m_Dialog.setLocationRelativeTo(m_Parent);
		m_Dialog.setResizable(false);
		m_Dialog.setModal(true);
		
		m_Dialog.getJbSign().addActionListener(this);
		m_Dialog.getJbSign().setActionCommand(CMD_SIGN);
		m_Dialog.getJbBrowse().addActionListener(this);
		m_Dialog.getJbBrowse().setActionCommand(CMD_BROWSE);
		m_Dialog.getJchbUseTimeStampServer().addItemListener(this);
		m_Dialog.getJchbAuthentication().addItemListener(this);
		m_Dialog.getJrbCertifyingSignature().addActionListener(this);
		m_Dialog.getJrbCertifyingSignature().setActionCommand(CMD_SIGNATURE_TYPE);
		m_Dialog.getJrbStandardSignature().addActionListener(this);
		m_Dialog.getJrbStandardSignature().setActionCommand(CMD_SIGNATURE_TYPE);
		m_Dialog.getJbAlias().addActionListener(this);
		m_Dialog.getJbAlias().setActionCommand(CMD_ALIAS);
		
		// initialize the radio buttons
		m_Dialog.getJrbStandardSignature().doClick();
		
		// initialize appearance
		m_Dialog.getJchbCity().setSelected(true);
		m_Dialog.getJchbCommonName().setSelected(true);
		m_Dialog.getJchbCountry().setSelected(true);
		m_Dialog.getJchbDate().setSelected(true);
		m_Dialog.getJchbEmail().setSelected(true);
		m_Dialog.getJchbName().setSelected(true);
		m_Dialog.getJchbOrgName().setSelected(true);
		m_Dialog.getJchbOrgUnit().setSelected(true);
		m_Dialog.getJchbSignedBy().setSelected(true);
		m_Dialog.getJchbState().setSelected(true);
		
		m_Dialog.setVisible(true);	
	}

	public void itemStateChanged(ItemEvent e)
	{
		if (e.getSource() == m_Dialog.getJchbUseTimeStampServer() || e.getSource() == m_Dialog.getJchbAuthentication())
		{
			boolean tsaIsSelected = m_Dialog.getJchbUseTimeStampServer().isSelected();
			m_Dialog.getJtfURL().setEnabled(tsaIsSelected);
			m_Dialog.getJchbAuthentication().setEnabled(tsaIsSelected);
			m_Dialog.getJlURL().setEnabled(tsaIsSelected);

			boolean tsaAuthReqIsSelected = tsaIsSelected && m_Dialog.getJchbAuthentication().isSelected();
			m_Dialog.getJtfTimeStampUsername().setEnabled(tsaAuthReqIsSelected);
			m_Dialog.getJtfTimeStampPassword().setEnabled(tsaAuthReqIsSelected);
			m_Dialog.getJlPassword().setEnabled(tsaAuthReqIsSelected);
			m_Dialog.getJlUsername().setEnabled(tsaAuthReqIsSelected);
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals(CMD_SIGN))
		{
			if (cmdSign()) 
			{
				m_Dialog.dispose();
			}
		}
		else if (e.getActionCommand().equals(CMD_BROWSE))
		{
			cmdBrowse();
		}
		else if (e.getActionCommand().equals(CMD_SIGNATURE_TYPE))
		{
			togglePermsCombo();
		}
		else if (e.getActionCommand().equals(CMD_ALIAS))
		{
			loadAliases();
		}
	}
	
	private void loadAliases()
	{
		DefaultListModel model = new DefaultListModel();
		List<String> aliases = getKeyAliases();
		for (String alias : aliases)
		{
			model.addElement(alias);
		}
		m_Dialog.getJlAlias().setModel(model);
	}
	
	private List<String> getKeyAliases()
	{
		List<String> keyAliases = new ArrayList<String>();
		try
		{
			KeyStore keystore = getKeyStore();
			if (keystore != null)
			{
				Enumeration<String> aliases = keystore.aliases();
				while(aliases.hasMoreElements())
				{
					String nextAlias = aliases.nextElement();
					if (keystore.isKeyEntry(nextAlias))
					{
						keyAliases.add(nextAlias);
					}
				}
			}
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(m_Dialog, GenUtil.isEmptyString(e.getMessage()) ? "Error loading aliases" : e.getMessage());
		}
		return keyAliases;
	}

	private void togglePermsCombo()
	{
		m_Dialog.getJcbCertifyingPerms().setEnabled(m_Dialog.getJrbCertifyingSignature().isSelected());
	}
	
	// returns null if there's a problem loading the keystore
	private KeyStore getKeyStore() throws KeyStoreException, IOException
	{
        String digitalIDLocation = m_Dialog.getJtfDigitalID().getText();
        char[] password = m_Dialog.getJpfPassword().getPassword();
        
    	// Check the file
        File digialIDFile  = new File(digitalIDLocation);
		if (digialIDFile.exists() == false)
		{
			JOptionPane.showMessageDialog(m_Parent, "File does not exist");
			return null;
		}
		// Check the password
		if (password.length == 0)
		{
			JOptionPane.showMessageDialog(m_Parent, "Please enter a password to open digital ID");
			return null;
		}

		// Load the keystore that contains the digital id to use in signing
		FileInputStream pkcs12Stream = new FileInputStream(digitalIDLocation);
		KeyStore store = KeyStore.getInstance("PKCS12");
		try
		{
			store.load(pkcs12Stream, password);
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(m_Parent, "Unable to load signature");
			return null;
		}
		finally
		{
			pkcs12Stream.close();
		}
		return store;
	}

	private boolean cmdSign()
	{
		try
		{
			// Check document is open
            if(m_PDFSecure == null)
            {
                JOptionPane.showMessageDialog(m_Parent, "Please open a document first.");
                return false;
            }

			SigningInformation signInfo = null;

			KeyStore store = getKeyStore();
			if (store == null)
			{
				return false;
			}
			
			try
			{
				String alias = null;
				if (m_Dialog.getJlAlias().getSelectedValue() != null)
				{
					alias = (String) m_Dialog.getJlAlias().getSelectedValue();
				}
				else
				{
					List<String> aliases = getKeyAliases();
					if (aliases.size() > 0)
					{
						alias = aliases.get(0);
					}
				}
				
				if (alias != null)
				{
					// Create signing information
					signInfo = new SigningInformation(store, alias, new String(m_Dialog.getJpfPassword().getPassword()));
				}
			}
			catch (Exception ex)
			{
				// Do nothing
			}
			
	        if (signInfo == null)
	        {
	        	throw new PDFException("No digital IDs in the file");
	        }
			// Create signature field on the first page
			Rectangle2D signBounds = new Rectangle2D.Double(36, 36, 144, 48);
			SignatureField signField = m_PDFSecure.addSignatureField(0, "signature", signBounds);

			// timestamp server
			if (m_Dialog.getJchbUseTimeStampServer().isSelected())
			{
				String url = m_Dialog.getJtfURL().getText();
				if (url.length() == 0)
				{
					JOptionPane.showMessageDialog(m_Parent, "Please enter URL for Timestamp Server");
					return false;
				}
				String tsaUsername = null;
				String tsaPassword = null;
				if (m_Dialog.getJchbAuthentication().isSelected())
				{
					tsaUsername = m_Dialog.getJtfTimeStampUsername().getText();
					tsaPassword = m_Dialog.getJtfTimeStampUsername().getText();
				}
				// Add a TimestampServer to the SigningInformation
				TimestampServer timestampServer = new TimestampServer(url, tsaUsername, tsaPassword);
				signInfo.setTimestampServer(timestampServer);
			}
			
			// certifying signature
			if (m_Dialog.getJrbCertifyingSignature().isSelected())
			{
				signInfo.setCertifyingSignature(true);
				if (m_Dialog.getJcbCertifyingPerms().getSelectedItem().equals(ChooseSignatureDialog.PERM_NOCHANGES))
				{
					signInfo.setPermissions(SigningInformation.PERM_NOCHANGES);
				}
				else if (m_Dialog.getJcbCertifyingPerms().getSelectedItem().equals(ChooseSignatureDialog.PERM_FORMFILL_SIGNATURE))
				{
					signInfo.setPermissions(SigningInformation.PERM_FORMFILL_SIGNATURE);
				}
				else
				{
					signInfo.setPermissions(SigningInformation.PERM_FORMFILL_SIGNATURE_COMMENTS);
				}
			}
			
			// appearance
			SignatureAppearance signAppearance = new SignatureAppearance();
			signAppearance.setVisibleName(m_Dialog.getJchbName().isSelected());
			signAppearance.setVisibleCommonName(m_Dialog.getJchbCommonName().isSelected());
			signAppearance.setVisibleCountry(m_Dialog.getJchbCountry().isSelected());
			signAppearance.setVisibleDate(m_Dialog.getJchbDate().isSelected());
			signAppearance.setVisibleDigitallySigned(m_Dialog.getJchbSignedBy().isSelected());
			signAppearance.setVisibleEmail(m_Dialog.getJchbEmail().isSelected());
			signAppearance.setVisibleLocal(m_Dialog.getJchbCity().isSelected());
			signAppearance.setVisibleOrgName(m_Dialog.getJchbOrgName().isSelected());
			signAppearance.setVisibleOrgUnit(m_Dialog.getJchbOrgUnit().isSelected());
			signAppearance.setVisibleState(m_Dialog.getJchbState().isSelected());
			
			signInfo.setSignatureAppearance(signAppearance);
			
			// Apply digital signature
			m_PDFSecure.signDocument(signField, signInfo);

            m_pdfSecurePanel.save();
	    }
		catch (Throwable t)
		{
			System.out.println(t.getCause());
			JOptionPane.showMessageDialog(m_Parent, "Error signing document " + t.getMessage());
			return false;
		}
		return true;
	}
	
	private void cmdBrowse()
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new PDFFileFilter(new String[] {"p12", "pfx"}));
		if (fileChooser.showOpenDialog(m_Dialog) == JFileChooser.APPROVE_OPTION)
		{
			File chosenFile = fileChooser.getSelectedFile();
			if (chosenFile != null)
			{
				m_Dialog.getJtfDigitalID().setText(chosenFile.getAbsolutePath());
			}
		}
	}

}
