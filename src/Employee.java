public class Employee extends User{

    private boolean isManager; 

    /**
     * Constructs an Employee object with the given details.
     * 
     * @param firstName The first name of the employee.
     * @param lastName The last name of the employee.
     * @param email The email address of the employee.
     * @param password The password for the employee.
     * @param isManager Indicates if the employee is a manager.
     */
    public Employee(String firstName, String lastName, String username, String password, boolean isManager) {
        super(firstName, lastName, username, password);
        this.isManager = isManager;
    }

    /**
     * Retrieves whether the employee is a manager.
     * 
     * @return true if the employee is a manager, false otherwise.
     */
    public boolean getManager() {
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


 

}
