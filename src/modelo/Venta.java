package modelo;

import java.util.Date;

/**
 *
 * @author Asus
 */
public class Venta {
    private Integer id_venta;
    private Integer id_vendedor;
    private Integer id_auto;
    private String codigoVenta;
    private Date fecha;
    private String descripcion;

    public Venta() {
    }

    public Venta(Integer id_venta, Integer id_vendedor, Integer id_auto, String codigoVenta, Date fecha, String descripcion) {
        this.id_venta = id_venta;
        this.id_vendedor = id_vendedor;
        this.id_auto = id_auto;
        this.codigoVenta = codigoVenta;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }

    public Integer getId_venta() {
        return id_venta;
    }

    public void setId_venta(Integer id_venta) {
        this.id_venta = id_venta;
    }

    public Integer getId_vendedor() {
        return id_vendedor;
    }

    public void setId_vendedor(Integer id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    public Integer getId_auto() {
        return id_auto;
    }

    public void setId_auto(Integer id_auto) {
        this.id_auto = id_auto;
    }

    public String getCodigoVenta() {
        return codigoVenta;
    }

    public void setCodigoVenta(String codigoVenta) {
        this.codigoVenta = codigoVenta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
    
}
