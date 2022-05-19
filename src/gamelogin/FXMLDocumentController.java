/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamelogin;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import space_invaders.ControllerJuego;

/**
 *
 * @author Alejandro
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private Pane content_area;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    
    
    @FXML 
    private TableView<JugadorPublico> tabla;
    @FXML
    private TableColumn  tablaPuestos;
    @FXML
    private TableColumn tablaPuntos;
    @FXML
    private TableColumn tablaNombre;
   
    private ObservableList<JugadorPublico> lista; 
    private Cliente cliente=new Cliente();
    private ArrayList<Player> array;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.iniciarTabla();
        
        
    }
    
    private void iniciarTabla(){
        tablaPuestos.setCellValueFactory(new PropertyValueFactory<JugadorPublico,Integer>("puesto"));
        tablaPuntos.setCellValueFactory(new PropertyValueFactory<JugadorPublico,Integer>("puntos"));
        tablaNombre.setCellValueFactory(new PropertyValueFactory<JugadorPublico,String>("nombre"));
        
        
        lista = FXCollections.observableArrayList();
        tabla.setItems(lista);
    }
    
    private void llamarServer() {
        lista.clear();
        Player temp = new Player();
        temp.setAccion("darTabla");

        cliente.inicio(temp);
        
        this.array = cliente.getLista();
        int i = 1;
       
        if (array != null) {
            for (Player p : array) {
                lista.add(new JugadorPublico(p.getUsername(), p.getPuntajeMax(), i));

                i++;
            }
        }

        
    }

    @FXML
    private void exit_program(MouseEvent event) {
        Player temp= new Player();
        temp.setAccion("cerrar");
        cliente.inicio(temp);
        System.exit(0);
    }

    @FXML
    private void open_registration(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("RegistrationForm.fxml"));
        content_area.getChildren().removeAll();
        content_area.getChildren().setAll(fxml);
    }

    @FXML
    private void login(MouseEvent event) {
        
        
        String user = username.getText();
        String pass = password.getText();

        username.clear();
        password.clear();
        Player temp = new Player(user, pass);
        temp.setAccion("buscar");
        temp=cliente.inicio(temp); 
        ControllerJuego juego=new ControllerJuego(temp);
        
        if(temp.getAccion().equals("abrirJuego")){
            juego.start(new Stage());
 
        }else {
            JOptionPane.showMessageDialog(null,temp.getAccion());
        }
        
        
    }

    @FXML
    private void verTabla(MouseEvent event) {
        
        llamarServer();
    }

    @FXML
    private void ver_Tutorial(MouseEvent event) throws IOException{
        Parent fxml = FXMLLoader.load(getClass().getResource("Tutorial.fxml"));
        content_area.getChildren().removeAll();
        content_area.getChildren().setAll(fxml);
    }
   
    
    
    
}
