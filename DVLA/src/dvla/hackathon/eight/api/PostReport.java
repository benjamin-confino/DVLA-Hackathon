package dvla.hackathon.eight.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dvla.hackathon.eight.api.JSON.Report;
import dvla.hackathon.eight.datastorage.StorageLayer;
import dvla.hackathon.eight.datastorage.StorageSingleton;

@Path("/postReport")
public class PostReport {

	StorageLayer DB = StorageSingleton.sl;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response query(final Report submit) {

		String finalMessage = "";

		if (submit.reports != null && submit.VRM != null){
			Response r = Response.serverError().entity("You tried to submit both a single VRM and multiple reports").build();

			return r;

		}
		String errorMessage = "";
		if (submit.reports == null && submit.VRM == null){
			errorMessage = "No VRM was submitted";
		}

		if (submit.reports == null && submit.Photo == null){
			errorMessage = "No photo was submitted";
		}
		
		if (submit.reports != null){
			for (Report r : submit.reports){
				if (submit.reports == null && submit.Photo == null){
					errorMessage = "No photo was submitted";
				}
			}
		}

		if (! errorMessage.isEmpty()){
			Response r = Response.serverError().entity(errorMessage).build();
			return r;
		}
		try{
			if (submit.reports != null){


				for (Report r : submit.reports){
					DB.createReport(r);
				}

			}
			else {
				DB.createReport(submit);
			}
		}catch (Exception e){
			Response r = Response.serverError().entity(e.getMessage()).build();
			return r;
		}


		return Response.ok().build();


	}	



}
