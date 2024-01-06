package vista.Listas.Tablas;
import controlador.Listas.AutoControllerListas;
import controlador.Listas.VendedorControllerListas;
import controlador.TDALista.LinkedList;
import controlador.TDALista.exceptions.VacioException;
import controlador.listas.MarcaControllerListas;
import java.text.SimpleDateFormat;
import javax.swing.table.AbstractTableModel;
import modelo.AgenteVendedor;
import modelo.Auto;
import modelo.Marca;
import modelo.Venta;

/**
 *
 * @author Asus
 */
public class ModeloVentaLista extends AbstractTableModel{
    private LinkedList<Venta> ventas;
    
    @Override
    public int getRowCount() {
        return getVentas().getSize();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Venta venta = getVentas().get(rowIndex);
            AgenteVendedor vendedor = new VendedorControllerListas().getVendedores().get(venta.getId_vendedor()-1);
            Auto auto = new AutoControllerListas().getAutos().get(venta.getId_auto()-1);
            Marca marca = new MarcaControllerListas().getMarcas().get(auto.getId_marca()-1);
            switch (columnIndex) {
            case 0:
                return (venta != null) ? venta.getCodigoVenta(): "";
            case 1:
                return (venta != null) ? vendedor.toString(): "";
            case 2:
                return (venta != null) ? auto.getPlaca(): "";
            case 3:
                return (venta != null) ? marca.getNombre(): "";
            case 4:
                return (venta != null) ? auto.getPrecio(): "";
            case 5:
                return (venta != null) ? new SimpleDateFormat().format(venta.getFecha()): "";
            case 6:
                return (venta != null) ? venta.getDescripcion(): "";
            default:
                return null;
        }
        } catch (VacioException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
        return null;
    }
    
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Código";
            case 1:
                return "Agente Vendedor";
            case 2:
                return "Auto Vendido";
            case 3:
                return "Marca";
            case 4:
                return "Precio";
            case 5:
                return "Fecha de Venta";
            case 6:
                return "Observación";
            default:
                return null;
        }
    }

    public LinkedList<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(LinkedList<Venta> ventas) {
        this.ventas = ventas;
    }

    
   
}
