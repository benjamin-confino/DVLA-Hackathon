package dvla.hackathon.eight.api;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mongodb.DB;

import dvla.hackathon.eight.api.JSON.ColourByReg;
import dvla.hackathon.eight.api.JSON.Report;
import dvla.hackathon.eight.api.JSON.TaxStatus;
import dvla.hackathon.eight.datastorage.StorageLayer;
import dvla.hackathon.eight.datastorage.StorageSingleton;


@Path("/getReport")
public class GetReports {

	StorageLayer DB = StorageSingleton.sl;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Report query(final Report query) {
		
		Report response = new Report();

		if (query.VRM != null && query.VRMs == null) {
			response.VRM = query.VRM;
			response = DB.getReport(query.VRM, response);
		} else if (query.VRM == null
				&& query.VRMs != null) {

			List<Report> resultsList = new LinkedList<Report>();

			for (String s : query.VRMs) {
				Report nestedResponse = new Report();
				nestedResponse.VRM = s;
				nestedResponse = DB.getReport(s, nestedResponse);
				resultsList.add(nestedResponse);
			}
			response.reports = resultsList;
		}

		return response;
		
	}

}
