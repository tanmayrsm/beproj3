/*
 * Created on Jun 4, 2009
 *
 */
package jPDFFieldsSamples;

import java.util.Vector;

import com.qoppa.pdf.form.FormField;
import com.qoppa.pdf.form.TextField;
import com.qoppa.pdfFields.PDFFields;

public class SetFieldValues
{
    public static void main (String [] args)
    {
        try
        {
            // Load the document
            PDFFields pdfDoc = new PDFFields ("input.pdf", null);
            
            // Get the list of fields in the document
            Vector<?> fieldList = pdfDoc.getFieldList();
            
            // Echo the fields
            for (int count = 0; count < fieldList.size(); ++count)
            {
                // Set the values of text fields
                FormField field = (FormField)fieldList.get (count);
                if (field instanceof TextField)
                {
                    ((TextField)field).setValue("TF: " + count);
                }
            }
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }




}
