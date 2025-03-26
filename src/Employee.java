/**
  * Employee.java
  * @author Huey Nguyen
  * CIS 22C, Final Project
  */
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import DataStructures.BST;
import DataStructures.Heap;
import DataStructures.HashTable;
import java.util.ArrayList;
import java.util.Comparator;
import DataStructures.LinkedList;

public class Employee extends User{

    private boolean isManager;
    private BST<VideoGame> gameTitles;
    private BST<VideoGame> gameGenres;
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
        this.gameTitles = new BST<>(videoGameTitles, new VideoGameComparator());
        this.gameGenres = new BST<>(videoGameGenres, new VideoGameComparator());
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
        this.gameTitles = new BST<VideoGame>();
        this.gameGenres = new BST<VideoGame>();
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

    public Order searchOrderByCustomerName(String firstName, String lastName) {
        ArrayList<Order> ordersList = unshippedOrders.sort();
            for (int i = 0; i < ordersList.size(); i++) {
                Order order = ordersList.get(i);
                if (order.getCustomer().getFirstName().equalsIgnoreCase(firstName) &&
                    order.getCustomer().getLastName().equalsIgnoreCase(lastName)) {
                    return order;
                }
            }
            return null;
    }
    /**
     * View the next order in the priority 
     * 
     */

    public void viewHighestPriorityOrder() {
        System.out.println(unshippedOrders.getMax());
    }

    /**
     * Prints all the orders
     */
    public void viewAllOrders() {
        System.out.println(unshippedOrders); 
    }
    /**
     * ships an order out  (Need get shipped/unshipped list from customer class)
     * (Remove from Heap. Insert Order to shipped Linked List for the Customer + Remove from Unshipped List)
     * @param orderID the order to be shipped
     */
    public void shipOrder(int orderID) {
        Order orderToShip = searchOrderById(orderID);
        if (orderToShip != null) {
            unshippedOrders.remove(orderID);

            LinkedList<Order> shipped = orderToShip.getCustomer().getShippedList();
            shipped.addLast(orderToShip);

            LinkedList<Order> unshipped = orderToShip.getCustomer().getUnshippedList();
            int orderIndex = unshipped.findIndex(orderToShip);
            unshipped.advanceIteratorToIndex(orderIndex);
            unshipped.removeIterator();


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
            
            pw.println(getFirstName() + " " + getLastName());
            pw.println(getEmail());
            pw.println(getPassword());
            pw.println(isManager());


        } catch (IOException e) {
            System.out.println("Error writing to " + filename);
        }
    }

    /**
     * update game details
     * @param gameTitle title of the game
     * @param newPrice new price to be set
     * @param newDescription new description
     * @param additionalStock the stock to add
     * @precondition gameTitle needs to be given
     */

    public void updateGameByKey(String gameTitle, double newPrice, String newDescription,
     int additionalStock, String rating, String genre) {
        if(this.isManager()){
            VideoGame searchGame = new VideoGame(gameTitle);
            VideoGame game = gameTitles.search(searchGame, new VideoGameComparator());

            if (game != null) {
                if(gameTitle != null){
                    game.setTitle(gameTitle);
                    gameTitles.remove(game, new VideoGameComparator());
                    gameGenres.remove(game, new VideoGameComparator());
                    gameTitles.insert(game, new VideoGameComparator());
                    gameGenres.insert(game, new VideoGameComparator());
                }
                else if(newPrice != 0.00){
                    game.setPrice(newPrice);
                }
                else if(newDescription != null){
                    game.setDescription(newDescription);
                }  
                else if(additionalStock != 0){
                    game.setStock(game.getStock() + additionalStock);
                }
                else if(rating != null){
                    game.setAgeRating(rating);
                }
                else if(genre != null){
                    game.setGenre(genre);
                    gameTitles.remove(game, new VideoGameComparator());
                    gameGenres.remove(game, new VideoGameComparator());
                    gameTitles.insert(game, new VideoGameComparator());
                    gameGenres.insert(game, new VideoGameComparator());
                }
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
            gameTitles.insert(newGame, new VideoGameComparator());
            gameGenres.insert(newGame, new VideoGameComparator());
        }
    }
/**
 * removes a game
 * @param gameTitle
 */
    public void removeGame(String gameTitle) {
        if(this.isManager()){
            gameTitles.remove(new VideoGame(gameTitle), new VideoGameComparator());
            gameGenres.remove(new VideoGame(gameTitle), new VideoGameComparator());
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
        finish += isManager();
        return finish;

    }

}
