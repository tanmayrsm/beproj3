package jPDFSecureSamples;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class ChooseSignatureDialog extends JDialog
{
	private JTextField jtfDigitalID;
	private JPasswordField jpfPassword;
	private JList jlAlias;
	private JButton jbAlias;
	private JCheckBox jchbUseTimeStampServer;
	private JButton jbSign;
	private JPanel jpDigitalID;
	private JPanel jpTimestampServer;
	private JButton jbBrowse;
	private JTextField jtfURL;
	private JCheckBox jchbAuthentication;
	private JTextField jtfTimeStampUsername;
	private JPasswordField jpfTimeStampPassword;
	private JLabel jlURL;
	private JLabel jlUsername;
	private JLabel jlPassword;
	private JPanel jpSignatureType;
	private JRadioButton jrbStandardSig;
	private JRadioButton jrbCertifyingSig;
	private ButtonGroup bgSignatureType;
	private JComboBox jcbCertifyingPerms;
	private JPanel jpAppearance;
	private JCheckBox jchbName;
	private JCheckBox jchbEmail;
	private JCheckBox jchbDate;
	private JCheckBox jchbCity;
	private JCheckBox jchbState;
	private JCheckBox jchbCommonName;
	private JCheckBox jchbOrgName;
	private JCheckBox jchbOrgUnit;
	private JCheckBox jchbSignedBy;
	private JCheckBox jchbCountry;
	
	public static final String PERM_NOCHANGES = "Disallow Changes";
	public static final String PERM_FORMFILL_SIGNATURE = "Allow Form fill and Signatures";
	public static final String PERM_FORMFILL_SIGNATURE_COMMENTS = "Allow Form fill, Signatures, and Comments";
	
	ChooseSignatureDialog(Frame parent)
	{
		super(parent);
		init();
	}
	
	private void init()
	{
		setLayout(null);
		add(getJpDigitalID());
		add(getJpTimeStampServer());
		add(getJpSignatureType());
		add(getJpAppearance());
		add(getJbSign());
	}
	
	public JPanel getJpDigitalID()
	{
		if (jpDigitalID == null)
		{
			jpDigitalID = new JPanel();
			jpDigitalID.setBounds(new Rectangle((int)(5 * SampleUtil.getDPIScalingMultiplier()), (int)(5 * SampleUtil.getDPIScalingMultiplier()), (int)(480 * SampleUtil.getDPIScalingMultiplier()), (int)(150 * SampleUtil.getDPIScalingMultiplier())));
			jpDigitalID.setBorder(BorderFactory.createTitledBorder(null, "Digital ID", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN, (int)SampleUtil.getScaledFont(new JLabel().getFont().getSize(), 11)), Color.black));
			JLabel label1 = new JLabel("File name:");
			label1.setBounds(new Rectangle((int)(20 * SampleUtil.getDPIScalingMultiplier()), (int)(30 * SampleUtil.getDPIScalingMultiplier()), (int)(50 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier())));
			add(label1);
			add(getJtfDigitalID());
			add(getJbBrowse());
			JLabel label2 = new JLabel("Password:");
			label2.setBounds(new Rectangle((int)(20 * SampleUtil.getDPIScalingMultiplier()), (int)(55 * SampleUtil.getDPIScalingMultiplier()), (int)(50 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier())));
			add(label2);
			add(getJpfPassword());
			JLabel label3 = new JLabel("Alias:");
			label3.setBounds(new Rectangle((int)(20 * SampleUtil.getDPIScalingMultiplier()), (int)(80 * SampleUtil.getDPIScalingMultiplier()), (int)(50 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier())));
			add(label3);
			add(getJlAlias());
			add(getJbAlias());
		}
		return jpDigitalID;
	}
	
	public JButton getJbAlias()
	{
		if (jbAlias == null)
		{
			jbAlias = new JButton("Load Aliases");
			jbAlias.setBounds(new Rectangle((int)(280 * SampleUtil.getDPIScalingMultiplier()), (int)(80 * SampleUtil.getDPIScalingMultiplier()), (int)(100 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier())));
		}
		return jbAlias;
	}

	public JList getJlAlias()
	{
		if (jlAlias == null)
		{
			jlAlias = new JList();
			jlAlias.setBounds(new Rectangle((int)(75 * SampleUtil.getDPIScalingMultiplier()), (int)(80 * SampleUtil.getDPIScalingMultiplier()), (int)(200 * SampleUtil.getDPIScalingMultiplier()), (int)(60 * SampleUtil.getDPIScalingMultiplier())));
		}
		return jlAlias;
	}

	public JTextField getJtfDigitalID()
	{
		if (jtfDigitalID == null)
		{
			jtfDigitalID = new JTextField();
			jtfDigitalID.setBounds(new Rectangle((int)(75 * SampleUtil.getDPIScalingMultiplier()), (int)(30 * SampleUtil.getDPIScalingMultiplier()), (int)(300 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier())));
		}
		return jtfDigitalID;
	}
	
	public JPasswordField getJpfPassword()
	{
		if (jpfPassword == null)
		{
			jpfPassword = new JPasswordField();
			jpfPassword.setBounds(new Rectangle((int)(75 * SampleUtil.getDPIScalingMultiplier()), (int)(55 * SampleUtil.getDPIScalingMultiplier()), (int)(200 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier())));
		}
		return jpfPassword;
	}
	
	public JButton getJbBrowse()
	{
		if (jbBrowse == null)
		{
			jbBrowse = new JButton("Browse...");
			jbBrowse.setBounds(new Rectangle((int)(380 * SampleUtil.getDPIScalingMultiplier()), (int)(29 * SampleUtil.getDPIScalingMultiplier()), (int)(80 * SampleUtil.getDPIScalingMultiplier()), (int)(22 * SampleUtil.getDPIScalingMultiplier())));
		}
		return jbBrowse;
	}

	public JPanel getJpTimeStampServer()
	{
		if (jpTimestampServer == null)
		{
			jpTimestampServer = new JPanel();
			jpTimestampServer.setBounds(new Rectangle((int)(5 * SampleUtil.getDPIScalingMultiplier()), (int)(160 * SampleUtil.getDPIScalingMultiplier()), (int)(480 * SampleUtil.getDPIScalingMultiplier()), (int)(160 * SampleUtil.getDPIScalingMultiplier())));
			jpTimestampServer.setBorder(BorderFactory.createTitledBorder(null, "Timestamp Server", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN, (int)SampleUtil.getScaledFont(new JLabel().getFont().getSize(), 11)), Color.black));
			add(getJchbUseTimeStampServer());
			add(getJlURL());
			add(getJtfURL());
			add(getJchbAuthentication());
			add(getJlUsername());
			add(getJtfTimeStampUsername());
			add(getJlPassword());
			add(getJtfTimeStampPassword());
		}
		return jpTimestampServer;
	}
	
	public JLabel getJlURL()
	{
		if (jlURL == null)
		{
			jlURL = new JLabel("URL:");
			jlURL.setBounds(new Rectangle((int)(20 * SampleUtil.getDPIScalingMultiplier()), (int)(205 * SampleUtil.getDPIScalingMultiplier()), (int)(30 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier())));
			jlURL.setEnabled(false);
		}
		return jlURL;
	}
	
	public JLabel getJlUsername()
	{
		if (jlUsername == null)
		{
			jlUsername = new JLabel("Username:");
			jlUsername.setBounds(new Rectangle((int)(20 * SampleUtil.getDPIScalingMultiplier()), (int)(255 * SampleUtil.getDPIScalingMultiplier()), (int)(80 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier())));
			jlUsername.setEnabled(false);
		}
		return jlUsername;
	}
	
	public JLabel getJlPassword()
	{
		if (jlPassword == null)
		{
			jlPassword = new JLabel("Password:");
			jlPassword.setBounds(new Rectangle((int)(20 * SampleUtil.getDPIScalingMultiplier()), (int)(280 * SampleUtil.getDPIScalingMultiplier()), (int)(80 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier())));
			jlPassword.setEnabled(false);
		}
		return jlPassword;
	}

	public JCheckBox getJchbUseTimeStampServer()
	{
		if (jchbUseTimeStampServer == null)
		{
			jchbUseTimeStampServer = new JCheckBox();
			jchbUseTimeStampServer.setBounds(new Rectangle((int)(30 * SampleUtil.getDPIScalingMultiplier()), (int)(180 * SampleUtil.getDPIScalingMultiplier()), (int)(200 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier())));
			jchbUseTimeStampServer.setText("Use Timestamp Server");
		}
		return jchbUseTimeStampServer;
	}
	
	public JTextField getJtfURL()
	{
		if (jtfURL == null)
		{
			jtfURL = new JTextField();
			jtfURL.setBounds(new Rectangle((int)(55 * SampleUtil.getDPIScalingMultiplier()), (int)(205 * SampleUtil.getDPIScalingMultiplier()), (int)(300 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier())));
			jtfURL.setEnabled(false);
		}
		return jtfURL;
	}
	
	public JCheckBox getJchbAuthentication()
	{
		if (jchbAuthentication == null)
		{
			jchbAuthentication = new JCheckBox("TSA Requires Authentication");
			jchbAuthentication.setBounds(new Rectangle((int)(30 * SampleUtil.getDPIScalingMultiplier()), (int)(230 * SampleUtil.getDPIScalingMultiplier()), (int)(300 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier())));
			jchbAuthentication.setEnabled(false);
		}
		return jchbAuthentication;
	}
	
	public JTextField getJtfTimeStampUsername()
	{
		if (jtfTimeStampUsername == null)
		{
			jtfTimeStampUsername = new JTextField();
			jtfTimeStampUsername.setBounds(new Rectangle((int)(75 * SampleUtil.getDPIScalingMultiplier()), (int)(255 * SampleUtil.getDPIScalingMultiplier()), (int)(200 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier())));
			jtfTimeStampUsername.setEnabled(false);
		}
		return jtfTimeStampUsername;
	}
	
	public JPasswordField getJtfTimeStampPassword()
	{
		if (jpfTimeStampPassword == null)
		{
			jpfTimeStampPassword = new JPasswordField();
			jpfTimeStampPassword.setBounds(new Rectangle((int)(75 * SampleUtil.getDPIScalingMultiplier()), (int)(280 * SampleUtil.getDPIScalingMultiplier()), (int)(200 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier())));
			jpfTimeStampPassword.setEnabled(false);
		}
		return jpfTimeStampPassword;
	}

	public JButton getJbSign()
	{
		if (jbSign == null)
		{
			jbSign = new JButton("Sign");
			jbSign.setBounds(new Rectangle((int)(380 * SampleUtil.getDPIScalingMultiplier()), (int)(545 * SampleUtil.getDPIScalingMultiplier()), (int)(75 * SampleUtil.getDPIScalingMultiplier()), (int)(25 * SampleUtil.getDPIScalingMultiplier())));
		}
		return jbSign;
	}
	
	public JPanel getJpSignatureType()
	{
		if (jpSignatureType == null)
		{
			jpSignatureType = new JPanel();
			jpSignatureType.setBounds(new Rectangle((int)(5 * SampleUtil.getDPIScalingMultiplier()), (int)(325 * SampleUtil.getDPIScalingMultiplier()), (int)(480 * SampleUtil.getDPIScalingMultiplier()), (int)(75 * SampleUtil.getDPIScalingMultiplier())));
			jpSignatureType.setBorder(BorderFactory.createTitledBorder("Signature Type"));
			add(getJrbStandardSignature());
			add(getJrbCertifyingSignature());
			add(getJcbCertifyingPerms());
		}
		return jpSignatureType;
	}
	
	public JRadioButton getJrbStandardSignature()
	{
		if (jrbStandardSig == null)
		{
			jrbStandardSig = new JRadioButton("Signature");
			jrbStandardSig.setBounds(new Rectangle((int)(30 * SampleUtil.getDPIScalingMultiplier()), (int)(345 * SampleUtil.getDPIScalingMultiplier()), (int)(140 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier())));
			getBgSignatureType().add(jrbStandardSig);
		}
		return jrbStandardSig;
	}
	
	public JRadioButton getJrbCertifyingSignature()
	{
		if (jrbCertifyingSig == null)
		{
			jrbCertifyingSig = new JRadioButton("Certifying Signature");
			jrbCertifyingSig.setBounds(new Rectangle((int)(30 * SampleUtil.getDPIScalingMultiplier()), (int)(370 * SampleUtil.getDPIScalingMultiplier()), (int)(140 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier())));
			getBgSignatureType().add(jrbCertifyingSig);
		}
		return jrbCertifyingSig;
	}
	
	private ButtonGroup getBgSignatureType()
	{
		if (bgSignatureType == null)
		{
			bgSignatureType = new ButtonGroup();
		}
		return bgSignatureType;
	}
	
	public JComboBox getJcbCertifyingPerms()
	{
		if (jcbCertifyingPerms == null)
		{
			jcbCertifyingPerms = new JComboBox(new String [] {PERM_NOCHANGES, PERM_FORMFILL_SIGNATURE, PERM_FORMFILL_SIGNATURE_COMMENTS});
			jcbCertifyingPerms.setBounds(new Rectangle((int)(175 * SampleUtil.getDPIScalingMultiplier()), (int)(370 * SampleUtil.getDPIScalingMultiplier()), (int)(250 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier())));
		}
		return jcbCertifyingPerms;
	}
	
	public JPanel getJpAppearance()
	{
		if (jpAppearance == null)
		{
			jpAppearance = new JPanel();
			jpAppearance.setBorder(BorderFactory.createTitledBorder("Appearance"));
			jpAppearance.setBounds(new Rectangle((int)(5 * SampleUtil.getDPIScalingMultiplier()), (int)(400 * SampleUtil.getDPIScalingMultiplier()), (int)(480 * SampleUtil.getDPIScalingMultiplier()), (int)(135 * SampleUtil.getDPIScalingMultiplier())));
			add(getJchbCity());
			add(getJchbCommonName());
			add(getJchbCountry());
			add(getJchbDate());
			add(getJchbEmail());
			add(getJchbName());
			add(getJchbOrgName());
			add(getJchbOrgUnit());
			add(getJchbSignedBy());
			add(getJchbState());
		}
		return jpAppearance;
	}

	public JCheckBox getJchbName()
	{
		if (jchbName == null)
		{
			jchbName = new JCheckBox("Name");
			jchbName.setBounds(new Rectangle((int)(30 * SampleUtil.getDPIScalingMultiplier()), (int)(425 * SampleUtil.getDPIScalingMultiplier()), (int)(100 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier())));
		}
		return jchbName;
	}

	public JCheckBox getJchbEmail()
	{
		if (jchbEmail == null)
		{
			jchbEmail = new JCheckBox("Email");
			jchbEmail.setBounds(new Rectangle((int)(300 * SampleUtil.getDPIScalingMultiplier()), (int)(475 * SampleUtil.getDPIScalingMultiplier()), (int)(120 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier())));
		}
		return jchbEmail;
	}

	public JCheckBox getJchbDate()
	{
		if (jchbDate == null)
		{
			jchbDate = new JCheckBox("Date");
			jchbDate.setBounds(new Rectangle((int)(300 * SampleUtil.getDPIScalingMultiplier()), (int)(450 * SampleUtil.getDPIScalingMultiplier()), (int)(120 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier())));
		}
		return jchbDate;
	}

	public JCheckBox getJchbCity()
	{
		if (jchbCity == null)
		{
			jchbCity = new JCheckBox("City/Locality");
			jchbCity.setBounds(new Rectangle((int)(30 * SampleUtil.getDPIScalingMultiplier()), (int)(450 * SampleUtil.getDPIScalingMultiplier()), (int)(100 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier())));
		}
		return jchbCity;
	}

	public JCheckBox getJchbState()
	{
		if (jchbState == null)
		{
			jchbState = new JCheckBox("State");
			jchbState.setBounds(new Rectangle((int)(30 * SampleUtil.getDPIScalingMultiplier()), (int)(475 * SampleUtil.getDPIScalingMultiplier()), (int)(100 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier())));
		}
		return jchbState;
	}

	public JCheckBox getJchbCommonName()
	{
		if (jchbCommonName == null)
		{
			jchbCommonName = new JCheckBox("Common Name");
			jchbCommonName.setBounds(new Rectangle((int)(150 * SampleUtil.getDPIScalingMultiplier()), (int)(425 * SampleUtil.getDPIScalingMultiplier()), (int)(120 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier())));
		}
		return jchbCommonName;
	}

	public JCheckBox getJchbOrgName()
	{
		if (jchbOrgName == null)
		{
			jchbOrgName = new JCheckBox("Organization Name");
			jchbOrgName.setBounds(new Rectangle((int)(150 * SampleUtil.getDPIScalingMultiplier()), (int)(450 * SampleUtil.getDPIScalingMultiplier()), (int)(120 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier())));
		}
		return jchbOrgName;
	}

	public JCheckBox getJchbOrgUnit()
	{
		if (jchbOrgUnit == null)
		{
			jchbOrgUnit = new JCheckBox("Organizational Unit");
			jchbOrgUnit.setBounds(new Rectangle((int)(150 * SampleUtil.getDPIScalingMultiplier()), (int)(475 * SampleUtil.getDPIScalingMultiplier()), (int)(120 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier())));
		}
		return jchbOrgUnit;
	}

	public JCheckBox getJchbSignedBy()
	{
		if (jchbSignedBy == null)
		{
			jchbSignedBy = new JCheckBox("Digitally Signed By");
			jchbSignedBy.setBounds(new Rectangle((int)(300 * SampleUtil.getDPIScalingMultiplier()), (int)(425 * SampleUtil.getDPIScalingMultiplier()), (int)(120 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier())));
		}
		return jchbSignedBy;
	}

	public JCheckBox getJchbCountry()
	{
		if (jchbCountry == null)
		{
			jchbCountry = new JCheckBox("Country");
			jchbCountry.setBounds(new Rectangle((int)(30 * SampleUtil.getDPIScalingMultiplier()), (int)(500 * SampleUtil.getDPIScalingMultiplier()), (int)(200 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier())));
		}
		return jchbCountry;
	}
	
}
