import com.opencsv.CSVReader;
import java.net.URISyntaxException;
import java.net.URL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
/**
 * UserDataLoader loads the user information from the database in the form of the list
 *
 * @author Alisher Zhaken, Adrian Surani, Mahsum Kocabey and Andrei Cinca
 * @version 2020.03.27
 */
public class UserDataLoader
{
    ArrayList<User> list;
    
    public UserDataLoader()
    {
        list = new ArrayList<>();
    }
    
    /**
     * Loads the information about users from database
     * @return users List containing information about users
     */
    public ArrayList<User> load()
    {
        try
        {
            URL url = getClass().getResource("user-info.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String[] line;
            reader.readNext();
            while ( (line = reader.readNext()) != null)
            {
                String fName = line[0];
                String sName = line[1];
                String email = line[2];
                String password = line[3];
                list.add(new User(fName, sName, email, password));
            }
            reader.close();
        }
        catch (IOException | URISyntaxException e)
        {
            System.out.println("Failed to load the file!");
            e.printStackTrace();
        }
        return list;
    }
}
