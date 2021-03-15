import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
/**
 * Write a description of class Main here.
 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Main extends Application
{
    private static Stage primaryStage;
    
    private double width = 700;
    private double height = 400;
        
    private int lowestPrice = 0;
    private int highestPrice = 40;
    
    private static Scene loginScene;
    
    @Override
    public void start(Stage stage) throws Exception
    {
        primaryStage = stage;
        primaryStage.setResizable(false);
        
        Pane loginRoot = FXMLLoader.load(getClass().getResource("LoginPanel.fxml"));     

        // JavaFX must have a Scene (window content) inside a Stage (window)
        loginScene = new Scene(loginRoot);
        loginScene.getStylesheets().addAll("login_styles.css");
        
        primaryStage.setTitle("Airbnb Application");
        primaryStage.setScene(loginScene);
        
        primaryStage.show();
    }
    
    /**
     * Return login scene
     * @return loginScene
     */
    public static Scene getLoginScene()
    {
        return loginScene;
    }
    
    /**
     * Return current stage
     * @return primartyStage
     */
    public static Stage getStage()
    {
        return primaryStage;
    }
    
    /**
     * Main method to start the app
     */
    public static void main(String args[])
    {           
        launch();      
    } 
    

}
