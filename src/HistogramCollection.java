
import java.awt.BorderLayout;

import javax.swing.*;

public class HistogramCollection 
{	
	private JFrame redFrame;
	private JFrame greenFrame;
	private JFrame blueFrame;
	
	private ColorPanel redPanel;
	private ColorPanel greenPanel;
	private ColorPanel bluePanel;
	
	public HistogramCollection()
	{
		redFrame = new JFrame("Red");
		redFrame.setSize(305, 600);
		redFrame.setLocation(800,0);
		redPanel = new ColorPanel();
		redFrame.getContentPane().add(redPanel, BorderLayout.CENTER);
		redFrame.setVisible(true);
		
		greenFrame = new JFrame("Green");
		greenFrame.setSize(305, 600);
		greenFrame.setLocation(1150,0);
		greenPanel = new ColorPanel();
		greenFrame.getContentPane().add(greenPanel, BorderLayout.CENTER);
		greenFrame.setVisible(true);
		
		blueFrame = new JFrame("Blue");
		blueFrame.setSize(305, 600);
		blueFrame.setLocation(1450, 0);
		bluePanel = new ColorPanel();
		blueFrame.getContentPane().add(bluePanel, BorderLayout.CENTER);
		blueFrame.setVisible(true);
	}
	
	public HistogramCollection(int[] red, int[] green, int[] blue)
	{
		this();
		redPanel.setColor(red);
		greenPanel.setColor(green);
		bluePanel.setColor(blue);
	}
	
}
