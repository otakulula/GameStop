public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    /**
     * Constructs a new User object with the given details.
     * 
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param email The email address of the user.
     * @param password The password for the user.
     */
    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    /**
     * Retrieves the first name of the user.
     * 
     * @return The first name of the user.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Retrieves the last name of the user.
     * 
     * @return The last name of the user.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Retrieves the email address of the user.
     * 
     * @return The email address of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Retrieves the password for the user.
     * 
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the first name of the user.
     * 
     * @param firstName The first name to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the last name of the user.
     * 
     * @param lastName The last name to set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets the email address for the user.
     * 
     * @param email The email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the password for the user.
     * 
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns a string representation of the user, including the first name, last name, and email.
     * 
     * @return A string containing the user's first name, last name, and email.
     */
    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + email + ")";
    }
}