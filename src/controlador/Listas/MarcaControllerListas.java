package controlador.listas;
import controlador.TDALista.LinkedList;
import controlador.TDALista.exceptions.VacioException;
import controlador.listas.DAO.DataAccesObject;
import modelo.Marca;
/**
 *
 * @author Asus
 */
public class MarcaControllerListas extends DataAccesObject<Marca>{
    private LinkedList<Marca> marcas = new LinkedList<>();
    private Marca marca;
    
    public MarcaControllerListas(){
        super(Marca.class);
    }

    public Marca getMarca() {
        if(marca == null)
            marca = new Marca();
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    
    public LinkedList<Marca> getMarcas() {
        if(marcas.isEmpty())
            marcas = this.listall();
        return marcas;
    }

    public void setMarcas(LinkedList<Marca> marcas) {
        this.marcas = marcas;
    }
    
    //Si queremos hacer bien deberíamos reescribir el metodo save
    public Boolean save(){
        this.marca.setId(generated_id());
        return this.save(marca);
    }

    public static void main(String[] args) {
        MarcaControllerListas mc = new MarcaControllerListas();
        //Se guardan las marcas estándar
            
        mc.getMarca().setId(mc.generated_id());
        mc.getMarca().setNombre("Toyota");
        mc.getMarca().setEstado(true);
        mc.save();
        
        mc.getMarca().setId(mc.generated_id());
        mc.getMarca().setNombre("Chevrolet");
        mc.getMarca().setEstado(true);
        mc.save();
        
        mc.getMarca().setId(mc.generated_id());
        mc.getMarca().setNombre("Nissan");
        mc.getMarca().setEstado(true);
        mc.save();
        
        mc.getMarca().setId(mc.generated_id());
        mc.getMarca().setNombre("Hyundai");
        mc.getMarca().setEstado(true);
        mc.save();
        
        mc.getMarca().setId(mc.generated_id());
        mc.getMarca().setNombre("Audi");
        mc.getMarca().setEstado(true);
        mc.save();
        
        mc.getMarca().setId(mc.generated_id());
        mc.getMarca().setNombre("Kia");
        mc.getMarca().setEstado(true);
        mc.save();
        
        System.out.println(mc.listall().print());
        
    }
    
    
}
