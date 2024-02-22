
import controlador.AntenaController;
import controlador.grafos.GrafoEtiquetadoDirigido;
import controlador.grafos.GrafoEtiquetadoNoDirigido;
import controlador.listas.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Antena;


/**
 *
 * @author Asus
 */
public class ejecutor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            AntenaController ac = new AntenaController(); 
            LinkedList<Antena> listaReal = ac.getAntenas();
            Integer size = listaReal.getSize();
            System.out.println("Size: "+size);
            GrafoEtiquetadoNoDirigido<Antena> segundo = new GrafoEtiquetadoNoDirigido<>(size, Antena.class);
            
            if(size > 0){                
                for (int i = 0; i < size; i++) {
                    segundo.etiquetarVertice((i+1), listaReal.get(i));
                }
            }
            System.out.println("Seundo: "+segundo.toString());
            
            ac.cargarGrafo();
            GrafoEtiquetadoNoDirigido<Antena> grafoInicial = ac.getGrafoAntena();
            System.out.println("original: "+grafoInicial.toString());
            segundo.setListaAdyacente(grafoInicial.getListaAdyacente());
            System.out.println("Final: "+segundo.toString());
            
//            grafoInicial.setNro_vertices(11);
//            System.out.println("Numero de vertices: "+grafoInicial.getNro_vertices());
            
            
//            System.out.println(grafoInicial.toString());
//            System.out.println(segundo.toString());
        } catch (Exception ex) {
            System.out.println("Error "+ex);
        }
        
    }
    
}
