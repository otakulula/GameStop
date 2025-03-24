/**
 * VideoGame.java
 * @author Sheryl Lee
 * CIS 22C
 */

import DataStructures.BST;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;

public class VideoGame{
    private String title;
    private double price;
    private String description;
    private int stock;
    private String ageRating;
    private String genre;

    private static BST<VideoGame> gamesByTitle = new BST<>();
    private static BST<VideoGame> gamesByGenre = new BST<>();

    private static Comparator<VideoGame> titleComparator = new TitleComparator();
    private static Comparator<VideoGame> genreComparator = new GenreComparator();

    /**CONSTRUCTORS */

    /**
     * Full-argument constructor for VideoGame.
     * @param title the title of the video game
     * @param price the price of the video game
     * @param description the description of the video game
     * @param stock the stock of the video game
     * @param ageRating the age rating of the video game
     * @param genre the gemre of the video game
     */
    public VideoGame(String title, double price, String description, int stock, String ageRating, String genre){
        this.title = title;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.ageRating = ageRating;
        this.genre = genre;
    }

    /**
     * One-argument constructor, when only the title is known.
     * @param title the title of the video game
     * and assigns 0 to price and stock, and
     * empty string to description, ageRating, and genre.
     */
    public VideoGame(String title){
        this.title = title;
        this.price = 0;
        this.description = "";
        this.stock = 0;
        this.ageRating = "";
        this.genre = "";
    }

    /**ACCESSORS */

    /**
     * Accesses the title of the video game.
     * @return the title of the video game
     */
    public String getTitle(){
        return title;
    }

    /**
     * Accesses the price of the video game.
     * @return the price of the video game
     */
    public double getPrice(){
        return price;
    }

    /**
     * Accesses the description of the video game.
     * @return the description of the video game
     */
    public String getDescription(){
        return description;
    }

    /**
     * Accesses the stock of the video game.
     * @return the stock of the video game
     */
    public int getStock(){
        return stock;
    }

    /**
     * Accesses the age rating of the video game.
     * @return the age rating of the video game
     */
    public String getAgeRating(){
        return ageRating;
    }

    /**
     * Accesses the genre of the video game.
     * @return the genre of the video game
     */
    public String getGenre(){
        return genre;
    }

    /**MUTATORS */

    /**
     * Adds the title and genre of the video game to the BSTs.
     * @param game the video game to add
     */
    public static void addGame(VideoGame game){
        gamesByTitle.insert(game, titleComparator);
        gamesByGenre.insert(game, genreComparator);
    }

    /**
     * Removes the title and genre of the video game from the BSTs.
     * @param game the video game to remove
     */
    public static void removeGame(VideoGame game){
        gamesByTitle.remove(game, titleComparator);
        gamesByGenre.remove(game, genreComparator);
    }

    /**
     * Searches for a video game by title.
     * @param title the title of the video game to search for
     */
    public static VideoGame searchByTitle(String title){
        VideoGame game = new VideoGame(title);
        return gamesByTitle.search(game, titleComparator);
    }

    /**
     * Searches for a video game by genre.
     * @param genre the genre of the video game to search for
     */
    public static VideoGame searchByGenre(String genre){
        VideoGame game = new VideoGame("", 0, "", 0, "", genre);
        return gamesByGenre.search(game, genreComparator);
    }

    /**
     * Sets the title of the video game.
     * @param title the title of the video game
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * Sets the price of the video game.
     * @param price the price of the video game
     */
    public void setPrice(double price){
        this.price = price;
    }

    /**
     * Sets the description of the video game.
     * @param description the description of the video game
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * Sets the stock of the video game.
     * @param stock the stock of the video game
     */
    public void setStock(int stock){
        this.stock = stock;
    }

    /**
     * Sets the age rating of the video game.
     * @param ageRating the age rating of the video game
     */
    public void setAgeRating(String ageRating){
        this.ageRating = ageRating;
    }

    /**
     * Sets the genre of the video game.
     * @param genre the genre of the video game
     */
    public void setGenre(String genre){
        this.genre = genre;
    }

    /**ADDITIONAL METHODS */

    /**
     * Compares two VideoGame objects by title.
     */
    private static class TitleComparator implements Comparator<VideoGame>{
        /**
         * Compares two VideoGame objects by title.
         * @param game1 the first VideoGame
         * @param game2 the second VideoGame
         * @return 0 if the titles are the same, a positive number if the first title comes after the second title,
         * and a negative number if the first title comes before the second title
         */
        public int compare(VideoGame game1, VideoGame game2){
            return game1.title.compareTo(game2.title);
        }
    }

    /**
     * Compares two VideoGame objects by genre.
     */
    private static class GenreComparator implements Comparator<VideoGame>{
        /**
         * Compares two VideoGame objects by genre.
         * @param game1 the first VideoGame
         * @param game2 the second VideoGame
         * @return 0 if the genres are the same, a positive number if the first genre comes after the second genre,
         * and a negative number if the first genre comes before the second genre
         */
        public int compare(VideoGame game1, VideoGame game2){
            return game1.genre.compareTo(game2.genre);
        }
    }

    /**
     * Increases the stock of the video game by a given amount.
     * @param amount the amount to increase the stock by
     */
    public void increaseStock(int amount){
        stock += amount;
    }

    /**
     * Decreases the stock of the video game by a given amount.
     * @param amount the amount to decrease the stock by
     */
    public void decreaseStock(int amount){
        stock -= amount;
    }

    /**
     * Returns a string representation of the video game.
     * @return the string representation of the video game
     */
    @Override
    public String toString(){
        return title + "\nPrice: $" + price + "\nDescription: " + description + "\nStock: " 
        + stock + "\nAge Rating: " + ageRating + "\nGenre: " + genre;
    }

    /**
     * Returns a consistent hash code for each VideoGame 
     * by summing the Unicode values of each character in the key
     * Key = title
     * @return the hash code
     */
    @Override
    public int hashCode(){
        int sum = 0;
        for(int i = 0; i < title.length(); i++){
            sum += (int)title.charAt(i);
        }
        return sum;
    }

    /**
     * Compares two VideoGame objects for equality.
     * They are equal if the title is the same.
     * @param o the other VideoGame to compare to
     * @return true if the VideoGames are equal, false otherwise
     */
    @Override
    public boolean equals(Object o){
        if(o == this){
            return true;
        } else if(!(o instanceof VideoGame)){
            return false;
        } else {
            VideoGame game = (VideoGame)o;
            return title.equals(game.title);
        }
    }

    /**
     * Converts the VideoGame object to a string for writing to a file
     */
    public String toFileString(){
        return title + "\n" + price + "\n" + description + "\n" + stock + "\n" + ageRating + "\n" + genre + "\n";
    }

    /**
     * Writes the VideoGame object to a file
     * @param filename the name of the file
     */
    public void writeToFile(String fileName){
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(fileName, true));
            writer.println(this.toFileString().trim());
            writer.println(); 
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing game to file: " + fileName);
            e.printStackTrace();
        }
    }
}