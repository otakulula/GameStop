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
	private int shippingSpeed;
	private int priority;
	
	/**CONSTRUCTORS */
	/**
	 * Three-argument construcotr, when only the order id, order contents, 
	 * and order priority are known
	 * @param orderID the id of the order
	 * @param game the game being ordered
	 * @priority the priority of the order
	 * */
	public Order(int orderID, VideoGame game, int priority) {
		this.orderID = orderID;
		orderContents = new LinkedList<VideoGame>();
		this.orderContents.addLast(game);
		this.priority = priority;
		
		//reverse calculate date and shippingspeed
		int shippingSpeed = calculateShippingSpeed(priority);
		this.shippingSpeed = shippingSpeed;
		
		String date = calculateDate(priority);
		this.date = date;
	}
	
    /**
     * Full-argument constructor for Order
     * @param orderID the id of the order
     * @param customer the customer which made the order
     * @param date the date the order was made
     * @param orderContents a linked list containing the video games in the order
     * @param shippingSpeed the speed of shipping for the order
     * @param priority the priority of the order
     */
	public Order (int orderID, Customer customer, String date, LinkedList<VideoGame> orderContents, int shippingSpeed){
		this.orderID = orderID;
		this.customer = customer;
		this.date = date;
		this.orderContents = orderContents;
		this.shippingSpeed = shippingSpeed;
		
		this.priority = calculatePriority();
	}
	
    /**ACCESSORS */

    /**
     * Accesses the order ID
     * @return the order ID
     */
	public int getOrderID() {
		return orderID;
	}
	
	/**
     * Accesses the customer who made the order
     * @return the customer who made the order
     */
	public Customer getCustomer() {
		return customer;
	}
	
	/**
     * Accesses the date the order was made
     * @return the customer who made the order
     */
	public String getDate() {
		return date;
	}
	
	/**
     * Accesses the content of the order
     * @return the content of the order
     */
	public LinkedList<VideoGame> ordercontent() {
		return orderContents;
	}
	
	/**
     * Accesses the shipping speed of the order
     * @return the shipping speed of the order
     */
	public int getShippingSpeed() {
		return shippingSpeed;
	}
	
	/**
     * Accesses the priority of the order
     * @return the priority of the order
     */
	public int getPriority() {
		return priority;
	}

    /**MUTATORS */

    /**
     * Sets the id of the order
     * @param orderID the id of the order
     */
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	
	/**
     * Sets the customer of the order
     * @param customer the customer of the order
     */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	/**
     * Sets the date of the order
     * @param date the date of the order
     */
	public void setDate(String date) {
		this.date = date;
	}
	
	/**
     * Sets the content of the order
     * @param orderContents the content of the order
     */
	public void setOrderContent(LinkedList<VideoGame> orderContents) {
		this.orderContents = orderContents;
	}
	
	/**
     * Sets shipping speed of the order
     * @param shippingSpeed the speed of shipping for the order
     */
	public void setShippingSpeed(int shippingSpeed) {
		this.shippingSpeed = shippingSpeed;
	}
	
	/**
     * Sets the priority of the order
     * @param priority the priority of the order
     */
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	/**ADDITIONAL METHODS */
	
	/**
	 * Returns the title of the first game in the order
	 * */
	public String getGameTitle() {
		return orderContents.getFirst().getTitle();
	}
	
	public String calculateDate(int priority) {
		int date = priority % 10000;
		int day = date % 100; // --> 12 or 06
		int month = date / 100; //--> 12 or 1
		
		String dayStr = "" + day;
		String monthStr = "";
		if(month == 1) {
			monthStr += "January";
		} else if(month == 2) {
			monthStr += "February";
		} else if(month == 3) {
			monthStr += "March";
		} else if(month == 4) {
			monthStr += "April";
		} else if(month == 5) {
			monthStr += "May";
		} else if(month == 6) {
			monthStr += "June";
		} else if(month == 7) {
			monthStr += "July";
		} else if(month == 8) {
			monthStr += "August";
		} else if(month == 9) {
			monthStr += "September";
		} else if(month == 10) {
			monthStr += "October";
		} else if(month == 11) {
			monthStr += "November";
		} else if(month == 12) {
			monthStr += "December";
		}
		
		String dateStr = monthStr + ", " + dayStr;
		return dateStr;
	}
	
	public int calculateShippingSpeed(int priority) {
		int shippingSpeed = priority/10000;
		return shippingSpeed;
	}
	
	/**
	 * Adds a game to the order
	 * @param game The game to be added to the order
	 * */
	public void addGame(VideoGame game) {
		this.orderContents.addLast(game);
	}
	
	/**
     * Turns the order date into an integer
     * @returns the order date as an integer
     */
	public int calculateDate() {
		//Date Format: "Month, day" Ex. March, 6 ; Ex. August, 21
		int date = 0;
		int monthEndIndex = -1;
		int dayStartIndex = -1;
		for(int i = 0; i < this.date.length(); i++) {
			if(Character.isLetter(this.date.charAt(i))) {
				monthEndIndex = i;
			}
			if(Character.isDigit(this.date.charAt(i))) {
				dayStartIndex = i;
				i = this.date.length() + 1;
			}
		}
		String month = this.date.substring(0, monthEndIndex + 1).toLowerCase();
		String day = this.date.substring(dayStartIndex);
		
		if(month.equals("january")) {
			date += 100;
		} else if(month.equals("february")) {
			date += 200;
		} else if(month.equals("march")) {
			date += 300;
		} else if(month.equals("april")) {
			date += 400;
		} else if(month.equals("may")) {
			date += 500;
		} else if(month.equals("june")) {
			date += 600;
		} else if(month.equals("july")) {
			date += 700;
		} else if(month.equals("august")) {
			date += 800;
		} else if(month.equals("september")) {
			date += 900;
		} else if(month.equals("october")) {
			date += 1000;
		} else if(month.equals("november")) {
			date += 1100;
		} else if(month.equals("december")) {
			date += 1200;
		}
		
		int dayNum = Integer.parseInt(day);
		day += dayNum;
		
		return dayNum;
	}
	
	/**
	 * Calculates the priority of the order based off of shipping speed and the date the
	 * order was placed
	 * @returns an integer representing the priority of the order
	 * */
	public int calculatePriority() {
		//Shipping overnight order : 3 == 30000
		//Shipping rush order: 2 = 20000
		//Shipping reg : 1 = 10000 
		
		int priority = 0;
		int date = calculateDate();
		if(shippingSpeed == 3) {
			priority += 30000;
		} else if(shippingSpeed == 2) {
			priority += 20000;
		} else if(shippingSpeed == 1) {
			priority += 10000;
		}
		
		priority += date;
		return priority;
	}
	
	/**
     * Returns a string representation of the Order object
     * @return A string containing the order's information
     */
	@Override
	public String toString(){
		StringBuilder str = new StringBuilder();
		//no customer// date//game//prioritynum
		//str.append("Order ID: ").append(orderID).append("\nCustomer: ");
		//str.append(customer.getFirstName()).append(" ").append(customer.getLastName());
		
		str.append(orderContents.getFirst().getTitle());
		str.append("\nDate: ").append(date);
		str.append("\nPriority: ").append(priority).append("\n");
		
		//orderContents.positionIterator();
		
		/*for(int i = 0; i < orderContents.getLength(); i++) {
			str.append(i).append(". ").append(orderContents.getIterator().getTitle()).append("\n");
			orderContents.advanceIterator();
		}*/
		
		
		return str.toString();
    }
}
