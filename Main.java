import java.io.*;
import java.sql.SQLException;
import java.util.Properties;


public class Main {

    public static void main(String[] args) throws FileNotFoundException{
    	
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
				
			}
		}
		else{
			System.out.println("Usage: java Alignments.jar <option1> <option2> Score.matrix string1 string2");
		}
        
    }
}

