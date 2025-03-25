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
    private double cashBalance;
    private boolean isGuest;

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
        this.pendingOrders = new Heap<>(unshippedVideoGames != null ? unshippedVideoGames : new ArrayList<>(),
                orderPriorityComparator);

        this.cashBalance = cashBalance;
        this.isGuest = false;

        // Add shipped orders to shippedOrders LinkedList
        if (shippedVideoGames != null) {
            for (Order order : shippedVideoGames) {
                this.shippedOrders.addLast(order);
            }
        }

        // Add unshipped orders to unshippedOrders LinkedList
        if (unshippedVideoGames != null) {
            for (Order order : unshippedVideoGames) {
                this.unshippedOrders.addLast(order);
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
     * Retrieves the heap of pending orders.
     * 
     * @return A Heap of pending orders.
     */
    public Heap<Order> getPendingOrders() {
        return pendingOrders;
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
     * Combines unshipped orders into a single heap for easy access.
     * 
     * @return A Heap containing only unshipped orders.
     */
    public Heap<Order> consolidateOrders() {
        
        // Create an ArrayList to hold unshipped orders
        ArrayList<Order> unshippedOrdersList = new ArrayList<>();

        // Add all unshipped orders
        this.unshippedOrders.positionIterator();
        while (!this.unshippedOrders.offEnd()) {
            unshippedOrdersList.add(this.unshippedOrders.getIterator());
            this.unshippedOrders.advanceIterator();
        }

        // Create and return a new Heap with only unshipped orders
        return new Heap<>(unshippedOrdersList, orderPriorityComparator);
    }

    /**
     * Places a new order with standard shipping.
     * 
     * @param game The video game to order.
     * @param date The date of the order.
     * @return The order ID.
     */
    public int placeStandardOrder(VideoGame game, String date) {
        return placeOrder(game, date, 1);
    }

    /**
     * Places a new order with rush shipping.
     * 
     * @param game The video game to order.
     * @param date The date of the order.
     * @return The order ID.
     */
    public int placeRushOrder(VideoGame game, String date) {
        return placeOrder(game, date, 2);
    }

    /**
     * Places a new order with overnight shipping.
     * 
     * @param game The video game to order.
     * @param date The date of the order.
     * @return The order ID.
     */
    public int placeOvernightOrder(VideoGame game, String date) {
        return placeOrder(game, date, 3);
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
    /**
     * Places a new order.
     * 
     * @param game          The video game to order.
     * @param date          The date of the order.
     * @param shippingSpeed The shipping speed (1=standard, 2=rush, 3=overnight).
     * @return The order ID.
     */
    public int placeOrder(VideoGame game, String date, int shippingSpeed) {
        // Validate input
        if (game == null || date == null) {
            throw new IllegalArgumentException("Game and date cannot be null");
        }

        LinkedList<VideoGame> gameList = new LinkedList<>();
        gameList.addLast(game);

        // Use the current size of unshippedOrders + 1 as the order ID
        int orderId = unshippedOrders.getLength() + 1;

        Order newOrder = new Order(orderId, this, date, gameList, shippingSpeed);
        unshippedOrders.addLast(newOrder);

        // Decrease game stock
        game.decreaseStock(1);

        return orderId;
    }

    /**
     * Processes the highest priority order, marking it as shipped.
     * 
     * @return The order that was processed, or null if no orders to process.
     */
    public Order processNextOrder() {
        if (unshippedOrders.getLength() == 0) {
            return null;
        }

        // Find and remove the highest priority order
        Order highestPriorityOrder = null;
        int highestPriority = Integer.MIN_VALUE;
        int highestPriorityIndex = -1;

        unshippedOrders.positionIterator();
        for (int i = 0; i < unshippedOrders.getLength(); i++) {
            Order currentOrder = unshippedOrders.getIterator();
            if (currentOrder.getPriority() > highestPriority) {
                highestPriority = currentOrder.getPriority();
                highestPriorityOrder = currentOrder;
                highestPriorityIndex = i;
            }
            unshippedOrders.advanceIterator();
        }

        // Remove the highest priority order
        if (highestPriorityOrder != null) {
            // Reset iterator and remove the order
            unshippedOrders.positionIterator();
            for (int i = 0; i < highestPriorityIndex; i++) {
                unshippedOrders.advanceIterator();
            }
            unshippedOrders.removeIterator();

            // Add to shipped orders
            shippedOrders.addLast(highestPriorityOrder);
            return highestPriorityOrder;
        }

        return null;
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

    /**
     * Writes all customer data to a file.
     * 
     * @param filename The name of the file to write to.
     * @return true if successful, false otherwise.
     */
    public boolean writeToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {

            writer.println(getFirstName() + " " + getLastName());

            writer.println(getEmail());

            writer.println(getPassword());

            // Write cash balance
            writer.println(cashBalance);

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