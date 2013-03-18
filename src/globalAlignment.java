
public class globalAlignment extends Alignment{

    public globalAlignment(String scoreMatrixFile,String s1,String s2)
    {
        super(scoreMatrixFile,s1,s2);
    }

    public void global()
    {
        int i=0,j=0;
        outputMatrix[0][0] = 0;
        //initialize first row and column
        for (i=1;i<s1.length()+1;i++){
            outputMatrix[i][j] =  outputMatrix[i-1][j]+scoreMatrix[letters.get(s1.charAt(i-1))][6];
            j++;
        }
        for (j=1;j<s2.length()+1;j++){
            outputMatrix[i][j] =  outputMatrix[i][j-1]+scoreMatrix[letters.get(s2.charAt(j-1))][6];
            i++;
        }
        for(i=1;i<s1.length()+1;i++)
            for(j=1;j<s2.length()+1;j++)
                outputMatrix[i][j] = Math.max(outputMatrix[i-1][j]+scoreMatrix[letters.get(s1.charAt(i-1))][6], Math.max(outputMatrix[i-1][j-1]+scoreMatrix[letters.get(s1.charAt(i-1))][letters.get(s2.charAt(j-1))], outputMatrix[i][j-1]+scoreMatrix[6][letters.get(s2.charAt(j-1))]));
    }

    public void globalGap()
    {

    }

    public void globalAffine()
    {

    }

}
