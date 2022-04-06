import java.util.ArrayList;

public class WordStat {
    /* stores the list of words from the input */
    ArrayList<String> wordList;

    /* stores a table with words and their frequency */
    WordTable wordTable;

    /* stores an ordered list of the entries */
    ArrayList<HashEntry> entryList;

    HashTable pairTable;

    ArrayList<HashEntry> pairList;

    public WordStat(String path) throws Exception {
        this.wordList = (new Tokenizer(path)).wordList();
        processWordList();
    }

    public WordStat(String[] words) {
        this.wordList = (new Tokenizer(words)).wordList();
        processWordList();
    }

    public int wordCount(String word) {
        return (this.wordTable.get(word) == -1) ? 0 : this.wordTable.get(word);
    }

    public int wordPairCount(String w1, String w2) {
        int num = this.pairTable.get(w1 + " " + w2);
        return (num == -1 ? 0 : num);
    }

    public int wordRank(String word) {
        return this.wordTable.getRank(word);
    }

    public int wordPairRank(String w1, String w2) {
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
        ArrayList<HashEntry> mainList;
        if (i == -1) {
            mainList = wordTable.getEntry(baseWord).getWordPrecedingList();
        }
        else {
            mainList = wordTable.getEntry(baseWord).getWordFollowingList();
        }

        String[] list = new String[k];
        for (int j = 0; j < k; j++) {
            list[j] = mainList.get(mainList.size() - j - 1).getKey();
        }

        return list;
    }

    private void processWordList() {
        this.wordTable = new WordTable(this.wordList);
        for (String word : this.wordList) {
            this.wordTable.update(word, (this.wordTable.get(word) == -1 ?
                                         1 : this.wordTable.get(word) + 1));
        }

        for (int i = 0; i < this.wordList.size() - 1; i++) {
            String pair = this.wordList.get(i) + " " + this.wordList.get(i + 1);
            this.pairTable.update(pair, (this.pairTable.get(pair) == -1 ?
                                         1 : this.wordTable.get(pair) + 1));
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
}
