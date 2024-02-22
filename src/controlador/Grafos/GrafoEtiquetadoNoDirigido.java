package controlador.grafos;
import controlador.Util.Utilidades;
import controlador.grafos.exceptions.EtiquetaException;
import controlador.listas.LinkedList;
import java.util.HashMap;
import modelo.Antena;

/**
 *
 * @author Asus
 */
public class GrafoEtiquetadoNoDirigido<E> extends GrafoEtiquetadoDirigido<E> {

    public GrafoEtiquetadoNoDirigido(Integer nro_vertice, Class<E> clazz) {
        super(nro_vertice, clazz);
    }

    public GrafoEtiquetadoNoDirigido() {
        super(null, null);
    }
    
    @Override
    public void insertar(Integer a, Integer b, Double peso) throws Exception {
        if (a.intValue() <= nro_vertices() && b.intValue() <= nro_vertices()) {
            if (!existe_arista(a, b)) {
                Adyacencia auxA = new Adyacencia();
                auxA.setD(b);
                auxA.setPeso(peso);
                
                Adyacencia auxB = new Adyacencia();
                auxB.setD(a);
                auxB.setPeso(peso);
                getListaAdyacente()[a].add(auxA);
                getListaAdyacente()[b].add(auxB);
                //nro_aristas++;
                setNro_aristas(nro_aristas() + 1);
            }
        } else {
            throw new Exception();
        }
    }
    
    private Boolean[] obtenerVerticesNoVisitados(){
        Boolean[] verticesNoVisitados = new Boolean[nro_vertices()+1];
        for (int i = 1; i <= nro_vertices(); i++) {
            verticesNoVisitados[i] = false;
        }
        return verticesNoVisitados;
    }
    
    public boolean conectadoAnchuraGNoDirigido()throws Exception{
        Boolean[] visitados = obtenerVerticesNoVisitados();
        LinkedList<Integer> lista = new LinkedList<>();
        lista.add(1);
        visitados[1] = true;
        while (!lista.isEmpty()) {            
            Integer actual = lista.deleteFirst();
            System.out.println("Actual: "+actual);
            Adyacencia[] adyacentes = adyacentes(actual).toArray();
            for (Adyacencia adyacencia : adyacentes) {
                Integer destino = adyacencia.getD();
                System.out.println("Destino de adyacencia: "+destino);
                if(visitados[destino] != null){
                    if(!visitados[destino]){
                    lista.add(destino);
                    visitados[destino] = true;
                }
                }
            }
        }
        for (int i = 1; i <= nro_vertices(); i++) {
            if(!visitados[i])
                return false;
        }
        return true;
    }
    
    public Boolean conectadoProfundidadGNoDirigido()throws Exception{
        Boolean[] visitados = obtenerVerticesNoVisitados();
        LinkedList<Integer> lista = new LinkedList<>();
        lista.add(1);
        visitados[1] = true;
        while (!lista.isEmpty()) {            
            Integer actual = lista.deleteLast();
            System.out.println("Actual: "+actual);
            Adyacencia[] adyacentes = adyacentes(actual).toArray();
            for (Adyacencia adyacencia : adyacentes) {
                Integer destino = adyacencia.getD();
                System.out.println("Destino de adyacencia: "+destino);
                if(visitados[destino] != null){
                    if(!visitados[destino]){
                    lista.add(destino);
                    visitados[destino] = true;
                }
                }
            }
        }
        for (int i = 1; i <= nro_vertices(); i++) {
            if(!visitados[i])
                return false;
        }
        return true;
    }
    
    public HashMap<Integer, Integer> caminosDijsktra(Integer o) throws Exception {
        HashMap<Integer, Integer> distancias = new HashMap<>();
        Integer V = nro_vertices();
        Double[] longitud = new Double[V + 1];
        Boolean[] marcado = new Boolean[V + 1];
        HashMap<Integer, Integer> anteriores = new HashMap<>();

        for (int i = 1; i <= V; i++) {
            longitud[i] = Double.MAX_VALUE;
            marcado[i] = false;
        }

        longitud[o] = 0.0;

        for (int i = 1; i <= V - 1; i++) {
            Double min = Double.MAX_VALUE;
            Integer min_index = -1;

            for (int v = 1; v <= V; v++) {
                if (!marcado[v] && longitud[v] <= min) {
                    min = longitud[v];
                    min_index = v;
                }
            }
            marcado[min_index] = true;

            for (int j = 1; j <= V; j++) {
                if (!marcado[j] && existe_arista(min_index, j) && longitud[min_index] != Double.MAX_VALUE && longitud[min_index] + peso_arista(min_index, j) < longitud[j]) {
                    longitud[j] = longitud[min_index] + peso_arista(min_index, j);
                    anteriores.put(j, min_index);
                }
            }
        }
        for (int i = 1; i <= V; i++) {
            distancias.put(i, longitud[i].intValue());
        }

        return anteriores;
    }
    
    public Double[][] caminosFloyd(GrafoEtiquetadoNoDirigido g) throws Exception{
        Integer v = g.nro_vertices();
        Double distancias[][] = new Double[v][v];
        for (int i = 0; i < v; i++) {//Inicializando la matriz
            for (int j = 0; j < v; j++) {
                try {
                    Double peso = g.peso_arista(i, j);
                    distancias[i][j] = peso;
                }catch (Exception e) {
                    distancias[i][j] = Double.POSITIVE_INFINITY;
                }
            }
        }
        for (int k = 0; k < v; k++) {
            for (int i = 0; i < v; i++) {
                for (int j = 0; j < v; j++) {//Itera todos los vertices y aplica el algoritmo de Floyd
                    if (distancias[i][k] + distancias[k][j] < distancias[i][j])
                        distancias[i][j] = distancias[i][k] + distancias[k][j];
                }
            }
        }//Finalmente se imprime la solución
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < v; j++) {
                if (distancias[i][j] == Double.POSITIVE_INFINITY)
                    System.out.print("INF ");
                else
                    System.out.print(Utilidades.redondear(distancias[i][j]) + "   ");
            }
            System.out.println();
        }
        return distancias;
    }
    
    
}

