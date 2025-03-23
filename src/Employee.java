/**
  * Employee.java
  * @author Huey Nguyen
  * CIS 22C, Final Project
  */
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import DataStructures.BST;
import DataStructures.LinkedList;
import DataStructures.Heap;
import DataStructures.HashTable;
 
public class Employee extends User{

    private boolean isManager;
    private LinkedList<Order> unshippedOrders;
    private LinkedList<Order> shippedOrders;
    private BST<VideoGame> gameCatalog;
    private HashTable<Customer> customers;
    
 

    /**
     * Constructs an Employee object with the given details.
     * 
     * @param firstName The first name of the employee.
     * @param lastName The last name of the employee.
     * @param email The email address of the employee.
     * @param password The password for the employee.
     * @param isManager Indicates if the employee is a manager.
     */
    public Employee(String firstName, String lastName, String username, String password, boolean isManager, HashTable<Customer> customers) {
        super(firstName, lastName, username, password);
        this.isManager = isManager;
        this.unshippedOrders = new LinkedList<>();
        this.shippedOrders = new LinkedList<>();
        this.gameCatalog = new BST<>();
        this.customers = customers;
    }

    /**
     * Retrieves whether the employee is a manager.
     * 
     * @return true if the employee is a manager, false otherwise.
     */
    public boolean getManager() {
        return isManager;
    }
    
    /**
     * Sets whether the employee is a manager.
     * 
     * @param isManager true if the employee should be a manager, false otherwise.
     */
    public void setManager(boolean isManager) {
        this.isManager = isManager;
    }

    /**
     * Search for an order by ID.
     * @param orderId is the ID to be searched
     * @return order we are searching for
     */
    public Order searchOrderById(int orderID) {
        unshippedOrders.positionIterator();
        while(!unshippedOrders.offEnd()){
            Order order = unshippedOrders.getIterator();
            if(order.getOrderID() == orderID){
                return order;
            }
            else{
                unshippedOrders.advanceIterator();
            }
        }
        return null;

    }
    /**
     * Search for an order by customer's first and last name.
     * @param firstName is the first name of the customer
     * @param lastName is the last name of the custormer
     * @return order we are looking for
     */

    public Order searchOrderByCustomerName(String firstName, String lastName) {
        unshippedOrders.positionIterator();
        while (!unshippedOrders.offEnd()) {
            Order order = unshippedOrders.getIterator();
            if (this.getFirstName().equalsIgnoreCase(firstName) &&
                this.getLastName().equalsIgnoreCase(lastName)) {
                return order;
            } 
            else {
                unshippedOrders.advanceIterator();
            }
        }
        return null;
    }
    /**
     * View the next order in the priority 
     * @return the next order in the priority
     */

    public void viewHighestPriorityOrder() {
        Order highestPriorityOrder = null;
        unshippedOrders.positionIterator();
        while (!unshippedOrders.offEnd()) {
            Order currentOrder = unshippedOrders.getIterator();
            if (highestPriorityOrder == null || currentOrder.getPriority() > highestPriorityOrder.getPriority()) {
                highestPriorityOrder = currentOrder;
            }
            unshippedOrders.advanceIterator();
        }
        System.out.println(highestPriorityOrder);

    }

    /**
     * View all orders (heap sort?)
     */
    public void viewAllOrders() {
        
    }


 

}
