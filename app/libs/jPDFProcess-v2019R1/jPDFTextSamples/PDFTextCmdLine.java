/*
 * Created on Sep 4, 2008
 *
 */
package jPDFTextSamples;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import com.qoppa.pdf.PDFPassword;
import com.qoppa.pdfText.PDFText;

public class PDFTextCmdLine
{
    private static class JobInfo
    {
        public String m_InputFile;
        public String m_OutputFile;
        private PDFPassword m_Password;
        private int m_PageNumber = -1;
        
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
                PDFText.setKey (jobInfo.m_LicenseKey);
            }
            
            // Load the document
            PDFText pdfText = new PDFText (jobInfo.m_InputFile, jobInfo.m_Password);
            
            // Output print stream
            PrintStream outStream = System.out;
            if (jobInfo.m_OutputFile != null)
            {
                // Output file
                File outFile = new File (jobInfo.m_OutputFile);
                
                // Warn for overwrite
                if (outFile.exists())
                {
                    try
                    {
                        System.out.print ("Overwrite " + outFile.getName() + " (Y/N)? ");
                        BufferedReader inReader = new BufferedReader (new InputStreamReader (System.in));
                        String readLine = inReader.readLine();
                        if ("y".equalsIgnoreCase(readLine) == false && "yes".equalsIgnoreCase(readLine) == false)
                        {
                            System.exit(-1);
                        }
                        
                        inReader.close();
                    }
                    catch (IOException ioE)
                    {
                        System.err.println (ioE.getMessage());
                        System.exit (-1);
                    }
                }
                
                // Create output stream to the file
                outStream = new PrintStream (new FileOutputStream (outFile));
            }
            
            // Output the text
            if (jobInfo.m_PageNumber == -1)
            {
                outStream.println (pdfText.getText());
            }
            else
            {
                outStream.println (pdfText.getText(jobInfo.m_PageNumber-1));
            }

            // Close output and exit
            outStream.close();
            System.exit (0);
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
            if ("-output".equalsIgnoreCase(args [ix]))
            {
                jobInfo.m_OutputFile = args [ix+1];
                ix += 2;
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
            else if ("-pagenumber".equalsIgnoreCase(args [ix]))
            {
                jobInfo.m_PageNumber = Integer.parseInt(args [ix+1]);
                ix += 2;
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
        System.out.println ("\tPDFTextCmdLine [options...] pdffile");
        System.out.println ();
        System.out.println ("Where:");
        System.out.println ("\tpdffile is the path to a PDF file to extract text from.");
        System.out.println ();
        System.out.println ("Options: ");
        System.out.println ("\t-output <file name> - Name of a file to output the text to.");
        System.out.println ("\t-pwd <password> - Password to open the PDF file.");
        System.out.println ("\t-pagenumber <integer> - The number of the page to extract.");
        System.out.println ();
        System.out.println ("\t-key <license key> - License key to run the product in production mode.");
        System.out.println ();
    }

}
