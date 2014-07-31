package dvla.hackathon.eight.api.JSON;

import java.util.Date;
import java.util.List;

public class Report {
	
	public Integer CaseNo;
	public String VRM; //req
	public Date DateTime;
	public String MACAddress;
	public String[] Photo;//req
	public Object GpsLocation;
	public String BrowserInfo;
	public List<Report> reports;
	public List<String> VRMs;
	public Boolean notFound;
	public ColourByReg car;

}
