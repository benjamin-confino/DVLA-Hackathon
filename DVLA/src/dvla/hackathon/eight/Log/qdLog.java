package dvla.hackathon.eight.Log;

import java.io.File;
import java.io.FileWriter;

public class qdLog {
	
	public static void writeLog(String log){
		
		try{
		File f = new File("/tmp/log");
		FileWriter fw = new FileWriter(f);
		
		fw.append("-----------------------------------------");
		fw.append(log);
		}
		catch (Exception e){
			
		}
	}

}
