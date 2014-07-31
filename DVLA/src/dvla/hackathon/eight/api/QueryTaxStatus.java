package dvla.hackathon.eight.api;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.FormParam;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

import dvla.hackathon.eight.api.JSON.TaxStatus;

@Path("/QueryTaxStatus")
public class QueryTaxStatus {
	DBStub DB = new DBStub();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public TaxStatus querySingle(final TaxStatus query) {

		TaxStatus response = new TaxStatus();

		if (query.VRM != null && query.VRMs == null) {
			response.VRM = query.VRM;
			response.isTaxCurrent = DB.query(query.VRM);
		} else if (query.VRM == null
				&& query.VRMs != null) {

			List<TaxStatus> resultsList = new LinkedList<TaxStatus>();

			for (String s : query.VRMs) {
				TaxStatus nestedResponse = new TaxStatus();
				nestedResponse.VRM = s;
				nestedResponse.isTaxCurrent = DB.query(s);
				resultsList.add(nestedResponse);
			}
			response.results = resultsList;
		}

		return response;
	}

}
