package controlador.Grafos.Antena;
import controlador.AntenaController;
import controlador.Util.Utilidades;
import controlador.grafos.Grafo;
import controlador.grafos.GrafoEtiquetadoDirigido;
import controlador.grafos.GrafoEtiquetadoNoDirigido;
import controlador.listas.LinkedList;
import java.io.FileWriter;
import modelo.Antena;
import org.edisoncor.gui.comboBox.ComboBoxRect;

/**
 *
 * @author Asus
 */
public class UtilidadesAntenaVista {
    public static void crearMapaAntena(Grafo grafo){
        if(grafo instanceof GrafoEtiquetadoDirigido || grafo instanceof GrafoEtiquetadoNoDirigido){
            GrafoEtiquetadoDirigido ge = (GrafoEtiquetadoDirigido) grafo;
            String mapa = "var osmUrl = 'https://tile.openstreetmap.org/{z}/{x}/{y}.png',\n" +
            "        osmAttrib = '&copy; <a href=\"https://www.openstreetmap.org/copyright\">OpenStreetMap</a> contributors',\n" +
            "        osm = L.tileLayer(osmUrl, {maxZoom: 15, attribution: osmAttrib});\n" +
            "\n" +
            "var map = L.map('map').setView([-4.036, -79.201], 15);\n" +
            "L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {\n" +
            "    attribution: '&copy; <a href=\"https://www.openstreetmap.org/copyright\">OpenStreetMap</a> contributors'\n" +
            "}).addTo(map);\n";
            try {
                for (int i = 1; i <= ge.nro_vertices(); i++) {
                Antena an = (Antena)ge.obtenerEtiqueta(i);
                mapa += "L.marker(["+an.getLatitud()+","+an.getLongitud()+"]).addTo(map)\n";
                mapa += ".bindPopup('"+an.getCodigo()+"')\n";
                mapa += ".openPopup();\n";
            }
                FileWriter file = new FileWriter("mapas//mapa.js");
                file.write(mapa);
                file.close();
            } catch (Exception e) {
            }
        }
    }
    
    public static void cargarComboAntena(ComboBoxRect combo) throws Exception{
        combo.removeAllItems();
        LinkedList<Antena> lista = new AntenaController().listall();
        for(int i = 0; i < lista.getSize();i++){
            combo.addItem(lista.get(i));
        }
    }
    
    public static Antena getComboAntena(ComboBoxRect combo){
        return (Antena) combo.getSelectedItem();
    }
    
    public static double distanciaAntena(Antena origen, Antena destino){
        return Utilidades.coordGpsToKm(origen.getLatitud(), origen.getLongitud(), destino.getLatitud(), destino.getLongitud());
    }
}
