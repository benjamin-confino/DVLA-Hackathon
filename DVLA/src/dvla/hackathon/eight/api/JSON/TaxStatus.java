package dvla.hackathon.eight.api.JSON;

import java.util.List;

public class TaxStatus {

	public Boolean isTaxCurrent;
    public String VRM;
	public List<String> VRMs;
	public List<TaxStatus> results;
}
