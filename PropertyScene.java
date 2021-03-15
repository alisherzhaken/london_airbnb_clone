import javafx.application.Application;

import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;

import java.util.ResourceBundle;
import java.util.ArrayList;

import java.net.URL;
import java.net.URI;
import java.net.URISyntaxException;
/**
 * Write a description of class PropertyScene here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PropertyScene implements Initializable
{    
    private Scene scene;
    
    private static AirbnbListing currentProperty;
   
    private static ArrayList<AirbnbListing> properties;
    
    private int index;
    
    @FXML
    private Text idLabel;
    
    @FXML
    private Text nameLabel;
    
    @FXML
    private Text hostIdLabel;
    
    @FXML
    private Text hostNameLabel;
    
    @FXML
    private Text priceLabel;
    
    @FXML
    private Text roomLabel;
    
    @FXML
    private Text availabilityLabel;
    
    @FXML
    private Text minimumStayLabel;
    
    @FXML
    private Text reviewsLabel;
    
    @FXML
    private Text lastReviewLabel;
    
    
    @FXML
    private Button backButton;
    
    @FXML
    private Button nextButton;
    
    @FXML
    private Button previousButton;
    
    @FXML
    private Button googleMapsButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        showProperty();
    }
    
    /**
     * Load the scene from .fxml file
     */
    public void loadScene() throws IOException
    {
        scene = new Scene(FXMLLoader.load(getClass().getResource("PropertyPanel.fxml")));
        scene.getStylesheets().addAll("welcome_styles.css");
    }
    
    public void loadProperties(ArrayList<AirbnbListing> properties)
    {
        index = 0;
        this.properties = properties;
        this.currentProperty = this.properties.get(index);
    }
    
    /**
     * Return the scene
     * @return scene
     */
    public Scene getScene()
    {
        return scene;
    }
    
    /**
     * When "back" button is pressed,
     * Map scene is set on the stage
     */
    @FXML
    private void loadMapScene(ActionEvent event)
    {
        Main.getStage().setScene(MapScene.getInstance().getScene());
    }
    
    /**
     * Show the information about current property
     */
    private void showProperty()
    {
        idLabel.setText(currentProperty.getId());
        nameLabel.setText(currentProperty.getName());
        hostIdLabel.setText(currentProperty.getHost_id());
        hostNameLabel.setText(currentProperty.getHost_name());
        priceLabel.setText("£" + currentProperty.getPrice() + " per night");
        roomLabel.setText(currentProperty.getRoom_type());
        availabilityLabel.setText(""+currentProperty.getAvailability365());
        minimumStayLabel.setText(""+currentProperty.getMinimumNights());
        reviewsLabel.setText(""+currentProperty.getNumberOfReviews());
        lastReviewLabel.setText(currentProperty.getLastReview());
    }
    
    /**
     * When "next" button is pressed,
     * go to next property
     */
    @FXML
    private void nextProperty(ActionEvent event)
    {
        index++;
        if ( index >= properties.size())
            index = 0;
        currentProperty = properties.get(index);
        showProperty();
    }
    
    /**
     * When "previous" button is pressed,
     * Go to previous property
     */
    @FXML
    private void previousProperty(ActionEvent event)
    {
        index--;
        if (index < 0)
            index = properties.size() - 1;
        currentProperty = properties.get(index);
        showProperty();
    }
    
    @FXML
    private void viewPropertyOnGoogleMaps(ActionEvent event)
    {
        try
        {
            URI uri = new URI("https://www.google.com/maps/place/"+currentProperty.getLatitude()+","
                            +currentProperty.getLongitude());
            java.awt.Desktop.getDesktop().browse(uri);
        }
        catch (IOException | URISyntaxException e)
        {
            System.out.println("Failed to load!!!");
            e.printStackTrace();
        }
    }
    
}
