
package gamelogin;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


/**
 * FXML Controller class
 *
 * @author Alejandro
 */
public class TutorialController implements Initializable {

    @FXML
    private AnchorPane pane;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void exit_program(MouseEvent event) {
        
        System.exit(0);
    }

    @FXML
    private void back_to_menu(MouseEvent event) throws IOException {
       Parent root =FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
       GameLogin.stage.getScene().setRoot(root);
    }

   
    
}
