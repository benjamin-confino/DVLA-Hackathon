package dvla.hackathon.eight.api;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dvla.hackathon.eight.api.JSON.ColourByReg;
import dvla.hackathon.eight.api.JSON.TaxStatus;
import dvla.hackathon.eight.datastorage.StorageLayer;
import dvla.hackathon.eight.datastorage.StorageSingleton;

@Path("/colour")
public class QueryColourByReg {
	
	private final StorageLayer DB = StorageSingleton.sl;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ColourByReg querySingle(final TaxStatus query) {
		
		

		ColourByReg response = new ColourByReg();

		if (query.VRM != null && query.VRMs == null) {
			response.VRM = query.VRM;
			response = DB.getColour(query.VRM, response);
		} else if (query.VRM == null
				&& query.VRMs != null) {

			List<ColourByReg> resultsList = new LinkedList<ColourByReg>();

			for (String s : query.VRMs) {
				ColourByReg nestedResponse = new ColourByReg();
				nestedResponse.VRM = s;
				nestedResponse = DB.getColour(s, nestedResponse);
				resultsList.add(nestedResponse);
			}
			response.results = resultsList;
		}

		return response;
	}

}
