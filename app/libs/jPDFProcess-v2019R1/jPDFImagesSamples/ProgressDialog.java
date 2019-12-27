package jPDFImagesSamples;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class ProgressDialog extends JDialog
{
	private static final Color BANNER_COLOR = new Color(0x4D82B8);

	private JPanel jpContentPane = null;
	private JPanel jpTitlePanel = null;
	private JPanel jpProgressPanel = null;
	private JPanel jpButtonPanel = null;
	private JLabel jlProcessing = null;
	private JLabel jbStopCancel = null;
	private JLabel jlFunctionName = null;
	private JProgressBar jpbProgress = null;

	private boolean m_Continue = true;

	public static ProgressDialog getInstance(final Frame frame, final String functionName)
	{
		if (SwingUtilities.isEventDispatchThread())
		{
			return new ProgressDialog(frame, functionName);
		}

		final ProgressDialog[] pd = new ProgressDialog[1];
		try
		{
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run()
				{
					pd[0] = new ProgressDialog(frame, functionName);
				}
			});
		}
		catch (Exception e)
		{
			// If there's an exception, just create it not on EDT
			pd[0] = new ProgressDialog(frame, functionName);
		}

		return pd[0];
	}

	private ProgressDialog(Frame owner, String functionName)
	{
		super(owner);
		initialize(functionName);

		if (owner != null)
		{
			setLocation(owner.getX() + (owner.getWidth() - getWidth()) / 2, owner.getY() + (owner.getHeight() - getHeight()) / 2);
		}
		else
		{
			setLocationRelativeTo(owner);
		}
	}

	private void initialize(String functionName)
	{
		setContentPane(getJpContentPane(functionName));
		setUndecorated(true);

		getJpContentPane().setPreferredSize(new Dimension(getPreferredSize().width + (int)(60 * SampleUtil.getDPIScalingMultiplier()), getPreferredSize().height));
		
		setModal(true);
		pack();

		getJlProcessing().setText(" ");
		getJpbProgress().setIndeterminate(true);
		getJpbProgress().setStringPainted(false);
		getJpContentPane().paintImmediately(0, 0, getJpContentPane().getWidth(), getJpContentPane().getHeight());
	}

	private JPanel getJpContentPane(String functionName)
	{
		if (jpContentPane == null)
		{
			jpContentPane = new JPanel(new BorderLayout());
			jpContentPane.setBorder(BorderFactory.createLineBorder(Color.gray));

			jpContentPane.add(getTitlePanel(), BorderLayout.NORTH);
			jpContentPane.add(Box.createRigidArea(new Dimension(0, 8)));
			jpContentPane.add(getProgressPanel(functionName), BorderLayout.CENTER);
			jpContentPane.add(getButtonPanel(), BorderLayout.SOUTH);
		}
		return jpContentPane;
	}

	private JPanel getJpContentPane()
	{
		return getJpContentPane(null);
	}

	private JPanel getTitlePanel()
	{ 
		if (jpTitlePanel == null)
		{
			jpTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)) {
				protected void paintComponent(Graphics g)
				{
					g.setColor(BANNER_COLOR);
					g.fillRect(0, 0, jpTitlePanel.getWidth(), jpTitlePanel.getHeight());
				}
			};
			JLabel title = new JLabel("Progress");
			title.setFont(title.getFont().deriveFont(Font.BOLD));
			jpTitlePanel.add(title);
			jpTitlePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black), BorderFactory.createEmptyBorder(5, 5, 3, 5)));
		}
		return jpTitlePanel;
	}

	private JPanel getProgressPanel(String functionName)
	{
		if (jpProgressPanel == null)
		{
			jpProgressPanel = new JPanel(new BorderLayout(0, 5));
			jpProgressPanel.setBorder(new EmptyBorder(15, 25, 5, 25));
			jpProgressPanel.add(getJlFunctionName(functionName), BorderLayout.NORTH);
			jpProgressPanel.add(getJpbProgress(), BorderLayout.CENTER);
			jpProgressPanel.add(getJlProcessing(), BorderLayout.SOUTH);
		}
		return jpProgressPanel;
	}

	private JLabel getJlFunctionName(String functionName)
	{
		if (jlFunctionName == null)
		{
			jlFunctionName = new JLabel(functionName);
		}
		return jlFunctionName;
	}

	public void setJlFunctionName(String newFunctionName)
	{
		getJlFunctionName(null).setText(newFunctionName);
	}

	private JProgressBar getJpbProgress()
	{
		if (jpbProgress == null)
		{
			jpbProgress = new JProgressBar(0, 100);
			jpbProgress.setStringPainted(true);
			jpbProgress.setValue(0);
		}
		return jpbProgress;
	}

	private JLabel getJlProcessing()
	{
		if (jlProcessing == null)
		{
			jlProcessing = new JLabel("Processing: ");
		}
		return jlProcessing;
	}

	private JPanel getButtonPanel()
	{
		if (jpButtonPanel == null)
		{
			jpButtonPanel = new JPanel(new GridLayout(1, 3));
			jpButtonPanel.setBorder(new EmptyBorder(0, 5, 5, 5));
			jpButtonPanel.add(Box.createRigidArea(new Dimension(1, 1)));
			jpButtonPanel.add(Box.createRigidArea(new Dimension(1, 1)));
			jpButtonPanel.add(getJbStopCancel());
		}
		return jpButtonPanel;
	}

	public JLabel getJbStopCancel()
	{
		if (jbStopCancel == null)
		{
			JButton size = new JButton("Stop");

			jbStopCancel = new JLabel("Stop");
			jbStopCancel.setPreferredSize(size.getPreferredSize());
			jbStopCancel.setHorizontalAlignment(SwingConstants.CENTER);
			jbStopCancel.setBorder(BorderFactory.createLineBorder(Color.black));
					
			jbStopCancel.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e)
				{
					if (jbStopCancel.isEnabled())
					{
						jbStopCancel.setBorder(BorderFactory.createLineBorder(BANNER_COLOR.darker()));
					}
				}
				public void mouseExited(MouseEvent e)
				{
					jbStopCancel.setBorder(BorderFactory.createLineBorder(Color.black));
				}
				public void mouseReleased(MouseEvent e)
				{
					cmdStopCancel();
				}
				public void mousePressed(MouseEvent e)
				{
					cmdStopCancel();
				}
			});
		}
		return jbStopCancel;
	}

	public void cmdStopCancel()
	{
		if (getJbStopCancel().isVisible())
		{
			m_Continue = false;
			updateProgress(getJbStopCancel().getText());
		}
	}

	public boolean shouldContinue()
	{
		return m_Continue;
	}

	public void updateProgress(final String message)
	{
		if (SwingUtilities.isEventDispatchThread())
		{
			safeUpdateProgress(message);
		}
		else
		{
			SwingUtilities.invokeLater(new Runnable() 
    		{
				public void run()
				{
					safeUpdateProgress(message);
				}
    		});
		}
	}

	private void safeUpdateProgress(String message)
	{
		getJlProcessing().setText(message == null ? " " : "Processing: " + message);
		getJpContentPane().paintImmediately(0, 0, getJpContentPane().getWidth(), getJpContentPane().getHeight());
	}

	public void setVisible(final boolean visible)
	{
		if (SwingUtilities.isEventDispatchThread())
		{
			safeSetVisible(visible);
		}
		else
		{
			SwingUtilities.invokeLater(new Runnable() 
    		{
				public void run() 
				{
					safeSetVisible(visible);
				}
    		});
		}
	}

	private void safeSetVisible(boolean visible)
	{
		super.setVisible(visible);
	}

	public void dispose()
	{
		if (SwingUtilities.isEventDispatchThread())
		{
			safeDispose();
		}
		else
		{
			SwingUtilities.invokeLater(new Runnable() 
    		{
				public void run() 
				{
					safeDispose();
				}
    		});
		}
	}

	private void safeDispose()
	{
		super.dispose();
	}
}

