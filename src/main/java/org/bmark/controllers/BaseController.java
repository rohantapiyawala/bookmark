package org.bmark.controllers;

import java.util.AbstractMap.SimpleEntry;
import javax.ws.rs.core.Response;
import org.bmark.support.ErrorResponse;
import org.bmark.support.InfoResponse;

public class BaseController {

    protected int statusCode = 200;

    public int getStatusCode()
    {
        return this.statusCode;
    }

    public BaseController setStatusCode(int statusCode)
    {
        this.statusCode = statusCode;
        return this;
    }

    public Response respond(Object data, SimpleEntry<String, String> headers)
    {
        return Response.status(this.getStatusCode()).entity(data).build();
    }
    
    public Response respond(Object data)
    {
        return this.respond(data, null);
    }

    public Response respondWithError(String message)
    {
        return this.respond(new ErrorResponse(message, this.getStatusCode()));
    }

    public Response respondNotFound(String message)
    {
        return this.setStatusCode(404).respondWithError(message);
    }

    public Response respondInternalError(String message)
    {
        return this.setStatusCode(500).respondWithError(message);
    }

    public Response respondAlreadyExists(String message)
    {
        return this.setStatusCode(409).respondWithError(message);
    }

    public Response respondCreated(String message)
    {
        return this.setStatusCode(201).respond(new InfoResponse(message));
    }
    
    public Response respondUpdated(String message)
    {
        return this.setStatusCode(204).respond(new InfoResponse(message));
    }
    
    public Response respondDeleted(String message)
    {
        return this.setStatusCode(204).respond(new InfoResponse(message));
    }

    public Response respondValidationFailed(String message)
    {
        return this.setStatusCode(422).respondWithError(message);
    }
	
}
