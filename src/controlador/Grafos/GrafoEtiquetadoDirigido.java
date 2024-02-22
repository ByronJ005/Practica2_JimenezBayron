package controlador.grafos;
import java.lang.reflect.Array;
import java.util.HashMap;
import controlador.listas.LinkedList;
import controlador.grafos.exceptions.EtiquetaException;

/**
 *
 * @author Asus
 */
public class GrafoEtiquetadoDirigido<E> extends GrafoDirigido{
    protected E etiqueta[];//etiquetas del grado, Integer o, d
    protected HashMap<E, Integer> dirVertices;
    private Class<E> clazz;
    
    public GrafoEtiquetadoDirigido(Integer nro_vertices, Class<E> clazz) {
        super(nro_vertices);
        this.clazz = clazz;
        etiqueta = (E[]) Array.newInstance(clazz, nro_vertices + 1);
        dirVertices = new HashMap<>(nro_vertices);
    }

    public E[] getEtiqueta() {
        return etiqueta;
    }
    
    public Integer sizeEtiqueta(){
        return etiqueta.length;
    }
    
    public String getEtiquetaAlineada() {
        StringBuilder etiquetaString = new StringBuilder();
        for (int i = 1; i < etiqueta.length; i++) {;
            etiquetaString.append(etiqueta[i]).append(",");
        }
        //etiquetaString.deleteCharAt(0)
        return etiquetaString.toString();
    }
    
    public Boolean existeAristaE(E o, E d) throws Exception{
        if(estaEtiquetado())
            return existe_arista(obtenerCodigoE(o), obtenerCodigoE(d));
        else
            throw new EtiquetaException();
    }
    
    public void insertarAristaE(E o, E d, Double peso) throws Exception{
        if(estaEtiquetado())
            insertar(obtenerCodigoE(o), obtenerCodigoE(d), peso);
        else
            throw new EtiquetaException();
    }
    
    public void insertarAristaE(E o, E d) throws Exception{
        if(estaEtiquetado())
            insertar(obtenerCodigoE(o), obtenerCodigoE(d), Double.NaN);
        else
            throw new EtiquetaException();
    }
    
    public LinkedList<Adyacencia> adyacentesE (E o) throws Exception{
        if(estaEtiquetado())
            return adyacentes(obtenerCodigoE(o));
        else
            throw new EtiquetaException();
    }
    
    public void etiquetarVertice(Integer vertice, E dato){
        try {
            etiqueta[vertice] = dato;
            dirVertices.put(dato, vertice);
        } catch (Exception e) {
            System.out.println("Error al etiquetar: "+e.getMessage());
        }
    }
    
    public Boolean estaEtiquetado(){
        Boolean band = true;
        for(int i = 1; i < etiqueta.length; i++){
            E dato = etiqueta[i];
            if(dato == null){
                band = false;
                break;
            }
        }
        return band;
    }
    
    public Integer obtenerCodigoE(E etiqueta){
        return dirVertices.get(etiqueta);
    }

    public E obtenerEtiqueta(Integer i){
        return etiqueta[i];
    }

    @Override
    public String toString() {
        StringBuilder grafo = new StringBuilder("GRAFOS Etiquetados\n");
        try {
            System.out.println("Vertices toString: "+nro_vertices());
            for (int i = 1; i <= nro_vertices(); i++) {
                grafo.append("Vertice ").append(obtenerEtiqueta(i)).append("\n");
                if(!adyacentes(i).isEmpty()){
                    Adyacencia[] lista = adyacentes(i).toArray();
                    for (int j = 0; j < lista.length ; j++) {
                        Adyacencia a = lista[j];
                        grafo.append("Adyacente ").append(obtenerEtiqueta(a.getD()))
                                .append(" Peso: ").append(a.getPeso()).append("\n");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("excepcion GEDirigido: "+e.getMessage());
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
        }
        return grafo.toString();
    }
    
    public boolean conectadoAnchura()throws Exception{
        Boolean[] visitados = new Boolean[nro_vertices()+1];
        LinkedList<Integer> lista = new LinkedList<>();
        lista.add(1);
        visitados[1] = true;
        while (!lista.isEmpty()) {            
            Integer actual = lista.deleteFirst();
            Adyacencia[] adyacentes = adyacentes(actual).toArray();
            for (Adyacencia adyacencia : adyacentes) {
                Integer destino = adyacencia.getD();
                if(!visitados[destino]){
                    lista.add(destino);
                    visitados[destino] = true;
                }
            }
        }
        for (int i = 1; i <= nro_vertices(); i++) {
            if(!visitados[i])
                return false;
        }
        return true;
    }
    
    public Boolean conectadoProfundidad()throws Exception{
        Boolean[] visitados = new Boolean[nro_vertices()+1];
        LinkedList<Integer> lista = new LinkedList<>();
        lista.add(1);
        visitados[1] = true;
        while (!lista.isEmpty()) {            
            Integer actual = lista.deleteLast();
            Adyacencia[] adyacentes = adyacentes(actual).toArray();
            for (Adyacencia adyacencia : adyacentes) {
                Integer destino = adyacencia.getD();
                if(!visitados[destino]){
                    lista.add(destino);
                    visitados[destino] = true;
                }
            }
        }
        for (int i = 1; i <= nro_vertices(); i++) {
            if(!visitados[i])
                return false;
        }
        return true;
    }
    
    public static void main(String[] args) {
        /*GrafoEtiquetadoDirigido<String> ged = new GrafoEtiquetadoDirigido<>(5, String.class);
        try {
            ged.etiquetarVertice(1, "Alexis");
            ged.etiquetarVertice(2, "Bravo");
            ged.etiquetarVertice(3, "Carrion");
            ged.etiquetarVertice(4, "Naim");
            ged.etiquetarVertice(5, "Gerardo");
            
            ged.insertarAristaE("Bravo", "Naim",90.);
            System.out.println(ged.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }*/
        String[]arreglo = {"r","t","s","o"};
        for (int i = 0; i < arreglo.length; i++) {
            System.out.print(arreglo[i]+",");
        }
    }
}
