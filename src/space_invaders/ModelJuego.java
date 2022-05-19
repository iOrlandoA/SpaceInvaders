
package space_invaders;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author Orlando & Alejandro
 * Clase Modelo de la aplicacion
 * 
 * Contiene variables que se usan en el controlador para la funcionalidad
 * del juego, ademas como su set y get segun lo necesiten
 */
public class ModelJuego {
    
    private int numbVidas;
    private int puntos;
    private Sprite jugador;
    private ArrayList<Sprite> balas = new ArrayList<>();
    private ArrayList<Sprite> balasAlien = new ArrayList<>();
    private ArrayList<BloquesEscudo> escudos = new ArrayList<>();
    private boolean balaDisparada,disparoAlien, irDerecha,pausarJuego, existeUFO;
    private double maxIzq;
    private double maxDer;
    private Sprite ultimoAlien;
    private double ultimoAlienY;
    private Sprite[][] aliens = new Sprite[3][10];
    private int totalAliens;
    private Sprite UFO;

    public boolean isExisteUFO() {
        return existeUFO;
    }

    public void setExisteUFO(boolean existeUFO) {
        this.existeUFO = existeUFO;
    }
    
    public Sprite getUFO() {
        return UFO;
    }

    public void setUFO(Sprite UFO) {
        this.UFO = UFO;
    }
    
    

    public int getNumbVidas() {
        return numbVidas;
    }

    public int getPuntos() {
        return puntos;
    }

    public Sprite getJugador() {
        return jugador;
    }

    public ArrayList<Sprite> getBalas() {
        return balas;
    }

    public ArrayList<Sprite> getBalasAlien() {
        return balasAlien;
    }

    public ArrayList<BloquesEscudo> getEscudos() {
        return escudos;
    }
    

    public boolean isBalaDisparada() {
        return balaDisparada;
    }

    public boolean isDisparoAlien() {
        return disparoAlien;
    }

    public boolean isIrDerecha() {
        return irDerecha;
    }

    public boolean isPausarJuego() {
        return pausarJuego;
    }

    public double getMaxIzq() {
        return maxIzq;
    }

    public double getMaxDer() {
        return maxDer;
    }

    public Sprite getUltimoAlien() {
        return ultimoAlien;
    }

    public double getUltimoAlienY() {
        return ultimoAlienY;
    }

    public Sprite[][] getAliens() {
        return aliens;
    }

    public int getTotalAliens() {
        return totalAliens;
    }

    public void setNumbVidas(int numbVidas) {
        this.numbVidas = numbVidas;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public void setJugador(Sprite jugador) {
        this.jugador = jugador;
    }

    public void setBalaDisparada(boolean balaDisparada) {
        this.balaDisparada = balaDisparada;
    }

    public void setDisparoAlien(boolean disparoAlien) {
        this.disparoAlien = disparoAlien;
    }

    public void setIrDerecha(boolean irDerecha) {
        this.irDerecha = irDerecha;
    }

    public void setPausarJuego(boolean pausarJuego) {
        this.pausarJuego = pausarJuego;
    }

    public void setMaxIzq(double maxIzq) {
        this.maxIzq = maxIzq;
    }

    public void setMaxDer(double maxDer) {
        this.maxDer = maxDer;
    }

    public void setUltimoAlien(Sprite ultimoAlien) {
        this.ultimoAlien = ultimoAlien;
    }

    public void setUltimoAlienY(double ultimoAlienY) {
        this.ultimoAlienY = ultimoAlienY;
    }

    public void setTotalAliens(int totalAliens) {
        this.totalAliens = totalAliens;
    }
    
    
    
    
}
