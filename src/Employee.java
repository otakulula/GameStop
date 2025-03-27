/**
 * Employee.java
 * @author Huey Nguyen
 * CIS 22C, Final Project
 */
import DataStructures.BST;
import DataStructures.Heap;
import DataStructures.HashTable;
import java.util.ArrayList;
import java.util.Comparator;
import DataStructures.LinkedList;

public class Employee extends User{

    private boolean isManager;
    private HashTable<Customer> customers;
    private Heap<Order> unshippedOrders;


    /**
     * Constructs an Employee object with the given details.
     *
     * @param firstName The first name of the employee.
     * @param lastName The last name of the employee.
     * @param email The email address of the employee.
     * @param password The password for the employee.
     * @param isManager Indicates if the employee is a manager.
     */
    public Employee(String firstName, String lastName, String email,
                    String password, boolean isManager, HashTable<Customer> customers,
                    BST<VideoGame> videoGameTitles, BST<VideoGame> videoGameGenres,
                    ArrayList<Order> allUnshippedGames) {
        super(firstName, lastName, email, password);
        this.isManager = isManager;
        this.customers = customers;
        this.unshippedOrders = new Heap<>(allUnshippedGames, new OrderPriorityComparator());
    }
    /**
     * Employee constructor only email and password
     * @param email employee email
     * @param password employee password
     */

    public Employee(String email, String password){
        super("", "", email, password);
        this.isManager = false;
        this.customers = new HashTable<>(10);
    }

    /**
     * comparator for order priority
     */
    private static class OrderPriorityComparator implements Comparator<Order> {
        @Override
        public int compare(Order order1, Order order2) {
            return Integer.compare(order2.getPriority(), order1.getPriority());
        }
    }

    /**
     * Retrieves whether the employee is a manager.
     *
     * @return true if the employee is a manager, false otherwise.
     */
    public boolean isManager() {
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
        ArrayList<Order> ordersList = unshippedOrders.sort();
        for (int i = 0; i < ordersList.size(); i++) {
            if (ordersList.get(i).getOrderID() == orderID) {
                return ordersList.get(i);
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
    public String searchOrderByCustomerName(String firstName, String lastName) {
        ArrayList<Order> ordersList = unshippedOrders.sort();
        LinkedList<Order> finish = new LinkedList<Order>();
        for (int i = 0; i < ordersList.size(); i++) {
            Order order = ordersList.get(i);

            if (order.getCustomer().getFirstName().equalsIgnoreCase(firstName) &&
                    order.getCustomer().getLastName().equalsIgnoreCase(lastName)) {
                finish.addLast(order);
            }
        }
        if(!finish.isEmpty()){
            return finish.toString();
        }
        return firstName + " " + lastName + " doesn't have any orders!\n\n";
    }
    /**
     * View the next order in the priority
     *
     */

    public void viewHighestPriorityOrder() {
        ArrayList<Order> sorted = unshippedOrders.sort();
        System.out.println(sorted.get(0));
    }

    /**
     * Prints all the orders
     */
    public void viewAllOrders() {
        ArrayList<Order> sorted = unshippedOrders.sort();
        for (int i = 0; i < sorted.size(); i++) {
            System.out.println(sorted.get(i));
        }
    }

    /**
     * ships an order out  (Need get shipped/unshipped list from customer class)
     * (Remove from Heap. Insert Order to shipped Linked List for the Customer + Remove from Unshipped List)
     * @param orderID the order to be shipped
     */
    public void shipOrder(int orderID) {
        Order orderToShip = searchOrderById(orderID);

        if (orderToShip != null) {

            int indexToRemove = -1;
            for (int i = 1; i <= unshippedOrders.getHeapSize(); i++) {
                if (unshippedOrders.getElement(i) == orderToShip) {
                    indexToRemove = i;
                    break;
                }
            }
            if (indexToRemove != -1) {
                unshippedOrders.remove(indexToRemove);

                LinkedList<Order> shipped = orderToShip.getCustomer().getShippedList();
                shipped.addLast(orderToShip);

                orderToShip.getCustomer().getUnshippedList().positionIterator();
                int orderIndex =  orderToShip.getCustomer().getUnshippedList().findIndex(orderToShip);
                orderToShip.getCustomer().getUnshippedList().advanceIteratorToIndex(orderIndex);

                orderToShip.getCustomer().getUnshippedList().removeIterator();


                System.out.println("Order " + orderID + " has been shipped.");
            }
            else {
                System.out.println("Order not found in unshipped orders.");
            }
        }
        else {
            System.out.println("Order not found.");
        }
    }

    /**
     * Returns a consistent hash code for each Employee email + password
     * by summing the Unicode values of each character in the key
     * Key = title
     * @return the hash code
     */
    @Override
    public int hashCode() {
        int sum = 0;
        String key = getEmail();
        key += getPassword();
        for (int i = 0; i < key.length(); i++) {
            sum += (int) key.charAt(i);
        }
        return sum;
    }

    /**
     * Compares two employee objects for equality.
     * They are equal if the title is the same.
     * @param o the other employee to compare to
     * @return true if the employees are equal, false otherwise
     */
    @Override
    public boolean equals(Object o){
        if(o == this){
            return true;
        } else if(!(o instanceof Employee)){
            return false;
        } else {
            Employee employee = (Employee)o;
            return this.getEmail().equals(employee.getEmail()) && this.getPassword().equals(employee.getPassword());
        }
    }
    /**
     * Override toString
     * @return a String of employee details
     */
    @Override
    public String toString() {
        String finish = "";
        finish += getFirstName() + " " + getLastName() + "\n";
        finish += getEmail() + "\n";
        finish += getPassword() + "\n";
        finish += isManager() + "\n";
        return finish;

    }

}