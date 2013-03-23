import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;



public class Alignment{
	final byte DIAG = 1;
	final byte UP = 2;
	final byte LEFT = 3;
	
	String s1;
	String s2;
	Hashtable<Character,Integer> letters;
	char [][] scoreMatrix;
	int [][] intOutMatrix;
	double [][] doubleOutMatrix;
	byte [][] traceBack;
	int [][] gapSize;
	int a;
	int b;
	int [][] E;
	int [][] eGapSize;
	int [][] F;
	int [][] fGapSize;
	String option1,option2;
	
	public Alignment(String scoreMatrixFile,String s1,String s2,String option1, String option2)
	{
		this.s1 = s1;
		this.s2 = s2;
		letters = new Hashtable<Character,Integer>();
		letters.put('A',0);
		letters.put('T',1);
		letters.put('G',2);
		letters.put('C',3);
		letters.put('U',4);
		letters.put('N',5);
		letters.put('*',6);
		scoreMatrix = new char [7][7];
		this.option1 = option1;
        this.option2 = option2;

        if(option2.equals("-p"))		// if we're in the arbitrary gap functio we'll init a double output matrix
		{
			doubleOutMatrix = new double[s1.length()+1][s2.length()+1];
			gapSize = new int[s1.length()+1][s2.length()+1];
		}
		else if(option2.equals("-a"))
		{
			intOutMatrix = new int[s1.length()+1][s2.length()+1];
			gapSize = new int[s1.length()+1][s2.length()+1];
			E = new int[s1.length()+1][s2.length()+1];
			eGapSize = new int[s1.length()+1][s2.length()+1];
			F = new int[s1.length()+1][s2.length()+1];
			fGapSize = new int[s1.length()+1][s2.length()+1];
		}
		else					// if we're in the regular local alignment we'll init an int output matrix
			intOutMatrix = new int[s1.length()+1][s2.length()+1];		

        traceBack = new byte[s1.length()+1][s2.length()+1];
		
		
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
							scoreMatrix[lineIndex-9][matIndex] = strLine.charAt(i);
							matIndex++;
						}
						else if(strLine.charAt(i)=='-')
						{
							scoreMatrix[lineIndex-9][matIndex] = (char)(strLine.charAt(i+1)*(-1));
							matIndex++;
							i++;
						}
					}
					lineIndex++;
				}
				
				if(option2.equals("-a"))
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
	
	public double[] G(int i, int j){
	        return new double[]{doubleOutMatrix[i][j]+scoreMatrix[letters.get(s1.charAt(i-1))][letters.get(s2.charAt(j-1))],DIAG};
	    }

    public double[] E(int i, int j){
        if(option2.equals("-p")){
            if(j==0){
                return new double[]{gapPenalty(i),i};
            }
            else{
                double max=Double.MIN_VALUE,temp,kVal=0;
                for(int k=0;k<j;k++){
                    temp = doubleOutMatrix[i][k]+gapPenalty(j-k);
                    if(max < temp){
                        max = temp;
                        kVal = k;
                    }
                }
                return new double[]{max,kVal};
            }
        }
        else
        {
            if(j == 0){
                return new double[]{(-a-(i*b)),i};
            }
            else{
            E[i][j] = Math.max(E[i][j-1],intOutMatrix[i][j-1]-a) -b;
                if(E[i][j] == E[i][j-1]-b)
                    eGapSize[i][j] = eGapSize[i][j-1]+1;
                else
                    eGapSize[i][j] = 1;
            return new double[]{E[i][j],eGapSize[i][j]};
            }
        }
    }

    public double[] F(int i, int j){
        if(option2.equals("-p"))
        {
            if(i == 0){
                return new double[]{gapPenalty(j),j};
            }
            else{
	            double max=Double.MIN_VALUE,temp,kVal=0;
	            for(int k=0;k<i;k++){
	                temp = doubleOutMatrix[k][j]+gapPenalty(i-k);
	                if(max < temp){
	                    max = temp;
	                    kVal = k;
	                }
	            }
	            return new double[]{max,kVal};
        	}
        }
        else
        {
            if(i == 0){
                return new double[]{(-a-(j*b)),j};
            }
            else{
            F[i][j] = Math.max(F[i-1][j],intOutMatrix[i-1][j]-a) -b;
            if(F[i][j] == F[i-1][j]-b)
                fGapSize[i][j] = fGapSize[i-1][j]+1;
            else
                fGapSize[i][j] = 1;
            return new double[]{F[i][j],fGapSize[i][j]};
            }
        }
    }
    
    public double gapPenalty(int k){
        return (Math.log(k)-10);
    }
	
	public void print(int i,int j)
	{
		String outString1 = "";
		String outString2 = "";
        while((i>0 || j>0) && traceBack[i][j]!=0)
        {
            if(option2.equals("")) //alignment without gaps
            {
                if(option1.equals("-l") && (i==0 || j==0))
                    break;
                if(traceBack[i][j]==DIAG)
                {
                    outString1 = s1.substring(i-1,i) + outString1;
                    outString2 = s2.substring(j-1,j) + outString2;
                    i--;
                    j--;
                }
                else if (traceBack[i][j]==UP)
                {
                    outString1 = s1.substring(i-1,i) + outString1;
                    outString2 = "_" + outString2;
                    i--;
                }
                else
                {
                    outString1 = "_" + outString1;
                    outString2 = s2.substring(j-1,j) + outString2;
                    j--;
                }
            } else
            {
                if(option1.equals("-l") && (i==0 || j==0))
                    break;
                if(traceBack[i][j]==DIAG)
                {
                    outString1 = s1.substring(i-1,i) + outString1;
                    outString2 = s2.substring(j-1,j) + outString2;
                    i--;
                    j--;
                }
                else if (traceBack[i][j]==UP)
                {
                    outString1 = s1.substring(i-1,i) + outString1;
                    for(int k=0;k<gapSize[i][j];k++)
                        outString2 = "_" + outString2;
                    i = i-gapSize[i][j];
                }
                else
                {
                    for(int k=0;k<gapSize[i][j];k++)
                        outString1 = "_" + outString1;
                    outString2 = s2.substring(j-1,j) + outString2;
                    j = j-gapSize[i][j];
                }
            }
        }
		System.out.println(outString1);
		System.out.println(outString2);
	}
}