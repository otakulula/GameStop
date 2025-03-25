/**
 * Customer.java
 * @author Stefano Pinna Segovia
 * CIS 22C, Final Project
 */

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import DataStructures.LinkedList;
import DataStructures.Heap;

public class Customer extends User {
    private LinkedList<Order> shippedOrders;
    private LinkedList<Order> unshippedOrders;
    private Heap<Order> pendingOrders;
    private int nextOrderID; // also not needed
    private double cashBalance;
    private boolean isGuest;

    // /**
    //  * Constructor for Customer.
    //  * 
    //  * @param firstName The first name of the customer.
    //  * @param lastName  The last name of the customer.
    //  * @param email     The email address of the customer.
    //  * @param password  The password for the customer.
    //  */
    // public Customer(String firstName, String lastName, String email, String password) {
    //     super(firstName, lastName, email, password);
    //     this.shippedOrders = new LinkedList<>();
    //     this.unshippedOrders = new LinkedList<>();
    //     this.pendingOrders = new Heap<>(new ArrayList<Order>(), orderPriorityComparator);
    //     this.nextOrderID = 1;
    //     this.ownedGames = new ArrayList<>();
    //     this.cashBalance = 0.0;
    //     this.isGuest = false;
    // }

    /**
     * Constructor with comprehensive customer details including order information.
     * 
     * @param firstName           The first name of the customer.
     * @param lastName            The last name of the customer.
     * @param email               The email address of the customer.
     * @param password            The password for the customer.
     * @param cashBalance         The initial cash balance of the customer.
     * @param numOfShipped        The number of shipped orders.
     * @param shippedVideoGames   The list of shipped video game orders.
     * @param numOfUnshipped      The number of unshipped orders.
     * @param unshippedVideoGames The list of unshipped video game orders.
     */
    public Customer(String firstName, String lastName, String email, String password,
            double cashBalance, int numOfShipped, ArrayList<Order> shippedVideoGames,
            int numOfUnshipped, ArrayList<Order> unshippedVideoGames) {
        super(firstName, lastName, email, password);

        this.shippedOrders = new LinkedList<>();
        this.unshippedOrders = new LinkedList<>();
        this.pendingOrders = new Heap<>(unshippedVideoGames, orderPriorityComparator);
        this.nextOrderID = 1;

        this.cashBalance = cashBalance;
        this.isGuest = false;
       
     
        // ALSO PUT IN unshippedVideogames into  unshippedOrders LINKEDLIST!!!!!!
        if (shippedVideoGames != null) {
            for (Order order : shippedVideoGames) {
                this.shippedOrders.addLast(order);
                order.ordercontent().positionIterator();
               
            }
        }
    }

    /**
     * Constructor for Customer with only email and password.
     * 
     * @param email    The email address of the customer.
     * @param password The password for the customer.
     */
    public Customer(String email, String password) {
        super("", "", email, password);
        this.shippedOrders = new LinkedList<>();
        this.unshippedOrders = new LinkedList<>();
        this.pendingOrders = new Heap<>(new ArrayList<Order>(), orderPriorityComparator);
        this.nextOrderID = 1;
        this.cashBalance = 0.0;
        this.isGuest = false;
    }

    /**
     * Constructor for Customer with isGuest parameter.
     * 
     * @param email    The email address of the customer.
     * @param password The password for the customer.
     * @param isGuest  Whether the customer is a guest.
     */
    public Customer(String email, String password, boolean isGuest) {
        super("", "", email, password);
        this.shippedOrders = new LinkedList<>();
        this.unshippedOrders = new LinkedList<>();
        this.pendingOrders = new Heap<>(new ArrayList<Order>(), orderPriorityComparator);
        this.nextOrderID = 1;
        this.cashBalance = 0.0;
        this.isGuest = isGuest;
    }

    /**
     * Constructor for Customer with isGuest parameter and names.
     * 
     * @param firstName The first name of the customer.
     * @param lastName  The last name of the customer.
     * @param email     The email address of the customer.
     * @param password  The password for the customer.
     * @param isGuest   Whether the customer is a guest.
     */
    public Customer(String firstName, String lastName, String email, String password, boolean isGuest) {
        super(firstName, lastName, email, password);
        this.shippedOrders = new LinkedList<>();
        this.unshippedOrders = new LinkedList<>();
        this.pendingOrders = new Heap<>(new ArrayList<Order>(), orderPriorityComparator);
        this.nextOrderID = 1;
        this.cashBalance = 0.0;
        this.isGuest = isGuest;
    }

    /**
     * Retrieves the list of shipped orders.
     * 
     * @return A LinkedList of shipped orders.
     */
    public LinkedList<Order> getShippedList() {
        return shippedOrders;
    }

    /**
     * Retrieves the list of unshipped orders.
     * 
     * @return A LinkedList of unshipped orders.
     */
    public LinkedList<Order> getUnshippedList() {
        return unshippedOrders;
    }

    /**
     * Gets the cash balance.
     * 
     * @return The customer's cash balance.
     */
    public double getCashBalance() {
        return cashBalance;
    }

    /**
     * Sets the cash balance.
     * 
     * @param balance The balance to set.
     */
    public void setCashBalance(double balance) {
        this.cashBalance = balance;
    }

    /**
     * Gets whether the customer is a guest.
     * 
     * @return True if the customer is a guest, false otherwise.
     */
    public boolean isGuest() {
        return isGuest;
    }

    /**
     * Sets whether the customer is a guest.
     * 
     * @param isGuest True if the customer is a guest, false otherwise.
     */
    public void setGuest(boolean isGuest) {
        this.isGuest = isGuest;
    }

    /**
     * Comparator for ordering Orders by priority.
     */
    private Comparator<Order> orderPriorityComparator = new Comparator<Order>() {
        @Override
        public int compare(Order order1, Order order2) {
            return Integer.compare(order2.getPriority(), order1.getPriority());
        }
    };

    /**
     * Places a new order with standard shipping.
     * 
     * @param games The list of video games to order.
     * @param date  The date of the order.
     * @return The order ID.
     */
    public int placeStandardOrder(LinkedList<VideoGame> games, String date) {
        return placeOrder(games, date, 1, 1);
    }

    /**
     * Places a new order with rush shipping.
     * 
     * @param games The list of video games to order.
     * @param date  The date of the order.
     * @return The order ID.
     */
    public int placeRushOrder(LinkedList<VideoGame> games, String date) {
        return placeOrder(games, date, 2, 2);
    }

    /**
     * Places a new order with overnight shipping.
     * 
     * @param games The list of video games to order.
     * @param date  The date of the order.
     * @return The order ID.
     */
    public int placeOvernightOrder(LinkedList<VideoGame> games, String date) {
        return placeOrder(games, date, 3, 3);
    }

    /**
     * Places a new order.
     * 
     * @param games         The list of video games to order.
     * @param date          The date of the order.
     * @param shippingSpeed The shipping speed (1=standard, 2=rush, 3=overnight).
     * @param priority      The priority of the order (higher numbers are processed
     *                      first).
     * @return The order ID.
     */
    private int placeOrder(LinkedList<VideoGame> games, String date, int shippingSpeed, int priority) {
        int orderId = nextOrderID++;
        Order newOrder = new Order(orderId, this, date, games, shippingSpeed);
        pendingOrders.insert(newOrder);
        unshippedOrders.addLast(newOrder);

        if (!games.isEmpty()) {
            games.positionIterator();
            while (!games.offEnd()) {
                VideoGame game = games.getIterator();
                game.decreaseStock(1);
                games.advanceIterator();
            }
        }

        return orderId;
    }

    /**
     * Processes the highest priority order, marking it as shipped.
     * 
     * @return The order that was processed, or null if no orders to process.
     */
    public Order processNextOrder() {
        if (pendingOrders.getHeapSize() == 0) {
            return null;
        }

        Order nextOrder = pendingOrders.getMax();
        pendingOrders.remove(1);

        unshippedOrders.positionIterator();
        while (!unshippedOrders.offEnd()) {
            if (unshippedOrders.getIterator().getOrderID() == nextOrder.getOrderID()) {
                unshippedOrders.removeIterator();
                break;
            }
            unshippedOrders.advanceIterator();
        }

        shippedOrders.addLast(nextOrder);
        return nextOrder;
    }

    /**
     * Gets a list of all shipped orders.
     * 
     * @return A string representation of all shipped orders.
     */
    public String viewShippedOrders() {
        return shippedOrders.toString();
    }

    /**
     * Gets a list of all unshipped orders.
     * 
     * @return A string representation of all unshipped orders.
     */
    public String viewUnshippedOrders() {
        return unshippedOrders.toString();
    }

    // NO NEED OF THIS: becuase of there is already :  viewShippedOrders and viewShippedOrders methods!!!!!
    //  * Gets a list of all orders (both shipped and unshipped).
    //  * 
    //  * @return A string representation of all orders.
    //  */
    // public String viewAllOrders() {
    //     StringBuilder sb = new StringBuilder();
    //     sb.append("Shipped Orders:\n");
    //     sb.append(viewShippedOrders());
    //     sb.append("\nUnshipped Orders:\n");
    //     sb.append(viewUnshippedOrders());
    //     return sb.toString();
    // }

    /**
     * Writes all customer data to a file.
     * 
     * @param filename The name of the file to write to.
     * @return true if successful, false otherwise.
     */
    public boolean writeToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            // Write customer full name (first + last)
            writer.println(getFirstName() + " " + getLastName());

            // Write email
            writer.println(getEmail());

            // Write password
            writer.println(getPassword());

            // Write cash balance
            writer.println(cashBalance);

            // Write number of owned games
         //   writer.println(ownedGames.size());

            // Write each owned game title on a new line
            // for (VideoGame game : ownedGames) {
            //     writer.println(game.getTitle());
            // }

            // Add an extra newline at the end for separation
            writer.println();

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Returns a string representation of the Customer object.
     * 
     * @return A string containing the customer's information, owned games, and
     *         orders.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Customer basic information
        sb.append("Customer: ").append(getFirstName()).append(" ").append(getLastName()).append("\n");
        sb.append("Email: ").append(getEmail()).append("\n");
        sb.append("Cash Balance: $").append(String.format("%.2f", cashBalance)).append("\n");
        sb.append("Guest: ").append(isGuest ? "Yes" : "No").append("\n");

        // Owned games
        // sb.append("\nOwned Games (").append(ownedGames.size()).append("):\n");
        // if (ownedGames.isEmpty()) {
        //     sb.append("  No games owned\n");
        // } else {
        //     for (VideoGame game : ownedGames) {
        //         sb.append("  ").append(game.getTitle()).append("\n");
        //     }
        // }

        // Order information
        sb.append("\nShipped Orders (").append(shippedOrders.getLength()).append("):\n");
        if (shippedOrders.isEmpty()) {
            sb.append("  No shipped orders\n");
        } else {
            sb.append(viewShippedOrders()).append("\n");
        }

        sb.append("\nUnshipped Orders (").append(unshippedOrders.getLength()).append("):\n");
        if (unshippedOrders.isEmpty()) {
            sb.append("  No pending orders\n");
        } else {
            sb.append(viewUnshippedOrders());
        }

        return sb.toString();
    }

    /**
     * Returns a consistent hash code for each Customer email + password
     * by summing the Unicode values of each character in the key
     * 
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
     * Overrides the equals method to compare Customers based on email and password.
     * 
     * @param obj The object to compare with this Customer.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Customer otherCustomer = (Customer) obj;

        return (this.getEmail().equals(otherCustomer.getEmail()) &&
                this.getPassword().equals(otherCustomer.getPassword()));
    }

    /**
     * Adds an order to the shipped orders list.
     * 
     * @param order The order to add to shipped orders.
     */
    public void addShippedOrder(Order order) {
        this.shippedOrders.addLast(order);
    }

    /**
     * Removes a specific order from the shipped orders list.
     * 
     * @param order The order to remove from shipped orders.
     * @return true if the order was successfully removed, false otherwise.
     */
    public boolean removeShippedOrder(Order order) {
        this.shippedOrders.positionIterator();
        while (!this.shippedOrders.offEnd()) {
            if (this.shippedOrders.getIterator().equals(order)) {
                this.shippedOrders.removeIterator();
                return true;
            }
            this.shippedOrders.advanceIterator();
        }
        return false;
    }

    /**
     * Adds an order to the unshipped orders list.
     * 
     * @param order The order to add to unshipped orders.
     */
    public void addUnshippedOrder(Order order) {
        this.unshippedOrders.addLast(order);
    }

    /**
     * Removes a specific order from the unshipped orders list.
     * 
     * @param order The order to remove from unshipped orders.
     * @return true if the order was successfully removed, false otherwise.
     */
    public boolean removeUnshippedOrder(Order order) {
        this.unshippedOrders.positionIterator();
        while (!this.unshippedOrders.offEnd()) {
            if (this.unshippedOrders.getIterator().equals(order)) {
                this.unshippedOrders.removeIterator();
                return true;
            }
            this.unshippedOrders.advanceIterator();
        }
        return false;
    }
}