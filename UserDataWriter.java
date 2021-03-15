import java.net.URISyntaxException;
import java.net.URL;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/**
 * UserDataWriter is a class designed to add the details of the new user to the database
 *
 * @author Alisher Zhaken, Adrian Surani, Andrei Cinca and Mahsum Kocabey
 * @version 2020.03.26
 */
public class UserDataWriter
{   
    /**
     * Add user to the database
     */
    public boolean addUser(User user)
    {
        try
        {
            URL url = getClass().getResource("user-info.csv");
            CSVWriter writer = new CSVWriter(new FileWriter((new File(url.toURI()).getAbsolutePath()), true));
            String[] line = {user.getName(), user.getSurname(), user.getEmail(), user.getPassword()};
            writer.writeNext(line);
            writer.close();
            System.out.println("User is added successfully");
            return true;
        }
        catch (IOException | URISyntaxException e)
        {
            System.out.println("Failed to load the file!");
            e.printStackTrace();
            return false;
        }
    }
}
