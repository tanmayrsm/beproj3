/*
 * Created on Sep 21, 2006
 *
 */
package jPDFProcessSamples;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JOptionPane;

public class MergeDialogController implements ActionListener
{
    // Return codes
    public final static int RC_OK = 0;
    public final static int RC_CANCEL = -1;
    private int m_RC = RC_CANCEL;
    
    // Dialog
    private MergeDialog m_Dialog;
    
    // Output values
    public File m_Document1;
    public File m_Document2;
    public File m_OutputDocument;
    
    public int showDialog (Frame parent, String title)
    {
        // Create the dialog
        m_Dialog = new MergeDialog (parent);
        m_Dialog.setTitle(title);
        SampleUtil.centerDialog (parent, m_Dialog);
        
        // Add listeners
        m_Dialog.getJbGo().addActionListener(this);
        m_Dialog.getJbCancel().addActionListener(this);
        m_Dialog.getJbBrowseDocument1().addActionListener(this);
        m_Dialog.getJbBrowseDocument2().addActionListener(this);
        m_Dialog.getJbBrowseOutputDocument().addActionListener(this);
        
        m_Dialog.setVisible(true);
        
        return m_RC;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == m_Dialog.getJbGo())
        {
            // get values
            String doc1Path = m_Dialog.getJtfDocument1().getText();
            String doc2Path = m_Dialog.getJtfDocument2().getText();
            String outDocPath = m_Dialog.getJtfOutputDocument().getText();
            
            // Check that the user entered something in the text fields
            if (doc1Path.trim().length() == 0)
            {
                JOptionPane.showMessageDialog(m_Dialog, "You must enter a file name for the first document.");
                return;
            }
            if (doc2Path.trim().length() == 0)
            {
                JOptionPane.showMessageDialog(m_Dialog, "You must enter a file name for the second document.");
                return;
            }
            if (outDocPath.trim().length() == 0)
            {
                JOptionPane.showMessageDialog(m_Dialog, "You must enter a file name for the output document.");
                return;
            }
            
            // Create File objects
            m_Document1 = new File (doc1Path);
            m_Document2 = new File (doc2Path);
            m_OutputDocument = new File (outDocPath);
            
            // Check for existing files
            if (m_Document1.exists() == false)
            {
                JOptionPane.showMessageDialog (m_Dialog, m_Document1.getName() + " is not a valid file.");
                return;
            }
            if (m_Document2.exists() == false)
            {
                JOptionPane.showMessageDialog (m_Dialog, m_Document2.getName() + " is not a valid file.");
                return;
            }
            if (m_OutputDocument.exists() == true)
            {
                int rc = JOptionPane.showConfirmDialog (m_Dialog, "Overwrite " + m_OutputDocument.getName() + "?", "Overwrite?", JOptionPane.YES_NO_OPTION);
                if (rc != JOptionPane.YES_OPTION)
                {
                    return;
                }                
            }

            // Return code and dispose dialog
            m_RC = RC_OK;
            m_Dialog.dispose();
        }
        else if (e.getSource() == m_Dialog.getJbBrowseDocument1())
        {
            File f = SampleUtil.getFile (m_Dialog, SampleUtil.OPEN_FILE, new PDFFileFilter());
            if (f != null)
            {
                m_Dialog.getJtfDocument1().setText (f.getAbsolutePath());
            }
        }
        else if (e.getSource() == m_Dialog.getJbBrowseDocument2())
        {
            File f = SampleUtil.getFile (m_Dialog, SampleUtil.OPEN_FILE, new PDFFileFilter());
            if (f != null)
            {
                m_Dialog.getJtfDocument2().setText (f.getAbsolutePath());
            }
        }
        else if (e.getSource() == m_Dialog.getJbBrowseOutputDocument())
        {
            File f = SampleUtil.getFile (m_Dialog, SampleUtil.SAVE_FILE, new PDFFileFilter());
            if (f != null)
            {
                m_Dialog.getJtfOutputDocument().setText (f.getAbsolutePath());
            }
        }
        else if (e.getSource() == m_Dialog.getJbCancel())
        {
            m_RC = RC_CANCEL;
            m_Dialog.dispose();
        }
    }
}
