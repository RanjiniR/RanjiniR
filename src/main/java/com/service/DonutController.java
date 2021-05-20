package com.service;

import java.util.List;

import com.entity.Donut;
import com.entity.DonutQ;
import com.model.QueryDonutDB;

//This class contains the Q Logic and also calls to interact the apis with the DB 

public class DonutController {

	private QueryDonutDB queryDonuts = new QueryDonutDB();

	//Fetch all the waiting customers 
	public List<DonutQ> getCustomerWaitingList() {

		@SuppressWarnings("unchecked")
		List<DonutQ> activeCust = (List<DonutQ>) queryDonuts.getQueueStatus();
		return activeCust;
	}

	
	public List<Donut> getCustomerOrders(Integer customerId) {

		List<Donut> customerOrder = (List<Donut>) queryDonuts.getActiveOrdersForCust(customerId);
		return customerOrder;

	}

	public Boolean createCustOrder(Integer custId, Integer qty) {

		if (queryDonuts.insertOrder(custId, qty) && queryDonuts.insertOrderIntoQueue(custId, qty))
			return true;

		else
			return false;

	}

	public boolean customerOrderExists(Integer customerId) {
		queryDonuts.getActiveOrdersForCust(customerId);
		return false;
	}

	public boolean cancelCustOrder(Integer customerId) {
		if (queryDonuts.cancelOrderforCust(customerId) && queryDonuts.removeCustFromQueue(customerId))
			return true;

		else
			return false;
	}

}
