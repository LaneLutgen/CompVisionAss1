
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class ColorPanel extends JPanel
{	
	private int[] colorArray;
	
	public ColorPanel()
	{
		super();
	}
	
	public ColorPanel(int[] colorArray)
	{
		super();
		this.colorArray = colorArray;
	}
	
	public void setColor(int[] array)
	{
		colorArray = array;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		int w = this.getWidth();
		int h = this.getHeight();
		
		int drawIndex = 20;
		for(int i = 0; i < colorArray.length; i++)
		{
			g.drawLine(drawIndex + i, h, drawIndex + i, h - colorArray[i]);
		}	
	}
}
