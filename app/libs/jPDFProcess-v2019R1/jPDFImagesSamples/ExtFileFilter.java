/*
 * Created on Dec 8, 2004
 *
 */
package jPDFImagesSamples;

import javax.swing.filechooser.FileFilter;

/**
 * @author Gerald Holmann
 *
 */
public class ExtFileFilter extends FileFilter
{
    private String [] m_FileExtension;
    private String m_Description;
	
    /**
     * PDFFileFilter constructor comment.
     */
    public ExtFileFilter(String fileExt) 
    {
        m_FileExtension = new String [] {fileExt};
        m_Description = "*" + fileExt;
    }
    
    public ExtFileFilter (String [] fileExts)
    {
        m_FileExtension = fileExts;
        
        StringBuffer desc = new StringBuffer();
        for (int count = 0; count < fileExts.length; ++count)
        {
            if (count > 0)
            {
                desc.append(",");
            }
            desc.append ("*");
            desc.append (fileExts [count]);
        }
        m_Description = desc.toString();
    }

	/**
	 * Tests if a specified file should be included in a file list.
	 *
	 * @param   dir    the directory in which the file was found.
	 * @param   name   the name of the file.
	 * @return  <code>true</code> if and only if the name should be
	 * included in the file list; <code>false</code> otherwise.
	 */
    public boolean accept(java.io.File file) 
    {
    	if(file.isDirectory())
    	{
    		return true;
    	}
    	else if(file != null)
    	{
            for (int count = 0; count < m_FileExtension.length; ++count)
            {
                if (file.getName().toLowerCase().endsWith(m_FileExtension [count]))
                {
                    return true;
                }
            }
    	}
    	
    	return false;
    }
    
    public String getDescription()
    {
        return m_Description;
    }
}
