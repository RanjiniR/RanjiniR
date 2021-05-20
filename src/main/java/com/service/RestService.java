package com.service;

import java.util.List;

import com.entity.Donut;
import com.entity.DonutQ;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
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
	private List<DonutQ> donutQList = null;
	private DonutController donutController = new DonutController();

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

	@GET
	public Response getAllActiveCustomers() {
		donutQList = donutController.getCustomerWaitingList();
		return Response.ok(donutQList).build();
	}

	
	@GET
	@Path("/cust")
	public List<Donut> getCustOrder(@PathParam("id") Integer id) {
		donutList = donutController.getCustomerOrders(id);
		return donutList;
	}
	
	
	 @DELETE
	  @Path("{custId}")
	  public boolean deleteOrderById(@PathParam("custId") int custId) {
	      return donutController.cancelCustOrder(custId);
	  }
	
	

}
