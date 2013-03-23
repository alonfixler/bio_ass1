
public class Main {

    public static void main(String[] args){
    	
    	String option1 = args[0];
        if(option1.equals("-g")){    //Global Alignment is used
			if(args[1].equals("-p"))  //Use the regular Gap function
			{
				globalAlignment align = new globalAlignment(args[2],args[3],args[4],option1,"-p");
				align.globalGap();
			}
			else if(args[1].equals("-a"))  //Use the Affine Gap function
			{
				globalAlignment align = new globalAlignment(args[2],args[3],args[4],option1,"-a");
				align.globalAffine();
			}
			else    //Use no Gap function
			{
				globalAlignment align = new globalAlignment(args[1],args[2],args[3],option1,"");
				align.global();
			}
		}
		else if(option1.equals("-l")){   //Local Alignment is used
			if(args[1].equals("-p"))     //Use the regular Gap function
			{
				LocalAlignment align = new LocalAlignment(args[2],args[3],args[4],option1,"-p");
				align.localGap();
			}
			else if(args[1].equals("-a"))  //Use the Affine Gap function
			{
				LocalAlignment align = new LocalAlignment(args[2],args[3],args[4],option1,"-a");
				align.localAffine();
			}
			else   //Use no Gap function
			{
				LocalAlignment align = new LocalAlignment(args[1],args[2],args[3],option1,"");
				align.local();
			}
		}
		else{
			System.out.println("Usage: java -jar Alignments.jar <option1> <option2> Score.matrix sequence1 sequence2");
            System.out.println("Options:");
            System.out.println("option1: [-l = Local Alignment | -g = Global Alignment]");
            System.out.println("option2: [-p = Regular Gap Function | -a = Affine Gap Function | No Param = No Gap Function]");        }
    }
}

