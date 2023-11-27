package vista.listas.util;
import controlador.Listas.AutoControllerListas;
import controlador.Listas.VendedorControllerListas;
import controlador.listas.MarcaControllerListas;
import controlador.TDALista.exceptions.VacioException;
import javax.swing.JComboBox;
import modelo.Marca;

/**
 *
 * @author Asus
 */
public class UtilVistaLista {
    public static void cargarMarca(JComboBox cbxmarca) throws VacioException{
        MarcaControllerListas mc = new MarcaControllerListas();
        cbxmarca.removeAllItems();
        for(int i = 0; i <mc.getMarcas().getSize(); i++){
            cbxmarca.addItem(mc.getMarcas().get(i));
        }
    }
   
    public static void cargarVendedor(JComboBox cbx) throws VacioException{
        VendedorControllerListas vc = new VendedorControllerListas();
        cbx.removeAllItems();
        for(int i = 0; i <vc.getVendedores().getSize(); i++){
            cbx.addItem(vc.getVendedores().get(i));
        }
    }
    
    public static void cargarAuto(JComboBox cbx) throws VacioException{//Solo se mostrarán los autos que no estén vendidos
        AutoControllerListas ac = new AutoControllerListas();
        cbx.removeAllItems();
        for(int i = 0; i <ac.getAutos().getSize(); i++){
            if(ac.getAutos().get(i).getEstado() == true){
               cbx.addItem(ac.getAutos().get(i)); 
            }
        }
    }
    
    /*public static Marca getcomboMarcas(JComboBox cbx){
        return (Marca) cbx.getSelectedItem();
    }*/
    
    public static Object getCombo(JComboBox cbx){
        return cbx.getSelectedItem();
    }
    
}
