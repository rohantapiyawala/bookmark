package org.bmark.controllers;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/organize")
@Produces(MediaType.APPLICATION_JSON)
public class ExportImportController extends BaseController {

	@POST
	@Path("/export")
	public Response exportBookmarks() {
		return this.respond("Export");
	}
	
	@POST
	@Path("/import")
	public Response importBookmarks() {
		return this.respond("Import");
	}
	
}