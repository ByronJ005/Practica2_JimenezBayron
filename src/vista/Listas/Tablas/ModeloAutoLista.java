package vista.Listas.Tablas;
import controlador.Listas.AutoControllerListas;
import controlador.TDALista.LinkedList;
import controlador.TDALista.exceptions.VacioException;
import controlador.listas.MarcaControllerListas;
import javax.swing.table.AbstractTableModel;
import modelo.AgenteVendedor;
import modelo.Auto;

/**
 *
 * @author Asus
 */
public class ModeloAutoLista extends AbstractTableModel{
    private LinkedList<Auto> autos;
    
    @Override
    public int getRowCount() {
        return getAutos().getSize();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Auto auto = getAutos().get(rowIndex);
            switch (columnIndex) {
            case 0:
                return (auto != null) ? auto.getPlaca(): "";
            case 1:
                return (auto != null) ? new MarcaControllerListas().getMarcas().get(auto.getId_marca()-1).getNombre(): "";
            case 2:
                return (auto != null) ? auto.getColor(): "";
            case 3:
                return (auto != null) ? "$"+auto.getPrecio(): "";
            case 4:
                return (auto != null) ? auto.getVelocidadMax()+" Km/h": "";
            case 5:
                return (auto != null) ? auto.verEstado(): "";
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
                return "Placa";
            case 1:
                return "Marca";
            case 2:
                return "Color";
            case 3:
                return "Precio";
            case 4:
                return "Velocidad Máx.";
            case 5:
                return "Estado";
            default:
                return null;
        }
    }

    public LinkedList<Auto> getAutos() {
        return autos;
    }

    public void setAutos(LinkedList<Auto> autos) {
        this.autos = autos;
    }

    
   
}
