package jPDFProcessSamples.cli;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.qoppa.pdf.PDFException;
import com.qoppa.pdf.TIFFOptions;
import com.qoppa.pdfProcess.PDFDocument;

public class PDFFunction_ConvertToTIFF implements PDFFunction
{
	private String mTIFFFile;
	private String mTIFFCompression;
	private int mDPI;
	
	private final static String COMP_LZW = "LZW";
	private final static String COMP_GROUPIV = "GroupIV";
	private final static String COMP_GROUP4 = "Group4";
	private final static String COMP_GROUPIII = "GroupIII";
	private final static String COMP_GROUP3 = "Group3";
	private final static String COMP_PACKBITS = "Packbits";
	
	public PDFFunction_ConvertToTIFF (String tiffFileName, String tiffCompression, int dpi)
	{
		mTIFFFile = tiffFileName;
		mTIFFCompression = tiffCompression;
		mDPI = dpi;
	}
	
	public void perform(PDFDocument doc) throws PDFException, IOException
	{
		BufferedOutputStream outStream = null;
		
		try
		{
			// create output stream
			outStream = new BufferedOutputStream(new FileOutputStream(mTIFFFile));
			
			// Create TIFF options
			TIFFOptions options = new TIFFOptions(mDPI, getCompression(mTIFFCompression));
			doc.saveDocumentAsTIFF(outStream, options);
		}
		finally
		{
			if (outStream != null)
			{
				outStream.close();
			}
		}
	}
	
	private String getCompression(String compName) throws PDFException
	{
		if (COMP_LZW.equalsIgnoreCase(mTIFFCompression))
			return TIFFOptions.TIFF_LZW;
		else if (COMP_GROUPIV.equalsIgnoreCase(mTIFFCompression) || COMP_GROUP4.equalsIgnoreCase(mTIFFCompression))
			return TIFFOptions.TIFF_FAX_GROUP4;
		else if (COMP_GROUPIII.equalsIgnoreCase(mTIFFCompression) || COMP_GROUP3.equalsIgnoreCase(mTIFFCompression))
			return TIFFOptions.TIFF_FAX_GROUP3;
		else if (COMP_PACKBITS.equalsIgnoreCase(mTIFFCompression))
			return TIFFOptions.TIFF_PACKBITS;
		
		else throw new PDFException("Invalid TIFF compression name: " + compName);
	}
}
