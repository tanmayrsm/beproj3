/*
 * Created on Aug 14, 2008
 *
 */
package jPDFPrintSamples;

import java.awt.print.PrinterJob;
import java.io.File;

import javax.print.PrintService;

import com.qoppa.pdf.PDFPassword;
import com.qoppa.pdf.PrintSettings;
import com.qoppa.pdfPrint.PDFPrint;

public class PDFPrintCLI
{
    private static class JobInfo
    {
        public String m_InputFile;
        public String m_PrinterName;
        public PrintSettings m_PrintSettings = new PrintSettings();
        private PDFPassword m_Password;
        
        private String m_LicenseKey;
    }
    
    public static void main (String [] args)
    {
        try
        {
            // Parse the arguments
            JobInfo jobInfo = parseArgs (args);
            
            // Check for the key
            if (jobInfo.m_LicenseKey != null)
            {
                PDFPrint.setKey (jobInfo.m_LicenseKey);
            }

            // Load the document
            PDFPrint pdfPrint = new PDFPrint (jobInfo.m_InputFile, jobInfo.m_Password);
            
            // Print the file
            pdfPrint.print (jobInfo.m_PrinterName, jobInfo.m_PrintSettings);
        }
        catch (Throwable t)
        {
            System.out.println (t.getMessage());
            System.exit(-1);
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
            if ("-printer".equalsIgnoreCase(args [ix]))
            {
                jobInfo.m_PrinterName = args [ix+1];
                ix += 2;
            }
            else if ("-shrinktomargins".equalsIgnoreCase(args [ix]))
            {
                jobInfo.m_PrintSettings.m_ShrinkToMargins = true;
                ++ix;
            }
            else if ("-expandtomargins".equalsIgnoreCase(args [ix]))
            {
                jobInfo.m_PrintSettings.m_ExpandToMargins = true;
                ++ix;
            }
            else if ("-centerinpage".equalsIgnoreCase(args [ix]))
            {
                jobInfo.m_PrintSettings.m_CenterInPage = true;
                ++ix;
            }
            else if ("-autorotate".equalsIgnoreCase(args [ix]))
            {
                jobInfo.m_PrintSettings.m_AutoRotate = true;
                ++ix;
            }
            else if ("-pwd".equalsIgnoreCase(args [ix]))
            {
                jobInfo.m_Password = new PDFPassword (args [ix+1]);
                ix += 2;
            }
            else if ("-key".equalsIgnoreCase(args [ix]))
            {
                jobInfo.m_LicenseKey = args [ix+1];
                ix += 2;
            }
            else if ("-listprinters".equalsIgnoreCase(args [ix]))
            {
                listPrinters();
                System.exit (0);
            }
            else if (args [ix].startsWith("-") == false)
            {
                jobInfo.m_InputFile = args [ix];
                ++ix;
            }
            else
            {
                throw new RuntimeException ("Unrecognized command line option: " + args [ix]);
            }
        }
        
        // Check that we got an input file
        if (jobInfo.m_InputFile == null)
        {
            throw new RuntimeException ("Missing input file.");
        }
        
        // Check that the input file exists and is not a folder
        File inputFile = new File (jobInfo.m_InputFile);
        if (inputFile.exists() == false)
        {
            throw new RuntimeException ("Invalid input file: " + jobInfo.m_InputFile);
        }
        else if (inputFile.isDirectory() == true)
        {
            throw new RuntimeException ("File can not be a folder.");
        }
        
        return jobInfo;
    }
    
    private static void printUsage()
    {
        System.out.println ("Usage:");
        System.out.println ("\tjPDFPrint [options...] file");
        System.out.println ("\tjPDFPrint -listprinters");
        System.out.println ();
        System.out.println ("Where:");
        System.out.println ("\tfile is the path to a PDF file to print.");
        System.out.println ();
        System.out.println ("Options: ");
        System.out.println ("\t-printer <printer name> - Name of the printer to print the file to, default printer if missing.");
        System.out.println ("\t-pwd <password> - Password to open the PDF file.");
        System.out.println ("\t-shrinktomargins - Shrink the output, if necessary, so that it fits in the printer margins.");
        System.out.println ("\t-expandtomargins - Expand the output, if necessary, so that it will fill the printer margins.");
        System.out.println ("\t-centerinpage - Ccenter the output on the page.");
        System.out.println ("\t-autorotate - Rotate the output, if necessary, to landscape or portrait for best fit.");
        System.out.println ();
        System.out.println ("\t-key <license key> - License key to run the product in production mode.");
        System.out.println ();
        System.out.println ("\t-listprinters - Lists all the printers that Java can see.");
        System.out.println ();
    }
    
    private static void listPrinters()
    {
        System.out.println ("Printers: ");
        System.out.println ();

        // List printers
        PrintService [] ps = PrinterJob.lookupPrintServices();
        for (int count = 0; count < ps.length; ++count)
        {
            System.out.println (ps [count].getName());
        }

        // check for no printers
        if (ps.length == 0)
        {
            System.out.println ("<No Printers>");
        }

        System.out.println ();
    }
}
