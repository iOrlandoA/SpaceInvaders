/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package space_invaders;



import gamelogin.Cliente;
import gamelogin.Player;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 * Clase principal y controlador de la aplicacion
 *
 * @author Orlando & Alejandro
 */
public class ControllerJuego extends Application {

    private GraphicsContext gc;
    private Canvas canvasJuego;
    private Group root;
    private int ancho= 800;
    private int largo= 600;
    private int espacio=30;
    private int coordinateY = 70;
    private int coordinateX = espacio*3+10;
    private Stage stage;
    private Scene cuadro;
    private  AnimationTimer timer;
    private long cicloTime;
    private double alienT=0.40;
    private double ufoT = 0.40;
    private Text puntaje,vidas,pausa,gameOver;
    private Button reiniciar,salir;
    private ModelJuego model;
    private ViewJuego view;
    private Group escudo1 = new Group();
    private Group escudo2 = new Group();
    private Group escudo3 = new Group();
    private Player jugador;
    
    
    /**
     * @param temp
     * @throws Exception
     * En este metodo se crea un stage y se inicia la aplicacion del juego
     */
    
    public ControllerJuego(Player jugador){
         this.jugador=jugador;
    }
    
    @Override
    public void start(Stage temp)  {
       
        this.stage=temp;
        this.root=new Group();
        this.cuadro = new Scene(root);
        this.stage.setScene(cuadro);
        inicializarComponentes();
        crearAliens();
        gc=view.meterJugador(gc, model, ancho, largo);
        generarEscudos();
        
                                                                                                                    //Se va a Controller
        gestionTeclas();
        cicloJuego();
        stage.show();
        

    }
    
    /**
     * Este metodo se usa para inicializar los componentes necesarios del juego
     * para que puedar funcionar
     */
    public void inicializarComponentes(){
        view=new ViewJuego();
        stage=view.generarVentana(stage, largo, ancho);
        
        cuadro=view.pintarCuadro(cuadro);
        
        model=new ModelJuego();
        model.setNumbVidas(3);
        model.setPuntos(0);
        mostrarHUD();
        this.canvasJuego= new Canvas(ancho, largo);
        this.gc=canvasJuego.getGraphicsContext2D();
        this.root.getChildren().addAll(canvasJuego,vidas,puntaje,gameOver,pausa,reiniciar,salir, escudo1,escudo2,escudo3); 
        model.setIrDerecha(true);
        model.setPausarJuego(false);
    }
   
   
    
    
    /**
     * Este metodo usa un EventHandler que indica cuando una tecla es presionada
     * y sirve para hacer los movimientos del jugador y para disparar
     * 
     * Tambien detecta si el boton ESCAPE es presionado, lo que lleva al usuario
     * a un menu con una opcion para salir y otra para reiniciar
     */
    private void gestionTeclas(){
         stage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                switch(event.getCode().toString()){
                    case "RIGHT":
                        if(model.getJugador().getX()< ancho-50){
                            derechaJ();
                        }    

                    break;    
                    case "LEFT":
                        if(model.getJugador().getX()>25){
                            izquierdaJ();
                        }

                    break;
                    case "ESCAPE":
                        model.setPausarJuego(!model.isPausarJuego());
                        
                        pausa.setVisible(model.isPausarJuego());
                        juegoPausado();
                        
                    break;
                        
                        
                }
            }
            
           
           
        });
        
         /**
          * Este metodo usa un EventHandler que nos indica cuando una tecla se
          * suelta, esto nos permite tener movimiento y disparos mas nitidos
          */
        stage.getScene().setOnKeyReleased(new EventHandler<KeyEvent>(){
             @Override
             public void handle(KeyEvent event) {
                 switch(event.getCode().toString()){
                    case "RIGHT":
                        model.getJugador().setVel(0, 0);
                    break;    
                    case "LEFT":
                        model.getJugador().setVel(0, 0);
                    break;
                    case "SPACE":
                        if(!model.isBalaDisparada()){
                            disparar();
                            model.setBalaDisparada(true);
                        }
                        
                    
                }
             }
        });
    }
    
    
    /**
     * Este metodo se usa para mover al jugador a la izquierda
     */
    private void izquierdaJ() {
        model.getJugador().setVel(-5, 0);

    }

    /**
     * Este metodo se usa para mover al jugador a la derecha
     */
    private void derechaJ() {
        model.getJugador().setVel(5, 0);
    }
    
    
    /**
     * Pausa el juego y muestra un menu con una opcion para salir y otra para
     * reiniciar el juego
     */
    public void juegoPausado(){
        
        /**
         * Usa un EventHandler y se activa su funcion cuando el boton de
         * reiniciar es presionado Cuando este se presiona, se crea un nuevo
         * juego y todos valores vuelven a su punto inicial
         */
        reiniciar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gameOver.setVisible(false);
                reiniciar.setVisible(false);
                salir.setVisible(false);
                pausa.setVisible(false);
                mostrarVidas(3);
                nuevoJuego();
                model.setPausarJuego(false);
            }
        });
        
        /**
         * Usa un EventHandler y se activa su funcion cuando el boton de
         * salir es presionado
         * Cuando este se presiona, sale del juego y devuelve al usuario al menu
         * principal
         */
        salir.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (model.getPuntos() > jugador.getPuntajeMax()) {
                    Cliente cliente=new Cliente();
                    jugador.setAccion("guardar");
                    jugador.setPuntajeMax(model.getPuntos());
                    cliente.inicio(jugador);
                }
                
                
                stage.close();
            }
        });

        if (model.isPausarJuego() == true) {

            timer.stop();
            reiniciar.setVisible(true);
            salir.setVisible(true);

        } else {
            timer.start();
            pausa.setVisible(false);
            reiniciar.setVisible(false);
            salir.setVisible(false);
        }
    }
    
    
    /**
     * Este metodo se encarga de mostrar elementos graficos al usuario, asi como
     * indicarle que perdio o que el juego se encuentra pausado
     */
    private void mostrarHUD() {
  
        vidas = view.setVidas(vidas, model, ancho, espacio);

        puntaje = view.setPuntaje(puntaje, model, ancho, espacio);

        gameOver = view.setGameOver(gameOver, ancho, largo);

        pausa = view.setPausa(pausa, ancho, largo);

        reiniciar = view.setReiniciar(reiniciar, ancho, largo);
        salir = view.setSalir(salir, ancho, largo);
        
    }
    
    /**
     * @param quitar la cantidad de vidas a restar 
     * 
     * Este metodo le resta una cantidad de vidas al jugador cuando se llama
     * Tambien actualiza las vidas para que se vean reflejadas
     * las vidas actuales
     */
    public void mostrarVidas(int quitar){
       
       model.setNumbVidas(model.getNumbVidas()-quitar);
        
       vidas.setText("Vidas: "+model.getNumbVidas());
       
    }
    
    /**
     * Actualiza los puntos del jugador cuando acerto un golpe a un enemigo
     */
    public void actualizarPuntos(){
       puntaje.setText("Puntaje: "+model.getPuntos());
    }
    
    
    
    /**
     * Este metodo se usa para el disparo del jugador
     * Dispara una bala desde la posicion actual de la nave
     * Agrega la bala al canvas del juego y a un ArrayList de balas
     */
    private void disparar(){
        Sprite bala=new Sprite();
        bala.abrirImagen("/space_invaders/imagenes/balaNave.png");
        bala.setPosicion(model.getJugador().getX(), model.getJugador().getY()-15);
        bala.setVel(0, -10);
        bala.agregarImagen(gc);
        model.getBalas().add(bala);
        
    }
    
    
    /**
     * Este metodo llena el ArrayList de aliens dependiendo de la posicion con
     * un alien pequeño, mediano o grande
     */
    private void crearAliens() {
        for (int y = 70, i = 0; y < ancho / 2 + espacio && i < 3; y += espacio, i++) {
            for (int x = espacio*3+10, j = 0; x < 660 && j < 10; x += espacio * 2, j++) {
                if (y < 80) {
                    model.getAliens()[i][j] = darAlien(x, y, "/space_invaders/imagenes/bigAlien.png");
                    gc.drawImage(model.getAliens()[i][j].getImagen(), x, y);
                } else if (y < 130) {
                    model.getAliens()[i][j] = darAlien(x, y, "/space_invaders/imagenes/mediumAlien.png");
                    gc.drawImage(model.getAliens()[i][j].getImagen(), x, y);
                } else {
                    model.getAliens()[i][j] = darAlien(x, y, "/space_invaders/imagenes/littleAlien.png");
                    gc.drawImage(model.getAliens()[i][j].getImagen(), x, y);
                }
                model.setTotalAliens(model.getTotalAliens()+1);;
            }
        }
    }

    
    /**
     * Este metodo es usado para darle una imagen a los aliens, es llamado en el
     * metodo de crearAliens
     */
    private Sprite darAlien(int x, int y, String dir) {
        Sprite nuevo = new Sprite();
        nuevo.abrirImagen(dir);
        nuevo.setPosicion(x, y);
        return nuevo;
    }

    
    /**
     * Con este metodo animamos a los aliens, se llama en el ciclo de juego para
     * dar la sensacion de que se mueven
     */
    private void animarAliens() {
        for (int y = coordinateY, i = 0; y < largo-100 && i < 3; y += espacio, i++) {
            for (int x = coordinateX, j = 0; x < ancho-50 && j < 10; x += espacio *2, j++) {
                if (model.getAliens()[i][j] != null) {
                    model.getAliens()[i][j].setPosicion(x, y);
                    if (y < 80) {
                        gc.drawImage(model.getAliens()[i][j].getImagen(), x, y);
                    } else if (y < 130) {
                        gc.drawImage(model.getAliens()[i][j].getImagen(), x, y);
                    } else {
                        gc.drawImage(model.getAliens()[i][j].getImagen(), x, y);
                    }
                }
            }
        }
    }
   

    
    /**
     * Este metodo se usa para el movimiento de los invasores cuando llegan a
     * una de las esquinas de la pantalla
     */
    private void getMaxEspacio() {
        model.setMaxIzq(0.00);
        model.setMaxDer(0.00);
        
        for (int i = 0; i < model.getAliens().length; i++) {
            for (int j = 0; j < model.getAliens()[0].length; j++) {
                if (model.getAliens()[i][j] != null) {
                    if (model.getMaxIzq() > 0.00) {
                        model.setMaxIzq(Math.min(model.getMaxIzq(), model.getAliens()[i][j].getX()));
                    } else {
                        model.setMaxIzq(model.getAliens()[i][j].getX()); 
                    }
                    break;
                }
            }
        }
        
        for (int i = 0; i < model.getAliens().length; i++) {
            for (int j = model.getAliens()[0].length - 1; j >= 0; j--) {
                if (model.getAliens()[i][j] != null) {
                    if (model.getMaxDer() > 0.00) {
                         model.setMaxDer(Math.max(model.getMaxDer(), model.getAliens()[i][j].getX()));
                    } else {
                         model.setMaxDer(model.getAliens()[i][j].getX());
                    }
                    break;
                }
            }
        }

    }
   
   
   
    /**
     * La funcion de este metodo es renderizar y actualizar los componentes
     * graficos del juego, funciona como nuestro Thread principal 
     */
    public void cicloJuego() {
        long tiempoInicial = System.nanoTime();
        timer = new AnimationTimer() {

            @Override
            public void handle(long tiempoActual) {
                cicloTime = (long) ((tiempoActual - tiempoInicial) / 1000000000.0);
                tiempoActual = tiempoActual;
                gc.clearRect(0, 30, ancho, largo);
                
                model.setUltimoAlien(getUltimo());
                
                llegoAbajo();
                getMaxEspacio();
                actualizarPuntos();
                animarAliens();
                lanzarUFO();
                estadoUFO();
                actualizarJugador();
                actualizarBalaAlien();
                actualizarBalas();
                estadoBala();
                alienT += 1/(model.getTotalAliens() * 1.0);
                if (alienT >= 1.5) {
                    if (model.isIrDerecha() == true) {
                        if (model.getMaxDer() < 690) {
                            coordinateX += 15;
                        } else {
                            coordinateY += 25;
                            model.setIrDerecha(false);
                        }
                    } else if (model.isIrDerecha() == false) {
                        if (model.getMaxIzq() > 50) {
                            coordinateX -= 15;
                        } else {
                            coordinateY += 25;
                            model.setIrDerecha(true);
                        }
                    }
                    dispararAlien();
                    alienT=0;
                    
                    
                    
                }
                if (model.isExisteUFO()) {
                    ufoT += 0.013;
                    if (ufoT >= 0.140) {
                      
                        ufoT = 0;
                    }
                }
            }
        };
        timer.start();
    }


    /**
     * Este metodo sirve para que el movimiento del jugador se vea de una
     * manera mas fluida
     */
    private void actualizarJugador(){
          if (model.getJugador() != null) {
            if (model.getJugador().getX() < 25) {
                model.getJugador().setPosicion(model.getJugador().getX() + 1, model.getJugador().getY());
                model.getJugador().setVel(0, 0);
            } else if (model.getJugador().getX() > ancho- 50) {
                model.getJugador().setPosicion(model.getJugador().getX() - 1, model.getJugador().getY());
                model.getJugador().setVel(0, 0);
            }
            
            model.getJugador().agregarImagen(gc);
            model.getJugador().moverAuto();
        }
    }
    
    
    /**
     * La funcion de este metodo es actualizar las balas del jugador a la hora
     * de dispararlas
     */
    private void actualizarBalas(){
        if(model.isBalaDisparada()==true){
            Sprite bala=model.getBalas().get(0);
            bala.agregarImagen(gc);
            bala.moverAuto();
            if(bala.getY()<=30){
                model.getBalas().clear();
                model.setBalaDisparada(false);
            }
            
        }
    } 
       
    
    
    /**
     * Este metodo usa la funcion intersects de la clase Rectangle para
     * determinar si la bala de la nave ha tocado a un alien, ademas
     * le suma los puntos al jugador dependiendo del alien que intersecto,
     * esto se determina con el ancho de la imagen del alien
     */    
    private boolean colisionAlien() {
        for (int i = 0; i < model.getAliens().length; i++) {
            for (int j = 0; j < model.getAliens()[0].length; j++) {
                if (model.getAliens()[i][j] != null) {
                    if (model.getAliens()[i][j].interseccion(model.getBalas().get(0))) {
                        switch ((int) model.getAliens()[i][j].getAncho()) {
                            case 28:
                                model.setPuntos(model.getPuntos()+10);
                                break;
                            case 30:
                                model.setPuntos(model.getPuntos()+20);
                                break;
                            case 32:
                                model.setPuntos(model.getPuntos()+40);
                                break;
                        }
                        actualizarPuntos();
                        model.setTotalAliens(model.getTotalAliens()-1);
                        model.getAliens()[i][j] = null;
                        if(model.getTotalAliens()<=0){
                            nuevoJuego();
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    
    /**
     * Este metodo se usa para saber si la bala fue disparada, y si choca con un
     * alien o una barrera, limpiar el ArrayList de balas
     */
    
    private void estadoBala() {
        
        if(model.isBalaDisparada()){
            Sprite bala = model.getBalas().get(0);
            bala.agregarImagen(gc);
            bala.moverAuto();

            if (colisionAlien() || bala.getY() <= 30 || revisarEscudo(bala)) {
                model.getBalas().clear();
                model.setBalaDisparada(false); 
                
                
            }
        }
    }
    
    
    /**
     * Este metodo se usa para el disparo de los aliens
     * Dispara una bala desde la posicion actual de uno de los aliens
     * Agrega la bala al canvas del juego y a un ArrayList de balasAlien
     * 
     * La velocidad de la bala depende del tipo de alien que la disparo
     */
    public void dispararAlien(){
        Sprite balaAlien = new Sprite();
        balaAlien.abrirImagen("/space_invaders/imagenes/balaAlien.png");
        for (int i = (int)(Math.random() * model.getAliens().length - 1); i >= 0; i--) {
            if (model.isDisparoAlien()==true) {
                break;
            }
            for (int j = (int)(Math.random() * model.getAliens()[i].length); j >= 0; j--) {
                if (model.getAliens()[i][j] != null) {
                    balaAlien.setPosicion(model.getAliens()[i][j].getX(),
                    model.getAliens()[i][j].getY());
                    switch((int)model.getAliens()[i][j].getAncho()){
                        case 28:
                            balaAlien.setVel(0, 2);
                        break;
                        case 30:
                            balaAlien.setVel(0, 3);
                        break;
                        case 32:
                            balaAlien.setVel(0, 5);
                        break;
                    }
                    
                    model.getBalasAlien().add(balaAlien);
                    model.setDisparoAlien(true);
                    break;
                }
            }
        }
    }
    
   
    /**
     * @param balaAlien recibe una bala de alien para saber si colisiono
     * con el jugador
     * 
     * La funcion de este metodo es determinar si el jugador recibio un disparo,
     * si es asi, se le resta una vida
     * 
     * @return verdadero si el jugador recibio un disparo
     */

    public boolean colisionJugador(Sprite balaAlien){
        
        if (model.getNumbVidas()!=0 && model.getJugador().interseccion(balaAlien)) {
            mostrarVidas(1);
            if(model.getNumbVidas()==0){
                terminado();
            }
            return true;
        }
        return false;
   
    }
    
    
    /**
     * Este metodo actualiza las balas de los aliens y limpia el ArrayList
     * si esta colisiona con un escudo o con el jugador
     */
    public void actualizarBalaAlien(){
        if (model.isDisparoAlien()) {
            Sprite balaAlien = model.getBalasAlien().get(0);
            balaAlien.agregarImagen(gc);
            balaAlien.moverAuto();
            if (balaAlien.getY() >= ancho - espacio || revisarEscudo(balaAlien)) {
                model.getBalasAlien().clear();
                model.setDisparoAlien(false);
            }
            if(colisionJugador(balaAlien)){
                model.getBalasAlien().clear();
                model.setDisparoAlien(false);
            } 
        }
    }
    
                                                                                        //metodos del UFO
    
    
    /**
     * Este metodo agrega al invasor rojo al canvas del juego
     */
    private void darUFO() {
        model.setUFO(new Sprite());
        model.getUFO().abrirImagen("/space_invaders/imagenes/UFO.png");
        model.getUFO().setPosicion(0, 50);
        model.getUFO().agregarImagen(gc);

    }
    
    
    /**
     * Este metodo determina si hay que lanzar al invasor rojo "UFO" 
     * en intervalos aleatorios
     */
    private boolean aleatorioUFO() {
        double random = Math.random();
        if (random < 0.003) {
            darUFO();
            return true;
        }
        return false;
    }
    
    
    /**
     * Este metodo se encarga del movimiento del UFO
     * Tambien se encarga de darle al jugador un cantidad aleatoria de puntos
     * entre 80-150 si logra golpear al UFO
     */ 
    private void estadoUFO() {
        Random random = new Random();
        if (model.isExisteUFO() && model.getUFO().getX() < ancho) {
            model.getUFO().agregarImagen(gc);
            model.getUFO().moverAuto();
            if (model.isBalaDisparada()) {
                if (model.getUFO().interseccion(model.getBalas().get(0))) {

                    model.setPuntos(model.getPuntos() + random.nextInt(150 - 80) + 80);
                    actualizarPuntos();
                    model.getBalas().clear();
                    model.setBalaDisparada(false);
                    model.setUFO(null);
                    model.setExisteUFO(false);
                }
            }
        } else {
             model.setUFO(null);
             model.setExisteUFO(false);
        }
    }
    
    
    /**
     * Este metodo se encarga de lanzar un UFO si este no existe
     */
    private void lanzarUFO() {
        if (!model.isExisteUFO() && aleatorioUFO()) {
             model.setExisteUFO(true);
             model.getUFO().setVel(5, 0);
        }
    }
    
    
                                                                                    //metodos de escudos
    
    
    /**
     * Se usa para determinar la posicion de cada uno de los escudos
     */
    private Group getGrupoEscudo(int corX) {
        if (corX <= 120) {
            return escudo1;
        } else if (corX <= 320) {
            return escudo2;
        } else {
            return escudo3;
        }      
    }

    
    /**
     * Este metodo se encarga de pintar los escudos que protejen al
     * jugador
     */
    private void pintarEscudos(int inicio, int[][] matriz, Group grupo, BloquesEscudo escudo) {
        escudo.setX(inicio);
        escudo.setY(largo - 150);
        model.getEscudos().add(escudo);
        grupo.getChildren().clear();

        for (int i = 0, y = largo - 150; i < matriz.length; i++, y += 8) {
            for (int j = 0, x = inicio; j < matriz[0].length && x <= ancho + espacio; j++, x += 8) {
                if (matriz[i][j] != 0) {
                    Rectangle tempR = new Rectangle();
                    tempR.setFill(escudo.getColor());
                    tempR.setWidth(8);
                    tempR.setHeight(8);
                    tempR.relocate(x, y);
                    grupo.getChildren().add(tempR);
                }
            }
        }
    }

    /**
     * Este metodo funciona para generar los 3 escudos 
     */
    private void generarEscudos(){
        for (int x = 120, i = 0; x < ancho && i<3; x += 200, i++) {
            BloquesEscudo temp = new BloquesEscudo();
            int [][]matrizTemp= temp.getBarrera();
            System.out.println(x);
            Group grupoTemp = getGrupoEscudo(x);
            pintarEscudos(x, matrizTemp, grupoTemp, temp);
        }
    }

    /**
     * La funcion de este metodo es determinar si el escudo ha sido golpeado
     * por una bala, de ser asi, destruye el bloque(pixel) donde se golpeo
     * 
     * @return verdadero si es golpeado, falso si no lo es
     */
    private boolean revisarEscudo(Sprite bala) {
        for (BloquesEscudo escudos : model.getEscudos()) {
            int[][] matriz = escudos.getBarrera();
            for (int f = 0; f < matriz.length; f++) {
                for (int c = 0; c < matriz[0].length; c++) {
                    if (matriz[f][c] != 0) {
                        if (bala.getX() >= escudos.getX() - 5
                                && bala.getX() <= escudos.getX() + c * 8 + 5
                                && bala.getY() >= escudos.getY() - 5
                                && bala.getY() >= escudos.getY() + f * 8 + 5) {
                            
                            escudos.destruirBloque(f, c);
                            Group grupo = getGrupoEscudo((int)escudos.getX());
                            pintarEscudos((int)escudos.getX(), matriz, grupo, escudos);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Este metodo se usa para determinar si los aliens llegaron al final de
     * la pantalla, de ser asi, se pierde el juego
     */
    private void llegoAbajo() {
        if (model.getUltimoAlien() != null) {
            model.setUltimoAlienY(model.getUltimoAlien().getY());
            if (model.getUltimoAlienY() >= this.largo - 100 - model.getUltimoAlien().getAlto()) {

                timer.stop();
                mostrarVidas(3);
                terminado();
            }
        }
    }

    
    /**
     * Este metodo se usa para saber cual es el ultimo alien con vida
     * @return el ultimo alien vivo
     */
    private Sprite getUltimo() {
        for (int i = model.getAliens().length - 1; i >= 0; i--) {
            for (int j = 0; j < model.getAliens()[0].length; j++) {
                if (model.getAliens()[i][j] != null) {
                    return model.getAliens()[i][j];
                }
            }
        }
        return null;
    }

    /**
     * Este metodo se usa para terminar el juego
     * Salta el menu de salir y de reiniciar
     */
    public void terminado() {
        timer.stop();
        gameOver.setVisible(true);
        model.setPausarJuego(true);
        juegoPausado();
    }

    /**
     * Este metodo se usa para reiniciar el juego
     */
    private void nuevoJuego() {
        timer.stop();

        if (model.getNumbVidas() <= 0) {
            limpiarDatos();
            model.getEscudos().clear();
            generarEscudos();
        }
        limpiarJuego();

        coordinateY = 80;
        coordinateX = ancho / 3 - (espacio * 3);
        crearAliens();

        gc=view.meterJugador(gc, model, ancho, largo);
        cicloJuego();
    }

    /**
     * Este metodo se usa para poner en su valor inicial las variables del
     * juego
     */
    public void limpiarJuego() {

        alienT = 0;

        model.setDisparoAlien(false);
        model.setBalaDisparada(false);
        model.setExisteUFO(false);
        model.getBalasAlien().clear();
        model.getBalas().clear();
        model.setUFO(null);
    }
    
    /**
     * Este metodo limpia los datos presentados al usuario
     */

    public void limpiarDatos() {
        model.setNumbVidas(3);
        model.setTotalAliens(0);
        model.setPuntos(0);
        mostrarVidas(0);
        actualizarPuntos();
    }
    
    public ModelJuego getModel(){
        return this.model;
    }
    
}
