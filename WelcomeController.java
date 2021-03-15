import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.concurrent.TimeUnit;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

public class WelcomeController implements Initializable
{
    //ObservableList<String> options = 
    //FXCollections.observableArrayList(
       // "Option 1",
       // "Option 2",
     //   "Option 3"
    //);
    
    @FXML 
    private ComboBox cBox1;
    
    @FXML
    private ComboBox cBox2;
    
    @FXML 
    private Text t1;
    
    @FXML 
    private Text t2;
    
    @FXML
    private Button checkPropertiesButton;
    
    @FXML
    private Button setButton;
    
    @FXML
    private Button backButton;
    
    @FXML
    private Text err;
    
    int lowestPrice, highestPrice;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        for (Integer i = 0; i < 100; i += 10)
            cBox1.getItems().add(i.toString());
        
        for (Integer i = 10; i < 150; i += 10)
            cBox2.getItems().add(i.toString());
            
        
        t1.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent MouseEvent){
                if(cBox1.getValue() != null)cBox2.show();
                else cBox1.show();
            }
        });
        
        t2.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent MouseEvent){
                if(cBox2.getValue() != null && cBox1.getValue() != null)
                {
                    handleButtonClick(new ActionEvent());
                }
                else {
                    threadErr(err, "Please set your desired price range!");
                }
            }
        });
    }
    
    @FXML
    public void handleButtonClick(ActionEvent event) {
        lowestPrice = Integer.parseInt(cBox1.getValue().toString());
        highestPrice = Integer.parseInt(cBox2.getValue().toString());
        if (cBox1.getValue() != null && cBox2.getValue() != null)
        {
            if(lowestPrice <= highestPrice)
            {
                checkPropertiesButton.setDisable(false);
            }
            else threadErr(err, "Please ensure that the second price is higher!");
        }
    }
    
    /**
     * Load map scene when the user has set the valid price range
     */
    @FXML
    private void loadMapScene(ActionEvent event)
    {
        if (!checkPropertiesButton.isDisabled())
        {
            MapScene mapScene = MapScene.getInstance();
            mapScene.setMap(lowestPrice, highestPrice);
            Main.getStage().setScene(mapScene.getScene());
        }
    }
    
    @FXML
    private void loadLoginScene(ActionEvent event)
    {
        Main.getStage().setScene(Main.getLoginScene());
    }
    
    private void setP(Text t, String err)
    {
        t.setText(err);
    }
    
    private void threadErr(Text t, String err)
    {
        new Thread(() -> {
                setP(t, err);
                try {
                    TimeUnit.SECONDS.sleep(2) ;
                }
                catch(InterruptedException e){
                    
                }
                t.setText("");
            }).start();
    }
}
