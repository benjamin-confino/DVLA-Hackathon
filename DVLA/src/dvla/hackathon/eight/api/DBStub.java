package dvla.hackathon.eight.api;

import java.util.HashMap;
import java.util.Map;

public class DBStub {
	
	private Map<String, Boolean> DB;
	
	
	public DBStub(){
		DB = new HashMap<String, Boolean>();
		
		DB.put("a", true);
		DB.put("b", true);
		DB.put("c", true);
		DB.put("x", false);
		DB.put("y", false);
		DB.put("z", false);
		
	}
	
	protected boolean query(String s){
		return DB.get(s);
	}

}
