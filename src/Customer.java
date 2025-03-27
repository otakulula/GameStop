/**
 * Customer.java
 * @author Stefano Pinna Segovia
 * CIS 22C, Final Project
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import DataStructures.LinkedList;
import DataStructures.Heap;

public class Customer extends User {
    private LinkedList<Order> shippedOrders;
    private LinkedList<Order> unshippedOrders;
    private Heap<Order> pendingOrders;
    private double cashBalance;
    private boolean isGuest;

    private static final Logger LOGGER = Logger.getLogger(Customer.class.getName());

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
                    int numOfUnshipped, ArrayList<Order> unshippedVideoGames, boolean isGuest) {
        super(firstName, lastName, email, password);

        this.shippedOrders = new LinkedList<>();
        this.unshippedOrders = new LinkedList<>();
        this.pendingOrders = new Heap<>(unshippedVideoGames != null ? unshippedVideoGames : new ArrayList<>(),
                orderPriorityComparator);

        this.cashBalance = cashBalance;
        this.isGuest = isGuest;

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
        super("","", email, password);
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
        super("NO FIRST NAME GIVEN", "NO LAST NAME GIVEN", email, password);
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
     * Consolidates unshipped orders with improved error handling.
     *
     * @return A Heap containing only unshipped orders.
     */
    public Heap<Order> consolidateOrders() {
        // Create an ArrayList to hold unshipped orders
        ArrayList<Order> unshippedOrdersList = new ArrayList<>();

        // Add debug logging for empty list
        if (this.unshippedOrders.isEmpty()) {
            LOGGER.log(Level.INFO, "No unshipped orders to consolidate.");
            return new Heap<>(unshippedOrdersList, orderPriorityComparator);
        }

        // Add all unshipped orders
        this.unshippedOrders.positionIterator();
        try {
            while (!this.unshippedOrders.offEnd()) {
                unshippedOrdersList.add(this.unshippedOrders.getIterator());
                this.unshippedOrders.advanceIterator();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error consolidating orders", e);
        }

        // Create and return a new Heap with only unshipped orders
        return new Heap<>(unshippedOrdersList, orderPriorityComparator);
    }

    /**
     * Places a new order with specified shipping speed.
     *
     * @param game          The video game to order.
     * @param date          The date of the order.
     * @param shippingSpeed The shipping speed:
     *                      1 = Standard Shipping
     *                      2 = Rush Shipping
     *                      3 = Overnight Shipping
     * @return The order ID.
     * @throws IllegalArgumentException if game or date is null, or shipping speed
     *                                  is invalid
     */
    public int placeOrder(VideoGame game, String date, int shippingSpeed) {
        // Validate input
        if (game == null || date == null) {
            throw new IllegalArgumentException("Game and date cannot be null");
        }

        // Validate shipping speed
        if (shippingSpeed < 1 || shippingSpeed > 3) {
            throw new IllegalArgumentException(
                    "Invalid shipping speed. Must be 1 (Standard), 2 (Rush), or 3 (Overnight).");
        }

        LinkedList<VideoGame> gameList = new LinkedList<>();
        gameList.addLast(game);




            int sum = 0;
            String key = this.getFirstName() + " " + this.getLastName() + this.getEmail() + this.getPassword() + game.getTitle();
            for(int j = 0; j < key.length(); j++){
                sum += key.charAt(j);
            }
            int orderID = sum;

        Order newOrder = new Order(orderID, this, date, gameList, shippingSpeed);
        unshippedOrders.addLast(newOrder);

        game.decreaseStock(1);

        return orderID;
    }

    /**
     * Processes the highest priority order with enhanced error handling.
     *
     * @return The order that was processed, or null if no orders to process.
     */
    public Order processNextOrder() {
        if (unshippedOrders.getLength() == 0) {
            LOGGER.log(Level.INFO, "No unshipped orders to process.");
            return null;
        }

        try {
            // Find and remove the highest priority order
            Order highestPriorityOrder = null;
            int highestPriority = Integer.MIN_VALUE;
            int highestPriorityIndex = -1;

            unshippedOrders.positionIterator();
            for (int i = 0; i < unshippedOrders.getLength(); i++) {
                Order currentOrder = unshippedOrders.getIterator();
                if (currentOrder == null) {
                    LOGGER.log(Level.WARNING, "Null order encountered at index " + i);
                    continue;
                }

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
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing next order", e);
        }

        return null;
    }

    /**
     * Gets a list of all shipped orders.
     *
     * @return A string representation of all shipped orders.
     */
    public String viewShippedOrders() {
        if(shippedOrders.isEmpty()){
            return "You don't have any shipped orders!\n\n";
        }
        return "\nHere is Shipped orders:\n" + shippedOrders.toString();
    }

    /**
     * Gets a list of all unshipped orders.
     *
     * @return A string representation of all unshipped orders.
     */
    public String viewUnshippedOrders() {
        if(unshippedOrders.isEmpty()){
            return "You don't have any unshipped orders!\n\n";
        }
        return "\nHere is Unshipped orders:\n" + unshippedOrders.toString();
    }

    /**
     * Returns a string representation of the Customer object in the specified
     * format.
     *
     * @return A string containing the customer's information and owned/ordered
     *         games.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Customer basic information
        sb.append(getFirstName()).append(" ").append(getLastName()).append("\n");
        sb.append(getEmail()).append("\n");
        sb.append(getPassword()).append("\n");
        sb.append(String.format("%.2f", cashBalance)).append("\n");

        // Owned Games Section
        ArrayList<Order> ownedGames = new ArrayList<>();

        // Add shipped orders
        shippedOrders.positionIterator();
        while (!shippedOrders.offEnd()) {
            ownedGames.add(shippedOrders.getIterator());
            shippedOrders.advanceIterator();
        }

        // Write number of owned games
        sb.append(ownedGames.size()).append("\n");

        // Write owned game details
        for (Order order : ownedGames) {
            sb.append(order.getGameTitle()).append("\n");
            sb.append(order.getPriority()).append("\n");
        }

        // Ordered Games Section
        ArrayList<Order> orderedGames = new ArrayList<>();

        // Add unshipped orders
        unshippedOrders.positionIterator();
        while (!unshippedOrders.offEnd()) {
            orderedGames.add(unshippedOrders.getIterator());
            unshippedOrders.advanceIterator();
        }

        // Write number of ordered games
        sb.append(orderedGames.size()).append("\n");

        // Write ordered game details
        for (Order order : orderedGames) {
            sb.append(order.getGameTitle()).append("\n");
            sb.append(order.getPriority()).append("\n");
        }

        sb.append(isGuest).append("\n");

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
     * Removes a specific order from the shipped orders list with debug logging.
     *
     * @param order The order to remove from shipped orders.
     * @return true if the order was successfully removed, false otherwise.
     */
    public boolean removeShippedOrder(Order order) {
        if (this.shippedOrders.isEmpty()) {
            LOGGER.log(Level.INFO, "Cannot remove order: shipped orders list is empty.");
            return false;
        }

        this.shippedOrders.positionIterator();
        try {
            while (!this.shippedOrders.offEnd()) {
                if (this.shippedOrders.getIterator().equals(order)) {
                    this.shippedOrders.removeIterator();
                    LOGGER.log(Level.INFO, "Order successfully removed from shipped orders.");
                    return true;
                }
                this.shippedOrders.advanceIterator();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error removing shipped order", e);
        }

        LOGGER.log(Level.WARNING, "Order not found in shipped orders list.");
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
     * Removes a specific order from the unshipped orders list with debug logging.
     *
     * @param order The order to remove from unshipped orders.
     * @return true if the order was successfully removed, false otherwise.
     */
    public boolean removeUnshippedOrder(Order order) {
        if (this.unshippedOrders.isEmpty()) {
            LOGGER.log(Level.INFO, "Cannot remove order: unshipped orders list is empty.");
            return false;
        }

        this.unshippedOrders.positionIterator();
        try {
            while (!this.unshippedOrders.offEnd()) {
                if (this.unshippedOrders.getIterator().equals(order)) {
                    this.unshippedOrders.removeIterator();
                    LOGGER.log(Level.INFO, "Order successfully removed from unshipped orders.");
                    return true;
                }
                this.unshippedOrders.advanceIterator();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error removing unshipped order", e);
        }

        LOGGER.log(Level.WARNING, "Order not found in unshipped orders list.");
        return false;
    }

    /**
     * Returns the heap of pending orders.
     *
     * @return The heap of pending orders.
     */
    public Heap<Order> getPendingOrderHeap() {
        return pendingOrders;
    }
}