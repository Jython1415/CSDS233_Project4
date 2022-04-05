import org.junit.Assert;
import org.junit.Test;
import java.io.File;

public class TokenizerTester {
    
    /**
     * Unit tests for Tokenizer constructors
     * This also tests the wordList method
     */
    @Test
    public void testTokenizer() throws Exception{
        String message = "The input was not separated and normalized correctly";
        String badException = "The method should not have thrown an exception";

        // Check file input
        File f = new File("../src/test.txt");


        try {
            Assert.assertArrayEquals(message, new String[]{"this", "is", "an", "easy", "sentence", "this", "is", "slightly", "more", "difficult",
                                                        "thisis", "supposed", "to", "be", "even", "more", "difficult", "abcd", "efgh"},
                                              (new Tokenizer("/Users/Joshua/Documents/_CASE/CSDS_233/P4/Project4/src/test.txt").wordList().toArray()));
        }
        catch (Exception e) {
            Assert.fail(badException + e.toString());
        }
    }

    /**
     * Unit tests for normalizeWord
     */
    @Test
    public void testNormalizeWord() {
        String message = "The word was not normalized correctly";

        // check capitalization
        Assert.assertEquals(message, "a", Tokenizer.normalizeWord("A"));
        Assert.assertEquals(message, "a", Tokenizer.normalizeWord("a"));
        Assert.assertEquals(message, "abcdefghijklmnopqrstuvwxyz", Tokenizer.normalizeWord("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        Assert.assertEquals(message, "abcd", Tokenizer.normalizeWord("AbcD"));

        // check punctuation
        Assert.assertEquals(message, "", Tokenizer.normalizeWord("!#$%&'()*+,-./:;<=>?@[]^_`{|}~\""));
        Assert.assertEquals(message, "a", Tokenizer.normalizeWord(",a,"));
        Assert.assertEquals(message, "aa", Tokenizer.normalizeWord("a,a"));

        // check spaces
        Assert.assertEquals(message, "", Tokenizer.normalizeWord(" "));
        Assert.assertEquals(message, "", Tokenizer.normalizeWord("     "));
        Assert.assertEquals(message, "a", Tokenizer.normalizeWord(" a "));
    }
}
