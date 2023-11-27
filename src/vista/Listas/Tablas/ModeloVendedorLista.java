package vista.Listas.Tablas;
import controlador.TDALista.LinkedList;
import controlador.TDALista.exceptions.VacioException;
import javax.swing.table.AbstractTableModel;
import modelo.AgenteVendedor;

/**
 *
 * @author Asus
 */
public class ModeloVendedorLista extends AbstractTableModel{
    private LinkedList<AgenteVendedor> vendedores;
    
    @Override
    public int getRowCount() {
        return vendedores.getSize();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            AgenteVendedor vendedor = getVendedores().get(rowIndex);
            switch (columnIndex) {
            case 0:
                return (vendedor != null) ? vendedor.getDni(): "";
            case 1:
                return (vendedor != null) ? vendedor.getRuc(): "";
            case 2:
                return (vendedor != null) ? vendedor.getNombres(): "";
            case 3:
                return (vendedor != null) ? vendedor.getApellidos(): "";
            case 4:
                return (vendedor != null) ? vendedor.getDireccion(): "";
            case 5:
                return (vendedor != null) ? vendedor.getTelefono(): "";
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
                return "DNI";
            case 1:
                return "RUC";
            case 2:
                return "Nombres";
            case 3:
                return "Apellidos";
            case 4:
                return "Dirección";
            case 5:
                return "Teléfono";
            default:
                return null;
        }
    }

    public LinkedList<AgenteVendedor> getVendedores() {
        return vendedores;
    }

    public void setVendedores(LinkedList<AgenteVendedor> vendedores) {
        this.vendedores = vendedores;
    }
 
   
}
