
public class LocalAlignment extends Alignment{
	
	public LocalAlignment(String scoreMatrixFile,String s1,String s2)
	{
		super(scoreMatrixFile,s1,s2);
	}
	
	public void local()
	{
		for(int i=1;i<s1.length()+1;i++)
			for(int j=1;j<s2.length()+1;j++)
				outputMatrix[i][j] = Math.max(0, Math.max(outputMatrix[i-1][j]+scoreMatrix[letters.get(s1.charAt(i-1))][6], Math.max(outputMatrix[i-1][j-1]+scoreMatrix[letters.get(s1.charAt(i-1))][letters.get(s2.charAt(j-1))], outputMatrix[i][j-1]+scoreMatrix[6][letters.get(s2.charAt(j-1))])));
	}
	
	public void localGap()
	{
		
	}
	
	public void localAffine()
	{
		
	}
}