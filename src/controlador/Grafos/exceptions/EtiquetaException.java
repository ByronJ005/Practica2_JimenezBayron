package controlador.grafos.exceptions;

/**
 *
 * @author Asus
 */
public class EtiquetaException extends Exception{
    public EtiquetaException(){
        super("El vértice no está etiquetado");
    }
    
    public EtiquetaException(String msg){
        super(msg);
    }
}
