package vista.modeloTabla;
import controlador.Util.Utilidades;
import controlador.grafos.GrafoEtiquetadoNoDirigido;
import javax.swing.table.AbstractTableModel;
import modelo.Antena;

/**
 *
 * @author Asus
 */
public class ModeloTablaAdyacencia extends AbstractTableModel{
    private GrafoEtiquetadoNoDirigido<Antena> grafo;
    
    @Override
    public int getRowCount() {
        return grafo.nro_vertices();
    }

    @Override
    public int getColumnCount() {
        return grafo.nro_vertices() + 1;
    }

    @Override
    public String getColumnName(int column) {
        if(column == 0)
            return "Antenas";
        else
            return grafo.obtenerEtiqueta(column).toString();
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(columnIndex == 0)
            return grafo.obtenerEtiqueta(rowIndex + 1).toString();
        else{
            String valor ="--";
            try {
                Antena o = grafo.obtenerEtiqueta(rowIndex+1);
                Antena d = grafo.obtenerEtiqueta(columnIndex);
                if(grafo.existeAristaE(o, d)){
                   valor = Utilidades.redondear(grafo.peso_arista((rowIndex + 1), columnIndex)).toString();
                }
                return valor;
                //LinkedList<Adyacencia> ady = grafo.adyacentesE(e); 
            } catch (Exception e) {
                System.out.println("error en modelo tabla: "+e);
                return valor;
            }
        }
    }

    public GrafoEtiquetadoNoDirigido<Antena> getGrafo() {
        return grafo;
    }

    public void setGrafo(GrafoEtiquetadoNoDirigido<Antena> grafo) {
        this.grafo = grafo;
    }
    
    
}
