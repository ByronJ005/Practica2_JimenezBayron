package controlador.Listas;
import controlador.TDALista.LinkedList;
import controlador.TDALista.exceptions.VacioException;
import controlador.listas.DAO.DataAccesObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Auto;
import modelo.Venta;

/**
 *
 * @author Asus
 */
public class VentaControllerListas extends DataAccesObject<Venta>{
    private Venta venta = new Venta();
    private LinkedList<Venta> ventas = new LinkedList<Venta>();
    private Integer index = -1;
    
    public VentaControllerListas() {
        super(Venta.class);
    }
    
    public String generatedCode() {
        StringBuilder code = new StringBuilder();
        Integer lenght = listall().getSize() + 1;
        Integer pos = lenght.toString().length();
        for (int i = 0; i < (10 - pos); i++) {
            code.append("0");
        }
        code.append(lenght.toString());
        return code.toString();
    }
    
    public Boolean save(){
        venta.setId_venta(generated_id());
        AutoControllerListas ac = new AutoControllerListas();
        VendedorControllerListas vc = new VendedorControllerListas();
        try {
            ac.setAuto(ac.listall().get(getVenta().getId_auto()-1));
            ac.getAuto().setEstado(false);//Se cambia el estado del auto a False: NoDisponible o Vendido y se actualiza
            ac.update(getVenta().getId_auto()-1);
            
            /*vc.setVendedor(vc.listall().get(getVenta().getId_auto()));
            vc.getVendedor().setApellidos("");//Aquí se adjudica la venta del auto al vendedor*/
        } catch (VacioException ex) {
            System.out.println("error guardar venta: "+ex.getMessage());
        }
        return save(venta);
    }
    
    public void mergeSort (int arreglo [] , int ini , int fin ) {
        int m = 0 ;
        if (ini < fin) {
            m = (ini + fin) / 2 ;
            mergeSort (arreglo, ini , m) ;
            mergeSort (arreglo , m + 1 , fin ) ;
            merge (arreglo , ini , m, fin) ;
        }
    }
    
    public void merge(int arreglo[], int ini, int m, int fin){
        int k = 0;
        int i = ini;
        int j = m + 1;
        int n = fin - ini + 1;
        int b[] = new int [n]; 
        while (i <= m && j <= fin) {
            if (arreglo [i] < arreglo [j]) {
                    b [k] = arreglo [i] ;
                    i ++;
                    k ++;
                } else {
                    b [k] = arreglo [j] ;
                    j ++;
                    k ++;
                }
            }
        while (i <= m) {
            b [k] = arreglo [i] ;
            i ++;
            k ++;
        }
        while (j <= fin) {
            b [k] = arreglo [j] ;
            j ++;
            k ++;
        }
        for (k = 0; k<n ; k ++ ) {
            arreglo [ini + k] = b [k] ;
        }
    }
    
    public void mergeSortPlaca (Venta arreglo [] , int ini , int fin) throws VacioException {
        int m = 0 ;
        if (ini < fin) {
            m = (ini + fin) / 2 ;
            System.out.println("En esta parte de merge; voy de ini: "+ini+" a fin: "+fin+" y m vale: "+m);
            System.out.println("Se supone que ini es:"+arreglo[ini].getCodigoVenta()+"-"+arreglo[ini].getDescripcion()+" y fin es:"+arreglo[fin].getCodigoVenta()+"-"+arreglo[fin].getDescripcion());
            mergeSortPlaca (arreglo, ini , m) ;
            mergeSortPlaca (arreglo , m + 1 , fin);
            mergePlaca(arreglo , ini , m, fin) ;
        }
    }
    
    public void mergePlaca(Venta arreglo[], int ini, int m, int fin) throws VacioException{
        int k = 0;
        int i = ini;
        int j = m + 1;
        int n = fin - ini + 1;
        Venta b[] = new Venta [n]; 
        LinkedList<Auto> autos = new AutoControllerListas().getAutos();
        while (i <= m && j <= fin) {
                //if (new AutoControllerListas().getAutos().get(getVentas().get(i).getId_auto() - 1).getPlaca().compareTo(new AutoControllerListas().getAutos().get(getVentas().get(j).getId_auto() - 1).getPlaca()) < 0) {
                if (autos.get(arreglo[i].getId_auto() - 1).getPlaca().compareToIgnoreCase(autos.get(arreglo[j].getId_auto() - 1).getPlaca()) < 0) {
                    b[k] = arreglo[i] ;
                    i++;
                    k++;
                } else {
                    b[k] = arreglo[j] ;
                    j++;
                    k++;
                }
        }
        
        while (i <= m) {
            b[k] = arreglo[i] ;
            i++;
            k++;
        }
        while (j <= fin) {
            b[k] = arreglo[j] ;
            j++;
            k++;
        }
        for ( k = 0; k<n ; k ++ ) {
            arreglo[ini + k] = b[k] ;
        }
        //System.out.println("arreglo en "+(ini+k)+" es: "+arreglo[ini + k].getCodigoVenta());
    }
    
    public LinkedList<Venta> mergeSortVenta (Venta arreglo [] , int ini , int fin, int orden, String field) throws VacioException {
        int m = 0 ;
        if (ini < fin) {
            m = (ini + fin) / 2 ;
            mergeSortVenta (arreglo, ini , m, orden, field) ;
            mergeSortVenta (arreglo , m + 1 , fin, orden,field);
            mergeVenta(arreglo , ini , m, fin,orden, field) ;
        }
        return new LinkedList<Venta>().toList(arreglo);
    }
    
    public void mergeVenta(Venta arreglo[], int ini, int m, int fin, int orden, String field) throws VacioException{
        int k = 0;
        int i = ini;
        int j = m + 1;
        int n = fin - ini + 1;
        Venta b[] = new Venta [n]; 
        while (i <= m && j <= fin) {
                if (arreglo[i].comparar(arreglo[j], field, orden)) {
                    b [k] = arreglo [i] ;
                    i ++;
                    k ++;
                } else {
                    b [k] = arreglo [j] ;
                    j ++;
                    k ++;
                }
        }
        while (i <= m) {
            b [k] = arreglo [i] ;
            i ++;
            k ++;
        }
        while (j <= fin) {
            b [k] = arreglo [j] ;
            j ++;
            k ++;
        }
        for ( k = 0; k<n ; k ++ ) {
            arreglo [ini + k] = b [k] ;
        }
    }
  
    public void quickSort (int [] arreglo, int inicio , int fin) {
        int i = inicio; // i siempre avanza en el arreglo hacia la derecha
        int j = fin; // j siempre avanza hacia la izquierda
        int pivote = arreglo[(inicio + fin)/2] ;
        do{
            while(arreglo[i] < pivote)//si ya esta ordenado incrementa i
                i++;
            while(pivote < arreglo[j])//si ya esta ordenado decrementa j
                j--;
            if(i <= j){// Hace el intercambio
                int aux = arreglo[i];
                arreglo[i] = arreglo[j] ;
                arreglo[j] = aux ;
                i++;
                j--;
            }
            }while(i <= j);
            if(inicio < j)
                quickSort(arreglo,inicio,j);// invocación recursiva
            if(i < fin)
                quickSort(arreglo, i , fin );// invocacion recursiva
    }
    
    public LinkedList<Venta> quickSortVenta (Venta[] arreglo, int inicio , int fin, int orden, String field) throws VacioException {
        int i = inicio; // i siempre avanza en el arreglo hacia la derecha
        int j = fin; // j siempre avanza hacia la izquierda
        Venta pivote = arreglo[(inicio + fin)/2] ;
        do{
            while(arreglo[i].comparar(pivote, field, orden))//si ya esta ordenado incrementa i
                i++;
            while(pivote.comparar(arreglo[j], field, orden))//si ya esta ordenado decrementa j
                j--;
            if(i <= j){// Hace el intercambio
                Venta aux = arreglo[i];
                arreglo[i] = arreglo[j] ;
                arreglo[j] = aux ;
                i++;
                j--;
            }
            }while(i <= j);
            if(inicio < j)
                quickSortVenta(arreglo,inicio,j, orden, field);// invocación recursiva
            if(i < fin)
                quickSortVenta(arreglo, i , fin, orden, field);// invocacion recursiva
            return new LinkedList<Venta>().toList(arreglo);
    }
    
    public LinkedList<Venta> busquedaBin (String field, Object valor) throws VacioException{
        Venta[] arreglo = getVentas().toArray();
        Venta[] arrayOrdenado = this.quickSortVenta(arreglo, 0, (arreglo.length - 1), 0, field).toArray();
        LinkedList<Venta> result = new LinkedList<>();
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
    
    public LinkedList<Venta> busqLineBin(String field, Object valor, Integer orden) throws VacioException{//La que devuelve una lista
        Venta[] arreglo = getVentas().toArray();
        Venta[] arrayOrdenado = this.quickSortVenta(arreglo, 0, (arreglo.length - 1), 0, field).toArray();
        LinkedList<Venta> result = new LinkedList<>();
        for(int i = 0; i < arrayOrdenado.length ; i++){
            int buscado = arrayOrdenado[i].esSimilar(field, valor);
            if(buscado == 0 && orden == 0)//Si son iguales y busco valores iguales, añado a la lista
                result.add(arrayOrdenado[i]);
            else if(buscado < 0 && orden == 1)//Si son menores y busco valores menores, añado a la lista
                result.add(arrayOrdenado[i]);
            else if(buscado > 0 && orden == 2)//Si son mayores y busco valores mayores, añado a la lista
                result.add(arrayOrdenado[i]);
        }
        return result;//Devuelve una lista
    }
    
    public Boolean update(Integer index) {
        return update(venta, index);
    }
    
    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Venta getVenta() {
        if(venta == null)
            venta = new Venta();
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public LinkedList<Venta> getVentas() {
        if(ventas.isEmpty())
            ventas = listall();
        return ventas;
    }

    public void setVentas(LinkedList<Venta> ventas) {
        this.ventas = ventas;
    }
    
    public static void main(String[] args) {
        //int arreglo[] = {2,0,6,4,3,1};i
        /*int arreglo[] = new int[10];
        for (int i = 0; i < 10; i++) {
            arreglo[i] = (int)(Math.random()*1000);
        }
        System.out.println("arreglo:");
        for (int i = 0; i < arreglo.length; i++) {
            System.out.println(arreglo[i]);
        }
        System.out.println("nuevo arreglo:");
        new VentaControllerListas().quickSort(arreglo, 0, arreglo.length-1);
        for (int i = 0; i < arreglo.length; i++) {
            System.out.println(arreglo[i]);
        }*/
        String a = "24-01-04";
        String b = "23-11-12";
        System.out.println(b.compareTo(a));
    }
}
