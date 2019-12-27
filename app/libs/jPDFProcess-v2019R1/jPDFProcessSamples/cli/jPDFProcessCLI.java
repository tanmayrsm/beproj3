package jPDFProcessSamples.cli;

import java.io.IOException;

import com.qoppa.pdf.PDFException;
import com.qoppa.pdfProcess.PDFDocument;

/**
 * Command line implementation of PDF functions using jPDFProcess.
 * 
 * Usage: java -jar PDFTool.jar  <options> <inputFile> <outputFile><br>
 * <br>
 * Options:
 *    -input <filename> 
 *    -output <filename>
 *    -lickey <lic key>
 *    
 *    -convertPdfToTif <Lzw|Group IV>
 *    -removePage	<PageNo>
 *    -extractPage	<PageNo>
 *    -insertFile	<fileToBeInserted>  <PageNo>
 *    -rotatePage	<PageNo> <90|180|270>
 *    -appendFile	<fileToBeAppended>
 *    
 *    @auth Qoppa Software
 *
 */
public class jPDFProcessCLI
{
	public static void main(String[] args)
	{
		try
		{
			// Parse the arguments
			JobInfo jobInfo = parseArgs(args);
			jobInfo.validate();

			// Check for the key
			if (jobInfo.mLicenseKey != null)
			{
				PDFDocument.setKey(jobInfo.mLicenseKey);
			}
			
			// load the document
			PDFDocument pdfDoc = new PDFDocument(jobInfo.mInputFile, null);
			
			// Perform functions
			for (int count = 0; count < jobInfo.mFunctions.size(); ++count)
			{
				((PDFFunction)jobInfo.mFunctions.get(count)).perform(pdfDoc);
			}

			// Save the output
			if (pdfDoc.isDocumentModified())
				pdfDoc.saveDocument(jobInfo.mOutputFile);
		}
		catch(CLIException cliE)
		{
			cliE.printStackTrace();
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
	
	private static JobInfo parseArgs(String[] args) throws CLIException
	{
		if (args.length < 2)
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
			if ("-input".equalsIgnoreCase(args[ix]))
			{
				if (args.length <= ix+1)
					throw new CLIException("Not enough arguments");
				jobInfo.mInputFile = args[ix+1];
				ix += 2;
			}
			else if ("-output".equalsIgnoreCase(args[ix]))
			{
				jobInfo.mOutputFile = args[ix+1];
				ix += 2;
			}
			else if ("-lickey".equalsIgnoreCase(args[ix]))
			{
				jobInfo.mLicenseKey = args[ix+1];
				ix += 2;
			}
			else if ("-removePage".equalsIgnoreCase(args[ix]))
			{
				try
				{
					int pageIndex = Integer.parseInt(args[ix+1])-1;
					jobInfo.addFunction(new PDFFunction_RemovePage(pageIndex));
				}
				catch(NumberFormatException ne)
				{
					throw new CLIException("Error parsing page index: " + args[ix+1]);
				}
				ix += 2;
			}
			else if ("-extractPage".equalsIgnoreCase(args[ix]))
			{
				try
				{
					int pageIndex = Integer.parseInt(args[ix+1])-1;
					jobInfo.addFunction(new PDFFunction_ExtractPage(pageIndex));
				}
				catch(NumberFormatException ne)
				{
					throw new CLIException("Error parsing page index: " + args[ix+1]);
				}
				ix += 2;
			}
			else if ("-insertFile".equalsIgnoreCase(args[ix]))
			{
				if (args.length <= ix+2)
					throw new CLIException("Not enough arguments");
				
				try
				{
					String fileName = args[ix+1];
					int pageIndex = Integer.parseInt(args[ix+2])-1;
					jobInfo.addFunction(new PDFFunction_InsertFile(fileName, pageIndex));
				}
				catch(NumberFormatException ne)
				{
					throw new CLIException("Error parsing page index: " + args[ix+2]);
				}
				ix += 3;
			}
			else if ("-appendFile".equalsIgnoreCase(args[ix]))
			{
				if (args.length <= ix+1)
					throw new CLIException("Not enough arguments");
				
				try
				{
					String fileName = args[ix+1];
					jobInfo.addFunction(new PDFFunction_AppendFile(fileName));
				}
				catch(NumberFormatException ne)
				{
					throw new CLIException("Error parsing page index: " + args[ix+2]);
				}
				ix += 2;
			}
			else if ("-rotatePage".equalsIgnoreCase(args[ix]))
			{
				if (args.length <= ix+2)
					throw new CLIException("Not enough arguments");
				
				try
				{
					int pageIndex = Integer.parseInt(args[ix+1])-1;
					int rotDegrees = Integer.parseInt(args[ix+2]);
					jobInfo.addFunction(new PDFFunction_RotatePage(pageIndex, rotDegrees));
				}
				catch(NumberFormatException ne)
				{
					throw new CLIException("Error parsing page index: " + args[ix+2]);
				}
				ix += 3;
			}
			else if ("-convertPDFToTIFF".equalsIgnoreCase(args[ix]))
			{
				if (args.length <= ix+2)
					throw new CLIException("Not enough arguments");

				try
				{
					String tiffFile = args[ix+1];
					String tiffComp = args[ix+2];
					int dpi = Integer.parseInt(args[ix+3]);
					jobInfo.addFunction(new PDFFunction_ConvertToTIFF(tiffFile, tiffComp, dpi));
				}
				catch(NumberFormatException ne)
				{
					throw new CLIException("Error parsing DPI value: " + args[ix+3]);
				}
				
				ix += 4;
			}
			else
			{
				throw new CLIException("Unrecognized command line option: " + args[ix]);
			}
		}

		return jobInfo;
	}

	private static void printUsage()
	{
		System.out.println("Usage: -input <filename> -output <filename> <function>");
		System.out.println();
		System.out.println("Required arguments:");
		System.out.println("-input <filename>           Input file name.");
		System.out.println("-output <filename>          Output file name.");
		System.out.println("-lickey <key>               License key to run in production mode");
		System.out.println();
		System.out.println("<function> - One of the following:");
		System.out.println("-removePage <page number>                Remove a page from the PDF.");
		System.out.println("-extractPage <page number>               Outputs a document with one page, extracted from the original document.");
		System.out.println("-insertFile <file> <page number>         Inserts a file at the given page number.");
		System.out.println("-rotatePage <page number> <90|180|270>   Rotates a page by the given degrees.");
		System.out.println("-convertToTIFF <tiff file> <tiff comp> <dpi>   Convert PDF to a TIFF image.");
		System.out.println("-convertToPDFA <subformat>               Convert PDF to PDF/A.  Only 1b is support for subformat.");
	}
}
