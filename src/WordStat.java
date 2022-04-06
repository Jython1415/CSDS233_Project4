import java.util.ArrayList;

public class WordStat {
    /* stores the list of words from the input */
    private ArrayList<String> wordList;

    /* stores a table with words and their frequency */
    public WordTable wordTable;

    /* stores an ordered list of the entries */
    private ArrayList<HashEntry> entryList;

    public HashTable pairTable;

    private ArrayList<HashEntry> pairList;

    public WordStat(String path) throws Exception {
        this.wordList = (new Tokenizer(path)).wordList();
        processWordList();
    }

    public WordStat(String[] words) {
        this.wordList = (new Tokenizer(words)).wordList();
        processWordList();
    }

    public int wordCount(String word) {
        word = Tokenizer.normalizeWord(word);
        return (this.wordTable.get(word) == -1) ? 0 : this.wordTable.get(word);
    }

    public int wordPairCount(String w1, String w2) {
        w1 = Tokenizer.normalizeWord(w1);
        w2 = Tokenizer.normalizeWord(w2);
        int num = this.pairTable.get(w1 + " " + w2);
        return (num == -1 ? 0 : num);
    }

    public int wordRank(String word) {
        word = Tokenizer.normalizeWord(word);
        int rank = this.wordTable.getRank(word);
        return rank == -1 ? 0 : rank;
    }

    public int wordPairRank(String w1, String w2) {
        w1 = Tokenizer.normalizeWord(w1);
        w2 = Tokenizer.normalizeWord(w2);
        int rank = this.pairTable.getRank(w1 + " " + w2);
        return (rank == -1) ? 0 : rank;
    }
 
    public String[] mostCommonWords(int k) {
        String[] list = new String[k];

        for (int i = 0; i < k; i++) {
            list[i] = entryList.get(entryList.size() - i - 1).getKey();
        }

        return list;
    }

    public String[] leastCommonWords(int k) {
        String[] list = new String[k];
        
        for (int i = 0; i < k; i++) {
            list[i] = entryList.get(i).getKey();
        }

        return list;
    }

    public String[] mostCommonWordPairs(int k) {
        String[] list = new String[k];

        for (int i = 0; i < k; i++) {
            list[i] = pairList.get(pairList.size() - i - 1).getKey();
        }

        return list;
    }

    public String[] mostCommonCollocs(int k, String baseWord, int i) {
        baseWord = Tokenizer.normalizeWord(baseWord);
        ArrayList<HashEntry> mainList;
        if (i == -1) {
            mainList = wordTable.getEntry(baseWord).getWordPrecedingList();
        }
        else {
            mainList = wordTable.getEntry(baseWord).getWordFollowingList();
        }

        String[] list = new String[Math.min(k, mainList.size())];
        for (int j = 0; j < k && j < mainList.size(); j++) {
            list[j] = mainList.get(mainList.size() - j - 1).getKey();
        }

        return list;
    }

    private void processWordList() {
        /* add all words to the hash table */
        this.wordTable = new WordTable(this.wordList);
        for (String word : this.wordList) {
            this.wordTable.update(word, (this.wordTable.get(word) == -1 ?
                                         1 : this.wordTable.get(word) + 1));
        }

        /* add all word pairs to the hash table */
        this.pairTable = new HashTable();
        for (int i = 0; i < this.wordList.size() - 1; i++) {
            String pair = this.wordList.get(i) + " " + this.wordList.get(i + 1);
            this.pairTable.update(pair, (this.pairTable.get(pair) == -1 ?
                                         1 : this.pairTable.get(pair) + 1));
        }

        this.entryList = this.wordTable.exportArray();
        this.entryList.sort(new HashEntry.ValueCompare());

        this.pairList = this.pairTable.exportArray();
        this.pairList.sort(new HashEntry.ValueCompare());

        for (int i = 0; i < entryList.size(); i++) {
            wordTable.updateRank(entryList.get(i).getKey(), entryList.size() - i);
        }

        for (int i = 0; i < pairList.size(); i++) {
            pairTable.updateRank(pairList.get(i).getKey(), pairList.size() - i);
        }
    }

    public static void main(String[] args) throws Exception {
        WordStat frankenstein = new WordStat("/Users/Joshua/Documents/_CASE/CSDS_233/P4/Project4/src/odyssey.txt");

        System.out.println(frankenstein.wordCount("and"));
    }
}
