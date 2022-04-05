import org.junit.Assert;
import org.junit.Test;

public class HashTableTester {
    
    /**
     * Unit tests for the constructors and getter/setter methods
     */
    @Test
    public void testHashTable() {
        HashTable t1 = new HashTable();

        Assert.assertEquals(t1.getSize(), 0);
        Assert.assertEquals(t1.getCapacity(), 1009);

        HashTable t2 = new HashTable(1);
        
        Assert.assertEquals(t2.getSize(), 0);
        Assert.assertEquals(t2.getCapacity(), 1);
    }

    /**
     * Unit tests for the put method (both versions)
     */
    @Test
    public void testPut() {
        // test initial insertion

        // test duplicate keys

        // test collisions with different keys

        // test rehashing

    }

    /**
     * Unit tets for the update method
     */
    @Test
    public void testUpdate() {
        // test when the key is not in the table

        // test when the key is found in one search

        // test when there is collision

        // test when the key cannot be found
    }

    /**
     * Unit tests for the updateRank method
     */
    @Test
    public void testUpdateRank() {
        // test when key is not in the table

        // test when the key can be found

    }

    /**
     * Unit tests for the getRank method
     */
    @Test
    public void testGetRank() {
        // test when the key can be found

        // test when the key is not in the table

    }

    /**
     * Unit tests for the get method (both versions)
     */
    @Test
    public void testGet() {
        // test when the key can be found

        // test when the key cannot be found
    }

    /**
     * Unit tests for the exportArray method
     */
    @Test
    public void testExportArray() {
        // test with an empty table

        // test with a table with one entry

        // test with a table with many keys
    }

    /**
     * Unit tests for the nextPrime method
     */
    @Test
    public void testNextPrime() {
        // test for low primes

        // test for mid-range primes (1,000-50,000)

        // test for large primes (1,000,000-10,000,000)
        
    }
}
