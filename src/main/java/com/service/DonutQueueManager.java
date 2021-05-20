package com.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;

import com.entity.Donut;
import com.entity.DonutQ;
import com.model.QueryDonutDB;

public class DonutQueueManager {
	
	
	PriorityQueue<DonutQ> orderQueue = new PriorityQueue<DonutQ>();
	private QueryDonutDB queryDonuts = new QueryDonutDB();
	private DonutController donutController = new DonutController(); 
	private DonutQ donutQ;
	private Donut donut = new Donut();
	Timer timer;
	Timestamp pollTimeStamp;
	List<DonutQ> shoppingCart = new ArrayList<DonutQ>();
	
	public DonutQueueManager() {
		
		Timestamp pollTimeStamp = Timestamp.valueOf(LocalDateTime.now().plusMinutes(5));		
		
	}	
 
	public List<Donut> getActiveCustomerList() {

		@SuppressWarnings("unchecked")
		List<Donut> activeCust = (List<Donut>) queryDonuts.getAllActiveCustomers();
		return activeCust;
	}
	
		
	//schedule task to fetch from DB every 2 mins 
	//schedule task to complete the shopping cart every 5 mins 
	//function to create the shopping cart and the queue
	
	public void generateQ(){
		Integer waitingTime;
		
		// fetch all the active customers
		
			List<Donut> activeCustList =  getActiveCustomerList();
			List<DonutQ> generateDonutQ = new ArrayList<DonutQ>();
			
					
			//get the current time 
			LocalTime time = LocalTime.now();
			int orderSize = 0;
			
			for(int i=0; i<activeCustList.size(); i++) {
			
				Donut inputDonut = activeCustList.get(i);
				
			//Calculate the appr wait time - formula used: (current time - start time)  + (polltime - current time ) 
				Timestamp currentTimeStamp = Timestamp.valueOf(LocalDateTime.now());
				waitingTime = (int)(long)(((currentTimeStamp.getTime() - inputDonut.getStartTime().getTime()) +  (pollTimeStamp.getTime() - currentTimeStamp.getTime()))/1000);
				
				donutQ = new DonutQ();
				
				donutQ.setCustomerId(inputDonut.getCustomerId());
				donutQ.setQuantity(inputDonut.getQuantity());
				
				if(orderSize <= 50) {
					donutQ.setWaitingTime(waitingTime);					
					
					shoppingCart.set(i, donutQ);
				}				else {
					donutQ.setWaitingTime(waitingTime+300); //add 5 more mins to waiting time					
				}				
				generateDonutQ.set(i, donutQ);
			}
			
			//TODO: update the Q table with the waiting time

}
	//TODO: after 5 mins - remove the shopping cart items from queue; update the order table with status as done 
	class RunTask extends TimerTask {
        public void run() {
            
        }
    }
	
	
	public void scheduleQGeneration() {
		
		timer = new Timer();
		timer.schedule(new RunTask(), pollTimeStamp);
	}
	
	
}
	