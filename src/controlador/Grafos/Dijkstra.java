package controlador.Grafos;
import controlador.grafos.GrafoEtiquetadoNoDirigido;

/**
 *
 * @author Asus
 */
public class Dijkstra {//Tomado del libro: Estructura de datos en JAVA
    private Double[][] pesos;
    private Integer[] ultimo;
    private Double[] D;
    private Boolean[] F;
    private Integer verticeInicial, totalVertices; 

    public Dijkstra(GrafoEtiquetadoNoDirigido g, Integer origen) {
        totalVertices = g.nro_vertices();
        verticeInicial = origen;//Puede ser origen + 1
        pesos = new Double[totalVertices][totalVertices];
        llenarMatrizPesos(g, pesos);
        ultimo = new Integer[totalVertices];
        D = new Double[totalVertices];
        F = new Boolean[totalVertices];
    }
    
    public void llenarMatrizPesos(GrafoEtiquetadoNoDirigido grafo, Double[][] matrizPesos) {
    Integer numVertices = grafo.nro_vertices();
    // Iterar sobre todos los pares de vértices
    for (int i = 0; i < numVertices; i++) {
        for (int j = 0; j < numVertices; j++) {
            try {
                //Double peso = grafo.peso_arista(i, j);
                Double peso = grafo.peso_arista(i+1, j+1);
                matrizPesos[i][j] = peso;
            } catch (Exception e) {
                matrizPesos[i][j] = Double.POSITIVE_INFINITY;
            }
        }
    }/*
    for (int i = 0; i < numVertices; i++) {
        for (int j = 0; j < numVertices; j++) {
            System.out.print(" [" + matrizPesos[i][j] + "] ");
        }
            System.out.println("");
        }   */ 
    }

    public void caminoMinimos() {
// valores iniciales
        for (int i = 0; i < totalVertices; i++) {
            F[i] = false;
            D[i] = pesos[verticeInicial][i];
            ultimo[i] = verticeInicial;
        }
        F[verticeInicial] = true;
        D[verticeInicial] = 0.0;
        for (int i = 1; i <= totalVertices; i++) { //!Estaba i <
            Integer v = minimo();
            F[v] = true;
// actualiza distancia de vértices no marcados
            for (int w = 1; w < totalVertices; w++) {//Estaba i <
                if (!F[w]) {
                    if ((D[v] + pesos[v][w]) < D[w]) {
                        D[w] = D[v] + pesos[v][w];
                        ultimo[w] = v;
                    }
                }
            }
        }

    }

 
    public Integer minimo() {
        Double mx = Double.POSITIVE_INFINITY;;
        int v = 1;
        for (int j = 1; j < totalVertices; j++) { //1
            if (!F[j] && (D[j] <= mx)) {
                mx = D[j];
                v = j;
            }
        }
        return v;
    }
    
    public void recuperaCamino(int v) {
        int anterior = ultimo[v];
        if (v != verticeInicial) {
            recuperaCamino(anterior);
            System.out.print("..V" + v);
        } else {
            System.out.print("V" + verticeInicial);
        }
    }
    
  } 
