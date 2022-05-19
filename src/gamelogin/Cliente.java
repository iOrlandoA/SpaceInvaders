
package gamelogin;



import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.*;

public class Cliente {

    private Socket conectorSimple;
    private ObjectInputStream entrada;
    private ObjectOutputStream salida;
    private ArrayList<Player> lista=new ArrayList<Player>();
    

    public Cliente(String nombreServidor, String puerto) {
        try {
            conectorSimple = new Socket(nombreServidor, Integer.parseInt(puerto));
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public Cliente() {
    }
    

    private void obtenerFlujos() {
        try {
            salida = new ObjectOutputStream(conectorSimple.getOutputStream());
            salida.flush();
            entrada = new ObjectInputStream(conectorSimple.getInputStream());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private void cerrarFlujos() {
        try {
            entrada.close();
            salida.close();
            conectorSimple.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private Player recibirMensaje() {
        Player usuario = null;
        try {
            usuario = (Player) entrada.readObject();
        } catch (Exception e) {
            System.err.println(e);
        }
        return usuario;
    }
    
     private void recibirLista() {
        
     
        
       
    }
    
    

    private void enviarMensaje(Player usuario) {
        try {
            salida.writeObject(usuario);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public ArrayList getLista() {
        return this.lista;
    }
    
    
    
    public Player inicio(Player jugador) {

        Cliente c = new Cliente("127.0.0.1", "3000");
        c.obtenerFlujos();
        Player temp  = null;
        if ("darTabla".equals(jugador.getAccion())) {
            c.enviarMensaje(jugador);
          
            try{
                c.lista = (ArrayList<Player>) c.entrada.readObject();
            }catch(IOException |ClassNotFoundException  ex){
                System.out.println(ex.getMessage());
            }
            this.lista=c.lista;
            
        }else {
            c.enviarMensaje(jugador);
            temp = c.recibirMensaje();
            c.cerrarFlujos();
            return temp;
        }
        c.cerrarFlujos();
        return null;
        
        
    }
}
