import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.util.List;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import java.util.concurrent.TimeUnit;
import java.util.regex.*; 

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

/**
 * LoginController is responsible for controlling the behaviour of the application during the login scene 
 *
 * @author Adrian Surani, Alisher Zhaken, Mahsum Kocabey and Andrei Cinca
 * @version 2020.03.27
 */


public class LoginController implements Initializable
{
    private static Scene welcomeScene;
    
    @FXML
    private Button loginButton;
    @FXML
    private Button signUpButton;
    
    private static List<User> users;
    
    @FXML
    private TextField loginEmail;
    @FXML
    private PasswordField loginPassword;
    
    @FXML
    private TextField signUpEmail;
    @FXML
    private PasswordField signUpPassword;
    @FXML
    private TextField signUpName;
    @FXML
    private TextField signUpSurname;
    @FXML
    private Text error;
    @FXML 
    private Text lerr;
    
    @FXML 
    private Text alr;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        alr.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent MouseEvent){
                loginEmail.requestFocus();
                threadErr(lerr,"Please insert your email and password!");
            }
        });
    }
    
    
    /**
     * Load welcome scene when loginButton is pressed
     */
    @FXML
    private void loadWelcomeScene(ActionEvent event)
    {
        try
        {
            if (isVerified(loginEmail.getText(), loginPassword.getText()) == "True")
            {
                Pane welcomeRoot = FXMLLoader.load(getClass().getResource("WelcomePanel.fxml"));
                welcomeScene = new Scene(welcomeRoot, 800, 500);
                welcomeScene.getStylesheets().addAll("welcome_styles.css");
                Main.getStage().setScene(welcomeScene);
            }
            else
            {
                threadErr(lerr,"Please insert a valid email, password combination!");
            }
        }
        catch (IOException e)
        {
            System.out.println("Failed to load the file!");
            e.printStackTrace();
        }
    }
    
    /**
     * Returns welcome scene
     * @return welcomeScene
     */
    public static Scene getWelcomeScene()
    {
        return welcomeScene;
    }
    
    /**
     * Verify entered email and password
     * @return true if the data is correct
     */
    public String isVerified(String email, String password)
    {
        if (email == null || password == null)
        {
            return "Please insert a valid email and password.";
        }
        users = new UserDataLoader().load();
        if (users.size() > 0)
        {
            for (User user : users)
            {
                if (user.getEmail().equals(email) && user.getPassword().equals(password))
                {
                    return "True";
                }
            }
        }
        return "False";
    }
    
    /**
     * Check the validity of the details of a new user
     * Add the details of a new user to the database
     * when "Sign Up" button is pressed
     */
    @FXML
    private void signUp(ActionEvent event)
    {
        System.out.println("Trying to add the user....");
        users = new UserDataLoader().load();
        User newUser = new User(signUpName.getText(), signUpSurname.getText(), signUpEmail.getText(), signUpPassword.getText());
        for (User user : users)
        {
            if (user.equals(newUser))
            {
                threadErr(error, "Current email has already been registered!");
                return;
            }
        }
        if(newUser.getPassword().length() < 6)
        {
            threadErr(error, "Password should have a minimum of 6 characters!");
            return;
        }
        if (!Pattern.matches("\\S+@\\S+\\.\\S+", newUser.getEmail()))
        {
            threadErr(error, "Please insert a valid email!");
        }
        UserDataWriter writer = new UserDataWriter();
        writer.addUser(newUser);
        signUpName.setText(null);
        signUpSurname.setText(null);
        signUpEmail.setText(null);
        signUpPassword.setText(null);
    }
    
    public void setP(Text t, String err)
    {
        t.setText(err);
    }
    
    public void threadErr(Text t, String err)
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
