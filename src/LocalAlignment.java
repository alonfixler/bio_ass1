
public class LocalAlignment extends Alignment{
	
	public LocalAlignment(String scoreMatrixFile,String s1,String s2,String option)
	{
		super(scoreMatrixFile,s1,s2,option);
	}
	
	public void local()
	{
		int max=0;
		for(int i=1;i<s1.length()+1;i++)
			for(int j=1;j<s2.length()+1;j++)
			{
				intOutMatrix[i][j] = Math.max(0, Math.max(intOutMatrix[i-1][j]+scoreMatrix[letters.get(s1.charAt(i-1))][6], Math.max(intOutMatrix[i-1][j-1]+scoreMatrix[letters.get(s1.charAt(i-1))][letters.get(s2.charAt(j-1))], intOutMatrix[i][j-1]+scoreMatrix[6][letters.get(s2.charAt(j-1))])));
				if(intOutMatrix[i][j]==intOutMatrix[i-1][j-1]+scoreMatrix[letters.get(s1.charAt(i-1))][letters.get(s2.charAt(j-1))])
					traceBack[i][j] = DIAG;
				else if(intOutMatrix[i][j]==intOutMatrix[i][j-1]+scoreMatrix[6][letters.get(s2.charAt(j-1))])
					traceBack[i][j] = UP;
				else if(intOutMatrix[i][j] == intOutMatrix[i-1][j]+scoreMatrix[letters.get(s1.charAt(i-1))][6])
					traceBack[i][j] = LEFT;	
				max = Math.max(max, intOutMatrix[i][j]);
			}
		tracePath(max);
		
	}
	
	public void localGap()
	{
		double max=0;
		for(int i=1;i<s1.length()+1;i++)
			for(int j=1;j<s2.length()+1;j++)
				
			{
				intOutMatrix[i][j] = Math.max(0, Math.max(intOutMatrix[i-1][j]+scoreMatrix[letters.get(s1.charAt(i-1))][6], Math.max(intOutMatrix[i-1][j-1]+scoreMatrix[letters.get(s1.charAt(i-1))][letters.get(s2.charAt(j-1))], intOutMatrix[i][j-1]+scoreMatrix[6][letters.get(s2.charAt(j-1))])));
				if(intOutMatrix[i][j]==intOutMatrix[i-1][j-1]+scoreMatrix[letters.get(s1.charAt(i-1))][letters.get(s2.charAt(j-1))])
					traceBack[i][j] = DIAG;
				else if(intOutMatrix[i][j]==intOutMatrix[i][j-1]+scoreMatrix[6][letters.get(s2.charAt(j-1))])
					traceBack[i][j] = UP;
				else if(intOutMatrix[i][j] == intOutMatrix[i-1][j]+scoreMatrix[letters.get(s1.charAt(i-1))][6])
					traceBack[i][j] = LEFT;	
				max = Math.max(max, intOutMatrix[i][j]);
			}
		gapTracePath(max);
	}
	
	public void localAffine()
	{
		
	}
	
	public void tracePath(int max)
	{
		// find calculated max value
		if(max!=0)
		{
			outerloop:
			for(int i=s1.length();i>0;i--)
				for(int j=s2.length();j>0;j--)
					if(intOutMatrix[i][j] == max)
					{
						print(i,j,max);
						break outerloop;
					}
		}
		else
		{
			System.out.println("Empty string");
			System.out.println("Empty string");
			System.out.println("Score: 0");
		}
	}
	
	public void gapTracePath(double max)
	{
		
	}
	
	public void print(int i,int j,int max)
	{
		String outString1 = "";
		String outString2 = "";
		
		while(i>0 & j>0 && traceBack[i][j]!=0)
		{
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
		}
		
		System.out.println(outString1);
		System.out.println(outString2);
		System.out.println("Score: "+max);
	}
}