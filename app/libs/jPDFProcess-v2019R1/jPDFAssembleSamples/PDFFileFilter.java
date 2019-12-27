/*
 * Created on Dec 8, 2004
 *
 */
package jPDFAssembleSamples;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * @author Qoppa Software
 *
 */
public class PDFFileFilter extends FileFilter
{
    private final static String PDF_EXTENSION = "pdf";
	
/**
 * PDFFileFilter constructor comment.
 */
public PDFFileFilter() 
{
	super();
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
	    if (PDF_EXTENSION.equalsIgnoreCase (getExtension(file)))
	    {
	        return true;
	    }
	}
	
	return false;
}

public String getDescription()
{
    return "*.pdf";
}
public static String getExtension(File f) 
{
       String ext = null;
       String s = f.getName();
       int i = s.lastIndexOf('.');

       if (i > 0 &&  i < s.length() - 1) 
       {
           ext = s.substring(i+1).toLowerCase();
       }
       
       return ext;
}}
