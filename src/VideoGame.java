/**
 * VideoGame.java
 * @author Sheryl Lee
 * CIS 22C
 */

public class VideoGame{
    private String title;
    private double price;
    private String description;
    private int stock;
    private String ageRating;
    private String genre;

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
}