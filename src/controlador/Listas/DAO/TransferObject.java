/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.listas.DAO;

import controlador.TDALista.LinkedList;

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
