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



public class JShellFrame extends JFrame implements ComponentListener, KeyListener
{
    private boolean maximized = false;
    private Thread panelThread;
    private String defaultResultsFile = "./results/";
    private JShellFrame myFrame;

    public JShellFrame(boolean original, JShellImage bb, JShellImage gr)
    {
        //File[] results = (new File(defaultResultsFile)).listFiles();
        //if (results != null)
        //    defaultResultsFile += "results" + (results.length+1) + ".xls";
	defaultResultsFile += getDefaultResultsFileName(defaultResultsFile);

	testBegun = false;

	setTitle("Who Has More?");
	setSize(WIDTH, HEIGHT);

	JMenuBar menuBar = new JMenuBar();
	setJMenuBar(menuBar);

	JMenu fileMenu= new JMenu("File");
        final JShellFrame frame = this;
        if (!original)
            panel = new JShellPanel(this, 127, 127, 127, bb, gr);
        else
            panel = new JShellPanel(this, 127, 127, 127);
        panel.setFrame(this);
        this.getContentPane().add(panel);
        panel.setVisible(true);
        if (original)
            addKeyListener(this);
        addComponentListener(this);


	JMenuItem startTest = new JMenuItem("Start Test");

	startTest.addActionListener(new	ActionListener()
        {
		public void actionPerformed(ActionEvent event)
		{
                    if (panelThread != null)
                    {
                        try
                        {

                            panel.stopTest(panelThread);
                        }
                        catch(TestStoppedException tse)
                        {}

                    }

                    panelThread = new Thread(panel);
                    JOptionPane jop = new JOptionPane();
                    boolean done = false;
                    boolean cancelled = false;
                    String filename = "";
                    do
                    {
                        filename = jop.showInputDialog(panel, "Where would you like to save the results of this test?", defaultResultsFile);
                        if (filename != null)
                        {
                            File f = new File(filename);
                            int response;
                            if (f.exists())
                            {
                                response = jop.showConfirmDialog(panel, "That file exists, are you sure you want to overwrite it?", "File exists", JOptionPane.YES_NO_OPTION);
                                if (response == JOptionPane.YES_OPTION)
                                    done = true;
                            }
                            else
                                done = true;
                        }
                        else
                        {
                            cancelled = true;
                            done = true;
                        }
                    } while (!done);
					if (!cancelled)
					{
						String input = "";
						String disptime = "";
						String feedbackInput = "";
						String trialFile = "";
						boolean useFeedback = false;
						String voiceFeedbackInput = "";
						boolean isFeedbackVoiced = false;
						long disptimems = 0;
						while (input == null || input.equals(""))
						{
							input = jop.showInputDialog(panel, "Please enter a subject ID: ");
						}
						while (trialFile == null || trialFile.equals(""))
						{
							trialFile = jop.showInputDialog(panel, "Please enter difficulty (1e, 2h, 3r)");
							trialFile="trialArray_"+trialFile+".txt";							
						}						
						while (feedbackInput == null || !(feedbackInput.equalsIgnoreCase("y") || feedbackInput.equalsIgnoreCase("n"))) {
							feedbackInput = jop.showInputDialog(panel, "Please enter y/n for whether feedback should be given during test trials: ");
						}
						useFeedback = feedbackInput.equalsIgnoreCase("y");
						if (useFeedback) {
							while (voiceFeedbackInput == null || !(voiceFeedbackInput.equalsIgnoreCase("y") || voiceFeedbackInput.equalsIgnoreCase("n"))) {
								voiceFeedbackInput = jop.showInputDialog(panel, "Please enter y/n for whether feedback should be voiced (the alternative is beeps of different tones): ");
							}
						}
						isFeedbackVoiced = voiceFeedbackInput.equalsIgnoreCase("y");
						while (disptime == null || disptime.equals(""))
						{
							disptime = jop.showInputDialog(panel, "Please enter the display time in milliseconds: ", Test.getDisplayTime());
							try{
								disptimems = Integer.parseInt(disptime);
							}
							catch (NumberFormatException nfe)
							{
								disptime = "";
							}
						}
                        panel.setNewTest(new Test(panel, filename));
                        panel.getCurrentTest().setSubjectID(input);
                        panel.getCurrentTest().setTrialArrayFile(trialFile);
                        panel.getCurrentTest().setDisplayTime(disptimems);
			panel.getCurrentTest().setFeedbackOn(useFeedback, isFeedbackVoiced);
			panel.getCurrentTest().setOutputFile(filename);
						panelThread.start();
                    }/*
                    if (!cancelled)
                    {
                        panel.setNewTest(new Test(panel, filename));
                       panelThread.start();
                    }*/


		}});
        JMenuItem fullScreen = new JMenuItem("Full Screen Toggle (Press 4)");

	fullScreen.addActionListener(new	ActionListener()
        {
		public void actionPerformed(ActionEvent event)
		{
                    maximize();
                }
        });


	JMenuItem quitJShell = new JMenuItem("Quit");
	quitJShell.addActionListener(new	ActionListener()
	    {
		public void actionPerformed(ActionEvent event)
		{
                    quit();
		}});


	fileMenu.add(startTest);
        fileMenu.add(fullScreen);
	fileMenu.add(quitJShell);
        menuBar.add(fileMenu);

	Container contentPane = getContentPane();
	contentPane.add(panel);


        addWindowListener(new WindowListener()
        {
            public void windowDeactivated(WindowEvent e)
            {}
            public void windowActivated(WindowEvent e)
            {}
            public void windowDeiconified(WindowEvent e)
            {}
            public void windowIconified(WindowEvent e)
            {}
            public void windowClosed(WindowEvent e)
            {}
            public void windowClosing(WindowEvent e)
            {
                quit();
            }
            public void windowOpened(WindowEvent e)
            {}
            public void windowOpening(WindowEvent e)
            {}
        });

    }



    public void componentResized(ComponentEvent e)
    {
    	System.out.println("new dims: " + this.getWidth() + "," + this.getHeight());
        panel.redrawScreen();
    }
    public void componentHidden(ComponentEvent e)
    {}
    public void componentMoved(ComponentEvent e)
    {}
    public void componentShown(ComponentEvent e)
    {}


    		public void keyPressed(KeyEvent e){
		    char characterThatWasPushed = e.getKeyChar();

		    if(characterThatWasPushed == ' ')
			{
                            if (myFrame != null)
                                myFrame.getPanel().spaceWasPushed();

                            panel.spaceWasPushed();
			}
                    else if(e.getKeyCode() == KeyEvent.VK_F)
                    {
			
                            if (myFrame != null)
                                myFrame.getPanel().BBwasPushed();
                            panel.BBwasPushed();
		    }
			
                    else if(e.getKeyCode() == KeyEvent.VK_J)
 
		    {
                            if (myFrame != null)
                                myFrame.getPanel().GwasPushed();
                            panel.GwasPushed();

                    }
                    else if (e.getKeyCode() == KeyEvent.VK_4)
                    {
                        if (!maximized)
                            maximize();
                        else
                            minimize();
                    }
		}
		public void keyReleased(KeyEvent e){
		}
		public void keyTyped(KeyEvent e){
		}


    public JShellPanel getPanel()
    {
        return panel;
    }

    private void maximize ()
    {
        if (myFrame == null)
        {
            myFrame = new JShellFrame(false, panel.getBigBirdObjects(), panel.getGroverObjects());
		//myFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
            myFrame.setBounds(0, 0, (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());

	    myFrame.setUndecorated(true);
//            myFrame.getJMenuBar().setVisible(false);
            myFrame.addKeyListener(this);
            myFrame.getPanel().setFrame(myFrame);
        }
        if (panel.getCurrentTest() != null)
        {
            panel.getCurrentTest().setPanel(myFrame.getPanel());
            myFrame.getPanel().setCurrentTest(panel.getCurrentTest());
            myFrame.getPanel().setWaitingForAnswer(panel.getWaitingForAnswer());
            myFrame.getPanel().setWaitingForSpace(panel.getWaitingForSpace());
        }
        //panel.redrawScreen();


        this.setVisible(false);
        myFrame.setVisible(true);
                myFrame.getPanel().redrawScreen();
        //myFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());

        maximized = true;

    }

    private void minimize()
    {
        //panel.redrawScreen();
        if (panel.getCurrentTest() != null)
            panel.getCurrentTest().setPanel(panel);
        panel.setWaitingForAnswer(myFrame.getPanel().getWaitingForAnswer());
        panel.setWaitingForSpace(myFrame.getPanel().getWaitingForSpace());
        myFrame.setVisible(false);
        panel.redrawScreen();
        this.setVisible(true);
        maximized = false;
    }

    private void quit()
    {
        if (panel.Testing())
        {
            boolean cancelled = false;
            JOptionPane jop = new JOptionPane();
            int response = jop.showConfirmDialog(panel, "You are in the middle of a test, are you sure you want to quit?", "Testing!", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION)
            {
                response = jop.showConfirmDialog(panel, "Would you like to save the results from the current test before quitting?", "Save resutls?", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION)
                {
                    boolean done = false;
                    do{
                        String filename = JOptionPane.showInputDialog(panel, "Please enter the filename", panel.getCurrentTest().getResultsFile());
                        if (filename != null)
                        {
                            File f = new File(filename);
                            if (f.exists() && !filename.equals(panel.getCurrentTest().getOutputFile()))
                            {
                                response = jop.showConfirmDialog(panel, "That file exists, are you sure you want to overwrite it?", "File exists", JOptionPane.YES_NO_OPTION);
                                if (response == JOptionPane.YES_OPTION)
                                {
                                    panel.getCurrentTest().setOutputFile(filename);                                    
                                    done = panel.getCurrentTest().saveResults();
                                    if (!done)
                                        jop.showMessageDialog(panel, "The file could not be written, please enter a different file name.", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                            else
                            {
                                panel.getCurrentTest().setOutputFile(filename);
                                done = panel.getCurrentTest().saveResults();
                                    if (!done)
                                        jop.showMessageDialog(panel, "The file could not be written, please enter a different file name.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        else
                        {
                            done = true;
                            cancelled = true;
                        }

/*
                            panel.getCurrentTest().setResultsFile(filename);
                            File f = new File(filename);
                            if (f.exists())
                            {
                                response = jop.showConfirmDialog(panel, "That file exists, are you sure you want to overwrite it?", "File exists", JOptionPane.YES_NO_OPTION);
                                if (response == JOptionPane.YES_OPTION)
                                {
                                    done = panel.getCurrentTest().saveResults();
                                    if (!done)
                                        jop.showMessageDialog(panel, "The file could not be written, please enter a different file name.", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                            else
                            {
                                done = panel.getCurrentTest().saveResults();
                                    if (!done)
                                        jop.showMessageDialog(panel, "The file could not be written, please enter a different file name.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        else
                        {
                            done = true;
                            cancelled = true;
                        } */
                    } while(!done);
                }
		else
		{
			panel.getCurrentTest().deleteFile();
		}
                if (!cancelled)
                    System.exit(0);
            }
        }
        else
            System.exit(0);
    }

    public void clearPanel()
    {
        panel.clearScreen();
    }


        public void setPanel(JShellPanel p)
    {
        panel = p;
    }

    public void finishTest()
    {
        panel.clearScreen();
        Graphics g = panel.getOffscreen().getGraphics();
        g.setFont(new Font(null, Font.BOLD, 40));
        g.setColor(Color.white);
        //g.drawString("The experiment is finished.", panel.getImageWidth()/2 - 300, panel.getImageHeight()/2 - 50);
        //g.drawString("Thank you for participating!", panel.getImageWidth()/2 - 300, panel.getImageHeight()/2 + 25);
        panel.repaint();
        //setResizable(true);
        testBegun = false;
	panel.endScreen();
    }



    private JDesktopPane desktop;
    private JShellPanel panel;
    private boolean waitingForResponse = false;
    private boolean startNextRound = false;
    private boolean testBegun;



    // parameters
    private static final int WIDTH =  600;
    private static final int HEIGHT = 450;

	private String getDefaultResultsFileName(String directory) {
		// get all files in the results directory
		File dir = new File(directory);
		FileFilter onlyFilesFilter = new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.isFile() && !pathname.isHidden() && !pathname.getName().startsWith(".");
			}
		};
		
		File[] results = dir.listFiles(onlyFilesFilter);
		int numResults = results != null ? results.length : 0;

		return "results" + (numResults + 1) + ".xls";
	}

}








