package DataStructures;
import java.util.ArrayList;

public class HashTable<T> {

    private int numElements;
    private ArrayList<LinkedList<T> > table;

    /**
     * Constructor for the HashTable class. Initializes the Table to be sized
     * according to value passed in as a parameter. Inserts size empty Lists into
     * the table. Sets numElements to 0
     *
     * @param size the table size
     * @precondition size > 0
     * @throws IllegalArgumentException when size <= 0
     */
    public HashTable(int size) throws IllegalArgumentException {
        if (size <= 0) {
            throw new IllegalArgumentException("HashTable: the size cannot be equal to or less than 0");
        }
        this.table = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            this.table.add(i, new LinkedList<>());
        }
        this.numElements = 0;
    }

    /**
     * Constructor for HashTable class.
     * Inserts the contents of the given array
     * into the Table at the appropriate indices
     * @param array an array of elements to insert
     * @param size the size of the Table
     * @precondition size > 0
     * @throws IllegalArgumentException when size <= 0
     */
    public HashTable(T[] array, int size) throws IllegalArgumentException {
        if(size <= 0){
            throw new IllegalArgumentException("HashTable arraycopy: the size cannot be equal to or less than 0");
        }
        this.table = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            this.table.add(i, new LinkedList<>());
        }

        if( array == null){
            this.numElements = 0;
        } else {
            for (T t : array) {
                this.add(t);
            }
        }
    }

    /** Accessors */

    /**
     * Returns the hash value in the table for a given Object.
     * @param obj the Object
     * @return the index in the table
     */
    private int hash(T obj) {
        int code = obj.hashCode();
        return code % table.size();
    }

    /**
     * Counts the number of elements at this index.
     * @param index the index in the table
     * @precondition <index >= 0>
     * @return the count of elements at this index
     * @throws IndexOutOfBoundsException when the precondition is violated
     */
    public int countBucket(int index) throws IndexOutOfBoundsException {
        if( index < 0){
            throw new IndexOutOfBoundsException("countBucket: the index is out of bounds");
        }
        LinkedList<T> list = table.get(index);
        return list.getLength();
    }

    /**
     * Determines total number of elements in the table
     * @return total number of elements
     */
    public int getNumElements() {
        return this.numElements;
    }

    /**
     * Accesses a specified key in the Table
     * @param elmt the key to search for
     * @return the value to which the specified key is mapped,
     * or null if this table contains no mapping for the key.
     * @precondition <the element cannot be null>
     * @throws NullPointerException when the precondition is violated.
     */
    public T get(T elmt) throws NullPointerException {
        if(elmt == null){
            throw new NullPointerException("get: the element is null");
        }
        int index = hash(elmt);
        LinkedList<T> list = table.get(index);
        list.positionIterator();
        int loc = 0;
        while ( loc < list.getLength() ) {
            if (list.getIterator().equals(elmt)) {
                return list.getIterator();
            } else {
                list.advanceIterator();
                loc++;
            }
        }
        return null;
    }

    /**
     * Accesses a specified element in the table.
     * @param elmt the element to locate
     * @return the bucket number where the element
     * is located or -1 if it is not found.
     * @precondition <the element cannot be null>
     * @throws NullPointerException when the precondition is violated.
     */
    public int find(T elmt) throws NullPointerException{
        if( elmt == null){
            throw new NullPointerException("find: the element is null");
        }
        int index = 0;
        while( index < this.table.size() ){
            LinkedList<T> list = this.table.get(index);
            list.positionIterator();
            int loc = 0;
            while ( loc < list.getLength() ) {
                if(list.getIterator().equals(elmt)){
                    return index;
                } else {
                    list.advanceIterator();
                    loc++;
                }
            }
            index++;
        }
        return -1;
    }

    /**
     * Determines whether a specified element is in the table.
     * @param elmt the element to locate
     * @return whether the element is in the table
     * @precondition <the element cannot be null>
     * @throws NullPointerException when the precondition is violated
     */
    public boolean contains(T elmt) throws NullPointerException {
        if(elmt == null){
            throw new NullPointerException("contains: the element cannot be null");
        }
        int index = hash(elmt);
        int loc = 0;
        LinkedList<T> list = table.get(index);
        list.positionIterator();
        while ( loc < list.getLength() ) {
            if (list.getIterator().equals(elmt)) {
                return true;
            } else {
                list.advanceIterator();
                loc++;
            }
        }
        return false;
    }

    /** Mutators */

    /**
     * Inserts a new element in the table at the end of the chain
     * of the correct bucket.
     * @param elmt the element to insert
     * @precondition <the element cannot be null>
     * @throws NullPointerException when the precondition is violated.
     */
    public void add(T elmt) throws NullPointerException {
        if(elmt == null){
            throw new NullPointerException("add: the element cannot be null");
        }
        int index = hash(elmt);
        LinkedList<T> list = table.get(index);
        list.addLast(elmt);
        this.numElements++;
    }

    /**
     * Removes the given element from the table.
     * @param elmt the element to remove
     * @precondition <the element cannot be null>
     * @return whether elmt exists and was removed from the table
     * @throws NullPointerException when the precondition is violated
     */
    public boolean delete(T elmt) throws NullPointerException {
        if(elmt == null){
            throw new NullPointerException("delete: the element cannot be null");
        }
        int index = hash(elmt);
        LinkedList<T> list = table.get(index);
        list.positionIterator();
        int loc = 0;
        while ( loc < list.getLength() ) {
            if (list.getIterator().equals(elmt)) {
                list.removeIterator();
                this.numElements--;
                return true;
            } else {
                list.advanceIterator();
                loc++;
            }
        }
        return false;
    }

    /**
     * Resets the hash table back to the empty state, as if the one argument
     * constructor has just been called.
     */
    public void clear() {
        int size = this.table.size();
        this.table = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            this.table.add(i, new LinkedList<>());
        }
        this.numElements = 0;
    }

    /** Additional Methods */

    /**
     * Computes the load factor.
     * @return the load factor
     */
    public double getLoadFactor() {
        return this.numElements/ (double) this.table.size();
    }

    /**
     * Creates a String of all elements at a given bucket
     * @param bucket the index in the table
     * @return a String of elements, separated by spaces with a new line character
     *         at the end
     * @precondition <the index has to be greater than or equal to 0>
     * @throws IndexOutOfBoundsException when bucket is
     * out of bounds
     */
    public String bucketToString(int bucket) throws IndexOutOfBoundsException {
        if(bucket < 0){
            throw new IndexOutOfBoundsException("bucketToString: the index is out of bounds");
        }
        LinkedList<T> list = table.get(bucket);
        return list.toString();
    }

    /**
     * Creates a String of the bucket number followed by a colon followed by
     * the first element at each bucket followed by a new line. For empty buckets,
     * add the bucket number followed by a colon followed by empty.
     *
     * @return a String of all first elements at each bucket.
     */
    public String rowToString() {
        StringBuilder result = new StringBuilder("");
        for (int i = 0; i < this.table.size(); i++) {
            LinkedList<T> list = this.table.get(i);
            list.positionIterator();
            if(list.getLength() != 0){
                result.append("Bucket ").append(i).append(": ").append(list.getIterator()).append("\n");
            } else {
                result.append("Bucket ").append(i).append(":").append(" empty\n");
            }
        }

        return result.toString();
    }

    /**
     * Starting at the 0th bucket, and continuing in order until the last
     * bucket, concatenates all elements at all buckets into one String, with
     * a new line between buckets and one more new line at the end of the
     * entire String.
     * @return a String of all elements in this HashTable.
     */
    @Override
    public String toString() {
        String result = "";
        for (LinkedList<T> list : this.table) {
            list.positionIterator();
            if( list.getLength() > 0) {
                for (int j = 0; j < list.getLength(); j++) {
                    result += list.getIterator() + "";
                    list.advanceIterator();
                }
                result += "\n";
            }
        }
        return result + "\n";
    }
}