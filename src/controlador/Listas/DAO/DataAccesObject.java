package controlador.listas.DAO;
import com.thoughtworks.xstream.XStream;
import controlador.TDALista.LinkedList;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;

/**
 *
 * @author Asus
 */
public class DataAccesObject<T> implements TransferObject<T>{
    private XStream xstream;
    private Class clazz;
    private String URL;

    public DataAccesObject(Class<T> clazz){
        xstream = Connection.getXstream();
        this.clazz = clazz;
        URL = Connection.getURL()+this.clazz.getSimpleName()+".json";
    }
            
    
    @Override
    public Boolean save(T data) {
        LinkedList<T> list = listall();
        list.add(data);
        try {
            this.xstream.toXML(list, new FileOutputStream(URL));
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println("Error en save: "+ex.getMessage());
            return false;
        }
    }

    @Override
    public Boolean update(T data, Integer index) {
        LinkedList<T> list = listall();
        try {
            list.update(data, index);
            this.xstream.alias(list.getClass().getName(), LinkedList.class);
            this.xstream.toXML(list, new FileOutputStream(URL));
            return true;
        } catch (Exception e) {
            System.out.println("Error en update: "+e.getMessage());
            return false;
        }
    }

    @Override
    public LinkedList<T> listall() {
        LinkedList<T> list = new LinkedList<>();
        try {
            list = (LinkedList<T>)xstream.fromXML(new FileReader(URL));
        } catch (Exception e) {
            System.out.println("Error en listall: "+e.getMessage());
        }
        return list;
    }

    @Override
    public T find(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public Integer generated_id(){
        return listall().getSize() + 1;
    }
}
