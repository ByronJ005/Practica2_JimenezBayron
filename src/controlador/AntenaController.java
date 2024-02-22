package controlador;
import controlador.listas.LinkedList;
import controlador.DAO.DataAccesObject;
import controlador.grafos.GrafoEtiquetadoNoDirigido;
import java.io.FileReader;
import java.io.FileWriter;
import modelo.Antena;

/**
 *
 * @author Asus
 */
public class AntenaController extends DataAccesObject<Antena>{
    private Antena antena = new Antena();
    private LinkedList<Antena> antenas = new LinkedList<Antena>();
    private GrafoEtiquetadoNoDirigido<Antena> grafoAntena;
    
    public AntenaController() {
        super(Antena.class);
    }
    
    public String generatedCode() {
        StringBuilder code = new StringBuilder("ANT");
        Integer lenght = listall().getSize() + 1;
        Integer pos = lenght.toString().length();
        for (int i = 0; i < (5 - pos); i++) {
            code.append("0");
        }
        code.append(lenght.toString());
        return code.toString();
    }
    
    public Boolean save(){
        antena.setId(generated_id());
        return save(antena);
    }
    
    public Integer buscarIndex(Integer id){
        Integer index = -1;
        if(!listall().isEmpty()){
            Antena[] antenas = listall().toArray();
            for (int i = 0; i < antenas.length ; i++) {
                if(id.intValue() == antenas[i].getId().intValue()){
                    index = i;
                    break;
                }
            }    
        }
        return index;
    }
    
    public Boolean update(Integer index) {
        return update(antena, buscarIndex(antena.getId()));
    }
    
    public GrafoEtiquetadoNoDirigido<Antena> getGrafoAntena() throws Exception{
        if(grafoAntena == null){
            LinkedList<Antena> lista = getAntenas();
            Integer size = lista.getSize();
            if(size > 0){
                grafoAntena = new GrafoEtiquetadoNoDirigido<>(size, Antena.class);
                for (int i = 0; i < size; i++) {
                    grafoAntena.etiquetarVertice((i+1), lista.get(i));
                }
            }
        }
        return grafoAntena;
    }
    
    public void distanciaPuntosFloyd(Double distancias[][], Integer source, Integer destination) {
        if (distancias[source][destination] == Double.POSITIVE_INFINITY) {
            System.out.println("No hay camino entre los vértices " + source + " y " + destination);
            return;
        }
        System.out.print(source + " ");
        Integer next = source;
        while (next != destination) {
            for (int i = 0; i < distancias.length; ++i) {
                if (i != next && distancias[next][destination] == distancias[next][i] + distancias[i][destination]) {
                    System.out.print(i + " ");
                    next = i;
                    break;
                }
            }
        }
        System.out.print(destination);
    }

    public void setGrafoAntena(GrafoEtiquetadoNoDirigido<Antena> grafoAntena) {
        this.grafoAntena = grafoAntena;
    }
    
    public void guardarGrafo() throws Exception{
        if(grafoAntena != null){
            this.getXstream().alias(grafoAntena.getClass().getName(), GrafoEtiquetadoNoDirigido.class);
            this.getXstream().toXML(grafoAntena, new FileWriter("data/grafo.json"));
        }else new NullPointerException("Grafo Vacío");      
    }
    
    public void cargarGrafo() throws Exception{
          grafoAntena = (GrafoEtiquetadoNoDirigido<Antena>)this.getXstream().fromXML(new FileReader("data/grafo.json"));
          for (int i = 1; i <= grafoAntena.nro_vertices(); i++) {
                antenas.add(grafoAntena.obtenerEtiqueta(i));
            }
//        GrafoEtiquetadoNoDirigido<Antena> grafoenArchivo = (GrafoEtiquetadoNoDirigido<Antena>)this.getXstream().fromXML(new FileReader("data/grafo.json"));
//        System.out.println("Grafo en archivo: \n"+grafoenArchivo.toString());
//        System.out.println("Grafo antiguo:\n"+grafoAntena.toString());
//        if(getGrafoAntena() == null){
//            grafoAntena = grafoenArchivo; 
//            antenas.clear();
//            for (int i = 1; i <= grafoAntena.nro_vertices(); i++) {
//                antenas.add(grafoAntena.obtenerEtiqueta(i));
//            }
//        }else{
//            grafoAntena.setListaAdyacente(grafoenArchivo.getListaAdyacente());
//        }
//        System.out.println("Grafo Final: \n"+grafoAntena.toString());
        //System.out.println("Antenas en cargar:"+antenas.print());
    }
    
    /*public LinkedList<Antena> quickSortAntena (Antena[] arreglo, int inicio , int fin, int orden, String field) throws VacioException {
        int i = inicio; // i siempre avanza en el arreglo hacia la derecha
        int j = fin; // j siempre avanza hacia la izquierda
        Antena pivote = arreglo[(inicio + fin)/2] ;
        do{
            while(arreglo[i].comparar(pivote, field, orden))//si ya esta ordenado incrementa i
                i++;
            while(pivote.comparar(arreglo[j], field, orden))//si ya esta ordenado decrementa j
                j--;
            if(i <= j){// Hace el intercambio
                Antena aux = arreglo[i];
                arreglo[i] = arreglo[j] ;
                arreglo[j] = aux ;
                i++;
                j--;
            }
            }while(i <= j);
            if(inicio < j)
                quickSortAntena(arreglo,inicio,j, orden, field);// invocación recursiva
            if(i < fin)
                quickSortAntena(arreglo, i , fin, orden, field);// invocacion recursiva
            return new LinkedList<Antena>().toList(arreglo);
    }
    
    public LinkedList<Antena> busquedaBin (String field, Object valor) throws VacioException{
        Antena[] arreglo = getAntenas().toArray();
        Antena[] arrayOrdenado = this.quickSortAntena(arreglo, 0, (arreglo.length - 1), 0, field).toArray();
        LinkedList<Antena> result = new LinkedList<>();
        int inicio = 0;
        int fin = arrayOrdenado.length - 1;
        while(inicio <= fin) {
            int medio = (inicio + fin)/2;
            int buscado = arrayOrdenado[medio].esIgual(field, valor);
            if(buscado == 0) {
                result.add(arrayOrdenado[medio]);//Cuando encuentra el objeto, rompo el ciclo y envio este dato
                break;
            }else{
                if(buscado > 0)
                    fin = medio - 1;
                else
                    inicio = medio + 1 ;
            }
        }
        return result;
    }
    
    public LinkedList<Antena> busqLineBin(String field, Object valor, Integer orden) throws VacioException{//La que devuelve una lista
        //LinkedList<Antena> ordenada = this.quickSortAntena(arreglo, 0, (arreglo.length - 1), 0, field);
        //System.out.println(ordenada.print().toString());
        Antena[] arreglo = getAntenas().toArray();
        Antena[] arrayOrdenado = this.quickSortAntena(arreglo, 0, (arreglo.length - 1), 0, field).toArray();
        LinkedList<Antena> result = new LinkedList<>();
        for (Antena arrayOrdenado1 : arrayOrdenado) {
            //System.out.println("Entrando a busqueda");
            //System.out.println("ARRAY en i: " + arrayOrdenado1.getPrecio() + " ");
            int buscado = arrayOrdenado1.esSimilar(field, valor);
            if (buscado == 0 && orden == 0) {
                result.add(arrayOrdenado1);
            } else if (buscado < 0 && orden == 1) {
                result.add(arrayOrdenado1);
            } else if (buscado > 0 && orden == 2) {
                result.add(arrayOrdenado1);
            }
        }
        /*for(int i = 0; i < arrayOrdenado.length ; i++){
            System.out.println("Entrando a busqueda");
            System.out.println("ARRAY en i: "+arrayOrdenado[i].getPrecio() + " ");
            int buscado = arrayOrdenado[i].esIgual(field, valor);
            if(buscado == 0 && orden == 0)//Si son iguales y busco valores iguales, añado a la lista
                result.add(arrayOrdenado[i]);
            else if(buscado < 0 && orden == 1)//Si son menores y busco valores menores, añado a la lista
                result.add(arrayOrdenado[i]);
            else if(buscado > 0 && orden == 2)//Si son mayores y busco valores mayores, añado a la lista
                result.add(arrayOrdenado[i]);
        }*//*
        return result;//Devuelve una lista
    }*/
    
    public Antena getAntena() {
        if(antena == null)
            antena = new Antena();
        return antena;
    }

    public void setAntena(Antena antena) {
        this.antena = antena;
    }

    public LinkedList<Antena> getAntenas() {
        if(antenas.isEmpty())
            antenas = listall();
        return antenas;
    }

    public LinkedList<Antena> getAntenasGuardadas() {
        return listall();
    }
    
    public void setAntenas(LinkedList<Antena> antenas) {
        this.antenas = antenas;
    }
    
}
