import DataStructures.BST;
import DataStructures.HashTable;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

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
    
        public static void main(String[] args) throws Exception {
            final int NUM_CUSTOMERS = 100;
            final int NUM_EMPLOYEE = 4;
            
            File custFile = new File("DataBases/customers.txt");
            File empFile = new File("DataBases/employees.txt");
            File vgFile = new File("DataBases/videoGames.txt");
    
            HashTable<Customer> cuHashTable = new HashTable<>(NUM_CUSTOMERS);
            HashTable<Employee> empHashTable = new HashTable<>(NUM_EMPLOYEE * 3);
            ArrayList<Order> allUnshippedOrders = new ArrayList<>();
            BST<VideoGame> videoGameDatabase_Title = new BST<>();
            BST<VideoGame> videoGameDatabase_Genre = new BST<>();
    
            fillVideoGames(vgFile ,videoGameDatabase_Title, videoGameDatabase_Genre);
           // fillCustomers(custFile, cuHashTable, videoGameDatabase_Title, allUnshippedOrders);
            fillEmployee(empFile, empHashTable, videoGameDatabase_Title, cuHashTable, allUnshippedOrders);
    
            Scanner obj = new Scanner(System.in);
            System.out.println("Welcome to Game Stop!");
            System.out.print("Please enter your email address: ");
            String email = obj.next();
            System.out.print("Please enter your password: ");
            String password = obj.next();
            User isAccReal = doesAccountExist(email, password, cuHashTable, empHashTable);
    
            if(isAccReal instanceof Customer){
                System.out.print("\nWelcome, " + isAccReal.getFirstName() + " " + isAccReal.getLastName() + "!\n\n\n");
                performOption(obj, isAccReal, videoGameDatabase_Title, videoGameDatabase_Genre);
            } else if ( isAccReal instanceof Employee) {
                System.out.print("\nWelcome, " + isAccReal.getFirstName() + " " + isAccReal.getLastName() + "!\n\n\n");
                performOption(obj, isAccReal, videoGameDatabase_Title, videoGameDatabase_Genre);
            } else {
                System.out.print("\nWe don't have your account on file...\n\n" +
                "To remain as Guest type 1 or To create an account type 2: ");
                int answer = obj.nextInt();
    
                while (answer != 1 && answer != 2 ){ 
                    System.out.print("\nInvalid answer\n" + "Enter 1 for Guest or 2 to create account: ");
                    answer = obj.nextInt();
                }
    
                if( answer == 1){
                  User guest = new Customer(email, password, true); // or should just use isAccReal and create it?
                  System.out.println();
                  System.out.print("Welcome, Guest user!\n\n\n");
                  performOption(obj, guest, videoGameDatabase_Title, videoGameDatabase_Genre);
                } else {
                    System.out.print("Enter your first name: ");
                    String firstName = obj.next();
                    System.out.print("Enter your last name: ");
                    String lastName = obj.next();
                  //  User newCus = new Customer(firstName, lastName, email, password, false);  // or should just use isAccReal and create it?
                    System.out.println();
                 //   System.out.print("Welcome, " + newCus.getFirstName() + " " + newCus.getLastName() + "!\n\n\n");
                 //   performOption(obj, newCus);
                }
            }
        }
    
         /**
         * Displays the menu options and performs the selected operation based on user input.
         *
         * @param obj   The Scanner object to read user input.
         * @param user   The User object representing the current user.
         */
        public static void performOption (Scanner obj, User user, BST<VideoGame> titleBST, BST<VideoGame> genreBST){ // more params to come..
            String choice = options(obj, user);
            String c = choice.toUpperCase();
            
          if( user instanceof Customer) { // C and D,  have a check to check for guest and limit their use
              if(c.equals("A")){
                searchAGame(obj, user, titleBST, genreBST);
            } else if (c.equals("B")){
                viewAllGames(obj, user, titleBST, genreBST);
            } else if (c.equals("C")){
                // customer's option C
            } else if (c.equals("D")){
                // customer's option D
            } else if ( c.equals("X")){
                exitOptionX(obj);
            } else {
                invalidChoice(obj, user, titleBST, genreBST);
            }
         } else if( user instanceof Employee ) { 
            Employee emp = (Employee) user;
            if( emp.isManager()){
                if(c.equals("A")){
                    // manager's option A
                } else if (c.equals("B")){
                    // manager's option B
                } else if (c.equals("C")){
                   // manager's option C
                } else if (c.equals("D")){
                    // manager's option D
                } else if ( c.equals("E")){
                    // manager's option E
                } else if ( c.equals("X")) {
                    exitOptionX(obj);
                } else{
                    invalidChoice(obj, user, titleBST, genreBST);
                }
            } else {
                if(c.equals("A")){
                    // employee's option A
                } else if (c.equals("B")){
                    // employee's option B
                } else if (c.equals("C")){
                    // employee's option C
                } else if (c.equals("D")){
                    // employee's option D
                } else if ( c.equals("X")){
                    exitOptionX(obj);
                } else {
                    invalidChoice(obj, user, titleBST, genreBST);
                }
            }
           }
        }
    

        public static void searchAGame ( Scanner obj, User user, BST<VideoGame> titleBST, BST<VideoGame> genreBST){
            System.out.print("\nDo you want to search by Title (type 1) or genre (type 2): ");
            int answer = obj.nextInt();

            if( answer == 1){ //title
                System.out.print("\nType the Title of the video game: ");
               obj.nextLine();
               String gameName = obj.nextLine();
                VideoGame videoGame = new VideoGame(gameName);
                VideoGame game = titleBST.search(videoGame, titleComparator);
                System.out.println();
                System.out.print(game);
            } else {// genre
                System.out.print("\nType the genre: ");

            }
            performOption(obj, user, titleBST, genreBST);
        }

        public static void viewAllGames( Scanner obj, User user, BST<VideoGame> titleBST, BST<VideoGame> genreBST ){
            System.out.print(titleBST.inOrderString());
            performOption(obj, user, titleBST, genreBST);
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
                    "A. Search A Video Game\n" + // happening in interface
                    "B. View All Video Games\n" + // happening in interface
                    "C. Order A Video Game\n" + // happening customer class ( GUEST CANNOT , make them create an accoutn)
                    "D. View Purchased Video Games\n" + // see both shipped and unshipped and happening in customer ( guest cannot)
                    "X. Exit\n\n" +
                    "Enter your choice: ");
            } else if (user instanceof Employee){ 
                Employee emp = (Employee) user;
                if ( emp.isManager()){
                    System.out.print("Please select from the following options:\n\n" +
                    "A. Search A Customer's Order\n" +
                    "B. View Order With Highest Priority\n" +
                    "C. View All Orders Sorted by Priority\n" + // show all customers organzied by priority
                    "D. Ship A Customer's Order\n" +
                    "E. Update The Video Game Database" + // like add a new game, remove a game, or update game's info
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
        public static void invalidChoice(Scanner obj, User user, BST<VideoGame> titleBST, BST<VideoGame> genreBST) { // more param to come...
           if( user instanceof Customer){
                System.out.println("\nInvalid menu option. Please enter A-D or X to exit.\n");
                performOption(obj, user, titleBST, genreBST);
           } else if( user instanceof Employee){ 
            Employee emp = (Employee) user;
            if(emp.isManager()){ 
                System.out.println("\nInvalid menu option. Please enter A-E or X to exit.\n");
                performOption(obj, user, titleBST, genreBST);
            } else {
            System.out.println("\nInvalid menu option. Please enter A-D or X to exit.\n");
            performOption(obj, user, titleBST, genreBST);
            }
        }
     }
    
         /**
         * Handles the exit option, closing the scanner, writing to the file of the changes that occured in the databases and displaying a goodbye message.
         * @param obj The Scanner object to read user input.
         */
        public static void exitOptionX(Scanner obj){
            System.out.print("\nGoodbye!");
            // code that will write to files of the changes that occured throughout the program
            obj.close();
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
                user =  employees.get(emp); // right now not wroking becuase no hashcode in customer and employee class AND NEED equal method to be overrideen
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
                       // String key = name;
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
                        input.nextLine();
                    }
                   
                    for (int i = 0; i < numOfUnshipped; i++) {
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
                        cuUnshipped.add(new Order(orderID,game,priorityNum)); 
                        allUnShipped.add(new Order(orderID, game, priorityNum));
    
                       if(input.hasNextLine()){
                        input.nextLine();
                    }
                 }
    
                    int spaceLoc = name.indexOf(' ');
                    String firstName = name.substring(0, spaceLoc);
                    String lastName = name.substring(spaceLoc + 1);
                    Customer cus = new Customer(firstName, lastName, email, password, cashBalance, numOfShipped, shippVideoGames, numOfUnshipped, cuUnshipped );
                     cuHashTable.add(cus);
    
                    for (int i = 0; i < cuUnshipped.size(); i++) {
                        cuUnshipped.get(i).setCustomer(cus);
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
    
        public static void fillEmployee (File file, HashTable<Employee> empHashTable, BST<VideoGame> titleBST,  HashTable<Customer> customers, ArrayList<Order> allUnShipped){
            try {
                Scanner input = new Scanner(file);
                while (input.hasNextLine()) {
                    String name = input.nextLine();
                    String email = input.next();
                    String password = input.next();
                    int empID = input.nextInt();
                    boolean isManager = input.nextBoolean();
                
                    int spaceLoc = name.indexOf(' ');
                    String firstName = name.substring(0, spaceLoc);
                    String lastName = name.substring(spaceLoc + 1);
                 //   empHashTable.add(new Employee(firstName, lastName, email, password, isManager, customers, titleBST, allUnShipped)); 
                    if(input.hasNextLine()){
                        input.nextLine();
                        input.nextLine();
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
                    int ageLimit = 0;
                    if(ageRating.contains("+")){
                        int plusIndex = ageRating.indexOf("+");
                        ageLimit = Integer.parseInt(ageRating.substring(0, plusIndex));
                    } else {
                        ageLimit = Integer.parseInt(ageRating);
                    }
                 
                    String genre = input.next();
                    titleBST.insert(new VideoGame(title, price, gameDes, stock, Integer.toString(ageLimit), genre), titleComparator);
                    genreBST.insert(new VideoGame(title, price, gameDes, stock, Integer.toString(ageLimit), genre), genreComparator);
                if(input.hasNextLine()){
                    input.nextLine();
                    input.nextLine();
                }
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
