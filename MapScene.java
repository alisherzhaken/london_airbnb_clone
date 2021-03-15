import javafx.scene.Scene;
import javafx.scene.shape.Path;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.fxml.FXMLLoader;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.IOException;
/**
 * MapScene is responsible for creating a scene that contains a map of London with all properties in their respective boroughs
 *
 * @author Alisher Zhaken
 * @version 2020.03.16
 */
public class MapScene
{
    // Ensures that only one instance of map scene exists throughout the application lifetime
    private static MapScene mapScene = new MapScene(800,500);
    // Used to store the borough names with their respective name tags
    private HashMap<String, String> boroughNames;
    // Current Scene
    private Scene scene;
    // Dimensions of the scene
    private double width, height;
    
    private BoroughPath boroughs[] = new BoroughPath[33];
    private Text boroughNameTags[] = new Text[33];
    
    // Map of London
    private String levels[][] = { 
                                  {"","","","ENFI","","",""},                              //  {0,0,0,1,0,0,0}
                                 {"","","BARN","HRGY","WALT","",""},                       // {0,0,1,1,1,0,0}
                                  {"HRRW","BREN","CAMD","ISLI","HACK","REDB","HAVE"},      //  {1,1,1,1,1,1,1}
                                 {"HILL","EALI","KENS","WSTM","TOWH","NEWH","BARK"},       // {1,1,1,1,1,1,1}
                                  {"HOUN","HAMM","WAND","CITY","GWCH","BEXL",""},          //  {1,1,1,1,1,1,0}
                                 {"","RICH","MERT","LAMB","STHW","LEWS",""},               // {0,1,1,1,1,1,0}
                                  {"","KING","SUTT","CROY","BROM","",""}                   //  {0,1,1,1,1,0,0}
                                };
    /**
     * Nested class
     * Used to represent the borough
     * Stores the information about current properties in the borough
     */
    public class BoroughPath extends Path
    {
        private String name;
        private String nameTag;
        private ArrayList<AirbnbListing> properties = new ArrayList<>();
        private int highestPropertyPrice = 0;
        /**
         * Add the borough name
         * @param text Borough name
         */
        public void addBoroughName(String text)
        {
            name = text;
        }
        /**
         * Return borough name
         * @return name
         */
        public String getBoroughName()
        {
            return name;
        }
        /**
         * Add borough name tag
         * @param text Borough name tag
         */
        public void addBoroughNameTag(String text)
        {
            nameTag = text;
        }
        /**
         * Return borough name tag
         * @return nameTag
         */
        public String getBoroughNameTag()
        {
            return nameTag;
        }
        /**
         * Return number of properties in the borough
         * @return properties.size()
         */
        public int getPropertyCount()
        {
            return properties.size();
        }
        /**
         * Add property to the borough
         * In addition, set the highest property price
         * @param property Property to be added
         */
        public void addProperty(AirbnbListing property)
        {
            properties.add(property);
            if ((property.getMinimumNights() * property.getPrice()) > highestPropertyPrice)
                highestPropertyPrice = property.getMinimumNights() * property.getPrice();
        }
        /**
         * Return highest property price
         * @return highestPropertyPrice
         */
        public int getHighestPropertyPrice()
        {
            return highestPropertyPrice;
        }
        /**
         * Return the list of properties
         * @return properties
         */
        public ArrayList<AirbnbListing> getPropertyList()
        {
            return properties;
        }
    }
    
    private MapScene(double width, double height/*, int lowestPrice, int highestPrice*/)
    {
        this.width = width;
        this.height = height;
        
        boroughNames = new HashMap<>();
        boroughNames.put("ENFI", "Enfield");
        boroughNames.put("BARN","Barnet");
        boroughNames.put("HRGY","Haringey");
        boroughNames.put("WALT","Waltham Forest");
        boroughNames.put("HRRW","Harrow");
        boroughNames.put("BREN","Brent");
        boroughNames.put("CAMD","Camden");
        boroughNames.put("ISLI", "Islington");
        boroughNames.put("HACK","Hackney");
        boroughNames.put("REDB","Redbridge");
        boroughNames.put("HAVE","Havering");
        boroughNames.put("HILL","Hillingdon");
        boroughNames.put("EALI","Ealing");
        boroughNames.put("KENS","Kensington and Chelsea");
        boroughNames.put("WSTM", "Westminster");
        boroughNames.put("TOWH","Tower Hamlets");
        boroughNames.put("NEWH","Newham");
        boroughNames.put("BARK","Barking and Dagenham");
        boroughNames.put("HOUN","Hounslow");
        boroughNames.put("HAMM", "Hammersmith and Fulham");
        boroughNames.put("WAND","Wandsworth");
        boroughNames.put("CITY", "City of London");
        boroughNames.put("GWCH", "Greenwich");
        boroughNames.put("BEXL","Bexley");
        boroughNames.put("RICH","Richmond upon Thames");
        boroughNames.put("MERT","Merton");
        boroughNames.put("LAMB","Lambeth");
        boroughNames.put("STHW", "Southwark");
        boroughNames.put("LEWS","Lewisham");
        boroughNames.put("KING", "Kingston upon Thames");
        boroughNames.put("SUTT","Sutton");
        boroughNames.put("CROY","Croydon");
        boroughNames.put("BROM","Bromley");                
    }
    
    public void setMap(int lowestPrice, int highestPrice)
    {                     
        ArrayList<AirbnbListing> properties = new AirbnbDataLoader().load(lowestPrice, highestPrice);                  
        
        // Size of the vertical side of hexagon is 25
        // Size of the diagonal side of hexagon z is sqrt(25^2 + 15^2)
        //                                        x = 25, y = 15
        // On each level :
        //  X difference is 54
        // Between each level:
        //  X difference is 27
        //  Y difference is 45
        double defaultEvenX = 237.00f; // X coordinate of a starting point of
                                      // even line
        double defaultOddX = 210.00f;  // X coordinate of a starting point of
                                      // odd line
        
        double y = 40.00f;                // Y coordinate of the starting point (X, 40)
        int count = 0;
        for (int i = 0; i < levels.length; i++)
        {
            double x;
            if (i % 2 == 0)
            {
                x = defaultEvenX;
            }
            else
            {
                x = defaultOddX;
            }
            for (int j = 0; j < 7; j++)
            {
                if (levels[i][j] != "")
                {
                    // Setup the hexagon
                    BoroughPath borough = drawHexagon(x, y);
                    borough.addBoroughNameTag(levels[i][j]);
                    borough.addBoroughName(boroughNames.get(borough.getBoroughNameTag()));
                    
                    properties.forEach(property->{
                                                    if (property.getNeighbourhood().equals(borough.getBoroughName()))
                                                    {
                                                        borough.addProperty(property);
                                                    }
                                                 });
                    if (borough.getPropertyCount() < 100)
                    {
                        Color c = Color.web("rgba(255, 255, 255, 0.4)");
                        borough.fillProperty().set(c);
                    }
                    else if (borough.getPropertyCount() < 500)
                    {
                        Color c = Color.web("rgba(255, 255, 255, 0.5)");
                        borough.fillProperty().set(c);
                    }
                    else if (borough.getPropertyCount() < 1000)
                    {
                        Color c = Color.web("rgba(255, 255, 255, 0.6)");
                        borough.fillProperty().set(c);
                    }
                    else if (borough.getPropertyCount() < 1500)
                    {
                        Color c = Color.web("rgba(255, 255, 255, 0.7)");
                        borough.fillProperty().set(c);
                    }
                    else if (borough.getPropertyCount() < 2000)
                    {
                        Color c = Color.web("rgba(255, 255, 255, 0.8)");
                        borough.fillProperty().set(c);
                    }
                    else if (borough.getPropertyCount() < 2500)
                    {
                        Color c = Color.web("rgba(255, 255, 255, 0.9)");
                        borough.fillProperty().set(c);
                    }
                    else
                    {
                        Color c = Color.web("rgba(255, 255, 255, 1.0)");
                        borough.fillProperty().set(c);
                    }
                    
                    DropShadow rollOverColor = new DropShadow();
                    rollOverColor.setColor(Color.WHITE);
                    
                    borough.addEventHandler(MouseEvent.MOUSE_ENTERED,
                                    event->borough.setEffect(rollOverColor));
                    borough.addEventHandler(MouseEvent.MOUSE_EXITED,
                                    event->borough.setEffect(null));
                    borough.addEventHandler(MouseEvent.MOUSE_PRESSED,
                                    event-> {
                                                if (borough.getPropertyList().size() > 0) 
                                                    showBorough(borough);
                                            });
                                    
                    Text nameTag = new Text(borough.getBoroughNameTag());
                    
                    nameTag.setX(x - 13.0);
                    nameTag.setY(y + 32.50);
                    
                    nameTag.addEventHandler(MouseEvent.MOUSE_ENTERED,
                                    event->borough.setEffect(rollOverColor));
                    nameTag.addEventHandler(MouseEvent.MOUSE_EXITED,
                                    event->borough.setEffect(null));
                    nameTag.addEventHandler(MouseEvent.MOUSE_PRESSED,
                                    event-> {
                                                if (borough.getPropertyList().size() > 0) 
                                                    showBorough(borough);
                                            });
                                            
                    if (borough.getPropertyCount() == 0)
                    {
                        borough.setDisable(true);
                        nameTag.setDisable(true);
                    }                
                    boroughs[count] = borough;
                    boroughNameTags[count] = nameTag;                    
                    count++;
                }
                x += 54.00;
            }
            y += 45.00;
        }
        try
        {
            Button backButton = new Button("<<");
            backButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent event)
                {
                    Main.getStage().setScene(LoginController.getWelcomeScene());
                }
            });
            backButton.setId("btn");
            VBox backBox = new VBox(backButton);
            backBox.setMargin(backButton, new Insets(10, 0, 0, 10));
            
            Button statisticsButton = new Button("View Statistics");
            statisticsButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent event)
                {
                    loadStatistics();
                }
            });
            statisticsButton.setId("button");
            VBox statisticsBox = new VBox(statisticsButton);
            statisticsBox.setMargin(statisticsButton, new Insets(20, 0, 0, 345));
            
            BorderPane root = new BorderPane();
            root.setId("main");
            root.getChildren().addAll(boroughs);
            root.getChildren().addAll(boroughNameTags);
            
            root.setTop(backBox);
            root.setMargin(backBox, new Insets(0, 720, 0,0));
            
            root.setBottom(statisticsBox);
            root.setMargin(statisticsBox, new Insets(0, 0, 20, 0));
            
            scene = new Scene(root, width, height);
            scene.getStylesheets().addAll("map_styles.css");
        }
        catch (Exception e)
        {
            System.out.println("Exception occured");
        }
    }
    
    
    /**
     * Return an instance of map scene
     * @return mapScene
     */
    public static MapScene getInstance()
    {
        return mapScene;
    }
    
    /**
     * Return current scene
     * @return scene
     */
    public Scene getScene()
    {
        return scene;
    }
    
    public BoroughPath[] getBoroughs()
    {
        return boroughs;
    }
    
    private void loadStatistics()
    {
        try
        {
            BorderPane root = FXMLLoader.load(getClass().getResource("StatisticsPanel.fxml"));
            Scene statisticsScene = new Scene(root, 800, 500);
            statisticsScene.getStylesheets().addAll("stat_styles.css");
            Main.getStage().setScene(statisticsScene);
        }
        catch (IOException e)
        {
            System.out.println("Failed to load the file!!!");
            e.printStackTrace();
        }
    }
    
    /**
     * Draw hexagon
     * @param startX x coordinate of the starting point
     * @param startY y coordinate of the starting point
     * @return path Path that forms hexagon
     */
    private BoroughPath drawHexagon(double startX, double startY)
    {
        BoroughPath path = new BoroughPath();
        MoveTo startPoint = new MoveTo(startX, startY);
        path.getElements().add(startPoint);
        path.getElements().addAll(drawLines(startPoint));
        
        return path;
    }

    /**
     * Load a new scene that shows the information about properties in the borough
     * @param borough Borough that is shown
     */
    private void showBorough(BoroughPath borough)
    {
        try
        {
            PropertyScene pScene = new PropertyScene();
            pScene.loadProperties(borough.getPropertyList());
            pScene.loadScene();
            Main.getStage().setScene(pScene.getScene());
        }
        catch(IOException e)
        {
            System.out.println("IOException");
        }
    }
    
    /**
     * Draw lines
     * @param startPoint Starting Point of the hexagon
     * @return lines Array containing lines that form a hexagon
     */
    private LineTo[] drawLines(MoveTo startPoint)
    {
        // Setup the offset
        // An offset is determined by the number of pixels
        // + or - signs determine direction
        double xOffset = 25.00;
        double yOffset = 15.00;
        double lineOffset = 25.00;
        
        LineTo line1 = new LineTo(startPoint.getX() + xOffset, startPoint.getY() + yOffset);
        LineTo line2 = new LineTo(line1.getX(), line1.getY() + lineOffset);
        LineTo line3 = new LineTo(line2.getX() - xOffset , line2.getY() + yOffset);
        LineTo line4 = new LineTo(line3.getX() - xOffset , line3.getY() - yOffset);
        LineTo line5 = new LineTo(line4.getX(), line4.getY() - lineOffset);
        LineTo line6 = new LineTo(line5.getX() + xOffset , line5.getY() - yOffset);
        
        LineTo[] lines = {line1,line2, line3, line4, line5, line6};
        return lines;
    }
    
}
