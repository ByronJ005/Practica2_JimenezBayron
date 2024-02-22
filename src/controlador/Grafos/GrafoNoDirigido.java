package controlador.grafos;
import controlador.listas.LinkedList;
import controlador.grafos.exceptions.VerticeOfSizeException;

/**
 *
 * @author Asus
 */
public class GrafoNoDirigido extends GrafoDirigido{
    
    public GrafoNoDirigido(Integer nro_vertices) {
        super(nro_vertices);
    }

    @Override
    public void insertar(Integer a, Integer b, Double peso) throws Exception {
        if(a.intValue() <= nro_vertices().intValue() && b.intValue() <= nro_vertices().intValue()){
            if(!existe_arista(a, b)){
                //nro_aristas++;
                setNro_aristas(nro_aristas() + 1);
                
                Adyacencia aux  = new Adyacencia();
                aux.setPeso(peso);
                aux.setD(b);
                getListaAdyacente()[a].add(aux);
                
                Adyacencia auxb = new Adyacencia();
                auxb.setPeso(peso);
                auxb.setD(a);
                getListaAdyacente()[b].add(auxb);
            }
        }else
            throw new VerticeOfSizeException();
    }
    
    
}
