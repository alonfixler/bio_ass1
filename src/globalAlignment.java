
public class globalAlignment extends Alignment{

    int i,j,curICell,upICell,diagICell,leftICell;
    double[] upDCell,diagDCell,leftDCell;
    double curDCell;
    public globalAlignment(String scoreMatrixFile,String s1,String s2, String option)
    {
        super(scoreMatrixFile,s1,s2,option);
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
        print(s1.length(),s2.length(),curICell);
    }

    public void globalGap()
    {
        initBaseCasesWithGap();
        for(i=1;i<s1.length()+1;i++){
            for(j=1;j<s2.length()+1;j++){
                leftDCell = F(i,j);
                upDCell = E(i,j);
                diagDCell = G(i,j);
                curDCell = doubleOutMatrix[i][j] = Math.max(diagDCell[0],Math.max(upDCell[0],leftDCell[0]));

                if (curDCell==diagDCell[0]){
                    traceBack[i][j] = DIAG;
                }
                else if (curDCell==upDCell[0]){
                    for(int n=0;n<upDCell[1];n++){
                        traceBack[i][j-n] = UP;
                    }
                }
                else if (curDCell==leftDCell[0]){
                    for(int m=0;m<leftDCell[1];m++){
                        traceBack[i-m][j] = LEFT;
                    }
                }
            }
        }
    }

    public void globalAffine()
    {

    }

    public void gapTracePath(double max)
    {

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

    public double[] G(int i, int j){
        return new double[]{doubleOutMatrix[i][j]+scoreMatrix[letters.get(s1.charAt(i-1))][letters.get(s2.charAt(j-1))],DIAG};
    }

    public double[] E(int i, int j){
        if(j==0){
            return new double[]{-gapPenalty(i),i};
        }
        else{
            double max=Double.MIN_VALUE,temp,kVal=0;
            for(int k=1;k<j;k++){
                temp = doubleOutMatrix[i][k]+gapPenalty(j-k);
                if(max < temp){
                    max = temp;
                    kVal = k;
                }
            }
            return new double[]{max,kVal};
        }
    }
    public double[] F(int i, int j){
        if(i==0){
            return new double[]{-gapPenalty(j),j};
        }
        else{
            double max=Double.MIN_VALUE,temp,kVal=0;
            for(int k=1;k<i;k++){
                temp = doubleOutMatrix[k][j]+gapPenalty(i-k);
                if(max < temp){
                    max = temp;
                    kVal = k;
                }
            }
            return new double[]{max,kVal};
        }
    }


}
