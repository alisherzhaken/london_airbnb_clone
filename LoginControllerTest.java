import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.ArrayList;
import javafx.scene.text.Text;

/**
 * The test class LoginControllerTest.
 *
 * @author Adrian Surani, Alisher Zhaken, Mahsum Kocabey and Andrei Cinca
 * @version 2020.03.27
 */
public class LoginControllerTest
{
    private Main m;
    private LoginController ln;
    private Text t;
    
    /**
     * Setting up and initializing the initial values to be used for testing.
     */
    public LoginControllerTest()
    {
        t = new Text();
        ln = new LoginController();
        List<User> users = new ArrayList();
        users.add(new User("Dani", "Mani", "sa@gmail.com", "11111"));
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
    
    
    /**
     * First test checking for email and password as null values.
      */
    @Test
    public void Test1(){
        assertNotEquals(ln.isVerified(null, null), "True");
    }
    
    /**
     * Second test checking if the email address has already being used.
      */
    @Test
    public void Test2(){
        assertNotEquals(ln.isVerified("sa@gmail.com", "1231ASD"), "True"); 
    }
    
    /**
     * Third test case checking for the change of value of the error Text field.
     */
    @Test
    public void Test3(){
        String s = "Dana";
        ln.setP(t, s);
        assertEquals(t.getText(), "Dana");
    }
    
    /**
     * Fourth test case checking for the change of the text field value back to normal.
     */
    @Test
    public void Test4(){
        String s = "Dana";
        ln.threadErr(t, s);
        assertEquals(t.getText(), "");
    }
}
