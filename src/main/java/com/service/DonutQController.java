package com.service;

import java.util.List;

import com.entity.Donut;
import com.model.QueryDonutDB;

//THis class contains the Q Logic and also calls to interact the apis with the DB 

public class DonutQController {

	private QueryDonutDB queryDonuts = new QueryDonutDB();

	public List<Donut> getActiveCustomerList() {

		@SuppressWarnings("unchecked")
		List<Donut> activeCust = (List<Donut>) queryDonuts.getAllActiveCustomers();
		return activeCust;
	}
	
	
		public List<Donut> getCustomerOrders(Integer customerId){
			
			List<Donut> customerOrder = (List<Donut>) queryDonuts.getActiveOrdersForCust(customerId);
			return customerOrder;
			
		}
		
		
		public Boolean createCustOrder(Integer custId,  Integer qty) {
			return queryDonuts.insertOrder(custId, qty);
		}
		
		
		
		public boolean customerOrderExists(Integer customerId) {
			queryDonuts.getActiveOrdersForCust(customerId);
			return false;
		}
	
}
