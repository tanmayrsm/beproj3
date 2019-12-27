package jPDFSecureSamples;

import java.awt.Font;
import java.awt.Frame;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.qoppa.pdf.permissions.AllPDFPermissions;
import com.qoppa.pdf.permissions.Restrictions;

public class PermissionsDialog extends JDialog
{
	private static final Font DIALOG_FONT = new java.awt.Font("Dialog", java.awt.Font.PLAIN, (int)SampleUtil.getScaledFont(new JLabel().getFont().getSize(), 12));
	
	public static final String PASSWORD_SPAN = "<span style=\"color:red\">*</span>"; 
	public static final String UR_SPAN = "<span style=\"color:purple\">&dagger</span>";
	public static final String DOCMDP_SPAN = "<span style=\"color:blue\">&Dagger</span>";
	public static final String SIGNATURE_SPAN = "<span style=\"color:green\">§</span>";
	
	private JPanel jpPermissions;
	private JPanel jpKey;
	private JLabel jlPrinting = null;
	private JLabel jlCopying = null;
    private JLabel jlCopyingForAccessibility = null;
	private JLabel jlChanges = null;
	private JLabel jlAnnotations = null;
    private JLabel jlFillingFormFields = null;
    private JLabel jlPrintingHighRes = null;
    private JLabel jlAssemblingDoc = null;
    private AllPDFPermissions permissions = null;
    private Frame parent;
    
	public PermissionsDialog(Frame parent, AllPDFPermissions permissions) {
		super(parent);
		this.parent = parent;
		this.permissions = permissions;
		this.initialize();	
	}
	
	private void initialize() {
        this.setModal(true);
        JPanel contentPane = getBoxPanel(BoxLayout.Y_AXIS);
        contentPane.add(getJpPermissions());
        contentPane.add(getJPKey());
        this.setContentPane(contentPane);
        this.displaySecurityInfo();
        this.setTitle("Permissions");
        this.pack();
        this.setLocationRelativeTo(parent);
	}
	
	/**
	 * This method initializes jpPermissions
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJpPermissions()
	{
		if (jpPermissions == null)
		{
			jpPermissions = getBoxPanel(BoxLayout.X_AXIS);
			jpPermissions.setBorder(BorderFactory.createTitledBorder("Permissions"));
			
			JPanel leftLabels = getBoxPanel(BoxLayout.Y_AXIS);
			JPanel leftPermissions = getBoxPanel(BoxLayout.Y_AXIS);
			JPanel rightLabels = getBoxPanel(BoxLayout.Y_AXIS);
			JPanel rightPermissions = getBoxPanel(BoxLayout.Y_AXIS);
			
			leftLabels.add(getLabel("Printing High Res:"));
			leftLabels.add(getLabel("Printing:"));
			leftLabels.add(getLabel("Text & Graphics Extraction:"));
			leftLabels.add(getLabel("Extraction for Accessibility:"));
			
			leftPermissions.add(getjlAllowPrintingHighRes());
			leftPermissions.add(getjlAllowPrinting());
			leftPermissions.add(getjlAllowCopy());
			leftPermissions.add(getjlAllowCopyForAccessiibility());
			
			rightLabels.add(getLabel("Change Document:"));
			rightLabels.add(getLabel("Document Assembly:"));
			rightLabels.add(getLabel("Annotations & Comments:"));
			rightLabels.add(getLabel("Form Filling & Signing:"));

			rightPermissions.add(getjlAllowChange());
			rightPermissions.add(getjlAllowAssembleDoc());
			rightPermissions.add(getjlAllowAnnotations());
			rightPermissions.add(getjlAllowFillFormFields());
			
			jpPermissions.add(leftLabels);
			jpPermissions.add(leftPermissions);
			jpPermissions.add(new JSeparator(SwingConstants.VERTICAL));
			jpPermissions.add(rightLabels);
			jpPermissions.add(rightPermissions);
		}
		return jpPermissions;
	}
	
	private JPanel getJPKey()
	{
		if (jpKey == null)
		{
			jpKey = getBoxPanel(BoxLayout.Y_AXIS);
			jpKey.add(getLabel("<html>" + PASSWORD_SPAN + " Document Security</html>"));
			jpKey.add(getLabel("<html>" + UR_SPAN + " Usage Rights</html>"));
			jpKey.add(getLabel("<html>" + DOCMDP_SPAN + " Certifying Signature</html>"));
			jpKey.add(getLabel("<html>" + SIGNATURE_SPAN + " Digital Signature</html>"));
		}
		return jpKey;
	}
	
	private JLabel getLabel(String text)
	{
		JLabel label = new JLabel(text);
		label.setFont(DIALOG_FONT);
		return label;
	}

	private JPanel getBoxPanel(int axis)
	{
		JPanel panel = new JPanel();
		BoxLayout layout = new BoxLayout(panel, axis);
		panel.setLayout(layout);
		panel.setAlignmentX(LEFT_ALIGNMENT);
		panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		return panel;
	}
	
	private void displaySecurityInfo()
	{
	    setPermissionText(permissions.getPrintRestrictions(false), getjlAllowPrinting());
	    setPermissionText(permissions.getPrintHighResRestrictions(false), getjlAllowPrintingHighRes());
	    setPermissionText(permissions.getChangeDocumentRestrictions(false), getjlAllowChange());
	    setPermissionText(permissions.getAssembleDocumentRestrictions(false), getjlAllowAssembleDoc());
	    setPermissionText(permissions.getExtractTextGraphicsRestrictions(false), getjlAllowCopy());
	    setPermissionText(permissions.getExtractTextGraphicsForAccessibilityRestrictions(false), getjlAllowCopyForAccessiibility());
	    setPermissionText(permissions.getModifyAnnotsRestrictions(false), getjlAllowAnnotations());
	    setPermissionText(permissions.getFillFormFieldsRestrictions(false), getjlAllowFillFormFields());
	}
	
	private void setPermissionText(Restrictions restrictions, JLabel label)
	{
		if (!restrictions.isRestricted())
		{
			label.setText("Allowed");
		}
		else
		{
			label.setText("<html>Not Allowed " + getHTML(restrictions) + "</html>");
			// Without this line the label gets cut off - seems like there's something funky with having a space in the html
			label.setMinimumSize(label.getPreferredSize());
		}
	}
	
	/**
	 * Returns the HTML symbol representation of this permission.  This string does not include opening and closing HTML tags.
	 * @return
	 */
	public String getHTML(Restrictions restrictions)
	{
		StringBuffer html = new StringBuffer();
		if(restrictions.isRestrictedByPasswordPermissions())
		{
			html.append(PASSWORD_SPAN);
		}
		if (restrictions.isRestrictedByUsageRightsPermissions())
		{
			html.append(UR_SPAN);
		}		
		if (restrictions.isRestrictedByDocMDPPermissions())
		{
			html.append(DOCMDP_SPAN);
		}
		if (restrictions.isRestrictedBySignature())
		{
			html.append(SIGNATURE_SPAN);
		}
		
		return html.toString();
	}
	
	private JLabel getjlAllowPrinting() {
		if (jlPrinting == null) {
			jlPrinting = getLabel("N/A");
		}
		return jlPrinting;
	}
	
	private JLabel getjlAllowCopy() {
		if (jlCopying == null) {
			jlCopying = getLabel("N/A");
		}
		return jlCopying;
	}
	
    private JLabel getjlAllowFillFormFields() {
        if (jlFillingFormFields == null) {
            jlFillingFormFields = getLabel("N/A");
        }
        return jlFillingFormFields;
    }
    
    private JLabel getjlAllowAssembleDoc() {
        if (jlAssemblingDoc == null) {
            jlAssemblingDoc = getLabel("N/A");
        }
        return jlAssemblingDoc;
    }
    
    private JLabel getjlAllowPrintingHighRes() {
        if (jlPrintingHighRes == null) {
            jlPrintingHighRes = getLabel("N/A");
        }
        return jlPrintingHighRes;
    }

	private JLabel getjlAllowChange() {
		if (jlChanges == null) {
			jlChanges = getLabel("N/A");
		}
		return jlChanges;
	}
	
    private JLabel getjlAllowCopyForAccessiibility() {
        if (jlCopyingForAccessibility == null) {
            jlCopyingForAccessibility = getLabel("N/A");
        }
        return jlCopyingForAccessibility;
    }
    
	private JLabel getjlAllowAnnotations() {
		if (jlAnnotations == null) {
			jlAnnotations = getLabel("N/A");
		}
		return jlAnnotations;
	}

}
