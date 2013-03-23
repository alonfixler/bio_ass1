
public class globalAlignment extends Alignment{

    int i,j,curICell,upICell,diagICell,leftICell;
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
                leftICell = intOutMatrix[i-1][j]+scoreMatrix[letters.get(s1.charAt(i-1))][6];
                diagICell = intOutMatrix[i-1][j-1]+scoreMatrix[letters.get(s1.charAt(i-1))][letters.get(s2.charAt(j-1))];
                upICell = intOutMatrix[i][j-1]+scoreMatrix[6][letters.get(s2.charAt(j-1))];
                curICell = intOutMatrix[i][j] = Math.max(upICell, Math.max(diagICell, leftICell));

                if (curICell==diagICell) 	traceBack[i][j] = DIAG;
                else if (curICell==upICell) 	traceBack[i][j] = UP;
                else if (curICell==leftICell) 	traceBack[i][j] = LEFT;
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

    public void globalAffine()
    {
        initBaseCasesWithAffineGap();
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
        print(i,j);
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
        }
        i=0;
        for (j=1;j<s2.length()+1;j++){
            intOutMatrix[i][j] =  intOutMatrix[i][j-1]+scoreMatrix[letters.get(s2.charAt(j-1))][6];
        }
    }

    public void initBaseCasesWithGap(){
        doubleOutMatrix[0][0] = 0;
        //initialize first row and column
        j=0;
        for (i=1;i<s1.length()+1;i++){
            doubleOutMatrix[i][j] =  gapPenalty(i);
        }
        i=0;
        for (j=1;j<s2.length()+1;j++){
            doubleOutMatrix[i][j] =  gapPenalty(j);
        }
    }

    public void initBaseCasesWithAffineGap(){
        intOutMatrix[0][0] = 0;
        //initialize first row and column
        j=0;
        for (i=1;i<s1.length()+1;i++){
            E[i][j] = intOutMatrix[i][j] =  (int)E(i,0)[0];

        }
        i=0;
        for (j=1;j<s2.length()+1;j++){
            F[i][j] = intOutMatrix[i][j] =  (int)F(0,j)[0];
        }
    }




}
