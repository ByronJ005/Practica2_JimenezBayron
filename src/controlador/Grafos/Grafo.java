package controlador.grafos;
import controlador.listas.LinkedList;
import java.util.HashMap;

/**
 *
 * @author Asus
 */
public abstract class Grafo {
    public abstract Integer nro_vertices();
    public abstract Integer nro_aristas();
    public abstract Boolean existe_arista(Integer a, Integer b) throws Exception;
    public abstract Double peso_arista(Integer a, Integer b) throws Exception;
    public abstract void insertar(Integer a, Integer b)throws Exception;
    public abstract void insertar(Integer a, Integer b, Double peso)throws Exception;
    public abstract LinkedList<Adyacencia> adyacentes(Integer a);
    //private abstract LinkedList<Adyacencia> ada(Integer o);

    @Override
    public String toString() {
        StringBuilder grafo = new StringBuilder("GRAFOS \n");
        try {
            for (int i = 0; i < nro_vertices(); i++) {
                grafo.append("Vertice ").append(String.valueOf(i)).append("\n");
                if(!adyacentes(i).isEmpty()){
                    Adyacencia[] lista = adyacentes(i).toArray();
                    for (int j = 0; j < lista.length ; j++) {
                        Adyacencia a = lista[j];
                        grafo.append("Adyacente ").append(a.getD().toString()).append("\n");
                    }
                }
            }
        } catch (Exception e) {}
        return grafo.toString();
    }
    
    //caminos mínimos
    protected Boolean esta_Conectado(){
        Boolean band = true;
        try {
            for (int i = 0; i < nro_vertices(); i++) {
                if(adyacentes(i).isEmpty()){
                    band = false;
                    break;
                }
            }
        } catch (Exception e) {}
        return band;
    }
    
    public HashMap camino(Integer origen, Integer destino)throws Exception{
        HashMap sendero = new HashMap();
        if(esta_Conectado()){
            LinkedList<Integer> vertices = new LinkedList<>();
            LinkedList<Double> pesos = new LinkedList<>();
            Boolean finalizar = false;
            Integer inicial = origen;
            vertices.add(inicial);
            while (!finalizar) {                
                LinkedList<Adyacencia> adyacencias = adyacentes(inicial);
                Double peso = Double.MAX_VALUE;
                Integer T = -1;
                for (int i = 0; i < adyacencias.getSize(); i++) {
                    Adyacencia ad = adyacencias.get(i);
                    if(!estaEnCamino(vertices, ad.getD().intValue())){
                        Double pesoarista = ad.getPeso();
                        if(destino.intValue() == ad.getD().intValue()){
                            T = ad.getD();
                            peso = pesoarista;
                            break;
                        }else if(pesoarista <peso){
                            T = ad.getD();
                            peso = pesoarista;
                        }
                    }
                    /*Double pesoarista = ad.getPeso();
                    T = ad.getD();
                    peso = pesoarista;
                    if(destino.intValue() == ad.getD().intValue()) break;*/
                }
                vertices.add(T);
                pesos.add(peso);
                inicial = T;
                if(destino.intValue() == inicial.intValue()) break;
            }
            sendero.put("camino", vertices);
            sendero.put("peso", pesos);
        }else{System.out.println("error en camino: ");}
        return sendero;
    }
    
    private Boolean estaEnCamino(LinkedList<Integer> lista, Integer vertice) throws Exception{
        Boolean band = false;
        for (int i = 0; i < lista.getSize(); i++) {
            if(lista.get(i).intValue() == vertice.intValue()){
                band = true;
                break;
            }
        }
        return band;
    }
}
