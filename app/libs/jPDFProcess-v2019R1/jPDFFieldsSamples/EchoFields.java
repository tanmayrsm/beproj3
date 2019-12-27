/*
 * This Java program uses Qoppa's library jPDFFields
 * to open a PDF document / form, loop through all the 
 * fields contained in the document and outputs
 * information about the fields including name,
 * type, value, default value, etc... 
 *
 */
package jPDFFieldsSamples;

import java.util.Vector;

import com.qoppa.pdf.form.CheckBoxField;
import com.qoppa.pdf.form.ComboField;
import com.qoppa.pdf.form.FormField;
import com.qoppa.pdf.form.ListField;
import com.qoppa.pdf.form.RadioButtonGroupField;
import com.qoppa.pdf.form.SignatureField;
import com.qoppa.pdf.form.TextField;
import com.qoppa.pdfFields.PDFFields;

public class EchoFields
{
    public static void main (String [] args)
    {
        try
        {
            // Load the document
            PDFFields pdfDoc = new PDFFields ("C:/myfolder/form04.pdf", null);
            
            // get list of fields
            Vector<FormField> fields = pdfDoc.getFieldList();
             
            // loop through the fields
            for (int count = 0; count < fields.size(); ++count) 
            {
              // get field
              FormField field = fields.get(count);
              
              // field name
              System.out.println("Field Name " + field.getFieldName());
              
              // field type description
              System.out.println("Field Type " + field.getFieldTypeDesc());
             
              // text field
              if (field instanceof TextField) 
              {
                System.out.println("Value " + ((TextField) field).getValue());
                System.out.println("Default Value " + ((TextField) field).getDefaultValue());
              }
              // radio button field
              else if (field instanceof RadioButtonGroupField) 
              {
                System.out.println("Value " + ((RadioButtonGroupField) field).getValue());
                System.out.println("Default Value " + ((RadioButtonGroupField) field).getDefaultValue());
              }
              // check box field
              else if (field instanceof CheckBoxField) 
              {
                System.out.println("Value " + ((CheckBoxField) field).getValue());
                System.out.println("Default Value " + ((CheckBoxField) field).getDefaultValue());
              }
              // combo box field
              else if (field instanceof ComboField) 
              {
                System.out.println("Value " +((ComboField) field).getValue());
                System.out.println("Options " +((ComboField) field).getExportOptions());
                System.out.println("Default Value " + ((ComboField) field).getDefaultValue());
              }
              // list field
              else if (field instanceof ListField) 
              {
                System.out.println("Value " + ((ListField) field).getValues());
                System.out.println("Options " + ((ListField) field).getExportOptions());
                System.out.println("Default Values " +((ListField) field).getDefaultValue());
              }
              // signature field
              else if (field instanceof SignatureField) 
              {
                System.out.println("Signature Name " + ((SignatureField) field).getSignName());
                System.out.println("Signature Date " + ((SignatureField) field).getSignDateTime());
              }
            }
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }
}
