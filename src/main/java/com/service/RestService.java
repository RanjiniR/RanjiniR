package com.service;

import java.util.List;

import com.entity.Donut;

import jakarta.ws.rs.Consumes;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/donutq")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestService {

	private Donut donuts;
	private List<Donut> donutList = null;
	private DonutQController donutController = new DonutQController();

	@Context
	private UriInfo uriInfo;

	@PUT
	@Path("/insert")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createCustOrder(@PathParam("id") Integer id, @PathParam("quantity") Integer qty) {

		if (donutController.customerOrderExists(id)) {
			// status code 204
			return Response.noContent().build();
		} else {
			donutController.createCustOrder(id, qty);
			return Response.created(uriInfo.getAbsolutePath()).build();
		}

	}

	//change the method to include the q and waiting time
	@GET
	public Response getAllActiveCustomers() {
		donutList = donutController.getActiveCustomerList();
		return Response.ok(donutList).build();
	}

	
	//modify the controller to show the q and time remaining
	@GET
	@Path("/cust")
	public List<Donut> getCustOrders(@PathParam("id") Integer id) {
		donutList = donutController.getCustomerOrders(id);
		return donutList;
	}

}
