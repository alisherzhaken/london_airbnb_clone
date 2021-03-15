import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.event.ActionEvent;

import java.util.HashMap;
/**
 * StatisticsContoller controls the Statistics scene.
 * Statistics scene is split into four panels, each of which shows certain information about London boroughs.
 *
 * @author Alisher Zhaken, Adrian Surani, Mahsum Kocabey and Andrei Cinca
 * @version 2020.03.28
 */
public class StatisticsController implements Initializable
{
    @FXML
    private Button backButton;
    @FXML
    private Button nextButton1;    
    @FXML
    private Button nextButton2;
    @FXML
    private Button nextButton3;
    @FXML
    private Button nextButton4;
    
    @FXML
    private Button previousButton1;
    @FXML
    private Button previousButton2; 
    @FXML
    private Button previousButton3;
    @FXML
    private Button previousButton4;
    
    @FXML
    private Text label1;
    @FXML
    private Text label2;
    @FXML
    private Text label3;
    @FXML
    private Text label4;
    
    @FXML
    private Text view1;
    @FXML
    private Text view2;
    @FXML
    private Text view3;
    @FXML
    private Text view4;
    
    private static MapScene.BoroughPath[] boroughs = MapScene.getInstance().getBoroughs();
    // Used for iterating through the array
    private int count = 0;
    // Used to store the index of statistics type stored in each panel
    // i.e. if counters[0] == 2, then it means that the first panel previously shows the statistics from statistics[2]
    private int counters[] = {0, 0, 0, 0};
    
    private static String[][] statistics = {{"Average number of reviews per property", "", "false"},
                                     {"Total number of properties", "", "false"},
                                     {"Total number of houses and apartments", "", "false"},
                                     {"The most expensive borough", "", "false"},
                                     {"The cheapest borough", "", "false"},
                                     {"Borough with highest number of properties", "", "false"},
                                     {"Most popular host", "", "false"},
                                     {"Lowest property price","","false"}};
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        calculateStatistics();
        showPanel1();
        count++;
        showPanel2();
        count++;
        showPanel3();
        count++;
        showPanel4();
        count++;
    }
    
    /**
     * When "back" button is pressed
     * Go back to map scene
     */
    @FXML
    private void loadMapScene(ActionEvent event)
    {
        Main.getStage().setScene(MapScene.getInstance().getScene());
    }
    
    /**
     * Show information on the first panel
     */
    private void showPanel1()
    {
        label1.setText(statistics[count][0]);
        view1.setText(statistics[count][1]);
        counters[0] = count;
        statistics[count][2] = "true";
    }
    
    /**
     * Show information on the second panel
     */
    private void showPanel2()
    {
        label2.setText(statistics[count][0]);
        view2.setText(statistics[count][1]);
        counters[1] = count;
        statistics[count][2] = "true";
    }
    
    /**
     * Show information on the third panel
     */
    private void showPanel3()
    {
        label3.setText(statistics[count][0]);
        view3.setText(statistics[count][1]);
        counters[2] = count;
        statistics[count][2] = "true";
    }
    
    /**
     * Show information on the fourth panel
     */
    private void showPanel4()
    {
        label4.setText(statistics[count][0]);
        view4.setText(statistics[count][1]);
        counters[3] = count;
        statistics[count][2] = "true";
    }
    
    private void calculateStatistics()
    {
        statistics[0][1] = averageNumberOfReviews();
        statistics[1][1] = numberOfProperties();
        statistics[2][1] = totalNumber();
        statistics[3][1] = mostExpensiveBorough();
        statistics[4][1] = cheapestBorough();
        statistics[5][1] = boroughWithMostProperties();
        statistics[6][1] = mostPopularHost();
        statistics[7][1] = lowestPropertyPrice();
    }
    
    private String numberOfProperties()
    {
        int numberOfProperties = 0;
        for (MapScene.BoroughPath borough : boroughs)
        {
            numberOfProperties += borough.getPropertyCount();
        }
        return ""+numberOfProperties;
    }
    
    private String mostExpensiveBorough()
    {
        int highestPropertyPrice = 0;
        String boroughName = "";
        for (MapScene.BoroughPath borough : boroughs)
        {
            if (borough.getHighestPropertyPrice() > highestPropertyPrice)
            {
                boroughName = borough.getBoroughName();
                highestPropertyPrice = borough.getHighestPropertyPrice();
            }
        }
        return boroughName;
    }
    
    private String averageNumberOfReviews()
    {
        int totalProperties = Integer.parseInt(numberOfProperties());
        int totalReviews = 0;
        int avgNumberOfReviews = 0;
        for (MapScene.BoroughPath borough : boroughs)
        {
            for (AirbnbListing property : borough.getPropertyList())
            {
                totalReviews += property.getNumberOfReviews();
            }
        }
        if (totalProperties != 0)
            avgNumberOfReviews = totalReviews/totalProperties;
        return "" + avgNumberOfReviews;
    }
    
    private String totalNumber()
    {
        HashMap<String, Integer> realEstate = new HashMap<>();
        for (MapScene.BoroughPath borough : boroughs)
        {
            for (AirbnbListing property : borough.getPropertyList())
            {
                realEstate.put(property.getHost_id(), property.getCalculatedHostListingsCount());
            }
        }
        int totalNumber = 0;
        for (Integer number : realEstate.values())
        {
            totalNumber += number;
        }
        return "" + totalNumber;
    }
    
    private String cheapestBorough()
    {
        int lowestPrice = 999999;
        String boroughName = "";
        for (MapScene.BoroughPath borough : boroughs)
        {
            if (borough.getHighestPropertyPrice() < lowestPrice)
            {
                lowestPrice = borough.getHighestPropertyPrice();
                boroughName = borough.getBoroughName();
            }
        }
        return boroughName;
    }
    
    private String boroughWithMostProperties()
    {
        String boroughName = "";
        int numberOfProperties = 0;
        for (MapScene.BoroughPath borough : boroughs)
        {
            if (borough.getPropertyCount() > numberOfProperties)
            {
                numberOfProperties = borough.getPropertyCount();
                boroughName = borough.getBoroughName();
            }
        }
        return boroughName;
    }
    
    private String mostPopularHost()
    {
        String popularHost = "";
        int totalReviews = 0;
        // Stores host id and total number of reviews
        HashMap<String, Integer> hosts = new HashMap<>();
        
        for (MapScene.BoroughPath borough : boroughs)
        {
            for (AirbnbListing property : borough.getPropertyList())
            {
                if (hosts.get(property.getHost_id()) == null)
                {
                    hosts.put(property.getHost_id(), property.getNumberOfReviews());
                }
                else
                {
                    hosts.put(property.getHost_id(), hosts.get(property.getHost_id()) + property.getNumberOfReviews());
                }
            }
        }
        for (String host : hosts.keySet())
        {
            if (hosts.get(host) > totalReviews)
            {
                popularHost = host;
            }
        }
        return "ID:" + popularHost;
    }
    
    private String lowestPropertyPrice()
    {
        int lowestPrice = Integer.MAX_VALUE;
        for (MapScene.BoroughPath borough : boroughs)
        {
            if (borough.getHighestPropertyPrice() < lowestPrice)
            {
                lowestPrice = borough.getHighestPropertyPrice();
            }
        }
        return "£" + lowestPrice + " per stay";
    }
    
    @FXML
    private void nextLPanel1(ActionEvent event)
    {
        while (!statistics[count][2].equals("false"))
        {
            count++;
            if (count >= 8)
            {
                count = 0;
            }
        }
        statistics[counters[0]][2] = "false";
        showPanel1();
    }
    
    @FXML
    private void previousPanel1(ActionEvent event)
    {
        while(!statistics[count][2].equals("false"))
        {
            count--;
            if (count < 0)
            {
                count = 7;
            }
        }
        statistics[counters[0]][2] = "false";
        showPanel1();
    }
    
    @FXML
    private void nextLPanel2(ActionEvent event)
    {
        while (!statistics[count][2].equals("false"))
        {
            count++;
            if (count >= 8)
            {
                count = 0;
            }
        }
        statistics[counters[1]][2] = "false";
        showPanel2();
    }
    
    @FXML
    private void previousPanel2(ActionEvent event)
    {
        while(!statistics[count][2].equals("false"))
        {
            count--;
            if (count < 0)
            {
                count = 7;
            }
        }
        statistics[counters[1]][2] = "false";
        showPanel2();
    }
    
    @FXML
    private void nextLPanel3(ActionEvent event)
    {
        while (!statistics[count][2].equals("false"))
        {
            count++;
            if (count >= 8)
            {
                count = 0;
            }
        }
        statistics[counters[2]][2] = "false";
        showPanel3();
    }
    
    @FXML
    private void previousPanel3(ActionEvent event)
    {
        while(!statistics[count][2].equals("false"))
        {
            count--;
            if (count < 0)
            {
                count = 7;
            }
        }
        statistics[counters[2]][2] = "false";
        showPanel3();
    }    
 
    @FXML
    private void nextLPanel4(ActionEvent event)
    {
        while (!statistics[count][2].equals("false"))
        {
            count++;
            if (count >= 8)
            {
                count = 0;
            }
        }
        statistics[counters[3]][2] = "false";
        showPanel4();
    }
    
    @FXML
    private void previousPanel4()
    {
        while(!statistics[count][2].equals("false"))
        {
            count--;
            if (count < 0)
            {
                count = 7;
            }
        }
        statistics[counters[3]][2] = "true";
        showPanel4();
    } 
}
