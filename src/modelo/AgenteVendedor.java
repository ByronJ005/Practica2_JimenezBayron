package modelo;

/**
 *
 * @author Asus
 */
public class AgenteVendedor {
    private Integer id;
    private String dni;
    private String ruc;
    private String nombres;
    private String apellidos;
    private String direccion;
    private String telefono;

    public AgenteVendedor() {
    }

    public AgenteVendedor(Integer id, String dni, String ruc, String nombres, String apellidos, String direccion, String telefono) {
        this.id = id;
        this.dni = dni;
        this.ruc = ruc;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return nombres + " "+apellidos;
    }
    
    
}
