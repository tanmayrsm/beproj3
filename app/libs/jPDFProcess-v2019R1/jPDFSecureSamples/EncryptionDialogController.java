/*
 * Created on Feb 24, 2005
 *
 */
package jPDFSecureSamples;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.qoppa.pdf.permissions.PasswordPermissions;

/**
 * @author Gerald Holmann
 *
 */
public class EncryptionDialogController implements ActionListener, ItemListener
{
	private EncryptionDialog m_Dialog;
	private SecurityInfo m_SecurityInfo;
    

	private final static String CMD_OK = "Ok";
    private final static String CMD_CANCEL = "Cancel";
    private final String CMD_CHECK_PRINTHIGHRES = "CheckPrintHighRes";
    private final String CMD_CHECK_PRINT = "CheckPrint";
    private final String CMD_CHECK_CHANGEDOC = "Check Change Doc";
    private final String CMD_CHECK_ASSEMBLEDOC = "Check Assemble Doc";
    private final String CMD_CHECK_ANNOTS = "Check Annots";
    private final String CMD_CHECK_FORMS = "Check Forms";
    private final String CMD_CHECK_COPY = "Check Copy";
    private final String CMD_CHECK_COPYACCESS = "Check Copy Access";
    private final String CMD_CHANGE_ENCRYPTION = "ChangeEncryption";

    public EncryptionDialogController()
    {
        // nothing to do
    }
    
    protected void centerDialog (Component parent, JDialog dialog)
    {
    	if(dialog != null && parent != null)
    	{
    		dialog.setLocation (parent.getX () + (parent.getWidth() - dialog.getWidth()) / 2,
							parent.getY() + (parent.getHeight() - dialog.getHeight()) / 2);
    	}
    }
    
	/**
	 * Insert the method's description here.
	 * Creation date: (12/8/2002 7:32:17 PM)
	 */
	public SecurityInfo showDialog (Frame frame, PasswordPermissions permissions, String encryptType) 
	{
	    // Create dialog
	    m_Dialog = new EncryptionDialog(frame);
	    centerDialog(frame, m_Dialog);
	    
	    // initialize check boxes
	    if (permissions != null)
    	{
	        m_Dialog.getjcbAnnotations().setSelected(permissions.isModifyAnnotsAllowed());
	        m_Dialog.getjcbAssemblingDoc().setSelected(permissions.isAssembleDocumentAllowed());
	        m_Dialog.getjcbChanges().setSelected(permissions.isChangeDocumentAllowed());
	        m_Dialog.getjcbCopy().setSelected(permissions.isExtractTextGraphicsAllowed());
	        m_Dialog.getjcbCopyForAccessibility().setSelected(permissions.isExtractTextGraphicsForAccessibilityAllowed());
	        m_Dialog.getjcbFillingFormFields().setSelected(permissions.isFillFormFieldsAllowed());
	        m_Dialog.getjcbPrinting().setSelected(permissions.isPrintAllowed());
	        m_Dialog.getjcbPrintingHighRes().setSelected(permissions.isPrintHighResAllowed());
    	}
	    
	    // initialize encryption type
	    if (encryptType != null)
	    {
	    	m_Dialog.getJcbEncryption().setSelectedItem(encryptType);
	    }

	    // Add listeners
        m_Dialog.getjcbPrintingHighRes().addActionListener(this);
        m_Dialog.getjcbPrintingHighRes().setActionCommand(CMD_CHECK_PRINTHIGHRES);
        m_Dialog.getjcbPrinting().addActionListener(this);
        m_Dialog.getjcbPrinting().setActionCommand(CMD_CHECK_PRINT);
        m_Dialog.getjcbAnnotations().addActionListener(this);
        m_Dialog.getjcbAnnotations().setActionCommand(CMD_CHECK_ANNOTS);
        m_Dialog.getjcbAssemblingDoc().addActionListener(this);
        m_Dialog.getjcbAssemblingDoc().setActionCommand(CMD_CHECK_ASSEMBLEDOC);
        m_Dialog.getjcbChanges().addActionListener(this);
        m_Dialog.getjcbChanges().setActionCommand(CMD_CHECK_CHANGEDOC);
        m_Dialog.getjcbCopy().addActionListener(this);
        m_Dialog.getjcbCopy().setActionCommand(CMD_CHECK_COPY);
        m_Dialog.getjcbCopyForAccessibility().addActionListener(this);
        m_Dialog.getjcbCopyForAccessibility().setActionCommand(CMD_CHECK_COPYACCESS);
        m_Dialog.getjcbFillingFormFields().addActionListener(this);
        m_Dialog.getjcbFillingFormFields().setActionCommand(CMD_CHECK_FORMS);
        
        m_Dialog.getjbOk().setActionCommand(CMD_OK);
	    m_Dialog.getjbOk().addActionListener(this);
	    m_Dialog.getjbCancel().setActionCommand(CMD_CANCEL);
	    m_Dialog.getjbCancel().addActionListener(this);
	    m_Dialog.getjcbOpenPassword().addItemListener(this);
	    m_Dialog.getjcbPermissionsPassword().addItemListener(this);
	    m_Dialog.getJcbEncryption().addItemListener(this);
	    m_Dialog.getJcbEncryption().addActionListener(this);
	    m_Dialog.getJcbEncryption().setActionCommand(CMD_CHANGE_ENCRYPTION);
	    
	    toggleCompatibilityLabel();
	    
	    // Center the dialog and show it
	   // centerDialog(null, m_Dialog);
		m_Dialog.getRootPane().setDefaultButton(m_Dialog.getjbOk());
	    m_Dialog.setVisible(true);
	    
	    return m_SecurityInfo;
	}
	
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand() == CMD_OK)
        {
            // Create PDF stamp to do encryption
            m_SecurityInfo = new SecurityInfo();
            
            if(m_Dialog.getjcbOpenPassword().isSelected() == true)
            {
                // empty user password
                if(m_Dialog.getjpfUserPassword().getPassword().length == 0)
                {
                    JOptionPane.showMessageDialog(m_Dialog, "Please enter a password to open document or uncheck the box");
                    return;
                }
                m_SecurityInfo.setOpenPassword(new String (m_Dialog.getjpfUserPassword().getPassword()));
            }
            
            if(m_Dialog.getjcbPermissionsPassword().isSelected() == true)
            {
                // empty permissions password
                if(m_Dialog.getjpfOwnerPassword().getPassword().length == 0)
                {
                    JOptionPane.showMessageDialog(m_Dialog, "Please enter a passord to change security or uncheck the box");
                    return;
                }
                m_SecurityInfo.setPermissionsPassword(new String (m_Dialog.getjpfOwnerPassword().getPassword()));
            }
            
            PasswordPermissions perms = new PasswordPermissions();
            perms.setPrintAllowed(m_Dialog.getjcbPrinting().isSelected());
            perms.setPrintHighResAllowed(m_Dialog.getjcbPrintingHighRes().isSelected());
            perms.setExtractTextGraphicsAllowed(m_Dialog.getjcbCopy().isSelected());
            perms.setExtractTextGraphicsForAccessibilityAllowed(m_Dialog.getjcbCopyForAccessibility().isSelected());
            perms.setChangeDocumentAllowed(m_Dialog.getjcbChanges().isSelected());
            perms.setAssembleDocumentAllowed(m_Dialog.getjcbAssemblingDoc().isSelected());
            perms.setModifyAnnotsAllowed(m_Dialog.getjcbAnnotations().isSelected());
            perms.setFillFormFieldsAllowed(m_Dialog.getjcbFillingFormFields().isSelected());
            
            m_SecurityInfo.setPermissions(perms);
            
            int encryption = PasswordPermissions.ENCRYPTION_RC4_128;
        	if (m_Dialog.getJcbEncryption().getSelectedItem().equals(EncryptionDialog.STR_128AES))
			{
				encryption = PasswordPermissions.ENCRYPTION_AES_128;
			}
			else if (m_Dialog.getJcbEncryption().getSelectedItem().equals(EncryptionDialog.STR_256AES)) 
			{
				encryption = PasswordPermissions.ENCRYPTION_AES_256;
			}
        	m_SecurityInfo.setEncryption(encryption);
            m_Dialog.dispose();
        }
        else if (e.getActionCommand() == CMD_CANCEL)
        {
        	m_SecurityInfo = null;
            m_Dialog.dispose();
        }
        else if (e.getActionCommand() == CMD_CHECK_PRINT)
        {
            if(m_Dialog.getjcbPrinting().isSelected() == false)
            {
                m_Dialog.getjcbPrintingHighRes().setSelected(false);
            }
        }
        else if (e.getActionCommand() == CMD_CHECK_PRINTHIGHRES)
        {
            if(m_Dialog.getjcbPrintingHighRes().isSelected() == true)
            {
                m_Dialog.getjcbPrinting().setSelected(true);
            }
        }

        else if (e.getActionCommand() == CMD_CHECK_CHANGEDOC)
        {
            if(m_Dialog.getjcbChanges().isSelected() == true)
            {
                m_Dialog.getjcbAssemblingDoc().setSelected(true);
                m_Dialog.getjcbFillingFormFields().setSelected(true);
                m_Dialog.getjcbAnnotations().setSelected(true);
            }
        }
        else if (e.getActionCommand() == CMD_CHECK_ASSEMBLEDOC)
        {
            if(m_Dialog.getjcbAssemblingDoc().isSelected() == false)
            {
                m_Dialog.getjcbChanges().setSelected(false);
            }
        }
        else if (e.getActionCommand() == CMD_CHECK_ANNOTS)
        {
            if(m_Dialog.getjcbAnnotations().isSelected() == false)
            {
                m_Dialog.getjcbChanges().setSelected(false);
            }
            else
            {
                m_Dialog.getjcbFillingFormFields().setSelected(true);
            }
        } 
        else if (e.getActionCommand() == CMD_CHECK_FORMS)
        {
            if(m_Dialog.getjcbFillingFormFields().isSelected() == false)
            {
                m_Dialog.getjcbChanges().setSelected(false);
                m_Dialog.getjcbAnnotations().setSelected(false);
            }
        }
        else if (e.getActionCommand() == CMD_CHECK_COPY)
        {
            if(m_Dialog.getjcbCopy().isSelected() == true)
            {
                m_Dialog.getjcbCopyForAccessibility().setSelected(true);
            }
        }
        else if (e.getActionCommand() == CMD_CHECK_COPYACCESS)
        {
            if(m_Dialog.getjcbCopyForAccessibility().isSelected() == false)
            {
                m_Dialog.getjcbCopy().setSelected(false);
            }
        }
        else if (e.getActionCommand() == CMD_CHANGE_ENCRYPTION)
		{
			if (m_Dialog.getJcbEncryption().getSelectedItem() == EncryptionDialog.STR_256AES && !is256Supported())
			{
				JOptionPane.showMessageDialog(m_Dialog, "Encrypting or decrypting PDF documents with 256-bit AES Encryption\nis supported but requires the Java Cryptography Extension (JCE)");
				m_Dialog.getJcbEncryption().setSelectedItem(EncryptionDialog.STR_128AES);
			}
		}
    }

    private static boolean is256Supported()
	{
		try
		{
			// Get the KeyGenerator
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(256);

			// Generate the secret key specs.
			SecretKey skey = kgen.generateKey();
			byte[] raw = skey.getEncoded();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

			// Instantiate the cipher
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

			return true;
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchPaddingException e)
		{
			e.printStackTrace();
		}
		catch (InvalidKeyException e)
		{
			// This should fail if the jce is not installed
			e.printStackTrace();
		}

		return false;
	}

    public void itemStateChanged(ItemEvent e) 
    {
    	if (e.getSource() == m_Dialog.getjcbPermissionsPassword())
    	{
    		m_Dialog.getjpfOwnerPassword().setEnabled(m_Dialog.getjcbPermissionsPassword().isSelected());
    		if (m_Dialog.getjcbPermissionsPassword().isSelected())
    		{
    			m_Dialog.getjpfOwnerPassword().selectAll();
    			m_Dialog.getjpfOwnerPassword().grabFocus();
    		}
    	}
    	else if (e.getSource() == m_Dialog.getjcbOpenPassword())
    	{
    		m_Dialog.getjpfUserPassword().setEnabled (m_Dialog.getjcbOpenPassword().isSelected());
    		if (m_Dialog.getjcbOpenPassword().isSelected())
    		{
    			m_Dialog.getjpfUserPassword().selectAll();
    			m_Dialog.getjpfUserPassword().grabFocus();
    		}
    	}
		else if (e.getSource() == m_Dialog.getJcbEncryption())
		{			
			toggleCompatibilityLabel();
		}
    }
	
    private void toggleCompatibilityLabel()
    {
        if (EncryptionDialog.STR_RC4.equals(m_Dialog.getJcbEncryption().getSelectedItem()))
        {
        	m_Dialog.getjlEncryptionDesc().setText(EncryptionDialog.STR_ACRO5);
        }
        else if (EncryptionDialog.STR_256AES.equals(m_Dialog.getJcbEncryption().getSelectedItem()))
        {
        	m_Dialog.getjlEncryptionDesc().setText(EncryptionDialog.STR_ACROX);
        }
        else // 128AES
        {
        	m_Dialog.getjlEncryptionDesc().setText(EncryptionDialog.STR_ACRO7);
        }
    }
}
