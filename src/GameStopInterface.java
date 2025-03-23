import DataStructures.HashTable;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

/*
 * This comparator will compare Integer objects using compare().
 */
class IntComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer a, Integer b) {
        int result = 0;
        if( a.equals(b)){
            return result;
        } else if ( a.compareTo(b) > 0){
            result = 1;
        }else{
            result = -1;
        }
        return result;
    }
}

/*
 * This comparator will compare String objects using compare().
 */
class StrComparator implements Comparator<String> {
    @Override
    public int compare(String a, String b) {
        int result = 0;
        if (a.equals(b)) {
            return result;
        } else if ( a.compareTo(b) > 0 ){
            result = 1;
        } else {
            result = -1;
        }
        return result;
    }
}

public class GameStopInterface {

    private StrComparator strCmp = new StrComparator();
    private IntComparator intCmp = new IntComparator();

    public static void main(String[] args) throws Exception {
        final int NUM_CUSTOMERS = 100;
        final int NUM_EMPLOYEE = 4;
        final int NUM_VIDEO_GAMES = 50;
        
        File custFile = new File("DataBases/customers.txt");
        File empFile = new File("DataBases/employees.txt");
        File vgFile = new File("DataBases/videoGames.txt");

        HashTable<Customer> cuHashTable = new HashTable<>(NUM_CUSTOMERS);
        HashTable<Employee> empHashTable = new HashTable<>(NUM_EMPLOYEE * 3);
        HashTable<VideoGame> gamesHashTable = new HashTable<>(NUM_VIDEO_GAMES);

        fillVideoGames(vgFile ,gamesHashTable);
        fillCustomers(custFile, cuHashTable, gamesHashTable);
       //fillEmployee(empFile, empHashTable);gm

       
        Scanner obj = new Scanner(System.in);
        System.out.println("Welcome to Game Stop!");
        System.out.print("Please enter your email address: ");
        String email = obj.next();
        System.out.print("Please enter your password: ");
        String password = obj.next();
        User isAccReal = doesAccountExist(email, password, cuHashTable, empHashTable);

        if(isAccReal instanceof Customer){
            System.out.print("\nWelcome, " + isAccReal.getFirstName() + " " + isAccReal.getLastName() + "!\n\n\n");
            performOption(obj, isAccReal);
        } else if ( isAccReal instanceof Employee) {
            System.out.print("\nWelcome, " + isAccReal.getFirstName() + " " + isAccReal.getLastName() + "!\n\n\n");
            performOption(obj, isAccReal);
        } else {
            System.out.print("\nWe don't have your account on file...\n\n" +
            "Let's create an account for you!\n");
            System.out.print("Enter your first name: ");
            String firstName = obj.next();
            System.out.print("Enter your last name: ");
            String lastName = obj.next();
            User newCus = new Customer(firstName, lastName, email, password);
            System.out.println();
            System.out.print("Welcome, " + newCus.getFirstName() + " " + newCus.getLastName() + "!\n\n\n");
            performOption(obj, newCus);
        }
    }

     /**
     * Displays the menu options and performs the selected operation based on user input.
     *
     * @param obj   The Scanner object to read user input.
     * @param user   The User object representing the current user.
     */
    public static void performOption (Scanner obj, User user){ // more params to come..
        String choice = options(obj, user);
        String c = choice.toUpperCase();
        
      if( user instanceof Customer) {
          if(c.equals("A")){
           // customer's option A
        } else if (c.equals("B")){
           // customer's option B
        } else if (c.equals("C")){
            // customer's option C
        } else if (c.equals("D")){
            // customer's option D
        } else if ( c.equals("X")){
            exitOptionX(obj);
        } else {
            invalidChoice(obj, user);
        }
        } else if( user instanceof Employee) { // and check manager status isAccReal.isManger == true
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
                invalidChoice(obj, user);
            }
       }  else if ( user instanceof Employee){
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
                invalidChoice(obj, user);
            }
       }
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
        } else if (user instanceof Employee){ // and check manager status isAccReal.isManger == true
            System.out.print("Please select from the following options:\n\n" +
            "A. Search A Customer's Order\n" +
            "B. View Order With Highest Priority\n" +
            "C. View All Orders Sorted by Priority\n" +
            "D. Ship A Customer's Order\n" +
            "E. Update The Video Game Database" +
            "X. Exit\n\n" +
            "Enter your choice: ");
        } else if (user instanceof Employee){
            System.out.print("Please select from the following options:\n\n" +
            "A. Search A Customer's Order\n" +
            "B. View Order With Highest Priority\n" +
            "C. View All Orders Sorted by Priority\n" +
            "D. Ship A Customer's Order\n" +
            "X. Exit\n\n" +
            "Enter your choice: ");
        }
        return obj.next();
    }

      /**
     * Displays an error message when an invalid menu option is selected.
     * @param obj   The Scanner object to read user input.
     * @param user   The User object representing the current user.
     */
    public static void invalidChoice(Scanner obj, User user) { // more param to come...
       if( user instanceof Customer){
            System.out.println("\nInvalid menu option. Please enter A-D or X to exit.\n");
            performOption(obj, user);
       } else if( user instanceof Employee){ //and check manager status isAccReal.isManger == true
        System.out.println("\nInvalid menu option. Please enter A-E or X to exit.\n");
        performOption(obj, user);
      } else if ( user instanceof Employee){
        System.out.println("\nInvalid menu option. Please enter A-D or X to exit.\n");
        performOption(obj, user);
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
       // Customer cus = new Customer(email, password);
        //Employee emp = new Employee( email, password);

    //   if(  customers.get(cus) != null){
       //     user = customers.get(cus);
    //   } else if (  employees.get(emp) != null ){
     //  user =  employees.get(emp);
   // }
      //User emp = new Employee("g", "g", "g", "pw", true); --> just used it to test stuff
      return user;
    }

    public static void fillCustomers( File file, HashTable<Customer> cuHashTable, HashTable<VideoGame> vidHashTable){       
        try {
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                String name = input.nextLine();
                String email = input.next();
                String password = input.next();
                double cashBalance = input.nextDouble();
                int numOfGames = input.nextInt();
                if(input.hasNextLine()){
                    input.nextLine();
                }
                ArrayList<VideoGame> vidgameList = new ArrayList<>();
                //here create a video game arraylist that holds all games cust owns
                for (int i = 0; i < numOfGames; i++) {
                    String gameName = input.nextLine();
                    VideoGame videoGame = new VideoGame(gameName);
                    VideoGame game = vidHashTable.get(videoGame);
                    vidgameList.add(game);
                }
                int spaceLoc = name.indexOf(' ');
                String firstName = name.substring(0, spaceLoc);
                String lastName = name.substring(spaceLoc + 1);
              //cuHashTable.add(new Customer()); create and add all things into cust object and push into hashtable
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

    public static void fillEmployee (File file, HashTable<Employee> empHashTable){
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
                // empHashTable.add(new Employee()); create and add all things into emp object and push into hashtable
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

    public static void fillVideoGames (File file, HashTable<VideoGame> vidHashTable){
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
                vidHashTable.add(new VideoGame(title, price, gameDes, stock, Integer.toString(ageLimit), genre));
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
