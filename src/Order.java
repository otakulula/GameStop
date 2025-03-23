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
	
	public LinkedList<VideoGame> ordercontent() {
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
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public void setOrderContent(LinkedList<VideoGame> orderContents) {
		this.orderContents = orderContents;
	}
	
	public void setShippingSpeed(int shippingSpeed) {
		this.shippingSpeed = shippingSpeed;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	/**
     * Returns a string representation of the Order object
     * @return A string containing the order's information
     */
	@Override
	public String toString(){
		StringBuilder str = new StringBuilder();
		
		str.append("Order ID: ").append(orderID).append("\nCustomer: ");
		str.append(customer.getFirstName()).append(" ").append(customer.getLastName());
		str.append("\nDate: ").append(date).append("\nShipping Speed: ").append(shippingSpeed);
		str.append("\nPriority: ").append(priority).append("\nOrder Content:\n");
		
		orderContents.positionIterator();
		for(int i = 0; i < orderContents.getLength(); i++) {
			str.append(i).append(". ").append(orderContents.getIterator().getTitle()).append("\n");
			orderContents.advanceIterator();
		}
		return str.toString();
    }
}
