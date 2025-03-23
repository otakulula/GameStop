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
import DataStructures.BST;
import DataStructures.LinkedList;
import DataStructures.Heap;

public class Customer extends User {
    private LinkedList<Order> shippedOrders;
    private LinkedList<Order> unshippedOrders;
    private BST<VideoGame> gamesByTitle;
    private BST<VideoGame> gamesByGenre;
    private Heap<Order> pendingOrders;
    private int nextOrderID;
    private ArrayList<VideoGame> ownedGames;
    private double cashBalance;

    // Use the existing StrComparator
    private StrComparator strComparator = new StrComparator();

    /**
     * Constructor for Customer.
     * 
     * @param firstName The first name of the customer.
     * @param lastName  The last name of the customer.
     * @param email     The email address of the customer.
     * @param password  The password for the customer.
     */
    public Customer(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
        this.shippedOrders = new LinkedList<>();
        this.unshippedOrders = new LinkedList<>();
        this.gamesByTitle = new BST<>();
        this.gamesByGenre = new BST<>();
        this.pendingOrders = new Heap<>(new ArrayList<Order>(), orderPriorityComparator);
        this.nextOrderID = 1;
        this.ownedGames = new ArrayList<>();
        this.cashBalance = 0.0;

        if (ownedGames != null) {
            this.ownedGames = new ArrayList<>(ownedGames);
            // Add each game to both BSTs
            for (VideoGame game : ownedGames) {
                gamesByTitle.insert(game, titleComparator);
                gamesByGenre.insert(game, genreComparator);
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
        this.gamesByTitle = new BST<>();
        this.gamesByGenre = new BST<>();
        this.pendingOrders = new Heap<>(new ArrayList<Order>(), orderPriorityComparator);
        this.nextOrderID = 1;
        this.ownedGames = new ArrayList<>();
        this.cashBalance = 0.0;

        if (ownedGames != null) {
            this.ownedGames = new ArrayList<>(ownedGames);
            // Add each game to both BSTs
            for (VideoGame game : ownedGames) {
                gamesByTitle.insert(game, titleComparator);
                gamesByGenre.insert(game, genreComparator);
            }
        }
    }
    
    //Commented out the arraylist functions

    /*
    public void addOwnedGame(VideoGame game) {
        ownedGames.add(game);
    }

    public ArrayList<VideoGame> getOwnedGames() {
        return ownedGames;
    }

    public void setOwnedGames(ArrayList<VideoGame> games) {
        this.ownedGames = games;
    }
    */

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
     * Comparator for sorting VideoGames by title (primary key).
     */
    private Comparator<VideoGame> titleComparator = new Comparator<VideoGame>() {
        @Override
        public int compare(VideoGame game1, VideoGame game2) {
            return strComparator.compare(game1.getTitle(), game2.getTitle());
        }
    };

    /**
     * Comparator for sorting VideoGames by genre (secondary key).
     */
    private Comparator<VideoGame> genreComparator = new Comparator<VideoGame>() {
        @Override
        public int compare(VideoGame game1, VideoGame game2) {
            int result = strComparator.compare(game1.getGenre(), game2.getGenre());
            if (result == 0) {
                return strComparator.compare(game1.getTitle(), game2.getTitle());
            }
            return result;
        }
    };

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
     * Adds a video game to both BSTs.
     * 
     * @param game The video game to add.
     */
    public void addGame(VideoGame game) {
        gamesByTitle.insert(game, titleComparator);
        gamesByGenre.insert(game, genreComparator);
    }

    /**
     * Searches for a video game by title.
     * 
     * @param title The title to search for.
     * @return The video game if found, null otherwise.
     */
    public VideoGame searchByTitle(String title) {
        VideoGame searchKey = new VideoGame(title);
        return gamesByTitle.search(searchKey, titleComparator);
    }

    /**
     * Searches for video games by genre.
     * 
     * @param genre The genre to search for.
     * @return The video game if found, null otherwise.
     */
    public VideoGame searchByGenre(String genre) {
        // Create a temporary game with the search genre
        VideoGame searchKey = new VideoGame("");
        searchKey.setGenre(genre);
        return gamesByGenre.search(searchKey, genreComparator);
    }

    /**
     * Lists all video games sorted by title.
     * 
     * @return A string containing all games sorted by title.
     */
    public String listGamesByTitle() {
        return gamesByTitle.inOrderString();
    }

    /**
     * Lists all video games sorted by genre.
     * 
     * @return A string containing all games sorted by genre.
     */
    public String listGamesByGenre() {
        return gamesByGenre.inOrderString();
    }

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
        Order newOrder = new Order(orderId, this, date, games, shippingSpeed, priority);
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

    /**
     * Gets a list of all orders (both shipped and unshipped).
     * 
     * @return A string representation of all orders.
     */
    public String viewAllOrders() {
        StringBuilder sb = new StringBuilder();
        sb.append("Shipped Orders:\n");
        sb.append(viewShippedOrders());
        sb.append("\nUnshipped Orders:\n");
        sb.append(viewUnshippedOrders());
        return sb.toString();
    }

    /**
     * Writes all customer data to a file.
     * 
     * @param filename The name of the file to write to.
     * @return true if successful, false otherwise.
     */
    public boolean writeToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Write customer information
            writer.println("Customer: " + getFirstName() + " " + getLastName());
            writer.println("Email: " + getEmail());
            writer.println("Cash Balance: $" + cashBalance);

            // Write owned games
            writer.println("\nOwned Games:");
            for (VideoGame game : ownedGames) {
                writer.println(game.toString());
            }

            // Write game database
            writer.println("\nGame Database (Sorted by Title):");
            writer.println(listGamesByTitle());

            // Write orders
            writer.println("Shipped Orders:");
            writer.println(viewShippedOrders());

            writer.println("Unshipped Orders:");
            writer.println(viewUnshippedOrders());

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Search for a product by any criteria.
     * 
     * @param keyword The keyword to search for.
     * @return A string containing all matching games.
     */
    public String searchProducts(String keyword) {
        StringBuilder results = new StringBuilder();
        keyword = keyword.toLowerCase();

        // Search in titles, descriptions, genres, age ratings
        if (!gamesByTitle.isEmpty()) {
            String allGames = gamesByTitle.inOrderString();
            String[] games = allGames.split("\n");

            for (String gameStr : games) {
                if (gameStr.toLowerCase().contains(keyword)) {
                    results.append(gameStr).append("\n");
                }
            }
        }

        return results.length() > 0 ? results.toString() : "No games found matching: " + keyword;
    }

    /**
     * Lists the entire database of products.
     * 
     * @return A string containing all games.
     */
    public String listDatabase() {
        return gamesByTitle.inOrderString();
    }

    /**
     * Check if the pendingOrders heap is empty.
     * 
     * @return true if there are no pending orders, false otherwise.
     */
    public boolean isPendingOrdersEmpty() {
        return pendingOrders.getHeapSize() == 0;
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

        // Owned games
        sb.append("\nOwned Games (").append(ownedGames.size()).append("):\n");
        if (ownedGames.isEmpty()) {
            sb.append("  No games owned\n");
        } else {
            for (VideoGame game : ownedGames) {
                sb.append("  ").append(game.getTitle()).append("\n");
            }
        }

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
}