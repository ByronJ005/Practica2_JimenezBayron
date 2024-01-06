package controlador.Listas;
import controlador.TDALista.LinkedList;
import controlador.listas.DAO.DataAccesObject;
import modelo.Auto;

/**
 *
 * @author Asus
 */
public class AutoControllerListas extends DataAccesObject<Auto>{
    private LinkedList<Auto> autos = new LinkedList<>();
    private Auto auto = new Auto();
    private Integer index = -1;
    
    public AutoControllerListas() {
        super(Auto.class);
    }

    public Boolean save(){
        this.auto.setId(generated_id());
        return save(auto);
    }
    
    public Boolean update(Integer index) {
        return update(auto, index);
    }
    
    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
    
    public LinkedList<Auto> getAutos() {
        if(autos.isEmpty())
            autos = listall();
        return autos;
    }

    public void setAutos(LinkedList<Auto> autos) {
        this.autos = autos;
    }

    public Auto getAuto() {
        if(auto == null)
            auto = new Auto();
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }
    
    
    
    
}
