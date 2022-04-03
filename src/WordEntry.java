import java.util.ArrayList;

public class WordEntry extends HashEntry {
    private HashTable wordFollowing;
    private HashTable wordPreceding;

    private ArrayList<HashEntry> entryFollowing;
    private ArrayList<HashEntry> entryPreceding;

    public WordEntry(String key, int value, ArrayList<String> wordList) {
        super(key, value);

        this.wordFollowing = new HashTable();
        this.wordPreceding = new HashTable();

        process(wordList);
    }

    public HashTable getFollowing() {
        return this.wordFollowing;
    }

    public HashTable getPreceding() {
        return this.wordPreceding;
    }

    public ArrayList<HashEntry> getEntryFollowing() {
        return entryFollowing;
    }
    public ArrayList<HashEntry> getEntryPreceding() {
        return entryPreceding;
    }

    protected void process(ArrayList<String> wordList) {
        for (int i = 0; i < wordList.size(); i++) {
            if (wordList.get(i).equals(getKey())) {
                if (i + 1 < wordList.size()) {
                    wordFollowing.update(wordList.get(i + 1), (wordFollowing.get(wordList.get(i + 1)) == -1 ?
                                                               1 : wordFollowing.get(wordList.get(i + 1) + 1)));
                }
                if (i - 1 > -1) {
                    wordPreceding.update(wordList.get(i - 1), (wordFollowing.get(wordList.get(i - 1)) == -1 ?
                                                               1 : wordFollowing.get(wordList.get(i - 1) + 1)));
                }
            }
        }

        this.entryFollowing = this.wordFollowing.exportArray();
        this.entryPreceding = this.wordPreceding.exportArray();
        this.entryFollowing.sort(new HashEntry.ValueCompare());
        this.entryPreceding.sort(new HashEntry.ValueCompare());

        for (int i = 0; i < entryFollowing.size(); i++) {
            wordFollowing.updateRank(entryFollowing.get(i).getKey(), entryFollowing.size() - i);
        }

        for (int i = 0; i < entryPreceding.size(); i++) {
            wordPreceding.updateRank(entryPreceding.get(i).getKey(), entryPreceding.size() - i);
        }
    }
}
