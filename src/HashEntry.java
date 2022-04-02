/**
 * Represents an entry in a HashTable
 * @author Joshua Shew
 */
public class HashEntry {
    /* Stores the string that is the key for the HashEntry */
    private String key;

    /* Keeps track of the number of occurences for the String */
    private int value;

    /**
     * Creates a new HashEntry with the specified key and value
     * @param key the String that acts as a key
     * @param value the number of occurences for that String
     */
    public HashEntry(String key, int value) {

    }

    /**
     * Getter for key field
     * @return return the key of the HashEntry
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Setter for the key field
     * @param key the key for the 
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Getter for the value field
     * @return the number of occurences of the word
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Setter for the value field
     * @param value the new value
     */
    public void setValue(int value) {
        this.value = value;
    }
}
