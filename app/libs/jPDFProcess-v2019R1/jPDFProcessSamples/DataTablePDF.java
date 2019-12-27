/*
 * Created on Jul 26, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jPDFProcessSamples;

import java.awt.Graphics2D;
import java.util.Vector;

import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.pdfProcess.PDFGraphics;
import com.qoppa.pdfProcess.PDFPage;

/**
 * @author Qoppa Software
 *
 */
public class DataTablePDF
{
	private final static int DEFAULT_COLUMN_WIDTH = 72;
	private final static int CELL_MARGIN_X = 4;
	private final static int CELL_MARGIN_Y = 4;
    
    private final static double PAGE_WIDTH = 8.5 * 72;
    private final static double PAGE_HEIGHT = 11 * 72;

    private final static int MARGIN_TOP = 72;
    private final static int MARGIN_BOTTOM = 72;
    private final static int MARGIN_RIGHT = 72;

	
	private Vector m_Data;
	private int m_ColumnWidths [];
	private boolean m_DrawGrid;
	
	/**
	 * @param data A Vector of Vector's.  This parameter should hold a list of Vectors, one for each
     * row in the data table.  Each row's Vector should contain the data for a column's cell.
     * @param colWidths The width of each of the columns in 72 DPI.
     * @param drawGrid Boolean value to decide whether to draw the grid on the page or not.
	 */
	public DataTablePDF(Vector data, int [] colWidths, boolean drawGrid) 
	{
		super();
		m_Data = data;
		m_ColumnWidths = colWidths;
		m_DrawGrid = drawGrid;
	}
	
	private static Vector initData ()
	{
		Vector data = new Vector ();
		
		// Initialize data
		for (int row = 0; row < 100; ++row)
		{
			Vector rowData = new Vector ();
			for (int col = 0; col < 5; ++col)
			{
				rowData.addElement ("Cell " + row + ", " + col);
			}
			data.addElement (rowData);
		}

		return data;
	}
	
	
	public static void main (String args [])
	{
        DataTablePDF dataTable = new DataTablePDF (initData (), null, true);
        dataTable.createPDF ("c:\\out.pdf");
    }
    
    private void createPDF (String outFileName)
    {
        try
        {
            // Create a new document
            PDFDocument pdfDoc = new PDFDocument ();

            // Create the first page
            PDFPage page = pdfDoc.appendNewPage(PAGE_WIDTH, PAGE_HEIGHT);
            Graphics2D g2d = page.createGraphics();
            g2d.setFont (PDFGraphics.HELVETICA.deriveFont(11f));
            
            int currentRow = 0;
            int lineHeight = g2d.getFontMetrics().getHeight();
            int currentY = MARGIN_TOP + lineHeight;

            while (currentRow < m_Data.size())
            {
                // check if we have to start a new page
                if (currentY + lineHeight > PAGE_HEIGHT - (MARGIN_TOP + MARGIN_BOTTOM))
                {
                    page = pdfDoc.appendNewPage (PAGE_WIDTH, PAGE_HEIGHT);
                    g2d = page.createGraphics();
                    currentY = MARGIN_TOP + lineHeight;
                }

                // Draw the next line
                int currentX = MARGIN_RIGHT;
                Vector nextRow = (Vector)m_Data.elementAt (currentRow);
                for (int col = 0; col < nextRow.size(); ++col)
                {
                    // Draw the cell
                    String cellString = (String)nextRow.elementAt (col);
                    g2d.drawString (cellString, currentX + CELL_MARGIN_X, currentY + CELL_MARGIN_Y);
                    
                    // Get column width
                    int colWidth = DEFAULT_COLUMN_WIDTH;
                    if (m_ColumnWidths != null && m_ColumnWidths.length > col)
                    {
                        colWidth = m_ColumnWidths [col];
                    }
                    
                    // Draw grid if needed
                    if (m_DrawGrid)
                    {
                        g2d.drawRect (currentX, currentY - (lineHeight / 2), colWidth, lineHeight);
                    }
                    
                    // Advance x
                    currentX += colWidth;
                }
                
                // Advance to the next line
                ++currentRow;
                currentY += lineHeight;
            }
            
            // Save the document
            pdfDoc.saveDocument(outFileName);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
	}
}
