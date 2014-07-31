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


@Path("/getAllReports")
public class GetAllReports {

	StorageLayer DB = StorageSingleton.sl;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Report getAll() {
		
		Report response = new Report();
	
		
	
			List<Report> resultsList = DB.getAllReports(true);
			response.reports = resultsList;
		
	
		return response;
		
	}
	
	

}
