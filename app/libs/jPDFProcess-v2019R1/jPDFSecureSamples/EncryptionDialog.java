/*
 * Created on Feb 24, 2005
 *
 */
package jPDFSecureSamples;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

/**
 * @author Gerald Holmann
 *
 */
public class EncryptionDialog extends JDialog
{
	private static final long serialVersionUID = 1L;
	private JPanel jPanel = null;
	private JButton jbOk = null;
	private JButton jbCancel = null;
	private JPasswordField jpfUserPassword = null;
	private JPasswordField jpfOwnerPassword = null;
	private JCheckBox jcbPrinting = null;
    private JCheckBox jcbPrintingHighRes = null;
	private JCheckBox jcbChanges = null;
    private JCheckBox jcbAssemblingDoc = null;
	private JCheckBox jcbCopy = null;
    private JCheckBox jcbCopyForAccessibility = null;
	private JCheckBox jcbAnnotations = null;
    private JCheckBox jcbFillingFormFields = null;
	private JCheckBox jcbOpenPassword = null;
	private JCheckBox jcbPermissionsPassword = null;
	private JPanel jpPermissions = null;
	private JPanel jpPassword = null;
	private JPanel jpEncryption = null;
	
    public final static String STR_ACRO5 = "Acrobat 5";
    public final static String STR_ACRO7 = "Acrobat 7";
    public final static String STR_ACROX = "Acrobat X";
    
    public final static String STR_RC4 = "RC4";
    public final static String STR_128AES = "128 AES";
    public final static String STR_256AES = "256 AES";
    
    private JComboBox jcbEncryption = null;
    private JLabel jlEncryptionDesc = null;
	
	/**
	 * This method initializes 
	 * 
	 */
	public EncryptionDialog(Frame frame)
	{
		super(frame);
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.setModal(true);
        this.setContentPane(getJPanel());
        this.setTitle("Change Security");
        this.setSize((int)(498 * SampleUtil.getDPIScalingMultiplier()), (int)(400 * SampleUtil.getDPIScalingMultiplier()));
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getjbOk(), null);
			jPanel.add(getjbCancel(), null);
			jPanel.add(getJpEncryption(), null);
			jPanel.add(getJpPermissions(), null);
			jPanel.add(getJpPassword(), null);
		}
		return jPanel;
	}
	
	public JPanel getJpEncryption() {
		if (jpEncryption == null) {
			jpEncryption = new JPanel();
			jpEncryption.setBounds(new java.awt.Rectangle((int)(12 * SampleUtil.getDPIScalingMultiplier()),(int)(20 * SampleUtil.getDPIScalingMultiplier()),(int)(470 * SampleUtil.getDPIScalingMultiplier()),(int)(60 * SampleUtil.getDPIScalingMultiplier())));
			jpEncryption.setLayout(new FlowLayout(FlowLayout.LEFT));
			jpEncryption.setBorder(BorderFactory.createTitledBorder("Compatibility"));
			jpEncryption.add(new JLabel("Encryption" + ": "));
			jpEncryption.add(getJcbEncryption());
			jpEncryption.add(new JLabel("Compatibility" + ": "));
			jpEncryption.add(getjlEncryptionDesc());
			getJcbEncryption().addItem(STR_RC4);
			getJcbEncryption().addItem(STR_128AES);
			getJcbEncryption().addItem(STR_256AES);
			getJcbEncryption().setSelectedIndex(1);
		}
		return jpEncryption;
	}
	
	/**
	 * This method initializes JButton	
	 * 	
	 * @return com.qoppa.pdfStudio.components.JButton	
	 */    
	public JButton getjbOk() {
		if (jbOk == null) {
			jbOk = new JButton("Ok");
			jbOk.setBounds(new java.awt.Rectangle((int)(161 * SampleUtil.getDPIScalingMultiplier()),(int)(320 * SampleUtil.getDPIScalingMultiplier()),(int)(70 * SampleUtil.getDPIScalingMultiplier()),(int)(25 * SampleUtil.getDPIScalingMultiplier())));
			jbOk.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jbOk.getFont().getSize(), 10)));
		}
		return jbOk;
	}
	/**
	 * This method initializes JButton1	
	 * 	
	 * @return com.qoppa.pdfStudio.components.JButton	
	 */    
	public JButton getjbCancel() {
		if (jbCancel == null) {
			jbCancel = new JButton("Cancel");
			jbCancel.setBounds(new java.awt.Rectangle((int)(264 * SampleUtil.getDPIScalingMultiplier()),(int)(320 * SampleUtil.getDPIScalingMultiplier()),(int)(70 * SampleUtil.getDPIScalingMultiplier()),(int)(25 * SampleUtil.getDPIScalingMultiplier())));
			jbCancel.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jbCancel.getFont().getSize(), 10)));
			jbCancel.setMargin(new Insets(0,0,0,0));
		}
		return jbCancel;
	}
	/**
	 * This method initializes qPasswordField	
	 * 	
	 * @return com.qoppa.pdfStudio.components.QPasswordField	
	 */    
	public JPasswordField getjpfUserPassword() {
		if (jpfUserPassword == null) {
			jpfUserPassword = new JPasswordField();
			jpfUserPassword.setBounds(new java.awt.Rectangle((int)(31 * SampleUtil.getDPIScalingMultiplier()),(int)(40 * SampleUtil.getDPIScalingMultiplier()),(int)(150 * SampleUtil.getDPIScalingMultiplier()),(int)(19 * SampleUtil.getDPIScalingMultiplier())));
		}
		return jpfUserPassword;
	}
	/**
	 * This method initializes qPasswordField1	
	 * 	
	 * @return com.qoppa.pdfStudio.components.QPasswordField	
	 */    
	public JPasswordField getjpfOwnerPassword() {
		if (jpfOwnerPassword == null) {
			jpfOwnerPassword = new JPasswordField();
			jpfOwnerPassword.setBounds(new java.awt.Rectangle(31,85,150,19));
			jpfOwnerPassword.setBounds(new java.awt.Rectangle((int)(31 * SampleUtil.getDPIScalingMultiplier()),(int)(85 * SampleUtil.getDPIScalingMultiplier()),(int)(150 * SampleUtil.getDPIScalingMultiplier()),(int)(19 * SampleUtil.getDPIScalingMultiplier())));
		}
		return jpfOwnerPassword;
	}
	/**
	 * This method initializes JCheckBox	
	 * 	
	 * @return com.qoppa.pdfStudio.components.JCheckBox	
	 */    
	public JCheckBox getjcbPrinting() {
		if (jcbPrinting == null) {
			jcbPrinting = new JCheckBox("Allow Printing");
			jcbPrinting.setBounds(new java.awt.Rectangle((int)(24 * SampleUtil.getDPIScalingMultiplier()),(int)(34 * SampleUtil.getDPIScalingMultiplier()),(int)(188 * SampleUtil.getDPIScalingMultiplier()),(int)(19 * SampleUtil.getDPIScalingMultiplier())));
			jcbPrinting.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jcbPrinting.getFont().getSize(), 10)));
		}
		return jcbPrinting;
	}
    /**
     * This method initializes jcbPrintingHighRes    
     *  
     * @return com.qoppa.pdfStudio.components.JCheckBox 
     */    
    public JCheckBox getjcbPrintingHighRes() {
        if (jcbPrintingHighRes == null) {
            jcbPrintingHighRes = new JCheckBox("Allow Printing High Res");
            jcbPrintingHighRes.setBounds(new java.awt.Rectangle((int)(15 * SampleUtil.getDPIScalingMultiplier()),(int)(16 * SampleUtil.getDPIScalingMultiplier()),(int)(188 * SampleUtil.getDPIScalingMultiplier()),(int)(19 * SampleUtil.getDPIScalingMultiplier())));
			jcbPrintingHighRes.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jcbPrintingHighRes.getFont().getSize(), 10)));
        }
        return jcbPrintingHighRes;
    }
	/**
	 * This method initializes JCheckBox1	
	 * 	
	 * @return com.qoppa.pdfStudio.components.JCheckBox	
	 */    
	public JCheckBox getjcbChanges() {
		if (jcbChanges == null) {
			jcbChanges = new JCheckBox("Allow All Changes");
			jcbChanges.setBounds(new java.awt.Rectangle((int)(225 * SampleUtil.getDPIScalingMultiplier()),(int)(16 * SampleUtil.getDPIScalingMultiplier()),(int)(188 * SampleUtil.getDPIScalingMultiplier()),(int)(19 * SampleUtil.getDPIScalingMultiplier())));
			jcbChanges.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jcbChanges.getFont().getSize(), 10)));
		}
		return jcbChanges;
	}
    /**
     * This method initializes jcbAssemblingDoc   
     *  
     * @return com.qoppa.pdfStudio.components.JCheckBox 
     */    
    public JCheckBox getjcbAssemblingDoc() {
        if (jcbAssemblingDoc == null) {
            jcbAssemblingDoc = new JCheckBox("Allow Document Assembly");
            jcbAssemblingDoc.setBounds(new java.awt.Rectangle((int)(240 * SampleUtil.getDPIScalingMultiplier()),(int)(34 * SampleUtil.getDPIScalingMultiplier()),(int)(188 * SampleUtil.getDPIScalingMultiplier()),(int)(19 * SampleUtil.getDPIScalingMultiplier())));
			jcbAssemblingDoc.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jcbAssemblingDoc.getFont().getSize(), 10)));
        }
        return jcbAssemblingDoc;
    }
	/**
	 * This method initializes jcbCopy	
	 * 	
	 * @return com.qoppa.pdfStudio.components.JCheckBox	
	 */    
	public JCheckBox getjcbCopy() {
		if (jcbCopy == null) {
			jcbCopy = new JCheckBox("Allow Content Copying or Extraction");
			jcbCopy.setBounds(new java.awt.Rectangle((int)(15 * SampleUtil.getDPIScalingMultiplier()),(int)(53 * SampleUtil.getDPIScalingMultiplier()),(int)(208 * SampleUtil.getDPIScalingMultiplier()),(int)(19 * SampleUtil.getDPIScalingMultiplier())));
			jcbCopy.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jcbCopy.getFont().getSize(), 10)));
		}
		return jcbCopy;
	}
    /**
     * This method initializes jcbCopyForAccessibility   
     *  
     * @return com.qoppa.pdfStudio.components.JCheckBox 
     */    
    public JCheckBox getjcbCopyForAccessibility() {
        if (jcbCopyForAccessibility == null) {
            jcbCopyForAccessibility = new JCheckBox("Allow Extraction For Accessibility");
            jcbCopyForAccessibility.setBounds(new java.awt.Rectangle((int)(24 * SampleUtil.getDPIScalingMultiplier()),(int)(72 * SampleUtil.getDPIScalingMultiplier()),(int)(216 * SampleUtil.getDPIScalingMultiplier()),(int)(19 * SampleUtil.getDPIScalingMultiplier())));
			jcbCopyForAccessibility.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jcbCopyForAccessibility.getFont().getSize(), 10)));
        }
        return jcbCopyForAccessibility;
    }
	/**
	 * This method initializes jcbAnnotations	
	 * 	
	 * @return com.qoppa.pdfStudio.components.JCheckBox	
	 */    
	public JCheckBox getjcbAnnotations() {
		if (jcbAnnotations == null) {
			jcbAnnotations = new JCheckBox("Allow Annotations & Comments");
			jcbAnnotations.setBounds(new java.awt.Rectangle((int)(240 * SampleUtil.getDPIScalingMultiplier()),(int)(53 * SampleUtil.getDPIScalingMultiplier()),(int)(195 * SampleUtil.getDPIScalingMultiplier()),(int)(19 * SampleUtil.getDPIScalingMultiplier())));
			jcbAnnotations.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jcbAnnotations.getFont().getSize(), 10)));
		}
		return jcbAnnotations;
	}
    /**
     * This method initializes jcbFillingFormFields   
     *  
     * @return com.qoppa.pdfStudio.components.JCheckBox 
     */    
    public JCheckBox getjcbFillingFormFields() {
        if (jcbFillingFormFields == null) {
            jcbFillingFormFields = new JCheckBox("Allow Filling of Form Fields & Signing");
            jcbFillingFormFields.setBounds(new java.awt.Rectangle((int)(253 * SampleUtil.getDPIScalingMultiplier()),(int)(73 * SampleUtil.getDPIScalingMultiplier()),(int)(210 * SampleUtil.getDPIScalingMultiplier()),(int)(19 * SampleUtil.getDPIScalingMultiplier())));
			jcbFillingFormFields.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jcbFillingFormFields.getFont().getSize(), 10)));
        }
        return jcbFillingFormFields;
    }
	/**
	 * This method initializes jcbOpenPassword	
	 * 	
	 * @return com.qoppa.pdfStudio.components.JCheckBox	
	 */
	public JCheckBox getjcbOpenPassword() {
		if (jcbOpenPassword == null) {
			jcbOpenPassword = new JCheckBox("Password To Open Document");
			jcbOpenPassword.setBounds(new java.awt.Rectangle((int)(10 * SampleUtil.getDPIScalingMultiplier()),(int)(20 * SampleUtil.getDPIScalingMultiplier()),(int)(190 * SampleUtil.getDPIScalingMultiplier()),(int)(19 * SampleUtil.getDPIScalingMultiplier())));
			jcbOpenPassword.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jcbOpenPassword.getFont().getSize(), 10)));
		}
		return jcbOpenPassword;
	}
	/**
	 * This method initializes JCheckBox	
	 * 	
	 * @return com.qoppa.pdfStudio.components.JCheckBox	
	 */
	public JCheckBox getjcbPermissionsPassword() {
		if (jcbPermissionsPassword == null) {			
			jcbPermissionsPassword = new JCheckBox("Password to Change Security");
			jcbPermissionsPassword.setBounds(new java.awt.Rectangle((int)(10 * SampleUtil.getDPIScalingMultiplier()),(int)(65 * SampleUtil.getDPIScalingMultiplier()),(int)(183 * SampleUtil.getDPIScalingMultiplier()),(int)(19 * SampleUtil.getDPIScalingMultiplier())));
			jcbPermissionsPassword.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jcbPermissionsPassword.getFont().getSize(), 10)));
			}
		return jcbPermissionsPassword;
	}
	/**
	 * This method initializes jpPermissions	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJpPermissions() {
		if (jpPermissions == null) {
			jpPermissions = new JPanel();
			jpPermissions.setLayout(null);
			jpPermissions.setBounds(new java.awt.Rectangle((int)(12 * SampleUtil.getDPIScalingMultiplier()),(int)(210 * SampleUtil.getDPIScalingMultiplier()),(int)(470 * SampleUtil.getDPIScalingMultiplier()),(int)(101 * SampleUtil.getDPIScalingMultiplier())));
			jpPermissions.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Permissions", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(new JLabel().getFont().getSize(), 11)), java.awt.Color.black));
			jpPermissions.add(getjcbPrinting(), null);
			jpPermissions.add(getjcbCopy(), null);
			jpPermissions.add(getjcbChanges(), null);
			jpPermissions.add(getjcbAnnotations(), null);
			jpPermissions.add(getjcbFillingFormFields(), null);
			jpPermissions.add(getjcbCopyForAccessibility(), null);
			jpPermissions.add(getjcbPrintingHighRes(), null);
			jpPermissions.add(getjcbAssemblingDoc(), null);
		}
		return jpPermissions;
	}
	/**
	 * This method initializes jpPassword	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJpPassword() {
		if (jpPassword == null) {
			jpPassword = new JPanel();
			jpPassword.setLayout(null);
			jpPassword.setBounds(new java.awt.Rectangle((int)(12 * SampleUtil.getDPIScalingMultiplier()),(int)(80 * SampleUtil.getDPIScalingMultiplier()),(int)(470 * SampleUtil.getDPIScalingMultiplier()),(int)(120 * SampleUtil.getDPIScalingMultiplier())));
			jpPassword.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Password", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(new JLabel().getFont().getSize(), 10)), null));
			jpPassword.add(getjcbOpenPassword(), null);
			jpPassword.add(getjpfUserPassword(), null);
			jpPassword.add(getjcbPermissionsPassword(), null);
			jpPassword.add(getjpfOwnerPassword(), null);
		}
		return jpPassword;
	}
	
    public JComboBox getJcbEncryption()
    {
        if (jcbEncryption == null)
        {
            jcbEncryption = new JComboBox();
        }
        return jcbEncryption;
    }

    public JLabel getjlEncryptionDesc ()
    {
        if (jlEncryptionDesc == null)
        {
            jlEncryptionDesc = new JLabel();
        }
        return jlEncryptionDesc;
    }

}  //  @jve:decl-index=0:visual-constraint="10,10"
