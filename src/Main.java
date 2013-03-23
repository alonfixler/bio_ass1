
public class Main {

    public static void main(String[] args){
    	
    	String option1 = args[0];
        if(option1.equals("-g")){
			if(args[1].equals("-p"))
			{
				globalAlignment align = new globalAlignment(args[2],args[3],args[4],option1,"-p");
				align.globalGap();
			}
			else if(args[1].equals("-a"))
			{
				globalAlignment align = new globalAlignment(args[2],args[3],args[4],option1,"-a");
				align.globalAffine();
			}
			else
			{
				globalAlignment align = new globalAlignment(args[1],args[2],args[3],option1,"");
				align.global();
			}
		}
		else if(option1.equals("-l")){
			if(args[1].equals("-p"))
			{
				LocalAlignment align = new LocalAlignment(args[2],args[3],args[4],option1,"-p");
				align.localGap();
			}
			else if(args[1].equals("-a"))
			{
				LocalAlignment align = new LocalAlignment(args[2],args[3],args[4],option1,"-a");
				align.localAffine();
			}
			else
			{
				LocalAlignment align = new LocalAlignment(args[1],args[2],args[3],option1,"");
				align.local();
			}
		}
		else{
			System.out.println("Usage: java Alignments.jar <option1> <option2> Score.matrix string1 string2");
		} 
    }
}

