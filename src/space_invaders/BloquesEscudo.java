/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package space_invaders;

import javafx.scene.paint.Color;

/**
 *
 * @author Orlando & Alejandro
 * Esta clase se uso para clear los bloques que defienden al jugador
 */
public class BloquesEscudo {

    private int[][] barrera;
    private Color color;
    private double x, y;
    
    
    /**
     * Este constructor inicializa una matriz y le da forma a los bloques
     */
    public BloquesEscudo() {
        this.barrera = new int[][]{
            {0, 0, 1, 1, 1, 1, 1, 1, 0, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 0, 0, 0, 0, 1, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 1, 1}
        };
        this.color = Color.rgb(56, 252, 65);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int[][] getBarrera() {
        return barrera;
    }

    public Color getColor() {
        return color;
    }

    
    /**
     * @param i fila de la matriz
     * @param j columna de la matriz
     * Este metodo se usa para destriur un bloque de los escudos
     */
    public void destruirBloque(int i, int j) {
        barrera[i][j] = 0;
        if (i < barrera.length - 1) {
            barrera[i + 1][j] = 0;
            if (j < barrera[0].length - 1) {
                barrera[i][j + 1] = 0;
            }
            if (j > 0) {
                barrera[i][j - 1] = 0;
            }
        }
    }
}
