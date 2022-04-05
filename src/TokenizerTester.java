import org.junit.Assert;
import org.junit.Test;

public class TokenizerTester {
    
    /**
     * Unit tests for Tokenizer constructors
     * This also tests the wordList method
     */
    @Test
    public void testTokenizer() {

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
