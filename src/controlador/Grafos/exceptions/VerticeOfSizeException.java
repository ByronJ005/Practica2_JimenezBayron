package controlador.grafos.exceptions;

/**
 *
 * @author Asus
 */
public class VerticeOfSizeException extends Exception{
    public VerticeOfSizeException(){
        super("Fuera de rango");
    }
    
    public VerticeOfSizeException(String msg){
        super(msg);
    }
}
