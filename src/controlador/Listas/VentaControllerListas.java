package controlador.Listas;

import controlador.TDALista.LinkedList;
import controlador.TDALista.exceptions.VacioException;
import controlador.listas.DAO.DataAccesObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Venta;

/**
 *
 * @author Asus
 */
public class VentaControllerListas extends DataAccesObject<Venta>{
    private Venta venta = new Venta();
    private LinkedList<Venta> ventas = new LinkedList<Venta>();
    private Integer index = -1;
    
    public VentaControllerListas() {
        super(Venta.class);
    }
    
    public String generatedCode() {
        StringBuilder code = new StringBuilder();
        Integer lenght = listall().getSize() + 1;
        Integer pos = lenght.toString().length();
        for (int i = 0; i < (10 - pos); i++) {
            code.append("0");
        }
        code.append(lenght.toString());
        return code.toString();
    }
    
    public Boolean save(){
        venta.setId_venta(generated_id());
        AutoControllerListas ac = new AutoControllerListas();
        VendedorControllerListas vc = new VendedorControllerListas();
        try {
            ac.setAuto(ac.listall().get(getVenta().getId_auto()-1));
            ac.getAuto().setEstado(false);//Se cambia el estado del auto a False: NoDisponible o Vendido y se actualiza
            ac.update(getVenta().getId_auto()-1);
            
            /*vc.setVendedor(vc.listall().get(getVenta().getId_auto()));
            vc.getVendedor().setApellidos("");//Aquí se adjudica la venta del auto al vendedor*/
        } catch (VacioException ex) {
            System.out.println("error guardar venta: "+ex.getMessage());
        }
        return save(venta);
    }
    
    public Boolean update(Integer index) {
        return update(venta, index);
    }
    
    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Venta getVenta() {
        if(venta == null)
            venta = new Venta();
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public LinkedList<Venta> getVentas() {
        if(ventas.isEmpty())
            ventas = listall();
        return ventas;
    }

    public void setVentas(LinkedList<Venta> ventas) {
        this.ventas = ventas;
    }
    
    
}
