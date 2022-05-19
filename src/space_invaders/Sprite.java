    
package space_invaders;



import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 *
 * @author Orlando & Alejandro
 * 
 * Esta clase funciona como un elemento con imagen y propiedades como
 * posicion, ancho, alto y velocidad, del juego
 */
public class Sprite {
   private Image imagen;
   private double x,y,velX,velY,ancho,alto;
   private String dir;
   
   public Sprite(){
       this.x=0;
       this.y=0;
       this.velX=0;
       this.velY=0;
   }
   
    public Image getImagen() {
        return imagen;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getAncho() {
        return ancho;
    }

    public double getAlto() {
        return alto;
    }
   
    public void setPosicion(double x,double y){
       this.x=x;
       this.y=y;
    }
    
    public void setVel(double x,double y){
        this.velX=x;
        this.velY=y;
    }
    
    public void moverAuto(){
        this.x+=this.velX;
        this.y+=this.velY;
    }
   
   /**
    * @param dir recibe la ruta donde se encuentra la imagen a usar
    * Le da una imagen al Sprite
    */
   public void abrirImagen(String dir){
       try{
            Image temp= new Image(getClass().getResource(dir).toExternalForm());
            temp.exceptionProperty();
            setImagen(new Image(dir,temp.getWidth(),temp.getHeight(),true,false));
            
       }catch(Exception ex){System.out.println("Error: "+ex.getMessage()+" "+ dir);}
       
   }
    /**
     * 
     * @param imagen recibe una imagen
     * Iguala la imagen recibida y le da las propiedades de ancho y largo
     */
   public void setImagen(Image imagen){
       this.imagen=imagen;
       this.ancho=imagen.getWidth();
       this.alto=imagen.getHeight();
   }
   
   
   /**
    * @param gc recibe un GraphicsContext en el cual dibujar la imagen
    * Agrega una imagen al canvas
    */
   public void agregarImagen(GraphicsContext gc){
       gc.drawImage(imagen, this.x, this.y);
   }
   
   
   /**
    * Este metodo se usa para dibujar un rectangulo en la posicion del Sprite
    * esto se usa para usar el metodo de intersects
     * @return el rectangulo dibujado
    */
   public Rectangle2D getPerimetro(){
       return new Rectangle2D(this.x,this.y,this.ancho,this.alto);
   }
   
   /**
    * @param imagen Sprite que puede colisonar
    * 
    * Este metodo funciona para determinar si hubo una colision
    * 
    * @return si un Sprite colisiona con otro
    */
   public boolean interseccion(Sprite imagen){
       return imagen.getPerimetro().intersects(this.getPerimetro());
   }
   
   
   
}
