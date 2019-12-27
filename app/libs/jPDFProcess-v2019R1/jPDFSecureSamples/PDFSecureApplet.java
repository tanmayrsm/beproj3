/*
 * Created on May 31, 2005
 *
 */
package jPDFSecureSamples;

import javax.swing.JApplet;
import javax.swing.UIManager;

/**
 * @author Gerald Holmann
 *
 */
public class PDFSecureApplet extends JApplet 
{
    /**
     * This method initializes 
     * 
     */
    public PDFSecureApplet() 
    {
        super();
        init();
    }
        /**
         * This method initializes this
         * 
         * @return void
         */
        public void init() 
        {
            setLookAndFeel();
            PDFSecurePanel securePanel = new PDFSecurePanel();
            securePanel.getjpAPI().setVisible(false);
            this.setContentPane(securePanel);
            this.setSize((int)(710 * SampleUtil.getDPIScalingMultiplier()), (int)(700 * SampleUtil.getDPIScalingMultiplier()));
        }
        
        /**
         * Set the look and feel.
         */
        private void setLookAndFeel ()
        {
            // Set the look and feel
            try
            {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            catch (Throwable t)
            {
                try
                {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }
                catch (Throwable tt)
                {
                    // ignore
                }
            }
        }

        public static void  main (String [] args)
        {
            PDFSecureApplet applet = new PDFSecureApplet ();
            applet.setVisible(true);
        }
    }
