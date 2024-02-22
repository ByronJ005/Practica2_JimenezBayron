package controlador.grafos;
import controlador.listas.LinkedList;
import controlador.grafos.exceptions.VerticeOfSizeException;

/**
 *
 * @author Asus
 */
public class GrafoDirigido extends Grafo{
    private Integer nro_vertices;
    private Integer nro_aristas;
    private LinkedList<Adyacencia> listaAdyacente[];

    public GrafoDirigido(Integer nro_vertices) {
        this.nro_vertices = nro_vertices;
        nro_aristas = 0;
        listaAdyacente = new LinkedList[nro_vertices + 1];
        for (int i = 1; i <= nro_vertices; i++) {
            listaAdyacente[i] = new LinkedList<>();
        }
    }

    @Override
    public Integer nro_vertices() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        return this.nro_vertices;
    }

    @Override
    public Integer nro_aristas() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        return this.nro_vertices;
    }

    @Override
    public Boolean existe_arista(Integer a, Integer b) throws Exception{
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        Boolean band = false;
        if(a.intValue() <= nro_vertices.intValue() && b.intValue() <= nro_vertices().intValue()){
            LinkedList<Adyacencia> lista = listaAdyacente[a];
            for (int i = 0; i < lista.getSize(); i++) {
                Adyacencia aux = lista.get(i);
                if(aux.getD().intValue() == b.intValue()){
                    band = true;
                    break;  
                }
            }
        }else{
            throw new VerticeOfSizeException();
        }
        return band;
    }

    @Override
    public Double peso_arista(Integer a, Integer b) throws Exception{
        Double peso = Double.POSITIVE_INFINITY; //Doublr.nan
        if(existe_arista(a, b)){
            LinkedList<Adyacencia> lista = listaAdyacente[a];
            for (int i = 0; i < lista.getSize(); i++) {
                Adyacencia aux = lista.get(i);
                if(aux.getD().intValue() == b.intValue()){
                    peso = aux.getPeso();
                    break;  
                }
        
            }
            }
        return peso;
    }

    @Override
    public void insertar(Integer a, Integer b) throws Exception{
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        insertar(a, b, Double.NaN);
    }

    @Override
    public void insertar(Integer a, Integer b, Double peso) throws Exception{
        if(a.intValue() <= nro_vertices().intValue() && b.intValue() <= nro_vertices().intValue()){
            if(!existe_arista(a, b)){
                nro_aristas++;
                Adyacencia aux  = new Adyacencia();
                aux.setPeso(peso);
                aux.setD(b);
                listaAdyacente[a].add(aux);
            }
        }
    }


    @Override
    public LinkedList<Adyacencia> adyacentes(Integer a) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        return listaAdyacente[a];
    }

    public Integer getNro_vertices() {
        return nro_vertices;
    }

    public void setNro_vertices(Integer nro_vertices) {
        this.nro_vertices = nro_vertices;
    }

    public Integer getNro_aristas() {
        return nro_aristas;
    }

    public void setNro_aristas(Integer nro_aristas) {
        this.nro_aristas = nro_aristas;
    }

    
    
    public LinkedList<Adyacencia>[] getListaAdyacente() {
        return listaAdyacente;
    }

    public void setListaAdyacente(LinkedList<Adyacencia>[] listaAdyacente) {
        this.listaAdyacente = listaAdyacente;
    }
    
}
