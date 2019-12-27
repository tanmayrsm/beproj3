package jPDFPrintSamples.pdftops;

import java.io.File;
import java.io.FileOutputStream;

import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.SimpleDoc;
import javax.print.StreamPrintService;
import javax.print.StreamPrintServiceFactory;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaTray;
import javax.print.attribute.standard.Sides;

import com.qoppa.pdf.PDFPassword;
import com.qoppa.pdfPrint.PDFPrint;

public class PDFToPSCLI
{
    private static class JobInfo
    {
        public String mInputFile;
        public String mOutputFile;
        private PDFPassword mPassword;
        
        private String mLicenseKey;
    }

    public static void main (String [] args)
    {
        try
        {
        	// Parse the arguments
        	JobInfo jobInfo = parseArgs (args);
        	
            // Check for the key
            if (jobInfo.mLicenseKey != null)
            {
                PDFPrint.setKey (jobInfo.mLicenseKey);
            }

            // Load the document
            PDFPrint pdfPrint = new PDFPrint (jobInfo.mInputFile, jobInfo.mPassword);
            
        	// find the Postscript service
            DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
            String psMimeType = DocFlavor.BYTE_ARRAY.POSTSCRIPT.getMimeType();
            StreamPrintServiceFactory[] factories = StreamPrintServiceFactory.lookupStreamPrintServiceFactories(flavor, psMimeType);
 
            if(factories.length == 0)
            {
                System.err.println ("No PS factories available!");
                System.exit(0);
            }

            // Open the output file
            FileOutputStream fos = new FileOutputStream(jobInfo.mOutputFile);
 
            // Use the first service available
            StreamPrintService sps = factories[0].getPrintService(fos);
            DocPrintJob pj = sps.createPrintJob();

            // Define paper size
            PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
            //aset.add(MediaSizeName.NA_LETTER);
            aset.add(Sides.DUPLEX);
            aset.add(MediaTray.TOP);
 
            // Create simple doc using PDFPrint as Printable and print it
            SimpleDoc doc = new SimpleDoc(pdfPrint, flavor, null);
            pj.print(doc, aset);
            
            // Close the output PS stream
            fos.close();
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }
    
    private static JobInfo parseArgs (String [] args)
    {
        if (args.length == 0)
        {
            printUsage();
            System.exit (0);
        }

        // Create new job info
        JobInfo jobInfo = new JobInfo();
        
        // Loop through the arguments
        int ix = 0;
        while (ix < args.length)
        {
        	if ("-input".equalsIgnoreCase(args[ix]))
        	{
        		jobInfo.mInputFile = args[ix+1];
        		ix += 2;
        	}
        	else if ("-output".equalsIgnoreCase(args[ix]))
        	{
        		jobInfo.mOutputFile = args[ix+1];
        		ix += 2;
        	}
        	else if ("-pwd".equalsIgnoreCase(args [ix]))
            {
                jobInfo.mPassword = new PDFPassword (args [ix+1]);
                ix += 2;
            }
            else if ("-key".equalsIgnoreCase(args [ix]))
            {
                jobInfo.mLicenseKey = args [ix+1];
                ix += 2;
            }
            else
            {
                throw new RuntimeException ("Unrecognized command line option: " + args [ix]);
            }
        }
        
        // Check that we got an input file
        if (jobInfo.mInputFile == null)
        {
            throw new RuntimeException ("Missing input file.");
        }
        
        // Check that the input file exists and is not a folder
        File inputFile = new File (jobInfo.mInputFile);
        if (inputFile.exists() == false)
        {
            throw new RuntimeException ("Invalid input file: " + jobInfo.mInputFile);
        }
        else if (inputFile.isDirectory() == true)
        {
            throw new RuntimeException ("File can not be a folder.");
        }
        
        // check that we have an output file
        if (jobInfo.mOutputFile == null)
        {
        	throw new RuntimeException("Missing output file.");
        }
        
        return jobInfo;
    }
    
    private static void printUsage()
    {
        System.out.println ("Usage:");
        System.out.println ("\tPDFToPSCLI [options...]");
        System.out.println ();
        System.out.println ("Options: ");
        System.out.println ("\t-input <pdf file> - Name of the input PDF file.");
        System.out.println ("\t-output <ps file> - Name of the output Postscript file.");
        System.out.println ("\t-pwd <password> - Password to open the PDF file.");
        System.out.println ();
        System.out.println ("\t-key <license key> - License key to run the product in production mode.");
        System.out.println ();
    }
}
