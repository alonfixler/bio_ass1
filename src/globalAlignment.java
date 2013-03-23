
public class globalAlignment extends Alignment{

    int i,j,cur,up,diag,left;  //Variables for global alignment without gaps
    double[] eValue,fValue,gValue; //Variables for global alignment with gaps
    double vValue;  //Variables for global alignment with gaps

    public globalAlignment(String scoreMatrixFile,String s1,String s2, String option1,String option2)
    {
        super(scoreMatrixFile,s1,s2,option1,option2);
    }

    public void global()   //global alignment without gaps
    {
        initBaseCases();   //initialize the first row and column (base cases)
        for(i=1;i<s1.length()+1;i++){     //iterate over the table row after row starting from (1,1)
            for(j=1;j<s2.length()+1;j++){
                left = intOutMatrix[i][j-1]+scoreMatrix[letters.get(s2.charAt(j-1))][6];  //Insert Case
                diag = intOutMatrix[i-1][j-1]+scoreMatrix[letters.get(s1.charAt(i-1))][letters.get(s2.charAt(j-1))]; //Replace/Match case
                up = intOutMatrix[i-1][j]+scoreMatrix[6][letters.get(s1.charAt(i-1))];   //Delete Case
                cur = intOutMatrix[i][j] = Math.max(up, Math.max(diag, left));  //current cell get the max value

                if (cur==diag) 	traceBack[i][j] = DIAG;   //check which neighbour is the parent and mark for trace
                else if (cur==up) 	traceBack[i][j] = UP;
                else if (cur==left) 	traceBack[i][j] = LEFT;
            }
        }
        tracePath(cur);    //print the alignment and score
    }

    public void globalGap()
    {
        initBaseCasesWithGap(); //initialize the first row and column (base cases)
        for(i=1;i<s1.length()+1;i++){    //iterate over the table row after row starting from (1,1)
            for(j=1;j<s2.length()+1;j++){
                eValue = E(i,j);   //Insert Case - with Gap
                fValue = F(i,j);   //Delete Case - With Gap
                gValue = G(i,j);   //Replace/Match Case
                vValue = doubleOutMatrix[i][j] = Math.max(gValue[0],Math.max(eValue[0],fValue[0])); //current cell get the max value

                if (vValue==gValue[0]){         //check which neighbour is the parent and mark path for trace
                    traceBack[i][j] = DIAG;
                }
                else if (vValue==eValue[0]){
                    traceBack[i][j] = LEFT;
                    gapSize[i][j] = (int) eValue[1];    //check the gap size
                }
                else if (vValue==fValue[0]){
                    traceBack[i][j] = UP;
                    gapSize[i][j] = (int) fValue[1];    //check the gap size
                }
            }
        }
        tracePath(vValue);   //print the alignment and score
    }

    public void globalAffine()
    {
        initBaseCasesWithAffineGap();   //initialize the first row and column (base cases)
        for(i=1;i<s1.length()+1;i++){    //iterate over the table row after row starting from (1,1)
            for(j=1;j<s2.length()+1;j++){
                eValue = E(i,j);       //Insert Case - with Affine Gap
                fValue = F(i,j);       //Delete Case - With Affine Gap
                gValue = G(i,j);        //Replace/Match Case
                vValue = intOutMatrix[i][j] = (int) Math.max(gValue[0],Math.max(eValue[0],fValue[0]));

                if (vValue==gValue[0]){         //check which neighbour is the parent and mark path for trace
                    traceBack[i][j] = DIAG;
                }
                else if (vValue==eValue[0]){
                    for(int n=0;n<eValue[1];n++){
                        traceBack[i][j-n] = UP;
                        gapSize[i][j] = (int) eValue[1];   //check the gap size
                    }
                }
                else if (vValue==fValue[0]){
                    for(int m=0;m<fValue[1];m++){
                        traceBack[i-m][j] = LEFT;
                        gapSize[i][j] = (int) fValue[1];   //check the gap size
                    }
                }
            }
        }
        tracePath(vValue);  //print the alignment and score
    }

    public void tracePath(double max)  //trace the alignment path according to the traceBack & gapSizes matrices
    {                                  //and print it with the score
        print(s1.length(),s2.length());
        System.out.println("Score: "+max);
    }

    public double gapPenalty(int k){   //The gap penalty function
        return (Math.log(k)-10);
    }

    public void initBaseCases(){    //initialize the first row & column (base cases) - without gaps
        intOutMatrix[0][0] = 0;
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

    public void initBaseCasesWithGap(){   //initialize the first row & column (base cases) - with gaps
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

    public void initBaseCasesWithAffineGap(){   //initialize the first row & column (base cases) - with Affine gaps
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
