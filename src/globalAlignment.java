
public class globalAlignment extends Alignment{

    int i,j,cur,up,diag,left;
    double[] eValue,fValue,gValue;
    double vValue;
    public globalAlignment(String scoreMatrixFile,String s1,String s2, String option1,String option2)
    {
        super(scoreMatrixFile,s1,s2,option1,option2);
    }

    public void global()
    {
        initBaseCases();
        for(i=1;i<s1.length()+1;i++){
            for(j=1;j<s2.length()+1;j++){
                left = intOutMatrix[i][j-1]+scoreMatrix[letters.get(s2.charAt(j-1))][6];
                diag = intOutMatrix[i-1][j-1]+scoreMatrix[letters.get(s1.charAt(i-1))][letters.get(s2.charAt(j-1))];
                up = intOutMatrix[i-1][j]+scoreMatrix[6][letters.get(s1.charAt(i-1))];
                cur = intOutMatrix[i][j] = Math.max(up, Math.max(diag, left));

                if (cur==diag) 	traceBack[i][j] = DIAG;
                else if (cur==up) 	traceBack[i][j] = UP;
                else if (cur==left) 	traceBack[i][j] = LEFT;
            }
        }
        tracePath(vValue);
    }

    public void globalGap()
    {
        initBaseCasesWithGap();
        for(i=1;i<s1.length()+1;i++){
            for(j=1;j<s2.length()+1;j++){
                eValue = E(i,j);
                fValue = F(i,j);
                gValue = G(i,j);
                vValue = doubleOutMatrix[i][j] = Math.max(gValue[0],Math.max(eValue[0],fValue[0]));

                if (vValue==gValue[0]){
                    traceBack[i][j] = DIAG;
                }
                else if (vValue==eValue[0]){
                    traceBack[i][j] = LEFT;
                    gapSize[i][j] = (int) eValue[1];
                }
                else if (vValue==fValue[0]){
                    traceBack[i][j] = UP;
                    gapSize[i][j] = (int) fValue[1];
                }
            }
        }
        tracePath(vValue);
    }

    public void globalAffine()
    {
        initBaseCasesWithAffineGap();
        for(i=1;i<s1.length()+1;i++){
            for(j=1;j<s2.length()+1;j++){
                eValue = E(i,j);
                fValue = F(i,j);
                gValue = G(i,j);
                vValue = intOutMatrix[i][j] = (int) Math.max(gValue[0],Math.max(eValue[0],fValue[0]));

                if (vValue==gValue[0]){
                    traceBack[i][j] = DIAG;
                }
                else if (vValue==eValue[0]){
                    for(int n=0;n<eValue[1];n++){
                        traceBack[i][j-n] = UP;
                        gapSize[i][j] = (int) eValue[1];
                    }
                }
                else if (vValue==fValue[0]){
                    for(int m=0;m<fValue[1];m++){
                        traceBack[i-m][j] = LEFT;
                        gapSize[i][j] = (int) fValue[1];
                    }
                }
            }
        }
        tracePath(vValue);
    }

    public void tracePath(double max)
    {
        print(s1.length(),s2.length());
        System.out.println("Score: "+max);
    }

    public double gapPenalty(int k){
        return (Math.log(k)-10);
    }

    public void initBaseCases(){
        intOutMatrix[0][0] = 0;
        //initialize first row and column
        j=0;
        for (i=1;i<s1.length()+1;i++){
            intOutMatrix[i][j] =  intOutMatrix[i-1][j]+scoreMatrix[letters.get(s1.charAt(i-1))][6];
            traceBack[i][j] = UP;
        }
        i=0;
        for (j=1;j<s2.length()+1;j++){
            intOutMatrix[i][j] =  intOutMatrix[i][j-1]+scoreMatrix[letters.get(s2.charAt(j-1))][6];
            traceBack[i][j] = LEFT;
        }
    }

    public void initBaseCasesWithGap(){
        doubleOutMatrix[0][0] = 0;
        //initialize first row and column
        j=0;
        for (i=1;i<s1.length()+1;i++){
            doubleOutMatrix[i][j] =  gapPenalty(i);
            traceBack[i][j] = UP;
            gapSize[i][j] = i;
        }
        i=0;
        for (j=1;j<s2.length()+1;j++){
            doubleOutMatrix[i][j] =  gapPenalty(j);
            traceBack[i][j] = LEFT;
            gapSize[i][j] = j;
        }
    }

    public void initBaseCasesWithAffineGap(){
        intOutMatrix[0][0] = 0;
        //initialize first row and column
        j=0;
        for (i=1;i<s1.length()+1;i++){
            E[i][j] = intOutMatrix[i][j] =  (int)E(i,0)[0];
            gapSize[i][j] = i;
            traceBack[i][j] = UP;
        }
        i=0;
        for (j=1;j<s2.length()+1;j++){
            F[i][j] = intOutMatrix[i][j] =  (int)F(0,j)[0];
            gapSize[i][j] = j;
            traceBack[i][j] = LEFT;
        }
    }




}
