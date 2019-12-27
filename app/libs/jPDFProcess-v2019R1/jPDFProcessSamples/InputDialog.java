/*
 * Created on Sep 21, 2006
 *
 */
package jPDFProcessSamples;

import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

public class InputDialog extends JDialog
{
    private JPanel jpContent = null;
    private JSeparator jSeparator = null;
    private JButton jbGo = null;
    private JButton jbCancel = null;
    private JTextField jtfDocument1 = null;
    private JTextField jtfOutputDocument = null;
    private JButton jbBrowseDocument1 = null;
    private JButton jbBrowseOutputDocument = null;

    /**
     * This method initializes 
     * 
     */
    public InputDialog() {
    	super();
    	initialize();
    }
    
    public InputDialog (Frame parent)
    {
        super (parent);
        initialize();
    }

    /**
     * This method initializes this
     * 
     */
    private void initialize() {
        this.setSize(new java.awt.Dimension((int)(404 * SampleUtil.getDPIScalingMultiplier()),(int)(220 * SampleUtil.getDPIScalingMultiplier())));
        this.setModal(true);
        this.setTitle("Merge PDF Documents");
        this.setContentPane(getJpContent());
    		
    }

    /**
     * This method initializes jpContent	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getJpContent()
    {
        if (jpContent == null)
        {
        	JLabel jlOutputDocument = new JLabel("Output Document:");
            jlOutputDocument.setBounds(new java.awt.Rectangle((int)(10 * SampleUtil.getDPIScalingMultiplier()),(int)(80 * SampleUtil.getDPIScalingMultiplier()),(int)(130 * SampleUtil.getDPIScalingMultiplier()),(int)(21 * SampleUtil.getDPIScalingMultiplier())));
            JLabel jlDocument1 = new JLabel("Input Document:");
            jlDocument1.setBounds(new java.awt.Rectangle((int)(10 * SampleUtil.getDPIScalingMultiplier()),(int)(20 * SampleUtil.getDPIScalingMultiplier()),(int)(130 * SampleUtil.getDPIScalingMultiplier()),(int)(21 * SampleUtil.getDPIScalingMultiplier())));
            jpContent = new JPanel();
            jpContent.setLayout(null);
            jpContent.add(jlDocument1, null);
            jpContent.add(getJSeparator(), null);
            jpContent.add(jlOutputDocument, null);
            jpContent.add(getJbGo(), null);
            jpContent.add(getJbCancel(), null);
            jpContent.add(getJtfDocument1(), null);
            jpContent.add(getJtfOutputDocument(), null);
            jpContent.add(getJbBrowseDocument1(), null);
            jpContent.add(getJbBrowseOutputDocument(), null);
        }
        return jpContent;
    }

    /**
     * This method initializes jSeparator	
     * 	
     * @return javax.swing.JSeparator	
     */
    private JSeparator getJSeparator()
    {
        if (jSeparator == null)
        {
            jSeparator = new JSeparator();
            jSeparator.setBounds(new java.awt.Rectangle((int)(20 * SampleUtil.getDPIScalingMultiplier()),(int)(60 * SampleUtil.getDPIScalingMultiplier()),(int)(340 * SampleUtil.getDPIScalingMultiplier()),(int)(12 * SampleUtil.getDPIScalingMultiplier())));
        }
        return jSeparator;
    }

    /**
     * This method initializes jbMerge	
     * 	
     * @return javax.swing.JButton	
     */
    public JButton getJbGo()
    {
        if (jbGo == null)
        {
            jbGo = new JButton("Go");
            jbGo.setBounds(new java.awt.Rectangle((int)(101 * SampleUtil.getDPIScalingMultiplier()),(int)(130 * SampleUtil.getDPIScalingMultiplier()),(int)(75 * SampleUtil.getDPIScalingMultiplier()),(int)(30 * SampleUtil.getDPIScalingMultiplier())));
        }
        return jbGo;
    }

    /**
     * This method initializes jbCancel	
     * 	
     * @return javax.swing.JButton	
     */
    public JButton getJbCancel()
    {
        if (jbCancel == null)
        {
            jbCancel = new JButton("Cancel");
            jbCancel.setBounds(new java.awt.Rectangle((int)(220 * SampleUtil.getDPIScalingMultiplier()),(int)(130 * SampleUtil.getDPIScalingMultiplier()),(int)(75 * SampleUtil.getDPIScalingMultiplier()),(int)(30 * SampleUtil.getDPIScalingMultiplier())));
        }
        return jbCancel;
    }

    /**
     * This method initializes jtfDocument1	
     * 	
     * @return javax.swing.JTextField	
     */
    public JTextField getJtfDocument1()
    {
        if (jtfDocument1 == null)
        {
            jtfDocument1 = new JTextField();
            jtfDocument1.setBounds(new java.awt.Rectangle((int)(150 * SampleUtil.getDPIScalingMultiplier()),(int)(20 * SampleUtil.getDPIScalingMultiplier()),(int)(180 * SampleUtil.getDPIScalingMultiplier()),(int)(20 * SampleUtil.getDPIScalingMultiplier())));
        }
        return jtfDocument1;
    }

    /**
     * This method initializes jTextField1	
     * 	
     * @return javax.swing.JTextField	
     */
    public JTextField getJtfOutputDocument()
    {
        if (jtfOutputDocument == null)
        {
            jtfOutputDocument = new JTextField();
            jtfOutputDocument.setBounds(new java.awt.Rectangle((int)(150 * SampleUtil.getDPIScalingMultiplier()),(int)(80 * SampleUtil.getDPIScalingMultiplier()),(int)(180 * SampleUtil.getDPIScalingMultiplier()),(int)(20 * SampleUtil.getDPIScalingMultiplier())));
        }
        return jtfOutputDocument;
    }

    /**
     * This method initializes jbBrowseDocument1	
     * 	
     * @return javax.swing.JButton	
     */
    public JButton getJbBrowseDocument1()
    {
        if (jbBrowseDocument1 == null)
        {
            jbBrowseDocument1 = new JButton("...");
            jbBrowseDocument1.setBounds(new java.awt.Rectangle((int)(335 * SampleUtil.getDPIScalingMultiplier()),(int)(20 * SampleUtil.getDPIScalingMultiplier()),(int)(25 * SampleUtil.getDPIScalingMultiplier()),(int)(20 * SampleUtil.getDPIScalingMultiplier())));
        }
        return jbBrowseDocument1;
    }

    /**
     * This method initializes jButton1	
     * 	
     * @return javax.swing.JButton	
     */
    public JButton getJbBrowseOutputDocument()
    {
        if (jbBrowseOutputDocument == null)
        {
            jbBrowseOutputDocument = new JButton("...");
            jbBrowseOutputDocument.setBounds(new java.awt.Rectangle((int)(335 * SampleUtil.getDPIScalingMultiplier()),(int)(80 * SampleUtil.getDPIScalingMultiplier()),(int)(25 * SampleUtil.getDPIScalingMultiplier()),(int)(20 * SampleUtil.getDPIScalingMultiplier())));
        }
        return jbBrowseOutputDocument;
    }
}  //  @jve:decl-index=0:visual-constraint="10,10"
