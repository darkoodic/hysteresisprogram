/*
 * JShellImage.java
 *
 * Created on February 8, 2006, 4:57 PM
 */

package WhoHasMore;

import javax.imageio.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.Image;
import java.util.Vector;
import java.awt.Graphics;
import java.awt.Color;
import java.util.Random;

/**
 *
 * @author  Joanna
 */
public class JShellImage {
    private JShellPanel panel;
    private Image image;
    private ImageIcon type;
    private int imageWidth;
    private int imageHeight;
    private int typeWidth;
    private int typeHeight;
    private int currentObjectWidth;
    private int currentObjectHeight;
    private int currentImageWidth;
    private int currentImageHeight;
    private int[][] position;
    private int [][] currentPosition;
    private int X = 0;
    private int Y = 1;
    private int numObjects;
    private boolean initial = true;
    private int oldWidth;
    private int oldHeight;
    private int drawWidth;
    private int drawHeight;
    private Color backColor;
    private double sizePlusMinus = .35;
    private Random random = new Random();
    private boolean clearState = true;
    double ratio = 1;
    private double startScaleFactor = 0.4;
    
    
    /** Creates a new instance of Class */
    public JShellImage(Image _image, int maxObjects, JShellPanel _panel, Color bc) {
        panel = _panel;
        image = _image;
        type = null;
        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);  
        currentImageWidth = imageWidth;
        currentImageHeight = imageHeight;
        position = new int[maxObjects][2];
        currentPosition = new int[maxObjects][2];
        numObjects = 0;
        backColor = bc;
        
    }
    
    public void setType(ImageIcon _type)
    {
        type = _type;
        typeWidth = (int)((double)type.getIconWidth() * startScaleFactor);
        typeHeight = (int)((double)type.getIconHeight() * startScaleFactor);
        currentObjectWidth = typeWidth;
        currentObjectHeight = typeHeight;
        numObjects = 0;
        oldWidth = typeWidth;
        oldHeight = typeHeight;
        drawWidth = typeWidth;
        drawHeight = typeHeight;
    }
    
    public void addObject(int x, int y)
    {

        if (numObjects < position.length);
        /*{
            if (initial)
            {
                position[numObjects][X] = x;
                position[numObjects][Y] = y;
            }
        }
        if (currentImageWidth != imageWidth || currentImageHeight != imageHeight)*/
        {
            position[numObjects][X] = x;
            position[numObjects][Y] = y;
            //double ratio = 1;
            double ratiow = (double)(currentImageWidth)/imageWidth;
            double ratioh = (double)(currentImageHeight)/imageHeight; 
            if (ratiow < ratioh)
                ratio = ratiow;
            else
                ratio = ratioh;

            x = (int)((double)x*ratio);
            y = (int)((double)y*ratio);
            currentObjectWidth = (int)((double)drawWidth*ratio);
            currentObjectHeight = (int)((double)drawHeight*ratio);
            currentPosition[numObjects][X] = x;
            currentPosition[numObjects][Y] = y;
        
            numObjects++;
        }
    }
    
    public boolean isOverlapping(int x, int y)
    {
            for (int i = 0; i < numObjects; i++)
            {
                    if (areOverlapping(position[i], x, y))
                            return true;
            }
            return false;
    }

    private boolean areOverlapping(int[] position, int x, int y)
    {
            if ((position[X] + (int)(drawWidth*(1.+sizePlusMinus)) + 1) < x || (position[X] > (x + (int)(drawWidth*(1.+sizePlusMinus)) + 1)))
                    return false;
            else
                    if ((position[Y] + (int)(drawHeight*(1.+sizePlusMinus)) + 1) < y || (position[Y] > (y + (int)(drawHeight*(1.+sizePlusMinus)) + 1)))
                            return false;
            return true;
    }
    
    public void drawImage() throws TestStoppedException
    {
        if (!panel.Testing())
            throw new TestStoppedException();
        //System.out.println(currentObjectWidth);
        Graphics g = image.getGraphics();
        //random.setSeed(5);
        g.setColor(backColor);
        g.fillRect(0, 0, image.getWidth(null), image.getHeight(null));
        int begin = 0;
        boolean alternate = true;
        double resizeBy = 1;
        if (numObjects % 2 == 1)
        {
            g.drawImage(type.getImage(), currentPosition[0][X], currentPosition[0][Y], currentObjectWidth, currentObjectHeight, type.getImageObserver());
            begin = 1;
        }
        for (int i = begin; i < numObjects; i++)
        {
            if (alternate)
            {
                resizeBy = random.nextDouble()*sizePlusMinus + 1;
                alternate = false;
            }
            else
            {
                resizeBy = 1/resizeBy;
                alternate = true;
            }
            g.drawImage(type.getImage(), currentPosition[i][X], currentPosition[i][Y], (int)(currentObjectWidth*resizeBy), (int)(currentObjectHeight*resizeBy), type.getImageObserver());
        }
        clearState = false;
    }
    
    public void clearImage()
    {
        Graphics g = image.getGraphics();
        g.setColor(backColor);
        g.fillRect(0, 0, image.getWidth(null), image.getHeight(null));
        clearState = true;
    }
    
    public void resizeImage(int w, int h)
    {
        image = panel.createImage(w, h);
        Graphics g = image.getGraphics();
        g.setColor(backColor);
        g.fillRect(0, 0, w, h);
        initial = false;
        
        currentImageWidth = w;
        currentImageHeight = h;
        /*double ratio = 0;
        if (w < h)
            ratio = (double)(w)/imageWidth;
        else
            ratio = (double)(h)/imageHeight;*/
            double ratio = 1;
            double ratiow = (double)(w)/imageWidth;
            double ratioh = (double)(h)/imageHeight; 
            if (ratiow < ratioh)
                ratio = ratiow;
            else
                ratio = ratioh;
        
        currentObjectWidth = (int)(ratio*typeWidth);
        currentObjectHeight = (int)(ratio*typeHeight);
        
        for (int i = 0; i < numObjects; i++)
            {
                currentPosition[i][X] = (int)(position[i][X]*ratio);
                currentPosition[i][Y] = (int)(position[i][Y]*ratio);
                if (!clearState)
                    g.drawImage(type.getImage(), currentPosition[i][X], currentPosition[i][Y], currentObjectWidth, currentObjectHeight, type.getImageObserver());
            }
    }
    
    public void changeTypeSize(double r)
    {
        oldWidth = currentObjectWidth;
        oldHeight = currentObjectHeight;
        drawWidth = (int)(typeWidth*r);
        drawHeight = (int)(typeHeight*r);
        
    }
    
    public void resetSize()
    {
        currentObjectWidth = oldWidth;
        currentObjectHeight = oldHeight;
    }
    
    public Graphics getGraphics()
    {
        return image.getGraphics();
    }
    
    public int getWidth()
    {
        return image.getWidth(null);
    }
    
    public int getHeight()
    {
        return image.getHeight(null);
    }
    
    public Image getImage()
    {
        return image;
    }
    
    public int getTypeWidth()
    {
        return typeWidth;
    }
    
    public int getTypeHeight()
    {
        return typeHeight;
    }
    
    public int getDrawHeight()
	{
		return drawHeight;
	}

	public int getDrawWidth()
	{
		return drawWidth;
	}

    public int getCurrentObjectWidth()
    {
        return currentObjectWidth;
    }
    
    public int getCurrentObjectHeight()
    {
        return currentObjectHeight;
    }
    public int getOriginalWidth()
    {
        return imageWidth;
    }
    
    public int getOriginalHeight()
    {
        return imageHeight;
    }
    
    public double getSizePlusMinus()
    {
        return sizePlusMinus;
    }
    
    
}
