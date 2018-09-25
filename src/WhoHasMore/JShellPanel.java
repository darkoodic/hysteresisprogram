package WhoHasMore;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import java.awt.geom.*;

public class JShellPanel extends JPanel implements Runnable
{
    private String inputValue;
    private Image offscreen;
    private Color backColor;
    private Color backOffColor = new Color(127, 127, 127);
    private Color BigBirdFrameColor = Color.black;
    private Color GroverFrameColor = Color.black;
    private Color BigBirdBackColor = new Color(127, 127, 127);//new Color(236, 206, 68);
    private Color GroverBackColor = new Color(127, 127, 127);//new Color(36, 42, 116);
//    private ImageIcon BigBird = new ImageIcon("./data/pictures/bigbird.gif");
//    private ImageIcon Grover = new ImageIcon("./data/pictures/grover.gif");
    private JShellImage BigBirdObjects;
    private JShellImage GroverObjects;
    private int MAXOBJECTS = 20; //OLD 15
    private int MINOBJECTS = 4; 
    private static int FRAMEWIDTH = 10;
    private int lastbbposX = 0;
    private int lastbbposY = 0;
    private int lastgposX = 0;
    private int lastgposY = 0;
    private boolean BBwasLastAnswer = false;
    private double ObjectsImageWidthRatio = 6/16.0;//7.5/16.0; //3/8
    private static final double ObjectsImageHeightRatio = .95;//1.18/2;
    private double BBObjectsImageRatioFromSide = .6/5;//.25/16.0; //.5/8
    private static final double ObjectsImageRatioFromTop = .25/16.0;
    private double GObjectsImageRatioFromSide = .5 + BBObjectsImageRatioFromSide;
    private static final double CharacterHeight = 2.0/5.0;
    private static final double widthSeparationRatio = .25/16;
    private static final double edgeSeparationRatio = widthSeparationRatio;
    private double offscreenHeight;
    private double offscreenWidth;
    private int offscreenX;
    private int offscreenY;
    private static final double left = 0;//.18;
    private static final double top = 0;//.18;
    private static final double widthR = (1 - left*2);
    private static final double heightR = (1 - top*2);
    
    private boolean waitingForSpace = false;
    private boolean waitingForAnswer = false;
    private boolean testing = false;
	private boolean endSlide = false;
    
    /** Creates a new instance of JShellCanvas */
    
    public void paint(Graphics g)
    {
	if (endSlide && offscreen != null)
	{
        	g.drawImage(offscreen, offscreenX, offscreenY, null);
	}
	else
	{
        if (offscreen == null)
        {

            int height = (int)(getHeight()*(CharacterHeight));
            BBObjectsImageRatioFromSide = edgeSeparationRatio;//(BigBird.getIconWidth()*((double)height/BigBird.getIconHeight()))/getWidth();
            ObjectsImageWidthRatio = .5 - BBObjectsImageRatioFromSide - widthSeparationRatio;
            GObjectsImageRatioFromSide = .5+widthSeparationRatio;
	    offscreenHeight = widthR * this.getHeight();
	    offscreenWidth = heightR * this.getWidth();
	    offscreenX = (int)(left*this.getWidth());
 	    offscreenY = (int)(top*this.getHeight());
		System.out.println(offscreenX);
            offscreen = (BufferedImage)createImage((int)offscreenWidth, (int)offscreenHeight);
            clearScreen();
            if (BigBirdObjects == null)
                BigBirdObjects = new JShellImage((BufferedImage)createImage((int)((double)offscreenWidth*ObjectsImageWidthRatio) - 2*FRAMEWIDTH, (int)((double)offscreenHeight*ObjectsImageHeightRatio) - 2*FRAMEWIDTH), MAXOBJECTS, this, BigBirdBackColor);
            Graphics bbg = BigBirdObjects.getGraphics();
            bbg.setColor(BigBirdBackColor);
            bbg.fillRect(0, 0, BigBirdObjects.getWidth(), BigBirdObjects.getHeight());
            Graphics og = offscreen.getGraphics();
            og.setColor(BigBirdFrameColor);
            og.fillRect((int)(BBObjectsImageRatioFromSide*(double)(offscreenWidth)), (int)(ObjectsImageRatioFromTop*(double)(offscreenHeight)), BigBirdObjects.getWidth()+2*FRAMEWIDTH, BigBirdObjects.getHeight()+2*FRAMEWIDTH);
            lastbbposX = (int)(BBObjectsImageRatioFromSide*(double)(offscreenWidth))+FRAMEWIDTH;
            lastbbposY = (int)(ObjectsImageRatioFromTop*(double)(offscreenHeight))+FRAMEWIDTH;
            offscreen.getGraphics().drawImage(BigBirdObjects.getImage(), lastbbposX, lastbbposY, null);

            if (GroverObjects == null)
                GroverObjects = new JShellImage((BufferedImage)createImage((int)((double)offscreenWidth*ObjectsImageWidthRatio) - 2*FRAMEWIDTH, (int)((double)offscreenHeight*ObjectsImageHeightRatio) - 2*FRAMEWIDTH), MAXOBJECTS, this, GroverBackColor);
            Graphics gg = GroverObjects.getGraphics();
            gg.setColor(GroverBackColor);
            gg.fillRect(0, 0, GroverObjects.getWidth(), GroverObjects.getHeight());
            Graphics of = offscreen.getGraphics();
            og.setColor(GroverFrameColor);
            og.fillRect((int)(GObjectsImageRatioFromSide*(double)(offscreenWidth)), (int)(ObjectsImageRatioFromTop*(double)(offscreenHeight)), GroverObjects.getWidth()+2*FRAMEWIDTH, GroverObjects.getHeight()+2*FRAMEWIDTH);
            offscreen.getGraphics().drawImage(GroverObjects.getImage(), (int)(GObjectsImageRatioFromSide*(double)(offscreenWidth))+FRAMEWIDTH, (int)(ObjectsImageRatioFromTop*(double)(offscreenHeight))+FRAMEWIDTH, null);
	
            
        }
        g.setColor(backColor);
        g.fillRect(0, 0, getWidth(), getHeight());
        lastbbposX = (int)(BBObjectsImageRatioFromSide*(double)(offscreenWidth))+FRAMEWIDTH;
        lastbbposY = (int)(ObjectsImageRatioFromTop*(double)(offscreenHeight))+FRAMEWIDTH;
        offscreen.getGraphics().drawImage(BigBirdObjects.getImage(), lastbbposX, lastbbposY, null);
        offscreen.getGraphics().drawImage(GroverObjects.getImage(), (int)(GObjectsImageRatioFromSide*(double)(offscreenWidth))+FRAMEWIDTH, (int)(ObjectsImageRatioFromTop*(double)(offscreenHeight))+FRAMEWIDTH, null);
        g.drawImage(offscreen, offscreenX, offscreenY, (int)offscreenWidth, (int)offscreenHeight, null);
	}
    }
    
    public void paintComponent(Graphics g)
    {
	if (endSlide && offscreen != null)
	{
       		g.drawImage(offscreen, offscreenX, offscreenY, null);
	}
	else
	{
        if (offscreen == null)
        {
            int height = (int)(getHeight()*(CharacterHeight));
//            BBObjectsImageRatioFromSide = (BigBird.getIconWidth()*((double)height/BigBird.getIconHeight()))/getWidth();
            ObjectsImageWidthRatio = .5 - 0 - widthSeparationRatio;
            GObjectsImageRatioFromSide = .5 + widthSeparationRatio;
	    offscreenHeight = widthR * this.getHeight();
	    offscreenWidth = heightR * this.getWidth();
	    offscreenX = (int)(left*this.getWidth());
 	    offscreenY = (int)(top*this.getHeight());
            offscreen = (BufferedImage)createImage((int)offscreenWidth, (int)offscreenHeight);
            clearScreen();
         
        }
        g.drawImage(offscreen, offscreenX, offscreenY, null);
	}
    }
    
    public void setCurrentTest(Test t)
    {
        currentTest = t;
        testing = true;
    }
    
    public int getImagePositionX()
    {
        return getWidth()/2 - offscreen.getWidth(this)/2;
    }
    
    public int getImagePositionY()
    {
        return getHeight()/2 - offscreen.getHeight(this)/2;
    }
    
    public Image getOffscreen()
    {
        return offscreen;
    }
    
    public JShellImage getBigBirdObjects()
    {
        return BigBirdObjects;
    }
    
    public JShellImage getGroverObjects()
    {
        return GroverObjects;
    }
    
    public JShellPanel(Frame myFrame, int r, int g, int b)
    {

	super();     
	frame = myFrame;
        this.setSize(frame.getWidth(), frame.getHeight());
        backColor = new Color(r, g, b);
        this.setBackground(backColor);
        final JShellPanel panel = this;

    }
    
    public JShellPanel(Frame myFrame, int r, int g, int b, JShellImage bb, JShellImage gr)
    {
	super();     
	frame = myFrame;
        BigBirdObjects = bb;
        GroverObjects = gr;
        this.setSize(frame.getWidth(), frame.getHeight());
        backColor = new Color(r, g, b);
        this.setBackground(backColor);
        final JShellPanel panel = this;

    }
    
    public int getImageWidth()
    {
        return offscreen.getWidth(this);
    }
    
    public int getImageHeight()
    {
        return offscreen.getHeight(this);
    }
    
    public void changeBackColor(int r, int g, int b)
    {
        backColor = new Color(r, g, b);
    }
    private Test currentTest;
    
    public void stopTest(Thread pThread) throws TestStoppedException
    {
        testing = false;
        pThread.interrupt();
        try{
            Thread.sleep(1000);
        }
        catch (Exception e)
        {}
        
        throw new TestStoppedException();
    }
    
    public void setNewTest(Test t)
    {
        waitingForAnswer = false;
        waitingForSpace = false;
        testing = false;
        currentTest = t;
    }
    
    public Test getCurrentTest()
    {
        return currentTest;
    }

    
    public void setInput(String i)
    {
        inputValue = i;
    }
    private int count = 0;
    public void run()
    {
	endSlide = false;
        try{
            testing = true;
            try {
                currentTest.runTest();
            }
            catch(TestStoppedException tse)
            {}
            testing = false;
            //currentTest.writeToFile();
            //currentTest = currentTest.resetTest();
        }
        catch(Exception e){e.printStackTrace();}
        //currentTest.writeToFile();
        ((JShellFrame)frame).finishTest();

    }
    
    public int getMaxObjects()
    {
        return MAXOBJECTS;
    }

    public int getMinObjects()
    {
        return MINOBJECTS;
    }
    
    public synchronized void waitForSpace()
    {
        clearScreen();
        waitingForSpace = true;
                //Graphics g = offscreen.getGraphics();
        //g.setFont(new Font(null, Font.BOLD, 50));
        //g.setColor(Color.white);
        //g.drawString("Press space for next trial.", getImageWidth()/2 - 300, getImageHeight()/2);
        //repaint();
        try{
                wait();
            }catch(Exception e){}
        clearScreen();

        return;

    }
    
    public synchronized void waitForAnswer()
    {
        clearScreen();
        waitingForAnswer = true;
        //Graphics g = offscreen.getGraphics();
        //g.setFont(new Font(null, Font.BOLD, 50));
        //g.setColor(Color.white);
        //g.drawString("Press space for next trial.", getImageWidth()/2 - 300, getImageHeight()/2);
        //repaint();
        try{
                wait();
            }catch(Exception e){}
        clearScreen();

        return;

    }
    
    public boolean getWaitingForAnswer()
    {
        return waitingForAnswer;
    }
    
    public void setWaitingForAnswer(boolean w)
    {
        waitingForAnswer = w;
    }
    
    public void setWaitingForSpace(boolean w)
    {
        waitingForSpace = w;
    }
    
    public boolean getWaitingForSpace()
    {
        return waitingForSpace;
    }
    
    public synchronized void spaceWasPushed()
    {
        if (waitingForSpace)
        {
            waitingForSpace = false;
            notify();
        }
    } 
    
    public synchronized void BBwasPushed()
    {
        if (waitingForAnswer)
        { System.out.println("BBwasPushed");
            BBwasLastAnswer = true;
            waitingForAnswer = false;
            Test.continuePlaying = false;
            notify();
        }
    } 
        
    public synchronized void GwasPushed()
    {
        if (waitingForAnswer)
        {
	System.out.println("GwasPushed");
            BBwasLastAnswer = false;
            waitingForAnswer = false;
            Test.continuePlaying = false;
            notify();
        }
    } 

    public boolean getBBwasLastAnswer()
    {
        return BBwasLastAnswer;
    }
    
    public boolean Testing()
    {
        return testing;
    }

	public void endScreen()
{
   ImageIcon endPic = new ImageIcon("./data/pictures/daisyEndSlide.jpg");
        Graphics g = offscreen.getGraphics();
	g.drawImage(endPic.getImage(), 0, 0, (int)offscreenWidth, (int)offscreenHeight, endPic.getImageObserver());
	endSlide = true;
	paint(getGraphics());	
	
}
    
    public void clearScreen()
    {
        int height = (int)(offscreenHeight*(CharacterHeight));
        BBObjectsImageRatioFromSide = edgeSeparationRatio;//(BigBird.getIconWidth()*((double)height/BigBird.getIconHeight()))/offscreenWidth;
        ObjectsImageWidthRatio = .5 - BBObjectsImageRatioFromSide - widthSeparationRatio;
        GObjectsImageRatioFromSide = .5 + widthSeparationRatio;
        //offscreen = createImage(offscreenWidth, offscreenHeight);
        Graphics g = offscreen.getGraphics();
        g.setColor(backOffColor);
        g.fillRect(0, 0, getImageWidth(), getImageHeight());
        if (BigBirdObjects != null)
            BigBirdObjects.clearImage();
        if (GroverObjects != null)
            GroverObjects.clearImage();
//        g.drawImage(BigBird.getImage(), 0, (int)offscreenHeight - height, (int)((double)BigBird.getIconWidth()*(double)(height)/BigBird.getIconHeight()), height, BigBird.getImageObserver());
//        int width = (int)((double)Grover.getIconWidth()*((double)height/Grover.getIconHeight()));
//        g.drawImage(Grover.getImage(), (int)offscreenWidth - width, (int)offscreenHeight - height, width, height, Grover.getImageObserver());
        
		initJShellImages();
		drawObjects();
        repaint();
    }
	
	private void initJShellImages() {
		if (BigBirdObjects == null)
            BigBirdObjects = new JShellImage((BufferedImage)createImage((int)((double)offscreenWidth*ObjectsImageWidthRatio) - 2*FRAMEWIDTH, (int)((double)offscreenHeight*ObjectsImageHeightRatio) - 2*FRAMEWIDTH), MAXOBJECTS, this, BigBirdBackColor);
        Graphics bbg = BigBirdObjects.getGraphics();
        bbg.setColor(BigBirdBackColor);
        bbg.fillRect(0, 0, BigBirdObjects.getWidth(), BigBirdObjects.getHeight());

        if (GroverObjects == null)
            GroverObjects = new JShellImage((BufferedImage)createImage((int)((double)offscreenWidth*ObjectsImageWidthRatio) - 2*FRAMEWIDTH, (int)((double)offscreenHeight*ObjectsImageHeightRatio) - 2*FRAMEWIDTH), MAXOBJECTS, this, GroverBackColor);
        Graphics gg = GroverObjects.getGraphics();
        gg.setColor(GroverBackColor);
        gg.fillRect(0, 0, GroverObjects.getWidth(), GroverObjects.getHeight());
	}
	
	private void drawObjects() {
		Graphics g = offscreen.getGraphics();
        g.setColor(BigBirdFrameColor);
        g.fillRect((int)(BBObjectsImageRatioFromSide*(double)(offscreenWidth)), (int)(ObjectsImageRatioFromTop*(double)(offscreenHeight)), BigBirdObjects.getWidth()+2*FRAMEWIDTH, BigBirdObjects.getHeight()+2*FRAMEWIDTH);
        
        lastbbposX = (int)(BBObjectsImageRatioFromSide*(double)(offscreenWidth))+FRAMEWIDTH;
        lastbbposY = (int)(ObjectsImageRatioFromTop*(double)(offscreenHeight))+FRAMEWIDTH;
        offscreen.getGraphics().drawImage(BigBirdObjects.getImage(), lastbbposX, lastbbposY, null);
        

        g.setColor(GroverFrameColor);
        g.fillRect((int)(GObjectsImageRatioFromSide*(double)(offscreenWidth)), (int)(ObjectsImageRatioFromTop*(double)(offscreenHeight)), GroverObjects.getWidth()+2*FRAMEWIDTH, GroverObjects.getHeight()+2*FRAMEWIDTH);
        g.drawImage(GroverObjects.getImage(), (int)(GObjectsImageRatioFromSide*(double)(offscreenWidth))+FRAMEWIDTH, (int)(ObjectsImageRatioFromTop*(double)(offscreenHeight))+FRAMEWIDTH, null);
	
	}
    
    public void redrawScreen()
{
	    offscreenHeight = widthR * this.getHeight();
	    offscreenWidth = heightR * this.getWidth();
	    offscreenX = (int)(left*this.getWidth());
 	    offscreenY = (int)(top*this.getHeight());
        int height = (int)(offscreenHeight*(CharacterHeight));
        BBObjectsImageRatioFromSide = edgeSeparationRatio;//(BigBird.getIconWidth()*((double)height/BigBird.getIconHeight()))/offscreenWidth;
        ObjectsImageWidthRatio = .5 - BBObjectsImageRatioFromSide - widthSeparationRatio;
        GObjectsImageRatioFromSide = .5 + widthSeparationRatio;
        offscreen = createImage((int)offscreenWidth, (int)offscreenHeight);
        //System.out.println(offscreen);
        Graphics g = offscreen.getGraphics();
        g.setColor(backOffColor);
        g.fillRect(0, 0, getImageWidth(), getImageHeight());
        
//        g.drawImage(BigBird.getImage(), 0, (int)offscreenHeight - height, (int)((double)BigBird.getIconWidth()*(double)(height)/BigBird.getIconHeight()), height, BigBird.getImageObserver());
//        int width = (int)((double)Grover.getIconWidth()*((double)height/Grover.getIconHeight()));
//        g.drawImage(Grover.getImage(), (int)offscreenWidth - width, (int)offscreenHeight - height, width, height, Grover.getImageObserver());
        
		if (BigBirdObjects == null || GroverObjects == null) {
			initJShellImages();
		}
		else {
	        BigBirdObjects.resizeImage((int)((double)offscreenWidth*ObjectsImageWidthRatio) - 2*FRAMEWIDTH, (int)((double)offscreenHeight*ObjectsImageHeightRatio) - 2*FRAMEWIDTH);
	        
	        GroverObjects.resizeImage((int)((double)offscreenWidth*ObjectsImageWidthRatio) - 2*FRAMEWIDTH, (int)((double)offscreenHeight*ObjectsImageHeightRatio) - 2*FRAMEWIDTH);
		}
        drawObjects();
        
        repaint();      
    }
    
    public int getLastbbposX()
    {
        return lastbbposX;
    }
    
    public int getLastbbposY()
    {
        return lastbbposY;
    }
    
    public Frame getFrame()
    {
        return frame;
    }

    public void setFrame(Frame f)
    {
        frame = f;
    }
    
    public static final int MESSAGE_X =200;
    public static final int MESSAGE_Y =200;
    
    Vector myElementsToDraw;
    BufferedImage[] images;
    private int maximumHeightOrWidth = 50;
    private String textOfTheGuess;

    Vector maskOne;
    Vector maskTwo;
    Vector vectorOfTargets;



    // all from JShell.java


    private int delay = 100;
    private int numberOfTypesOfTrials = 5;
    private int numberOfRoundsPerTrial = 33;
    private int numberOfDifferentTypesOfProbes = 2 ;
    private int numberOfRounds = numberOfRoundsPerTrial * numberOfTypesOfTrials * numberOfDifferentTypesOfProbes;
    private final int finalNumberOfRounds = numberOfRounds;
    private int baseOfNumerosities = 5 ;
    private int rangeOfNumerosites = 11;
    private int testNumber = 1;


    //private java.util.Timer timer;


    private long startTime;
    private long endTime;


    private Random random;

    //private Test wholeTest;
    private boolean programDisplayedTheCorrectNumerosity;
    
    private int currentTypeOfTest = 1;
    private int timesThatThisTypeOfTestHasBeenDone = 0;
    
    private boolean readyForNext = false;
    private long lastTimeOfRepaint = 0;

    private Image imageOne;
    private Image imageTwo;



    long time1;
    long time2;
    long time3;
    long time4;
    



    public static int GREEN = 0;
    public static int RED = 1;
    public static int YELLOW = 2;
    public static int WHITE = 3;
    public static int BLUE = 4;
    public static int MAGENTA = 5;

    private Frame frame ;


}
