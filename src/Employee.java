public class Employee extends User{

    private boolean isManager; 
    private Queue<Order> unshippedOrders;
    private LinkedList<Order> shippedOrders;  

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
        this.unshippedOrders = new Queue<>();
        this.shippedOrders = new LinkedList<>();
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

    /**
     * Search for an order by ID.
     * @param orderId is the ID to be searched
     * @return order we are searching for
     */
    public Order searchOrderById(int orderID) {
        Queue<Order> temp = new Queue<>(unshippedOrders);
        while (!temp.isEmpty()) {
            Order order = temp.getFront();
            if (order.getID() == orderId) {
                return order;
            }
            else{
                temp.dequeue();
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
        Queue<Order> temp = new Queue<>(unshippedOrders);
        while (temp.isEmpty()) {
            Order order = temp.getFront();
            if (order.getCustomer().getFirstName().equals(firstName) &&
                order.getCustomer().getLastName().equals(lastName)) {
                return order;
            }
            else{
                temp.dequeue();
            }
        }
        return null;
    }
    /**
     * View the next order in the queue 
     * @return the next order in the queue
     */

    public Order viewHighestPriorityOrder() {
        return unshippedOrders.getFront();
    }

    /**
     * View all orders (heap sort?)
     */
    public void viewAllOrders() {

    }


 

}
