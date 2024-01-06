package modelo;
import controlador.Listas.AutoControllerListas;
import controlador.Listas.VendedorControllerListas;
import controlador.Listas.VentaControllerListas;
import controlador.TDALista.exceptions.VacioException;
import controlador.listas.MarcaControllerListas;
import java.text.SimpleDateFormat;
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

    /*public Boolean comparar(Marca c, String field, Integer type){
        switch (type) {
            case 0:
                if(field.equalsIgnoreCase("nombre"))
                    return getNombre().compareTo(c.getNombre()) < 0;
                else if(field.equalsIgnoreCase("id"))
                    return getId().intValue() < c.getId().intValue();
            case 1:
                if(field.equalsIgnoreCase("nombre"))
                    return getNombre().compareTo(c.getNombre()) > 0;
                else if(field.equalsIgnoreCase("id"))
                    return getId().intValue() > c.getId().intValue();
            default:
                throw new AssertionError();
        }
    /*AutoControllerListas ac = new AutoControllerListas();
        VendedorControllerListas vc = new VendedorControllerListas();
        MarcaControllerListas mc = new MarcaControllerListas();
        Auto autoActual = ac.getAutos().get(getId_auto()-1);
        AgenteVendedor vendedorActual = vc.getVendedores().get(getId_vendedor()-1);
        Marca marcaActual = mc.getMarcas().get(ac.getAutos().get(getId_auto()-1).getId_marca()-1);
        Auto autoComp = ac.getAutos().get(v.getId_auto()-1);
        AgenteVendedor vendedorComp = vc.getVendedores().get(v.getId_vendedor()-1);
        Marca marcaComp = mc.getMarcas().get(ac.getAutos().get(v.getId_auto()-1).getId_marca()-1);
    }*/
    
    public Boolean comparar(Venta v, String field, Integer type) throws VacioException{
        Auto[] autos = new AutoControllerListas().getAutos().toArray();
        AgenteVendedor[] vendedores = new VendedorControllerListas().getVendedores().toArray();
        Marca[] marcas = new MarcaControllerListas().getMarcas().toArray();
        Auto autoActual = autos[getId_auto() - 1];
        AgenteVendedor vendedorActual = vendedores[getId_vendedor() - 1];
        Marca marcaActual = marcas[autos[getId_auto() - 1].getId_marca() - 1];
        Auto autoComp = autos[v.getId_auto() - 1];
        AgenteVendedor vendedorComp = vendedores[v.getId_vendedor() - 1];
        Marca marcaComp = marcas[autos[v.getId_auto() - 1].getId_marca() - 1];
        switch (type) {
            case 0:
                if(field.equalsIgnoreCase("marca"))
                    return marcaActual.getNombre().compareToIgnoreCase(marcaComp.getNombre()) < 0;
                else if(field.equalsIgnoreCase("agente vendedor"))
                    return vendedorActual.toString().compareTo(vendedorComp.toString()) < 0;
                else if(field.equalsIgnoreCase("placa"))
                    return autoActual.getPlaca().compareTo(autoComp.getPlaca()) < 0;
                else if(field.equalsIgnoreCase("precio"))
                    return autoActual.getPrecio().compareTo(autoComp.getPrecio()) < 0;
                else if(field.equalsIgnoreCase("fecha"))
                    return getFecha().before(v.getFecha());
            case 1:
                if(field.equalsIgnoreCase("marca"))
                    return marcaActual.getNombre().compareToIgnoreCase(marcaComp.getNombre()) > 0;
                else if(field.equalsIgnoreCase("agente vendedor"))
                    return vendedorActual.toString().compareTo(vendedorComp.toString()) > 0;
                else if(field.equalsIgnoreCase("placa"))
                    return autoActual.getPlaca().compareTo(autoComp.getPlaca()) > 0;
                else if(field.equalsIgnoreCase("precio"))
                    return autoActual.getPrecio().compareTo(autoComp.getPrecio()) > 0;
                else if(field.equalsIgnoreCase("fecha"))
                    return getFecha().after(v.getFecha());
            default:
                throw new AssertionError();
        }
    }
    
    public Integer esIgual(String field, Object valor){
        Auto[] autos = new AutoControllerListas().getAutos().toArray();
        AgenteVendedor[] vendedores = new VendedorControllerListas().getVendedores().toArray();
        Marca[] marcas = new MarcaControllerListas().getMarcas().toArray();
        Auto autoActual = autos[getId_auto() - 1];
        AgenteVendedor vendedorActual = vendedores[getId_vendedor() - 1];
        Marca marcaActual = marcas[autos[getId_auto() - 1].getId_marca() - 1];
        if(field.equalsIgnoreCase("marca")){
            return marcaActual.getNombre().compareToIgnoreCase(valor.toString());
        }else if(field.equalsIgnoreCase("agente vendedor")){
            return vendedorActual.toString().compareToIgnoreCase(valor.toString());
        }else if(field.equalsIgnoreCase("placa")){
            return autoActual.getPlaca().compareToIgnoreCase(valor.toString());
        }else if(field.equalsIgnoreCase("precio"))
            return autoActual.getPrecio().compareTo(Double.parseDouble(valor.toString()));
        else if(field.equalsIgnoreCase("fecha")){
            SimpleDateFormat formatofe = new SimpleDateFormat("yy-MM-dd");
            String fecha = formatofe.format(getFecha());
            return fecha.compareTo(valor.toString());
        }
        return null;
    }
    
    public Integer esSimilar(String field, Object valor){
        Auto[] autos = new AutoControllerListas().getAutos().toArray();
        AgenteVendedor[] vendedores = new VendedorControllerListas().getVendedores().toArray();
        Marca[] marcas = new MarcaControllerListas().getMarcas().toArray();
        Auto autoActual = autos[getId_auto() - 1];
        AgenteVendedor vendedorActual = vendedores[getId_vendedor() - 1];
        Marca marcaActual = marcas[autos[getId_auto() - 1].getId_marca() - 1];
        if(field.equalsIgnoreCase("marca")){
            if(marcaActual.getNombre().toLowerCase().contains(valor.toString().toLowerCase()))
                return 0;
            else
                return 1;
            //return marcaActual.getNombre().compareToIgnoreCase(valor.toString());
        }else if(field.equalsIgnoreCase("agente vendedor")){
            if(vendedorActual.toString().toLowerCase().contains(valor.toString().toLowerCase())){
                return 0;
            }else
                return 1;
            //return vendedorActual.toString().compareToIgnoreCase(valor.toString());
        }else if(field.equalsIgnoreCase("placa")){
            if(autoActual.getPlaca().toLowerCase().contains(valor.toString().toLowerCase()))
                return 0;
            else
                return 1;
        }else if(field.equalsIgnoreCase("precio"))
            return autoActual.getPrecio().compareTo(Double.parseDouble(valor.toString()));
        else if(field.equalsIgnoreCase("fecha")){
            SimpleDateFormat formatofe = new SimpleDateFormat("yy-MM-dd");
            String fecha = formatofe.format(getFecha());
            return fecha.compareTo(valor.toString());
        }
        return null;
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
