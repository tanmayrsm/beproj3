package jPDFFieldsSamples;

import java.io.IOException;

import com.qoppa.pdf.PDFException;
import com.qoppa.pdfFields.PDFFields;


public class PDFFieldsCLI
{
	public static void main(String[] args)
	{
		// Parse the arguments
		JobInfo jobInfo = parseArgs(args);
		
		// Check for the key
		if (jobInfo.m_LicenseKey != null)
		{
			PDFFields.setKey(jobInfo.m_LicenseKey);
		}
		
		try
		{
			// load the document
			PDFFields pdfDoc = new PDFFields(jobInfo.m_InputPDFFile, null);
			
			// import either fdf or xfdf data
			if ("FDF".equalsIgnoreCase(jobInfo.m_Format))
			{
				pdfDoc.importFDF(jobInfo.m_InputFieldFile);
			}
			else if ("XFDF".equalsIgnoreCase(jobInfo.m_Format))
			{
				pdfDoc.importXFDF(jobInfo.m_InputFieldFile);
			}
			else if ("XDP".equalsIgnoreCase(jobInfo.m_Format))
			{
			    pdfDoc.importXDP(jobInfo.m_InputFieldFile);
			}
			
			// flatten fields
			if (jobInfo.m_Flatten == true)
			{
				pdfDoc.flattenFields(jobInfo.m_PaintButtons, jobInfo.m_PaintNonPrintable);
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
		private String m_InputFieldFile;
		private String m_Format;
		private String m_OutputFileName;
		private String m_LicenseKey;
		private boolean m_Flatten = false;
		private boolean m_PaintButtons = false;
		private boolean m_PaintNonPrintable = false;
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
			if ("-inputpdf".equalsIgnoreCase(args[ix]))
			{
				jobInfo.m_InputPDFFile = args[ix + 1];
				ix += 2;
			}
			else if ("-inputfields".equalsIgnoreCase(args[ix]))
			{
				jobInfo.m_InputFieldFile = args[ix + 1];
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
			else if ("-flatten".equalsIgnoreCase(args[ix]))
			{
				jobInfo.m_Flatten = true;
				ix += 1;
			}
			else if ("-paintbuttons".equalsIgnoreCase(args[ix]))
			{
				jobInfo.m_PaintButtons = true;
				ix += 1;
			}
			else if ("-paintnonprintable".equalsIgnoreCase(args[ix]))
			{
				jobInfo.m_PaintNonPrintable = true;
				ix += 1;
			}
			else if ("-outputfile".equalsIgnoreCase(args[ix]))
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
		System.out.println("Usage: -inputpdf <filename> -inputfields <filename> -format <FDF|XFDF|XDP>");
		System.out.println();
		System.out.println("Optional flags:");
		System.out.println("-outputfile <filename>  Name of the outputfile, if not present,");
		System.out.println("                        original input will be overwritten");
		System.out.println("-flatten                Flatten the fields after importing");
		System.out.println("-paintbuttons           By default, when flattening fields,");
		System.out.println("                        push buttons are not painted. This flag");
		System.out.println("                        forces them to be painted.");
		System.out.println("-paintnonprintable      By default, when flattening fields,");
		System.out.println("                        non-printable fields are not painted.");
		System.out.println("                        This flag forces them to be painted.");
		System.out.println("-key <key>              License key to run in production mode");
	}
}
