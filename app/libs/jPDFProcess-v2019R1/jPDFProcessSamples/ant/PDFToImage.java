package jPDFProcessSamples.ant;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.qoppa.pdf.IPassword;
import com.qoppa.pdf.PDFException;
import com.qoppa.pdf.PDFPassword;
import com.qoppa.pdf.TIFFOptions;
import com.qoppa.pdfProcess.PDFDocument;

/**
 * Sample implementation of an ANT taks that uses jPDFProcess to convert any page in a PDF to an image, in
 * JPEG, PNG or TIFF formats.
 */
public class PDFToImage extends Task
{
	private int mPageIndex = 0;
	private int mOutputDPI = 150;
	
	private final static String FORMAT_JPEG_1 = "jpg";
	private final static String FORMAT_JPEG_2 = "jpeg";
	private final static String FORMAT_PNG = "png";
	private final static String FORMAT_TIFF_1 = "tiff";
	private final static String FORMAT_TIFF_2 = "tif";
	private String mFormat;
	
	private String mInputFile;
	private String mPassword;
	private String mOutputFile;

	public void execute() throws BuildException 
	{
		try
		{
			// Open the document
			IPassword password = null;
			if (mPassword != null)
			{
				password = new PDFPassword(mPassword);
			}
			PDFDocument pdf = new PDFDocument(mInputFile, password);

			// Validate page
			if (mPageIndex < 0 || mPageIndex >= pdf.getPageCount())
			{
				throw new BuildException("Invalid page number: " + mPageIndex);
			}
			
			// Save as JPEG
			if (FORMAT_JPEG_1.equalsIgnoreCase(mFormat) || FORMAT_JPEG_2.equalsIgnoreCase(mFormat))
			{
				pdf.getPage(mPageIndex).savePageAsJPEG(new FileOutputStream(mOutputFile), mOutputDPI, 0.8f);
			}
			// Save as PNG
			else if (FORMAT_PNG.equalsIgnoreCase(mFormat))
			{
				pdf.getPage(mPageIndex).savePageAsPNG(new FileOutputStream(mOutputFile), mOutputDPI);
			}
			// Save as TIFF
			else if (FORMAT_TIFF_1.equalsIgnoreCase(mFormat) || FORMAT_TIFF_2.equalsIgnoreCase(mFormat))
			{
				pdf.getPage(mPageIndex).savePageAsTIFF(new FileOutputStream(mOutputFile), mOutputDPI, TIFFOptions.TIFF_PACKBITS);
			}
			else
			{
				throw new BuildException("Unrecognized output format: " + mFormat);
			}
		} 
		catch(PDFException pdfE)
		{
			throw new BuildException(pdfE);
		}
		catch(IOException ioe)
		{
			throw new BuildException(ioe);
		}
	}

	public void setOutputDPI(int outputDPI)
	{
		mOutputDPI = outputDPI; 
	}
	
	public void setPageIndex(int pageIndex)
	{
		mPageIndex = pageIndex;
	}
	
	public void setFormat(String format)
	{
		mFormat = format;
	}
	
	public void setInputFile(String fileName)
	{
		mInputFile = fileName;
	}
	
	public void setPassword(String password)
	{
		mPassword = password;
	}
	
	public void setOutputFile(String fileName)
	{
		mOutputFile = fileName;
	}
}
