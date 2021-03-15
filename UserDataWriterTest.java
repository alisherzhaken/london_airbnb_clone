import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class UserDataWriterTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class UserDataWriterTest
{
    /**
     * Default constructor for test class UserDataWriterTest
     */
    public UserDataWriterTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
    

    @Test
    public void Test1()
    {
        User user = new User("bourdain", "anthony", "ant.bourd@gmail.com", "12345678");
        UserDataWriter writer = new UserDataWriter();
        assertTrue(writer.addUser(user));
    }

    @Test
    public void Test2()
    {
        User user1 = new User("sam", "smith", "sam.smith@gmail.com", "1234567");
        UserDataWriter writer = new UserDataWriter();
        assertTrue(writer.addUser(user1));
    }
}
