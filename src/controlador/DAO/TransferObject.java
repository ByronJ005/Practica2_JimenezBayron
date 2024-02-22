package controlador.DAO;

import controlador.listas.LinkedList;

/**
 *
 * @author Asus
 */
public interface TransferObject <T>{
    public Boolean save(T data);
    public Boolean update(T data, Integer index);
    public LinkedList<T> listall();
    public T find(Integer id);
    
}
