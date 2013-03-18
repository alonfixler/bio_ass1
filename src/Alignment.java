import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;



public class Alignment{
	final int DIAG = 1;
	final int UP = 2;
	final int LEFT = 3;
	
	String s1;
	String s2;
	Hashtable<String,Integer> letters;
	int [][] scoreMatrix;
	int [][][] intOutMatrix;
	double [][][] doubleOutMatrix;
	int a;
	int b;
	
	public Alignment(String scoreMatrixFile,String s1,String s2,String option)
	{
		this.s1 = s1;
		this.s2 = s2;
		letters = new Hashtable<String,Integer>();
		letters.put("A",0);
		letters.put("T",1);
		letters.put("G",2);
		letters.put("C",3);
		letters.put("U",4);
		letters.put("N",5);
		letters.put("*",6);
		scoreMatrix = new int [7][7];
		if(option.equals("-p"))
			doubleOutMatrix = new double[s1.length()+1][s2.length()+1][2];
		else
			intOutMatrix = new int[s1.length()+1][s2.length()+1][2];
		
		
		try{
			  // Open the file that is the first 
			  // command line parameter
			  FileInputStream fstream = new FileInputStream(scoreMatrixFile);
			  // Get the object of DataInputStream
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  //Read File Line By Line
			  int lineIndex = 1;
			  while ((strLine = br.readLine()) != null && lineIndex<16) 
			  {
				if(lineIndex<9)
				{
					lineIndex++;
					continue;
				}
				else
				{
					for(int i=0,matIndex=0;i<strLine.length();i++)
					{
						if(strLine.charAt(i)>=48 && strLine.charAt(i)<=57)
						{
							scoreMatrix[lineIndex-9][matIndex] = Character.getNumericValue(strLine.charAt(i));
							matIndex++;
						}
						else if(strLine.charAt(i)=='-')
						{
							scoreMatrix[lineIndex-9][matIndex] = Character.getNumericValue(strLine.charAt(i+1))*(-1);
							matIndex++;
							i++;
						}
					}
					lineIndex++;
				}
				
				if(option.equals("-a"))
				{
					strLine = br.readLine();
					strLine = br.readLine();
					strLine = br.readLine();
					a = Integer.parseInt(strLine.substring(2));
					strLine = br.readLine();
					b = Integer.parseInt(strLine.substring(2));
				}
			  }
			  //Close the input stream
			  in.close();
			    }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
	}
}