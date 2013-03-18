
public class Main {

    public static void main(String[] args){
    	
    	String option1 = args[0];
        if(option1.equals("-g")){
			if(args[1].equals("-p"))
			{
				
			}
			else if(args[1].equals("-a"))
			{
				
			}
			else
			{
				
			}
		}
		else if(option1.equals("-l")){
			if(args[1].equals("-p"))
			{
				
			}
			else if(args[1].equals("-a"))
			{
				
			}
			else
			{
				LocalAlignment align = new LocalAlignment(args[1],args[2],args[3],"-a");
				align.local();
			}
		}
		else{
			System.out.println("Usage: java Alignments.jar <option1> <option2> Score.matrix string1 string2");
		} 
    }
}

