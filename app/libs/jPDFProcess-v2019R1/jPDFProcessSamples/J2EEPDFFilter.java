package jPDFProcessSamples;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.qoppa.pdf.PDFException;
import com.qoppa.pdfProcess.PDFDocument;

/**
 * This samples illustrates how jPDFProcess can be deployed within a J2EE filter.  With jPDFProcess,
 * every PDF document that is served by the server (or any subset of documents) can be modified by
 * this filter to add watermarks, inject content, etc.
 * 
 * @author Qoppa Software
 *
 */
public class J2EEPDFFilter implements Filter 
{
	public void destroy()
	{
		
	}
	
	public void init(FilterConfig config) throws ServletException
	{
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		// pass a stand-in stream to the servlet
		ByteArrayResponseWrapper wrappedResponse = new ByteArrayResponseWrapper((HttpServletResponse) response);

		chain.doFilter(request, wrappedResponse);
		byte[] bytes = wrappedResponse.getByteArray();

		// we're expecting a pdf
		if (wrappedResponse.getContentType().indexOf("application/pdf") != -1)
		{
			try
			{
				PDFDocument doc = new PDFDocument(new ByteArrayInputStream(bytes), null);
				int pageCount = doc.getPageCount();
				for (int i = 0; i < pageCount; i++)
				{
					// add a custom watermark
					doc.getPage(i).createGraphics().drawString("CUSTOM WATERMARK", 100, 100);
				}

				// save the doc to a temp file to get & update the new content length
				File temp = File.createTempFile("tmp", null);
				doc.saveDocument(temp.getAbsolutePath());
				response.setContentLength((int) temp.length());
				FileInputStream in = new FileInputStream(temp);
				copy(in, response.getOutputStream());
				in.close();
				temp.delete();
			}
			catch (PDFException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			response.getOutputStream().write(bytes);
		}

	}

	private static class ByteArrayServletStream extends ServletOutputStream
	{
		ByteArrayOutputStream baos;

		ByteArrayServletStream(ByteArrayOutputStream baos)
		{
			this.baos = baos;
		}

		public void write(int param) throws IOException
		{
			baos.write(param);
		}
	}

	private static class ByteArrayPrintWriter
	{
		private ByteArrayOutputStream baos = new ByteArrayOutputStream();

		private PrintWriter pw = new PrintWriter(baos);

		private ServletOutputStream sos = new ByteArrayServletStream(baos);

		public PrintWriter getWriter()
		{
			return pw;
		}

		public ServletOutputStream getStream()
		{
			return sos;
		}

		byte[] toByteArray()
		{
			return baos.toByteArray();
		}
	}

	public class ByteArrayResponseWrapper extends HttpServletResponseWrapper
	{
		private ByteArrayPrintWriter output;
		private boolean usingWriter;

		public ByteArrayResponseWrapper(HttpServletResponse response)
		{
			super(response);
			usingWriter = false;
			output = new ByteArrayPrintWriter();
		}

		public byte[] getByteArray()
		{
			return output.toByteArray();
		}

		public ServletOutputStream getOutputStream() throws IOException
		{
			// will error out, if in use
			if (usingWriter)
			{
				super.getOutputStream();
			}
			usingWriter = true;
			return output.getStream();
		}

		public PrintWriter getWriter() throws IOException
		{
			// will error out, if in use
			if (usingWriter)
			{
				super.getWriter();
			}
			usingWriter = true;
			return output.getWriter();
		}

		public String toString()
		{
			return output.toString();
		}
	}
	
	/**
	 * Copy data from the InputStream to the OutputStream
	 * @param inStream
	 * @param outStream
	 * @throws IOException
	 */
	public static void copy(InputStream inStream, OutputStream outStream) throws IOException
	{
		byte[] buffer = new byte[4096];
	    int bytesRead = -1;
	     
	    while ((bytesRead = inStream.read(buffer)) != -1) {
	        outStream.write(buffer, 0, bytesRead);
	    }
	}
}
