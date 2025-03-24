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
import java.util.ArrayList;
import java.util.Comparator;

public class Employee extends User{

    private boolean isManager;
    private LinkedList<Order> unshippedOrders;
    private LinkedList<Order> shippedOrders;
    private BST<VideoGame> gameCatalog;
    private HashTable<VideoGame> gameTable;
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
    public Employee(String firstName, String lastName, String email, String password, boolean isManager, HashTable<Customer> customers, HashTable<VideoGame> gameTable) {
        super(firstName, lastName, email, password);
        this.isManager = isManager;
        this.unshippedOrders = new LinkedList<>();
        this.shippedOrders = new LinkedList<>();
        this.gameCatalog = new BST<>();
        this.gameTable = gameTable;
        this.customers = customers;
    }
     /**
      * Employee constructor only email and password
      * @param email employee email
      * @param password employee password
      */

    public Employee(String email, String password){
        super("", "", email, password);
        this.isManager = false;        
        this.unshippedOrders = new LinkedList<>();
        this.shippedOrders = new LinkedList<>();
        this.gameCatalog = new BST<>();
        this.gameTable = new HashTable<>(10);
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
 * comparator for video game titles
 */
    public class VideoGameComparator implements Comparator<VideoGame> {
        @Override
        public int compare(VideoGame game1, VideoGame game2) {
            return game1.getTitle().compareTo(game2.getTitle());
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
     * Prints all the orders
     */
    public void viewAllOrders() {
        ArrayList<Order> ordersArrayList = new ArrayList<>();
        unshippedOrders.positionIterator();
        while (!unshippedOrders.offEnd()) {
            Order order = unshippedOrders.getIterator();
            ordersArrayList.add(order);
            unshippedOrders.advanceIterator();
        }
        Heap<Order> heap = new Heap<>(ordersArrayList, new OrderPriorityComparator());

        ArrayList<Order> sortedOrders = heap.sort();

        for (int i = 0; i < sortedOrders.size(); i++) {
            System.out.println(sortedOrders.get(i)); 
        }
    }
    /**
     * ships an order out 
     * @param orderID the order to be shipped
     */
    public void shipOrder(int orderID) {
        Order orderToShip = searchOrderById(orderID);
        if (orderToShip != null) {
            while(!unshippedOrders.offEnd()){
                unshippedOrders.positionIterator();
                if(unshippedOrders.getIterator().getOrderID() == orderID){
                    unshippedOrders.removeIterator();
                }
                else{
                    unshippedOrders.advanceIterator();
                }
            }
            shippedOrders.addLast(orderToShip);
            System.out.println("Order " + orderID + " has been shipped.");
        } 
        else {
            System.out.println("Order not found.");
        }
    }
    /**
     * writes all the shipped orders to a file
     * @param filename name of the file to write to
     */
    public void writeToFile(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            shippedOrders.positionIterator();
            while (!shippedOrders.offEnd()) {
                pw.println(shippedOrders.getIterator().toString());
                shippedOrders.advanceIterator();
            }
        } catch (IOException e) {
            System.out.println("Error writing to " + filename);
        }
    }

    /**
     *  not finished
     * @param gameTitle title of the game
     * @param newPrice new price to be set
     * @param newDescription new description
     * @param additionalStock the stock to add
     */

    public void updateGameByKey(String gameTitle, double newPrice, String newDescription, int additionalStock) {
        if(this.isManager()){
            VideoGame searchGame = new VideoGame(gameTitle);

            VideoGame game = gameTable.get(searchGame);
            
            if (game != null) {
                game.setPrice(newPrice);
                game.setDescription(newDescription);
                game.setStock(game.getStock() + additionalStock);

            } 
            else {
            System.out.println("Game not found.");
            }
        }
        
        
    }

/**
 * adds new game
 * @param newGame new game to be added
 */
    public void addNewGame(VideoGame newGame) {
        if(this.isManager()){
            gameCatalog.insert(newGame, new VideoGameComparator());
        }
    }
/**
 * removes a game
 * @param gameTitle
 */
    public void removeGame(String gameTitle) {
        if(this.isManager()){
            gameCatalog.remove(new VideoGame(gameTitle), new VideoGameComparator());
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

}
