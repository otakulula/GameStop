/**
  * Order.java
  * @author Daniella Rand
  * CIS 22C, Final Project
  */

import java.util.Comparator;
import DataStructures.LinkedList;

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
	
   public Order (){
	   System.out.println("test");
   }

  
}
