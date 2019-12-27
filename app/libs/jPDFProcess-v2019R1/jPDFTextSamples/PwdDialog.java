/*
 * Created on Dec 8, 2004
 *
 */
package jPDFTextSamples;

import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

/**
 * @author Gerald Holmann
 *
 */
public class PwdDialog extends JDialog
{
    private JPanel jPanel = null;
	private JPasswordField jpfPassword = null;
    private JButton jbOk = null;
    private JButton jbCancel = null;
	
	protected String m_Password;
	
	/**
	 * This method initializes 
	 * 
	 */
	public PwdDialog() {
		super();
		initialize();
	}
	
	/**
	 * This method initializes 
	 * 
	 */
	public PwdDialog(Frame parentFrame) {
		super(parentFrame);
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() 
	{
        this.setModal(true);
        this.setTitle("Enter Password");
        this.setContentPane(getJPanel());
        this.setSize((int)(260 * SampleUtil.getDPIScalingMultiplier()), (int)(167 * SampleUtil.getDPIScalingMultiplier()));
        this.getRootPane().setDefaultButton(getJbOk());
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			JLabel jLabel = new JLabel("Please enter password:");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel.setBounds((int)(15 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier()), (int)(221 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier()));
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jLabel.getFont().getSize(), 12)));
			jPanel.add(jLabel, null);
			jPanel.add(getJpfPassword(), null);
			jPanel.add(getJbOk(), null);
			jPanel.add(getJbCancel(), null);
		}
		return jPanel;
	}
	/**
	 * This method initializes jpfPassword	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */    
	protected JPasswordField getJpfPassword() {
		if (jpfPassword == null) {
			jpfPassword = new JPasswordField();
			jpfPassword.setBounds((int)(15 * SampleUtil.getDPIScalingMultiplier()), (int)(43 * SampleUtil.getDPIScalingMultiplier()), (int)(221 * SampleUtil.getDPIScalingMultiplier()), (int)(20 * SampleUtil.getDPIScalingMultiplier()));
		}
		return jpfPassword;
	}
	
	public String getPassword ()
	{
	    return m_Password;
	}
	/**
	 * This method initializes jbOk	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJbOk() {
		if (jbOk == null) {
			jbOk = new JButton("Ok");
			jbOk.setBounds((int)(40 * SampleUtil.getDPIScalingMultiplier()), (int)(80 * SampleUtil.getDPIScalingMultiplier()), (int)(70 * SampleUtil.getDPIScalingMultiplier()), (int)(25 * SampleUtil.getDPIScalingMultiplier()));
			jbOk.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jbOk.getFont().getSize(), 12)));
			jbOk.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
				    m_Password = new String (getJpfPassword().getPassword());
				    if (m_Password.length() == 0)
				    {
				        m_Password = null;
				    }
				    dispose();
				}
			});
		}
		return jbOk;
	}
	/**
	 * This method initializes jbCancel	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJbCancel() {
		if (jbCancel == null) {
			jbCancel = new JButton("Cancel");
			jbCancel.setBounds((int)(133 * SampleUtil.getDPIScalingMultiplier()), (int)(80 * SampleUtil.getDPIScalingMultiplier()), (int)(70 * SampleUtil.getDPIScalingMultiplier()), (int)(25 * SampleUtil.getDPIScalingMultiplier()));
			jbCancel.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(jbCancel.getFont().getSize(), 12)));
			jbCancel.setMargin(new java.awt.Insets(2,2,2,2));
			jbCancel.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
				    m_Password = null;
				    dispose();
				}
			});
		}
		return jbCancel;
	}
    }
