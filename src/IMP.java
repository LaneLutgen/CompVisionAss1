/*
 *Hunter Lloyd
 * Copyrite.......I wrote, ask permission if you want to use it outside of class. 
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.awt.image.PixelGrabber;
import java.awt.image.MemoryImageSource;
import java.math.*;

public class IMP implements MouseListener{
   JFrame frame;
   JPanel mp;
   JButton start;
   JScrollPane scroll;
   JMenuItem openItem, exitItem, resetItem;
   Toolkit toolkit;
   File pic;
   ImageIcon img;
   int colorX, colorY;
   int [] pixels;
   int [] results;
   //Instance Fields you will be using below
   
   //This will be your height and width of your 2d array
   int height=0, width=0;
   int startHeight = 0;
   int startWidth = 0;
   
   //your 2D array of pixels
    int picture[][];

    /* 
     * In the Constructor I set up the GUI, the frame the menus. The open pulldown 
     * menu is how you will open an image to manipulate. 
     */
   IMP()
   {
      toolkit = Toolkit.getDefaultToolkit();
      frame = new JFrame("Image Processing Software by Hunter");
      JMenuBar bar = new JMenuBar();
      JMenu file = new JMenu("File");
      JMenu functions = getFunctions();
      frame.addWindowListener(new WindowAdapter(){
            @Override
              public void windowClosing(WindowEvent ev){quit();}
            });
      openItem = new JMenuItem("Open");
      openItem.addActionListener(new ActionListener(){
            @Override
          public void actionPerformed(ActionEvent evt){ handleOpen(); }
           });
      resetItem = new JMenuItem("Reset");
      resetItem.addActionListener(new ActionListener(){
            @Override
          public void actionPerformed(ActionEvent evt){ reset(); }
           });     
      exitItem = new JMenuItem("Exit");
      exitItem.addActionListener(new ActionListener(){
            @Override
          public void actionPerformed(ActionEvent evt){ quit(); }
           });
      file.add(openItem);
      file.add(resetItem);
      file.add(exitItem);
      bar.add(file);
      bar.add(functions);
      frame.setSize(600, 600);
      mp = new JPanel();
      mp.setBackground(new Color(0, 0, 0));
      scroll = new JScrollPane(mp);
      frame.getContentPane().add(scroll, BorderLayout.CENTER);
      JPanel butPanel = new JPanel();
      butPanel.setBackground(Color.black);
      start = new JButton("start");
      start.setEnabled(false);
      start.addActionListener(new ActionListener(){
            @Override
          public void actionPerformed(ActionEvent evt){ fun2(); }
           });
      butPanel.add(start);
      frame.getContentPane().add(butPanel, BorderLayout.SOUTH);
      frame.setJMenuBar(bar);
      frame.setVisible(true);      
   }
   
   /* 
    * This method creates the pulldown menu and sets up listeners to selection of the menu choices. If the listeners are activated they call the methods 
    * for handling the choice, fun1, fun2, fun3, fun4, etc. etc. 
    */
   
  private JMenu getFunctions()
  {
     JMenu fun = new JMenu("Functions");
     JMenuItem firstItem = new JMenuItem("MyExample - fun1 method");
     JMenuItem secondItem = new JMenuItem("Rotate 90 Degrees Clockwise");
     JMenuItem thirdItem = new JMenuItem("Rotate 90 Degrees Counter-clockwise");
     JMenuItem fourthItem = new JMenuItem("Grayscale Image");
     JMenuItem fifthItem = new JMenuItem("Edge Detection");
     JMenuItem sixthItem = new JMenuItem("Track Color (Click on image first)");
     JMenuItem seventhItem = new JMenuItem("Normalize Image");
    
     firstItem.addActionListener(new ActionListener(){
            @Override
          public void actionPerformed(ActionEvent evt){fun1();}
           });
     
     secondItem.addActionListener(new ActionListener(){
    	 @Override
    	 public void actionPerformed(ActionEvent evt){rotateNinetyClockwise();}
     });
     
     thirdItem.addActionListener(new ActionListener(){
    	 @Override
    	 public void actionPerformed(ActionEvent evt){rotateNinetyCounterClockWise();}
     });

     fourthItem.addActionListener(new ActionListener(){
    	 @Override
    	 public void actionPerformed(ActionEvent evt){grayscale();}
     });
     
     fifthItem.addActionListener(new ActionListener(){
    	 @Override
    	 public void actionPerformed(ActionEvent evt){edgeDetection();}
     });
     
     sixthItem.addActionListener(new ActionListener(){
    	 @Override
    	 public void actionPerformed(ActionEvent evt){trackColor();}
     });
     
     seventhItem.addActionListener(new ActionListener(){
    	 @Override
    	 public void actionPerformed(ActionEvent evt){normalizeImage();}
     });
 
     fun.add(firstItem);
     fun.add(secondItem);
     fun.add(thirdItem);
     fun.add(fourthItem);
     fun.add(fifthItem);
     fun.add(sixthItem);
     fun.add(seventhItem);
      
     return fun;   

  }
  
  /*
   * This method handles opening an image file, breaking down the picture to a one-dimensional array and then drawing the image on the frame. 
   * You don't need to worry about this method. 
   */
    private void handleOpen()
  {  
     img = new ImageIcon();
     JFileChooser chooser = new JFileChooser();
     int option = chooser.showOpenDialog(frame);
     if(option == JFileChooser.APPROVE_OPTION) {
       pic = chooser.getSelectedFile();
       img = new ImageIcon(pic.getPath());
      }
     startWidth = width = img.getIconWidth();
     startHeight = height = img.getIconHeight(); 
     
     JLabel label = new JLabel(img);
     label.addMouseListener(this);
     pixels = new int[width*height];
     
     results = new int[width*height];
  
          
     Image image = img.getImage();
        
     PixelGrabber pg = new PixelGrabber(image, 0, 0, width, height, pixels, 0, width );
     try{
         pg.grabPixels();
     }catch(InterruptedException e)
       {
          System.err.println("Interrupted waiting for pixels");
          return;
       }
     for(int i = 0; i<width*height; i++)
        results[i] = pixels[i];  
     turnTwoDimensional();
     mp.removeAll();
     
     mp.add(label);
     
     mp.revalidate();
  }
  
  /*
   * The libraries in Java give a one dimensional array of RGB values for an image, I thought a 2-Dimensional array would be more usefull to you
   * So this method changes the one dimensional array to a two-dimensional. 
   */
  private void turnTwoDimensional()
  {
     picture = new int[height][width];
     for(int i=0; i<height; i++)
       for(int j=0; j<width; j++)
          picture[i][j] = pixels[i*width+j];
      
     
  }
  /*
   *  This method takes the picture back to the original picture
   */
  private void reset()
  {	  
	  width = startWidth;
	  height = startHeight;
	  
        for(int i = 0; i<width*height; i++)
             pixels[i] = results[i]; 
       Image img2 = toolkit.createImage(new MemoryImageSource(width, height, pixels, 0, width)); 

      JLabel label2 = new JLabel(new ImageIcon(img2));
      label2.addMouseListener(this);
      
      turnTwoDimensional();
	  resetPicture();
	  
       mp.removeAll();
       mp.repaint();
       mp.add(label2);
     
       mp.revalidate(); 
    }
  /*
   * This method is called to redraw the screen with the new image. 
   */
  private void resetPicture()
  {
       for(int i=0; i<height; i++)
       for(int j=0; j<width; j++)
          pixels[i*width+j] = picture[i][j];
      Image img2 = toolkit.createImage(new MemoryImageSource(width, height, pixels, 0, width)); 

      JLabel label2 = new JLabel(new ImageIcon(img2));
      label2.addMouseListener(this);
       mp.removeAll();
       mp.repaint();
       mp.add(label2);
     
       mp.revalidate(); 
   
    }
    /*
     * This method takes a single integer value and breaks it down doing bit manipulation to 4 individual int values for A, R, G, and B values
     */
  private int [] getPixelArray(int pixel)
  {
      int temp[] = new int[4];
      temp[0] = (pixel >> 24) & 0xff;
      temp[1]   = (pixel >> 16) & 0xff;
      temp[2] = (pixel >>  8) & 0xff;
      temp[3]  = (pixel      ) & 0xff;
      return temp;
      
    }
    /*
     * This method takes an array of size 4 and combines the first 8 bits of each to create one integer. 
     */
  private int getPixels(int rgb[])
  {
         int alpha = 0;
         int rgba = (rgb[0] << 24) | (rgb[1] <<16) | (rgb[2] << 8) | rgb[3];
        return rgba;
  }
  
  public void getValue()
  {
      int pix = picture[colorY][colorX];
      int temp[] = getPixelArray(pix);
      System.out.println("Color value " + temp[0] + " " + temp[1] + " "+ temp[2] + " " + temp[3]);
  }
  
  public int[] getRBGFromClick()
  {
	  int pix = picture[colorY][colorX];
      return getPixelArray(pix);
  }
  
  /**************************************************************************************************
   * This is where you will put your methods. Every method below is called when the corresponding pulldown menu is 
   * used. As long as you have a picture open first the when your fun1, fun2, fun....etc method is called you will 
   * have a 2D array called picture that is holding each pixel from your picture. 
   *************************************************************************************************/
   /*
    * Example function that just removes all red values from the picture. 
    * Each pixel value in picture[i][j] holds an integer value. You need to send that pixel to getPixelArray the method which will return a 4 element array 
    * that holds A,R,G,B values. Ignore [0], that's the Alpha channel which is transparency, we won't be using that, but you can on your own.
    * getPixelArray will breaks down your single int to 4 ints so you can manipulate the values for each level of R, G, B. 
    * After you make changes and do your calculations to your pixel values the getPixels method will put the 4 values in your ARGB array back into a single
    * integer value so you can give it back to the program and display the new picture. 
    */
  private void fun1()
  {
     
    for(int i=0; i<height; i++)
       for(int j=0; j<width; j++)
       {   
          int rgbArray[] = new int[4];
         
          //get three ints for R, G and B
          rgbArray = getPixelArray(picture[i][j]);
         
        
           rgbArray[1] = 0;
           //take three ints for R, G, B and put them back into a single int
           picture[i][j] = getPixels(rgbArray);
        } 
     resetPicture();
  }

  /*
   * fun2
   * This is where you will write your STACK
   * All the pixels are in picture[][]
   * Look at above fun1() to see how to get the RGB out of the int (getPixelArray)
   * and then put the RGB back to an int (getPixels)
   */ 
  private void fun2()
  {
	  //Arrays to store occurances for color values
	  int[] red = new int[256];
	  int[] green = new int[256];
	  int[] blue = new int[256];
	  
	  //Parse picture and populate arrays
	  for(int i=0; i<height; i++)
	  {
	       for(int j=0; j<width; j++)
	       {
	    	   int rgbArray[] = new int[4];
	    	   
	    	   rgbArray = getPixelArray(picture[i][j]);
	    	   
	    	   //Increment the occurances for the color values
	    	   red[rgbArray[1]]++;
	    	   green[rgbArray[2]]++;
	    	   blue[rgbArray[3]]++;
	       }
	  }
	  
	  HistogramCollection histogram = new HistogramCollection(red, green, blue);
  }
  
  /*
   * Rotates the image 90 degrees clockwise
   */
  private void rotateNinetyClockwise()
  {
	  int[][] newPicture = new int[width][height];
	  
	  for(int i = 0; i<height; i++)
	  {
		  for(int j = 0; j<width; j++)
		  {
			  newPicture[j][height - i - 1] = picture[i][j];
		  }
	  }
	  
	  picture = new int[width][height];
	  
	  for(int i = 0; i<width; i++)
	  {
		  for(int j = 0; j<height; j++)
		  {
			  picture[i][j] = newPicture[i][j];
		  }
	  }
	  
	  int temp = width;
	  width = height;
	  height = temp;
	  
	  resetPicture();
  }
  
  /*
   * Rotates the image 90 degrees counter-clockwise
   */
  private void rotateNinetyCounterClockWise()
  {
	  int[][] newPicture = new int[width][height];
	  
	  for(int i = 0; i<height; i++)
	  {
		  for(int j = 0; j<width; j++)
		  {
			  newPicture[width - j - 1][i] = picture[i][j];
		  }
	  }
	  
	  picture = new int[width][height];
	  
	  for(int i = 0; i<width; i++)
	  {
		  for(int j = 0; j<height; j++)
		  {
			  picture[i][j] = newPicture[i][j];
		  }
	  }
	  
	  int temp = width;
	  width = height;
	  height = temp;
	  
	  resetPicture();
  }
  
  /*
   * Applies grayscale to an image
   */
  private void grayscale()
  {
	  //Luminosity formula 0.21R + 0.72G + 0.07B
	  for(int i=0; i<height; i++)
	       for(int j=0; j<width; j++)
	       {   
	          int rgbArray[] = new int[4];
	         
	          //get three ints for R, G and B
	          rgbArray = getPixelArray(picture[i][j]);
	         
	        
	           int red = (int)(0.21 * rgbArray[1]);
	           int green = (int)(0.72 * rgbArray[2]);
	           int blue = (int)(0.07 * rgbArray[3]);
	           
	           int grayscale = red + green + blue;
	           rgbArray[1] = rgbArray[2] = rgbArray[3] = grayscale;
	           
	           //take three ints for R, G, B and put them back into a single int
	           picture[i][j] = getPixels(rgbArray);
	        } 
	  resetPicture();
  }
  
  /*
   * Normalize the image colors based on the mapping function
   */
  private void normalizeImage()
  {
	//Arrays to store occurances for color values
	  int[] red = new int[256];
	  int[] green = new int[256];
	  int[] blue = new int[256];
	  
	  //Parse picture and populate arrays
	  for(int i=0; i<height; i++)
	  {
	       for(int j=0; j<width; j++)
	       {
	    	   int rgbArray[] = new int[4];
	    	   
	    	   rgbArray = getPixelArray(picture[i][j]);
	    	   
	    	   //Increment the occurances for the color values
	    	   red[rgbArray[1]]++;
	    	   green[rgbArray[2]]++;
	    	   blue[rgbArray[3]]++;
	       }
	  }
	  
	  int runningRedTotal = 0;
	  int runningGreenTotal = 0;
	  int runningBlueTotal = 0;
	  
	  //Normalization loop
	  for(int i=0; i<red.length; i++)
	  {  
    	   //Increment the occurances for the color values
    	   runningRedTotal += red[i];
    	   runningGreenTotal += green[i];
    	   runningBlueTotal += blue[i];
    	   
    	   red[i] = (int)Math.round((((float)runningRedTotal)/(float)pixels.length) * 255.0f);
    	   green[i] = (int)Math.round((((float)runningGreenTotal)/(float)pixels.length) * 255.0f);
    	   blue[i] = (int)Math.round((((float)runningBlueTotal)/(float)pixels.length) * 255.0f);
	  }
	  
	  for(int i=0; i<height; i++)
	  {
	       for(int j=0; j<width; j++)
	       {
	    	   int rgbArray[] = new int[4];
	    	   
	    	   rgbArray = getPixelArray(picture[i][j]);
	    	   
	    	   //Increment the occurances for the color values
	    	   rgbArray[1] = red[rgbArray[1]];
	    	   rgbArray[2] = green[rgbArray[2]];
	    	   rgbArray[3] = blue[rgbArray[3]];
	    	   
	    	   picture[i][j] = getPixels(rgbArray);
	       }
	  }
	  
	  resetPicture();
  }
  
  /*
   * Applies grayscale and then does a 3x3 mask for edge detection
   */
  private void edgeDetection()
  {
	  grayscale();
	  
	  int[][] newPicture = new int[height][width];
	  int rgbArray[] = new int[4];
	  int edgeTotal = 0;
	  int value;
	  
	  for(int i=0; i<height; i++)
	  {
	       for(int j=0; j<width; j++)
	       {
	    	   try
	    	   {
	    		   rgbArray = getPixelArray(picture[i+1][j]);
	    		   edgeTotal += rgbArray[1];
	    	   }
	    	   catch(IndexOutOfBoundsException e){}
	    	   try
	    	   {
	    		   rgbArray = getPixelArray(picture[i-1][j]);
	    		   edgeTotal += rgbArray[1];
	    	   }
	    	   catch(IndexOutOfBoundsException e){}
	    	   try
	    	   {
	    		   rgbArray = getPixelArray(picture[i+1][j+1]);
	    		   edgeTotal += rgbArray[1];
	    	   }
	    	   catch(IndexOutOfBoundsException e){}
	    	   try
	    	   {
	    		   rgbArray = getPixelArray(picture[i-1][j-1]);
	    		   edgeTotal += rgbArray[1];
	    	   }
	    	   catch(IndexOutOfBoundsException e){}
	    	   try
	    	   {
	    		   rgbArray = getPixelArray(picture[i][j+1]);
	    		   edgeTotal += rgbArray[1];
	    	   }
	    	   catch(IndexOutOfBoundsException e){}
	    	   try
	    	   {
	    		   rgbArray = getPixelArray(picture[i][j-1]);
	    		   edgeTotal += rgbArray[1];
	    	   }
	    	   catch(IndexOutOfBoundsException e){}
	    	   try
	    	   {
	    		   rgbArray = getPixelArray(picture[i+1][j-1]);
	    		   edgeTotal += rgbArray[1];
	    	   }
	    	   catch(IndexOutOfBoundsException e){}
	    	   try
	    	   {
	    		   rgbArray = getPixelArray(picture[i-1][j+1]);
	    		   edgeTotal += rgbArray[1];
	    	   }
	    	   catch(IndexOutOfBoundsException e){}
	    	   
	    	   rgbArray = getPixelArray(picture[i][j]);
	    	   value = edgeTotal - (8 * rgbArray[1]);

	    	   if(value > 255)
	    	   {
	    		   rgbArray[1] = rgbArray[2] = rgbArray[3] = 255;
	    	   }
	    	   else if(value < 0)
	    	   {
	    		   rgbArray[1] = rgbArray[2] = rgbArray[3] = 0;
	    	   }
	    	   else
	    	   {
	    		   rgbArray[1] = rgbArray[2] = rgbArray[3] = value;
	    	   }
	    	   
	    	   newPicture[i][j] = getPixels(rgbArray); 
	    	   
	    	   edgeTotal = 0;
	       }
	  }
	  
	  picture = newPicture;
	  
	  resetPicture();
  }
  
  /*
   * Tracks a color in an image (can maybe supply a color as input)
   */
  private void trackColor()
  {
	  //Get the x and y coords of the mouse click
	  if(colorX > 0 && colorY > 0)
	  {
		  int[] temp = getRBGFromClick();
		  
		  for(int i=0; i<height; i++)
		  {
		       for(int j=0; j<width; j++)
		       {
		    	   int rgbArray[] = new int[4];
		    	   
		    	   rgbArray = getPixelArray(picture[i][j]);
		    	   
		    	   if(Math.abs(temp[1] - rgbArray[1]) < 20 &&
	    			  Math.abs(temp[2] - rgbArray[2]) < 20 &&
	    			  Math.abs(temp[3] - rgbArray[3]) < 20)
		    	   {
		    		   picture[i][j] = Integer.MAX_VALUE; 
		    	   }
		    	   else
		    	   {
		    		   picture[i][j] = 0; 
		    	   }
		       }
		  }
	  }
	  
	  resetPicture();
  }
  
  
  private void quit()
  {  
     System.exit(0);
  }

    @Override
   public void mouseEntered(MouseEvent m){}
    @Override
   public void mouseExited(MouseEvent m){}
    @Override
   public void mouseClicked(MouseEvent m){
        colorX = m.getX();
        colorY = m.getY();
        System.out.println(colorX + "  " + colorY);
        getValue();
        start.setEnabled(true);
    }
    @Override
   public void mousePressed(MouseEvent m){}
    @Override
   public void mouseReleased(MouseEvent m){}
   
   public static void main(String [] args)
   {
      IMP imp = new IMP();
   }
 
}
