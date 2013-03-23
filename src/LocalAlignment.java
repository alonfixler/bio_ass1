
public class LocalAlignment extends Alignment{
	
	public LocalAlignment(String scoreMatrixFile,String s1,String s2,String option1,String option2)
	{
		super(scoreMatrixFile,s1,s2,option1,option2);
	}
	
	public void local()
	{
		int max=0;
		for(int i=1;i<s1.length()+1;i++)	
			for(int j=1;j<s2.length()+1;j++)
			{
				intOutMatrix[i][j] = Math.max(0, Math.max(intOutMatrix[i-1][j]+scoreMatrix[letters.get(s1.charAt(i-1))][6], Math.max(intOutMatrix[i-1][j-1]+scoreMatrix[letters.get(s1.charAt(i-1))][letters.get(s2.charAt(j-1))], intOutMatrix[i][j-1]+scoreMatrix[6][letters.get(s2.charAt(j-1))])));
				// updating our trace back
				if(intOutMatrix[i][j]==intOutMatrix[i-1][j-1]+scoreMatrix[letters.get(s1.charAt(i-1))][letters.get(s2.charAt(j-1))])
					traceBack[i][j] = DIAG;
				else if(intOutMatrix[i][j]==intOutMatrix[i-1][j]+scoreMatrix[6][letters.get(s1.charAt(i-1))])
					traceBack[i][j] = UP;
				else if(intOutMatrix[i][j] == intOutMatrix[i][j-1]+scoreMatrix[letters.get(s2.charAt(j-1))][6])
					traceBack[i][j] = LEFT;	
				max = Math.max(max, intOutMatrix[i][j]);
			}
		tracePath(max);		// restoring the path of our local alignment
		
	}
	
	public void localGap()
	{
		double max=0;
		for(int i=1;i<s1.length()+1;i++)
			for(int j=1;j<s2.length()+1;j++)
				for(int k=0,l=0;k<j || l<i;k++,l++)
				{
					if(k<j && l<i)		// if both gap sizes are still within the boundaries
					{
						doubleOutMatrix[i][j] = Math.max(doubleOutMatrix[i][j], Math.max(doubleOutMatrix[l][j]+(-10+Math.log(i-l)), Math.max(doubleOutMatrix[i-1][j-1]+scoreMatrix[letters.get(s1.charAt(i-1))][letters.get(s2.charAt(j-1))], doubleOutMatrix[i][k]+(-10+Math.log(j-k)))));
						if(doubleOutMatrix[i][j] == doubleOutMatrix[l][j]+(-10+Math.log(i-l)))
						{
							gapSize[i][j] = l;
							traceBack[i][j] = LEFT;
						}
						else if(doubleOutMatrix[i][j] == doubleOutMatrix[i][k]+(-10+Math.log(j-k)))
						{
							gapSize[i][j] = k;
							traceBack[i][j] = UP;
						}
						else if(doubleOutMatrix[i][j] == doubleOutMatrix[i-1][j-1]+scoreMatrix[letters.get(s1.charAt(i-1))][letters.get(s2.charAt(j-1))])
						{
							traceBack[i][j] = DIAG;
						}
					}
					else if(k<j)		// only the row gap size is within the boundaries
					{
						doubleOutMatrix[i][j] = Math.max(doubleOutMatrix[i][j],Math.max(doubleOutMatrix[i-1][j-1]+scoreMatrix[letters.get(s1.charAt(i-1))][letters.get(s2.charAt(j-1))],doubleOutMatrix[i][k]+(-10+Math.log(j-k))));
						if(doubleOutMatrix[i][j] == doubleOutMatrix[i][k]+(-10+Math.log(j-k)))
						{
							gapSize[i][j] = k;
							traceBack[i][j] = UP;
						}
						else if(doubleOutMatrix[i][j] == doubleOutMatrix[i-1][j-1]+scoreMatrix[letters.get(s1.charAt(i-1))][letters.get(s2.charAt(j-1))])
						{
							traceBack[i][j] = DIAG;
						}
					}
					else				// only the column gap size is within the boundaries
					{
						doubleOutMatrix[i][j] = Math.max(doubleOutMatrix[i][j],Math.max(doubleOutMatrix[i-1][j-1]+scoreMatrix[letters.get(s1.charAt(i-1))][letters.get(s2.charAt(j-1))],doubleOutMatrix[l][j]+(-10+Math.log(i-l))));
						if(doubleOutMatrix[i][j] == doubleOutMatrix[l][j]+(-10+Math.log(i-l)))
						{
							gapSize[i][j] = l;
							traceBack[i][j] = LEFT;
						}
						else if(doubleOutMatrix[i][j] == doubleOutMatrix[i-1][j-1]+scoreMatrix[letters.get(s1.charAt(i-1))][letters.get(s2.charAt(j-1))])
						{
							traceBack[i][j] = DIAG;
						}
					}
					
					// updating our trace back
					if(doubleOutMatrix[i][j]==doubleOutMatrix[i-1][j-1]+scoreMatrix[letters.get(s1.charAt(i-1))][letters.get(s2.charAt(j-1))])
						traceBack[i][j] = DIAG;
					else if(doubleOutMatrix[i][j]==doubleOutMatrix[i][j-1]+scoreMatrix[6][letters.get(s2.charAt(j-1))])
						traceBack[i][j] = UP;
					else if(doubleOutMatrix[i][j] == doubleOutMatrix[i-1][j]+scoreMatrix[letters.get(s1.charAt(i-1))][6])
						traceBack[i][j] = LEFT;	
					max = Math.max(max, doubleOutMatrix[i][j]);
				}
		gapTracePath(max);			// restoring the path of our local alignment
	}
	
	public void localAffine()
	{
		int max=0;
		for(int i=1;i<s1.length()+1;i++)
			for(int j=1;j<s2.length()+1;j++)
			{
				double [] eValue = E(i,j);
				double [] fValue = F(i,j);
				double [] gValue = G(i,j);
				intOutMatrix[i][j] = (int) Math.max(0, Math.max(eValue[0],Math.max(fValue[0],gValue[0])));
				// updating our trace back and the gap size we used for our current output matrix value
				if(intOutMatrix[i][j]==gValue[0])
				{
					traceBack[i][j] = DIAG;
				}
				else if(intOutMatrix[i][j]==eValue[0])
				{
					gapSize[i][j] = (int) eValue[1];
					traceBack[i][j] = UP;
				}
				else if(intOutMatrix[i][j]==fValue[0])
				{
					gapSize[i][j] = (int) fValue[1];
					traceBack[i][j] = LEFT;
				}
				max = Math.max(max, intOutMatrix[i][j]);
			}
		gapTracePath(max);			// restoring the path of our local alignment
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
						print(i,j);
						System.out.println("Score: "+max);
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
		// find calculated max value
		if(max!=0)
		{
			outerloop:
			for(int i=s1.length();i>0;i--)
				for(int j=s2.length();j>0;j--)
					if(option2.equals("-p"))		// for the arbitrary gap case
					{
						if(doubleOutMatrix[i][j] == max)
						{
							print(i,j);
							System.out.println("Score: "+max);
							break outerloop;
						}
					}		
					else							// for the affine gap case
					{
						if(intOutMatrix[i][j] == max)
						{
							print(i,j);
							System.out.println("Score: "+max);
							break outerloop;
						}
					}
		}
		else										// if our max value is 0 we'll print out 2 empty strings 
		{
			System.out.println("Empty string");
			System.out.println("Empty string");
			System.out.println("Score: 0");
		}
	}
}