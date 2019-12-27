package jPDFProcessSamples.cli;

import java.util.Vector;

public class JobInfo
{
	public String mLicenseKey;

	public String mInputFile;
	public String mOutputFile;
	
	public Vector mFunctions;
	
	public void validate() throws CLIException
	{
		if (mInputFile == null)
		{
			throw new CLIException("Missing input file.");
		}
		if (mFunctions == null || mFunctions.size() == 0)
		{
			throw new CLIException("No functions");
		}
	}

	public void addFunction(PDFFunction newFunction)
	{
		if (mFunctions == null)
		{
			mFunctions = new Vector();
		}
		mFunctions.add(newFunction);
	}

}
