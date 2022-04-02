public class HashTable {
    /* Stores the number of slots in the table */
    private int tableSize;

    /* Stores the number of entries stored in the table */
    private int entries;

    /* Stores the load factor of the table */
    private double loadFactor;

    /* Stores the HashEntries */
    private HashEntry[] table;

    /* The load factor at which to expand and rehash */
    private final double loadFactorLimit = 0.7;

    /**
     * Creates a new HashTable with a size of 1009
     */
    public HashTable() {
        this.tableSize = 1009; // first prime number over 1000
        this.entries = 0;
        this.loadFactor = 0.0;

        table = new HashEntry[this.tableSize];
    }

    /**
     * Creates a new HashTable at the specified size
     * @param tableSize the size of the table
     */
    public HashTable(int tableSize) {
        this.tableSize = tableSize;
        this.entries = 0;
        this.loadFactor = 0.0;

        table = new HashEntry[this.tableSize];
    }

    /**
     * Stores the key-value pair in the HashTable
     * @param key the key to insert
     * @param value the value associated with the key
     */
    public void put(String key, int value) {
        rehashIfNeeded();

        putEntry(new HashEntry(key, value), this.table);
        
        entries++;
        updateLoadFactor();
        rehashIfNeeded();
    }

    public void put(String key, int value, int hashCode) {
        rehashIfNeeded();

        putEntry(new HashEntry(key, value), this.table, hashCode);

        entries++;
        updateLoadFactor();
        rehashIfNeeded();
    }

    /**
     * Updates the value associated with the key. If it doesn't exist, then it is added to the table
     * @param key the key to search for
     * @param value the new value
     */
    public void update(String key, int value) {
        int currentIndex = findIndex(key);
        
        if (currentIndex == -1) {
            put(key, value);
        }
        else {
            this.table[currentIndex].setValue(value);
        }
    }

    /**
     * Returns the value associated with the specified key. If there is no entry, then returns -1
     * @param key the key to look for
     * @return -1 or the value
     */
    public int get(String key) {
        int currentIndex = findIndex(key);

        if (currentIndex == -1) {
            return -1;
        }

        return this.table[currentIndex].getValue();
    }

    /**
     * Method for testing purposes
     * @param key the key to look for
     * @param hashCode the hash of the key
     * @return -1 or the value
     */
    public int get(String key, int hashCode) {
        int currentIndex = findIndex(key, hashCode);

        if (currentIndex == -1) {
            return -1;
        }

        return this.table[currentIndex].getValue();
    }

    /**
     * Expands the table size and rehashes all entries to the new table
     */
    private void rehash() {
        HashEntry[] newTable = new HashEntry[nextPrime(this.tableSize * 2)];
        this.tableSize *= 2;

        for (HashEntry entry : this.table) {
            if (putEntry(entry, newTable) == -1) {
                rehash();
                return;
            }
        }

        this.table = newTable;
    }

    /**
     * Adds a HashEntry to a table
     * @param entry the entry to add
     * @param table the table to add to
     * @return -1 if unsuccessful, otherwise the index the entry was inserted at
     */
    private int putEntry(HashEntry entry, HashEntry[] table) {
        int currentIndex = findOpenIndex(entry.getKey());
        
        if (currentIndex == -1) {
            return -1;
        }

        table[currentIndex] = entry;
        return currentIndex;
    }

    /**
     * putEntry, but for testing
     * @param entry the entry to add
     * @param table the table to add to
     * @param hashCode the custom hash code
     * @return -1 if unsuccessful, otherwise the index the entry was inserted at
     */
    private int putEntry(HashEntry entry, HashEntry[] table, int hashCode) {
        int currentIndex = findOpenIndex(entry.getKey(), hashCode);
        
        if (currentIndex == -1) {
            return -1;
        }

        table[currentIndex] = entry;
        return currentIndex;
    }

    /**
     * Finds the index of a given key. -1 if it cannot be found
     * @param key the key to look for
     * @return the index of the key
     */
    private int findIndex(String key) {
        int currentIndex = Math.abs(key.hashCode()) % this.tableSize;
        int counter = 1;

        while (!table[currentIndex].getKey().equals(key) && counter < this.tableSize) {
            currentIndex = (currentIndex + (counter*counter++)) % this.tableSize;
        }

        if (counter >= this.tableSize) {
            return -1;
        }

        return currentIndex;
    }

    /**
     * findIndex, but for testing
     * @param key the key to search for
     * @param hashCode the custom hash
     * @return the index of the key
     */
    private int findIndex(String key, int hashCode) {
        int currentIndex = Math.abs(hashCode) % this.tableSize;
        int counter = 1;

        while (!table[currentIndex].getKey().equals(key) && counter < this.tableSize) {
            currentIndex = (currentIndex + (counter*counter++)) % this.tableSize;
        }

        if (counter >= this.tableSize) {
            return -1;
        }

        return currentIndex;
    }

    /**
     * Finds the index where the key can be inserted. -1 if it cannot be found
     * @param key the key to insert
     * @return the index
     */
    private int findOpenIndex(String key) {
        int currentIndex = Math.abs(key.hashCode()) % this.tableSize;
        int counter = 1;

        while (table[currentIndex] == null && counter < this.tableSize) {
            currentIndex = (currentIndex + (counter*counter++)) % this.tableSize;
        }

        if (counter >= this.tableSize) {
            return -1;
        }

        return currentIndex;
    }

    /**
     * findOpenIndex, but for testing
     * @param key the key to insert
     * @param hashCode the custom hash code
     * @return the index
     */
    private int findOpenIndex(String key, int hashCode) {
        int currentIndex = Math.abs(hashCode) % this.tableSize;
        int counter = 1;

        while (table[currentIndex] == null && counter < this.tableSize) {
            currentIndex = (currentIndex + (counter*counter++)) % this.tableSize;
        }

        if (counter >= this.tableSize) {
            return -1;
        }

        return currentIndex;
    }

    /**
     * Updates the load factor field
     */
    private void updateLoadFactor() {
        this.loadFactor = this.entries / (double)this.tableSize;
    }

    /**
     * rehashes the table if needed
     */
    private void rehashIfNeeded() {
        if (this.loadFactor > this.loadFactorLimit) {
            rehash();
        }
    }

    /**
     * Method for finding the next prime
     * @param input the lower limit
     * @return the next prime after the lower limit
     * @author not me
     */
    private int nextPrime(int input){
        input++;
        int limit = (int) Math.sqrt(input);
        int counter;
        while(true) {
            counter = 0;
            for(int i = 2; i <= limit && counter == 0; i ++) {
                if (input % i == 0)
                    counter++;
            }

            if (counter == 0) {
                return input;
            }
            else {
                input++;
            }
        }
    }
}
