package vista.modeloTabla;

import controlador.Util.Utilidades;
import controlador.listas.LinkedList;
import controlador.listas.exceptions.VacioException;
import java.text.SimpleDateFormat;
import javax.swing.table.AbstractTableModel;
import modelo.Antena;

/**
 *
 * @author Asus
 */
public class ModeloTablaAntena extends AbstractTableModel{
    private LinkedList<Antena> antenas;
    SimpleDateFormat formato = new SimpleDateFormat("dd-MM-YY");
    
    @Override
    public int getRowCount() {
        return antenas.getSize();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Antena a = antenas.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return (a != null) ? a.getCodigo() : "";
                case 1:
                    return (a != null) ? a.getAltura() : "";
                case 2:
                    return (a != null) ? /*Utilidades.redondear*/(a.getLatitud()): "";
                case 3:
                    return (a != null) ? /*Utilidades.redondear*/(a.getLongitud()): "";
                case 4:
                    return (a != null) ? formato.format(a.getFechaInstalacion()): "";
                case 5:
                    return (a != null) ? a.getTipo(): "";
                default:
                    throw new AssertionError();
            }
        } catch (VacioException ex) {
            throw new AssertionError();
        }
    }
 
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Codigo";
            case 1:
                return "Altura";
            case 2:
                return "Latitud";
            case 3:
                return "Longitud";
            case 4:
                return "Fecha de Intalación";
            case 5:
                return "Tipo";
            default:
                throw new AssertionError();
        }
    }

    public LinkedList<Antena> getAntenas() {
        return antenas;
    }

    public void setAntenas(LinkedList<Antena> antenas) {
        this.antenas = antenas;
    }
    
    
    
}
