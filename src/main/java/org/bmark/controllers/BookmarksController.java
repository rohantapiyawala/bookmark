package org.bmark.controllers;

import java.util.List;
import org.apache.commons.validator.UrlValidator;		
import org.bmark.repositories.BookmarkRepository;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import jersey.repackaged.com.google.common.collect.Lists;
import org.bmark.models.Bookmark;

@Path("/bookmarks")
@Produces(MediaType.APPLICATION_JSON)
public class BookmarksController extends BaseController {
	
	BookmarkRepository repository = new BookmarkRepository();
	
	@GET
	public Response index() {
		List<Bookmark> bookmarks = this.repository.all();

		GenericEntity<List<Bookmark>> entity = new GenericEntity<List<Bookmark>>(Lists.newArrayList(bookmarks)) {};
	    return this.respond(entity);
	}
	
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
	public Response store(MultivaluedMap<String, String> formParams) {
		String url = null, title = null, desc = null;
		boolean isValid = true;
		UrlValidator urlValidator = new UrlValidator();
		
		if(formParams.containsKey("url"))
		{
			url = formParams.getFirst("url");
		}
		if(formParams.containsKey("title"))
		{
			title = formParams.getFirst("title");
		}
		if(formParams.containsKey("desc"))
		{
			desc = formParams.getFirst("desc");
		}
		
		if(null == url || url.equals("") || !urlValidator.isValid(url))
		{
			isValid = false;
		}
		
		if (!isValid)
		{
			return this.respondValidationFailed("Validation failed");
		}
		
		Bookmark bookmark = new Bookmark(url, title, desc);
		this.repository.save(bookmark);
		
		return this.respondCreated("Bookmark added successfully");
	}
	
	@GET
	@Path("/{id}")
	public Response show(@PathParam("id") int id) {		
		Bookmark bookmark = this.repository.find(id);
		
		if (null == bookmark)
		{
			return this.respondNotFound("Bookmark not found");
		}
		
		return this.respond(bookmark);
	}
	
	@PUT
	@Path("/{id}")
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
	public Response update(@PathParam("id") int id, MultivaluedMap<String, String> formParams) {
		String url = null, title = null, desc = null;
		boolean isValid = true;
		UrlValidator urlValidator = new UrlValidator();
		
		if(formParams.containsKey("url"))
		{
			url = formParams.getFirst("url");
		}
		if(formParams.containsKey("title"))
		{
			title = formParams.getFirst("title");
		}
		if(formParams.containsKey("desc"))
		{
			desc = formParams.getFirst("desc");
		}
		
		if(null == url || url.equals("") || !urlValidator.isValid(url))
		{
			isValid = false;
		}
		
		if (!isValid)
		{
			return this.respondValidationFailed("Validation failed");
		}
		
		Bookmark bookmark = new Bookmark(id, url, title, desc);
		this.repository.update(bookmark);
		
		return this.respondUpdated("Bookmark updated successfully");
	}
	
	@DELETE
	@Path("/{id}")
	public Response destroy(@PathParam("id") int id) {
		this.repository.delete(id);
		
		return this.respondDeleted("Bookmark deleted successfully");
	}

}