package DataStructures;
import java.util.NoSuchElementException;

public class LinkedList<T> {
    private class Node {
        private T data;
        private Node next;
        private Node prev;

        public Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

    private int length;
    private Node first;
    private Node last;
    private Node iterator;

    /**** CONSTRUCTORS ****/

    /**
     * Instantiates a new LinkedList with default values
     * @postcondition <the LinkedList is setup and empty>
     */
    public LinkedList() {
        first = null;
        last = null;
        iterator = null;
        length = 0;
    }

    /**
     * Converts the given array into a LinkedList
     * @param array the array of values to insert into this LinkedList
     * @postcondition <a new LinkedList object, which is transferred from a array, and has identical values from the array>
     */
    public LinkedList(T[] array) {
        if(array == null) {
            return;
        } else if ( array.length == 0 ) {
            first = null;
            last = null;
            iterator = null;
            length = 0;
        } else {
            for (int i = 0; i < array.length; i++) {
                addLast(array[i]);
            }
            iterator = null;
        }
    }

    /**
     * Instantiates a new LinkedList by copying another List
     * @param original the LinkedList to copy
     * @postcondition a new List object, which is an identical,
     * but separate, copy of the LinkedList original
     */
    public LinkedList(LinkedList<T> original) {
        if(original == null) {
            return;
        } else if (original.length == 0) {
            first = null;
            last = null;
            iterator = null;
            length = 0;
        } else {
            Node temp = original.first;
            while(temp != null) {
                addLast(temp.data);
                temp = temp.next;
            }
            iterator = null;
        }

    }

    /**** ACCESSORS ****/

    /**
     * Returns the value stored in the first node
     * @precondition <length > 0>
     * @return the value stored at node first
     * @throws NoSuchElementException <There is nothing in the list to get>
     */
    public T getFirst() throws NoSuchElementException {
        if(length == 0){
            throw new NoSuchElementException("The list is empty");
        }
        return first.data;
    }

    /**
     * Returns the value stored in the last node
     * @precondition <length > 0>
     * @return the value stored in the node last
     * @throws NoSuchElementException <There is nothing in the list to get>
     */
    public T getLast() throws NoSuchElementException {
        if(length == 0){
            throw new NoSuchElementException("The list is empty");
        }
        return last.data;
    }

    /**
     * Returns the data stored in the iterator node
     * @precondition <iterator cannot be off end>
     * @return the data stored in the iterator node
     * @throw NullPointerException <the iterator is empty>
     */
    public T getIterator() throws NullPointerException {
        if(iterator == null) {
            throw new NullPointerException("getIterator: The iterator is empty");
        }
        return iterator.data;
    }

    /**
     * Returns the current length of the LinkedList
     * @return the length of the LinkedList from 0 to n
     */
    public int getLength() {
        return this.length;
    }

    /**
     * Returns whether the LinkedList is currently empty
     * @return whether the LinkedList is empty
     */
    public boolean isEmpty() {
        return this.length == 0;
    }

    /**
     * Returns whether the iterator is offEnd, i.e. null
     * @return whether the iterator is null
     */
    public boolean offEnd() {
        return iterator == null;
    }

    /**** MUTATORS ****/

    /**
     * Creates a new first element
     * @param data the data to insert at the front of the LinkedList
     * @postcondition <There is a new first element and the list size increased by one>
     */
    public void addFirst(T data) {
        if(length == 0){
            first = last = new Node(data);
        } else {
            Node temp = new Node(data);
            temp.next = first;
            first.prev = temp;
            first = temp;
        }
        length++;
    }

    /**
     * Creates a new last element
     * @param data the data to insert at the end of the LinkedList
     * @postcondition <There is a new last element and the list size increased by one>
     */
    public void addLast(T data) {
        if(length == 0){
            first = last = new Node(data);
        } else {
            Node temp = new Node(data);
            temp.prev = last;
            last.next = temp;
            last = temp;
        }
        length++;
    }

    /**
     * Inserts a new element after the iterator
     * @param data the data to insert
     * @precondition <iterator cannot be off end>
     * @throws NullPointerException <The iterator is empty>
     */
    public void addIterator(T data) throws NullPointerException{
        if(iterator == null) {
            throw new NullPointerException ("addIterator: iterator is null");
        } else if( iterator == last) {
            addLast(data);
        } else {
            Node newNode = new Node(data);
            newNode.next = iterator.next;
            newNode.prev = iterator;
            iterator.next.prev = newNode;
            iterator.next = newNode;
            length++;
        }
    }

    /**
     * removes the element at the front of the LinkedList
     * @precondition <length > 0>
     * @postcondition <The second element is now the first element and the size is decreased by one>
     * @throws NoSuchElementException <There is nothing to remove>
     */
    public void removeFirst() throws NoSuchElementException {
        if(length == 0){
            throw new NoSuchElementException("removeFirst(): Cannot remove from an empty List!");
        } else if ( length == 1){
            first = last = iterator = null;
            length = 0;
        } else {
            first = first.next;
            first.prev = null;

            length--;
        }
    }

    /**
     * removes the element at the end of the LinkedList
     * @precondition <length > 0>
     * @postcondition <The second last element is now the last element and the size is decreased by one>
     * @throws NoSuchElementException <There is nothing to remove>
     */
    public void removeLast() throws NoSuchElementException {
        if(length == 0){
            throw new NoSuchElementException("removeFirst(): Cannot remove from an empty List!");
        } else if ( length == 1){
            first = last = iterator = null;
            length = 0;
        } else {
            last = last.prev;
            last.next = null;
            length--;
        }
    }

    /**
     * removes the element referenced by the iterator
     * @precondition <iterator cannot be off end>
     * @postcondition <iterator is set to null and the length has decreased by one>
     * @throws NullPointerException <the iterator is empty>
     */
    public void removeIterator() throws NullPointerException {
        if(iterator == null) {
            throw new NullPointerException ("removeIterator: iterator is null");
        }else if(iterator == first) {
            removeFirst();
            iterator = null;
        }else if(iterator == last) {
            removeLast();
            iterator = null;
        }else {
            iterator.prev.next = iterator.next;
            iterator.next.prev = iterator.prev;
            iterator = null;
            length--;
        }
    }

    /**
     * places the iterator at the first node
     * @postcondition <the iterator is at the beginning of the list>
     */
    public void positionIterator(){
        iterator = first;
    }

    /**
     * Moves the iterator one node towards the last
     * @precondition <iterator cannot be off end>
     * @postcondition <iterator points to the next one on the list>
     * @throws NullPointerException <the iterator is empty>
     */
    public void advanceIterator() throws NullPointerException {
        if(offEnd()) {
            throw new NullPointerException ("advanceIterator: iterator is null");
        }
        iterator = iterator.next;
    }

    /**
     * Moves the iterator one node towards the first
     * @precondition <iterator cannot be off end>
     * @postcondition <iterator points to the previous one on the list>
     * @throws NullPointerException <the iterator is empty>
     */
    public void reverseIterator() throws NullPointerException {
        if(offEnd()) {
            throw new NullPointerException ("reverseIterator: iterator is null");
        }
        iterator = iterator.prev;
    }

    /**** ADDITIONAL OPERATIONS ****/

    /**
     * Re-sets LinkedList to empty as if the
     * default constructor had just been called
     */
    public void clear() {
        first = null;
        last = null;
        iterator = null;
        length = 0;
    }

    /**
     * Converts the LinkedList to a String, with each value separated by a blank
     * line At the end of the String, place a new line character
     * @return the LinkedList as a String
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        Node temp = first;
        while(temp != null) {
            result.append(temp.data + " ");
            temp = temp.next;
        }
        return result.toString() + "\n";
    }

    /**
     * Returns each element in the LinkedList along with its
     * numerical position from 1 to n, followed by a newline.
     * @return a numbered position of the Linked List as a String
     */
    public String numberedListString() {
        StringBuilder result = new StringBuilder();
        Node temp = first;
        int counter = 1;
        while(temp != null) {
            result.append(counter + ". " + temp.data + "\n");
            temp = temp.next;
            counter++;
        }
        return result.toString() + "\n";
    }

    /**
     * Determines whether the given Object is
     * another LinkedList, containing
     * the same data in the same order
     * @param obj another Object
     * @return whether there is equality
     */
    @SuppressWarnings("unchecked") //good practice to remove warning here
    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        } else if(!(obj instanceof LinkedList)) {
            return false;
        } else {
            LinkedList<T> list = (LinkedList<T>) obj;
            if(this.length != list.length) {
                return false;
            } else {
                Node temp1 = this.first;
                Node temp2 = list.first;
                while(temp1 != null) {
                    if(temp1.data == null || temp2.data == null) {
                        if(temp1.data != temp2.data) {
                            return false;
                        }
                    } else if (!(temp1.data.equals(temp2.data))) {
                        return false;
                    }
                    temp1 = temp1.next;
                    temp2 = temp2.next;
                }
                return true;
            }
        }
    }
    /**
     Moves all nodes in the list towards the end
     of the list the number of times specified
     Any node that falls off the end of the list as it
     moves forward will be placed the front of the list
     For example: [1, 2, 3, 4, 5], numMoves = 2 -> [4, 5, 1,2,3]
     For example: [1, 2, 3, 4, 5], numMoves = 4 -> [2, 3, 4, 5, 1]
     For example: [1, 2, 3, 4, 5], numMoves = 7 →> [4, 5, 1, 2 , 3]
     @param numMoves the number of times to move each node.
     @precondition numMoves >= 0
     @postcondition iterator position unchanged (i.e. still referencing
     the same node in the list, regardless of new location of Node)
     @throws IllegalArgumentException when numMoves ‹ 0
     */
    public void spinList (int numMoves) throws IllegalArgumentException{
        if(numMoves < 0) {
            throw new IllegalArgumentException("spinList: move has to be greater than or equal to 0 moves");
        } else if(numMoves == 0) {
            return;
        }else if(this.length == 0){
            return;
        } else if(this.length == 1) {
            return;
        }else {
            numMoves = numMoves % this.length;
            T saveValue = iterator.data;
            if(numMoves == 0) {
                return;
            } else {
                positionIterator();
                for (int i = 0; i < numMoves; i++) {
                    T tempValue = iterator.data;
                    iterator.data = last.data;
                    advanceIterator();
                    for (int j = 1; j < this.length; j++) {
                        T tempV2 = iterator.data;
                        iterator.data = tempValue;
                        advanceIterator();
                        if(offEnd()) {
                            positionIterator();
                        }
                        tempValue = tempV2;
                    }
                }
            }
            while(iterator.data != saveValue) {
                advanceIterator();
            }
        }
    }


    /**
     Splices together two LinkedLists to create a third List
     which contains alternating values from this list
     and the given parameter
     For example: [1,2,3] and [4,5,6] -> [1,4,2,5, 3,6]
     For example: [1, 2, 3, 4] and [5, 6] → [1, 5, 2, 6, 3, 4]
     For example: [1, 2] and [3, 4, 5, 6] -> [1, 3, 2, 4, 5, 6]
     @param list the second LinkedList
     @return a new LinkedList, which is the result of
     interlocking this and list
     @postcondition this and list are unchanged
     */
    public LinkedList<T> altLists (LinkedList<T> list) {
        LinkedList<T> newList = new LinkedList<T>();
        int counter = 0;
        if((this.length == 0 && list.length == 0) || (this == null && list == null)) {
            return newList;
        } else if(this.length == 0 || this == null) {
            return new LinkedList<T>(list);
        } else if(list == null || list.length == 0) {
            return new LinkedList<T>(this);
        } else if(this.length >= list.length) {
            this.positionIterator();
            list.positionIterator();
            for (int i = 0; i < list.length; i++) {
                newList.addLast(this.iterator.data);
                newList.addLast(list.iterator.data);
                this.advanceIterator();
                list.advanceIterator();
                counter++;
            }

            if(counter != this.length) {
                for (int i = counter; i < this.length; i++) {
                    newList.addLast(this.iterator.data);
                    this.advanceIterator();
                }
            }

        } else {
            this.positionIterator();
            list.positionIterator();
            for (int i = 0; i < this.length; i++) {
                newList.addLast(this.iterator.data);
                newList.addLast(list.iterator.data);
                this.advanceIterator();
                list.advanceIterator();
                counter++;
            }

            for (int i = counter; i < list.length; i++) {
                newList.addLast(list.iterator.data);
                list.advanceIterator();
            }

        }
        return newList;
    }

    /** More methods */
    /**
     * Searches the LinkedList for a given element's index.
     * @param data the data whose index to locate.
     * @return the index of the data or -1 if the data is not contained
     * in the LinkedList.
     */
    public int findIndex(T data) {
        Node temp = first;
        int counter = 0;

        if(isEmpty()){
            return -1;
        }

        while(!temp.data.equals(data) && counter < this.getLength()) {
            temp = temp.next;
            counter++;
        }
        if(counter >= this.getLength()){
            return -1;
        } else {
            return counter;
        }
    }

    /**
     * Advances the iterator to location within the LinkedList
     * specified by the given index.
     * @param index the index at which to place the iterator.
     * @precondition index &gt;= 0,  index &lt; length
     * @throws IndexOutOfBoundsException when index is out of bounds
     */
    public void advanceIteratorToIndex(int index)  throws IndexOutOfBoundsException {
        if(index >= this.getLength() || index < 0){
            throw new IndexOutOfBoundsException("advanceIteratorToIndex: the index is out of bounds");
        }

        for (int i = 0; i < index; i++) {
            advanceIterator();
        }
    }
}