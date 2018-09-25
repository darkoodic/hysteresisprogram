/*
 * JSPlayback.java
 *
 * Created on February 22, 2006, 1:52 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package WhoHasMore;

import javax.sound.sampled.*;
import java.io.*;
/**
 *
 * @author Joanna Kochaniak
 */
public class JSPlayback implements LineListener {
    
    AudioFormat af;
    AudioInputStream ais;
    Clip c;
    JShellPanel panel;
    
    /** Creates a new instance of JSPlayback */
    public JSPlayback( String sSoundFile, JShellPanel _panel ) {
            File f = new File(sSoundFile);
            init(f, _panel);
    }
    
    public JSPlayback( File soundFile, JShellPanel _panel ) {
        init(soundFile, _panel);
    }

    private void init( File soundFile, JShellPanel _panel ) {
        panel = _panel;
        try {
            ais = AudioSystem.getAudioInputStream(soundFile);
            af = ais.getFormat();
		DataLine.Info dliClip = new DataLine.Info(Clip.class, af);
		if (AudioSystem.isLineSupported(dliClip))
         	   {
          	      c = (Clip)AudioSystem.getLine(dliClip);
			c.open(ais);
		}
        }
        catch(Exception e)
        {}
    }

    
    public void playIt() throws TestStoppedException
    {
        if (!panel.Testing())
        {
            System.out.println("TEST STOPPED");
            throw new TestStoppedException();
        }
        try
        {
            if (c != null)
            {
				c.setFramePosition(0);
                c.start();
				Thread.sleep(5); // necessary to make c.isRunning() immediately true on windows
		
                while (c.isRunning() && Test.continuePlaying)
                { 
                    Thread.sleep(5);
                }
                if (!Test.continuePlaying) {
   			 c.stop();
		}
            }
        }
        catch(Exception e)
        {}
    }

    public boolean playIt(long time) throws TestStoppedException
    {
	long start = System.currentTimeMillis();
	boolean cleared = false;
        if (!panel.Testing())
        {
            System.out.println("TEST STOPPED");
            throw new TestStoppedException();
        }
        try
        {            
            if (c != null)
            {
				c.setFramePosition(0);
                c.start();
				Thread.sleep(5); // necessary to make c.isRunning() immediately true on windows
		
		while (c.isRunning() && Test.continuePlaying)
                {
				if (System.currentTimeMillis() - start >= time && !cleared)
		    {
			panel.clearScreen();
			cleared = true;
		    }
		    Thread.sleep(5);
                }
                if (!Test.continuePlaying) {
                    c.stop();
		}
		
                return cleared;
            }
        }
        catch(Exception e)
        {}
	return cleared;
    }
    
    public void update(LineEvent event)
    {
        if(event.getType().equals(LineEvent.Type.STOP))
        {
            try{ c.close(); }
            catch(Exception e){}
        }
    }
}
