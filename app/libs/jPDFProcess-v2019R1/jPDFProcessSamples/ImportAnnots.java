package jPDFProcessSamples;

import java.io.IOException;

import com.qoppa.pdf.PDFException;
import com.qoppa.pdfProcess.PDFDocument;

/**
 * This is a command line program to demonstrate importing annotations from an FDF or XFDF
 * file into a PDF document.
 * 
 * @author Qoppa Software
 *
 */
public class ImportAnnots extends FDFFileFilter
{
	public static void main(String[] args)
	{
		// Parse the arguments
		JobInfo jobInfo = parseArgs(args);
		
		// Check for the key
		if (jobInfo.m_LicenseKey != null)
		{
			PDFDocument.setKey(jobInfo.m_LicenseKey);
		}
		
		try
		{
			// load the document
			PDFDocument pdfDoc = new PDFDocument(jobInfo.m_InputPDFFile, null);
			
			// import either fdf or xfdf data
			if ("FDF".equalsIgnoreCase(jobInfo.m_Format))
			{
				pdfDoc.importAnnotsFDF(jobInfo.m_InputAnnotsFile);
			}
			else if ("XFDF".equalsIgnoreCase(jobInfo.m_Format))
			{
				pdfDoc.importAnnotsXFDF(jobInfo.m_InputAnnotsFile);
			}
			
			// save the document
			if (jobInfo.m_OutputFileName != null && jobInfo.m_OutputFileName.length() > 0)
			{
				pdfDoc.saveDocument(jobInfo.m_OutputFileName);
			}
			else
			{
				pdfDoc.saveDocument(jobInfo.m_InputPDFFile);
			}
		}
		catch (PDFException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private static class JobInfo
	{
		private String m_InputPDFFile;
		private String m_InputAnnotsFile;
		private String m_Format;
		private String m_OutputFileName;
		private String m_LicenseKey;
	}

	private static JobInfo parseArgs(String[] args)
	{
		// Require at least -inputpdf <filename>, inputfields <filename>, and -format <format>
		if (args.length < 6)
		{
			printUsage();
			System.exit(0);
		}

		// Create new job info
		JobInfo jobInfo = new JobInfo();

		// Loop through the arguments
		int ix = 0;
		while (ix < args.length)
		{
			if ("-pdf".equalsIgnoreCase(args[ix]))
			{
				jobInfo.m_InputPDFFile = args[ix + 1];
				ix += 2;
			}
			else if ("-annots".equalsIgnoreCase(args[ix]))
			{
				jobInfo.m_InputAnnotsFile = args[ix + 1];
				ix += 2;
			}
			else if ("-format".equalsIgnoreCase(args[ix]))
			{
				jobInfo.m_Format = args[ix + 1];
				ix += 2;
			}
			else if ("-key".equalsIgnoreCase(args[ix]))
			{
				jobInfo.m_LicenseKey = args[ix + 1];
				ix += 2;
			}
			else if ("-output".equalsIgnoreCase(args[ix]))
			{
				jobInfo.m_OutputFileName = args[ix + 1];
				ix += 2;
			}
			else
			{
				throw new RuntimeException("Unrecognized command line option: " + args[ix]);
			}
		}

		return jobInfo;
	}

	private static void printUsage()
	{
		System.out.println("Usage: ImportAnnots -pdf <filename> -annots <filename> -format <FDF|XFDF>");
		System.out.println();
		System.out.println("Flags:");
		System.out.println("-pdf <filename>			Name of the input PDF file");
		System.out.println("-annots <filename>		Name of the file containing annotations");
		System.out.println("-format <FDF|XFDF>		Format of the input file, either FDF or XFDF");
		System.out.println("-output <filename>  	Name of the output file, if not present,");
		System.out.println("                        original input will be overwritten");
		System.out.println("-key <key>              License key to run in production mode");
	}

}
