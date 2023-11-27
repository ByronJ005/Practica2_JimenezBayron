package controlador.TDALista.exceptions;

/**
 *
 * @author Asus
 */
public class LlenoException extends Exception{

    /**
     * Creates a new instance of <code>VacioException</code> without detail
     * message.
     */
    public LlenoException() {
    }

    /**
     * Constructs an instance of <code>VacioException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public LlenoException(String msg) {
        super(msg);
    }
}
