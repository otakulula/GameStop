import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

import DataStructures.HashTable;

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
     // HashTable<VideoGames> gamesHashTable = new HashTable<>(NUM_VIDEO_GAMES);

        // method to fill video games into ... 
        fillCustomers(custFile, cuHashTable); // one more param (gamesHashTables)
        fillEmployee(empFile, empHashTable);
       

        Scanner obj = new Scanner(System.in);
        System.out.println("Welcome to Game Stop!");
       
    }


    public static void fillCustomers( File file, HashTable<Customer> cuHashTable){//another param to add game into cust list
        try {
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                String name = input.nextLine();
                String email = input.next();
                String password = input.next();
                double cashBalance = input.nextDouble();
                int numOfGames = input.nextInt();
                //here create a video game arraylist that holds all games cust owns
                for (int i = 0; i < numOfGames; i++) {
                    String gameName = input.nextLine();
                    // create a videogame object using gameName and add it to videogame arraylist;
                    input.nextLine();
                }
                int spaceLoc = name.indexOf(' ');
                String firstName = name.substring(0, spaceLoc);
                String lastName = name.substring(spaceLoc + 1);
              //  cuHashTable.add(new Customer()); create and add all things into cust object and push into hashtable
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
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } 
    }




}
