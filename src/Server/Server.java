package Server;

import gamelogin.Player;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class Server implements Runnable{

    private ServerSocket conectorDeServicio;
    private Socket conectorSimple;
    private ObjectInputStream entrada;
    private ObjectOutputStream salida;
    private static ArrayList<Player> archivo;
    private Integer buscador;
    private static Player usuario;
    
    public Server(){
    
    }
    
    private void ServerInicio (String puerto) {
        try {
            conectorDeServicio = new ServerSocket(Integer.parseInt(puerto));
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private void aceptarConexion() {
        try {
            conectorSimple = conectorDeServicio.accept();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private void obtenerFlujos() {
        try {
            entrada = new ObjectInputStream(conectorSimple.getInputStream());
            salida = new ObjectOutputStream(conectorSimple.getOutputStream());
            salida.flush();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private void cerrarFlujos() {
        try {
            entrada.close();
            salida.close();
            conectorSimple.close();
            salida.flush();
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

    private void enviarMensaje(Player usuario) {
        try {
            salida.writeObject(usuario);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    private void enviarTabla(ArrayList<Player> tabla) {
        if(tabla!=null){
            
            try {
                salida.writeObject(tabla);
            } catch (Exception e) {
                System.err.println(e);
            }
        }else{
            
            ArrayList<Player> aux=new ArrayList();
            try {
                salida.writeObject(aux);
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
    
    private void abrirArchivo() {
        
        
        
        File temp = new File("jugadores.txt");
        if (temp.exists() == false) {
            try {
                temp.createNewFile();
                archivo = new ArrayList<Player>();
            } catch (IOException err) {
                System.out.println(err.getMessage());
            }
        } else {
            try {
                FileInputStream input = new FileInputStream(temp);
                ObjectInputStream objectRead = new ObjectInputStream(input);
                archivo = (ArrayList<Player>) objectRead.readObject();
                objectRead.close();
                input.close();
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("No se encontro la direccion de los jugadores al abrir " + ex.getMessage());
            }
        }
        

    }
    
    private void guardarArchivo(){
        try {
            File temp = new File("jugadores.txt");
            temp.createNewFile();
            FileOutputStream output = new FileOutputStream(temp);
            ObjectOutputStream objectWrite = new ObjectOutputStream(output);
            objectWrite.writeObject(archivo);
            objectWrite.close();
            output.close();
        } catch (IOException ex) {
            System.out.println("No se encontro la direccion de los jugadores al guardar "+ex.getMessage());
        }
    }

    private void buscarPlayer(Player buscar) throws ServerExceptions {
        buscador = 0;
        Player temp = null;

        while (archivo.size() > buscador) {
            
            temp = (Player) archivo.get(buscador);
            if (temp.getUsername().equals(buscar.getUsername())) {
                if (temp.getPassword().equals(buscar.getPassword())) {
                    temp.setAccion("abrirJuego");
                    temp.setPosicion(buscador);
                    usuario=temp;
                } else {
                    temp.setAccion("Contraseña Invalida");
                    usuario=temp;
                }
            } 
            buscador++;
        }
        if (temp.getAccion().equals("buscar")) {
                temp.setAccion("Usuario Invalido");
                usuario=temp;
        }
        
    }

    private void crearPlayer(Player nuevo) {
        boolean existe=false;
        for(Player p : archivo){
            if(p.getUsername().equals(nuevo.getUsername())){
                existe=true;
            }
        }
        if(existe==false){
            try {
                buscador = archivo.size();
            } catch (NullPointerException ex) {
                buscador = 1;
            }
            nuevo.setPuntajeMax(0);
            archivo.add(buscador, nuevo);
            guardarArchivo();
        }else{
            usuario.setAccion("Ya existe ese jugador");
        }
       
    }

    private void guardarPlayer(Player guardar) {
        archivo.set(guardar.getPosicion(), guardar);
        ordenarArchivo();
        guardarArchivo();
        buscador=0;
        
    }
    private void ordenarArchivo(){
        Player[] lista = new Player[archivo.size()];
        int it = 0;
        for (Player usuario : archivo) {
            lista[it]=usuario;
            it+=1;
        }
        Arrays.sort(lista);
        ArrayList<Player> temp=new ArrayList<Player>();
        for(int i=0;i<lista.length;i++){
            temp.add(i,lista[i]);
        }
        archivo=temp;
    }
    
    
   // public static void main(String arg[]) 
    @Override
    public void run(){

        Server s;
        
        ServerInicio("3000");
        
        usuario = null;
        
        
        boolean cerrar = false;
        OUTER:        
        while (cerrar != true) {
            aceptarConexion();
            obtenerFlujos();
            usuario =(Player) recibirMensaje();
            if (usuario!=null) {
                abrirArchivo();
                try {
                    if (usuario.getAccion()!= null) {
                        switch (usuario.getAccion()) {
                            case "buscar":
                                buscarPlayer(usuario);
                                enviarMensaje(usuario);
                                break;
                            case "crear":
                                crearPlayer(usuario);
                                enviarMensaje(usuario);
                                break;
                            case "guardar":
                                guardarPlayer(usuario);
                                enviarMensaje(usuario);
                                break;
                            case "darTabla":
                                ordenarArchivo();
                                enviarTabla(archivo);
                                break;
                            case "cerrar":
                                cerrar = true;
                                break OUTER;
                            default:
                                break;
                        }
                    }
                }catch (ServerExceptions ex) {
                    usuario.setAccion(ex.getLocalizedMessage());
                    enviarMensaje(usuario);
                }
            }
        }
        cerrarFlujos();
    }

}
