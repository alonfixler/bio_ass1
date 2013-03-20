
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
						break outerloop;
		}
		else
		{
			System.out.println("Empty string");
			System.out.println("Empty string");
			System.out.println("Score: 0");
		}
	}
	
	public void gapTracePath()
	{
		
	}
}