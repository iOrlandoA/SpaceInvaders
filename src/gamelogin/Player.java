/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamelogin;

import java.io.Serializable;

/**
 *
 * @author Alejandro
 */
public class Player implements Comparable<Player>, Serializable{
    
    private String username;
    private String password;
    private Integer puntajeMax;
    private String accion;
    private int posicion;
    public Player() {
    }

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }


    public int getPuntajeMax() {
        return puntajeMax;
    }

    public void setPuntajeMax(int puntajeMax) {
        this.puntajeMax = puntajeMax;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }
    
    @Override
    public String toString() {
        return "Player{" + "username=" + username + ", password=" + password + '}';
    }  

    @Override
    public int compareTo(Player o) {
        return o.puntajeMax.compareTo(this.puntajeMax);
    }
    
}
