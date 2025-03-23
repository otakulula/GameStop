/**
  * Order.java
  * @author Daniella Rand
  * CIS 22C, Final Project
  */

import java.util.Comparator;
import DataStructures.LinkedList;
import DataStructures.Heap;

public class Order {
	private int orderID;
	private Customer customer;
	private String date;
	private LinkedList<VideoGame> orderContents;
	private int shippingSpeed; // or use enums
	private int priority;
	
	// put heap in customer class??
	private Heap heap;
	
	
	/*
	 * getters, setters, constructors go here
	 * 
	 * priority que needed
	 * orders stored in order of priority in a priority queue that is based on a heap data 
	 * structure
	 * inserts, sorts, and removes according to an elements priority
	 * 
	 * priority is to be assigned according to the date and time of the order and the
	 * shipping speed selected by the customer
	 * */
	
	//basic establishment for now, more details to come
	public Order (int ID, Customer cus, String date, LinkedList<VideoGame> games, int speed, int priority){
		orderID = ID;
		customer = cus;
		this.date = date;
		orderContents = games;
		shippingSpeed = speed;
		this.priority = priority;
		
	}
	
	public int getOrderID() {
		return orderID;
	}
	
	// is this necessary/wanted
	public Customer getCustomer() {
		return customer;
	}
	
	public String getDate() {
		return date;
	}
	
	public LinkedList ordercontent() {
		return orderContents;
	}
	
	public int getShippingSpeed() {
		return shippingSpeed;
	}
	
	public int getPriority() {
		return priority;
	}
	// do you want ability to inittially add in linked list to make up order?
//method to add videoGames to order ()
  
}
