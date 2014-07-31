package dvla.hackathon.eight.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/test")
public class SimpleTestPage {
	
	@GET
	public Response testPage(){
		
		return Response.ok("Hello World!").build();
	}

}
