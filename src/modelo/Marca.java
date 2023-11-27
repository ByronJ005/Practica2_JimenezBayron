package modelo;

/**
 *
 * @author Asus
 * Esta clase es creada por separado para evitar que los usuarios escriban nombres erroneos
 */
public class Marca {
    private Integer id;
    private String nombre;
    private boolean estado;

    public Marca() {
    }
    
    public Marca(Integer id, String nombre, boolean estado) {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
}
