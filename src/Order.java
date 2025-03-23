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
	public Order (int orderID, Customer customer, String date, LinkedList<VideoGame> orderContents, int shippingSpeed, int priority){
		this.orderID = orderID;
		this.customer = customer;
		this.date = date;
		this.orderContents = orderContents;
		this.shippingSpeed = shippingSpeed;
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
	
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	//setters
	
	public String toString(){
		String str = "";
		str += "Order ID: " + orderID + "\nCustomer: " + customer.getFirstName() + " " + customer.getLastName();
		str += "\nDate: " + date + "\nShipping Speed: " + shippingSpeed + "\nPriority: " + priority + "\nOrder Content:\n";
		
		orderContents.positionIterator();
		for(int i = 0; i < orderContents.getLength(); i++) {
			str += i + ". " + orderContents.getIterator() + "\n";
			orderContents.advanceIterator();
		}
		return str;
    }
}
