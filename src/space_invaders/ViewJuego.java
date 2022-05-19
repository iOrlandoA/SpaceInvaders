package space_invaders;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Orlando & Alejandro
 * 
 * Clase view de la aplicacion
 */
public class ViewJuego {
    
    
    
    public Stage generarVentana(Stage temp, int largo, int ancho){
        temp.setTitle("SPACE INVADERS");
        temp.setMaxHeight(largo);
        temp.setMaxWidth(ancho);
        temp.setResizable(false);
        return temp;
    }
    
    
    public Scene pintarCuadro(Scene cuadro){
        cuadro.setFill(Color.BLACK);
        return cuadro;
    }
    
     /**
     * Este metodo se usa para crear la imagen de la nave del jugador y
     * agregarla al canvas del juego
     */
    public GraphicsContext meterJugador(GraphicsContext gc, ModelJuego model, int ancho,int largo){
        model.setJugador(new Sprite());
        model.getJugador().abrirImagen("/space_invaders/imagenes/jugador.png");
        model.getJugador().setPosicion(ancho/2,largo-50);
        model.getJugador().agregarImagen(gc);
        return gc;
    }
    
    
    
    public Text setVidas(Text vidas, ModelJuego model, int ancho,int espacio){
        vidas = new Text("Vidas: "+model.getNumbVidas());
        vidas.setFill(Color.GREEN);
        vidas.setFont(Font.font("family", FontWeight.EXTRA_BOLD, 16));
        vidas.setX(ancho - 75);
        vidas.setY(espacio);
        return vidas;
    }
    
    
    public Text setPuntaje(Text puntaje, ModelJuego model, int ancho,int espacio){
        puntaje = new Text("Puntaje: "+model.getPuntos());
        puntaje.setFill(Color.GREEN);
        puntaje.setFont(Font.font("family", FontWeight.EXTRA_BOLD, 16));
        puntaje.setX(10);
        puntaje.setY(espacio);
        return puntaje;
    }
    
    
    public Text setGameOver(Text gameOver, int ancho, int largo){
        gameOver = new Text("GAME OVER");
        gameOver.setFill(Color.GREEN);
        gameOver.setFont(Font.font("family", FontWeight.EXTRA_BOLD,40));
        gameOver.setX(ancho/2-125);
        gameOver.setY(largo/2-50);
        gameOver.setVisible(false);
        return gameOver;
    }
    
        
    
    public Text setPausa(Text pausa,int ancho,int largo){
        pausa =new Text("PAUSED");
        pausa.setFill(Color.GREEN);
        pausa.setFont(Font.font("family", FontWeight.EXTRA_BOLD,40));
        pausa.setX(ancho/2-100);
        pausa.setY(largo/2-50);
        pausa.setVisible(false);
        return pausa;
    }
    
    
    public Button setReiniciar(Button reiniciar,int ancho, int largo){
        reiniciar=new Button("REINICIAR");
        reiniciar.setDefaultButton(true);
        reiniciar.setLayoutX(ancho/2+25);
        reiniciar.setLayoutY(largo/2);
        reiniciar.setVisible(false);
        return reiniciar;
    }
    
    
    public Button setSalir(Button salir, int ancho, int largo){
        salir=new Button("SALIR");
        salir.setDefaultButton(true);
        salir.setLayoutX(ancho/2-150);
        salir.setLayoutY(largo/2);
        salir.setVisible(false);
        return salir;
    }
    
}
