package gamelogin;


import Server.Server;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Alejandro and Orlando
 */
public class GameLogin extends Application {
    
    public static Stage stage = null;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        this.stage=stage;

        stage.setResizable(false);
        stage.show();
        
       
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Server server=new Server();
        Thread serverHilo = new Thread(server,"Hilo Servidor");
        serverHilo.start();
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){}
        
        
        launch(args);
        
        
    }
    
}
