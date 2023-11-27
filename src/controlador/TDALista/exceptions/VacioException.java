package controlador.TDALista.exceptions;

/**
 *
 * @author Asus
 */
public class VacioException extends Exception{

    /**
     * Creates a new instance of <code>VacioException</code> without detail
     * message.
     */
    public VacioException() {
    }

    /**
     * Constructs an instance of <code>VacioException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public VacioException(String msg) {
        super(msg);
    }
}
