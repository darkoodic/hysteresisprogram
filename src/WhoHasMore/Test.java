/*
 * Test.java
 * Modified for QT STATIC IMAGES
 * FEBRUARY-9-2010
 * DARKO ODIC
 * JOHNS HOPKINS UNIVERISTY
 * 
 */

package WhoHasMore;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class Test {

	private JShellPanel panel;
	private static int NUMOBJECTS;
	private Random random = new Random();
	private Random random2 = new Random();
	private static final String correctSound = "./data/sounds/correct";
	private static final String wrongSound = "./data/sounds/wrong";
	//    private static final String whoHasMoreSound = "./data/sounds/WhoHasMore.wav";
	//    private static final String objectSound = "./data/sounds/objects/type";
	private static final String getReadySound = "./data/sounds/GetReady.wav";
	private static final String typeDir = "./data/pictures/";
	private static final String youreAllDoneSound = "./data/sounds/YoureAllDone.wav";
	private static final String youreDoingGreatSound = "./data/sounds/YoureDoingGreat.wav";
	private static final String letsPlayAGameSound = "./data/sounds/LetsPlayAGame.wav";
	//    private static final String hereAreBBobjects = "./data/sounds/HereAreBigBirds.wav";
	//    private static final String hereAreGobjects = "./data/sounds/HereAreGrovers.wav";
	private static long DISPLAYTIME = 1200;
	private static String TRIALARRAY_FILE = "";
	private static long FAMDISP = 2000;
	private int numtypes;
	private int trialnum = 0;
	private static final int NUMRATIOS = 7;
	//private static final int MINRATIONUM = 1;
	//private static final int MINRATIODEN = 2;
	//private static final int MAXRATIONUM = MINRATIONUM + NUMRATIOS - 1;
	//private static final int MAXRATIODEN = MINRATIODEN + NUMRATIOS - 1;
	private double[] ratio = {1.0/2,2.0/3,3.0/4,6.0/7,7.0/8,8.0/9,9.0/10};
	private int numtrials;
	private int numtrialsperbin = 5; //old is 15
	private int maxobjects;
	private int minobjects;
	private static final int BIGBIRD = 0;
	private static final int GROVER = 1;
	private int[][] trials;
	private int NUMFAMTRIALS = 4; //4
	private int [][] bin = new int[NUMRATIOS][4];
	private static final int CORRECTS = 0;
	private static final int WRONGS = 1;
	private static final int CORRECTNS = 2;
	private static final int WRONGNS = 3;
	private static final int MINTRIALSPERBIN = 3;
	private String results;
	private static final int TIMESTOTRY = 4;
	private static final int MAXTRIALS = 20;
	private static final int TIMESINAROW = 3;
	private static final int TIMESINAROWFAM = 2;
	private String resultsFile;
	public static boolean continuePlaying;
	private int[] timesUsed;
	private int[][] trialArray;
	private int[] shuffledNumbers;
	private int[] taBuffer;
	private int useNoMoreThan;
	private int bigbirdtrials = 0;
	private int grovertrials = 0;
	private int[] trialsperbin;
	private double[][] actualratiosum;
	private double[][] RTsum;
	private int SIZECON = 0;
	private int NOTSIZECON = 1;
	private int currenttrial;
	private boolean feedbackOn = true;
	private boolean isFeedbackVoiced = true;
	WritableWorkbook outputwb;
	WritableSheet sheet0;
	WritableSheet sheet1;
	WritableSheet sheet2;
	int bbcorrect;
	int gcorrect;
	String SubjID = "1";
	String outputFile = "./results/res.xls";

	private ImageIcon yellowIcon;
	private ImageIcon blueIcon;
	private AudioHelper audioHelper;

	//	private JSPlayback whoHasMoreJSP; 

	//first sheet consts
	private static final int SUBJIDCOL = 0;
	private static final int TRIALNUMCOL = 1;
	private static final int NUMBBCOL = 2;
	private static final int NUMGCOL = 3;
	private static final int CORRECTRESPCOL = 4;
	private static final int SUBJRESPCOL = 5;
	private static final int SUBJCORRECTCOL = 6;
	private static final int ACTUALRATIOCOL = 7;
	private static final int BINCOL = 8;
	private static final int RTCOL = 9;
	private static final int SIZECOL = 10;
	private static final int DISPTIMECOL = 11;
	private static final int TRIALARRAYCOL = 12;
	//	private static final int OBJECTNUMCOL = 12;

	//second sheet consts
	private static final int BINROW = 0;
	private static final int TIMESQRDROW = 1;
	private static final int TIMESCORRECTROW = 2;
	private static final int PERCORRECTROW = 3;
	private static final int AVGACTUALROW = 4;
	private static final int AVGRTROW = 5;


	public Test(JShellPanel _panel, String rf)
	{
		//NEW CODE
		trialArray = new int[ratio.length*numtrialsperbin][5];
		taBuffer= new int[5];
		//    	init("trials.txt");
		//    	loadTrialArray();
		/*
    	String buff = "";
    	for(Integer z = 1; z<=ratio.length*numtrialsperbin; z++)
    	{
    		buff = z.toString();
	    	taBuffer = getTrialLine(buff);
	    	trialArray[z-1][0]=taBuffer[0]; //trial #
	    	trialArray[z-1][1]=taBuffer[1]; //bb objects
	    	trialArray[z-1][2]=taBuffer[2]; //g objects
	    	System.err.printf("z:%d\n",z);
	    	//System.err.printf("tabuffer:%d\t%d\t%d\n",taBuffer[0],taBuffer[1],taBuffer[2]);
    	}
    	System.err.printf("Trial Array loaded");
		 */
		//DONE NEW CODE
		audioHelper =  new AudioHelper();
		continuePlaying = true;
		outputFile = rf;
		panel = _panel;
		//        whoHasMoreJSP = new JSPlayback(whoHasMoreSound, panel); 
		maxobjects = panel.getMaxObjects();
		minobjects = panel.getMinObjects();
		actualratiosum = new double[2][ratio.length];
		RTsum = new double[2][ratio.length];

		for (int i = 0; i < NUMRATIOS; i++)
		{
			//ratio[i] = ((double)(MINRATIONUM + i))/(MINRATIODEN + i);


			results += "bin " + i + " " + ratio[i] + "\n";
		}
		results += "\n";
		/*if (numtrials < MINTRIALSPERBIN * NUMRATIOS)
            numtrials = MINTRIALSPERBIN*NUMRATIOS;*/

		// to hard wire number of trials per bin:
		trialsperbin = new int[ratio.length];

		for (int i = 0; i < trialsperbin.length; i++)
			trialsperbin[i] = numtrialsperbin;

		/*
		for (int i = 0; i < 4; i++)
			trialsperbin[i] = 2;
		for (int i = 4; i < 7; i++)
			trialsperbin[i] = 10;
		trialsperbin[7] = 14;
		trialsperbin[8] = 14;
		 */
		numtrials = 0;

		for (int i = 0; i < ratio.length; i++)
		{
			numtrials += trialsperbin[i];
		}
		results = "";
	}

	// INSERTING FILE READ
	private static Hashtable<String,String> hash;

	public void loadTrialArray()
	{
		System.err.printf("Trial Array loading\n");
		String buff = "";

		List<Integer> intArray  = new ArrayList<Integer>();		
		for(Integer z = 1; z<=ratio.length*numtrialsperbin; z++)
		{
			intArray.add(z);
		}
		if(getTrialArrayFile().equals("trialArray_3.txt"))
		{
			Collections.shuffle(intArray);
		}


		for(Integer z = 1; z<=ratio.length*numtrialsperbin; z++)
		{
			buff = z.toString();
			taBuffer = getTrialLine(buff);
			trialArray[intArray.get(z-1)-1][0]=taBuffer[0]; //trial #
			trialArray[intArray.get(z-1)-1][1]=taBuffer[1]; //bb objects
			trialArray[intArray.get(z-1)-1][2]=taBuffer[2]; //g objects
			trialArray[intArray.get(z-1)-1][3]=taBuffer[3]; //random seed
			trialArray[intArray.get(z-1)-1][4]=taBuffer[4]; //size control
		}

		System.err.printf("Trial Array loaded\n");
		System.err.printf("*********************\n");

	}

	public static boolean init(String filename) {

		hash = new Hashtable<String,String>();
		boolean retval = true;

		try {

			BufferedReader f = new BufferedReader(new FileReader(filename));

			for (String line = f.readLine(); line != null; line = f.readLine()) {

				if (line.startsWith("#") || line.equals(""))
					continue;

				String[] split = line.split(":", 2);
				if (split.length != 2) {
					System.err.printf("Warning: Don't understand this line from config file, ignoring it: %s\n", line);
					continue;
				}

				String key = split[0].trim();
				String value = split[1].trim();
				//System.out.printf("Storing key %s, value %s\n", key, value);
				hash.put(key, value);

			}

			f.close();

		} catch (IOException e) {
			e.printStackTrace();
			retval = false;
		}

		return retval;

	}


	private static String[] getStringArray(String key) 
	{

		String v = hash.get(key);

		if (v == null) 
		{
			System.err.printf("Warning: no config value given for key %s; returning null, which might not work\n", key);
			return null;
		} 
		else 
		{
			return v.split(" ");
		}
	}

	private static int[] getIntArray(String key) 
	{
		String[] strings = getStringArray(key);
		if (strings == null)
			return null;

		int[] result = new int[strings.length];
		for (int i=0; i<result.length; i++) 
		{
			try 
			{
				result[i] = Integer.parseInt(strings[i]);
			} 
			catch (NumberFormatException e) 
			{
				System.err.printf("Warning: non-numeric config value given in value %s for key %s; returning null, which might not work\n", strings[i], key);
				return null;
			}
		}
		return result;
	}

	public static int[] getTrialLine(String inputvalue) {
		String value = inputvalue;
		return getIntArray(value);
	}
	// OLD CODE BELOW

	private void writeExcelHeaders(WritableSheet sheet) {
		try{
			Label label = new Label(SUBJIDCOL, 0, "Subject");
			sheet.addCell(label);
			label = new Label(TRIALNUMCOL, 0, "TrialNum");
			sheet.addCell(label);
			label = new Label(NUMBBCOL, 0, "Yellow");
			sheet.addCell(label);
			label = new Label(NUMGCOL, 0, "Blue");
			sheet.addCell(label);
			label = new Label(CORRECTRESPCOL, 0, "CorrectResponse");
			sheet.addCell(label);
			label = new Label(SUBJRESPCOL, 0, "SubjResponse");
			sheet.addCell(label);
			label = new Label(SUBJCORRECTCOL, 0, "Correct?");
			sheet.addCell(label);
			label = new Label(ACTUALRATIOCOL, 0, "ActualRatio");
			sheet.addCell(label);
			label = new Label(BINCOL, 0, "Bin");
			sheet.addCell(label);
			label = new Label(RTCOL, 0, "RT");
			sheet.addCell(label);
			label = new Label(SIZECOL, 0, "SizeControlled");
			sheet.addCell(label);
			label = new Label(DISPTIMECOL, 0, "DisplayTime");
			sheet.addCell(label);
			label = new Label(TRIALARRAYCOL, 0, "TrialArrayFile");
			sheet.addCell(label);
			//			label = new Label(OBJECTNUMCOL, 0, "ObjectNum");
			//			sheet.addCell(label);
		}
		catch(WriteException we)
		{}

	}

	public void runTest() throws TestStoppedException
	{
		bbcorrect = 0;
		gcorrect = 0;
		for (int i = 0; i < actualratiosum[SIZECON].length; i++)
		{
			actualratiosum[NOTSIZECON][i] = 0;
			RTsum[NOTSIZECON][i] = 0;
			actualratiosum[SIZECON][i] = 0;
			RTsum[SIZECON][i] = 0;
		}
		outputwb = null;
		try {
			outputwb = Workbook.createWorkbook(new File(outputFile));
		}
		catch(IOException ioe)
		{}

		if (outputwb != null)
		{
			sheet0 = outputwb.createSheet("practice", 0);
			writeExcelHeaders(sheet0);
			sheet1 = outputwb.createSheet("results", 1);
			writeExcelHeaders(sheet1);
			sheet2 = outputwb.createSheet("summary", 2);
		}

		if (isFeedbackVoiced) {
			panel.waitForSpace();


			JSPlayback jsp = new JSPlayback(letsPlayAGameSound, panel);
			jsp.playIt();
		}
		numtypes = getValidFiles(typeDir).length;

		timesUsed = new int[numtypes];
		//useNoMoreThan = (NUMFAMTRIALS+(MINTRIALSPERBIN*NUMRATIOS)+MAXTRIALS+1)/(numtypes) + 1;
		useNoMoreThan = (NUMFAMTRIALS + numtrials)/(numtypes) + 1;

		//        ImageIcon[] types = new ImageIcon[numtypes];
		//        for (int i = 0; i < numtypes; i++)
		//        {
		//            types[i] = new ImageIcon(typeDir + "type" + (i+1) + ".gif");
		//        }
		yellowIcon = new ImageIcon(typeDir + "yellow.gif");
		blueIcon = new ImageIcon(typeDir + "blue.gif");

		//Random random = new Random();
		//random.setSeed(5);
		JShellImage BigBirdObjects = panel.getBigBirdObjects();
		JShellImage GroverObjects = panel.getGroverObjects();
		//Graphics bbg = BigBirdObjects.getGraphics();
		//Graphics gg = GroverObjects.getGraphics();
		//BigBirdObjects.setType(new ImageIcon("C:/Joanna/ChildDev Lab/WhoHasMore/pictures/type 00" + "1" + ".jpg"));
		//trials = new int[numtrials][2];
		File[] correctFiles = getValidFiles(correctSound);
		File[] wrongFiles = getValidFiles(wrongSound);


		results += "Familiarization trials:\n";
		results += "BigBird\tGrover\tAnswer\tCorrect?\tRT\tRatio\tBin\n";
		FamiliarizationTrials(correctFiles, wrongFiles, NUMFAMTRIALS, BigBirdObjects, GroverObjects);
		results += "\nTest trials:\n";
		results += "BigBird\tGrover\tAnswer\tCorrect?\tRT\tRatio\tBin\n";

		//MORE MODS
		init(this.getTrialArrayFile());
		loadTrialArray();


		TestTrials(correctFiles, wrongFiles, numtrials, BigBirdObjects, GroverObjects);
		System.out.println(results);
		boolean done = saveResults();
		int response;
		JOptionPane jop = new JOptionPane();
		while (!done)
		{
			jop.showMessageDialog(panel, "The file could not be written, please enter a different file name.", "Error", JOptionPane.ERROR_MESSAGE);
			String filename = JOptionPane.showInputDialog(panel, "Please enter the filename", panel.getCurrentTest().getResultsFile());
			if (filename != null)
			{
				panel.getCurrentTest().setResultsFile(filename);
				File f = new File(filename);
				if (f.exists())
				{
					response = jop.showConfirmDialog(panel, "That file exists, are you sure you want to overwrite it?", "File exists", JOptionPane.YES_NO_OPTION);
					if (response == JOptionPane.YES_OPTION)
						done = saveResults();
				}
				else
					done = saveResults();
			}
			else
				done = false;
		} while(!done);
	}

	private void TestTrials(File[] correctFiles, File[] wrongFiles, int ntrials, JShellImage BigBirdObjects, JShellImage GroverObjects) throws TestStoppedException
	{
		int x = 0, y=0;
		currenttrial = 0;
		boolean BBhasMore = false;
		int totalSize = 0;
		boolean size = false;
		boolean ranTrial = false;

		for (int j = 0; j < numtrials /* NUMRATIOS*MINTRIALSPERBIN */; j++)
		{
			currenttrial = j+1;
			try{
				Label label = new Label(SUBJIDCOL, currenttrial, SubjID);
				sheet1.addCell(label);
				jxl.write.Number number = new jxl.write.Number(TRIALNUMCOL, currenttrial, currenttrial);
				sheet1.addCell(number);
				number = new jxl.write.Number(DISPTIMECOL, currenttrial, DISPLAYTIME);
				sheet1.addCell(number);
				jxl.write.Label trialA = new jxl.write.Label(TRIALARRAYCOL, currenttrial, this.getTrialArrayFile());
				sheet1.addCell(trialA);
			}
			catch(WriteException we)
			{}
			ranTrial = false;
			int numBBobjects = 0;
			int numGobjects = 0;
			int r; //= random.nextInt(NUMRATIOS);

			//MORE MODS
			//init(this.getTrialArrayFile());
			//loadTrialArray();
			int randomSeed = trialArray[trialnum][3];
			random.setSeed(randomSeed);

			/*BBhasMore = random.nextBoolean();
            if (BBhasMore && bigbirdtrials >= TIMESINAROW)
                BBhasMore = false;
            else if (!BBhasMore && grovertrials >= TIMESINAROW)
                BBhasMore = true;
            if (BBhasMore)
            {
                bigbirdtrials++;
                grovertrials = 0;
            }
            else
            {
                bigbirdtrials = 0;
                grovertrials++;
            }*/

			panel.waitForSpace();
			System.out.println("");

			while(!ranTrial)
			{
				double actualRatio = 1;
				int binIndex = 0;
				if (BBhasMore)
				{
					do {
						r = random.nextInt(NUMRATIOS);
						do
						{
							numGobjects = random.nextInt((int)(maxobjects*ratio[r]-minobjects+1)) + minobjects;
							numBBobjects = (int)((double)numGobjects / ratio[r]);
							actualRatio = (double)numGobjects/numBBobjects;
						} while (actualRatio > ratio[NUMRATIOS - 1] || actualRatio < ratio[0]);
						binIndex = findBin(actualRatio, r);
						System.out.println("*");
					} while (!shouldDo(binIndex));

				}
				else
				{
					do {

						r = random.nextInt(NUMRATIOS);
						do
						{
							numBBobjects = random.nextInt((int)(maxobjects*ratio[r]-minobjects+1)) + minobjects;
							numGobjects = (int)((double)numBBobjects / ratio[r]);
							actualRatio = (double)numBBobjects/numGobjects;
						} while (actualRatio > ratio[NUMRATIOS - 1] || actualRatio < ratio[0]);
						binIndex = findBin(actualRatio, r);
						System.out.println("*");
					} while (!shouldDo(binIndex));
				}

				//MORE MORE MODS
				numBBobjects = trialArray[trialnum][1];
				numGobjects = trialArray[trialnum][2];

				/*size = random.nextBoolean();
			if (size)
				totalSize++;
			if (size && totalSize > (ntrials/2))
			{
				totalSize--;
				size = false;
			}*/

				if(trialArray[trialnum][4]==1)
					size = true;
				else
					size = false;

				System.err.printf("BB:%d. G:%d\n",numBBobjects,numGobjects);
				ranTrial = runTestTrial(correctFiles, wrongFiles, BigBirdObjects, GroverObjects, binIndex, numBBobjects, numGobjects, ((numtrials)/2)==j, size, randomSeed);
				trialnum++;
				//END MODS
				if (!ranTrial)
				{
					System.out.println("!ranTrial");
					trialnum--;
				}
				else
				{
					if (size)
						actualratiosum[SIZECON][binIndex] += actualRatio;
					else
						actualratiosum[NOTSIZECON][binIndex] += actualRatio;
				}
			}
		}

		//JSPlayback jsp = new JSPlayback(youreAllDoneSound, panel);
		//jsp.playIt();

		if (isFeedbackVoiced) {
			JSPlayback jsp;
			jsp = new JSPlayback(youreAllDoneSound, panel);
			jsp.playIt();
		}
		try{
			Label label = new Label(0, BINROW, "Bin");
			sheet2.addCell(label);
			for (int i = 1; i <= ratio.length; i++)
			{
				label = new Label(i*2, BINROW, "NSC" + ratio[i-1]);
				sheet2.addCell(label);
				label = new Label(i*2 - 1, BINROW, "SC" + ratio[i-1]);
				sheet2.addCell(label);
			}
			label = new Label(0, TIMESQRDROW, "TimesQueried");
			sheet2.addCell(label);
			label = new Label(0, TIMESCORRECTROW, "TimesCorrect");
			sheet2.addCell(label);
			label = new Label(0, PERCORRECTROW, "PercentCorrect");
			sheet2.addCell(label);
			label = new Label(0, AVGACTUALROW, "AverageActualRatio");
			sheet2.addCell(label);
			label = new Label(0, AVGRTROW, "AverageRT");
			sheet2.addCell(label);
			label = new Label(0, currenttrial+2, "#timesBBcorrect");
			sheet1.addCell(label);
			label = new Label(0, currenttrial+3, "#timesGcorrect");
			sheet1.addCell(label);
			jxl.write.Number number = new jxl.write.Number(1, currenttrial+2, bbcorrect);
			sheet1.addCell(number);
			number = new jxl.write.Number(1, currenttrial+3, gcorrect);
			sheet1.addCell(number);

		}
		catch(WriteException we)
		{}


		//for (int i = 0; i < numtrials; i++) OMG WHY WAS THIS LOOP HERE?!
		//{
		for (int j = 0; j < ratio.length; j++)
		{

			try{
				int totals = bin[j][CORRECTS] + bin[j][WRONGS];
				int totalns = bin[j][CORRECTNS] + bin[j][WRONGNS];
				jxl.write.Number number = new jxl.write.Number((j+1)*2 - 1, TIMESQRDROW, totals);
				sheet2.addCell(number);
				number = new jxl.write.Number((j+1)*2 - 1, TIMESCORRECTROW, bin[j][CORRECTS]);
				sheet2.addCell(number);
				number = new jxl.write.Number((j+1)*2 - 1, PERCORRECTROW, ((double)bin[j][CORRECTS]/totals)*100);
				sheet2.addCell(number);
				number = new jxl.write.Number((j+1)*2 - 1, AVGACTUALROW, actualratiosum[SIZECON][j]/totals);
				sheet2.addCell(number);
				number = new jxl.write.Number((j+1)*2 - 1, AVGRTROW, RTsum[SIZECON][j]/totals);
				sheet2.addCell(number);
				number = new jxl.write.Number((j+1)*2, TIMESQRDROW, totalns);
				sheet2.addCell(number);
				number = new jxl.write.Number((j+1)*2, TIMESCORRECTROW, bin[j][CORRECTNS]);
				sheet2.addCell(number);
				number = new jxl.write.Number((j+1)*2, PERCORRECTROW, ((double)bin[j][CORRECTNS]/totalns)*100);
				sheet2.addCell(number);
				number = new jxl.write.Number((j+1)*2, AVGACTUALROW, actualratiosum[NOTSIZECON][j]/totalns);
				sheet2.addCell(number);
				number = new jxl.write.Number((j+1)*2, AVGRTROW, RTsum[NOTSIZECON][j]/totalns);
				sheet2.addCell(number);
			}
			catch(WriteException we)
			{}
		}
		//}

	}

	private boolean runTestTrial(File[] correctFiles, File[] wrongFiles, JShellImage BigBirdObjects, JShellImage GroverObjects, int binIndex, int numBBobjects, int numGobjects, boolean doingGood, boolean sizeControlled, int randomSeed)
	{
		System.out.println("Trial number: " + currenttrial);
		System.out.println("Seed Used: " + randomSeed);
		random2.setSeed(randomSeed);
		try{
			Robot robot = new Robot();
			robot.mouseMove(0, 0);
		}
		catch(Exception e)
		{}
		boolean BBhasMore = numBBobjects > numGobjects;
		int t = 0;
		/*
        do{
        	t = random.nextInt(numtypes);
        }
        while (timesUsed[t] >= useNoMoreThan);
		 */
		timesUsed[t]++;
		BigBirdObjects.setType(yellowIcon);
		GroverObjects.setType(blueIcon);

		//System.out.print("SC " + sizeControlled);
		//System.err.printf(" G:%d B:%d T:%d X:",numGobjects,numBBobjects,timesUsed[t]);
		if (sizeControlled)
		{
			if (BBhasMore)
				BigBirdObjects.changeTypeSize((double)numGobjects/numBBobjects);
			else
				GroverObjects.changeTypeSize((double)numBBobjects/numGobjects);
		}
		long startTime = 0;
		long endTime = 0;

		int x = 0, y = 0;

		int i = 0;
		int tried = 0;
		for (i = 0; i < numBBobjects; i++)
		{
			x = random2.nextInt(BigBirdObjects.getOriginalWidth() - 2 - (int)(BigBirdObjects.getTypeWidth()*(1.+BigBirdObjects.getSizePlusMinus()))) + 1;
			y = random2.nextInt(BigBirdObjects.getOriginalHeight() - 2 - (int)(BigBirdObjects.getTypeHeight()*(1.+BigBirdObjects.getSizePlusMinus()))) + 1;
			//System.out.print(x + ",");
			//System.out.println(x + " " + y);
			tried = 0;
			while (BigBirdObjects.isOverlapping(x, y) && tried < 100)
			{
				x = random2.nextInt(BigBirdObjects.getOriginalWidth() - 2 - (int)(BigBirdObjects.getTypeWidth()*(1.+BigBirdObjects.getSizePlusMinus()))) + 1;
				y = random2.nextInt(BigBirdObjects.getOriginalHeight() - 2 - (int)(BigBirdObjects.getTypeHeight()*(1.+BigBirdObjects.getSizePlusMinus()))) + 1;
				tried++;
				//System.out.println("************* " + tried);
			}
			if (tried >= 100)
			{
				/* BigBirdObjects.setType(types[t]);
                    GroverObjects.setType(types[t]);
                    runTestTrial(correctFiles, wrongFiles, BigBirdObjects, GroverObjects, types, binIndex, numBBobjects, numGobjects, doingGood, sizeControlled); */
				timesUsed[t]--;
				return false;
				//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

			}
			BigBirdObjects.addObject(x, y);
		}
		//System.out.println(" ");
		for (i = 0; i < numGobjects; i++)
		{
			x = random2.nextInt(GroverObjects.getOriginalWidth() - 2 - (int)(GroverObjects.getTypeWidth()*(1.+BigBirdObjects.getSizePlusMinus()))) + 1;
			y = random2.nextInt(GroverObjects.getOriginalHeight() - 2 - (int)(GroverObjects.getTypeHeight()*(1.+BigBirdObjects.getSizePlusMinus()))) + 1;
			tried = 0;
			//System.out.println(x + " " + y);
			while (GroverObjects.isOverlapping(x, y) && tried < 100)
			{
				x = random2.nextInt(GroverObjects.getOriginalWidth() - 2 - (int)(GroverObjects.getTypeWidth()*(1.+BigBirdObjects.getSizePlusMinus()))) + 1;
				y = random2.nextInt(GroverObjects.getOriginalHeight() - 2 - (int)(GroverObjects.getTypeHeight()*(1.+BigBirdObjects.getSizePlusMinus()))) + 1;
				tried++;
				//System.out.println("************ " + tried);
			}
			if (tried >= 100)
			{
				/*BigBirdObjects.setType(types[t]);
                    GroverObjects.setType(types[t]);
                    //System.out.println(">>>>>>>>>>>>>>>>>>>>>>");
                    runTestTrial(correctFiles, wrongFiles, BigBirdObjects, GroverObjects, types, binIndex, numBBobjects, numGobjects, doingGood, sizeControlled);*/
				timesUsed[t]--;
				return false;
				//i = 0;
			}
			GroverObjects.addObject(x, y);
		}

		JSPlayback jsp;
		//JSPlayback jsp = new JSPlayback(getReadySound, panel);
		//jsp.playIt();

		try{
			//Thread.sleep(DISPLAYTIME);
			results += numBBobjects + "\t" + numGobjects + "\t";

			try{
				jxl.write.Number number = new jxl.write.Number(NUMBBCOL, currenttrial, numBBobjects);
				sheet1.addCell(number);
				number = new jxl.write.Number(NUMGCOL, currenttrial, numGobjects);
				sheet1.addCell(number);
			}
			catch(WriteException we)
			{}

			panel.clearScreen();
			continuePlaying = true;
			startTime = System.currentTimeMillis();

			panel.repaint();
			panel.setWaitingForAnswer(true);
			continuePlaying = true;
			BigBirdObjects.drawImage();
			GroverObjects.drawImage();
			long onset = System.currentTimeMillis();
			panel.repaint();
			boolean cleared = false;
			long stop = System.currentTimeMillis();
			long time = DISPLAYTIME - (stop - onset);
			while (time > 0 && continuePlaying)
			{
				time -= 5;
				Thread.sleep(5);
			}
			if (!cleared) panel.clearScreen();
			if (panel.getWaitingForAnswer())
			{
				panel.waitForAnswer();
			}
			continuePlaying = true;
			endTime = System.currentTimeMillis();
			String subjresp;
			if (panel.getBBwasLastAnswer())
			{
				results += "BigBird\t";
				subjresp = "BigBird";

			}
			else
			{
				results += "Grover\t";
				subjresp = "Grover";
			}
			try{
				Label label = new Label(SUBJRESPCOL, currenttrial, subjresp);
				sheet1.addCell(label);
			}
			catch(WriteException we)
			{}
			double actual = 1;
			if (BBhasMore)
				actual = (double)numGobjects/numBBobjects;
			else
				actual = (double)numBBobjects/numGobjects;
			if (BBhasMore)
				bbcorrect++;
			else
				gcorrect++;
			if ((panel.getBBwasLastAnswer() && BBhasMore) || (!panel.getBBwasLastAnswer() && !BBhasMore))
			{
				results += "1\t";
				try{
					jxl.write.Number number = new jxl.write.Number(SUBJCORRECTCOL, currenttrial, 100);
					sheet1.addCell(number);
				}
				catch(WriteException we)
				{}
				if (feedbackOn) {
					if (isFeedbackVoiced) {
						int c = random.nextInt(correctFiles.length);
						//AudioPlayer.player.start(new AudioStream(new FileInputStream(correctFiles[c])));
						jsp = new JSPlayback(correctFiles[c], panel);
						jsp.playIt();
					} else {
						audioHelper.playCorrectSound();
					}
				}
				if (sizeControlled)
					bin[binIndex][CORRECTS]++;
				else
					bin[binIndex][CORRECTNS]++;
			}
			else
			{
				results += "0\t";
				try{
					jxl.write.Number number = new jxl.write.Number(SUBJCORRECTCOL, currenttrial, 0);
					sheet1.addCell(number);
				}
				catch(WriteException we)
				{}
				if (feedbackOn) {
					if (isFeedbackVoiced) {
						int w = random.nextInt(wrongFiles.length);
						//AudioPlayer.player.start(new AudioStream(new FileInputStream(wrongFiles[w])));
						jsp = new JSPlayback(wrongFiles[w], panel);
						jsp.playIt();
					} else {
						audioHelper.playIncorrectSound();
					}
				}
				if (sizeControlled)
					bin[binIndex][WRONGS]++;
				else
					bin[binIndex][WRONGNS]++;
			}
			long rt = endTime - startTime;
			if (sizeControlled)
				RTsum[SIZECON][binIndex] += rt;
			else
				RTsum[NOTSIZECON][binIndex] += rt;
			results += (endTime - startTime) + "\t";
			results += actual + "\t" + binIndex + "\t";
			String sizec;
			String correctresp;
			if (sizeControlled)
			{
				results += "yes\n";
				sizec = "yes";
			}
			else
			{
				results += "no\n";
				sizec = "no";
			}
			if (BBhasMore)
				correctresp = "BigBird";
			else
				correctresp = "Grover";

			try{
				jxl.write.Number number = new jxl.write.Number(RTCOL, currenttrial, rt);
				sheet1.addCell(number);
				number = new jxl.write.Number(BINCOL, currenttrial, ratio[binIndex]);
				sheet1.addCell(number);
				number = new jxl.write.Number(ACTUALRATIOCOL, currenttrial, randomSeed);
				sheet1.addCell(number);
				Label label = new Label(CORRECTRESPCOL, currenttrial, correctresp);
				sheet1.addCell(label);
				label = new Label(SIZECOL, currenttrial, sizec);
				sheet1.addCell(label);

			}
			catch(WriteException we)
			{}
			if (doingGood)
			{
				if (isFeedbackVoiced) {
					jsp = new JSPlayback(youreDoingGreatSound, panel);
					jsp.playIt();
				}
			}
			//panel.waitForSpace();

		}
		catch(InterruptedException ie)
		{System.out.println(ie);}



		BigBirdObjects.resetSize();
		GroverObjects.resetSize();
		return true;

	}

	private int findBin(double actual, int index)
	{
		int increment = 1;
		if (actual <= ratio[index])
		{
			if (index > 0 && actual > ratio[index-1])
				return index;
			else if (index == 0)
				return index;
			else
				increment = -1;
		}
		else
			increment = 1;
		boolean found = false;
		while (!found)
		{
			index += increment;
			if (actual <= ratio[index] && (index > 0 && actual > ratio[index-1]))
				found = true;
		}
		return index;
	}

	private boolean checkIfInBin(double r, int index)
	{
		if (index == 0)
			return (r == ratio[index]);
		else
			return (r <= ratio[index] && r > ratio[index - 1]);
	}

	private int findBinToTest(boolean[] tested)
	{
		boolean okay = true;
		int i;
		int[] needToTest = new int[bin.length];
		int num = 0;
		double[] ratioCorrect = new double[bin.length];
		double[] difference = new double[bin.length];

		for (i = 0; i < ratioCorrect.length; i++)
		{
			ratioCorrect[i] = (double)bin[i][CORRECTS]/(bin[i][CORRECTS] + bin[i][WRONGS]);
		}
		i = 0;
		if (difference.length > 1)
		{
			difference[0] = Math.abs(ratioCorrect[1] - ratioCorrect[0]);
		}
		while (i < bin.length - 1)
		{
			i++;
			if (i+1 < bin.length && !tested[i])
			{
				okay = (ratioCorrect[i-1] >= ratioCorrect[i] && ratioCorrect[i] >= ratioCorrect[i+1]);
				difference[i] = Math.abs(ratioCorrect[i+1] - ratioCorrect[i]) + Math.abs(ratioCorrect[i] - ratioCorrect[i - 1]);
			}
			else if (i+1 == bin.length && !tested[i])
			{
				okay = (ratioCorrect[i-1] >= ratioCorrect[i]);
				difference[i] = Math.abs(ratioCorrect[i] - ratioCorrect[i - 1]);
			}
			if (!okay)
			{
				needToTest[num] = i;
				num++;
			}
		}
		if (num > 0)
			//return needToTest[Smallest(needToTest, num)];
		return needToTest[BiggestDifference(needToTest, num, difference)];
		else
			return -1;
	}

	private int Smallest(int[] intarray, int num)
	{
		int smallest = 0;
		if (num > intarray.length)
			num = intarray.length;
		for (int i = 0; i < num; i++)
		{
			if ((bin[intarray[i]][CORRECTS] + bin[intarray[i]][WRONGS]) < (bin[intarray[smallest]][CORRECTS]+bin[intarray[smallest]][WRONGS]))
				smallest = i;
		}
		return smallest;
	}

	private int BiggestDifference(int[] intarray, int num, double[] difference)
	{
		int biggestDiff = 0;
		if (num > intarray.length)
			num = intarray.length;
		for (int i = 0; i < num; i++)
		{
			if(difference[intarray[i]] > difference[biggestDiff])
				biggestDiff = i;
		}
		return biggestDiff;
	}

	private boolean checkIfOkay(int i)
	{
		if (i == 0)
		{
			double current = (double)bin[i][CORRECTS]/(bin[i][CORRECTS] + bin[i][WRONGS]);
			double next = (double)bin[i+1][CORRECTS]/(bin[i+1][CORRECTS] + bin[i+1][WRONGS]);
			return (current >= next);
		}
		else if (i == bin.length - 1)
		{
			double prev = (double)bin[i-1][CORRECTS]/(bin[i-1][CORRECTS] + bin[i-1][WRONGS]);
			double current = (double)bin[i][CORRECTS]/(bin[i][CORRECTS] + bin[i][WRONGS]);
			return (prev >= current);
		}
		else
		{
			double prev = (double)bin[i-1][CORRECTS]/(bin[i-1][CORRECTS] + bin[i-1][WRONGS]);
			double current = (double)bin[i][CORRECTS]/(bin[i][CORRECTS] + bin[i][WRONGS]);
			double next = (double)bin[i+1][CORRECTS]/(bin[i+1][CORRECTS] + bin[i+1][WRONGS]);
			return (prev >= current && current >= next);
		}

	}

	private boolean shouldDo(int index)
	{
		int total = bin[index][CORRECTS] + bin[index][CORRECTNS] + bin[index][WRONGS] + bin[index][WRONGNS];
		return (total < trialsperbin[index]);
	}

	public String getResultsFile()
	{
		return outputFile;
	}

	public void setResultsFile(String rf)
	{
		outputFile = rf;
	}

	public void setPanel(JShellPanel p)
	{
		panel = p;
	}

	/*public boolean saveResults()
    {
		        			try{
							outputwb.write();
							outputwb.close();}
							catch(IOException ioe)
							{}
							catch(WriteException we)
            {}
        try{
	    FileWriter writer = new FileWriter(resultsFile);
	    writer.write(results);
	    writer.flush();
	    writer.close();
            return true;
        }
	catch(Exception e)
        {
            return false;
        }

    }*/

	private void FamiliarizationTrials(File correctFiles[], File wrongFiles[], int ntrials, JShellImage BigBirdObjects, JShellImage GroverObjects)
	{
		int maxfamobjects = 10;
		int minfamobjects = 4;
		boolean BBhasMore;
		int x = 0, y = 0;
		JSPlayback jsp;
		currenttrial = 0;

		for (int j = 0; j < ntrials; j++)
		{
			currenttrial = j+1;
			try{
				Label label = new Label(SUBJIDCOL, currenttrial, SubjID);
				sheet0.addCell(label);
				jxl.write.Number number = new jxl.write.Number(TRIALNUMCOL, currenttrial, currenttrial);
				sheet0.addCell(number);
				number = new jxl.write.Number(DISPTIMECOL, currenttrial, DISPLAYTIME);
				sheet0.addCell(number);
				jxl.write.Label trialA = new jxl.write.Label(TRIALARRAYCOL, currenttrial, this.getTrialArrayFile());
				sheet1.addCell(trialA);
			}
			catch(WriteException we)
			{}
			panel.waitForSpace();
			int t = 0;
			do{
				t = random.nextInt(numtypes);
			}
			while (timesUsed[t] >= useNoMoreThan);
			timesUsed[t]++;
			BigBirdObjects.setType(yellowIcon);
			GroverObjects.setType(blueIcon);

			//		try {
				//		jxl.write.Number number = new jxl.write.Number(OBJECTNUMCOL, currenttrial, (t+1));
				//					sheet0.addCell(number);
			//		}
			//		catch(WriteException we) {}

			int numBBobjects = 0;
			int numGobjects = 0;
			int r = random.nextInt(Math.round(NUMRATIOS/2));
			BBhasMore = random.nextBoolean();
			if (BBhasMore && bigbirdtrials >= TIMESINAROWFAM)
				BBhasMore = false;
			else if (!BBhasMore && grovertrials >= TIMESINAROWFAM)
				BBhasMore = true;
			if (BBhasMore)
			{
				bigbirdtrials++;
				grovertrials = 0;
			}
			else
			{
				bigbirdtrials = 0;
				grovertrials++;
			}


			if (BBhasMore)
			{
				do {
					numGobjects = random.nextInt((int)(maxfamobjects*ratio[r]-minfamobjects+1)) + minfamobjects;
					numBBobjects = (int)((double)numGobjects / ratio[r]);
					//numBBobjects = random.nextInt(maxfamobjects) + 1;
					//numGobjects = (int)((double)numBBobjects * ratio[r] + .5);
					System.out.println(numBBobjects + " " + numGobjects);
					BigBirdObjects.changeTypeSize((double)numGobjects/numBBobjects);
				} while (numBBobjects <= numGobjects);

			}
			else
			{
				do{
					numBBobjects = random.nextInt((int)(maxfamobjects*ratio[r]-minfamobjects+1)) + minfamobjects;
					numGobjects = (int)((double)numBBobjects / ratio[r]);
					System.out.println(numBBobjects + " " + numGobjects);
					GroverObjects.changeTypeSize((double)numBBobjects/numGobjects);
				} while (numGobjects <= numBBobjects);
			}

			for (int i = 0; i < numBBobjects; i++)
			{
				x = random.nextInt(BigBirdObjects.getOriginalWidth() - 2 - BigBirdObjects.getTypeWidth()) + 1;
				y = random.nextInt(BigBirdObjects.getOriginalHeight() - 2 - BigBirdObjects.getTypeHeight()) + 1;

				//System.out.println(x + " " + y);
				while (BigBirdObjects.isOverlapping(x, y))
				{
					x = random.nextInt(BigBirdObjects.getOriginalWidth() - 2 - BigBirdObjects.getTypeWidth()) + 1;
					y = random.nextInt(BigBirdObjects.getOriginalHeight() - 2 - BigBirdObjects.getTypeHeight()) + 1;
				}
				BigBirdObjects.addObject(x, y);
			}

			try{
				Robot robot = new Robot();
				robot.mouseMove(0, 0);
			}
			catch(java.awt.AWTException awte)
			{}
			//            jsp = new JSPlayback(hereAreBBobjects, panel);
			//            jsp.playIt();

			BigBirdObjects.drawImage();
			panel.repaint();
			//            jsp = new JSPlayback(objectSound + (t+1) + ".wav", panel);
			//            jsp.playIt();
			try{
				Thread.sleep(FAMDISP);
			}
			catch(InterruptedException ie)
			{}
			panel.clearScreen();

			for (int i = 0; i < numGobjects; i++)
			{
				x = random.nextInt(GroverObjects.getOriginalWidth() - 2 - GroverObjects.getTypeWidth()) + 1;
				y = random.nextInt(GroverObjects.getOriginalHeight() - 2 - GroverObjects.getTypeHeight()) + 1;

				//System.out.println(x + " " + y);
				while (GroverObjects.isOverlapping(x, y))
				{
					x = random.nextInt(GroverObjects.getOriginalWidth() - 2 - GroverObjects.getTypeWidth()) + 1;
					y = random.nextInt(GroverObjects.getOriginalHeight() - 2 - GroverObjects.getTypeHeight()) + 1;
				}
				GroverObjects.addObject(x, y);
			}
			//            jsp = new JSPlayback(hereAreGobjects, panel);
			//            jsp.playIt();

			GroverObjects.drawImage();
			panel.repaint();
			//            jsp = new JSPlayback(objectSound + (t+1) + ".wav", panel);
			//            jsp.playIt();
			try{
				Thread.sleep(FAMDISP);
			}
			catch(InterruptedException ie)
			{}
			panel.clearScreen();


			try{
				Thread.sleep(500);
			}
			catch(InterruptedException ie)
			{}

			/*try{
            jsp = new JSPlayback(getReadySound, panel);
            jsp.playIt();
        }
        catch(Exception e)
        {}*/
			//BigBirdObjects.drawImage();
			//GroverObjects.drawImage();
			//panel.repaint();
			long startTime;
			long endTime;
			//try{

			try{
				//Thread.sleep(DISPLAYTIME);
				results += numBBobjects + "\t" + numGobjects + "\t";
				//panel.clearScreen();
				//AudioPlayer.player.start(new AudioStream(new FileInputStream(whoHasMoreSound)));
				try{
					jxl.write.Number number = new jxl.write.Number(NUMBBCOL, currenttrial, numBBobjects);
					sheet0.addCell(number);
					number = new jxl.write.Number(NUMGCOL, currenttrial, numGobjects);
					sheet0.addCell(number);
				}
				catch(WriteException we)
				{}
				continuePlaying = true;
				startTime = System.currentTimeMillis();
				//            whoHasMoreJSP.playIt();
				//panel.repaint();
				panel.setWaitingForAnswer(true);
				continuePlaying = true;
				BigBirdObjects.drawImage();
				GroverObjects.drawImage();
				long onset = System.currentTimeMillis();
				panel.repaint();
				//            jsp = new JSPlayback(objectSound + (t+1) + ".wav", panel);
				//            jsp.playIt();
				long stop = System.currentTimeMillis();
				long time = DISPLAYTIME - (stop - onset);
				while (time > 0 && continuePlaying)
				{
					time -= 5;
					Thread.sleep(5);
				}
				panel.clearScreen();
				if (panel.getWaitingForAnswer())
				{
					panel.waitForAnswer();
				}
				continuePlaying = true;
				endTime = System.currentTimeMillis();
				String subjresp;
				if (panel.getBBwasLastAnswer())
				{
					results += "BigBird\t";
					subjresp = "BigBird";
				}
				else
				{
					results += "Grover\t";
					subjresp = "Grover";
				}
				try{
					Label label = new Label(SUBJRESPCOL, currenttrial, subjresp);
					sheet0.addCell(label);
				}
				catch(WriteException we)
				{}
				double actual = 1;
				if (BBhasMore)
					actual = (double)numGobjects/numBBobjects;
				else
					actual = (double)numBBobjects/numGobjects;
				if ((panel.getBBwasLastAnswer() && BBhasMore) || (!panel.getBBwasLastAnswer() && !BBhasMore))
				{
					results += "1\t";
					try{
						jxl.write.Number number = new jxl.write.Number(SUBJCORRECTCOL, currenttrial, 100);
						sheet0.addCell(number);
					}
					catch(WriteException we)
					{}
					if (isFeedbackVoiced) {
						int c = random.nextInt(correctFiles.length);
						//AudioPlayer.player.start(new AudioStream(new FileInputStream(correctFiles[c])));
						jsp = new JSPlayback(correctFiles[c], panel);
						jsp.playIt();
					} else {
						audioHelper.playCorrectSound();
					}
					//bin[binIndex][CORRECT]++;
				}
				else
				{
					results += "0\t";
					try{
						jxl.write.Number number = new jxl.write.Number(SUBJCORRECTCOL, currenttrial, 0);
						sheet0.addCell(number);
					}
					catch(WriteException we)
					{}
					if (isFeedbackVoiced) {
						int w = random.nextInt(wrongFiles.length);
						//AudioPlayer.player.start(new AudioStream(new FileInputStream(wrongFiles[w])));
						jsp = new JSPlayback(wrongFiles[w], panel);
						jsp.playIt();
					} else {
						audioHelper.playIncorrectSound();
					}
					//bin[binIndex][WRONG]++;
				}
				results += (endTime - startTime) + "\t";
				long rt = endTime - startTime;
				String sizec;
				String correctresp;
				// (not size controlled) 
				results += "no\n";
				sizec = "no";
				if (BBhasMore)
					correctresp = "BigBird";
				else
					correctresp = "Grover";

				try{
					jxl.write.Number number = new jxl.write.Number(RTCOL, currenttrial, rt);
					sheet0.addCell(number);
					number = new jxl.write.Number(BINCOL, currenttrial, -1); // there is no bin
					sheet0.addCell(number);
					number = new jxl.write.Number(ACTUALRATIOCOL, currenttrial, actual);
					sheet0.addCell(number);
					Label label = new Label(CORRECTRESPCOL, currenttrial, correctresp);
					sheet0.addCell(label);
					label = new Label(SIZECOL, currenttrial, sizec);
					sheet0.addCell(label);
				}
				catch(WriteException we)
				{}
				//results += actual + "\t" + binIndex + "\n";
				/*
            Thread.sleep(1000);
            results += numBBobjects + "\t" + numGobjects + "\t";
            panel.clearScreen();
            jsp = new JSPlayback(whoHasMoreSound, panel);
            startTime = System.currentTimeMillis();
            jsp.playIt();
            jsp = new JSPlayback(objectSound + (t+1) + ".wav", panel);
            jsp.playIt();
            panel.waitForAnswer();
            endTime = System.currentTimeMillis();
            if (panel.getBBwasLastAnswer())
                results += "BigBird\t";
            else
                results += "Grover\t";
            double actual = 1;
            if (BBhasMore)
                actual = (double)numGobjects/numBBobjects;
            else
                actual = (double)numBBobjects/numGobjects;
            if ((panel.getBBwasLastAnswer() && BBhasMore) || (!panel.getBBwasLastAnswer() && !BBhasMore))
            {
                results += "1\t";
                int c = random.nextInt(correctFiles.length);
                jsp = new JSPlayback(correctFiles[c], panel);
                jsp.playIt();
            }
            else
            {
                results += "0\t";
                int w = random.nextInt(wrongFiles.length);
                jsp = new JSPlayback(wrongFiles[w], panel);
                jsp.playIt();
            }*/
			}
			catch(InterruptedException ie)
			{System.out.println(ie);}


		}
	}


	public void setOutputFile(String filename)
	{
		String old = outputFile;
		outputFile = filename;
		try{
			if (outputwb != null)
				outputwb.setOutputFile(new File(outputFile));
		}
		catch(IOException ioe)
		{}
		if (!old.equals(filename))
			(new File(old)).delete();

	}

	public void deleteFile()
	{
		try{
			if (outputwb != null)
			{
				outputwb.write();
				outputwb.close();
			}
		}
		catch(IOException ioe)
		{}
		catch(WriteException we)
		{
		}
		(new File(outputFile)).delete();
	}

	public String getOutputFile()
	{
		return outputFile;
	}

	public void setSubjectID(String ID)
	{
		SubjID = ID;
	}

	public boolean saveResults()
	{
		try{
			if (outputwb != null)
			{
				outputwb.write();
				outputwb.close();
			}
		}
		catch(IOException ioe)
		{return false;}
		catch(WriteException we)
		{
			return false;
		}
		return true;
	}

	public void setDisplayTime(long disptime)
	{
		DISPLAYTIME = disptime;
	}

	public static long getDisplayTime() {
		return DISPLAYTIME;
	}

	public void setTrialArrayFile(String trialArrayFile)
	{
		TRIALARRAY_FILE = trialArrayFile;
	}

	public String getTrialArrayFile()
	{
		return TRIALARRAY_FILE;
	}
	public void setFeedbackOn(boolean feedbackOn, boolean isFeedbackVoiced) {
		this.feedbackOn = feedbackOn;
		this.isFeedbackVoiced = isFeedbackVoiced;
	}

	public File[] getValidFiles(String directory) {
		// get all files in the results directory
		File dir = new File(directory);
		FileFilter onlyFilesFilter = new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.isFile() && !pathname.isHidden() && !pathname.getName().startsWith(".");
			}
		};

		return dir.listFiles(onlyFilesFilter);
	}


}