import java.util.ArrayList;

public class WordTable extends HashTable{
    ArrayList<String> wordList;

    public WordTable(ArrayList<String> wordList) {
        super();

        this.wordList = wordList;
    }

    public WordTable(int tableSize, ArrayList<String> wordList) {
        super(tableSize);

        this.wordList = wordList;
    }

    public WordEntry getEntry(String key) {
        if (get(key) == -1) {
            return null;
        }

        return (WordEntry)getTable()[findIndex(key, getTable())];
    }

    /**
     * Stores the key-value pair in the HashTable
     * @param key the key to insert
     * @param value the value associated with the key
     */
    @Override
    public void put(String key, int value) {
        rehashIfNeeded();

        putEntry(new WordEntry(key, value, this.wordList), getTable());
        
        incrementSize();
        updateLoadFactor();
        rehashIfNeeded();
    }

    @Override
    public void put(String key, int value, int hashCode) {
        rehashIfNeeded();

        putEntry(new WordEntry(key, value, this.wordList), getTable(), hashCode);

        incrementSize();
        updateLoadFactor();
        rehashIfNeeded();
    }
}
