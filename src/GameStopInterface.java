import DataStructures.BST;
import DataStructures.HashTable;

import java.io.*;
import java.util.*;

/**
 * Compares two VideoGame objects by title.
 */
class TitleComparator implements Comparator<VideoGame>{
    /**
     * Compares two VideoGame objects by title.
     * @param game1 the first VideoGame
     * @param game2 the second VideoGame
     * @return 0 if the titles are the same, a positive number if the first title comes after the second title,
     * and a negative number if the first title comes before the second title
     */
    public int compare(VideoGame game1, VideoGame game2){
        return game1.getTitle().compareTo(game2.getTitle());
    }
}

/**
 * Compares two VideoGame objects by genre.
 */
class GenreComparator implements Comparator<VideoGame>{
    /**
     * Compares two VideoGame objects by genre.
     * @param game1 the first VideoGame
     * @param game2 the second VideoGame
     * @return 0 if the genres are the same, a positive number if the first genre comes after the second genre,
     * and a negative number if the first genre comes before the second genre
     */
    public int compare(VideoGame game1, VideoGame game2){
        return game1.getTitle().compareTo(game2.getTitle());
    }
}



public class GameStopInterface {

    static Comparator<VideoGame> titleComparator = new TitleComparator();
    static  Comparator<VideoGame> genreComparator = new GenreComparator();
    static  final int NUM_CUSTOMERS = 100;
    static  final int NUM_EMPLOYEE = 4;


    public static void main(String[] args) throws Exception {
//        File cusFile = new File("DataBases/customers.txt");
//        File empFile = new File("DataBases/employees.txt");
//        File vgFile = new File("DataBases/videoGames.txt");

        File cusFile = new File("src/scuffedCustomers.txt");
       File empFile = new File("src/UpdatedEmp.txt");
          File vgFile = new File("src/VideoGameUpdated.txt");


        HashTable<Customer> cuHashTable = new HashTable<>(NUM_CUSTOMERS);
        HashTable<Employee> empHashTable = new HashTable<>(NUM_EMPLOYEE * 3);
        ArrayList<Order> allUnshippedOrders = new ArrayList<>();
        BST<VideoGame> videoGameDatabase_Title = new BST<>();
        BST<VideoGame> videoGameDatabase_Genre = new BST<>();

        fillVideoGames(vgFile ,videoGameDatabase_Title, videoGameDatabase_Genre);
        fillCustomers(cusFile, cuHashTable, videoGameDatabase_Title, allUnshippedOrders);
        fillEmployee(empFile, empHashTable, videoGameDatabase_Title, videoGameDatabase_Genre, cuHashTable, allUnshippedOrders);


        Scanner obj = new Scanner(System.in);
        System.out.println("Welcome to Game Stop!");
        System.out.print("Please enter your email address: ");
        String email = obj.next();
        System.out.print("Please enter your password: ");
        String password = obj.next();
        User isAccReal = doesAccountExist(email, password, cuHashTable, empHashTable);

        if(isAccReal instanceof Customer){
            System.out.print("\nWelcome, " + isAccReal.getFirstName() + " " + isAccReal.getLastName() + "!\n\n\n");
            performOption(obj, isAccReal, cuHashTable, empHashTable, videoGameDatabase_Title, videoGameDatabase_Genre, vgFile);
        } else if ( isAccReal instanceof Employee) {
            System.out.print("\nWelcome, " + isAccReal.getFirstName() + " " + isAccReal.getLastName() + "!\n\n\n");
            performOption(obj, isAccReal, cuHashTable, empHashTable, videoGameDatabase_Title, videoGameDatabase_Genre, vgFile);
        } else {
            System.out.print("\nWe don't have your account on file...\n\n" +
                    "To remain as Guest type 1 or To create an account type 2: ");
            int answer = obj.nextInt();

            while (answer != 1 && answer != 2 ){
                System.out.print("\nInvalid answer\n" + "Enter 1 for Guest or 2 to create account: ");
                answer = obj.nextInt();
            }

            if( answer == 1){
                Customer guest = new Customer(email, password, true);
                cuHashTable.add(guest);
                System.out.println();
                System.out.print("Welcome, Guest user!\n\n\n");
                performOption(obj, guest, cuHashTable, empHashTable, videoGameDatabase_Title, videoGameDatabase_Genre, vgFile);
            } else {
                System.out.print("Enter your first name: ");
                String firstName = obj.next();
                System.out.print("Enter your last name: ");
                String lastName = obj.next();
                Customer newCus = new Customer(firstName, lastName, email, password, false);
                cuHashTable.add(newCus);
                System.out.println();
                System.out.print("Welcome, " + newCus.getFirstName() + " " + newCus.getLastName() + "!\n\n\n");
                performOption(obj, newCus, cuHashTable, empHashTable, videoGameDatabase_Title, videoGameDatabase_Genre, vgFile);
            }
        }
    }

    /**
     * Displays the menu options and performs the selected operation based on user input.
     *
     * @param obj   The Scanner object to read user input.
     * @param user   The User object representing the current user.
     */
    public static void performOption (Scanner obj, User user, HashTable<Customer> cuHashTable,  HashTable<Employee> empHashTable, BST<VideoGame> titleBST, BST<VideoGame> genreBST, File vgFile) throws IOException {
        String choice = options(obj, user);
        String c = choice.toUpperCase();

        if( user instanceof Customer) {
            if(c.equals("A")){
                searchAGame(obj, user, cuHashTable, empHashTable, titleBST, genreBST, vgFile);  //works NOT THE GENRE
            } else if (c.equals("B")){
                viewAllGames(obj, user, cuHashTable, empHashTable, titleBST, genreBST, vgFile); // works
            } else if (c.equals("C")){
                orderVideoGame(obj, user, cuHashTable, empHashTable, titleBST, genreBST, vgFile);
            } else if (c.equals("D")){
                viewPurchasedGames(obj, user, cuHashTable, empHashTable, titleBST, genreBST, vgFile); // works (scuffed)
            } else if ( c.equals("X")){
                exitOptionX(obj, user, empHashTable, titleBST, cuHashTable);
            } else {
                invalidChoice(obj, user, cuHashTable, empHashTable, titleBST, genreBST, vgFile);
            }
        } else if( user instanceof Employee ) {
            Employee emp = (Employee) user;
            if( emp.isManager()){
                if(c.equals("A")){
                    searchCusOrder(obj, user, cuHashTable, empHashTable, titleBST, genreBST, vgFile); // works
                } else if (c.equals("B")){
                    highestPriorityOrder(obj, user, cuHashTable, empHashTable, titleBST, genreBST, vgFile); //works
                } else if (c.equals("C")){
                    viewAllOrders(obj, user, cuHashTable, empHashTable, titleBST, genreBST, vgFile); // works almost , needs to be sorted!
                } else if (c.equals("D")){
                    shipOrder(obj, user, cuHashTable, empHashTable, titleBST, genreBST, vgFile); // need to be fixed
                } else if ( c.equals("E")){
                    updateVideoGameDB(obj, user, cuHashTable, empHashTable, titleBST, genreBST, vgFile); // maybe
                } else if ( c.equals("X")) {
                    exitOptionX(obj,user ,empHashTable, titleBST, cuHashTable);
                } else{
                    invalidChoice(obj, user, cuHashTable, empHashTable, titleBST, genreBST, vgFile); // need to be fixed
                }
            } else {
                if(c.equals("A")){
                    searchCusOrder(obj, user, cuHashTable, empHashTable, titleBST, genreBST, vgFile); // works
                } else if (c.equals("B")){
                    highestPriorityOrder(obj, user, cuHashTable, empHashTable, titleBST, genreBST, vgFile); //works
                } else if (c.equals("C")){
                    viewAllOrders(obj, user, cuHashTable, empHashTable, titleBST, genreBST, vgFile); // works almost , needs to be sorted!
                } else if (c.equals("D")){
                    shipOrder(obj, user, cuHashTable, empHashTable, titleBST, genreBST, vgFile); // need to be fixed
                } else if ( c.equals("X")){
                    exitOptionX(obj, user, empHashTable, titleBST, cuHashTable);
                } else {
                    invalidChoice(obj, user, cuHashTable, empHashTable, titleBST, genreBST, vgFile);
                }
            }
        }
    }

    /* Customer Options: */
    public static void searchAGame ( Scanner obj, User user, HashTable<Customer> cuHashTable,  HashTable<Employee> empHashTable, BST<VideoGame> titleBST, BST<VideoGame> genreBST, File vgFile) throws IOException {
        System.out.print("\nDo you want to search by Title (type 1) or genre (type 2): ");
        int answer = obj.nextInt();

        while (answer != 1 && answer != 2  ){
            System.out.print("\nInvalid answer\n" + "Enter 1 for Title or 2 for Genre: ");
            answer = obj.nextInt();
        }

        if( answer == 1){
            System.out.print("\nType the Title of the video game: ");
            obj.nextLine();
            String gameName = obj.nextLine();
            VideoGame videoGame = new VideoGame(gameName);
            VideoGame game = titleBST.search(videoGame, titleComparator);
            System.out.println();
            if (game == null) {
                System.out.print(gameName + " doesn't exist!");
                System.out.println();
            } else {
                System.out.print(game.getTitle() + "\nPrice: $" + game.getPrice() + "\nDescription: " + game.getDescription() + "\nStock: "
                        + game.getStock() + "\nAge Rating: " + game.getAgeRating() + "\nGenre: " + game.getGenre() + "\n");
            }
            System.out.println();
        } else {
            System.out.print("\nType the genre: ");
            String genre = obj.next();

            ArrayList<VideoGame> gamesInGenre = new ArrayList<>();


            collectGamesByGenre(genreBST, genre, gamesInGenre, vgFile);

            System.out.println();
            if (gamesInGenre.isEmpty()) {
                System.out.print("No games found in the " + genre + " genre.");
                System.out.println();
            } else {
                System.out.println("Games in the " + genre + " genre:");
                for (VideoGame game : gamesInGenre) {
                    System.out.print("\n" + game.getTitle() +
                            "\nPrice: $" + game.getPrice() +
                            "\nDescription: " + game.getDescription() +
                            "\nStock: " + game.getStock() +
                            "\nAge Rating: " + game.getAgeRating());
                    System.out.println();
                }
            }
            System.out.println();
        }
        performOption(obj, user, cuHashTable, empHashTable, titleBST, genreBST, vgFile);
    }

    public static void collectGamesByGenre(BST<VideoGame> genreBST, String genre, ArrayList<VideoGame> games, File vgFile) throws FileNotFoundException {
        ArrayList<String> gamesInGenre = new ArrayList<>();

        Scanner input = new Scanner(vgFile);
        while (input.hasNextLine()) {
            String name = input.nextLine();
            input.nextLine();
            input.nextLine();
            input.nextLine();
            input.nextLine();
            String g = input.next();
            if (input.hasNextLine()) {
                input.nextLine();
            }
            if( g.equals(genre)){
                gamesInGenre.add(name);
            }
            if (input.hasNextLine()) {
                input.nextLine();
            }
        }

        for (int i = 0; i < gamesInGenre.size() ; i++) {
            VideoGame game = new VideoGame( gamesInGenre.get(i));
            VideoGame videoGame = genreBST.search(game, genreComparator);
            games.add(videoGame);
        }

    }

    public static void viewAllGames( Scanner obj, User user, HashTable<Customer> cuHashTable,  HashTable<Employee> empHashTable, BST<VideoGame> titleBST, BST<VideoGame> genreBST, File vgFile) throws IOException {
        System.out.println();
        System.out.print(titleBST.inOrderString());
        performOption(obj, user, cuHashTable, empHashTable, titleBST, genreBST, vgFile);
    }

    public static void orderVideoGame(Scanner obj, User user,HashTable<Customer> cuHashTable,  HashTable<Employee> empHashTable, BST<VideoGame> titleBST, BST<VideoGame> genreBST, File vgFile) throws IOException {
        Customer cus = (Customer) user;
        if(!cus.isGuest()){
            System.out.println();
            System.out.print(titleBST.inOrderString());
            System.out.println();
            System.out.print("\nEnter the name of video game you want to order: ");
            obj.nextLine();
            String gameName = obj.nextLine();

            VideoGame videoGame = new VideoGame(gameName);
            VideoGame game = titleBST.search(videoGame, titleComparator);

            if (game == null) {
                System.out.println();
                System.out.print(gameName + " doesn't exist!");
                System.out.println();
            } else {
                if( cus.getCashBalance() < game.getPrice()){
                    System.out.print("\nYou don't have enough cash\nYou have $" + (int) cus.getCashBalance() + "\nWhile the video game costs: $" + game.getPrice());
                    System.out.print("\nWant to add cash --> Yes (type 1) or No (type 2): ");
                    int choice = obj.nextInt();

                    if(choice == 1){
                        System.out.print("\nEnter the amount of cash to add to buy game: $");
                        double moreCash = obj.nextDouble();
                        cus.setCashBalance(moreCash);

                        if(cus.getCashBalance() >= game.getPrice()) {
                            System.out.print("\nDo you want:\n1. Standard Shipping\n2. Rush Shipping\n3. Overnight Shipping\n\nEnter the number of your choice: ");
                            int shipNum = obj.nextInt();
                            System.out.print("\nEnter the date you ordered it, Follow this format --> Month, day: ");
                            obj.nextLine();
                            String date = obj.nextLine();
                            cus.placeOrder(game, date, shipNum);
                            cus.setCashBalance(cus.getCashBalance() - game.getPrice());
                            System.out.println();
                            System.out.println(gameName + " is ordered!");
                            System.out.println();
                        }
                    }
                } else {
                    System.out.print("\nDo you want:\n1. Standard Shipping\n2. Rush Shipping\n3. Overnight Shipping\n\nEnter the number of your choice: ");
                    int shipNum = obj.nextInt();
                    System.out.print("\nEnter the date you ordered it, Follow this format --> Month, day: ");
                    obj.nextLine();
                    String date = obj.nextLine();
                    cus.placeOrder(game, date, shipNum);
                    cus.setCashBalance(cus.getCashBalance() - game.getPrice());
                    System.out.println();
                    System.out.println(gameName + " is ordered!");
                    System.out.println();
                }
                System.out.println();
            }
        } else {
            System.out.print("\nAs a Guest, you don't have an account to purchase video games!\n"+
                    "Do you want to create an account\nYes (type 1)\nNo (type 2)\n\nEnter a number: ");
            int choice = obj.nextInt();

            while (choice != 1 && choice != 2 ){
                System.out.print("\nInvalid answer\n" + "Enter 1 to create an account or 2 to remain as Guest: ");
                choice = obj.nextInt();
            }

            if(choice == 1){
                System.out.print("Enter your first name: ");
                String firstName = obj.next();
                System.out.print("Enter your last name: ");
                String lastName = obj.next();
                cus.setFirstName(firstName);
                cus.setLastName(lastName);
                cus.setGuest(false);
                System.out.println();
                System.out.print("Welcome, " + cus.getFirstName() + " " + cus.getLastName() + "!\n\n\n");
            }
            System.out.println();
        }
        performOption(obj, user, cuHashTable, empHashTable, titleBST, genreBST, vgFile);
    }

    public static void viewPurchasedGames(Scanner obj, User user, HashTable<Customer> cuHashTable,  HashTable<Employee> empHashTable, BST<VideoGame> titleBST, BST<VideoGame> genreBST, File vgFile) throws IOException {
        Customer cus = (Customer) user;
        if(!cus.isGuest()){
            System.out.print("\nDo you want to see your shipped (type 1) or unshipped (type 2) orders: ");
            int answer = obj.nextInt();

            while (answer != 1 && answer != 2 ){
                System.out.print("\nInvalid answer\n" + "Enter 1 for shipped or 2 for unshipped: ");
                answer = obj.nextInt();
            }

            if(answer == 1){
            System.out.print(cus.viewShippedOrders());
            } else {
                System.out.print(cus.viewUnshippedOrders());
            }
        } else {
            System.out.print("\nAs a Guest, you don't have an account to see your purchased video games!\n"+
                    "Do you want to create an account\n1. Yes\n2. No\n\nEnter a number: ");
            int choice = obj.nextInt();

            while (choice != 1 && choice != 2 ){
                System.out.print("\nInvalid answer\n" + "Enter 1 to create an account or 2 to remain as Guest: ");
                choice = obj.nextInt();
            }

            if(choice == 1){
                System.out.print("Enter your first name: ");
                String firstName = obj.next();
                System.out.print("Enter your last name: ");
                String lastName = obj.next();
                cus.setFirstName(firstName);
                cus.setLastName(lastName);
                cus.setGuest(false);
                System.out.println();
                System.out.print("Welcome, " + cus.getFirstName() + " " + cus.getLastName() + "!\n\n\n");
            }
            System.out.println();
        }
        performOption(obj, user, cuHashTable, empHashTable, titleBST, genreBST, vgFile);
    }

    /* Employee and Manager Options: */
    public static void searchCusOrder(Scanner obj, User user, HashTable<Customer> cuHashTable,  HashTable<Employee> empHashTable, BST<VideoGame> titleBST, BST<VideoGame> genreBST, File vgFile) throws IOException {
        Employee emp = (Employee) user;
        System.out.print("\nDo you want to search by Order ID (type 1) or by customer's name (type 2): ");
        int answer = obj.nextInt();

        while (answer != 1 && answer != 2 ){
            System.out.print("\nInvalid answer\n" + "Enter 1 for Order ID or 2 for customer's name: ");
            answer = obj.nextInt();
        }

        if( answer == 1){
            System.out.print("\nType the Order ID: ");
            int orderId = obj.nextInt();
            System.out.print("\nHere is the Customer's order:\n" + emp.searchOrderById(orderId) + "\n\n");
        } else {
            System.out.print("\nEnter the customer's full name: ");
            String firstName = obj.next();
            String lastName = obj.next();
            obj.nextLine();
            System.out.print("\nHere is the Customer's order:\n" + emp.searchOrderByCustomerName(firstName, lastName));
        }
        performOption(obj, user, cuHashTable, empHashTable, titleBST, genreBST, vgFile);
    }

    public static void highestPriorityOrder (Scanner obj, User user,HashTable<Customer> cuHashTable,  HashTable<Employee> empHashTable, BST<VideoGame> titleBST, BST<VideoGame> genreBST, File vgFile) throws IOException {
        Employee emp = (Employee) user;
        System.out.print("\n\nCurrently the Order with the highest priority is:\n");
        emp.viewHighestPriorityOrder();
        performOption(obj, user, cuHashTable, empHashTable, titleBST, genreBST, vgFile);
    }

    public static void viewAllOrders(Scanner obj, User user, HashTable<Customer> cuHashTable,  HashTable<Employee> empHashTable, BST<VideoGame> titleBST, BST<VideoGame> genreBST, File vgFile) throws IOException {
        Employee emp = (Employee) user;
        emp.viewAllOrders();
        performOption(obj, user, cuHashTable, empHashTable, titleBST, genreBST, vgFile);
    }

    public static void shipOrder(Scanner obj, User user, HashTable<Customer> cuHashTable,  HashTable<Employee> empHashTable, BST<VideoGame> titleBST, BST<VideoGame> genreBST, File vgFile) throws IOException {
        Employee emp = (Employee) user;
        System.out.print("\nType the Order ID: ");
        int orderId = obj.nextInt();
        emp.shipOrder(orderId);
        performOption(obj, user, cuHashTable, empHashTable, titleBST, genreBST, vgFile);
    }

    public static void updateVideoGameDB(Scanner obj, User user, HashTable<Customer> cuHashTable,  HashTable<Employee> empHashTable, BST<VideoGame> titleBST, BST<VideoGame> genreBST, File vgFile) throws IOException {
        System.out.print("\nWould you like to:\n1. Add a new game\n2. Remove a existing game\n3. Update a existing game's info\n\nEnter your choice: ");
        int answer = obj.nextInt();

        while (answer != 1 && answer != 2 && answer != 3){
            System.out.print("\nInvalid answer\n" + "Enter 1 to add new game or 2 to remove a game or 3 to update a game's info: ");
            answer = obj.nextInt();
        }

        if( answer == 1){
            System.out.print("\n\nAdding a new game:\n");
            System.out.print("\nEnter the name of the video game: ");
            obj.nextLine();
            String gameName = obj.nextLine();
            System.out.print("\nEnter the price: ");
            double price = obj.nextDouble();
            System.out.print("\nEnter the game's Description: ");
            obj.nextLine();
            String gameDec = obj.nextLine();
            System.out.print("\nEnter how many will be in stock: ");
            int stock = obj.nextInt();
            System.out.print("\nEnter the age rating of the game: ");
            String ageString = obj.next();
            System.out.print("\nEnter the genre of the game: ");
            String genre = obj.next();
            VideoGame newGame = new VideoGame(gameName, price, gameDec, stock, ageString, genre);
            titleBST.insert(newGame, titleComparator);
            genreBST.insert(newGame, genreComparator);
            System.out.print("\n\nNew Game added!\n");
        } else if (answer == 2){
            System.out.print("\nName of video game to Remove: ");
            obj.nextLine();
            String gameName = obj.nextLine();
            VideoGame gameToRemove = titleBST.search(new VideoGame(gameName), titleComparator);

            if (gameToRemove != null) {
                titleBST.remove(gameToRemove, titleComparator);
                genreBST.remove(gameToRemove, genreComparator);
                System.out.print("\n" + gameName + " is removed.\n");
            } else {
                System.out.print("\n" + gameName + " not found in database.\n");
            }

        } else{
            System.out.print("\nEnter the name of game you would like to update: ");
            obj.nextLine();
            String gameName = obj.nextLine();
            VideoGame gameToChange = titleBST.search(new VideoGame(gameName), titleComparator);
            System.out.print("\nWould you like to:\n1. Change the game name\n2. Change the price\n3. Change the description\n4. Change the stock\n5. Change the age rating\n6. Change the genre\n\nEnter a number choice: ");
            int choice = obj.nextInt();
            titleBST.remove(gameToChange, titleComparator);
            genreBST.remove(gameToChange, genreComparator);

            if(choice == 1){
                System.out.print("\nEnter the new Game Name: ");
                obj.nextLine();
                String gName = obj.nextLine();
                gameToChange.setTitle(gName);
            } else if ( choice == 2){
                System.out.print("\nEnter the new Price: ");
                double cost = obj.nextDouble();
                gameToChange.setPrice(cost);
            } else if ( choice == 3){
                System.out.print("\nEnter the new Game Description: ");
                obj.nextLine();
                String gDec = obj.nextLine();
                gameToChange.setDescription(gDec);
            } else if ( choice == 4){
                System.out.print("\nEnter the new Stock: ");
                int gameStock = obj.nextInt();
                gameToChange.setStock(gameStock);
            } else if ( choice == 5){
                System.out.print("\nEnter the new Age Rating: ");
                String rating = obj.next();
                gameToChange.setAgeRating(rating);
            } else {
                System.out.print("\nEnter the new Genre: ");
                String gameGenre = obj.next();
                gameToChange.setGenre(gameGenre);
            }
            titleBST.insert(gameToChange, titleComparator);
            genreBST.insert(gameToChange, genreComparator);
            System.out.print("\n\nChange successful!\n");
        }
        performOption(obj, user, cuHashTable, empHashTable, titleBST, genreBST, vgFile);
    }

    /**
     * Displays the menu options and returns the user's choice.
     * @param obj The Scanner object to read user input.
     * @param user The user object that represents the current user
     * @return The user's menu choice as a String.
     */
    public static String options ( Scanner obj, User user){
        if(user instanceof Customer){
            System.out.print("Please select from the following options:\n\n" +
                    "A. Search A Video Game\n" +
                    "B. View All Video Games\n" +
                    "C. Order A Video Game\n" +
                    "D. View Purchased Video Games\n" +
                    "X. Exit\n\n" +
                    "Enter your choice: ");
        } else if (user instanceof Employee){
            Employee emp = (Employee) user;
            if ( emp.isManager()){
                System.out.print("Please select from the following options:\n\n" +
                        "A. Search A Customer's Order\n" +
                        "B. View Order With Highest Priority\n" +
                        "C. View All Orders Sorted by Priority\n" +
                        "D. Ship A Customer's Order\n" +
                        "E. Update The Video Game Database\n" +
                        "X. Exit\n\n" +
                        "Enter your choice: ");
            } else {
                System.out.print("Please select from the following options:\n\n" +
                        "A. Search A Customer's Order\n" +
                        "B. View Order With Highest Priority\n" +
                        "C. View All Orders Sorted by Priority\n" +
                        "D. Ship A Customer's Order\n" +
                        "X. Exit\n\n" +
                        "Enter your choice: ");
            }
        }
        return obj.next();
    }

    /**
     * Displays an error message when an invalid menu option is selected.
     * @param obj   The Scanner object to read user input.
     * @param user   The User object representing the current user.
     */
    public static void invalidChoice(Scanner obj, User user, HashTable<Customer> cuHashTable,  HashTable<Employee> empHashTable, BST<VideoGame> titleBST, BST<VideoGame> genreBST, File vgFile) throws IOException {
        if( user instanceof Customer){
            System.out.println("\nInvalid menu option. Please enter A-D or X to exit.\n");
            performOption(obj, user, cuHashTable, empHashTable, titleBST, genreBST, vgFile);
        } else if( user instanceof Employee){
            Employee emp = (Employee) user;
            if(emp.isManager()){
                System.out.println("\nInvalid menu option. Please enter A-E or X to exit.\n");
                performOption(obj, user, cuHashTable, empHashTable, titleBST, genreBST, vgFile);
            } else {
                System.out.println("\nInvalid menu option. Please enter A-D or X to exit.\n");
                performOption(obj, user, cuHashTable, empHashTable, titleBST, genreBST, vgFile);
            }
        }
    }

    /**
     * Handles the exit option, closing the scanner, writing to the file of the changes that occured in the databases and displaying a goodbye message.
     * @param obj The Scanner object to read user input.
     */
    public static void exitOptionX(Scanner obj, User user, HashTable<Employee> empHashTable, BST<VideoGame> videoGameDatabase_Title, HashTable<Customer> cuHashTable) throws IOException {
        System.out.print("\nGoodbye!");
        writeDataToFile("src/UpdatedEmp.txt", empHashTable.toString());
        writeDataToFile("src/VideoGameUpdated.txt", videoGameDatabase_Title.preOrderString());
        writeDataToFile("src/scuffedCustomers.txt", cuHashTable.toString());
        removeTrailingBlankLines("src/UpdatedEmp.txt");
        removeTrailingBlankLines("src/VideoGameUpdated.txt");
        removeTrailingBlankLines("src/scuffedCustomers.txt");
        obj.close();
    }


    /**
     * It writes a new file with given data
     * @parm a file name to write to,
     * @parm what data should be written in the file
     */
    public static void writeDataToFile(String filePath, String data) throws IOException {
        try (FileWriter file = new FileWriter(filePath);
             BufferedWriter buffer = new BufferedWriter(file);
             PrintWriter writer = new PrintWriter(buffer);) {
            writer.println(data);
        } catch (IOException error) {
            System.err.println("There was a problem writing to the file: " + filePath);
        }
    }

    public static void removeTrailingBlankLines(String filePath) throws IOException {
            List<String> lines = new ArrayList<>();
            boolean trailingEmpty = true;
            int lastNonEmpty = -1;

            // Read file and find last non-empty line
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                int index = 0;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                    if (!line.trim().isEmpty()) {
                        lastNonEmpty = index;
                    }
                    index++;
                }
            }

            // If file ends with non-empty line, no need to modify
            if (lastNonEmpty == lines.size() - 1) return;

            // Write back only up to last non-empty line
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (int i = 0; i <= lastNonEmpty + 1; i++) {
                    if (i > 0) writer.newLine();
                    if (i <= lastNonEmpty) {
                        writer.write(lines.get(i));
                    }
                }
            }
        }



    /**
     * Checks if a user account exists in the database of customer or employee based on the provided email and password.
     * @param email     The email address of the user.
     * @param password  The password of the user.
     * @param customers The HashTable containing all customer accounts.
     * @param employees The HashTable containing all employees and one manager.
     * @return The either a customer or employee if the account exists, otherwise null.
     */
    public static User doesAccountExist(String email, String password, HashTable<Customer> customers, HashTable<Employee> employees) {
        User user = null;
        Customer cus = new Customer(email, password);
        Employee emp = new Employee( email, password);

        if(  customers.get(cus) != null){
            user = customers.get(cus);
        } else if (  employees.get(emp) != null ){
            user =  employees.get(emp);
        }
        return user;
    }

    public static void fillCustomers( File file, HashTable<Customer> cuHashTable, BST<VideoGame> titleBST, ArrayList<Order> allUnShipped){
        try {
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                String name = input.nextLine();
                String email = input.next();
                String password = input.next();
                double cashBalance = input.nextDouble();
                int numOfShipped = input.nextInt();
                if(input.hasNextLine()){
                    input.nextLine();
                }

                ArrayList<Order> shippVideoGames = new ArrayList<>();
                ArrayList<Order> cuUnshipped = new ArrayList<>();

                for (int i = 0; i < numOfShipped; i++) {
                    String gameName = input.nextLine();

                    int sum = 0;
                    String key = name + email + password + gameName;
                    //String key = name;
                    for(int j = 0; j < key.length(); j++){
                        sum += key.charAt(j);
                    }
                    int orderID = sum;

                    int priorityNum = input.nextInt();
                    VideoGame videoGame = new VideoGame(gameName);
                    VideoGame game = titleBST.search(videoGame, titleComparator);
                    shippVideoGames.add(new Order(orderID, game, priorityNum));

                    if(input.hasNextLine()){
                        input.nextLine();
                    }
                }

                int numOfUnshipped = 0;
                if(input.hasNextLine()){
                    numOfUnshipped = input.nextInt();
                    if(input.hasNextLine()){
                        input.nextLine();
                    }
                }

                for (int i = 0; i < numOfUnshipped; i++) {
                    String gameName = input.nextLine();

                    int sum = 0;
                    String key = name + email + password + gameName;
                    // String key = name;
                    for(int j = 0; j < key.length(); j++){
                        sum += key.charAt(j);
                    }
                    int orderID = sum;

                    int priorityNum = input.nextInt();
                    VideoGame videoGame = new VideoGame(gameName);
                    VideoGame game = titleBST.search(videoGame, titleComparator);
                    cuUnshipped.add(new Order(orderID,game,priorityNum));

                    if(input.hasNextLine()){
                        input.nextLine();
                    }
                }
                boolean isGuest = input.nextBoolean();
                if(input.hasNextLine()){
                    input.nextLine();
                }


                int spaceLoc = name.indexOf(' ');
                String firstName = name.substring(0, spaceLoc);
                String lastName = name.substring(spaceLoc + 1);
                Customer cus = new Customer(firstName, lastName, email, password, cashBalance, numOfShipped, shippVideoGames, numOfUnshipped, cuUnshipped, isGuest);
                cuHashTable.add(cus);

                for (int i = 0; i < cuUnshipped.size(); i++) {
                    cuUnshipped.get(i).setCustomer(cus);
                    allUnShipped.add(cuUnshipped.get(i));
                }
                for (int i = 0; i < shippVideoGames.size(); i++) {
                    shippVideoGames.get(i).setCustomer(cus);
                }

                if(input.hasNextLine()){
                    input.nextLine();
                }
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void fillEmployee (File file, HashTable<Employee> empHashTable, BST<VideoGame> titleBST, BST<VideoGame> genreBST, HashTable<Customer> customers, ArrayList<Order> allUnShipped){
        try {
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                String name = input.nextLine();
                String email = input.next();
                String password = input.next();
                boolean isManager = input.nextBoolean();

                int spaceLoc = name.indexOf(' ');
                String firstName = name.substring(0, spaceLoc);
                String lastName = name.substring(spaceLoc + 1);
                empHashTable.add(new Employee(firstName, lastName, email, password, isManager, customers, titleBST, genreBST, allUnShipped));
                if(input.hasNextLine()){
                    input.nextLine();
                    if(input.hasNextLine()) {
                        input.nextLine();
                    }
                }
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void fillVideoGames (File file, BST<VideoGame> titleBST, BST<VideoGame> genreBST){
        try {
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                String title = input.nextLine();
                String priceInText = input.next();
                double price = 0;
                if(!(priceInText.equals("Free"))){
                    price = Double.parseDouble(priceInText);
                }
                input.nextLine();
                String gameDes = input.nextLine();
                int stock = input.nextInt();
                String ageRating = input.next();


                String genre = input.next();
                titleBST.insert(new VideoGame(title, price, gameDes, stock, ageRating, genre), titleComparator);
                genreBST.insert(new VideoGame(title, price, gameDes, stock, ageRating, genre), genreComparator);
                if(input.hasNextLine()){
                    input.nextLine();
                    if(input.hasNextLine()) {
                        input.nextLine();
                    }
                }
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
