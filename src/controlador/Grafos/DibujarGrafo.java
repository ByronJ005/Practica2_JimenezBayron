package controlador.grafos;
import controlador.Util.Utilidades;
import java.io.FileWriter;
import modelo.Antena;

/**
 *
 * @author Asus
 */
public class DibujarGrafo {
    String url = "d3/grafo.js";
    
    private Boolean estáDibujado(String data){
        
        return false;
    }
    
    private Boolean verificarPeso(String data, Integer origen, Integer destino){
        String sentencia = "from: "+destino+", to: "+origen;
        //{ from: 3, to: 4, label: '0.7' },
	//{ from: 4, to: 3, label: '0.7' },
        return data.contains(sentencia);
    }
    
    public void crearArchivo(GrafoEtiquetadoDirigido g){
        StringBuilder datos = new StringBuilder("var nodes = new vis.DataSet([\n");
        for (int i = 1; i <= g.nro_vertices(); i++) {//Se recorre los vertices y se extrae su etiqueta:
            String etiqueta = g.obtenerEtiqueta(i).toString();
            datos.append("\t{ id: ").append(i).append(", label: '").append("").append(etiqueta).append("' },\n");
        }
        datos.append("]);\n");
        
        try {
        datos.append("var edges = new vis.DataSet([\n");
        for (int i = 1; i <= g.nro_vertices(); i++) {//Vemos las adyacencias de cada vertice
            if(!g.adyacentes(i).isEmpty()){
                Adyacencia[] lista = g.adyacentes(i).toArray();
                    for (int j = 0; j < lista.length ; j++) {
                        Adyacencia a = lista[j];
                        if(g.existe_arista(i, a.getD())){
                            if(verificarPeso(datos.toString(),i,a.getD()))
                                datos.append("\t{ from: ").append(i).append(", to: ").append(a.getD()).append(" },\n");
                            else 
                                datos.append("\t{ from: ").append(i).append(", to: ").append(a.getD()).append(", label: '").append(Utilidades.redondear(a.getPeso())).append("' },\n");
                        }
                    }
            }
        }
        datos.append("]);");
        String data = datos.toString();
        String finalArchivo = 
            "var container = document.getElementById(\"mynetwork\");\n" +
            "      var data = {\n" +
            "        nodes: nodes,\n" +
            "        edges: edges,\n" +
            "      };\n" +
            "      var options = {};\n" +
            "      var network = new vis.Network(container, data, options);";
        
            FileWriter file = new FileWriter(url);
            file.write(data+ "\n"+finalArchivo);
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void crearArchivo(Grafo grafo){
        if(grafo instanceof GrafoEtiquetadoDirigido || grafo instanceof GrafoEtiquetadoNoDirigido){
            GrafoEtiquetadoDirigido g = (GrafoEtiquetadoDirigido)grafo;
            StringBuilder datos = new StringBuilder("var nodes = new vis.DataSet([\n");
            for (int i = 1; i <= g.nro_vertices(); i++) {//Se recorre los vertices y se extrae su etiqueta:
                String etiqueta = g.obtenerEtiqueta(i).toString();
                datos.append("\t{ id: ").append(i).append(", label: '").append("").append(etiqueta).append("' },\n");
            }
            datos.append("]);\n");
            try {
            datos.append("var edges = new vis.DataSet([\n");
            for (int i = 1; i <= g.nro_vertices(); i++) {//Vemos las adyacencias de cada vertice
            if(!g.adyacentes(i).isEmpty()){
                Adyacencia[] lista = g.adyacentes(i).toArray();
                    for (int j = 0; j < lista.length ; j++) {
                        Adyacencia a = lista[j];
                        if(g.existe_arista(i, a.getD())){
                            datos.append("\t{ from: ").append(i).append(", to: ").append(a.getD()).append(", label: '").append(a.getPeso()).append("' },\n");
                        }
                    }
            }
        }
        datos.append("]);");
        //faltan edges 
        String data = datos.toString();
        String finalArchivo = 
            "var container = document.getElementById(\"mynetwork\");\n" +
            "      var data = {\n" +
            "        nodes: nodes,\n" +
            "        edges: edges,\n" +
            "      };\n" +
            "      var options = {};\n" +
            "      var network = new vis.Network(container, data, options);";
        
            FileWriter file = new FileWriter(url);
            file.write(data+ "\n"+finalArchivo);
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
        
    }
  
}
