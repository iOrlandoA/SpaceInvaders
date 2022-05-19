
package gamelogin;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class JugadorPublico {
    private SimpleStringProperty nombre=new SimpleStringProperty();
    private SimpleIntegerProperty puntos=new SimpleIntegerProperty();
    private SimpleIntegerProperty puesto=new SimpleIntegerProperty();

    public JugadorPublico() {
    }

    public JugadorPublico(String nombre, int puntos, int puesto) {
        
        this.nombre.set(nombre);
        this.puntos.set(puntos);
        this.puesto.set(puesto);
    }
    
    
    public String getNombre() {
        return this.nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public int getPuntos() {
        return puntos.get();
    }

    public void setPuntos(int puntos) {
        this.puntos.set(puntos);
    }

    public int getPuesto() {
        return puesto.get();
    }

    public void setPuesto(int puesto) {
        this.puesto.set(puesto);
    }
    
    
    
}
