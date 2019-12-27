/*
 * Created on Sep 21, 2006
 *
 */
package jPDFAssembleSamples;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

public class InputDialogController implements ActionListener
{
    // Return codes
    public final static int RC_OK = 0;
    public final static int RC_CANCEL = -1;
    private int m_RC = RC_CANCEL;

    // Dialog
    private InputDialog m_Dialog;
    
    // Output values
    public File m_InputDocument;
    public File m_OutputDocument;
    
    // File filters
    public FileFilter m_InputFileFilter = new PDFFileFilter();
    public FileFilter m_OutputFileFilter = new PDFFileFilter();
    
    public int showDialog (Frame parent, String title)
    {
        // Create the dialog
        m_Dialog = new InputDialog (parent);
        m_Dialog.setTitle(title);
        SampleUtil.centerDialog (parent, m_Dialog);
        
        // Add listeners
        m_Dialog.getJbGo().addActionListener(this);
        m_Dialog.getJbCancel().addActionListener(this);
        m_Dialog.getJbBrowseDocument1().addActionListener(this);
        m_Dialog.getJbBrowseOutputDocument().addActionListener(this);
        
        m_Dialog.setVisible(true);
        
        return m_RC;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == m_Dialog.getJbGo())
        {
            // get values
            String inDoc = m_Dialog.getJtfDocument1().getText();
            String outDoc = m_Dialog.getJtfOutputDocument().getText();
            
            // Check that the user entered something in the text fields
            if (inDoc.trim().length() == 0)
            {
                JOptionPane.showMessageDialog(m_Dialog, "You must enter a file name for the first document.");
                return;
            }
            if (outDoc.trim().length() == 0)
            {
                JOptionPane.showMessageDialog(m_Dialog, "You must enter a file name for the output document.");
                return;
            }
            
            // Create File objects
            m_InputDocument = new File (inDoc);
            m_OutputDocument = new File (outDoc);
            
            // Check for existing files
            if (m_InputDocument.exists() == false)
            {
                JOptionPane.showMessageDialog (m_Dialog, m_InputDocument + " is not a valid file.");
                return;
            }
            if (m_OutputDocument.exists())
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
            File f = SampleUtil.getFile (m_Dialog, SampleUtil.OPEN_FILE, m_InputFileFilter);
            if (f != null)
            {
                m_Dialog.getJtfDocument1().setText (f.getAbsolutePath());
            }
        }
        else if (e.getSource() == m_Dialog.getJbBrowseOutputDocument())
        {
            File f = SampleUtil.getFile (m_Dialog, SampleUtil.SAVE_FILE, m_OutputFileFilter);
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
