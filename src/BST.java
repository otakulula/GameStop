import java.util.Comparator;
import java.util.NoSuchElementException;
 
 public class BST<T> {
     private class Node {
         private T data;
         private Node left;
         private Node right;
 
         public Node(T data) {
             this.data = data;
             this.left = null;
             this.right = null;
         }
     }
 
     private Node root;
 
     /***CONSTRUCTORS***/
 
     /**
      * Default constructor for BST sets root to null.
      */
     public BST() {
         this.root = null;
     }
 
     /**
      * Copy constructor for BST.
      * @param bst the BST of which to make a copy.
      * @param cmp the way the tree is organized.
      */
     public BST(BST<T> bst, Comparator<T> cmp) {
         if(bst == null){
             this.root = null;
         } else {
             copyHelper(bst.root, cmp);
         }
     }
 
     /**
      * Helper method for copy constructor.
      * @param node the node containing data to copy.
      * @param cmp the way the tree is organized.
      */
     private void copyHelper(Node node, Comparator<T> cmp) {
         if( node == null){
             return;
         } else {
             insert(node.data, cmp);
             copyHelper(node.left, cmp);
             copyHelper(node.right, cmp);
         }
     }
 
     /**
      * Creates a BST of minimal height from an array of values.
      * @param array the list of values to insert.
      * @param cmp the way the tree is organized.
      * @precondition array must be sorted in ascending order.
      * @throws IllegalArgumentException when the array is unsorted.
      */
     public BST(T[] array, Comparator<T> cmp) throws IllegalArgumentException {
         if(!isSorted(array, cmp)){
             throw new IllegalArgumentException("BST: the array is not sorted");
         } else {
             if (array != null) {
                 this.root = arrayHelper(0, array.length - 1, array);
             }
         }
         // fill in here
     }
 
     /**
      * Private helper method for array constructor
      * to check for a sorted array.
      * @param array the array to check.
      * @param cmp the way the tree is organized.
      * @return whether the array is sorted.
      */
     private boolean isSorted(T[] array, Comparator<T> cmp) {
         if (array != null) {
             for (int i = 0; i < array.length - 1; i++) {
                 if (cmp.compare(array[i], array[i + 1]) >= 0) {
                     return false;
                 }
             }
         }
         return true;
     }
 
     /**
      * Recursive helper for the array constructor.
      * @param begin beginning array index.
      * @param end ending array index.
      * @param array array to search.
      * @return the newly created Node.
      */
     private Node arrayHelper(int begin, int end, T[] array) {
         if( begin > end){
             return null;
         }
         int mid = (end + begin)/ 2;
         Node newNode = new Node(array[mid]);
 
         newNode.left = arrayHelper(begin, mid - 1, array);
         newNode.right = arrayHelper(mid + 1, end, array);
 
         return newNode;
     }
 
     /***ACCESSORS***/
 
     /**
      * Returns the data stored in the root.
      * @precondition !isEmpty()
      * @return the data stored in the root.
      * @throws NoSuchElementException when precondition is violated.
      */
     public T getRoot() throws NoSuchElementException {
         if(isEmpty()){
             throw new NoSuchElementException("getRoot: the root is empty");
         }
         return this.root.data;
     }
 
     /**
      * Determines whether the tree is empty.
      * @return whether the tree is empty.
      */
     public boolean isEmpty() {
         return root == null;
     }
 
     /**
      * Returns the current size of the tree (number of nodes).
      * @return the size of the tree.
      */
     public int getSize() {
         int size = 0;
         if(isEmpty()){
             return size;
         } else {
             size = getSize(root);
         }
         return size;
     }
 
     /**
      * Helper method for the getSize method.
      * @param node the current node to count.
      * @return the size of the tree.
      */
     private int getSize(Node node) {
         int size = 1;
         if(node.left == null && node.right == null) {
             return size;
         } else if ( node.left != null && node.right != null){
             size = 1 + getSize(node.left) + getSize(node.right);
         } else if ( node.left == null){
             size = 1 + getSize(node.right);
         } else {
             size = 1 + getSize(node.left);
         }
         return size;
     }
 
     /**
      * Returns the height of tree by counting edges.
      * @return the height of the tree.
      */
     public int getHeight() {
         int height = -1;
         if(isEmpty()){
             return height;
         } else {
             height = getHeight(root);
         }
         return height;
     }
 
     /**
      * Helper method for getHeight method.
      * @param node the current node whose height to count.
      * @return the height of the tree.
      */
     private int getHeight(Node node) {
         int height = 0;
         if(node.left == null && node.right == null){
             return height;
         } else if( node.left == null){
             height = 1 + getHeight(node.right);
         } else {
             height = 1 + getHeight(node.left);
         }
         return height;
     }
 
     /**
      * Returns the smallest value in the tree.
      * @precondition !isEmpty()
      * @return the smallest value in the tree.
      * @throws NoSuchElementException when the precondition is violated.
      */
     public T findMin() throws NoSuchElementException {
         if(isEmpty()){
             throw new NoSuchElementException("findMin: the BST is empty");
         }
         return findMin(root.left);
     }
 
     /**
      * Recursive helper method to findMin method.
      * @param node the current node to check if it is the smallest.
      * @return the smallest value in the tree.
      */
     private T findMin(Node node) {
         if (node.left != null) {
             return findMin(node.left);
         } else {
             return node.data;
         }
     }
 
     /**
      * Returns the largest value in the tree
      * @precondition !isEmpty()
      * @return the largest value in the tree.
      * @throws NoSuchElementException when the precondition is violated.
      */
     public T findMax() throws NoSuchElementException {
         if(isEmpty()){
             throw new NoSuchElementException("findMax: the BST is empty");
         }
         return findMax(root.right);
     }
 
     /**
      * Recursive helper method to findMax method.
      * @param node the current node to check if it is the largest.
      * @return the largest value in the tree.
      */
     private T findMax(Node node) {
         if (node.right != null) {
             return findMax(node.right);
         } else {
             return node.data;
         }
     }
 
     /**
      * Searches for a specified value in the tree.
      * @param data the value to search for.
      * @param cmp the Comparator that indicates the way
      * the data in the tree was ordered.
      * @return the data stored in that Node of the tree, otherwise null.
      */
     public T search(T data, Comparator<T> cmp) {
         if(isEmpty()){
             return null;
         } else {
             return search(data, root, cmp);
         }
     }
 
     /**
      * Helper method for the search method.
      * @param data the data to search for.
      * @param node the current node to check.
      * @param cmp the Comparator that determines how the BST is organized.
      * @return the data stored in that Node of the tree, otherwise null.
      */
     private T search(T data, Node node, Comparator<T> cmp) {
         T result;
         if(cmp.compare(data, node.data) == 0){
             return node.data;
         } else if( cmp.compare(data, node.data) <= -1 ){
             if(node.left == null) {
                 return null;
             } else {
                 result = search(data, node.left, cmp);
             }
         } else{
             if(node.right == null){
                 return null;
             } else {
                 result = search(data, node.right, cmp);
             }
         }
         return result;
     }
 
     /***MUTATORS***/
 
     /**
      * Inserts a new node in the tree.
      * @param data the data to insert.
      * @param cmp the Comparator indicating how data in the tree is ordered.
      */
     public void insert(T data, Comparator<T> cmp) {
         if(isEmpty()){
             this.root = new Node(data);
         } else {
             insert(data, root, cmp);
         }
     }
 
     /**
      * Helper method to insert.
      * Inserts a new value in the tree.
      * @param data the data to insert.
      * @param node the current node in the search for the correct insert
      *     location.
      * @param cmp the Comparator indicating how data in the tree is ordered.
      */
     private void insert(T data, Node node, Comparator<T> cmp) {
         if (cmp.compare(data, node.data) <= -1) {
             if (node.left == null ) {
                 node.left = new Node(data);
             } else {
                 insert(data, node.left, cmp);
             }
         } else {
             if (node.right == null) {
                 node.right = new Node(data);
             } else {
                 insert(data, node.right, cmp);
             }
         }
     }
 
     /**
      * Removes a value from the BST
      * @param data the value to remove
      * @param cmp the Comparator indicating how data in the tree is organized.
      * Note: updates nothing when the element is not in the tree.
      */
     public void remove(T data, Comparator<T> cmp) {
         this.root = remove(data, root, cmp);
     }
 
     /**
      * Helper method to the remove method.
      * @param data the data to remove.
      * @param node the current node.
      * @param cmp the Comparator indicating how data in the tree is organized.
      * @return an updated reference variable.
      */
     private Node remove(T data, Node node, Comparator<T> cmp) {
         if(node == null){
             return node;
         } else if ( cmp.compare(data, node.data) <= -1){
             node.left = remove(data, node.left, cmp);
         } else if ( cmp.compare(data, node.data) > 0 ){
             node.right = remove(data, node.right, cmp);
         } else {
             if( node.left == null && node.right == null){ // leaf , case 1; easy
                 node = null;
             } else if ( node.right == null && node.left != null){  // medium
                 node = node.left;
             } else if ( node.left == null && node.right != null){  // medium
                 node = node.right;
             } else {  // hard case
                 T minValue = findMin(node.right);
                 node.data = minValue;
                 node.right = remove(minValue, node.right, cmp);
             }
         }
         return node;
     }
 
     /***ADDITONAL OPERATIONS***/
 
     /**
      * Returns a String containing the data in pre order
      * followed by a new line.
      * @return a String of data in pre order.
      */
     public String preOrderString() {
         StringBuilder result = new StringBuilder("");
         if(this.isEmpty()){
             return "\n";
         } else {
             preOrderString(this.root, result);
         }
         return result.toString() + "\n";
     }
 
     /**
      * Helper method to preOrderString method.
      * Prints the data in pre order to the console followed by a new line.
      * @param node the current Node
      * @param preOrder a StringBuilder containing the data
      */
     private void preOrderString(Node node, StringBuilder preOrder) {
         if(node == null){
             return;
         } else{
             preOrder.append(node.data + " ");
             preOrderString(node.left, preOrder);
             preOrderString(node.right, preOrder);
         }
     }
 
     /**
      * Returns a String containing the data in order followed by a new line.
      * @return a String of data in order
      */
     public String inOrderString() {
         StringBuilder result = new StringBuilder("");
         if(this.isEmpty()){
             return "";
         } else {
             inOrderString(this.root, result);
         }
         return result.toString();
     }
 
     /**
      * Helper method to inOrderString.
      * Inserts the data in order into a String in order.
      * @param node the current Node
      * @param inOrder a String containing the data
      */
     private void inOrderString(Node node, StringBuilder inOrder) {
         if(node == null){
             return;
         } else {
             inOrderString(node.left, inOrder);
             inOrder.append(node.data + "\n");
             inOrderString(node.right, inOrder);
         }
     }
 
     /**
      * Returns a String containing the data in post order.
      * @return a String of data in post order
      */
     public String postOrderString() {
         StringBuilder result = new StringBuilder("");
         if(this.isEmpty()){
             return "\n";
         } else {
             postOrderString(this.root, result);
         }
         return result.toString() + "\n";
     }
 
     /**
      * Helper method to postOrderString
      * Inserts the data in post order into a String
      * @param node the current Node
      * @param postOrder a String containing the data
      */
     private void postOrderString(Node node, StringBuilder postOrder) {
         if(node == null){
             return;
         } else {
             postOrderString(node.left, postOrder);
             postOrderString(node.right, postOrder);
             postOrder.append(node.data + " ");
         }
     }
 
    //  /**
    //   * Creates a String that is a height order
    //   * traversal of the data in the tree starting at
    //   * the Node with the largest height (the root)
    //   * down to Nodes of smallest height - with
    //   * Nodes of equal height added from left to right.
    //   * @return the level order traversal as a String
    //   */
    //  public String levelOrderString() {
    //      Queue<Node> que  = new Queue<>();
    //      StringBuilder sb = new StringBuilder();
    //      que.enqueue(root);
    //      levelOrderString(que, sb);
    //      return sb.toString() + "\n";
    //  }
 
    //  /**
    //   * Helper method to levelOrderString.
    //   * Inserts the data in level order into a String.
    //   * @param que the Queue in which to store the data.
    //   * @param heightTraverse a StringBuilder containing the data.
    //   */
    //  private void levelOrderString(Queue<Node> que, StringBuilder heightTraverse) {
    //      if(!que.isEmpty()) {
    //          Node nd = que.getFront();
    //          que.dequeue();
    //          if(nd != null) {
    //              que.enqueue(nd.left);
    //              que.enqueue(nd.right);
    //              heightTraverse.append(nd.data + " ");
    //          }
    //          levelOrderString(que, heightTraverse);
    //      }
    //  }
     /**Challenge Method */
 
     /**
      * Returns the data of the Node who is the shared precursor to the two
      * Nodes containing the given data. If either data1 or data2 is a
      * duplicate value, the method will find the precursor of the duplicate
      * with greatest height.
      * @param data1 the data contained in one Node of the tree.
      * @param data2 the data contained in one Node of the tree.
      * @param cmp the Comparator indicating how data in the tree is organized.
      * @return the data stored by the shared precursor
      * @precondition data1 and data2 must exist in the BST.
      * @throws IllegalArgumentException when one or both values do not exist
      * in the BST.
      */
     public T sharedPrecursor(T data1, T data2, Comparator<T> cmp) throws IllegalArgumentException {
         T one = search(data1, cmp);
         T two = search(data2, cmp);
         if( one == null || two == null){
             throw new IllegalArgumentException("sharedPrecursor: one or both values do not exist");
         }
         return sharedPrecursor(data1, data2, root, cmp);
     }
 
     /**
      * Private helper method to sharedPrecursor, which recursively locates
      * the shared precursor.
      * @param data1 the data contained in one Node of the tree.
      * @param data2 the data contained in one Node of the tree.
      * @param currLevel the current Node.
      * @param cmp the Comparator indicating how data in the tree is organized.
      * @return the data stored by the shared precursor.
      */
     private T sharedPrecursor(T data1, T data2, Node currLevel, Comparator<T> cmp) {
         T result = currLevel.data;
         if( cmp.compare(data1, data2) == 0){
             return result;
         } else if (  cmp.compare(data1, currLevel.data) <= -1 && cmp.compare(data2, currLevel.data) > 0 ) {
             result = currLevel.data;
         } else if ( cmp.compare(data1, currLevel.data) > 0 && cmp.compare(data2, currLevel.data) <= -1) {
             result = currLevel.data;
         }else if ( cmp.compare(data1, currLevel.data) <= -1 && cmp.compare(data2, currLevel.data) <= -1)  {
             result = sharedPrecursor(data1, data2, currLevel.left, cmp);
         } else if ( cmp.compare(data1, currLevel.data) > 0 && cmp.compare(data2, currLevel.data) > 0) {
             result = sharedPrecursor(data1, data2, currLevel.right, cmp);
         }
         return result;
     }
 }