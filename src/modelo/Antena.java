package modelo;
import controlador.AntenaController;
import controlador.listas.LinkedList;
import controlador.listas.exceptions.VacioException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Asus
 */
public class Antena {
    private Integer id;
    private String codigo;
    private String foto1;
    private String foto2;
    private String foto3;
    private Double altura;
    private Double latitud;
    private Double longitud;
    private Date fechaInstalacion;
    private TipoAntena tipo;

    public Antena() {
    }

    public Antena(Integer id, String codigo, String foto1, String foto2, String foto3, Double altura, Double latitud, Double longitud, Date fechaInstalacion, TipoAntena tipo) {
        this.id = id;
        this.codigo = codigo;
        this.foto1 = foto1;
        this.foto2 = foto2;
        this.foto3 = foto3;
        this.altura = altura;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fechaInstalacion = fechaInstalacion;
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFoto1() {
        return foto1;
    }

    public void setFoto1(String foto1) {
        this.foto1 = foto1;
    }

    public String getFoto2() {
        return foto2;
    }

    public void setFoto2(String foto2) {
        this.foto2 = foto2;
    }

    public String getFoto3() {
        return foto3;
    }

    public void setFoto3(String foto3) {
        this.foto3 = foto3;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }
    
    

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getFechaInstalacion() {
        return fechaInstalacion;
    }

    public void setFechaInstalacion(Date fechaInstalacion) {
        this.fechaInstalacion = fechaInstalacion;
    }

    public TipoAntena getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        switch (tipo) {
            case "Dipolo":
                this.tipo = TipoAntena.Dipolo;
                break;
            case "Plana":
                this.tipo = TipoAntena.Plana;
                break;
            case "Yagi":
                this.tipo = TipoAntena.Yagi;
                break;
            case "Parabolica":
                this.tipo = TipoAntena.Parabolica;
                break;
            case "Ranura":
                this.tipo = TipoAntena.Ranura;
                break;
            case "Mircrostrip":
                this.tipo = TipoAntena.Mircrostrip;
                break;
            default:
                throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        return codigo;
    }
    
    public static void main(String[] args) {
        try {
            LinkedList<Antena> lista = new AntenaController().listall();
            
            System.out.println(lista.print());
        } catch (Exception ex) {
            Logger.getLogger(Antena.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
